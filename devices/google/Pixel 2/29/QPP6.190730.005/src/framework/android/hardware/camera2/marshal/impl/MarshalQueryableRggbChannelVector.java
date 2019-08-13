/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.RggbChannelVector;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableRggbChannelVector
implements MarshalQueryable<RggbChannelVector> {
    private static final int SIZE = 16;

    @Override
    public Marshaler<RggbChannelVector> createMarshaler(TypeReference<RggbChannelVector> typeReference, int n) {
        return new MarshalerRggbChannelVector(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<RggbChannelVector> typeReference, int n) {
        boolean bl = n == 2 && RggbChannelVector.class.equals((Object)typeReference.getType());
        return bl;
    }

    private class MarshalerRggbChannelVector
    extends Marshaler<RggbChannelVector> {
        protected MarshalerRggbChannelVector(TypeReference<RggbChannelVector> typeReference, int n) {
            super(MarshalQueryableRggbChannelVector.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 16;
        }

        @Override
        public void marshal(RggbChannelVector rggbChannelVector, ByteBuffer byteBuffer) {
            for (int i = 0; i < 4; ++i) {
                byteBuffer.putFloat(rggbChannelVector.getComponent(i));
            }
        }

        @Override
        public RggbChannelVector unmarshal(ByteBuffer byteBuffer) {
            return new RggbChannelVector(byteBuffer.getFloat(), byteBuffer.getFloat(), byteBuffer.getFloat(), byteBuffer.getFloat());
        }
    }

}

