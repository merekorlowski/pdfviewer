package com.seg3525_project.pdfviewer;

import java.util.ArrayList;

/**
 * Created by merek on 07/07/16.
 */
public class Library {

    private static Library instance = null;
    private ArrayList<Book> books;

    private Library() {}

    public static Library getInstance() {
        if(instance == null)
            instance = new Library();
        return instance;
    }

    public ArrayList<Book> getBook() {
        return books;
    }

}
