package vttp.batch4.csf.ecommerce.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch4.csf.ecommerce.models.LineItem;
import vttp.batch4.csf.ecommerce.models.Order;

@Repository
public class PurchaseOrderRepository {

  @Autowired
  private JdbcTemplate template;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  // You may only add Exception to the method's signature
  @Transactional
  public void create(Order order) {
    // TODO Task 3

    String SQL_INSERT_ORDER = 
    """
      INSERT INTO orders (order_id, date, name, address, priority, comments)
        VALUES (?, ?, ?, ?, ?, ?)    
    """;

    String SQL_INSERT_LINE_ITEM = 
    """
      INSERT INTO line_items (product_id, name, quantity, price, order_id)
        VALUES (?, ?, ?, ?, ?) 
    """;

    template.update(SQL_INSERT_ORDER, 
      order.getOrderId(),
      order.getDate(),
      order.getName(),
      order.getAddress(), 
      order.getPriority(), 
      order.getComments());

    List<LineItem> lineItems = order.getCart().getLineItems();

    for (LineItem li: lineItems) {
      template.update(SQL_INSERT_LINE_ITEM,
        li.getProductId(), 
        li.getName(), 
        li.getQuantity(), 
        li.getPrice(), 
        order.getOrderId());
    }

  }
}
