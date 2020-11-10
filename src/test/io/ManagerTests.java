package test.io;

import main.model.parameters.permissions.ChildPermission;
import main.model.parameters.permissions.ParentPermission;
import main.model.parameters.permissions.Permission;
import main.util.PermissionManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ManagerTests {

    private static final Map<String, Permission> PROFILES = new HashMap<>();

    static {
        PROFILES.put("Parent", new ParentPermission());
        PROFILES.put("Child", new ChildPermission());
    }


    @Test
    void testSavePermission() {
        assertDoesNotThrow(() -> PermissionManager.savePermissions(PROFILES, new File("./permissions.txt")));
    }

}
