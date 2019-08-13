/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.ICameraDeviceUser;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.utils.SubmitInfo;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.view.Surface;

public class ICameraDeviceUserWrapper {
    private final ICameraDeviceUser mRemoteDevice;

    public ICameraDeviceUserWrapper(ICameraDeviceUser iCameraDeviceUser) {
        if (iCameraDeviceUser != null) {
            this.mRemoteDevice = iCameraDeviceUser;
            return;
        }
        throw new NullPointerException("Remote device may not be null");
    }

    public void beginConfigure() throws CameraAccessException {
        try {
            this.mRemoteDevice.beginConfigure();
            return;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public long cancelRequest(int n) throws CameraAccessException {
        try {
            long l = this.mRemoteDevice.cancelRequest(n);
            return l;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public CameraMetadataNative createDefaultRequest(int n) throws CameraAccessException {
        try {
            CameraMetadataNative cameraMetadataNative = this.mRemoteDevice.createDefaultRequest(n);
            return cameraMetadataNative;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public int createInputStream(int n, int n2, int n3) throws CameraAccessException {
        try {
            n = this.mRemoteDevice.createInputStream(n, n2, n3);
            return n;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public int createStream(OutputConfiguration outputConfiguration) throws CameraAccessException {
        try {
            int n = this.mRemoteDevice.createStream(outputConfiguration);
            return n;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public void deleteStream(int n) throws CameraAccessException {
        try {
            this.mRemoteDevice.deleteStream(n);
            return;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public void disconnect() {
        try {
            this.mRemoteDevice.disconnect();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void endConfigure(int n, CameraMetadataNative cameraMetadataNative) throws CameraAccessException {
        try {
            ICameraDeviceUser iCameraDeviceUser = this.mRemoteDevice;
            if (cameraMetadataNative == null) {
                cameraMetadataNative = new CameraMetadataNative();
            }
            iCameraDeviceUser.endConfigure(n, cameraMetadataNative);
            return;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public void finalizeOutputConfigurations(int n, OutputConfiguration outputConfiguration) throws CameraAccessException {
        try {
            this.mRemoteDevice.finalizeOutputConfigurations(n, outputConfiguration);
            return;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public long flush() throws CameraAccessException {
        try {
            long l = this.mRemoteDevice.flush();
            return l;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public CameraMetadataNative getCameraInfo() throws CameraAccessException {
        try {
            CameraMetadataNative cameraMetadataNative = this.mRemoteDevice.getCameraInfo();
            return cameraMetadataNative;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public Surface getInputSurface() throws CameraAccessException {
        try {
            Surface surface = this.mRemoteDevice.getInputSurface();
            return surface;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws CameraAccessException {
        try {
            boolean bl = this.mRemoteDevice.isSessionConfigurationSupported(sessionConfiguration);
            return bl;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
        catch (ServiceSpecificException serviceSpecificException) {
            if (serviceSpecificException.errorCode != 10) {
                if (serviceSpecificException.errorCode == 3) {
                    throw new IllegalArgumentException("Invalid session configuration");
                }
                throw serviceSpecificException;
            }
            throw new UnsupportedOperationException("Session configuration query not supported");
        }
    }

    public void prepare(int n) throws CameraAccessException {
        try {
            this.mRemoteDevice.prepare(n);
            return;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public void prepare2(int n, int n2) throws CameraAccessException {
        try {
            this.mRemoteDevice.prepare2(n, n2);
            return;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public SubmitInfo submitRequest(CaptureRequest parcelable, boolean bl) throws CameraAccessException {
        try {
            parcelable = this.mRemoteDevice.submitRequest((CaptureRequest)parcelable, bl);
            return parcelable;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public SubmitInfo submitRequestList(CaptureRequest[] object, boolean bl) throws CameraAccessException {
        try {
            object = this.mRemoteDevice.submitRequestList((CaptureRequest[])object, bl);
            return object;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public void tearDown(int n) throws CameraAccessException {
        try {
            this.mRemoteDevice.tearDown(n);
            return;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public void unlinkToDeath(IBinder.DeathRecipient deathRecipient, int n) {
        if (this.mRemoteDevice.asBinder() != null) {
            this.mRemoteDevice.asBinder().unlinkToDeath(deathRecipient, n);
        }
    }

    public void updateOutputConfiguration(int n, OutputConfiguration outputConfiguration) throws CameraAccessException {
        try {
            this.mRemoteDevice.updateOutputConfiguration(n, outputConfiguration);
            return;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }

    public void waitUntilIdle() throws CameraAccessException {
        try {
            this.mRemoteDevice.waitUntilIdle();
            return;
        }
        catch (Throwable throwable) {
            CameraManager.throwAsPublicException(throwable);
            throw new UnsupportedOperationException("Unexpected exception", throwable);
        }
    }
}

