package groups;
 
import model.Movie;

public class User extends Subscriber{
  
  Publisher publisher;

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

  @Override
  public void beNoitified(String action, Object object) {
    Match match = (Match) object;
    System.out.println("Título do filme: " + match.getMovie().getTitle());
    System.out.println("Nome do grupo: " + match.getGroup().getName());

  }

}
