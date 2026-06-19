package analytics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AnalyticsEngine {
    private static final String FILE_NAME = "orders_database.csv";
    
    private double totalRevenue = 0;
    private int totalOrders = 0;
    private Map<String, Integer> itemCounts = new HashMap<>();

    public void processData() {
        totalRevenue = 0;
        totalOrders = 0;
        itemCounts.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                // Format: OrderID,Date,Total,Details
                String[] parts = line.split(",", 4);
                if (parts.length < 4) continue;
                
                totalOrders++;
                totalRevenue += Double.parseDouble(parts[2]);
                
                // Parse Details: "Order #X - Status: Y | - Item1: $Z | - Item2: $Z | Total..."
                String details = parts[3];
                String[] items = details.split("\\|");
                for (String itemStr : items) {
                    if (itemStr.contains("- ") && itemStr.contains(": $")) {
                        // Extract item name
                        int start = itemStr.indexOf("- ") + 2;
                        int end = itemStr.indexOf(": $");
                        if (start < end) {
                            String itemName = itemStr.substring(start, end).trim();
                            itemCounts.put(itemName, itemCounts.getOrDefault(itemName, 0) + 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read database file for analytics.");
        }
    }

    public double getTotalRevenue() { return totalRevenue; }
    public int getTotalOrders() { return totalOrders; }
    public double getAverageOrderValue() { return totalOrders == 0 ? 0 : totalRevenue / totalOrders; }
    public Map<String, Integer> getItemCounts() { return itemCounts; }
}
