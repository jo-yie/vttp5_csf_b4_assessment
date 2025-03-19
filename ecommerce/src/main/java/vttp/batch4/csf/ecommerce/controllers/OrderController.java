package vttp.batch4.csf.ecommerce.controllers;


import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch4.csf.ecommerce.Utils;
import vttp.batch4.csf.ecommerce.models.Cart;
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
  public ResponseEntity<String> postOrder(@RequestBody String jsonString) {

    System.out.println(jsonString.toString());

    // call utils
    Order order = Utils.jsonToOrderPojo(jsonString);

    // check order is formatted correctly
    System.out.println(">>>Order: " + order);

    try {
      poSvc.createNewPurchaseOrder(order);

      // json string response
      JsonObject jo = Json.createObjectBuilder()
        .add("orderId", order.getOrderId())
        .build();

  return ResponseEntity.status(200).body(jo.toString());

    } catch (Exception e) {
      JsonObject errorMessage = Json.createObjectBuilder()
        .add("message", e.getMessage())
        .build();

      return ResponseEntity.status(400)
        .body(errorMessage.toString());

    }

  }

}