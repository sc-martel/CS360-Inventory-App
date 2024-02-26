package com.example.projecttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Scott Martel
 * Date: 02/19/2024
 * This class manages database operations for inventory items.
 */
public class InventoryManager {
    private final DatabaseHelper dbHelper;

    private Context context;

    /**
     * Constructor for InventoryManager.
     * @param context The context used to initialize the DatabaseHelper.
     */
    public InventoryManager(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Adds a new inventory item to the database.
     * @param itemName The name of the item.
     * @param quantity The quantity of the item.
     */
    public void addItem(String itemName, int quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("itemName", itemName);
        values.put("quantity", quantity);
        db.insert("inventory", null, values);
        db.close();
    }

    /**
     * Retrieves all inventory items from the database.
     * @return A list of Item objects representing all inventory items.
     */
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("inventory", new String[]{"id", "itemName", "quantity"}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    /**
     * Updates an existing inventory item in the database.
     * @param id The ID of the item to update.
     * @param itemName The new name for the item.
     * @param quantity The new quantity for the item.
     * @return The number of rows affected.
     */
    public int updateItem(int id, String itemName, int quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("itemName", itemName);
        values.put("quantity", quantity);
        int rowsAffected = db.update("inventory", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();

        if (quantity <= 0) {
            String message = itemName + " is now out of stock!";

            SmsUtility.sendSmsMessage(context, "(650) 555-1212", message);
        }

        return rowsAffected;
    }

    /**
     * Deletes an inventory item from the database.
     * @param id The ID of the item to delete.
     */
    public void deleteItem(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("inventory", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
