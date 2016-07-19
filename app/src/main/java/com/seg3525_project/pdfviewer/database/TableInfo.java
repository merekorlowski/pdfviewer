package com.seg3525_project.pdfviewer.database;

import android.provider.BaseColumns;

/**
 * Created by merek on 13/07/16.
 */
public class TableInfo {

    public TableInfo() {}

    public static abstract class UserInfo implements BaseColumns {
        public static final String FULL_NAME = "full_name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String TABLE_NAME = "user_data";
        public static final int FULL_NAME_COLUMN_NUMBER = 0;
        public static final int EMAIL_COLUMN_NUMBER = 1;
        public static final int PASSWORD_COLUMN_NUMBER = 2;
    }

    public static abstract class BookInfo implements BaseColumns {
        public static final String ID = "id";
        public static final String BORROWER = "borrower";
        public static final String IMAGE = "image";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String ISBN = "ISBN";
        public static final String DESCRIPTION = "description";
        public static final String PDF = "pdf";
        public static final String EXPIRY_DATE = "expiry_date";
        public static final String TABLE_NAME = "book_data";
        public static final int ID_COLUMN_NUMBER = 0;
        public static final int BORROWER_COLUMN_NUMBER = 1;
        public static final int IMAGE_COLUMN_NUMBER = 2;
        public static final int TITLE_COLUMN_NUMBER = 3;
        public static final int AUTHOR_COLUMN_NUMBER = 4;
        public static final int ISBN_COLUMN_NUMBER = 5;
        public static final int DESCRIPTION_COLUMN_NUMBER = 6;
        public static final int PDF_COLUMN_NUMBER = 7;
        public static final int EXPIRY_DATE_COLUMN_NUMBER = 8;
    }

}
