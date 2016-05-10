package uk.ac.napier.passitonapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

/** DBHelper for the SQLite database for the Items activity. **/
public class DBItemHelper extends SQLiteOpenHelper {

    /** Database variables. **/
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "items.db";
    private static final String TAG = "DBItemHelper";

    /** The database and version being used. **/
    DBItemHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** Create the database table. **/
    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL create table
        String CREATE_ITEM_TABLE = "create table " + Item.TABLE_NAME + " ( " +
                Item.KEY_ID + " integer primary key autoincrement, " +
                Item.KEY_NAME + " text, " +
                Item.KEY_DESC + " text, " +
                Item.KEY_LOCAL + " text, " +
                Item.KEY_PICKUP + " text, " +
                Item.KEY_IMAGE + " blob )";

        //create table
        db.execSQL(CREATE_ITEM_TABLE);
    }

    /** Add a new item record to the database. **/
    public void addItem(Item item) {
        // Get the writable database
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            //create ContentValues to add
            ContentValues values = new ContentValues();
            values.put(Item.KEY_NAME, item.getName());
            values.put(Item.KEY_DESC, item.getDesc());
            values.put(Item.KEY_LOCAL, item.getLocal());
            values.put(Item.KEY_PICKUP, item.getPickup());
            values.put(Item.KEY_IMAGE, Util.bitmapToByteArray(item.getImage()));

            //Insert the item into the database
            db.insert(Item.TABLE_NAME, null, values);
        }
        catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
        finally {
            //close the database
            if(null != db){ db.close(); }
        }
    }

    /** Return all the items stored in the database. **/
    public ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();
        String query = "SELECT * FROM " + Item.TABLE_NAME;
        SQLiteDatabase db = null;
        try {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Item item;
        if (cursor.moveToFirst()) {
            do {
                item = new Item();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setDesc(cursor.getString(2));
                item.setLocal(cursor.getString(3));
                item.setPickup(cursor.getString(4));
                item.setImage(Util.byteArrayToBitmap(cursor.getBlob(5)));
                items.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
        catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
        finally {
            if(null != db){ db.close();
            }
        }
        return items;
    }

    /** Delete an item record from the dtatabase. **/
    public void deleteItem(Item item) {
        // Get the writable database
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
        // Delete the item
        db.delete(Item.TABLE_NAME, Item.KEY_ID + " = ?", new String[]{String.valueOf(item.getId())});
        }
        catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
        finally {
            // Close the database
            if(null != db){ db.close(); }
        }
        // Log deletion
        Log.d("deleteItem(" + item.getId() + ")", item.toString());
    }

    /** Check if there's an old version of the database already. **/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS items");
        // create fresh table
        this.onCreate(db);
    }

    /** Read and return the item data from the database. **/
    public Item getItemByDesc(String desc) {
        // Get the writable database
        SQLiteDatabase db = null;
        Item item = null;
        try {
            db = this.getReadableDatabase();
        // Build the item query
        Cursor cursor = db.query(Item.TABLE_NAME,
                Item.COLUMNS, // Column names
                " desc = ?", // Selections
                new String[]{desc}, // Selections args
                null, // Group by rows
                null, // Which rows to include
                null, // Order by
                null); // Limit

        // If results go to the first one
        if (cursor != null)
            cursor.moveToFirst();

        // Build the item
        item = new Item();
            assert cursor != null;
            item.setId(Integer.parseInt(cursor.getString(0)));
            item.setName(cursor.getString(1));
            item.setDesc(cursor.getString(2));
            item.setLocal(cursor.getString(3));
            item.setPickup(cursor.getString(4));
            item.setImage(Util.byteArrayToBitmap(cursor.getBlob(5)));

        // Get the item
        Log.d("getItem(" + desc + ")", item.toString());
            cursor.close();
        }
        catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
        finally {
            if(null != db){ db.close(); }
        }
        // Return the item
        return item;

    }

}
