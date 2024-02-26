package com.example.projecttwo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {
    private EditText itemNameEditText, itemCountEditText;
    private Button saveItemButton;
    private InventoryManager inventoryManager;

    // Add fields for item ID and a flag to check if we are editing
    private int itemId = -1; // Default to -1 to signify adding a new item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        inventoryManager = new InventoryManager(this);
        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemCountEditText = findViewById(R.id.itemCountEditText);
        saveItemButton = findViewById(R.id.saveItemButton);

        // Check if we're editing an item
        if (getIntent().hasExtra("itemId")) {
            itemId = getIntent().getIntExtra("itemId", -1);
            String itemName = getIntent().getStringExtra("itemName");
            int itemQuantity = getIntent().getIntExtra("itemQuantity", 0);

            itemNameEditText.setText(itemName);
            itemCountEditText.setText(String.valueOf(itemQuantity));
        }

        saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    private void saveItem() {
        String itemName = itemNameEditText.getText().toString().trim();
        int itemCount = Integer.parseInt(itemCountEditText.getText().toString().trim());

        if (itemId == -1) {
            // Add new item
            inventoryManager.addItem(itemName, itemCount);
        } else {
            // Update existing item
            inventoryManager.updateItem(itemId, itemName, itemCount);
        }

        // Close this activity and go back
        finish();
    }
}