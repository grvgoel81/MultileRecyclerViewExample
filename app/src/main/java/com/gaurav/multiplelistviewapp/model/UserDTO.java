package com.gaurav.multiplelistviewapp.model;

import java.util.ArrayList;

/**
 * Created by Gaurav on 11/15/2017.
 */

public class UserDTO {

    private String name;
    private String image;
    private ArrayList<String> items;

    public UserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }
}
