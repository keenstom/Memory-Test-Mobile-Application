package YusufSevilmis.MarmaraUni.hafizatesti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class HafizaTestDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hafiza_test.db";
    private static final int DATABASE_VERSION = 4;

    public HafizaTestDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createMaxScoreTableQuery = "CREATE TABLE MaxScoreTable (max_score INTEGER)";
        db.execSQL(createMaxScoreTableQuery);
        ContentValues values = new ContentValues();
        values.put("max_score", 0);
        db.insert("MaxScoreTable", null, values);

        String kullaniciTablosuOlusturma = "CREATE TABLE Kullanicilar (kullanici_id INTEGER PRIMARY KEY AUTOINCREMENT,kullanici_adi TEXT," +
                "sifre INTEGER ,chimp_score INTEGER, number_score INTEGER )";
        db.execSQL(kullaniciTablosuOlusturma);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("HafizaTestDbHelper", "onUpgrade called from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS MaxScoreTable");
        db.execSQL("DROP TABLE IF EXISTS Kullanicilar");
        onCreate(db);
    }

    public boolean kayitEtme(String isim , String sifre){

        if (kullaniciAdiMevcutMu(isim)) {
            return false;
        }

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put("kullanici_adi", isim);
        values.put("sifre", sifre);
        values.put("chimp_score", 0);
        values.put("number_score", 0);
        long result = db.insert("Kullanicilar", null, values);
        db.close();
        return result != -1;
    }

    public boolean kullaniciAdiMevcutMu(String isim) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT kullanici_id FROM Kullanicilar WHERE kullanici_adi = ?", new String[]{isim});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public int girisYap(String kullaniciAdi, String sifre) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"kullanici_id"};
        String selection = "kullanici_adi = ? AND sifre = ?";
        String[] selectionArgs = {kullaniciAdi, sifre};
        Cursor cursor = db.query("Kullanicilar", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int kullaniciId = cursor.getInt(cursor.getColumnIndex("kullanici_id"));
            cursor.close();
            return kullaniciId;
        }

        if (cursor != null) {
            cursor.close();
        }

        return -1;
    }

    public void chimpPuanKaydet(int kullaniciId, int chimpScore) {
        int currentScore = getChimpScore(kullaniciId);
        if (chimpScore > currentScore) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("chimp_score", chimpScore);
            db.update("Kullanicilar", values, "kullanici_id = ?", new String[]{String.valueOf(kullaniciId)});
            db.close();
        }
    }

    public int getChimpScore(int kullaniciId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT chimp_score FROM Kullanicilar WHERE kullanici_id = ?", new String[]{String.valueOf(kullaniciId)});
        int score = 0;
        if (cursor.moveToFirst()) {
            score = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return score;
    }

    public int getNumberScore(int kullaniciId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT number_score FROM Kullanicilar WHERE kullanici_id = ?", new String[]{String.valueOf(kullaniciId)});
        int score = 0;
        if (cursor.moveToFirst()) {
            score = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return score;
    }

    public void numberPuanKaydet(int kullaniciId,int numberScore) {
        int currentScore = getNumberScore(kullaniciId);
        if (numberScore > currentScore) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("number_score", numberScore);
            db.update("Kullanicilar", values, "kullanici_id = ?", new String[]{String.valueOf(kullaniciId)});
            db.close();
        }
    }
    public ArrayList<String> getKullaniciSkorlari() {
        ArrayList<String> skorListesi = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT kullanici_adi, chimp_score, number_score FROM Kullanicilar", null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String kullaniciAdi = cursor.getString(cursor.getColumnIndex("kullanici_adi"));
                    int chimpScore = cursor.getInt(cursor.getColumnIndex("chimp_score"));
                    int numberScore = cursor.getInt(cursor.getColumnIndex("number_score"));
                    skorListesi.add(kullaniciAdi + ", Şempanze Testi: " + chimpScore + ", Numara Testi: "+ numberScore);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Hata", "Veri alınamadı", e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        db.close();
        return skorListesi;
    }
    public int getMaxScore() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT max_score FROM MaxScoreTable", null);
        int maxScore = 0;
        if (cursor.moveToFirst()) {
            maxScore = cursor.getInt(0);
        }
        cursor.close();
        return maxScore;
    }
    public void updateScore(int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("max_score", score);
        db.update("MaxScoreTable", values, null, null);
        db.close();
    }
}
