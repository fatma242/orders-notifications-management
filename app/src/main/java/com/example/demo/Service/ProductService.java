package com.example.demo.Service;
import java.util.Set;
import com.example.demo.Common;
import com.example.demo.Model.Product;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ProductService {
    public Boolean addProduct(Product product){
        try{
            if(Common.products.get(product.getSerialNumber()) != null){
                return false;
            }
            Common.products.put(product.getSerialNumber(), product);
        }catch (Exception e){
            System.out.println("Exception in addProduct as "+ e.getMessage());
            return false;
        }
        return true;
    }
    public Boolean deleteProduct(String serialNumber){
        try{
            if(Common.products.get(serialNumber) == null){
                return false;
            }
            Common.products.remove(serialNumber);
        }catch (Exception e){
            System.out.println("Exception in deletePerson as "+ e.getMessage());
            return false;
        }return true;
    }
    public Product getProduct(String serialNumber){
        try {
            return Common.products.get(serialNumber);
        }catch (Exception e){
            System.out.println("Exception in getPerson as "+ e.getMessage());
        }
        return null;
    }
    public Product[] getAllProducts(){
        try{
            Set<String> serialNumbers = Common.products.keySet();
            Product[] products = new Product[serialNumbers.size()];
            int i=0;
            for(String serialNumber : serialNumbers){
                products[i] = Common.products.get(serialNumber);
                i++;
            }
            return products;
        }catch (Exception e){
            System.out.println("Exception in getAllProducts as "+ e.getMessage());
        }
        return null;
    }


}
