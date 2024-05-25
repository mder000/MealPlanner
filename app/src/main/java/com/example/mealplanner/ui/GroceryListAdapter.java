package com.example.mealplanner.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.GroceryList;
import com.example.mealplanner.R;

import java.util.ArrayList;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {
    private ArrayList<GroceryList> groceryLists;

    public GroceryListAdapter(ArrayList<GroceryList> groceryLists) {
        this.groceryLists = groceryLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroceryList groceryList = groceryLists.get(position);
        String text = "Grocery List #" + groceryList.getId() + "\n" + groceryList.getIngredient();
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return groceryLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_grocery_list);
        }
    }
}