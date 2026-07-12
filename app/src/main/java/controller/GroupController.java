package controller;

import java.util.ArrayList;

import model.Group;
import model.User;
import model.dto.UserProfileDTO;
import service.Services;
import service.dataManager.DataManager;
import view.Utils;

/**
 * Coordena as acoes de grupo de um usuario e a persistencia do perfil.
 */
public class GroupController {


    /**
     * Faz o usuario entrar no grupo e persiste tanto o grupo quanto a
     * referencia do grupo no perfil do usuario.
     * <p>
     * Se o usuario ja participar do grupo (mesmo {@code id}), a operacao e
     * ignorada para evitar cadastros duplicados.
     * </p>
     *
     * @param user  usuario logado.
     * @param group grupo a ser ingressado.
     */
    public static void joinGroup(User user, Group group) {
        if (alreadyMember(user, group)) {
            return;
        }

        user.joinGroup(group);
        saveGroup(group);
        saveUserGroups(user);
    }

    /**
     * Verifica se o usuario ja participa do grupo, comparando pelo {@code id}.
     *
     * @param user  usuario a ser verificado.
     * @param group grupo alvo.
     * @return {@code true} se o usuario ja for membro do grupo.
     */
    private static boolean alreadyMember(User user, Group group) {
        for (Group existing : user.getGroups()) {
            if (existing.getId().equals(group.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Grava (ou sobrescreve) o arquivo do grupo na tabela {@code group}, usando
     * o {@code id} do grupo como nome do arquivo ({@code group%<id>.json}).
     *
     * @param group grupo a ser persistido.
     */
    public static void saveGroup(Group group) {
        DataManager manager = Services.getManager();
        manager.createData("group", group.getId(), group.toDTO());
    }

    /**
     * Regrava o {@code <email>.json} do usuario atualizando apenas a lista de
     * ids dos grupos.
     * <p>
     * Como o {@link User} nao guarda senha nem o caminho da foto, lemos o
     * {@link UserProfileDTO} ja gravado e reescrevemos trocando so o campo
     * {@code groups}, que passa a conter apenas os ids dos grupos (nomes de
     * arquivo sem o prefixo {@code group%}).
     * </p>
     *
     * @param user usuario cujo perfil sera atualizado.
     */
    private static void saveUserGroups(User user) {
        DataManager manager = Services.getManager();
        UserProfileDTO current = Utils.getUserProfile(user.getEmail());

        ArrayList<String> groupIds = new ArrayList<>();
        for (Group group : user.getGroups()) {
            if (!groupIds.contains(group.getId())) {
                groupIds.add(group.getId());
            }
        }

        UserProfileDTO updated = new UserProfileDTO(
            current.name(),
            current.email(),
            current.password(),
            current.pathPhotoFile(),
            current.likedMovies(),
            groupIds,
            current.notifications()
        );

        manager.createData("user", user.getEmail(), updated);
    }
}
