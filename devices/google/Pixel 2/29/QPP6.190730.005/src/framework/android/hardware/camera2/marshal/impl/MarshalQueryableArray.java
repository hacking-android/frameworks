/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.MarshalRegistry;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.util.Log;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MarshalQueryableArray<T>
implements MarshalQueryable<T> {
    private static final boolean DEBUG = false;
    private static final String TAG = MarshalQueryableArray.class.getSimpleName();

    @Override
    public Marshaler<T> createMarshaler(TypeReference<T> typeReference, int n) {
        return new MarshalerArray(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<T> typeReference, int n) {
        return typeReference.getRawType().isArray();
    }

    private class MarshalerArray
    extends Marshaler<T> {
        private final Class<T> mClass;
        private final Class<?> mComponentClass;
        private final Marshaler<?> mComponentMarshaler;

        protected MarshalerArray(TypeReference<T> typeReference, int n) {
            super(MarshalQueryableArray.this, typeReference, n);
            this.mClass = typeReference.getRawType();
            MarshalQueryableArray.this = typeReference.getComponentType();
            this.mComponentMarshaler = MarshalRegistry.getMarshaler(MarshalQueryableArray.this, this.mNativeType);
            this.mComponentClass = ((TypeReference)MarshalQueryableArray.this).getRawType();
        }

        private <TElem> int calculateElementMarshalSize(Marshaler<TElem> marshaler, Object object, int n) {
            return marshaler.calculateMarshalSize(Array.get(object, n));
        }

        private Object copyListToArray(ArrayList<?> arrayList, Object object) {
            return arrayList.toArray((Object[])object);
        }

        private <TElem> void marshalArrayElement(Marshaler<TElem> marshaler, ByteBuffer byteBuffer, Object object, int n) {
            marshaler.marshal(Array.get(object, n), byteBuffer);
        }

        @Override
        public int calculateMarshalSize(T t) {
            int n = this.mComponentMarshaler.getNativeSize();
            int n2 = Array.getLength(t);
            if (n != Marshaler.NATIVE_SIZE_DYNAMIC) {
                return n * n2;
            }
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                n3 += this.calculateElementMarshalSize(this.mComponentMarshaler, t, n);
            }
            return n3;
        }

        @Override
        public int getNativeSize() {
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override
        public void marshal(T t, ByteBuffer byteBuffer) {
            int n = Array.getLength(t);
            for (int i = 0; i < n; ++i) {
                this.marshalArrayElement(this.mComponentMarshaler, byteBuffer, t, i);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public T unmarshal(ByteBuffer object) {
            Object object2;
            int n = this.mComponentMarshaler.getNativeSize();
            if (n != Marshaler.NATIVE_SIZE_DYNAMIC) {
                int n2 = ((Buffer)object).remaining();
                int n3 = n2 / n;
                if (n2 % n != 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Arrays for ");
                    ((StringBuilder)object).append(this.mTypeReference);
                    ((StringBuilder)object).append(" must be packed tighly into a multiple of ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append("; but there are ");
                    ((StringBuilder)object).append(n2 % n);
                    ((StringBuilder)object).append(" left over bytes");
                    throw new UnsupportedOperationException(((StringBuilder)object).toString());
                }
                object2 = Array.newInstance(this.mComponentClass, n3);
                for (n2 = 0; n2 < n3; ++n2) {
                    Array.set(object2, n2, this.mComponentMarshaler.unmarshal((ByteBuffer)object));
                }
            } else {
                object2 = new ArrayList();
                while (((Buffer)object).hasRemaining()) {
                    ((ArrayList)object2).add(this.mComponentMarshaler.unmarshal((ByteBuffer)object));
                }
                int n4 = ((ArrayList)object2).size();
                object2 = this.copyListToArray((ArrayList<?>)object2, Array.newInstance(this.mComponentClass, n4));
            }
            if (((Buffer)object).remaining() != 0) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Trailing bytes (");
                stringBuilder.append(((Buffer)object).remaining());
                stringBuilder.append(") left over after unpacking ");
                stringBuilder.append(this.mClass);
                Log.e(string2, stringBuilder.toString());
            }
            return this.mClass.cast(object2);
        }
    }

}

