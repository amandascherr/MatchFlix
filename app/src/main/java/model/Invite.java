package model;

import java.util.List;

import dto.GroupDTO;
import dto.InviteDTO;
import dto.UserProfileDTO;
import service.Services;
import service.dataManager.DataManager;

/**
 * Notificação de convite para um usuário ingressar em um grupo. Guarda quem
 * enviou o convite, o destinatário e o grupo de destino.
 */
public class Invite extends Notification {

    private final User sender;
    private final String receiver;
    private final Group group;

    /**
     * Cria um convite.
     *
     * @param sender   usuário que envia o convite.
     * @param receiver nome do usuário destinatário.
     * @param group    grupo ao qual o destinatário é convidado.
     */
    public Invite(User sender, String receiver, Group group) {
        this.sender = sender;
        this.receiver = receiver;
        this.group = group;
    }

    /**
     * Reconstrói um convite a partir do seu {@link InviteDTO} persistido,
     * reinstanciando o remetente e o grupo.
     *
     * @param inviteInfo representação serializada do convite.
     */
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

    /**
     * Converte este convite na sua representação serializável, lendo o perfil
     * atual do remetente para compor o DTO.
     *
     * @return um {@link InviteDTO} do convite, ou {@code null} se o perfil do
     *         remetente não for encontrado.
     */
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

        return new InviteDTO(userDTO, receiver, new GroupDTO(group.getId(), group.getName(), group.getNumOfUsers(), group.getLikedMovies(), group.getMatches()));
        }
}