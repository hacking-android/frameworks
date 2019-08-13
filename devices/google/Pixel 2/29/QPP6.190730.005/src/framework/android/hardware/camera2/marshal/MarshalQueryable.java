/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal;

import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;

public interface MarshalQueryable<T> {
    public Marshaler<T> createMarshaler(TypeReference<T> var1, int var2);

    public boolean isTypeMappingSupported(TypeReference<T> var1, int var2);
}

