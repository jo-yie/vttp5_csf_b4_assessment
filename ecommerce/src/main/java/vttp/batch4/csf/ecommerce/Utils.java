package vttp.batch4.csf.ecommerce;


import java.io.StringReader;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.batch4.csf.ecommerce.models.Cart;
import vttp.batch4.csf.ecommerce.models.LineItem;
import vttp.batch4.csf.ecommerce.models.Order;
import vttp.batch4.csf.ecommerce.models.Product;

public class Utils {

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  public static Product toProduct(Document doc) {
    Product product = new Product();
    product.setId(doc.getObjectId("_id").toHexString());
    product.setName(doc.getString("ProductName"));
    product.setBrand(doc.getString("Brand"));
    product.setPrice(doc.getDouble("Price").floatValue());
    product.setDiscountPrice(doc.getDouble("DiscountPrice").floatValue());
    product.setImage(doc.getString("Image_Url"));
    product.setQuantity(doc.getString("Quantity"));
    return product;
  }

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  public static JsonObject toJson(Product product) {
    return Json.createObjectBuilder()
      .add("prodId", product.getId())
      .add("name", product.getName())
      .add("brand", product.getBrand())
      .add("price", product.getPrice())
      .add("discountPrice", product.getDiscountPrice())
      .add("image", product.getImage())
      .add("quantity", product.getQuantity())
      .build();
  }

  // json string to order POJO 
  public static Order jsonToOrderPojo(String jsonString) {

    StringReader sr = new StringReader(jsonString); 
    JsonReader jr = Json.createReader(sr);
    JsonObject jo = jr.readObject();

    // map fields to order POJO 
    Order order = new Order(); 
    order.setName(jo.getString("name"));
    order.setAddress(jo.getString("address"));
    order.setPriority(jo.getBoolean("priority"));
    order.setComments(jo.getString("comments"));

    // cart POJO 
    JsonObject cartTemp = jo.getJsonObject("cart"); 
    JsonArray lineItemsTemp = cartTemp.getJsonArray("lineItems");

    Cart cart = new Cart(); 

    for (JsonValue jv: lineItemsTemp) {

      JsonObject temp = jv.asJsonObject();

      LineItem lineItem = new LineItem();
      lineItem.setProductId(temp.getString("prodId"));
      lineItem.setQuantity(temp.getInt("quantity"));
      lineItem.setName(temp.getString("name"));
      lineItem.setPrice((float) temp.getJsonNumber("price").doubleValue());

      cart.addLineItem(lineItem);
    }

    order.setCart(cart);

    return order;
  }

}


//   // json string to Order POJO 
//   private Order jsonToOrderPojo(String jsonString) {

//     StringReader sr = new StringReader(jsonString); 
//     JsonReader jr = Json.createReader(sr);
//     JsonObject jo = jr.readObject();

//     // map JSON fields to Order POJO 

//     Cart cart = new Cart(); 
    

//     Order order = new Order(); 
//     order.setName(jo.getString("name"));
//     order.setAddress(jo.getString("address"));
//     order.setPriority(jo.getBoolean("priority"));
//     order.setComments(jo.getString("comments"));
//     order.setCart(null);
//     return null;
//   }
// }

// {
//   "name":"sadds",
//   "address":"adsasdasd",
//   "priority":false,
//   "comments":"",
//   "cart": {"lineItems":
//     [{"prodId":"67d7c0e103d9b8a4583f59ed","quantity":1,"name":"Deodorant Body Spray - Be Delicious Woman EDT","price":5550},{"prodId":"67d7c0e103d9b8a4583f59ed","quantity":1,"name":"Deodorant Body Spray - Be Delicious Woman EDT","price":5550},{"prodId":"67d7c0e103d9b8a4583f59ed","quantity":1,"name":"Deodorant Body Spray - Be Delicious Woman EDT","price":5550}]
//   }
// }

  //   // helper method to parse JSON string into a BoardGame POJO 
  //   private BoardGame jsonStringToBoardGame(String jString) {

  //     StringReader sr = new StringReader(jString);
  //     JsonReader jr = Json.createReader(sr);
  //     JsonObject jo = jr.readObject();

  //     // map JSON fields to a BoardGame POJO 

  //     BoardGame boardGame = new BoardGame(
  //         jo.getInt("gid"),
  //         jo.getString("name"),
  //         jo.getInt("year"));

  //     return boardGame;

  // }