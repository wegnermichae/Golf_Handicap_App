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


    public static final String COLUMN_NAME = "COURSE";
    public static final String COURSE_TABLE_NAME = COLUMN_NAME + "S_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PAR = "PAR";
    public static final String COLUMN_SLOPE = "SLOPE";
    public static final String COLUMN_HANDICAP = "HANDICAP";


    public DataBaseHelperCourses(@Nullable Context context) {
        super(context, "courses.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + COURSE_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_PAR + " INTEGER, " + COLUMN_SLOPE + " INTEGER, " + COLUMN_HANDICAP + " FLOAT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addOne(Courses courses){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, courses.getName());
        cv.put(COLUMN_PAR, courses.getPar());
        cv.put(COLUMN_SLOPE, courses.getSlopeRating());
        cv.put(COLUMN_HANDICAP, courses.getHandicap());

        long insert = db.insert(COURSE_TABLE_NAME, null, cv);
        return insert != -1;
    }


    public List<Courses> getAllCourses(){
        List<Courses> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + COURSE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int customerID = cursor.getInt(0);
                String name = cursor.getString(1);
                int par = cursor.getInt(2);
                int slope = cursor.getInt(3);
                double handicap = cursor.getDouble(4);

                Courses newCourse = new Courses(name, par, slope, handicap);
                returnList.add(newCourse);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

}
