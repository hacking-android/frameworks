/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedInputStream;
import com.android.framework.protobuf.MessageLite;
import com.android.framework.protobuf.Utf8;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

public final class Internal {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final ByteBuffer EMPTY_BYTE_BUFFER;
    public static final CodedInputStream EMPTY_CODED_INPUT_STREAM;
    static final Charset ISO_8859_1;
    static final Charset UTF_8;

    static {
        UTF_8 = Charset.forName("UTF-8");
        ISO_8859_1 = Charset.forName("ISO-8859-1");
        EMPTY_BYTE_ARRAY = new byte[0];
        EMPTY_BYTE_BUFFER = ByteBuffer.wrap(EMPTY_BYTE_ARRAY);
        EMPTY_CODED_INPUT_STREAM = CodedInputStream.newInstance(EMPTY_BYTE_ARRAY);
    }

    private Internal() {
    }

    public static byte[] byteArrayDefaultValue(String string2) {
        return string2.getBytes(ISO_8859_1);
    }

    public static ByteBuffer byteBufferDefaultValue(String string2) {
        return ByteBuffer.wrap(Internal.byteArrayDefaultValue(string2));
    }

    public static ByteString bytesDefaultValue(String string2) {
        return ByteString.copyFrom(string2.getBytes(ISO_8859_1));
    }

    public static ByteBuffer copyByteBuffer(ByteBuffer byteBuffer) {
        byteBuffer = byteBuffer.duplicate();
        byteBuffer.clear();
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(byteBuffer.capacity());
        byteBuffer2.put(byteBuffer);
        byteBuffer2.clear();
        return byteBuffer2;
    }

    public static boolean equals(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            if (Arrays.equals(list.get(i), list2.get(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean equalsByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        if (byteBuffer.capacity() != byteBuffer2.capacity()) {
            return false;
        }
        return byteBuffer.duplicate().clear().equals(byteBuffer2.duplicate().clear());
    }

    public static boolean equalsByteBuffer(List<ByteBuffer> list, List<ByteBuffer> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            if (Internal.equalsByteBuffer(list.get(i), list2.get(i))) continue;
            return false;
        }
        return true;
    }

    public static <T extends MessageLite> T getDefaultInstance(Class<T> class_) {
        Object object;
        try {
            object = class_.getMethod("getDefaultInstance", new Class[0]);
            object = (MessageLite)((Method)object).invoke(object, new Object[0]);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to get default instance for ");
            stringBuilder.append(class_);
            throw new RuntimeException(stringBuilder.toString(), exception);
        }
        return (T)object;
    }

    public static int hashBoolean(boolean bl) {
        int n = bl ? 1231 : 1237;
        return n;
    }

    public static int hashCode(List<byte[]> object) {
        int n = 1;
        object = object.iterator();
        while (object.hasNext()) {
            n = n * 31 + Internal.hashCode((byte[])object.next());
        }
        return n;
    }

    public static int hashCode(byte[] arrby) {
        return Internal.hashCode(arrby, 0, arrby.length);
    }

    static int hashCode(byte[] arrby, int n, int n2) {
        block0 : {
            if ((n = Internal.partialHash(n2, arrby, n, n2)) != 0) break block0;
            n = 1;
        }
        return n;
    }

    public static int hashCodeByteBuffer(ByteBuffer byteBuffer) {
        int n;
        block4 : {
            boolean bl = byteBuffer.hasArray();
            int n2 = 1;
            int n3 = 1;
            if (bl) {
                int n4 = Internal.partialHash(byteBuffer.capacity(), byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
                if (n4 != 0) {
                    n3 = n4;
                }
                return n3;
            }
            n = byteBuffer.capacity();
            n3 = 4096;
            if (n <= 4096) {
                n3 = byteBuffer.capacity();
            }
            byte[] arrby = new byte[n3];
            ByteBuffer byteBuffer2 = byteBuffer.duplicate();
            byteBuffer2.clear();
            n = byteBuffer.capacity();
            while (byteBuffer2.remaining() > 0) {
                int n5 = byteBuffer2.remaining() <= n3 ? byteBuffer2.remaining() : n3;
                byteBuffer2.get(arrby, 0, n5);
                n = Internal.partialHash(n, arrby, 0, n5);
            }
            if (n != 0) break block4;
            n = n2;
        }
        return n;
    }

    public static int hashCodeByteBuffer(List<ByteBuffer> object) {
        int n = 1;
        object = object.iterator();
        while (object.hasNext()) {
            n = n * 31 + Internal.hashCodeByteBuffer((ByteBuffer)object.next());
        }
        return n;
    }

    public static int hashEnum(EnumLite enumLite) {
        return enumLite.getNumber();
    }

    public static int hashEnumList(List<? extends EnumLite> object) {
        int n = 1;
        object = object.iterator();
        while (object.hasNext()) {
            n = n * 31 + Internal.hashEnum((EnumLite)object.next());
        }
        return n;
    }

    public static int hashLong(long l) {
        return (int)(l >>> 32 ^ l);
    }

    public static boolean isValidUtf8(ByteString byteString) {
        return byteString.isValidUtf8();
    }

    public static boolean isValidUtf8(byte[] arrby) {
        return Utf8.isValidUtf8(arrby);
    }

    static int partialHash(int n, byte[] arrby, int n2, int n3) {
        for (int i = n2; i < n2 + n3; ++i) {
            n = n * 31 + arrby[i];
        }
        return n;
    }

    public static String stringDefaultValue(String string2) {
        return new String(string2.getBytes(ISO_8859_1), UTF_8);
    }

    public static byte[] toByteArray(String string2) {
        return string2.getBytes(UTF_8);
    }

    public static String toStringUtf8(byte[] arrby) {
        return new String(arrby, UTF_8);
    }

    public static interface BooleanList
    extends ProtobufList<Boolean> {
        public void addBoolean(boolean var1);

        public boolean getBoolean(int var1);

        public BooleanList mutableCopyWithCapacity(int var1);

        public boolean setBoolean(int var1, boolean var2);
    }

    public static interface DoubleList
    extends ProtobufList<Double> {
        public void addDouble(double var1);

        public double getDouble(int var1);

        public DoubleList mutableCopyWithCapacity(int var1);

        public double setDouble(int var1, double var2);
    }

    public static interface EnumLite {
        public int getNumber();
    }

    public static interface EnumLiteMap<T extends EnumLite> {
        public T findValueByNumber(int var1);
    }

    public static interface FloatList
    extends ProtobufList<Float> {
        public void addFloat(float var1);

        public float getFloat(int var1);

        public FloatList mutableCopyWithCapacity(int var1);

        public float setFloat(int var1, float var2);
    }

    public static interface IntList
    extends ProtobufList<Integer> {
        public void addInt(int var1);

        public int getInt(int var1);

        public IntList mutableCopyWithCapacity(int var1);

        public int setInt(int var1, int var2);
    }

    public static class ListAdapter<F, T>
    extends AbstractList<T> {
        private final Converter<F, T> converter;
        private final List<F> fromList;

        public ListAdapter(List<F> list, Converter<F, T> converter) {
            this.fromList = list;
            this.converter = converter;
        }

        @Override
        public T get(int n) {
            return this.converter.convert(this.fromList.get(n));
        }

        @Override
        public int size() {
            return this.fromList.size();
        }

        public static interface Converter<F, T> {
            public T convert(F var1);
        }

    }

    public static interface LongList
    extends ProtobufList<Long> {
        public void addLong(long var1);

        public long getLong(int var1);

        public LongList mutableCopyWithCapacity(int var1);

        public long setLong(int var1, long var2);
    }

    public static class MapAdapter<K, V, RealValue>
    extends AbstractMap<K, V> {
        private final Map<K, RealValue> realMap;
        private final Converter<RealValue, V> valueConverter;

        public MapAdapter(Map<K, RealValue> map, Converter<RealValue, V> converter) {
            this.realMap = map;
            this.valueConverter = converter;
        }

        public static <T extends EnumLite> Converter<Integer, T> newEnumConverter(EnumLiteMap<T> enumLiteMap, final T t) {
            return new Converter<Integer, T>(){

                @Override
                public Integer doBackward(T t2) {
                    return t2.getNumber();
                }

                @Override
                public T doForward(Integer object) {
                    block0 : {
                        if ((object = EnumLiteMap.this.findValueByNumber((Integer)object)) != null) break block0;
                        object = t;
                    }
                    return (T)object;
                }
            };
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return new SetAdapter(this.realMap.entrySet());
        }

        @Override
        public V get(Object object) {
            if ((object = this.realMap.get(object)) == null) {
                return null;
            }
            return this.valueConverter.doForward(object);
        }

        @Override
        public V put(K object, V v) {
            if ((object = this.realMap.put(object, this.valueConverter.doBackward(v))) == null) {
                return null;
            }
            return this.valueConverter.doForward(object);
        }

        public static interface Converter<A, B> {
            public A doBackward(B var1);

            public B doForward(A var1);
        }

        private class EntryAdapter
        implements Map.Entry<K, V> {
            private final Map.Entry<K, RealValue> realEntry;

            public EntryAdapter(Map.Entry<K, RealValue> entry) {
                this.realEntry = entry;
            }

            @Override
            public K getKey() {
                return this.realEntry.getKey();
            }

            @Override
            public V getValue() {
                return (V)MapAdapter.this.valueConverter.doForward(this.realEntry.getValue());
            }

            @Override
            public V setValue(V object) {
                object = this.realEntry.setValue(MapAdapter.this.valueConverter.doBackward(object));
                if (object == null) {
                    return null;
                }
                return (V)MapAdapter.this.valueConverter.doForward(object);
            }
        }

        private class IteratorAdapter
        implements Iterator<Map.Entry<K, V>> {
            private final Iterator<Map.Entry<K, RealValue>> realIterator;

            public IteratorAdapter(Iterator<Map.Entry<K, RealValue>> iterator) {
                this.realIterator = iterator;
            }

            @Override
            public boolean hasNext() {
                return this.realIterator.hasNext();
            }

            @Override
            public Map.Entry<K, V> next() {
                return new EntryAdapter(this.realIterator.next());
            }

            @Override
            public void remove() {
                this.realIterator.remove();
            }
        }

        private class SetAdapter
        extends AbstractSet<Map.Entry<K, V>> {
            private final Set<Map.Entry<K, RealValue>> realSet;

            public SetAdapter(Set<Map.Entry<K, RealValue>> set) {
                this.realSet = set;
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return new IteratorAdapter(this.realSet.iterator());
            }

            @Override
            public int size() {
                return this.realSet.size();
            }
        }

    }

    public static interface ProtobufList<E>
    extends List<E>,
    RandomAccess {
        public boolean isModifiable();

        public void makeImmutable();

        public ProtobufList<E> mutableCopyWithCapacity(int var1);
    }

}

