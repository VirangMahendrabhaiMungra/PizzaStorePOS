package composite;

import java.util.ArrayList;
import java.util.List;

public class MenuCategory extends MenuComponent {
    private List<MenuComponent> menuComponents = new ArrayList<>();
    private String name;
    private String description;

    public MenuCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void add(MenuComponent menuComponent) {
        menuComponents.add(menuComponent);
    }
    @Override
    public void remove(MenuComponent menuComponent) {
        menuComponents.remove(menuComponent);
    }
    @Override
    public MenuComponent getChild(int i) {
        return menuComponents.get(i);
    }
    @Override
    public String getName() { return name; }
    @Override
    public String getDescription() { return description; }

    @Override
    public void print() {
        System.out.println("\n" + getName() + ", " + getDescription());
        System.out.println("---------------------");
        for (MenuComponent component : menuComponents) {
            component.print();
        }
    }
    
    public List<MenuComponent> getComponents() {
        return menuComponents;
    }
}
