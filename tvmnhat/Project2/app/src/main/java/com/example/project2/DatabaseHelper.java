package com.example.project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BookLibrary.db";
//    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "my_library";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "book_title";
    public static final String COLUMN_AUTHOR = "book_author";
    public static final String COLUMN_PAGES = "book_pages";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " + COLUMN_AUTHOR + " TEXT, " + COLUMN_PAGES + "INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String _id, String book_title, String book_author, String book_pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, _id);
        contentValues.put(COLUMN_TITLE, book_title);
        contentValues.put(COLUMN_AUTHOR, book_author);
        contentValues.put(COLUMN_PAGES, book_pages);

        long result =  db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
}
