package org.example.models;

public class Item {
    String itemName;
    int price;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Item(String itemName, int price) {
        this.itemName = itemName;
        this.price = price;
    }
}
