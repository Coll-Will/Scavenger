package com.example.scavenger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class RecipeDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "recipes.db";
    public static final String TABLE_NAME = "recipes";
    public static final String COL_1 = "DISHID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "INGREDIENTS";
    public static final String COL_4 = "GLUTEN_FREE";
    public static final String COL_5 = "DAIRY_FREE";
    public static final String COL_6 = "VEGAN";
    public static final String COL_7 = "VEGETARIAN";
    public static final String COL_8 = "IMGNAME";
    public static final String COL_9 = "INSTRUCTIONS";

    public RecipeDBHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (DISHID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, INGREDIENTS TEXT, GLUTEN_FREE INTEGER, DAIRY_FREE INTEGER, VEGAN INTEGER, VEGETARIAN INTEGER, IMGNAME TEXT, INSTRUCTIONS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String ingredients, int gluten, int dairy, int vegan,  int vegetarian, String imgname, String instuctions)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, ingredients);
        contentValues.put(COL_4, gluten);
        contentValues.put(COL_5, dairy);
        contentValues.put(COL_6, vegan);
        contentValues.put(COL_7, vegetarian);
        contentValues.put(COL_8, imgname);
        contentValues.put(COL_9, instuctions);
        long result = db.insert(TABLE_NAME, null,contentValues);
        return result != -1;
    }

    public Cursor getAllRecipes()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + ";",null);
    }

    public Cursor getRecipeFromIngredients(String ingredients, int restriction)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        switch(restriction)
        {
            case 1:
                return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE INGREDIENTS = '" + ingredients + "' AND GLUTEN_FREE = 1;",null);
            case 2:
                return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE INGREDIENTS = '" + ingredients + "' AND DAIRY_FREE = 1;",null);
            case 3:
                return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE INGREDIENTS = '" + ingredients + "' AND VEGAN = 1;",null);
            case 4:
                return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE INGREDIENTS = '" + ingredients + "' AND VEGETARIAN = 1;",null);
            default:
                return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE INGREDIENTS = '" + ingredients + "';",null);
        }
    }
}
