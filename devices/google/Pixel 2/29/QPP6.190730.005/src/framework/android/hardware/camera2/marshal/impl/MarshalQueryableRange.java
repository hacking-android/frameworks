/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.MarshalRegistry;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.util.Range;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public class MarshalQueryableRange<T extends Comparable<? super T>>
implements MarshalQueryable<Range<T>> {
    private static final int RANGE_COUNT = 2;

    @Override
    public Marshaler<Range<T>> createMarshaler(TypeReference<Range<T>> typeReference, int n) {
        return new MarshalerRange(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<Range<T>> typeReference, int n) {
        return Range.class.equals(typeReference.getRawType());
    }

    private class MarshalerRange
    extends Marshaler<Range<T>> {
        private final Class<? super Range<T>> mClass;
        private final Constructor<Range<T>> mConstructor;
        private final Marshaler<T> mNestedTypeMarshaler;

        protected MarshalerRange(TypeReference<Range<T>> typeReference, int n) {
            super(MarshalQueryableRange.this, typeReference, n);
            this.mClass = typeReference.getRawType();
            try {
                MarshalQueryableRange.this = (ParameterizedType)typeReference.getType();
            }
            catch (ClassCastException classCastException) {
                throw new AssertionError("Raw use of Range is not supported", classCastException);
            }
            MarshalQueryableRange.this = MarshalQueryableRange.this.getActualTypeArguments()[0];
            this.mNestedTypeMarshaler = MarshalRegistry.getMarshaler(TypeReference.createSpecializedTypeReference((Type)MarshalQueryableRange.this), this.mNativeType);
            try {
                this.mConstructor = this.mClass.getConstructor(Comparable.class, Comparable.class);
                return;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                throw new AssertionError(noSuchMethodException);
            }
        }

        @Override
        public int calculateMarshalSize(Range<T> range) {
            int n = this.getNativeSize();
            if (n != NATIVE_SIZE_DYNAMIC) {
                return n;
            }
            return this.mNestedTypeMarshaler.calculateMarshalSize(range.getLower()) + this.mNestedTypeMarshaler.calculateMarshalSize(range.getUpper());
        }

        @Override
        public int getNativeSize() {
            int n = this.mNestedTypeMarshaler.getNativeSize();
            if (n != NATIVE_SIZE_DYNAMIC) {
                return n * 2;
            }
            return NATIVE_SIZE_DYNAMIC;
        }

        @Override
        public void marshal(Range<T> range, ByteBuffer byteBuffer) {
            this.mNestedTypeMarshaler.marshal(range.getLower(), byteBuffer);
            this.mNestedTypeMarshaler.marshal(range.getUpper(), byteBuffer);
        }

        @Override
        public Range<T> unmarshal(ByteBuffer object) {
            Comparable comparable = (Comparable)this.mNestedTypeMarshaler.unmarshal((ByteBuffer)object);
            object = (Comparable)this.mNestedTypeMarshaler.unmarshal((ByteBuffer)object);
            try {
                object = this.mConstructor.newInstance(comparable, object);
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

