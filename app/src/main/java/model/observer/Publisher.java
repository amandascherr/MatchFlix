package model.observer;

import java.util.ArrayList;

/**
 * Representa um publicador (Publisher) no padrão Observer.
 * <p>
 * O Publisher mantém uma lista de inscritos ({@link Subscriber})
 * e é responsável por notificá-los quando uma determinada ação ocorre.
 * </p>
 */
public class Publisher {

    /**
     * Lista de inscritos que receberão notificações.
     */
    private ArrayList<Subscriber> subscribers = new ArrayList<>();

    /**
     * Notifica todos os inscritos sobre uma ação ocorrida.
     * <p>
     * Para cada {@link Subscriber} registrado, o método
     * {@code beNotified(action)} é chamado.
     * </p>
     *
     * @param action descrição da ação ou evento que será enviado
     *               aos inscritos
     * @param object pode ser tanto um filme como um Match
     */
    public void toNotify(String action, Object movie) {
        for (Subscriber subscriber : subscribers) {
            // Utilizar lógica Strategy possivelmente
            subscriber.beNotified(action, movie);
        }
    }
    /**
     * Inscreve um Subscriber na lista para ser notificado.
     * @param subscriber Elemento a ser adicionado na lista.
     */
    public void addSubscriber(Subscriber subscriber){
      subscribers.add(subscriber);
    }

    /**
     * Remove um Subscriber na lista.
     * @param subscriber Elemento a ser removida da lista.
     */
    public void removeSubscriber(Subscriber subscriber){
      subscribers.remove(subscriber);
    }

    public int getSubsSize(){
      return subscribers.size();
    }

}