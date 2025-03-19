package vttp.batch4.csf.ecommerce.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch4.csf.ecommerce.models.Order;
import vttp.batch4.csf.ecommerce.services.PurchaseOrderService;

@Controller
public class OrderController {

  @Autowired
  private PurchaseOrderService poSvc;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  @PostMapping("/api/order")
  @ResponseBody
  public ResponseEntity<String> postOrder(@RequestBody Order order) {

    // TODO Task 3
    System.out.println("called POST /api/order");
    System.out.println(">>>NAME:" + order.getName());
    System.out.println(">>>ID: " + order.getOrderId());
    System.out.println(">>>CART: " + order.getCart());
    
    // json string response 
    JsonObject jo = Json.createObjectBuilder()
      .add("Success", order.getOrderId() + " added")
      .build();

	  return ResponseEntity.ok().body(jo.toString());
  }
}
