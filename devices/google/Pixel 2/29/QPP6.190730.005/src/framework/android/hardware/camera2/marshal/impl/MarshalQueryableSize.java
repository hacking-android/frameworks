/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.util.Size;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableSize
implements MarshalQueryable<Size> {
    private static final int SIZE = 8;

    @Override
    public Marshaler<Size> createMarshaler(TypeReference<Size> typeReference, int n) {
        return new MarshalerSize(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<Size> typeReference, int n) {
        boolean bl = true;
        if (n != 1 || !Size.class.equals((Object)typeReference.getType())) {
            bl = false;
        }
        return bl;
    }

    private class MarshalerSize
    extends Marshaler<Size> {
        protected MarshalerSize(TypeReference<Size> typeReference, int n) {
            super(MarshalQueryableSize.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 8;
        }

        @Override
        public void marshal(Size size, ByteBuffer byteBuffer) {
            byteBuffer.putInt(size.getWidth());
            byteBuffer.putInt(size.getHeight());
        }

        @Override
        public Size unmarshal(ByteBuffer byteBuffer) {
            return new Size(byteBuffer.getInt(), byteBuffer.getInt());
        }
    }

}

