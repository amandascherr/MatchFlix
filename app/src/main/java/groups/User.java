package groups;

public class User {
  
  Publisher publisher;

  public User(){
    publisher = new Publisher();
  }


  public void userLike(){
    publisher.toNotify("like");
  }

  public void userDislike(){
    publisher.toNotify("dislike");
  }

  public void joinGroup(Group group){
    publisher.addSubscriber(group);
  } 

  public void leaveGroup(Group group){
    publisher.removeSubscriber(group);
  }

}
