/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

public class ThreadControllerWrapper {
    private static ThreadController m_tpool = new ThreadController();

    public static Thread runThread(Runnable runnable, int n) {
        return m_tpool.run(runnable, n);
    }

    public static void waitThread(Thread thread, Runnable runnable) throws InterruptedException {
        m_tpool.waitThread(thread, runnable);
    }

    public static class ThreadController {
        public Thread run(Runnable runnable, int n) {
            runnable = new Thread(runnable);
            ((Thread)runnable).start();
            return runnable;
        }

        public void waitThread(Thread thread, Runnable runnable) throws InterruptedException {
            thread.join();
        }
    }

}

