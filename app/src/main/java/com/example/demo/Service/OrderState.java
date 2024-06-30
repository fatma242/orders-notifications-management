package com.example.demo.Service;


import ch.qos.logback.core.util.TimeUtil;
import com.example.demo.Common;
import com.example.demo.Model.CustomerAccount;
import com.example.demo.Model.Notification;
import com.example.demo.Model.Order;
import com.example.demo.Model.Product;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class OrderState {
    private boolean orderState = false;
    private OrderList orderList = OrderList.getInstant();

    public boolean OrderState() {
        return orderState;
    }

    public void OrderStatePlacement(String username,int orderID){
        CustomerAccount account = new CustomerAccount();
        Order order;
        account = account.getAccount(username);
        if(orderList.ReturnOrder(username,orderID).getOrderState()){
            return;
        }else{
            order =  orderList.ReturnOrder(username,orderID);
            order.setOrderState(true);
            for(Product product : order.getProducts()){
                for (Map.Entry<String, Product> entry : Common.products.entrySet()){
                    if(product == entry.getValue()){
                        entry.getValue().setQuantity(product.getQuantity()-1);

                    }
                }
            }
            orderState = true;
            account.setBalance(account.getBalance() - orderList.ReturnOrder(username,orderID).getOrderPrice());
        }
    }
    public void OrderStateCancellation(String username, int orderID){
        CustomerAccount account = new CustomerAccount();
        Order order;
        account = account.getAccount(username);
        order =  orderList.ReturnOrder(username,orderID);
        Instant cancelTime = Instant.now();
        Duration duration = Duration.between(order.getTime(),cancelTime);
        if(duration.toSeconds() > 30 ){
            return;
        }else{
            double temp =0;
            temp = order.getOrderPrice();
            System.out.println(order.getProducts().size());
            System.out.println(Common.products.size());
            for(Product product : order.getProducts()){
                System.out.println(product.getName());
                for (Map.Entry<String, Product> entry : Common.products.entrySet()){
                    System.out.println(entry.getValue());
                    System.out.println(product);
                    if(product == entry.getValue()){
                        entry.getValue().setQuantity(product.getQuantity()+1);
                        order.setPrice(order.getOrderPrice()-product.getPrice());
                    }
                }
            }
            order.getProducts().clear();
            orderState = false;
            account.setBalance(account.getBalance() + temp);
            Notification notification = new OrderCancellationNotification();
            orderList.ReturnOrder(username,orderID).setNotification(notification);
            notification.send(account, orderList.ReturnOrder(username,orderID).getProducts());
            notification.setTempNum(2);
            notification.setAccountNum(account);
        }
    }
}