package test.io;

import main.model.parameters.permissions.ParentPermission;
import main.model.parameters.permissions.Permission;
import main.util.PermissionManager;
import main.util.ProfileManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerTests {

    private static final Map<String, Permission> PROFILES = new HashMap<>();

    static {
        PROFILES.put("John Doe", new ParentPermission());
        PROFILES.put("Jane Doe", new ParentPermission());
    }


    @Test
    void testSavePermission() {
        assertDoesNotThrow(() -> PermissionManager.savePermissions(PROFILES, new File("./permissions.txt")));
    }

    @Test
    void testSaveProfiles() {
        assertDoesNotThrow(() -> ProfileManager.saveProfiles(PROFILES, new File("./profiles.txt")));
    }

    @Test
    void testLoadProfiles()
            throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException,
            InvocationTargetException, ClassNotFoundException {
        assertEquals(PROFILES, ProfileManager.loadProfiles(new File("./src/test/io/profiles.txt")));
    }

}
