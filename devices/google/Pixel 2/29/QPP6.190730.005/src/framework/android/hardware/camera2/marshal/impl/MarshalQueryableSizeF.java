/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.util.SizeF;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableSizeF
implements MarshalQueryable<SizeF> {
    private static final int SIZE = 8;

    @Override
    public Marshaler<SizeF> createMarshaler(TypeReference<SizeF> typeReference, int n) {
        return new MarshalerSizeF(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<SizeF> typeReference, int n) {
        boolean bl = n == 2 && SizeF.class.equals((Object)typeReference.getType());
        return bl;
    }

    private class MarshalerSizeF
    extends Marshaler<SizeF> {
        protected MarshalerSizeF(TypeReference<SizeF> typeReference, int n) {
            super(MarshalQueryableSizeF.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 8;
        }

        @Override
        public void marshal(SizeF sizeF, ByteBuffer byteBuffer) {
            byteBuffer.putFloat(sizeF.getWidth());
            byteBuffer.putFloat(sizeF.getHeight());
        }

        @Override
        public SizeF unmarshal(ByteBuffer byteBuffer) {
            return new SizeF(byteBuffer.getFloat(), byteBuffer.getFloat());
        }
    }

}

