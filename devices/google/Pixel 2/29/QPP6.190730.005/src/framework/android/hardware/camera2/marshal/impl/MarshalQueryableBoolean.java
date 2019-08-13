/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableBoolean
implements MarshalQueryable<Boolean> {
    @Override
    public Marshaler<Boolean> createMarshaler(TypeReference<Boolean> typeReference, int n) {
        return new MarshalerBoolean(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<Boolean> typeReference, int n) {
        boolean bl = (Boolean.class.equals((Object)typeReference.getType()) || Boolean.TYPE.equals((Object)typeReference.getType())) && n == 0;
        return bl;
    }

    private class MarshalerBoolean
    extends Marshaler<Boolean> {
        protected MarshalerBoolean(TypeReference<Boolean> typeReference, int n) {
            super(MarshalQueryableBoolean.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 1;
        }

        @Override
        public void marshal(Boolean bl, ByteBuffer byteBuffer) {
            byteBuffer.put((byte)(bl.booleanValue() ? 1 : 0));
        }

        @Override
        public Boolean unmarshal(ByteBuffer byteBuffer) {
            boolean bl = byteBuffer.get() != 0;
            return bl;
        }
    }

}

