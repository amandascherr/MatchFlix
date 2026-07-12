package view;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

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


}
