package com.seg3525_project.pdfviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        email = (EditText) findViewById(R.id.email);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void recoverPassword(View view) {
        ArrayList<User> users = Library.getInstance().getUsers();
        User user = null;

        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getEmail().equals(email.getText().toString())) {
                user = users.get(i);
                break;
            }
        }

        if(user != null) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{user.getEmail()});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Used Textbooks - Password Recovery");
            intent.putExtra(Intent.EXTRA_TEXT, "Your password is " + user.getPassword());

            try {
                startActivity(intent);
                Toast.makeText(ForgotPasswordActivity.this, "Password sent.", Toast.LENGTH_SHORT).show();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(ForgotPasswordActivity.this, "No email client installed.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(ForgotPasswordActivity.this, "User does not exist.", Toast.LENGTH_SHORT).show();
        }
    }

}
