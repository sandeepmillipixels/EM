package com.application.millipixels.expense_rocket.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by millipixelsinteractive_024 on 10/05/18.
 */

public class ImageList implements Parcelable{
    String imagePath;
    boolean isSelected = false;
    public  ImageList(String imagePath, boolean isSelected){
        this. imagePath= imagePath;
        this. isSelected= isSelected;
    }

    protected ImageList(Parcel in) {
        imagePath = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<ImageList> CREATOR = new Creator<ImageList>() {
        @Override
        public ImageList createFromParcel(Parcel in) {
            return new ImageList(in);
        }

        @Override
        public ImageList[] newArray(int size) {
            return new ImageList[size];
        }
    };

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagePath);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}
