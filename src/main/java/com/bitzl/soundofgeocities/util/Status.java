package com.bitzl.soundofgeocities.util;


import com.bitzl.soundofgeocities.source.SequenceSourceStatus;

public class Status {

    private long start;
    private long last;

    public Status() {
        start = System.currentTimeMillis();
        last = start;
    }

    public void printStart(String what) {
        System.out.print(what + " ... ");
    }

    public void round(SequenceSourceStatus status) {
        System.out.println("Done.");
        long duration = (System.currentTimeMillis() - last) / 1000;
        System.out.println("\tInvalid " + status.getInvalidCount() + ". IOException: " + status.getIoExceptionCount() + ".");
        double minutes = duration / 60.0;
        System.out.println("\tTook " + duration + " s (" + minutes + "min).");
        System.out.println();
        last = System.currentTimeMillis();
    }

    public void done() {
        long duration = (System.currentTimeMillis() - start) / 1000;
        double minutes = duration / 60.0;
        System.out.println("All done. Took " + duration + " s (" + minutes + "min).");
    }
}
