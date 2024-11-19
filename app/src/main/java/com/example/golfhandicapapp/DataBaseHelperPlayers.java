package com.example.golfhandicapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperPlayers extends SQLiteOpenHelper {

    public static final String COLUMN_HANDICAP = "HANDICAP";
    public static final String PLAYER_TABLE_NAME = COLUMN_HANDICAP + "S_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";


    public DataBaseHelperPlayers(@Nullable Context context) {
        super(context, "Players.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PLAYER_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_HANDICAP + " INTEGER)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Golfers golfers){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, golfers.getName());
        cv.put(COLUMN_HANDICAP, golfers.getHandicap());

        long insert = db.insert(PLAYER_TABLE_NAME, null, cv);
        return insert != -1;
    }

    public List<Golfers> getAllGolfers(){
        List<Golfers> returnList = new ArrayList<>();

        String query = " SELECT * FROM " + PLAYER_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int golferId = cursor.getInt(0);
                String golferName = cursor.getString(1);
                int golferHandicap = cursor.getInt(2);

                Golfers newGolfer = new Golfers(golferId, golferName, golferHandicap);
                returnList.add(newGolfer);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;

    }

    public void deleteOne(Golfers golfers) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + PLAYER_TABLE_NAME + " WHERE " + COLUMN_ID + " = " + golfers.getId();
        db.execSQL(query);
    }

}
