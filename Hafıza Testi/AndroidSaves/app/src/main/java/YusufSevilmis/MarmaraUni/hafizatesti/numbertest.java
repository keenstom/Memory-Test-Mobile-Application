package YusufSevilmis.MarmaraUni.hafizatesti;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.Random;

public class numbertest extends AppCompatActivity {

    CountDownTimer countDownTimer;
    Group Giris;
    Group Oyun;
    Button StartNum;
    TextView SkorNum;
    TextView countDown;
    TextView rastgeleSayi;
    EditText numaraGiris;
    Button numaraButton;

    String String1;
    String String2;
    int skor = 0;
    int soruSayisi,level=1;
    String sonSayi;

    HafizaTestDbHelper dbHelperNum;
    Random rnd = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.numbertest);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelperNum = new HafizaTestDbHelper(this);

        Giris = findViewById(R.id.GameOver2);
        StartNum = findViewById(R.id.startNumber);
        SkorNum = findViewById(R.id.SkorNumara);
        countDown = findViewById(R.id.countDown);
        rastgeleSayi = findViewById(R.id.rastgeleNumara);
        numaraGiris = findViewById(R.id.numaraGiris);
        numaraButton = findViewById(R.id.numaraButton);
        Oyun = findViewById(R.id.numberGame);

        StartNum.setText("Oyuna Başla");
        SkorNum.setText("");

        StartNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Giris.setVisibility(View.INVISIBLE);
                soruUpgrade();
                startTimer();
                SkorNum.setText("Skor: " + skor);
            }
        });

        numaraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String1 = numaraGiris.getText().toString();
                String2 = rastgeleSayi.getText().toString();

                if (String1.equals(String2)) {
                    skor++;
                    level++;
                    SkorNum.setText("Skor: " + skor);
                    soruUpgrade();
                    startTimer();
                } else {

                    if(MainActivity.suankiKullanici>-1) {
                        dbHelperNum.numberPuanKaydet(MainActivity.suankiKullanici,skor);
                    }

                    level = 1;
                    SkorNum.setText("Skor: " + skor);
                    StartNum.setText("Yeni Oyuna Başla");

                    Giris.setVisibility(View.VISIBLE);
                    skor = 0;
                }
            }
        });
    }

    public void soruUpgrade() {

        sonSayi = "";

        for (int i = 1; i <= level ; i++) {
            sonSayi = sonSayi + String.valueOf(rnd.nextInt(10));
        }
        rastgeleSayi.setText(sonSayi);
    }
    private void startTimer() {

        countDown.setVisibility(View.VISIBLE);
        rastgeleSayi.setVisibility(View.VISIBLE);
        numaraGiris.setVisibility(View.INVISIBLE);
        numaraButton.setVisibility(View.INVISIBLE);



        countDownTimer = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                countDown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                countDown.setVisibility(View.INVISIBLE);
                rastgeleSayi.setVisibility(View.INVISIBLE);
                numaraGiris.setVisibility(View.VISIBLE);
                numaraButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }


}