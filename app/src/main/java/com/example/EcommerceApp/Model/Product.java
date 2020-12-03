package com.example.EcommerceApp.Model;
import java.lang.reflect.Method;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("categoryID")
    @Expose
    private int categoryID;
    @SerializedName("name")
    @Expose
    private String name;

    public void setMedia(Media media) {
        this.media = media;
    }

    @SerializedName("media")
    @Expose
    private Media media;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    public Product(int id, int categoryID, String name, Media media, String description, String price) {
        this.id = id;
        this.categoryID = categoryID;
        this.name = name;
        this.media = media;
        this.description = description;
        this.price = price;
    }

    public Product( String name, Media media, String price) {
        this.name = name;
        this.media = media;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Media getMedia() {
        return media;
    }

    public void getMedia(Media media) {
        this.media = media;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
