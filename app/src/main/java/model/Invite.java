package model;

public class Invite extends Notification {

    private User sender;
    private User receiver;
    private Group group;

    public Invite(User sender, User receiver, Group group) {
        this.sender = sender;
        this.receiver = receiver;
        this.group = group;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public String getMessage() {
        return "Convite para participar de \"" + group.getName() + "\"";
    }
}