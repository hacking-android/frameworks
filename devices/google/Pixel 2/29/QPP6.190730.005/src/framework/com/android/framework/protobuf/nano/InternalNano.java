/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.ExtendableMessageNano;
import com.android.framework.protobuf.nano.FieldArray;
import com.android.framework.protobuf.nano.MapFactories;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class InternalNano {
    protected static final Charset ISO_8859_1;
    public static final Object LAZY_INIT_LOCK;
    public static final int TYPE_BOOL = 8;
    public static final int TYPE_BYTES = 12;
    public static final int TYPE_DOUBLE = 1;
    public static final int TYPE_ENUM = 14;
    public static final int TYPE_FIXED32 = 7;
    public static final int TYPE_FIXED64 = 6;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_GROUP = 10;
    public static final int TYPE_INT32 = 5;
    public static final int TYPE_INT64 = 3;
    public static final int TYPE_MESSAGE = 11;
    public static final int TYPE_SFIXED32 = 15;
    public static final int TYPE_SFIXED64 = 16;
    public static final int TYPE_SINT32 = 17;
    public static final int TYPE_SINT64 = 18;
    public static final int TYPE_STRING = 9;
    public static final int TYPE_UINT32 = 13;
    public static final int TYPE_UINT64 = 4;
    protected static final Charset UTF_8;

    static {
        UTF_8 = Charset.forName("UTF-8");
        ISO_8859_1 = Charset.forName("ISO-8859-1");
        LAZY_INIT_LOCK = new Object();
    }

    private InternalNano() {
    }

    public static byte[] bytesDefaultValue(String string2) {
        return string2.getBytes(ISO_8859_1);
    }

    public static void cloneUnknownFieldData(ExtendableMessageNano extendableMessageNano, ExtendableMessageNano extendableMessageNano2) {
        if (extendableMessageNano.unknownFieldData != null) {
            extendableMessageNano2.unknownFieldData = extendableMessageNano.unknownFieldData.clone();
        }
    }

    public static <K, V> int computeMapFieldSize(Map<K, V> object, int n, int n2, int n3) {
        int n4 = 0;
        int n5 = CodedOutputByteBufferNano.computeTagSize(n);
        object = object.entrySet().iterator();
        n = n4;
        while (object.hasNext()) {
            Map.Entry entry = (Map.Entry)object.next();
            Object k = entry.getKey();
            entry = entry.getValue();
            if (k != null && entry != null) {
                n4 = CodedOutputByteBufferNano.computeFieldSize(1, n2, k) + CodedOutputByteBufferNano.computeFieldSize(2, n3, entry);
                n += n5 + n4 + CodedOutputByteBufferNano.computeRawVarint32Size(n4);
                continue;
            }
            throw new IllegalStateException("keys and values in maps cannot be null");
        }
        return n;
    }

    public static byte[] copyFromUtf8(String string2) {
        return string2.getBytes(UTF_8);
    }

    public static <K, V> boolean equals(Map<K, V> object, Map<K, V> map) {
        boolean bl = true;
        boolean bl2 = true;
        if (object == map) {
            return true;
        }
        if (object == null) {
            if (map.size() != 0) {
                bl2 = false;
            }
            return bl2;
        }
        if (map == null) {
            bl2 = object.size() == 0 ? bl : false;
            return bl2;
        }
        if (object.size() != map.size()) {
            return false;
        }
        for (Map.Entry entry : object.entrySet()) {
            if (!map.containsKey(entry.getKey())) {
                return false;
            }
            if (InternalNano.equalsMapValue(entry.getValue(), map.get(entry.getKey()))) continue;
            return false;
        }
        return true;
    }

    public static boolean equals(double[] arrd, double[] arrd2) {
        if (arrd != null && arrd.length != 0) {
            return Arrays.equals(arrd, arrd2);
        }
        boolean bl = arrd2 == null || arrd2.length == 0;
        return bl;
    }

    public static boolean equals(float[] arrf, float[] arrf2) {
        if (arrf != null && arrf.length != 0) {
            return Arrays.equals(arrf, arrf2);
        }
        boolean bl = arrf2 == null || arrf2.length == 0;
        return bl;
    }

    public static boolean equals(int[] arrn, int[] arrn2) {
        if (arrn != null && arrn.length != 0) {
            return Arrays.equals(arrn, arrn2);
        }
        boolean bl = arrn2 == null || arrn2.length == 0;
        return bl;
    }

    public static boolean equals(long[] arrl, long[] arrl2) {
        if (arrl != null && arrl.length != 0) {
            return Arrays.equals(arrl, arrl2);
        }
        boolean bl = arrl2 == null || arrl2.length == 0;
        return bl;
    }

    public static boolean equals(Object[] arrobject, Object[] arrobject2) {
        int n = 0;
        int n2 = arrobject == null ? 0 : arrobject.length;
        int n3 = 0;
        int n4 = arrobject2 == null ? 0 : arrobject2.length;
        do {
            int n5 = n3;
            if (n < n2) {
                n5 = n3;
                if (arrobject[n] == null) {
                    ++n;
                    continue;
                }
            }
            while (n5 < n4 && arrobject2[n5] == null) {
                ++n5;
            }
            n3 = n >= n2 ? 1 : 0;
            int n6 = n5 >= n4 ? 1 : 0;
            if (n3 != 0 && n6 != 0) {
                return true;
            }
            if (n3 != n6) {
                return false;
            }
            if (!arrobject[n].equals(arrobject2[n5])) {
                return false;
            }
            ++n;
            n3 = n5 + 1;
        } while (true);
    }

    public static boolean equals(boolean[] arrbl, boolean[] arrbl2) {
        if (arrbl != null && arrbl.length != 0) {
            return Arrays.equals(arrbl, arrbl2);
        }
        boolean bl = arrbl2 == null || arrbl2.length == 0;
        return bl;
    }

    public static boolean equals(byte[][] arrby, byte[][] arrby2) {
        int n = 0;
        int n2 = arrby == null ? 0 : arrby.length;
        int n3 = 0;
        int n4 = arrby2 == null ? 0 : arrby2.length;
        do {
            int n5 = n3;
            if (n < n2) {
                n5 = n3;
                if (arrby[n] == null) {
                    ++n;
                    continue;
                }
            }
            while (n5 < n4 && arrby2[n5] == null) {
                ++n5;
            }
            n3 = n >= n2 ? 1 : 0;
            int n6 = n5 >= n4 ? 1 : 0;
            if (n3 != 0 && n6 != 0) {
                return true;
            }
            if (n3 != n6) {
                return false;
            }
            if (!Arrays.equals(arrby[n], arrby2[n5])) {
                return false;
            }
            ++n;
            n3 = n5 + 1;
        } while (true);
    }

    private static boolean equalsMapValue(Object object, Object object2) {
        if (object != null && object2 != null) {
            if (object instanceof byte[] && object2 instanceof byte[]) {
                return Arrays.equals((byte[])object, (byte[])object2);
            }
            return object.equals(object2);
        }
        throw new IllegalStateException("keys and values in maps cannot be null");
    }

    public static <K, V> int hashCode(Map<K, V> object2) {
        if (object2 == null) {
            return 0;
        }
        int n = 0;
        for (Map.Entry entry : object2.entrySet()) {
            n += InternalNano.hashCodeForMap(entry.getKey()) ^ InternalNano.hashCodeForMap(entry.getValue());
        }
        return n;
    }

    public static int hashCode(double[] arrd) {
        int n = arrd != null && arrd.length != 0 ? Arrays.hashCode(arrd) : 0;
        return n;
    }

    public static int hashCode(float[] arrf) {
        int n = arrf != null && arrf.length != 0 ? Arrays.hashCode(arrf) : 0;
        return n;
    }

    public static int hashCode(int[] arrn) {
        int n = arrn != null && arrn.length != 0 ? Arrays.hashCode(arrn) : 0;
        return n;
    }

    public static int hashCode(long[] arrl) {
        int n = arrl != null && arrl.length != 0 ? Arrays.hashCode(arrl) : 0;
        return n;
    }

    public static int hashCode(Object[] arrobject) {
        int n = 0;
        int n2 = arrobject == null ? 0 : arrobject.length;
        for (int i = 0; i < n2; ++i) {
            Object object = arrobject[i];
            int n3 = n;
            if (object != null) {
                n3 = n * 31 + object.hashCode();
            }
            n = n3;
        }
        return n;
    }

    public static int hashCode(boolean[] arrbl) {
        int n = arrbl != null && arrbl.length != 0 ? Arrays.hashCode(arrbl) : 0;
        return n;
    }

    public static int hashCode(byte[][] arrby) {
        int n = 0;
        int n2 = arrby == null ? 0 : arrby.length;
        for (int i = 0; i < n2; ++i) {
            byte[] arrby2 = arrby[i];
            int n3 = n;
            if (arrby2 != null) {
                n3 = n * 31 + Arrays.hashCode(arrby2);
            }
            n = n3;
        }
        return n;
    }

    private static int hashCodeForMap(Object object) {
        if (object instanceof byte[]) {
            return Arrays.hashCode((byte[])object);
        }
        return object.hashCode();
    }

    public static final <K, V> Map<K, V> mergeMapEntry(CodedInputByteBufferNano object, Map<K, V> object2, MapFactories.MapFactory map, int n, int n2, V v, int n3, int n4) throws IOException {
        Map<Object, Object> map2 = map.forMap(object2);
        int n5 = ((CodedInputByteBufferNano)object).pushLimit(((CodedInputByteBufferNano)object).readRawVarint32());
        object2 = null;
        do {
            Object object3;
            block10 : {
                block8 : {
                    int n6;
                    block11 : {
                        block9 : {
                            if ((n6 = ((CodedInputByteBufferNano)object).readTag()) == 0) break block8;
                            if (n6 != n3) break block9;
                            map = ((CodedInputByteBufferNano)object).readPrimitiveField(n);
                            object3 = v;
                            break block10;
                        }
                        if (n6 != n4) break block11;
                        if (n2 == 11) {
                            ((CodedInputByteBufferNano)object).readMessage((MessageNano)v);
                            map = object2;
                            object3 = v;
                        } else {
                            object3 = ((CodedInputByteBufferNano)object).readPrimitiveField(n2);
                            map = object2;
                        }
                        break block10;
                    }
                    map = object2;
                    object3 = v;
                    if (((CodedInputByteBufferNano)object).skipField(n6)) break block10;
                }
                ((CodedInputByteBufferNano)object).checkLastTagWas(0);
                ((CodedInputByteBufferNano)object).popLimit(n5);
                object = object2;
                if (object2 == null) {
                    object = InternalNano.primitiveDefaultValue(n);
                }
                object2 = v;
                if (v == null) {
                    object2 = InternalNano.primitiveDefaultValue(n2);
                }
                map2.put(object, object2);
                return map2;
            }
            object2 = map;
            v = object3;
        } while (true);
    }

    private static Object primitiveDefaultValue(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Type: ");
                stringBuilder.append(n);
                stringBuilder.append(" is not a primitive type.");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 12: {
                return WireFormatNano.EMPTY_BYTES;
            }
            case 9: {
                return "";
            }
            case 8: {
                return Boolean.FALSE;
            }
            case 5: 
            case 7: 
            case 13: 
            case 14: 
            case 15: 
            case 17: {
                return 0;
            }
            case 3: 
            case 4: 
            case 6: 
            case 16: 
            case 18: {
                return 0L;
            }
            case 2: {
                return Float.valueOf(0.0f);
            }
            case 1: 
        }
        return 0.0;
    }

    public static <K, V> void serializeMapField(CodedOutputByteBufferNano codedOutputByteBufferNano, Map<K, V> object, int n, int n2, int n3) throws IOException {
        for (Map.Entry entry : object.entrySet()) {
            Object k = entry.getKey();
            Object entry2 = entry.getValue();
            if (k != null && entry2 != null) {
                int n4 = CodedOutputByteBufferNano.computeFieldSize(1, n2, k);
                int n5 = CodedOutputByteBufferNano.computeFieldSize(2, n3, entry2);
                codedOutputByteBufferNano.writeTag(n, 2);
                codedOutputByteBufferNano.writeRawVarint32(n4 + n5);
                codedOutputByteBufferNano.writeField(1, n2, k);
                codedOutputByteBufferNano.writeField(2, n3, entry2);
                continue;
            }
            throw new IllegalStateException("keys and values in maps cannot be null");
        }
    }

    public static String stringDefaultValue(String string2) {
        return new String(string2.getBytes(ISO_8859_1), UTF_8);
    }
}

