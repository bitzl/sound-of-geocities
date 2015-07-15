package com.bitzl.soundofgeocities;

import com.bitzl.soundofgeocities.source.ZipSequenceSource;
import com.bitzl.soundofgeocities.transformations.MapSequence;
import com.bitzl.soundofgeocities.util.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Application {

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.main();
    }

    public void main() throws IOException {
//        final String filename = "D:/Daten/2009.GeoCities.MIDI.ArchiveTeam.zip";
        final String filename = "D:/Daten/MIDI-Test.zip";

        Map<String, Object> data = new HashMap<>();

        Status status = new Status();

        data.put("MidiEvents", countMidiEvents(filename, status));

        status.done();

        System.out.println();
        System.out.println();

        Gson gson = new Gson();
        System.out.println(gson.toJson(data));
    }

    public Map<String, Long> countMidiEvents(String filename, Status status) throws IOException {
        status.printStart("Counting events");
        ZipSequenceSource source = new ZipSequenceSource(filename);

        long count = source
                .stream()
                .parallel()
                .flatMap(MapSequence::toMidiEvent)
                .count();

        status.round(source);
        Map<String, Long> result = new HashMap<>();
        result.put("allEvents", count);
        return result;
    }
}
