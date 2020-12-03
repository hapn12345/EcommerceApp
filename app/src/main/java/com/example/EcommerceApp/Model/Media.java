package com.example.EcommerceApp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Media {
    public Media(String id, String url, String alt) {
        this.id = id;
        this.url = url;
        this.alt = alt;
    }

    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("alt")
    @Expose
    String alt;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
