package pos;

import model.Order;
import observer.Observer;

import javax.swing.*;
import java.awt.*;

public class KitchenDisplayGUI extends JFrame implements Observer {
    private JTextArea displayArea;

    public KitchenDisplayGUI() {
        setTitle("Kitchen Display System");
        setSize(400, 600);
        setLocation(820, 0); // Position it next to the POS
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Keep open

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        displayArea.setBackground(Color.BLACK);
        displayArea.setForeground(Color.GREEN);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);
        
        updateDisplay("No orders currently in progress.");
    }

    public void addOrder(Order order) {
        order.registerObserver(this);
        // Initially show it
        update(order.getOrderDetails());
        
        // Simulate Kitchen process
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                order.nextState(); // Baking
                Thread.sleep(3000);
                order.nextState(); // Ready
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void update(String orderDetails) {
        SwingUtilities.invokeLater(() -> {
            displayArea.setText(displayArea.getText() + "\n\n*** KITCHEN UPDATE ***\n" + orderDetails);
            // Autoscroll
            displayArea.setCaretPosition(displayArea.getDocument().getLength());
        });
    }
    
    private void updateDisplay(String text) {
        displayArea.setText(text);
    }
}
