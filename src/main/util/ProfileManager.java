package main.util;

import main.model.parameters.permissions.Permission;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class ProfileManager {

    public static HashMap<String, Permission> loadProfiles(String filePath) {
        HashMap<String, Permission> actors = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineArray = line.split(" ");
                String name = lineArray[0];
                String permissionString = lineArray[1]+"Permission";
                Class<?> klass = Class.forName("main.model.parameters.permissions." + permissionString);
                Permission permission = (Permission) klass.getDeclaredConstructor().newInstance();

                actors.put(name, permission);
            }
        } catch (Exception e) {
            return actors;
        }

        return actors;
    }

    public static void saveProfiles(Map<String, Permission> actors, String filePath) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
            for (Map.Entry<String, Permission> item : actors.entrySet()) {
                out.write(item.getKey() + " " + item.getValue());
                out.newLine();
            }
            out.close();
        } catch (Exception e) {

        }

    }
}
