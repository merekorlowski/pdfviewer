package com.seg3525_project.pdfviewer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.seg3525_project.pdfviewer.TableInfo.*;

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

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database operations", "Database created");

        /*addBook(new Book("nobody",
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.stats)).getBitmap(),
                "Essentials of Probability & Statistics for Engineers & Scientists",
                "Ronald E. Walpole",
                "0-321-78373-5",
                "",
                "https://firdaramadhena.files.wordpress.com/2014/01/ronald_e_walpole__et_al_essentials_of_probability__statistics_for_engineers__scientists__2013.pdf"));

        addBook(new Book("nobody",
                ((BitmapDrawable) context.getResources().getDrawable(R.drawable.computer_science)).getBitmap(),
                "Computer Science - The Hardware, Software and Heart of It",
                "Edward K. Blum & Alfred V. Aho",
                "78-1-4614-1167-3",
                "",
                "http://publik.tuwien.ac.at/files/PubDat_201302.pdf"));*/

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
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(UserInfo.FULL_NAME, user.getFullName());
        contentValues.put(UserInfo.EMAIL, user.getEmail());
        contentValues.put(UserInfo.PASSWORD, user.getPassword());

        sqLiteDatabase.insert(UserInfo.TABLE_NAME, null, contentValues);
        Log.d("Database operations", "User row inserted");
    }

    public Cursor getUsers() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {
                UserInfo.FULL_NAME,
                UserInfo.EMAIL,
                UserInfo.PASSWORD
        };

        return sqLiteDatabase.query(UserInfo.TABLE_NAME, columns, null, null, null, null, null);
    }

    public int updateUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserInfo.FULL_NAME, user.getFullName());
        contentValues.put(UserInfo.EMAIL, user.getEmail());
        contentValues.put(UserInfo.PASSWORD, user.getPassword());

        return sqLiteDatabase.update(UserInfo.TABLE_NAME, contentValues, UserInfo.EMAIL + " = ?",
                new String[]{String.valueOf(user.getEmail())});
    }

    public void addBook(Book book) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
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

        sqLiteDatabase.insert(BookInfo.TABLE_NAME, null, contentValues);
        Log.d("Database operations", "Book row inserted");
    }

    public int deleteBook(/*long id*/) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(BookInfo.TABLE_NAME, null/*BookInfo.ID + " = " + id*/, null);
    }

    public Cursor getBooks() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {
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

        return sqLiteDatabase.query(BookInfo.TABLE_NAME, columns, null, null, null, null, null);
    }

    public Cursor getBook(long id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {
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
        return sqLiteDatabase.query(BookInfo.TABLE_NAME, columns, BookInfo.ID + "=" + id, null, null, null, null);
    }

    public void updateBook(Book book) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
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

        sqLiteDatabase.update(BookInfo.TABLE_NAME, contentValues, BookInfo.ID + " = " + book.getId(), null);
        Log.d("Database operations", "Book row updated");
    }
}
