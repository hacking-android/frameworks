/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.channels.NetworkChannel;
import java.nio.channels.ServerSocketChannel;
import sun.nio.ch.Net;
import sun.nio.ch.ServerSocketChannelImpl;

public class ServerSocketAdaptor
extends ServerSocket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final ServerSocketChannelImpl ssc;
    private volatile int timeout = 0;

    private ServerSocketAdaptor(ServerSocketChannelImpl serverSocketChannelImpl) throws IOException {
        this.ssc = serverSocketChannelImpl;
    }

    public static ServerSocket create(ServerSocketChannelImpl closeable) {
        try {
            closeable = new ServerSocketAdaptor((ServerSocketChannelImpl)closeable);
            return closeable;
        }
        catch (IOException iOException) {
            throw new Error(iOException);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public Socket accept() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [12[WHILELOOP]], but top level block is 6[TRYBLOCK]
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

    @Override
    public void bind(SocketAddress socketAddress) throws IOException {
        this.bind(socketAddress, 50);
    }

    @Override
    public void bind(SocketAddress socketAddress, int n) throws IOException {
        SocketAddress socketAddress2 = socketAddress;
        if (socketAddress == null) {
            socketAddress2 = new InetSocketAddress(0);
        }
        try {
            this.ssc.bind(socketAddress2, n);
        }
        catch (Exception exception) {
            Net.translateException(exception);
        }
    }

    @Override
    public void close() throws IOException {
        this.ssc.close();
    }

    @Override
    public ServerSocketChannel getChannel() {
        return this.ssc;
    }

    @Override
    public InetAddress getInetAddress() {
        if (!this.ssc.isBound()) {
            return null;
        }
        return Net.getRevealedLocalAddress(this.ssc.localAddress()).getAddress();
    }

    @Override
    public int getLocalPort() {
        if (!this.ssc.isBound()) {
            return -1;
        }
        return Net.asInetSocketAddress(this.ssc.localAddress()).getPort();
    }

    @Override
    public int getReceiveBufferSize() throws SocketException {
        try {
            int n = this.ssc.getOption(StandardSocketOptions.SO_RCVBUF);
            return n;
        }
        catch (IOException iOException) {
            Net.translateToSocketException(iOException);
            return -1;
        }
    }

    @Override
    public boolean getReuseAddress() throws SocketException {
        try {
            boolean bl = this.ssc.getOption(StandardSocketOptions.SO_REUSEADDR);
            return bl;
        }
        catch (IOException iOException) {
            Net.translateToSocketException(iOException);
            return false;
        }
    }

    @Override
    public int getSoTimeout() throws SocketException {
        return this.timeout;
    }

    @Override
    public boolean isBound() {
        return this.ssc.isBound();
    }

    @Override
    public boolean isClosed() {
        return this.ssc.isOpen() ^ true;
    }

    @Override
    public void setReceiveBufferSize(int n) throws SocketException {
        if (n > 0) {
            try {
                this.ssc.setOption((SocketOption)StandardSocketOptions.SO_RCVBUF, (Object)n);
            }
            catch (IOException iOException) {
                Net.translateToSocketException(iOException);
            }
            return;
        }
        throw new IllegalArgumentException("size cannot be 0 or negative");
    }

    @Override
    public void setReuseAddress(boolean bl) throws SocketException {
        try {
            this.ssc.setOption((SocketOption)StandardSocketOptions.SO_REUSEADDR, (Object)bl);
        }
        catch (IOException iOException) {
            Net.translateToSocketException(iOException);
        }
    }

    @Override
    public void setSoTimeout(int n) throws SocketException {
        this.timeout = n;
    }

    @Override
    public String toString() {
        if (!this.isBound()) {
            return "ServerSocket[unbound]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ServerSocket[addr=");
        stringBuilder.append(this.getInetAddress());
        stringBuilder.append(",localport=");
        stringBuilder.append(this.getLocalPort());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

