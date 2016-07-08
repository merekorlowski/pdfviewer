package com.seg3525_project.pdfviewer;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class BrowseActivity extends AppCompatActivity {

    private ListView searchResults;
    private ImageView cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Drawable addToCart = getResources().getDrawable(R.drawable.ic_add_shopping_cart_white_24dp);
        addToCart.setColorFilter(new
                PorterDuffColorFilter(0xFFC24846, PorterDuff.Mode.MULTIPLY));

        cart = (ImageView) findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }

        });
        searchResults = (ListView) findViewById(R.id.searchResults);
        searchResults.setAdapter(new SearchResultsBookAdapter(this, Library.getInstance().getBooks()));

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
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


}
