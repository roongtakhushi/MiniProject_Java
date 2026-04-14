# Snack Dealer Management System

A Java Swing desktop application to manage snack inventory.

## Features
- Add snacks with name, price, category and stock
- Delete snacks
- Update stock quantity
- View all snacks in a table

## Technologies Used
- Java Swing (GUI)
- MySQL (Database)
- JDBC (Database Connection)

## Database Setup
Run this SQL:
create database snackdb;
use snackdb;
create table snacks(
id int primary key auto_increment,
snack_name varchar(50),
price int,
category varchar(50),
stock_quantity int
);

## How to Run
javac -cp ".;mysql-connector-j-8.0.33.jar" java_mini_project/connect.java java_mini_project/SnackDealerManagement.java
java -cp ".;mysql-connector-j-8.0.33.jar" java_mini_project.SnackDealerManagement
