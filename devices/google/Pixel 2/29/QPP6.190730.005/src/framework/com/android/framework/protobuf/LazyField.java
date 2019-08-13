/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.ExtensionRegistryLite;
import com.android.framework.protobuf.LazyFieldLite;
import com.android.framework.protobuf.MessageLite;
import java.util.Iterator;
import java.util.Map;

public class LazyField
extends LazyFieldLite {
    private final MessageLite defaultInstance;

    public LazyField(MessageLite messageLite, ExtensionRegistryLite extensionRegistryLite, ByteString byteString) {
        super(extensionRegistryLite, byteString);
        this.defaultInstance = messageLite;
    }

    @Override
    public boolean containsDefaultInstance() {
        boolean bl = super.containsDefaultInstance() || this.value == this.defaultInstance;
        return bl;
    }

    @Override
    public boolean equals(Object object) {
        return this.getValue().equals(object);
    }

    public MessageLite getValue() {
        return this.getValue(this.defaultInstance);
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }

    public String toString() {
        return this.getValue().toString();
    }

    static class LazyEntry<K>
    implements Map.Entry<K, Object> {
        private Map.Entry<K, LazyField> entry;

        private LazyEntry(Map.Entry<K, LazyField> entry) {
            this.entry = entry;
        }

        public LazyField getField() {
            return this.entry.getValue();
        }

        @Override
        public K getKey() {
            return this.entry.getKey();
        }

        @Override
        public Object getValue() {
            LazyField lazyField = this.entry.getValue();
            if (lazyField == null) {
                return null;
            }
            return lazyField.getValue();
        }

        @Override
        public Object setValue(Object object) {
            if (object instanceof MessageLite) {
                return this.entry.getValue().setValue((MessageLite)object);
            }
            throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
        }
    }

    static class LazyIterator<K>
    implements Iterator<Map.Entry<K, Object>> {
        private Iterator<Map.Entry<K, Object>> iterator;

        public LazyIterator(Iterator<Map.Entry<K, Object>> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public Map.Entry<K, Object> next() {
            Map.Entry<K, Object> entry = this.iterator.next();
            if (entry.getValue() instanceof LazyField) {
                return new LazyEntry(entry);
            }
            return entry;
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }
    }

}

