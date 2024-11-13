package com.example.golfhandicapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperCourses extends SQLiteOpenHelper {


    public static final String COLUMN_HOLE = "HOLE";
    public static final String COURSE_TABLE_NAME = COLUMN_HOLE + "S_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PAR = "PAR";
    public static final String COLUMN_SLOPE = "SLOPE";
    public static final String COLUMN_HANDICAP = "HANDICAP";

    private String dbName;

    public DataBaseHelperCourses(@Nullable Context context, String dbName) {
        super(context, dbName +".db", null, 1);
        this.dbName = dbName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + COURSE_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_HOLE + " INTEGER, " + COLUMN_PAR + " INTEGER, " + COLUMN_HANDICAP + " INTEGER)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public boolean addOne(Courses courses){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_HOLE, courses.getHoleNumber());
        cv.put(COLUMN_PAR, courses.getPar());
        cv.put(COLUMN_HANDICAP, courses.getHoleHandicap());

        long insert = db.insert(COURSE_TABLE_NAME, null, cv);
        return insert != -1;
    }


    public List<Courses> getAllHoles(){
        List<Courses> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + COURSE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int holeId = cursor.getInt(0);
                int holeNumber = cursor.getInt(1);
                int par = cursor.getInt(2);
                int handicap = cursor.getInt(3);

                Courses newCourse = new Courses(holeId, holeNumber, par, handicap);
                returnList.add(newCourse);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public void deleteOne(Courses courses){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + COURSE_TABLE_NAME + " WHERE " + COLUMN_ID + " = " + courses.getId();
        db.execSQL(query);

    }

}
