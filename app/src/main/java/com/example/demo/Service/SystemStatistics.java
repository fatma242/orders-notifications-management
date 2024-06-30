package com.example.demo.Service;
import com.example.demo.Model.CustomerAccount;
import com.example.demo.Model.Notification;
import org.springframework.stereotype.Service;

@Service
public class SystemStatistics {
    public String mostSentTemplate(){
        try {
            StringBuilder message = new StringBuilder();
            int maxtemp= Notification.maxtemp();
            if(maxtemp !=-1){
                message.append("The most sent notification template is ");
                if(maxtemp==0){
                message.append("Dear {x} , your booking of item {y} is confirmed. thanks for using our store :)");

                }else if(maxtemp==1){
                    message.append("Dear {x} , your shipment of item {y} is confirmed. thanks for using our store :)");

                }else if(maxtemp==2){
                    message.append("Dear {x} , your cancellation of item {y} is confirmed. thanks for using our store :)");

                }else if(maxtemp==3){
                    message.append("Dear {x} , your cancellation of the shipment of item {y} is confirmed. thanks for using our store :)");

            } return  message.toString();}
            else {
                return  "No existing notification yet";}
        }catch (Exception e){
            System.out.println("Exception in mostSentTemplate as "+ e.getMessage());
        }
        return null;
    }
    public String mostNotified(){
        try {
            CustomerAccount MaxAccount = Notification.maxAccount();
            StringBuilder message = new StringBuilder();
            if(MaxAccount != null){
            message.append("The most notified email-address: ");
            message.append(MaxAccount.getEmail());
            message.append(" phone-number: ");
            message.append(MaxAccount.getPhoneNumber());
            return message.toString();
            }
            else {return "There are no notifications yet to determine the most notified account";}

        }catch (Exception e){
            System.out.println("Exception in mostnotified as "+ e.getMessage());
        }
        return null;

    }


}
