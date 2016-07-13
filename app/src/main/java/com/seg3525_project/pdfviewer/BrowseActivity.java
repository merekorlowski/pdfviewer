package com.seg3525_project.pdfviewer;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class BrowseActivity extends AppCompatActivity {

    private ListView searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Book> displayedBooks = new ArrayList<>();

        if(books.size() == 0) {
            books.add(new Book(((BitmapDrawable) getResources().getDrawable(R.drawable.stats)).getBitmap(),
                    "nobody",
                    "Essentials of Probability & Statistics for Engineers & Scientists",
                    "Ronald E. Walpole",
                    "0-321-78373-5",
                    "",
                    "/app/res/pdf/stats.pdf"));
        }

        for(int i = 0; i < books.size(); i++) {
            if(books.get(i).getBorrower().equals("nobody"))
                displayedBooks.add(books.get(i));
        }

        searchResults = (ListView) findViewById(R.id.searchResults);
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
