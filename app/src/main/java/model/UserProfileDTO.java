package model;

import java.util.ArrayList;

public record UserProfileDTO(
    String name,
    String email,
    String password,
    String pathPhotoFile,
    ArrayList<String> likedMovies,
    ArrayList<GroupDTO> groups,
    ArrayList<NotificationDTO> notifications
) {}
