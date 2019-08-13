/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.StreamConfigurationDuration;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableStreamConfigurationDuration
implements MarshalQueryable<StreamConfigurationDuration> {
    private static final long MASK_UNSIGNED_INT = 0xFFFFFFFFL;
    private static final int SIZE = 32;

    @Override
    public Marshaler<StreamConfigurationDuration> createMarshaler(TypeReference<StreamConfigurationDuration> typeReference, int n) {
        return new MarshalerStreamConfigurationDuration(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<StreamConfigurationDuration> typeReference, int n) {
        boolean bl = n == 3 && StreamConfigurationDuration.class.equals((Object)typeReference.getType());
        return bl;
    }

    private class MarshalerStreamConfigurationDuration
    extends Marshaler<StreamConfigurationDuration> {
        protected MarshalerStreamConfigurationDuration(TypeReference<StreamConfigurationDuration> typeReference, int n) {
            super(MarshalQueryableStreamConfigurationDuration.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 32;
        }

        @Override
        public void marshal(StreamConfigurationDuration streamConfigurationDuration, ByteBuffer byteBuffer) {
            byteBuffer.putLong((long)streamConfigurationDuration.getFormat() & 0xFFFFFFFFL);
            byteBuffer.putLong(streamConfigurationDuration.getWidth());
            byteBuffer.putLong(streamConfigurationDuration.getHeight());
            byteBuffer.putLong(streamConfigurationDuration.getDuration());
        }

        @Override
        public StreamConfigurationDuration unmarshal(ByteBuffer byteBuffer) {
            return new StreamConfigurationDuration((int)byteBuffer.getLong(), (int)byteBuffer.getLong(), (int)byteBuffer.getLong(), byteBuffer.getLong());
        }
    }

}

