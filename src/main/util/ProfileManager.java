package main.util;

import main.model.parameters.permissions.Permission;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class ProfileManager {

    public static HashMap<String, Permission> loadProfiles(File file) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, FileNotFoundException {
        HashMap<String, Permission> actors = new HashMap<>();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineArray = line.split(" ");
                String name = lineArray[0];
                String permissionString = lineArray[1] + "Permission";
                Class<?> klass = Class.forName("main.model.parameters.permissions." + permissionString);
                Permission permission = (Permission) klass.getDeclaredConstructor().newInstance();

                actors.put(name, permission);
            }

        return actors;
    }

    public static void saveProfiles(Map<String, Permission> actors, File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file.getPath()));
        for (Map.Entry<String, Permission> item : actors.entrySet()) {
            out.write(item.getKey() + " " + item.getValue());
            out.newLine();
        }
        out.close();
    }
}
