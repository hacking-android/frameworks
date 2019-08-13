/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.lang.reflect.Type;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class MarshalQueryableString
implements MarshalQueryable<String> {
    private static final boolean DEBUG = false;
    private static final byte NUL = 0;
    private static final String TAG = MarshalQueryableString.class.getSimpleName();

    @Override
    public Marshaler<String> createMarshaler(TypeReference<String> typeReference, int n) {
        return new MarshalerString(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<String> typeReference, int n) {
        boolean bl = n == 0 && String.class.equals((Object)typeReference.getType());
        return bl;
    }

    private class MarshalerString
    extends Marshaler<String> {
        protected MarshalerString(TypeReference<String> typeReference, int n) {
            super(MarshalQueryableString.this, typeReference, n);
        }

        @Override
        public int calculateMarshalSize(String string2) {
            return string2.getBytes(PreloadHolder.UTF8_CHARSET).length + 1;
        }

        @Override
        public int getNativeSize() {
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override
        public void marshal(String string2, ByteBuffer byteBuffer) {
            byteBuffer.put(string2.getBytes(PreloadHolder.UTF8_CHARSET));
            byteBuffer.put((byte)0);
        }

        @Override
        public String unmarshal(ByteBuffer byteBuffer) {
            boolean bl;
            byteBuffer.mark();
            boolean bl2 = false;
            int n = 0;
            do {
                bl = bl2;
                if (!byteBuffer.hasRemaining()) break;
                if (byteBuffer.get() == 0) {
                    bl = true;
                    break;
                }
                ++n;
            } while (true);
            if (bl) {
                byteBuffer.reset();
                byte[] arrby = new byte[n + 1];
                byteBuffer.get(arrby, 0, n + 1);
                return new String(arrby, 0, n, PreloadHolder.UTF8_CHARSET);
            }
            throw new UnsupportedOperationException("Strings must be null-terminated");
        }
    }

    private static class PreloadHolder {
        public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

        private PreloadHolder() {
        }
    }

}

