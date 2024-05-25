package com.example.mealplanner.backend;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


import com.example.mealplanner.GroceryList;
import com.example.mealplanner.Meal;
import com.example.mealplanner.Recipe;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mealplanner.db";

    private static final String SQL_CREATE_ENTRIES_RECIPE =
            "CREATE TABLE " + RecipeContract.RecipeEntry.TABLE_NAME + " (" +
                    RecipeContract.RecipeEntry._ID + " INTEGER PRIMARY KEY," +
                    RecipeContract.RecipeEntry.COLUMN_NAME_NAME + " TEXT," +
                    RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTS + " TEXT," +
                    RecipeContract.RecipeEntry.COLUMN_NAME_INSTRUCTIONS + " TEXT)";

    private static final String SQL_CREATE_ENTRIES_MEAL =
            "CREATE TABLE " + MealContract.MealEntry.TABLE_NAME + " (" +
                    MealContract.MealEntry._ID + " INTEGER PRIMARY KEY," +
                    MealContract.MealEntry.COLUMN_NAME_BREAKFAST_ID + " INTEGER," +
                    MealContract.MealEntry.COLUMN_NAME_LUNCH_ID + " INTEGER," +
                    MealContract.MealEntry.COLUMN_NAME_DINNER_ID + " INTEGER)";

    private static final String SQL_CREATE_ENTRIES_GROCERY_LIST =
            "CREATE TABLE " + GroceryListContract.GroceryListEntry.TABLE_NAME + " (" +
                    GroceryListContract.GroceryListEntry._ID + " INTEGER PRIMARY KEY," +
                    GroceryListContract.GroceryListEntry.COLUMN_NAME_INGREDIENT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_RECIPE =
            "DROP TABLE IF EXISTS " + RecipeContract.RecipeEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_MEAL =
            "DROP TABLE IF EXISTS " + MealContract.MealEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_GROCERY_LIST =
            "DROP TABLE IF EXISTS " + GroceryListContract.GroceryListEntry.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_RECIPE);
        db.execSQL(SQL_CREATE_ENTRIES_MEAL);
        db.execSQL(SQL_CREATE_ENTRIES_GROCERY_LIST);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_RECIPE);
        db.execSQL(SQL_DELETE_ENTRIES_MEAL);
        db.execSQL(SQL_DELETE_ENTRIES_GROCERY_LIST);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private String createTables() {
        return "CREATE TABLE " + RecipeContract.RecipeEntry.TABLE_NAME + " (" +
                RecipeContract.RecipeEntry._ID + " INTEGER PRIMARY KEY, " +
                RecipeContract.RecipeEntry.COLUMN_NAME_NAME + " TEXT, " +
                RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTS + " TEXT, " +
                RecipeContract.RecipeEntry.COLUMN_NAME_INSTRUCTIONS + " TEXT); " +
                "CREATE TABLE " + MealContract.MealEntry.TABLE_NAME + " (" +
                MealContract.MealEntry._ID + " INTEGER PRIMARY KEY, " +
                MealContract.MealEntry.COLUMN_NAME_BREAKFAST_ID + " INTEGER, " +
                MealContract.MealEntry.COLUMN_NAME_LUNCH_ID + " INTEGER, " +
                MealContract.MealEntry.COLUMN_NAME_DINNER_ID + " INTEGER); " +
                "CREATE TABLE " + GroceryListContract.GroceryListEntry.TABLE_NAME + " (" +
                GroceryListContract.GroceryListEntry._ID + " INTEGER PRIMARY KEY, " +
                GroceryListContract.GroceryListEntry.COLUMN_NAME_INGREDIENT + " TEXT)";
    }

    private String dropTables() {
        return "DROP TABLE IF EXISTS " + RecipeContract.RecipeEntry.TABLE_NAME + "; " +
                "DROP TABLE IF EXISTS " + MealContract.MealEntry.TABLE_NAME + "; " +
                "DROP TABLE IF EXISTS " + GroceryListContract.GroceryListEntry.TABLE_NAME;
    }

    public long insertRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_NAME, recipe.getName());
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTS, recipe.getIngredients());
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_INSTRUCTIONS, recipe.getInstructions());
        long id = db.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, values);
        return id;
    }

    public ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                RecipeContract.RecipeEntry.COLUMN_NAME_NAME,
                RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTS,
                RecipeContract.RecipeEntry.COLUMN_NAME_INSTRUCTIONS
        };
        String sortOrder = RecipeContract.RecipeEntry.COLUMN_NAME_NAME + " ASC";
        Cursor cursor = db.query(
                RecipeContract.RecipeEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_NAME));
            String ingredients = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTS));
            String instructions = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_INSTRUCTIONS));
            Recipe recipe = new Recipe(id, name, ingredients, instructions);
            recipes.add(recipe);
        }
        cursor.close();
        return recipes;
    }

    public Recipe getRecipeById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                RecipeContract.RecipeEntry.COLUMN_NAME_NAME,
                RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTS,
                RecipeContract.RecipeEntry.COLUMN_NAME_INSTRUCTIONS
        };

        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = db.query(
                RecipeContract.RecipeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Recipe recipe = null;
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_NAME));
            String ingredients = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTS));
            String instructions = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_INSTRUCTIONS));
            recipe = new Recipe(id, name, ingredients, instructions);
        }
        cursor.close();

        return recipe;
    }

    public Recipe getRandomRecipe() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + RecipeContract.RecipeEntry.TABLE_NAME + " ORDER BY RANDOM() LIMIT 1", null);
        Recipe recipe = null;
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_NAME));
            String ingredients = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_INGREDIENTS));
            String instructions = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_INSTRUCTIONS));
            recipe = new Recipe(id, name, ingredients, instructions);
        }
        cursor.close();
        return recipe;
    }


    public long insertMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MealContract.MealEntry.COLUMN_NAME_BREAKFAST_ID, meal.getBreakfastId());
        values.put(MealContract.MealEntry.COLUMN_NAME_LUNCH_ID, meal.getLunchId());
        values.put(MealContract.MealEntry.COLUMN_NAME_DINNER_ID, meal.getDinnerId());
        long id = db.insert(MealContract.MealEntry.TABLE_NAME, null, values);
        return id;
    }

    public ArrayList<Meal> getMeals() {
        ArrayList<Meal> meals = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                MealContract.MealEntry.COLUMN_NAME_BREAKFAST_ID,
                MealContract.MealEntry.COLUMN_NAME_LUNCH_ID,
                MealContract.MealEntry.COLUMN_NAME_DINNER_ID
        };
        String sortOrder = MealContract.MealEntry._ID + " ASC";
        Cursor cursor = db.query(
                MealContract.MealEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            long breakfastId = cursor.getLong(cursor.getColumnIndexOrThrow(MealContract.MealEntry.COLUMN_NAME_BREAKFAST_ID));
            long lunchId = cursor.getLong(cursor.getColumnIndexOrThrow(MealContract.MealEntry.COLUMN_NAME_LUNCH_ID));
            long dinnerId = cursor.getLong(cursor.getColumnIndexOrThrow(MealContract.MealEntry.COLUMN_NAME_DINNER_ID));
            Meal meal = new Meal(id, breakfastId, lunchId, dinnerId);
            meals.add(meal);
        }
        cursor.close();
        return meals;
    }

    public long insertGroceryList(GroceryList groceryList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GroceryListContract.GroceryListEntry.COLUMN_NAME_INGREDIENT, groceryList.getIngredient());
        long id = db.insert(GroceryListContract.GroceryListEntry.TABLE_NAME, null, values);
        return id;
    }

    public ArrayList<GroceryList> getGroceryLists() {
        ArrayList<GroceryList> groceryLists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                GroceryListContract.GroceryListEntry.COLUMN_NAME_INGREDIENT
        };
        String sortOrder = GroceryListContract.GroceryListEntry._ID + " ASC";
        Cursor cursor = db.query(
                GroceryListContract.GroceryListEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String ingredient = cursor.getString(cursor.getColumnIndexOrThrow(GroceryListContract.GroceryListEntry.COLUMN_NAME_INGREDIENT));
            GroceryList groceryList = new GroceryList(id, ingredient);
            groceryLists.add(groceryList);
        }
        cursor.close();
        return groceryLists;
    }
}