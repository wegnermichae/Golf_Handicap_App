package com.example.golfhandicapapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    public boolean courseExists(String courseName) {
        SQLiteDatabase db = this.getReadableDatabase(); // Get a readable database instance
        Cursor cursor = null;
        try {
            // Query the database to check if the course exists
            cursor = db.query(
                    COURSE_TABLE_NAME,              // Table name
                    new String[]{COLUMN_NAME},      // Column to retrieve
                    COLUMN_NAME + " = ?",           // WHERE clause
                    new String[]{courseName},       // Arguments for WHERE clause
                    null, null, null                // Group By, Having, Order By
            );

            // Check if the cursor contains any results
            return cursor.moveToFirst();       // Returns true if a matching record exists
        } finally {
            if (cursor != null) {
                cursor.close();                // Ensure the cursor is closed to avoid memory leaks
            }
        }
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

    @SuppressLint("Range")
    public List<String> getAllCourseNames() {
        List<String> courseNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query to get all course names from the table
            cursor = db.query(
                    COURSE_TABLE_NAME,    // Table name
                    new String[]{COLUMN_NAME},   // Column to retrieve
                    null,                // No WHERE clause
                    null,                // No selection args
                    null,                // No GROUP BY
                    null,                // No HAVING
                    null                 // No ORDER BY
            );

            // Loop through the cursor to add course names to the list
            while (cursor.moveToNext()) {
                courseNames.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            }
        } finally {
            if (cursor != null) {
                cursor.close(); // Close the cursor to avoid memory leaks
            }
            db.close(); // Close the database connection
        }

        return courseNames; // Return the list of course names
    }

    public void deleteOne(Courses courses){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + COURSE_TABLE_NAME + " WHERE " + COLUMN_ID + " = " + courses.getId();
        db.execSQL(query);
    }


}
