/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.renderscript.BaseObj;
import android.renderscript.Byte2;
import android.renderscript.Byte3;
import android.renderscript.Byte4;
import android.renderscript.Double2;
import android.renderscript.Double3;
import android.renderscript.Double4;
import android.renderscript.Float2;
import android.renderscript.Float3;
import android.renderscript.Float4;
import android.renderscript.Int2;
import android.renderscript.Int3;
import android.renderscript.Int4;
import android.renderscript.Long2;
import android.renderscript.Long3;
import android.renderscript.Long4;
import android.renderscript.Matrix2f;
import android.renderscript.Matrix3f;
import android.renderscript.Matrix4f;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.Short2;
import android.renderscript.Short3;
import android.renderscript.Short4;
import android.util.Log;
import java.util.BitSet;

public class FieldPacker {
    private BitSet mAlignment;
    private byte[] mData;
    private int mLen;
    private int mPos;

    public FieldPacker(int n) {
        this.mPos = 0;
        this.mLen = n;
        this.mData = new byte[n];
        this.mAlignment = new BitSet();
    }

    public FieldPacker(byte[] arrby) {
        this.mPos = arrby.length;
        this.mLen = arrby.length;
        this.mData = arrby;
        this.mAlignment = new BitSet();
    }

    private void add(Object object) {
        if (object instanceof Boolean) {
            this.addBoolean((Boolean)object);
            return;
        }
        if (object instanceof Byte) {
            this.addI8((Byte)object);
            return;
        }
        if (object instanceof Short) {
            this.addI16((Short)object);
            return;
        }
        if (object instanceof Integer) {
            this.addI32((Integer)object);
            return;
        }
        if (object instanceof Long) {
            this.addI64((Long)object);
            return;
        }
        if (object instanceof Float) {
            this.addF32(((Float)object).floatValue());
            return;
        }
        if (object instanceof Double) {
            this.addF64((Double)object);
            return;
        }
        if (object instanceof Byte2) {
            this.addI8((Byte2)object);
            return;
        }
        if (object instanceof Byte3) {
            this.addI8((Byte3)object);
            return;
        }
        if (object instanceof Byte4) {
            this.addI8((Byte4)object);
            return;
        }
        if (object instanceof Short2) {
            this.addI16((Short2)object);
            return;
        }
        if (object instanceof Short3) {
            this.addI16((Short3)object);
            return;
        }
        if (object instanceof Short4) {
            this.addI16((Short4)object);
            return;
        }
        if (object instanceof Int2) {
            this.addI32((Int2)object);
            return;
        }
        if (object instanceof Int3) {
            this.addI32((Int3)object);
            return;
        }
        if (object instanceof Int4) {
            this.addI32((Int4)object);
            return;
        }
        if (object instanceof Long2) {
            this.addI64((Long2)object);
            return;
        }
        if (object instanceof Long3) {
            this.addI64((Long3)object);
            return;
        }
        if (object instanceof Long4) {
            this.addI64((Long4)object);
            return;
        }
        if (object instanceof Float2) {
            this.addF32((Float2)object);
            return;
        }
        if (object instanceof Float3) {
            this.addF32((Float3)object);
            return;
        }
        if (object instanceof Float4) {
            this.addF32((Float4)object);
            return;
        }
        if (object instanceof Double2) {
            this.addF64((Double2)object);
            return;
        }
        if (object instanceof Double3) {
            this.addF64((Double3)object);
            return;
        }
        if (object instanceof Double4) {
            this.addF64((Double4)object);
            return;
        }
        if (object instanceof Matrix2f) {
            this.addMatrix((Matrix2f)object);
            return;
        }
        if (object instanceof Matrix3f) {
            this.addMatrix((Matrix3f)object);
            return;
        }
        if (object instanceof Matrix4f) {
            this.addMatrix((Matrix4f)object);
            return;
        }
        if (object instanceof BaseObj) {
            this.addObj((BaseObj)object);
            return;
        }
    }

    private void addSafely(Object object) {
        boolean bl;
        int n = this.mPos;
        do {
            bl = false;
            try {
                this.add(object);
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                this.mPos = n;
                this.resize(this.mLen * 2);
                bl = true;
            }
        } while (bl);
    }

    static FieldPacker createFromArray(Object[] arrobject) {
        FieldPacker fieldPacker = new FieldPacker(RenderScript.sPointerSize * 8);
        int n = arrobject.length;
        for (int i = 0; i < n; ++i) {
            fieldPacker.addSafely(arrobject[i]);
        }
        fieldPacker.resize(fieldPacker.mPos);
        return fieldPacker;
    }

    private boolean resize(int n) {
        if (n == this.mLen) {
            return false;
        }
        byte[] arrby = new byte[n];
        System.arraycopy(this.mData, 0, arrby, 0, this.mPos);
        this.mData = arrby;
        this.mLen = n;
        return true;
    }

    public void addBoolean(boolean bl) {
        this.addI8((byte)(bl ? 1 : 0));
    }

    public void addF32(float f) {
        this.addI32(Float.floatToRawIntBits(f));
    }

    public void addF32(Float2 float2) {
        this.addF32(float2.x);
        this.addF32(float2.y);
    }

    public void addF32(Float3 float3) {
        this.addF32(float3.x);
        this.addF32(float3.y);
        this.addF32(float3.z);
    }

    public void addF32(Float4 float4) {
        this.addF32(float4.x);
        this.addF32(float4.y);
        this.addF32(float4.z);
        this.addF32(float4.w);
    }

    public void addF64(double d) {
        this.addI64(Double.doubleToRawLongBits(d));
    }

    public void addF64(Double2 double2) {
        this.addF64(double2.x);
        this.addF64(double2.y);
    }

    public void addF64(Double3 double3) {
        this.addF64(double3.x);
        this.addF64(double3.y);
        this.addF64(double3.z);
    }

    public void addF64(Double4 double4) {
        this.addF64(double4.x);
        this.addF64(double4.y);
        this.addF64(double4.z);
        this.addF64(double4.w);
    }

    public void addI16(Short2 short2) {
        this.addI16(short2.x);
        this.addI16(short2.y);
    }

    public void addI16(Short3 short3) {
        this.addI16(short3.x);
        this.addI16(short3.y);
        this.addI16(short3.z);
    }

    public void addI16(Short4 short4) {
        this.addI16(short4.x);
        this.addI16(short4.y);
        this.addI16(short4.z);
        this.addI16(short4.w);
    }

    public void addI16(short s) {
        this.align(2);
        byte[] arrby = this.mData;
        int n = this.mPos;
        this.mPos = n + 1;
        arrby[n] = (byte)(s & 255);
        n = this.mPos;
        this.mPos = n + 1;
        arrby[n] = (byte)(s >> 8);
    }

    public void addI32(int n) {
        this.align(4);
        byte[] arrby = this.mData;
        int n2 = this.mPos;
        this.mPos = n2 + 1;
        arrby[n2] = (byte)(n & 255);
        n2 = this.mPos;
        this.mPos = n2 + 1;
        arrby[n2] = (byte)(n >> 8 & 255);
        n2 = this.mPos;
        this.mPos = n2 + 1;
        arrby[n2] = (byte)(n >> 16 & 255);
        n2 = this.mPos;
        this.mPos = n2 + 1;
        arrby[n2] = (byte)(n >> 24 & 255);
    }

    public void addI32(Int2 int2) {
        this.addI32(int2.x);
        this.addI32(int2.y);
    }

    public void addI32(Int3 int3) {
        this.addI32(int3.x);
        this.addI32(int3.y);
        this.addI32(int3.z);
    }

    public void addI32(Int4 int4) {
        this.addI32(int4.x);
        this.addI32(int4.y);
        this.addI32(int4.z);
        this.addI32(int4.w);
    }

    public void addI64(long l) {
        this.align(8);
        byte[] arrby = this.mData;
        int n = this.mPos;
        this.mPos = n + 1;
        arrby[n] = (byte)(l & 255L);
        n = this.mPos;
        this.mPos = n + 1;
        arrby[n] = (byte)(l >> 8 & 255L);
        n = this.mPos;
        this.mPos = n + 1;
        arrby[n] = (byte)(l >> 16 & 255L);
        n = this.mPos;
        this.mPos = n + 1;
        arrby[n] = (byte)(l >> 24 & 255L);
        n = this.mPos;
        this.mPos = n + 1;
        arrby[n] = (byte)(l >> 32 & 255L);
        n = this.mPos;
        this.mPos = n + 1;
        arrby[n] = (byte)(l >> 40 & 255L);
        n = this.mPos;
        this.mPos = n + 1;
        arrby[n] = (byte)(l >> 48 & 255L);
        n = this.mPos;
        this.mPos = n + 1;
        arrby[n] = (byte)(l >> 56 & 255L);
    }

    public void addI64(Long2 long2) {
        this.addI64(long2.x);
        this.addI64(long2.y);
    }

    public void addI64(Long3 long3) {
        this.addI64(long3.x);
        this.addI64(long3.y);
        this.addI64(long3.z);
    }

    public void addI64(Long4 long4) {
        this.addI64(long4.x);
        this.addI64(long4.y);
        this.addI64(long4.z);
        this.addI64(long4.w);
    }

    public void addI8(byte by) {
        byte[] arrby = this.mData;
        int n = this.mPos;
        this.mPos = n + 1;
        arrby[n] = by;
    }

    public void addI8(Byte2 byte2) {
        this.addI8(byte2.x);
        this.addI8(byte2.y);
    }

    public void addI8(Byte3 byte3) {
        this.addI8(byte3.x);
        this.addI8(byte3.y);
        this.addI8(byte3.z);
    }

    public void addI8(Byte4 byte4) {
        this.addI8(byte4.x);
        this.addI8(byte4.y);
        this.addI8(byte4.z);
        this.addI8(byte4.w);
    }

    public void addMatrix(Matrix2f matrix2f) {
        for (int i = 0; i < matrix2f.mMat.length; ++i) {
            this.addF32(matrix2f.mMat[i]);
        }
    }

    public void addMatrix(Matrix3f matrix3f) {
        for (int i = 0; i < matrix3f.mMat.length; ++i) {
            this.addF32(matrix3f.mMat[i]);
        }
    }

    public void addMatrix(Matrix4f matrix4f) {
        for (int i = 0; i < matrix4f.mMat.length; ++i) {
            this.addF32(matrix4f.mMat[i]);
        }
    }

    public void addObj(BaseObj baseObj) {
        if (baseObj != null) {
            if (RenderScript.sPointerSize == 8) {
                this.addI64(baseObj.getID(null));
                this.addI64(0L);
                this.addI64(0L);
                this.addI64(0L);
            } else {
                this.addI32((int)baseObj.getID(null));
            }
        } else if (RenderScript.sPointerSize == 8) {
            this.addI64(0L);
            this.addI64(0L);
            this.addI64(0L);
            this.addI64(0L);
        } else {
            this.addI32(0);
        }
    }

    public void addU16(int n) {
        if (n >= 0 && n <= 65535) {
            this.align(2);
            byte[] arrby = this.mData;
            int n2 = this.mPos;
            this.mPos = n2 + 1;
            arrby[n2] = (byte)(n & 255);
            n2 = this.mPos;
            this.mPos = n2 + 1;
            arrby[n2] = (byte)(n >> 8);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FieldPacker.addU16( ");
        stringBuilder.append(n);
        stringBuilder.append(" )");
        Log.e("rs", stringBuilder.toString());
        throw new IllegalArgumentException("Saving value out of range for type");
    }

    public void addU16(Int2 int2) {
        this.addU16(int2.x);
        this.addU16(int2.y);
    }

    public void addU16(Int3 int3) {
        this.addU16(int3.x);
        this.addU16(int3.y);
        this.addU16(int3.z);
    }

    public void addU16(Int4 int4) {
        this.addU16(int4.x);
        this.addU16(int4.y);
        this.addU16(int4.z);
        this.addU16(int4.w);
    }

    public void addU32(long l) {
        if (l >= 0L && l <= 0xFFFFFFFFL) {
            this.align(4);
            byte[] arrby = this.mData;
            int n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)(l & 255L);
            n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)(l >> 8 & 255L);
            n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)(l >> 16 & 255L);
            n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)(255L & l >> 24);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FieldPacker.addU32( ");
        stringBuilder.append(l);
        stringBuilder.append(" )");
        Log.e("rs", stringBuilder.toString());
        throw new IllegalArgumentException("Saving value out of range for type");
    }

    public void addU32(Long2 long2) {
        this.addU32(long2.x);
        this.addU32(long2.y);
    }

    public void addU32(Long3 long3) {
        this.addU32(long3.x);
        this.addU32(long3.y);
        this.addU32(long3.z);
    }

    public void addU32(Long4 long4) {
        this.addU32(long4.x);
        this.addU32(long4.y);
        this.addU32(long4.z);
        this.addU32(long4.w);
    }

    public void addU64(long l) {
        if (l >= 0L) {
            this.align(8);
            byte[] arrby = this.mData;
            int n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)(l & 255L);
            n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)(l >> 8 & 255L);
            n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)(l >> 16 & 255L);
            n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)(l >> 24 & 255L);
            n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)(l >> 32 & 255L);
            n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)(l >> 40 & 255L);
            n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)(l >> 48 & 255L);
            n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)(l >> 56 & 255L);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FieldPacker.addU64( ");
        stringBuilder.append(l);
        stringBuilder.append(" )");
        Log.e("rs", stringBuilder.toString());
        throw new IllegalArgumentException("Saving value out of range for type");
    }

    public void addU64(Long2 long2) {
        this.addU64(long2.x);
        this.addU64(long2.y);
    }

    public void addU64(Long3 long3) {
        this.addU64(long3.x);
        this.addU64(long3.y);
        this.addU64(long3.z);
    }

    public void addU64(Long4 long4) {
        this.addU64(long4.x);
        this.addU64(long4.y);
        this.addU64(long4.z);
        this.addU64(long4.w);
    }

    public void addU8(Short2 short2) {
        this.addU8(short2.x);
        this.addU8(short2.y);
    }

    public void addU8(Short3 short3) {
        this.addU8(short3.x);
        this.addU8(short3.y);
        this.addU8(short3.z);
    }

    public void addU8(Short4 short4) {
        this.addU8(short4.x);
        this.addU8(short4.y);
        this.addU8(short4.z);
        this.addU8(short4.w);
    }

    public void addU8(short s) {
        if (s >= 0 && s <= 255) {
            byte[] arrby = this.mData;
            int n = this.mPos;
            this.mPos = n + 1;
            arrby[n] = (byte)s;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FieldPacker.addU8( ");
        stringBuilder.append(s);
        stringBuilder.append(" )");
        Log.e("rs", stringBuilder.toString());
        throw new IllegalArgumentException("Saving value out of range for type");
    }

    public void align(int n) {
        if (n > 0 && (n - 1 & n) == 0) {
            int n2;
            while ((n - 1 & (n2 = this.mPos)) != 0) {
                this.mAlignment.flip(n2);
                byte[] arrby = this.mData;
                n2 = this.mPos;
                this.mPos = n2 + 1;
                arrby[n2] = (byte)(false ? 1 : 0);
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("argument must be a non-negative non-zero power of 2: ");
        stringBuilder.append(n);
        throw new RSIllegalArgumentException(stringBuilder.toString());
    }

    public final byte[] getData() {
        return this.mData;
    }

    public int getPos() {
        return this.mPos;
    }

    public void reset() {
        this.mPos = 0;
    }

    public void reset(int n) {
        if (n >= 0 && n <= this.mLen) {
            this.mPos = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("out of range argument: ");
        stringBuilder.append(n);
        throw new RSIllegalArgumentException(stringBuilder.toString());
    }

    public void skip(int n) {
        int n2 = this.mPos + n;
        if (n2 >= 0 && n2 <= this.mLen) {
            this.mPos = n2;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("out of range argument: ");
        stringBuilder.append(n);
        throw new RSIllegalArgumentException(stringBuilder.toString());
    }

    public boolean subBoolean() {
        return this.subI8() == 1;
    }

    public Byte2 subByte2() {
        Byte2 byte2 = new Byte2();
        byte2.y = this.subI8();
        byte2.x = this.subI8();
        return byte2;
    }

    public Byte3 subByte3() {
        Byte3 byte3 = new Byte3();
        byte3.z = this.subI8();
        byte3.y = this.subI8();
        byte3.x = this.subI8();
        return byte3;
    }

    public Byte4 subByte4() {
        Byte4 byte4 = new Byte4();
        byte4.w = this.subI8();
        byte4.z = this.subI8();
        byte4.y = this.subI8();
        byte4.x = this.subI8();
        return byte4;
    }

    public Double2 subDouble2() {
        Double2 double2 = new Double2();
        double2.y = this.subF64();
        double2.x = this.subF64();
        return double2;
    }

    public Double3 subDouble3() {
        Double3 double3 = new Double3();
        double3.z = this.subF64();
        double3.y = this.subF64();
        double3.x = this.subF64();
        return double3;
    }

    public Double4 subDouble4() {
        Double4 double4 = new Double4();
        double4.w = this.subF64();
        double4.z = this.subF64();
        double4.y = this.subF64();
        double4.x = this.subF64();
        return double4;
    }

    public float subF32() {
        return Float.intBitsToFloat(this.subI32());
    }

    public double subF64() {
        return Double.longBitsToDouble(this.subI64());
    }

    public Float2 subFloat2() {
        Float2 float2 = new Float2();
        float2.y = this.subF32();
        float2.x = this.subF32();
        return float2;
    }

    public Float3 subFloat3() {
        Float3 float3 = new Float3();
        float3.z = this.subF32();
        float3.y = this.subF32();
        float3.x = this.subF32();
        return float3;
    }

    public Float4 subFloat4() {
        Float4 float4 = new Float4();
        float4.w = this.subF32();
        float4.z = this.subF32();
        float4.y = this.subF32();
        float4.x = this.subF32();
        return float4;
    }

    public short subI16() {
        int n;
        int n2;
        this.subalign(2);
        byte[] arrby = this.mData;
        this.mPos = n = this.mPos - 1;
        n = (short)((arrby[n] & 255) << 8);
        this.mPos = n2 = this.mPos - 1;
        return (short)((short)(arrby[n2] & 255) | n);
    }

    public int subI32() {
        int n;
        int n2;
        int n3;
        this.subalign(4);
        byte[] arrby = this.mData;
        this.mPos = n = this.mPos - 1;
        n = arrby[n];
        this.mPos = n2 = this.mPos - 1;
        n2 = arrby[n2];
        this.mPos = n3 = this.mPos - 1;
        byte by = arrby[n3];
        this.mPos = n3 = this.mPos - 1;
        return (n & 255) << 24 | (n2 & 255) << 16 | (by & 255) << 8 | arrby[n3] & 255;
    }

    public long subI64() {
        int n;
        this.subalign(8);
        byte[] arrby = this.mData;
        this.mPos = n = this.mPos - 1;
        long l = arrby[n];
        this.mPos = n = this.mPos - 1;
        long l2 = arrby[n];
        this.mPos = n = this.mPos - 1;
        long l3 = arrby[n];
        this.mPos = n = this.mPos - 1;
        long l4 = arrby[n];
        this.mPos = n = this.mPos - 1;
        long l5 = arrby[n];
        this.mPos = n = this.mPos - 1;
        long l6 = arrby[n];
        this.mPos = n = this.mPos - 1;
        long l7 = arrby[n];
        this.mPos = n = this.mPos - 1;
        return 0L | (l & 255L) << 56 | (l2 & 255L) << 48 | (l3 & 255L) << 40 | (l4 & 255L) << 32 | (l5 & 255L) << 24 | (l6 & 255L) << 16 | (l7 & 255L) << 8 | (long)arrby[n] & 255L;
    }

    public byte subI8() {
        int n;
        this.subalign(1);
        byte[] arrby = this.mData;
        this.mPos = n = this.mPos - 1;
        return arrby[n];
    }

    public Int2 subInt2() {
        Int2 int2 = new Int2();
        int2.y = this.subI32();
        int2.x = this.subI32();
        return int2;
    }

    public Int3 subInt3() {
        Int3 int3 = new Int3();
        int3.z = this.subI32();
        int3.y = this.subI32();
        int3.x = this.subI32();
        return int3;
    }

    public Int4 subInt4() {
        Int4 int4 = new Int4();
        int4.w = this.subI32();
        int4.z = this.subI32();
        int4.y = this.subI32();
        int4.x = this.subI32();
        return int4;
    }

    public Long2 subLong2() {
        Long2 long2 = new Long2();
        long2.y = this.subI64();
        long2.x = this.subI64();
        return long2;
    }

    public Long3 subLong3() {
        Long3 long3 = new Long3();
        long3.z = this.subI64();
        long3.y = this.subI64();
        long3.x = this.subI64();
        return long3;
    }

    public Long4 subLong4() {
        Long4 long4 = new Long4();
        long4.w = this.subI64();
        long4.z = this.subI64();
        long4.y = this.subI64();
        long4.x = this.subI64();
        return long4;
    }

    public Matrix2f subMatrix2f() {
        Matrix2f matrix2f = new Matrix2f();
        for (int i = matrix2f.mMat.length - 1; i >= 0; --i) {
            matrix2f.mMat[i] = this.subF32();
        }
        return matrix2f;
    }

    public Matrix3f subMatrix3f() {
        Matrix3f matrix3f = new Matrix3f();
        for (int i = matrix3f.mMat.length - 1; i >= 0; --i) {
            matrix3f.mMat[i] = this.subF32();
        }
        return matrix3f;
    }

    public Matrix4f subMatrix4f() {
        Matrix4f matrix4f = new Matrix4f();
        for (int i = matrix4f.mMat.length - 1; i >= 0; --i) {
            matrix4f.mMat[i] = this.subF32();
        }
        return matrix4f;
    }

    public Short2 subShort2() {
        Short2 short2 = new Short2();
        short2.y = this.subI16();
        short2.x = this.subI16();
        return short2;
    }

    public Short3 subShort3() {
        Short3 short3 = new Short3();
        short3.z = this.subI16();
        short3.y = this.subI16();
        short3.x = this.subI16();
        return short3;
    }

    public Short4 subShort4() {
        Short4 short4 = new Short4();
        short4.w = this.subI16();
        short4.z = this.subI16();
        short4.y = this.subI16();
        short4.x = this.subI16();
        return short4;
    }

    public void subalign(int n) {
        if ((n - 1 & n) == 0) {
            int n2;
            while ((n - 1 & (n2 = this.mPos)) != 0) {
                this.mPos = n2 - 1;
            }
            if (n2 > 0) {
                while (this.mAlignment.get(this.mPos - 1)) {
                    --this.mPos;
                    this.mAlignment.flip(this.mPos);
                }
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("argument must be a non-negative non-zero power of 2: ");
        stringBuilder.append(n);
        throw new RSIllegalArgumentException(stringBuilder.toString());
    }
}

