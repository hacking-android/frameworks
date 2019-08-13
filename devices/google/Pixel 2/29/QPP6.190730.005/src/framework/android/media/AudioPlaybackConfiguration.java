/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.media.IPlayer;
import android.media.PlayerBase;
import android.media.PlayerProxy;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public final class AudioPlaybackConfiguration
implements Parcelable {
    public static final Parcelable.Creator<AudioPlaybackConfiguration> CREATOR;
    private static final boolean DEBUG = false;
    public static final int PLAYER_PIID_INVALID = -1;
    @SystemApi
    public static final int PLAYER_STATE_IDLE = 1;
    @SystemApi
    public static final int PLAYER_STATE_PAUSED = 3;
    @SystemApi
    public static final int PLAYER_STATE_RELEASED = 0;
    @SystemApi
    public static final int PLAYER_STATE_STARTED = 2;
    @SystemApi
    public static final int PLAYER_STATE_STOPPED = 4;
    @SystemApi
    public static final int PLAYER_STATE_UNKNOWN = -1;
    public static final int PLAYER_TYPE_AAUDIO = 13;
    public static final int PLAYER_TYPE_EXTERNAL_PROXY = 15;
    public static final int PLAYER_TYPE_HW_SOURCE = 14;
    @SystemApi
    public static final int PLAYER_TYPE_JAM_AUDIOTRACK = 1;
    @SystemApi
    public static final int PLAYER_TYPE_JAM_MEDIAPLAYER = 2;
    @SystemApi
    public static final int PLAYER_TYPE_JAM_SOUNDPOOL = 3;
    @SystemApi
    public static final int PLAYER_TYPE_SLES_AUDIOPLAYER_BUFFERQUEUE = 11;
    @SystemApi
    public static final int PLAYER_TYPE_SLES_AUDIOPLAYER_URI_FD = 12;
    @SystemApi
    public static final int PLAYER_TYPE_UNKNOWN = -1;
    public static final int PLAYER_UPID_INVALID = -1;
    private static final String TAG;
    public static PlayerDeathMonitor sPlayerDeathMonitor;
    private int mClientPid;
    private int mClientUid;
    private IPlayerShell mIPlayerShell;
    private AudioAttributes mPlayerAttr;
    private final int mPlayerIId;
    private int mPlayerState;
    private int mPlayerType;

    static {
        TAG = new String("AudioPlaybackConfiguration");
        CREATOR = new Parcelable.Creator<AudioPlaybackConfiguration>(){

            @Override
            public AudioPlaybackConfiguration createFromParcel(Parcel parcel) {
                return new AudioPlaybackConfiguration(parcel);
            }

            public AudioPlaybackConfiguration[] newArray(int n) {
                return new AudioPlaybackConfiguration[n];
            }
        };
    }

    private AudioPlaybackConfiguration(int n) {
        this.mPlayerIId = n;
        this.mIPlayerShell = null;
    }

    public AudioPlaybackConfiguration(PlayerBase.PlayerIdCard playerIdCard, int n, int n2, int n3) {
        this.mPlayerIId = n;
        this.mPlayerType = playerIdCard.mPlayerType;
        this.mClientUid = n2;
        this.mClientPid = n3;
        this.mPlayerState = 1;
        this.mPlayerAttr = playerIdCard.mAttributes;
        this.mIPlayerShell = sPlayerDeathMonitor != null && playerIdCard.mIPlayer != null ? new IPlayerShell(this, playerIdCard.mIPlayer) : null;
    }

    private AudioPlaybackConfiguration(Parcel object) {
        this.mPlayerIId = ((Parcel)object).readInt();
        this.mPlayerType = ((Parcel)object).readInt();
        this.mClientUid = ((Parcel)object).readInt();
        this.mClientPid = ((Parcel)object).readInt();
        this.mPlayerState = ((Parcel)object).readInt();
        this.mPlayerAttr = AudioAttributes.CREATOR.createFromParcel((Parcel)object);
        IPlayer iPlayer = IPlayer.Stub.asInterface(((Parcel)object).readStrongBinder());
        object = null;
        if (iPlayer != null) {
            object = new IPlayerShell(null, iPlayer);
        }
        this.mIPlayerShell = object;
    }

    public static AudioPlaybackConfiguration anonymizedCopy(AudioPlaybackConfiguration audioPlaybackConfiguration) {
        AudioPlaybackConfiguration audioPlaybackConfiguration2 = new AudioPlaybackConfiguration(audioPlaybackConfiguration.mPlayerIId);
        audioPlaybackConfiguration2.mPlayerState = audioPlaybackConfiguration.mPlayerState;
        AudioAttributes.Builder builder = new AudioAttributes.Builder().setUsage(audioPlaybackConfiguration.mPlayerAttr.getUsage()).setContentType(audioPlaybackConfiguration.mPlayerAttr.getContentType()).setFlags(audioPlaybackConfiguration.mPlayerAttr.getFlags());
        int n = audioPlaybackConfiguration.mPlayerAttr.getAllowedCapturePolicy();
        int n2 = 1;
        if (n != 1) {
            n2 = 3;
        }
        audioPlaybackConfiguration2.mPlayerAttr = builder.setAllowedCapturePolicy(n2).build();
        audioPlaybackConfiguration2.mPlayerType = -1;
        audioPlaybackConfiguration2.mClientUid = -1;
        audioPlaybackConfiguration2.mClientPid = -1;
        audioPlaybackConfiguration2.mIPlayerShell = null;
        return audioPlaybackConfiguration2;
    }

    private void playerDied() {
        PlayerDeathMonitor playerDeathMonitor = sPlayerDeathMonitor;
        if (playerDeathMonitor != null) {
            playerDeathMonitor.playerDeath(this.mPlayerIId);
        }
    }

    public static String toLogFriendlyPlayerState(int n) {
        if (n != -1) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) {
                                return "unknown player state - FIXME";
                            }
                            return "stopped";
                        }
                        return "paused";
                    }
                    return "started";
                }
                return "idle";
            }
            return "released";
        }
        return "unknown";
    }

    public static String toLogFriendlyPlayerType(int n) {
        if (n != -1) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        switch (n) {
                            default: {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("unknown player type ");
                                stringBuilder.append(n);
                                stringBuilder.append(" - FIXME");
                                return stringBuilder.toString();
                            }
                            case 15: {
                                return "external proxy";
                            }
                            case 14: {
                                return "hardware source";
                            }
                            case 13: {
                                return "AAudio";
                            }
                            case 12: {
                                return "OpenSL ES AudioPlayer (URI/FD)";
                            }
                            case 11: 
                        }
                        return "OpenSL ES AudioPlayer (Buffer Queue)";
                    }
                    return "android.media.SoundPool";
                }
                return "android.media.MediaPlayer";
            }
            return "android.media.AudioTrack";
        }
        return "unknown";
    }

    public static String toLogFriendlyString(AudioPlaybackConfiguration audioPlaybackConfiguration) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID:");
        stringBuilder.append(audioPlaybackConfiguration.mPlayerIId);
        stringBuilder.append(" -- type:");
        stringBuilder.append(AudioPlaybackConfiguration.toLogFriendlyPlayerType(audioPlaybackConfiguration.mPlayerType));
        stringBuilder.append(" -- u/pid:");
        stringBuilder.append(audioPlaybackConfiguration.mClientUid);
        stringBuilder.append("/");
        stringBuilder.append(audioPlaybackConfiguration.mClientPid);
        stringBuilder.append(" -- state:");
        stringBuilder.append(AudioPlaybackConfiguration.toLogFriendlyPlayerState(audioPlaybackConfiguration.mPlayerState));
        stringBuilder.append(" -- attr:");
        stringBuilder.append(audioPlaybackConfiguration.mPlayerAttr);
        return new String(stringBuilder.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(PrintWriter printWriter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        stringBuilder.append(AudioPlaybackConfiguration.toLogFriendlyString(this));
        printWriter.println(stringBuilder.toString());
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && object instanceof AudioPlaybackConfiguration) {
            object = (AudioPlaybackConfiguration)object;
            if (this.mPlayerIId != ((AudioPlaybackConfiguration)object).mPlayerIId || this.mPlayerType != ((AudioPlaybackConfiguration)object).mPlayerType || this.mClientUid != ((AudioPlaybackConfiguration)object).mClientUid || this.mClientPid != ((AudioPlaybackConfiguration)object).mClientPid) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public AudioAttributes getAudioAttributes() {
        return this.mPlayerAttr;
    }

    @SystemApi
    public int getClientPid() {
        return this.mClientPid;
    }

    @SystemApi
    public int getClientUid() {
        return this.mClientUid;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    IPlayer getIPlayer() {
        IPlayerShell iPlayerShell;
        synchronized (this) {
            iPlayerShell = this.mIPlayerShell;
        }
        if (iPlayerShell != null) return iPlayerShell.getIPlayer();
        return null;
    }

    @SystemApi
    public int getPlayerInterfaceId() {
        return this.mPlayerIId;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public PlayerProxy getPlayerProxy() {
        IPlayerShell iPlayerShell;
        synchronized (this) {
            iPlayerShell = this.mIPlayerShell;
        }
        if (iPlayerShell != null) return new PlayerProxy(this);
        return null;
    }

    @SystemApi
    public int getPlayerState() {
        return this.mPlayerState;
    }

    @SystemApi
    public int getPlayerType() {
        int n = this.mPlayerType;
        switch (n) {
            default: {
                return n;
            }
            case 13: 
            case 14: 
            case 15: 
        }
        return -1;
    }

    public boolean handleAudioAttributesEvent(AudioAttributes audioAttributes) {
        boolean bl = audioAttributes.equals(this.mPlayerAttr);
        this.mPlayerAttr = audioAttributes;
        return bl ^ true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean handleStateEvent(int n) {
        synchronized (this) {
            boolean bl = this.mPlayerState != n;
            this.mPlayerState = n;
            if (bl && n == 0 && this.mIPlayerShell != null) {
                this.mIPlayerShell.release();
                this.mIPlayerShell = null;
            }
            return bl;
        }
    }

    public int hashCode() {
        return Objects.hash(this.mPlayerIId, this.mPlayerType, this.mClientUid, this.mClientPid);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void init() {
        synchronized (this) {
            if (this.mIPlayerShell != null) {
                this.mIPlayerShell.monitorDeath();
            }
            return;
        }
    }

    public boolean isActive() {
        return this.mPlayerState == 2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void writeToParcel(Parcel parcel, int n) {
        Object object;
        parcel.writeInt(this.mPlayerIId);
        parcel.writeInt(this.mPlayerType);
        parcel.writeInt(this.mClientUid);
        parcel.writeInt(this.mClientPid);
        parcel.writeInt(this.mPlayerState);
        this.mPlayerAttr.writeToParcel(parcel, 0);
        synchronized (this) {
            object = this.mIPlayerShell;
        }
        object = object == null ? null : ((IPlayerShell)object).getIPlayer();
        parcel.writeStrongInterface((IInterface)object);
    }

    static final class IPlayerShell
    implements IBinder.DeathRecipient {
        private volatile IPlayer mIPlayer;
        final AudioPlaybackConfiguration mMonitor;

        IPlayerShell(AudioPlaybackConfiguration audioPlaybackConfiguration, IPlayer iPlayer) {
            this.mMonitor = audioPlaybackConfiguration;
            this.mIPlayer = iPlayer;
        }

        @Override
        public void binderDied() {
            AudioPlaybackConfiguration audioPlaybackConfiguration = this.mMonitor;
            if (audioPlaybackConfiguration != null) {
                audioPlaybackConfiguration.playerDied();
            }
        }

        IPlayer getIPlayer() {
            return this.mIPlayer;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void monitorDeath() {
            synchronized (this) {
                Object object;
                block9 : {
                    object = this.mIPlayer;
                    if (object != null) break block9;
                    return;
                }
                try {
                    this.mIPlayer.asBinder().linkToDeath(this, 0);
                    return;
                }
                catch (RemoteException remoteException) {
                    if (this.mMonitor != null) {
                        String string2 = TAG;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Could not link to client death for piid=");
                        ((StringBuilder)object).append(this.mMonitor.mPlayerIId);
                        Log.w(string2, ((StringBuilder)object).toString(), remoteException);
                        return;
                    } else {
                        Log.w(TAG, "Could not link to client death", remoteException);
                    }
                    return;
                }
                finally {
                }
            }
        }

        void release() {
            synchronized (this) {
                block4 : {
                    IPlayer iPlayer = this.mIPlayer;
                    if (iPlayer != null) break block4;
                    return;
                }
                this.mIPlayer.asBinder().unlinkToDeath(this, 0);
                this.mIPlayer = null;
                Binder.flushPendingCommands();
                return;
            }
        }
    }

    public static interface PlayerDeathMonitor {
        public void playerDeath(int var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PlayerState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PlayerType {
    }

}

