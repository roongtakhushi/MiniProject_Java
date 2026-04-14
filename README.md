# 🍿 Snack Dealer Management System

A Java Swing desktop application to manage snack inventory with MySQL database.

## 📸 Screenshot
(upload a screenshot of your app here)

## ✨ Features
- Add snacks with name, price, category and stock
- Delete snacks with confirmation
- Update stock quantity
- View all snacks in a table
- Total item counter

## 🛠️ Technologies Used
- Java Swing (GUI)
- MySQL 8.0 (Database)
- JDBC (Database Connection)

## ⚙️ Database Setup
```sql
create database snackdb;
use snackdb;
create table snacks(
id int primary key auto_increment,
snack_name varchar(50),
price int,
category varchar(50),
stock_quantity int
);
```

## ▶️ How to Run
```bash
javac -cp ".;mysql-connector-j-8.0.33.jar" java_mini_project/connect.java java_mini_project/SnackDealerManagement.java

java -cp ".;mysql-connector-j-8.0.33.jar" java_mini_project.SnackDealerManagement
```

## 👤 Author
Khushi Roongta
