package ru.turbogoose.grpc.client;

public interface Prompts {
    String MENU = """
            \n------------------------
            Choose option:
            1) Create item
            2) Get item by id
            3) Get page of items
            4) Delete items
            5) Exit
            """;
}
