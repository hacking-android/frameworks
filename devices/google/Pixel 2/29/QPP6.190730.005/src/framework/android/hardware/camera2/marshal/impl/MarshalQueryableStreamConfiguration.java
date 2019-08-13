/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableStreamConfiguration
implements MarshalQueryable<StreamConfiguration> {
    private static final int SIZE = 16;

    @Override
    public Marshaler<StreamConfiguration> createMarshaler(TypeReference<StreamConfiguration> typeReference, int n) {
        return new MarshalerStreamConfiguration(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<StreamConfiguration> typeReference, int n) {
        boolean bl = true;
        if (n != 1 || !typeReference.getType().equals(StreamConfiguration.class)) {
            bl = false;
        }
        return bl;
    }

    private class MarshalerStreamConfiguration
    extends Marshaler<StreamConfiguration> {
        protected MarshalerStreamConfiguration(TypeReference<StreamConfiguration> typeReference, int n) {
            super(MarshalQueryableStreamConfiguration.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 16;
        }

        @Override
        public void marshal(StreamConfiguration streamConfiguration, ByteBuffer byteBuffer) {
            byteBuffer.putInt(streamConfiguration.getFormat());
            byteBuffer.putInt(streamConfiguration.getWidth());
            byteBuffer.putInt(streamConfiguration.getHeight());
            byteBuffer.putInt((int)streamConfiguration.isInput());
        }

        @Override
        public StreamConfiguration unmarshal(ByteBuffer byteBuffer) {
            int n = byteBuffer.getInt();
            int n2 = byteBuffer.getInt();
            int n3 = byteBuffer.getInt();
            boolean bl = byteBuffer.getInt() != 0;
            return new StreamConfiguration(n, n2, n3, bl);
        }
    }

}

