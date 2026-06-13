package model;

import java.util.ArrayList;

public record UserProfileDTO(
    String pathPhotoFile,
    ArrayList<String> likedMovies,
    ArrayList<String> groups
) {}
