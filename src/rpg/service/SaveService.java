package rpg.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import rpg.model.Player;

public class SaveService {
    private final Path savePath;

    public SaveService(Path savePath) {
        this.savePath = savePath;
    }

    public void save(Player player) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(savePath.toFile()))) {
            output.writeObject(player);
        }
    }

    public Player load() throws IOException, ClassNotFoundException {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(savePath.toFile()))) {
            return (Player) input.readObject();
        }
    }
}
