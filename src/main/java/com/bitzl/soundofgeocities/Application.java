package com.bitzl.soundofgeocities;

import com.bitzl.soundofgeocities.source.ZipSequenceSource;
import com.bitzl.soundofgeocities.transformations.MapSequence;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.main();
    }

    public void main() throws IOException {
        final String filename = "D:/Daten/2009.GeoCities.MIDI.ArchiveTeam.zip";
        final long start = System.currentTimeMillis();
        ZipSequenceSource source = new ZipSequenceSource(filename);

        long count = source
                .stream()
                .parallel()
                .flatMap(MapSequence::toMidiEvent)
                .count();

        long duration = (System.currentTimeMillis() - start) / 1000;
        System.out.println("Count: " + count + ".");
        System.out.println("Invalid " + source.invalidCount() + ". IOException: " + source.ioExceptionCount() + ".");
        double minutes = duration / 60.0;
        System.out.println("Took " + duration + " s (" + minutes + "min).");
    }

}
