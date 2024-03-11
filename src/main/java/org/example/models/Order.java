package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Order {

    List<Item> items = new ArrayList<>();
    String customerName;


    public Order(List<Item> items, String customerName) {
        this.items = items;
        this.customerName = customerName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getPrice() {
        int totalPrice = 0;
        for (Item item : this.items){
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }



}
