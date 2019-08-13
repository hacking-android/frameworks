/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.HandlerThread;
import android.os.Looper;

public final class ConnectivityThread
extends HandlerThread {
    private ConnectivityThread() {
        super("ConnectivityThread");
    }

    static /* synthetic */ ConnectivityThread access$000() {
        return ConnectivityThread.createInstance();
    }

    private static ConnectivityThread createInstance() {
        ConnectivityThread connectivityThread = new ConnectivityThread();
        connectivityThread.start();
        return connectivityThread;
    }

    public static ConnectivityThread get() {
        return Singleton.INSTANCE;
    }

    public static Looper getInstanceLooper() {
        return Singleton.INSTANCE.getLooper();
    }

    private static class Singleton {
        private static final ConnectivityThread INSTANCE = ConnectivityThread.access$000();

        private Singleton() {
        }
    }

}

