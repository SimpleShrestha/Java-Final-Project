package org.example;

import org.example.models.Item;
import org.example.models.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String databaseUrl = "jdbc:sqlite:coffeeDatabase.db";
        Statement statement;

        String createOrderTable = "CREATE TABLE IF NOT EXISTS ORDERS ( orderId integer primary key autoincrement, customerName varchar(100), totalPrice int, items varchar(100) ) ";
        String createItemTable = "CREATE TABLE IF NOT EXISTS ITEMS ( itemId integer primary key autoincrement, itemName varchar(50), itemPrice int )";

        DatabaseHelper databaseHelper = new DatabaseHelper(databaseUrl);

        try {
            Connection connection = DriverManager.getConnection(databaseUrl);
            statement = connection.createStatement();
            statement.execute(createOrderTable);
            statement.execute(createItemTable);
        } catch (Exception e) {
            System.out.println("Cannot create tables");
        }


        System.out.println("Coffee Shop Manager");

        System.out.println("Pick an action:");
        System.out.println("1. Get items list");
        System.out.println("2. Get order list");
        System.out.println("3. Add new Order");
        System.out.println("4. Add new Item");

        int actionSelection = scanner.nextInt();

        switch (actionSelection) {
            case 1:
                // item list
                int index = 1;
                for (Item item : databaseHelper.getItems()){
                    System.out.println(index + ": " + item.getItemName() + " - $" + item.getPrice());
                    index++;
                }
                break;
            case 2:
                // order list
                databaseHelper.generateOrderList();
                break;
            case 3:
                // add order

                System.out.println("Enter the customer name: ");
                String customerName = scanner.next();
                boolean adding = true;
                List<Item> itemList = databaseHelper.getItems();
                List<Item> itemsToAdd = new ArrayList<>();
                while (adding){
                    int i = 1;
                    for (Item item : itemList){
                        System.out.println(i + ": " + item.getItemName() + " - $" + item.getPrice());
                        i++;
                    }
                    System.out.println("Enter the number to add");
                    int selection = scanner.nextInt();
                    itemsToAdd.add( itemList.get(selection - 1));
                    System.out.println("Press y to stop adding items and n to continue");
                    String continueAdding = scanner.next();
                    if (continueAdding.equals("y") ){
                        adding = false;
                    }
                }
                Order order = new Order(itemsToAdd, customerName);
                databaseHelper.addOrder(order);
                break;
            case 4:
                // add item
                System.out.println("Set name for new item: ");
                String itemName = scanner.next();
                System.out.println("Set price for new item: ");
                int itemPrice = scanner.nextInt();
                Item item = new Item(itemName, itemPrice);
                databaseHelper.addItem(item);
                break;
            default:
                System.out.println("Please pick the number from the list");
        }

    }


}