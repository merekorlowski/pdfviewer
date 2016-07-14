package com.seg3525_project.pdfviewer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Date;

public class BookInfoActivity extends AppCompatActivity {

    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getBooks();
        long index = 0;

        Bundle extras = getIntent().getExtras();
        if(extras != null)
            index = extras.getLong("bookIndex");

        Log.d("ID-1", "" + index);

        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            Log.d("ID-2", "" + cursor.getLong(TableInfo.BookInfo.ID_COLUMN_NUMBER));
            if(cursor.getLong(TableInfo.BookInfo.ID_COLUMN_NUMBER) == index) {
                book = new Book(
                        cursor.getLong(TableInfo.BookInfo.ID_COLUMN_NUMBER),
                        cursor.getString(TableInfo.BookInfo.BORROWER_COLUMN_NUMBER),
                        BitmapUtility.getImage(cursor.getBlob(TableInfo.BookInfo.IMAGE_COLUMN_NUMBER)),
                        cursor.getString(TableInfo.BookInfo.TITLE_COLUMN_NUMBER),
                        cursor.getString(TableInfo.BookInfo.AUTHOR_COLUMN_NUMBER),
                        cursor.getString(TableInfo.BookInfo.ISBN_COLUMN_NUMBER),
                        cursor.getString(TableInfo.BookInfo.DESCRIPTION_COLUMN_NUMBER),
                        cursor.getString(TableInfo.BookInfo.PDF_COLUMN_NUMBER),
                        new Date(cursor.getString(TableInfo.BookInfo.EXPIRY_DATE_COLUMN_NUMBER))
                );
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = Session.getInstance().getUser();
                book.setBorrower(user.getEmail());
                user.addBookToCart(book);
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch(item.getItemId()) {
            case R.id.action_logOut:
                Session.getInstance().setUser(null);
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_browse:
                intent = new Intent(this, BrowseActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_borrowed_books:
                intent = new Intent(this, BorrowedBooksActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_cart:
                intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
