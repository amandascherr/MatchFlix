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
  private ArrayList<Movie> likedMovies;
  private ArrayList<Group> groups;

  public User(String name, String email){
    
  }

  public User(UserProfileDTO userInfo){
    this.name = userInfo.name();
    this.email = userInfo.email();
    this.likedMovies = new ArrayList<>();
    // this.likedMovies = userInfo.likedMovies();
    this.groups = new ArrayList<>();
    if (userInfo.groups() != null) {
      for (GroupDTO groupInfo : userInfo.groups()) {
        this.groups.add(new Group(groupInfo));
      }
    }
    
    if (userInfo.pathPhotoFile() != null && !userInfo.pathPhotoFile().equals("")) {
      loadProfileImage(userInfo.pathPhotoFile());
    }
    
    publisher = new Publisher();
  
  }

  public String getName() {
      return name;
  }

  public String getEmail() {
      return email;
  }

  public ArrayList<Movie> getLikedMovies() {
    return likedMovies;
  }

  public ArrayList<Group> getGroups() {
    return groups;
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
    likedMovies.add(movie);
    publisher.toNotify("like", movie);
  }

  public void userDislike(Movie movie){
    publisher.toNotify("dislike", movie);
  }

  public void joinGroup(Group group){
    groups.add(group);
    publisher.addSubscriber(group);
    group.addUser(this);
  }

  public void beNotified(String action, Object object) {
    Match match = (Match) object;
    System.out.println("Título do filme: " + match.getMovie().getTitle());
    System.out.println("Nome do grupo: " + match.getGroup().getName());
  }
}
