package com.bitzl.soundofgeocities.transformations;


import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MapSequence {

    public static Stream<MidiEvent> toMidiEvent(Sequence sequence) {
        List<MidiEvent> allEvents = new ArrayList<MidiEvent>();
        if (sequence != null) {
            for (Track track : sequence.getTracks()) {
                for (int i = 0; i < track.size(); i++) {
                    allEvents.add(track.get(i));
                }
            }
        }
        return allEvents.stream();
    }

}
