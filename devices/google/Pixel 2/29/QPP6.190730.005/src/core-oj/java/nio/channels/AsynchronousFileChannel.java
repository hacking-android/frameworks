/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.nio.file.FileSystem;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class AsynchronousFileChannel
implements AsynchronousChannel {
    private static final FileAttribute<?>[] NO_ATTRIBUTES = new FileAttribute[0];

    protected AsynchronousFileChannel() {
    }

    public static AsynchronousFileChannel open(Path path, Set<? extends OpenOption> set, ExecutorService executorService, FileAttribute<?> ... arrfileAttribute) throws IOException {
        return path.getFileSystem().provider().newAsynchronousFileChannel(path, set, executorService, arrfileAttribute);
    }

    public static AsynchronousFileChannel open(Path path, OpenOption ... arropenOption) throws IOException {
        HashSet hashSet = new HashSet(arropenOption.length);
        Collections.addAll(hashSet, arropenOption);
        return AsynchronousFileChannel.open(path, hashSet, null, NO_ATTRIBUTES);
    }

    public abstract void force(boolean var1) throws IOException;

    public final Future<FileLock> lock() {
        return this.lock(0L, Long.MAX_VALUE, false);
    }

    public abstract Future<FileLock> lock(long var1, long var3, boolean var5);

    public abstract <A> void lock(long var1, long var3, boolean var5, A var6, CompletionHandler<FileLock, ? super A> var7);

    public final <A> void lock(A a, CompletionHandler<FileLock, ? super A> completionHandler) {
        this.lock(0L, Long.MAX_VALUE, false, a, completionHandler);
    }

    public abstract Future<Integer> read(ByteBuffer var1, long var2);

    public abstract <A> void read(ByteBuffer var1, long var2, A var4, CompletionHandler<Integer, ? super A> var5);

    public abstract long size() throws IOException;

    public abstract AsynchronousFileChannel truncate(long var1) throws IOException;

    public final FileLock tryLock() throws IOException {
        return this.tryLock(0L, Long.MAX_VALUE, false);
    }

    public abstract FileLock tryLock(long var1, long var3, boolean var5) throws IOException;

    public abstract Future<Integer> write(ByteBuffer var1, long var2);

    public abstract <A> void write(ByteBuffer var1, long var2, A var4, CompletionHandler<Integer, ? super A> var5);
}

