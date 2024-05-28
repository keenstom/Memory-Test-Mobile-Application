package YusufSevilmis.MarmaraUni.hafizatesti;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class giris extends AppCompatActivity {

    EditText giris,sifre;
    Button girisButton,kayitButton;
    HafizaTestDbHelper database;

    String ad,kSifre;

    boolean kBasari;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.giris);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toast.makeText(giris.this, String.valueOf(MainActivity.suankiKullanici), Toast.LENGTH_SHORT).show();

        database = new HafizaTestDbHelper(this);

        SQLiteDatabase db = database.getWritableDatabase();

        giris = findViewById(R.id.girisInput);
        sifre = findViewById(R.id.sifreInput);
        girisButton = findViewById(R.id.girisButton);
        kayitButton = findViewById(R.id.kayitButton);

        kayitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad = giris.getText().toString();
                kSifre = sifre.getText().toString();
                kBasari = database.kayitEtme(ad,kSifre);
                if (kBasari) {
                    Toast.makeText(giris.this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(giris.this, "Kayıt başarısız! Lütfen tekrar deneyin.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        girisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kullaniciAdi = giris.getText().toString();
                String sifre1 = sifre.getText().toString();
                int kullaniciId = database.girisYap(kullaniciAdi, sifre1);
                if (kullaniciId != -1) {
                    Toast.makeText(giris.this, "Giriş başarılı!", Toast.LENGTH_SHORT).show();
                    MainActivity.setSuankiKullanici(kullaniciId);
                    MainActivity.setGirisButton();
                } else {
                    Toast.makeText(giris.this, "Geçersiz kullanıcı adı veya şifre", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}