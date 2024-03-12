import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Component interface
interface MenuComponent {
    void display();
}

// Leaf class representing a single menu item
class MenuItem implements MenuComponent {
    private String name;

    public MenuItem(String name) {
        this.name = name;
    }

    public void display() {
        System.out.println(name);
    }
}

// Composite class representing a menu containing submenus or menu items
class Menu implements MenuComponent {
    private String name;
    private List<MenuComponent> menuComponents = new ArrayList<>();

    public Menu(String name) {
        this.name = name;
    }

    public void add(MenuComponent component) {
        menuComponents.add(component);
    }

    public void remove(MenuComponent component) {
        menuComponents.remove(component);
    }

    public void display() {
        System.out.println("Menu: " + name);
        for (MenuComponent component : menuComponents) {
            component.display();
        }
    }
}

public class MenuExample {
    public static void main(String[] args) {
        // Create main menu
        Menu mainMenu = new Menu("Main Menu");

        // Create submenus for starters, main meals, and desserts
        Menu starterMenu = new Menu("Starters");
        Menu mainMealMenu = new Menu("Main Meals");
        Menu dessertMenu = new Menu("Desserts");

        // Add starters, main meals, and desserts menus to the main menu
        mainMenu.add(starterMenu);
        mainMenu.add(mainMealMenu);
        mainMenu.add(dessertMenu);

        // Create menu items for starters
        MenuItem saladItem = new MenuItem("Salad");
        MenuItem soupItem = new MenuItem("Soup");
        MenuItem breadItem = new MenuItem("Breadsticks");

        // Create menu items for main meals
        MenuItem tacosItem = new MenuItem("Tacos");
        MenuItem pastaItem = new MenuItem("Pasta");
        MenuItem pizzaItem = new MenuItem("Pizza");

        // Create menu items for desserts
        MenuItem cakeItem = new MenuItem("Cake");
        MenuItem iceCreamItem = new MenuItem("Ice Cream");
        MenuItem fruitItem = new MenuItem("Fruit Salad");

        // Add menu items to the submenus
        starterMenu.add(saladItem);
        starterMenu.add(soupItem);
        starterMenu.add(breadItem);

        mainMealMenu.add(tacosItem);
        mainMealMenu.add(pastaItem);
        mainMealMenu.add(pizzaItem);

        dessertMenu.add(cakeItem);
        dessertMenu.add(iceCreamItem);
        dessertMenu.add(fruitItem);

        // Display the main menu
        mainMenu.display();

        // Simulate user interaction by selecting a submenu
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the submenu to display (Starters/Main Meals/Desserts): ");
        String selectedMenu = scanner.nextLine().trim().toLowerCase();

        // Display the selected submenu
        switch (selectedMenu) {
            case "starters":
                starterMenu.display();
                break;
            case "main meals":
                mainMealMenu.display();
                break;
            case "desserts":
                dessertMenu.display();
                break;
            default:
                System.out.println("Invalid submenu selection.");
        }

        scanner.close();
    }
}
