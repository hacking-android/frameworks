/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableNativeByteToInteger
implements MarshalQueryable<Integer> {
    private static final int UINT8_MASK = 255;

    @Override
    public Marshaler<Integer> createMarshaler(TypeReference<Integer> typeReference, int n) {
        return new MarshalerNativeByteToInteger(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<Integer> typeReference, int n) {
        boolean bl = (Integer.class.equals((Object)typeReference.getType()) || Integer.TYPE.equals((Object)typeReference.getType())) && n == 0;
        return bl;
    }

    private class MarshalerNativeByteToInteger
    extends Marshaler<Integer> {
        protected MarshalerNativeByteToInteger(TypeReference<Integer> typeReference, int n) {
            super(MarshalQueryableNativeByteToInteger.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 1;
        }

        @Override
        public void marshal(Integer n, ByteBuffer byteBuffer) {
            byteBuffer.put((byte)n.intValue());
        }

        @Override
        public Integer unmarshal(ByteBuffer byteBuffer) {
            return byteBuffer.get() & 255;
        }
    }

}

