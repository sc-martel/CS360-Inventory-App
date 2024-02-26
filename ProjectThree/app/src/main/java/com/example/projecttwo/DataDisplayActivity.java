package com.example.projecttwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
* Author: Scott Martel
* Date: 02-19-2024
* This class displays a list of inventory items with counts and provides
* options to edit or delete each entry.
*/
public class DataDisplayActivity extends AppCompatActivity {
    private RecyclerView dataRecyclerView;
    private InventoryManager inventoryManager; // Ensure this is declared
    private ItemAdapter adapter;
    private Button addDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        // Initialize InventoryManager
        inventoryManager = new InventoryManager(this);

        dataRecyclerView = findViewById(R.id.dataRecyclerView);
        dataRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // Initialize your RecyclerView with items from the database
        loadItems();

        addDataButton = findViewById(R.id.addDataButton);
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddItemActivity to add a new item
                Intent intent = new Intent(DataDisplayActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems(); // Reload items to reflect any changes
    }

    private void loadItems() {
        // Fetch items from the database
        List<Item> items = inventoryManager.getAllItems();
        // Pass both items and inventoryManager to the adapter
        adapter = new ItemAdapter(items, inventoryManager);
        dataRecyclerView.setAdapter(adapter);
    }
}