package com.example.mealplanner.backend;
import android.provider.BaseColumns;

public final class MealContract {
    private MealContract() {}

    public static class MealEntry implements BaseColumns {
        public static final String TABLE_NAME = "meals";
        public static final String COLUMN_NAME_BREAKFAST_ID = "breakfast_id";
        public static final String COLUMN_NAME_LUNCH_ID = "lunch_id";
        public static final String COLUMN_NAME_DINNER_ID = "dinner_id";
    }
}