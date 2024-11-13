package com.example.golfhandicapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperGolfers extends SQLiteOpenHelper {

    public static final String COLUMN_NAME = "GOLFER";
    public static final String GOLFER_TABLE_NAME = COLUMN_NAME + "S_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_HANDICAP = "HANDICAP";
    public static final String COLUMN_PLAYER = "PLAYER";

    private String dbName;

    public DataBaseHelperGolfers(@Nullable Context context, String dbName) {
        super(context, dbName + ".db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + GOLFER_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PLAYER + " TEXT, " + COLUMN_HANDICAP + " TEXT )";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addOne(Golfers golfers){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PLAYER, golfers.getName());
        cv.put(COLUMN_HANDICAP, golfers.getHandicap());


        long insert = db.insert(GOLFER_TABLE_NAME, null, cv);
        return insert != -1;
    }


    public List<Golfers> getAllGolfers(){
        List<Golfers> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + GOLFER_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int golferID = cursor.getInt(0);
                String name = cursor.getString(1);
                int handicap = cursor.getInt(2);

                Golfers newGolfers = new Golfers(golferID, name, handicap);
                returnList.add(newGolfers);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public void deleteOne(Golfers golfer){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + GOLFER_TABLE_NAME + " WHERE " + COLUMN_ID + " = " + golfer.getId();
        db.execSQL(query);

    }


}





