package main.model.elements;

import main.model.parameters.permissions.Permission;

import java.util.*;
import java.util.function.BiConsumer;

import static main.util.NameValidator.validateName;

/**
 * The {@code House} class represents a collection of rooms that provide the context for a simulation and are subject to
 * the evolving conditions that emerge during simulation.
 *
 * @author Philippe Vo
 * @author Jeff Wilgus
 * @see Room
 */
public class House implements Iterable<Room> {

    private static class Node {

        final Room room;

        final Set<String> adjacents;
        boolean visited;

        Node(Room room) {
            this.room = Objects.requireNonNull(room);
            this.adjacents = new HashSet<>();
        }
        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof Node)) {
                return false;
            }
            Node node = (Node) obj;
            return room.equals(node.room);
        }

    }

    /**
     * The maximum number of {@code Room}s that another {@code Room} may be adjacent to.
     */
    public static final int MAX_CONNECTIONS = 4;

    /**
     * The name of the {@code Place} surrounding a {@code House}.
     */
    public static final String EXTERIOR_NAME = "yard";

    private String root;
    private final Map<String, Node> rooms;
    private final Map<String, String> people;

    /**
     * Constructs a new {@code House} object with no {@code Room}s.
     */
    public House() {
        rooms = new HashMap<>();
        people = new HashMap<>();
    }

    /**
     * Adds the specified {@code room} to this {@code House} at the specified {@code location}. Note: if another {@code
     * Room} already exists at the specified {@code location}, the new {@code Room} is not added.
     *
     * @param room The specified {@code Room}
     * @param location The specified location
     * @throws IllegalArgumentException If the specified {@code location} is not a non-empty string of word characters
     * (i.e. [a-z, A-Z, 0-9, _]) and whitespace
     */
    public void addRoom(Room room, String location) {
        rooms.putIfAbsent(validateName(location), new Node(room));
    }
    /**
     * Signifies adjacency between the specified locations in this {@code House}.
     *
     * @param locationOne The first location
     * @param locationTwo The other location
     * @throws IllegalArgumentException If the {@code Room} at either of the specified locations is already connected to
     * {@value #MAX_CONNECTIONS} other {@code Room}s, or if they both share a connection to another {@code Room}
     * already.
     * @throws NoSuchElementException If either of the specified locations does not exist in this {@code House}
     */
    public void addConnection(String locationOne, String locationTwo) {
        Node nodeOne = validateLocation(locationOne);
        Node nodeTwo = validateLocation(locationTwo);
        validateConnection(nodeOne, nodeTwo);
        nodeOne.adjacents.add(locationTwo);
        nodeTwo.adjacents.add(locationOne);
    }

    private void validateConnection(Node one, Node two) {
        if (one.adjacents.size() > MAX_CONNECTIONS || two.adjacents.size() > MAX_CONNECTIONS) {
            throw new IllegalArgumentException("Cannot connect a room with more than " + MAX_CONNECTIONS + " others");
        }
        for (String adjacentOne : one.adjacents) {
            for (String adjacentTwo : two.adjacents) {
                if (adjacentOne.equals(adjacentTwo)) {
                    throw new IllegalArgumentException(
                        "Rooms that are already connected through another room, cannot be connected to each other."
                    );
                }
            }
        }
    }

    /**
     * Adds a person with the specified {@code name} and {@code permission} level to this {@code House} at the specified
     * {@code location}.
     *
     * @param name The specified name
     * @param permission The specified {@code Permission}
     * @param location The specified location
     * @throws IllegalArgumentException If the specified {@code name} is not a non-empty string of word characters (i.e.
     * [a-z, A-Z, 0-9, _, ]) and whitespace
     * @throws NoSuchElementException If the specified {@code location} does not exist in this {@code House}
     * @throws NullPointerException If the specified {@code permission} is {@code null}
     */
    public void addPerson(String name, Permission permission, String location) {
        String previousLocation = people.put(validateName(name), location);

        if (previousLocation != null) {
            if (previousLocation.equals(EXTERIOR_NAME)) {
                Yard.getInstance().removePerson(name);
            } else {
                rooms.get(previousLocation).room.removePerson(name);
            }
        }

        if (location != null && location.equals(EXTERIOR_NAME)) {
            Yard.getInstance().addPerson(name, permission);
        } else {
            validateLocation(location).room.addPerson(name, permission);
        }
    }

    /**
     * Removes the person with the specified {@code name} from this {@code House}.
     *
     * @param name The specified name
     * @return {@code true} if the specified person was removed from this {@code House}
     */
    public boolean removePerson(String name) {
        return rooms.get(people.remove(name)).room.removePerson(name);
    }

    /**
     * Returns a collection of the names of {@code Room}s in this {@code House}.
     *
     * @return The names of the {@code Room}s in this {@code House}
     */
    public Set<String> getLocations() {
        return Collections.unmodifiableSet(rooms.keySet());
    }

    /**
     * Provides the {@code Room} in this {@code House} at the specified {@code location}.
     *
     * @param location The specified location
     * @return The {@code Room} at the specified {@code location}
     * @throws NoSuchElementException If the specified {@code location} does not exist in this {@code House}
     */
    public Room getRoom(String location) {
        return validateLocation(location).room;
    }

    /**
     * @return The number of {@code Room}s in this house
     */
    public int size() {
        return rooms.size();
    }

    /**
     * Determines whether or not the specified {@code person} is in this {@code House}.
     *
     * @param person The specified person
     * @return {@code true} if the specified {@code person} is in this {@code House}
     */
    public boolean contains(String person) {
        return people.containsKey(person);
    }

    /**
     * Provides the location of the specified {@code person} in this {@code House}.
     *
     * @param person The specified person
     * @return The name of the {@code Room} that the specified {@code person} is in.
     * @throws NoSuchElementException If the specified {@code person} is not in this {@code House}
     */
    public String locationOf(String person) {
        if (contains(person)) {
            return people.get(person);
        }
        throw new NoSuchElementException("No person by that name exists in this house.");
    }

    /**
     * Provides the number of people in this {@code House}.
     *
     * @return The number of people in this {@code House}
     */
    public int getNumberOfPeople() {
        int count = 0;
        for (Room room : this) {
            count += room.getNumberOfPeople();
        }
        return count;
    }

    /**
     * Determines whether or not any {@code Window} in this {@code House} is obstructed.
     *
     * @return {@code true} if any {@code Window} in this {@code House} is obstructed
     */
    public boolean hasObstructedWindow() {
        for (Room room : this) {
            if (room.getNumberOfWindowsBlocked() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param temperature The provided temperature
     * @return {@code true} if any room in the house has a higher temperature than the one provided
     */
    public boolean hasTemperatureAberration(double temperature) {
        for (Room room : this) {
            if (Double.compare(room.getTemperature(), temperature) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether or not this {@code House} has any people in it.
     *
     * @return {@code true} if any people are in this {@code House}
     */
    public boolean isOccupied() {
        if (Yard.getInstance().isOccupied()) {
            return true;
        }
        for (Room room : this) {
            if (room.isOccupied()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Locks every {@code Door} and closes every {@code Window} in this {@code House}.
     *
     * @throws IllegalStateException if there is an open {@code Door} or blocked {@code Window} anywhere in this {@code
     * House}
     */
    public void closeOpenables() {
        if (hasObstructedWindow()) {
            throw new IllegalStateException("At least one window in this house is blocked");
        }
        for (Room room : this) {
            for (Door door : room.getDoors()) {
                if (door != null) {
                    door.setOpen(false);
                    door.setLocked(true);
                }
            }
            for (Window window : room.getWindows()) {
                if (window != null) {
                    window.setOpen(false);
                }
            }
        }
    }

    /**
     * Sets the {@code root} location of this {@code House} to that specified. All subsequent {@link #tour(BiConsumer)
     * tour}s of this {@code House} will begin from the specified {@code root}
     *
     * @param root The specified root
     * @throws NoSuchElementException If the specified {@code location} does not exist in this {@code House}
     */
    public void setRoot(String root) {
        if (!rooms.containsKey(root)) {
            throw new NoSuchElementException("That location does not exist.");
        }
        this.root = root;
    }

    /**
     * Applies the specified {@code action} to each {@code Room} in this {@code House}, starting form the root location
     * of this {@code House}.
     *
     * @param action The specified action to perform
     * @throws IllegalStateException If the root of this {@code House} has not been set
     * @throws NullPointerException If the specified {@code action} is {@code null}
     */
    public void tour(BiConsumer<String, Room> action) {
        /*
         * This is a depth-first search. It will only find nodes connected to the source. Maintainers should consider
         * implementing a search routine that finds unconnected nodes if having detached rooms becomes desirable in the
         * future.
         */
        if (root == null) {
            throw new IllegalStateException("You must specify a root location.");
        }

        Deque<String> stack = new ArrayDeque<>();
        for (Node node : rooms.values()) {
            node.visited = false;
        }

        stack.push(root);

        while (!stack.isEmpty()) {
            String current = stack.pop();
            Node node = validateLocation(current);
            
            if (!node.visited) {
                action.accept(current, node.room);
                node.visited = true;
            }

            for (String adjacent : node.adjacents) {
                if (!rooms.get(adjacent).visited) {
                    stack.push(adjacent);
                }
            }
        }
    }

    private Node validateLocation(String location) {
        if (rooms.containsKey(location)) {
            return rooms.get(location);
        }
        throw new NoSuchElementException("That location does not exist");
    }

    /**
     * @return The number of {@code Room}s in the {@code House}
     */
    public int getSize() {
        return rooms.size();
    }

    @Override
    public Iterator<Room> iterator() {
        return new Iterator<Room>() {

            final Iterator<Node> iterator = rooms.values().iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Room next() {
                return iterator.next().room;
            }

            @Override
            public void remove() {
                iterator.remove();
            }

        };
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof House)) {
            return false;
        }
        House h = (House) obj;
        return rooms.equals(h.rooms) && people.equals(h.people);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + rooms.hashCode();
        result = prime * result + people.hashCode();
        return result;
    }

}