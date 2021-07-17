package org.crayzer.jvm.opcode;

import java.io.Console;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

public final class TestSystem {

    private TestSystem() {}
    public static final InputStream in = null;
    public static final PrintStream out = null;
    public static final PrintStream err = null;
    public static Console console(){return null;}
    public static native long currentTimeMillis();
    public static native long nanoTime();

    public static Properties getProperties(){return null;}
    public static void setProperties(Properties props) {}
    public static String getProperty(String key) {return null;}

    public static String lineSeparator() {return lineSeparator;}
    private static String lineSeparator;

    public static String getenv(String name){return null;}
    public static java.util.Map<String,String> getenv() {return null;}

    public static void exit(int status) { Runtime.getRuntime().exit(status); }
    public static void gc() {
        Runtime.getRuntime().gc();
    }
    /**
     * Initialize the system class.  Called after thread initialization.
     */
    private static void initPhase1() {}
}
