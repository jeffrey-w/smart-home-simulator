package util;

import java.util.regex.Pattern;

public class NameValidator {

    private static final Pattern NAME_PATTERN = Pattern.compile("^\\w+$");

    public static String validateName(String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("The provided identifier is not valid.");
        }
        return name;
    }

    // Clients cannot instantiate this class
    private NameValidator() {
        throw new AssertionError();
    }

}
