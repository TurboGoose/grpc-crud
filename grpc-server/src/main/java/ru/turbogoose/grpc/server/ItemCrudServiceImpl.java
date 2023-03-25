package ru.turbogoose.grpc.server;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.turbogoose.grpc.ItemCrudServiceGrpc;
import ru.turbogoose.grpc.ItemCrudServiceOuterClass;

@GrpcService
@RequiredArgsConstructor
public class ItemCrudServiceImpl extends ItemCrudServiceGrpc.ItemCrudServiceImplBase {
    private final ItemsService itemsService;

    @Override
    public void getItemById(ItemCrudServiceOuterClass.ItemId request,
                            StreamObserver<ItemCrudServiceOuterClass.ItemInfo> responseObserver) {
        responseObserver.onNext(itemsService.getItemById(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getPageOfItems(ItemCrudServiceOuterClass.ItemPaginatedRequest request,
                               StreamObserver<ItemCrudServiceOuterClass.ItemInfoPaginatedResponse> responseObserver) {
        responseObserver.onNext(itemsService.getItemsPage(request));
        responseObserver.onCompleted();
    }

    @Override
    public void createItem(ItemCrudServiceOuterClass.ItemCreateRequest request, StreamObserver<ItemCrudServiceOuterClass.ItemInfo> responseObserver) {
        responseObserver.onNext(itemsService.createItem(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateItem(ItemCrudServiceOuterClass.ItemInfo request, StreamObserver<ItemCrudServiceOuterClass.ItemInfo> responseObserver) {
        responseObserver.onNext(itemsService.updateItem(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteItemById(ItemCrudServiceOuterClass.ItemId request,
                               StreamObserver<ItemCrudServiceOuterClass.Empty> responseObserver) {
        responseObserver.onNext(itemsService.deleteItemById(request));
        responseObserver.onCompleted();
    }
}
