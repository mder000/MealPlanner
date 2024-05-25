package com.example.mealplanner.backend;
import android.provider.BaseColumns;

public final class GroceryListContract {
    private GroceryListContract() {}

    public static class GroceryListEntry implements BaseColumns {
        public static final String TABLE_NAME = "grocery_list";
        public static final String COLUMN_NAME_INGREDIENT = "ingredient";
    }
}
