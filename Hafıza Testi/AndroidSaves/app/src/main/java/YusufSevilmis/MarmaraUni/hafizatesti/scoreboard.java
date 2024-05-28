package YusufSevilmis.MarmaraUni.hafizatesti;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class scoreboard extends AppCompatActivity {

    HafizaTestDbHelper skorCek;
    ListView liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);

        liste = findViewById(R.id.ScoreBoard);
        skorCek = new HafizaTestDbHelper(this);

        ArrayList<String> skorListesi = skorCek.getKullaniciSkorlari();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, skorListesi);
        liste.setAdapter(adapter);
    }
}
