package com.example.millipixelsinteractive_031.em.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by millipixelsinteractive_024 on 22/01/18.
 */

public class ExpenseCategory implements Parcelable{
    long id;
    String colorCode;
    String catName;
    boolean isSelected = false;
    float amount = 0;

    protected ExpenseCategory(Parcel in) {
        id = in.readLong();
        colorCode = in.readString();
        catName = in.readString();
        isSelected = in.readByte() != 0;
        amount = in.readFloat();
    }

    public static final Creator<ExpenseCategory> CREATOR = new Creator<ExpenseCategory>() {
        @Override
        public ExpenseCategory createFromParcel(Parcel in) {
            return new ExpenseCategory(in);
        }

        @Override
        public ExpenseCategory[] newArray(int size) {
            return new ExpenseCategory[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ExpenseCategory(String catName, String colorCode){
        this.catName = catName;
        this.colorCode = colorCode;
    }

    public ExpenseCategory(){

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(colorCode);
        dest.writeString(catName);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeFloat(amount);
    }
}
