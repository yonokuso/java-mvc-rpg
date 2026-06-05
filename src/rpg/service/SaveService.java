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
    private final Object fileLock = new Object();

    public SaveService(Path savePath) {
        this.savePath = savePath;
    }

    public void save(Player player) throws IOException {
        synchronized (fileLock) {
            try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(savePath.toFile()))) {
                output.writeObject(player);
            }
        }
    }

    public Player load() throws IOException, ClassNotFoundException {
        synchronized (fileLock) {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(savePath.toFile()))) {
                return (Player) input.readObject();
            }
        }
    }
}
