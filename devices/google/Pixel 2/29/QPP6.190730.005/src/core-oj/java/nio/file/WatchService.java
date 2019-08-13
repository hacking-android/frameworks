/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.WatchKey;
import java.util.concurrent.TimeUnit;

public interface WatchService
extends Closeable {
    @Override
    public void close() throws IOException;

    public WatchKey poll();

    public WatchKey poll(long var1, TimeUnit var3) throws InterruptedException;

    public WatchKey take() throws InterruptedException;
}

