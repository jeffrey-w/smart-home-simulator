package main.util;

import main.model.parameters.permissions.Permission;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class ProfileReader {

    public static Map<String, Permission> loadProfiles()  {
        HashMap actors = new HashMap<String, Permission>();
        try{
            Scanner scanner = new Scanner(new File("assets/profiles.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineArray = line.split(" ");
                String name = lineArray[0];
                String permissionString = lineArray[1];
                Class klass = Class.forName("main.model.parameters.permissions."+permissionString);
                Permission permission = (Permission) klass.getDeclaredConstructor().newInstance();

                actors.put(name, permission);
            }
        }catch(Exception e){
            return actors;
        }

        return actors;
    }
}
