package com.bitzl.soundofgeocities.processing;

import com.bitzl.soundofgeocities.source.SequenceSource;

import javax.sound.midi.Sequence;
import java.util.stream.Stream;


public interface Processor {

    Object process(Stream<Sequence> source);

}
