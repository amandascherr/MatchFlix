package service.dataManager;

public record DataDTO<Body extends Record>(
    String id,
    Body body
) {
}
