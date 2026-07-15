package model;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import controller.Session;
import dto.GroupDTO;
import model.observer.Publisher;
import model.observer.Subscriber;
import service.Services;
import service.dataManager.DataManager;

public class Group implements Subscriber {

  private final Publisher publisher;
  private int numOfUsers;
  private Map<Integer, Integer> likedMovies = new HashMap<>();
  private final ArrayList<Movie> groupMatches = new ArrayList<>();
  private final String id;
  private final String name;
  private ImageIcon profileImage;

  public Group(String name) {
    publisher = new Publisher();
    numOfUsers = 0;
    this.name = name;
    this.id = name + "_" + System.currentTimeMillis();
  }

  /**
   * Reconstroi um {@link Group} a partir do seu {@link GroupDTO}.
   * <p>
   * Restaura apenas o estado persistido (nome, numero de usuarios e likes). Os
   * inscritos do {@link Publisher} sao estado de runtime e nao sao recuperados:
   * o publisher comeca vazio e os usuarios precisam reentrar no grupo via
   * {@link #addUser(User)}.
   * </p>
   *
   * @param dto representacao serializavel do grupo.
   */
  public Group(GroupDTO dto) {
    publisher = new Publisher();
    this.id = dto.id();
    this.name = dto.name();
    this.numOfUsers = dto.numOfUsers();
    this.likedMovies = new HashMap<>(dto.likedMovies());
  }

  /**
   * Converte este grupo na sua representacao serializavel.
   *
   * @return um {@link GroupDTO} com o estado persistivel deste grupo.
   */
  public GroupDTO toDTO() {
    return new GroupDTO(id, name, numOfUsers, new HashMap<>(likedMovies));
  }

  /**
   * Grava (ou sobrescreve) o arquivo deste grupo na tabela {@code group},
   * usando o {@code id} do grupo como nome do arquivo ({@code group%<id>.json}).
   */
  public void saveGroup() {
    DataManager manager = Services.getManager();
    manager.createData("group", this.id, this.toDTO());
  }

  @Override
  public void beNotified(String action, Object object) {
    Movie movie = (Movie) object;
    if (action.equals("like")) {
      if (likedMovies.containsKey(movie.getId())) {
        likedMovies.put(movie.getId(), likedMovies.get(movie.getTitle()) + 1);
      } else {
        likedMovies.put(movie.getId(), 1);
      }
      checkMatch(movie.getId());
    } else if (action.equals("dislike")) {
      System.out.println("disliked");
    }
    saveGroup();
  }

  public void checkMatch(Integer movieId) {
    int numOfLikes = likedMovies.get(movieId);
    if (numOfLikes == numOfUsers) {
      // Passar o filme no match
      Match match = new Match(movieId, this.getName());
      publisher.toNotify("match", match);
      Session.logAction = "match";
    } else {
      Session.logAction = "check_match";
    }
  }

  public void addUser(User user) {
    addToPublisher(user);
    numOfUsers += 1;
  }

  public void addToPublisher(User user){
    publisher.addSubscriber(user);
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public ArrayList<Movie> getMatches() {
    return groupMatches;
  }

  public int getNumOfUsers() {
    return this.numOfUsers;
  }

  public int getSubsSize() {
    return publisher.getSubsSize();
  }

  public Map<Integer, Integer> getLikedMovies() {
    return likedMovies;
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

}
