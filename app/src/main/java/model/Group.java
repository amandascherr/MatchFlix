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

/**
 * Representa um grupo de usuários e concentra a regra de match do aplicativo.
 * <p>
 * No padrão Observer, o grupo é {@link Subscriber} das curtidas dos usuários e
 * mantém um {@link Publisher} para notificar seus membros quando ocorre um
 * match. Para cada filme, o grupo contabiliza quantos membros o curtiram; um
 * match acontece quando todos os membros ({@code numOfUsers}) curtem o mesmo
 * filme.
 * </p>
 */
public class Group implements Subscriber {

  private final Publisher publisher;
  private int numOfUsers;
  private Map<Integer, Integer> likedMovies = new HashMap<>();
  private final ArrayList<Integer> groupMatches;
  private final String id;
  private final String name;
  private ImageIcon profileImage;

  /**
   * Cria um grupo vazio com o nome informado. O {@code id} é gerado
   * concatenando o nome com o instante de criação, garantindo unicidade.
   *
   * @param name nome do grupo.
   */
  public Group(String name) {
    publisher = new Publisher();
    numOfUsers = 0;
    this.name = name;
    this.id = name + "_" + System.currentTimeMillis();
    this.groupMatches = new ArrayList<>();
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
    this.groupMatches = new ArrayList<>(dto.groupMatches());
  }

  /**
   * Converte este grupo na sua representacao serializavel.
   *
   * @return um {@link GroupDTO} com o estado persistivel deste grupo.
   */
  public GroupDTO toDTO() {
    return new GroupDTO(id, name, numOfUsers, new HashMap<>(likedMovies), new ArrayList<>(groupMatches));
  }

  /**
   * Grava (ou sobrescreve) o arquivo deste grupo na tabela {@code group},
   * usando o {@code id} do grupo como nome do arquivo ({@code group%<id>.json}).
   */
  public void saveGroup() {
    DataManager manager = Services.getManager();
    manager.createData("group", this.id, this.toDTO());
  }

  /**
   * Reage a uma curtida ou rejeição de filme feita por um membro.
   * <p>
   * Numa curtida ({@code "like"}) de um filme que ainda não deu match, o voto é
   * contabilizado e {@link #checkMatch(Integer)} é avaliado. Ao final, o estado
   * do grupo é persistido.
   * </p>
   *
   * @param action ação notificada ({@code "like"} ou {@code "dislike"}).
   * @param object o {@link Movie} alvo da ação.
   */
  @Override
  public void beNotified(String action, Object object) {
    Movie movie = (Movie) object;
    if (action.equals("like") && !groupMatches.contains(movie.getId())) {
      addLikedMovies(movie);
      checkMatch(movie.getId());
    } else if (action.equals("dislike")) {
      System.out.println("disliked");
    }
    saveGroup();
  }

  /**
   * Verifica se um filme atingiu o match, ou seja, se todos os membros do grupo
   * o curtiram. Havendo match, cria um {@link Match}, notifica os membros e
   * registra o filme em {@code groupMatches} para não repetir o match.
   *
   * @param movieId id do filme a ser avaliado.
   */
  public void checkMatch(Integer movieId) {
    int numOfLikes = likedMovies.get(movieId);
    if (numOfLikes == numOfUsers) {
      // Passar o filme no match
      Match match = new Match(movieId, this.getName());
      publisher.toNotify("match", match);
      Session.logAction = "match";
      if (!groupMatches.contains(movieId)){
        groupMatches.add(movieId);
      }
    } else {
      Session.logAction = "check_match";
    }
  }

  /**
   * Adiciona um usuário ao grupo: contabiliza os filmes que ele já curtiu,
   * inscreve-o no publisher do grupo e incrementa o total de membros.
   *
   * @param user usuário que entra no grupo.
   */
  public void addUser(User user) {
    for (Movie movie : user.getLikedMovies()){
      addLikedMovies(movie);
    }
    addToPublisher(user);
    numOfUsers += 1;
  }

  /**
   * Incrementa a contagem de curtidas de um filme no grupo, criando a entrada
   * quando for o primeiro voto.
   *
   * @param movie filme curtido.
   */
  private void addLikedMovies(Movie movie){
    if (likedMovies.containsKey(movie.getId())) {
      likedMovies.put(movie.getId(), likedMovies.get(movie.getId()) + 1);
    } else {
      likedMovies.put(movie.getId(), 1);
    }

    if (!groupMatches.contains(movie.getId())){
        
    }

  }

  /**
   * Inscreve um usuario no publisher do grupo, para que passe a receber as
   * notificacoes de match.
   *
   * @param user usuario a inscrever.
   */
  public void addToPublisher(User user){
    publisher.addSubscriber(user);
  }

  /** {@return o identificador unico do grupo} */
  public String getId() {
    return this.id;
  }

  /** {@return o nome do grupo} */
  public String getName() {
    return this.name;
  }

  /** {@return os ids dos filmes que ja deram match no grupo} */
  public ArrayList<Integer> getMatches() {
    return groupMatches;
  }

  /** {@return o numero de membros do grupo} */
  public int getNumOfUsers() {
    return this.numOfUsers;
  }

  /** {@return o numero de inscritos no publisher do grupo} */
  public int getSubsSize() {
    return publisher.getSubsSize();
  }

  /** {@return o mapa de curtidas por id de filme} */
  public Map<Integer, Integer> getLikedMovies() {
    return likedMovies;
  }

  /** {@return a imagem de perfil do grupo, ou {@code null} se nao definida} */
  public ImageIcon getProfileImage() {
    return profileImage;
  }

  /**
   * Define a imagem de perfil do grupo.
   *
   * @param profileImage nova imagem de perfil.
   */
  public void setProfileImage(ImageIcon profileImage) {
    this.profileImage = profileImage;
  }

  /**
   * Carrega a imagem de perfil do grupo a partir de um arquivo, redimensionando-a.
   * Se o arquivo nao existir, a imagem nao e alterada.
   *
   * @param path caminho do arquivo de imagem.
   */
  public void loadProfileImage(String path) {
    File file = new File(path);

    if (file.exists()) {
      ImageIcon originalIcon = new ImageIcon(file.getAbsolutePath());
      Image scaledImage = originalIcon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
      this.profileImage = new ImageIcon(scaledImage);
    }
  }

}
