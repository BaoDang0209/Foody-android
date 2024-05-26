package com.example.foody_android;

import com.example.foody_android.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    private static OrderManager instance;
    private List<Order> orderList;

    private OrderManager() {
        orderList = new ArrayList<>();
    }

    public static synchronized OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public void removeOrder(Order order) {
        orderList.remove(order);
    }

    public List<Order> getOrderList() {
        return orderList;
    }
    public String printOrders() {
        StringBuilder ordersString = new StringBuilder("Current Orders:\n");
        for (Order order : orderList) {
            ordersString.append(order.toString()).append("\n");
        }
        return ordersString.toString();
    }
}

