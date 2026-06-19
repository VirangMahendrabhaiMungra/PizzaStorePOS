package database;

import model.Order;
import java.util.List;

public interface OrderDAO {
    void saveOrder(Order order);
    List<String> getAllOrders();
}
