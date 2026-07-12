package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import controller.Session;
import service.RatingService;



public class GroupTest {
  
  @Test
  public void addUserTest(){
    Group group = new Group("Conpecs");
    User user1 = new User("Bixao", "nobrega");
    user1.joinGroup(group);
    assertEquals(1, group.getNumOfUsers());
    assertEquals(1, group.getSubsSize());
  }

  @Test
  public void beNotifiedLikeTest(){
    Group group = new Group("Conpecs");
    User user1 = new User("Bixao", "nobrega");
    User user2 = new User("Scherr", "Amanda");
    user1.joinGroup(group);
    user2.joinGroup(group);
    Movie movie = new Movie(0, "OOO", "oooo", "oooo");
    RatingService ratingService = new RatingService();
    ratingService.likeMovie(user1, movie);
    assertEquals(1, group.getLikedMovies().get(movie.getTitle()));
  }

  @Test
  public void beNotifiedMatchTest(){
    Group group = new Group("Conpecs");

    User user1 = new User("Bixao", "nobrega");
    User user2 = new User("Scherr", "Amanda");
    User user3 = new User("Gustavin", "Selva");

    user1.joinGroup(group);
    user2.joinGroup(group);
    user3.joinGroup(group);
    Movie movie = new Movie(0, "OOO", "oooo", "oooo");
    RatingService ratingService = new RatingService();
    ratingService.likeMovie(user1, movie);
    assertEquals(1, group.getLikedMovies().get(movie.getTitle()));
    System.out.println(Session.logAction);
    assertEquals("check_match", Session.logAction);
    ratingService.likeMovie(user2, movie);
    assertEquals(2, group.getLikedMovies().get(movie.getTitle()));
    assertEquals("check_match", Session.logAction);
    ratingService.likeMovie(user3, movie);
    assertEquals(3, group.getLikedMovies().get(movie.getTitle()));
    assertEquals("match", Session.logAction);


  }

}
