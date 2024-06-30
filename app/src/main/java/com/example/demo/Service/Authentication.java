package com.example.demo.Service;
import com.example.demo.Common;
import com.example.demo.Model.CustomerAccount;
import org.springframework.stereotype.Service;
@Service
public class Authentication{
    public Boolean signUp(CustomerAccount customerAccount) {
        try {
            if(Common.customers.get(customerAccount.getEmail()) != null){
                return false;
            }Common.customers.put(customerAccount.getEmail(), customerAccount);
        }catch (Exception e){
            System.out.println("Exception in signUp as "+e.getMessage());
            return false;
        }
        return true;
    }
    public CustomerAccount logIn(String email, String password) {
        try{
            for(CustomerAccount customer : Common.customers.values()){
                if(customer.getEmail().equals(email) && customer.getPassword().equals(password)){
                    return customer;
                }
            }
        }catch (Exception e){
            System.out.println("Exception in logIn as " + e.getMessage());
        }return null;
    }
}
