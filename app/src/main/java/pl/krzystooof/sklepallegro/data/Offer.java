package pl.krzystooof.sklepallegro.data;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class Offer {
    String id;
    String name;
    String thumbnailUrl;
    Price price;
    String description​;

    public Offer(String id, String name, String thumbnailUrl, Price price, String description​) {
        this.id = id;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.price = price;
        this.description​ = description​;
    }

    public Offer() {
        id = "";
        name = "";
        thumbnailUrl = "";
        price = new Price();
        description​ = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getDescription​() {
        return description​;
    }

    public void setDescription​(String description​) {
        this.description​ = description​;
    }
}

class Price {
    double amount;
    String currency;

    public Price(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Price() {
        amount = 0;
        currency = "";
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}