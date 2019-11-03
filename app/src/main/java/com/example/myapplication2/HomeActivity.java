package com.example.myapplication2;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {

    private boolean isFragmentA = true;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    public static final String TAG = "TestFileActivity";

    int dragvalue = 1;
    int dragvalue2 = 1;
    int dragcount = 0;
    int dragcount2 = 0;
    int numdrags = 0;
    int numcig = 0;
    private Handler handler;
    private Runnable mRunnable;
    Float azimut;
    Float pitch;

    File file;
    String outstr="";
    Scanner sc;


    private SensorManager mSensorManager;
    Sensor accelerometer;
    Float roll;
    Sensor magnetometer;

    TextView t1;

    private boolean writeFile(File file , byte[] file_content){
        boolean result;
        FileOutputStream fos;
        if(file!=null&&file.exists()&&file_content!=null){
            try {
                fos = new FileOutputStream(file);
                try {
                    fos.write(file_content);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            result = true;
        }else{
            result = false;
        }
        return result;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, new FragmentA());
        fragmentTransaction.commit();

        Button buttonTemp = findViewById(R.id.button0) ;
        buttonTemp.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment();
            }
        });

        ImageButton buttonH = findViewById(R.id.imageButton1) ;
        buttonH.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonT = findViewById(R.id.imageButton2) ;
        buttonT.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TimelineActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonR = findViewById(R.id.imageButton3) ;
        buttonR.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonS = findViewById(R.id.imageButton4) ;
        buttonS.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


        t1 = findViewById(R.id.myTextView);//shoudbemodified

        File dir = new File(Environment.getExternalStorageDirectory()+"/testfolder/");
        if (!dir.exists())
        {
            dir.mkdirs();
            Log.i( TAG , "!dir.exists" );
        }else{
            Log.i( TAG , "dir.exists" );
        }

        boolean isSuccess = false;
        if(dir.isDirectory()){
            file = new File(Environment.getExternalStorageDirectory()+"/testfolder/output.txt");
            if(file!=null&&!file.exists()){
                Log.i( TAG , "!file.exists" );
                try {
                    isSuccess = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    Log.i(TAG, "파일생성 여부 = " + isSuccess);
                }
            }else{
                Log.i( TAG , "file.exists" );
            }
        }




        handler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (dragvalue == 1) {
                    dragcount++;

                } else {
                    if (dragcount>20&&dragcount<100) {
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        outstr=(sdf.format(timestamp)+"\n")+outstr;
                        writeFile(file,outstr.getBytes());

                        try {
                            sc = new Scanner(file);
                            int count = 1;
                            String line=sc.nextLine();
                            int hour= Integer.parseInt(line.substring(11,13));
                            int minute = Integer.parseInt(line.substring(14,16))+hour*60;
                            int second = Integer.parseInt(line.substring(17,19))+minute*60;
                            for (;sc.hasNextLine();) {
                                line = sc.nextLine();
                                System.out.println(line+"\n");
                                if(line.equals("asd")){
                                    System.out.println("debug");
                                    break;
                                }
                                int hour2= Integer.parseInt(line.substring(11,13));
                                int minute2 = Integer.parseInt(line.substring(14,16))+hour2*60;
                                int second2 = Integer.parseInt(line.substring(17,19))+minute2*60;
                                System.out.println("second "+second+" Second2 "+second2+"count "+count);
                                if(second<second2+240)count++;

                                else break;
                            }
                            if(count>=8){
                                numcig++;
                                outstr=("asd\n")+outstr;
                                writeFile(file,outstr.getBytes());
                            }
                        }catch(FileNotFoundException e){

                        }

                    }
                    dragcount = 0;
                }
                //  lightercount++;
                //System.out.println("dragcount:"+dragcount+" dragvalue:"+dragvalue);
                //System.out.println("numcig:"+numcig);


                t1.setText("dragcount:"+dragcount+"\ndragvalue:"+dragvalue+"\nnumcig:"+numcig);
                //Toast.makeText(getApplicationContext(), "dragcount:"+dragcount+" dragvalue:"+dragvalue+"numdrag:"+numdrags, Toast.LENGTH_LONG).show();

                handler.postDelayed(mRunnable, 100);// move this inside the run method
            }
        };
        mRunnable.run();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);




    }


    public void switchFragment() {
        Fragment fr;
        fr = isFragmentA ? new FragmentB() : new FragmentA();
        isFragmentA = !isFragmentA;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fr);
        fragmentTransaction.commit();
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
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
                /*Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                outstr+=(sdf.format(timestamp)+" "+pitch+"\t"+roll+"\n");
                writeFile(file,outstr.getBytes());*/
                //System.out.println("pitch: "+pitch);
                //System.out.println("roll: "+roll);
                if ((pitch <= (-0.2) && pitch >= (-1.2)) && (roll >= 0.3 && roll <= 2.2)) {
                    dragvalue = 1;
                }else {
                    dragvalue = 0;
                }
                /*if((pitch <= (-0.2) && pitch >= (-1.2)) && (roll >= -1.2 && roll <= 0.6)) {
                    dragvalue2 = 1;
                } else {
                        dragvalue2 = 0;

                }*/
            }
        }
        // mCustomDrawableView.invalidate();

    }


}
