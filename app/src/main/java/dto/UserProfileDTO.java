package dto;

import java.util.ArrayList;

public record UserProfileDTO(
    String name,
    String email,
    String password,
    String pathPhotoFile,
    ArrayList<Integer> likedMovies,
    ArrayList<String> groups,
    ArrayList<NotificationDTO> notifications
) {}
