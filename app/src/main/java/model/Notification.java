package model;

import model.dto.NotificationDTO;

public abstract class Notification {

    private boolean read;

    public boolean isRead() {
        return read;
    }

    public void markAsRead() {
        read = true;
    }

    public abstract String getMessage();

    public abstract NotificationDTO toDTO();
 
}