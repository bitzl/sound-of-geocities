package com.bitzl.soundofgeocities.source;


import javax.sound.midi.Sequence;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Stream;

public class CachedZipSequenceSource implements SequenceSource {

    private final Sequence[] sequences;
    private final SequenceSourceStatus status;

    public CachedZipSequenceSource(String filename) throws IOException {
        ZipSequenceSource zipSequenceSource = new ZipSequenceSource(filename);
        System.out.println("Generate array... " + new Date());
        sequences = zipSequenceSource.stream().toArray(size -> new Sequence[size]);
        System.out.println("Generate array... Done." + new Date());
        zipSequenceSource.close();
        status = zipSequenceSource.getStatus();
    }

    @Override
    public Stream<Sequence> stream() {
        System.out.println("Create new Stream... " + new Date());
        return Stream.of(sequences);
    }

    @Override
    public void close() throws IOException {
        // Nothing to close because everything is in memory.
    }

    @Override
    public SequenceSourceStatus getStatus() {
        return status;
    }
}
