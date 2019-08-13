/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.MediaMetadataEditor;
import android.media.Rating;
import android.media.session.MediaSession;
import android.media.session.MediaSessionLegacyHelper;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

@Deprecated
public class RemoteControlClient {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_PLAYBACK_VOLUME = 15;
    public static final int DEFAULT_PLAYBACK_VOLUME_HANDLING = 1;
    public static final int FLAGS_KEY_MEDIA_NONE = 0;
    public static final int FLAG_INFORMATION_REQUEST_ALBUM_ART = 8;
    public static final int FLAG_INFORMATION_REQUEST_KEY_MEDIA = 2;
    public static final int FLAG_INFORMATION_REQUEST_METADATA = 1;
    public static final int FLAG_INFORMATION_REQUEST_PLAYSTATE = 4;
    public static final int FLAG_KEY_MEDIA_FAST_FORWARD = 64;
    public static final int FLAG_KEY_MEDIA_NEXT = 128;
    public static final int FLAG_KEY_MEDIA_PAUSE = 16;
    public static final int FLAG_KEY_MEDIA_PLAY = 4;
    public static final int FLAG_KEY_MEDIA_PLAY_PAUSE = 8;
    public static final int FLAG_KEY_MEDIA_POSITION_UPDATE = 256;
    public static final int FLAG_KEY_MEDIA_PREVIOUS = 1;
    public static final int FLAG_KEY_MEDIA_RATING = 512;
    public static final int FLAG_KEY_MEDIA_REWIND = 2;
    public static final int FLAG_KEY_MEDIA_STOP = 32;
    @UnsupportedAppUsage
    public static int MEDIA_POSITION_READABLE = 0;
    @UnsupportedAppUsage
    public static int MEDIA_POSITION_WRITABLE = 0;
    public static final int PLAYBACKINFO_INVALID_VALUE = Integer.MIN_VALUE;
    public static final int PLAYBACKINFO_PLAYBACK_TYPE = 1;
    public static final int PLAYBACKINFO_USES_STREAM = 5;
    public static final int PLAYBACKINFO_VOLUME = 2;
    public static final int PLAYBACKINFO_VOLUME_HANDLING = 4;
    public static final int PLAYBACKINFO_VOLUME_MAX = 3;
    public static final long PLAYBACK_POSITION_ALWAYS_UNKNOWN = -9216204211029966080L;
    public static final long PLAYBACK_POSITION_INVALID = -1L;
    public static final float PLAYBACK_SPEED_1X = 1.0f;
    public static final int PLAYBACK_TYPE_LOCAL = 0;
    private static final int PLAYBACK_TYPE_MAX = 1;
    private static final int PLAYBACK_TYPE_MIN = 0;
    public static final int PLAYBACK_TYPE_REMOTE = 1;
    public static final int PLAYBACK_VOLUME_FIXED = 0;
    public static final int PLAYBACK_VOLUME_VARIABLE = 1;
    public static final int PLAYSTATE_BUFFERING = 8;
    public static final int PLAYSTATE_ERROR = 9;
    public static final int PLAYSTATE_FAST_FORWARDING = 4;
    public static final int PLAYSTATE_NONE = 0;
    public static final int PLAYSTATE_PAUSED = 2;
    public static final int PLAYSTATE_PLAYING = 3;
    public static final int PLAYSTATE_REWINDING = 5;
    public static final int PLAYSTATE_SKIPPING_BACKWARDS = 7;
    public static final int PLAYSTATE_SKIPPING_FORWARDS = 6;
    public static final int PLAYSTATE_STOPPED = 1;
    private static final long POSITION_DRIFT_MAX_MS = 500L;
    private static final long POSITION_REFRESH_PERIOD_MIN_MS = 2000L;
    private static final long POSITION_REFRESH_PERIOD_PLAYING_MS = 15000L;
    public static final int RCSE_ID_UNREGISTERED = -1;
    private static final String TAG = "RemoteControlClient";
    private final Object mCacheLock = new Object();
    private int mCurrentClientGenId = -1;
    private MediaMetadata mMediaMetadata;
    private Bundle mMetadata = new Bundle();
    private OnMetadataUpdateListener mMetadataUpdateListener;
    private boolean mNeedsPositionSync = false;
    private Bitmap mOriginalArtwork;
    private long mPlaybackPositionMs = -1L;
    private float mPlaybackSpeed = 1.0f;
    private int mPlaybackState = 0;
    private long mPlaybackStateChangeTimeMs = 0L;
    private OnGetPlaybackPositionListener mPositionProvider;
    private OnPlaybackPositionUpdateListener mPositionUpdateListener;
    private final PendingIntent mRcMediaIntent;
    private MediaSession mSession;
    private PlaybackState mSessionPlaybackState = null;
    private int mTransportControlFlags = 0;
    private MediaSession.Callback mTransportListener = new MediaSession.Callback(){

        @Override
        public void onSeekTo(long l) {
            RemoteControlClient remoteControlClient = RemoteControlClient.this;
            remoteControlClient.onSeekTo(remoteControlClient.mCurrentClientGenId, l);
        }

        @Override
        public void onSetRating(Rating rating) {
            if ((RemoteControlClient.this.mTransportControlFlags & 512) != 0) {
                RemoteControlClient remoteControlClient = RemoteControlClient.this;
                remoteControlClient.onUpdateMetadata(remoteControlClient.mCurrentClientGenId, 268435457, rating);
            }
        }
    };

    static {
        MEDIA_POSITION_READABLE = 1;
        MEDIA_POSITION_WRITABLE = 2;
    }

    public RemoteControlClient(PendingIntent pendingIntent) {
        this.mRcMediaIntent = pendingIntent;
    }

    public RemoteControlClient(PendingIntent pendingIntent, Looper looper) {
        this.mRcMediaIntent = pendingIntent;
    }

    private static long getActionForRccFlag(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 32) {
                                if (n != 64) {
                                    if (n != 128) {
                                        if (n != 256) {
                                            if (n != 512) {
                                                return 0L;
                                            }
                                            return 128L;
                                        }
                                        return 256L;
                                    }
                                    return 32L;
                                }
                                return 64L;
                            }
                            return 1L;
                        }
                        return 2L;
                    }
                    return 512L;
                }
                return 4L;
            }
            return 8L;
        }
        return 16L;
    }

    private static long getActionsFromRccControlFlags(int n) {
        long l = 0L;
        for (long i = 1L; i <= (long)n; i <<= 1) {
            long l2 = l;
            if (((long)n & i) != 0L) {
                l2 = l | RemoteControlClient.getActionForRccFlag((int)i);
            }
            l = l2;
        }
        return l;
    }

    private static long getCheckPeriodFromSpeed(float f) {
        if (Math.abs(f) <= 1.0f) {
            return 15000L;
        }
        return Math.max((long)(15000.0f / Math.abs(f)), 2000L);
    }

    static int getRccControlFlagsFromActions(long l) {
        int n = 0;
        for (long i = 1L; i <= l && i < Integer.MAX_VALUE; i <<= 1) {
            int n2 = n;
            if ((i & l) != 0L) {
                n2 = n | RemoteControlClient.getRccFlagForAction(i);
            }
            n = n2;
        }
        return n;
    }

    private static int getRccFlagForAction(long l) {
        int n = l < Integer.MAX_VALUE ? (int)l : 0;
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 32) {
                                if (n != 64) {
                                    if (n != 128) {
                                        if (n != 256) {
                                            if (n != 512) {
                                                return 0;
                                            }
                                            return 8;
                                        }
                                        return 256;
                                    }
                                    return 512;
                                }
                                return 64;
                            }
                            return 128;
                        }
                        return 1;
                    }
                    return 2;
                }
                return 4;
            }
            return 16;
        }
        return 32;
    }

    static int getRccStateFromState(int n) {
        switch (n) {
            default: {
                return -1;
            }
            case 10: {
                return 6;
            }
            case 9: {
                return 7;
            }
            case 7: {
                return 9;
            }
            case 6: {
                return 8;
            }
            case 5: {
                return 5;
            }
            case 4: {
                return 4;
            }
            case 3: {
                return 3;
            }
            case 2: {
                return 2;
            }
            case 1: {
                return 1;
            }
            case 0: 
        }
        return 0;
    }

    private static int getStateFromRccState(int n) {
        switch (n) {
            default: {
                return -1;
            }
            case 9: {
                return 7;
            }
            case 8: {
                return 6;
            }
            case 7: {
                return 9;
            }
            case 6: {
                return 10;
            }
            case 5: {
                return 5;
            }
            case 4: {
                return 4;
            }
            case 3: {
                return 3;
            }
            case 2: {
                return 2;
            }
            case 1: {
                return 1;
            }
            case 0: 
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onSeekTo(int n, long l) {
        Object object = this.mCacheLock;
        synchronized (object) {
            if (this.mCurrentClientGenId == n && this.mPositionUpdateListener != null) {
                this.mPositionUpdateListener.onPlaybackPositionUpdate(l);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onUpdateMetadata(int n, int n2, Object object) {
        Object object2 = this.mCacheLock;
        synchronized (object2) {
            if (this.mCurrentClientGenId == n && this.mMetadataUpdateListener != null) {
                this.mMetadataUpdateListener.onMetadataUpdate(n2, object);
            }
            return;
        }
    }

    static boolean playbackPositionShouldMove(int n) {
        if (n != 1 && n != 2) {
            switch (n) {
                default: {
                    return true;
                }
                case 6: 
                case 7: 
                case 8: 
                case 9: 
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setPlaybackStateInt(int n, long l, float f, boolean bl) {
        Object object = this.mCacheLock;
        synchronized (object) {
            if (this.mPlaybackState != n || this.mPlaybackPositionMs != l || this.mPlaybackSpeed != f) {
                this.mPlaybackState = n;
                long l2 = -1L;
                this.mPlaybackPositionMs = bl ? (l < 0L ? -1L : l) : -9216204211029966080L;
                this.mPlaybackSpeed = f;
                this.mPlaybackStateChangeTimeMs = SystemClock.elapsedRealtime();
                if (this.mSession != null) {
                    n = RemoteControlClient.getStateFromRccState(n);
                    l = bl ? this.mPlaybackPositionMs : l2;
                    PlaybackState.Builder builder = new PlaybackState.Builder(this.mSessionPlaybackState);
                    builder.setState(n, l, f, SystemClock.elapsedRealtime());
                    builder.setErrorMessage(null);
                    this.mSessionPlaybackState = builder.build();
                    this.mSession.setPlaybackState(this.mSessionPlaybackState);
                }
            }
            return;
        }
    }

    public MetadataEditor editMetadata(boolean bl) {
        MediaMetadata mediaMetadata;
        MetadataEditor metadataEditor = new MetadataEditor();
        if (bl) {
            metadataEditor.mEditorMetadata = new Bundle();
            metadataEditor.mEditorArtwork = null;
            metadataEditor.mMetadataChanged = true;
            metadataEditor.mArtworkChanged = true;
            metadataEditor.mEditableKeys = 0L;
        } else {
            metadataEditor.mEditorMetadata = new Bundle(this.mMetadata);
            metadataEditor.mEditorArtwork = this.mOriginalArtwork;
            metadataEditor.mMetadataChanged = false;
            metadataEditor.mArtworkChanged = false;
        }
        metadataEditor.mMetadataBuilder = !bl && (mediaMetadata = this.mMediaMetadata) != null ? new MediaMetadata.Builder(mediaMetadata) : new MediaMetadata.Builder();
        return metadataEditor;
    }

    public MediaSession getMediaSession() {
        return this.mSession;
    }

    public PendingIntent getRcMediaIntent() {
        return this.mRcMediaIntent;
    }

    public void registerWithSession(MediaSessionLegacyHelper mediaSessionLegacyHelper) {
        mediaSessionLegacyHelper.addRccListener(this.mRcMediaIntent, this.mTransportListener);
        this.mSession = mediaSessionLegacyHelper.getSession(this.mRcMediaIntent);
        this.setTransportControlFlags(this.mTransportControlFlags);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMetadataUpdateListener(OnMetadataUpdateListener onMetadataUpdateListener) {
        Object object = this.mCacheLock;
        synchronized (object) {
            this.mMetadataUpdateListener = onMetadataUpdateListener;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnGetPlaybackPositionListener(OnGetPlaybackPositionListener onGetPlaybackPositionListener) {
        Object object = this.mCacheLock;
        synchronized (object) {
            this.mPositionProvider = onGetPlaybackPositionListener;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPlaybackPositionUpdateListener(OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener) {
        Object object = this.mCacheLock;
        synchronized (object) {
            this.mPositionUpdateListener = onPlaybackPositionUpdateListener;
            return;
        }
    }

    public void setPlaybackState(int n) {
        this.setPlaybackStateInt(n, -9216204211029966080L, 1.0f, false);
    }

    public void setPlaybackState(int n, long l, float f) {
        this.setPlaybackStateInt(n, l, f, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setTransportControlFlags(int n) {
        Object object = this.mCacheLock;
        synchronized (object) {
            this.mTransportControlFlags = n;
            if (this.mSession != null) {
                PlaybackState.Builder builder = new PlaybackState.Builder(this.mSessionPlaybackState);
                builder.setActions(RemoteControlClient.getActionsFromRccControlFlags(n));
                this.mSessionPlaybackState = builder.build();
                this.mSession.setPlaybackState(this.mSessionPlaybackState);
            }
            return;
        }
    }

    public void unregisterWithSession(MediaSessionLegacyHelper mediaSessionLegacyHelper) {
        mediaSessionLegacyHelper.removeRccListener(this.mRcMediaIntent);
        this.mSession = null;
    }

    @Deprecated
    public class MetadataEditor
    extends MediaMetadataEditor {
        public static final int BITMAP_KEY_ARTWORK = 100;
        public static final int METADATA_KEY_ARTWORK = 100;

        private MetadataEditor() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void apply() {
            synchronized (this) {
                if (this.mApplied) {
                    Log.e(RemoteControlClient.TAG, "Can't apply a previously applied MetadataEditor");
                    return;
                }
                Object object = RemoteControlClient.this.mCacheLock;
                synchronized (object) {
                    block16 : {
                        Object object2;
                        block15 : {
                            object2 = RemoteControlClient.this;
                            Bundle bundle = new Bundle(this.mEditorMetadata);
                            ((RemoteControlClient)object2).mMetadata = bundle;
                            RemoteControlClient.this.mMetadata.putLong(String.valueOf(536870911), this.mEditableKeys);
                            object2 = RemoteControlClient.this.mOriginalArtwork;
                            if (object2 != null) {
                                if (RemoteControlClient.this.mOriginalArtwork.equals(this.mEditorArtwork)) break block15;
                                RemoteControlClient.this.mOriginalArtwork.recycle();
                            }
                        }
                        RemoteControlClient.this.mOriginalArtwork = this.mEditorArtwork;
                        this.mEditorArtwork = null;
                        object2 = RemoteControlClient.this.mSession;
                        if (object2 != null) {
                            if (this.mMetadataBuilder == null) break block16;
                            RemoteControlClient.this.mMediaMetadata = this.mMetadataBuilder.build();
                            RemoteControlClient.this.mSession.setMetadata(RemoteControlClient.this.mMediaMetadata);
                        }
                    }
                    this.mApplied = true;
                    return;
                    {
                        try {}
                        catch (Throwable throwable) {
                            continue;
                        }
                        throw throwable;
                    }
                }
            }
        }

        @Override
        public void clear() {
            synchronized (this) {
                super.clear();
                return;
            }
        }

        public Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public MetadataEditor putBitmap(int n, Bitmap bitmap) throws IllegalArgumentException {
            synchronized (this) {
                String string2;
                super.putBitmap(n, bitmap);
                if (this.mMetadataBuilder != null && (string2 = MediaMetadata.getKeyFromMetadataEditorKey(n)) != null) {
                    this.mMetadataBuilder.putBitmap(string2, bitmap);
                }
                return this;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public MetadataEditor putLong(int n, long l) throws IllegalArgumentException {
            synchronized (this) {
                String string2;
                super.putLong(n, l);
                if (this.mMetadataBuilder != null && (string2 = MediaMetadata.getKeyFromMetadataEditorKey(n)) != null) {
                    this.mMetadataBuilder.putLong(string2, l);
                }
                return this;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public MetadataEditor putObject(int n, Object object) throws IllegalArgumentException {
            synchronized (this) {
                String string2;
                super.putObject(n, object);
                if (this.mMetadataBuilder != null && (n == 268435457 || n == 101) && (string2 = MediaMetadata.getKeyFromMetadataEditorKey(n)) != null) {
                    this.mMetadataBuilder.putRating(string2, (Rating)object);
                }
                return this;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public MetadataEditor putString(int n, String string2) throws IllegalArgumentException {
            synchronized (this) {
                String string3;
                super.putString(n, string2);
                if (this.mMetadataBuilder != null && (string3 = MediaMetadata.getKeyFromMetadataEditorKey(n)) != null) {
                    this.mMetadataBuilder.putText(string3, string2);
                }
                return this;
            }
        }
    }

    public static interface OnGetPlaybackPositionListener {
        public long onGetPlaybackPosition();
    }

    public static interface OnMetadataUpdateListener {
        public void onMetadataUpdate(int var1, Object var2);
    }

    public static interface OnPlaybackPositionUpdateListener {
        public void onPlaybackPositionUpdate(long var1);
    }

}

