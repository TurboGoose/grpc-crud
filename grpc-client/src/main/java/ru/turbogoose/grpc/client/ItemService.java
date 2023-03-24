package ru.turbogoose.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ru.turbogoose.grpc.ItemCrudServiceGrpc;
import ru.turbogoose.grpc.ItemCrudServiceOuterClass;

import java.util.List;

public class ItemService {
    private final ItemCrudServiceGrpc.ItemCrudServiceBlockingStub stub;

    public ItemService() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:9090").usePlaintext().build();
        stub = ItemCrudServiceGrpc.newBlockingStub(channel);
    }

    public Item getItemById(int id) {
        ItemCrudServiceOuterClass.ItemId itemId = ItemCrudServiceOuterClass.ItemId.newBuilder().setId(id).build();
        return mapDtoToItem(stub.getItemById(itemId));
    }

    public List<Item> getPageOfItems(int page, int size) {
        ItemCrudServiceOuterClass.ItemPaginatedRequest paginatedRequest =
                ItemCrudServiceOuterClass.ItemPaginatedRequest.newBuilder()
                        .setPageNumber(page)
                        .setPageLength(size)
                        .build();
        return stub.getPageOfItems(paginatedRequest).getItemsList().stream().map(this::mapDtoToItem).toList();
    }

    public Item createItem(Item item) {
        return mapDtoToItem(stub.createItem(mapItemToCreateRequest(item)));
    }

    public Item updateItem(Item item) {
        return mapDtoToItem(stub.updateItem(mapItemToDto(item)));
    }

    public void deleteItemById(int id) {
        ItemCrudServiceOuterClass.ItemId itemId = ItemCrudServiceOuterClass.ItemId.newBuilder().setId(id).build();
        stub.deleteItemById(itemId);
    }

    private Item mapDtoToItem(ItemCrudServiceOuterClass.ItemInfo itemInfo) {
        return Item.builder()
                .id(itemInfo.getId())
                .name(itemInfo.getName())
                .build();
    }

    private ItemCrudServiceOuterClass.ItemInfo mapItemToDto(Item item) {
        return ItemCrudServiceOuterClass.ItemInfo.newBuilder()
                .setId(item.getId())
                .setName(item.getName())
                .build();
    }

    private ItemCrudServiceOuterClass.ItemCreateRequest mapItemToCreateRequest(Item item) {
        return ItemCrudServiceOuterClass.ItemCreateRequest.newBuilder()
                .setName(item.getName())
                .build();
    }
}
