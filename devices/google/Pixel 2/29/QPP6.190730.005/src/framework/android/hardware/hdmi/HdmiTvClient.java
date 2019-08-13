/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.hardware.hdmi.HdmiClient;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiRecordListener;
import android.hardware.hdmi.HdmiRecordSources;
import android.hardware.hdmi.HdmiTimerRecordSources;
import android.hardware.hdmi.IHdmiControlCallback;
import android.hardware.hdmi.IHdmiControlService;
import android.hardware.hdmi.IHdmiInputChangeListener;
import android.hardware.hdmi.IHdmiMhlVendorCommandListener;
import android.hardware.hdmi.IHdmiRecordListener;
import android.os.RemoteException;
import android.util.Log;
import java.util.Collections;
import java.util.List;
import libcore.util.EmptyArray;

@SystemApi
public final class HdmiTvClient
extends HdmiClient {
    private static final String TAG = "HdmiTvClient";
    public static final int VENDOR_DATA_SIZE = 16;

    HdmiTvClient(IHdmiControlService iHdmiControlService) {
        super(iHdmiControlService);
    }

    private void checkTimerRecordingSourceType(int n) {
        if (n != 1 && n != 2 && n != 3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid source type:");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    static HdmiTvClient create(IHdmiControlService iHdmiControlService) {
        return new HdmiTvClient(iHdmiControlService);
    }

    private static IHdmiControlCallback getCallbackWrapper(SelectCallback selectCallback) {
        return new IHdmiControlCallback.Stub(){

            @Override
            public void onComplete(int n) {
                SelectCallback.this.onComplete(n);
            }
        };
    }

    private static IHdmiInputChangeListener getListenerWrapper(InputChangeListener inputChangeListener) {
        return new IHdmiInputChangeListener.Stub(){

            @Override
            public void onChanged(HdmiDeviceInfo hdmiDeviceInfo) {
                InputChangeListener.this.onChanged(hdmiDeviceInfo);
            }
        };
    }

    private IHdmiMhlVendorCommandListener getListenerWrapper(final HdmiMhlVendorCommandListener hdmiMhlVendorCommandListener) {
        return new IHdmiMhlVendorCommandListener.Stub(){

            @Override
            public void onReceived(int n, int n2, int n3, byte[] arrby) {
                hdmiMhlVendorCommandListener.onReceived(n, n2, n3, arrby);
            }
        };
    }

    private static IHdmiRecordListener getListenerWrapper(HdmiRecordListener hdmiRecordListener) {
        return new IHdmiRecordListener.Stub(){

            @Override
            public byte[] getOneTouchRecordSource(int n) {
                HdmiRecordSources.RecordSource recordSource = HdmiRecordListener.this.onOneTouchRecordSourceRequested(n);
                if (recordSource == null) {
                    return EmptyArray.BYTE;
                }
                byte[] arrby = new byte[recordSource.getDataSize(true)];
                recordSource.toByteArray(true, arrby, 0);
                return arrby;
            }

            @Override
            public void onClearTimerRecordingResult(int n, int n2) {
                HdmiRecordListener.this.onClearTimerRecordingResult(n, n2);
            }

            @Override
            public void onOneTouchRecordResult(int n, int n2) {
                HdmiRecordListener.this.onOneTouchRecordResult(n, n2);
            }

            @Override
            public void onTimerRecordingResult(int n, int n2) {
                HdmiRecordListener.this.onTimerRecordingResult(n, HdmiRecordListener.TimerStatusData.parseFrom(n2));
            }
        };
    }

    public void clearTimerRecording(int n, int n2, HdmiTimerRecordSources.TimerRecordSource timerRecordSource) {
        if (timerRecordSource != null) {
            this.checkTimerRecordingSourceType(n2);
            try {
                byte[] arrby = new byte[timerRecordSource.getDataSize()];
                timerRecordSource.toByteArray(arrby, 0);
                this.mService.clearTimerRecording(n, n2, arrby);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "failed to start record: ", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("source must not be null.");
    }

    public void deviceSelect(int n, SelectCallback selectCallback) {
        if (selectCallback != null) {
            try {
                this.mService.deviceSelect(n, HdmiTvClient.getCallbackWrapper(selectCallback));
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "failed to select device: ", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("callback must not be null.");
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
        return 0;
    }

    public void portSelect(int n, SelectCallback selectCallback) {
        if (selectCallback != null) {
            try {
                this.mService.portSelect(n, HdmiTvClient.getCallbackWrapper(selectCallback));
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "failed to select port: ", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("Callback must not be null");
    }

    public void sendMhlVendorCommand(int n, int n2, int n3, byte[] object) {
        if (object != null && ((byte[])object).length == 16) {
            if (n2 >= 0 && n2 < 16) {
                if (n3 >= 0 && n2 + n3 <= 16) {
                    try {
                        this.mService.sendMhlVendorCommand(n, n2, n3, (byte[])object);
                    }
                    catch (RemoteException remoteException) {
                        Log.e(TAG, "failed to send vendor command: ", remoteException);
                    }
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid length:");
                ((StringBuilder)object).append(n3);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid offset:");
            ((StringBuilder)object).append(n2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("Invalid vendor command data.");
    }

    public void sendStandby(int n) {
        try {
            this.mService.sendStandby(this.getDeviceType(), n);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "sendStandby threw exception ", remoteException);
        }
    }

    public void setHdmiMhlVendorCommandListener(HdmiMhlVendorCommandListener hdmiMhlVendorCommandListener) {
        if (hdmiMhlVendorCommandListener != null) {
            try {
                this.mService.addHdmiMhlVendorCommandListener(this.getListenerWrapper(hdmiMhlVendorCommandListener));
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "failed to set hdmi mhl vendor command listener: ", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("listener must not be null.");
    }

    public void setInputChangeListener(InputChangeListener inputChangeListener) {
        if (inputChangeListener != null) {
            try {
                this.mService.setInputChangeListener(HdmiTvClient.getListenerWrapper(inputChangeListener));
            }
            catch (RemoteException remoteException) {
                Log.e("TAG", "Failed to set InputChangeListener:", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("listener must not be null.");
    }

    public void setRecordListener(HdmiRecordListener hdmiRecordListener) {
        if (hdmiRecordListener != null) {
            try {
                this.mService.setHdmiRecordListener(HdmiTvClient.getListenerWrapper(hdmiRecordListener));
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "failed to set record listener.", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("listener must not be null.");
    }

    public void setSystemAudioMode(boolean bl, SelectCallback selectCallback) {
        try {
            this.mService.setSystemAudioMode(bl, HdmiTvClient.getCallbackWrapper(selectCallback));
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "failed to set system audio mode:", remoteException);
        }
    }

    public void setSystemAudioMute(boolean bl) {
        try {
            this.mService.setSystemAudioMute(bl);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "failed to set mute: ", remoteException);
        }
    }

    public void setSystemAudioVolume(int n, int n2, int n3) {
        try {
            this.mService.setSystemAudioVolume(n, n2, n3);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "failed to set volume: ", remoteException);
        }
    }

    public void startOneTouchRecord(int n, HdmiRecordSources.RecordSource recordSource) {
        if (recordSource != null) {
            try {
                byte[] arrby = new byte[recordSource.getDataSize(true)];
                recordSource.toByteArray(true, arrby, 0);
                this.mService.startOneTouchRecord(n, arrby);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "failed to start record: ", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("source must not be null.");
    }

    public void startTimerRecording(int n, int n2, HdmiTimerRecordSources.TimerRecordSource timerRecordSource) {
        if (timerRecordSource != null) {
            this.checkTimerRecordingSourceType(n2);
            try {
                byte[] arrby = new byte[timerRecordSource.getDataSize()];
                timerRecordSource.toByteArray(arrby, 0);
                this.mService.startTimerRecording(n, n2, arrby);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "failed to start record: ", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("source must not be null.");
    }

    public void stopOneTouchRecord(int n) {
        try {
            this.mService.stopOneTouchRecord(n);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "failed to stop record: ", remoteException);
        }
    }

    public static interface HdmiMhlVendorCommandListener {
        public void onReceived(int var1, int var2, int var3, byte[] var4);
    }

    public static interface InputChangeListener {
        public void onChanged(HdmiDeviceInfo var1);
    }

    public static interface SelectCallback {
        public void onComplete(int var1);
    }

}

