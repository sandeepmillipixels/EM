//package com.example.millipixelsinteractive_031.em.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.example.millipixelsinteractive_031.em.model.Data;
//
//import java.io.File;
//import java.util.ArrayList;
//
///**
// * Created by millipixelsinteractive_031 on 22/01/18.
// */
//
//public class SqliteDatabaseClass extends SQLiteOpenHelper {
//
//    /////////////Expense Table////////////////////////////////////
//
//    public static final String dbNAME = "Expense.db";
//    public static final String Table_Name = "Expenses";
//    public static final String id = "id";
//    public static final String Exp_amount = "Amount";
//    public static final String Exp_date = "Date";
//    public static final String Exp_note = "Note";
//    public static final String Exp_category = "Category";
//    public static final String Exp_Slips_Img = "Slips";
//
//
//    /////////////End of Expense Table////////////////////////
//
//    Context context;
//
//    File file = null;
//
//    int totalExpense;
//
//    String columsIndex = Exp_amount + "," + Exp_date + "," + Exp_note + "," + Exp_category;
//
//    String color;
//
//
//    public SqliteDatabaseClass(Context context) {
//        super(context, dbNAME, null, 1);
//
//        this.context = context;
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//        //////////////Create Expenses Table////////////////////////////////////////
//        db.execSQL("create table " + Table_Name + " (id integer primary key, " + Exp_amount + " integer,  " + Exp_date + " integer, " + Exp_category + " text, " + Exp_note + " text ,"+ Exp_Slips_Img + " text)");
//
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
//        onCreate(db);
//    }
//
//
//    public void insertRecords(String amount, String category, String note, String date,String image) {
//
//
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Exp_amount, amount);
//        contentValues.put(Exp_date, date);
//        contentValues.put(Exp_note, note);
//        contentValues.put(Exp_category, category);
//        contentValues.put(Exp_Slips_Img, image);
//
//        db.insert(Table_Name, null, contentValues);
//
//
//    }
//
//
//    public ArrayList<Data> getAllRecords() {
//
//        ArrayList<Data> arrayList = new ArrayList<>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from " + Table_Name , null);
//
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                do {
//                    String amount = cursor.getString(cursor.getColumnIndex(Exp_amount));
//                    String date = cursor.getString(cursor.getColumnIndex(Exp_date));
//                    String note = cursor.getString(cursor.getColumnIndex(Exp_note));
//                    String dbcategory = cursor.getString(cursor.getColumnIndex(Exp_category));
//
//                    String image = cursor.getString(cursor.getColumnIndex(Exp_Slips_Img));
//
//                    Data data=new Data();
//
//                    data.setAmount(amount);
//                    data.setCategory(dbcategory);
//                    data.setDate(date);
//                    data.setNotes(note);
//                    data.setImage(image);
//
//                    arrayList.add(data);
//
//
//                } while (cursor.moveToNext());
//            }
//        }
//
//        return arrayList;
//    }
//
//
//    public ArrayList<String> getListByCategory() {
//
//
//        ArrayList<String> arrayList = new ArrayList<>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("select " + Exp_category + " from " + Table_Name + " GROUP BY " + Exp_category, null);
//
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//
//                do {
//                    String categoryName = cursor.getString(cursor.getColumnIndex(Exp_category));
//                    arrayList.add(categoryName);
//
//                } while (cursor.moveToNext());
//            }
//
//        }
//
//        return arrayList;
//    }
//
//
//
//
//
//}