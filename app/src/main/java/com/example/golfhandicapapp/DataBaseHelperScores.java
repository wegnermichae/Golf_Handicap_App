package com.example.golfhandicapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperScores extends SQLiteOpenHelper {

    public static final String COLUMN_SCORE = "SCORE";
    public static final String SCORES_TABLE_NAME = COLUMN_SCORE + "S_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_COURSE = "COURSE";
    public static final String COLUMN_PLAYER = "PLAYER";


    public DataBaseHelperScores(@Nullable Context context) {
        super(context, "score.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + SCORES_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SCORE + " INTEGER, " + COLUMN_COURSE + " TEXT, " + COLUMN_PLAYER + " TEXT)";
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
        return insert != -1;
    }


    public List<Scores> getAllScores(){
        List<Scores> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + SCORES_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int scorerID = cursor.getInt(0);
                int score = cursor.getInt(1);
                String course = cursor.getString(2);
                String player = cursor.getString(3);

                Scores newScores = new Scores(scorerID, score, course, player);
                returnList.add(newScores);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<Scores> getScoresByPlayer(String player){
        List<Scores> returnList = new ArrayList<>();
        String query = "SELECT * FROM " + SCORES_TABLE_NAME + " WHERE " + COLUMN_PLAYER + " = '" + player + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int scorerID = cursor.getInt(0);
                int score = cursor.getInt(1);
                String course = cursor.getString(2);
                String playerName = cursor.getString(3);
                Scores newScores = new Scores(scorerID, score, course, playerName);
                returnList.add(newScores);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }


    public void deleteOne(Scores score){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + SCORES_TABLE_NAME + " WHERE " + COLUMN_ID + " = " + score.getId();
        db.execSQL(query);

    }


}
