package YusufSevilmis.MarmaraUni.hafizatesti;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    public static int suankiKullanici = -1;

    static Button girisButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        HafizaTestDbHelper db = new HafizaTestDbHelper(getApplicationContext());

        chimptest test = new chimptest();
        numbertest test1 = new numbertest();

        giris giris1 = new giris();


        Button chimpButton = (Button) findViewById(R.id.ChimpButton);

        Button numberButton = (Button) findViewById(R.id.NumberButton);

        Button scoreButton = (Button) findViewById(R.id.Skorlar);

        girisButton = findViewById(R.id.giris);



        chimpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, chimptest.class);
                startActivity(intent);
            }
        });

        girisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(suankiKullanici>-1){
                    suankiKullanici = -1;
                    girisButton.setText("Giriş Yap");
                }
                else {
                    Intent intent = new Intent(MainActivity.this, giris.class);
                    startActivity(intent);
                }
            }
        });

        numberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, numbertest.class);
                startActivity(intent);
            }
        });

        girisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, giris.class);
                startActivity(intent);
            }
        });

        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, scoreboard.class);
                startActivity(intent);
            }
        });

    }


    public static void setSuankiKullanici(int kullaniciId){
        suankiKullanici = kullaniciId;
    }

    public static void setGirisButton(){
        girisButton.setText("Çıkış Yap");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (suankiKullanici > -1) {
            girisButton.setText("Çıkış Yap");

            girisButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if(suankiKullanici>-1){
                        suankiKullanici = -1;
                        girisButton.setText("Giriş Yap");
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, giris.class);
                        startActivity(intent);
                    }
                }
            });

        } else {
            girisButton.setText("Giriş Yap");
        }
    }

}