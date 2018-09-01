package com.itsm.util;

public class ThreadSleeper {
    public static void sleep(int m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("ThreadSleeper.sleep interrupted");
        }
    }
}
