package etl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ETLPipelineJob {
    private static final String DB_FILE = "orders_database.csv";

    public void runPipeline() {
        System.out.println("Starting ETL Pipeline...");
        List<HistoricalOrder> orders = extractData();
        
        if (orders.isEmpty()) {
            System.out.println("No data to process.");
            return;
        }

        // Apply Visitor Pattern for Transform & Load
        ExportVisitor jsonVisitor = new JSONExportVisitor();
        
        System.out.println("Transforming and Loading data to warehouse...");
        for (HistoricalOrder order : orders) {
            order.accept(jsonVisitor);
        }
        
        jsonVisitor.finishExport();
        System.out.println("ETL Pipeline finished successfully.");
    }

    private List<HistoricalOrder> extractData() {
        List<HistoricalOrder> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DB_FILE))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length < 4) continue;
                
                HistoricalOrder order = new HistoricalOrder(parts[0], parts[1], Double.parseDouble(parts[2]));
                
                // Parse items
                String details = parts[3];
                String[] itemsStr = details.split("\\|");
                for (String itemStr : itemsStr) {
                    if (itemStr.contains("- ") && itemStr.contains(": $")) {
                        int start = itemStr.indexOf("- ") + 2;
                        int end = itemStr.indexOf(": $");
                        if (start < end) {
                            order.addItem(itemStr.substring(start, end).trim());
                        }
                    }
                }
                orders.add(order);
            }
        } catch (IOException e) {
            System.out.println("Could not read database. Run a checkout first.");
        }
        return orders;
    }
}
