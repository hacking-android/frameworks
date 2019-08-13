/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.HighSpeedVideoConfiguration;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableHighSpeedVideoConfiguration
implements MarshalQueryable<HighSpeedVideoConfiguration> {
    private static final int SIZE = 20;

    @Override
    public Marshaler<HighSpeedVideoConfiguration> createMarshaler(TypeReference<HighSpeedVideoConfiguration> typeReference, int n) {
        return new MarshalerHighSpeedVideoConfiguration(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<HighSpeedVideoConfiguration> typeReference, int n) {
        boolean bl = true;
        if (n != 1 || !typeReference.getType().equals(HighSpeedVideoConfiguration.class)) {
            bl = false;
        }
        return bl;
    }

    private class MarshalerHighSpeedVideoConfiguration
    extends Marshaler<HighSpeedVideoConfiguration> {
        protected MarshalerHighSpeedVideoConfiguration(TypeReference<HighSpeedVideoConfiguration> typeReference, int n) {
            super(MarshalQueryableHighSpeedVideoConfiguration.this, typeReference, n);
        }

        @Override
        public int getNativeSize() {
            return 20;
        }

        @Override
        public void marshal(HighSpeedVideoConfiguration highSpeedVideoConfiguration, ByteBuffer byteBuffer) {
            byteBuffer.putInt(highSpeedVideoConfiguration.getWidth());
            byteBuffer.putInt(highSpeedVideoConfiguration.getHeight());
            byteBuffer.putInt(highSpeedVideoConfiguration.getFpsMin());
            byteBuffer.putInt(highSpeedVideoConfiguration.getFpsMax());
            byteBuffer.putInt(highSpeedVideoConfiguration.getBatchSizeMax());
        }

        @Override
        public HighSpeedVideoConfiguration unmarshal(ByteBuffer byteBuffer) {
            return new HighSpeedVideoConfiguration(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getInt());
        }
    }

}

