package com.seg3525_project.pdfviewer;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;

import java.util.Date;

/**
 * Created by merek on 07/07/16.
 */
public class Book {

    private long id;
    private String borrower;
    private Bitmap image;
    private String title;
    private String author;
    private String ISBN;
    private String description;
    private Date expiryDate;
    private String pdf;

    public Book(String borrower, Bitmap image, String title, String author, String ISBN, String description, String pdf) {
        id = System.currentTimeMillis();
        this.borrower = borrower;
        this.image = image;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.pdf = pdf;
        expiryDate = new Date();
        expiryDate.setTime(expiryDate.getTime() + 15 * 24 * 60 * 60 * 1000);
    }

    public Book(long id, String borrower, Bitmap image, String title, String author, String ISBN, String description, String pdf, Date expiryDate) {
        this.id = id;
        this.borrower = borrower;
        this.image = image;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.pdf = pdf;
        this.expiryDate = expiryDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
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

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getRemainingTime() {
        long nowInMilliseconds = new Date().getTime();
        long expiryDateInMilliseconds = expiryDate.getTime();

        long difference = expiryDateInMilliseconds - nowInMilliseconds;
        if(difference / (1000 * 60 * 60 * 24) > 0)
            return Math.round(difference / (1000 * 60 * 60 * 24)) + " Days left";
        else if(difference / (1000 * 60 * 60) > 0)
            return Math.round(difference / (1000 * 60 * 60)) + " Hours left";
        else
            return Math.round(difference / (1000 * 60)) + " Minutes left";

    }

}
