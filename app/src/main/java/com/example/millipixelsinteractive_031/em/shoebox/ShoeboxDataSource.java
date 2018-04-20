package com.example.millipixelsinteractive_031.em.shoebox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.application.millipixels.expense_rocket.database.ExpenseManagerDatabaseHandler;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by millipixelsinteractive_024 on 11/04/18.
 */

public class ShoeboxDataSource {
    private SQLiteDatabase database;
    private ExpenseManagerDatabaseHandler dbHelper;
    private String[] allColumns = {ExpenseManagerDatabaseHandler.SHOEBOX_ID, ExpenseManagerDatabaseHandler.KEY_SHOEBOX_NAME, ExpenseManagerDatabaseHandler.KEY_SHOEBOX_NAME, ExpenseManagerDatabaseHandler.KEY_SHOEBOX_DATE};

    public ShoeboxDataSource(Context context) {
        dbHelper = new ExpenseManagerDatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createCategory(ShoeBoxBean box) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_SHOEBOX_NAME, box.getName());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_SHOEBOX_TIME, box.getTime());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_SHOEBOX_DATE, box.getDate());
        long insertId = database.insert(ExpenseManagerDatabaseHandler.TABLE_SHOEBOX, null, contentValues);
        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_SHOEBOX,
                allColumns, ExpenseManagerDatabaseHandler._ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ShoeBoxBean Categories1 = cursorToCategoriesModal(cursor);
        Log.e("", "" + Categories1);
    }
    public ArrayList<ShoeBoxBean> getAllCategories() {
        ArrayList<ShoeBoxBean> arrayList = new ArrayList<ShoeBoxBean>();
        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_SHOEBOX,
                allColumns,null, null,
                null, null, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            ShoeBoxBean Categories1 = cursorToCategoriesModal(cursor);
            arrayList.add(Categories1);
        }
        return arrayList;
    }

    public boolean updateCategory(long _id, ShoeBoxBean box) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_SHOEBOX_NAME, box.getName());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_SHOEBOX_TIME, box.getTime());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_SHOEBOX_DATE, box.getDate());
        return database.update(ExpenseManagerDatabaseHandler.TABLE_SHOEBOX, contentValues, ExpenseManagerDatabaseHandler.SHOEBOX_ID + " = " +  _id  , null) > 0;
    }

    public boolean deleteCategory(long _id) {
        return database.delete(ExpenseManagerDatabaseHandler.TABLE_SHOEBOX,
                ExpenseManagerDatabaseHandler.SHOEBOX_ID + " = " + " + _id + "
                , null) > 0;
    }

    private String cursorToCategoriesIDStrings(Cursor cursor) {
        return cursor.getString(2);
    }

    private ShoeBoxBean cursorToCategoriesModal(Cursor cursor) {
        ShoeBoxBean localCategories = new ShoeBoxBean();
        localCategories.setId(cursor.getLong(0));
        localCategories.setName(cursor.getString(1));
        localCategories.setTime(cursor.getString(2));
        localCategories.setDate(cursor.getString(3));
        return localCategories;
    }
}

