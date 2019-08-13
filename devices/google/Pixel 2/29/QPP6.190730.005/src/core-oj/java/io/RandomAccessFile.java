/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 *  dalvik.annotation.optimization.ReachabilitySensitive
 *  dalvik.system.CloseGuard
 *  libcore.io.IoBridge
 *  libcore.io.IoTracker
 *  libcore.io.IoTracker$Mode
 *  libcore.io.IoUtils
 *  libcore.io.Libcore
 *  libcore.io.Os
 */
package java.io;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import dalvik.annotation.optimization.ReachabilitySensitive;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import libcore.io.IoBridge;
import libcore.io.IoTracker;
import libcore.io.IoUtils;
import libcore.io.Libcore;
import sun.nio.ch.FileChannelImpl;

public class RandomAccessFile
implements DataOutput,
DataInput,
Closeable {
    private static final int FLUSH_FDATASYNC = 2;
    private static final int FLUSH_FSYNC = 1;
    private static final int FLUSH_NONE = 0;
    private FileChannel channel;
    private Object closeLock;
    private volatile boolean closed;
    @ReachabilitySensitive
    private FileDescriptor fd;
    private int flushAfterWrite;
    @ReachabilitySensitive
    private final CloseGuard guard;
    private final IoTracker ioTracker;
    private int mode;
    private final String path;
    private boolean rw;
    private final byte[] scratch;

    public RandomAccessFile(File serializable, String string) throws FileNotFoundException {
        this.guard = CloseGuard.get();
        this.scratch = new byte[8];
        this.flushAfterWrite = 0;
        String string2 = null;
        this.channel = null;
        this.closeLock = new Object();
        this.closed = false;
        this.ioTracker = new IoTracker();
        if (serializable != null) {
            string2 = ((File)serializable).getPath();
        }
        int n = -1;
        if (string.equals("r")) {
            n = OsConstants.O_RDONLY;
        } else if (string.startsWith("rw")) {
            int n2 = OsConstants.O_RDWR | OsConstants.O_CREAT;
            this.rw = true;
            n = n2;
            if (string.length() > 2) {
                if (string.equals("rws")) {
                    this.flushAfterWrite = 1;
                    n = n2;
                } else if (string.equals("rwd")) {
                    this.flushAfterWrite = 2;
                    n = n2;
                } else {
                    n = -1;
                }
            }
        }
        if (n >= 0) {
            if (string2 != null) {
                if (!((File)serializable).isInvalid()) {
                    this.path = string2;
                    this.mode = n;
                    this.fd = IoBridge.open((String)string2, (int)n);
                    IoUtils.setFdOwner((FileDescriptor)this.fd, (Object)this);
                    this.maybeSync();
                    this.guard.open("close");
                    return;
                }
                throw new FileNotFoundException("Invalid file path");
            }
            throw new NullPointerException("file == null");
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Illegal mode \"");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append("\" must be one of \"r\", \"rw\", \"rws\", or \"rwd\"");
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public RandomAccessFile(String object, String string) throws FileNotFoundException {
        object = object != null ? new File((String)object) : null;
        this((File)object, string);
    }

    private void maybeSync() {
        int n = this.flushAfterWrite;
        if (n == 1) {
            try {
                this.fd.sync();
            }
            catch (IOException iOException) {}
        } else if (n == 2) {
            try {
                Os.fdatasync((FileDescriptor)this.fd);
            }
            catch (ErrnoException errnoException) {
                // empty catch block
            }
        }
    }

    private int readBytes(byte[] arrby, int n, int n2) throws IOException {
        this.ioTracker.trackIo(n2, IoTracker.Mode.READ);
        return IoBridge.read((FileDescriptor)this.fd, (byte[])arrby, (int)n, (int)n2);
    }

    private void writeBytes(byte[] arrby, int n, int n2) throws IOException {
        this.ioTracker.trackIo(n2, IoTracker.Mode.WRITE);
        IoBridge.write((FileDescriptor)this.fd, (byte[])arrby, (int)n, (int)n2);
        this.maybeSync();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        this.guard.close();
        Object object = this.closeLock;
        synchronized (object) {
            if (this.closed) {
                return;
            }
            this.closed = true;
        }
        FileChannel fileChannel = this.channel;
        if (fileChannel != null && fileChannel.isOpen()) {
            this.channel.close();
        }
        IoBridge.closeAndSignalBlockedThreads((FileDescriptor)this.fd);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.guard != null) {
                this.guard.warnIfOpen();
            }
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final FileChannel getChannel() {
        synchronized (this) {
            if (this.channel != null) return this.channel;
            this.channel = FileChannelImpl.open(this.fd, this.path, true, this.rw, this);
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

    public long getFilePointer() throws IOException {
        try {
            long l = Libcore.os.lseek(this.fd, 0L, OsConstants.SEEK_CUR);
            return l;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
    }

    public long length() throws IOException {
        try {
            long l = Libcore.os.fstat((FileDescriptor)this.fd).st_size;
            return l;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
    }

    public int read() throws IOException {
        int n = this.read(this.scratch, 0, 1);
        int n2 = -1;
        if (n != -1) {
            n2 = this.scratch[0] & 255;
        }
        return n2;
    }

    public int read(byte[] arrby) throws IOException {
        return this.readBytes(arrby, 0, arrby.length);
    }

    public int read(byte[] arrby, int n, int n2) throws IOException {
        return this.readBytes(arrby, n, n2);
    }

    @Override
    public final boolean readBoolean() throws IOException {
        int n = this.read();
        if (n >= 0) {
            boolean bl = n != 0;
            return bl;
        }
        throw new EOFException();
    }

    @Override
    public final byte readByte() throws IOException {
        int n = this.read();
        if (n >= 0) {
            return (byte)n;
        }
        throw new EOFException();
    }

    @Override
    public final char readChar() throws IOException {
        int n;
        int n2 = this.read();
        if ((n2 | (n = this.read())) >= 0) {
            return (char)((n2 << 8) + (n << 0));
        }
        throw new EOFException();
    }

    @Override
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }

    @Override
    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }

    @Override
    public final void readFully(byte[] arrby) throws IOException {
        this.readFully(arrby, 0, arrby.length);
    }

    @Override
    public final void readFully(byte[] arrby, int n, int n2) throws IOException {
        int n3;
        int n4 = 0;
        while ((n3 = this.read(arrby, n + n4, n2 - n4)) >= 0) {
            n4 = n3 = n4 + n3;
            if (n3 < n2) continue;
            return;
        }
        throw new EOFException();
    }

    @Override
    public final int readInt() throws IOException {
        int n;
        int n2;
        int n3;
        int n4 = this.read();
        if ((n4 | (n3 = this.read()) | (n2 = this.read()) | (n = this.read())) >= 0) {
            return (n4 << 24) + (n3 << 16) + (n2 << 8) + (n << 0);
        }
        throw new EOFException();
    }

    @Override
    public final String readLine() throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        int n = -1;
        boolean bl = false;
        while (!bl) {
            int n2 = n = this.read();
            if (n != -1 && n != 10) {
                if (n != 13) {
                    stringBuffer.append((char)n2);
                    n = n2;
                    continue;
                }
                boolean bl2 = true;
                long l = this.getFilePointer();
                n = n2;
                bl = bl2;
                if (this.read() == 10) continue;
                this.seek(l);
                n = n2;
                bl = bl2;
                continue;
            }
            bl = true;
            n = n2;
        }
        if (n == -1 && stringBuffer.length() == 0) {
            return null;
        }
        return stringBuffer.toString();
    }

    @Override
    public final long readLong() throws IOException {
        return ((long)this.readInt() << 32) + ((long)this.readInt() & 0xFFFFFFFFL);
    }

    @Override
    public final short readShort() throws IOException {
        int n;
        int n2 = this.read();
        if ((n2 | (n = this.read())) >= 0) {
            return (short)((n2 << 8) + (n << 0));
        }
        throw new EOFException();
    }

    @Override
    public final String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }

    @Override
    public final int readUnsignedByte() throws IOException {
        int n = this.read();
        if (n >= 0) {
            return n;
        }
        throw new EOFException();
    }

    @Override
    public final int readUnsignedShort() throws IOException {
        int n;
        int n2 = this.read();
        if ((n2 | (n = this.read())) >= 0) {
            return (n2 << 8) + (n << 0);
        }
        throw new EOFException();
    }

    public void seek(long l) throws IOException {
        if (l >= 0L) {
            try {
                Libcore.os.lseek(this.fd, l, OsConstants.SEEK_SET);
                this.ioTracker.reset();
                return;
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsIOException();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("offset < 0: ");
        stringBuilder.append(l);
        throw new IOException(stringBuilder.toString());
    }

    public void setLength(long l) throws IOException {
        if (l >= 0L) {
            try {
                Libcore.os.ftruncate(this.fd, l);
                if (this.getFilePointer() > l) {
                    this.seek(l);
                }
                this.maybeSync();
                return;
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsIOException();
            }
        }
        throw new IllegalArgumentException("newLength < 0");
    }

    @Override
    public int skipBytes(int n) throws IOException {
        long l;
        if (n <= 0) {
            return 0;
        }
        long l2 = this.getFilePointer();
        long l3 = this.length();
        long l4 = l = (long)n + l2;
        if (l > l3) {
            l4 = l3;
        }
        this.seek(l4);
        return (int)(l4 - l2);
    }

    @Override
    public void write(int n) throws IOException {
        byte[] arrby = this.scratch;
        arrby[0] = (byte)(n & 255);
        this.write(arrby, 0, 1);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.writeBytes(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.writeBytes(arrby, n, n2);
    }

    @Override
    public final void writeBoolean(boolean bl) throws IOException {
        this.write((int)bl);
    }

    @Override
    public final void writeByte(int n) throws IOException {
        this.write(n);
    }

    @Override
    public final void writeBytes(String string) throws IOException {
        int n = string.length();
        byte[] arrby = new byte[n];
        string.getBytes(0, n, arrby, 0);
        this.writeBytes(arrby, 0, n);
    }

    @Override
    public final void writeChar(int n) throws IOException {
        this.write(n >>> 8 & 255);
        this.write(n >>> 0 & 255);
    }

    @Override
    public final void writeChars(String string) throws IOException {
        int n = string.length();
        int n2 = n * 2;
        byte[] arrby = new byte[n2];
        char[] arrc = new char[n];
        string.getChars(0, n, arrc, 0);
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            int n4 = n3 + 1;
            arrby[n3] = (byte)(arrc[i] >>> 8);
            n3 = n4 + 1;
            arrby[n4] = (byte)(arrc[i] >>> 0);
        }
        this.writeBytes(arrby, 0, n2);
    }

    @Override
    public final void writeDouble(double d) throws IOException {
        this.writeLong(Double.doubleToLongBits(d));
    }

    @Override
    public final void writeFloat(float f) throws IOException {
        this.writeInt(Float.floatToIntBits(f));
    }

    @Override
    public final void writeInt(int n) throws IOException {
        this.write(n >>> 24 & 255);
        this.write(n >>> 16 & 255);
        this.write(n >>> 8 & 255);
        this.write(n >>> 0 & 255);
    }

    @Override
    public final void writeLong(long l) throws IOException {
        this.write((int)(l >>> 56) & 255);
        this.write((int)(l >>> 48) & 255);
        this.write((int)(l >>> 40) & 255);
        this.write((int)(l >>> 32) & 255);
        this.write((int)(l >>> 24) & 255);
        this.write((int)(l >>> 16) & 255);
        this.write((int)(l >>> 8) & 255);
        this.write((int)(l >>> 0) & 255);
    }

    @Override
    public final void writeShort(int n) throws IOException {
        this.write(n >>> 8 & 255);
        this.write(n >>> 0 & 255);
    }

    @Override
    public final void writeUTF(String string) throws IOException {
        DataOutputStream.writeUTF(string, this);
    }
}

