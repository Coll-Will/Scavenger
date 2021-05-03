package com.example.scavenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity
{
    DatabaseHelper myDB;
    EditText editName,editEmail, editPassword;
    Button btnSignup, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        myDB = new DatabaseHelper(this);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.goToLoginbtn);
        addData();
        initGoToLoginButton();
    }

    public void addData()
    {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Cursor result = myDB.checkData(editEmail.getText().toString(), editPassword.getText().toString());
                if (result.getCount() == 1)
                {
                    Toast.makeText(SignupActivity.this, "Account Already Exists", Toast.LENGTH_LONG).show();
                } else {
                    boolean isInserted = myDB.insertData(editName.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString());
                    if (isInserted)
                    {
                        Toast.makeText(SignupActivity.this, "Welcome " + editName.getText().toString(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignupActivity.this, MainMenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignupActivity.this, "Account Creation Failed", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void initGoToLoginButton()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}