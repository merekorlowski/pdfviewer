package com.seg3525_project.pdfviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by merek on 07/07/16.
 */
public class SearchResultsBookAdapter extends ArrayAdapter<Book> {
    public SearchResultsBookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Book book = getItem(position);
        final int index = position;

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_results_book, parent, false);

        ImageView bookImage = (ImageView) convertView.findViewById(R.id.bookImage);
        TextView bookTitle = (TextView) convertView.findViewById(R.id.bookTitle);
        TextView bookAuthor = (TextView) convertView.findViewById(R.id.bookAuthor);
        TextView bookISBN = (TextView) convertView.findViewById(R.id.bookISBN);
        ImageView addToCart = (ImageView) convertView.findViewById(R.id.addToCart);

        bookImage.setImageBitmap(book.getImage());
        bookTitle.setText(book.getTitle());
        bookAuthor.setText("by " + book.getAuthor());
        bookISBN.setText("ISBN: " + book.getISBN());

        addToCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                User user = Session.getInstance().getUser();
                book.setBorrower(user.getEmail());
                user.addBookToCart(book);
                remove(book);
            }

        });

        return convertView;
    }


}


