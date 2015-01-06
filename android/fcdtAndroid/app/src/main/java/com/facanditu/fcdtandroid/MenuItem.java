package com.facanditu.fcdtandroid;

/**
 * Created by fengqin on 14/12/30.
 */
public class MenuItem {

    private int icon;
    private String title;
    private String tagValue;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public MenuItem(int icon, String title, String tagValue) {
        this.icon = icon;
        this.title = title;
        this.tagValue = tagValue;
    }
}
