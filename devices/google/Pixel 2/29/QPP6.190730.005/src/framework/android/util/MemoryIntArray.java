/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 *  libcore.io.IoUtils
 */
package android.util;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;
import libcore.io.IoUtils;

public final class MemoryIntArray
implements Parcelable,
Closeable {
    public static final Parcelable.Creator<MemoryIntArray> CREATOR = new Parcelable.Creator<MemoryIntArray>(){

        @Override
        public MemoryIntArray createFromParcel(Parcel object) {
            try {
                object = new MemoryIntArray((Parcel)object);
                return object;
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("Error unparceling MemoryIntArray");
            }
        }

        public MemoryIntArray[] newArray(int n) {
            return new MemoryIntArray[n];
        }
    };
    private static final int MAX_SIZE = 1024;
    private static final String TAG = "MemoryIntArray";
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private int mFd = -1;
    private final boolean mIsOwner;
    private final long mMemoryAddr;

    public MemoryIntArray(int n) throws IOException {
        if (n <= 1024) {
            this.mIsOwner = true;
            this.mFd = this.nativeCreate(UUID.randomUUID().toString(), n);
            this.mMemoryAddr = this.nativeOpen(this.mFd, this.mIsOwner);
            this.mCloseGuard.open("close");
            return;
        }
        throw new IllegalArgumentException("Max size is 1024");
    }

    private MemoryIntArray(Parcel object) throws IOException {
        this.mIsOwner = false;
        object = (ParcelFileDescriptor)((Parcel)object).readParcelable(null);
        if (object != null) {
            this.mFd = ((ParcelFileDescriptor)object).detachFd();
            this.mMemoryAddr = this.nativeOpen(this.mFd, this.mIsOwner);
            this.mCloseGuard.open("close");
            return;
        }
        throw new IOException("No backing file descriptor");
    }

    private void enforceNotClosed() {
        if (!this.isClosed()) {
            return;
        }
        throw new IllegalStateException("cannot interact with a closed instance");
    }

    private void enforceValidIndex(int n) throws IOException {
        int n2 = this.size();
        if (n >= 0 && n <= n2 - 1) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(" not between 0 and ");
        stringBuilder.append(n2 - 1);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    private void enforceWritable() {
        if (this.isWritable()) {
            return;
        }
        throw new UnsupportedOperationException("array is not writable");
    }

    public static int getMaxSize() {
        return 1024;
    }

    private native void nativeClose(int var1, long var2, boolean var4);

    private native int nativeCreate(String var1, int var2);

    private native int nativeGet(int var1, long var2, int var4);

    private native long nativeOpen(int var1, boolean var2);

    private native void nativeSet(int var1, long var2, int var4, int var5);

    private native int nativeSize(int var1);

    @Override
    public void close() throws IOException {
        if (!this.isClosed()) {
            this.nativeClose(this.mFd, this.mMemoryAddr, this.mIsOwner);
            this.mFd = -1;
            this.mCloseGuard.close();
        }
    }

    @Override
    public int describeContents() {
        return 1;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (MemoryIntArray)object;
        if (this.mFd == ((MemoryIntArray)object).mFd) {
            bl = true;
        }
        return bl;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            IoUtils.closeQuietly((AutoCloseable)this);
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int get(int n) throws IOException {
        this.enforceNotClosed();
        this.enforceValidIndex(n);
        return this.nativeGet(this.mFd, this.mMemoryAddr, n);
    }

    public int hashCode() {
        return this.mFd;
    }

    public boolean isClosed() {
        boolean bl = this.mFd == -1;
        return bl;
    }

    public boolean isWritable() {
        this.enforceNotClosed();
        return this.mIsOwner;
    }

    public void set(int n, int n2) throws IOException {
        this.enforceNotClosed();
        this.enforceWritable();
        this.enforceValidIndex(n);
        this.nativeSet(this.mFd, this.mMemoryAddr, n, n2);
    }

    public int size() throws IOException {
        this.enforceNotClosed();
        return this.nativeSize(this.mFd);
    }

    /*
     * Exception decompiling
     */
    @Override
    public void writeToParcel(Parcel var1_1, int var2_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

}

