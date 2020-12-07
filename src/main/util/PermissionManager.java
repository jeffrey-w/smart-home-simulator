package main.util;

import main.model.Action;
import main.model.parameters.permissions.Permission;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The {@code PermissionManager} class is a facility for writing custom {@code Permission} preferences to the disk, and
 * loading them into a simulation when required.
 *
 * @author Ayman Shehri
 */
public abstract class PermissionManager { // TODO redo comments

    /**
     * Loads the {@code Permission}s defined at the specified {@code file}. This method throws several checked
     * exceptions that must be caught by clients.
     *
     * @param file The specified {@code File}
     */
    public static Map<String, Permission> loadPermissions(File file)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException, FileNotFoundException {

        HashMap<String, Permission> permissions = new HashMap<>();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            int index;
            String line = scanner.nextLine();
            String name = line.substring(0, (index = line.indexOf(":")));
            String[] actions = line.substring(index + 1).split(",");
            String permissionString = name + "Permission";
            Class<?> klass = Class.forName("main.model.parameters.permissions." + permissionString);

            Permission permission = (Permission) klass.getDeclaredConstructor().newInstance();
            for (Action action : permission.allowed()) {
                permission.disallow(action);
            }

            for (String action : actions) {
                try {
                    permission.allow(Action.valueOf(action));
                } catch (IllegalArgumentException exception) {
                    exception.printStackTrace(); // TODO what to do here?
                }
            }
            permissions.put(name, permission);
        }
        scanner.close();
        return permissions;
    }

    /**
     * Writes the specified {@code permissions} to the specified {@code file}.
     *
     * @param permissions The specified {@code Permission}s
     * @param file The specified {@code File}
     * @throws IOException If any error occurs when attempting to write to the specified {@code file}
     */
    public static void savePermissions(Map<String, Permission> permissions, File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));

        for (Map.Entry<String, Permission> item : permissions.entrySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(item.getKey());
            stringBuilder.append(":");

            for (Action action : item.getValue().allowed()) {
                stringBuilder.append(action.name());
                stringBuilder.append(",");
            }

            String permissionAsString = stringBuilder.toString(); // Remove the last comma in the string

            if (!item.getValue().allowed().isEmpty()) {
                permissionAsString = permissionAsString.substring(0, stringBuilder.length() - 1);
            }

            out.write(permissionAsString);
            out.newLine();
        }
        out.close();
    }
}
