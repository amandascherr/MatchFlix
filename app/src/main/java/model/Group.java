package model;

import java.util.HashMap;
import java.util.Map;

import model.observer.Subscriber;
import model.observer.Publisher;

public class Group implements Subscriber {

  private Publisher publisher;
  private int numOfUsers;
  private Map<String, Integer> likedMovies = new HashMap<>();
  private String name;
  

  public Group(String name){
    publisher = new Publisher();
    numOfUsers = 0;
    this.name = name;
  }

  public void beNotified(String action, Object object) {
    Movie movie = (Movie) object;
    if (action.equals("like")){
      if(likedMovies.containsKey(movie.getTitle())){
        likedMovies.put(movie.getTitle(), likedMovies.get(movie.getTitle()) + 1);
        checkMatch(movie);
      } else {
        likedMovies.put(movie.getTitle(), 1);
      }
    } else if(action.equals("dislike")){
      System.out.println("disliked");
    }
  }

  private void checkMatch(Movie movie){
    int numOfLikes = likedMovies.get(movie.getTitle());
    if (numOfLikes == numOfUsers){
      // Passar o filme no match
      Match match = new Match(movie, this);
      publisher.toNotify("match", match);
    }
  }

  public void addUser(User user){
    publisher.addSubscriber(user);
    numOfUsers += 1;
  }

  public String getName(){
    return this.name;
  }
  
}
