package ru.turbogoose.grpc.client;

import java.util.List;
import java.util.Scanner;

public class ConsoleClient {
    private Scanner sc;
    private final ItemService itemService = new ItemService();

    private void run() {
        try (Scanner scan = new Scanner(System.in)) {
            sc = scan;

            boolean running = true;
            while (running) {
                try {
                    System.out.println(Prompts.MENU);
                    String choice = sc.nextLine().trim();
                    switch (choice) {
                        case "1" -> runCreationDialog();
                        case "2" -> runRetrievingDialog();
                        case "3" -> runPageRetrievingDialog();
                        case "4" -> runDeletingDialog();
                        case "5" -> running = false;
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
        Item itemToCreate = Item.builder().name(name).build();
        Item created = itemService.putItem(itemToCreate);
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
