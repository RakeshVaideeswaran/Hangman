package com.example.android.hangmangame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CategoriesActivity extends AppCompatActivity {

    RadioGroup RG;
    int selID;
    char c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
    }


    public void chooseCorrectCategory(View view)
    {
        RG = (RadioGroup) findViewById(R.id.RG);
        selID = RG.getCheckedRadioButtonId();

        switch(selID)
        {
            case R.id.COUNTRIES : c = 'c'; break;
            case R.id.ANIMALS : c = 'a'; break;
            case R.id.COLOURS : c = 'C'; break;
            case R.id.VEGETABLES : c = 'v'; break;
            case R.id.FRUITS : c = 'f'; break;
        }

        Intent intent = new Intent(CategoriesActivity.this,Gameplay.class);
        intent.putExtra("playinglist",c);
        startActivity(intent);
    }
}












