package com.bitzl.soundofgeocities.processing;

import com.bitzl.soundofgeocities.processing.ProcessEngine;
import com.bitzl.soundofgeocities.processing.Processor;
import com.bitzl.soundofgeocities.source.SequenceSource;
import com.bitzl.soundofgeocities.source.SequenceSourceStatus;
import com.bitzl.soundofgeocities.util.Status;
import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.Sequence;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProcessEngineTest {

    private ProcessEngine processEngine;

    @Before
    public void setUp() {
        processEngine = new ProcessEngine(new SequenceSource() {
            @Override
            public Stream<Sequence> stream() throws IOException {
                return Stream.empty();
            }

            @Override
            public SequenceSourceStatus getStatus() {
                return new SequenceSourceStatus();
            }
        });
        processEngine.setStatus(mock(Status.class));
    }

    @Test
    public void testEngineCallsProcessor() throws IOException {
        Processor processor = mock(Processor.class);
        processEngine.register("nice processor", processor);
        processEngine.process();
        verify(processor).process(any());
    }

    @Test(expected = RuntimeException.class)
    public void testEnginePreventsDoubleRegistration() {
        processEngine.register("nice processor", mock(Processor.class));
        processEngine.register("nice processor", mock(Processor.class));
    }

    @Test
    public void testEngineSavesResult() throws IOException {
        Processor processor = mock(Processor.class);
        when(processor.process(any())).thenReturn("akdhasdkhasd");
        processEngine.register("nice processor", processor);
        processEngine.process();
        assertEquals("akdhasdkhasd", processEngine.getResults().get("nice processor"));
    }
}
