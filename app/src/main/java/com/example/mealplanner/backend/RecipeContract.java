package com.example.mealplanner.backend;
import android.provider.BaseColumns;
public final class RecipeContract {
    private RecipeContract() {}

    public static class RecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_INGREDIENTS = "ingredients";
        public static final String COLUMN_NAME_INSTRUCTIONS = "instructions";
    }
}
