/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.SelectableChannel;

public class ChannelInputStream
extends InputStream {
    private byte[] b1 = null;
    private ByteBuffer bb = null;
    private byte[] bs = null;
    protected final ReadableByteChannel ch;

    public ChannelInputStream(ReadableByteChannel readableByteChannel) {
        this.ch = readableByteChannel;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int read(ReadableByteChannel object, ByteBuffer byteBuffer, boolean bl) throws IOException {
        if (!(object instanceof SelectableChannel)) {
            return object.read(byteBuffer);
        }
        SelectableChannel selectableChannel = (SelectableChannel)object;
        Object object2 = selectableChannel.blockingLock();
        synchronized (object2) {
            boolean bl2 = selectableChannel.isBlocking();
            if (!bl2) {
                object = new IllegalBlockingModeException();
                throw object;
            }
            if (bl2 != bl) {
                selectableChannel.configureBlocking(bl);
            }
            int n = object.read(byteBuffer);
            if (bl2 != bl) {
                selectableChannel.configureBlocking(bl2);
            }
            return n;
        }
    }

    @Override
    public int available() throws IOException {
        ReadableByteChannel readableByteChannel = this.ch;
        if (readableByteChannel instanceof SeekableByteChannel) {
            long l = Math.max(0L, (readableByteChannel = (SeekableByteChannel)readableByteChannel).size() - readableByteChannel.position());
            int n = l > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)l;
            return n;
        }
        return 0;
    }

    @Override
    public void close() throws IOException {
        this.ch.close();
    }

    @Override
    public int read() throws IOException {
        synchronized (this) {
            block4 : {
                if (this.b1 == null) {
                    this.b1 = new byte[1];
                }
                if (this.read(this.b1) != 1) break block4;
                byte by = this.b1[0];
                return by & 255;
            }
            return -1;
        }
    }

    protected int read(ByteBuffer byteBuffer) throws IOException {
        return ChannelInputStream.read(this.ch, byteBuffer, true);
    }

    @Override
    public int read(byte[] object, int n, int n2) throws IOException {
        synchronized (this) {
            block9 : {
                if (n >= 0) {
                    if (n > ((byte[])object).length || n2 < 0) break block9;
                    int n3 = ((byte[])object).length;
                    if (n + n2 <= n3 && n + n2 >= 0) {
                        if (n2 == 0) {
                            return 0;
                        }
                        ByteBuffer byteBuffer = this.bs == object ? this.bb : ByteBuffer.wrap(object);
                        byteBuffer.limit(Math.min(n + n2, byteBuffer.capacity()));
                        byteBuffer.position(n);
                        this.bb = byteBuffer;
                        this.bs = object;
                        n = this.read(byteBuffer);
                        return n;
                    }
                }
            }
            object = new IndexOutOfBoundsException();
            throw object;
        }
    }
}

