/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalHelpers;
import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class MarshalQueryableEnum<T extends Enum<T>>
implements MarshalQueryable<T> {
    private static final boolean DEBUG = false;
    private static final String TAG = MarshalQueryableEnum.class.getSimpleName();
    private static final int UINT8_MASK = 255;
    private static final int UINT8_MAX = 255;
    private static final int UINT8_MIN = 0;
    private static final HashMap<Class<? extends Enum>, int[]> sEnumValues = new HashMap();

    private static <T extends Enum<T>> T getEnumFromValue(Class<T> class_, int n) {
        int[] arrn;
        int n2;
        block5 : {
            arrn = sEnumValues.get(class_);
            if (arrn != null) {
                int n3 = -1;
                int n4 = 0;
                do {
                    n2 = n3;
                    if (n4 >= arrn.length) break block5;
                    if (arrn[n4] == n) {
                        n2 = n4;
                        break block5;
                    }
                    ++n4;
                } while (true);
            }
            n2 = n;
        }
        Enum[] arrenum = (Enum[])class_.getEnumConstants();
        if (n2 >= 0 && n2 < arrenum.length) {
            return (T)arrenum[n2];
        }
        boolean bl = true;
        if (arrn == null) {
            bl = false;
        }
        throw new IllegalArgumentException(String.format("Argument 'value' (%d) was not a valid enum value for type %s (registered? %b)", n, class_, bl));
    }

    private static <T extends Enum<T>> int getEnumValue(T t) {
        int[] arrn = sEnumValues.get(t.getClass());
        int n = ((Enum)t).ordinal();
        if (arrn != null) {
            return arrn[n];
        }
        return n;
    }

    public static <T extends Enum<T>> void registerEnumValues(Class<T> class_, int[] arrn) {
        if (((Enum[])class_.getEnumConstants()).length == arrn.length) {
            sEnumValues.put(class_, arrn);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected values array to be the same size as the enumTypes values ");
        stringBuilder.append(arrn.length);
        stringBuilder.append(" for type ");
        stringBuilder.append(class_);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public Marshaler<T> createMarshaler(TypeReference<T> typeReference, int n) {
        return new MarshalerEnum(typeReference, n);
    }

    @Override
    public boolean isTypeMappingSupported(TypeReference<T> object, int n) {
        if ((n == 1 || n == 0) && ((TypeReference)object).getType() instanceof Class && ((Class)(object = (Class)((TypeReference)object).getType())).isEnum()) {
            try {
                ((Class)object).getDeclaredConstructor(String.class, Integer.TYPE);
                return true;
            }
            catch (SecurityException securityException) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Can't marshal class ");
                stringBuilder.append(object);
                stringBuilder.append("; not accessible");
                Log.e(string2, stringBuilder.toString());
            }
            catch (NoSuchMethodException noSuchMethodException) {
                String string3 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Can't marshal class ");
                stringBuilder.append(object);
                stringBuilder.append("; no default constructor");
                Log.e(string3, stringBuilder.toString());
            }
        }
        return false;
    }

    private class MarshalerEnum
    extends Marshaler<T> {
        private final Class<T> mClass;

        protected MarshalerEnum(TypeReference<T> typeReference, int n) {
            super(MarshalQueryableEnum.this, typeReference, n);
            this.mClass = typeReference.getRawType();
        }

        @Override
        public int getNativeSize() {
            return MarshalHelpers.getPrimitiveTypeSize(this.mNativeType);
        }

        @Override
        public void marshal(T t, ByteBuffer byteBuffer) {
            block4 : {
                int n;
                block5 : {
                    block3 : {
                        block2 : {
                            n = MarshalQueryableEnum.getEnumValue(t);
                            if (this.mNativeType != 1) break block2;
                            byteBuffer.putInt(n);
                            break block3;
                        }
                        if (this.mNativeType != 0) break block4;
                        if (n < 0 || n > 255) break block5;
                        byteBuffer.put((byte)n);
                    }
                    return;
                }
                throw new UnsupportedOperationException(String.format("Enum value %x too large to fit into unsigned byte", n));
            }
            throw new AssertionError();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public T unmarshal(ByteBuffer byteBuffer) {
            int n = this.mNativeType;
            if (n != 0) {
                if (n != 1) throw new AssertionError((Object)"Unexpected native type; impossible since its not supported");
                n = byteBuffer.getInt();
                return (T)MarshalQueryableEnum.getEnumFromValue(this.mClass, n);
            } else {
                n = byteBuffer.get() & 255;
            }
            return (T)MarshalQueryableEnum.getEnumFromValue(this.mClass, n);
        }
    }

}

