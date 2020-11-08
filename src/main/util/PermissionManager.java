package main.util;

import main.model.parameters.permissions.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public abstract class PermissionManager {

    public static void savePermissions(File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file.getPath()));
        HashMap<String, Set<Action>> permissions = new HashMap<>();
        permissions.put("Parent", new ParentPermission().allowed());
        permissions.put("Child", new ChildPermission().allowed());
        permissions.put("Guest", new GuestPermission().allowed());
        permissions.put("Stranger", new StrangerPermission().allowed());
        for (Map.Entry<String, Set<Action>> item : permissions.entrySet()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(item.getKey());
            stringBuilder.append(":");
            for(Action action : item.getValue()){
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
