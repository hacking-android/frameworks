/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 *  dalvik.annotation.optimization.ReachabilitySensitive
 *  dalvik.system.BlockGuard
 *  dalvik.system.CloseGuard
 *  libcore.io.IoBridge
 *  libcore.io.IoTracker
 *  libcore.io.IoUtils
 */
package java.io;

import android.system.OsConstants;
import dalvik.annotation.optimization.ReachabilitySensitive;
import dalvik.system.BlockGuard;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import libcore.io.IoBridge;
import libcore.io.IoTracker;
import libcore.io.IoUtils;
import sun.nio.ch.FileChannelImpl;

public class FileInputStream
extends InputStream {
    private FileChannel channel;
    private final Object closeLock;
    private volatile boolean closed;
    @ReachabilitySensitive
    private final FileDescriptor fd;
    @ReachabilitySensitive
    private final CloseGuard guard;
    private final boolean isFdOwner;
    private final String path;
    private final IoTracker tracker;

    public FileInputStream(File file) throws FileNotFoundException {
        SecurityManager securityManager;
        String string = null;
        this.channel = null;
        this.closeLock = new Object();
        this.closed = false;
        this.guard = CloseGuard.get();
        this.tracker = new IoTracker();
        if (file != null) {
            string = file.getPath();
        }
        if ((securityManager = System.getSecurityManager()) != null) {
            securityManager.checkRead(string);
        }
        if (string != null) {
            if (!file.isInvalid()) {
                this.fd = IoBridge.open((String)string, (int)OsConstants.O_RDONLY);
                this.isFdOwner = true;
                this.path = string;
                IoUtils.setFdOwner((FileDescriptor)this.fd, (Object)this);
                this.guard.open("close");
                return;
            }
            throw new FileNotFoundException("Invalid file path");
        }
        throw new NullPointerException();
    }

    public FileInputStream(FileDescriptor fileDescriptor) {
        this(fileDescriptor, false);
    }

    public FileInputStream(FileDescriptor fileDescriptor, boolean bl) {
        this.channel = null;
        this.closeLock = new Object();
        this.closed = false;
        this.guard = CloseGuard.get();
        this.tracker = new IoTracker();
        if (fileDescriptor != null) {
            this.fd = fileDescriptor;
            this.path = null;
            this.isFdOwner = bl;
            return;
        }
        throw new NullPointerException("fdObj == null");
    }

    public FileInputStream(String object) throws FileNotFoundException {
        object = object != null ? new File((String)object) : null;
        this((File)object);
    }

    private native int available0() throws IOException;

    private void open(String string) throws FileNotFoundException {
        this.open0(string);
    }

    private native void open0(String var1) throws FileNotFoundException;

    private native long skip0(long var1) throws IOException, UseManualSkipException;

    @Override
    public int available() throws IOException {
        if (!this.closed) {
            return this.available0();
        }
        throw new IOException("Stream Closed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        Object object = this.closeLock;
        synchronized (object) {
            if (this.closed) {
                return;
            }
            this.closed = true;
        }
        this.guard.close();
        FileChannel fileChannel = this.channel;
        if (fileChannel != null) {
            fileChannel.close();
        }
        if (this.isFdOwner) {
            IoBridge.closeAndSignalBlockedThreads((FileDescriptor)this.fd);
        }
    }

    protected void finalize() throws IOException {
        Object object = this.guard;
        if (object != null) {
            object.warnIfOpen();
        }
        if ((object = this.fd) != null && object != FileDescriptor.in) {
            this.close();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public FileChannel getChannel() {
        synchronized (this) {
            if (this.channel != null) return this.channel;
            this.channel = FileChannelImpl.open(this.fd, this.path, true, false, this);
            return this.channel;
        }
    }

    public final FileDescriptor getFD() throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor != null) {
            return fileDescriptor;
        }
        throw new IOException();
    }

    @Override
    public int read() throws IOException {
        byte[] arrby = new byte[1];
        int n = this.read(arrby, 0, 1);
        int n2 = -1;
        if (n != -1) {
            n2 = arrby[0] & 255;
        }
        return n2;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (this.closed && n2 > 0) {
            throw new IOException("Stream Closed");
        }
        this.tracker.trackIo(n2);
        return IoBridge.read((FileDescriptor)this.fd, (byte[])arrby, (int)n, (int)n2);
    }

    @Override
    public long skip(long l) throws IOException {
        if (!this.closed) {
            try {
                BlockGuard.getThreadPolicy().onReadFromDisk();
                long l2 = this.skip0(l);
                return l2;
            }
            catch (UseManualSkipException useManualSkipException) {
                return super.skip(l);
            }
        }
        throw new IOException("Stream Closed");
    }

    private static class UseManualSkipException
    extends Exception {
        private UseManualSkipException() {
        }
    }

}

