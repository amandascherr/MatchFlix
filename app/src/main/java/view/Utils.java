package view;

import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;

import model.dto.UserProfileDTO;
import service.Services;
import service.dataManager.DataManager;

public class Utils {
  
   public static ImageIcon loadProfileImage(String path) {
    File file = new File(path);
    
    if (file.exists()) {
        ImageIcon originalIcon = new ImageIcon(file.getAbsolutePath());
        Image scaledImage = originalIcon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
    return null;
  }

  public static UserProfileDTO getUserProfile(String email){
    DataManager manager = Services.getManager();
        List<UserProfileDTO> existing = manager.readData("user", email, UserProfileDTO.class);
        if (existing == null || existing.isEmpty()) {
            System.out.println("[ERROR] Perfil nao encontrado para: " + email);
            return null;
        }

        UserProfileDTO current = existing.get(0);
        return current;

  }

}
