package com.example.demo.Model;

import com.example.demo.Service.OrderState;
import com.example.demo.Service.OrderShipmentState;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.Timer;

abstract public class Order {
    Random random = new Random();
    protected OrderState Orderstate = new OrderState();
    private OrderShipmentState Ordershipmentstate = new OrderShipmentState();
    protected boolean orderState = Orderstate.OrderState();
    protected Instant startTime;
    protected Instant startShipmentTime;

    public Instant getStartShipmentTime() {
        return startShipmentTime;
    }

    public void setStartShipmentTime(Instant startShipmentTime) {
        this.startShipmentTime = startShipmentTime;
    }

    public Instant getTime() {
        return startTime;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    protected boolean orderShipmentState = Ordershipmentstate.OrderShipmentState();
    protected double Price;
    protected double Shipping = 50;
    protected List<Product> products;
    protected String username;
    protected Notification notification = null;
    protected int orderNumber  = random.nextInt(0,10000009);

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public boolean getOrderState() {
        return orderState;
    }
    public boolean getOrderShipmentState() {
        return orderShipmentState;
    }
    public void setOrderState(boolean t){orderState = t;}
    public void setOrderShipmentState(boolean t){orderShipmentState = t;}

    public List<Product> getProducts() {
        return products;
    }

    public int getOrderNumber() {
        return orderNumber;
    }
    public abstract List<Product> SelectProductsByNames(String username,List<String> productNames);
    public double getOrderPrice(){
        return Price;
    }
    public double getShipping()
    {
        return Shipping;
    }

}
