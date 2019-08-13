/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.WatchEvent;
import java.nio.file.Watchable;
import java.util.List;

public interface WatchKey {
    public void cancel();

    public boolean isValid();

    public List<WatchEvent<?>> pollEvents();

    public boolean reset();

    public Watchable watchable();
}

