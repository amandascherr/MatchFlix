package dto;

public record MatchDTO(
    String movie,
    String group
) implements NotificationDTO{}
