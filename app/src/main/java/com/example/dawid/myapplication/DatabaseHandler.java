package com.example.dawid.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawid on 2015-05-25.
 */
public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contacts_db";

    private static final String
    TABLE_NAME = "contacts",
    KEY_ID = "id",
    KEY_NAME = "name",
    KEY_PHONE = "phone",
    KEY_EMAIL = "email";


    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " TEXT," +
                KEY_PHONE + " TEXT," +
                KEY_EMAIL + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void createContact(Contact c)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_ID, c.getId());
        cv.put(KEY_NAME, c.getName());
        cv.put(KEY_PHONE, c.getPhonenumber());
        cv.put(KEY_EMAIL, c.getEmail());

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public Contact getContact(int id)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_PHONE, KEY_EMAIL}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor!=null)
            cursor.moveToFirst();

        Contact c = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        db.close();
        cursor.close();

        return c;
    }

    public int getContactsCount()
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int count = cursor.getCount();

        cursor.close();

        return count;
    }

    public void updateContact(Contact c, int id)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, c.getName());
        cv.put(KEY_PHONE, c.getPhonenumber());
        cv.put(KEY_EMAIL, c.getEmail());

        db.update(TABLE_NAME, cv, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Contact> getAllContacts()
    {
        List<Contact> list = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst());
        {
            do
            {
                list.add(new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public void deleteContact(int id)
    {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteFirst()
    {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if(cursor.moveToFirst()) {
            long rowId = cursor.getLong(cursor.getColumnIndex(KEY_ID));

            db.delete(TABLE_NAME, KEY_ID +  "=" + rowId, null);
        }

        cursor.close();
        db.close();
    }



}
