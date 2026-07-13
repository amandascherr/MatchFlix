package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import controller.MatchController;
import model.dto.GroupDTO;
import model.dto.InviteDTO;
import model.dto.MatchDTO;
import model.dto.NotificationDTO;
import model.dto.UserProfileDTO;
import model.observer.Publisher;
import model.observer.Subscriber;
import service.Services;
import service.dataManager.DataManager;
import view.Utils;
 

public class User implements Subscriber{
  
  private final Publisher publisher;
  private final String name;
  private final String email;
  private ImageIcon profileImage;
  private final ArrayList<Movie> likedMovies;
  private final ArrayList<Group> groups;
  private final ArrayList<Notification> notifications;
  private final DataManager manager = Services.getManager();

  public User(String name, String email){
    this.name = name;
    this.email = email;
    this.likedMovies = new ArrayList<>();
    // this.likedMovies = userInfo.likedMovies();
    this.groups = new ArrayList<>();
    this.notifications = new ArrayList<>();
    
    publisher = new Publisher();  
  }

  public User(UserProfileDTO userInfo){
    this(userInfo.name(), userInfo.email());

    if (userInfo.groups() != null) {
      DataManager manager = Services.getManager();
      for (String groupId : userInfo.groups()) {
        List<GroupDTO> groupData = manager.readData("group", groupId, GroupDTO.class);
        if (groupData != null && !groupData.isEmpty()) {
          Group group = new Group(groupData.get(0));
          group.addToPublisher(this);
          this.groups.add(group);
          this.publisher.addSubscriber(group);
          for (String movieTitle : group.getLikedMovies().keySet()){
            group.checkMatch(movieTitle);
          }
        }
      }
    }

    if (userInfo.notifications() != null){
      for (NotificationDTO matchInfo : userInfo.notifications()){
        if (matchInfo instanceof MatchDTO){
          notifications.add(new Match((MatchDTO)matchInfo));
        } else if (matchInfo instanceof InviteDTO){
          notifications.add(new Invite((InviteDTO) matchInfo));
        }
      }
    }
    
    if (userInfo.pathPhotoFile() != null && !userInfo.pathPhotoFile().equals("")) {
      this.profileImage = Utils.loadProfileImage(userInfo.pathPhotoFile());
    }
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

 
  public void userLike(Movie movie){
    likedMovies.add(movie);
    saveUser();
    publisher.toNotify("like", movie);
  }

  public void userDislike(Movie movie){
    saveUser();
    publisher.toNotify("dislike", movie);
  }

  public void joinGroup(Group group){
    for (Group existing : groups) {
      if (existing.getId().equals(group.getId())) {
        return;
      }
    }
    groups.add(group);
    publisher.addSubscriber(group);
    group.addUser(this);
  }

  @Override
  public void beNotified(String action, Object object) {
    Match match = (Match) object;
    notifications.add(match);
    MatchController.saveMatch(this);
  }

  public ArrayList<Notification> getNotifications(){
    return this.notifications;
  }

  private void saveUser() {
    UserProfileDTO actualUserInfo = manager.readData("user", email, UserProfileDTO.class).get(0);
    
    UserProfileDTO updatedUserInfo = new UserProfileDTO(
      name,
      email,
      actualUserInfo.password(),
      actualUserInfo.pathPhotoFile(),
      new ArrayList<>(likedMovies.stream().map(Movie::getTitle).toList()),
      new ArrayList<>(groups.stream().map(Group::getId).toList()),
      new ArrayList<>(notifications.stream().map(Notification::toDTO).toList())
    );

    manager.createData("user", email, updatedUserInfo);
  }
}

