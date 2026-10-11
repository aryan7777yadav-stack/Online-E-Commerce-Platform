# Online E-Commerce Platform - Review 1 Presentation Content
### GUVI | HCL Hackathon

Copy this into PowerPoint / Canva / Google Slides (12 slides). Add screenshots after running the GUI.

---

#### Slide 1: Title
**Online E-Commerce Platform**
GUVI HCL Hackathon - Review 1
Team: [Your Team Name] | abhishek.25scse1010473@galgotiasuniversity.ac.in
Date: 15 October 2026

#### Slide 2: Problem Statement & Objective
Goal: Develop an e-commerce platform where sellers can list products, buyers can purchase, and administrators manage the system. Each user type has a dedicated dashboard.
Key Objectives: User Management, Product Management, Order Processing, Inventory Tracking, Secure Role-based Access.

#### Slide 3: User Roles & Functionalities
Diagram: 3 columns
- Admin: Manages Users/Products/Orders, System Activity Monitoring
- Seller: Lists Products, Inventory Management, Order Processing, Sales Reports
- Buyer: Browses Products, Purchases, Order Tracking, Wishlist, Account Overview

#### Slide 4: System Architecture (3-Tier)
```
Presentation (Swing GUI - Login + 3 Dashboards)
        ↓
Business Logic (AuthService, InventoryService)
        ↓
Data Access (DAO Interfaces + Impl via JDBC PreparedStatement)
        ↓
Database (MySQL - ecommerce_db)
```
Tech Stack: Java 26, Swing, JDBC, MySQL 8.0, Maven/VS Code

#### Slide 5: Module Breakdown
Table from plan: Show packages com.ecommerce.model / dao / dao.impl / service / util / ui

#### Slide 6: OOP Concepts Implemented
- Inheritance: User (abstract) → Admin, Seller, Buyer
- Abstraction: Abstract methods getDashboardType(), displayInfo()
- Polymorphism: UserDAO interface → UserDAOImpl, ProductDAO → ProductDAOImpl
- Encapsulation: Private fields + getters/setters
- Exception Handling: try-with-resources, SQLException handling

#### Slide 7: Database Design - ER Diagram
Screenshot placeholder: Paste MySQL Workbench ER Diagram
Tables: users, products, orders, order_items, wishlist, system_activity
Relationships: users 1-N products, users 1-N orders, orders 1-N order_items

#### Slide 8: JDBC Connectivity
Code snippet: DBConnection.java (Singleton, DriverManager.getConnection)
DAO with PreparedStatement: ps.setString(1, user.getName())
Screenshot: Console "MySQL Driver Loaded" + "Database Connected"

#### Slide 9: Collections, Generics & Multithreading
- Collections: List<Product>, Map<Product,Integer> Cart, ConcurrentHashMap inventory
- Generics: <T extends Product> void addProduct(T p), Optional<User>
- Streams: filter low stock, groupByCategory
- Multithreading: ExecutorService(4 threads) for order processing, SwingWorker for DB test, synchronized updateStock()

#### Slide 10: UI/UX Design - Screenshots
Paste 4 screenshots:
1. Login Panel 2. Admin Dashboard (4 tabs) 3. Seller Dashboard (4 tabs) 4. Buyer Dashboard (4 tabs)
Highlight: Responsive, Bootstrap-like colors, Role-based navigation

#### Slide 11: Project Structure & GitHub
Screenshot: VS Code explorer + bin/ + lib/mysql-connector-j.jar
GitHub Repo: [paste your link] | Structure: src/ database/schema.sql docs/
How to Run: Run schema.sql → Add lib jar → Run ECommerceGUI.main()

#### Slide 12: Work Done & Next Plan
Review 1 DONE: Project structure, DB design, JDBC DAOs, Swing UI for all 3 roles
Review 2 PLAN: Full CRUD integration, Payment flow, JFreeChart graphs, Session persistence, Validation

---
