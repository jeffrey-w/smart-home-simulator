package main.util;

import java.util.regex.Pattern;

/**
 * The {@code NameValidator} class provides a facility for checking that character strings adhere to a certain format.
 * Specifically that they are non-empty and comprised of only word characters (i.e. [a-z, A-Z, 0-9, _]) and whitespace.
 * Note: this class is not instantiable.
 *
 * @author Jeff Wilgus
 */
public class NameValidator {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\w,\\s]+$");

    /**
     * Determines whether or not the specified {@code name} is valid insofar as it is a non-empty string comprised of
     * only word characters (i.e. [a-z, A-Z, 0-9, _]) and whitespace.
     *
     * @param name The specified name
     * @return The specified {@code name} provided it is valid
     * @throws IllegalArgumentException If the specified {@code name} is not valid
     * @throws NullPointerException If the specified {@code name} is {@code null}
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
