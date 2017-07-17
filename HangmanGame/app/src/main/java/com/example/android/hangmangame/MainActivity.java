package com.example.android.hangmangame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    final int CAMERA_PERMISSION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void intentgen(View view)
    {
        Intent i = new Intent(MainActivity.this,OCRActivity.class);
        startActivityForResult(i,CAMERA_PERMISSION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CAMERA_PERMISSION)
        {
            if(resultCode == RESULT_OK)
            {
                Intent i = new Intent(MainActivity.this,CategoriesActivity.class);
                startActivity(i);
            }
        }
    }
}
