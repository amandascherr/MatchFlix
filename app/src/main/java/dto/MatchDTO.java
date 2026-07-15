package dto;

public record MatchDTO(
    Integer movieId,
    String group
) implements NotificationDTO{}
