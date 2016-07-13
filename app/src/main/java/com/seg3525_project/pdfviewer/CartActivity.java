package com.seg3525_project.pdfviewer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView booksInCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        booksInCart = (ListView) findViewById(R.id.booksInCart);
        booksInCart.setEmptyView(findViewById(R.id.noBooksInCart));
        booksInCart.setAdapter(new BookInCartAdapter(this, Session.getInstance().getUser().getBooksInCart()));

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

    public void borrowBooks(View view) {
        User user = Session.getInstance().getUser();

        DBHelper dbHelper = new DBHelper(this);
        /*Cursor cursor = dbHelper.getBooks();

        cursor.moveToFirst();
        while(cursor.moveToNext()) {

        }*/

        for(int i = 0; i < user.getBooksInCart().size(); i++) {
            user.getBooksInCart().get(i).setBorrower(Session.getInstance().getUser().getEmail());
            dbHelper.addBook(user.getBooksInCart().get(i));
        }

        user.setBooksInCart(new ArrayList<Book>());
        Intent intent = new Intent(this, BorrowedBooksActivity.class);
        startActivity(intent);
    }


}
