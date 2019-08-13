/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.params.ReprocessFormatsMap;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class MarshalQueryableReprocessFormatsMap
implements MarshalQueryable<ReprocessFormatsMap> {
    @Override
    public Marshaler<ReprocessFormatsMap> createMarshaler(TypeReference<ReprocessFormatsMap> typeReference, int n) {
        return new MarshalerReprocessFormatsMap(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<ReprocessFormatsMap> typeReference, int n) {
        boolean bl = true;
        if (n != 1 || !typeReference.getType().equals(ReprocessFormatsMap.class)) {
            bl = false;
        }
        return bl;
    }

    private class MarshalerReprocessFormatsMap
    extends Marshaler<ReprocessFormatsMap> {
        protected MarshalerReprocessFormatsMap(TypeReference<ReprocessFormatsMap> typeReference, int n) {
            super(MarshalQueryableReprocessFormatsMap.this, typeReference, n);
        }

        @Override
        public int calculateMarshalSize(ReprocessFormatsMap reprocessFormatsMap) {
            int n = 0;
            int[] arrn = reprocessFormatsMap.getInputs();
            int n2 = arrn.length;
            for (int i = 0; i < n2; ++i) {
                n = n + 1 + 1 + reprocessFormatsMap.getOutputs(arrn[i]).length;
            }
            return n * 4;
        }

        @Override
        public int getNativeSize() {
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override
        public void marshal(ReprocessFormatsMap reprocessFormatsMap, ByteBuffer byteBuffer) {
            for (int n : StreamConfigurationMap.imageFormatToInternal(reprocessFormatsMap.getInputs())) {
                byteBuffer.putInt(n);
                int[] arrn = StreamConfigurationMap.imageFormatToInternal(reprocessFormatsMap.getOutputs(n));
                byteBuffer.putInt(arrn.length);
                int n2 = arrn.length;
                for (n = 0; n < n2; ++n) {
                    byteBuffer.putInt(arrn[n]);
                }
            }
        }

        @Override
        public ReprocessFormatsMap unmarshal(ByteBuffer byteBuffer) {
            int n = byteBuffer.remaining() / 4;
            if (byteBuffer.remaining() % 4 == 0) {
                int[] arrn = new int[n];
                byteBuffer.asIntBuffer().get(arrn);
                return new ReprocessFormatsMap(arrn);
            }
            throw new AssertionError((Object)"ReprocessFormatsMap was not TYPE_INT32");
        }
    }

}

