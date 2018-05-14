package com.application.millipixels.models;

/**
 * Created by millipixelsinteractive_024 on 10/05/18.
 */

public class PagingListModal {
    boolean isSelected = false ;
    public  PagingListModal(boolean isSelected){
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
