/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

public abstract class FileLock
implements AutoCloseable {
    private final Channel channel;
    private final long position;
    private final boolean shared;
    private final long size;

    protected FileLock(AsynchronousFileChannel asynchronousFileChannel, long l, long l2, boolean bl) {
        if (l >= 0L) {
            if (l2 >= 0L) {
                if (l + l2 >= 0L) {
                    this.channel = asynchronousFileChannel;
                    this.position = l;
                    this.size = l2;
                    this.shared = bl;
                    return;
                }
                throw new IllegalArgumentException("Negative position + size");
            }
            throw new IllegalArgumentException("Negative size");
        }
        throw new IllegalArgumentException("Negative position");
    }

    protected FileLock(FileChannel fileChannel, long l, long l2, boolean bl) {
        if (l >= 0L) {
            if (l2 >= 0L) {
                if (l + l2 >= 0L) {
                    this.channel = fileChannel;
                    this.position = l;
                    this.size = l2;
                    this.shared = bl;
                    return;
                }
                throw new IllegalArgumentException("Negative position + size");
            }
            throw new IllegalArgumentException("Negative size");
        }
        throw new IllegalArgumentException("Negative position");
    }

    public Channel acquiredBy() {
        return this.channel;
    }

    public final FileChannel channel() {
        Channel channel = this.channel;
        channel = channel instanceof FileChannel ? (FileChannel)channel : null;
        return channel;
    }

    @Override
    public final void close() throws IOException {
        this.release();
    }

    public final boolean isShared() {
        return this.shared;
    }

    public abstract boolean isValid();

    public final boolean overlaps(long l, long l2) {
        long l3 = this.position;
        if (l + l2 <= l3) {
            return false;
        }
        return l3 + this.size > l;
    }

    public final long position() {
        return this.position;
    }

    public abstract void release() throws IOException;

    public final long size() {
        return this.size;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[");
        stringBuilder.append(this.position);
        stringBuilder.append(":");
        stringBuilder.append(this.size);
        stringBuilder.append(" ");
        String string = this.shared ? "shared" : "exclusive";
        stringBuilder.append(string);
        stringBuilder.append(" ");
        string = this.isValid() ? "valid" : "invalid";
        stringBuilder.append(string);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

