package com.example.demo.Service;

import ch.qos.logback.core.joran.sanity.Pair;
import com.example.demo.Common;
import com.example.demo.Model.CustomerAccount;
import com.example.demo.Model.Order;
import com.example.demo.Model.Product;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Service
public class CompoundOrder extends Order {

    private List<Order> orders = new ArrayList<>();
    private OrderList orderList = OrderList.getInstant();
    @Override
    public List<Product> SelectProductsByNames(String usernames,List<String> productNames){
        Order simpleOrder = new SimpleOrder();
        double temp =0;
        try {
            simpleOrder.setUsername(usernames);
            CustomerAccount account = new CustomerAccount();
            account = account.getAccount(usernames);
            products = new ArrayList<>();
            for (String name : productNames) {
                for (Product product : Common.products.values()) {
                        if (product.getName().equalsIgnoreCase(name)) {
                            products.add(product);
                            temp = temp + product.getPrice();
                        }
                    }
            }
            simpleOrder.setProducts(products);
            simpleOrder.setPrice(temp);
            this.Price+=temp;
            System.out.println(this.Price);
            orderList.addOrder(account,simpleOrder);
            orders.add(simpleOrder);
            Orderstate.OrderStatePlacement(simpleOrder.getUsername(),simpleOrder.getOrderNumber());
            notification = new OrderPlacementNotification();
            notification.send(account, products);
            notification.setTempNum(0);
            notification.setAccountNum(account);
            startTime = Instant.now();
            return simpleOrder.getProducts();
        }catch (Exception e){
            System.out.println("Exception in selectProduct as "+e.getMessage());
        }
        return null;
    }

    public void removeOrder(Order order){
        orders.remove(order);
    }
    public void addChild(Order order){
        orders.add(order);
    }
    public List<Order> GetChild(){
        return orders;
    }
}
