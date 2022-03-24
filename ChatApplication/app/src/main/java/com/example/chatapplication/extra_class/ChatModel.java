package com.example.chatapplication.extra_class;

import java.util.ArrayList;

public class ChatModel {
    public String RoomID;
    public ArrayList<String> Users;

    public ChatModel(){}

    public ChatModel(ArrayList<String> Users, String RoomID){
        this.Users = Users;
        this.RoomID = RoomID;
    }

    public ArrayList<String> getUsers() {
        return Users;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public void setUsers(ArrayList<String> users) {
        Users = users;
    }
}
