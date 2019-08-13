/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.AbstractProtobufList;
import com.android.framework.protobuf.Internal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class ProtobufArrayList<E>
extends AbstractProtobufList<E> {
    private static final ProtobufArrayList<Object> EMPTY_LIST = new ProtobufArrayList<E>();
    private final List<E> list;

    static {
        EMPTY_LIST.makeImmutable();
    }

    ProtobufArrayList() {
        this(new ArrayList(10));
    }

    private ProtobufArrayList(List<E> list) {
        this.list = list;
    }

    public static <E> ProtobufArrayList<E> emptyList() {
        return EMPTY_LIST;
    }

    @Override
    public void add(int n, E e) {
        this.ensureIsMutable();
        this.list.add(n, e);
        ++this.modCount;
    }

    @Override
    public E get(int n) {
        return this.list.get(n);
    }

    @Override
    public ProtobufArrayList<E> mutableCopyWithCapacity(int n) {
        if (n >= this.size()) {
            ArrayList<E> arrayList = new ArrayList<E>(n);
            arrayList.addAll(this.list);
            return new ProtobufArrayList(arrayList);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public E remove(int n) {
        this.ensureIsMutable();
        E e = this.list.remove(n);
        ++this.modCount;
        return e;
    }

    @Override
    public E set(int n, E e) {
        this.ensureIsMutable();
        e = this.list.set(n, e);
        ++this.modCount;
        return e;
    }

    @Override
    public int size() {
        return this.list.size();
    }
}

