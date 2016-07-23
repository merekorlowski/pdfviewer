package com.seg3525_project.pdfviewer.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.seg3525_project.pdfviewer.database.DbHelper;
import com.seg3525_project.pdfviewer.R;
import com.seg3525_project.pdfviewer.database.TableInfo.UserInfo;
import com.seg3525_project.pdfviewer.models.User;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText fullName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        fullName = (EditText) findViewById(R.id.fullName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.comfirmPassword);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void createAccount(View view) {
        DbHelper dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.getUsers();

        boolean exists = false;
        if(cursor.moveToFirst()) {
            do {
                if (cursor.getString(UserInfo.EMAIL_COLUMN_NUMBER).equals(email.getText().toString()))
                    exists = true;
            } while (cursor.moveToNext());
        }

        if(validateFullName() && validateEmail() && validatePassword() && validateConfirmPassword()) {
            if(!exists) {

                dbHelper.addUser(new User(
                        fullName.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString()
                ));

                Toast.makeText(CreateAccountActivity.this, "Successfully created account.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("email", email.getText().toString());
                intent.putExtra("password", password.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(CreateAccountActivity.this, "User with this email already exists.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateFullName() {
        if(!fullName.getText().toString().equals(""))
            return true;
        else {
            fullName.setError("Must enter full name.");
            return false;
        }
    }

    private boolean validateEmail() {

        String emailStr = email.getText().toString();

        if(emailStr.endsWith("@uottawa.ca"))
            return true;
        else {
            email.setError("Invalid uOttawa email.");
            return false;
        }
    }

    private boolean validatePassword() {
        String passwordStr = password.getText().toString();

        if(passwordStr.length() > 6 && passwordStr.length() < 12
                && !passwordStr.equals(""))
            return true;
        else {
            password.setError("Must be between 6 and 12 characters.");
            return false;
        }

    }

    private boolean validateConfirmPassword() {
        if(password.getText().toString().matches(confirmPassword.getText().toString())
                && !confirmPassword.getText().toString().equals(""))
            return true;
        else {
            confirmPassword.setError("Does not match password.");
            return false;
        }
    }


}
