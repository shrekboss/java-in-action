package org.crayzer.design.design_mode_pattern.behavioural.template;

public class Runtime {
    private static class ShutdownHook extends Thread {
        @Override
        public void run() {
            System.out.println("I am called during shutting down.");
        }
    }

    public static void main(String[] args) {
        java.lang.Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }
}
