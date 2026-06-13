package service;

import model.Movie;
import model.User;

public class RatingService {

    public RatingService() {
    }

    public void likeMovie(User user, Movie movie){
      user.userLike(movie);
    }
}
