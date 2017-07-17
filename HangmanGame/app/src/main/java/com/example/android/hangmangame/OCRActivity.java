package com.example.android.hangmangame;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;


public class OCRActivity extends AppCompatActivity {

    CameraSource cameraSource;
    SurfaceView S;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        S = (SurfaceView) findViewById(R.id.sv);
        createCameraSource();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        return;
                    }
                    try
                    {
                        cameraSource.start(S.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }


    public void createCameraSource()
    {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (textRecognizer.isOperational())
        {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setAutoFocusEnabled(true)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedFps(5.0f)
                    .build();


            S.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder)
                {
                    try
                    {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                        {
                            ActivityCompat.requestPermissions(OCRActivity.this,new String[]{Manifest.permission.CAMERA},1);
                            return;
                        }
                        cameraSource.start(S.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
                {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder)
                {

                    if(cameraSource!=null)
                        cameraSource.release();

                    cameraSource = null;
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release()
                {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections)
                {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();

                    if(items.size()!=0)
                    {
                        for(int i=0;i<items.size();i++)
                        {
                            
                            if(items.valueAt(i).getValue().toLowerCase().equals("hangman"))
                            {
                                Intent I = getIntent();
                                setResult(RESULT_OK,I);
                                finish();
                            }
                        }
                    }
                }
            });
        }
    }
}
