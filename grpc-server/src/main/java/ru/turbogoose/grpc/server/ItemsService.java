package ru.turbogoose.grpc.server;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.turbogoose.grpc.ItemCrudServiceOuterClass;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemsService {
    private final ItemRepository itemRepository;

    public ItemCrudServiceOuterClass.ItemInfo getItemById(ItemCrudServiceOuterClass.ItemId itemId) {
        return mapItemToDto(itemRepository.getById(itemId.getId()));
    }

    public ItemCrudServiceOuterClass.ItemInfoPaginatedResponse getItemsPage(
            ItemCrudServiceOuterClass.ItemPaginatedRequest pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageLength());

        List<ItemCrudServiceOuterClass.ItemInfo> items = itemRepository.findAll(pageable)
                .map(this::mapItemToDto)
                .toList();
        return ItemCrudServiceOuterClass.ItemInfoPaginatedResponse.newBuilder().addAllItems(items).build();
    }

    public ItemCrudServiceOuterClass.ItemInfo createItem(ItemCrudServiceOuterClass.ItemCreateRequest createRequest) {
        return mapItemToDto(itemRepository.save(mapDtoToItem(createRequest)));
    }

    public ItemCrudServiceOuterClass.ItemInfo updateItem(ItemCrudServiceOuterClass.ItemInfo itemInfo) {
        return mapItemToDto(itemRepository.save(mapDtoToItem(itemInfo)));
    }


    public ItemCrudServiceOuterClass.Empty deleteItemById(ItemCrudServiceOuterClass.ItemId itemId) {
        itemRepository.deleteById(itemId.getId());
        return ItemCrudServiceOuterClass.Empty.newBuilder().build();
    }

    private Item mapDtoToItem(ItemCrudServiceOuterClass.ItemCreateRequest createRequest) {
        return Item.builder()
                .name(createRequest.getName())
                .build();
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
}
