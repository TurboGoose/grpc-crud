package ru.turbogoose.grpc.client;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleClient {
    static {
        Logger.getLogger("io.netty").setLevel(Level.OFF);
    }

    private final String MENU_PROMPT = """
            \n------------------------
            Choose option:
            1) Create item
            2) Get item by id
            3) Get page of items
            4) Update item
            5) Delete item by id
            6) Exit
            """;

    private Scanner sc;
    private final ItemService itemService = new ItemService();

    private void run() {
        try (Scanner scan = new Scanner(System.in)) {
            sc = scan;

            boolean running = true;
            while (running) {
                try {
                    System.out.println(MENU_PROMPT);
                    System.out.print("Your choice: ");
                    String choice = sc.nextLine().trim();
                    switch (choice) {
                        case "1" -> runCreationDialog();
                        case "2" -> runRetrievingDialog();
                        case "3" -> runPageRetrievingDialog();
                        case "4" -> runUpdatingDialog();
                        case "5" -> runDeletingDialog();
                        case "6" -> running = false;
                        default -> System.out.println("Wrong input, try again");
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        }
    }

    private void runCreationDialog() {
        System.out.print("Enter item name: ");
        String name = sc.nextLine().trim();
        if (name.isBlank()) {
            System.out.println("Name cannot be empty");
            return;
        }
        Item itemToCreate = Item.builder().name(name).build();
        Item created = itemService.createItem(itemToCreate);
        System.out.println("Created item: " + created);
    }

    private void runRetrievingDialog() {
        System.out.print("Enter item id: ");
        int id = sc.nextInt();
        sc.nextLine();
        Item item = itemService.getItemById(id);
        System.out.println("Retrieved item: " + item);
    }

    private void runPageRetrievingDialog() {
        System.out.print("Enter number of page: ");
        int page = Math.max(sc.nextInt() - 1, 0);
        sc.nextLine();

        System.out.print("Enter size of page: ");
        int size = sc.nextInt();
        sc.nextLine();

        List<Item> items = itemService.getPageOfItems(page, size);
        System.out.println("Retrieved items:");
        items.forEach(System.out::println);
    }

    private void runUpdatingDialog() {
        System.out.print("Enter existing item id: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter item name: ");
        String name = sc.nextLine().trim();
        if (name.isBlank()) {
            System.out.println("Name cannot be empty");
            return;
        }
        Item itemToUpdate = Item.builder().id(id).name(name).build();
        Item updated = itemService.updateItem(itemToUpdate);
        System.out.println("Updated item: " + updated);
    }

    private void runDeletingDialog() {
        System.out.print("Enter item id: ");
        int id = sc.nextInt();
        sc.nextLine();
        itemService.deleteItemById(id);
        System.out.println("Deleted");
    }

    public static void main(String[] args) {
        new ConsoleClient().run();
    }
}
