/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.BlackLevelPattern;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableBlackLevelPattern
implements MarshalQueryable<BlackLevelPattern> {
    private static final int SIZE = 16;

    @Override
    public Marshaler<BlackLevelPattern> createMarshaler(TypeReference<BlackLevelPattern> typeReference, int n) {
        return new MarshalerBlackLevelPattern(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<BlackLevelPattern> typeReference, int n) {
        boolean bl = true;
        if (n != 1 || !BlackLevelPattern.class.equals((Object)typeReference.getType())) {
            bl = false;
        }
        return bl;
    }

    private class MarshalerBlackLevelPattern
    extends Marshaler<BlackLevelPattern> {
        protected MarshalerBlackLevelPattern(TypeReference<BlackLevelPattern> typeReference, int n) {
            super(MarshalQueryableBlackLevelPattern.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 16;
        }

        @Override
        public void marshal(BlackLevelPattern blackLevelPattern, ByteBuffer byteBuffer) {
            for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < 2; ++j) {
                    byteBuffer.putInt(blackLevelPattern.getOffsetForIndex(j, i));
                }
            }
        }

        @Override
        public BlackLevelPattern unmarshal(ByteBuffer byteBuffer) {
            int[] arrn = new int[4];
            for (int i = 0; i < 4; ++i) {
                arrn[i] = byteBuffer.getInt();
            }
            return new BlackLevelPattern(arrn);
        }
    }

}

