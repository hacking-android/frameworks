/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.hardware.hdmi.HdmiClient;
import android.hardware.hdmi.IHdmiControlService;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;

public final class HdmiAudioSystemClient
extends HdmiClient {
    private static final int REPORT_AUDIO_STATUS_INTERVAL_MS = 500;
    private static final String TAG = "HdmiAudioSystemClient";
    private boolean mCanSendAudioStatus = true;
    private final Handler mHandler;
    private boolean mLastIsMute;
    private int mLastMaxVolume;
    private int mLastVolume;
    private boolean mPendingReportAudioStatus;

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public HdmiAudioSystemClient(IHdmiControlService iHdmiControlService) {
        this(iHdmiControlService, null);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public HdmiAudioSystemClient(IHdmiControlService iHdmiControlService, Handler handler) {
        super(iHdmiControlService);
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        this.mHandler = handler;
    }

    @Override
    public int getDeviceType() {
        return 5;
    }

    public void sendReportAudioStatusCecCommand(boolean bl, int n, int n2, boolean bl2) {
        if (bl) {
            try {
                this.mService.reportAudioStatus(this.getDeviceType(), n, n2, bl2);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        this.mLastVolume = n;
        this.mLastMaxVolume = n2;
        this.mLastIsMute = bl2;
        if (this.mCanSendAudioStatus) {
            try {
                this.mService.reportAudioStatus(this.getDeviceType(), n, n2, bl2);
                this.mCanSendAudioStatus = false;
                Handler handler = this.mHandler;
                Runnable runnable = new Runnable(){

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void run() {
                        if (!HdmiAudioSystemClient.this.mPendingReportAudioStatus) {
                            HdmiAudioSystemClient.this.mCanSendAudioStatus = true;
                            return;
                        }
                        try {
                            try {
                                HdmiAudioSystemClient.this.mService.reportAudioStatus(HdmiAudioSystemClient.this.getDeviceType(), HdmiAudioSystemClient.this.mLastVolume, HdmiAudioSystemClient.this.mLastMaxVolume, HdmiAudioSystemClient.this.mLastIsMute);
                                HdmiAudioSystemClient.this.mHandler.postDelayed(this, 500L);
                            }
                            catch (RemoteException remoteException) {
                                HdmiAudioSystemClient.this.mCanSendAudioStatus = true;
                            }
                        }
                        catch (Throwable throwable2) {}
                        HdmiAudioSystemClient.this.mPendingReportAudioStatus = false;
                        return;
                        HdmiAudioSystemClient.this.mPendingReportAudioStatus = false;
                        throw throwable2;
                    }
                };
                handler.postDelayed(runnable, 500L);
            }
            catch (RemoteException remoteException) {}
        } else {
            this.mPendingReportAudioStatus = true;
        }
    }

    public void setSystemAudioMode(boolean bl, SetSystemAudioModeCallback setSystemAudioModeCallback) {
    }

    public void setSystemAudioModeOnForAudioOnlySource() {
        try {
            this.mService.setSystemAudioModeOnForAudioOnlySource();
        }
        catch (RemoteException remoteException) {
            Log.d(TAG, "Failed to set System Audio Mode on for Audio Only source");
        }
    }

    public static interface SetSystemAudioModeCallback {
        public void onComplete(int var1);
    }

}

