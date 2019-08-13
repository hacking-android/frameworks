/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.StackLogger;
import gov.nist.core.net.NetworkLayer;
import gov.nist.javax.sip.SipStackImpl;
import gov.nist.javax.sip.stack.HandshakeCompletedListenerImpl;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import gov.nist.javax.sip.stack.TLSMessageChannel;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSocket;

class IOHandler {
    private static String TCP = "tcp";
    private static String TLS = "tls";
    private Semaphore ioSemaphore = new Semaphore(1);
    private SipStackImpl sipStack;
    private ConcurrentHashMap<String, Socket> socketTable;

    protected IOHandler(SIPTransactionStack sIPTransactionStack) {
        this.sipStack = (SipStackImpl)sIPTransactionStack;
        this.socketTable = new ConcurrentHashMap();
    }

    protected static String makeKey(InetAddress inetAddress, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(inetAddress.getHostAddress());
        stringBuilder.append(":");
        stringBuilder.append(n);
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writeChunks(OutputStream outputStream, byte[] arrby, int n) throws IOException {
        synchronized (outputStream) {
            int n2 = 0;
            do {
                if (n2 >= n) {
                    // MONITOREXIT [3, 4, 5] lbl5 : MonitorExitStatement: MONITOREXIT : var1_1
                    outputStream.flush();
                    return;
                }
                int n3 = n2 + 8192 < n ? 8192 : n - n2;
                outputStream.write(arrby, n2, n3);
                n2 += 8192;
            } while (true);
        }
    }

    public void closeAll() {
        Enumeration<Socket> enumeration = this.socketTable.elements();
        while (enumeration.hasMoreElements()) {
            Socket socket = enumeration.nextElement();
            try {
                socket.close();
            }
            catch (IOException iOException) {}
        }
    }

    protected Socket getSocket(String string) {
        return this.socketTable.get(string);
    }

    public SocketAddress obtainLocalAddress(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        Socket socket;
        String string = IOHandler.makeKey(inetAddress, n);
        Socket socket2 = socket = this.getSocket(string);
        if (socket == null) {
            socket2 = this.sipStack.getNetworkLayer().createSocket(inetAddress, n, inetAddress2, n2);
            this.putSocket(string, socket2);
        }
        return socket2.getLocalSocketAddress();
    }

    protected void putSocket(String string, Socket socket) {
        this.socketTable.put(string, socket);
    }

    protected void removeSocket(String string) {
        this.socketTable.remove(string);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Socket sendBytes(InetAddress var1_1, InetAddress var2_11, int var3_12, String var4_13, byte[] var5_16, boolean var6_17, MessageChannel var7_18) throws IOException {
        block39 : {
            block40 : {
                block38 : {
                    var8_20 = var6_17 != false ? 2 : 1;
                    var9_21 = ((Object)var5_16).length;
                    if (this.sipStack.isLoggingEnabled()) {
                        var10_22 = this.sipStack.getStackLogger();
                        var11_24 = new StringBuilder();
                        var11_24.append("sendBytes ");
                        var11_24.append((String)var4_13);
                        var11_24.append(" inAddr ");
                        var11_24.append(var2_11.getHostAddress());
                        var11_24.append(" port = ");
                        var11_24.append(var3_12);
                        var11_24.append(" length = ");
                        var11_24.append(var9_21);
                        var10_22.logDebug(var11_24.toString());
                    }
                    if (this.sipStack.isLoggingEnabled() && this.sipStack.isLogStackTraceOnMessageSend()) {
                        this.sipStack.getStackLogger().logStackTrace(16);
                    }
                    if (var4_13.compareToIgnoreCase(IOHandler.TCP) == 0) {
                        var10_22 = IOHandler.makeKey(var2_11, var3_12);
                        var4_13 = this.ioSemaphore;
                        try {
                            var7_18 = TimeUnit.MILLISECONDS;
                        }
                        catch (InterruptedException var1_4) {
                            throw new IOException("exception in acquiring sem");
                        }
                        try {
                            var6_17 = var4_13.tryAcquire(10000L, (TimeUnit)var7_18);
                            if (!var6_17) {
                                var1_1 = new IOException("Could not acquire IO Semaphore after 10 seconds -- giving up ");
                                throw var1_1;
                            }
                            var4_13 = this.getSocket((String)var10_22);
                            var12_25 = 0;
                        }
                        catch (InterruptedException var1_3) {
                            throw new IOException("exception in acquiring sem");
                        }
                        catch (InterruptedException var1_5) {
                            // empty catch block
                        }
                        throw new IOException("exception in acquiring sem");
                    }
                    if (var4_13.compareToIgnoreCase(IOHandler.TLS) != 0) {
                        var1_1 = this.sipStack.getNetworkLayer().createDatagramSocket();
                        var1_1.connect(var2_11, var3_12);
                        var1_1.send(new DatagramPacket((byte[])var5_16, 0, var9_21, var2_11, var3_12));
                        var1_1.close();
                        return null;
                    }
                    var11_24 = IOHandler.makeKey(var2_11, var3_12);
                    var6_17 = this.ioSemaphore.tryAcquire(10000L, TimeUnit.MILLISECONDS);
                    if (!var6_17) break block38;
                    var4_13 = this.getSocket((String)var11_24);
                    break block39;
                }
                try {
                    var1_1 = new IOException("Timeout acquiring IO SEM");
                    throw var1_1;
                }
                catch (InterruptedException var1_9) {
                    throw new IOException("exception in acquiring sem");
                }
                catch (InterruptedException var1_10) {
                    // empty catch block
                }
                throw new IOException("exception in acquiring sem");
                do {
                    var11_24 = var5_16;
                    var7_18 = var4_13;
                    if (var12_25 >= var8_20) break block40;
                    if (var4_13 != null) ** GOTO lbl93
                    try {
                        if (this.sipStack.isLoggingEnabled()) {
                            var5_16 = this.sipStack.getStackLogger();
                            var4_13 = new StringBuilder();
                            var4_13.append("inaddr = ");
                            var4_13.append(var2_11);
                            var5_16.logDebug(var4_13.toString());
                            var5_16 = this.sipStack.getStackLogger();
                            var4_13 = new StringBuilder();
                            var4_13.append("port = ");
                            var4_13.append(var3_12);
                            var5_16.logDebug(var4_13.toString());
                        }
                        var7_18 = this.sipStack.getNetworkLayer().createSocket(var2_11, var3_12, (InetAddress)var1_1);
                        this.writeChunks(var7_18.getOutputStream(), (byte[])var11_24, var9_21);
                        this.putSocket((String)var10_22, (Socket)var7_18);
                        break block40;
lbl93: // 2 sources:
                        this.writeChunks(var4_13.getOutputStream(), (byte[])var11_24, var9_21);
                        var7_18 = var4_13;
                        break block40;
                    }
                    catch (Throwable var1_2) {
                        break;
                    }
                    catch (IOException var7_19) {
                        if (this.sipStack.isLoggingEnabled()) {
                            var11_24 = this.sipStack.getStackLogger();
                            var7_18 = new StringBuilder();
                            var7_18.append("IOException occured retryCount ");
                            var7_18.append(var12_25);
                            var11_24.logDebug(var7_18.toString());
                        }
                        this.removeSocket((String)var10_22);
                        try {
                            var4_13.close();
                        }
                        catch (Exception var4_14) {
                            // empty catch block
                        }
                        var4_13 = null;
                        ++var12_25;
                        continue;
                    }
                    break;
                } while (true);
                this.ioSemaphore.release();
                throw var1_2;
            }
            this.ioSemaphore.release();
            if (var7_18 != null) return var7_18;
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug(this.socketTable.toString());
                var1_1 = this.sipStack.getStackLogger();
                var4_13 = new StringBuilder();
                var4_13.append("Could not connect to ");
                var4_13.append(var2_11);
                var4_13.append(":");
                var4_13.append(var3_12);
                var1_1.logError(var4_13.toString());
            }
            var1_1 = new StringBuilder();
            var1_1.append("Could not connect to ");
            var1_1.append(var2_11);
            var1_1.append(":");
            var1_1.append(var3_12);
            throw new IOException(var1_1.toString());
        }
        for (var12_26 = 0; var12_26 < var8_20; ++var12_26) {
            block41 : {
                if (var4_13 != null) ** GOTO lbl178
                if (this.sipStack.isLoggingEnabled()) {
                    var10_22 = this.sipStack.getStackLogger();
                    var4_13 = new StringBuilder();
                    var4_13.append("inaddr = ");
                    var4_13.append(var2_11);
                    var10_22.logDebug(var4_13.toString());
                    var4_13 = this.sipStack.getStackLogger();
                    var10_22 = new StringBuilder();
                    var10_22.append("port = ");
                    var10_22.append(var3_12);
                    var4_13.logDebug(var10_22.toString());
                }
                var4_13 = this.sipStack.getNetworkLayer().createSSLSocket(var2_11, var3_12, (InetAddress)var1_1);
                var1_1 = (SSLSocket)var4_13;
                var10_22 = new HandshakeCompletedListenerImpl((TLSMessageChannel)var7_18);
                ((TLSMessageChannel)var7_18).setHandshakeCompletedListener((HandshakeCompletedListener)var10_22);
                var1_1.addHandshakeCompletedListener((HandshakeCompletedListener)var10_22);
                var1_1.setEnabledProtocols(this.sipStack.getEnabledProtocols());
                var1_1.startHandshake();
                var1_1 = var4_13.getOutputStream();
                {
                    catch (Throwable var1_6) {}
                }
                try {
                    this.writeChunks((OutputStream)var1_1, (byte[])var5_16, var9_21);
                    this.putSocket((String)var11_24, (Socket)var4_13);
                    break;
                    break block41;
lbl178: // 2 sources:
                    this.writeChunks(var4_13.getOutputStream(), (byte[])var5_16, var9_21);
                    break;
                }
                catch (Throwable var1_7) {
                    break block41;
                }
                catch (IOException var10_23) {
                    if (this.sipStack.isLoggingEnabled()) {
                        this.sipStack.getStackLogger().logException(var10_23);
                    }
                    this.removeSocket((String)var11_24);
                    try {
                        var4_13.close();
                    }
                    catch (Exception var4_15) {
                        // empty catch block
                    }
                    var4_13 = null;
                    continue;
                }
            }
            this.ioSemaphore.release();
            throw var1_8;
        }
        this.ioSemaphore.release();
        if (var4_13 != null) {
            return var4_13;
        }
        var1_1 = new StringBuilder();
        var1_1.append("Could not connect to ");
        var1_1.append(var2_11);
        var1_1.append(":");
        var1_1.append(var3_12);
        throw new IOException(var1_1.toString());
    }
}

