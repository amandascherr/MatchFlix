package controller;

import java.util.ArrayList;
import java.util.List;

import model.Group;
import model.GroupDTO;
import model.User;
import model.UserProfileDTO;
import service.Services;
import service.dataManager.DataDTO;
import service.dataManager.DataManager;
import view.Utils;

/**
 * Coordena as acoes de grupo de um usuario e a persistencia do perfil.
 */
public class GroupController {


    /**
     * Faz o usuario entrar no grupo (em memoria) e regrava o perfil no arquivo.
     *
     * @param user  usuario logado.
     * @param group grupo a ser ingressadso.
     */
    public static void joinGroup(User user, Group group) {
        user.joinGroup(group);
        saveGroups(user);
    }

    /**
     * Regrava o {@code <email>.json} do usuario atualizando apenas os grupos.
     * <p>
     * Como o {@link User} nao guarda senha nem o caminho da foto, lemos o
     * {@link UserProfileDTO} ja gravado e reescrevemos trocando so o campo
     * {@code groups}.
     * </p>
     *
     * @param user usuario cujo perfil sera atualizado.
     */
    private static void saveGroups(User user) {
        DataManager manager = Services.getManager();
        UserProfileDTO current = Utils.getUserProfile(user.getEmail());

        ArrayList<GroupDTO> groupsDTO = new ArrayList<>();
        for (Group group : user.getGroups()) {
            groupsDTO.add(group.toDTO());
        }

        UserProfileDTO updated = new UserProfileDTO(
            current.name(),
            current.email(),
            current.password(),
            current.pathPhotoFile(),
            current.likedMovies(),
            groupsDTO,
            current.notifications()
        );

        manager.createData(new DataDTO<>(user.getEmail(), updated));
    }
}
