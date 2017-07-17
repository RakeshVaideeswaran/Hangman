package com.example.android.hangmangame;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.CharacterPickerDialog;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import static com.example.android.hangmangame.R.id.anstxtview;


public class Gameplay extends AppCompatActivity {

    String[] words;
    String WORD=null;
    char c;
    char[] MOD_WORD=null;
    TextView T,A;
    ImageView I;
    LinearLayout AL,BL;
    int n,count,imgcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        count=0;
        imgcount=0;
        words = new String[30];
        MOD_WORD = null;
        AL = (LinearLayout) findViewById(R.id.anslayout);
        BL = (LinearLayout) findViewById(R.id.buttonlayout);
        A = (TextView) findViewById(anstxtview);
        I = (ImageView) findViewById(R.id.imgview);
        T = (TextView) findViewById(R.id.txtview);
        playingList();
        startGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(getApplicationContext());
        inflater.inflate(R.menu.categories,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(Gameplay.this,CategoriesActivity.class);
        startActivity(i);
        finish();
        return true;
    }

    public void playingList()
    {
        Resources res = getApplicationContext().getResources();
        Intent i = getIntent();
        char c = i.getCharExtra("playinglist",'c');

        switch (c)
        {
            case 'c': words = res.getStringArray(R.array.countries); break;
            case 'C': words = res.getStringArray(R.array.colours);break;
            case 'v': words = res.getStringArray(R.array.vegetables);break;
            case 'f': words = res.getStringArray(R.array.fruits);break;
            case 'a': words = res.getStringArray(R.array.animals);break;
        }

       // T.setText(words[0]);
    }

    public void startGame()
    {
        Random random = new Random();
        n = random.nextInt(30);

        WORD = new String(words[n]);
        MOD_WORD = new char[WORD.length()];

        for(int i = 0;i < WORD.length(); i++)
            MOD_WORD[i] = '-';

        T.setText(String.valueOf(MOD_WORD));

    }

    public void gamePlay(View view)
    {
        int buttonclicked = view.getId();
        Button B = (Button) view;

        switch(buttonclicked)
        {
            case R.id.A: c = 'a'; break;
            case R.id.B: c = 'b'; break;
            case R.id.C: c = 'c'; break;
            case R.id.D: c = 'd'; break;
            case R.id.E: c = 'e'; break;
            case R.id.F: c = 'f'; break;
            case R.id.G: c = 'g'; break;
            case R.id.H: c = 'h'; break;
            case R.id.I: c = 'i'; break;
            case R.id.J: c = 'j'; break;
            case R.id.K: c = 'k'; break;
            case R.id.L: c = 'l'; break;
            case R.id.M: c = 'm'; break;
            case R.id.N: c = 'n'; break;
            case R.id.O: c = 'o'; break;
            case R.id.P: c = 'p'; break;
            case R.id.Q: c = 'q'; break;
            case R.id.R: c = 'r'; break;
            case R.id.S: c = 's'; break;
            case R.id.T: c = 't'; break;
            case R.id.U: c = 'u'; break;
            case R.id.V: c = 'v'; break;
            case R.id.W: c = 'w'; break;
            case R.id.X: c = 'x'; break;
            case R.id.Y: c = 'y'; break;
            case R.id.Z: c = 'z'; break;
        }

        B.setVisibility(View.INVISIBLE);
        Log.d("gameplay","c = "+c);
        searchChar(c);
    }


    public void searchChar(char c)
    {
        count=0;

        for(int i=0;i<WORD.length();i++)
            if(c == WORD.toLowerCase().charAt(i))
            {
                count=1;
                MOD_WORD[i] = Character.toUpperCase(c);
            }

            Log.d("Searchchar","count = " + count);
        if(count==1)
            UIupdate();

        else
            setImage();

    }


    public void setImage()
    {
        imgcount++;

        switch(imgcount)
        {
            case 1: I.setImageResource(R.drawable.pic2);break;
            case 2: I.setImageResource(R.drawable.pic3);break;
            case 3: I.setImageResource(R.drawable.pic4);break;
            case 4: I.setImageResource(R.drawable.pic5);break;
            case 5: I.setImageResource(R.drawable.pic6);break;
            case 6: I.setImageResource(R.drawable.pic7);break;
            case 7: I.setImageResource(R.drawable.pic8);break;
        }

        if(imgcount == 7)
        {
            Toast.makeText(getApplicationContext(),"YOU LOSE",Toast.LENGTH_LONG).show();
            showAnswer();
            imgcount = 0;
        }
    }

    public void showAnswer()
    {
        int i;
        BL.setVisibility(View.GONE);
        AL.setVisibility(View.VISIBLE);



        for(i = 0; i<MOD_WORD.length;i++)
            if(MOD_WORD[i] == '-')
            {
                MOD_WORD[i] = WORD.toLowerCase().charAt(i);
            }

        SpannableStringBuilder RESULT = new SpannableStringBuilder();

        String s;
        SpannableString S;
        for(i=0;i<MOD_WORD.length;i++)
        {
            if(Character.isLowerCase(MOD_WORD[i]))
            {
                s = Character.toUpperCase(MOD_WORD[i]) + "";
                S = new SpannableString(s);
                S.setSpan(new ForegroundColorSpan(getApplicationContext().getResources().getColor(R.color.nguesslettercolor)),0,s.length(),0);
                RESULT.append(S);
            }

            else
            {
                s = MOD_WORD[i] + "";
                S = new SpannableString(s);
                S.setSpan(new ForegroundColorSpan(getApplicationContext().getResources().getColor(R.color.deflettercolor)),0,s.length(),0);
                RESULT.append(S);
            }
        }


        A.setText(RESULT, TextView.BufferType.SPANNABLE);
    }

    public void UIupdate()
    {
        Log.d("UIupdate",String.valueOf(MOD_WORD));
        T.setText(String.valueOf(MOD_WORD));
        checkResult();
    }

    public void checkResult()
    {
        if(String.valueOf(MOD_WORD).equals(WORD))
            Toast.makeText(getApplicationContext(),"YOU WIN",Toast.LENGTH_LONG).show();
    }
}
