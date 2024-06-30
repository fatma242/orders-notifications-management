package com.example.demo.Model;
import java.util.*;

public abstract class Notification {
    private static int[] tempNum = new int[4];  //the placement temp is in index 0, shipment 1, order cancellation 2 and shipment cancellation 3
    public static Map<CustomerAccount, Integer> accountNum = new HashMap<>();

    public void setAccountNum(CustomerAccount account){
        if(!accountNum.containsKey(account)){
            accountNum.put(account,0);
        }else{
            accountNum.put(account,accountNum.get(account)+1);
        }

    }
    public static CustomerAccount maxAccount(){
        int max=Integer.MIN_VALUE;
        CustomerAccount accountWithMaxValue = null;
        for(Map.Entry<CustomerAccount, Integer> entry : accountNum.entrySet()){
            if(entry.getValue()>max){
                max = entry.getValue();
                accountWithMaxValue = entry.getKey();
            }
        }
        return accountWithMaxValue;
    }
    public void setTempNum(int index){
        tempNum[index]++;
    }
    public static int maxtemp(){
        int max=0;
        int index=-1;
        for(int i=0;i<4;i++){
            if(tempNum[i]>max){
                max=tempNum[i];
                index=i;
            }
        }
        return index;
    }
    public abstract void send(CustomerAccount customerAccount, List<Product> selectedProducts);


}
