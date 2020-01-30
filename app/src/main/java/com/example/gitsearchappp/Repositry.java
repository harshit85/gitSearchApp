package com.example.gitsearchappp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Repositry {
    private int total_count;
    private List<items> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<com.example.gitsearchappp.items> getItems() {
        return items;
    }

    public void setItems(List<com.example.gitsearchappp.items> items) {
        this.items = items;
    }
}


