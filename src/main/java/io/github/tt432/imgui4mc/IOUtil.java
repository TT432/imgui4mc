package io.github.tt432.imgui4mc;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IOUtil {

    public static void extractResource(String resource, String outputFolder) {
        Path folder = Paths.get(outputFolder);
        Path filePath = folder.resolve(resource);
        File file = filePath.toFile();
        if (file.exists()) {
            file.delete();
        }
        createFolder(folder);
        InputStream resourceFromJar = getResourceFromJar(resource);
        try(FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(resourceFromJar.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getResourceFromJar(String resource) {
        return IOUtil.class.getResourceAsStream("/" + resource);
    }

    public static void createFolder(Path path) {
        File folder = path.toFile();
        if (!folder.isDirectory()) {
            try {
                Files.createDirectories(folder.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
