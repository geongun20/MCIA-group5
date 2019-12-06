package com.example.smokare;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;


import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

public class getDataService extends Service implements DataClient.OnDataChangedListener {

    FileOutputStream fos;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static final String COUNT_KEY = "com.example.key.count";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    public static final String TAG2 = "TestFileActivity";

    private String timestamp = "";

    File file;
    String outstr="";
    Scanner sc;



    TextView mText;


    private boolean writeFile(File file , byte[] file_content){
        boolean result;
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
    public int onStartCommand(Intent intent, int flags, int startID){
        Log.d("debug","debug");
        File dir = new File(getExternalFilesDir(null)+"/testfolder/");
        System.out.println("debug1:"+getExternalFilesDir(null));
        if (!dir.exists())
        {
            dir.mkdirs();
            Log.i( TAG2 , "!dir.exists" );
        }else{
            Log.i( TAG2 , "dir.exists" );
        }

        boolean isSuccess = false;
        if(dir.isDirectory()){
            System.out.println("debug2:"+getExternalFilesDir(null));
            file = new File(getExternalFilesDir(null)+"/testfolder/output.txt");
            if(file!=null&&!file.exists()){
                Log.i( TAG2 , "!file.exists" );
                try {
                    isSuccess = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    Log.i(TAG2, "파일생성 여부 = " + isSuccess);
                }
            }else{
                Log.i( TAG2 , "file.exists" );
            }
        }

        Wearable.getDataClient(this).addListener(this);

        return START_STICKY; // TODO edit
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d("debug","debug2");
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/count") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    updateCount(dataMap.getString("count"));
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }

    // Our method to update the count
    private void updateCount(String c) {
        timestamp = c;
        writeFile(file, c.getBytes());
    }

    public void onDestroy() {
        Wearable.getDataClient(this).removeListener(this);
    }
}
