package dto;

import java.time.Duration;

public record MovieDTO(
    String name, 
    String desc, 
    String posterTag,
    Duration duration
) {
}