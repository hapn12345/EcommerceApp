package com.example.EcommerceApp.Model;

public class Category {
    private String categoryID;
    private String category_name;

    public Category(String categoryID, String category_name) {
        this.categoryID = categoryID;
        this.category_name = category_name;
    }


    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }


}
