/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioAttributes;
import android.media.AudioPlaybackConfiguration;
import android.media.IAudioService;
import android.media.IPlayer;
import android.media.VolumeShaper;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsService;
import java.lang.ref.WeakReference;
import java.util.Objects;

public abstract class PlayerBase {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_APP_OPS = false;
    private static final String TAG = "PlayerBase";
    private static final boolean USE_AUDIOFLINGER_MUTING_FOR_OP = true;
    private static IAudioService sService;
    private IAppOpsService mAppOps;
    private IAppOpsCallback mAppOpsCallback;
    protected AudioAttributes mAttributes;
    protected float mAuxEffectSendLevel = 0.0f;
    @GuardedBy(value={"mLock"})
    private boolean mHasAppOpsPlayAudio = true;
    private final int mImplType;
    protected float mLeftVolume = 1.0f;
    private final Object mLock = new Object();
    @GuardedBy(value={"mLock"})
    private float mPanMultiplierL = 1.0f;
    @GuardedBy(value={"mLock"})
    private float mPanMultiplierR = 1.0f;
    private int mPlayerIId = -1;
    protected float mRightVolume = 1.0f;
    @GuardedBy(value={"mLock"})
    private int mStartDelayMs = 0;
    @GuardedBy(value={"mLock"})
    private int mState;
    @GuardedBy(value={"mLock"})
    private float mVolMultiplier = 1.0f;

    PlayerBase(AudioAttributes audioAttributes, int n) {
        if (audioAttributes != null) {
            this.mAttributes = audioAttributes;
            this.mImplType = n;
            this.mState = 1;
            return;
        }
        throw new IllegalArgumentException("Illegal null AudioAttributes");
    }

    public static void deprecateStreamTypeForPlayback(int n, String string2, String string3) throws IllegalArgumentException {
        if (n != 10) {
            Log.w(string2, "Use of stream types is deprecated for operations other than volume control");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("See the documentation of ");
            stringBuilder.append(string3);
            stringBuilder.append(" for what to use instead with android.media.AudioAttributes to qualify your playback use case");
            Log.w(string2, stringBuilder.toString());
            return;
        }
        throw new IllegalArgumentException("Use of STREAM_ACCESSIBILITY is reserved for volume control");
    }

    private static IAudioService getService() {
        IAudioService iAudioService = sService;
        if (iAudioService != null) {
            return iAudioService;
        }
        sService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        return sService;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateAppOpsPlayAudio() {
        Object object = this.mLock;
        synchronized (object) {
            this.updateAppOpsPlayAudio_sync(false);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updatePlayerVolume() {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        boolean bl;
        float f6;
        Object object = this.mLock;
        synchronized (object) {
            f3 = this.mVolMultiplier;
            f5 = this.mLeftVolume;
            f4 = this.mPanMultiplierL;
            f = this.mVolMultiplier;
            f6 = this.mRightVolume;
            f2 = this.mPanMultiplierR;
            bl = this.isRestricted_sync();
        }
        this.playerSetVolume(bl, f3 * f5 * f4, f * f6 * f2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateState(int n) {
        int n2;
        Object object = this.mLock;
        synchronized (object) {
            this.mState = n;
            n2 = this.mPlayerIId;
        }
        try {
            PlayerBase.getService().playerEvent(n2, n);
            return;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error talking to audio service, ");
            stringBuilder.append(AudioPlaybackConfiguration.toLogFriendlyPlayerState(n));
            stringBuilder.append(" state will not be tracked for piid=");
            stringBuilder.append(n2);
            Log.e(TAG, stringBuilder.toString(), remoteException);
        }
    }

    void basePause() {
        this.updateState(3);
    }

    protected void baseRegisterPlayer() {
        try {
            IAudioService iAudioService = PlayerBase.getService();
            int n = this.mImplType;
            AudioAttributes audioAttributes = this.mAttributes;
            IPlayerWrapper iPlayerWrapper = new IPlayerWrapper(this);
            PlayerIdCard playerIdCard = new PlayerIdCard(n, audioAttributes, iPlayerWrapper);
            this.mPlayerIId = iAudioService.trackPlayer(playerIdCard);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error talking to audio service, player will not be tracked", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void baseRelease() {
        boolean bl = false;
        Object object = this.mLock;
        // MONITORENTER : object
        if (this.mState != 0) {
            bl = true;
            this.mState = 0;
        }
        // MONITOREXIT : object
        if (bl) {
            try {
                PlayerBase.getService().releasePlayer(this.mPlayerIId);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error talking to audio service, the player will still be tracked", remoteException);
            }
        }
        try {
            if (this.mAppOps == null) return;
            this.mAppOps.stopWatchingMode(this.mAppOpsCallback);
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int baseSetAuxEffectSendLevel(float f) {
        Object object = this.mLock;
        synchronized (object) {
            this.mAuxEffectSendLevel = f;
            if (this.isRestricted_sync()) {
                return 0;
            }
            return this.playerSetAuxEffectSendLevel(false, f);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void baseSetPan(float f) {
        f = Math.min(Math.max(-1.0f, f), 1.0f);
        Object object = this.mLock;
        synchronized (object) {
            if (f >= 0.0f) {
                this.mPanMultiplierL = 1.0f - f;
                this.mPanMultiplierR = 1.0f;
            } else {
                this.mPanMultiplierL = 1.0f;
                this.mPanMultiplierR = 1.0f + f;
            }
        }
        this.updatePlayerVolume();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void baseSetStartDelayMs(int n) {
        Object object = this.mLock;
        synchronized (object) {
            this.mStartDelayMs = Math.max(n, 0);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void baseSetVolume(float f, float f2) {
        Object object = this.mLock;
        synchronized (object) {
            this.mLeftVolume = f;
            this.mRightVolume = f2;
        }
        this.updatePlayerVolume();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void baseStart() {
        this.updateState(2);
        Object object = this.mLock;
        synchronized (object) {
            if (this.isRestricted_sync()) {
                this.playerSetVolume(true, 0.0f, 0.0f);
            }
            return;
        }
    }

    void baseStop() {
        this.updateState(4);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void baseUpdateAudioAttributes(AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            throw new IllegalArgumentException("Illegal null AudioAttributes");
        }
        try {
            PlayerBase.getService().playerAttributes(this.mPlayerIId, audioAttributes);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error talking to audio service, STARTED state will not be tracked", remoteException);
        }
        Object object = this.mLock;
        synchronized (object) {
            boolean bl = this.mAttributes != audioAttributes;
            this.mAttributes = audioAttributes;
            this.updateAppOpsPlayAudio_sync(bl);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected int getStartDelayMs() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mStartDelayMs;
        }
    }

    boolean isRestricted_sync() {
        return false;
    }

    abstract int playerApplyVolumeShaper(VolumeShaper.Configuration var1, VolumeShaper.Operation var2);

    abstract VolumeShaper.State playerGetVolumeShaperState(int var1);

    abstract void playerPause();

    abstract int playerSetAuxEffectSendLevel(boolean var1, float var2);

    abstract void playerSetVolume(boolean var1, float var2, float var3);

    abstract void playerStart();

    abstract void playerStop();

    public void setStartDelayMs(int n) {
        this.baseSetStartDelayMs(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setVolumeMultiplier(float f) {
        Object object = this.mLock;
        synchronized (object) {
            this.mVolMultiplier = f;
        }
        this.updatePlayerVolume();
    }

    void updateAppOpsPlayAudio_sync(boolean bl) {
    }

    private static class IAppOpsCallbackWrapper
    extends IAppOpsCallback.Stub {
        private final WeakReference<PlayerBase> mWeakPB;

        public IAppOpsCallbackWrapper(PlayerBase playerBase) {
            this.mWeakPB = new WeakReference<PlayerBase>(playerBase);
        }

        @Override
        public void opChanged(int n, int n2, String object) {
            if (n == 28 && (object = (PlayerBase)this.mWeakPB.get()) != null) {
                ((PlayerBase)object).updateAppOpsPlayAudio();
            }
        }
    }

    private static class IPlayerWrapper
    extends IPlayer.Stub {
        private final WeakReference<PlayerBase> mWeakPB;

        public IPlayerWrapper(PlayerBase playerBase) {
            this.mWeakPB = new WeakReference<PlayerBase>(playerBase);
        }

        @Override
        public void applyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) {
            PlayerBase playerBase = (PlayerBase)this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.playerApplyVolumeShaper(configuration, operation);
            }
        }

        @Override
        public void pause() {
            PlayerBase playerBase = (PlayerBase)this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.playerPause();
            }
        }

        @Override
        public void setPan(float f) {
            PlayerBase playerBase = (PlayerBase)this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.baseSetPan(f);
            }
        }

        @Override
        public void setStartDelayMs(int n) {
            PlayerBase playerBase = (PlayerBase)this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.baseSetStartDelayMs(n);
            }
        }

        @Override
        public void setVolume(float f) {
            PlayerBase playerBase = (PlayerBase)this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.setVolumeMultiplier(f);
            }
        }

        @Override
        public void start() {
            PlayerBase playerBase = (PlayerBase)this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.playerStart();
            }
        }

        @Override
        public void stop() {
            PlayerBase playerBase = (PlayerBase)this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.playerStop();
            }
        }
    }

    public static class PlayerIdCard
    implements Parcelable {
        public static final int AUDIO_ATTRIBUTES_DEFINED = 1;
        public static final int AUDIO_ATTRIBUTES_NONE = 0;
        public static final Parcelable.Creator<PlayerIdCard> CREATOR = new Parcelable.Creator<PlayerIdCard>(){

            @Override
            public PlayerIdCard createFromParcel(Parcel parcel) {
                return new PlayerIdCard(parcel);
            }

            public PlayerIdCard[] newArray(int n) {
                return new PlayerIdCard[n];
            }
        };
        public final AudioAttributes mAttributes;
        public final IPlayer mIPlayer;
        public final int mPlayerType;

        PlayerIdCard(int n, AudioAttributes audioAttributes, IPlayer iPlayer) {
            this.mPlayerType = n;
            this.mAttributes = audioAttributes;
            this.mIPlayer = iPlayer;
        }

        private PlayerIdCard(Parcel object) {
            this.mPlayerType = ((Parcel)object).readInt();
            this.mAttributes = AudioAttributes.CREATOR.createFromParcel((Parcel)object);
            object = ((Parcel)object).readStrongBinder();
            object = object == null ? null : IPlayer.Stub.asInterface((IBinder)object);
            this.mIPlayer = object;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && object instanceof PlayerIdCard) {
                object = (PlayerIdCard)object;
                if (this.mPlayerType != ((PlayerIdCard)object).mPlayerType || !this.mAttributes.equals(((PlayerIdCard)object).mAttributes)) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mPlayerType);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mPlayerType);
            this.mAttributes.writeToParcel(parcel, 0);
            Object object = this.mIPlayer;
            object = object == null ? null : object.asBinder();
            parcel.writeStrongBinder((IBinder)object);
        }

    }

}

