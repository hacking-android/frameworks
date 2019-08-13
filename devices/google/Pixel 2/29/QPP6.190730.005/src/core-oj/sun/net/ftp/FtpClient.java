/*
 * Decompiled with CFR 0.145.
 */
package sun.net.ftp;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import sun.net.ftp.FtpClientProvider;
import sun.net.ftp.FtpDirEntry;
import sun.net.ftp.FtpDirParser;
import sun.net.ftp.FtpProtocolException;
import sun.net.ftp.FtpReplyCode;

public abstract class FtpClient
implements Closeable {
    private static final int FTP_PORT = 21;

    protected FtpClient() {
    }

    public static FtpClient create() {
        return FtpClientProvider.provider().createFtpClient();
    }

    public static FtpClient create(String string) throws FtpProtocolException, IOException {
        return FtpClient.create(new InetSocketAddress(string, 21));
    }

    public static FtpClient create(InetSocketAddress inetSocketAddress) throws FtpProtocolException, IOException {
        FtpClient ftpClient = FtpClient.create();
        if (inetSocketAddress != null) {
            ftpClient.connect(inetSocketAddress);
        }
        return ftpClient;
    }

    public static final int defaultPort() {
        return 21;
    }

    public abstract FtpClient abort() throws FtpProtocolException, IOException;

    public abstract FtpClient allocate(long var1) throws FtpProtocolException, IOException;

    public abstract FtpClient appendFile(String var1, InputStream var2) throws FtpProtocolException, IOException;

    public abstract FtpClient changeDirectory(String var1) throws FtpProtocolException, IOException;

    public abstract FtpClient changeToParentDirectory() throws FtpProtocolException, IOException;

    @Override
    public abstract void close() throws IOException;

    public abstract FtpClient completePending() throws FtpProtocolException, IOException;

    public abstract FtpClient connect(SocketAddress var1) throws FtpProtocolException, IOException;

    public abstract FtpClient connect(SocketAddress var1, int var2) throws FtpProtocolException, IOException;

    public abstract FtpClient deleteFile(String var1) throws FtpProtocolException, IOException;

    public abstract FtpClient enablePassiveMode(boolean var1);

    public abstract FtpClient endSecureSession() throws FtpProtocolException, IOException;

    public abstract int getConnectTimeout();

    public abstract List<String> getFeatures() throws FtpProtocolException, IOException;

    public abstract FtpClient getFile(String var1, OutputStream var2) throws FtpProtocolException, IOException;

    public abstract InputStream getFileStream(String var1) throws FtpProtocolException, IOException;

    public abstract String getHelp(String var1) throws FtpProtocolException, IOException;

    public abstract String getLastFileName();

    public abstract Date getLastModified(String var1) throws FtpProtocolException, IOException;

    public abstract FtpReplyCode getLastReplyCode();

    public abstract String getLastResponseString();

    public abstract long getLastTransferSize();

    public abstract Proxy getProxy();

    public abstract int getReadTimeout();

    public abstract SocketAddress getServerAddress();

    public abstract long getSize(String var1) throws FtpProtocolException, IOException;

    public abstract String getStatus(String var1) throws FtpProtocolException, IOException;

    public abstract String getSystem() throws FtpProtocolException, IOException;

    public abstract String getWelcomeMsg();

    public abstract String getWorkingDirectory() throws FtpProtocolException, IOException;

    public abstract boolean isConnected();

    public abstract boolean isLoggedIn();

    public abstract boolean isPassiveModeEnabled();

    public abstract InputStream list(String var1) throws FtpProtocolException, IOException;

    public abstract Iterator<FtpDirEntry> listFiles(String var1) throws FtpProtocolException, IOException;

    public abstract FtpClient login(String var1, char[] var2) throws FtpProtocolException, IOException;

    public abstract FtpClient login(String var1, char[] var2, String var3) throws FtpProtocolException, IOException;

    public abstract FtpClient makeDirectory(String var1) throws FtpProtocolException, IOException;

    public abstract InputStream nameList(String var1) throws FtpProtocolException, IOException;

    public abstract FtpClient noop() throws FtpProtocolException, IOException;

    public FtpClient putFile(String string, InputStream inputStream) throws FtpProtocolException, IOException {
        return this.putFile(string, inputStream, false);
    }

    public abstract FtpClient putFile(String var1, InputStream var2, boolean var3) throws FtpProtocolException, IOException;

    public OutputStream putFileStream(String string) throws FtpProtocolException, IOException {
        return this.putFileStream(string, false);
    }

    public abstract OutputStream putFileStream(String var1, boolean var2) throws FtpProtocolException, IOException;

    public abstract FtpClient reInit() throws FtpProtocolException, IOException;

    public abstract FtpClient removeDirectory(String var1) throws FtpProtocolException, IOException;

    public abstract FtpClient rename(String var1, String var2) throws FtpProtocolException, IOException;

    public FtpClient setAsciiType() throws FtpProtocolException, IOException {
        this.setType(TransferType.ASCII);
        return this;
    }

    public FtpClient setBinaryType() throws FtpProtocolException, IOException {
        this.setType(TransferType.BINARY);
        return this;
    }

    public abstract FtpClient setConnectTimeout(int var1);

    public abstract FtpClient setDirParser(FtpDirParser var1);

    public abstract FtpClient setProxy(Proxy var1);

    public abstract FtpClient setReadTimeout(int var1);

    public abstract FtpClient setRestartOffset(long var1);

    public abstract FtpClient setType(TransferType var1) throws FtpProtocolException, IOException;

    public abstract FtpClient siteCmd(String var1) throws FtpProtocolException, IOException;

    public abstract FtpClient startSecureSession() throws FtpProtocolException, IOException;

    public abstract FtpClient structureMount(String var1) throws FtpProtocolException, IOException;

    public abstract FtpClient useKerberos() throws FtpProtocolException, IOException;

    public static enum TransferType {
        ASCII,
        BINARY,
        EBCDIC;
        
    }

}

