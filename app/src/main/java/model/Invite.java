package model;

import java.util.List;

import service.Services;
import service.dataManager.DataManager;

public class Invite extends Notification {

    private User receiver;
    private String sender;
    private Group group;

    public Invite(User receiver, String sender, Group group) {
        this.sender = sender;
        this.receiver = receiver;
        this.group = group;
    }

    public Invite(InviteDTO inviteInfo){
        this.receiver = new User(inviteInfo.receiver());
        this.sender = inviteInfo.sender();
        this.group = new Group(inviteInfo.groupDTO());
    }

    public User getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public String getMessage() {
        return "Convite para participar de \"" + group.getName() + "\"";
    }

    @Override
    public InviteDTO toDTO() {
        DataManager manager = Services.getManager();
        User user = receiver;
        List<UserProfileDTO> existing = manager.readData(user.getEmail(), UserProfileDTO.class);

        if (existing == null || existing.isEmpty()) {
            System.out.println("[ERROR] Perfil nao encontrado para: " + user.getEmail());
            return null;
        }

        UserProfileDTO current = existing.get(0);

        UserProfileDTO userDTO = new UserProfileDTO(
            current.name(),
            current.email(),
            current.password(),
            current.pathPhotoFile(),
            current.likedMovies(),
            current.groups(),
            current.notifications()
        );

        return new InviteDTO(userDTO, sender, new GroupDTO(group.getName(), group.getNumOfUsers(), group.getLikedMovies()));
        }
}