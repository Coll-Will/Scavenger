package com.example.scavenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ArrayList<String> ingredients;
    private LinearLayout ingredientList;
    private IngredientDBHelper db;
    private CheckBox chkGluten, chkDairy, chkVegan, chkVegetarian;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ingredientList = findViewById(R.id.ingredientLayout);
        ingredients = new ArrayList<String>();
        db = new IngredientDBHelper(this);
        initFavoritesButton();
        initProfileButton();
        initSettingsButton();
        initAddButton();
        initSearchButton();
        initCheckBoxes();
    }

    private void initProfileButton() {
        ImageButton profilebtn = findViewById(R.id.profileBtn);
        profilebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initFavoritesButton() {
        ImageButton favoritesbtn = findViewById(R.id.favoritesBtn);
        favoritesbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton settingsbtn = (ImageButton) findViewById(R.id.settingsBtn);
        settingsbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private boolean isIngredient(String str)
    {

        return !str.matches(".*[1-9].*") && db.isIngredient(str);
    }

    private void initAddButton()
    {
        ImageButton addBtn = (ImageButton) findViewById(R.id.imgbtnAdd);
        final EditText editIngredients = findViewById(R.id.editIngredients);

        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String ingredient = editIngredients.getText().toString();
                TextView textError = (TextView) findViewById(R.id.textErrorMsg);
                if(!isIngredient(ingredient))
                {
                    textError.setText("Invalid Ingredient");
                }
                else if(ingredients.contains(ingredient))
                {
                    textError.setText("Ingredient Already Added");
                }
                else
                {
                    textError.setText("");
                    editIngredients.getText().clear();
                    TextView textView = new TextView(MainActivity.this);
                    textView.setText(ingredient);
                    textView.setTextColor(getResources().getColor(R.color.white));
                    textView.setTextSize(18);
                    ingredientList.addView(textView);
                    ingredients.add(ingredient);
                }
            }
        });
    }

    private void initSearchButton()
    {
        Button searchBtn = (Button)findViewById(R.id.btnSearch);
        searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayRecipesActivity.class);
                StringBuilder msg = new StringBuilder();
                for(int i = 0; i < ingredients.size(); i++) {
                    if( i == ingredients.size()-1)
                    {
                        msg.append(ingredients.get(i));
                    }
                    else{
                        msg.append(ingredients.get(i) + "|");
                    }
                }
                intent.putExtra("MESSAGE", (CharSequence) msg);
                intent.putExtra("GLUTEN", "NO");
                intent.putExtra("DAIRY", "NO");
                intent.putExtra("VEGAN", "NO");
                intent.putExtra("VEGETARIAN", "NO");
                if(chkGluten.isChecked())
                {
                    intent.putExtra("GLUTEN", "YES");
                }
                if(chkDairy.isChecked())
                {
                    intent.putExtra("DAIRY", "YES");
                }
                if(chkVegan.isChecked())
                {
                    intent.putExtra("VEGAN", "YES");
                }
                if(chkVegetarian.isChecked())
                {
                    intent.putExtra("VEGETARIAN", "YES");
                }
                startActivity(intent);
            }
        });
    }

    private void initCheckBoxes() //replace with onclicks
    {
        chkGluten = findViewById(R.id.ckGlutenFree);
        chkGluten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkDairy.setChecked(false);
                chkVegan.setChecked(false);
                chkVegetarian.setChecked(false);
            }
        });

        chkDairy = findViewById(R.id.ckDairyFree);
        chkDairy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkGluten.setChecked(false);
                chkVegan.setChecked(false);
                chkVegetarian.setChecked(false);
            }
        });

        chkVegan = findViewById(R.id.ckVegan);
        chkVegan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkDairy.setChecked(false);
                chkGluten.setChecked(false);
                chkVegetarian.setChecked(false);
            }
        });

        chkVegetarian = findViewById(R.id.ckVegetarian);
        chkVegetarian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkDairy.setChecked(false);
                chkVegan.setChecked(false);
                chkGluten.setChecked(false);
            }
        });
    }
}
