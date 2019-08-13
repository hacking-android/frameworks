/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  dalvik.system.CloseGuard
 */
package android.database;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.database.CharArrayBuffer;
import android.database.sqlite.SQLiteClosable;
import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseIntArray;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.CloseGuard;

public class CursorWindow
extends SQLiteClosable
implements Parcelable {
    public static final Parcelable.Creator<CursorWindow> CREATOR;
    private static final String STATS_TAG = "CursorWindowStats";
    @UnsupportedAppUsage
    private static int sCursorWindowSize;
    @UnsupportedAppUsage
    private static final LongSparseArray<Integer> sWindowToPidMap;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final String mName;
    private int mStartPos;
    @UnsupportedAppUsage
    public long mWindowPtr;

    static {
        sCursorWindowSize = -1;
        CREATOR = new Parcelable.Creator<CursorWindow>(){

            @Override
            public CursorWindow createFromParcel(Parcel parcel) {
                return new CursorWindow(parcel);
            }

            public CursorWindow[] newArray(int n) {
                return new CursorWindow[n];
            }
        };
        sWindowToPidMap = new LongSparseArray();
    }

    private CursorWindow(Parcel parcel) {
        this.mStartPos = parcel.readInt();
        long l = this.mWindowPtr = CursorWindow.nativeCreateFromParcel(parcel);
        if (l != 0L) {
            this.mName = CursorWindow.nativeGetName(l);
            this.mCloseGuard.open("close");
            return;
        }
        throw new AssertionError();
    }

    public CursorWindow(String string2) {
        this(string2, CursorWindow.getCursorWindowSize());
    }

    public CursorWindow(String string2, long l) {
        this.mStartPos = 0;
        if (string2 == null || string2.length() == 0) {
            string2 = "<unnamed>";
        }
        this.mName = string2;
        this.mWindowPtr = CursorWindow.nativeCreate(this.mName, (int)l);
        if (this.mWindowPtr != 0L) {
            this.mCloseGuard.open("close");
            this.recordNewWindow(Binder.getCallingPid(), this.mWindowPtr);
            return;
        }
        throw new AssertionError();
    }

    @Deprecated
    public CursorWindow(boolean bl) {
        this((String)null);
    }

    private void dispose() {
        long l;
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            closeGuard.close();
        }
        if ((l = this.mWindowPtr) != 0L) {
            this.recordClosingOfWindow(l);
            CursorWindow.nativeDispose(this.mWindowPtr);
            this.mWindowPtr = 0L;
        }
    }

    private static int getCursorWindowSize() {
        if (sCursorWindowSize < 0) {
            sCursorWindowSize = Resources.getSystem().getInteger(17694764) * 1024;
        }
        return sCursorWindowSize;
    }

    @FastNative
    private static native boolean nativeAllocRow(long var0);

    @FastNative
    private static native void nativeClear(long var0);

    private static native void nativeCopyStringToBuffer(long var0, int var2, int var3, CharArrayBuffer var4);

    private static native long nativeCreate(String var0, int var1);

    private static native long nativeCreateFromParcel(Parcel var0);

    private static native void nativeDispose(long var0);

    @FastNative
    private static native void nativeFreeLastRow(long var0);

    private static native byte[] nativeGetBlob(long var0, int var2, int var3);

    @FastNative
    private static native double nativeGetDouble(long var0, int var2, int var3);

    @FastNative
    private static native long nativeGetLong(long var0, int var2, int var3);

    private static native String nativeGetName(long var0);

    @FastNative
    private static native int nativeGetNumRows(long var0);

    private static native String nativeGetString(long var0, int var2, int var3);

    @FastNative
    private static native int nativeGetType(long var0, int var2, int var3);

    private static native boolean nativePutBlob(long var0, byte[] var2, int var3, int var4);

    @FastNative
    private static native boolean nativePutDouble(long var0, double var2, int var4, int var5);

    @FastNative
    private static native boolean nativePutLong(long var0, long var2, int var4, int var5);

    @FastNative
    private static native boolean nativePutNull(long var0, int var2, int var3);

    private static native boolean nativePutString(long var0, String var2, int var3, int var4);

    @FastNative
    private static native boolean nativeSetNumColumns(long var0, int var2);

    private static native void nativeWriteToParcel(long var0, Parcel var2);

    public static CursorWindow newFromParcel(Parcel parcel) {
        return CREATOR.createFromParcel(parcel);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private String printStats() {
        int n;
        int n2;
        int n3;
        StringBuilder stringBuilder = new StringBuilder();
        int n4 = Process.myPid();
        int n5 = 0;
        Object object = new SparseIntArray();
        Object object2 = sWindowToPidMap;
        synchronized (object2) {
            n3 = sWindowToPidMap.size();
            if (n3 == 0) {
                return "";
            }
            for (n2 = 0; n2 < n3; ++n2) {
                n = sWindowToPidMap.valueAt(n2);
                ((SparseIntArray)object).put(n, ((SparseIntArray)object).get(n) + 1);
            }
        }
        n3 = ((SparseIntArray)object).size();
        for (n2 = 0; n2 < n3; n5 += n, ++n2) {
            stringBuilder.append(" (# cursors opened by ");
            n = ((SparseIntArray)object).keyAt(n2);
            if (n == n4) {
                stringBuilder.append("this proc=");
            } else {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("pid ");
                ((StringBuilder)object2).append(n);
                ((StringBuilder)object2).append("=");
                stringBuilder.append(((StringBuilder)object2).toString());
            }
            n = ((SparseIntArray)object).get(n);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(")");
            stringBuilder.append(((StringBuilder)object2).toString());
        }
        object = stringBuilder.length() > 980 ? stringBuilder.substring(0, 980) : stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append("# Open Cursors=");
        stringBuilder.append(n5);
        stringBuilder.append((String)object);
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void recordClosingOfWindow(long l) {
        LongSparseArray<Integer> longSparseArray = sWindowToPidMap;
        synchronized (longSparseArray) {
            if (sWindowToPidMap.size() == 0) {
                return;
            }
            sWindowToPidMap.delete(l);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void recordNewWindow(int n, long l) {
        LongSparseArray<Integer> longSparseArray = sWindowToPidMap;
        synchronized (longSparseArray) {
            sWindowToPidMap.put(l, n);
            if (Log.isLoggable("CursorWindowStats", 2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Created a new Cursor. ");
                stringBuilder.append(this.printStats());
                Log.i("CursorWindowStats", stringBuilder.toString());
            }
            return;
        }
    }

    public boolean allocRow() {
        this.acquireReference();
        try {
            boolean bl = CursorWindow.nativeAllocRow(this.mWindowPtr);
            return bl;
        }
        finally {
            this.releaseReference();
        }
    }

    public void clear() {
        this.acquireReference();
        try {
            this.mStartPos = 0;
            CursorWindow.nativeClear(this.mWindowPtr);
            return;
        }
        finally {
            this.releaseReference();
        }
    }

    public void copyStringToBuffer(int n, int n2, CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer != null) {
            this.acquireReference();
            try {
                CursorWindow.nativeCopyStringToBuffer(this.mWindowPtr, n - this.mStartPos, n2, charArrayBuffer);
                return;
            }
            finally {
                this.releaseReference();
            }
        }
        throw new IllegalArgumentException("CharArrayBuffer should not be null");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.dispose();
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    public void freeLastRow() {
        this.acquireReference();
        try {
            CursorWindow.nativeFreeLastRow(this.mWindowPtr);
            return;
        }
        finally {
            this.releaseReference();
        }
    }

    public byte[] getBlob(int n, int n2) {
        this.acquireReference();
        try {
            byte[] arrby = CursorWindow.nativeGetBlob(this.mWindowPtr, n - this.mStartPos, n2);
            return arrby;
        }
        finally {
            this.releaseReference();
        }
    }

    public double getDouble(int n, int n2) {
        this.acquireReference();
        try {
            double d = CursorWindow.nativeGetDouble(this.mWindowPtr, n - this.mStartPos, n2);
            return d;
        }
        finally {
            this.releaseReference();
        }
    }

    public float getFloat(int n, int n2) {
        return (float)this.getDouble(n, n2);
    }

    public int getInt(int n, int n2) {
        return (int)this.getLong(n, n2);
    }

    public long getLong(int n, int n2) {
        this.acquireReference();
        try {
            long l = CursorWindow.nativeGetLong(this.mWindowPtr, n - this.mStartPos, n2);
            return l;
        }
        finally {
            this.releaseReference();
        }
    }

    public String getName() {
        return this.mName;
    }

    public int getNumRows() {
        this.acquireReference();
        try {
            int n = CursorWindow.nativeGetNumRows(this.mWindowPtr);
            return n;
        }
        finally {
            this.releaseReference();
        }
    }

    public short getShort(int n, int n2) {
        return (short)this.getLong(n, n2);
    }

    public int getStartPosition() {
        return this.mStartPos;
    }

    public String getString(int n, int n2) {
        this.acquireReference();
        try {
            String string2 = CursorWindow.nativeGetString(this.mWindowPtr, n - this.mStartPos, n2);
            return string2;
        }
        finally {
            this.releaseReference();
        }
    }

    public int getType(int n, int n2) {
        this.acquireReference();
        try {
            n = CursorWindow.nativeGetType(this.mWindowPtr, n - this.mStartPos, n2);
            return n;
        }
        finally {
            this.releaseReference();
        }
    }

    @Deprecated
    public boolean isBlob(int n, int n2) {
        boolean bl = (n = this.getType(n, n2)) == 4 || n == 0;
        return bl;
    }

    @Deprecated
    public boolean isFloat(int n, int n2) {
        boolean bl = this.getType(n, n2) == 2;
        return bl;
    }

    @Deprecated
    public boolean isLong(int n, int n2) {
        n = this.getType(n, n2);
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    @Deprecated
    public boolean isNull(int n, int n2) {
        boolean bl = this.getType(n, n2) == 0;
        return bl;
    }

    @Deprecated
    public boolean isString(int n, int n2) {
        boolean bl = (n = this.getType(n, n2)) == 3 || n == 0;
        return bl;
    }

    @Override
    protected void onAllReferencesReleased() {
        this.dispose();
    }

    public boolean putBlob(byte[] arrby, int n, int n2) {
        this.acquireReference();
        try {
            boolean bl = CursorWindow.nativePutBlob(this.mWindowPtr, arrby, n - this.mStartPos, n2);
            return bl;
        }
        finally {
            this.releaseReference();
        }
    }

    public boolean putDouble(double d, int n, int n2) {
        this.acquireReference();
        try {
            boolean bl = CursorWindow.nativePutDouble(this.mWindowPtr, d, n - this.mStartPos, n2);
            return bl;
        }
        finally {
            this.releaseReference();
        }
    }

    public boolean putLong(long l, int n, int n2) {
        this.acquireReference();
        try {
            boolean bl = CursorWindow.nativePutLong(this.mWindowPtr, l, n - this.mStartPos, n2);
            return bl;
        }
        finally {
            this.releaseReference();
        }
    }

    public boolean putNull(int n, int n2) {
        this.acquireReference();
        try {
            boolean bl = CursorWindow.nativePutNull(this.mWindowPtr, n - this.mStartPos, n2);
            return bl;
        }
        finally {
            this.releaseReference();
        }
    }

    public boolean putString(String string2, int n, int n2) {
        this.acquireReference();
        try {
            boolean bl = CursorWindow.nativePutString(this.mWindowPtr, string2, n - this.mStartPos, n2);
            return bl;
        }
        finally {
            this.releaseReference();
        }
    }

    public boolean setNumColumns(int n) {
        this.acquireReference();
        try {
            boolean bl = CursorWindow.nativeSetNumColumns(this.mWindowPtr, n);
            return bl;
        }
        finally {
            this.releaseReference();
        }
    }

    public void setStartPosition(int n) {
        this.mStartPos = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getName());
        stringBuilder.append(" {");
        stringBuilder.append(Long.toHexString(this.mWindowPtr));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        block3 : {
            this.acquireReference();
            parcel.writeInt(this.mStartPos);
            CursorWindow.nativeWriteToParcel(this.mWindowPtr, parcel);
            if ((n & 1) == 0) break block3;
            this.releaseReference();
        }
        return;
        finally {
            this.releaseReference();
        }
    }

}

