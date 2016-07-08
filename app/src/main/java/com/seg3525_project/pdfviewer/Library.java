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
    private ArrayList<User> users;

    private Library() {
        books = new ArrayList<>();
        books.add(new Book(R.drawable.stats,
                "Essentials of Probability & Statistics for Engineers & Scientists",
                "Ronald E. Walpole",
                "0-321-78373-5",
                "",
                "/app/res/pdf/stats.pdf"));

        users = new ArrayList<>();

    }

    public static Library getInstance() {
        if(instance == null)
            instance = new Library();
        return instance;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<User> getUsers() { return users; }

    public boolean addUser(User user) {

        boolean exists = false;
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getEmail().matches(user.getEmail()))
                exists = true;
        }

        if(!exists)
            users.add(user);

        return exists;

    }

}
