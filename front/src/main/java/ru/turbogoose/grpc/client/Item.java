package ru.turbogoose.grpc.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Item {
    private int id;
    private String name;
}
