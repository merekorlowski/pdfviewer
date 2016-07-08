package com.seg3525_project.pdfviewer;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by merek on 07/07/16.
 */
public class Library {

    private static Library instance = null;
    private ArrayList<Book> books;

    private Library() {
        books = new ArrayList<>();
        books.add(new Book(R.drawable.stats, "Stats", "Someone", "3434-343-2343-3", "gerfgergergregregerge", "/app/res/pdf/stats.pdf"));
    }

    public static Library getInstance() {
        if(instance == null)
            instance = new Library();
        return instance;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

}
