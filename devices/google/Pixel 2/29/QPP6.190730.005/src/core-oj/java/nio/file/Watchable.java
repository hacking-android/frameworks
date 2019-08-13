/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.IOException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public interface Watchable {
    public WatchKey register(WatchService var1, WatchEvent.Kind<?> ... var2) throws IOException;

    public WatchKey register(WatchService var1, WatchEvent.Kind<?>[] var2, WatchEvent.Modifier ... var3) throws IOException;
}

