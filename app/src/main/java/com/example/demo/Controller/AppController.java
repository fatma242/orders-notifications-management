package com.example.demo.Controller;
import com.example.demo.Model.*;
import com.example.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class AppController {
    @Autowired
    Authentication authentication;
    @Autowired
    ProductService productService;
    @Autowired
    NotificationQueue notificationQueue;
    @Autowired
    SimpleOrder simpleOrder;
    @Autowired
    OrderList orderList;
    @Autowired
    CompoundOrder compoundOrder;
    @Autowired
    OrderState orderState;
    @Autowired
    SystemStatistics systemStatistics;
    @Autowired
    OrderShipmentState orderShipmentState;

    @PostMapping("/signup")
    public Response signUp(@RequestBody CustomerAccount customerAccount){
        System.out.println("in add customer "+customerAccount);
        boolean res = authentication.signUp(customerAccount);
        Response response = new Response();
        if(!res){
            response.setStatus(false);
            response.setMessage("CustomerAccount Already Exists");
            return response;
        }
        response.setStatus(true);
        response.setMessage("CustomerAccount Created successfully");
        return response;
    }
    @GetMapping("/check/{email}/{password}")
    public ResponseEntity<ResponseWrapper<CustomerAccount>> logIn(@PathVariable("email")String email, @PathVariable("password")String password){
        CustomerAccount customerAccount = authentication.logIn(email,password);
        if (customerAccount != null){
            return new ResponseEntity<>(new ResponseWrapper<>(customerAccount, null), HttpStatus.OK);
        }else{String errorMessage = "Invalid email or password";
            return new ResponseEntity<>(new ResponseWrapper<>(null, errorMessage), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/addproduct")
    public Response addProduct(@RequestBody Product product){
        System.out.println("in add product "+product);
        boolean res = productService.addProduct(product);
        Response response = new Response();
        if(!res){
            response.setStatus(false);
            response.setMessage("Product Already Exists");
            return response;
        }
        response.setStatus(true);
        response.setMessage("Product Created successfully");
        return response;
    }
    @GetMapping("/getproduct/{serialnumber}")
    public ResponseEntity<ResponseWrapper<Product>> getProduct(@PathVariable("serialnumber")String serialnumber){
        Product product = productService.getProduct(serialnumber);
        if (product != null){
            return new ResponseEntity<>(new ResponseWrapper<>(product, null), HttpStatus.OK);
        }else{String errorMessage = "No such product exist";
            return new ResponseEntity<>(new ResponseWrapper<>(null, errorMessage), HttpStatus.UNAUTHORIZED);
        }
    }
    @DeleteMapping("/deleteproduct/{serialnumber}")
    public Response deleteProduct(@PathVariable("serialnumber")String serialnumber){
        System.out.println("in delete with serial number: "+serialnumber);
        boolean res = productService.deleteProduct(serialnumber);
        Response response = new Response();
        if(!res){
            response.setStatus(false);
            response.setMessage("Product Doesn't Exists");
            return response;
        }
        response.setStatus(true);
        response.setMessage("Product deleted successfully");
        return response;
    }
    @GetMapping("/getallproducts")
    public Product[] getAll(){
        System.out.println("in getALl");
        return productService.getAllProducts();
    }
    @PostMapping("/createsimpleorder")
    public Order selectProducts(@RequestBody Map<String, Object> requestBody) {
        simpleOrder = new SimpleOrder();
        String username = (String) requestBody.get("username");
        List<String> productNames = (List<String>) requestBody.get("names");
        simpleOrder.SelectProductsByNames(username, productNames);
        return simpleOrder;

    }


    @PostMapping("/createcompoundorder")
    public List<Order> createCompoundOrder(@RequestBody List<Map<String, Object>> requestBody) {
        compoundOrder = new CompoundOrder();
        for(Map<String, Object> entry : requestBody){
            String username = (String) entry.get("username");
            List<String> names = (List<String>) entry.get("names");
            compoundOrder.SelectProductsByNames(username, names);
        }
        return compoundOrder.GetChild();
    }

    @GetMapping("/returnorder/{username}/{orderNumber}")
    public Order returnOrder(@PathVariable("username") String username, @PathVariable("orderNumber") int orderNumber){
        return orderList.ReturnOrder(username,orderNumber);
    }
    @PostMapping("/orderstate-placement/{username}/{orderID}")
    public void OrderStatePlacement(@PathVariable("username") String username, @PathVariable("orderID") int orderID) {
        orderState.OrderStatePlacement(username,orderID);
    }
    @PostMapping("/orderstate-cancellation/{username}/{orderID}")
    public void OrderStateCancellation(@PathVariable("username") String username, @PathVariable("orderID") int orderID) {
        orderState.OrderStateCancellation(username,orderID);
    }
    @GetMapping("/getalltemplates")
    public String[] listqueue(){
        System.out.println("in getALl");
        return notificationQueue.listQueue();
    }
    @GetMapping("/mostsenttemplate")
    public String  mostsenttemplate(){
        return systemStatistics.mostSentTemplate();
    }
    @GetMapping("/mostnotified")
    public String mostnotified(){
        return systemStatistics.mostNotified();
    }
    @PostMapping("/ordershipment-cancellation/{username}/{orderID}")
    public void OrderShipmentCancellation(@PathVariable("username") String username, @PathVariable("orderID") int orderID) {
        orderShipmentState.OrderShipmentStateCancellation(username, orderID);
    }
    @PostMapping("/ordershipment-placement/{username}/{orderID}")
    public void OrderShipmentPlacement(@PathVariable("username") String username, @PathVariable("orderID") int orderID) {
        orderShipmentState.OrderShipmentStatePlacement(username, orderID);
    }

}
