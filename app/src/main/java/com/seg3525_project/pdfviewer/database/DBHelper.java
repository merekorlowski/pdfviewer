package com.seg3525_project.pdfviewer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.seg3525_project.pdfviewer.database.TableInfo.*;
import com.seg3525_project.pdfviewer.helpers.BitmapUtility;
import com.seg3525_project.pdfviewer.models.Book;
import com.seg3525_project.pdfviewer.models.User;

/**
 * Created by merek on 13/07/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pdfviewer.db";
    private static final int DATABASE_VERSION = 2;

    private String CREATE_USER_TABLE = "CREATE TABLE " + UserInfo.TABLE_NAME + "(" +
            UserInfo.FULL_NAME + " TEXT, " +
            UserInfo.EMAIL + " TEXT, " +
            UserInfo.PASSWORD + " TEXT);";

    private String CREATE_BOOK_TABLE = "CREATE TABLE " + BookInfo.TABLE_NAME + "(" +
            BookInfo.ID + " LONG, " +
            BookInfo.BORROWER + " TEXT, " +
            BookInfo.IMAGE + " BLOB, " +
            BookInfo.TITLE + " TEXT, " +
            BookInfo.AUTHOR + " TEXT, " +
            BookInfo.ISBN + " TEXT, " +
            BookInfo.DESCRIPTION + " TEXT, " +
            BookInfo.PDF + " TEXT, " +
            BookInfo.EXPIRY_DATE + " TEXT);";

    private String[] userColumns = {
            UserInfo.FULL_NAME,
            UserInfo.EMAIL,
            UserInfo.PASSWORD
    };

    private String[] bookColumns = {
            BookInfo.ID,
            BookInfo.BORROWER,
            BookInfo.IMAGE,
            BookInfo.TITLE,
            BookInfo.AUTHOR,
            BookInfo.ISBN,
            BookInfo.DESCRIPTION,
            BookInfo.PDF,
            BookInfo.EXPIRY_DATE
    };

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database operations", "Database created");

       /*addBook(new Book("nobody",
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.stats)).getBitmap(),
                "Essentials of Probability & Statistics for Engineers & Scientists",
                "Ronald E. Walpole",
                "0-321-78373-5",
                "This text covers the essential topics needed for a fundamental understanding of basic statistics and its applications in the fields of engineering and the sciences. Interesting, relevant applications use real data from actual studies, showing how the concepts and methods can be used to solve problems in the field. The authors assume one semester of differential and integral calculus as a prerequisite.",
                "https://firdaramadhena.files.wordpress.com/2014/01/ronald_e_walpole__et_al_essentials_of_probability__statistics_for_engineers__scientists__2013.pdf"));

        addBook(new Book("nobody",
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.computer_science)).getBitmap(),
                "Computer Science - The Hardware, Software and Heart of It",
                "Edward K. Blum & Alfred V. Aho",
                "78-1-4614-1167-3",
                "",
                "http://publik.tuwien.ac.at/files/PubDat_201302.pdf"));//*/

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        Log.d("Database operations", "User table created");
        sqLiteDatabase.execSQL(CREATE_BOOK_TABLE);
        Log.d("Database operations", "My book table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserInfo.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BookInfo.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(UserInfo.TABLE_NAME, null, getUserContentValues(user));
        Log.d("Database operations", "User row inserted");
    }

    public Cursor getUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(UserInfo.TABLE_NAME, userColumns, null, null, null, null, null);
    }

    public Cursor getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(UserInfo.TABLE_NAME, userColumns, UserInfo.EMAIL + "=" + "'" + email + "'", null, null, null, null);
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(UserInfo.TABLE_NAME, getUserContentValues(user), UserInfo.EMAIL + " = ?",
                new String[]{String.valueOf(user.getEmail())});
    }

    public void addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(BookInfo.TABLE_NAME, null, getBookContentValues(book));
        Log.d("Database operations", "Book row inserted");
    }

    public int deleteBook() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(BookInfo.TABLE_NAME, null, null);
    }

    public Cursor getBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(BookInfo.TABLE_NAME, bookColumns, null, null, null, null, null);
    }

    public Cursor getBook(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(BookInfo.TABLE_NAME, bookColumns, BookInfo.ID + "=" + id, null, null, null, null);
    }

    public void updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(BookInfo.TABLE_NAME, getBookContentValues(book), BookInfo.ID + "=" + book.getId(), null);
        Log.d("Database operations", "Book row updated");
    }

    private ContentValues getUserContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserInfo.FULL_NAME, user.getFullName());
        contentValues.put(UserInfo.EMAIL, user.getEmail());
        contentValues.put(UserInfo.PASSWORD, user.getPassword());
        return contentValues;
    }

    private ContentValues getBookContentValues(Book book) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookInfo.ID, book.getId());
        contentValues.put(BookInfo.BORROWER, book.getBorrower());
        contentValues.put(BookInfo.IMAGE, BitmapUtility.getBytes(book.getImage()));
        contentValues.put(BookInfo.TITLE, book.getTitle());
        contentValues.put(BookInfo.AUTHOR, book.getAuthor());
        contentValues.put(BookInfo.ISBN, book.getISBN());
        contentValues.put(BookInfo.DESCRIPTION, book.getDescription());
        contentValues.put(BookInfo.PDF, book.getPdf());
        contentValues.put(BookInfo.EXPIRY_DATE, book.getExpiryDate().toString());
        return contentValues;
    }

}
