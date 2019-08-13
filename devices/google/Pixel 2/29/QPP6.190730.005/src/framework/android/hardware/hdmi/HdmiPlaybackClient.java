/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.hardware.hdmi.HdmiClient;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.IHdmiControlCallback;
import android.hardware.hdmi.IHdmiControlService;
import android.os.RemoteException;
import android.util.Log;

@SystemApi
public final class HdmiPlaybackClient
extends HdmiClient {
    private static final int ADDR_TV = 0;
    private static final String TAG = "HdmiPlaybackClient";

    HdmiPlaybackClient(IHdmiControlService iHdmiControlService) {
        super(iHdmiControlService);
    }

    private IHdmiControlCallback getCallbackWrapper(final DisplayStatusCallback displayStatusCallback) {
        return new IHdmiControlCallback.Stub(){

            @Override
            public void onComplete(int n) {
                displayStatusCallback.onComplete(n);
            }
        };
    }

    private IHdmiControlCallback getCallbackWrapper(final OneTouchPlayCallback oneTouchPlayCallback) {
        return new IHdmiControlCallback.Stub(){

            @Override
            public void onComplete(int n) {
                oneTouchPlayCallback.onComplete(n);
            }
        };
    }

    @Override
    public int getDeviceType() {
        return 4;
    }

    public void oneTouchPlay(OneTouchPlayCallback oneTouchPlayCallback) {
        try {
            this.mService.oneTouchPlay(this.getCallbackWrapper(oneTouchPlayCallback));
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "oneTouchPlay threw exception ", remoteException);
        }
    }

    public void queryDisplayStatus(DisplayStatusCallback displayStatusCallback) {
        try {
            this.mService.queryDisplayStatus(this.getCallbackWrapper(displayStatusCallback));
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "queryDisplayStatus threw exception ", remoteException);
        }
    }

    public void sendStandby() {
        try {
            this.mService.sendStandby(this.getDeviceType(), HdmiDeviceInfo.idForCecDevice(0));
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "sendStandby threw exception ", remoteException);
        }
    }

    public static interface DisplayStatusCallback {
        public void onComplete(int var1);
    }

    public static interface OneTouchPlayCallback {
        public void onComplete(int var1);
    }

}

