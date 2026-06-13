package model;

import javax.swing.ImageIcon;

import model.observer.Publisher;
import model.observer.Subscriber;
 
public class User implements Subscriber{
  
  private Publisher publisher;
  private String name;
  private String email;
  private ImageIcon profileImage;

  public User(String name, String email){
    this.name = name;
    this.email = email;

    publisher = new Publisher();
  }

  public String getName() {
      return name;
  }

  public String getEmail() {
      return email;
  }

  public ImageIcon getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(ImageIcon profileImage) {
      this.profileImage = profileImage;
      publisher.toNotify("profileUpdate", this);
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
