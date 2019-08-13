/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.NativeAllocationRegistry
 */
package android.os;

import android.annotation.SystemApi;
import android.os.NativeHandle;
import libcore.util.NativeAllocationRegistry;

@SystemApi
public class HwBlob {
    private static final String TAG = "HwBlob";
    private static final NativeAllocationRegistry sNativeRegistry;
    private long mNativeContext;

    static {
        long l = HwBlob.native_init();
        sNativeRegistry = new NativeAllocationRegistry(HwBlob.class.getClassLoader(), l, 128L);
    }

    public HwBlob(int n) {
        this.native_setup(n);
        sNativeRegistry.registerNativeAllocation((Object)this, this.mNativeContext);
    }

    private static final native long native_init();

    private final native void native_setup(int var1);

    public static Boolean[] wrapArray(boolean[] arrbl) {
        int n = arrbl.length;
        Boolean[] arrboolean = new Boolean[n];
        for (int i = 0; i < n; ++i) {
            arrboolean[i] = arrbl[i];
        }
        return arrboolean;
    }

    public static Byte[] wrapArray(byte[] arrby) {
        int n = arrby.length;
        Byte[] arrbyte = new Byte[n];
        for (int i = 0; i < n; ++i) {
            arrbyte[i] = arrby[i];
        }
        return arrbyte;
    }

    public static Double[] wrapArray(double[] arrd) {
        int n = arrd.length;
        Double[] arrdouble = new Double[n];
        for (int i = 0; i < n; ++i) {
            arrdouble[i] = arrd[i];
        }
        return arrdouble;
    }

    public static Float[] wrapArray(float[] arrf) {
        int n = arrf.length;
        Float[] arrfloat = new Float[n];
        for (int i = 0; i < n; ++i) {
            arrfloat[i] = Float.valueOf(arrf[i]);
        }
        return arrfloat;
    }

    public static Integer[] wrapArray(int[] arrn) {
        int n = arrn.length;
        Integer[] arrinteger = new Integer[n];
        for (int i = 0; i < n; ++i) {
            arrinteger[i] = arrn[i];
        }
        return arrinteger;
    }

    public static Long[] wrapArray(long[] arrl) {
        int n = arrl.length;
        Long[] arrlong = new Long[n];
        for (int i = 0; i < n; ++i) {
            arrlong[i] = arrl[i];
        }
        return arrlong;
    }

    public static Short[] wrapArray(short[] arrs) {
        int n = arrs.length;
        Short[] arrshort = new Short[n];
        for (int i = 0; i < n; ++i) {
            arrshort[i] = arrs[i];
        }
        return arrshort;
    }

    public final native void copyToBoolArray(long var1, boolean[] var3, int var4);

    public final native void copyToDoubleArray(long var1, double[] var3, int var4);

    public final native void copyToFloatArray(long var1, float[] var3, int var4);

    public final native void copyToInt16Array(long var1, short[] var3, int var4);

    public final native void copyToInt32Array(long var1, int[] var3, int var4);

    public final native void copyToInt64Array(long var1, long[] var3, int var4);

    public final native void copyToInt8Array(long var1, byte[] var3, int var4);

    public final native boolean getBool(long var1);

    public final native double getDouble(long var1);

    public final native float getFloat(long var1);

    public final native short getInt16(long var1);

    public final native int getInt32(long var1);

    public final native long getInt64(long var1);

    public final native byte getInt8(long var1);

    public final native String getString(long var1);

    public final native long handle();

    public final native void putBlob(long var1, HwBlob var3);

    public final native void putBool(long var1, boolean var3);

    public final native void putBoolArray(long var1, boolean[] var3);

    public final native void putDouble(long var1, double var3);

    public final native void putDoubleArray(long var1, double[] var3);

    public final native void putFloat(long var1, float var3);

    public final native void putFloatArray(long var1, float[] var3);

    public final native void putInt16(long var1, short var3);

    public final native void putInt16Array(long var1, short[] var3);

    public final native void putInt32(long var1, int var3);

    public final native void putInt32Array(long var1, int[] var3);

    public final native void putInt64(long var1, long var3);

    public final native void putInt64Array(long var1, long[] var3);

    public final native void putInt8(long var1, byte var3);

    public final native void putInt8Array(long var1, byte[] var3);

    public final native void putNativeHandle(long var1, NativeHandle var3);

    public final native void putString(long var1, String var3);
}

