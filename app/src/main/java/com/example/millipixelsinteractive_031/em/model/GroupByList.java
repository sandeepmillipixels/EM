package com.example.millipixelsinteractive_031.em.model;

/**
 * Created by millipixelsinteractive_024 on 25/01/18.
 */

public class GroupByList {
    String name = "";
    boolean isSeleted = false;
    public GroupByList(String name, boolean isSeleted){
        this.name = name;
        this.isSeleted = isSeleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }
}
