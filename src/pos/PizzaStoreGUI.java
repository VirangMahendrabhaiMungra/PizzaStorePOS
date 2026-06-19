package pos;

import command.AddItemCommand;
import command.Invoker;
import composite.MenuCategory;
import composite.MenuItem;
import composite.ProductItem;
import factory.PizzaFactory;
import analytics.AnalyticsDashboardGUI;
import database.OrderDAO;
import etl.ETLPipelineJob;
import database.FileOrderDAOImpl;
import model.Order;
import observer.AchievementSystem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PizzaStoreGUI extends JFrame {
    private Order currentOrder;
    private Invoker invoker;
    private OrderQueue kitchenQueue;
    
    private JTextArea receiptArea;
    private KitchenDisplayGUI kitchenDisplay;
    private AchievementSystem achievements;
    private OrderDAO orderDAO;

    public PizzaStoreGUI() {
        // Apply a dark theme look
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Panel.background", new Color(45, 45, 48));
            UIManager.put("Label.foreground", Color.WHITE);
            UIManager.put("TabbedPane.background", new Color(30, 30, 30));
            UIManager.put("TabbedPane.foreground", Color.BLACK);
        } catch (Exception e) {}

        currentOrder = new Order();
        invoker = new Invoker();
        kitchenQueue = new OrderQueue();
        orderDAO = new FileOrderDAOImpl();
        
        kitchenDisplay = new KitchenDisplayGUI();
        kitchenDisplay.setVisible(true);
        
        achievements = new AchievementSystem(this);
        
        // Observers
        currentOrder.registerObserver(details -> updateOrderDisplay());
        currentOrder.registerObserver(achievements);

        setTitle("Advanced Pizza Store POS");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(45, 45, 48));

        // --- LEFT PANEL: MENU TABS ---
        JTabbedPane menuTabs = new JTabbedPane();
        menuTabs.setFont(new Font("Arial", Font.BOLD, 16));

        // Traditional Pizzas Panel
        JPanel pizzasPanel = createGridPanel();
        addPizzaButton(pizzasPanel, "Margherita ($11)", PizzaFactory.createMargherita());
        addPizzaButton(pizzasPanel, "Hawaiian ($15.50)", PizzaFactory.createHawaiian());
        addPizzaButton(pizzasPanel, "Meat Lovers ($20.50)", PizzaFactory.createMeatLovers());
        addPizzaButton(pizzasPanel, "Veggie Supreme ($17.50)", PizzaFactory.createVeggieSupreme());
        
        JButton customBtn = createStyledButton("CREATE YOUR OWN PIZZA");
        customBtn.addActionListener(e -> {
            CustomPizzaDialog dialog = new CustomPizzaDialog(this);
            dialog.setVisible(true);
            ProductItem customPizza = dialog.getResult();
            if (customPizza != null) {
                invoker.executeCommand(new AddItemCommand(currentOrder, customPizza));
            }
        });
        pizzasPanel.add(customBtn);
        menuTabs.addTab("Pizzas", new JScrollPane(pizzasPanel));

        // Sides Panel
        JPanel sidesPanel = createGridPanel();
        addMenuItemButton(sidesPanel, new MenuItem("Garlic Bread", "Freshly baked", 4.99));
        addMenuItemButton(sidesPanel, new MenuItem("Cheese Sticks", "Gooey mozzarella", 6.99));
        addMenuItemButton(sidesPanel, new MenuItem("Spicy Wings", "Buffalo sauce", 8.99));
        menuTabs.addTab("Sides", new JScrollPane(sidesPanel));

        // Drinks Panel
        JPanel drinksPanel = createGridPanel();
        addMenuItemButton(drinksPanel, new MenuItem("Cola Large", "32oz", 2.99));
        addMenuItemButton(drinksPanel, new MenuItem("Sprite Large", "32oz", 2.99));
        addMenuItemButton(drinksPanel, new MenuItem("Bottled Water", "Spring", 1.99));
        menuTabs.addTab("Drinks", new JScrollPane(drinksPanel));

        add(menuTabs, BorderLayout.CENTER);

        // --- RIGHT PANEL: RECEIPT & CONTROLS ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(350, 0));
        rightPanel.setBackground(new Color(30, 30, 30));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        receiptArea.setBackground(new Color(250, 250, 245));
        receiptArea.setForeground(Color.BLACK);
        
        TitledBorder border = BorderFactory.createTitledBorder("CURRENT ORDER");
        border.setTitleColor(Color.WHITE);
        JScrollPane receiptScroll = new JScrollPane(receiptArea);
        receiptScroll.setBorder(border);
        rightPanel.add(receiptScroll, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        controlPanel.setBackground(new Color(30, 30, 30));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton undoBtn = createStyledButton("Undo");
        undoBtn.addActionListener(e -> invoker.undo());
        
        JButton redoBtn = createStyledButton("Redo");
        redoBtn.addActionListener(e -> invoker.redo());

        JButton cancelBtn = createStyledButton("Void Order");
        cancelBtn.addActionListener(e -> {
            currentOrder = new Order();
            currentOrder.registerObserver(details -> updateOrderDisplay());
            currentOrder.registerObserver(achievements);
            invoker = new Invoker();
            updateOrderDisplay();
        });

        JButton submitBtn = createStyledButton("CHECKOUT");
        submitBtn.addActionListener(e -> submitOrder());
        
        JButton analyticsBtn = createStyledButton("📊 Analytics");
        analyticsBtn.addActionListener(e -> {
            AnalyticsDashboardGUI dashboard = new AnalyticsDashboardGUI();
            dashboard.setVisible(true);
        });

        JButton etlBtn = createStyledButton("⚙️ Run ETL");
        etlBtn.addActionListener(e -> {
            ETLPipelineJob job = new ETLPipelineJob();
            job.runPipeline();
            JOptionPane.showMessageDialog(this, "ETL Pipeline completed successfully.\nCheck /warehouse directory for JSON export.", "ETL Status", JOptionPane.INFORMATION_MESSAGE);
        });

        controlPanel.add(undoBtn);
        controlPanel.add(redoBtn);
        controlPanel.add(cancelBtn);
        controlPanel.add(submitBtn);
        controlPanel.add(analyticsBtn);
        controlPanel.add(etlBtn);

        rightPanel.add(controlPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);
        
        updateOrderDisplay();
    }

    private JPanel createGridPanel() {
        JPanel p = new JPanel(new GridLayout(0, 2, 10, 10));
        p.setBackground(new Color(45, 45, 48));
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return p;
    }

    private JButton createStyledButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        return b;
    }

    private void addPizzaButton(JPanel panel, String label, ProductItem item) {
        JButton btn = createStyledButton(label);
        btn.addActionListener(e -> {
            invoker.executeCommand(new AddItemCommand(currentOrder, item));
        });
        panel.add(btn);
    }
    
    private void addMenuItemButton(JPanel panel, MenuItem item) {
        JButton btn = createStyledButton(item.getName() + " ($" + item.getPrice() + ")");
        btn.addActionListener(e -> {
            invoker.executeCommand(new AddItemCommand(currentOrder, item));
        });
        panel.add(btn);
    }

    private void submitOrder() {
        if (currentOrder.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Order is empty!");
            return;
        }
        
        double total = currentOrder.getTotal();
        String[] options = {"Cash", "Credit Card"};
        int choice = JOptionPane.showOptionDialog(this, "Total: $" + String.format("%.2f", total) + "\nSelect Payment Method", "Payment",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                
        if (choice == 0) {
            // Cash Payment: Calculate Change
            String input = JOptionPane.showInputDialog(this, "Total is $" + String.format("%.2f", total) + "\nEnter amount tendered:");
            if (input != null && !input.trim().isEmpty()) {
                try {
                    double tendered = Double.parseDouble(input);
                    if (tendered < total) {
                        JOptionPane.showMessageDialog(this, "Insufficient amount! Need at least $" + String.format("%.2f", total), "Payment Error", JOptionPane.ERROR_MESSAGE);
                        return; // Abort checkout
                    }
                    double change = tendered - total;
                    JOptionPane.showMessageDialog(this, "Payment successful!\nChange due: $" + String.format("%.2f", change), "Transaction Complete", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                return; // Cancelled
            }
        } else if (choice == 1) {
            // Credit Card
            JOptionPane.showMessageDialog(this, "Credit Card approved.", "Transaction Complete", JOptionPane.INFORMATION_MESSAGE);
        } else {
            return; // Closed dialog
        }
        
        // Finalize Order
        currentOrder.nextState(); 
        kitchenQueue.enqueue(currentOrder);
        kitchenDisplay.addOrder(currentOrder);
        
        // Save to Database
        orderDAO.saveOrder(currentOrder);
        
        currentOrder = new Order();
        currentOrder.registerObserver(details -> updateOrderDisplay());
        currentOrder.registerObserver(achievements);
        invoker = new Invoker();
        updateOrderDisplay();
    }

    private void updateOrderDisplay() {
        receiptArea.setText("\n================================\n");
        receiptArea.append(currentOrder.getOrderDetails());
        receiptArea.append("\n================================\n");
    }
}
