package users;

import permissions.Permission;

import java.nio.file.Path;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The {@code users.User} class provides profiling for a human actor in the SHS system. It offers facilities for identification and authorization levels.
 *
 * @author Jeff Wilgus
 */
public class User {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Z][a-z]+ [A-Z][a-z]+$");

    private static String validateName(String name) {
        if(!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("The name you've entered has an invalid format. Please use the following format: <First> <Last>");
        }
        return name;
    }

    private final String name;
    private Path avatar;
    private Permission permission;

    /**
     * Constructs a new {@code users.User} object with the specified {@code name}, {@code avatarPath} and {@code permission}.
     * @param name the name of this user
     * @param avatarPath a path to an image file
     * @param permission the authorization level of this {@code users.User}
     * @throws IllegalArgumentException if {@code name} does not respect the following format: two strings, beginning with an uppercase alpha character followed by a non-empty suffix of lowercase alpha characters, separated by a single space
     * @throws java.nio.file.InvalidPathException if {@code avatarPath} cannot be interpreted a filepath
     * @throws NullPointerException if {@code name} or {@code permission} is {@code null}
     */
    public User(String name, String avatarPath, Permission permission) {
        this.name = validateName(name);
        setAvatar(avatarPath);
        setPermission(permission);
    }

    /**
     *
     * @return this {@code users.User}'s name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the filepath to this {@code users.User}'s avatar
     */
    public Path getAvatar() {
        return avatar;
    }

    /**
     *
     * @return this {@code users.User}'s {@code permissions.Permission} level
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Sets the filepath to this {@code users.User}'s avatar to that specified.
     * @param avatarPath the specified filepath
     * @throws java.nio.file.InvalidPathException if {@code avatarPath} cannot be interpreted as a filepath
     */
    public void setAvatar(String avatarPath) {
        this.avatar = avatarPath == null ? null : Path.of(avatarPath);
    }

    /**
     * Sets this {@code users.User}'s {@code permissions.Permission} level to that specified.
     * @param permission the specified {@code permissions.Permission} level
     * @throws NullPointerException if {@code permission} is {@code null}
     */
    public void setPermission(Permission permission) {
        this.permission = Objects.requireNonNull(permission);
    }

}
