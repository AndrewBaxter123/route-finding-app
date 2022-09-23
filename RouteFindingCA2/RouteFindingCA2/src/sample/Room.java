package sample;

public class Room {
    String roomName;
    String roomNumber;
    String x;
    String y;

    /**
     * Instantiates a new Room with the following data added:
     *
     * @param roomName   the room name
     * @param roomNumber the room number
     * @param x          the x
     * @param y          the y
     */
    public Room(String roomName, String roomNumber, String x, String y) {
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets room name.
     *
     * @return the room name
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Sets room name.
     *
     * @param roomName the room name
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Gets room number.
     *
     * @return the room number
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets room number.
     *
     * @param roomNumber the room number
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets x coordinate of the room.
     *
     * @return the x
     */
    public String getX() {
        return x;
    }

    /**
     * Sets x coordinate of the room.
     *
     * @param x the x
     */
    public void setX(String x) {
        this.x = x;
    }

    /**
     * Gets y coordinate of the room.
     *
     * @return the y
     */
    public String getY() {
        return y;
    }

    /**
     * Sets y coordinate of the room.
     *
     * @param y the y
     */
    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Room: " +
                "roomName = '" + roomName + '\'' +
                "," + roomNumber +
                "," + x +
                "," + y;
    }

    /**
     * Useful custom string.
     *
     * @return the string
     */
    public String usefulString() {
        return roomNumber + "," + x + "," + y;
    }
}
