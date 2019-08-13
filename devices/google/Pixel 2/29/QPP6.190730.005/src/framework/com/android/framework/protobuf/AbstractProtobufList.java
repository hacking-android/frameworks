/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.Internal;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

abstract class AbstractProtobufList<E>
extends AbstractList<E>
implements Internal.ProtobufList<E> {
    protected static final int DEFAULT_CAPACITY = 10;
    private boolean isMutable = true;

    AbstractProtobufList() {
    }

    @Override
    public void add(int n, E e) {
        this.ensureIsMutable();
        super.add(n, e);
    }

    @Override
    public boolean add(E e) {
        this.ensureIsMutable();
        return super.add(e);
    }

    @Override
    public boolean addAll(int n, Collection<? extends E> collection) {
        this.ensureIsMutable();
        return super.addAll(n, collection);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        this.ensureIsMutable();
        return super.addAll(collection);
    }

    @Override
    public void clear() {
        this.ensureIsMutable();
        super.clear();
    }

    protected void ensureIsMutable() {
        if (this.isMutable) {
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof List)) {
            return false;
        }
        if (!(object instanceof RandomAccess)) {
            return super.equals(object);
        }
        object = (List)object;
        int n = this.size();
        if (n != object.size()) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (this.get(i).equals(object.get(i))) continue;
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int n = this.size();
        int n2 = 1;
        for (int i = 0; i < n; ++i) {
            n2 = n2 * 31 + this.get(i).hashCode();
        }
        return n2;
    }

    @Override
    public boolean isModifiable() {
        return this.isMutable;
    }

    @Override
    public final void makeImmutable() {
        this.isMutable = false;
    }

    @Override
    public E remove(int n) {
        this.ensureIsMutable();
        return super.remove(n);
    }

    @Override
    public boolean remove(Object object) {
        this.ensureIsMutable();
        return super.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        this.ensureIsMutable();
        return super.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        this.ensureIsMutable();
        return super.retainAll(collection);
    }

    @Override
    public E set(int n, E e) {
        this.ensureIsMutable();
        return super.set(n, e);
    }
}

