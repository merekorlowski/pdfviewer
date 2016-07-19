package com.seg3525_project.pdfviewer.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.seg3525_project.pdfviewer.database.DBHelper;
import com.seg3525_project.pdfviewer.R;
import com.seg3525_project.pdfviewer.models.Session;
import com.seg3525_project.pdfviewer.database.TableInfo.UserInfo;
import com.seg3525_project.pdfviewer.models.User;

public class LoginActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private Cursor cursor;
    private EditText email;
    private EditText password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);
        cursor = dbHelper.getUsers();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            email.setText(extras.get("email").toString());
            password.setText(extras.get("password").toString());
        }

    }

    public void login(View view) {

        progressBar.setVisibility(View.VISIBLE);

        User user = null;

        cursor.moveToFirst();
        do {
            if(email.getText().toString().equals(cursor.getString(UserInfo.EMAIL_COLUMN_NUMBER))
                    && password.getText().toString().equals(cursor.getString(UserInfo.PASSWORD_COLUMN_NUMBER))) {
                user = new User(
                        cursor.getString(UserInfo.FULL_NAME_COLUMN_NUMBER),
                        cursor.getString(UserInfo.EMAIL_COLUMN_NUMBER),
                        cursor.getString(UserInfo.PASSWORD_COLUMN_NUMBER)
                );
                break;
            }
        } while(cursor.moveToNext());

        if(user != null) {
            Session.getInstance().setUser(user);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this, "Successfully logged in.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, BrowseActivity.class);
            startActivity(intent);
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
        }

    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }


}
