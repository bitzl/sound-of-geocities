package com.bitzl.soundofgeocities.processing.counting;


import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import static org.junit.Assert.assertEquals;

public class MidiEventCountTest {

    private MidiEventCount midiEventCount;

    @Before
    public void setUp() {
        midiEventCount = new MidiEventCount();
    }

    private MidiEvent createEvent(int command) throws InvalidMidiDataException {
        ShortMessage shortMessage = new ShortMessage();
        shortMessage.setMessage(command, 0, 0, 0);
        return new MidiEvent(shortMessage, 0);
    }

    @Test
    public void testCountsNoteOnEvent() throws InvalidMidiDataException {
        midiEventCount.count(createEvent(ShortMessage.NOTE_ON));
        assertEquals(1, midiEventCount.getNoteOn());
        assertEquals(0, midiEventCount.getNoteOff());
        assertEquals(0, midiEventCount.getOther());
        assertEquals(1, midiEventCount.getAll());
    }

    @Test
    public void testCountsNoteOffEvent() throws InvalidMidiDataException {
        midiEventCount.count(createEvent(ShortMessage.NOTE_OFF));
        assertEquals(0, midiEventCount.getNoteOn());
        assertEquals(1, midiEventCount.getNoteOff());
        assertEquals(0, midiEventCount.getOther());
        assertEquals(1, midiEventCount.getAll());
    }

    @Test
    public void testCountsOtherEvent() throws InvalidMidiDataException {
        midiEventCount.count(createEvent(ShortMessage.CONTROL_CHANGE));
        assertEquals(0, midiEventCount.getNoteOn());
        assertEquals(0, midiEventCount.getNoteOff());
        assertEquals(1, midiEventCount.getOther());
        assertEquals(1, midiEventCount.getAll());
    }

    @Test
    public void testCountsSysExEventAsOther() throws InvalidMidiDataException {
        midiEventCount.count(new MidiEvent(new SysexMessage(), 0));
        assertEquals(0, midiEventCount.getNoteOn());
        assertEquals(0, midiEventCount.getNoteOff());
        assertEquals(1, midiEventCount.getOther());
        assertEquals(1, midiEventCount.getAll());
    }

}
