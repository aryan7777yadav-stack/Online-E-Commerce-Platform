# Online E-Commerce Platform - GUVI HCL Hackathon

> Java Swing + JDBC + MySQL | Review 1 Submission | Deadline: 15 October 2026

## Summary
E-commerce platform where **Sellers** list products, **Buyers** purchase, and **Administrators** manage users, products, and orders. Each role has a dedicated dashboard.

## Features (As per specification)
- **Admin:** User Management, Product Management, Order Management, System Activity Monitoring
- **Seller:** Product Listing, Inventory Management, Order Processing, Inventory Overview (graphical), Sales Performance (reports/graphs)
- **Buyer:** Product Browsing, Purchase Products, Order Tracking, Browsing History, Order History, Wishlist, Account Overview

## Tech Stack
- Java 17+ (tested on Java 26), Swing (GUI), JDBC, MySQL 8.0, Maven/VS Code

## Project Structure
```
src/com/ecommerce/
  model/       - User (abstract), Admin, Seller, Buyer, Product, Order, Role
  dao/         - UserDAO, ProductDAO, OrderDAO (interfaces)
  dao/impl/    - UserDAOImpl, ProductDAOImpl, OrderDAOImpl (JDBC + PreparedStatement)
  service/     - AuthService, InventoryService (Collections, Generics, Multithreading)
  util/        - DBConnection (Singleton)
  ui/          - ECommerceGUI (Swing - all 3 dashboards)
database/
  schema.sql   - DDL + dummy data
docs/
  Review1_Presentation.md - Slide content for PPT
lib/
  mysql-connector-j-8.0.33.jar
```

## Database Setup
1. Install MySQL 8.0 and create database:
   ```sql
   mysql -u root -p < database/schema.sql
   -- Or open database/schema.sql in MySQL Workbench and execute
   ```
2. Update credentials in `src/com/ecommerce/util/DBConnection.java` if needed (default: root/root)

## How to Run
### VS Code
1. Open folder in VS Code. Dependencies are auto-resolved via `.vscode/settings.json` (`lib/**/*.jar`)
2. Run `src/com/ecommerce/ui/ECommerceGUI.java` → `Run`

### Command Line
```bash
javac -cp "lib/mysql-connector-j-8.0.33.jar" -d bin -sourcepath src src/com/ecommerce/model/*.java src/com/ecommerce/util/*.java src/com/ecommerce/dao/*.java src/com/ecommerce/dao/impl/*.java src/com/ecommerce/service/*.java src/com/ecommerce/ui/*.java
java -cp "bin;lib/mysql-connector-j-8.0.33.jar" com.ecommerce.ui.ECommerceGUI
```

## Demo Credentials (works without MySQL - in-memory fallback)
| Role | Email | Password |
|------|-------|----------|
| Admin | admin@ecommerce.com | admin123 |
| Seller | rahul@seller.com | seller123 |
| Buyer | amit@buyer.com | buyer123 |

Additional DB users: priya@seller.com / seller123, neha@buyer.com / buyer123

## Rubric Coverage
- OOP (Inheritance, Polymorphism, Interfaces, Exception Handling) ✓
- Collections & Generics (List, Map, Optional, Streams, Generic methods) ✓
- Multithreading & Synchronization (ExecutorService, synchronized, SwingWorker, ConcurrentHashMap) ✓
- Database Operations (DAO classes) ✓
- JDBC (DriverManager, PreparedStatement, ResultSet) ✓

## Team
- abhishek.25scse1010473@galgotiasuniversity.ac.in

## License
© 2024 GUVI Geek Network Pvt. Ltd.
