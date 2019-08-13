/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.-$
 *  android.hardware.radio.-$$Lambda
 *  android.hardware.radio.-$$Lambda$TunerAdapter
 *  android.hardware.radio.-$$Lambda$TunerAdapter$St9hluCzvLWs9wyE7kDX24NpwJQ
 *  android.hardware.radio.-$$Lambda$TunerAdapter$xm27iP_3PUgByOaDoK2KJcP5fnA
 */
package android.hardware.radio;

import android.graphics.Bitmap;
import android.hardware.radio.-$;
import android.hardware.radio.ITuner;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.hardware.radio.RadioTuner;
import android.hardware.radio.TunerCallbackAdapter;
import android.hardware.radio._$$Lambda$TunerAdapter$St9hluCzvLWs9wyE7kDX24NpwJQ;
import android.hardware.radio._$$Lambda$TunerAdapter$xm27iP_3PUgByOaDoK2KJcP5fnA;
import android.hardware.radio._$$Lambda$TunerAdapter$ytmKJEaNVVp6n7nE6SVU6pZ9g7c;
import android.os.RemoteException;
import android.util.Log;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class TunerAdapter
extends RadioTuner {
    private static final String TAG = "BroadcastRadio.TunerAdapter";
    private int mBand;
    private final TunerCallbackAdapter mCallback;
    private boolean mIsClosed = false;
    private Map<String, String> mLegacyListFilter;
    private ProgramList mLegacyListProxy;
    private final ITuner mTuner;

    TunerAdapter(ITuner iTuner, TunerCallbackAdapter tunerCallbackAdapter, int n) {
        this.mTuner = Objects.requireNonNull(iTuner);
        this.mCallback = Objects.requireNonNull(tunerCallbackAdapter);
        this.mBand = n;
    }

    static /* synthetic */ void lambda$getDynamicProgramList$2() {
    }

    static /* synthetic */ void lambda$getProgramList$0() {
    }

    @Override
    public int cancel() {
        try {
            this.mTuner.cancel();
            return 0;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "service died", remoteException);
            return -32;
        }
        catch (IllegalStateException illegalStateException) {
            Log.e(TAG, "Can't cancel", illegalStateException);
            return -38;
        }
    }

    @Override
    public void cancelAnnouncement() {
        try {
            this.mTuner.cancelAnnouncement();
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("service died", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        ITuner iTuner = this.mTuner;
        synchronized (iTuner) {
            if (this.mIsClosed) {
                Log.v(TAG, "Tuner is already closed");
                return;
            }
            this.mIsClosed = true;
            if (this.mLegacyListProxy != null) {
                this.mLegacyListProxy.close();
                this.mLegacyListProxy = null;
            }
            this.mCallback.close();
        }
        try {
            this.mTuner.close();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Exception trying to close tuner", remoteException);
        }
    }

    @Override
    public int getConfiguration(RadioManager.BandConfig[] arrbandConfig) {
        if (arrbandConfig != null && arrbandConfig.length == 1) {
            try {
                arrbandConfig[0] = this.mTuner.getConfiguration();
                return 0;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "service died", remoteException);
                return -32;
            }
        }
        throw new IllegalArgumentException("The argument must be an array of length 1");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ProgramList getDynamicProgramList(ProgramList.Filter object) {
        ITuner iTuner = this.mTuner;
        synchronized (iTuner) {
            if (this.mLegacyListProxy != null) {
                this.mLegacyListProxy.close();
                this.mLegacyListProxy = null;
            }
            this.mLegacyListFilter = null;
            ProgramList programList = new ProgramList();
            TunerCallbackAdapter tunerCallbackAdapter = this.mCallback;
            _$$Lambda$TunerAdapter$ytmKJEaNVVp6n7nE6SVU6pZ9g7c _$$Lambda$TunerAdapter$ytmKJEaNVVp6n7nE6SVU6pZ9g7c = new _$$Lambda$TunerAdapter$ytmKJEaNVVp6n7nE6SVU6pZ9g7c(this);
            tunerCallbackAdapter.setProgramListObserver(programList, _$$Lambda$TunerAdapter$ytmKJEaNVVp6n7nE6SVU6pZ9g7c);
            try {
                this.mTuner.startProgramListUpdates((ProgramList.Filter)object);
                return programList;
            }
            catch (RemoteException remoteException) {
                this.mCallback.setProgramListObserver(null, (ProgramList.OnCloseListener)_$$Lambda$TunerAdapter$St9hluCzvLWs9wyE7kDX24NpwJQ.INSTANCE);
                object = new RuntimeException("service died", remoteException);
                throw object;
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                Log.i(TAG, "Program list is not supported with this hardware");
                return null;
            }
        }
    }

    @Override
    public Bitmap getMetadataImage(int n) {
        try {
            Bitmap bitmap = this.mTuner.getImage(n);
            return bitmap;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("service died", remoteException);
        }
    }

    @Override
    public boolean getMute() {
        try {
            boolean bl = this.mTuner.isMuted();
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "service died", remoteException);
            return true;
        }
    }

    @Override
    public Map<String, String> getParameters(List<String> object) {
        try {
            object = this.mTuner.getParameters(Objects.requireNonNull(object));
            return object;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("service died", remoteException);
        }
    }

    @Override
    public int getProgramInformation(RadioManager.ProgramInfo[] arrprogramInfo) {
        if (arrprogramInfo != null && arrprogramInfo.length == 1) {
            RadioManager.ProgramInfo programInfo = this.mCallback.getCurrentProgramInformation();
            if (programInfo == null) {
                Log.w(TAG, "Didn't get program info yet");
                return -38;
            }
            arrprogramInfo[0] = programInfo;
            return 0;
        }
        Log.e(TAG, "The argument must be an array of length 1");
        return -22;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public List<RadioManager.ProgramInfo> getProgramList(Map<String, String> object) {
        ITuner iTuner = this.mTuner;
        // MONITORENTER : iTuner
        if (this.mLegacyListProxy == null || !Objects.equals(this.mLegacyListFilter, object)) {
            Log.i(TAG, "Program list filter has changed, requesting new list");
            Object object2 = new ProgramList();
            this.mLegacyListProxy = object2;
            this.mLegacyListFilter = object;
            this.mCallback.clearLastCompleteList();
            this.mCallback.setProgramListObserver(this.mLegacyListProxy, (ProgramList.OnCloseListener)_$$Lambda$TunerAdapter$xm27iP_3PUgByOaDoK2KJcP5fnA.INSTANCE);
            ITuner iTuner2 = this.mTuner;
            object2 = new ProgramList.Filter((Map<String, String>)object);
            iTuner2.startProgramListUpdates((ProgramList.Filter)object2);
        }
        if ((object = this.mCallback.getLastCompleteList()) != null) {
            // MONITOREXIT : iTuner
            return object;
        }
        object = new IllegalStateException("Program list is not ready yet");
        throw object;
        catch (RemoteException remoteException) {
            object = new RuntimeException("service died", remoteException);
            throw object;
        }
    }

    @Override
    public boolean hasControl() {
        try {
            boolean bl = this.mTuner.isClosed();
            return bl ^ true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean isAnalogForced() {
        try {
            boolean bl = this.isConfigFlagSet(2);
            return bl;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            throw new IllegalStateException(unsupportedOperationException);
        }
    }

    @Override
    public boolean isAntennaConnected() {
        return this.mCallback.isAntennaConnected();
    }

    @Override
    public boolean isConfigFlagSet(int n) {
        try {
            boolean bl = this.mTuner.isConfigFlagSet(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("service died", remoteException);
        }
    }

    @Override
    public boolean isConfigFlagSupported(int n) {
        try {
            boolean bl = this.mTuner.isConfigFlagSupported(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("service died", remoteException);
        }
    }

    public /* synthetic */ void lambda$getDynamicProgramList$1$TunerAdapter() {
        try {
            this.mTuner.stopProgramListUpdates();
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Couldn't stop program list updates", remoteException);
        }
    }

    @Override
    public int scan(int n, boolean bl) {
        boolean bl2;
        ITuner iTuner;
        block4 : {
            iTuner = this.mTuner;
            bl2 = true;
            if (n == 1) break block4;
            bl2 = false;
        }
        try {
            iTuner.scan(bl2, bl);
            return 0;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "service died", remoteException);
            return -32;
        }
        catch (IllegalStateException illegalStateException) {
            Log.e(TAG, "Can't scan", illegalStateException);
            return -38;
        }
    }

    @Override
    public void setAnalogForced(boolean bl) {
        try {
            this.setConfigFlag(2, bl);
            return;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            throw new IllegalStateException(unsupportedOperationException);
        }
    }

    @Override
    public void setConfigFlag(int n, boolean bl) {
        try {
            this.mTuner.setConfigFlag(n, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("service died", remoteException);
        }
    }

    @Override
    public int setConfiguration(RadioManager.BandConfig bandConfig) {
        if (bandConfig == null) {
            return -22;
        }
        try {
            this.mTuner.setConfiguration(bandConfig);
            this.mBand = bandConfig.getType();
            return 0;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "service died", remoteException);
            return -32;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, "Can't set configuration", illegalArgumentException);
            return -22;
        }
    }

    @Override
    public int setMute(boolean bl) {
        try {
            this.mTuner.setMuted(bl);
            return 0;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "service died", remoteException);
            return -32;
        }
        catch (IllegalStateException illegalStateException) {
            Log.e(TAG, "Can't set muted", illegalStateException);
            return Integer.MIN_VALUE;
        }
    }

    @Override
    public Map<String, String> setParameters(Map<String, String> map) {
        try {
            map = this.mTuner.setParameters(Objects.requireNonNull(map));
            return map;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("service died", remoteException);
        }
    }

    @Override
    public boolean startBackgroundScan() {
        try {
            boolean bl = this.mTuner.startBackgroundScan();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("service died", remoteException);
        }
    }

    @Override
    public int step(int n, boolean bl) {
        boolean bl2;
        ITuner iTuner;
        block4 : {
            iTuner = this.mTuner;
            bl2 = true;
            if (n == 1) break block4;
            bl2 = false;
        }
        try {
            iTuner.step(bl2, bl);
            return 0;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "service died", remoteException);
            return -32;
        }
        catch (IllegalStateException illegalStateException) {
            Log.e(TAG, "Can't step", illegalStateException);
            return -38;
        }
    }

    @Override
    public int tune(int n, int n2) {
        try {
            this.mTuner.tune(ProgramSelector.createAmFmSelector(this.mBand, n, n2));
            return 0;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "service died", remoteException);
            return -32;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, "Can't tune", illegalArgumentException);
            return -22;
        }
        catch (IllegalStateException illegalStateException) {
            Log.e(TAG, "Can't tune", illegalStateException);
            return -38;
        }
    }

    @Override
    public void tune(ProgramSelector programSelector) {
        try {
            this.mTuner.tune(programSelector);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("service died", remoteException);
        }
    }
}

