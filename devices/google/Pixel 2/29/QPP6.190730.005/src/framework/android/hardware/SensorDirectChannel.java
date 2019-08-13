/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.hardware;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.MemoryFile;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.channels.Channel;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SensorDirectChannel
implements Channel {
    public static final int RATE_FAST = 2;
    public static final int RATE_NORMAL = 1;
    public static final int RATE_STOP = 0;
    public static final int RATE_VERY_FAST = 3;
    public static final int TYPE_HARDWARE_BUFFER = 2;
    public static final int TYPE_MEMORY_FILE = 1;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mClosed = new AtomicBoolean();
    private final SensorManager mManager;
    private final int mNativeHandle;
    private final long mSize;
    private final int mType;

    SensorDirectChannel(SensorManager sensorManager, int n, int n2, long l) {
        this.mManager = sensorManager;
        this.mNativeHandle = n;
        this.mType = n2;
        this.mSize = l;
        this.mCloseGuard.open("SensorDirectChannel");
    }

    static long[] encodeData(MemoryFile memoryFile) {
        int n;
        try {
            n = memoryFile.getFileDescriptor().getInt$();
        }
        catch (IOException iOException) {
            n = -1;
        }
        return new long[]{1L, 0L, n};
    }

    @Override
    public void close() {
        if (this.mClosed.compareAndSet(false, true)) {
            this.mCloseGuard.close();
            this.mManager.destroyDirectChannel(this);
        }
    }

    public int configure(Sensor sensor, int n) {
        return this.mManager.configureDirectChannelImpl(this, sensor, n);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    int getNativeHandle() {
        return this.mNativeHandle;
    }

    @Override
    public boolean isOpen() {
        return this.mClosed.get() ^ true;
    }

    @Deprecated
    public boolean isValid() {
        return this.isOpen();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MemoryType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RateLevel {
    }

}

