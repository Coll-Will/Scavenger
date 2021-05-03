package com.example.scavenger;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;

public class DisplayRecipesActivity extends AppCompatActivity
{
    private RecipeDBHelper db;
    private LinearLayout output;
    private LinearLayout recipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayrecipes);
        db = new RecipeDBHelper(this);
        output = findViewById(R.id.displayLayout);
        recipeLayout = new LinearLayout(DisplayRecipesActivity.this);
        recipeLayout.setOrientation(LinearLayout.HORIZONTAL);
        displayRecipes();
    }

    private void displayRecipes()
    {
        ImageView image;
        TextView textView;
        Button showMoreBtn;
        View recipeView;

        Cursor result = getRecipes();
        if (result.moveToFirst())
        {
            do {
                recipeView = getLayoutInflater().inflate(R.layout.activity_recipelayout, null, false);

                image = recipeView.findViewById(R.id.imgRecipe);
                String imgName = result.getString(7);
                String uri = "@drawable/" + imgName;
                int imageResource = getResources().getIdentifier(uri, "drawable", getPackageName());
                if(imageResource != 0)
                {
                    Drawable res = AppCompatResources.getDrawable(DisplayRecipesActivity.this, imageResource);
                    image.setImageDrawable(res);
                }
                else
                {
                    image.setImageResource(R.drawable.defaultrecipeimage);
                }

                textView = recipeView.findViewById(R.id.textRecipeName);
                String recName = result.getString(1);
                textView.setText(recName);

                showMoreBtn = recipeView.findViewById(R.id.btnMoreInfo);
                showMoreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getSupportFragmentManager();
                        RecipeInfoDialog infoDialog = new RecipeInfoDialog();
                        infoDialog.show(fm, "More Info");
                    }
                });
                output.addView(recipeView);
            } while (result.moveToNext());
        }
        else
        {
            Toast.makeText(DisplayRecipesActivity.this, "No Recipes With Those Ingredients",Toast.LENGTH_LONG).show();
        }
    }

    public Cursor getRecipes()
    {
        Intent intent = getIntent();
        String str = intent.getStringExtra("MESSAGE");
        String gluten = intent.getStringExtra("GLUTEN");
        String dairy = intent.getStringExtra("DAIRY");
        String vegan = intent.getStringExtra("VEGAN");
        String vegetarian = intent.getStringExtra("VEGETARIAN");
        if(gluten.equalsIgnoreCase("yes"))
        {
            return db.getRecipeFromIngredients(str,1);
        }
        else if(dairy.equalsIgnoreCase("yes"))
        {
            return db.getRecipeFromIngredients(str,2);
        }
        else if(vegan.equalsIgnoreCase("yes"))
        {
            return db.getRecipeFromIngredients(str,3);
        }
        else if(vegetarian.equalsIgnoreCase("yes"))
        {
            return db.getRecipeFromIngredients(str,4);
        }
        else
        {
            return db.getRecipeFromIngredients(str,0);
        }
    }
}