package com.seg3525_project.pdfviewer.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seg3525_project.pdfviewer.helpers.BitmapUtility;
import com.seg3525_project.pdfviewer.models.Book;
import com.seg3525_project.pdfviewer.database.DbHelper;
import com.seg3525_project.pdfviewer.R;
import com.seg3525_project.pdfviewer.models.Session;
import com.seg3525_project.pdfviewer.database.TableInfo;
import com.seg3525_project.pdfviewer.models.User;

import java.util.Date;

public class BookInfoActivity extends AppCompatActivity {

    private Book book;
    private ImageView bookImage;
    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookISBN;
    private TextView bookDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        bookImage = (ImageView) findViewById(R.id.bookImage);
        bookTitle = (TextView) findViewById(R.id.bookTitle);
        bookAuthor = (TextView) findViewById(R.id.bookAuthor);
        bookISBN = (TextView) findViewById(R.id.bookISBN);
        bookDescription = (TextView) findViewById(R.id.bookDescription);

        DbHelper dbHelper = new DbHelper(this);
        long id = 0;

        Bundle extras = getIntent().getExtras();
        if(extras != null)
            id = extras.getLong("bookID");

        Cursor cursor = dbHelper.getBook(id);

        if(cursor.moveToFirst()) {
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

        bookImage.setImageBitmap(book.getImage());
        bookTitle.setText(book.getTitle());
        bookAuthor.setText("by " + book.getAuthor());
        bookISBN.setText("ISBN: " + book.getISBN());
        bookDescription.setText(book.getDescription());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = Session.getInstance().getUser();
                book.setBorrower(user.getEmail());
                user.addBookToCart(book);
                Toast.makeText(BookInfoActivity.this, book.getTitle() + " added to cart.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Successfully logged out.", Toast.LENGTH_SHORT).show();
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
