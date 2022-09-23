package org.example;

public class Room {
    String roomName;
    String roomNumber;
    String x;
    String y;

    public Room(String roomName, String roomNumber, String x, String y) {
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.x = x;
        this.y = y;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Room: " +
                "roomName = '" + roomName + '\'' +
                ", roomNumber = " + roomNumber +
                "," + x +
                "," + y;
    }
}
