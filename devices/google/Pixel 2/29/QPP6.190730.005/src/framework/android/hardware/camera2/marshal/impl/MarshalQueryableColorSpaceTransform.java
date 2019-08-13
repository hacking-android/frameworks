/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.ColorSpaceTransform;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableColorSpaceTransform
implements MarshalQueryable<ColorSpaceTransform> {
    private static final int ELEMENTS_INT32 = 18;
    private static final int SIZE = 72;

    @Override
    public Marshaler<ColorSpaceTransform> createMarshaler(TypeReference<ColorSpaceTransform> typeReference, int n) {
        return new MarshalerColorSpaceTransform(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<ColorSpaceTransform> typeReference, int n) {
        boolean bl = n == 5 && ColorSpaceTransform.class.equals((Object)typeReference.getType());
        return bl;
    }

    private class MarshalerColorSpaceTransform
    extends Marshaler<ColorSpaceTransform> {
        protected MarshalerColorSpaceTransform(TypeReference<ColorSpaceTransform> typeReference, int n) {
            super(MarshalQueryableColorSpaceTransform.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 72;
        }

        @Override
        public void marshal(ColorSpaceTransform colorSpaceTransform, ByteBuffer byteBuffer) {
            int[] arrn = new int[18];
            colorSpaceTransform.copyElements(arrn, 0);
            for (int i = 0; i < 18; ++i) {
                byteBuffer.putInt(arrn[i]);
            }
        }

        @Override
        public ColorSpaceTransform unmarshal(ByteBuffer byteBuffer) {
            int[] arrn = new int[18];
            for (int i = 0; i < 18; ++i) {
                arrn[i] = byteBuffer.getInt();
            }
            return new ColorSpaceTransform(arrn);
        }
    }

}

