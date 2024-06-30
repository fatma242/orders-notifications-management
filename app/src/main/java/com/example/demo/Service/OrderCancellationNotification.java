package com.example.demo.Service;

import com.example.demo.Model.CustomerAccount;
import com.example.demo.Model.Notification;
import com.example.demo.Model.Order;
import com.example.demo.Model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderCancellationNotification extends Notification {
    @Override
    public void send(CustomerAccount customerAccount, List<Product> products) {
        StringBuilder notification = new StringBuilder();
        notification.append("Dear ").append(customerAccount.getUserName()).append(", your cancellation of the item ");
        for (Product product : products){
            notification.append(product.getName()).append(" ");
        }
        notification.append("is confirmed, Thanks for using our store :)");
        NotificationQueue.enqueueNotification(notification.toString());
    }
}
