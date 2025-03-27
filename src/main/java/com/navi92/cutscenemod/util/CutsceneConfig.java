package com.navi92.cutscenemod.util;

public class CutsceneConfig {
    private int width;
    private int height;
    private int length;
    private String folder;
    private String sound;
    private boolean moving;
    private boolean esc;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public String getFolder() {
        return folder;
    }

    public String getSound() {
        return sound;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean canEsc() {
        return esc;
    }
}
