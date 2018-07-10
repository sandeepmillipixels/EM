package com.example.millipixelsinteractive_031.em.model;

/**
 * Created by millipixelsinteractive_024 on 23/01/18.
 */

public class AllExpenses {
    long id;
    long category_id;
    String category_name;
    String expense_name;
    String expense_amount;
    String expense_date;
    long expense_date_milli;
    String expense_note;
    String expense_image = "image.jpg";

    public String getExpense_image() {
        return expense_image;
    }

    public void setExpense_image(String expense_image) {
        this.expense_image = expense_image;
    }

    public long getExpense_date_milli() {
        return expense_date_milli;
    }

    public void setExpense_date_milli(long expense_date_milli) {
        this.expense_date_milli = expense_date_milli;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getExpense_amount() {
        return expense_amount;
    }

    public void setExpense_amount(String expense_amount) {
        this.expense_amount = expense_amount;
    }

    public String getExpense_date() {
        return expense_date;
    }

    public void setExpense_date(String expense_date) {
        this.expense_date = expense_date;
    }

    public String getExpense_note() {
        return expense_note;
    }

    public String getExpense_name() {
        return expense_name;
    }

    public void setExpense_name(String expense_name) {
        this.expense_name = expense_name;
    }

    public void setExpense_note(String expense_note) {
        this.expense_note = expense_note;
    }
}
