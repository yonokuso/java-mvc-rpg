package rpg.view;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import rpg.model.Monster;

final class ImageAssetLoader {
    private static final String IMAGE_DIRECTORY = "assets/images";
    private static final String[] IMAGE_EXTENSIONS = {".gif", ".png", ".jpg", ".jpeg"};

    private ImageAssetLoader() {
    }

    static ImageIcon loadIcon(String baseName) {
        File imageFile = findImageFile(baseName);
        if (imageFile == null) {
            return null;
        }
        return new ImageIcon(imageFile.getAbsolutePath());
    }

    static ImageIcon loadIcon(String baseName, int imageSize) {
        File imageFile = findImageFile(baseName);
        if (imageFile == null) {
            return null;
        }

        ImageIcon original = new ImageIcon(imageFile.getAbsolutePath());
        if (imageFile.getName().toLowerCase().endsWith(".gif")) {
            return original;
        }

        Image scaled = original.getImage().getScaledInstance(
                imageSize,
                imageSize,
                Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    static String getMonsterImageBaseName(Monster monster) {
        String name = monster.getName().toLowerCase();
        if (name.contains("sword") || name.contains("칼")) {
            return "spr_sword";
        }
        if (name.contains("axe") || name.contains("도끼")) {
            return "spr_axe";
        }
        if (name.contains("hammer") || name.contains("망치")) {
            return "spr_hammering";
        }
        if (name.contains("caught") || name.contains("채찍")) {
            return "spr_caught";
        }
        if (name.contains("skeleton") || name.contains("스켈레톤")) {
            return "skeleton_attack";
        }
        if (name.contains("death") || name.contains("흑화")) {
            return "spr_death";
        }
        return name.replace("야생의 ", "").replaceAll("[^a-z0-9]+", "");
    }

    private static File findImageFile(String baseName) {
        File currentDirectory = new File(System.getProperty("user.dir"));
        for (int depth = 0; currentDirectory != null && depth < 6; depth++) {
            File imageDirectory = new File(currentDirectory, IMAGE_DIRECTORY);
            File imageFile = findInDirectory(imageDirectory, baseName);
            if (imageFile != null) {
                return imageFile;
            }
            currentDirectory = currentDirectory.getParentFile();
        }
        return null;
    }

    private static File findInDirectory(File imageDirectory, String baseName) {
        for (String extension : IMAGE_EXTENSIONS) {
            File imageFile = new File(imageDirectory, baseName + extension);
            if (imageFile.exists()) {
                return imageFile;
            }
        }
        return null;
    }
}
