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
    product_id VARCHAR(24), -- primary key
    name VARCHAR(128), 
    quantity INT, 
    price DECIMAL, 
    order_id VARCHAR(26), -- foreign key
    CONSTRAINT pk_product_id PRIMARY KEY (product_id),
    CONSTRAINT fk_order_id 
        FOREIGN KEY (order_id)
        REFERENCES orders(order_id)
);