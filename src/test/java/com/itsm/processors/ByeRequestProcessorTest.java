package com.itsm.processors;

import com.itsm.parse.JsonRequest;
import com.itsm.parse.JsonResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ByeRequestProcessorTest {

    private ByeRequestProcessor processor;

    @Before
    public void setUp() {
        processor = new ByeRequestProcessor();
    }

    @Test
    public void canProcess1() {
        boolean can = processor.canProcess(new JsonRequest("", "Bye"));
        assertTrue(can);
    }

    @Test
    public void canProcess2() {
        boolean can = processor.canProcess(new JsonRequest("", "Hello"));
        assertTrue(!can);
    }

    @Test
    public void process() throws NullPointerException {
        JsonRequest req = new JsonRequest("Name", "see you later");
        JsonResponse process = null;
        if (processor.canProcess(req)) {
            process = processor.process(req);
        }
        assertEquals("Good bye, Name.", process.getMessage());
    }
}