package etl;

import java.util.ArrayList;
import java.util.List;

public class HistoricalOrder {
    private String orderId;
    private String date;
    private double total;
    private List<String> items = new ArrayList<>();

    public HistoricalOrder(String orderId, String date, double total) {
        this.orderId = orderId;
        this.date = date;
        this.total = total;
    }

    public void addItem(String item) { items.add(item); }
    
    public String getOrderId() { return orderId; }
    public String getDate() { return date; }
    public double getTotal() { return total; }
    public List<String> getItems() { return items; }

    // Accept method for Visitor Pattern
    public void accept(ExportVisitor visitor) {
        visitor.visit(this);
    }
}
