package controller;

import java.util.ArrayList;
import java.util.List;

import model.Notification;
import model.NotificationDTO;
import model.User;
import model.UserProfileDTO;
import service.Services;
import service.dataManager.DataDTO;
import service.dataManager.DataManager;

public class MatchController {


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
    public static void saveMatch(User user) {
        DataManager manager = Services.getManager();
        List<UserProfileDTO> existing = manager.readData(user.getEmail(), UserProfileDTO.class);
        if (existing == null || existing.isEmpty()) {
            System.out.println("[ERROR] Perfil nao encontrado para: " + user.getEmail());
            return;
        }

        UserProfileDTO current = existing.get(0);

        ArrayList<NotificationDTO> notDTO = new ArrayList<>();
        for (Notification not :  user.getNotifications()) {
            notDTO.add(not.toDTO());
        }

        UserProfileDTO updated = new UserProfileDTO(
            current.name(),
            current.email(),
            current.password(),
            current.pathPhotoFile(),
            current.likedMovies(),
            current.groups(),
            notDTO
        );

        manager.createData(new DataDTO<>(user.getEmail(), updated));
    }
  
}
