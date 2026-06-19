package model;

import composite.MenuComponent;
import observer.Observer;
import observer.Subject;
import state.NewOrderState;
import state.OrderState;

import java.util.ArrayList;
import java.util.List;

public class Order implements Subject {
    private List<Observer> observers;
    private List<MenuComponent> items;
    private OrderState state;
    private int orderId;
    private static int orderCounter = 1;

    public Order() {
        observers = new ArrayList<>();
        items = new ArrayList<>();
        state = new NewOrderState();
        orderId = orderCounter++;
    }

    public void addItem(MenuComponent item) {
        items.add(item);
        notifyObservers();
    }

    public void removeItem(MenuComponent item) {
        items.remove(item);
        notifyObservers();
    }
    
    public List<MenuComponent> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for (MenuComponent item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public void setState(OrderState state) {
        this.state = state;
        notifyObservers();
    }

    public OrderState getState() {
        return state;
    }

    public void nextState() {
        state.next(this);
    }

    public void prevState() {
        state.prev(this);
    }

    public int getOrderId() {
        return orderId;
    }

    public String getOrderDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Order #").append(orderId).append(" - Status: ").append(state.getStatus()).append("\n");
        for (MenuComponent item : items) {
            details.append(" - ").append(item.getName()).append(": $").append(String.format("%.2f", item.getPrice())).append("\n");
        }
        details.append("Total: $").append(String.format("%.2f", getTotal()));
        return details.toString();
    }

    @Override
    public void registerObserver(Observer o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        String details = getOrderDetails();
        for (Observer observer : observers) {
            observer.update(details);
        }
    }
}
