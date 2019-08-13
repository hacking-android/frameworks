/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.framed;

import com.android.okhttp.Protocol;
import com.android.okhttp.internal.Internal;
import com.android.okhttp.internal.NamedRunnable;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.framed.ErrorCode;
import com.android.okhttp.internal.framed.FrameReader;
import com.android.okhttp.internal.framed.FrameWriter;
import com.android.okhttp.internal.framed.FramedStream;
import com.android.okhttp.internal.framed.Header;
import com.android.okhttp.internal.framed.HeadersMode;
import com.android.okhttp.internal.framed.Http2;
import com.android.okhttp.internal.framed.Ping;
import com.android.okhttp.internal.framed.PushObserver;
import com.android.okhttp.internal.framed.Settings;
import com.android.okhttp.internal.framed.Spdy3;
import com.android.okhttp.internal.framed.Variant;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.Okio;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FramedConnection
implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
    private static final ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp FramedConnection", true));
    long bytesLeftInWriteWindow;
    final boolean client;
    private final Set<Integer> currentPushRequests;
    final FrameWriter frameWriter;
    private final String hostName;
    private long idleStartTimeNs;
    private int lastGoodStreamId;
    private final Listener listener;
    private int nextPingId;
    private int nextStreamId;
    Settings okHttpSettings;
    final Settings peerSettings;
    private Map<Integer, Ping> pings;
    final Protocol protocol;
    private final ExecutorService pushExecutor;
    private final PushObserver pushObserver;
    final Reader readerRunnable;
    private boolean receivedInitialPeerSettings;
    private boolean shutdown;
    final Socket socket;
    private final Map<Integer, FramedStream> streams;
    long unacknowledgedBytesRead;
    final Variant variant;

    private FramedConnection(Builder builder) throws IOException {
        block8 : {
            block7 : {
                block6 : {
                    this.streams = new HashMap<Integer, FramedStream>();
                    this.idleStartTimeNs = System.nanoTime();
                    this.unacknowledgedBytesRead = 0L;
                    this.okHttpSettings = new Settings();
                    this.peerSettings = new Settings();
                    this.receivedInitialPeerSettings = false;
                    this.currentPushRequests = new LinkedHashSet<Integer>();
                    this.protocol = builder.protocol;
                    this.pushObserver = builder.pushObserver;
                    this.client = builder.client;
                    this.listener = builder.listener;
                    boolean bl = builder.client;
                    int n = 2;
                    int n2 = bl ? 1 : 2;
                    this.nextStreamId = n2;
                    if (builder.client && this.protocol == Protocol.HTTP_2) {
                        this.nextStreamId += 2;
                    }
                    n2 = n;
                    if (builder.client) {
                        n2 = 1;
                    }
                    this.nextPingId = n2;
                    if (builder.client) {
                        this.okHttpSettings.set(7, 0, 16777216);
                    }
                    this.hostName = builder.hostName;
                    if (this.protocol != Protocol.HTTP_2) break block6;
                    this.variant = new Http2();
                    this.pushExecutor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Util.threadFactory(String.format("OkHttp %s Push Observer", this.hostName), true));
                    this.peerSettings.set(7, 0, 65535);
                    this.peerSettings.set(5, 0, 16384);
                    break block7;
                }
                if (this.protocol != Protocol.SPDY_3) break block8;
                this.variant = new Spdy3();
                this.pushExecutor = null;
            }
            this.bytesLeftInWriteWindow = this.peerSettings.getInitialWindowSize(65536);
            this.socket = builder.socket;
            this.frameWriter = this.variant.newWriter(builder.sink, this.client);
            this.readerRunnable = new Reader(this.variant.newReader(builder.source, this.client));
            new Thread(this.readerRunnable).start();
            return;
        }
        throw new AssertionError((Object)this.protocol);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void close(ErrorCode object, ErrorCode errorCode) throws IOException {
        block19 : {
            Object object22 = null;
            try {
                this.shutdown((ErrorCode)((Object)object));
                object = object22;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            FramedStream[] arrframedStream = null;
            Ping[] arrping = null;
            synchronized (this) {
                boolean bl = this.streams.isEmpty();
                int n = 0;
                if (!bl) {
                    arrframedStream = this.streams.values().toArray(new FramedStream[this.streams.size()]);
                    this.streams.clear();
                    this.setIdle(false);
                }
                if (this.pings != null) {
                    arrping = this.pings.values().toArray(new Ping[this.pings.size()]);
                    this.pings = null;
                }
            }
            object22 = object;
            if (arrframedStream != null) {
                for (Object object22 : arrframedStream) {
                    block18 : {
                        try {
                            ((FramedStream)object22).close(errorCode);
                            object22 = object;
                        }
                        catch (IOException iOException) {
                            object22 = object;
                            if (object == null) break block18;
                            object22 = iOException;
                        }
                    }
                    object = object22;
                }
                object22 = object;
            }
            if (arrping != null) {
                int n = arrping.length;
                for (int i = n; i < n; ++i) {
                    arrping[i].cancel();
                }
            }
            try {
                this.frameWriter.close();
                object = object22;
            }
            catch (IOException iOException) {
                object = object22;
                if (object22 != null) break block19;
                object = iOException;
            }
        }
        try {
            this.socket.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if (object == null) {
            return;
        }
        throw object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private FramedStream newStream(int var1_1, List<Header> var2_2, boolean var3_7, boolean var4_8) throws IOException {
        block15 : {
            block18 : {
                block17 : {
                    block16 : {
                        var5_7 = var3_5 ^ true;
                        var4_6 ^= true;
                        var6_8 = this.frameWriter;
                        // MONITORENTER : var6_8
                        // MONITORENTER : this
                        if (this.shutdown) break block15;
                        var7_9 = this.nextStreamId;
                        this.nextStreamId += 2;
                        var8_10 = new FramedStream(var7_9, this, var5_7, var4_6, (List<Header>)var2_2);
                        if (var8_10.isOpen()) {
                            this.streams.put(var7_9, var8_10);
                            this.setIdle(false);
                        }
                        // MONITOREXIT : this
                        if (var1_1 != 0) break block16;
                        this.frameWriter.synStream(var5_7, var4_6, var7_9, var1_1, (List<Header>)var2_2);
                        break block17;
                    }
                    if (this.client) break block18;
                    var9_11 = this.frameWriter;
                    var9_11.pushPromise(var1_1, var7_9, (List<Header>)var2_2);
                    // MONITOREXIT : var6_8
                }
                if (var3_5 != false) return var8_10;
                this.frameWriter.flush();
                return var8_10;
            }
            var2_2 = new IllegalArgumentException("client streams shouldn't have associated stream IDs");
            throw var2_2;
        }
        var2_2 = new IOException("shutdown");
        throw var2_2;
        {
            catch (Throwable var2_3) {}
            ** try [egrp 6[TRYBLOCK] [10 : 191->201)] { 
lbl40: // 1 sources:
            throw var2_3;
        }
    }

    private void pushDataLater(final int n, BufferedSource object, final int n2, final boolean bl) throws IOException {
        final Buffer buffer = new Buffer();
        object.require(n2);
        object.read(buffer, n2);
        if (buffer.size() == (long)n2) {
            this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Data[%s]", new Object[]{this.hostName, n}){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Converted monitor instructions to comments
                 * Lifted jumps to return sites
                 */
                @Override
                public void execute() {
                    try {
                        boolean bl2 = FramedConnection.this.pushObserver.onData(n, buffer, n2, bl);
                        if (bl2) {
                            FramedConnection.this.frameWriter.rstStream(n, ErrorCode.CANCEL);
                        }
                        if (!bl2) {
                            if (!bl) return;
                        }
                        FramedConnection framedConnection = FramedConnection.this;
                        // MONITORENTER : framedConnection
                        FramedConnection.this.currentPushRequests.remove(n);
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    // MONITOREXIT : framedConnection
                    return;
                }
            });
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(buffer.size());
        ((StringBuilder)object).append(" != ");
        ((StringBuilder)object).append(n2);
        throw new IOException(((StringBuilder)object).toString());
    }

    private void pushHeadersLater(final int n, final List<Header> list, final boolean bl) {
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[]{this.hostName, n}){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void execute() {
                boolean bl2 = FramedConnection.this.pushObserver.onHeaders(n, list, bl);
                if (bl2) {
                    try {
                        FramedConnection.this.frameWriter.rstStream(n, ErrorCode.CANCEL);
                    }
                    catch (IOException iOException) {
                        return;
                    }
                }
                if (!bl2) {
                    if (!bl) return;
                }
                FramedConnection framedConnection = FramedConnection.this;
                synchronized (framedConnection) {
                    FramedConnection.this.currentPushRequests.remove(n);
                    return;
                }
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void pushRequestLater(final int n, final List<Header> list) {
        synchronized (this) {
            if (this.currentPushRequests.contains(n)) {
                this.writeSynResetLater(n, ErrorCode.PROTOCOL_ERROR);
                return;
            }
            this.currentPushRequests.add(n);
        }
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Request[%s]", new Object[]{this.hostName, n}){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void execute() {
                if (!FramedConnection.this.pushObserver.onRequest(n, list)) return;
                try {
                    FramedConnection.this.frameWriter.rstStream(n, ErrorCode.CANCEL);
                    FramedConnection framedConnection = FramedConnection.this;
                    synchronized (framedConnection) {
                        FramedConnection.this.currentPushRequests.remove(n);
                    }
                }
                catch (IOException iOException) {
                    return;
                }
                {
                    return;
                }
            }
        });
    }

    private void pushResetLater(final int n, final ErrorCode errorCode) {
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[]{this.hostName, n}){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void execute() {
                FramedConnection.this.pushObserver.onReset(n, errorCode);
                FramedConnection framedConnection = FramedConnection.this;
                synchronized (framedConnection) {
                    FramedConnection.this.currentPushRequests.remove(n);
                    return;
                }
            }
        });
    }

    private boolean pushedStream(int n) {
        boolean bl = this.protocol == Protocol.HTTP_2 && n != 0 && (n & 1) == 0;
        return bl;
    }

    private Ping removePing(int n) {
        synchronized (this) {
            Ping ping = this.pings != null ? this.pings.remove(n) : null;
            return ping;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setIdle(boolean bl) {
        synchronized (this) {
            Throwable throwable2;
            block5 : {
                long l;
                block4 : {
                    if (bl) {
                        try {
                            l = System.nanoTime();
                            break block4;
                        }
                        catch (Throwable throwable2) {
                            break block5;
                        }
                    }
                    l = Long.MAX_VALUE;
                }
                this.idleStartTimeNs = l;
                return;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writePing(boolean bl, int n, int n2, Ping ping) throws IOException {
        FrameWriter frameWriter = this.frameWriter;
        synchronized (frameWriter) {
            if (ping != null) {
                ping.send();
            }
            this.frameWriter.ping(bl, n, n2);
            return;
        }
    }

    private void writePingLater(final boolean bl, final int n, final int n2, final Ping ping) {
        executor.execute(new NamedRunnable("OkHttp %s ping %08x%08x", new Object[]{this.hostName, n, n2}){

            @Override
            public void execute() {
                try {
                    FramedConnection.this.writePing(bl, n, n2, ping);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        });
    }

    void addBytesToWriteWindow(long l) {
        this.bytesLeftInWriteWindow += l;
        if (l > 0L) {
            this.notifyAll();
        }
    }

    @Override
    public void close() throws IOException {
        this.close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
    }

    public void flush() throws IOException {
        this.frameWriter.flush();
    }

    public long getIdleStartTimeNs() {
        synchronized (this) {
            long l = this.idleStartTimeNs;
            return l;
        }
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    FramedStream getStream(int n) {
        synchronized (this) {
            FramedStream framedStream = this.streams.get(n);
            return framedStream;
        }
    }

    public boolean isIdle() {
        synchronized (this) {
            long l = this.idleStartTimeNs;
            boolean bl = l != Long.MAX_VALUE;
            return bl;
        }
    }

    public int maxConcurrentStreams() {
        synchronized (this) {
            int n = this.peerSettings.getMaxConcurrentStreams(Integer.MAX_VALUE);
            return n;
        }
    }

    public FramedStream newStream(List<Header> list, boolean bl, boolean bl2) throws IOException {
        return this.newStream(0, list, bl, bl2);
    }

    public int openStreamCount() {
        synchronized (this) {
            int n = this.streams.size();
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Ping ping() throws IOException {
        int n;
        Ping ping = new Ping();
        synchronized (this) {
            if (this.shutdown) {
                IOException iOException = new IOException("shutdown");
                throw iOException;
            }
            n = this.nextPingId;
            this.nextPingId += 2;
            if (this.pings == null) {
                HashMap<Integer, Ping> hashMap = new HashMap<Integer, Ping>();
                this.pings = hashMap;
            }
            this.pings.put(n, ping);
        }
        this.writePing(false, n, 1330343787, ping);
        return ping;
    }

    public FramedStream pushStream(int n, List<Header> list, boolean bl) throws IOException {
        if (!this.client) {
            if (this.protocol == Protocol.HTTP_2) {
                return this.newStream(n, list, bl, false);
            }
            throw new IllegalStateException("protocol != HTTP_2");
        }
        throw new IllegalStateException("Client cannot push requests.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    FramedStream removeStream(int n) {
        synchronized (this) {
            FramedStream framedStream = this.streams.remove(n);
            if (framedStream != null && this.streams.isEmpty()) {
                this.setIdle(true);
            }
            this.notifyAll();
            return framedStream;
        }
    }

    public void sendConnectionPreface() throws IOException {
        this.frameWriter.connectionPreface();
        this.frameWriter.settings(this.okHttpSettings);
        int n = this.okHttpSettings.getInitialWindowSize(65536);
        if (n != 65536) {
            this.frameWriter.windowUpdate(0, n - 65536);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setSettings(Settings object) throws IOException {
        FrameWriter frameWriter = this.frameWriter;
        synchronized (frameWriter) {
            synchronized (this) {
                if (!this.shutdown) {
                    this.okHttpSettings.merge((Settings)object);
                    this.frameWriter.settings((Settings)object);
                    return;
                }
                object = new IOException("shutdown");
                throw object;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void shutdown(ErrorCode errorCode) throws IOException {
        FrameWriter frameWriter = this.frameWriter;
        synchronized (frameWriter) {
            int n;
            synchronized (this) {
                if (this.shutdown) {
                    return;
                }
                this.shutdown = true;
                n = this.lastGoodStreamId;
            }
            this.frameWriter.goAway(n, errorCode, Util.EMPTY_BYTE_ARRAY);
            return;
        }
    }

    /*
     * Exception decompiling
     */
    public void writeData(int var1_1, boolean var2_2, Buffer var3_3, long var4_7) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    void writeSynReply(int n, boolean bl, List<Header> list) throws IOException {
        this.frameWriter.synReply(bl, n, list);
    }

    void writeSynReset(int n, ErrorCode errorCode) throws IOException {
        this.frameWriter.rstStream(n, errorCode);
    }

    void writeSynResetLater(final int n, final ErrorCode errorCode) {
        executor.submit(new NamedRunnable("OkHttp %s stream %d", new Object[]{this.hostName, n}){

            @Override
            public void execute() {
                try {
                    FramedConnection.this.writeSynReset(n, errorCode);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        });
    }

    void writeWindowUpdateLater(final int n, final long l) {
        executor.execute(new NamedRunnable("OkHttp Window Update %s stream %d", new Object[]{this.hostName, n}){

            @Override
            public void execute() {
                try {
                    FramedConnection.this.frameWriter.windowUpdate(n, l);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        });
    }

    public static class Builder {
        private boolean client;
        private String hostName;
        private Listener listener = Listener.REFUSE_INCOMING_STREAMS;
        private Protocol protocol = Protocol.SPDY_3;
        private PushObserver pushObserver = PushObserver.CANCEL;
        private BufferedSink sink;
        private Socket socket;
        private BufferedSource source;

        public Builder(boolean bl) throws IOException {
            this.client = bl;
        }

        public FramedConnection build() throws IOException {
            return new FramedConnection(this);
        }

        public Builder listener(Listener listener) {
            this.listener = listener;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder pushObserver(PushObserver pushObserver) {
            this.pushObserver = pushObserver;
            return this;
        }

        public Builder socket(Socket socket) throws IOException {
            return this.socket(socket, ((InetSocketAddress)socket.getRemoteSocketAddress()).getHostName(), Okio.buffer(Okio.source(socket)), Okio.buffer(Okio.sink(socket)));
        }

        public Builder socket(Socket socket, String string, BufferedSource bufferedSource, BufferedSink bufferedSink) {
            this.socket = socket;
            this.hostName = string;
            this.source = bufferedSource;
            this.sink = bufferedSink;
            return this;
        }
    }

    public static abstract class Listener {
        public static final Listener REFUSE_INCOMING_STREAMS = new Listener(){

            @Override
            public void onStream(FramedStream framedStream) throws IOException {
                framedStream.close(ErrorCode.REFUSED_STREAM);
            }
        };

        public void onSettings(FramedConnection framedConnection) {
        }

        public abstract void onStream(FramedStream var1) throws IOException;

    }

    class Reader
    extends NamedRunnable
    implements FrameReader.Handler {
        final FrameReader frameReader;

        private Reader(FrameReader frameReader) {
            super("OkHttp %s", FramedConnection.this.hostName);
            this.frameReader = frameReader;
        }

        private void ackSettingsLater(final Settings settings) {
            executor.execute(new NamedRunnable("OkHttp %s ACK Settings", new Object[]{FramedConnection.this.hostName}){

                @Override
                public void execute() {
                    try {
                        FramedConnection.this.frameWriter.ackSettings(settings);
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
            });
        }

        @Override
        public void ackSettings() {
        }

        @Override
        public void alternateService(int n, String string, ByteString byteString, String string2, int n2, long l) {
        }

        @Override
        public void data(boolean bl, int n, BufferedSource bufferedSource, int n2) throws IOException {
            if (FramedConnection.this.pushedStream(n)) {
                FramedConnection.this.pushDataLater(n, bufferedSource, n2, bl);
                return;
            }
            FramedStream framedStream = FramedConnection.this.getStream(n);
            if (framedStream == null) {
                FramedConnection.this.writeSynResetLater(n, ErrorCode.INVALID_STREAM);
                bufferedSource.skip(n2);
                return;
            }
            framedStream.receiveData(bufferedSource, n2);
            if (bl) {
                framedStream.receiveFin();
            }
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        protected void execute() {
            Throwable throwable2222;
            ErrorCode errorCode2;
            ErrorCode errorCode;
            block12 : {
                block13 : {
                    ErrorCode errorCode3 = ErrorCode.INTERNAL_ERROR;
                    errorCode = ErrorCode.INTERNAL_ERROR;
                    errorCode2 = errorCode3;
                    ErrorCode errorCode4 = errorCode3;
                    if (!FramedConnection.this.client) {
                        errorCode2 = errorCode3;
                        errorCode4 = errorCode3;
                        this.frameReader.readConnectionPreface();
                    }
                    do {
                        errorCode2 = errorCode3;
                        errorCode4 = errorCode3;
                    } while (this.frameReader.nextFrame(this));
                    errorCode2 = errorCode3;
                    errorCode4 = errorCode3;
                    errorCode2 = errorCode3 = ErrorCode.NO_ERROR;
                    errorCode4 = errorCode3;
                    ErrorCode errorCode5 = ErrorCode.CANCEL;
                    try {
                        FramedConnection.this.close(errorCode3, errorCode5);
                    }
                    catch (IOException iOException) {}
                    break block13;
                    {
                        catch (Throwable throwable2222) {
                            break block12;
                        }
                        catch (IOException iOException) {}
                        errorCode2 = errorCode4;
                        {
                            errorCode2 = errorCode4 = ErrorCode.PROTOCOL_ERROR;
                            errorCode3 = ErrorCode.PROTOCOL_ERROR;
                        }
                        try {
                            FramedConnection.this.close(errorCode4, errorCode3);
                        }
                        catch (IOException iOException) {
                            // empty catch block
                        }
                    }
                }
                Util.closeQuietly(this.frameReader);
                return;
            }
            try {
                FramedConnection.this.close(errorCode2, errorCode);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            Util.closeQuietly(this.frameReader);
            throw throwable2222;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void goAway(int n, ErrorCode arrframedStream, ByteString object) {
            ((ByteString)object).size();
            object = FramedConnection.this;
            synchronized (object) {
                arrframedStream = FramedConnection.this.streams.values().toArray(new FramedStream[FramedConnection.this.streams.size()]);
                FramedConnection.this.shutdown = true;
            }
            int n2 = arrframedStream.length;
            int n3 = 0;
            while (n3 < n2) {
                object = arrframedStream[n3];
                if (((FramedStream)object).getId() > n && ((FramedStream)object).isLocallyInitiated()) {
                    ((FramedStream)object).receiveRstStream(ErrorCode.REFUSED_STREAM);
                    FramedConnection.this.removeStream(((FramedStream)object).getId());
                }
                ++n3;
            }
            return;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void headers(boolean bl, boolean bl2, int n, int n2, List<Header> object, HeadersMode object2) {
            void var6_6;
            if (FramedConnection.this.pushedStream(n)) {
                FramedConnection.this.pushHeadersLater(n, (List)object, bl2);
                return;
            }
            FramedConnection framedConnection = FramedConnection.this;
            // MONITORENTER : framedConnection
            if (FramedConnection.this.shutdown) {
                // MONITOREXIT : framedConnection
                return;
            }
            Object object3 = FramedConnection.this.getStream(n);
            if (object3 == null) {
                if (var6_6.failIfStreamAbsent()) {
                    FramedConnection.this.writeSynResetLater(n, ErrorCode.INVALID_STREAM);
                    // MONITOREXIT : framedConnection
                    return;
                }
                if (n <= FramedConnection.this.lastGoodStreamId) {
                    // MONITOREXIT : framedConnection
                    return;
                }
                if (n % 2 == FramedConnection.this.nextStreamId % 2) {
                    // MONITOREXIT : framedConnection
                    return;
                }
                final FramedStream framedStream = new FramedStream(n, FramedConnection.this, bl, bl2, (List<Header>)object);
                FramedConnection.this.lastGoodStreamId = n;
                FramedConnection.this.streams.put(n, framedStream);
                object = executor;
                object3 = new NamedRunnable("OkHttp %s stream %d", new Object[]{FramedConnection.this.hostName, n}){

                    @Override
                    public void execute() {
                        try {
                            FramedConnection.this.listener.onStream(framedStream);
                        }
                        catch (IOException iOException) {
                            Logger logger = Internal.logger;
                            Level level = Level.INFO;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("FramedConnection.Listener failure for ");
                            stringBuilder.append(FramedConnection.this.hostName);
                            logger.log(level, stringBuilder.toString(), iOException);
                            try {
                                framedStream.close(ErrorCode.PROTOCOL_ERROR);
                            }
                            catch (IOException iOException2) {
                                // empty catch block
                            }
                        }
                    }
                };
                object.execute((Runnable)object3);
                // MONITOREXIT : framedConnection
                return;
            }
            // MONITOREXIT : framedConnection
            if (var6_6.failIfStreamPresent()) {
                ((FramedStream)object3).closeLater(ErrorCode.PROTOCOL_ERROR);
                FramedConnection.this.removeStream(n);
                return;
            }
            ((FramedStream)object3).receiveHeaders((List<Header>)object, (HeadersMode)var6_6);
            if (!bl2) return;
            ((FramedStream)object3).receiveFin();
        }

        @Override
        public void ping(boolean bl, int n, int n2) {
            if (bl) {
                Ping ping = FramedConnection.this.removePing(n);
                if (ping != null) {
                    ping.receive();
                }
            } else {
                FramedConnection.this.writePingLater(true, n, n2, null);
            }
        }

        @Override
        public void priority(int n, int n2, int n3, boolean bl) {
        }

        @Override
        public void pushPromise(int n, int n2, List<Header> list) {
            FramedConnection.this.pushRequestLater(n2, list);
        }

        @Override
        public void rstStream(int n, ErrorCode errorCode) {
            if (FramedConnection.this.pushedStream(n)) {
                FramedConnection.this.pushResetLater(n, errorCode);
                return;
            }
            FramedStream framedStream = FramedConnection.this.removeStream(n);
            if (framedStream != null) {
                framedStream.receiveRstStream(errorCode);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void settings(boolean bl, Settings object) {
            long l = 0L;
            NamedRunnable namedRunnable = null;
            FramedConnection framedConnection = FramedConnection.this;
            // MONITORENTER : framedConnection
            int n = FramedConnection.this.peerSettings.getInitialWindowSize(65536);
            if (bl) {
                FramedConnection.this.peerSettings.clear();
            }
            FramedConnection.this.peerSettings.merge((Settings)object);
            if (FramedConnection.this.getProtocol() == Protocol.HTTP_2) {
                this.ackSettingsLater((Settings)object);
            }
            int n2 = FramedConnection.this.peerSettings.getInitialWindowSize(65536);
            long l2 = l;
            object = namedRunnable;
            if (n2 != -1) {
                l2 = l;
                object = namedRunnable;
                if (n2 != n) {
                    l = n2 - n;
                    if (!FramedConnection.this.receivedInitialPeerSettings) {
                        FramedConnection.this.addBytesToWriteWindow(l);
                        FramedConnection.this.receivedInitialPeerSettings = true;
                    }
                    l2 = l;
                    object = namedRunnable;
                    if (!FramedConnection.this.streams.isEmpty()) {
                        object = FramedConnection.this.streams.values().toArray(new FramedStream[FramedConnection.this.streams.size()]);
                        l2 = l;
                    }
                }
            }
            ExecutorService executorService = executor;
            String string = FramedConnection.this.hostName;
            n2 = 0;
            namedRunnable = new NamedRunnable("OkHttp %s settings", new Object[]{string}){

                @Override
                public void execute() {
                    FramedConnection.this.listener.onSettings(FramedConnection.this);
                }
            };
            executorService.execute(namedRunnable);
            // MONITOREXIT : framedConnection
            if (object == null) return;
            if (l2 == 0L) return;
            n = ((FramedStream[])object).length;
            while (n2 < n) {
                namedRunnable = object[n2];
                // MONITORENTER : namedRunnable
                ((FramedStream)((Object)namedRunnable)).addBytesToWriteWindow(l2);
                // MONITOREXIT : namedRunnable
                ++n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void windowUpdate(int n, long l) {
            if (n == 0) {
                FramedConnection framedConnection = FramedConnection.this;
                synchronized (framedConnection) {
                    FramedConnection framedConnection2 = FramedConnection.this;
                    framedConnection2.bytesLeftInWriteWindow += l;
                    FramedConnection.this.notifyAll();
                    return;
                }
            }
            FramedStream framedStream = FramedConnection.this.getStream(n);
            if (framedStream == null) return;
            synchronized (framedStream) {
                framedStream.addBytesToWriteWindow(l);
                return;
            }
        }

    }

}

