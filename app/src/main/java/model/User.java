package model;

import model.observer.Publisher;
import model.observer.Subscriber;
 
public class User implements Subscriber{
  
  private Publisher publisher;

  public User(){
    publisher = new Publisher();
  }

  public void userLike(Movie movie){
    publisher.toNotify("like", movie);
  }

  public void userDislike(Movie movie){
    publisher.toNotify("dislike", movie);
  }

  public void joinGroup(Group group){
    publisher.addSubscriber(group);
    group.addUser(this);
  } 

  public void beNotified(String action, Object object) {
    Match match = (Match) object;
    System.out.println("Título do filme: " + match.getMovie().getTitle());
    System.out.println("Nome do grupo: " + match.getGroup().getName());
  }
}
