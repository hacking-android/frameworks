/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import sun.nio.ch.ChannelInputStream;
import sun.nio.cs.StreamDecoder;
import sun.nio.cs.StreamEncoder;

public final class Channels {
    private Channels() {
    }

    private static void checkNotNull(Object object, String string) {
        if (object != null) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("\"");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("\" is null!");
        throw new NullPointerException(((StringBuilder)object).toString());
    }

    public static ReadableByteChannel newChannel(InputStream inputStream) {
        Channels.checkNotNull(inputStream, "in");
        if (inputStream instanceof FileInputStream && FileInputStream.class.equals(inputStream.getClass())) {
            return ((FileInputStream)inputStream).getChannel();
        }
        return new ReadableByteChannelImpl(inputStream);
    }

    public static WritableByteChannel newChannel(OutputStream outputStream) {
        Channels.checkNotNull(outputStream, "out");
        return new WritableByteChannelImpl(outputStream);
    }

    public static InputStream newInputStream(final AsynchronousByteChannel asynchronousByteChannel) {
        Channels.checkNotNull(asynchronousByteChannel, "ch");
        return new InputStream(){
            private byte[] b1 = null;
            private ByteBuffer bb = null;
            private byte[] bs = null;

            @Override
            public void close() throws IOException {
                asynchronousByteChannel.close();
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

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public int read(byte[] object, int n, int n2) throws IOException {
                Throwable throwable2222;
                int n3;
                // MONITORENTER : this
                if (n >= 0 && n <= ((Object)object).length && n2 >= 0 && n + n2 <= (n3 = ((Object)object).length) && n + n2 >= 0) {
                    if (n2 == 0) {
                        // MONITOREXIT : this
                        return 0;
                    }
                } else {
                    object = new IndexOutOfBoundsException();
                    throw object;
                }
                ByteBuffer byteBuffer = this.bs == object ? this.bb : ByteBuffer.wrap((byte[])object);
                byteBuffer.position(n);
                byteBuffer.limit(Math.min(n + n2, byteBuffer.capacity()));
                this.bb = byteBuffer;
                this.bs = object;
                n = 0;
                do {
                    block14 : {
                        n2 = asynchronousByteChannel.read(byteBuffer).get();
                        if (n == 0) break block14;
                        Thread.currentThread().interrupt();
                    }
                    // MONITOREXIT : this
                    return n2;
                    {
                        catch (Throwable throwable2222) {
                            break;
                        }
                        catch (InterruptedException interruptedException) {
                            n = 1;
                            continue;
                        }
                        catch (ExecutionException executionException) {}
                        {
                            object = new IOException(executionException.getCause());
                            throw object;
                        }
                    }
                    break;
                } while (true);
                if (n == 0) throw throwable2222;
                Thread.currentThread().interrupt();
                throw throwable2222;
            }
        };
    }

    public static InputStream newInputStream(ReadableByteChannel readableByteChannel) {
        Channels.checkNotNull(readableByteChannel, "ch");
        return new ChannelInputStream(readableByteChannel);
    }

    public static OutputStream newOutputStream(final AsynchronousByteChannel asynchronousByteChannel) {
        Channels.checkNotNull(asynchronousByteChannel, "ch");
        return new OutputStream(){
            private byte[] b1 = null;
            private ByteBuffer bb = null;
            private byte[] bs = null;

            @Override
            public void close() throws IOException {
                asynchronousByteChannel.close();
            }

            @Override
            public void write(int n) throws IOException {
                synchronized (this) {
                    if (this.b1 == null) {
                        this.b1 = new byte[1];
                    }
                    this.b1[0] = (byte)n;
                    this.write(this.b1);
                    return;
                }
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void write(byte[] var1_1, int var2_7, int var3_8) throws IOException {
                // MONITORENTER : this
                if (var2_10 >= 0 && var2_10 <= var1_1 /* !! */ .length && var3_11 >= 0 && var2_10 + var3_11 <= (var4_12 = var1_1 /* !! */ .length) && var2_10 + var3_11 >= 0) {
                    if (var3_11 == 0) {
                        // MONITOREXIT : this
                        return;
                    }
                } else {
                    var1_8 = new IndexOutOfBoundsException();
                    throw var1_8;
                }
                var5_13 = this.bs == var1_1 /* !! */  ? this.bb : ByteBuffer.wrap(var1_1 /* !! */ );
                var5_13.limit(Math.min((int)(var2_10 + var3_11), var5_13.capacity()));
                var5_13.position(var2_10);
                this.bb = var5_13;
                this.bs = var1_1 /* !! */ ;
                var2_10 = 0;
                do {
                    var3_11 = var5_13.remaining();
                    if (var3_11 <= 0) ** GOTO lbl34
                    try {
                        asynchronousByteChannel.write((ByteBuffer)var5_13).get();
                        continue;
                    }
                    catch (Throwable var1_3) {
                    }
                    catch (InterruptedException var1_4) {
                        var2_10 = 1;
                        continue;
                    }
                    break;
                } while (true);
                catch (ExecutionException var1_5) {
                    try {
                        var5_13 = new IOException(var1_5.getCause());
                        throw var5_13;
                    }
                    catch (Throwable var1_6) {
                        // empty catch block
                    }
lbl34: // 1 sources:
                    if (var2_10) {
                        Thread.currentThread().interrupt();
                    }
                    // MONITOREXIT : this
                    return;
                }
                if (var2_10 == 0) throw var1_7;
                Thread.currentThread().interrupt();
                throw var1_7;
            }
        };
    }

    public static OutputStream newOutputStream(final WritableByteChannel writableByteChannel) {
        Channels.checkNotNull(writableByteChannel, "ch");
        return new OutputStream(){
            private byte[] b1 = null;
            private ByteBuffer bb = null;
            private byte[] bs = null;

            @Override
            public void close() throws IOException {
                writableByteChannel.close();
            }

            @Override
            public void write(int n) throws IOException {
                synchronized (this) {
                    if (this.b1 == null) {
                        this.b1 = new byte[1];
                    }
                    this.b1[0] = (byte)n;
                    this.write(this.b1);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void write(byte[] object, int n, int n2) throws IOException {
                synchronized (this) {
                    int n3;
                    if (n >= 0 && n <= ((byte[])object).length && n2 >= 0 && n + n2 <= (n3 = ((byte[])object).length) && n + n2 >= 0) {
                        if (n2 == 0) {
                            return;
                        }
                        ByteBuffer byteBuffer = this.bs == object ? this.bb : ByteBuffer.wrap(object);
                        byteBuffer.limit(Math.min(n + n2, byteBuffer.capacity()));
                        byteBuffer.position(n);
                        this.bb = byteBuffer;
                        this.bs = object;
                        Channels.writeFully(writableByteChannel, byteBuffer);
                        return;
                    }
                    IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException();
                    throw indexOutOfBoundsException;
                }
            }
        };
    }

    public static Reader newReader(ReadableByteChannel readableByteChannel, String string) {
        Channels.checkNotNull(string, "csName");
        return Channels.newReader(readableByteChannel, Charset.forName(string).newDecoder(), -1);
    }

    public static Reader newReader(ReadableByteChannel readableByteChannel, CharsetDecoder charsetDecoder, int n) {
        Channels.checkNotNull(readableByteChannel, "ch");
        return StreamDecoder.forDecoder(readableByteChannel, charsetDecoder.reset(), n);
    }

    public static Writer newWriter(WritableByteChannel writableByteChannel, String string) {
        Channels.checkNotNull(string, "csName");
        return Channels.newWriter(writableByteChannel, Charset.forName(string).newEncoder(), -1);
    }

    public static Writer newWriter(WritableByteChannel writableByteChannel, CharsetEncoder charsetEncoder, int n) {
        Channels.checkNotNull(writableByteChannel, "ch");
        return StreamEncoder.forEncoder(writableByteChannel, charsetEncoder.reset(), n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void writeFully(WritableByteChannel object, ByteBuffer byteBuffer) throws IOException {
        if (!(object instanceof SelectableChannel)) {
            Channels.writeFullyImpl((WritableByteChannel)object, byteBuffer);
            return;
        }
        SelectableChannel selectableChannel = (SelectableChannel)object;
        Object object2 = selectableChannel.blockingLock();
        synchronized (object2) {
            if (selectableChannel.isBlocking()) {
                Channels.writeFullyImpl((WritableByteChannel)object, byteBuffer);
                return;
            }
            object = new IllegalBlockingModeException();
            throw object;
        }
    }

    private static void writeFullyImpl(WritableByteChannel writableByteChannel, ByteBuffer byteBuffer) throws IOException {
        while (byteBuffer.remaining() > 0) {
            if (writableByteChannel.write(byteBuffer) > 0) continue;
            throw new RuntimeException("no bytes written");
        }
    }

    private static class ReadableByteChannelImpl
    extends AbstractInterruptibleChannel
    implements ReadableByteChannel {
        private static final int TRANSFER_SIZE = 8192;
        private byte[] buf = new byte[0];
        InputStream in;
        private boolean open = true;
        private Object readLock = new Object();

        ReadableByteChannelImpl(InputStream inputStream) {
            this.in = inputStream;
        }

        @Override
        protected void implCloseChannel() throws IOException {
            this.in.close();
            this.open = false;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public int read(ByteBuffer var1_1) throws IOException {
            block11 : {
                var2_4 = var1_1.remaining();
                var3_5 = 0;
                var4_6 = 0;
                var5_7 = this.readLock;
                // MONITORENTER : var5_7
                do {
                    var6_8 = var4_6;
                    if (var3_5 >= var2_4) break block11;
                    var6_8 = Math.min(var2_4 - var3_5, 8192);
                    if (this.buf.length < var6_8) {
                        this.buf = new byte[var6_8];
                    }
                    if (var3_5 > 0 && (var7_9 = this.in.available()) <= 0) {
                        var6_8 = var4_6;
                        break block11;
                    }
                    var8_10 = true;
                    var9_11 = true;
                    this.begin();
                    var4_6 = var6_8 = this.in.read(this.buf, 0, var6_8);
                    if (var4_6 > 0) ** break block12
                    var9_11 = false;
                    break;
                } while (true);
                {
                    catch (Throwable var1_2) {
                        var9_11 = var4_6 > 0 ? var8_10 : false;
                        this.end(var9_11);
                        throw var1_2;
                    }
                }
                {
                    this.end(var9_11);
                    if (var4_6 < 0) {
                        var6_8 = var4_6;
                        break block11;
                    }
                    var3_5 += var4_6;
                    var1_1.put(this.buf, 0, var4_6);
                    continue;
                }
                catch (Throwable var1_3) {
                    throw var1_3;
                }
            }
            if (var6_8 < 0 && var3_5 == 0) {
                // MONITOREXIT : var5_7
                return -1;
            }
            // MONITOREXIT : var5_7
            return var3_5;
        }
    }

    private static class WritableByteChannelImpl
    extends AbstractInterruptibleChannel
    implements WritableByteChannel {
        private static final int TRANSFER_SIZE = 8192;
        private byte[] buf = new byte[0];
        private boolean open = true;
        OutputStream out;
        private Object writeLock = new Object();

        WritableByteChannelImpl(OutputStream outputStream) {
            this.out = outputStream;
        }

        @Override
        protected void implCloseChannel() throws IOException {
            this.out.close();
            this.open = false;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public int write(ByteBuffer var1_1) throws IOException {
            var2_3 = var1_1.remaining();
            var3_4 = 0;
            var4_5 = this.writeLock;
            // MONITORENTER : var4_5
            do lbl-1000: // 2 sources:
            {
                if (var3_4 >= var2_3) {
                    // MONITOREXIT : var4_5
                    return var3_4;
                }
                var5_6 = Math.min(var2_3 - var3_4, 8192);
                if (this.buf.length < var5_6) {
                    this.buf = new byte[var5_6];
                }
                var1_1.get(this.buf, 0, var5_6);
                var6_7 = true;
                var7_8 = true;
                this.begin();
                this.out.write(this.buf, 0, var5_6);
                if (var5_6 > 0) ** break block8
                var7_8 = false;
                break;
            } while (true);
            catch (Throwable var1_2) {
                var7_8 = var5_6 > 0 ? var6_7 : false;
                this.end(var7_8);
                throw var1_2;
            }
            {
                this.end(var7_8);
                var3_4 += var5_6;
                ** while (true)
            }
        }
    }

}

