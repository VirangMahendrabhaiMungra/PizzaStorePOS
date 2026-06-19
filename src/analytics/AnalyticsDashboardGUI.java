package analytics;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnalyticsDashboardGUI extends JFrame {
    private AnalyticsEngine engine;

    public AnalyticsDashboardGUI() {
        engine = new AnalyticsEngine();
        engine.processData();

        setTitle("Business Analytics Dashboard");
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        // Top KPI Panel
        JPanel kpiPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        kpiPanel.setBackground(new Color(30, 30, 30));
        kpiPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        kpiPanel.add(createKPICard("Total Orders", String.valueOf(engine.getTotalOrders())));
        kpiPanel.add(createKPICard("Total Sale", "$" + String.format("%.2f", engine.getTotalRevenue())));
        kpiPanel.add(createKPICard("Average Order Value", "$" + String.format("%.2f", engine.getAverageOrderValue())));

        add(kpiPanel, BorderLayout.NORTH);

        // Chart Panel
        BarChartPanel chartPanel = new BarChartPanel(engine.getItemCounts());
        add(chartPanel, BorderLayout.CENTER);
    }

    private JPanel createKPICard(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(45, 45, 48));
        panel.setBorder(BorderFactory.createLineBorder(new Color(0, 122, 204), 2));
        
        JLabel titleLbl = new JLabel(title, SwingConstants.CENTER);
        titleLbl.setForeground(Color.LIGHT_GRAY);
        titleLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        titleLbl.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        JLabel valLbl = new JLabel(value, SwingConstants.CENTER);
        valLbl.setForeground(Color.WHITE);
        valLbl.setFont(new Font("Arial", Font.BOLD, 28));
        valLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        panel.add(titleLbl, BorderLayout.NORTH);
        panel.add(valLbl, BorderLayout.CENTER);
        return panel;
    }
}

class BarChartPanel extends JPanel {
    private Map<String, Integer> data;
    
    public BarChartPanel(Map<String, Integer> data) {
        this.data = data;
        setBackground(new Color(30, 30, 30));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int padding = 50;
        int width = getWidth() - (2 * padding);
        int height = getHeight() - (2 * padding);
        
        // Find max value for scaling
        int maxVal = 0;
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(data.entrySet());
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue())); // Sort descending
        
        // Keep top 5
        if (entries.size() > 5) entries = entries.subList(0, 5);
        
        for (Map.Entry<String, Integer> entry : entries) {
            if (entry.getValue() > maxVal) maxVal = entry.getValue();
        }
        if (maxVal == 0) maxVal = 1;

        // Draw Axes
        g2d.setColor(Color.WHITE);
        g2d.drawLine(padding, getHeight() - padding, getWidth() - padding, getHeight() - padding); // X
        g2d.drawLine(padding, padding, padding, getHeight() - padding); // Y

        // Draw Bars
        int barWidth = width / entries.size() - 20;
        int xPos = padding + 10;
        
        Color[] colors = {new Color(0, 122, 204), new Color(40, 167, 69), new Color(220, 53, 69), new Color(255, 193, 7), new Color(111, 66, 193)};
        int cIdx = 0;

        for (Map.Entry<String, Integer> entry : entries) {
            int barHeight = (int) (((double) entry.getValue() / maxVal) * height);
            int yPos = getHeight() - padding - barHeight;

            g2d.setColor(colors[cIdx % colors.length]);
            g2d.fillRect(xPos, yPos, barWidth, barHeight);
            
            // Draw Value
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString(String.valueOf(entry.getValue()), xPos + barWidth/2 - 5, yPos - 5);

            // Draw Label (truncate if too long)
            String label = entry.getKey();
            if (label.length() > 12) label = label.substring(0, 10) + "..";
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString(label, xPos, getHeight() - padding + 20);

            xPos += barWidth + 20;
            cIdx++;
        }
        
        // Title
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Top Selling Items", getWidth()/2 - 80, padding - 20);
    }
}
