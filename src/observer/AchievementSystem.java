package observer;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class AchievementSystem implements Observer {
    private JFrame parentFrame;
    private Set<String> unlocked = new HashSet<>();

    public AchievementSystem(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    @Override
    public void update(String orderDetails) {
        // Parse order details to check for achievements
        if (!unlocked.contains("Big Spender") && orderDetails.contains("Total: $") && extractTotal(orderDetails) > 40.0) {
            unlock("Big Spender! Order > $40");
        }
        if (!unlocked.contains("Pizza Artist") && orderDetails.toLowerCase().contains("custom pizza")) {
            unlock("Pizza Artist! Custom Creation");
        }
        if (!unlocked.contains("Veggie Power") && orderDetails.toLowerCase().contains("vegan")) {
            unlock("Veggie Power! Vegan order");
        }
    }

    private double extractTotal(String details) {
        try {
            int idx = details.indexOf("Total: $");
            if (idx != -1) {
                String val = details.substring(idx + 8).trim();
                return Double.parseDouble(val);
            }
        } catch (Exception e) {}
        return 0.0;
    }

    private void unlock(String name) {
        unlocked.add(name);
        
        // Show animated toast
        JWindow toast = new JWindow(parentFrame);
        toast.setBackground(new Color(0, 0, 0, 0)); // Transparent
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50, 220));
        panel.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2)); // Gold border
        
        JLabel title = new JLabel("ACHIEVEMENT UNLOCKED", SwingConstants.CENTER);
        title.setForeground(new Color(255, 215, 0));
        title.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel desc = new JLabel(name, SwingConstants.CENTER);
        desc.setForeground(Color.WHITE);
        desc.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(desc, BorderLayout.CENTER);
        toast.add(panel);
        toast.pack();
        
        // Position at bottom right of screen
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screen.width - toast.getWidth() - 20;
        int y = screen.height;
        toast.setLocation(x, y);
        toast.setVisible(true);
        
        // Animate slide up
        new Thread(() -> {
            try {
                for (int i = 0; i < toast.getHeight() + 20; i += 5) {
                    toast.setLocation(x, y - i);
                    Thread.sleep(10);
                }
                Thread.sleep(3000); // Display for 3 secs
                for (int i = toast.getHeight() + 20; i >= 0; i -= 5) {
                    toast.setLocation(x, y - i);
                    Thread.sleep(10);
                }
                SwingUtilities.invokeLater(toast::dispose);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
