/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal.impl;

import android.hardware.camera2.marshal.MarshalHelpers;
import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import android.util.Rational;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public final class MarshalQueryablePrimitive<T>
implements MarshalQueryable<T> {
    @Override
    public Marshaler<T> createMarshaler(TypeReference<T> typeReference, int n) {
        return new MarshalerPrimitive(typeReference, n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean isTypeMappingSupported(TypeReference<T> object, int n) {
        boolean bl = ((TypeReference)object).getType() instanceof Class;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        boolean bl7 = false;
        if (!bl) return false;
        if ((object = (Class)((TypeReference)object).getType()) != Byte.TYPE && object != Byte.class) {
            if (object != Integer.TYPE && object != Integer.class) {
                if (object != Float.TYPE && object != Float.class) {
                    if (object != Long.TYPE && object != Long.class) {
                        if (object != Double.TYPE && object != Double.class) {
                            if (object != Rational.class) return false;
                            if (n != 5) return bl7;
                            return true;
                        }
                        bl7 = bl2;
                        if (n != 4) return bl7;
                        return true;
                    }
                    bl7 = bl3;
                    if (n != 3) return bl7;
                    return true;
                }
                bl7 = bl4;
                if (n != 2) return bl7;
                return true;
            }
            bl7 = bl5;
            if (n != 1) return bl7;
            return true;
        }
        bl7 = bl6;
        if (n != 0) return bl7;
        return true;
    }

    private class MarshalerPrimitive
    extends Marshaler<T> {
        private final Class<T> mClass;

        protected MarshalerPrimitive(TypeReference<T> typeReference, int n) {
            super(MarshalQueryablePrimitive.this, typeReference, n);
            this.mClass = MarshalHelpers.wrapClassIfPrimitive(typeReference.getRawType());
        }

        private void marshalPrimitive(byte by, ByteBuffer byteBuffer) {
            byteBuffer.put(by);
        }

        private void marshalPrimitive(double d, ByteBuffer byteBuffer) {
            byteBuffer.putDouble(d);
        }

        private void marshalPrimitive(float f, ByteBuffer byteBuffer) {
            byteBuffer.putFloat(f);
        }

        private void marshalPrimitive(int n, ByteBuffer byteBuffer) {
            byteBuffer.putInt(n);
        }

        private void marshalPrimitive(long l, ByteBuffer byteBuffer) {
            byteBuffer.putLong(l);
        }

        private void marshalPrimitive(Rational rational, ByteBuffer byteBuffer) {
            byteBuffer.putInt(rational.getNumerator());
            byteBuffer.putInt(rational.getDenominator());
        }

        private Object unmarshalObject(ByteBuffer object) {
            int n = this.mNativeType;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) {
                                if (n == 5) {
                                    return new Rational(((ByteBuffer)object).getInt(), ((ByteBuffer)object).getInt());
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Can't unmarshal native type ");
                                ((StringBuilder)object).append(this.mNativeType);
                                throw new UnsupportedOperationException(((StringBuilder)object).toString());
                            }
                            return ((ByteBuffer)object).getDouble();
                        }
                        return ((ByteBuffer)object).getLong();
                    }
                    return Float.valueOf(((ByteBuffer)object).getFloat());
                }
                return ((ByteBuffer)object).getInt();
            }
            return ((ByteBuffer)object).get();
        }

        @Override
        public int calculateMarshalSize(T t) {
            return MarshalHelpers.getPrimitiveTypeSize(this.mNativeType);
        }

        @Override
        public int getNativeSize() {
            return MarshalHelpers.getPrimitiveTypeSize(this.mNativeType);
        }

        @Override
        public void marshal(T object, ByteBuffer byteBuffer) {
            block8 : {
                block3 : {
                    block7 : {
                        block6 : {
                            block5 : {
                                block4 : {
                                    block2 : {
                                        if (!(object instanceof Integer)) break block2;
                                        MarshalHelpers.checkNativeTypeEquals(1, this.mNativeType);
                                        this.marshalPrimitive((Integer)object, byteBuffer);
                                        break block3;
                                    }
                                    if (!(object instanceof Float)) break block4;
                                    MarshalHelpers.checkNativeTypeEquals(2, this.mNativeType);
                                    this.marshalPrimitive(((Float)object).floatValue(), byteBuffer);
                                    break block3;
                                }
                                if (!(object instanceof Long)) break block5;
                                MarshalHelpers.checkNativeTypeEquals(3, this.mNativeType);
                                this.marshalPrimitive((Long)object, byteBuffer);
                                break block3;
                            }
                            if (!(object instanceof Rational)) break block6;
                            MarshalHelpers.checkNativeTypeEquals(5, this.mNativeType);
                            this.marshalPrimitive((Rational)object, byteBuffer);
                            break block3;
                        }
                        if (!(object instanceof Double)) break block7;
                        MarshalHelpers.checkNativeTypeEquals(4, this.mNativeType);
                        this.marshalPrimitive((Double)object, byteBuffer);
                        break block3;
                    }
                    if (!(object instanceof Byte)) break block8;
                    MarshalHelpers.checkNativeTypeEquals(0, this.mNativeType);
                    this.marshalPrimitive((Byte)object, byteBuffer);
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Can't marshal managed type ");
            ((StringBuilder)object).append(this.mTypeReference);
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }

        @Override
        public T unmarshal(ByteBuffer byteBuffer) {
            return this.mClass.cast(this.unmarshalObject(byteBuffer));
        }
    }

}

