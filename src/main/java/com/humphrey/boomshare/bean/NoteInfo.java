package com.humphrey.boomshare.bean;

/**
 * Created by Humphrey on 2016/3/31.
 */
public class NoteInfo {
    private String name;
    private String type;
    private int picIndex;

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

    public int getPicIndex() {
        return picIndex;
    }

    public void setPicIndex(int picIndex) {
        this.picIndex = picIndex;
    }

    @Override
    public String toString() {
        return "NoteInfo{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", picIndex=" + picIndex +
                '}';
    }
}
