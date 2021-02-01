
package com.example.Android_App;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextConfirmPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;
    DatabaseHelper db;

    int score = 0;
    // boolean indicating if password has an upper case
    boolean upper = false;
    // boolean indicating if password has a lower case
    boolean lower = false;
    // boolean indicating if password has at least one digit
    boolean digit = false;
    // boolean indicating if password has a least one special char
    boolean specialChar = false;
    // boolean indication if password meets the requirements
    boolean usable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        db = new DatabaseHelper(this);
        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mTextConfirmPassword = (EditText) findViewById(R.id.edittext_confirm_password);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mTextViewLogin = (TextView) findViewById(R.id.textview_login);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();
                String confirm_password = mTextConfirmPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, password);
                if (res == false) {
                    if (usable == true) {
                        if (password.equals(confirm_password)) {
                            long val = db.addUser(user, password);
                            if (val > 0) {
                                Toast.makeText(RegisterActivity.this, "You have registered successfully", Toast.LENGTH_SHORT).show();
                                Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(moveToLogin);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Password does not meet the requirements", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void passwordStrength (String password) {

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (!specialChar && !Character.isLetterOrDigit(c)) {
                specialChar = true;
            } else {
                if (!digit && Character.isDigit(c)) {
                    digit = true;
                } else {
                    if (!upper || !lower) {
                        if (Character.isUpperCase(c)) {
                            upper = true;
                        } else {
                            lower = true;
                        }
                    }
                }
            }
        }
    }

    public void passwordUsable ()
    {
        if (upper == true && lower == true && digit == true && specialChar == true)
        {
            usable = true;
        }
        else
        {
            usable = false;
        }
    }
}