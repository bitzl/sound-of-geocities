package com.bitzl.soundofgeocities;

import com.bitzl.soundofgeocities.model.EventCounts;
import com.bitzl.soundofgeocities.source.CachedZipSequenceSource;
import com.bitzl.soundofgeocities.source.SequenceSource;
import com.bitzl.soundofgeocities.source.ZipSequenceSource;
import com.bitzl.soundofgeocities.transformations.MapSequence;
import com.bitzl.soundofgeocities.util.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.sound.midi.Sequence;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

public class Application {

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.main();
    }

    public void main() throws IOException {
        final String filename = "D:/Daten/2009.GeoCities.MIDI.ArchiveTeam.zip";
//        final String filename = "D:/Daten/MIDI-Test.zip";

        Map<String, Object> data = new HashMap<>();

//        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
//        for (String inputArgument : bean.getInputArguments()) {
//            System.out.println(inputArgument);
//        }
//
//        System.out.println();
//        System.out.println();

        Status status = new Status();
        status.setShowIOStatus(false);

//        data.put("MidiEvents", countMidiEvents(source, status));

        // Without caching
//        for (int i = 0; i < 5; i++) {
//            SequenceSource source = new ZipSequenceSource(filename);
//            data.put("MidiEvents", countMidiEvents(source, status));
//            source.close();
//        }

        // With caching
        SequenceSource source = new CachedZipSequenceSource(filename);
        for (int i = 0; i < 5; i++) {
            data.put("MidiEvents", countMidiEvents(source, status));
        }

//        sizeInfo(filename, status);

        status.done();

        System.out.println();
        System.out.println();

        Gson gson = new Gson();
        System.out.println(gson.toJson(data));
    }

    // Embed in an execution frame (Register with Name/Message, inject a new ZipSequenceSource,
    // do status messages automatically,...)
    public EventCounts countMidiEvents(SequenceSource source, Status status) throws IOException {
        status.printStart("Counting events");
        MidiEventCounter midiEventCounter = new MidiEventCounter();
        source.stream()
                .parallel()
                .flatMap(MapSequence::toMidiEvent)
                .forEach(midiEventCounter::count);
        status.round(source.getStatus());
        return midiEventCounter.getEventCounts();
    }

    private void sizeInfo(String filename, Status status) throws IOException {
        status.printStart("Loading file sizes");
        ZipFile source = new ZipFile(filename);
        long totalUncompressedSize = source.stream().mapToLong(zipEntry -> zipEntry.getSize()).sum();
//        long totalCompressedSize = source.stream().mapToLong(zipEntry -> zipEntry.getCompressedSize()).sum();
        double totalUncompressedSizeInGB = totalUncompressedSize / 1024.0 / 1024;
//        double totalCompressedSizeInGB = totalCompressedSize / 1024.0 / 1024;
        System.out.println("Total uncompressed size: " + totalUncompressedSizeInGB + " MB. ("
                + totalUncompressedSize + " bytes).");
//        System.out.println("Total compressed size: " + totalCompressedSizeInGB + " MB. ("
//                + totalCompressedSize + " bytes).");
        source.close();
    }
}
