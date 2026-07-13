package model;

import java.util.List;

import model.dto.GroupDTO;
import model.dto.InviteDTO;
import model.dto.UserProfileDTO;
import service.Services;
import service.dataManager.DataManager;

public class Invite extends Notification {

    private final User sender;
    private final String receiver;
    private final Group group;

    public Invite(User sender, String receiver, Group group) {
        this.sender = sender;
        this.receiver = receiver;
        this.group = group;
    }

    public Invite(InviteDTO inviteInfo){
        this.sender = new User(inviteInfo.sender());
        this.receiver = inviteInfo.receiver();
        this.group = new Group(inviteInfo.groupDTO());
    }

    public User getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
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
        User user = sender;
        List<UserProfileDTO> existing = manager.readData("user", user.getEmail(), UserProfileDTO.class);

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

        return new InviteDTO(userDTO, receiver, new GroupDTO(group.getId(), group.getName(), group.getNumOfUsers(), group.getLikedMovies()));
        }
}