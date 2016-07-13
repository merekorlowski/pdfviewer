package com.seg3525_project.pdfviewer;

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
    }

    public static abstract class BookInfo implements BaseColumns {
        public static final String ID = "id";
        public static final String BORROWER = "borrower";
        public static final String IMAGE = "image";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String ISBN = "ISBN";
        public static final String EXPIRY_DATE = "expiry_date";
        public static final String TABLE_NAME = "book_data";
    }

}
