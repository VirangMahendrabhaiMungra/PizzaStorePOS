package database;

import model.Order;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileOrderDAOImpl implements OrderDAO {
    private static final String FILE_NAME = "orders_database.csv";

    public FileOrderDAOImpl() {
        // Create file and headers if it doesn't exist
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("OrderID,Date,Total,Details");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveOrder(Order order) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            // Sanitize details to fit in CSV (remove newlines and commas)
            String details = order.getOrderDetails().replace("\n", " | ").replace(",", ";");
            
            writer.printf("%d,%s,%.2f,%s\n", order.getOrderId(), date, order.getTotal(), details);
        } catch (IOException e) {
            System.err.println("Database Error: Could not save order.");
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getAllOrders() {
        List<String> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                orders.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
