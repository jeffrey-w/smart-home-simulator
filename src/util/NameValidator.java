package util;

import java.util.regex.Pattern;

/**
 * The {@code NameValidator} class provides a facility for checking that character strings adhere to a certain format.
 * Specifically that they are non-empty and comprised of only word characters (i.e. [a-z, A-z, 0-9, _]. Note: this class
 * is not instantiable.
 *
 * @author Jeff Wilgus
 */
public class NameValidator {

    private static final Pattern NAME_PATTERN = Pattern.compile("^\\w+$");

    /**
     * Determines whether or not the specified {@code name} is valid insofar as it is a non-empty string comprised of
     * only word characters.
     *
     * @param name the specified name
     * @return the specified {@code name} provided it is valid
     * @throws IllegalArgumentException if the specified {@code name} is not valid
     */
    public static String validateName(String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("The provided identifier is not valid.");
        }
        return name;
    }

    // Clients cannot instantiate this class.
    private NameValidator() {
        // Just in case a maintainer tries to do so from within this class.
        throw new AssertionError();
    }

}
