package com.humphrey.boomshare.bean;

/**
 * Created by Humphrey on 2016/3/25.
 */
public class ImageBean {

    private String topImagePath;
    private String folderName;
    private int ImageCount;

    public String getTopImagePath() {
        return topImagePath;
    }

    public void setTopImagePath(String topImagePath) {
        this.topImagePath = topImagePath;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getImageCount() {
        return ImageCount;
    }

    public void setImageCount(int imageCount) {
        ImageCount = imageCount;
    }
}
