package com.example.projecttwo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Author: Scott Martel
 * Date: 02/19/2024
 * DatabaseHelper class extends SQLiteOpenHelper for handling database creation and version management.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Name of the database file
    private static final String DATABASE_NAME = "inventory_app_data.db";

    // Database version
    private static final int DATABASE_VERSION = 2;

    // SQL statement to create a new table for users.
    // This table will include columns for ID, username, and password.
    private static final String TABLE_USERS_CREATE =
            "CREATE TABLE users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT NOT NULL, " +
                    "password TEXT NOT NULL);";

    // SQL statement to create table for inventory.
    // This table will include columns for id, item name and the quantity.
    private static final String TABLE_INVENTORY_CREATE =
            "CREATE TABLE inventory (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "itemName TEXT NOT NULL, " +
                    "quantity INTEGER NOT NULL);";

    /**
     * Constructor for DatabaseHelper.
     *
     * @param context the context in which the helper is operating.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Executes the SQL statement to create the users table.
        db.execSQL(TABLE_USERS_CREATE);
        db.execSQL(TABLE_INVENTORY_CREATE);
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing tables
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS inventory");
        // Create tables again
        onCreate(db);
    }
}
