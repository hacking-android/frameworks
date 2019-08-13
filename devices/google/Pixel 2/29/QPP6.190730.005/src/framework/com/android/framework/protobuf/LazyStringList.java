/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.ProtocolStringList;
import java.util.Collection;
import java.util.List;

public interface LazyStringList
extends ProtocolStringList {
    public void add(ByteString var1);

    public void add(byte[] var1);

    public boolean addAllByteArray(Collection<byte[]> var1);

    public boolean addAllByteString(Collection<? extends ByteString> var1);

    public List<byte[]> asByteArrayList();

    public byte[] getByteArray(int var1);

    public ByteString getByteString(int var1);

    public Object getRaw(int var1);

    public List<?> getUnderlyingElements();

    public LazyStringList getUnmodifiableView();

    public void mergeFrom(LazyStringList var1);

    @Override
    public void set(int var1, ByteString var2);

    @Override
    public void set(int var1, byte[] var2);
}

