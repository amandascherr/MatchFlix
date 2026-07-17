package model.observer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Testa o {@link Publisher} do padrao Observer: inscricao, remocao e o repasse
 * de notificacoes a todos os inscritos.
 */
public class PublisherTest {

  /** Inscrito de teste que apenas registra as notificacoes recebidas. */
  private static class RecordingSubscriber implements Subscriber {
    final List<String> actions = new ArrayList<>();
    Object lastObject;

    /**
     * Registra a acao recebida e guarda o ultimo objeto notificado.
     *
     * @param action acao notificada.
     * @param object dado associado a acao.
     */
    @Override
    public void beNotified(String action, Object object) {
      actions.add(action);
      lastObject = object;
    }
  }

  /**
   * Verifica que inscrever subscribers aumenta o tamanho da lista de inscritos.
   */
  @Test
  public void addSubscriberIncreasesSize() {
    Publisher publisher = new Publisher();

    publisher.addSubscriber(new RecordingSubscriber());
    publisher.addSubscriber(new RecordingSubscriber());

    assertEquals(2, publisher.getSubsSize());
  }

  /**
   * Verifica que remover um inscrito diminui o tamanho da lista de inscritos.
   */
  @Test
  public void removeSubscriberDecreasesSize() {
    Publisher publisher = new Publisher();
    RecordingSubscriber subscriber = new RecordingSubscriber();

    publisher.addSubscriber(subscriber);
    publisher.removeSubscriber(subscriber);

    assertEquals(0, publisher.getSubsSize());
  }

  /**
   * Verifica que uma notificacao alcanca todos os inscritos, repassando a acao e
   * o objeto corretos.
   */
  @Test
  public void notifyReachesAllSubscribersWithActionAndObject() {
    Publisher publisher = new Publisher();
    RecordingSubscriber first = new RecordingSubscriber();
    RecordingSubscriber second = new RecordingSubscriber();
    publisher.addSubscriber(first);
    publisher.addSubscriber(second);

    Object payload = new Object();
    publisher.toNotify("like", payload);

    assertEquals(List.of("like"), first.actions);
    assertEquals(List.of("like"), second.actions);
    assertEquals(payload, first.lastObject);
    assertEquals(payload, second.lastObject);
  }

  /**
   * Verifica que um inscrito removido nao recebe mais notificacoes.
   */
  @Test
  public void removedSubscriberIsNotNotified() {
    Publisher publisher = new Publisher();
    RecordingSubscriber subscriber = new RecordingSubscriber();
    publisher.addSubscriber(subscriber);
    publisher.removeSubscriber(subscriber);

    publisher.toNotify("like", new Object());

    assertEquals(0, subscriber.actions.size());
    assertNull(subscriber.lastObject);
  }
}
