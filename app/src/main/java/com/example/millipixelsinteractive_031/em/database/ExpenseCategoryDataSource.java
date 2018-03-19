package com.example.millipixelsinteractive_031.em.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.millipixelsinteractive_031.em.model.ExpenseCategory;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by millipixelsinteractive_024 on 22/01/18.
 */

public class ExpenseCategoryDataSource {
    private SQLiteDatabase database;
    private ExpenseManagerDatabaseHandler dbHelper;
    private String[] allColumns = {ExpenseManagerDatabaseHandler._ID, ExpenseManagerDatabaseHandler.KEY_CATEGORY_NAME, ExpenseManagerDatabaseHandler.KEY_CATEGORY_COLOR};

    public ExpenseCategoryDataSource(Context context) {
        dbHelper = new ExpenseManagerDatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createCategory(ExpenseCategory category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_CATEGORY_NAME, category.getCatName());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_CATEGORY_COLOR, category.getColorCode());
        long insertId = database.insert(ExpenseManagerDatabaseHandler.TABLE_EXPENSE_CATEGORY, null, contentValues);
        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_EXPENSE_CATEGORY,
                allColumns, ExpenseManagerDatabaseHandler._ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ExpenseCategory Categories1 = cursorToCategoriesModal(cursor);
        Log.e("", "" + Categories1);
    }
    public ArrayList<ExpenseCategory> getAllCategories(String catName) {
        ArrayList<ExpenseCategory> arrayList = new ArrayList<ExpenseCategory>();
        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_EXPENSE_CATEGORY,
                allColumns,ExpenseManagerDatabaseHandler.KEY_CATEGORY_NAME + " = " + "'" + catName +"'" + "COLLATE NOCASE", null,
                null, null, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            ExpenseCategory Categories1 = cursorToCategoriesModal(cursor);
            arrayList.add(Categories1);
        }
        return arrayList;
    }
    public ArrayList<ExpenseCategory> getAllCategories() {
        ArrayList<ExpenseCategory> arrayList = new ArrayList<ExpenseCategory>();
        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_EXPENSE_CATEGORY,
                allColumns,null, null,
                null, null, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            ExpenseCategory Categories1 = cursorToCategoriesModal(cursor);
            arrayList.add(Categories1);
        }
        return arrayList;
    }
    public ArrayList<ExpenseCategory> getAllCategories(String startDate, String endDate) {
        ArrayList<ExpenseCategory> arrayList = new ArrayList<ExpenseCategory>();
        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_EXPENSE_CATEGORY,
                allColumns,null, null,
                null, null, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            ExpenseCategory Categories1 = cursorToCategoriesModal(cursor);
            arrayList.add(Categories1);
        }
        return arrayList;
    }

//    public ArrayList<ExpenseCategory> getAllRecentCategories(String user_id, String category_type) {
//        ArrayList<ExpenseCategory> arrayList = new ArrayList<ExpenseCategory>();
//        String orderBy = ExpenseManagerDatabaseHandler.KEY_ID + " DESC";
//        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_EXPENSE_CATEGORY,
//                allColumns, ExpenseManagerDatabaseHandler.KEY_USER_ID + " = " + "'" + user_id + "'" + " AND " + ExpenseManagerDatabaseHandler.KEY_CATEGORY + " = " + "'" + category_type + "'", null,
//                null, null, orderBy, "20");
////        cursor.moveToFirst();
//        while (cursor.moveToNext()) {
//            ExpenseCategory Categories1 = cursorToCategoriesModal(cursor);
//            arrayList.add(Categories1);
//        }
//        return arrayList;
//    }


//    public ExpenseCategory getExpenseCategory(String user_id, String category_type, String category_id) {
//        boolean isExist = false;
//        ExpenseCategory localCategories = new ExpenseCategory();
//        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_EXPENSE_CATEGORY,
//                allColumns, ExpenseManagerDatabaseHandler.KEY_USER_ID + " = " + "'" + user_id + "'" + " AND " + ExpenseManagerDatabaseHandler.KEY_CATEGORY + " = " + "'" + category_type + "'"
//                        + " AND " + ExpenseManagerDatabaseHandler.KEY_PRODUCT_ID + " = " + "'" + category_id + "'", null,
//                null, null, null);
//        if (cursor != null && cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            localCategories = cursorToCategoriesModal(cursor);
//        }
//        return localCategories;
//    }
    public boolean updateCategory(long _id, ExpenseCategory category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_CATEGORY_NAME, category.getCatName());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_CATEGORY_COLOR, category.getColorCode());
        return database.update(ExpenseManagerDatabaseHandler.TABLE_EXPENSE_CATEGORY, contentValues, ExpenseManagerDatabaseHandler._ID + " = " +  _id  , null) > 0;
    }

    public boolean deleteCategory(long _id) {
        return database.delete(ExpenseManagerDatabaseHandler.TABLE_EXPENSE_CATEGORY,
                ExpenseManagerDatabaseHandler._ID + " = " + " + _id + "
                , null) > 0;
    }

    private String cursorToCategoriesIDStrings(Cursor cursor) {
        return cursor.getString(2);
    }

    private ExpenseCategory cursorToCategoriesModal(Cursor cursor) {
        ExpenseCategory localCategories = new ExpenseCategory();
        localCategories.setId(cursor.getLong(0));
        localCategories.setCatName(cursor.getString(1));
        localCategories.setColorCode(cursor.getString(2));
        return localCategories;
    }
}
