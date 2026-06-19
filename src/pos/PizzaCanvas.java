package pos;

import decorator.Product;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PizzaCanvas extends JPanel {
    private Product pizza;
    
    public PizzaCanvas() {
        setPreferredSize(new Dimension(300, 300));
        setBackground(new Color(40, 40, 40));
    }
    
    public void setPizza(Product pizza) {
        this.pizza = pizza;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pizza == null) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();
        int centerX = w / 2;
        int centerY = h / 2;
        int radius = 100;
        
        String desc = pizza.getDescription().toLowerCase();
        
        // Draw Crust
        g2d.setColor(new Color(222, 184, 135)); // Burlywood
        if (desc.contains("pan")) {
            g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        } else if (desc.contains("thin")) {
            radius = 110;
            g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
            g2d.setColor(new Color(205, 170, 125));
            g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
            radius = 105; // Inner sauce radius
        } else {
            g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }

        // Draw Sauce
        int sauceRadius = radius - 10;
        if (desc.contains("pesto")) {
            g2d.setColor(new Color(34, 139, 34)); // Green
            g2d.fillOval(centerX - sauceRadius, centerY - sauceRadius, sauceRadius * 2, sauceRadius * 2);
        } else if (desc.contains("alfredo")) {
            g2d.setColor(new Color(255, 250, 205)); // LemonChiffon
            g2d.fillOval(centerX - sauceRadius, centerY - sauceRadius, sauceRadius * 2, sauceRadius * 2);
        } else if (desc.contains("bbq")) {
            g2d.setColor(new Color(139, 69, 19)); // SaddleBrown
            g2d.fillOval(centerX - sauceRadius, centerY - sauceRadius, sauceRadius * 2, sauceRadius * 2);
        } else {
            // Default Tomato
            g2d.setColor(new Color(205, 92, 92)); // IndianRed
            g2d.fillOval(centerX - sauceRadius, centerY - sauceRadius, sauceRadius * 2, sauceRadius * 2);
        }

        // Draw Cheese
        int cheeseRadius = sauceRadius - 5;
        if (desc.contains("mozzarella") || desc.contains("vegan")) {
            g2d.setColor(new Color(255, 255, 224)); // LightYellow
            g2d.fillOval(centerX - cheeseRadius, centerY - cheeseRadius, cheeseRadius * 2, cheeseRadius * 2);
        } else if (desc.contains("cheddar")) {
            g2d.setColor(new Color(255, 165, 0)); // Orange
            g2d.fillOval(centerX - cheeseRadius, centerY - cheeseRadius, cheeseRadius * 2, cheeseRadius * 2);
        }

        // Draw Toppings
        Random rand = new Random(42); // Fixed seed so it doesn't jiggle on repaint
        if (desc.contains("pepperoni")) {
            g2d.setColor(Color.RED);
            for (int i = 0; i < 15; i++) {
                drawRandomCircle(g2d, centerX, centerY, cheeseRadius, 15, rand);
            }
        }
        if (desc.contains("olives")) {
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            for (int i = 0; i < 20; i++) {
                drawRandomRing(g2d, centerX, centerY, cheeseRadius, 8, rand);
            }
        }
        if (desc.contains("mushrooms")) {
            g2d.setColor(new Color(211, 211, 211));
            for (int i = 0; i < 12; i++) {
                drawRandomCircle(g2d, centerX, centerY, cheeseRadius, 12, rand);
            }
        }
        if (desc.contains("bell peppers")) {
            g2d.setColor(Color.GREEN);
            for (int i = 0; i < 15; i++) {
                drawRandomRect(g2d, centerX, centerY, cheeseRadius, 10, rand);
            }
        }
    }

    private void drawRandomCircle(Graphics2D g2d, int cx, int cy, int maxR, int size, Random rand) {
        double angle = rand.nextDouble() * 2 * Math.PI;
        double r = rand.nextDouble() * (maxR - size);
        int x = (int) (cx + r * Math.cos(angle)) - size/2;
        int y = (int) (cy + r * Math.sin(angle)) - size/2;
        g2d.fillOval(x, y, size, size);
    }
    
    private void drawRandomRing(Graphics2D g2d, int cx, int cy, int maxR, int size, Random rand) {
        double angle = rand.nextDouble() * 2 * Math.PI;
        double r = rand.nextDouble() * (maxR - size);
        int x = (int) (cx + r * Math.cos(angle)) - size/2;
        int y = (int) (cy + r * Math.sin(angle)) - size/2;
        g2d.drawOval(x, y, size, size);
    }

    private void drawRandomRect(Graphics2D g2d, int cx, int cy, int maxR, int size, Random rand) {
        double angle = rand.nextDouble() * 2 * Math.PI;
        double r = rand.nextDouble() * (maxR - size);
        int x = (int) (cx + r * Math.cos(angle)) - size/2;
        int y = (int) (cy + r * Math.sin(angle)) - size/2;
        g2d.fillRect(x, y, size, size/2);
    }
}
