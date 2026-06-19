package pos;

import decorator.*;
import composite.ProductItem;

import javax.swing.*;
import java.awt.*;

public class CustomPizzaDialog extends JDialog {
    private Product currentPizza;
    private PizzaCanvas canvas;
    private JLabel priceLabel;
    
    private JComboBox<String> sizeCombo;
    private JComboBox<String> doughCombo;
    private JComboBox<String> sauceCombo;
    private JComboBox<String> cheeseCombo;
    
    private JCheckBox cbPepperoni, cbSausage, cbMushrooms, cbOnions, cbOlives, cbPeppers, cbPineapple;
    
    private boolean confirmed = false;
    private ProductItem resultItem;

    public CustomPizzaDialog(JFrame parent) {
        super(parent, "Create Your Own Pizza", true);
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        canvas = new PizzaCanvas();
        add(canvas, BorderLayout.CENTER);
        
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(0, 2, 10, 10));
        controls.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        sizeCombo = new JComboBox<>(new String[]{"Small ($8)", "Medium ($10)", "Large ($12)"});
        doughCombo = new JComboBox<>(new String[]{"Hand-Tossed", "Thin Crust", "Pan Pizza", "Stuffed Crust (+$2)"});
        sauceCombo = new JComboBox<>(new String[]{"Tomato Sauce", "Alfredo Sauce", "Pesto Sauce (+$1)", "BBQ Sauce"});
        cheeseCombo = new JComboBox<>(new String[]{"Regular Mozzarella", "Extra Cheese (+$1)", "Vegan Cheese (+$1)", "No Cheese"});
        
        controls.add(new JLabel("Size:")); controls.add(sizeCombo);
        controls.add(new JLabel("Dough:")); controls.add(doughCombo);
        controls.add(new JLabel("Sauce:")); controls.add(sauceCombo);
        controls.add(new JLabel("Cheese:")); controls.add(cheeseCombo);
        
        JPanel toppingsPanel = new JPanel(new GridLayout(0, 2));
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings (+$1.50 each)"));
        cbPepperoni = new JCheckBox("Pepperoni");
        cbSausage = new JCheckBox("Sausage");
        cbMushrooms = new JCheckBox("Mushrooms");
        cbOnions = new JCheckBox("Onions");
        cbOlives = new JCheckBox("Olives");
        cbPeppers = new JCheckBox("Bell Peppers");
        cbPineapple = new JCheckBox("Pineapple");
        
        toppingsPanel.add(cbPepperoni); toppingsPanel.add(cbSausage);
        toppingsPanel.add(cbMushrooms); toppingsPanel.add(cbOnions);
        toppingsPanel.add(cbOlives); toppingsPanel.add(cbPeppers);
        toppingsPanel.add(cbPineapple);
        
        controls.add(toppingsPanel);
        
        // Add listeners to update canvas
        java.awt.event.ActionListener updater = e -> updatePizza();
        sizeCombo.addActionListener(updater);
        doughCombo.addActionListener(updater);
        sauceCombo.addActionListener(updater);
        cheeseCombo.addActionListener(updater);
        cbPepperoni.addActionListener(updater);
        cbSausage.addActionListener(updater);
        cbMushrooms.addActionListener(updater);
        cbOnions.addActionListener(updater);
        cbOlives.addActionListener(updater);
        cbPeppers.addActionListener(updater);
        cbPineapple.addActionListener(updater);
        
        add(controls, BorderLayout.WEST);
        
        JPanel bottom = new JPanel(new BorderLayout());
        priceLabel = new JLabel("Total: $0.00", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        bottom.add(priceLabel, BorderLayout.CENTER);
        
        JButton btnAdd = new JButton("Add to Order");
        btnAdd.addActionListener(e -> {
            confirmed = true;
            resultItem = new ProductItem("Custom Pizza", currentPizza);
            setVisible(false);
        });
        bottom.add(btnAdd, BorderLayout.EAST);
        
        add(bottom, BorderLayout.SOUTH);
        
        updatePizza();
    }
    
    private void updatePizza() {
        // Base Price from Size
        double basePrice = 8.00;
        String sizeStr = "Small";
        if (sizeCombo.getSelectedIndex() == 1) { basePrice = 10.00; sizeStr = "Medium"; }
        else if (sizeCombo.getSelectedIndex() == 2) { basePrice = 12.00; sizeStr = "Large"; }
        
        String doughStr = (String) doughCombo.getSelectedItem();
        if (doughStr.contains("Stuffed")) basePrice += 2.00;
        
        currentPizza = new BasePizza(sizeStr + " " + doughStr, basePrice);
        
        // Sauce
        String sauceStr = (String) sauceCombo.getSelectedItem();
        double saucePrice = sauceStr.contains("Pesto") ? 1.00 : 0.00;
        currentPizza = new SauceDecorator(currentPizza, sauceStr, saucePrice);
        
        // Cheese
        String cheeseStr = (String) cheeseCombo.getSelectedItem();
        if (!cheeseStr.equals("No Cheese")) {
            double cheesePrice = cheeseStr.contains("Extra") || cheeseStr.contains("Vegan") ? 1.00 : 0.00;
            currentPizza = new CheeseDecorator(currentPizza, cheeseStr, cheesePrice);
        }
        
        // Toppings
        if (cbPepperoni.isSelected()) currentPizza = new ToppingDecoratorImpl(currentPizza, "Pepperoni", 1.50);
        if (cbSausage.isSelected()) currentPizza = new ToppingDecoratorImpl(currentPizza, "Sausage", 1.50);
        if (cbMushrooms.isSelected()) currentPizza = new ToppingDecoratorImpl(currentPizza, "Mushrooms", 1.50);
        if (cbOnions.isSelected()) currentPizza = new ToppingDecoratorImpl(currentPizza, "Onions", 1.50);
        if (cbOlives.isSelected()) currentPizza = new ToppingDecoratorImpl(currentPizza, "Olives", 1.50);
        if (cbPeppers.isSelected()) currentPizza = new ToppingDecoratorImpl(currentPizza, "Bell Peppers", 1.50);
        if (cbPineapple.isSelected()) currentPizza = new ToppingDecoratorImpl(currentPizza, "Pineapple", 1.50);
        
        canvas.setPizza(currentPizza);
        priceLabel.setText(String.format("Total: $%.2f", currentPizza.getPrice()));
    }
    
    public ProductItem getResult() {
        if (confirmed) return resultItem;
        return null;
    }
}
