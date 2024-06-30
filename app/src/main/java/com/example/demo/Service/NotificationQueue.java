package com.example.demo.Service;
import com.example.demo.Common;
import com.example.demo.Model.Notification;
import com.example.demo.Model.NotificationWrapper;
import com.example.demo.Model.Product;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
@Service

public class NotificationQueue {
    private static final Queue<NotificationWrapper> queue = new LinkedList<>();
    private static final long cleanup_interval = TimeUnit.MINUTES.toMinutes(5);
    public static void enqueueNotification(String notification){

        queue.add(new NotificationWrapper(notification,System.currentTimeMillis()));
        checkAndCleanup();
    }

    private static void checkAndCleanup() {
        long currentTime = System.currentTimeMillis();
        Iterator<NotificationWrapper> iterator = queue.iterator();
        while (iterator.hasNext()){
            NotificationWrapper wrapper = iterator.next();
            if(currentTime - wrapper.getTimestamp() >= cleanup_interval){
                iterator.remove();
            }else{
                break;
            }
        }
    }

    public String dequeueNotification(){
        checkAndCleanup();
        return queue.poll().getNotification();
    }
    public String[] listQueue(){
        try{
            String [] q = new String[queue.size()];
            int i=0;
            for(NotificationWrapper wrapper : queue){
                q[i] = wrapper.getNotification();
                i++;
            }
            return q;
        }catch (Exception e){
            System.out.println("Exception in listQueue as "+ e.getMessage());
        }
        return null;
    }
}
