package com.example.golfhandicapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperClubs extends SQLiteOpenHelper {

    public static final String COLUMN_DISTANCE = "DISTANCE";
    public static final String CLUB_TABLE_NAME = COLUMN_DISTANCE + "S_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CLUB = "CLUB";

    public DataBaseHelperClubs(@Nullable Context context) {
        super(context, "clubs.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CLUB_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CLUB + " TEXT, " + COLUMN_DISTANCE + " INTEGER)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Clubs clubs){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CLUB, clubs.getName());
        cv.put(COLUMN_DISTANCE, clubs.getDistance());

        long insert = db.insert(CLUB_TABLE_NAME, null, cv);
        return insert != -1;
    }

    public List<Clubs> getAllClubs(){
        List<Clubs> returnList = new ArrayList<>();

        String query = " SELECT * FROM " + CLUB_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int clubId = cursor.getInt(0);
                String clubName = cursor.getString(1);
                int clubDistance = cursor.getInt(2);

                Clubs newClub = new Clubs(clubId, clubName, clubDistance);
                returnList.add(newClub);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;

    }

    public void deleteOne(Clubs clubs) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + CLUB_TABLE_NAME + " WHERE " + COLUMN_ID + " = " + clubs.getId();
        db.execSQL(query);
    }




}
