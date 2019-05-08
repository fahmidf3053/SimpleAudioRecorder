package com.example.recordaudio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int RequestPermissionCode = 1;
    private Button record;
    private Button stop;
    private MediaRecorder myAudioRecorder;
    private String outputFile;
    MediaPlayer mediaPlayer;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("permission", "Permission already granted.");
            }
            else {
                requestPermission();
            }
        }

        record=(Button)findViewById(R.id.button);
        stop=(Button)findViewById(R.id.button2);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() +"/AudioRecording.mp3";
        //System.out.println(outputFile);

        random = new Random();

//        record.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Please press to Record", Toast.LENGTH_LONG).show();
//            }
//        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Recording Playing",
                        Toast.LENGTH_LONG).show();
            }
        });




        record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        try {
                            myAudioRecorder = new MediaRecorder();
                            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                            myAudioRecorder.setOutputFile(outputFile);
                            System.out.println(" Dhuksi ami eine2");
                            myAudioRecorder.prepare();
                            myAudioRecorder.start();

                        } catch (IllegalStateException ise) {
                            System.out.println(" catch 1");
                        } catch (IOException ioe) {
                            System.out.println(""+ioe);

                            System.out.println(" catch 2");
                        }
                        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                        System.out.println(" pressed ");
                        return true;
                    case MotionEvent.ACTION_UP:

                        if( myAudioRecorder!=null){
                            myAudioRecorder.stop();
                            myAudioRecorder.release();
                            myAudioRecorder = null;
                            System.out.println("File saved to : "+outputFile);
                        }
                        else{
                            System.out.println("Error on opening");
                        }
                        Toast.makeText(getApplicationContext(), "Audio Recorder stopped", Toast.LENGTH_LONG).show();
                        System.out.println(" released ");
                        return true;
                }
                return false;
            }
        });
    }


    /*public boolean checkPermission() {

        int CallPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);

        return CallPermissionResult == PackageManager.PERMISSION_GRANTED;

    }*/

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

   /* private void requestPermission() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        Manifest.permission.RECORD_AUDIO
                }, PERMISSION_REQUEST_CODE);

    }

    private void requestPermission1() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, PERMISSION_REQUEST_CODE);

    }

    private void requestPermission2() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, PERMISSION_REQUEST_CODE);

    }


    private  void startRec(MediaRecorder myAudioRecorder) throws IOException {
        myAudioRecorder.prepare();
        myAudioRecorder.start();

    }*/

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }
}
