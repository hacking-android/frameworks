/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.IHdmiControlService;
import android.hardware.hdmi.IHdmiVendorCommandListener;
import android.os.RemoteException;
import android.util.Log;

@SystemApi
public abstract class HdmiClient {
    private static final String TAG = "HdmiClient";
    private IHdmiVendorCommandListener mIHdmiVendorCommandListener;
    final IHdmiControlService mService;

    HdmiClient(IHdmiControlService iHdmiControlService) {
        this.mService = iHdmiControlService;
    }

    private static IHdmiVendorCommandListener getListenerWrapper(HdmiControlManager.VendorCommandListener vendorCommandListener) {
        return new IHdmiVendorCommandListener.Stub(){

            @Override
            public void onControlStateChanged(boolean bl, int n) {
                VendorCommandListener.this.onControlStateChanged(bl, n);
            }

            @Override
            public void onReceived(int n, int n2, byte[] arrby, boolean bl) {
                VendorCommandListener.this.onReceived(n, n2, arrby, bl);
            }
        };
    }

    public HdmiDeviceInfo getActiveSource() {
        try {
            HdmiDeviceInfo hdmiDeviceInfo = this.mService.getActiveSource();
            return hdmiDeviceInfo;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "getActiveSource threw exception ", remoteException);
            return null;
        }
    }

    abstract int getDeviceType();

    public void sendKeyEvent(int n, boolean bl) {
        try {
            this.mService.sendKeyEvent(this.getDeviceType(), n, bl);
        }
        catch (RemoteException remoteException) {
            Log.e("HdmiClient", "sendKeyEvent threw exception ", remoteException);
        }
    }

    public void sendVendorCommand(int n, byte[] arrby, boolean bl) {
        try {
            this.mService.sendVendorCommand(this.getDeviceType(), n, arrby, bl);
        }
        catch (RemoteException remoteException) {
            Log.e("HdmiClient", "failed to send vendor command: ", remoteException);
        }
    }

    public void sendVolumeKeyEvent(int n, boolean bl) {
        try {
            this.mService.sendVolumeKeyEvent(this.getDeviceType(), n, bl);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e("HdmiClient", "sendVolumeKeyEvent threw exception ", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setVendorCommandListener(HdmiControlManager.VendorCommandListener object) {
        if (object != null) {
            if (this.mIHdmiVendorCommandListener == null) {
                try {
                    object = HdmiClient.getListenerWrapper((HdmiControlManager.VendorCommandListener)object);
                    this.mService.addVendorCommandListener((IHdmiVendorCommandListener)object, this.getDeviceType());
                    this.mIHdmiVendorCommandListener = object;
                }
                catch (RemoteException remoteException) {
                    Log.e("HdmiClient", "failed to set vendor command listener: ", remoteException);
                }
                return;
            }
            throw new IllegalStateException("listener was already set");
        }
        throw new IllegalArgumentException("listener cannot be null");
    }

}

