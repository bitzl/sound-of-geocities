package com.bitzl.soundofgeocities.processing.counting;


import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

/**
 * Counts MidiEvents depending on their purpose.
 */
public class MidiEventCount {

    private long noteOn;

    private long noteOff;

    private long other;

    public MidiEventCount() {
        noteOn = 0;
        noteOff = 0;
        other = 0;
    }

    /**
     * Performs the counting.
     * @param event The MidiEvent to count.
     */
    public void count(MidiEvent event) {
        MidiMessage message = event.getMessage();
        if (message instanceof ShortMessage) {
            int command = ((ShortMessage) message).getCommand();
            switch (command) {
                case ShortMessage.NOTE_ON:
                    noteOn++;
                    break;
                case ShortMessage.NOTE_OFF:
                    noteOff++;
                    break;
                default:
                    other++;
            }
        } else {
            other++;
        }
    }

    /**
     * @return the number of all NoteOn events.
     */
    public long getNoteOn() {
        return noteOn;
    }

    /**
     * @return the number of all NoteOff events.
     */
    public long getNoteOff() {
        return noteOff;
    }

    /**
     * @return the number of all other events.
     */
    public long getOther() {
        return other;
    }

    /**
     * The total count of all MidiEvents.
     *
     * @return The sum of all counts.
     */
    public long getAll() {
        return noteOn + noteOff + other;
    }
}
