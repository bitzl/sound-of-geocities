package com.bitzl.soundofgeocities.source;


import com.sun.media.sound.StandardMidiFileReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.spi.MidiFileReader;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ZipSequenceSource {

    private final ZipFile zipFile;
    private int invalidCount;
    private int ioExceptionCount;

    public ZipSequenceSource(String filename) throws IOException {
        zipFile = new ZipFile(new File(filename));
        invalidCount = 0;
        ioExceptionCount = 0;
    }

    public Stream<Sequence> stream() {
        return zipFile.stream()
                .filter(zipEntry -> zipEntry.getName().toLowerCase().endsWith(".mid"))
                .map(zipEntry -> {
                    MidiFileReader midiFileReader = new StandardMidiFileReader();
                    Sequence sequence = null;
                    try {
                        return midiFileReader.getSequence(zipFile.getInputStream(zipEntry));
                    } catch (InvalidMidiDataException e) {
                        invalidCount++;
                    } catch (IOException e) {
                        ioExceptionCount++;
                    }
                    return null;
                })
                .filter(sequence -> {
                    return sequence != null;
                });
    }

    public void close() throws IOException {
        zipFile.close();
    }

    public int invalidCount() {
        return invalidCount;
    }

    public int ioExceptionCount() {
        return ioExceptionCount;
    }

}
