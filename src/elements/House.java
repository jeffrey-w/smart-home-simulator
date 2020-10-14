// imports
package elements;

import java.util.*;

import static util.NameValidator.validateName;

public class House implements Iterable<Room> {

    private static class Node {

        Room room;
        Set<String> adjacents;

        Node(Room room) {
            this.room = Objects.requireNonNull(room);
            this.adjacents = new HashSet<>();
        }

    }

    // variables
    private Map<String, Node> rooms;

    // constructor
    public House() {
        rooms = new HashMap<>();
    }

    public void addRoom(Room room, String location) {
        rooms.putIfAbsent(validateName(location), new Node(room)); // TODO throw exception if already present?
    }

    public void addConnection(String locationOne, String locationTwo) {
        Node roomOne = validateLocation(locationOne);
        Node roomTwo = validateLocation(locationTwo);
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

    public Set<String> getLocations() {
        return rooms.keySet();
    }

    public Set<String> getAdjacentsOf(String location) {
        return validateLocation(location).adjacents;
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
            iterator.remove();;
        }

    }

}