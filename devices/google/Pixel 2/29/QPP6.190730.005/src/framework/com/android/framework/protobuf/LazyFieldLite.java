/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedInputStream;
import com.android.framework.protobuf.ExtensionRegistryLite;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.framework.protobuf.MessageLite;
import com.android.framework.protobuf.Parser;
import java.io.IOException;

public class LazyFieldLite {
    private static final ExtensionRegistryLite EMPTY_REGISTRY = ExtensionRegistryLite.getEmptyRegistry();
    private ByteString delayedBytes;
    private ExtensionRegistryLite extensionRegistry;
    private volatile ByteString memoizedBytes;
    protected volatile MessageLite value;

    public LazyFieldLite() {
    }

    public LazyFieldLite(ExtensionRegistryLite extensionRegistryLite, ByteString byteString) {
        LazyFieldLite.checkArguments(extensionRegistryLite, byteString);
        this.extensionRegistry = extensionRegistryLite;
        this.delayedBytes = byteString;
    }

    private static void checkArguments(ExtensionRegistryLite extensionRegistryLite, ByteString byteString) {
        if (extensionRegistryLite != null) {
            if (byteString != null) {
                return;
            }
            throw new NullPointerException("found null ByteString");
        }
        throw new NullPointerException("found null ExtensionRegistry");
    }

    public static LazyFieldLite fromValue(MessageLite messageLite) {
        LazyFieldLite lazyFieldLite = new LazyFieldLite();
        lazyFieldLite.setValue(messageLite);
        return lazyFieldLite;
    }

    private static MessageLite mergeValueAndBytes(MessageLite messageLite, ByteString object, ExtensionRegistryLite extensionRegistryLite) {
        try {
            object = messageLite.toBuilder().mergeFrom((ByteString)object, extensionRegistryLite).build();
            return object;
        }
        catch (InvalidProtocolBufferException invalidProtocolBufferException) {
            return messageLite;
        }
    }

    public void clear() {
        this.delayedBytes = null;
        this.value = null;
        this.memoizedBytes = null;
    }

    public boolean containsDefaultInstance() {
        ByteString byteString;
        boolean bl = this.memoizedBytes == ByteString.EMPTY || this.value == null && ((byteString = this.delayedBytes) == null || byteString == ByteString.EMPTY);
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void ensureInitialized(MessageLite messageLite) {
        if (this.value != null) {
            return;
        }
        synchronized (this) {
            if (this.value != null) {
                return;
            }
            try {
                if (this.delayedBytes != null) {
                    this.value = messageLite.getParserForType().parseFrom(this.delayedBytes, this.extensionRegistry);
                    this.memoizedBytes = this.delayedBytes;
                } else {
                    this.value = messageLite;
                    this.memoizedBytes = ByteString.EMPTY;
                }
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                this.value = messageLite;
                this.memoizedBytes = ByteString.EMPTY;
            }
            return;
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LazyFieldLite)) {
            return false;
        }
        LazyFieldLite lazyFieldLite = (LazyFieldLite)object;
        MessageLite messageLite = this.value;
        object = lazyFieldLite.value;
        if (messageLite == null && object == null) {
            return this.toByteString().equals(lazyFieldLite.toByteString());
        }
        if (messageLite != null && object != null) {
            return messageLite.equals(object);
        }
        if (messageLite != null) {
            return messageLite.equals(lazyFieldLite.getValue(messageLite.getDefaultInstanceForType()));
        }
        return this.getValue(object.getDefaultInstanceForType()).equals(object);
    }

    public int getSerializedSize() {
        if (this.memoizedBytes != null) {
            return this.memoizedBytes.size();
        }
        ByteString byteString = this.delayedBytes;
        if (byteString != null) {
            return byteString.size();
        }
        if (this.value != null) {
            return this.value.getSerializedSize();
        }
        return 0;
    }

    public MessageLite getValue(MessageLite messageLite) {
        this.ensureInitialized(messageLite);
        return this.value;
    }

    public int hashCode() {
        return 1;
    }

    public void merge(LazyFieldLite lazyFieldLite) {
        ByteString byteString;
        ByteString byteString2;
        if (lazyFieldLite.containsDefaultInstance()) {
            return;
        }
        if (this.containsDefaultInstance()) {
            this.set(lazyFieldLite);
            return;
        }
        if (this.extensionRegistry == null) {
            this.extensionRegistry = lazyFieldLite.extensionRegistry;
        }
        if ((byteString2 = this.delayedBytes) != null && (byteString = lazyFieldLite.delayedBytes) != null) {
            this.delayedBytes = byteString2.concat(byteString);
            return;
        }
        if (this.value == null && lazyFieldLite.value != null) {
            this.setValue(LazyFieldLite.mergeValueAndBytes(lazyFieldLite.value, this.delayedBytes, this.extensionRegistry));
            return;
        }
        if (this.value != null && lazyFieldLite.value == null) {
            this.setValue(LazyFieldLite.mergeValueAndBytes(this.value, lazyFieldLite.delayedBytes, lazyFieldLite.extensionRegistry));
            return;
        }
        if (lazyFieldLite.extensionRegistry != null) {
            this.setValue(LazyFieldLite.mergeValueAndBytes(this.value, lazyFieldLite.toByteString(), lazyFieldLite.extensionRegistry));
            return;
        }
        if (this.extensionRegistry != null) {
            this.setValue(LazyFieldLite.mergeValueAndBytes(lazyFieldLite.value, this.toByteString(), this.extensionRegistry));
            return;
        }
        this.setValue(LazyFieldLite.mergeValueAndBytes(this.value, lazyFieldLite.toByteString(), EMPTY_REGISTRY));
    }

    public void mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        ByteString byteString;
        if (this.containsDefaultInstance()) {
            this.setByteString(codedInputStream.readBytes(), extensionRegistryLite);
            return;
        }
        if (this.extensionRegistry == null) {
            this.extensionRegistry = extensionRegistryLite;
        }
        if ((byteString = this.delayedBytes) != null) {
            this.setByteString(byteString.concat(codedInputStream.readBytes()), this.extensionRegistry);
            return;
        }
        try {
            this.setValue(this.value.toBuilder().mergeFrom(codedInputStream, extensionRegistryLite).build());
        }
        catch (InvalidProtocolBufferException invalidProtocolBufferException) {
            // empty catch block
        }
    }

    public void set(LazyFieldLite object) {
        this.delayedBytes = ((LazyFieldLite)object).delayedBytes;
        this.value = ((LazyFieldLite)object).value;
        this.memoizedBytes = ((LazyFieldLite)object).memoizedBytes;
        object = ((LazyFieldLite)object).extensionRegistry;
        if (object != null) {
            this.extensionRegistry = object;
        }
    }

    public void setByteString(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) {
        LazyFieldLite.checkArguments(extensionRegistryLite, byteString);
        this.delayedBytes = byteString;
        this.extensionRegistry = extensionRegistryLite;
        this.value = null;
        this.memoizedBytes = null;
    }

    public MessageLite setValue(MessageLite messageLite) {
        MessageLite messageLite2 = this.value;
        this.delayedBytes = null;
        this.memoizedBytes = null;
        this.value = messageLite;
        return messageLite2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ByteString toByteString() {
        if (this.memoizedBytes != null) {
            return this.memoizedBytes;
        }
        ByteString byteString = this.delayedBytes;
        if (byteString != null) {
            return byteString;
        }
        synchronized (this) {
            if (this.memoizedBytes != null) {
                return this.memoizedBytes;
            }
            this.memoizedBytes = this.value == null ? ByteString.EMPTY : this.value.toByteString();
            return this.memoizedBytes;
        }
    }
}

