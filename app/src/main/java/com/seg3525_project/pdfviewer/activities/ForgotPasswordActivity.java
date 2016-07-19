package com.seg3525_project.pdfviewer.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.seg3525_project.pdfviewer.database.DBHelper;
import com.seg3525_project.pdfviewer.R;
import com.seg3525_project.pdfviewer.database.TableInfo;

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
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getUser(email.getText().toString());
        cursor.moveToFirst();

        if(cursor != null) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{cursor.getString(TableInfo.UserInfo.EMAIL_COLUMN_NUMBER)});
            intent.putExtra(Intent.EXTRA_SUBJECT, "E-brary - Password Recovery");
            intent.putExtra(Intent.EXTRA_TEXT, "Your password is " + cursor.getString(TableInfo.UserInfo.PASSWORD_COLUMN_NUMBER));

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
