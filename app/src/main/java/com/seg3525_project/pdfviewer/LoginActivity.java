package com.seg3525_project.pdfviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            email.setText(extras.get("email").toString());
            password.setText(extras.get("password").toString());
        }

    }

    public void login(View view) {

        ArrayList<User> users = Library.getInstance().getUsers();
        User user = null;

        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getEmail().matches(email.getText().toString())
                    && users.get(i).getPassword().matches(password.getText().toString())) {
                user = users.get(i);
                break;
            }

        }

        if(user != null) {
            Session.getInstance().setUser(user);
            Intent intent = new Intent(this, BrowseActivity.class);
            startActivity(intent);
        } else {
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
