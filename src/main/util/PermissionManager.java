package main.util;

import main.model.Action;
import main.model.parameters.permissions.Permission;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;


public abstract class PermissionManager {

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
