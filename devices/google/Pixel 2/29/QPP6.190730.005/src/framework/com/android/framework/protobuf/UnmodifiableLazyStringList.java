/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.LazyStringList;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public class UnmodifiableLazyStringList
extends AbstractList<String>
implements LazyStringList,
RandomAccess {
    private final LazyStringList list;

    public UnmodifiableLazyStringList(LazyStringList lazyStringList) {
        this.list = lazyStringList;
    }

    @Override
    public void add(ByteString byteString) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(byte[] arrby) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAllByteArray(Collection<byte[]> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAllByteString(Collection<? extends ByteString> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<byte[]> asByteArrayList() {
        return Collections.unmodifiableList(this.list.asByteArrayList());
    }

    @Override
    public List<ByteString> asByteStringList() {
        return Collections.unmodifiableList(this.list.asByteStringList());
    }

    @Override
    public String get(int n) {
        return (String)this.list.get(n);
    }

    @Override
    public byte[] getByteArray(int n) {
        return this.list.getByteArray(n);
    }

    @Override
    public ByteString getByteString(int n) {
        return this.list.getByteString(n);
    }

    @Override
    public Object getRaw(int n) {
        return this.list.getRaw(n);
    }

    @Override
    public List<?> getUnderlyingElements() {
        return this.list.getUnderlyingElements();
    }

    @Override
    public LazyStringList getUnmodifiableView() {
        return this;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>(){
            Iterator<String> iter;
            {
                this.iter = UnmodifiableLazyStringList.this.list.iterator();
            }

            @Override
            public boolean hasNext() {
                return this.iter.hasNext();
            }

            @Override
            public String next() {
                return this.iter.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public ListIterator<String> listIterator(final int n) {
        return new ListIterator<String>(){
            ListIterator<String> iter;
            {
                this.iter = UnmodifiableLazyStringList.this.list.listIterator(n);
            }

            @Override
            public void add(String string2) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasNext() {
                return this.iter.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return this.iter.hasPrevious();
            }

            @Override
            public String next() {
                return this.iter.next();
            }

            @Override
            public int nextIndex() {
                return this.iter.nextIndex();
            }

            @Override
            public String previous() {
                return this.iter.previous();
            }

            @Override
            public int previousIndex() {
                return this.iter.previousIndex();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(String string2) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public void mergeFrom(LazyStringList lazyStringList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(int n, ByteString byteString) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(int n, byte[] arrby) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return this.list.size();
    }

}

