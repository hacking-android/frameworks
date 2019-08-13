/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.MarshalRegistry;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.util.Pair;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryablePair<T1, T2>
implements MarshalQueryable<Pair<T1, T2>> {
    @Override
    public Marshaler<Pair<T1, T2>> createMarshaler(TypeReference<Pair<T1, T2>> typeReference, int n) {
        return new MarshalerPair(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<Pair<T1, T2>> typeReference, int n) {
        return Pair.class.equals(typeReference.getRawType());
    }

    private class MarshalerPair
    extends Marshaler<Pair<T1, T2>> {
        private final Class<? super Pair<T1, T2>> mClass;
        private final Constructor<Pair<T1, T2>> mConstructor;
        private final Marshaler<T1> mNestedTypeMarshalerFirst;
        private final Marshaler<T2> mNestedTypeMarshalerSecond;

        protected MarshalerPair(TypeReference<Pair<T1, T2>> object2, int n) {
            super(MarshalQueryablePair.this, object2, n);
            this.mClass = ((TypeReference)object2).getRawType();
            try {
                MarshalQueryablePair.this = (ParameterizedType)((TypeReference)object2).getType();
            }
            catch (ClassCastException classCastException) {
                throw new AssertionError("Raw use of Pair is not supported", classCastException);
            }
            object2 = MarshalQueryablePair.this.getActualTypeArguments()[0];
            this.mNestedTypeMarshalerFirst = MarshalRegistry.getMarshaler(TypeReference.createSpecializedTypeReference((Type)object2), this.mNativeType);
            MarshalQueryablePair.this = MarshalQueryablePair.this.getActualTypeArguments()[1];
            this.mNestedTypeMarshalerSecond = MarshalRegistry.getMarshaler(TypeReference.createSpecializedTypeReference((Type)MarshalQueryablePair.this), this.mNativeType);
            try {
                this.mConstructor = this.mClass.getConstructor(Object.class, Object.class);
                return;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                throw new AssertionError(noSuchMethodException);
            }
        }

        @Override
        public int calculateMarshalSize(Pair<T1, T2> pair) {
            int n = this.getNativeSize();
            if (n != NATIVE_SIZE_DYNAMIC) {
                return n;
            }
            return this.mNestedTypeMarshalerFirst.calculateMarshalSize(pair.first) + this.mNestedTypeMarshalerSecond.calculateMarshalSize(pair.second);
        }

        @Override
        public int getNativeSize() {
            int n = this.mNestedTypeMarshalerFirst.getNativeSize();
            int n2 = this.mNestedTypeMarshalerSecond.getNativeSize();
            if (n != NATIVE_SIZE_DYNAMIC && n2 != NATIVE_SIZE_DYNAMIC) {
                return n + n2;
            }
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override
        public void marshal(Pair<T1, T2> pair, ByteBuffer byteBuffer) {
            if (pair.first != null) {
                if (pair.second != null) {
                    this.mNestedTypeMarshalerFirst.marshal(pair.first, byteBuffer);
                    this.mNestedTypeMarshalerSecond.marshal(pair.second, byteBuffer);
                    return;
                }
                throw new UnsupportedOperationException("Pair#second must not be null");
            }
            throw new UnsupportedOperationException("Pair#first must not be null");
        }

        @Override
        public Pair<T1, T2> unmarshal(ByteBuffer object) {
            T1 T1 = this.mNestedTypeMarshalerFirst.unmarshal((ByteBuffer)object);
            object = this.mNestedTypeMarshalerSecond.unmarshal((ByteBuffer)object);
            try {
                object = this.mConstructor.newInstance(T1, object);
                return object;
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new AssertionError(invocationTargetException);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new AssertionError(illegalArgumentException);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError(illegalAccessException);
            }
            catch (InstantiationException instantiationException) {
                throw new AssertionError(instantiationException);
            }
        }
    }

}

