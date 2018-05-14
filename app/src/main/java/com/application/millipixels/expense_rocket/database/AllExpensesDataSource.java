package com.application.millipixels.expense_rocket.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;


import com.application.millipixels.expense_rocket.model.AllExpenses;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by millipixelsinteractive_024 on 23/01/18.
 */

public class AllExpensesDataSource {
    private SQLiteDatabase database;
    private ExpenseManagerDatabaseHandler dbHelper;
    private String[] allColumns = {ExpenseManagerDatabaseHandler._ID, ExpenseManagerDatabaseHandler.KEY_CATEGORY_ID, ExpenseManagerDatabaseHandler.KEY_EXPENSE_CATEGORY_NAME,ExpenseManagerDatabaseHandler.KEY_EXPENSE_NAME, ExpenseManagerDatabaseHandler.KEY_EXPENSE_AMOUNT, ExpenseManagerDatabaseHandler.KEY_EXPENSE_DATE, ExpenseManagerDatabaseHandler.KEY_EXPENSE_DATE_MILLI, ExpenseManagerDatabaseHandler.KEY_EXPENSE_NOTE, ExpenseManagerDatabaseHandler.KEY_EXPENSE_IMAGE};

    public AllExpensesDataSource(Context context) {
        dbHelper = new ExpenseManagerDatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createExpense(AllExpenses expense) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_CATEGORY_ID, expense.getCategory_id());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_EXPENSE_CATEGORY_NAME, expense.getCategory_name());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_EXPENSE_NAME, expense.getExpense_name());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_EXPENSE_AMOUNT, expense.getExpense_amount());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_EXPENSE_DATE, expense.getExpense_date());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_EXPENSE_DATE_MILLI, expense.getExpense_date_milli());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_EXPENSE_NOTE, expense.getExpense_note());
        contentValues.put(ExpenseManagerDatabaseHandler.KEY_EXPENSE_IMAGE, expense.getExpense_image());
        long insertId = database.insert(ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES, null, contentValues);
        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES,
                allColumns, ExpenseManagerDatabaseHandler._ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        AllExpenses Expenses1 = cursorToExpensesModal(cursor);
        Log.e("", "" + Expenses1);
    }


    public ArrayList<AllExpenses> getAllExpenses() {
        ArrayList<AllExpenses> arrayList = new ArrayList<AllExpenses>();
        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES,
                allColumns,null, null,
                null, null, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            AllExpenses Expenses1 = cursorToExpensesModal(cursor);
            arrayList.add(Expenses1);
        }
        return arrayList;
    }

    public ArrayList<AllExpenses> getAllExpensesbyCategoryId(long id,long startDate, long endDate) {
        ArrayList<AllExpenses> arrayList = new ArrayList<AllExpenses>();
        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES,
                allColumns,ExpenseManagerDatabaseHandler.KEY_EXPENSE_DATE_MILLI + " BETWEEN " +startDate +  " AND " + endDate + " AND " + ExpenseManagerDatabaseHandler.KEY_CATEGORY_ID + " = " + id , null,
                null, null, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            AllExpenses Expenses1 = cursorToExpensesModal(cursor);
            arrayList.add(Expenses1);
        }
        return arrayList;
    }

    public ArrayList<AllExpenses> getAllExpensesbyCategoryId_TODAY(long id,String date) {
        ArrayList<AllExpenses> arrayList = new ArrayList<AllExpenses>();
        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES,
                allColumns,ExpenseManagerDatabaseHandler.KEY_CATEGORY_ID + " = " + id , null,
                null, null, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            AllExpenses Expenses1 = cursorToExpensesModal(cursor);
            arrayList.add(Expenses1);
        }
        return arrayList;
    }

    public float getSum(String category){
        database = dbHelper.getReadableDatabase();
        Cursor cur = database.rawQuery("SELECT SUM(" + ExpenseManagerDatabaseHandler.KEY_EXPENSE_AMOUNT + ") FROM " + ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES + " WHERE " + ExpenseManagerDatabaseHandler.KEY_EXPENSE_CATEGORY_NAME + " = " + "'" + category + "'" , null );
        if(cur.moveToFirst())
        {
            return cur.getInt(0);
        }else {
            return 0;
        }
    }
    public float getSumAllExpenses(){
        Cursor cur = database.rawQuery("SELECT SUM(" + ExpenseManagerDatabaseHandler.KEY_EXPENSE_AMOUNT + ") FROM " + ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES , null );
        if(cur.moveToFirst())
        {
            return cur.getInt(0);
        }else {
            return 0;
        }
    }
    public float getSum(String category,long startDate, long endDate){
        Cursor cur = database.rawQuery("SELECT SUM(" + ExpenseManagerDatabaseHandler.KEY_EXPENSE_AMOUNT + ") FROM " + ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES + " WHERE " + ExpenseManagerDatabaseHandler.KEY_EXPENSE_DATE_MILLI + " BETWEEN " +startDate +  " AND " + endDate + " AND " +ExpenseManagerDatabaseHandler.KEY_EXPENSE_CATEGORY_NAME + " = " + "'" + category + "'", null );
//        Cursor cur = database.rawQuery("SELECT SUM(" + ExpenseManagerDatabaseHandler.KEY_EXPENSE_AMOUNT + ") FROM " + ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES + " WHERE " + ExpenseManagerDatabaseHandler.KEY_EXPENSE_CATEGORY_NAME + " = " + "'" + category + "'" + " AND " +ExpenseManagerDatabaseHandler.KEY_EXPENSE_DATE + " BETWEEN "+ "'" +startDate + "'"+ " AND " + "'" + endDate + "'", null );
        if(cur.moveToFirst())
        {
            return cur.getInt(0);
        }else {
            return 0;
        }
    }

//    public ArrayList<AllExpenses> getAllRecentExpenses(String user_id, String category_type) {
//        ArrayList<AllExpenses> arrayList = new ArrayList<AllExpenses>();
//        String orderBy = ExpenseManagerDatabaseHandler.KEY_ID + " DESC";
//        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES,
//                allColumns, ExpenseManagerDatabaseHandler.KEY_USER_ID + " = " + "'" + user_id + "'" + " AND " + ExpenseManagerDatabaseHandler.KEY_CATEGORY + " = " + "'" + category_type + "'", null,
//                null, null, orderBy, "20");
////        cursor.moveToFirst();
//        while (cursor.moveToNext()) {
//            AllExpenses Expenses1 = cursorToExpensesModal(cursor);
//            arrayList.add(Expenses1);
//        }
//        return arrayList;
//    }


    //    public AllExpenses getExpenseCategory(String user_id, String category_type, String category_id) {
//        boolean isExist = false;
//        AllExpenses localExpenses = new AllExpenses();
//        Cursor cursor = database.query(ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES,
//                allColumns, ExpenseManagerDatabaseHandler.KEY_USER_ID + " = " + "'" + user_id + "'" + " AND " + ExpenseManagerDatabaseHandler.KEY_CATEGORY + " = " + "'" + category_type + "'"
//                        + " AND " + ExpenseManagerDatabaseHandler.KEY_PRODUCT_ID + " = " + "'" + category_id + "'", null,
//                null, null, null);
//        if (cursor != null && cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            localExpenses = cursorToExpensesModal(cursor);
//        }
//        return localExpenses;
//    }
    public boolean update(long _id, AllExpenses expense) {
        ContentValues contentValues = new ContentValues();
//        contentValues.put(ExpenseManagerDatabaseHandler.KEY_CATEGORY_NAME, expense.getCatName());
//        contentValues.put(ExpenseManagerDatabaseHandler.KEY_CATEGORY_COLOR, expense.getColorCode());
        return database.update(ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES, contentValues, ExpenseManagerDatabaseHandler._ID + " = " +  _id  , null) > 0;
    }

    public boolean deleteCategory(long _id) {
        return database.delete(ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES,
                ExpenseManagerDatabaseHandler._ID + " = " + " + _id + "
                , null) > 0;
    }
    public boolean deleteAll() {
        return database.delete(ExpenseManagerDatabaseHandler.TABLE_ALL_EXPENSES,
                null
                , null) > 0;
    }

    private String cursorToExpensesIDStrings(Cursor cursor) {
        return cursor.getString(2);
    }

    private AllExpenses cursorToExpensesModal(Cursor cursor) {
        AllExpenses localExpenses = new AllExpenses();
        localExpenses.setId(cursor.getLong(0));
        localExpenses.setCategory_id(cursor.getLong(1));
        localExpenses.setCategory_name(cursor.getString(2));
        localExpenses.setExpense_name(cursor.getString(3));
        localExpenses.setExpense_amount(cursor.getString(4));
        localExpenses.setExpense_date(cursor.getString(5));
        localExpenses.setExpense_date_milli(cursor.getLong(6));
        localExpenses.setExpense_note(cursor.getString(7));
        localExpenses.setExpense_image(cursor.getString(8));
        return localExpenses;
    }
    public void exportData() {

        database = dbHelper.getReadableDatabase();
//        Cursor curCSV = database.rawQuery("SELECT * FROM " + dbHelper.TABLE_ALL_EXPENSES, null);
        Cursor curCSV = database.rawQuery("SELECT " + dbHelper.column_index + " FROM " + dbHelper.TABLE_ALL_EXPENSES, null);
        writeCsvFile(curCSV);

    }

    public void writeCsvFile(Cursor curCSV) {
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "expense.csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to exprort
                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2), curCSV.getString(3), curCSV.getString(4), curCSV.getString(5)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        } catch (Exception sqlEx) {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
    }
}
