package groups;

public class Group extends Subscriber {

  @Override
  public void beNoitified(String action) {
    if (action.equals("like")){
      System.out.println("Liked");
    } else if(action.equals("dislike")){
      System.out.println("disliked");
    }
  }
  
}
