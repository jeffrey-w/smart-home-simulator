package main.util;

import main.model.Action;
import main.model.parameters.permissions.Permission;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class PermissionManager {

    public static HashMap<String, Permission> loadPermissions(File file)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, FileNotFoundException {
        HashMap<String, Permission> permissions = new HashMap<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            int nameIndex;
            String line = scanner.nextLine();
            String name = line.substring(0, (nameIndex = line.indexOf(":")));
            line = line.substring(0, nameIndex + 1);
            String[] lineArray = line.split(",");
            String permissionString = name + "Permission";
            Class<?> klass = Class.forName("main.model.parameters.permissions." + permissionString);
            Permission permission = (Permission) klass.getDeclaredConstructor().newInstance();

            permissions.put(name, permission);
        }
        scanner.close();
        return permissions;
    }

    public static void savePermissions(Map<String, Permission> permissions, File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        for (Map.Entry<String, Permission> item : permissions.entrySet()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(item.getKey());
            stringBuilder.append(":");
            for(Action action : item.getValue().allowed()){
                stringBuilder.append(action.toString());
                stringBuilder.append(",");
            }
            //to remove the last comma in the string
            String permissionAsString = stringBuilder.substring(0, stringBuilder.length() - 1);
            out.write(permissionAsString);
            out.newLine();
        }
        out.close();
    }
}
