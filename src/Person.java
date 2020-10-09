public class Person implements ISource {

    private final Profile profile;
    private Room room;
    private boolean isInsideHouse;
    private final Enums.SourceType sourceType = Enums.SourceType.USER;

    public Person(Profile profile) {
        this.profile = profile;
        isInsideHouse = false;
        this.room = null;
    }

    public Person(Profile profile, Room room) {
        this.profile = profile;
        isInsideHouse = true;
        this.room = room;
    }

    public void setRoom(Room room) {
        isInsideHouse = false;
        this.room = room;
    }

    public void placeOutsideHouse(){
        this.room = null;
        isInsideHouse = false;
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public void sendRequest(Request request) {
        request.send();
    }
}
