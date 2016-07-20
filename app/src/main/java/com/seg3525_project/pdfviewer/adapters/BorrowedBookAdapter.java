package com.seg3525_project.pdfviewer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seg3525_project.pdfviewer.models.Book;
import com.seg3525_project.pdfviewer.database.DbHelper;
import com.seg3525_project.pdfviewer.R;

import java.util.ArrayList;

/**
 * Created by merek on 07/07/16.
 */
public class BorrowedBookAdapter extends ArrayAdapter<Book> {

    public BorrowedBookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Book book = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.borrowed_book, parent, false);

        ImageView bookImage = (ImageView) convertView.findViewById(R.id.bookImage);
        TextView bookTitle = (TextView) convertView.findViewById(R.id.bookTitle);
        TextView bookAuthor = (TextView) convertView.findViewById(R.id.bookAuthor);
        TextView bookISBN = (TextView) convertView.findViewById(R.id.bookISBN);
        TextView timeLeft = (TextView) convertView.findViewById(R.id.timeLeft);
        ImageView removeBook = (ImageView) convertView.findViewById(R.id.removeBook);

        bookImage.setImageBitmap(book.getImage());
        bookTitle.setText(book.getTitle());
        bookAuthor.setText("by " + book.getAuthor());
        bookISBN.setText("ISBN: " + book.getISBN());
        timeLeft.setText(book.getRemainingTime());

        removeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper dbHelper = new DbHelper(getContext());
                book.setBorrower("nobody");
                dbHelper.updateBook(book);
                remove(book);
            }
        });

        return convertView;
    }

}



