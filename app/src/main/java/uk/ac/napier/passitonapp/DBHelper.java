package uk.ac.napier.passitonapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/** DBHelper for the SQLite database for the Settings activity. **/
public class DBHelper extends SQLiteOpenHelper {

    /** Set the details for the database. **/
    public static final String DATABASE_NAME = "setting.db";
    public static final String SETTINGS_COLUMN_NAME = "name";
    public static final String SETTINGS_COLUMN_SURNAME = "last";
    public static final String SETTINGS_COLUMN_EMAIL = "email";
    public static final String SETTINGS_COLUMN_POSTCODE = "post";
    public static final String SETTINGS_COLUMN_PHONE = "phone";
    public static final String SETTINGS_COLUMN_PASSWORD = "password";

    /** The database and version being used. **/
    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    /** Create a database table called 'settings' including these columns and settings. **/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table settings" + "(id integer primary key, name text, last text, email text, post text, phone text, password text)");
    }

    /** Check if there's an old version of the database already. **/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS settings");
        onCreate(db);
    }

    /** Set the content values for the column data. **/
    public boolean insertSetting  (String name, String last, String email, String post, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("last", last);
        contentValues.put("email", email);
        contentValues.put("post", post);
        contentValues.put("phone", phone);
        contentValues.put("password", password);

        db.insert("settings", null, contentValues);
        return true;
    }

    /** Read and return the data from the database. **/
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from settings where id="+id+"", null );
    }

    /** Update the data to the database. **/
    public boolean updateSetting (Integer id, String name, String last, String email, String post, String phone, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("last", last);
        contentValues.put("email", email);
        contentValues.put("post", post);
        contentValues.put("phone", phone);
        contentValues.put("password", password);

        /** Give the record a new ID. **/
        db.update("settings", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    /** Delete the record by ID from the database. **/
    public Integer deleteSetting (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("settings",
                "id = ?",
                new String[] { Integer.toString(id) });
    }
}
