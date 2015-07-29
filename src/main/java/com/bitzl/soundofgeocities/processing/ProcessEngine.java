package com.bitzl.soundofgeocities.processing;

import com.bitzl.soundofgeocities.source.SequenceSource;
import com.bitzl.soundofgeocities.source.ZipSequenceSource;
import com.bitzl.soundofgeocities.util.Status;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class ProcessEngine {

    private final SequenceSource sequenceSource;

    private Map<String, Processor> processors;

    private Map<String, Object> results;

    private Status status;

    public ProcessEngine(SequenceSource sequenceSource) {
        this.sequenceSource = sequenceSource;
        this.processors = new HashMap<>();
        this.results = new HashMap<>();
        this.status = new Status();
    }

    private Object execute(Processor processor) throws IOException {
        Stream stream = sequenceSource.stream();
        Object result = processor.process(stream);
        stream.close();
        return result;
    }

    public void register(String name, Processor processor) {
        if (processors.containsKey(name)) {
            throw new RuntimeException("Processor " + name + " is already registered.");
        }
        processors.put(name, processor);
    }

    public void process() throws IOException {
        List<String> names = new ArrayList<>(processors.keySet());
        Collections.sort(names);
        for (String name : names) {
            status.printStart("Processing '" + name + "'.");
            results.put(name, execute(processors.get(name)));
            status.round();
        }
        status.done();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Map<String, Object> getResults() {
        return results;
    }
}
