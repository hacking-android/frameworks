/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  dalvik.system.VMRuntime
 *  java.nio.NioUtils
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import dalvik.system.VMRuntime;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.nio.ByteBuffer;
import java.nio.DirectByteBuffer;
import java.nio.NioUtils;
import sun.misc.Cleaner;

public final class SharedMemory
implements Parcelable,
Closeable {
    public static final Parcelable.Creator<SharedMemory> CREATOR;
    private static final int PROT_MASK;
    private Cleaner mCleaner;
    private final FileDescriptor mFileDescriptor;
    private final MemoryRegistration mMemoryRegistration;
    private final int mSize;

    static {
        PROT_MASK = OsConstants.PROT_READ | OsConstants.PROT_WRITE | OsConstants.PROT_EXEC | OsConstants.PROT_NONE;
        CREATOR = new Parcelable.Creator<SharedMemory>(){

            @Override
            public SharedMemory createFromParcel(Parcel parcel) {
                return new SharedMemory(parcel.readRawFileDescriptor());
            }

            public SharedMemory[] newArray(int n) {
                return new SharedMemory[n];
            }
        };
    }

    private SharedMemory(FileDescriptor fileDescriptor) {
        if (fileDescriptor != null) {
            if (fileDescriptor.valid()) {
                this.mFileDescriptor = fileDescriptor;
                this.mSize = SharedMemory.nGetSize(this.mFileDescriptor);
                int n = this.mSize;
                if (n > 0) {
                    this.mMemoryRegistration = new MemoryRegistration(n);
                    fileDescriptor = this.mFileDescriptor;
                    this.mCleaner = Cleaner.create(fileDescriptor, new Closer(fileDescriptor, this.mMemoryRegistration));
                    return;
                }
                throw new IllegalArgumentException("FileDescriptor is not a valid ashmem fd");
            }
            throw new IllegalArgumentException("Unable to create SharedMemory from closed FileDescriptor");
        }
        throw new IllegalArgumentException("Unable to create SharedMemory from a null FileDescriptor");
    }

    private void checkOpen() {
        if (this.mFileDescriptor.valid()) {
            return;
        }
        throw new IllegalStateException("SharedMemory is closed");
    }

    public static SharedMemory create(String string2, int n) throws ErrnoException {
        if (n > 0) {
            return new SharedMemory(SharedMemory.nCreate(string2, n));
        }
        throw new IllegalArgumentException("Size must be greater than zero");
    }

    private static native FileDescriptor nCreate(String var0, int var1) throws ErrnoException;

    private static native int nGetSize(FileDescriptor var0);

    private static native int nSetProt(FileDescriptor var0, int var1);

    public static void unmap(ByteBuffer byteBuffer) {
        if (byteBuffer instanceof DirectByteBuffer) {
            NioUtils.freeDirectBuffer((ByteBuffer)byteBuffer);
            return;
        }
        throw new IllegalArgumentException("ByteBuffer wasn't created by #map(int, int, int); can't unmap");
    }

    private static void validateProt(int n) {
        if ((PROT_MASK & n) == 0) {
            return;
        }
        throw new IllegalArgumentException("Invalid prot value");
    }

    @Override
    public void close() {
        Cleaner cleaner = this.mCleaner;
        if (cleaner != null) {
            cleaner.clean();
            this.mCleaner = null;
        }
    }

    @Override
    public int describeContents() {
        return 1;
    }

    @UnsupportedAppUsage
    public int getFd() {
        return this.mFileDescriptor.getInt$();
    }

    public FileDescriptor getFileDescriptor() {
        return this.mFileDescriptor;
    }

    public int getSize() {
        this.checkOpen();
        return this.mSize;
    }

    public ByteBuffer map(int n, int n2, int n3) throws ErrnoException {
        this.checkOpen();
        SharedMemory.validateProt(n);
        if (n2 >= 0) {
            if (n3 > 0) {
                if (n2 + n3 <= this.mSize) {
                    long l = Os.mmap((long)0L, (long)n3, (int)n, (int)OsConstants.MAP_SHARED, (FileDescriptor)this.mFileDescriptor, (long)n2);
                    boolean bl = (n & OsConstants.PROT_WRITE) == 0;
                    Unmapper unmapper = new Unmapper(l, n3, this.mMemoryRegistration.acquire());
                    return new DirectByteBuffer(n3, l, this.mFileDescriptor, (Runnable)unmapper, bl);
                }
                throw new IllegalArgumentException("offset + length must not exceed getSize()");
            }
            throw new IllegalArgumentException("Length must be > 0");
        }
        throw new IllegalArgumentException("Offset must be >= 0");
    }

    public ByteBuffer mapReadOnly() throws ErrnoException {
        return this.map(OsConstants.PROT_READ, 0, this.mSize);
    }

    public ByteBuffer mapReadWrite() throws ErrnoException {
        return this.map(OsConstants.PROT_READ | OsConstants.PROT_WRITE, 0, this.mSize);
    }

    public boolean setProtect(int n) {
        this.checkOpen();
        SharedMemory.validateProt(n);
        boolean bl = SharedMemory.nSetProt(this.mFileDescriptor, n) == 0;
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.checkOpen();
        parcel.writeFileDescriptor(this.mFileDescriptor);
    }

    private static final class Closer
    implements Runnable {
        private FileDescriptor mFd;
        private MemoryRegistration mMemoryReference;

        private Closer(FileDescriptor fileDescriptor, MemoryRegistration memoryRegistration) {
            this.mFd = fileDescriptor;
            this.mMemoryReference = memoryRegistration;
        }

        @Override
        public void run() {
            try {
                Os.close((FileDescriptor)this.mFd);
            }
            catch (ErrnoException errnoException) {
                // empty catch block
            }
            this.mMemoryReference.release();
            this.mMemoryReference = null;
        }
    }

    private static final class MemoryRegistration {
        private int mReferenceCount;
        private int mSize;

        private MemoryRegistration(int n) {
            this.mSize = n;
            this.mReferenceCount = 1;
            VMRuntime.getRuntime().registerNativeAllocation(this.mSize);
        }

        public MemoryRegistration acquire() {
            synchronized (this) {
                ++this.mReferenceCount;
                return this;
            }
        }

        public void release() {
            synchronized (this) {
                --this.mReferenceCount;
                if (this.mReferenceCount == 0) {
                    VMRuntime.getRuntime().registerNativeFree(this.mSize);
                }
                return;
            }
        }
    }

    private static final class Unmapper
    implements Runnable {
        private long mAddress;
        private MemoryRegistration mMemoryReference;
        private int mSize;

        private Unmapper(long l, int n, MemoryRegistration memoryRegistration) {
            this.mAddress = l;
            this.mSize = n;
            this.mMemoryReference = memoryRegistration;
        }

        @Override
        public void run() {
            try {
                Os.munmap((long)this.mAddress, (long)this.mSize);
            }
            catch (ErrnoException errnoException) {
                // empty catch block
            }
            this.mMemoryReference.release();
            this.mMemoryReference = null;
        }
    }

}

