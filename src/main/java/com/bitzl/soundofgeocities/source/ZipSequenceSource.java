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

public class ZipSequenceSource implements SequenceSource {

    private final ZipFile zipFile;
    private SequenceSourceStatus status;

    public ZipSequenceSource(String filename) throws IOException {
        this(new File(filename));
    }

    public ZipSequenceSource(File source) throws IOException {
        zipFile = new ZipFile(source);
        status = new SequenceSourceStatus();
    }

    @Override
    public Stream<Sequence> stream() {
        return zipFile.stream()
                .filter(zipEntry -> zipEntry.getName().toLowerCase().endsWith(".mid"))
                .map(zipEntry -> {
                    MidiFileReader midiFileReader = new StandardMidiFileReader();
                    Sequence sequence = null;
                    try {
                        return midiFileReader.getSequence(zipFile.getInputStream(zipEntry));
                    } catch (InvalidMidiDataException e) {
                        status.invalid();
                    } catch (IOException e) {
                        status.ioException();
                    }
                    return null;
                })
                .filter(sequence -> sequence != null);
    }

    @Override
    public void close() throws IOException {
        zipFile.close();
    }

    @Override
    public SequenceSourceStatus getStatus() {
        return status;
    }

}
