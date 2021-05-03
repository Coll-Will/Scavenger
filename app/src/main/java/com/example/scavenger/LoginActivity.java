package com.example.scavenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{
    DatabaseHelper myDB;
    EditText editEmail, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SharedPreferences logged = getSharedPreferences("logged",MODE_PRIVATE);
        String isLogged = logged.getString("logged", "false");
        if(isLogged.equals("true"))
        {
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLoginButton();
        initSignupButton();
        myDB = new DatabaseHelper(this);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
    }

    public void initSignupButton()
    {
        Button goToSignupBtn = findViewById(R.id.btnGoToSignup);
        goToSignupBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void initLoginButton()
    {
        Button Loginbtn = findViewById(R.id.btnLogin);
        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Cursor result = myDB.checkData(editEmail.getText().toString(), editPassword.getText().toString());
                if(result.getCount() == 1 && result.moveToFirst())
                {
                    SharedPreferences logged = getSharedPreferences("logged",MODE_PRIVATE);
                    SharedPreferences.Editor editor = logged.edit();
                    editor.putString("logged","true");
                    editor.apply();

                    String userName = result.getString(result.getColumnIndex("NAME"));
                    Toast.makeText(LoginActivity.this, "Welcome "+ userName, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Incorrect Email or Password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}