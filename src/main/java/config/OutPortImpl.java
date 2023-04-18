package config;

import dataProviders.out.OutPort;

import java.io.PrintStream;

public class OutPortImpl extends OutPort {
    public OutPortImpl(PrintStream outStream) {
        super(outStream);
    }
}
