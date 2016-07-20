package com.seg3525_project.pdfviewer.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import com.seg3525_project.pdfviewer.helpers.BitmapUtility;
import com.seg3525_project.pdfviewer.models.Book;
import com.seg3525_project.pdfviewer.database.DbHelper;
import com.seg3525_project.pdfviewer.R;
import com.seg3525_project.pdfviewer.adapters.SearchResultsBookAdapter;
import com.seg3525_project.pdfviewer.models.Session;
import com.seg3525_project.pdfviewer.database.TableInfo.BookInfo;

public class BrowseActivity extends AppCompatActivity {

    private DbHelper dbHelper;
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

        dbHelper = new DbHelper(this);
        cursor = dbHelper.getBooks();
        displayedBooks = new ArrayList<>();
        searchBar = (EditText) findViewById(R.id.searchBar);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchResults = (ListView) findViewById(R.id.searchResults);

        search();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        searchBar.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            search();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void search() {

        String query = searchBar.getText().toString();
        displayedBooks.clear();

        String email = Session.getInstance().getUser().getEmail();

        if(cursor.moveToFirst()) {
            boolean matchesQuery = false;
            boolean inCart = false;
            do {
                if (!cursor.getString(BookInfo.BORROWER_COLUMN_NUMBER).equals(email)
                        && (cursor.getString(BookInfo.TITLE_COLUMN_NUMBER).contains(query)
                        || cursor.getString(BookInfo.AUTHOR_COLUMN_NUMBER).contains(query)
                        || cursor.getString(BookInfo.ISBN_COLUMN_NUMBER).contains(query))) {
                    matchesQuery = true;
                }

                for(int i = 0; i < Session.getInstance().getUser().getBooksInCart().size(); i++) {
                    if(cursor.getLong(BookInfo.ID_COLUMN_NUMBER) ==
                            Session.getInstance().getUser().getBooksInCart().get(i).getId()) {
                        inCart = true;
                        break;
                    }
                }

                if(matchesQuery && !inCart) {
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

            } while (cursor.moveToNext());
        }

        searchResults.setAdapter(new SearchResultsBookAdapter(this, displayedBooks));


    }


}

