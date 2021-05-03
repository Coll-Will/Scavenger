package com.example.scavenger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class IngredientDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "ingredients.db";
    public static final String TABLE_NAME = "ingredients";
    public static final String COL_1 = "ingredientID";
    public static final String COL_2 = "NAME";

    public IngredientDBHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ingredientID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        long result = db.insert(TABLE_NAME, null,contentValues);
        return result != -1;
    }

    public boolean isIngredient(String str)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME + ";",null);
        boolean bool = false;
        if(result.moveToFirst())
        {
            do
            {
                String test = result.getString(1);
                if(str.equalsIgnoreCase(result.getString(1)))
                {
                    bool = true;
                }
            }while(result.moveToNext());
        }
        else
        {
            bool = false;
        }
        result.close();
        return bool;
    }
}
