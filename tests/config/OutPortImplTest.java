package config;

import dataProviders.out.OutPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.List;

import static org.mockito.Mockito.*;

public class OutPortImplTest {
    OutPort outPort;
    PrintStream printStream = mock(PrintStream.class);

    @BeforeEach
    public void init() {
        outPort = new OutPortImpl(printStream);
    }

    @Test
    public void print_stream_is_used() {
        for (CharSequence text : List.of("something", "")) {
            outPort.print(text);
            verify(printStream, times(1)).println(text);
        }
    }

}