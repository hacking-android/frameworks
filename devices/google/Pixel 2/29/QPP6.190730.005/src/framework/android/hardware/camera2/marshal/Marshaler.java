/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal;

import android.hardware.camera2.marshal.MarshalHelpers;
import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.utils.TypeReference;
import com.android.internal.util.Preconditions;
import java.nio.ByteBuffer;

public abstract class Marshaler<T> {
    public static int NATIVE_SIZE_DYNAMIC = -1;
    protected final int mNativeType;
    protected final TypeReference<T> mTypeReference;

    protected Marshaler(MarshalQueryable<T> object, TypeReference<T> typeReference, int n) {
        this.mTypeReference = Preconditions.checkNotNull(typeReference, "typeReference must not be null");
        this.mNativeType = MarshalHelpers.checkNativeType(n);
        if (object.isTypeMappingSupported(typeReference, n)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unsupported type marshaling for managed type ");
        ((StringBuilder)object).append(typeReference);
        ((StringBuilder)object).append(" and native type ");
        ((StringBuilder)object).append(MarshalHelpers.toStringNativeType(n));
        throw new UnsupportedOperationException(((StringBuilder)object).toString());
    }

    public int calculateMarshalSize(T t) {
        int n = this.getNativeSize();
        if (n != NATIVE_SIZE_DYNAMIC) {
            return n;
        }
        throw new AssertionError((Object)"Override this function for dynamically-sized objects");
    }

    public abstract int getNativeSize();

    public int getNativeType() {
        return this.mNativeType;
    }

    public TypeReference<T> getTypeReference() {
        return this.mTypeReference;
    }

    public abstract void marshal(T var1, ByteBuffer var2);

    public abstract T unmarshal(ByteBuffer var1);
}

