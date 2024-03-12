package Given.composite;

import java.util.*;

interface Component {
    void showPrice();
}

// 1
class Leaf implements Component {
    int price;
    String name;

    public Leaf(int price, String name) {
        // super();
        this.price = price;
        this.name = name;
    }

    @Override
    public void showPrice() {
        System.out.println(name + ":" + price);
    }
}

// 2
class Composite implements Component {
    String name;
    List<Component> components = new ArrayList();

    public Composite(String name) {
        // super();
        this.name = name;
    }

    public void addComponent(Component com) {
        components.add(com);
    }

    @Override
    public void showPrice() {
        System.out.println(name);
        for (Component c : components) {
            c.showPrice();
        }
    }
}

public class ComputerPart {

}