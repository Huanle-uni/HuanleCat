package com.unsw.valerie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CollHelper extends SQLiteOpenHelper {

    public CollHelper(Context context) {
        super(context, "cat.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table cat ("
                + "id VARCHAR primary key , "
                + "name VARCHAR, "
                + "country_code VARCHAR, "
                + "description VARCHAR, "
                + "life_span VARCHAR, "
                + "wikipedia_url VARCHAR, "
                + "shedding_level VARCHAR, "
                + "temperament VARCHAR, "
                + "dog_friendly VARCHAR, "
                + "origin VARCHAR, "
                + "imperial VARCHAR, "
                + "metric VARCHAR)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
