/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.SharedMemory;
import android.system.ErrnoException;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class MemoryFile {
    private static String TAG = "MemoryFile";
    private boolean mAllowPurging = false;
    private ByteBuffer mMapping;
    private SharedMemory mSharedMemory;

    public MemoryFile(String string2, int n) throws IOException {
        try {
            this.mSharedMemory = SharedMemory.create(string2, n);
            this.mMapping = this.mSharedMemory.mapReadWrite();
        }
        catch (ErrnoException errnoException) {
            errnoException.rethrowAsIOException();
        }
    }

    private void beginAccess() throws IOException {
        this.checkActive();
        if (this.mAllowPurging && MemoryFile.native_pin(this.mSharedMemory.getFileDescriptor(), true)) {
            throw new IOException("MemoryFile has been purged");
        }
    }

    private void checkActive() throws IOException {
        if (this.mMapping != null) {
            return;
        }
        throw new IOException("MemoryFile has been deactivated");
    }

    private void endAccess() throws IOException {
        if (this.mAllowPurging) {
            MemoryFile.native_pin(this.mSharedMemory.getFileDescriptor(), false);
        }
    }

    @UnsupportedAppUsage
    public static int getSize(FileDescriptor fileDescriptor) throws IOException {
        return MemoryFile.native_get_size(fileDescriptor);
    }

    @UnsupportedAppUsage
    private static native int native_get_size(FileDescriptor var0) throws IOException;

    @UnsupportedAppUsage
    private static native boolean native_pin(FileDescriptor var0, boolean var1) throws IOException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public boolean allowPurging(boolean bl) throws IOException {
        synchronized (this) {
            boolean bl2 = this.mAllowPurging;
            if (bl2 != bl) {
                FileDescriptor fileDescriptor = this.mSharedMemory.getFileDescriptor();
                boolean bl3 = !bl;
                MemoryFile.native_pin(fileDescriptor, bl3);
                this.mAllowPurging = bl;
            }
            return bl2;
        }
    }

    public void close() {
        this.deactivate();
        this.mSharedMemory.close();
    }

    @UnsupportedAppUsage
    void deactivate() {
        ByteBuffer byteBuffer = this.mMapping;
        if (byteBuffer != null) {
            SharedMemory.unmap(byteBuffer);
            this.mMapping = null;
        }
    }

    @UnsupportedAppUsage
    public FileDescriptor getFileDescriptor() throws IOException {
        return this.mSharedMemory.getFileDescriptor();
    }

    public InputStream getInputStream() {
        return new MemoryInputStream();
    }

    public OutputStream getOutputStream() {
        return new MemoryOutputStream();
    }

    @Deprecated
    public boolean isPurgingAllowed() {
        return this.mAllowPurging;
    }

    public int length() {
        return this.mSharedMemory.getSize();
    }

    public int readBytes(byte[] arrby, int n, int n2, int n3) throws IOException {
        this.beginAccess();
        try {
            this.mMapping.position(n);
            this.mMapping.get(arrby, n2, n3);
            return n3;
        }
        finally {
            this.endAccess();
        }
    }

    public void writeBytes(byte[] arrby, int n, int n2, int n3) throws IOException {
        this.beginAccess();
        try {
            this.mMapping.position(n2);
            this.mMapping.put(arrby, n, n3);
            return;
        }
        finally {
            this.endAccess();
        }
    }

    private class MemoryInputStream
    extends InputStream {
        private int mMark = 0;
        private int mOffset = 0;
        private byte[] mSingleByte;

        private MemoryInputStream() {
        }

        @Override
        public int available() throws IOException {
            if (this.mOffset >= MemoryFile.this.mSharedMemory.getSize()) {
                return 0;
            }
            return MemoryFile.this.mSharedMemory.getSize() - this.mOffset;
        }

        @Override
        public void mark(int n) {
            this.mMark = this.mOffset;
        }

        @Override
        public boolean markSupported() {
            return true;
        }

        @Override
        public int read() throws IOException {
            if (this.mSingleByte == null) {
                this.mSingleByte = new byte[1];
            }
            if (this.read(this.mSingleByte, 0, 1) != 1) {
                return -1;
            }
            return this.mSingleByte[0];
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            if (n >= 0 && n2 >= 0 && n + n2 <= arrby.length) {
                if ((n2 = Math.min(n2, this.available())) < 1) {
                    return -1;
                }
                if ((n = MemoryFile.this.readBytes(arrby, this.mOffset, n, n2)) > 0) {
                    this.mOffset += n;
                }
                return n;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void reset() throws IOException {
            this.mOffset = this.mMark;
        }

        @Override
        public long skip(long l) throws IOException {
            long l2 = l;
            if ((long)this.mOffset + l > (long)MemoryFile.this.mSharedMemory.getSize()) {
                l2 = MemoryFile.this.mSharedMemory.getSize() - this.mOffset;
            }
            this.mOffset = (int)((long)this.mOffset + l2);
            return l2;
        }
    }

    private class MemoryOutputStream
    extends OutputStream {
        private int mOffset = 0;
        private byte[] mSingleByte;

        private MemoryOutputStream() {
        }

        @Override
        public void write(int n) throws IOException {
            if (this.mSingleByte == null) {
                this.mSingleByte = new byte[1];
            }
            byte[] arrby = this.mSingleByte;
            arrby[0] = (byte)n;
            this.write(arrby, 0, 1);
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            MemoryFile.this.writeBytes(arrby, n, this.mOffset, n2);
            this.mOffset += n2;
        }
    }

}

