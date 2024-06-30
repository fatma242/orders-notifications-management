package com.example.demo.Service;
import com.example.demo.Model.CustomerAccount;
import com.example.demo.Model.Notification;
import com.example.demo.Model.Order;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
@Service
public class OrderShipmentState {
    private boolean orderShipmentState = false;
    private OrderList orderList = OrderList.getInstant();
    private Instant startshipmentTime;

    public boolean OrderShipmentState() {
        return orderShipmentState;
    }
    public void OrderShipmentStatePlacement(String username,int orderID){
        CustomerAccount account = new CustomerAccount();
        account = account.getAccount(username);
        if(orderList.ReturnOrder(username,orderID).getOrderShipmentState()){
            return;
        }else{
            orderList.ReturnOrder(username,orderID).setOrderShipmentState(true);
            orderShipmentState = true;
            account.setBalance(account.getBalance() - orderList.ReturnOrder(username,orderID).getShipping());
            Notification notification = new OrderShipmentNotification();
            orderList.ReturnOrder(username,orderID).setNotification(notification);
            notification.send(account, orderList.ReturnOrder(username,orderID).getProducts());
            notification.setTempNum(1);
            notification.setAccountNum(account);
            startshipmentTime = Instant.now();
            orderList.ReturnOrder(username, orderID).setStartShipmentTime(startshipmentTime);
        }
    }
    public void OrderShipmentStateCancellation(String username, int orderID){
        CustomerAccount account = new CustomerAccount();
        account = account.getAccount(username);
        Order order;
        order =  orderList.ReturnOrder(username,orderID);
        Instant cancelTime  = Instant.now();
        Duration duration = Duration.between(order.getStartShipmentTime(),cancelTime);
        if(duration.toSeconds() > 30 ){
            return;
        }else{
            System.out.println("ijdf");
            order.setOrderShipmentState(false);
            orderShipmentState = false;
            account.setBalance(account.getBalance() + order.getShipping());
            Notification notification = new OrderCancellationNotification();
            orderList.ReturnOrder(username,orderID).setNotification(notification);
            notification.send(account, orderList.ReturnOrder(username,orderID).getProducts());
            notification.setTempNum(3);
            notification.setAccountNum(account);
        }
    }
}
