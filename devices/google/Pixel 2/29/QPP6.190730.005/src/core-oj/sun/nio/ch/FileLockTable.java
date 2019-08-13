/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.List;
import sun.nio.ch.SharedFileLockTable;

abstract class FileLockTable {
    protected FileLockTable() {
    }

    public static FileLockTable newSharedFileLockTable(Channel channel, FileDescriptor fileDescriptor) throws IOException {
        return new SharedFileLockTable(channel, fileDescriptor);
    }

    public abstract void add(FileLock var1) throws OverlappingFileLockException;

    public abstract void remove(FileLock var1);

    public abstract List<FileLock> removeAll();

    public abstract void replace(FileLock var1, FileLock var2);
}

