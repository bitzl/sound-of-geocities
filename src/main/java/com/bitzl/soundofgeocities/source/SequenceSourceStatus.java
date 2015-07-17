package com.bitzl.soundofgeocities.source;

/**
 * Created by Marcus on 15.07.2015.
 */
public class SequenceSourceStatus {
    private int invalidCount;
    private int ioExceptionCount;

    public void invalid() {
        invalidCount++;
    }

    public void ioException() {
        ioExceptionCount++;
    }

    public int getInvalidCount() {
        return invalidCount;
    }

    public int getIoExceptionCount() {
        return ioExceptionCount;
    }
}
