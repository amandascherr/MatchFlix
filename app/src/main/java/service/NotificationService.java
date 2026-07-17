package service;

import java.util.ArrayList;
import java.util.List;

import model.Invite;
import model.Notification;
import model.User;

/**
 * Serviço que mantém, em memória, as notificações geradas durante a execução da
 * aplicação e permite consultá-las por usuário.
 */
public class NotificationService {

    private static final List<Notification> notifications =
            new ArrayList<>();

    /**
     * Registra uma notificação.
     *
     * @param notification notificação a ser armazenada.
     */
    public static void add(Notification notification){
        notifications.add(notification);
    }

    /**
     * Devolve as notificações relevantes para um usuário: convites destinados a
     * ele e demais notificações não específicas de destinatário.
     *
     * @param user usuário destinatário.
     * @return as notificações visíveis para o usuário.
     */
    public static List<Notification> getNotifications(User user){

        return notifications.stream()
                .filter(n -> {

                    if(n instanceof Invite invite)
                        return invite.getReceiver().equals(user);

                    return true;
                })
                .toList();
    }

    /**
     * Remove uma notificação do registro.
     *
     * @param notification notificação a ser removida.
     */
    public static void remove(Notification notification) {
        notifications.remove(notification);
    }
}