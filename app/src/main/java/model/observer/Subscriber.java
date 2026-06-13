package model.observer;

public interface Subscriber {
  
  public abstract void beNotified(String action, Object object);

}
