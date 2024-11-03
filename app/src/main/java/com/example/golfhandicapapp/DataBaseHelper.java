package com.example.golfhandicapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String COLUMN_SCORE = "SCORE";
    public static final String SCORES_TABLE_NAME = COLUMN_SCORE + "S_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_COURSE = "COURSE";
    public static final String COLUMN_PLAYER = "PLAYER";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "scores.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + SCORES_TABLE_NAME + " (" + COLUMN_ID + " INT PRIMARY KEY AUTOINCREMENT, " + COLUMN_SCORE + " INT, " + COLUMN_COURSE + " TEXT, " + COLUMN_PLAYER + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addOne(Scores scores){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SCORE, scores.getScore());
        cv.put(COLUMN_COURSE, scores.getCourse());
        cv.put(COLUMN_PLAYER, scores.getPlayer());

        long insert = db.insert(SCORES_TABLE_NAME, null, cv);

        if (insert == -1){
            return false;
        }else{
            return true;
        }
    }


}
