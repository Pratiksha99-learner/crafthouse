package com.mystore.crafthouse.model;

public class BestForYou {
    String name;
    String rating;
    String time;
    String currency;
    Integer price;
    Integer imageURL;

    public BestForYou(String name, String rating, String time, String currency, Integer price, Integer imageURL) {
        this.name = name;
        this.rating = rating;
        this.time = time;
        this.currency = currency;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getImageURL() {
        return imageURL;
    }

    public void setImageURL(Integer imageURL) {
        this.imageURL = imageURL;
    }
}
