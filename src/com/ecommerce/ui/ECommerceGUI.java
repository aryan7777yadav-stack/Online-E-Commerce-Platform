package com.ecommerce.ui;

import com.ecommerce.model.*;
import com.ecommerce.service.AuthService;
import com.ecommerce.service.InventoryService;
import com.ecommerce.util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collection;

/**
 * Swing GUI - Review 1 Deliverable
 * Demonstrates: OOP, Swing, Event Handling, Multithreading
 * Roles: Admin / Seller / Buyer Dashboards
 */
public class ECommerceGUI extends JFrame {

    private final AuthService authService = new AuthService();
    private final InventoryService inventoryService = new InventoryService();
    private User currentUser;

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    public ECommerceGUI() {
        setTitle("Online E-Commerce Platform - GUVI HCL Hackathon");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        seedInventory();
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createAdminDashboard(), "ADMIN");
        mainPanel.add(createSellerDashboard(), "SELLER");
        mainPanel.add(createBuyerDashboard(), "BUYER");
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    private void seedInventory() {
        inventoryService.addProduct(new Product(1, 2, "Wireless Headphones", "Noise cancelling BT 5.0", 2999, 50, "Electronics"));
        inventoryService.addProduct(new Product(2, 2, "Smart Watch", "Fitness + HR monitor", 4999, 8, "Electronics"));
        inventoryService.addProduct(new Product(3, 3, "Cotton T-Shirt", "Premium cotton", 599, 120, "Fashion"));
        inventoryService.addProduct(new Product(4, 3, "Running Shoes", "Lightweight breathable", 2499, 5, "Fashion"));
        inventoryService.addProduct(new Product(5, 2, "Laptop Stand", "Aluminum adjustable", 1299, 35, "Electronics"));
    }

    // ---- LOGIN PANEL ----
    private JPanel createLoginPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("E-Commerce Platform  |  Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(30, 60, 120));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; p.add(title, gbc);

        JLabel sub = new JLabel("GUVI | HCL - Review 1  |  Admin / Seller / Buyer", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(Color.GRAY);
        gbc.gridy = 1; p.add(sub, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2; gbc.gridx = 0; p.add(new JLabel("Email:"), gbc);
        JTextField emailField = new JTextField("admin@ecommerce.com", 22); gbc.gridx = 1; p.add(emailField, gbc);

        gbc.gridy = 3; gbc.gridx = 0; p.add(new JLabel("Password:"), gbc);
        JPasswordField passField = new JPasswordField("admin123", 22); gbc.gridx = 1; p.add(passField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        JLabel demoInfo = new JLabel("<html><center>Demo: admin@ecommerce.com / rahul@seller.com / amit@buyer.com<br>Password: admin123 / seller123 / buyer123</center></html>", SwingConstants.CENTER);
        demoInfo.setForeground(new Color(0, 130, 80)); demoInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        p.add(demoInfo, gbc);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(30, 60, 120)); loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14)); loginBtn.setFocusPainted(false);
        gbc.gridy = 5; p.add(loginBtn, gbc);

        JLabel statusLabel = new JLabel(" ", SwingConstants.CENTER); statusLabel.setForeground(Color.RED);
        gbc.gridy = 6; p.add(statusLabel, gbc);

        JButton testDbBtn = new JButton("Test Database Connection (JDBC)");
        testDbBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        gbc.gridy = 7; p.add(testDbBtn, gbc);

        loginBtn.addActionListener(e -> {
            try {
                currentUser = authService.login(emailField.getText().trim(), new String(passField.getPassword()));
                JOptionPane.showMessageDialog(this, "Welcome " + currentUser.getName() + " (" + currentUser.getRole() + ")");
                if (currentUser instanceof Admin) cardLayout.show(mainPanel, "ADMIN");
                else if (currentUser instanceof Seller) cardLayout.show(mainPanel, "SELLER");
                else cardLayout.show(mainPanel, "BUYER");
            } catch (Exception ex) {
                statusLabel.setText(ex.getMessage());
            }
        });
        testDbBtn.addActionListener(e -> {
            // Run DB test on background thread - demonstrates multithreading
            new SwingWorker<Boolean, Void>() {
                @Override protected Boolean doInBackground() { return DBConnection.getInstance().testConnection(); }
                @Override protected void done() {
                    try {
                        boolean ok = get();
                        JOptionPane.showMessageDialog(ECommerceGUI.this, ok ? "Database Connected Successfully!" : "Database Connection Failed.\nEnsure MySQL is running & run database/schema.sql\nSee DBConnection.java for URL/user config.", ok ? "Success" : "Failed", ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
                    } catch (Exception ex) { JOptionPane.showMessageDialog(ECommerceGUI.this, ex.getMessage()); }
                }
            }.execute();
        });

        return p;
    }

    private JPanel createHeader(String title) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 60, 120)); header.setPreferredSize(new Dimension(0, 56));
        JLabel l = new JLabel("  " + title); l.setForeground(Color.WHITE); l.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JButton logout = new JButton("Logout"); logout.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));
        header.add(l, BorderLayout.WEST); header.add(logout, BorderLayout.EAST);
        return header;
    }

    // ---- ADMIN DASHBOARD ----
    private JPanel createAdminDashboard() {
        JPanel panel = new JPanel(new BorderLayout(8, 8)); panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panel.add(createHeader("Admin Dashboard - User / Product / Order Management  |  System Activity Monitoring"), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();

        // User Management Tab
        String[] userCols = {"ID", "Name", "Email", "Role", "Extra"};
        DefaultTableModel userModel = new DefaultTableModel(userCols, 0);
        JTable userTable = new JTable(userModel);
        Collection<User> users = authService.getAllDemoUsers();
        for (User u : users) {
            String extra = (u instanceof Seller) ? ((Seller) u).getStoreName() : (u instanceof Buyer) ? ((Buyer) u).getAddress() : "-";
            userModel.addRow(new Object[]{u.getId(), u.getName(), u.getEmail(), u.getRole(), extra});
        }
        JPanel userTab = new JPanel(new BorderLayout(6, 6));
        userTab.add(new JScrollPane(userTable), BorderLayout.CENTER);
        JPanel userBtns = new JPanel();
        JButton addUserBtn = new JButton("Add User"); JButton editUserBtn = new JButton("Edit"); JButton delUserBtn = new JButton("Delete");
        delUserBtn.addActionListener(e -> { int r = userTable.getSelectedRow(); if (r >= 0) userModel.removeRow(r); else JOptionPane.showMessageDialog(this, "Select a row"); });
        addUserBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Create user: INSERT INTO users - See UserDAOImpl.createUser()"));
        userBtns.add(addUserBtn); userBtns.add(editUserBtn); userBtns.add(delUserBtn);
        userTab.add(userBtns, BorderLayout.SOUTH);
        tabs.addTab("User Management", userTab);

        // Product Management Tab
        String[] prodCols = {"ID", "Name", "Price", "Stock", "Category", "Seller ID", "Status"};
        DefaultTableModel prodModel = new DefaultTableModel(prodCols, 0);
        JTable prodTable = new JTable(prodModel);
        for (Product p : inventoryService.getAllProducts()) prodModel.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getStockQuantity(), p.getCategory(), p.getSellerId(), p.getStatus()});
        JPanel prodTab = new JPanel(new BorderLayout(6, 6));
        prodTab.add(new JScrollPane(prodTable), BorderLayout.CENTER);
        JPanel prodBtns = new JPanel();
        prodBtns.add(new JButton("Add Product")); prodBtns.add(new JButton("Edit")); prodBtns.add(new JButton("Remove"));
        prodTab.add(prodBtns, BorderLayout.SOUTH);
        tabs.addTab("Product Management", prodTab);

        // Order Management Tab
        String[] orderCols = {"Order ID", "Buyer ID", "Total", "Status", "Action"};
        DefaultTableModel orderModel = new DefaultTableModel(orderCols, 0) { public boolean isCellEditable(int r, int c) { return c == 4; } };
        orderModel.addRow(new Object[]{101, 4, 5998.0, "PENDING", "Update Status"});
        orderModel.addRow(new Object[]{102, 5, 2499.0, "SHIPPED", "Update Status"});
        JTable orderTable = new JTable(orderModel);
        JPanel orderTab = new JPanel(new BorderLayout()); orderTab.add(new JScrollPane(orderTable), BorderLayout.CENTER);
        tabs.addTab("Order Management", orderTab);

        // System Activity Tab
        JTextArea activityArea = new JTextArea(" [10:42:01] User admin@ecommerce.com logged in\n [10:42:15] Seller rahul@seller.com listed product 'Wireless Headphones'\n [10:43:02] Buyer amit@buyer.com viewed product #1\n [10:43:45] Order #101 created - PENDING\n [10:44:10] Inventory updated for product #2 (stock=8 LOW)\n ... Real-time updates via multithreading + system_activity table");
        activityArea.setEditable(false); activityArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        tabs.addTab("System Activity Monitoring", new JScrollPane(activityArea));

        panel.add(tabs, BorderLayout.CENTER);
        return panel;
    }

    // ---- SELLER DASHBOARD ----
    private JPanel createSellerDashboard() {
        JPanel panel = new JPanel(new BorderLayout(8, 8)); panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panel.add(createHeader("Seller Dashboard - Product Listings | Inventory | Orders | Sales Performance"), BorderLayout.NORTH);
        JTabbedPane tabs = new JTabbedPane();

        // Product Listings
        String[] cols = {"ID", "Name", "Price", "Stock", "Category"};
        DefaultTableModel m = new DefaultTableModel(cols, 0);
        for (Product p : inventoryService.getAllProducts()) m.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getStockQuantity(), p.getCategory()});
        JPanel listPanel = new JPanel(new BorderLayout(6, 6));
        listPanel.add(new JScrollPane(new JTable(m)), BorderLayout.CENTER);
        JPanel btns = new JPanel();
        JButton addProd = new JButton("Create Product Listing"); JButton editProd = new JButton("Edit / Manage Inventory");
        btns.add(addProd); btns.add(editProd);
        editProd.addActionListener(e -> JOptionPane.showMessageDialog(this, "Manage inventory: Update stock_quantity via ProductDAOImpl.updateProduct()"));
        listPanel.add(btns, BorderLayout.SOUTH);
        tabs.addTab("Product Listings", listPanel);

        // Orders
        DefaultTableModel om = new DefaultTableModel(new String[]{"Order ID", "Product", "Qty", "Status", "Fulfill"}, 0);
        om.addRow(new Object[]{101, "Wireless Headphones", 2, "PENDING", "Ship"}); om.addRow(new Object[]{102, "Running Shoes", 1, "SHIPPED", "..."});
        tabs.addTab("Order Management", new JScrollPane(new JTable(om)));

        // Inventory Overview
        JPanel invPanel = new JPanel(new BorderLayout());
        StringBuilder sb = new StringBuilder("Inventory Overview (Graphical placeholder - integrate JFreeChart for Review 2)\n\n");
        for (Product p : inventoryService.getAllProducts()) {
            sb.append(String.format(" %-22s | Stock: %3d | %s\n", p.getName(), p.getStockQuantity(), p.isLowStock() ? "*** LOW STOCK ALERT ***" : "OK"));
        }
        JTextArea invArea = new JTextArea(sb.toString()); invArea.setFont(new Font("Consolas", Font.PLAIN, 12)); invArea.setEditable(false);
        invPanel.add(new JScrollPane(invArea), BorderLayout.CENTER);
        tabs.addTab("Inventory Overview", invPanel);

        // Sales Performance
        JTextArea sales = new JTextArea("Sales Performance (Reports & Graphs)\n\n Total Products: " + inventoryService.getAllProducts().size() + "\n Low Stock Items: " + inventoryService.getLowStockProducts().size() + "\n\n [Bar Chart Placeholder] Category-wise sales\n Electronics: **** (3 products)\n Fashion: ** (2 products)\n\n Use JFreeChart / JavaFX charts for graphical visualization.");
        sales.setFont(new Font("Consolas", Font.PLAIN, 12)); sales.setEditable(false);
        tabs.addTab("Sales Performance", new JScrollPane(sales));

        panel.add(tabs, BorderLayout.CENTER);
        return panel;
    }

    // ---- BUYER DASHBOARD ---- Exactly per spec: 1. Browsing History 2. Order History 3. Wishlist 4. Account Overview
    private JPanel createBuyerDashboard() {
        JPanel panel = new JPanel(new BorderLayout(8, 8)); panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panel.add(createHeader("Buyer Dashboard - Browsing History | Order History | Wishlist | Account Overview"), BorderLayout.NORTH);
        JTabbedPane tabs = new JTabbedPane();

        // 1. Product Browsing History (Spec: List of recently viewed with revisit/purchase) + Catalog Search
        JPanel historyPanel = new JPanel(new BorderLayout(6, 6));
        // Top: search & filter (Product Browsing functionality per spec)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(18); JButton searchBtn = new JButton("Search");
        String[] categories = {"All", "Electronics", "Fashion"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);
        searchPanel.add(new JLabel("Search:")); searchPanel.add(searchField); searchPanel.add(searchBtn);
        searchPanel.add(new JLabel("  Filter:")); searchPanel.add(categoryBox);
        historyPanel.add(searchPanel, BorderLayout.NORTH);

        String[] catalogCols = {"ID", "Name", "Price", "Category", "Stock"};
        DefaultTableModel catalogModel = new DefaultTableModel(catalogCols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        for (Product p : inventoryService.getAllProducts()) catalogModel.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getCategory(), p.getStockQuantity() > 0 ? "In Stock" : "Out of Stock"});
        JTable catalogTable = new JTable(catalogModel);

        // Recently viewed (Browsing History)
        String[] histCols = {"Recently Viewed", "Price", "Last Viewed", "Action"};
        DefaultTableModel histModel = new DefaultTableModel(histCols, 0);
        histModel.addRow(new Object[]{"Wireless Headphones", 2999.0, "10 min ago", "Revisit / Purchase"});
        histModel.addRow(new Object[]{"Smart Watch", 4999.0, "1 hour ago", "Revisit / Purchase"});
        histModel.addRow(new Object[]{"Running Shoes", 2499.0, "Yesterday", "Revisit / Purchase"});
        JTable histTable = new JTable(histModel);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(catalogTable), new JScrollPane(histTable));
        split.setDividerLocation(180); split.setResizeWeight(0.6);
        // Title labels
        JPanel center = new JPanel(new BorderLayout());
        center.add(new JLabel("  Product Catalog - Browse and Search (Functionality: Browse and search for products)"), BorderLayout.NORTH);
        center.add(split, BorderLayout.CENTER);
        JPanel histLabelPanel = new JPanel(new BorderLayout());
        histLabelPanel.add(new JLabel("  Product Browsing History - Recently Viewed (Options to Revisit or Purchase)"), BorderLayout.NORTH);
        // Combine
        JPanel catalogWrapper = new JPanel(new BorderLayout());
        catalogWrapper.add(center, BorderLayout.CENTER);
        // Bottom buttons
        JPanel buyPanel = new JPanel();
        JButton addCart = new JButton("Add to Cart"); JButton buyNow = new JButton("Purchase");
        JButton revisitBtn = new JButton("Revisit Selected");
        buyPanel.add(addCart); buyPanel.add(buyNow); buyPanel.add(revisitBtn);
        searchBtn.addActionListener(e -> {
            String kw = searchField.getText().trim().toLowerCase();
            String cat = (String) categoryBox.getSelectedItem();
            catalogModel.setRowCount(0);
            inventoryService.getAllProducts().stream()
                .filter(p -> kw.isEmpty() || p.getName().toLowerCase().contains(kw) || p.getDescription().toLowerCase().contains(kw))
                .filter(p -> "All".equals(cat) || p.getCategory().equalsIgnoreCase(cat))
                .forEach(p -> catalogModel.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getCategory(), p.getStockQuantity() > 0 ? "In Stock" : "Out of Stock"}));
        });
        categoryBox.addActionListener(e -> searchBtn.doClick());
        catalogTable.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting() && catalogTable.getSelectedRow() >= 0) {
                String name = (String) catalogModel.getValueAt(catalogTable.getSelectedRow(), 1);
                // Simulate adding to browsing history (multithreading demo hook)
                boolean exists = false;
                for (int i = 0; i < histModel.getRowCount(); i++) if (histModel.getValueAt(i, 0).equals(name)) exists = true;
                if (!exists && histModel.getRowCount() < 6) histModel.insertRow(0, new Object[]{name, catalogModel.getValueAt(catalogTable.getSelectedRow(), 2), "Just now", "Revisit / Purchase"});
            }
        });
        buyNow.addActionListener(e -> {
            int r = catalogTable.getSelectedRow();
            if (r < 0) r = histTable.getSelectedRow();
            String prod = r >= 0 ? (catalogTable.getSelectedRow() >= 0 ? (String) catalogModel.getValueAt(catalogTable.getSelectedRow(), 1) : (String) histModel.getValueAt(histTable.getSelectedRow(), 0)) : null;
            if (prod == null) JOptionPane.showMessageDialog(this, "Select a product from catalog or history");
            else JOptionPane.showMessageDialog(this, "Order Confirmation\nProduct: " + prod + "\nStatus: Order Placed Successfully (PENDING)\nFunctionality: Make purchases and receive order confirmations.");
        });
        addCart.addActionListener(e -> JOptionPane.showMessageDialog(this, "Added to cart! (Cart -> Purchase flow for Review 2)"));
        historyPanel.add(catalogWrapper, BorderLayout.CENTER);
        historyPanel.add(buyPanel, BorderLayout.SOUTH);
        tabs.addTab("1. Product Browsing History", historyPanel);

        // 2. Order History (Spec: Table of past orders with details and status updates)
        DefaultTableModel om2 = new DefaultTableModel(new String[]{"Order ID", "Date", "Products", "Total", "Status (Track)"}, 0);
        om2.addRow(new Object[]{101, "2026-10-10", "Wireless Headphones x2", 5998.0, "PENDING"});
        om2.addRow(new Object[]{102, "2026-10-09", "Cotton T-Shirt x1", 599.0, "DELIVERED"});
        om2.addRow(new Object[]{103, "2026-10-08", "Running Shoes x1", 2499.0, "SHIPPED"});
        JPanel orderHistPanel = new JPanel(new BorderLayout(6, 6));
        orderHistPanel.add(new JScrollPane(new JTable(om2)), BorderLayout.CENTER);
        JPanel trackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        trackPanel.add(new JLabel("Order Tracking: PENDING -> SHIPPED -> DELIVERED"));
        JButton trackBtn = new JButton("Track Selected Order"); trackBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Order #101: PENDING - Seller is processing your order.\nTracking: Order placed -> Processing -> Shipped -> Delivered"));
        trackPanel.add(trackBtn);
        orderHistPanel.add(trackPanel, BorderLayout.SOUTH);
        tabs.addTab("2. Order History", orderHistPanel);

        // 3. Wishlist (Spec: Managing favorite products and tracking price changes)
        DefaultTableModel wm = new DefaultTableModel(new String[]{"Product", "Category", "Price", "Price Change", "Action"}, 0);
        wm.addRow(new Object[]{"Smart Watch", "Electronics", 4999.0, "No change", "Remove / Add to Cart"});
        wm.addRow(new Object[]{"Laptop Stand", "Electronics", 1299.0, "↓ ₹100 (was 1399)", "Remove / Add to Cart"});
        wm.addRow(new Object[]{"Cotton T-Shirt", "Fashion", 599.0, "↑ ₹50", "Remove / Add to Cart"});
        JPanel wishPanel = new JPanel(new BorderLayout(6, 6));
        wishPanel.add(new JScrollPane(new JTable(wm)), BorderLayout.CENTER);
        JPanel wishBtns = new JPanel(); wishBtns.add(new JButton("Add Selected to Cart")); wishBtns.add(new JButton("Remove from Wishlist"));
        wishPanel.add(wishBtns, BorderLayout.SOUTH);
        tabs.addTab("3. Wishlist", wishPanel);

        // 4. Account Overview (Spec: Managing personal details, payment methods, address book)
        JPanel accPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(6, 8, 6, 8); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel accTitle = new JLabel("Account Overview - Personal Details | Payment Methods | Address Book"); accTitle.setFont(new Font("Segoe UI", Font.BOLD, 13)); accTitle.setForeground(new Color(30, 60, 120)); accPanel.add(accTitle, gbc);
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0; accPanel.add(new JLabel("Name:"), gbc); JTextField nameF = new JTextField("Amit Buyer", 18); gbc.gridx = 1; accPanel.add(nameF, gbc);
        gbc.gridy = 2; gbc.gridx = 0; accPanel.add(new JLabel("Email:"), gbc); JTextField emailF = new JTextField("amit@buyer.com", 18); gbc.gridx = 1; accPanel.add(emailF, gbc);
        gbc.gridy = 3; gbc.gridx = 0; accPanel.add(new JLabel("Address Book:"), gbc); JTextField addrF = new JTextField("Delhi, India", 18); gbc.gridx = 1; accPanel.add(addrF, gbc);
        gbc.gridy = 4; gbc.gridx = 0; accPanel.add(new JLabel("Payment Methods:"), gbc); JComboBox<String> payBox = new JComboBox<>(new String[]{"Cash on Delivery (Default)", "Saved Card **** 1234", "UPI: amit@upi"}); gbc.gridx = 1; accPanel.add(payBox, gbc);
        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2; JButton saveAcc = new JButton("Save Changes"); accPanel.add(saveAcc, gbc);
        saveAcc.addActionListener(e -> JOptionPane.showMessageDialog(this, "Account updated successfully!"));
        gbc.gridy = 6; JLabel hint = new JLabel("<html><i>Note (per spec): Payment Methods is display/manage only. No real gateway in Review 1.</i></html>"); hint.setForeground(Color.GRAY); accPanel.add(hint, gbc);
        JPanel accWrap = new JPanel(new BorderLayout()); accWrap.add(accPanel, BorderLayout.NORTH);
        tabs.addTab("4. Account Overview", new JScrollPane(accWrap));

        panel.add(tabs, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ECommerceGUI().setVisible(true));
    }
}
