package com.seg3525_project.pdfviewer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import com.seg3525_project.pdfviewer.TableInfo.BookInfo;

public class BorrowedBooksActivity extends AppCompatActivity {

    private ListView borrowedBooks;
    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_books);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getBooks();
        books = new ArrayList<>();
        String email = Session.getInstance().getUser().getEmail();

        cursor.moveToFirst();
        do {
            Date now = new Date();
            Date expiryDate = new Date(cursor.getString(BookInfo.EXPIRY_DATE_COLUMN_NUMBER));

            if(cursor.getString(BookInfo.BORROWER_COLUMN_NUMBER).equals(email)
                    && now.before(expiryDate)) {
                books.add(new Book(
                        cursor.getLong(BookInfo.ID_COLUMN_NUMBER),
                        cursor.getString(BookInfo.BORROWER_COLUMN_NUMBER),
                        BitmapUtility.getImage(cursor.getBlob(BookInfo.IMAGE_COLUMN_NUMBER)),
                        cursor.getString(BookInfo.TITLE_COLUMN_NUMBER),
                        cursor.getString(BookInfo.AUTHOR_COLUMN_NUMBER),
                        cursor.getString(BookInfo.ISBN_COLUMN_NUMBER),
                        cursor.getString(BookInfo.DESCRIPTION_COLUMN_NUMBER),
                        cursor.getString(BookInfo.PDF_COLUMN_NUMBER),
                        expiryDate
                ));
            }
        } while(cursor.moveToNext());

        borrowedBooks = (ListView) findViewById(R.id.borrowedBooks);
        borrowedBooks.setAdapter(new BorrowedBookAdapter(this, books));
        borrowedBooks.setEmptyView(findViewById(R.id.noBooksBorrowed));
        borrowedBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Book book = books.get(position);
                Intent intent = new Intent(getApplicationContext(), PDFActivity.class);
                intent.putExtra("URL", book.getPdf());
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
        }

        return super.onOptionsItemSelected(item);
    }


}
