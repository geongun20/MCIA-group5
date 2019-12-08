package com.example.smokare;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;
import android.os.Handler;

import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.lang.Math;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class WearService extends Service implements SensorEventListener {
    public WearService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    public static final String TAG = "TestFileActivity";


    private TextView mTextView;
    private static final String TAG2 = "MainActivity";
    private DataClient dataClient;
    private int count = 0;
    private Handler handler;
    private Runnable mRunnable;

    int dragvalue = 1;
    int dragvalue2 = 1;
    int dragcount = 0;
    int dragcount2 = 0;
    Float x, y;
    int numdrags = 0;
    int numcig = 0;
    Float azimut;
    Float pitch;

    File file;
    File file_cig;

    String outstr = "";
    String outstr_cig = "";
    Scanner sc;


    private SensorManager mSensorManager;
    Sensor accelerometer;
    Float roll;
    Sensor magnetometer;

    Float maxAccelCurr = 0f;
    Float maxAccelPrev;
    Float accelCurr = 0f;



    private boolean writeFile(File file, byte[] file_content) {
        boolean result;
        FileOutputStream fos;
        if (file != null && file.exists() && file_content != null) {
            try {
                fos = new FileOutputStream(file);
                try {
                    fos.write(file_content);
                    fos.flush();
                    // fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    private static String readAllBytesJava7(String filePath) {
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {

        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel("mychannel", channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification =
                new Notification.Builder(this, "mychannel")
                        .setContentTitle("Wear Service")
                        .setContentText("Wear Serivce test")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentIntent(pendingIntent).build();
                        //.setTicker(getText(R.string.ticker_text))
        NotificationManagerCompat notificationManager=NotificationManagerCompat.from(this);

        notificationManager.notify(001,notification);
        startForeground(1, notification);

        File dir = getExternalFilesDir(null);

        boolean isSuccess = false;
        if (dir.isDirectory()) {
            file = new File(getExternalFilesDir(null), "output.txt");
            if (file != null && !file.exists()) {
                Log.i(TAG2, "!file.exists");
                try {
                    isSuccess = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Log.i(TAG2, "파일생성 여부 = " + isSuccess);
                }
            } else {
                Log.i(TAG2, "file.exists");
            }
        }
        boolean isSuccess_cig = false;
        if (dir.isDirectory()) {
            file_cig = new File(getExternalFilesDir(null), "cig.txt");
            if (file_cig != null && !file_cig.exists()) {
                Log.i(TAG2, "!file_cig.exists");
                try {
                    isSuccess_cig = file_cig.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    Log.i(TAG2, "파일생성 여부 = " + isSuccess_cig);
                }
            } else {
                Log.i(TAG2, "file_cig.exists");
            }
        }

        outstr_cig = readAllBytesJava7(getExternalFilesDir(null) + "/cig.txt");



        handler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (accelCurr > maxAccelCurr)
                    maxAccelCurr = accelCurr;

                if (dragvalue == 1) {
                    dragcount++;
                    Log.d("dragcount", Integer.toString(dragcount));
                } else {
                    if (dragcount >= 5 && dragcount < 20) {
                        Log.d("drag","drag");
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        outstr = (sdf.format(timestamp) + "\n") + outstr;
                        writeFile(file, outstr.getBytes());

                        try {
                            sc = new Scanner(file);
                            int count = 1;
                            String line = sc.nextLine();
                            int hour = Integer.parseInt(line.substring(11, 13));
                            int minute = Integer.parseInt(line.substring(14, 16)) + hour * 60;
                            int second = Integer.parseInt(line.substring(17, 19)) + minute * 60;
                            for (; sc.hasNextLine(); ) {
                                line = sc.nextLine();
                                if (line.equals("asd")) {
                                    break;
                                }
                                int hour2 = Integer.parseInt(line.substring(11, 13));
                                int minute2 = Integer.parseInt(line.substring(14, 16)) + hour2 * 60;
                                int second2 = Integer.parseInt(line.substring(17, 19)) + minute2 * 60;
                                if (second < second2 + 240){
                                    count++;}
                                else {
                                    break;
                                }
                            }
                            Log.d("count",Integer.toString(count));
                            if (count >= 8) {
                                putDataToPhone();
                                numcig++;
                                outstr = ("asd\n") + outstr;
                                writeFile(file, outstr.getBytes());
                                Vibrator vib = (Vibrator) getSystemService((VIBRATOR_SERVICE));
                                vib.vibrate(1000);
                            }
                        } catch (FileNotFoundException e) {

                        }
                        maxAccelCurr = 0f;
                    }
                    dragcount = 0;
                }
                //Log.d("accel", accelCurr.toString() + "\n");


                //System.out.println("dragcount:" + dragcount + "\ndragvalue:" + dragvalue + "\nnumcig:" + numcig);
                //Toast.makeText(getApplicationContext(), "dragcount:"+dragcount+" dragvalue:"+dragvalue+"numdrag:"+numdrags, Toast.LENGTH_LONG).show();

                handler.postDelayed(mRunnable, 400);// move this inside the run method
            }
        };
        mRunnable.run();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,magnetometer,SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;

    }




    public void putDataToPhone() {


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        outstr_cig=(sdf.format(timestamp)+"\n")+outstr_cig;
        writeFile(file_cig,outstr_cig.getBytes());


        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/count");


        putDataMapRequest.getDataMap().putString("count", outstr_cig);

        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        request.setUrgent();

        //Task<DataItem> dataItemTask = dataClient.putDataItem(request);
        Task<DataItem> dataItemTask =
                Wearable.getDataClient(getApplicationContext()).putDataItem(request);

//        try {
//            // Block on a task and get the result synchronously (because this is on a background
//            // thread).
//            DataItem dataItem = Tasks.await(dataItemTask);
//
//        } catch (ExecutionException exception) {
//            Log.e(TAG, "Task failed: " + exception);
//
//        } catch (InterruptedException exception) {
//            Log.e(TAG, "Interrupt occurred: " + exception);
//        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    float[] mGravity;
    float[] mGeomagnetic;
    int i=0;

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimut = orientation[0]; // orientation contains: azimut, pitch and roll
                pitch = orientation[1];
                roll = orientation[2];

                x=(float)(pitch-0.5);
                y=(float)(roll+1.517);
                x=(float)(x*Math.cos(3.1416*18/180))-(float)(y*Math.sin(3.1416*18/180));
                y=(float)(x*Math.sin(3.1416*18/180))+(float)(y*Math.cos(3.1416*18/180));
                if (x*x/0.2828+y*y/1.3984<1) {
                    dragvalue = 1;
                }else {
                    dragvalue = 0;
                }

            }

            // total acc
            accelCurr = (float)Math.sqrt(mGravity[0]* mGravity[0] + mGravity[1]*mGravity[1]
                    + mGravity[2]*mGravity[2]);
        }
        // mCustomDrawableView.invalidate();

    }

    @Override
    public void onDestroy() {

    }


}
