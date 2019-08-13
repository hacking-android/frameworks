/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableMeteringRectangle
implements MarshalQueryable<MeteringRectangle> {
    private static final int SIZE = 20;

    @Override
    public Marshaler<MeteringRectangle> createMarshaler(TypeReference<MeteringRectangle> typeReference, int n) {
        return new MarshalerMeteringRectangle(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<MeteringRectangle> typeReference, int n) {
        boolean bl = true;
        if (n != 1 || !MeteringRectangle.class.equals((Object)typeReference.getType())) {
            bl = false;
        }
        return bl;
    }

    private class MarshalerMeteringRectangle
    extends Marshaler<MeteringRectangle> {
        protected MarshalerMeteringRectangle(TypeReference<MeteringRectangle> typeReference, int n) {
            super(MarshalQueryableMeteringRectangle.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 20;
        }

        @Override
        public void marshal(MeteringRectangle meteringRectangle, ByteBuffer byteBuffer) {
            int n = meteringRectangle.getX();
            int n2 = meteringRectangle.getY();
            int n3 = meteringRectangle.getWidth();
            int n4 = meteringRectangle.getHeight();
            int n5 = meteringRectangle.getMeteringWeight();
            byteBuffer.putInt(n);
            byteBuffer.putInt(n2);
            byteBuffer.putInt(n3 + n);
            byteBuffer.putInt(n4 + n2);
            byteBuffer.putInt(n5);
        }

        @Override
        public MeteringRectangle unmarshal(ByteBuffer byteBuffer) {
            int n = byteBuffer.getInt();
            int n2 = byteBuffer.getInt();
            return new MeteringRectangle(n, n2, byteBuffer.getInt() - n, byteBuffer.getInt() - n2, byteBuffer.getInt());
        }
    }

}

