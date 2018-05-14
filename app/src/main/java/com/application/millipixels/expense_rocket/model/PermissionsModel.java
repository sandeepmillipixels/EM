package com.application.millipixels.expense_rocket.model;


public class PermissionsModel {


    int imagePath;
    String permissionDesc;
    String permissionTitle;

    public PermissionsModel(int imagePath,String permissionDesc,String permissionTitle){
        this.imagePath  = imagePath;
        this.permissionDesc  = permissionDesc;
        this.permissionTitle  = permissionTitle;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    public String getPermissionTitle() {
        return permissionTitle;
    }

    public void setPermissionTitle(String permissionTitle) {
        this.permissionTitle = permissionTitle;
    }
}
