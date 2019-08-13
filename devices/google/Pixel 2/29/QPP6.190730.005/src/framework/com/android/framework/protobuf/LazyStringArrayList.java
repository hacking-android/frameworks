/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.AbstractProtobufList;
import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.Internal;
import com.android.framework.protobuf.LazyStringList;
import com.android.framework.protobuf.UnmodifiableLazyStringList;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public class LazyStringArrayList
extends AbstractProtobufList<String>
implements LazyStringList,
RandomAccess {
    public static final LazyStringList EMPTY;
    private static final LazyStringArrayList EMPTY_LIST;
    private final List<Object> list;

    static {
        EMPTY_LIST = new LazyStringArrayList();
        EMPTY_LIST.makeImmutable();
        EMPTY = EMPTY_LIST;
    }

    public LazyStringArrayList() {
        this(10);
    }

    public LazyStringArrayList(int n) {
        this(new ArrayList<Object>(n));
    }

    public LazyStringArrayList(LazyStringList lazyStringList) {
        this.list = new ArrayList<Object>(lazyStringList.size());
        this.addAll(lazyStringList);
    }

    private LazyStringArrayList(ArrayList<Object> arrayList) {
        this.list = arrayList;
    }

    public LazyStringArrayList(List<String> list) {
        this(new ArrayList<Object>(list));
    }

    @Override
    private void add(int n, ByteString byteString) {
        this.ensureIsMutable();
        this.list.add(n, byteString);
        ++this.modCount;
    }

    @Override
    private void add(int n, byte[] arrby) {
        this.ensureIsMutable();
        this.list.add(n, arrby);
        ++this.modCount;
    }

    private static byte[] asByteArray(Object object) {
        if (object instanceof byte[]) {
            return (byte[])object;
        }
        if (object instanceof String) {
            return Internal.toByteArray((String)object);
        }
        return ((ByteString)object).toByteArray();
    }

    private static ByteString asByteString(Object object) {
        if (object instanceof ByteString) {
            return (ByteString)object;
        }
        if (object instanceof String) {
            return ByteString.copyFromUtf8((String)object);
        }
        return ByteString.copyFrom((byte[])object);
    }

    private static String asString(Object object) {
        if (object instanceof String) {
            return (String)object;
        }
        if (object instanceof ByteString) {
            return ((ByteString)object).toStringUtf8();
        }
        return Internal.toStringUtf8((byte[])object);
    }

    static LazyStringArrayList emptyList() {
        return EMPTY_LIST;
    }

    private Object setAndReturn(int n, ByteString byteString) {
        this.ensureIsMutable();
        return this.list.set(n, byteString);
    }

    private Object setAndReturn(int n, byte[] arrby) {
        this.ensureIsMutable();
        return this.list.set(n, arrby);
    }

    @Override
    public void add(int n, String string2) {
        this.ensureIsMutable();
        this.list.add(n, string2);
        ++this.modCount;
    }

    @Override
    public void add(ByteString byteString) {
        this.ensureIsMutable();
        this.list.add(byteString);
        ++this.modCount;
    }

    @Override
    public void add(byte[] arrby) {
        this.ensureIsMutable();
        this.list.add(arrby);
        ++this.modCount;
    }

    @Override
    public boolean addAll(int n, Collection<? extends String> collection) {
        this.ensureIsMutable();
        if (collection instanceof LazyStringList) {
            collection = ((LazyStringList)collection).getUnderlyingElements();
        }
        boolean bl = this.list.addAll(n, collection);
        ++this.modCount;
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends String> collection) {
        return this.addAll(this.size(), collection);
    }

    @Override
    public boolean addAllByteArray(Collection<byte[]> collection) {
        this.ensureIsMutable();
        boolean bl = this.list.addAll(collection);
        ++this.modCount;
        return bl;
    }

    @Override
    public boolean addAllByteString(Collection<? extends ByteString> collection) {
        this.ensureIsMutable();
        boolean bl = this.list.addAll(collection);
        ++this.modCount;
        return bl;
    }

    @Override
    public List<byte[]> asByteArrayList() {
        return new ByteArrayListView(this);
    }

    @Override
    public List<ByteString> asByteStringList() {
        return new ByteStringListView(this);
    }

    @Override
    public void clear() {
        this.ensureIsMutable();
        this.list.clear();
        ++this.modCount;
    }

    @Override
    public String get(int n) {
        byte[] arrby = this.list.get(n);
        if (arrby instanceof String) {
            return (String)arrby;
        }
        if (arrby instanceof ByteString) {
            arrby = (ByteString)arrby;
            String string2 = arrby.toStringUtf8();
            if (arrby.isValidUtf8()) {
                this.list.set(n, string2);
            }
            return string2;
        }
        arrby = arrby;
        String string3 = Internal.toStringUtf8(arrby);
        if (Internal.isValidUtf8(arrby)) {
            this.list.set(n, string3);
        }
        return string3;
    }

    @Override
    public byte[] getByteArray(int n) {
        Object object = this.list.get(n);
        byte[] arrby = LazyStringArrayList.asByteArray(object);
        if (arrby != object) {
            this.list.set(n, arrby);
        }
        return arrby;
    }

    @Override
    public ByteString getByteString(int n) {
        Object object = this.list.get(n);
        ByteString byteString = LazyStringArrayList.asByteString(object);
        if (byteString != object) {
            this.list.set(n, byteString);
        }
        return byteString;
    }

    @Override
    public Object getRaw(int n) {
        return this.list.get(n);
    }

    @Override
    public List<?> getUnderlyingElements() {
        return Collections.unmodifiableList(this.list);
    }

    @Override
    public LazyStringList getUnmodifiableView() {
        if (this.isModifiable()) {
            return new UnmodifiableLazyStringList(this);
        }
        return this;
    }

    @Override
    public void mergeFrom(LazyStringList object) {
        this.ensureIsMutable();
        for (Object object2 : object.getUnderlyingElements()) {
            if (object2 instanceof byte[]) {
                object2 = (byte[])object2;
                this.list.add(Arrays.copyOf(object2, ((E)object2).length));
                continue;
            }
            this.list.add(object2);
        }
    }

    public LazyStringArrayList mutableCopyWithCapacity(int n) {
        if (n >= this.size()) {
            ArrayList<Object> arrayList = new ArrayList<Object>(n);
            arrayList.addAll(this.list);
            return new LazyStringArrayList(arrayList);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String remove(int n) {
        this.ensureIsMutable();
        Object object = this.list.remove(n);
        ++this.modCount;
        return LazyStringArrayList.asString(object);
    }

    @Override
    public String set(int n, String string2) {
        this.ensureIsMutable();
        return LazyStringArrayList.asString(this.list.set(n, string2));
    }

    @Override
    public void set(int n, ByteString byteString) {
        this.setAndReturn(n, byteString);
    }

    @Override
    public void set(int n, byte[] arrby) {
        this.setAndReturn(n, arrby);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    private static class ByteArrayListView
    extends AbstractList<byte[]>
    implements RandomAccess {
        private final LazyStringArrayList list;

        ByteArrayListView(LazyStringArrayList lazyStringArrayList) {
            this.list = lazyStringArrayList;
        }

        @Override
        public void add(int n, byte[] arrby) {
            this.list.add(n, arrby);
            ++this.modCount;
        }

        @Override
        public byte[] get(int n) {
            return this.list.getByteArray(n);
        }

        @Override
        public byte[] remove(int n) {
            Object object = this.list.remove(n);
            ++this.modCount;
            return LazyStringArrayList.asByteArray(object);
        }

        @Override
        public byte[] set(int n, byte[] object) {
            object = this.list.setAndReturn(n, object);
            ++this.modCount;
            return LazyStringArrayList.asByteArray(object);
        }

        @Override
        public int size() {
            return this.list.size();
        }
    }

    private static class ByteStringListView
    extends AbstractList<ByteString>
    implements RandomAccess {
        private final LazyStringArrayList list;

        ByteStringListView(LazyStringArrayList lazyStringArrayList) {
            this.list = lazyStringArrayList;
        }

        @Override
        public void add(int n, ByteString byteString) {
            this.list.add(n, byteString);
            ++this.modCount;
        }

        @Override
        public ByteString get(int n) {
            return this.list.getByteString(n);
        }

        @Override
        public ByteString remove(int n) {
            Object object = this.list.remove(n);
            ++this.modCount;
            return LazyStringArrayList.asByteString(object);
        }

        @Override
        public ByteString set(int n, ByteString object) {
            object = this.list.setAndReturn(n, (ByteString)object);
            ++this.modCount;
            return LazyStringArrayList.asByteString(object);
        }

        @Override
        public int size() {
            return this.list.size();
        }
    }

}

