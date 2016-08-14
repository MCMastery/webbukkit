package com.dgrissom.webbukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileUtils {
    // the folder inside the base server folder which contains website files
    private static final File WEBSITE_FOLDER = new File("website");

    private FileUtils() {}

    // if the file does not exist, create it
    public static File createFileIfNecessary(File file) {
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.err.println("Could not create file " + file.getName() + "!");
                    return null;
                }
                return file;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else
            return file;
    }

    // if the file does not exist, create it
    public static File createFolderIfNecessary(File folder) {
        if (!folder.exists() && !folder.mkdirs()) {
            System.err.println("Could not create folder " + folder.getName() + "!");
            return null;
        }
        return folder;
    }

    public static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static File getWebsiteFolder() {
        return createFolderIfNecessary(WEBSITE_FOLDER);
    }

    public static File getWebsiteHome() {
        return createFileIfNecessary(new File(getWebsiteFolder(), "index.html"));
    }
    public static File getWebsite404() {
        return createFileIfNecessary(new File(getWebsiteFolder(), "404.html"));
    }
    // always assumes index.html
    public static File getWebsitePage(String request) {
        if (request.endsWith("/")) // ends with folder directory
            request += "index.html";
        File file = new File(getWebsiteFolder(), request);
        if (file.exists() && file.isDirectory())
            return getWebsitePage(request + "/index.html");
        if (!file.exists())
            return getWebsite404();
        return file;
    }
}

