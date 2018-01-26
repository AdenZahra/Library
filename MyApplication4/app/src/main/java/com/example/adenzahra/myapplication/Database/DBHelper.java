package com.example.adenzahra.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;

import java.util.jar.Attributes;
import com.example.adenzahra.myapplication.Globals;
import com.example.adenzahra.myapplication.Models.Library;

/**
 * Created by Aden Zahra on 1/25/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    Context ctx;
    public static final String TABLE_NAME = "Library_List";

    //COLUMN NAMES
    public static final String ID = "ID";
    public static final String NAME = "name";
    public static final String ISBN = "isbn";
    public static final String AUTHOR = "author";
    public static final String COST = "cost";

    //COLUMN TYPES
    private static final int DATABASE_VERSION = 1;
    public static final String TYPE_TEXT = " TEXT ";
    public static final String TYPE_INT = " INT ";
    public static final String SEPERATOR = ", ";
    private static final String DATABASE_NAME = "SQLiteDatabase.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query = "Create table " + TABLE_NAME + " ( "
                + ID + TYPE_INT + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SEPERATOR
                + NAME + TYPE_TEXT + SEPERATOR
                + ISBN + TYPE_TEXT + SEPERATOR
                + AUTHOR + TYPE_TEXT + SEPERATOR
                + COST + TYPE_TEXT + ");";
        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < newVersion) {
            db.execSQL("Drop Table If Exists " + DATABASE_NAME);
            onCreate(db);
        }
    }

    public long insert(String name, String isbn, String author,String cost) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(ISBN, isbn);
        cv.put(AUTHOR, author);
        cv.put(COST, cost);
        long i = db.insert(TABLE_NAME, null, cv);
        Log.d("Database_helper", String.valueOf(i));
        Toast.makeText(ctx, "Data Added", Toast.LENGTH_LONG).show();
        //be sure to close database after work is done
        db.close();
        return i;
    }


    public Cursor read(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{ID, NAME}, null, null, null, null, null);//"SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + id);
        return c;
    }

    public boolean delete_row(String email) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=" + email, null);
        db.close();
        return false;
    }


    public ArrayList getAll() {
        ArrayList<Library> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Log.d(Globals.LOG_TAG, cursor.getColumnNames()[0] + ":" + cursor.getString(cursor.getColumnIndex(ID)));
            Log.d(Globals.LOG_TAG, cursor.getColumnNames()[1] + ":" + cursor.getString(cursor.getColumnIndex(NAME)));
            Log.d(Globals.LOG_TAG, cursor.getColumnNames()[2] + ":" + cursor.getString(cursor.getColumnIndex(ISBN)));
            Log.d(Globals.LOG_TAG, cursor.getColumnNames()[3] + ":" + cursor.getString(cursor.getColumnIndex(AUTHOR)));
            Log.d(Globals.LOG_TAG, cursor.getColumnNames()[4] + ":" + cursor.getString(cursor.getColumnIndex(COST)));

            list.add(new Library(cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(NAME)),
                    cursor.getString(cursor.getColumnIndex(ISBN)),
                    cursor.getString(cursor.getColumnIndex(AUTHOR)),
                    cursor.getString(cursor.getColumnIndex(COST))));
        }
        return list;
    }


}
