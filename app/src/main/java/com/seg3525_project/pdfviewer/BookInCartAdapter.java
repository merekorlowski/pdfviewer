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
public class BookInCartAdapter extends ArrayAdapter<Book> {
    public BookInCartAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Book book = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_book, parent, false);

        ImageView bookImage = (ImageView) convertView.findViewById(R.id.bookImage);
        TextView bookTitle = (TextView) convertView.findViewById(R.id.bookTitle);
        TextView bookAuthor = (TextView) convertView.findViewById(R.id.bookAuthor);
        TextView bookISBN = (TextView) convertView.findViewById(R.id.bookISBN);
        ImageView removeFromCart = (ImageView) convertView.findViewById(R.id.removeFromCart);

        bookImage.setImageResource(book.getImage());
        bookTitle.setText(book.getTitle());
        bookAuthor.setText("by " + book.getAuthor());
        bookISBN.setText("ISBN: " + book.getISBN());

        removeFromCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Session.getInstance().getUser().removeBookFromCart(book);
                notifyDataSetChanged();
            }

        });

        return convertView;
    }


}


