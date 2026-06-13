package model;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import model.observer.Publisher;
import model.observer.Subscriber;
 

public class User implements Subscriber{
  
  private Publisher publisher;
  private String name;
  private String email;
  private ImageIcon profileImage;
  private ArrayList<String> likedMovies;
  private ArrayList<String> groups;

  public User(String name, String email){
    this.name = name;
    this.email = email;

    publisher = new Publisher();
  }

  public User(String name, String email, UserProfileDTO userInfo){
    this(name, email);
    this.likedMovies = userInfo.likedMovies();
    this.groups = userInfo.groups();
  
    if (userInfo.pathPhotoFile() != null && !userInfo.pathPhotoFile().equals("")) {
      loadProfileImage(userInfo.pathPhotoFile());
    }
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
  }

  public void loadProfileImage(String path) {
    File file = new File(path);
    
    if (file.exists()) {
        ImageIcon originalIcon = new ImageIcon(file.getAbsolutePath());
        Image scaledImage = originalIcon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
        this.profileImage = new ImageIcon(scaledImage);
    }
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
