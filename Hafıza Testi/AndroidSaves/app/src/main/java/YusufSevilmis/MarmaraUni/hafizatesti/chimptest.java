package YusufSevilmis.MarmaraUni.hafizatesti;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class chimptest extends AppCompatActivity {


    HafizaTestDbHelper dbHelper;
    SQLiteDatabase db;
    ContentValues values;


    Button btn00,btn01,btn02,btn03,
            btn10,btn11,btn12,btn13,
            btn20,btn21,btn22,btn23,
            btn30,btn31,btn32,btn33,
            btn40,btn41,btn42,btn43,
            btn50,btn51,btn52,btn53,
            btn60,btn61,btn62,btn63,
            btn70,btn71,btn72,btn73,basla;
    int bulunanSayi = 0, skor = 5, level = 5;

    TextView maxSkor,suankiSkor;
    Group game;
    Button[] deneme = new Button[32];
    Random rnd = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.chimptest);

        dbHelper = new HafizaTestDbHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        game = findViewById(R.id.gameOver1);

        level = 5;


        suankiSkor = findViewById(R.id.skor);

        btn00 = findViewById(R.id.button00); btn01 = findViewById(R.id.button01); btn02 = findViewById(R.id.button02); btn03 = findViewById(R.id.button03);
        btn10 = findViewById(R.id.button10); btn11 = findViewById(R.id.button11); btn12 = findViewById(R.id.button12); btn13 = findViewById(R.id.button13);
        btn20 = findViewById(R.id.button20); btn21 = findViewById(R.id.button21); btn22 = findViewById(R.id.button22); btn23 = findViewById(R.id.button23);
        btn30 = findViewById(R.id.button30); btn31 = findViewById(R.id.button31); btn32 = findViewById(R.id.button32); btn33 = findViewById(R.id.button33);
        btn40 = findViewById(R.id.button40); btn41 = findViewById(R.id.button41); btn42 = findViewById(R.id.button42); btn43 = findViewById(R.id.button43);
        btn50 = findViewById(R.id.button50); btn51 = findViewById(R.id.button51); btn52 = findViewById(R.id.button52); btn53 = findViewById(R.id.button53);
        btn60 = findViewById(R.id.button60); btn61 = findViewById(R.id.button61); btn62 = findViewById(R.id.button62); btn63 = findViewById(R.id.button63);
        btn70 = findViewById(R.id.button70); btn71 = findViewById(R.id.button71); btn72 = findViewById(R.id.button72); btn73 = findViewById(R.id.button73);
        basla = findViewById(R.id.Start1);

        deneme = new Button[]{btn00, btn01, btn02, btn03,
                btn10, btn11, btn12, btn13,
                btn20, btn21, btn22, btn23,
                btn30, btn31, btn32, btn33,
                btn40, btn41, btn42, btn43,
                btn50, btn51, btn52, btn53,
                btn60, btn61, btn62, btn63,
                btn70, btn71, btn72, btn73};



        for (int i = 0; i < 32;i++)
        {
            deneme[i].setVisibility(View.INVISIBLE);
        }

        basla.setText("Oyuna Başla");

        basla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                butonHazirlik();

                selectButton();


                game.setVisibility(View.INVISIBLE);

                for (int i = 0; i < 32;i++)
                {
                    deneme[i].setTextColor(0xFFFFFFFF);
                }
            }
        });


            suankiSkor.setText("");



    }

    public void butonHazirlik() {

        for (int i = 0; i < 32;i++)
        {
            int finalI = i;
            deneme[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deneme[finalI].getText() == String.valueOf(bulunanSayi+1))
                    {
                        deneme[finalI].setVisibility(View.INVISIBLE);

                        bulunanSayi++;
                        Log.d("Bulunan sayi deger" , String.valueOf(bulunanSayi));
                    } else {

                        if(MainActivity.suankiKullanici>-1) {
                            dbHelper.chimpPuanKaydet(MainActivity.suankiKullanici,skor);
                        }


                        skor = 5;

                        level = 5;

                        basla.setText("Yeni Oyuna Başla");

                        game.setVisibility(View.VISIBLE);

                        bulunanSayi = 0;

                        for (int i = 0; i < 32;i++)
                        {
                            deneme[i].setVisibility(View.INVISIBLE);
                        }
                    }

                    if (bulunanSayi == 1){
                        for (int i = 0; i < 32;i++)
                        {
                            deneme[i].setTextColor(0x00FFFFFFF);
                        }
                    }

                    if (bulunanSayi == level)
                    {
                        skor++;
                        suankiSkor.setText("Skor: "+skor);
                        bulunanSayi = 0;
                        Log.d("Bulunan sayi sifirlama<" , String.valueOf(bulunanSayi));

                        levelUp();


                    }
                }
            });
        }

    }
    public void selectButton() {
        int[] sayilar = new int[level];
        int secilmis;
        int i = 0;
        while( i < level) {

            secilmis = rnd.nextInt(32);
            boolean alreadyExists = false;
            for (int j = 0; j < sayilar.length; j++) {
                if (sayilar[j] == secilmis) {
                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists) {
                sayilar[i] = secilmis;
                i++;
            }

        }
        for (int g = 0 ; g < level ; g++) {

            deneme[sayilar[g]].setVisibility(View.VISIBLE);
            deneme[sayilar[g]].setText(String.valueOf(g + 1));
            Log.d("Array check" , String.valueOf(deneme[sayilar[g]].getText()));
            Log.d("Sayi olustur" , String.valueOf(sayilar[g]));
        }

    }

    public void levelUp() {
        if(level >= 32) {
            level = 32;
            for (int i = 0; i < 32;i++)
            {
                deneme[i].setTextColor(0xFFFFFFF);
            }
            selectButton();
        } else {
            level++;
            for (int i = 0; i < 32;i++)
            {
                deneme[i].setTextColor(0xFFFFFFFF);
            }
            Log.d("level" , String.valueOf(level));
            selectButton();
        }

    }





}