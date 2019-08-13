/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;

public interface AsynchronousByteChannel
extends AsynchronousChannel {
    public Future<Integer> read(ByteBuffer var1);

    public <A> void read(ByteBuffer var1, A var2, CompletionHandler<Integer, ? super A> var3);

    public Future<Integer> write(ByteBuffer var1);

    public <A> void write(ByteBuffer var1, A var2, CompletionHandler<Integer, ? super A> var3);
}

