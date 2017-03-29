package com.example.rafal.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rafal on 28.03.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "ShoppingList";
    private static final String TABLE_NAME = "ShoppingList";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE ShoppingList ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT )";

        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ShoppingList");
        this.onCreate(db);
    }

    public void addItem(Item item){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", item.getName());

        db.insert(TABLE_NAME,
                null,
                values);

        db.close();
    }
    public Item getItem(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_NAME,
                        new String[] {"name"},
                        "id = ?",
                        new String[] { String.valueOf(id) },
                        null,
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        item.setName(cursor.getString(0));

        return item;
    }

    public List<Item> getAllItems() {
        List<Item> items = new LinkedList<Item>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT  * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        Item item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new Item();
                item.setName(cursor.getString(1));
                item.setId(cursor.getInt(0));

                items.add(item);
            } while (cursor.moveToNext());
        }

        return items;
    }

    public int updateItem(Item item) {

        Log.d("UPDATE", item.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", item.getName());

        int i = db.update(TABLE_NAME,
                values,
                "id"+" = ?",
                new String[] { String.valueOf(item.getId()) });

        db.close();
        return i;
    }

    public void deleteItem(Item item) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,
                "id = ?",
                new String[] { String.valueOf(item.getId()) });
        db.close();


    }

}
