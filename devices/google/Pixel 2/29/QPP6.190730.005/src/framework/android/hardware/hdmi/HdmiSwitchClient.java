/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.hardware.hdmi.HdmiClient;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.IHdmiControlCallback;
import android.hardware.hdmi.IHdmiControlService;
import android.hardware.hdmi._$$Lambda$HdmiSwitchClient$2$knvX6ZgANoRRFcb_fUHlUdWIjCQ;
import android.hardware.hdmi._$$Lambda$HdmiSwitchClient$2$wYF9AcLTW87bh8nh0L1O42__jdg;
import android.hardware.hdmi._$$Lambda$HdmiSwitchClient$3$Cqxvec1NmkC6VlEdX5OEOabobXY;
import android.hardware.hdmi._$$Lambda$HdmiSwitchClient$3$apecUZ8P9DH90drOKNmw2Y8Fspg;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
public class HdmiSwitchClient
extends HdmiClient {
    private static final String TAG = "HdmiSwitchClient";

    HdmiSwitchClient(IHdmiControlService iHdmiControlService) {
        super(iHdmiControlService);
    }

    private static IHdmiControlCallback getCallbackWrapper(OnSelectListener onSelectListener) {
        return new IHdmiControlCallback.Stub(){

            @Override
            public void onComplete(int n) {
                OnSelectListener.this.onSelect(n);
            }
        };
    }

    public List<HdmiDeviceInfo> getDeviceList() {
        try {
            List<HdmiDeviceInfo> list = this.mService.getDeviceList();
            return list;
        }
        catch (RemoteException remoteException) {
            Log.e("TAG", "Failed to call getDeviceList():", remoteException);
            return Collections.emptyList();
        }
    }

    @Override
    public int getDeviceType() {
        return 6;
    }

    public void selectDevice(int n, OnSelectListener onSelectListener) {
        Preconditions.checkNotNull(onSelectListener);
        try {
            this.mService.deviceSelect(n, HdmiSwitchClient.getCallbackWrapper(onSelectListener));
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "failed to select device: ", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void selectDevice(int n, final Executor executor, final OnSelectListener onSelectListener) {
        Preconditions.checkNotNull(onSelectListener);
        try {
            IHdmiControlService iHdmiControlService = this.mService;
            IHdmiControlCallback.Stub stub = new IHdmiControlCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(OnSelectListener onSelectListener2, int n) {
                    onSelectListener2.onSelect(n);
                }

                static /* synthetic */ void lambda$onComplete$1(Executor executor2, OnSelectListener onSelectListener2, int n) throws Exception {
                    executor2.execute(new _$$Lambda$HdmiSwitchClient$2$wYF9AcLTW87bh8nh0L1O42__jdg(onSelectListener2, n));
                }

                @Override
                public void onComplete(int n) {
                    Binder.withCleanCallingIdentity(new _$$Lambda$HdmiSwitchClient$2$knvX6ZgANoRRFcb_fUHlUdWIjCQ(executor, onSelectListener, n));
                }
            };
            iHdmiControlService.deviceSelect(n, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "failed to select device: ", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void selectPort(int n, OnSelectListener onSelectListener) {
        Preconditions.checkNotNull(onSelectListener);
        try {
            this.mService.portSelect(n, HdmiSwitchClient.getCallbackWrapper(onSelectListener));
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "failed to select port: ", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void selectPort(int n, final Executor executor, final OnSelectListener onSelectListener) {
        Preconditions.checkNotNull(onSelectListener);
        try {
            IHdmiControlService iHdmiControlService = this.mService;
            IHdmiControlCallback.Stub stub = new IHdmiControlCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(OnSelectListener onSelectListener2, int n) {
                    onSelectListener2.onSelect(n);
                }

                static /* synthetic */ void lambda$onComplete$1(Executor executor2, OnSelectListener onSelectListener2, int n) throws Exception {
                    executor2.execute(new _$$Lambda$HdmiSwitchClient$3$apecUZ8P9DH90drOKNmw2Y8Fspg(onSelectListener2, n));
                }

                @Override
                public void onComplete(int n) {
                    Binder.withCleanCallingIdentity(new _$$Lambda$HdmiSwitchClient$3$Cqxvec1NmkC6VlEdX5OEOabobXY(executor, onSelectListener, n));
                }
            };
            iHdmiControlService.portSelect(n, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "failed to select port: ", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static interface OnSelectListener {
        public void onSelect(@HdmiControlManager.ControlCallbackResult int var1);
    }

}

