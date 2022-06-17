package com.example.uniritter_disp_mobile.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "BancoDeDados";
    private static final Integer DB_VERSION = 2;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String stm = "create table alunos (matricula integer primary key, nome text, email text);";
        sqLiteDatabase.execSQL(stm);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int currentVersion) {
        String stm = "create table localizacoes (id integer primary key, lat real, lng real, time integer, enviado integer);";
        if (oldVersion == 0) {
            //sqLiteDatabase.execSQL(stm);
        }
        if (oldVersion == 1) {
            sqLiteDatabase.execSQL(stm);
        }
    }
}
