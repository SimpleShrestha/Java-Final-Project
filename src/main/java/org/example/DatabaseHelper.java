package org.example;

import org.example.models.Item;
import org.example.models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    Connection connection;

    public DatabaseHelper(String url) {
        try {
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println("Cannot connect to database");
        }
    }

    public void addItem(Item item) {
        int price = item.getPrice();
        String name = item.getItemName();

        String addQuery = " INSERT INTO ITEMS(itemName, itemPrice) VALUES ( ?, ? ) ";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(addQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            preparedStatement.execute();
        } catch (Exception e) {
            System.out.println("Error while adding item");
            e.printStackTrace();
        }
    }


    public void addOrder(Order order) {
        String itemList = "";
        for (Item item : order.getItems()) {
            itemList += item.getItemName() + ", ";
        }
        try {
            String addOrderQuery = "INSERT INTO ORDERS (customerName, totalPrice, items ) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(addOrderQuery);
            preparedStatement.setString(1, order.getCustomerName());
            preparedStatement.setInt(2, order.getPrice());
            preparedStatement.setString(3, itemList);
            preparedStatement.execute();
        } catch (Exception e) {
            System.out.println("Error while uploading order");
        }
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            String getQuery = "SELECT * FROM ITEMS";
            ResultSet rs = statement.executeQuery(getQuery);
            while (rs.next()) {
                String itemName = rs.getString("itemName");
                int itemPrice = rs.getInt("itemPrice");
                Item item = new Item(itemName, itemPrice);
                items.add(item);
            }
        } catch (Exception e) {
            System.out.println("Error while retrieving items");
        }
        return items;
    }

    public void generateOrderList(){
        try {
            Statement statement = connection.createStatement();
            String getQuery = "SELECT * FROM ORDERS";
            ResultSet rs = statement.executeQuery(getQuery);

            while(rs.next()){
                int orderId = rs.getInt("orderId");
                String customerName = rs.getString("customerName");
                int totalPrice = rs.getInt("totalPrice");
                String items = rs.getString("items");
                System.out.println(orderId + ": " + customerName + " ordered - " + items + " - for $" + totalPrice);
            }

        }catch (Exception e){
            System.out.println("Error while getting orderList");
        }
    }

}
