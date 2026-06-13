package groups;

import model.Movie;

public class RatingService {

    public RatingService() {
    }


    public void likeMovie(User user, Movie movie){
      user.userLike(movie);
    }
  
  
}
