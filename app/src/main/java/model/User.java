package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import controller.MatchController;
import dto.GroupDTO;
import dto.InviteDTO;
import dto.MatchDTO;
import dto.NotificationDTO;
import dto.UserProfileDTO;
import model.observer.Publisher;
import model.observer.Subscriber;
import service.Services;
import service.TMDBService;
import service.dataManager.DataManager;
import util.Loader;
 

/**
 * Representa um usuário da aplicação e centraliza suas regras de negócio.
 * <p>
 * Um usuário mantém seus filmes curtidos, os grupos dos quais participa e as
 * notificações recebidas. No padrão Observer, atua como {@link Subscriber}
 * (recebe matches via {@link #beNotified(String, Object)}) e possui um
 * {@link Publisher} próprio para propagar suas curtidas aos grupos inscritos.
 * </p>
 */
public class User implements Subscriber{

  private final Publisher publisher;
  private final String name;
  private final String email;
  private ImageIcon profileImage;
  private final ArrayList<Movie> likedMovies;
  private final ArrayList<Group> groups;
  private final ArrayList<Notification> notifications;
  private final DataManager manager = Services.getManager();
  private CompletableFuture<Void> loadMoviesFuture;

  /**
   * Cria um usuário novo, com listas de filmes, grupos e notificações vazias.
   *
   * @param name  nome do usuário.
   * @param email email do usuário, usado como identificador único.
   */
  public User(String name, String email){
    this.name = name;
    this.email = email;
    this.likedMovies = new ArrayList<>();
    this.groups = new ArrayList<>();
    this.notifications = new ArrayList<>();
    
    publisher = new Publisher();  
  }

  /**
   * Reconstrói um usuário a partir do seu perfil persistido.
   * <p>
   * Os filmes curtidos são recarregados de forma assíncrona pela API (TMDB),
   * os grupos e notificações são reinstanciados a partir dos seus DTOs e o
   * usuário volta a se inscrever no {@link Publisher} de cada grupo. A foto de
   * perfil só é carregada quando há um caminho de arquivo válido.
   * </p>
   *
   * @param userInfo perfil serializado do usuário.
   */
  public User(UserProfileDTO userInfo){
    this(userInfo.name(), userInfo.email());

    loadMoviesFuture = loadMoviesByAPI(userInfo.likedMovies()).thenAccept(r -> {
      System.out.println("Filmes curtidos carredados");
    });


    if (userInfo.groups() != null) {
      DataManager manager = Services.getManager();
      for (String groupId : userInfo.groups()) {
        List<GroupDTO> groupData = manager.readData("group", groupId, GroupDTO.class);
        if (groupData != null && !groupData.isEmpty()) {
          Group group = new Group(groupData.get(0));
          group.addToPublisher(this);
          this.groups.add(group);
          this.publisher.addSubscriber(group);
          
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
      this.profileImage = Loader.loadProfileImage(userInfo.pathPhotoFile());
    }
  }

  /**
   * Carrega de forma assíncrona os filmes curtidos a partir dos seus ids,
   * consultando o {@link TMDBService}. Ids que falharem na consulta interrompem
   * o carregamento restante.
   *
   * @param likedMoviesIds ids dos filmes previamente curtidos.
   * @return um {@link CompletableFuture} concluído quando o carregamento termina.
   */
  private CompletableFuture<Void> loadMoviesByAPI(List<Integer> likedMoviesIds){
    TMDBService service = Services.getTMDBService();

    return CompletableFuture.runAsync(() -> {
      for (int id : likedMoviesIds) {
        try {
          this.likedMovies.add(service.getMovieById(id));
        }
        catch(Exception e) {
          return;
        }
      }
    });
  }

  public String getName() {
      return name;
  }

  public String getEmail() {
      return email;
  }

  /**
   * Devolve uma cópia da lista de filmes curtidos. O acesso é sincronizado
   * porque a lista pode ser preenchida em paralelo pelo carregamento assíncrono.
   *
   * @return uma nova lista com os filmes curtidos pelo usuário.
   */
  public ArrayList<Movie> getLikedMovies() {
    synchronized(likedMovies){
      return new ArrayList<>(likedMovies);
    }
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

 
  /**
   * Registra a curtida de um filme pelo usuário.
   * <p>
   * A operação espera o carregamento inicial dos filmes concluir e então, na
   * thread de UI, adiciona o filme, persiste o perfil e notifica os grupos
   * inscritos com a ação {@code "like"} — o que pode disparar um match.
   * </p>
   *
   * @param movie filme curtido.
   */
  public void userLike(Movie movie){
    loadMoviesFuture.thenRun( () -> {
      SwingUtilities.invokeLater(()->{
        likedMovies.add(movie);
        saveUser();
        publisher.toNotify("like", movie);
      });
    });
  }

  /**
   * Registra a rejeição de um filme, notificando os grupos com a ação
   * {@code "dislike"}. Não altera a lista de curtidos.
   *
   * @param movie filme rejeitado.
   */
  public void userDislike(Movie movie){
    publisher.toNotify("dislike", movie);
  }

  /**
   * Faz o usuário ingressar em um grupo, ignorando a operação se ele já for
   * membro (mesmo {@code id}). Inscreve o grupo no publisher do usuário e
   * adiciona o usuário ao grupo.
   *
   * @param group grupo a ser ingressado.
   */
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

  /**
   * Recebe a notificação de um match originada por um grupo: registra o
   * {@link Match} entre as notificações do usuário e persiste o perfil.
   *
   * @param action ação notificada (esperado {@code "match"}).
   * @param object o {@link Match} ocorrido.
   */
  @Override
  public void beNotified(String action, Object object) {
    Match match = (Match) object;
    notifications.add(match);
    MatchController.saveMatch(this);
  }

  public ArrayList<Notification> getNotifications(){
    return this.notifications;
  }

  /**
   * Persiste o perfil do usuário, preservando senha e foto já gravados e
   * atualizando filmes curtidos, grupos e notificações a partir do estado atual.
   */
  public void saveUser() {
    UserProfileDTO actualUserInfo = manager.readData("user", email, UserProfileDTO.class).get(0);

    UserProfileDTO updatedUserInfo = new UserProfileDTO(
      name,
      email,
      actualUserInfo.password(),
      actualUserInfo.pathPhotoFile(),
      new ArrayList<>(likedMovies.stream().map(Movie::getId).toList()),
      new ArrayList<>(groups.stream().map(Group::getId).toList()),
      new ArrayList<>(notifications.stream().map(Notification::toDTO).toList())
    );

    manager.createData("user", email, updatedUserInfo);
  }
}

