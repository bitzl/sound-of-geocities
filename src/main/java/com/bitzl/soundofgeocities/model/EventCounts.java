package com.bitzl.soundofgeocities.model;

@Deprecated
public class EventCounts {

    private long all;
    private long up;
    private long down;

    public EventCounts() {
        all = 0;
        up = 0;
        down = 0;
    }

    public void addAll() {
        all++;
    }

    public void addUp() {
        up++;
    }

    public void addDown() {
        down++;
    }

    public long getAll() {
        return all;
    }

    public long getUp() {
        return up;
    }

    public long getDown() {
        return down;
    }
}
