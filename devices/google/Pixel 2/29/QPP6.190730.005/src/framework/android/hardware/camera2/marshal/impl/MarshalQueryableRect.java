/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.graphics.Rect;
import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableRect
implements MarshalQueryable<Rect> {
    private static final int SIZE = 16;

    @Override
    public Marshaler<Rect> createMarshaler(TypeReference<Rect> typeReference, int n) {
        return new MarshalerRect(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<Rect> typeReference, int n) {
        boolean bl = true;
        if (n != 1 || !Rect.class.equals((Object)typeReference.getType())) {
            bl = false;
        }
        return bl;
    }

    private class MarshalerRect
    extends Marshaler<Rect> {
        protected MarshalerRect(TypeReference<Rect> typeReference, int n) {
            super(MarshalQueryableRect.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 16;
        }

        @Override
        public void marshal(Rect rect, ByteBuffer byteBuffer) {
            byteBuffer.putInt(rect.left);
            byteBuffer.putInt(rect.top);
            byteBuffer.putInt(rect.width());
            byteBuffer.putInt(rect.height());
        }

        @Override
        public Rect unmarshal(ByteBuffer byteBuffer) {
            int n = byteBuffer.getInt();
            int n2 = byteBuffer.getInt();
            return new Rect(n, n2, n + byteBuffer.getInt(), n2 + byteBuffer.getInt());
        }
    }

}

