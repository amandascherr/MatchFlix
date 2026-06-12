package groups;

import java.util.ArrayList;
import java.util.concurrent.Flow;


public class Publisher {
  
  public ArrayList<Subscriber> subscribers = new ArrayList<Subscriber>();

  public void Notify(String action){
    for (Subscriber subscriber : subscribers){
      // Utilizar Lógica Strategy possivelmente
      subscriber.beNoitified(action);
    }
  }

}
