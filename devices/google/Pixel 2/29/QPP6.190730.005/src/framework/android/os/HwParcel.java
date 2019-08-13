/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.NativeAllocationRegistry
 */
package android.os;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.HwBlob;
import android.os.IHwBinder;
import android.os.NativeHandle;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import libcore.util.NativeAllocationRegistry;

@SystemApi
public class HwParcel {
    public static final int STATUS_SUCCESS = 0;
    private static final String TAG = "HwParcel";
    private static final NativeAllocationRegistry sNativeRegistry;
    private long mNativeContext;

    static {
        long l = HwParcel.native_init();
        sNativeRegistry = new NativeAllocationRegistry(HwParcel.class.getClassLoader(), l, 128L);
    }

    public HwParcel() {
        this.native_setup(true);
        sNativeRegistry.registerNativeAllocation((Object)this, this.mNativeContext);
    }

    @UnsupportedAppUsage
    private HwParcel(boolean bl) {
        this.native_setup(bl);
        sNativeRegistry.registerNativeAllocation((Object)this, this.mNativeContext);
    }

    private static final native long native_init();

    private final native void native_setup(boolean var1);

    private final native boolean[] readBoolVectorAsArray();

    private final native double[] readDoubleVectorAsArray();

    private final native float[] readFloatVectorAsArray();

    private final native short[] readInt16VectorAsArray();

    private final native int[] readInt32VectorAsArray();

    private final native long[] readInt64VectorAsArray();

    private final native byte[] readInt8VectorAsArray();

    private final native NativeHandle[] readNativeHandleAsArray();

    private final native String[] readStringVectorAsArray();

    private final native void writeBoolVector(boolean[] var1);

    private final native void writeDoubleVector(double[] var1);

    private final native void writeFloatVector(float[] var1);

    private final native void writeInt16Vector(short[] var1);

    private final native void writeInt32Vector(int[] var1);

    private final native void writeInt64Vector(long[] var1);

    private final native void writeInt8Vector(byte[] var1);

    private final native void writeNativeHandleVector(NativeHandle[] var1);

    private final native void writeStringVector(String[] var1);

    public final native void enforceInterface(String var1);

    public final native boolean readBool();

    public final ArrayList<Boolean> readBoolVector() {
        return new ArrayList<Boolean>(Arrays.asList(HwBlob.wrapArray(this.readBoolVectorAsArray())));
    }

    public final native HwBlob readBuffer(long var1);

    public final native double readDouble();

    public final ArrayList<Double> readDoubleVector() {
        return new ArrayList<Double>(Arrays.asList(HwBlob.wrapArray(this.readDoubleVectorAsArray())));
    }

    public final native HwBlob readEmbeddedBuffer(long var1, long var3, long var5, boolean var7);

    public final native NativeHandle readEmbeddedNativeHandle(long var1, long var3);

    public final native float readFloat();

    public final ArrayList<Float> readFloatVector() {
        return new ArrayList<Float>(Arrays.asList(HwBlob.wrapArray(this.readFloatVectorAsArray())));
    }

    public final native short readInt16();

    public final ArrayList<Short> readInt16Vector() {
        return new ArrayList<Short>(Arrays.asList(HwBlob.wrapArray(this.readInt16VectorAsArray())));
    }

    public final native int readInt32();

    public final ArrayList<Integer> readInt32Vector() {
        return new ArrayList<Integer>(Arrays.asList(HwBlob.wrapArray(this.readInt32VectorAsArray())));
    }

    public final native long readInt64();

    public final ArrayList<Long> readInt64Vector() {
        return new ArrayList<Long>(Arrays.asList(HwBlob.wrapArray(this.readInt64VectorAsArray())));
    }

    public final native byte readInt8();

    public final ArrayList<Byte> readInt8Vector() {
        return new ArrayList<Byte>(Arrays.asList(HwBlob.wrapArray(this.readInt8VectorAsArray())));
    }

    public final native NativeHandle readNativeHandle();

    public final ArrayList<NativeHandle> readNativeHandleVector() {
        return new ArrayList<NativeHandle>(Arrays.asList(this.readNativeHandleAsArray()));
    }

    public final native String readString();

    public final ArrayList<String> readStringVector() {
        return new ArrayList<String>(Arrays.asList(this.readStringVectorAsArray()));
    }

    public final native IHwBinder readStrongBinder();

    public final native void release();

    public final native void releaseTemporaryStorage();

    public final native void send();

    public final native void verifySuccess();

    public final native void writeBool(boolean var1);

    public final void writeBoolVector(ArrayList<Boolean> arrayList) {
        int n = arrayList.size();
        boolean[] arrbl = new boolean[n];
        for (int i = 0; i < n; ++i) {
            arrbl[i] = arrayList.get(i);
        }
        this.writeBoolVector(arrbl);
    }

    public final native void writeBuffer(HwBlob var1);

    public final native void writeDouble(double var1);

    public final void writeDoubleVector(ArrayList<Double> arrayList) {
        int n = arrayList.size();
        double[] arrd = new double[n];
        for (int i = 0; i < n; ++i) {
            arrd[i] = arrayList.get(i);
        }
        this.writeDoubleVector(arrd);
    }

    public final native void writeFloat(float var1);

    public final void writeFloatVector(ArrayList<Float> arrayList) {
        int n = arrayList.size();
        float[] arrf = new float[n];
        for (int i = 0; i < n; ++i) {
            arrf[i] = arrayList.get(i).floatValue();
        }
        this.writeFloatVector(arrf);
    }

    public final native void writeInt16(short var1);

    public final void writeInt16Vector(ArrayList<Short> arrayList) {
        int n = arrayList.size();
        short[] arrs = new short[n];
        for (int i = 0; i < n; ++i) {
            arrs[i] = arrayList.get(i);
        }
        this.writeInt16Vector(arrs);
    }

    public final native void writeInt32(int var1);

    public final void writeInt32Vector(ArrayList<Integer> arrayList) {
        int n = arrayList.size();
        int[] arrn = new int[n];
        for (int i = 0; i < n; ++i) {
            arrn[i] = arrayList.get(i);
        }
        this.writeInt32Vector(arrn);
    }

    public final native void writeInt64(long var1);

    public final void writeInt64Vector(ArrayList<Long> arrayList) {
        int n = arrayList.size();
        long[] arrl = new long[n];
        for (int i = 0; i < n; ++i) {
            arrl[i] = arrayList.get(i);
        }
        this.writeInt64Vector(arrl);
    }

    public final native void writeInt8(byte var1);

    public final void writeInt8Vector(ArrayList<Byte> arrayList) {
        int n = arrayList.size();
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby[i] = arrayList.get(i);
        }
        this.writeInt8Vector(arrby);
    }

    public final native void writeInterfaceToken(String var1);

    public final native void writeNativeHandle(NativeHandle var1);

    public final void writeNativeHandleVector(ArrayList<NativeHandle> arrayList) {
        this.writeNativeHandleVector(arrayList.toArray(new NativeHandle[arrayList.size()]));
    }

    public final native void writeStatus(int var1);

    public final native void writeString(String var1);

    public final void writeStringVector(ArrayList<String> arrayList) {
        this.writeStringVector(arrayList.toArray(new String[arrayList.size()]));
    }

    public final native void writeStrongBinder(IHwBinder var1);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Status {
    }

}

