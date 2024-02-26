package com.example.projecttwo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Author: Scott Martel
 * Date: 02-20-2024
 * Manages the display and interaction of inventory items within a RecyclerView.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> items;
    private final InventoryManager inventoryManager;

    public ItemAdapter(List<Item> items, InventoryManager inventoryManager) {
        this.items = items;
        this.inventoryManager = inventoryManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemLabel.setText(item.getName());
        holder.itemCount.setText(String.valueOf(item.getCount()));

        holder.editButton.setOnClickListener(v -> {
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(v.getContext(), AddItemActivity.class);
                intent.putExtra("itemId", item.getId());
                intent.putExtra("itemName", item.getName());
                intent.putExtra("itemQuantity", item.getCount());
                v.getContext().startActivity(intent);
            }
        });

        // Updated delete logic
        holder.deleteButton.setOnClickListener(v -> {
            // Ensure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                // Delete the item from the database
                inventoryManager.deleteItem(items.get(position).getId());
                // Remove the item from the list
                items.remove(position);
                // Notify the adapter about the item being removed
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemLabel, itemCount;
        Button editButton, deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            itemLabel = itemView.findViewById(R.id.itemLabel);
            itemCount = itemView.findViewById(R.id.itemCount);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
