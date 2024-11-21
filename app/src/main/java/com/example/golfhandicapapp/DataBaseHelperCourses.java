package com.example.golfhandicapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelperCourses extends SQLiteOpenHelper {

    public static final String COLUMN_NAME = "NAME";
    public static final String COURSE_TABLE_NAME = COLUMN_NAME + "S_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_RATING = "RATING";
    public static final String COLUMN_SLOPE = "SLOPE";

    public DataBaseHelperCourses(@Nullable Context context) {
        super(context, "golf_courses.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + COURSE_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_RATING + " DOUBLE, " + COLUMN_SLOPE + " INTEGER)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Courses courses){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, courses.getName());
        cv.put(COLUMN_RATING, courses.getCourseRating());
        cv.put(COLUMN_SLOPE, courses.getSlopeRating());

        long insert = db.insert(COURSE_TABLE_NAME, null, cv);
        return insert != -1;
    }

    public int getCourseSlope(String name){
        int returnSlope = -1;
        String query = "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] {name});
        if(cursor.moveToFirst()){
            returnSlope = cursor.getInt(3);
        }
        cursor.close();
        db.close();
        return returnSlope;
    }

    public double getCourseRating(String name){
        double returnRating = -1;
        String query = "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + COLUMN_NAME + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] {name});
        if(cursor.moveToFirst()){
            returnRating = cursor.getDouble(2);
        }
        cursor.close();
        db.close();
        return returnRating;
    }
}
