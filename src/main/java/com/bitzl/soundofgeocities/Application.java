package com.bitzl.soundofgeocities;

import com.bitzl.soundofgeocities.processing.counting.EventCountingProcessor;
import com.bitzl.soundofgeocities.processing.ProcessEngine;
import com.bitzl.soundofgeocities.source.ZipSequenceSource;
import com.bitzl.soundofgeocities.util.Status;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

public class Application {

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.main();
    }

    // 384569450 Events in total
    public void main() throws IOException {
//        final String filename = "D:/Daten/2009.GeoCities.MIDI.ArchiveTeam.zip";
        final String filename = "D:/Daten/MIDI-Test.zip";

//        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
//        for (String inputArgument : bean.getInputArguments()) {
//            System.out.println(inputArgument);
//        }

        ProcessEngine processEngine = new ProcessEngine(new ZipSequenceSource(new File(filename)));
        processEngine.register("Count Midi Events", new EventCountingProcessor());
        processEngine.process();

        System.out.println();
        System.out.println();

        Gson gson = new Gson();
        System.out.println(gson.toJson(processEngine.getResults()));
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
