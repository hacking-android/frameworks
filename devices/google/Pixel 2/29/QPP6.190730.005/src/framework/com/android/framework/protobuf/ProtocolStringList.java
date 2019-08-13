/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import java.util.List;

public interface ProtocolStringList
extends List<String> {
    public List<ByteString> asByteStringList();
}

