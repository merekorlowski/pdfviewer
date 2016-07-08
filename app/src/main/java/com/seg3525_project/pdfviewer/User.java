package com.seg3525_project.pdfviewer;

import java.util.ArrayList;

/**
 * Created by merek on 07/07/16.
 */
public class User {

    private String fullName;
    private String email;
    private String password;
    private ArrayList<Book> borrowedBooks;
    private ArrayList<Book> booksInCart;

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        borrowedBooks = new ArrayList<>();
        booksInCart = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(ArrayList<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public ArrayList<Book> getBooksInCart() {
        return booksInCart;
    }

    public void setBooksInCart(ArrayList<Book> booksInCart) {
        this.booksInCart = booksInCart;
    }

    public void addBookToCart(Book book) {
        booksInCart.add(book);
    }

    public void addBookToBorrowedBooks(Book book) {
        borrowedBooks.add(book);
    }

    public void removeBookFromCart(Book book) {
        booksInCart.add(book);
    }

    public void removeBookFromBorrowedBooks(Book book) {
        borrowedBooks.add(book);
    }

}
