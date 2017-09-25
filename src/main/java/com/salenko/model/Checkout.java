package com.salenko.model;

import java.util.*;

public class Checkout {

    private static Checkout instance;

    public List<Deal> deals = new ArrayList<Deal>();

    private String check = "";

    private Double checkPrice = 0d;

    private Checkout() {
    }

    public static Checkout getInstance() {
        if (instance == null) {
            instance = new Checkout();
        }
        return instance;
    }

    public String getCheck() {
        return this.check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Double getCheckPrice() {
        return this.checkPrice;
    }

    public void setCheckPrice(Double checkPrice) {
        this.checkPrice = checkPrice;
    }

    @Override
    public String toString() {
        String line = "";
        for (int i = 0; i < this.deals.size(); i++)
            line += "" + this.deals.get(i).getProduct().getName() + this.deals.get(i).getProductCount();
        return line;
    }

}
