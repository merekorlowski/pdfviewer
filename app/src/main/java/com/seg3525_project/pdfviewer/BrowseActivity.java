package com.seg3525_project.pdfviewer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import com.seg3525_project.pdfviewer.TableInfo.BookInfo;

public class BrowseActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private Cursor cursor;
    private ArrayList<Book> displayedBooks;
    private EditText searchBar;
    private Button searchBtn;
    private ListView searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);
        cursor = dbHelper.getBooks();
        displayedBooks = new ArrayList<>();
        searchBar = (EditText) findViewById(R.id.searchBar);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchResults = (ListView) findViewById(R.id.searchResults);

        //dbHelper.deleteBook();

        search();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        /*searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

        searchResults.setEmptyView(findViewById(R.id.noResultsFound));
        searchResults.setAdapter(new SearchResultsBookAdapter(this, displayedBooks));

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

    public void search() {

        String query = searchBar.getText().toString();
        displayedBooks.clear();

        cursor.moveToFirst();
        do {
            if(cursor.getString(BookInfo.BORROWER_COLUMN_NUMBER).equals("nobody")
                    && (cursor.getString(BookInfo.TITLE_COLUMN_NUMBER).contains(query)
                    || cursor.getString(BookInfo.AUTHOR_COLUMN_NUMBER).contains(query)
                    || cursor.getString(BookInfo.ISBN_COLUMN_NUMBER).contains(query))) {
                displayedBooks.add(new Book(
                                cursor.getLong(BookInfo.ID_COLUMN_NUMBER),
                                cursor.getString(BookInfo.BORROWER_COLUMN_NUMBER),
                                BitmapUtility.getImage(cursor.getBlob(BookInfo.IMAGE_COLUMN_NUMBER)),
                                cursor.getString(BookInfo.TITLE_COLUMN_NUMBER),
                                cursor.getString(BookInfo.AUTHOR_COLUMN_NUMBER),
                                cursor.getString(BookInfo.ISBN_COLUMN_NUMBER),
                                cursor.getString(BookInfo.DESCRIPTION_COLUMN_NUMBER),
                                cursor.getString(BookInfo.PDF_COLUMN_NUMBER),
                                new Date(cursor.getString(BookInfo.EXPIRY_DATE_COLUMN_NUMBER))
                        )
                );
            }
        } while(cursor.moveToNext());

        searchResults.setAdapter(new SearchResultsBookAdapter(this, displayedBooks));


    }


}

