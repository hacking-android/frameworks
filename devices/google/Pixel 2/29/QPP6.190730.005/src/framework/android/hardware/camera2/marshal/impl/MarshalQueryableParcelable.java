/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class MarshalQueryableParcelable<T extends Parcelable>
implements MarshalQueryable<T> {
    private static final boolean DEBUG = false;
    private static final String FIELD_CREATOR = "CREATOR";
    private static final String TAG = "MarshalParcelable";

    @Override
    public Marshaler<T> createMarshaler(TypeReference<T> typeReference, int n) {
        return new MarshalerParcelable(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<T> typeReference, int n) {
        return Parcelable.class.isAssignableFrom(typeReference.getRawType());
    }

    private class MarshalerParcelable
    extends Marshaler<T> {
        private final Class<T> mClass;
        private final Parcelable.Creator<T> mCreator;

        protected MarshalerParcelable(TypeReference<T> typeReference, int n) {
            super(MarshalQueryableParcelable.this, typeReference, n);
            this.mClass = typeReference.getRawType();
            try {
                MarshalQueryableParcelable.this = this.mClass.getDeclaredField(MarshalQueryableParcelable.FIELD_CREATOR);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                throw new AssertionError(noSuchFieldException);
            }
            try {
                this.mCreator = (Parcelable.Creator)((Field)MarshalQueryableParcelable.this).get(null);
                return;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new AssertionError(illegalArgumentException);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError(illegalAccessException);
            }
        }

        @Override
        public int calculateMarshalSize(T t) {
            Parcel parcel = Parcel.obtain();
            try {
                t.writeToParcel(parcel, 0);
                int n = parcel.marshall().length;
                return n;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public int getNativeSize() {
            return NATIVE_SIZE_DYNAMIC;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void marshal(T t, ByteBuffer object) {
            Parcel parcel = Parcel.obtain();
            try {
                t.writeToParcel(parcel, 0);
                if (!parcel.hasFileDescriptors()) {
                    byte[] arrby = parcel.marshall();
                    if (arrby.length != 0) {
                        ((ByteBuffer)object).put(arrby);
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("No data marshaled for ");
                    ((StringBuilder)object).append(t);
                    throw new AssertionError((Object)((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Parcelable ");
                ((StringBuilder)object).append(t);
                ((StringBuilder)object).append(" must not have file descriptors");
                UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException(((StringBuilder)object).toString());
                throw unsupportedOperationException;
            }
            finally {
                parcel.recycle();
            }
        }

        @Override
        public T unmarshal(ByteBuffer object) {
            ((Buffer)object).mark();
            Parcel parcel = Parcel.obtain();
            try {
                int n = ((Buffer)object).remaining();
                Object object2 = new byte[n];
                ((ByteBuffer)object).get((byte[])object2);
                parcel.unmarshall((byte[])object2, 0, n);
                parcel.setDataPosition(0);
                object2 = (Parcelable)this.mCreator.createFromParcel(parcel);
                n = parcel.dataPosition();
                if (n != 0) {
                    ((Buffer)object).reset();
                    ((Buffer)object).position(((Buffer)object).position() + n);
                    object = (Parcelable)this.mClass.cast(object2);
                    return (T)object;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("No data marshaled for ");
                stringBuilder.append(object2);
                object = new AssertionError((Object)stringBuilder.toString());
                throw object;
            }
            finally {
                parcel.recycle();
            }
        }
    }

}

