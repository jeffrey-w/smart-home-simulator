package main.util;

/**
 * The {@code PermissionDeniedException} is used to throw an error if the user is trying to perform an action that
 * isn't allowed with their assigned permission level.
 *
 * @author Jeff Wilgus
 */
public class PermissionDeniedException extends RuntimeException {

    /**
     * Constructs a {@code PermissionDeniedException} with the specified message.
     *
     * @param message The specified error message
     */
    public PermissionDeniedException(String message) {
        super(message);
    }

}
