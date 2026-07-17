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
     * Construtor padrao usado pelas subclasses.
     */
    protected Notification() {
    }

    /**
     * Indica se a notificação já foi lida.
     *
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
     * Devolve o texto a ser exibido ao usuário para esta notificação.
     *
     * @return o texto a ser exibido ao usuário para esta notificação.
     */
    public abstract String getMessage();

    /**
     * Converte esta notificação na sua representação serializável.
     *
     * @return a representação serializável desta notificação.
     */
    public abstract NotificationDTO toDTO();

}