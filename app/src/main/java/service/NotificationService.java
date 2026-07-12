package service;

import java.util.ArrayList;
import java.util.List;

import model.Invite;
import model.Notification;
import model.User;

public class NotificationService {

    private static final List<Notification> notifications =
            new ArrayList<>();

    public static void add(Notification notification){
        notifications.add(notification);
    }

    public static List<Notification> getNotifications(User user){

        return notifications.stream()
                .filter(n -> {

                    if(n instanceof Invite invite)
                        return invite.getReceiver().equals(user);

                    return true;
                })
                .toList();
    }

    public static void remove(Notification notification) {
        notifications.remove(notification);
    }
}