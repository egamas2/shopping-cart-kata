package dataProviders.out;

import java.io.FilterOutputStream;
import java.io.PrintStream;

public abstract class OutPort {

    PrintStream outStream;

    protected OutPort(PrintStream outStream) {
        this.outStream = outStream;
    }

    public void print(CharSequence text) {
        outStream.println(text);
    }
}
