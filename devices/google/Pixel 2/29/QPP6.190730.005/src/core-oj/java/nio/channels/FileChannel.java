/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileLock;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.file.FileSystem;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class FileChannel
extends AbstractInterruptibleChannel
implements SeekableByteChannel,
GatheringByteChannel,
ScatteringByteChannel {
    private static final FileAttribute<?>[] NO_ATTRIBUTES = new FileAttribute[0];

    protected FileChannel() {
    }

    public static FileChannel open(Path path, Set<? extends OpenOption> set, FileAttribute<?> ... arrfileAttribute) throws IOException {
        return path.getFileSystem().provider().newFileChannel(path, set, arrfileAttribute);
    }

    public static FileChannel open(Path path, OpenOption ... arropenOption) throws IOException {
        HashSet hashSet = new HashSet(arropenOption.length);
        Collections.addAll(hashSet, arropenOption);
        return FileChannel.open(path, hashSet, NO_ATTRIBUTES);
    }

    public abstract void force(boolean var1) throws IOException;

    public final FileLock lock() throws IOException {
        return this.lock(0L, Long.MAX_VALUE, false);
    }

    public abstract FileLock lock(long var1, long var3, boolean var5) throws IOException;

    public abstract MappedByteBuffer map(MapMode var1, long var2, long var4) throws IOException;

    @Override
    public abstract long position() throws IOException;

    @Override
    public abstract FileChannel position(long var1) throws IOException;

    @Override
    public abstract int read(ByteBuffer var1) throws IOException;

    public abstract int read(ByteBuffer var1, long var2) throws IOException;

    @Override
    public final long read(ByteBuffer[] arrbyteBuffer) throws IOException {
        return this.read(arrbyteBuffer, 0, arrbyteBuffer.length);
    }

    @Override
    public abstract long read(ByteBuffer[] var1, int var2, int var3) throws IOException;

    @Override
    public abstract long size() throws IOException;

    public abstract long transferFrom(ReadableByteChannel var1, long var2, long var4) throws IOException;

    public abstract long transferTo(long var1, long var3, WritableByteChannel var5) throws IOException;

    @Override
    public abstract FileChannel truncate(long var1) throws IOException;

    public final FileLock tryLock() throws IOException {
        return this.tryLock(0L, Long.MAX_VALUE, false);
    }

    public abstract FileLock tryLock(long var1, long var3, boolean var5) throws IOException;

    @Override
    public abstract int write(ByteBuffer var1) throws IOException;

    public abstract int write(ByteBuffer var1, long var2) throws IOException;

    @Override
    public final long write(ByteBuffer[] arrbyteBuffer) throws IOException {
        return this.write(arrbyteBuffer, 0, arrbyteBuffer.length);
    }

    @Override
    public abstract long write(ByteBuffer[] var1, int var2, int var3) throws IOException;

    public static class MapMode {
        public static final MapMode PRIVATE;
        public static final MapMode READ_ONLY;
        public static final MapMode READ_WRITE;
        private final String name;

        static {
            READ_ONLY = new MapMode("READ_ONLY");
            READ_WRITE = new MapMode("READ_WRITE");
            PRIVATE = new MapMode("PRIVATE");
        }

        private MapMode(String string) {
            this.name = string;
        }

        public String toString() {
            return this.name;
        }
    }

}

