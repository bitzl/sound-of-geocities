package com.bitzl.soundofgeocities.source;

import javax.sound.midi.Sequence;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.zip.ZipException;

/**
 * Created by Marcus on 17.07.2015.
 */
public interface SequenceSource {
    Stream<Sequence> stream() throws IOException;

    SequenceSourceStatus getStatus();

}
