package com.example.millipixelsinteractive_031.em.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by millipixelsinteractive_024 on 22/01/18.
 */

public class ExpenseManagerDatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "expense";

    // Product table name
    public static final String TABLE_EXPENSE_CATEGORY = "expense_category";
    public static final String TABLE_ALL_EXPENSES = "all_expense";

    // TABLE_EXPENSE_CATEGORY Columns names
    public static final String _ID = "_id";
    public static final String KEY_CATEGORY_NAME = "category_name";
    public static final String KEY_CATEGORY_COLOR = "category_color";



    // TABLE_EXPENSE_CATEGORY Columns names

    public static final String KEY_CATEGORY_ID = "category_id";
    public static final String KEY_EXPENSE_CATEGORY_NAME = "category_name";
    public static final String KEY_EXPENSE_NAME = "expense_name";
    public static final String KEY_EXPENSE_AMOUNT = "expense_amount";
    public static final String KEY_EXPENSE_DATE = "expense_date";
    public static final String KEY_EXPENSE_DATE_MILLI = "expense_date_milli";
    public static final String KEY_EXPENSE_NOTE = "expense_note";
    public static final String KEY_EXPENSE_IMAGE = "expense_image";




    public static String column_index=KEY_CATEGORY_ID + "," + KEY_EXPENSE_CATEGORY_NAME + "," + KEY_EXPENSE_AMOUNT + "," + KEY_EXPENSE_DATE+  "," + KEY_EXPENSE_DATE_MILLI+  "," + KEY_EXPENSE_NOTE;

    public ExpenseManagerDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXPENSE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_EXPENSE_CATEGORY + "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CATEGORY_NAME + " TEXT," + KEY_CATEGORY_COLOR + " TEXT" + ")";

        db.execSQL(CREATE_EXPENSE_CATEGORY_TABLE);

        String CREATE_ALL_EXPENSE_TABLE = "CREATE TABLE " + TABLE_ALL_EXPENSES + "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CATEGORY_ID + " INTEGER," + KEY_EXPENSE_CATEGORY_NAME + " TEXT,"+ KEY_EXPENSE_NAME + " TEXT," + KEY_EXPENSE_AMOUNT + " TEXT," +  KEY_EXPENSE_DATE + " TEXT,"+  KEY_EXPENSE_DATE_MILLI + " INTEGER," + KEY_EXPENSE_NOTE + " TEXT,"+ KEY_EXPENSE_IMAGE + " TEXT" + ")";

        db.execSQL(CREATE_ALL_EXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_EXPENSES);
        // Create tables again
        onCreate(db);
    }
}
