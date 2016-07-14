package com.seg3525_project.pdfviewer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import com.seg3525_project.pdfviewer.TableInfo.BookInfo;

public class BrowseActivity extends AppCompatActivity {

    private ListView searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getBooks();
        ArrayList<Book> displayedBooks = new ArrayList<>();


        /*dbHelper.addBook(new Book("nobody",
                ((BitmapDrawable) getResources().getDrawable(R.drawable.stats)).getBitmap(),
                "Essentials of Probability & Statistics for Engineers & Scientists",
                "Ronald E. Walpole",
                "0-321-78373-5",
                "",
                "/app/res/pdf/stats.pdf"));*/

        //dbHelper.deleteBook();

        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            if(cursor.getString(BookInfo.BORROWER_COLUMN_NUMBER).equals("nobody")) {
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
        }

        searchResults = (ListView) findViewById(R.id.searchResults);
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


}

