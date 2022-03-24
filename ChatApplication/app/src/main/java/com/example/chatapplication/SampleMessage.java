package com.example.chatapplication;

public class SampleMessage {

    private String name;
    private String message;

    public SampleMessage(){
    }

    public SampleMessage(String _name, String _message){
        name = _name;
        message = _message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
}
