package com.example.projecttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Author: Scott Martel
 * Date: 02/19/2024
 * UserManager class to handle user registration and login using SQLite database.
 */
public class UserManager {
    // Database helper object to manage database creation and version management.
    private final DatabaseHelper dbHelper;

    /**
     * Constructor for UserManager.
     * @param context The context in which the manager is operating. This is used to initialize the DatabaseHelper.
     */
    public UserManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Registers a new user with a username and password.
     *
     * @param username The username of the new user.
     * @param password The password for the new user.
     * @return boolean Returns true if the user was successfully registered, false otherwise.
     */
    public boolean registerUser(String username, String password) {
        // Gets the database in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Creates a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("username", username); // Username value
        values.put("password", password); // Password value

        // Inserts the new row, returning the primary key value of the new row
        long result = db.insert("users", null, values);

        // Returns true if registration is successful (result is not -1)
        return result != -1;
    }

    /**
     * Attempts to log in a user with the provided username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return boolean Returns true if the credentials are correct and login is successful, false otherwise.
     */
    public boolean loginUser(String username, String password) {
        // Gets the database in read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Queries the users table to find a match for the provided username and password
        Cursor cursor = db.query("users", new String[] { "id" },
                "username=? AND password=?", new String[] { username, password },
                null, null, null);

        // Checks if the cursor found a record matching the credentials
        boolean isLoggedIn = cursor.getCount() > 0;
        cursor.close(); // Closes the cursor to release resources

        // Returns true if credentials are correct, a record was found
        return isLoggedIn;
    }
}
