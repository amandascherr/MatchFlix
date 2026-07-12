package model;

public class Invite {

    private User sender;
    private String invitedUsername;
    private Group group;
    private boolean accepted;

    public Invite(User sender, String invitedUsername, Group group) {
        this.sender = sender;
        this.invitedUsername = invitedUsername;
        this.group = group;
        this.accepted = false;
    }

    public User getSender() {
        return sender;
    }

    public String getInvitedUsername() {
        return invitedUsername;
    }

    public Group getGroup() {
        return group;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void accept() {
        accepted = true;
    }
}