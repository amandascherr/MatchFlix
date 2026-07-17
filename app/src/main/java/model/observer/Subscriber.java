package model.observer;

/**
 * Contrato do inscrito (Subscriber) no padrão Observer. Implementado por quem
 * precisa reagir às notificações de um {@link Publisher}, como {@code User} e
 * {@code Group}.
 */
public interface Subscriber {

  /**
   * Reage a uma notificação enviada pelo {@link Publisher}.
   *
   * @param action ação ou evento notificado.
   * @param object dado associado à ação (ex.: um filme ou um match).
   */
  public abstract void beNotified(String action, Object object);

}
