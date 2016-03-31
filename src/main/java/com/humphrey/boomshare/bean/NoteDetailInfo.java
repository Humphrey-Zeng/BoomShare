package com.humphrey.boomshare.bean;

import java.util.Arrays;

/**
 * Created by Humphrey on 2016/3/31.
 */
public class NoteDetailInfo {
    private String name;
    private int index;
    private byte[] picture;

    @Override
    public String toString() {
        return "NoteDetailInfo{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", picture=" + Arrays.toString(picture) +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
