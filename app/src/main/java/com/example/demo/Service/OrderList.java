package com.example.demo.Service;

import com.example.demo.Common;
import com.example.demo.Model.CustomerAccount;
import com.example.demo.Model.Order;
import com.example.demo.Model.Product;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderList {
    private static OrderList orderList = null;

    private OrderList() {}

    public static OrderList getInstant() {
        if (orderList == null) {
            orderList = new OrderList();
        }
        return orderList;
    }

    private static  Map<CustomerAccount,List<Order>> orders = new HashMap<>();

    public static Map<CustomerAccount, List<Order>> getOrders() {
        try {
            Set<String> customerAccounts = Common.customers.keySet();
            Map<CustomerAccount, List<Order>> orders = new HashMap<>();

            for (String customerAccount : customerAccounts) {
                List<Order> customerOrders = OrderList.orders.get(customerAccount);

                // If the customer has orders, add them to the map
                if (customerOrders != null) {
                    CustomerAccount account = Common.customers.get(customerAccount);
                    orders.put(account, customerOrders);
                }
            }

            return orders;
        } catch (Exception e) {
            System.out.println("Exception in getOrders: " + e.getMessage());
        }
        return null;
    }


    public void addOrder(CustomerAccount account, Order order) {
        if (!orders.containsKey(account)) {
            orders.put(account, new LinkedList<>());
        }
        List<Order> newList = orders.get(account);
        newList.add(order);
        orders.put(account,newList);
    }

    public Order ReturnOrder (String username,int orderId) {
        CustomerAccount account = new CustomerAccount();
        account = account.getAccount(username);
        for (Map.Entry<CustomerAccount, List<Order>> entry : orders.entrySet()) {
            if (entry.getKey().equals(account) && entry.getKey() != null) {
                for(int i = 0 ;i < entry.getValue().size();i++) {
                    if (entry.getValue().get(i).getOrderNumber() == orderId) {
                        return entry.getValue().get(i);
                    }
                }
            }
        }
        return null;
    }
}
