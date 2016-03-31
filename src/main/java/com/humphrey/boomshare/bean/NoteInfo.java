package com.humphrey.boomshare.bean;

/**
 * Created by Humphrey on 2016/3/31.
 */
public class NoteInfo {
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NoteInfo{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
