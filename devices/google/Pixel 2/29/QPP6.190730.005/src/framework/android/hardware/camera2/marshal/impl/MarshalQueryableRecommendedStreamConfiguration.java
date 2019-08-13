/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.RecommendedStreamConfiguration;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableRecommendedStreamConfiguration
implements MarshalQueryable<RecommendedStreamConfiguration> {
    private static final int SIZE = 20;

    @Override
    public Marshaler<RecommendedStreamConfiguration> createMarshaler(TypeReference<RecommendedStreamConfiguration> typeReference, int n) {
        return new MarshalerRecommendedStreamConfiguration(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<RecommendedStreamConfiguration> typeReference, int n) {
        boolean bl = true;
        if (n != 1 || !typeReference.getType().equals(RecommendedStreamConfiguration.class)) {
            bl = false;
        }
        return bl;
    }

    private class MarshalerRecommendedStreamConfiguration
    extends Marshaler<RecommendedStreamConfiguration> {
        protected MarshalerRecommendedStreamConfiguration(TypeReference<RecommendedStreamConfiguration> typeReference, int n) {
            super(MarshalQueryableRecommendedStreamConfiguration.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 20;
        }

        @Override
        public void marshal(RecommendedStreamConfiguration recommendedStreamConfiguration, ByteBuffer byteBuffer) {
            byteBuffer.putInt(recommendedStreamConfiguration.getWidth());
            byteBuffer.putInt(recommendedStreamConfiguration.getHeight());
            byteBuffer.putInt(recommendedStreamConfiguration.getFormat());
            byteBuffer.putInt((int)recommendedStreamConfiguration.isInput());
            byteBuffer.putInt(recommendedStreamConfiguration.getUsecaseBitmap());
        }

        @Override
        public RecommendedStreamConfiguration unmarshal(ByteBuffer byteBuffer) {
            int n = byteBuffer.getInt();
            int n2 = byteBuffer.getInt();
            int n3 = byteBuffer.getInt();
            boolean bl = byteBuffer.getInt() != 0;
            return new RecommendedStreamConfiguration(n3, n, n2, bl, byteBuffer.getInt());
        }
    }

}

