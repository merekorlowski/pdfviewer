package com.seg3525_project.pdfviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

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

    public void borrowBooks(View view) {
        User user = Session.getInstance().getUser();
        ArrayList<Book> booksInCart = user.getBooksInCart();

        DBHelper dbHelper = new DBHelper(this);

        Date expiryDate = new Date();
        expiryDate.setTime(expiryDate.getTime() + 15 * 24 * 60 * 60 * 1000);
        for(int i = 0, size = user.getBooksInCart().size(); i < size; i++) {
            Book book = booksInCart.get(i);
            book.setBorrower(user.getEmail());
            book.setExpiryDate(expiryDate);
            dbHelper.updateBook(book);
        }

        Toast.makeText(this, booksInCart.size() + " books borrowed.", Toast.LENGTH_SHORT).show();
        user.setBooksInCart(new ArrayList<Book>());
        Intent intent = new Intent(this, BorrowedBooksActivity.class);
        startActivity(intent);
    }


}
