package com.bitzl.soundofgeocities.source;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

public class ZipSequenceSource implements SequenceSource {

    private final File source;

    private SequenceSourceStatus status;

    public ZipSequenceSource(File source) throws IOException {
        this.source = source;
    }

    @Override
    public Stream<Sequence> stream() throws IOException {
        ZipFile zipFile = new ZipFile(source);
        status = new SequenceSourceStatus();
        return zipFile.stream()
                .filter(zipEntry -> zipEntry.getName().toLowerCase().endsWith(".mid"))
                .map(zipEntry -> {
                    try {
                        return MidiSystem.getSequence(zipFile.getInputStream(zipEntry));
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
    public SequenceSourceStatus getStatus() {
        return status;
    }

}
