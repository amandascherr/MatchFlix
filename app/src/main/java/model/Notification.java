package model;

import dto.NotificationDTO;

/**
 * Base para as notificações exibidas ao usuário, como {@link Match} e
 * {@link Invite}. Guarda o estado de leitura e define o contrato de mensagem e
 * de conversão para o DTO persistível.
 */
public abstract class Notification {

    private boolean read;

    /**
     * @return {@code true} se a notificação já foi lida.
     */
    public boolean isRead() {
        return read;
    }

    /**
     * Marca a notificação como lida.
     */
    public void markAsRead() {
        read = true;
    }

    /**
     * @return o texto a ser exibido ao usuário para esta notificação.
     */
    public abstract String getMessage();

    /**
     * @return a representação serializável desta notificação.
     */
    public abstract NotificationDTO toDTO();

}