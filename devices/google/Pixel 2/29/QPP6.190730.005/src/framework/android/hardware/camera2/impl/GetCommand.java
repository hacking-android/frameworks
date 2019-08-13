/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.impl.CameraMetadataNative;

public interface GetCommand {
    public <T> T getValue(CameraMetadataNative var1, CameraMetadataNative.Key<T> var2);
}

