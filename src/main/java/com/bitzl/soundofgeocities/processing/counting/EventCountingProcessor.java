package com.bitzl.soundofgeocities.processing.counting;

import com.bitzl.soundofgeocities.processing.Processor;
import com.bitzl.soundofgeocities.transformations.MapSequence;

import javax.sound.midi.Sequence;
import java.util.stream.Stream;

/**
 * Created by Marcus on 26.07.2015.
 */
public class EventCountingProcessor implements Processor {
    @Override
    public Object process(Stream<Sequence> stream) {
        MidiEventCount midiEventCount = new MidiEventCount();
        stream.parallel()
                .flatMap(MapSequence::toMidiEvent)
                .forEach(midiEventCount::count);
        return midiEventCount;
    }
}
