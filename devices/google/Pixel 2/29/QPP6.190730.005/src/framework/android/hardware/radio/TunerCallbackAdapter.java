/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.ITunerCallback;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.hardware.radio.RadioMetadata;
import android.hardware.radio.RadioTuner;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$4zf9n0sz_rU8z6a9GJmRInWrYkQ;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$B4BuskgdSatf_Xt5wzgLniEltQk;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$HcS5_voI1xju970_jCP6Iz0LgPE;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$Hj_P___HTEx_8p7qvYVPXmhwu7w;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$Hl80_0ppQ17uTjZuGamwBQMrO6Y;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$RSNrzX5_O3nayC2_jg0kAR6KkKY;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$UsmGhKordXy4lhCylRP0mm2NcYc;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$V_mJUy8dIlOVjsZ1ckkgn490jFI;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$Yz_4KCDu1MOynGdkDf_oMxqhjeY;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$ZwPm3xxjeLvbP12KweyzqFJVnj4;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$dR_VQmFrL_tBD2wpNvborTd8W08;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$jl29exheqPoYrltfLs9fLsjsI1A;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$tiaoLZrR2K56rYeqHvSRh5lRdBI;
import android.hardware.radio._$$Lambda$TunerCallbackAdapter$xIUT1Qu5TkA83V8ttYy1zv_JuFo;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class TunerCallbackAdapter
extends ITunerCallback.Stub {
    private static final String TAG = "BroadcastRadio.TunerCallbackAdapter";
    private final RadioTuner.Callback mCallback;
    RadioManager.ProgramInfo mCurrentProgramInfo;
    private boolean mDelayedCompleteCallback = false;
    private final Handler mHandler;
    boolean mIsAntennaConnected = true;
    List<RadioManager.ProgramInfo> mLastCompleteList;
    private final Object mLock = new Object();
    ProgramList mProgramList;

    TunerCallbackAdapter(RadioTuner.Callback callback, Handler handler) {
        this.mCallback = callback;
        this.mHandler = handler == null ? new Handler(Looper.getMainLooper()) : handler;
    }

    private void sendBackgroundScanCompleteLocked() {
        this.mDelayedCompleteCallback = false;
        this.mHandler.post(new _$$Lambda$TunerCallbackAdapter$xIUT1Qu5TkA83V8ttYy1zv_JuFo(this));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void clearLastCompleteList() {
        Object object = this.mLock;
        synchronized (object) {
            this.mLastCompleteList = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void close() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mProgramList != null) {
                this.mProgramList.close();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    RadioManager.ProgramInfo getCurrentProgramInformation() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mCurrentProgramInfo;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    List<RadioManager.ProgramInfo> getLastCompleteList() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mLastCompleteList;
        }
    }

    boolean isAntennaConnected() {
        return this.mIsAntennaConnected;
    }

    public /* synthetic */ void lambda$onAntennaState$9$TunerCallbackAdapter(boolean bl) {
        this.mCallback.onAntennaState(bl);
    }

    public /* synthetic */ void lambda$onBackgroundScanAvailabilityChange$10$TunerCallbackAdapter(boolean bl) {
        this.mCallback.onBackgroundScanAvailabilityChange(bl);
    }

    public /* synthetic */ void lambda$onConfigurationChanged$5$TunerCallbackAdapter(RadioManager.BandConfig bandConfig) {
        this.mCallback.onConfigurationChanged(bandConfig);
    }

    public /* synthetic */ void lambda$onCurrentProgramInfoChanged$6$TunerCallbackAdapter(RadioManager.ProgramInfo parcelable) {
        this.mCallback.onProgramInfoChanged((RadioManager.ProgramInfo)parcelable);
        parcelable = parcelable.getMetadata();
        if (parcelable != null) {
            this.mCallback.onMetadataChanged((RadioMetadata)parcelable);
        }
    }

    public /* synthetic */ void lambda$onEmergencyAnnouncement$8$TunerCallbackAdapter(boolean bl) {
        this.mCallback.onEmergencyAnnouncement(bl);
    }

    public /* synthetic */ void lambda$onError$2$TunerCallbackAdapter(int n) {
        this.mCallback.onError(n);
    }

    public /* synthetic */ void lambda$onParametersUpdated$13$TunerCallbackAdapter(Map map) {
        this.mCallback.onParametersUpdated(map);
    }

    public /* synthetic */ void lambda$onProgramListChanged$12$TunerCallbackAdapter() {
        this.mCallback.onProgramListChanged();
    }

    public /* synthetic */ void lambda$onTrafficAnnouncement$7$TunerCallbackAdapter(boolean bl) {
        this.mCallback.onTrafficAnnouncement(bl);
    }

    public /* synthetic */ void lambda$onTuneFailed$3$TunerCallbackAdapter(int n, ProgramSelector programSelector) {
        this.mCallback.onTuneFailed(n, programSelector);
    }

    public /* synthetic */ void lambda$onTuneFailed$4$TunerCallbackAdapter(int n) {
        this.mCallback.onError(n);
    }

    public /* synthetic */ void lambda$sendBackgroundScanCompleteLocked$11$TunerCallbackAdapter() {
        this.mCallback.onBackgroundScanComplete();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$setProgramListObserver$0$TunerCallbackAdapter(ProgramList programList, ProgramList.OnCloseListener onCloseListener) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mProgramList != programList) {
                return;
            }
            this.mProgramList = null;
            this.mLastCompleteList = null;
            onCloseListener.onClose();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$setProgramListObserver$1$TunerCallbackAdapter(ProgramList programList) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mProgramList != programList) {
                return;
            }
            this.mLastCompleteList = programList.toList();
            if (this.mDelayedCompleteCallback) {
                Log.d(TAG, "Sending delayed onBackgroundScanComplete callback");
                this.sendBackgroundScanCompleteLocked();
            }
            return;
        }
    }

    @Override
    public void onAntennaState(boolean bl) {
        this.mIsAntennaConnected = bl;
        this.mHandler.post(new _$$Lambda$TunerCallbackAdapter$dR_VQmFrL_tBD2wpNvborTd8W08(this, bl));
    }

    @Override
    public void onBackgroundScanAvailabilityChange(boolean bl) {
        this.mHandler.post(new _$$Lambda$TunerCallbackAdapter$4zf9n0sz_rU8z6a9GJmRInWrYkQ(this, bl));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onBackgroundScanComplete() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mLastCompleteList == null) {
                Log.i(TAG, "Got onBackgroundScanComplete callback, but the program list didn't get through yet. Delaying it...");
                this.mDelayedCompleteCallback = true;
                return;
            }
            this.sendBackgroundScanCompleteLocked();
            return;
        }
    }

    @Override
    public void onConfigurationChanged(RadioManager.BandConfig bandConfig) {
        this.mHandler.post(new _$$Lambda$TunerCallbackAdapter$B4BuskgdSatf_Xt5wzgLniEltQk(this, bandConfig));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onCurrentProgramInfoChanged(RadioManager.ProgramInfo programInfo) {
        if (programInfo == null) {
            Log.e(TAG, "ProgramInfo must not be null");
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            this.mCurrentProgramInfo = programInfo;
        }
        this.mHandler.post(new _$$Lambda$TunerCallbackAdapter$RSNrzX5_O3nayC2_jg0kAR6KkKY(this, programInfo));
    }

    @Override
    public void onEmergencyAnnouncement(boolean bl) {
        this.mHandler.post(new _$$Lambda$TunerCallbackAdapter$ZwPm3xxjeLvbP12KweyzqFJVnj4(this, bl));
    }

    @Override
    public void onError(int n) {
        this.mHandler.post(new _$$Lambda$TunerCallbackAdapter$jl29exheqPoYrltfLs9fLsjsI1A(this, n));
    }

    @Override
    public void onParametersUpdated(Map map) {
        this.mHandler.post(new _$$Lambda$TunerCallbackAdapter$Yz_4KCDu1MOynGdkDf_oMxqhjeY(this, map));
    }

    @Override
    public void onProgramListChanged() {
        this.mHandler.post(new _$$Lambda$TunerCallbackAdapter$UsmGhKordXy4lhCylRP0mm2NcYc(this));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onProgramListUpdated(ProgramList.Chunk chunk) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mProgramList == null) {
                return;
            }
            this.mProgramList.apply(Objects.requireNonNull(chunk));
            return;
        }
    }

    @Override
    public void onTrafficAnnouncement(boolean bl) {
        this.mHandler.post(new _$$Lambda$TunerCallbackAdapter$tiaoLZrR2K56rYeqHvSRh5lRdBI(this, bl));
    }

    @Override
    public void onTuneFailed(int n, ProgramSelector object) {
        block3 : {
            block2 : {
                block0 : {
                    block1 : {
                        this.mHandler.post(new _$$Lambda$TunerCallbackAdapter$Hj_P___HTEx_8p7qvYVPXmhwu7w(this, n, (ProgramSelector)object));
                        if (n == Integer.MIN_VALUE || n == -38) break block0;
                        if (n == -32) break block1;
                        if (n == -22 || n == -19) break block0;
                        if (n != -1) break block2;
                    }
                    n = 1;
                    break block3;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Got an error with no mapping to the legacy API (");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append("), doing a best-effort conversion to ERROR_SCAN_TIMEOUT");
                Log.i(TAG, ((StringBuilder)object).toString());
            }
            n = 3;
        }
        this.mHandler.post(new _$$Lambda$TunerCallbackAdapter$HcS5_voI1xju970_jCP6Iz0LgPE(this, n));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setProgramListObserver(ProgramList programList, ProgramList.OnCloseListener object) {
        Objects.requireNonNull(object);
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mProgramList != null) {
                Log.w(TAG, "Previous program list observer wasn't properly closed, closing it...");
                this.mProgramList.close();
            }
            this.mProgramList = programList;
            if (programList == null) {
                return;
            }
            _$$Lambda$TunerCallbackAdapter$Hl80_0ppQ17uTjZuGamwBQMrO6Y _$$Lambda$TunerCallbackAdapter$Hl80_0ppQ17uTjZuGamwBQMrO6Y = new _$$Lambda$TunerCallbackAdapter$Hl80_0ppQ17uTjZuGamwBQMrO6Y(this, programList, (ProgramList.OnCloseListener)object);
            programList.setOnCloseListener(_$$Lambda$TunerCallbackAdapter$Hl80_0ppQ17uTjZuGamwBQMrO6Y);
            object = new _$$Lambda$TunerCallbackAdapter$V_mJUy8dIlOVjsZ1ckkgn490jFI(this, programList);
            programList.addOnCompleteListener((ProgramList.OnCompleteListener)object);
            return;
        }
    }
}

