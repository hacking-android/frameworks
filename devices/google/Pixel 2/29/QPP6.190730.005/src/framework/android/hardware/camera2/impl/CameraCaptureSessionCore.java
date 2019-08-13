/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.impl.CameraDeviceImpl;

public interface CameraCaptureSessionCore {
    public CameraDeviceImpl.StateCallbackKK getDeviceStateCallback();

    public boolean isAborting();

    public void replaceSessionClose();
}

