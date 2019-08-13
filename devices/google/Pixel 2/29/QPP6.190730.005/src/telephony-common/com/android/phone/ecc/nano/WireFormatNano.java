/*
 * Decompiled with CFR 0.145.
 */
package com.android.phone.ecc.nano;

import com.android.phone.ecc.nano.CodedInputByteBufferNano;
import java.io.IOException;

public final class WireFormatNano {
    public static final boolean[] EMPTY_BOOLEAN_ARRAY;
    public static final byte[] EMPTY_BYTES;
    public static final byte[][] EMPTY_BYTES_ARRAY;
    public static final double[] EMPTY_DOUBLE_ARRAY;
    public static final float[] EMPTY_FLOAT_ARRAY;
    public static final int[] EMPTY_INT_ARRAY;
    public static final long[] EMPTY_LONG_ARRAY;
    public static final String[] EMPTY_STRING_ARRAY;
    static final int TAG_TYPE_BITS = 3;
    static final int TAG_TYPE_MASK = 7;
    static final int WIRETYPE_END_GROUP = 4;
    static final int WIRETYPE_FIXED32 = 5;
    static final int WIRETYPE_FIXED64 = 1;
    static final int WIRETYPE_LENGTH_DELIMITED = 2;
    static final int WIRETYPE_START_GROUP = 3;
    static final int WIRETYPE_VARINT = 0;

    static {
        EMPTY_INT_ARRAY = new int[0];
        EMPTY_LONG_ARRAY = new long[0];
        EMPTY_FLOAT_ARRAY = new float[0];
        EMPTY_DOUBLE_ARRAY = new double[0];
        EMPTY_BOOLEAN_ARRAY = new boolean[0];
        EMPTY_STRING_ARRAY = new String[0];
        EMPTY_BYTES_ARRAY = new byte[0][];
        EMPTY_BYTES = new byte[0];
    }

    private WireFormatNano() {
    }

    public static final int getRepeatedFieldArrayLength(CodedInputByteBufferNano codedInputByteBufferNano, int n) throws IOException {
        int n2 = 1;
        int n3 = codedInputByteBufferNano.getPosition();
        codedInputByteBufferNano.skipField(n);
        while (codedInputByteBufferNano.readTag() == n) {
            codedInputByteBufferNano.skipField(n);
            ++n2;
        }
        codedInputByteBufferNano.rewindToPosition(n3);
        return n2;
    }

    public static int getTagFieldNumber(int n) {
        return n >>> 3;
    }

    static int getTagWireType(int n) {
        return n & 7;
    }

    static int makeTag(int n, int n2) {
        return n << 3 | n2;
    }

    public static boolean parseUnknownField(CodedInputByteBufferNano codedInputByteBufferNano, int n) throws IOException {
        return codedInputByteBufferNano.skipField(n);
    }
}

