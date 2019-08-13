/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 *  dalvik.annotation.optimization.ReachabilitySensitive
 *  dalvik.system.CloseGuard
 *  libcore.io.IoBridge
 *  libcore.io.IoTracker
 *  libcore.io.IoUtils
 */
package java.io;

import android.system.OsConstants;
import dalvik.annotation.optimization.ReachabilitySensitive;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import libcore.io.IoBridge;
import libcore.io.IoTracker;
import libcore.io.IoUtils;
import sun.nio.ch.FileChannelImpl;

public class FileOutputStream
extends OutputStream {
    private final boolean append;
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

    public FileOutputStream(File file) throws FileNotFoundException {
        this(file, false);
    }

    public FileOutputStream(File file, boolean bl) throws FileNotFoundException {
        this.closeLock = new Object();
        this.closed = false;
        this.guard = CloseGuard.get();
        this.tracker = new IoTracker();
        String string = file != null ? file.getPath() : null;
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(string);
        }
        if (string != null) {
            if (!file.isInvalid()) {
                int n = OsConstants.O_WRONLY;
                int n2 = OsConstants.O_CREAT;
                int n3 = bl ? OsConstants.O_APPEND : OsConstants.O_TRUNC;
                this.fd = IoBridge.open((String)string, (int)(n | n2 | n3));
                this.isFdOwner = true;
                this.append = bl;
                this.path = string;
                IoUtils.setFdOwner((FileDescriptor)this.fd, (Object)this);
                this.guard.open("close");
                return;
            }
            throw new FileNotFoundException("Invalid file path");
        }
        throw new NullPointerException();
    }

    public FileOutputStream(FileDescriptor fileDescriptor) {
        this(fileDescriptor, false);
    }

    public FileOutputStream(FileDescriptor fileDescriptor, boolean bl) {
        this.closeLock = new Object();
        this.closed = false;
        this.guard = CloseGuard.get();
        this.tracker = new IoTracker();
        if (fileDescriptor != null) {
            this.fd = fileDescriptor;
            this.append = false;
            this.path = null;
            this.isFdOwner = bl;
            return;
        }
        throw new NullPointerException("fdObj == null");
    }

    public FileOutputStream(String object) throws FileNotFoundException {
        object = object != null ? new File((String)object) : null;
        this((File)object, false);
    }

    public FileOutputStream(String object, boolean bl) throws FileNotFoundException {
        object = object != null ? new File((String)object) : null;
        this((File)object, bl);
    }

    private void open(String string, boolean bl) throws FileNotFoundException {
        this.open0(string, bl);
    }

    private native void open0(String var1, boolean var2) throws FileNotFoundException;

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
        if ((object = this.fd) != null) {
            if (object != FileDescriptor.out && this.fd != FileDescriptor.err) {
                this.close();
            } else {
                this.flush();
            }
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
            this.channel = FileChannelImpl.open(this.fd, this.path, false, true, this.append, this);
            return this.channel;
        }
    }

    @ReachabilitySensitive
    public final FileDescriptor getFD() throws IOException {
        FileDescriptor fileDescriptor = this.fd;
        if (fileDescriptor != null) {
            return fileDescriptor;
        }
        throw new IOException();
    }

    @Override
    public void write(int n) throws IOException {
        this.write(new byte[]{(byte)n}, 0, 1);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (this.closed && n2 > 0) {
            throw new IOException("Stream Closed");
        }
        this.tracker.trackIo(n2);
        IoBridge.write((FileDescriptor)this.fd, (byte[])arrby, (int)n, (int)n2);
    }
}

