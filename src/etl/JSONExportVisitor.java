package etl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JSONExportVisitor implements ExportVisitor {
    private PrintWriter factWriter;
    private PrintWriter dimWriter;
    
    public JSONExportVisitor() {
        try {
            File warehouseDir = new File("warehouse");
            if (!warehouseDir.exists()) warehouseDir.mkdir();
            
            factWriter = new PrintWriter(new FileWriter("warehouse/fact_orders.json"));
            dimWriter = new PrintWriter(new FileWriter("warehouse/dim_order_items.json"));
            
            factWriter.println("[\n");
            dimWriter.println("[\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(HistoricalOrder order) {
        if (factWriter == null || dimWriter == null) return;
        
        // Transform and Load to Fact Table
        factWriter.printf("  { \"order_id\": \"%s\", \"date\": \"%s\", \"total_revenue\": %.2f },\n", 
                order.getOrderId(), order.getDate(), order.getTotal());
        
        // Transform and Load to Dimension Table
        for (String item : order.getItems()) {
            dimWriter.printf("  { \"order_id\": \"%s\", \"item_name\": \"%s\" },\n", 
                    order.getOrderId(), item.replace("\"", "\\\""));
        }
    }

    @Override
    public void finishExport() {
        if (factWriter != null) {
            factWriter.println("\n]");
            factWriter.close();
        }
        if (dimWriter != null) {
            dimWriter.println("\n]");
            dimWriter.close();
        }
    }
}
