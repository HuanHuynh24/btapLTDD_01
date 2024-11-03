package com.example.smsandroid;

public class SmS {
    private String Address, body, time;

    public SmS(String address, String body, String time) {
        Address = address;
        this.body = body;
        this.time = time;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
