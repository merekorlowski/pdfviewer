package com.seg3525_project.pdfviewer;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by merek on 07/07/16.
 */
public class Book {

    private Bitmap image;
    private String title;
    private String author;
    private String ISBN;
    private String description;
    private Date expiryDate;

    public Book(Bitmap image, String title, String author, String ISBN, String description, Date expiryDate) {
        this.image = image;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.expiryDate = expiryDate;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getRemainingTime() {
        long nowInMilliseconds = new Date().getTime();
        long expiryDateInMilliseconds = expiryDate.getTime();

        long difference = expiryDateInMilliseconds - nowInMilliseconds;
        if(difference / (1000 * 60 * 60 * 24) > 0)
            return Math.round(difference / (60000 * 24)) + " days left";
        else if(difference / (1000 * 60 * 60) > 0)
            return Math.round(difference / (1000 * 60 * 60)) + " hours left";
        else
            return Math.round(difference / (1000 * 60)) + " minutes left";

    }

}
