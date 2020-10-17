// imports
package elements;

import permissions.Permission;

import java.util.*;
import java.util.function.BiConsumer;

import static util.NameValidator.validateName;

public class House implements Iterable<Room> {

    private static class Node {
        Room room;
        Set<String> adjacents;
        boolean visited;

        Node(Room room) {
            this.room = Objects.requireNonNull(room);
            this.adjacents = new HashSet<>();
        }

    }

    // variables
    private final Map<String, Node> rooms;
    private final Map<String, String> people;

    // constructor
    public House() {
        rooms = new HashMap<>();
        people = new HashMap<>();
    }

    public void addRoom(Room room, String location) {
        rooms.putIfAbsent(validateName(location), new Node(room)); // TODO throw exception if already present?
    }

    public void addConnection(String locationOne, String locationTwo) {
        Node roomOne = validateLocation(locationOne);
        Node roomTwo = validateLocation(locationTwo);
        if (roomOne.adjacents.size() > 4 || roomTwo.adjacents.size() > 4) { // TODO avoid magic constants
            throw new IllegalArgumentException(
                    "Cannot connect a room with more than 4 others."); // TODO avoid magic constants
        }
        roomOne.adjacents.add(locationTwo);
        roomTwo.adjacents.add(locationOne);
    }

    public Room removeRoom(String location) {
        try {
            return rooms.remove(location).room;
        } catch (NullPointerException e) {
            throw new NoSuchElementException("That location does not exist.");
        }
    }

    public void addPerson(String name, String location, Permission permission) {
        if (rooms.containsKey(location)) {
            people.put(validateName(name), location);
            validateLocation(location).room.addPerson(name, Objects.requireNonNull(permission));
        }
        throw new IllegalArgumentException("That room does not exist.");
    }

    public void removePerson(String name) {
        String location = people.remove(name);
        validateLocation(people.remove(name)).room.removePerson(name);
    }

    public Set<String> getLocations() {
        return rooms.keySet();
    }

    public Set<String> getAdjacentsOf(String location) {
        return validateLocation(location).adjacents;
    }

    public int size() {
        return rooms.size();
    }

    /**
     * Applies the specified {@code action} to each {@code Room} in this {@code House}.
     *
     * @param action the specified action to perform
     * @throws NullPointerException if the specified {@code action} is {@code null}
     */
    public void tour(String location, BiConsumer<String, Room> action) {
        Deque<String> stack = new ArrayDeque<>();
        for (Node node : rooms.values()) {
            node.visited = false;
        }
        stack.push(location);
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

    @Override
    public Iterator<Room> iterator() {
        return new RoomIter();
    }

    private class RoomIter implements Iterator<Room> {

        Iterator<Node> iterator;

        RoomIter() {
            iterator = rooms.values().iterator();
        }

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
            ;
        }

    }

}