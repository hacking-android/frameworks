/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  dalvik.annotation.optimization.FastNative
 *  dalvik.system.VMRuntime
 *  libcore.util.ArrayUtils
 *  libcore.util.SneakyThrow
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.NetworkOnMainThreadException;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.StrictMode;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.ExceptionUtils;
import android.util.Log;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.VMRuntime;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import libcore.util.ArrayUtils;
import libcore.util.SneakyThrow;

public final class Parcel {
    private static final boolean DEBUG_ARRAY_MAP = false;
    private static final boolean DEBUG_RECYCLE = false;
    private static final int EX_BAD_PARCELABLE = -2;
    private static final int EX_HAS_REPLY_HEADER = -128;
    private static final int EX_ILLEGAL_ARGUMENT = -3;
    private static final int EX_ILLEGAL_STATE = -5;
    private static final int EX_NETWORK_MAIN_THREAD = -6;
    private static final int EX_NULL_POINTER = -4;
    private static final int EX_PARCELABLE = -9;
    private static final int EX_SECURITY = -1;
    private static final int EX_SERVICE_SPECIFIC = -8;
    private static final int EX_TRANSACTION_FAILED = -129;
    private static final int EX_UNSUPPORTED_OPERATION = -7;
    private static final int POOL_SIZE = 6;
    public static final Parcelable.Creator<String> STRING_CREATOR;
    private static final String TAG = "Parcel";
    private static final int VAL_BOOLEAN = 9;
    private static final int VAL_BOOLEANARRAY = 23;
    private static final int VAL_BUNDLE = 3;
    private static final int VAL_BYTE = 20;
    private static final int VAL_BYTEARRAY = 13;
    private static final int VAL_CHARSEQUENCE = 10;
    private static final int VAL_CHARSEQUENCEARRAY = 24;
    private static final int VAL_DOUBLE = 8;
    private static final int VAL_DOUBLEARRAY = 28;
    private static final int VAL_FLOAT = 7;
    private static final int VAL_IBINDER = 15;
    private static final int VAL_INTARRAY = 18;
    private static final int VAL_INTEGER = 1;
    private static final int VAL_LIST = 11;
    private static final int VAL_LONG = 6;
    private static final int VAL_LONGARRAY = 19;
    private static final int VAL_MAP = 2;
    private static final int VAL_NULL = -1;
    private static final int VAL_OBJECTARRAY = 17;
    private static final int VAL_PARCELABLE = 4;
    private static final int VAL_PARCELABLEARRAY = 16;
    private static final int VAL_PERSISTABLEBUNDLE = 25;
    private static final int VAL_SERIALIZABLE = 21;
    private static final int VAL_SHORT = 5;
    private static final int VAL_SIZE = 26;
    private static final int VAL_SIZEF = 27;
    private static final int VAL_SPARSEARRAY = 12;
    private static final int VAL_SPARSEBOOLEANARRAY = 22;
    private static final int VAL_STRING = 0;
    private static final int VAL_STRINGARRAY = 14;
    private static final int WRITE_EXCEPTION_STACK_TRACE_THRESHOLD_MS = 1000;
    private static final HashMap<ClassLoader, HashMap<String, Parcelable.Creator<?>>> mCreators;
    private static final Parcel[] sHolderPool;
    private static volatile long sLastWriteExceptionStackTrace;
    private static final Parcel[] sOwnedPool;
    private static boolean sParcelExceptionStackTrace;
    private ArrayMap<Class, Object> mClassCookies;
    @UnsupportedAppUsage
    private long mNativePtr;
    private long mNativeSize;
    private boolean mOwnsNativeParcelObject;
    private ReadWriteHelper mReadWriteHelper = ReadWriteHelper.DEFAULT;
    private RuntimeException mStack;

    static {
        sOwnedPool = new Parcel[6];
        sHolderPool = new Parcel[6];
        STRING_CREATOR = new Parcelable.Creator<String>(){

            @Override
            public String createFromParcel(Parcel parcel) {
                return parcel.readString();
            }

            public String[] newArray(int n) {
                return new String[n];
            }
        };
        mCreators = new HashMap();
    }

    private Parcel(long l) {
        this.init(l);
    }

    private Exception createException(int n, String string2) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown exception code: ");
                stringBuilder.append(n);
                stringBuilder.append(" msg ");
                stringBuilder.append(string2);
                return new RuntimeException(stringBuilder.toString());
            }
            case -1: {
                return new SecurityException(string2);
            }
            case -2: {
                return new BadParcelableException(string2);
            }
            case -3: {
                return new IllegalArgumentException(string2);
            }
            case -4: {
                return new NullPointerException(string2);
            }
            case -5: {
                return new IllegalStateException(string2);
            }
            case -6: {
                return new NetworkOnMainThreadException();
            }
            case -7: {
                return new UnsupportedOperationException(string2);
            }
            case -8: {
                return new ServiceSpecificException(this.readInt(), string2);
            }
            case -9: 
        }
        if (this.readInt() > 0) {
            return (Exception)this.readParcelable(Parcelable.class.getClassLoader());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" [missing Parcelable]");
        return new RuntimeException(stringBuilder.toString());
    }

    private void destroy() {
        long l = this.mNativePtr;
        if (l != 0L) {
            if (this.mOwnsNativeParcelObject) {
                Parcel.nativeDestroy(l);
                this.updateNativeSize(0L);
            }
            this.mNativePtr = 0L;
        }
        this.mReadWriteHelper = null;
    }

    private void freeBuffer() {
        if (this.mOwnsNativeParcelObject) {
            this.updateNativeSize(Parcel.nativeFreeBuffer(this.mNativePtr));
        }
        this.mReadWriteHelper = ReadWriteHelper.DEFAULT;
    }

    @UnsupportedAppUsage
    public static native long getGlobalAllocCount();

    @UnsupportedAppUsage
    public static native long getGlobalAllocSize();

    private void init(long l) {
        if (l != 0L) {
            this.mNativePtr = l;
            this.mOwnsNativeParcelObject = false;
        } else {
            this.mNativePtr = Parcel.nativeCreate();
            this.mOwnsNativeParcelObject = true;
        }
    }

    private static native long nativeAppendFrom(long var0, long var2, int var4, int var5);

    private static native int nativeCompareData(long var0, long var2);

    private static native long nativeCreate();

    private static native byte[] nativeCreateByteArray(long var0);

    @CriticalNative
    private static native int nativeDataAvail(long var0);

    @CriticalNative
    private static native int nativeDataCapacity(long var0);

    @CriticalNative
    private static native int nativeDataPosition(long var0);

    @CriticalNative
    private static native int nativeDataSize(long var0);

    private static native void nativeDestroy(long var0);

    private static native void nativeEnforceInterface(long var0, String var2);

    private static native long nativeFreeBuffer(long var0);

    @CriticalNative
    private static native long nativeGetBlobAshmemSize(long var0);

    @CriticalNative
    private static native boolean nativeHasFileDescriptors(long var0);

    private static native byte[] nativeMarshall(long var0);

    @CriticalNative
    private static native boolean nativePushAllowFds(long var0, boolean var2);

    private static native byte[] nativeReadBlob(long var0);

    private static native boolean nativeReadByteArray(long var0, byte[] var2, int var3);

    @CriticalNative
    private static native int nativeReadCallingWorkSourceUid(long var0);

    @CriticalNative
    private static native double nativeReadDouble(long var0);

    private static native FileDescriptor nativeReadFileDescriptor(long var0);

    @CriticalNative
    private static native float nativeReadFloat(long var0);

    @CriticalNative
    private static native int nativeReadInt(long var0);

    @CriticalNative
    private static native long nativeReadLong(long var0);

    static native String nativeReadString(long var0);

    private static native IBinder nativeReadStrongBinder(long var0);

    @CriticalNative
    private static native boolean nativeReplaceCallingWorkSourceUid(long var0, int var2);

    @CriticalNative
    private static native void nativeRestoreAllowFds(long var0, boolean var2);

    @FastNative
    private static native void nativeSetDataCapacity(long var0, int var2);

    @CriticalNative
    private static native void nativeSetDataPosition(long var0, int var2);

    @FastNative
    private static native long nativeSetDataSize(long var0, int var2);

    private static native long nativeUnmarshall(long var0, byte[] var2, int var3, int var4);

    private static native void nativeWriteBlob(long var0, byte[] var2, int var3, int var4);

    private static native void nativeWriteByteArray(long var0, byte[] var2, int var3, int var4);

    @FastNative
    private static native void nativeWriteDouble(long var0, double var2);

    private static native long nativeWriteFileDescriptor(long var0, FileDescriptor var2);

    @FastNative
    private static native void nativeWriteFloat(long var0, float var2);

    @FastNative
    private static native void nativeWriteInt(long var0, int var2);

    private static native void nativeWriteInterfaceToken(long var0, String var2);

    @FastNative
    private static native void nativeWriteLong(long var0, long var2);

    static native void nativeWriteString(long var0, String var2);

    private static native void nativeWriteStrongBinder(long var0, IBinder var2);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Parcel obtain() {
        Parcel[] arrparcel = sOwnedPool;
        synchronized (arrparcel) {
            int n = 0;
            while (n < 6) {
                Parcel parcel = arrparcel[n];
                if (parcel != null) {
                    arrparcel[n] = null;
                    parcel.mReadWriteHelper = ReadWriteHelper.DEFAULT;
                    return parcel;
                }
                ++n;
            }
            return new Parcel(0L);
        }
    }

    protected static final Parcel obtain(int n) {
        throw new UnsupportedOperationException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static final Parcel obtain(long l) {
        Parcel[] arrparcel = sHolderPool;
        synchronized (arrparcel) {
            int n = 0;
            while (n < 6) {
                Parcel parcel = arrparcel[n];
                if (parcel != null) {
                    arrparcel[n] = null;
                    parcel.init(l);
                    return parcel;
                }
                ++n;
            }
            return new Parcel(l);
        }
    }

    private void readArrayInternal(Object[] arrobject, int n, ClassLoader classLoader) {
        for (int i = 0; i < n; ++i) {
            arrobject[i] = this.readValue(classLoader);
        }
    }

    private void readListInternal(List list, int n, ClassLoader classLoader) {
        while (n > 0) {
            list.add(this.readValue(classLoader));
            --n;
        }
    }

    private final Serializable readSerializable(ClassLoader object) {
        String string2 = this.readString();
        if (string2 == null) {
            return null;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.createByteArray());
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream, (ClassLoader)object){
                final /* synthetic */ ClassLoader val$loader;
                {
                    this.val$loader = classLoader;
                    super(inputStream);
                }

                @Override
                protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                    Class<?> class_;
                    if (this.val$loader != null && (class_ = Class.forName(objectStreamClass.getName(), false, this.val$loader)) != null) {
                        return class_;
                    }
                    return super.resolveClass(objectStreamClass);
                }
            };
            object = (Serializable)objectInputStream.readObject();
            return object;
        }
        catch (ClassNotFoundException classNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Parcelable encountered ClassNotFoundException reading a Serializable object (name = ");
            stringBuilder.append(string2);
            stringBuilder.append(")");
            throw new RuntimeException(stringBuilder.toString(), classNotFoundException);
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Parcelable encountered IOException reading a Serializable object (name = ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(")");
            throw new RuntimeException(((StringBuilder)object).toString(), iOException);
        }
    }

    private void readSparseArrayInternal(SparseArray sparseArray, int n, ClassLoader classLoader) {
        while (n > 0) {
            sparseArray.append(this.readInt(), this.readValue(classLoader));
            --n;
        }
    }

    private void readSparseBooleanArrayInternal(SparseBooleanArray sparseBooleanArray, int n) {
        while (n > 0) {
            int n2 = this.readInt();
            byte by = this.readByte();
            boolean bl = true;
            if (by != 1) {
                bl = false;
            }
            sparseBooleanArray.append(n2, bl);
            --n;
        }
    }

    private void readSparseIntArrayInternal(SparseIntArray sparseIntArray, int n) {
        while (n > 0) {
            sparseIntArray.append(this.readInt(), this.readInt());
            --n;
        }
    }

    public static void setStackTraceParceling(boolean bl) {
        sParcelExceptionStackTrace = bl;
    }

    private void updateNativeSize(long l) {
        if (this.mOwnsNativeParcelObject) {
            long l2 = l;
            if (l > Integer.MAX_VALUE) {
                l2 = Integer.MAX_VALUE;
            }
            if (l2 != (l = this.mNativeSize)) {
                int n = (int)(l2 - l);
                if (n > 0) {
                    VMRuntime.getRuntime().registerNativeAllocation(n);
                } else {
                    VMRuntime.getRuntime().registerNativeFree(-n);
                }
                this.mNativeSize = l2;
            }
        }
    }

    public final void adoptClassCookies(Parcel parcel) {
        this.mClassCookies = parcel.mClassCookies;
    }

    public final void appendFrom(Parcel parcel, int n, int n2) {
        this.updateNativeSize(Parcel.nativeAppendFrom(this.mNativePtr, parcel.mNativePtr, n, n2));
    }

    public final int compareData(Parcel parcel) {
        return Parcel.nativeCompareData(this.mNativePtr, parcel.mNativePtr);
    }

    public Map<Class, Object> copyClassCookies() {
        return new ArrayMap<Class, Object>(this.mClassCookies);
    }

    public final IBinder[] createBinderArray() {
        int n = this.readInt();
        if (n >= 0) {
            IBinder[] arriBinder = new IBinder[n];
            for (int i = 0; i < n; ++i) {
                arriBinder[i] = this.readStrongBinder();
            }
            return arriBinder;
        }
        return null;
    }

    public final ArrayList<IBinder> createBinderArrayList() {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        ArrayList<IBinder> arrayList = new ArrayList<IBinder>(n);
        while (n > 0) {
            arrayList.add(this.readStrongBinder());
            --n;
        }
        return arrayList;
    }

    public final boolean[] createBooleanArray() {
        int n = this.readInt();
        if (n >= 0 && n <= this.dataAvail() >> 2) {
            boolean[] arrbl = new boolean[n];
            for (int i = 0; i < n; ++i) {
                boolean bl = this.readInt() != 0;
                arrbl[i] = bl;
            }
            return arrbl;
        }
        return null;
    }

    public final byte[] createByteArray() {
        return Parcel.nativeCreateByteArray(this.mNativePtr);
    }

    public final char[] createCharArray() {
        int n = this.readInt();
        if (n >= 0 && n <= this.dataAvail() >> 2) {
            char[] arrc = new char[n];
            for (int i = 0; i < n; ++i) {
                arrc[i] = (char)this.readInt();
            }
            return arrc;
        }
        return null;
    }

    public final double[] createDoubleArray() {
        int n = this.readInt();
        if (n >= 0 && n <= this.dataAvail() >> 3) {
            double[] arrd = new double[n];
            for (int i = 0; i < n; ++i) {
                arrd[i] = this.readDouble();
            }
            return arrd;
        }
        return null;
    }

    public final float[] createFloatArray() {
        int n = this.readInt();
        if (n >= 0 && n <= this.dataAvail() >> 2) {
            float[] arrf = new float[n];
            for (int i = 0; i < n; ++i) {
                arrf[i] = this.readFloat();
            }
            return arrf;
        }
        return null;
    }

    public final int[] createIntArray() {
        int n = this.readInt();
        if (n >= 0 && n <= this.dataAvail() >> 2) {
            int[] arrn = new int[n];
            for (int i = 0; i < n; ++i) {
                arrn[i] = this.readInt();
            }
            return arrn;
        }
        return null;
    }

    public final long[] createLongArray() {
        int n = this.readInt();
        if (n >= 0 && n <= this.dataAvail() >> 3) {
            long[] arrl = new long[n];
            for (int i = 0; i < n; ++i) {
                arrl[i] = this.readLong();
            }
            return arrl;
        }
        return null;
    }

    public final FileDescriptor[] createRawFileDescriptorArray() {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        FileDescriptor[] arrfileDescriptor = new FileDescriptor[n];
        for (int i = 0; i < n; ++i) {
            arrfileDescriptor[i] = this.readRawFileDescriptor();
        }
        return arrfileDescriptor;
    }

    public final String[] createStringArray() {
        int n = this.readInt();
        if (n >= 0) {
            String[] arrstring = new String[n];
            for (int i = 0; i < n; ++i) {
                arrstring[i] = this.readString();
            }
            return arrstring;
        }
        return null;
    }

    public final ArrayList<String> createStringArrayList() {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>(n);
        while (n > 0) {
            arrayList.add(this.readString());
            --n;
        }
        return arrayList;
    }

    public final <T> T[] createTypedArray(Parcelable.Creator<T> creator) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        T[] arrT = creator.newArray(n);
        for (int i = 0; i < n; ++i) {
            arrT[i] = this.readTypedObject(creator);
        }
        return arrT;
    }

    public final <T> ArrayList<T> createTypedArrayList(Parcelable.Creator<T> creator) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        ArrayList<T> arrayList = new ArrayList<T>(n);
        while (n > 0) {
            arrayList.add(this.readTypedObject(creator));
            --n;
        }
        return arrayList;
    }

    public final <T extends Parcelable> ArrayMap<String, T> createTypedArrayMap(Parcelable.Creator<T> creator) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        ArrayMap<String, Parcelable> arrayMap = new ArrayMap<String, Parcelable>(n);
        for (int i = 0; i < n; ++i) {
            arrayMap.append(this.readString(), (Parcelable)this.readTypedObject(creator));
        }
        return arrayMap;
    }

    public final <T extends Parcelable> SparseArray<T> createTypedSparseArray(Parcelable.Creator<T> creator) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        SparseArray<Parcelable> sparseArray = new SparseArray<Parcelable>(n);
        for (int i = 0; i < n; ++i) {
            sparseArray.append(this.readInt(), (Parcelable)this.readTypedObject(creator));
        }
        return sparseArray;
    }

    public final int dataAvail() {
        return Parcel.nativeDataAvail(this.mNativePtr);
    }

    public final int dataCapacity() {
        return Parcel.nativeDataCapacity(this.mNativePtr);
    }

    public final int dataPosition() {
        return Parcel.nativeDataPosition(this.mNativePtr);
    }

    public final int dataSize() {
        return Parcel.nativeDataSize(this.mNativePtr);
    }

    public final void enforceInterface(String string2) {
        Parcel.nativeEnforceInterface(this.mNativePtr, string2);
    }

    protected void finalize() throws Throwable {
        this.destroy();
    }

    public long getBlobAshmemSize() {
        return Parcel.nativeGetBlobAshmemSize(this.mNativePtr);
    }

    public final Object getClassCookie(Class object) {
        ArrayMap<Class, Object> arrayMap = this.mClassCookies;
        object = arrayMap != null ? arrayMap.get(object) : null;
        return object;
    }

    public final boolean hasFileDescriptors() {
        return Parcel.nativeHasFileDescriptors(this.mNativePtr);
    }

    public boolean hasReadWriteHelper() {
        ReadWriteHelper readWriteHelper = this.mReadWriteHelper;
        boolean bl = readWriteHelper != null && readWriteHelper != ReadWriteHelper.DEFAULT;
        return bl;
    }

    public final byte[] marshall() {
        return Parcel.nativeMarshall(this.mNativePtr);
    }

    public final boolean pushAllowFds(boolean bl) {
        return Parcel.nativePushAllowFds(this.mNativePtr, bl);
    }

    public void putClassCookies(Map<Class, Object> map) {
        if (map == null) {
            return;
        }
        if (this.mClassCookies == null) {
            this.mClassCookies = new ArrayMap<K, V>();
        }
        this.mClassCookies.putAll(map);
    }

    public final Object[] readArray(ClassLoader classLoader) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        Object[] arrobject = new Object[n];
        this.readArrayInternal(arrobject, n, classLoader);
        return arrobject;
    }

    public final ArrayList readArrayList(ClassLoader classLoader) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        ArrayList<E> arrayList = new ArrayList<E>(n);
        this.readListInternal(arrayList, n, classLoader);
        return arrayList;
    }

    @UnsupportedAppUsage
    public void readArrayMap(ArrayMap arrayMap, ClassLoader classLoader) {
        int n = this.readInt();
        if (n < 0) {
            return;
        }
        this.readArrayMapInternal(arrayMap, n, classLoader);
    }

    void readArrayMapInternal(ArrayMap arrayMap, int n, ClassLoader classLoader) {
        while (n > 0) {
            arrayMap.append(this.readString(), this.readValue(classLoader));
            --n;
        }
        arrayMap.validate();
    }

    void readArrayMapSafelyInternal(ArrayMap arrayMap, int n, ClassLoader classLoader) {
        while (n > 0) {
            arrayMap.put(this.readString(), this.readValue(classLoader));
            --n;
        }
    }

    @UnsupportedAppUsage
    public ArraySet<? extends Object> readArraySet(ClassLoader classLoader) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        ArraySet<Object> arraySet = new ArraySet<Object>(n);
        for (int i = 0; i < n; ++i) {
            arraySet.append(this.readValue(classLoader));
        }
        return arraySet;
    }

    public final void readBinderArray(IBinder[] arriBinder) {
        int n = this.readInt();
        if (n == arriBinder.length) {
            for (int i = 0; i < n; ++i) {
                arriBinder[i] = this.readStrongBinder();
            }
            return;
        }
        throw new RuntimeException("bad array lengths");
    }

    public final void readBinderList(List<IBinder> list) {
        int n;
        int n2 = list.size();
        int n3 = this.readInt();
        int n4 = 0;
        do {
            n = n4;
            if (n4 >= n2) break;
            n = n4;
            if (n4 >= n3) break;
            list.set(n4, this.readStrongBinder());
            ++n4;
        } while (true);
        do {
            if (n >= n3) break;
            list.add(this.readStrongBinder());
        } while (true);
        for (n4 = ++n; n4 < n2; ++n4) {
            list.remove(n3);
        }
    }

    @UnsupportedAppUsage
    public final byte[] readBlob() {
        return Parcel.nativeReadBlob(this.mNativePtr);
    }

    public final boolean readBoolean() {
        boolean bl = this.readInt() != 0;
        return bl;
    }

    public final void readBooleanArray(boolean[] arrbl) {
        int n = this.readInt();
        if (n == arrbl.length) {
            for (int i = 0; i < n; ++i) {
                boolean bl = this.readInt() != 0;
                arrbl[i] = bl;
            }
            return;
        }
        throw new RuntimeException("bad array lengths");
    }

    public final Bundle readBundle() {
        return this.readBundle(null);
    }

    public final Bundle readBundle(ClassLoader classLoader) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        Bundle bundle = new Bundle(this, n);
        if (classLoader != null) {
            bundle.setClassLoader(classLoader);
        }
        return bundle;
    }

    public final byte readByte() {
        return (byte)(this.readInt() & 255);
    }

    public final void readByteArray(byte[] arrby) {
        long l = this.mNativePtr;
        int n = arrby != null ? arrby.length : 0;
        if (Parcel.nativeReadByteArray(l, arrby, n)) {
            return;
        }
        throw new RuntimeException("bad array lengths");
    }

    public int readCallingWorkSourceUid() {
        return Parcel.nativeReadCallingWorkSourceUid(this.mNativePtr);
    }

    public final void readCharArray(char[] arrc) {
        int n = this.readInt();
        if (n == arrc.length) {
            for (int i = 0; i < n; ++i) {
                arrc[i] = (char)this.readInt();
            }
            return;
        }
        throw new RuntimeException("bad array lengths");
    }

    @UnsupportedAppUsage
    public final CharSequence readCharSequence() {
        return TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(this);
    }

    public final CharSequence[] readCharSequenceArray() {
        CharSequence[] arrcharSequence = null;
        int n = this.readInt();
        if (n >= 0) {
            CharSequence[] arrcharSequence2 = new CharSequence[n];
            int n2 = 0;
            do {
                arrcharSequence = arrcharSequence2;
                if (n2 >= n) break;
                arrcharSequence2[n2] = this.readCharSequence();
                ++n2;
            } while (true);
        }
        return arrcharSequence;
    }

    public final ArrayList<CharSequence> readCharSequenceList() {
        ArrayList<CharSequence> arrayList = null;
        int n = this.readInt();
        if (n >= 0) {
            ArrayList<CharSequence> arrayList2 = new ArrayList<CharSequence>(n);
            int n2 = 0;
            do {
                arrayList = arrayList2;
                if (n2 >= n) break;
                arrayList2.add(this.readCharSequence());
                ++n2;
            } while (true);
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    public final <T extends Parcelable> T readCreator(Parcelable.Creator<?> creator, ClassLoader classLoader) {
        if (creator instanceof Parcelable.ClassLoaderCreator) {
            return (T)((Parcelable)((Parcelable.ClassLoaderCreator)creator).createFromParcel(this, classLoader));
        }
        return (T)((Parcelable)creator.createFromParcel(this));
    }

    public final double readDouble() {
        return Parcel.nativeReadDouble(this.mNativePtr);
    }

    public final void readDoubleArray(double[] arrd) {
        int n = this.readInt();
        if (n == arrd.length) {
            for (int i = 0; i < n; ++i) {
                arrd[i] = this.readDouble();
            }
            return;
        }
        throw new RuntimeException("bad array lengths");
    }

    public final void readException() {
        int n = this.readExceptionCode();
        if (n != 0) {
            this.readException(n, this.readString());
        }
    }

    public final void readException(int n, String object) {
        block5 : {
            Object object2 = null;
            if (this.readInt() > 0) {
                object2 = this.readString();
            }
            object = this.createException(n, (String)object);
            if (object2 != null) {
                Serializable serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Remote stack trace:\n");
                ((StringBuilder)serializable).append((String)object2);
                object2 = new RemoteException(((StringBuilder)serializable).toString(), null, false, false);
                serializable = ExceptionUtils.getRootCause((Throwable)object);
                if (serializable == null) break block5;
                try {
                    ((Throwable)serializable).initCause((Throwable)object2);
                }
                catch (RuntimeException runtimeException) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Cannot set cause ");
                    ((StringBuilder)serializable).append(object2);
                    ((StringBuilder)serializable).append(" for ");
                    ((StringBuilder)serializable).append(object);
                    Log.e("Parcel", ((StringBuilder)serializable).toString(), runtimeException);
                }
            }
        }
        SneakyThrow.sneakyThrow((Throwable)object);
    }

    @UnsupportedAppUsage
    public final int readExceptionCode() {
        int n = this.readInt();
        if (n == -128) {
            if (this.readInt() == 0) {
                Log.e("Parcel", "Unexpected zero-sized Parcel reply header.");
            } else {
                StrictMode.readAndHandleBinderCallViolations(this);
            }
            return 0;
        }
        return n;
    }

    public final ParcelFileDescriptor readFileDescriptor() {
        Object object = Parcel.nativeReadFileDescriptor(this.mNativePtr);
        object = object != null ? new ParcelFileDescriptor((FileDescriptor)object) : null;
        return object;
    }

    public final float readFloat() {
        return Parcel.nativeReadFloat(this.mNativePtr);
    }

    public final void readFloatArray(float[] arrf) {
        int n = this.readInt();
        if (n == arrf.length) {
            for (int i = 0; i < n; ++i) {
                arrf[i] = this.readFloat();
            }
            return;
        }
        throw new RuntimeException("bad array lengths");
    }

    public final HashMap readHashMap(ClassLoader classLoader) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        HashMap<K, V> hashMap = new HashMap<K, V>(n);
        this.readMapInternal(hashMap, n, classLoader);
        return hashMap;
    }

    public final int readInt() {
        return Parcel.nativeReadInt(this.mNativePtr);
    }

    public final void readIntArray(int[] arrn) {
        int n = this.readInt();
        if (n == arrn.length) {
            for (int i = 0; i < n; ++i) {
                arrn[i] = this.readInt();
            }
            return;
        }
        throw new RuntimeException("bad array lengths");
    }

    public final void readList(List list, ClassLoader classLoader) {
        this.readListInternal(list, this.readInt(), classLoader);
    }

    public final long readLong() {
        return Parcel.nativeReadLong(this.mNativePtr);
    }

    public final void readLongArray(long[] arrl) {
        int n = this.readInt();
        if (n == arrl.length) {
            for (int i = 0; i < n; ++i) {
                arrl[i] = this.readLong();
            }
            return;
        }
        throw new RuntimeException("bad array lengths");
    }

    public final void readMap(Map map, ClassLoader classLoader) {
        this.readMapInternal(map, this.readInt(), classLoader);
    }

    void readMapInternal(Map map, int n, ClassLoader classLoader) {
        while (n > 0) {
            map.put(this.readValue(classLoader), this.readValue(classLoader));
            --n;
        }
    }

    public final <T extends Parcelable> T readParcelable(ClassLoader classLoader) {
        Parcelable.Creator<?> creator = this.readParcelableCreator(classLoader);
        if (creator == null) {
            return null;
        }
        if (creator instanceof Parcelable.ClassLoaderCreator) {
            return (T)((Parcelable)((Parcelable.ClassLoaderCreator)creator).createFromParcel(this, classLoader));
        }
        return (T)((Parcelable)creator.createFromParcel(this));
    }

    public final Parcelable[] readParcelableArray(ClassLoader classLoader) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        Parcelable[] arrparcelable = new Parcelable[n];
        for (int i = 0; i < n; ++i) {
            arrparcelable[i] = this.readParcelable(classLoader);
        }
        return arrparcelable;
    }

    public final <T extends Parcelable> T[] readParcelableArray(ClassLoader classLoader, Class<T> arrparcelable) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        arrparcelable = (Parcelable[])Array.newInstance(arrparcelable, n);
        for (int i = 0; i < n; ++i) {
            arrparcelable[i] = this.readParcelable(classLoader);
        }
        return arrparcelable;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public final Parcelable.Creator<?> readParcelableCreator(ClassLoader var1_1) {
        block16 : {
            block15 : {
                block14 : {
                    block10 : {
                        block11 : {
                            block12 : {
                                block13 : {
                                    var2_4 = this.readString();
                                    if (var2_4 == null) {
                                        return null;
                                    }
                                    var3_5 = Parcel.mCreators;
                                    // MONITORENTER : var3_5
                                    var4_6 = Parcel.mCreators.get(var1_1);
                                    var5_7 = var4_6;
                                    if (var4_6 == null) {
                                        var5_7 = new HashMap();
                                        Parcel.mCreators.put((ClassLoader)var1_1, (HashMap<String, Parcelable.Creator<?>>)var5_7);
                                    }
                                    var6_12 = (Parcelable.Creator)var5_7.get(var2_4);
                                    var4_6 = var6_12;
                                    if (var6_12 != null) {
                                        // MONITOREXIT : var3_5
                                        return var4_6;
                                    }
                                    if (var1_1 != null) ** GOTO lbl20
                                    var1_1 = this.getClass().getClassLoader();
lbl20: // 2 sources:
                                    if (!Parcelable.class.isAssignableFrom((Class<?>)(var1_1 = Class.forName(var2_4, false, (ClassLoader)var1_1)))) break block10;
                                    if (((var1_1 = var1_1.getField("CREATOR")).getModifiers() & 8) == 0) break block11;
                                    if (!Parcelable.Creator.class.isAssignableFrom(var1_1.getType())) break block12;
                                    var4_6 = (Parcelable.Creator)var1_1.get(null);
                                    if (var4_6 == null) break block13;
                                    var5_7.put(var2_4, var4_6);
                                    return var4_6;
                                }
                                var1_1 = new StringBuilder();
                                var1_1.append("Parcelable protocol requires a non-null Parcelable.Creator object called CREATOR on class ");
                                var1_1.append(var2_4);
                                var5_7 = new BadParcelableException(var1_1.toString());
                                throw var5_7;
                            }
                            try {
                                var5_7 = new StringBuilder();
                                var5_7.append("Parcelable protocol requires a Parcelable.Creator object called CREATOR on class ");
                                var5_7.append(var2_4);
                                var1_1 = new BadParcelableException(var5_7.toString());
                                throw var1_1;
                            }
                            catch (NoSuchFieldException var1_2) {
                                break block14;
                            }
                            catch (ClassNotFoundException var5_8) {
                                break block15;
                            }
                            catch (IllegalAccessException var5_9) {
                                break block16;
                            }
                        }
                        var1_1 = new StringBuilder();
                        var1_1.append("Parcelable protocol requires the CREATOR object to be static on class ");
                        var1_1.append(var2_4);
                        var5_7 = new BadParcelableException(var1_1.toString());
                        throw var5_7;
                    }
                    var1_1 = new StringBuilder();
                    var1_1.append("Parcelable protocol requires subclassing from Parcelable on class ");
                    var1_1.append(var2_4);
                    var5_7 = new BadParcelableException(var1_1.toString());
                    throw var5_7;
                }
                var1_3 = new StringBuilder();
                var1_3.append("Parcelable protocol requires a Parcelable.Creator object called CREATOR on class ");
                var1_3.append(var2_4);
                var5_7 = new BadParcelableException(var1_3.toString());
                throw var5_7;
            }
            var1_1 = new StringBuilder();
            var1_1.append("Class not found when unmarshalling: ");
            var1_1.append(var2_4);
            Log.e("Parcel", var1_1.toString(), var5_8);
            var5_10 = new StringBuilder();
            var5_10.append("ClassNotFoundException when unmarshalling: ");
            var5_10.append(var2_4);
            var1_1 = new BadParcelableException(var5_10.toString());
            throw var1_1;
        }
        var1_1 = new StringBuilder();
        var1_1.append("Illegal access when unmarshalling: ");
        var1_1.append(var2_4);
        Log.e("Parcel", var1_1.toString(), var5_9);
        var1_1 = new StringBuilder();
        var1_1.append("IllegalAccessException when unmarshalling: ");
        var1_1.append(var2_4);
        var5_11 = new BadParcelableException(var1_1.toString());
        throw var5_11;
    }

    public final <T extends Parcelable> List<T> readParcelableList(List<T> list, ClassLoader classLoader) {
        int n;
        int n2 = this.readInt();
        if (n2 == -1) {
            list.clear();
            return list;
        }
        int n3 = list.size();
        int n4 = 0;
        do {
            n = n4;
            if (n4 >= n3) break;
            n = n4;
            if (n4 >= n2) break;
            list.set(n4, this.readParcelable(classLoader));
            ++n4;
        } while (true);
        do {
            if (n >= n2) break;
            list.add(this.readParcelable(classLoader));
        } while (true);
        for (n4 = ++n; n4 < n3; ++n4) {
            list.remove(n2);
        }
        return list;
    }

    public final PersistableBundle readPersistableBundle() {
        return this.readPersistableBundle(null);
    }

    public final PersistableBundle readPersistableBundle(ClassLoader classLoader) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        PersistableBundle persistableBundle = new PersistableBundle(this, n);
        if (classLoader != null) {
            persistableBundle.setClassLoader(classLoader);
        }
        return persistableBundle;
    }

    @UnsupportedAppUsage
    public final FileDescriptor readRawFileDescriptor() {
        return Parcel.nativeReadFileDescriptor(this.mNativePtr);
    }

    public final void readRawFileDescriptorArray(FileDescriptor[] arrfileDescriptor) {
        int n = this.readInt();
        if (n == arrfileDescriptor.length) {
            for (int i = 0; i < n; ++i) {
                arrfileDescriptor[i] = this.readRawFileDescriptor();
            }
            return;
        }
        throw new RuntimeException("bad array lengths");
    }

    public final Serializable readSerializable() {
        return this.readSerializable(null);
    }

    public final Size readSize() {
        return new Size(this.readInt(), this.readInt());
    }

    public final SizeF readSizeF() {
        return new SizeF(this.readFloat(), this.readFloat());
    }

    public final <T> SparseArray<T> readSparseArray(ClassLoader classLoader) {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        SparseArray<E> sparseArray = new SparseArray<E>(n);
        this.readSparseArrayInternal(sparseArray, n, classLoader);
        return sparseArray;
    }

    public final SparseBooleanArray readSparseBooleanArray() {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray(n);
        this.readSparseBooleanArrayInternal(sparseBooleanArray, n);
        return sparseBooleanArray;
    }

    public final SparseIntArray readSparseIntArray() {
        int n = this.readInt();
        if (n < 0) {
            return null;
        }
        SparseIntArray sparseIntArray = new SparseIntArray(n);
        this.readSparseIntArrayInternal(sparseIntArray, n);
        return sparseIntArray;
    }

    public final String readString() {
        return this.mReadWriteHelper.readString(this);
    }

    public final void readStringArray(String[] arrstring) {
        int n = this.readInt();
        if (n == arrstring.length) {
            for (int i = 0; i < n; ++i) {
                arrstring[i] = this.readString();
            }
            return;
        }
        throw new RuntimeException("bad array lengths");
    }

    @UnsupportedAppUsage
    public final String[] readStringArray() {
        String[] arrstring = null;
        int n = this.readInt();
        if (n >= 0) {
            String[] arrstring2 = new String[n];
            int n2 = 0;
            do {
                arrstring = arrstring2;
                if (n2 >= n) break;
                arrstring2[n2] = this.readString();
                ++n2;
            } while (true);
        }
        return arrstring;
    }

    public final void readStringList(List<String> list) {
        int n;
        int n2 = list.size();
        int n3 = this.readInt();
        int n4 = 0;
        do {
            n = n4;
            if (n4 >= n2) break;
            n = n4;
            if (n4 >= n3) break;
            list.set(n4, this.readString());
            ++n4;
        } while (true);
        do {
            if (n >= n3) break;
            list.add(this.readString());
        } while (true);
        for (n4 = ++n; n4 < n2; ++n4) {
            list.remove(n3);
        }
    }

    public String readStringNoHelper() {
        return Parcel.nativeReadString(this.mNativePtr);
    }

    public final IBinder readStrongBinder() {
        return Parcel.nativeReadStrongBinder(this.mNativePtr);
    }

    public final <T> void readTypedArray(T[] arrT, Parcelable.Creator<T> creator) {
        int n = this.readInt();
        if (n == arrT.length) {
            for (int i = 0; i < n; ++i) {
                arrT[i] = this.readTypedObject(creator);
            }
            return;
        }
        throw new RuntimeException("bad array lengths");
    }

    @Deprecated
    public final <T> T[] readTypedArray(Parcelable.Creator<T> creator) {
        return this.createTypedArray(creator);
    }

    public final <T> void readTypedList(List<T> list, Parcelable.Creator<T> creator) {
        int n;
        int n2 = list.size();
        int n3 = this.readInt();
        int n4 = 0;
        do {
            n = n4;
            if (n4 >= n2) break;
            n = n4;
            if (n4 >= n3) break;
            list.set(n4, this.readTypedObject(creator));
            ++n4;
        } while (true);
        do {
            if (n >= n3) break;
            list.add(this.readTypedObject(creator));
        } while (true);
        for (n4 = ++n; n4 < n2; ++n4) {
            list.remove(n3);
        }
    }

    public final <T> T readTypedObject(Parcelable.Creator<T> creator) {
        if (this.readInt() != 0) {
            return creator.createFromParcel(this);
        }
        return null;
    }

    public final Object readValue(ClassLoader object) {
        int n = this.readInt();
        switch (n) {
            default: {
                int n2 = this.dataPosition();
                object = new StringBuilder();
                ((StringBuilder)object).append("Parcel ");
                ((StringBuilder)object).append(this);
                ((StringBuilder)object).append(": Unmarshalling unknown type code ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" at offset ");
                ((StringBuilder)object).append(n2 - 4);
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            case 28: {
                return this.createDoubleArray();
            }
            case 27: {
                return this.readSizeF();
            }
            case 26: {
                return this.readSize();
            }
            case 25: {
                return this.readPersistableBundle((ClassLoader)object);
            }
            case 24: {
                return this.readCharSequenceArray();
            }
            case 23: {
                return this.createBooleanArray();
            }
            case 22: {
                return this.readSparseBooleanArray();
            }
            case 21: {
                return this.readSerializable((ClassLoader)object);
            }
            case 20: {
                return this.readByte();
            }
            case 19: {
                return this.createLongArray();
            }
            case 18: {
                return this.createIntArray();
            }
            case 17: {
                return this.readArray((ClassLoader)object);
            }
            case 16: {
                return this.readParcelableArray((ClassLoader)object);
            }
            case 15: {
                return this.readStrongBinder();
            }
            case 14: {
                return this.readStringArray();
            }
            case 13: {
                return this.createByteArray();
            }
            case 12: {
                return this.readSparseArray((ClassLoader)object);
            }
            case 11: {
                return this.readArrayList((ClassLoader)object);
            }
            case 10: {
                return this.readCharSequence();
            }
            case 9: {
                int n3 = this.readInt();
                boolean bl = true;
                if (n3 != 1) {
                    bl = false;
                }
                return bl;
            }
            case 8: {
                return this.readDouble();
            }
            case 7: {
                return Float.valueOf(this.readFloat());
            }
            case 6: {
                return this.readLong();
            }
            case 5: {
                return (short)this.readInt();
            }
            case 4: {
                return this.readParcelable((ClassLoader)object);
            }
            case 3: {
                return this.readBundle((ClassLoader)object);
            }
            case 2: {
                return this.readHashMap((ClassLoader)object);
            }
            case 1: {
                return this.readInt();
            }
            case 0: {
                return this.readString();
            }
            case -1: 
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void recycle() {
        Parcel[] arrparcel;
        this.freeBuffer();
        if (this.mOwnsNativeParcelObject) {
            arrparcel = sOwnedPool;
        } else {
            this.mNativePtr = 0L;
            arrparcel = sHolderPool;
        }
        synchronized (arrparcel) {
            int n = 0;
            while (n < 6) {
                if (arrparcel[n] == null) {
                    arrparcel[n] = this;
                    return;
                }
                ++n;
            }
            return;
        }
    }

    public boolean replaceCallingWorkSourceUid(int n) {
        return Parcel.nativeReplaceCallingWorkSourceUid(this.mNativePtr, n);
    }

    public final void restoreAllowFds(boolean bl) {
        Parcel.nativeRestoreAllowFds(this.mNativePtr, bl);
    }

    public final void setClassCookie(Class class_, Object object) {
        if (this.mClassCookies == null) {
            this.mClassCookies = new ArrayMap<K, V>();
        }
        this.mClassCookies.put(class_, object);
    }

    public final void setDataCapacity(int n) {
        Parcel.nativeSetDataCapacity(this.mNativePtr, n);
    }

    public final void setDataPosition(int n) {
        Parcel.nativeSetDataPosition(this.mNativePtr, n);
    }

    public final void setDataSize(int n) {
        this.updateNativeSize(Parcel.nativeSetDataSize(this.mNativePtr, n));
    }

    public void setReadWriteHelper(ReadWriteHelper readWriteHelper) {
        if (readWriteHelper == null) {
            readWriteHelper = ReadWriteHelper.DEFAULT;
        }
        this.mReadWriteHelper = readWriteHelper;
    }

    public final void unmarshall(byte[] arrby, int n, int n2) {
        this.updateNativeSize(Parcel.nativeUnmarshall(this.mNativePtr, arrby, n, n2));
    }

    public final void writeArray(Object[] arrobject) {
        if (arrobject == null) {
            this.writeInt(-1);
            return;
        }
        int n = arrobject.length;
        this.writeInt(n);
        for (int i = 0; i < n; ++i) {
            this.writeValue(arrobject[i]);
        }
    }

    @UnsupportedAppUsage
    public void writeArrayMap(ArrayMap<String, Object> arrayMap) {
        this.writeArrayMapInternal(arrayMap);
    }

    void writeArrayMapInternal(ArrayMap<String, Object> arrayMap) {
        if (arrayMap == null) {
            this.writeInt(-1);
            return;
        }
        int n = arrayMap.size();
        this.writeInt(n);
        for (int i = 0; i < n; ++i) {
            this.writeString(arrayMap.keyAt(i));
            this.writeValue(arrayMap.valueAt(i));
        }
    }

    @UnsupportedAppUsage
    public void writeArraySet(ArraySet<? extends Object> arraySet) {
        int n = arraySet != null ? arraySet.size() : -1;
        this.writeInt(n);
        for (int i = 0; i < n; ++i) {
            this.writeValue(arraySet.valueAt(i));
        }
    }

    public final void writeBinderArray(IBinder[] arriBinder) {
        if (arriBinder != null) {
            int n = arriBinder.length;
            this.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeStrongBinder(arriBinder[i]);
            }
        } else {
            this.writeInt(-1);
        }
    }

    public final void writeBinderList(List<IBinder> list) {
        if (list == null) {
            this.writeInt(-1);
            return;
        }
        int n = list.size();
        this.writeInt(n);
        for (int i = 0; i < n; ++i) {
            this.writeStrongBinder(list.get(i));
        }
    }

    @UnsupportedAppUsage
    public final void writeBlob(byte[] arrby) {
        int n = arrby != null ? arrby.length : 0;
        this.writeBlob(arrby, 0, n);
    }

    public final void writeBlob(byte[] arrby, int n, int n2) {
        if (arrby == null) {
            this.writeInt(-1);
            return;
        }
        ArrayUtils.throwsIfOutOfBounds((int)arrby.length, (int)n, (int)n2);
        Parcel.nativeWriteBlob(this.mNativePtr, arrby, n, n2);
    }

    public final void writeBoolean(boolean bl) {
        this.writeInt((int)bl);
    }

    public final void writeBooleanArray(boolean[] arrbl) {
        if (arrbl != null) {
            int n = arrbl.length;
            this.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeInt(arrbl[i]);
            }
        } else {
            this.writeInt(-1);
        }
    }

    public final void writeBundle(Bundle bundle) {
        if (bundle == null) {
            this.writeInt(-1);
            return;
        }
        bundle.writeToParcel(this, 0);
    }

    public final void writeByte(byte by) {
        this.writeInt(by);
    }

    public final void writeByteArray(byte[] arrby) {
        int n = arrby != null ? arrby.length : 0;
        this.writeByteArray(arrby, 0, n);
    }

    public final void writeByteArray(byte[] arrby, int n, int n2) {
        if (arrby == null) {
            this.writeInt(-1);
            return;
        }
        ArrayUtils.throwsIfOutOfBounds((int)arrby.length, (int)n, (int)n2);
        Parcel.nativeWriteByteArray(this.mNativePtr, arrby, n, n2);
    }

    public final void writeCharArray(char[] arrc) {
        if (arrc != null) {
            int n = arrc.length;
            this.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeInt(arrc[i]);
            }
        } else {
            this.writeInt(-1);
        }
    }

    @UnsupportedAppUsage
    public final void writeCharSequence(CharSequence charSequence) {
        TextUtils.writeToParcel(charSequence, this, 0);
    }

    public final void writeCharSequenceArray(CharSequence[] arrcharSequence) {
        if (arrcharSequence != null) {
            int n = arrcharSequence.length;
            this.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeCharSequence(arrcharSequence[i]);
            }
        } else {
            this.writeInt(-1);
        }
    }

    public final void writeCharSequenceList(ArrayList<CharSequence> arrayList) {
        if (arrayList != null) {
            int n = arrayList.size();
            this.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeCharSequence(arrayList.get(i));
            }
        } else {
            this.writeInt(-1);
        }
    }

    public final void writeDouble(double d) {
        Parcel.nativeWriteDouble(this.mNativePtr, d);
    }

    public final void writeDoubleArray(double[] arrd) {
        if (arrd != null) {
            int n = arrd.length;
            this.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeDouble(arrd[i]);
            }
        } else {
            this.writeInt(-1);
        }
    }

    public final void writeException(Exception exception) {
        int n;
        int n2 = 0;
        if (exception instanceof Parcelable && exception.getClass().getClassLoader() == Parcelable.class.getClassLoader()) {
            n2 = -9;
        } else if (exception instanceof SecurityException) {
            n2 = -1;
        } else if (exception instanceof BadParcelableException) {
            n2 = -2;
        } else if (exception instanceof IllegalArgumentException) {
            n2 = -3;
        } else if (exception instanceof NullPointerException) {
            n2 = -4;
        } else if (exception instanceof IllegalStateException) {
            n2 = -5;
        } else if (exception instanceof NetworkOnMainThreadException) {
            n2 = -6;
        } else if (exception instanceof UnsupportedOperationException) {
            n2 = -7;
        } else if (exception instanceof ServiceSpecificException) {
            n2 = -8;
        }
        this.writeInt(n2);
        StrictMode.clearGatheredViolations();
        if (n2 == 0) {
            if (exception instanceof RuntimeException) {
                throw (RuntimeException)exception;
            }
            throw new RuntimeException(exception);
        }
        this.writeString(exception.getMessage());
        long l = sParcelExceptionStackTrace ? SystemClock.elapsedRealtime() : 0L;
        if (sParcelExceptionStackTrace && l - sLastWriteExceptionStackTrace > 1000L) {
            sLastWriteExceptionStackTrace = l;
            int n3 = this.dataPosition();
            this.writeInt(0);
            StackTraceElement[] arrstackTraceElement = exception.getStackTrace();
            int n4 = Math.min(arrstackTraceElement.length, 5);
            StringBuilder stringBuilder = new StringBuilder();
            for (n = 0; n < n4; ++n) {
                stringBuilder.append("\tat ");
                stringBuilder.append(arrstackTraceElement[n]);
                stringBuilder.append('\n');
            }
            this.writeString(stringBuilder.toString());
            n = this.dataPosition();
            this.setDataPosition(n3);
            this.writeInt(n - n3);
            this.setDataPosition(n);
        } else {
            this.writeInt(0);
        }
        if (n2 != -9) {
            if (n2 == -8) {
                this.writeInt(((ServiceSpecificException)exception).errorCode);
            }
        } else {
            n = this.dataPosition();
            this.writeInt(0);
            this.writeParcelable((Parcelable)((Object)exception), 1);
            n2 = this.dataPosition();
            this.setDataPosition(n);
            this.writeInt(n2 - n);
            this.setDataPosition(n2);
        }
    }

    public final void writeFileDescriptor(FileDescriptor fileDescriptor) {
        this.updateNativeSize(Parcel.nativeWriteFileDescriptor(this.mNativePtr, fileDescriptor));
    }

    public final void writeFloat(float f) {
        Parcel.nativeWriteFloat(this.mNativePtr, f);
    }

    public final void writeFloatArray(float[] arrf) {
        if (arrf != null) {
            int n = arrf.length;
            this.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeFloat(arrf[i]);
            }
        } else {
            this.writeInt(-1);
        }
    }

    public final void writeInt(int n) {
        Parcel.nativeWriteInt(this.mNativePtr, n);
    }

    public final void writeIntArray(int[] arrn) {
        if (arrn != null) {
            int n = arrn.length;
            this.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeInt(arrn[i]);
            }
        } else {
            this.writeInt(-1);
        }
    }

    public final void writeInterfaceToken(String string2) {
        Parcel.nativeWriteInterfaceToken(this.mNativePtr, string2);
    }

    public final void writeList(List list) {
        if (list == null) {
            this.writeInt(-1);
            return;
        }
        int n = list.size();
        this.writeInt(n);
        for (int i = 0; i < n; ++i) {
            this.writeValue(list.get(i));
        }
    }

    public final void writeLong(long l) {
        Parcel.nativeWriteLong(this.mNativePtr, l);
    }

    public final void writeLongArray(long[] arrl) {
        if (arrl != null) {
            int n = arrl.length;
            this.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeLong(arrl[i]);
            }
        } else {
            this.writeInt(-1);
        }
    }

    public final void writeMap(Map map) {
        this.writeMapInternal(map);
    }

    void writeMapInternal(Map<String, Object> iterator) {
        if (iterator == null) {
            this.writeInt(-1);
            return;
        }
        iterator = iterator.entrySet();
        int n = iterator.size();
        this.writeInt(n);
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            this.writeValue(entry.getKey());
            this.writeValue(entry.getValue());
            --n;
        }
        if (n == 0) {
            return;
        }
        throw new BadParcelableException("Map size does not match number of entries!");
    }

    public final void writeNoException() {
        if (StrictMode.hasGatheredViolations()) {
            this.writeInt(-128);
            int n = this.dataPosition();
            this.writeInt(0);
            StrictMode.writeGatheredViolationsToParcel(this);
            int n2 = this.dataPosition();
            this.setDataPosition(n);
            this.writeInt(n2 - n);
            this.setDataPosition(n2);
        } else {
            this.writeInt(0);
        }
    }

    public final void writeParcelable(Parcelable parcelable, int n) {
        if (parcelable == null) {
            this.writeString(null);
            return;
        }
        this.writeParcelableCreator(parcelable);
        parcelable.writeToParcel(this, n);
    }

    public final <T extends Parcelable> void writeParcelableArray(T[] arrT, int n) {
        if (arrT != null) {
            int n2 = arrT.length;
            this.writeInt(n2);
            for (int i = 0; i < n2; ++i) {
                this.writeParcelable((Parcelable)arrT[i], n);
            }
        } else {
            this.writeInt(-1);
        }
    }

    @UnsupportedAppUsage
    public final void writeParcelableCreator(Parcelable parcelable) {
        this.writeString(parcelable.getClass().getName());
    }

    public final <T extends Parcelable> void writeParcelableList(List<T> list, int n) {
        if (list == null) {
            this.writeInt(-1);
            return;
        }
        int n2 = list.size();
        this.writeInt(n2);
        for (int i = 0; i < n2; ++i) {
            this.writeParcelable((Parcelable)list.get(i), n);
        }
    }

    public final void writePersistableBundle(PersistableBundle persistableBundle) {
        if (persistableBundle == null) {
            this.writeInt(-1);
            return;
        }
        persistableBundle.writeToParcel(this, 0);
    }

    public final void writeRawFileDescriptor(FileDescriptor fileDescriptor) {
        Parcel.nativeWriteFileDescriptor(this.mNativePtr, fileDescriptor);
    }

    public final void writeRawFileDescriptorArray(FileDescriptor[] arrfileDescriptor) {
        if (arrfileDescriptor != null) {
            int n = arrfileDescriptor.length;
            this.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeRawFileDescriptor(arrfileDescriptor[i]);
            }
        } else {
            this.writeInt(-1);
        }
    }

    public final void writeSerializable(Serializable serializable) {
        if (serializable == null) {
            this.writeString(null);
            return;
        }
        String string2 = serializable.getClass().getName();
        this.writeString(string2);
        Object object = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream((OutputStream)object);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.close();
            this.writeByteArray(((ByteArrayOutputStream)object).toByteArray());
            return;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Parcelable encountered IOException writing serializable object (name = ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(")");
            throw new RuntimeException(((StringBuilder)object).toString(), iOException);
        }
    }

    public final void writeSize(Size size) {
        this.writeInt(size.getWidth());
        this.writeInt(size.getHeight());
    }

    public final void writeSizeF(SizeF sizeF) {
        this.writeFloat(sizeF.getWidth());
        this.writeFloat(sizeF.getHeight());
    }

    public final <T> void writeSparseArray(SparseArray<T> sparseArray) {
        if (sparseArray == null) {
            this.writeInt(-1);
            return;
        }
        int n = sparseArray.size();
        this.writeInt(n);
        for (int i = 0; i < n; ++i) {
            this.writeInt(sparseArray.keyAt(i));
            this.writeValue(sparseArray.valueAt(i));
        }
    }

    public final void writeSparseBooleanArray(SparseBooleanArray sparseBooleanArray) {
        if (sparseBooleanArray == null) {
            this.writeInt(-1);
            return;
        }
        int n = sparseBooleanArray.size();
        this.writeInt(n);
        for (int i = 0; i < n; ++i) {
            this.writeInt(sparseBooleanArray.keyAt(i));
            this.writeByte((byte)(sparseBooleanArray.valueAt(i) ? 1 : 0));
        }
    }

    public final void writeSparseIntArray(SparseIntArray sparseIntArray) {
        if (sparseIntArray == null) {
            this.writeInt(-1);
            return;
        }
        int n = sparseIntArray.size();
        this.writeInt(n);
        for (int i = 0; i < n; ++i) {
            this.writeInt(sparseIntArray.keyAt(i));
            this.writeInt(sparseIntArray.valueAt(i));
        }
    }

    public final void writeString(String string2) {
        this.mReadWriteHelper.writeString(this, string2);
    }

    public final void writeStringArray(String[] arrstring) {
        if (arrstring != null) {
            int n = arrstring.length;
            this.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeString(arrstring[i]);
            }
        } else {
            this.writeInt(-1);
        }
    }

    public final void writeStringList(List<String> list) {
        if (list == null) {
            this.writeInt(-1);
            return;
        }
        int n = list.size();
        this.writeInt(n);
        for (int i = 0; i < n; ++i) {
            this.writeString(list.get(i));
        }
    }

    public void writeStringNoHelper(String string2) {
        Parcel.nativeWriteString(this.mNativePtr, string2);
    }

    public final void writeStrongBinder(IBinder iBinder) {
        Parcel.nativeWriteStrongBinder(this.mNativePtr, iBinder);
    }

    public final void writeStrongInterface(IInterface object) {
        object = object == null ? null : object.asBinder();
        this.writeStrongBinder((IBinder)object);
    }

    public final <T extends Parcelable> void writeTypedArray(T[] arrT, int n) {
        if (arrT != null) {
            int n2 = arrT.length;
            this.writeInt(n2);
            for (int i = 0; i < n2; ++i) {
                this.writeTypedObject(arrT[i], n);
            }
        } else {
            this.writeInt(-1);
        }
    }

    public <T extends Parcelable> void writeTypedArrayMap(ArrayMap<String, T> arrayMap, int n) {
        if (arrayMap == null) {
            this.writeInt(-1);
            return;
        }
        int n2 = arrayMap.size();
        this.writeInt(n2);
        for (int i = 0; i < n2; ++i) {
            this.writeString(arrayMap.keyAt(i));
            this.writeTypedObject((Parcelable)arrayMap.valueAt(i), n);
        }
    }

    public final <T extends Parcelable> void writeTypedList(List<T> list) {
        this.writeTypedList(list, 0);
    }

    public <T extends Parcelable> void writeTypedList(List<T> list, int n) {
        if (list == null) {
            this.writeInt(-1);
            return;
        }
        int n2 = list.size();
        this.writeInt(n2);
        for (int i = 0; i < n2; ++i) {
            this.writeTypedObject((Parcelable)list.get(i), n);
        }
    }

    public final <T extends Parcelable> void writeTypedObject(T t, int n) {
        if (t != null) {
            this.writeInt(1);
            t.writeToParcel(this, n);
        } else {
            this.writeInt(0);
        }
    }

    public final <T extends Parcelable> void writeTypedSparseArray(SparseArray<T> sparseArray, int n) {
        if (sparseArray == null) {
            this.writeInt(-1);
            return;
        }
        int n2 = sparseArray.size();
        this.writeInt(n2);
        for (int i = 0; i < n2; ++i) {
            this.writeInt(sparseArray.keyAt(i));
            this.writeTypedObject((Parcelable)sparseArray.valueAt(i), n);
        }
    }

    public final void writeValue(Object object) {
        Serializable serializable;
        block31 : {
            block3 : {
                block30 : {
                    block29 : {
                        block28 : {
                            block27 : {
                                block26 : {
                                    block25 : {
                                        block24 : {
                                            block23 : {
                                                block22 : {
                                                    block21 : {
                                                        block20 : {
                                                            block19 : {
                                                                block18 : {
                                                                    block17 : {
                                                                        block16 : {
                                                                            block15 : {
                                                                                block14 : {
                                                                                    block13 : {
                                                                                        block12 : {
                                                                                            block11 : {
                                                                                                block10 : {
                                                                                                    block9 : {
                                                                                                        block8 : {
                                                                                                            block7 : {
                                                                                                                block6 : {
                                                                                                                    block5 : {
                                                                                                                        block4 : {
                                                                                                                            block2 : {
                                                                                                                                if (object != null) break block2;
                                                                                                                                this.writeInt(-1);
                                                                                                                                break block3;
                                                                                                                            }
                                                                                                                            if (!(object instanceof String)) break block4;
                                                                                                                            this.writeInt(0);
                                                                                                                            this.writeString((String)object);
                                                                                                                            break block3;
                                                                                                                        }
                                                                                                                        if (!(object instanceof Integer)) break block5;
                                                                                                                        this.writeInt(1);
                                                                                                                        this.writeInt((Integer)object);
                                                                                                                        break block3;
                                                                                                                    }
                                                                                                                    if (!(object instanceof Map)) break block6;
                                                                                                                    this.writeInt(2);
                                                                                                                    this.writeMap((Map)object);
                                                                                                                    break block3;
                                                                                                                }
                                                                                                                if (!(object instanceof Bundle)) break block7;
                                                                                                                this.writeInt(3);
                                                                                                                this.writeBundle((Bundle)object);
                                                                                                                break block3;
                                                                                                            }
                                                                                                            if (!(object instanceof PersistableBundle)) break block8;
                                                                                                            this.writeInt(25);
                                                                                                            this.writePersistableBundle((PersistableBundle)object);
                                                                                                            break block3;
                                                                                                        }
                                                                                                        if (!(object instanceof Parcelable)) break block9;
                                                                                                        this.writeInt(4);
                                                                                                        this.writeParcelable((Parcelable)object, 0);
                                                                                                        break block3;
                                                                                                    }
                                                                                                    if (!(object instanceof Short)) break block10;
                                                                                                    this.writeInt(5);
                                                                                                    this.writeInt(((Short)object).intValue());
                                                                                                    break block3;
                                                                                                }
                                                                                                if (!(object instanceof Long)) break block11;
                                                                                                this.writeInt(6);
                                                                                                this.writeLong((Long)object);
                                                                                                break block3;
                                                                                            }
                                                                                            if (!(object instanceof Float)) break block12;
                                                                                            this.writeInt(7);
                                                                                            this.writeFloat(((Float)object).floatValue());
                                                                                            break block3;
                                                                                        }
                                                                                        if (!(object instanceof Double)) break block13;
                                                                                        this.writeInt(8);
                                                                                        this.writeDouble((Double)object);
                                                                                        break block3;
                                                                                    }
                                                                                    if (!(object instanceof Boolean)) break block14;
                                                                                    this.writeInt(9);
                                                                                    this.writeInt((int)((Boolean)object).booleanValue());
                                                                                    break block3;
                                                                                }
                                                                                if (!(object instanceof CharSequence)) break block15;
                                                                                this.writeInt(10);
                                                                                this.writeCharSequence((CharSequence)object);
                                                                                break block3;
                                                                            }
                                                                            if (!(object instanceof List)) break block16;
                                                                            this.writeInt(11);
                                                                            this.writeList((List)object);
                                                                            break block3;
                                                                        }
                                                                        if (!(object instanceof SparseArray)) break block17;
                                                                        this.writeInt(12);
                                                                        this.writeSparseArray((SparseArray)object);
                                                                        break block3;
                                                                    }
                                                                    if (!(object instanceof boolean[])) break block18;
                                                                    this.writeInt(23);
                                                                    this.writeBooleanArray((boolean[])object);
                                                                    break block3;
                                                                }
                                                                if (!(object instanceof byte[])) break block19;
                                                                this.writeInt(13);
                                                                this.writeByteArray((byte[])object);
                                                                break block3;
                                                            }
                                                            if (!(object instanceof String[])) break block20;
                                                            this.writeInt(14);
                                                            this.writeStringArray((String[])object);
                                                            break block3;
                                                        }
                                                        if (!(object instanceof CharSequence[])) break block21;
                                                        this.writeInt(24);
                                                        this.writeCharSequenceArray((CharSequence[])object);
                                                        break block3;
                                                    }
                                                    if (!(object instanceof IBinder)) break block22;
                                                    this.writeInt(15);
                                                    this.writeStrongBinder((IBinder)object);
                                                    break block3;
                                                }
                                                if (!(object instanceof Parcelable[])) break block23;
                                                this.writeInt(16);
                                                this.writeParcelableArray((Parcelable[])object, 0);
                                                break block3;
                                            }
                                            if (!(object instanceof int[])) break block24;
                                            this.writeInt(18);
                                            this.writeIntArray((int[])object);
                                            break block3;
                                        }
                                        if (!(object instanceof long[])) break block25;
                                        this.writeInt(19);
                                        this.writeLongArray((long[])object);
                                        break block3;
                                    }
                                    if (!(object instanceof Byte)) break block26;
                                    this.writeInt(20);
                                    this.writeInt(((Byte)object).byteValue());
                                    break block3;
                                }
                                if (!(object instanceof Size)) break block27;
                                this.writeInt(26);
                                this.writeSize((Size)object);
                                break block3;
                            }
                            if (!(object instanceof SizeF)) break block28;
                            this.writeInt(27);
                            this.writeSizeF((SizeF)object);
                            break block3;
                        }
                        if (!(object instanceof double[])) break block29;
                        this.writeInt(28);
                        this.writeDoubleArray((double[])object);
                        break block3;
                    }
                    serializable = object.getClass();
                    if (!((Class)serializable).isArray() || ((Class)serializable).getComponentType() != Object.class) break block30;
                    this.writeInt(17);
                    this.writeArray((Object[])object);
                    break block3;
                }
                if (!(object instanceof Serializable)) break block31;
                this.writeInt(21);
                this.writeSerializable((Serializable)object);
            }
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Parcel: unable to marshal value ");
        ((StringBuilder)serializable).append(object);
        throw new RuntimeException(((StringBuilder)serializable).toString());
    }

    public static class ReadWriteHelper {
        public static final ReadWriteHelper DEFAULT = new ReadWriteHelper();

        public String readString(Parcel parcel) {
            return Parcel.nativeReadString(parcel.mNativePtr);
        }

        public void writeString(Parcel parcel, String string2) {
            Parcel.nativeWriteString(parcel.mNativePtr, string2);
        }
    }

}

