package pl.krzystooof.sklepallegro.data;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class Offer {
    private String id;
    private String name;
    private String thumbnailUrl;
    private Price price;
    private String description​;

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

