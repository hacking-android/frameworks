/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.IRingtonePlayer;
import android.media.MediaPlayer;
import android.media.PlayerBase;
import android.media.RingtoneManager;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class Ringtone {
    private static final boolean LOGD = true;
    private static final String[] MEDIA_COLUMNS = new String[]{"_id", "title"};
    private static final String MEDIA_SELECTION = "mime_type LIKE 'audio/%' OR mime_type IN ('application/ogg', 'application/x-flac')";
    private static final String TAG = "Ringtone";
    private static final ArrayList<Ringtone> sActiveRingtones = new ArrayList();
    private final boolean mAllowRemote;
    private AudioAttributes mAudioAttributes = new AudioAttributes.Builder().setUsage(6).setContentType(4).build();
    private final AudioManager mAudioManager;
    private final MyOnCompletionListener mCompletionListener = new MyOnCompletionListener();
    private final Context mContext;
    private boolean mIsLooping = false;
    @UnsupportedAppUsage
    private MediaPlayer mLocalPlayer;
    private final Object mPlaybackSettingsLock = new Object();
    private final IRingtonePlayer mRemotePlayer;
    private final Binder mRemoteToken;
    private String mTitle;
    @UnsupportedAppUsage
    private Uri mUri;
    private float mVolume = 1.0f;
    private VolumeShaper mVolumeShaper;
    private VolumeShaper.Configuration mVolumeShaperConfig;

    @UnsupportedAppUsage
    public Ringtone(Context object, boolean bl) {
        this.mContext = object;
        this.mAudioManager = (AudioManager)this.mContext.getSystemService("audio");
        this.mAllowRemote = bl;
        Object var3_3 = null;
        object = bl ? this.mAudioManager.getRingtonePlayer() : null;
        this.mRemotePlayer = object;
        object = var3_3;
        if (bl) {
            object = new Binder();
        }
        this.mRemoteToken = object;
    }

    private void applyPlaybackProperties_sync() {
        Object object = this.mLocalPlayer;
        if (object != null) {
            ((MediaPlayer)object).setVolume(this.mVolume);
            this.mLocalPlayer.setLooping(this.mIsLooping);
        } else if (this.mAllowRemote && (object = this.mRemotePlayer) != null) {
            try {
                object.setPlaybackProperties(this.mRemoteToken, this.mVolume, this.mIsLooping);
            }
            catch (RemoteException remoteException) {
                Log.w(TAG, "Problem setting playback properties: ", remoteException);
            }
        } else {
            Log.w(TAG, "Neither local nor remote player available when applying playback properties");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void destroyLocalPlayer() {
        MediaPlayer mediaPlayer = this.mLocalPlayer;
        if (mediaPlayer == null) return;
        mediaPlayer.setOnCompletionListener(null);
        this.mLocalPlayer.reset();
        this.mLocalPlayer.release();
        this.mLocalPlayer = null;
        this.mVolumeShaper = null;
        ArrayList<Ringtone> arrayList = sActiveRingtones;
        synchronized (arrayList) {
            sActiveRingtones.remove(this);
            return;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String getTitle(Context var0, Uri var1_2, boolean var2_3, boolean var3_4) {
        block15 : {
            block11 : {
                block12 : {
                    block16 : {
                        block13 : {
                            block10 : {
                                block14 : {
                                    var4_5 = var0.getContentResolver();
                                    var5_6 = null;
                                    var6_7 = null;
                                    var7_8 = null;
                                    var8_9 = null;
                                    if (var1_2 == null) break block13;
                                    var9_10 = ContentProvider.getAuthorityWithoutUserId(var1_2.getAuthority());
                                    if (!"settings".equals(var9_10)) break block14;
                                    if (var2_3) {
                                        var7_8 = var0.getString(17040936, new Object[]{Ringtone.getTitle((Context)var0, RingtoneManager.getActualDefaultRingtoneUri((Context)var0, RingtoneManager.getDefaultType((Uri)var1_2)), false, var3_4)});
                                    }
                                    break block15;
                                }
                                var10_11 = null;
                                var11_12 = null;
                                var12_13 = null;
                                var13_14 = var10_11;
                                var7_8 = var11_12;
                                if (!"media".equals(var9_10)) break block10;
                                var12_13 = var3_4 != false ? null : "mime_type LIKE 'audio/%' OR mime_type IN ('application/ogg', 'application/x-flac')";
                                var13_14 = var10_11;
                                var7_8 = var11_12;
                                var12_13 = var10_11 = var4_5.query((Uri)var1_2, Ringtone.MEDIA_COLUMNS, (String)var12_13, null, null);
                                if (var10_11 == null) break block10;
                                var12_13 = var10_11;
                                var13_14 = var10_11;
                                var7_8 = var10_11;
                                if (var10_11.getCount() != 1) break block10;
                                var13_14 = var10_11;
                                var7_8 = var10_11;
                                var10_11.moveToFirst();
                                var13_14 = var10_11;
                                var7_8 = var10_11;
                                var12_13 = var10_11.getString(1);
                                var10_11.close();
                                return var12_13;
                            }
                            var13_14 = var5_6;
                            if (var12_13 == null) break block11;
                            var7_8 = var12_13;
                            var13_14 = var8_9;
                            break block16;
                        }
                        var7_8 = var0.getString(17040940);
                        break block15;
                    }
lbl48: // 2 sources:
                    do {
                        var7_8.close();
                        break block11;
                        break;
                    } while (true);
                    {
                        catch (Throwable var0_1) {
                            ** GOTO lbl64
                        }
                        catch (SecurityException var13_15) {}
                        var10_11 = null;
                        if (!var3_4) ** GOTO lbl58
                        var13_14 = var7_8;
                        {
                            var10_11 = ((AudioManager)var0.getSystemService("audio")).getRingtonePlayer();
lbl58: // 2 sources:
                            var12_13 = var6_7;
                            if (var10_11 == null) break block12;
                            var13_14 = var7_8;
                            try {
                                var12_13 = var10_11.getTitle((Uri)var1_2);
                            }
                            catch (RemoteException var13_16) {
                                var12_13 = var6_7;
                            }
lbl64: // 1 sources:
                            if (var13_14 == null) throw var0_1;
                            var13_14.close();
                            throw var0_1;
                        }
                    }
                }
                var13_14 = var12_13;
                if (var7_8 != null) {
                    var13_14 = var12_13;
                    ** continue;
                }
            }
            var7_8 = var13_14;
            if (var13_14 == null) {
                var7_8 = var1_2.getLastPathSegment();
            }
        }
        var1_2 = var7_8;
        if (var7_8 != null) return var1_2;
        var1_2 = var0 = var0.getString(17040941);
        if (var0 != null) return var1_2;
        return "";
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private boolean playFallbackRingtone() {
        if (this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(this.mAudioAttributes)) == 0) return false;
        var1_1 = RingtoneManager.getDefaultType(this.mUri);
        if (var1_1 != -1 && RingtoneManager.getActualDefaultRingtoneUri(this.mContext, var1_1) == null) {
            var2_2 = new StringBuilder();
            var2_2.append("not playing fallback for ");
            var2_2.append(this.mUri);
            Log.w("Ringtone", var2_2.toString());
            return false;
        }
        var2_3 = this.mContext.getResources().openRawResourceFd(17825797);
        if (var2_3 == null) ** GOTO lbl-1000
        var3_6 = new MediaPlayer();
        this.mLocalPlayer = var3_6;
        if (var2_3.getDeclaredLength() < 0L) {
            this.mLocalPlayer.setDataSource(var2_3.getFileDescriptor());
        } else {
            this.mLocalPlayer.setDataSource(var2_3.getFileDescriptor(), var2_3.getStartOffset(), var2_3.getDeclaredLength());
        }
        this.mLocalPlayer.setAudioAttributes(this.mAudioAttributes);
        var3_6 = this.mPlaybackSettingsLock;
        // MONITORENTER : var3_6
        this.applyPlaybackProperties_sync();
        // MONITOREXIT : var3_6
        try {
            if (this.mVolumeShaperConfig != null) {
                this.mVolumeShaper = this.mLocalPlayer.createVolumeShaper(this.mVolumeShaperConfig);
            }
            this.mLocalPlayer.prepare();
            this.startLocalPlayer();
            var2_3.close();
            return true;
        }
        catch (Resources.NotFoundException var2_4) {
            Log.e("Ringtone", "Fallback ringtone does not exist");
            return false;
        }
        catch (IOException var2_5) {
            this.destroyLocalPlayer();
            Log.e("Ringtone", "Failed to open fallback ringtone");
        }
lbl-1000: // 1 sources:
        {
            Log.e("Ringtone", "Could not load fallback ringtone");
            return false;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void startLocalPlayer() {
        if (this.mLocalPlayer == null) {
            return;
        }
        Object object = sActiveRingtones;
        synchronized (object) {
            sActiveRingtones.add(this);
        }
        this.mLocalPlayer.setOnCompletionListener(this.mCompletionListener);
        this.mLocalPlayer.start();
        object = this.mVolumeShaper;
        if (object != null) {
            ((VolumeShaper)object).apply(VolumeShaper.Operation.PLAY);
        }
    }

    protected void finalize() {
        MediaPlayer mediaPlayer = this.mLocalPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public AudioAttributes getAudioAttributes() {
        return this.mAudioAttributes;
    }

    @Deprecated
    public int getStreamType() {
        return AudioAttributes.toLegacyStreamType(this.mAudioAttributes);
    }

    public String getTitle(Context object) {
        String string2 = this.mTitle;
        if (string2 != null) {
            return string2;
        }
        this.mTitle = object = Ringtone.getTitle((Context)object, this.mUri, true, this.mAllowRemote);
        return object;
    }

    @UnsupportedAppUsage
    public Uri getUri() {
        return this.mUri;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public float getVolume() {
        Object object = this.mPlaybackSettingsLock;
        synchronized (object) {
            return this.mVolume;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isLooping() {
        Object object = this.mPlaybackSettingsLock;
        synchronized (object) {
            return this.mIsLooping;
        }
    }

    public boolean isPlaying() {
        Object object = this.mLocalPlayer;
        if (object != null) {
            return ((MediaPlayer)object).isPlaying();
        }
        if (this.mAllowRemote && (object = this.mRemotePlayer) != null) {
            try {
                boolean bl = object.isPlaying(this.mRemoteToken);
                return bl;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Problem checking ringtone: ");
                stringBuilder.append(remoteException);
                Log.w(TAG, stringBuilder.toString());
                return false;
            }
        }
        Log.w(TAG, "Neither local nor remote playback available");
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void play() {
        if (this.mLocalPlayer != null) {
            if (this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(this.mAudioAttributes)) == 0) return;
            this.startLocalPlayer();
            return;
        }
        if (this.mAllowRemote && this.mRemotePlayer != null) {
            float f;
            boolean bl;
            Uri uri = this.mUri.getCanonicalUri();
            Object object = this.mPlaybackSettingsLock;
            synchronized (object) {
                bl = this.mIsLooping;
                f = this.mVolume;
            }
            try {
                this.mRemotePlayer.playWithVolumeShaping(this.mRemoteToken, uri, this.mAudioAttributes, f, bl, this.mVolumeShaperConfig);
                return;
            }
            catch (RemoteException remoteException) {
                if (this.playFallbackRingtone()) return;
                object = new StringBuilder();
                ((StringBuilder)object).append("Problem playing ringtone: ");
                ((StringBuilder)object).append(remoteException);
                Log.w(TAG, ((StringBuilder)object).toString());
                return;
            }
        }
        if (this.playFallbackRingtone()) return;
        Log.w(TAG, "Neither local nor remote playback available");
    }

    public void setAudioAttributes(AudioAttributes audioAttributes) throws IllegalArgumentException {
        if (audioAttributes != null) {
            this.mAudioAttributes = audioAttributes;
            this.setUri(this.mUri, this.mVolumeShaperConfig);
            return;
        }
        throw new IllegalArgumentException("Invalid null AudioAttributes for Ringtone");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setLooping(boolean bl) {
        Object object = this.mPlaybackSettingsLock;
        synchronized (object) {
            this.mIsLooping = bl;
            this.applyPlaybackProperties_sync();
            return;
        }
    }

    @Deprecated
    public void setStreamType(int n) {
        PlayerBase.deprecateStreamTypeForPlayback(n, TAG, "setStreamType()");
        this.setAudioAttributes(new AudioAttributes.Builder().setInternalLegacyStreamType(n).build());
    }

    void setTitle(String string2) {
        this.mTitle = string2;
    }

    @UnsupportedAppUsage
    public void setUri(Uri uri) {
        this.setUri(uri, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setUri(Uri uri, VolumeShaper.Configuration object) {
        block10 : {
            this.mVolumeShaperConfig = object;
            this.destroyLocalPlayer();
            this.mUri = uri;
            if (this.mUri == null) {
                return;
            }
            this.mLocalPlayer = new MediaPlayer();
            this.mLocalPlayer.setDataSource(this.mContext, this.mUri);
            this.mLocalPlayer.setAudioAttributes(this.mAudioAttributes);
            object = this.mPlaybackSettingsLock;
            // MONITORENTER : object
            this.applyPlaybackProperties_sync();
            // MONITOREXIT : object
            try {
                if (this.mVolumeShaperConfig != null) {
                    this.mVolumeShaper = this.mLocalPlayer.createVolumeShaper(this.mVolumeShaperConfig);
                }
                this.mLocalPlayer.prepare();
            }
            catch (IOException | SecurityException exception) {
                this.destroyLocalPlayer();
                if (this.mAllowRemote) break block10;
                object = new StringBuilder();
                ((StringBuilder)object).append("Remote playback not allowed: ");
                ((StringBuilder)object).append(exception);
                Log.w(TAG, ((StringBuilder)object).toString());
            }
        }
        if (this.mLocalPlayer != null) {
            Log.d(TAG, "Successfully created local player");
            return;
        }
        Log.d(TAG, "Problem opening; delegating to remote player");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setVolume(float f) {
        Object object = this.mPlaybackSettingsLock;
        synchronized (object) {
            float f2 = f;
            if (f < 0.0f) {
                f2 = 0.0f;
            }
            f = f2;
            if (f2 > 1.0f) {
                f = 1.0f;
            }
            this.mVolume = f;
            this.applyPlaybackProperties_sync();
            return;
        }
    }

    public void stop() {
        Object object;
        if (this.mLocalPlayer != null) {
            this.destroyLocalPlayer();
        } else if (this.mAllowRemote && (object = this.mRemotePlayer) != null) {
            try {
                object.stop(this.mRemoteToken);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Problem stopping ringtone: ");
                ((StringBuilder)object).append(remoteException);
                Log.w(TAG, ((StringBuilder)object).toString());
            }
        }
    }

    class MyOnCompletionListener
    implements MediaPlayer.OnCompletionListener {
        MyOnCompletionListener() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            ArrayList arrayList = sActiveRingtones;
            synchronized (arrayList) {
                sActiveRingtones.remove(Ringtone.this);
            }
            mediaPlayer.setOnCompletionListener(null);
        }
    }

}

