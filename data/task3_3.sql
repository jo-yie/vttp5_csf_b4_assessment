-- TODO Task 3

-- drop database if exits 
DROP DATABASE IF EXISTS csf_b4_assessment; 

-- create database 
CREATE DATABASE csf_b4_assessment; 

-- use database 
USE csf_b4_assessment;

-- create orders 
CREATE TABLE orders (
    order_id VARCHAR(26), -- primary key
    date DATE,
    name VARCHAR(128),
    address VARCHAR(128),
    priority BOOLEAN, 
    comments VARCHAR(256),
    CONSTRAINT pk_order_id PRIMARY KEY (order_id)
);

-- create line items 
CREATE TABLE line_items (
    product_id VARCHAR
)

--   private final String orderId;
--   private Date date = new Date();
--   private String name;
--   private String address;
--   private boolean priority;
--   private String comments;
--   private Cart cart = new Cart();

-- cart
--   private List<LineItem> lineItems = new LinkedList<>();

-- line item
--   private String productId;
--   private String name;
--   private int quantity;
--   private float price;

-- create table reservations
CREATE TABLE reservations (
    resv_id VARCHAR(8),
    name VARCHAR(128), 
    email VARCHAR(128), 
    acc_id VARCHAR(10), -- foreign key 
    arrival_date DATE, 
    duration INT, 
    CONSTRAINT pk_resv_id PRIMARY KEY (resv_id),
    CONSTRAINT fk_acc_id 
        FOREIGN KEY (acc_id)
        REFERENCES acc_occupancy(acc_id)
);