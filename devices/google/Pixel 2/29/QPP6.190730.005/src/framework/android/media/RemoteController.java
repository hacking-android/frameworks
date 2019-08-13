/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.MediaMetadataEditor;
import android.media.Rating;
import android.media.RemoteControlClient;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionLegacyHelper;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import java.util.List;

@Deprecated
public final class RemoteController {
    private static final boolean DEBUG = false;
    private static final int MAX_BITMAP_DIMENSION = 512;
    private static final int MSG_CLIENT_CHANGE = 0;
    private static final int MSG_NEW_MEDIA_METADATA = 2;
    private static final int MSG_NEW_PLAYBACK_STATE = 1;
    public static final int POSITION_SYNCHRONIZATION_CHECK = 1;
    public static final int POSITION_SYNCHRONIZATION_NONE = 0;
    private static final int SENDMSG_NOOP = 1;
    private static final int SENDMSG_QUEUE = 2;
    private static final int SENDMSG_REPLACE = 0;
    private static final String TAG = "RemoteController";
    private static final Object mInfoLock = new Object();
    private int mArtworkHeight;
    private int mArtworkWidth;
    private final Context mContext;
    @UnsupportedAppUsage
    private MediaController mCurrentSession;
    private boolean mEnabled;
    private final EventHandler mEventHandler;
    private boolean mIsRegistered;
    private PlaybackInfo mLastPlaybackInfo;
    private final int mMaxBitmapDimension;
    private MetadataEditor mMetadataEditor;
    private OnClientUpdateListener mOnClientUpdateListener;
    private MediaController.Callback mSessionCb;
    private MediaSessionManager.OnActiveSessionsChangedListener mSessionListener;
    private MediaSessionManager mSessionManager;

    public RemoteController(Context context, OnClientUpdateListener onClientUpdateListener) throws IllegalArgumentException {
        this(context, onClientUpdateListener, null);
    }

    public RemoteController(Context object, OnClientUpdateListener onClientUpdateListener, Looper looper) throws IllegalArgumentException {
        block5 : {
            block6 : {
                block9 : {
                    block8 : {
                        block7 : {
                            this.mSessionCb = new MediaControllerCallback();
                            this.mIsRegistered = false;
                            this.mArtworkWidth = -1;
                            this.mArtworkHeight = -1;
                            this.mEnabled = true;
                            if (object == null) break block5;
                            if (onClientUpdateListener == null) break block6;
                            if (looper == null) break block7;
                            this.mEventHandler = new EventHandler(this, looper);
                            break block8;
                        }
                        looper = Looper.myLooper();
                        if (looper == null) break block9;
                        this.mEventHandler = new EventHandler(this, looper);
                    }
                    this.mOnClientUpdateListener = onClientUpdateListener;
                    this.mContext = object;
                    this.mSessionManager = (MediaSessionManager)((Context)object).getSystemService("media_session");
                    this.mSessionListener = new TopTransportSessionListener();
                    if (ActivityManager.isLowRamDeviceStatic()) {
                        this.mMaxBitmapDimension = 512;
                    } else {
                        object = ((Context)object).getResources().getDisplayMetrics();
                        this.mMaxBitmapDimension = Math.max(((DisplayMetrics)object).widthPixels, ((DisplayMetrics)object).heightPixels);
                    }
                    return;
                }
                throw new IllegalArgumentException("Calling thread not associated with a looper");
            }
            throw new IllegalArgumentException("Invalid null OnClientUpdateListener");
        }
        throw new IllegalArgumentException("Invalid null Context");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void onClientChange(boolean bl) {
        Object object = mInfoLock;
        // MONITORENTER : object
        OnClientUpdateListener onClientUpdateListener = this.mOnClientUpdateListener;
        this.mMetadataEditor = null;
        // MONITOREXIT : object
        if (onClientUpdateListener == null) return;
        onClientUpdateListener.onClientChange(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void onNewMediaMetadata(MediaMetadata object) {
        if (object == null) {
            return;
        }
        Object object2 = mInfoLock;
        // MONITORENTER : object2
        OnClientUpdateListener onClientUpdateListener = this.mOnClientUpdateListener;
        boolean bl = this.mCurrentSession != null && this.mCurrentSession.getRatingType() != 0;
        long l = bl ? 0x10000001L : 0L;
        Bundle bundle = MediaSessionLegacyHelper.getOldMetadata((MediaMetadata)object, this.mArtworkWidth, this.mArtworkHeight);
        object = this.mMetadataEditor = (object = new MetadataEditor(bundle, l));
        // MONITOREXIT : object2
        if (onClientUpdateListener == null) return;
        onClientUpdateListener.onClientMetadataUpdate((MetadataEditor)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void onNewPlaybackState(PlaybackState playbackState) {
        Object object = mInfoLock;
        // MONITORENTER : object
        OnClientUpdateListener onClientUpdateListener = this.mOnClientUpdateListener;
        // MONITOREXIT : object
        if (onClientUpdateListener == null) return;
        int n = playbackState == null ? 0 : RemoteControlClient.getRccStateFromState(playbackState.getState());
        if (playbackState != null && playbackState.getPosition() != -1L) {
            onClientUpdateListener.onClientPlaybackStateUpdate(n, playbackState.getLastPositionUpdateTime(), playbackState.getPosition(), playbackState.getPlaybackSpeed());
        } else {
            onClientUpdateListener.onClientPlaybackStateUpdate(n);
        }
        if (playbackState == null) return;
        onClientUpdateListener.onClientTransportControlUpdate(RemoteControlClient.getRccControlFlagsFromActions(playbackState.getActions()));
    }

    private static void sendMsg(Handler object, int n, int n2, int n3, int n4, Object object2, int n5) {
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("null event handler, will not deliver message ");
            ((StringBuilder)object).append(n);
            Log.e(TAG, ((StringBuilder)object).toString());
            return;
        }
        if (n2 == 0) {
            ((Handler)object).removeMessages(n);
        } else if (n2 == 1 && ((Handler)object).hasMessages(n)) {
            return;
        }
        ((Handler)object).sendMessageDelayed(((Handler)object).obtainMessage(n, n3, n4, object2), n5);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateController(MediaController object) {
        Object object2 = mInfoLock;
        synchronized (object2) {
            if (object == null) {
                if (this.mCurrentSession != null) {
                    this.mCurrentSession.unregisterCallback(this.mSessionCb);
                    this.mCurrentSession = null;
                    RemoteController.sendMsg(this.mEventHandler, 0, 0, 0, 1, null, 0);
                }
            } else if (this.mCurrentSession == null || !((MediaController)object).getSessionToken().equals(this.mCurrentSession.getSessionToken())) {
                if (this.mCurrentSession != null) {
                    this.mCurrentSession.unregisterCallback(this.mSessionCb);
                }
                RemoteController.sendMsg(this.mEventHandler, 0, 0, 0, 0, null, 0);
                this.mCurrentSession = object;
                this.mCurrentSession.registerCallback(this.mSessionCb, this.mEventHandler);
                PlaybackState playbackState = ((MediaController)object).getPlaybackState();
                RemoteController.sendMsg(this.mEventHandler, 1, 0, 0, 0, playbackState, 0);
                object = ((MediaController)object).getMetadata();
                RemoteController.sendMsg(this.mEventHandler, 2, 0, 0, 0, object, 0);
            }
            return;
        }
    }

    public boolean clearArtworkConfiguration() {
        return this.setArtworkConfiguration(false, -1, -1);
    }

    public MetadataEditor editMetadata() {
        MetadataEditor metadataEditor = new MetadataEditor();
        metadataEditor.mEditorMetadata = new Bundle();
        metadataEditor.mEditorArtwork = null;
        metadataEditor.mMetadataChanged = true;
        metadataEditor.mArtworkChanged = true;
        metadataEditor.mEditableKeys = 0L;
        return metadataEditor;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getEstimatedMediaPosition() {
        Object object = mInfoLock;
        synchronized (object) {
            if (this.mCurrentSession == null) return -1L;
            PlaybackState playbackState = this.mCurrentSession.getPlaybackState();
            if (playbackState == null) return -1L;
            return playbackState.getPosition();
        }
    }

    @UnsupportedAppUsage
    OnClientUpdateListener getUpdateListener() {
        return this.mOnClientUpdateListener;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean seekTo(long l) throws IllegalArgumentException {
        if (!this.mEnabled) {
            Log.e(TAG, "Cannot use seekTo() from a disabled RemoteController");
            return false;
        }
        if (l < 0L) {
            throw new IllegalArgumentException("illegal negative time value");
        }
        Object object = mInfoLock;
        synchronized (object) {
            if (this.mCurrentSession != null) {
                this.mCurrentSession.getTransportControls().seekTo(l);
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean sendMediaKeyEvent(KeyEvent keyEvent) throws IllegalArgumentException {
        if (!KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
            throw new IllegalArgumentException("not a media key event");
        }
        Object object = mInfoLock;
        synchronized (object) {
            if (this.mCurrentSession == null) return false;
            return this.mCurrentSession.dispatchMediaButtonEvent(keyEvent);
        }
    }

    public boolean setArtworkConfiguration(int n, int n2) throws IllegalArgumentException {
        return this.setArtworkConfiguration(true, n, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public boolean setArtworkConfiguration(boolean bl, int n, int n2) throws IllegalArgumentException {
        Object object = mInfoLock;
        // MONITORENTER : object
        if (!bl) {
            this.mArtworkWidth = -1;
            this.mArtworkHeight = -1;
            // MONITOREXIT : object
            return true;
        }
        if (n > 0 && n2 > 0) {
            int n3 = n;
            if (n > this.mMaxBitmapDimension) {
                n3 = this.mMaxBitmapDimension;
            }
            n = n2;
            if (n2 > this.mMaxBitmapDimension) {
                n = this.mMaxBitmapDimension;
            }
            this.mArtworkWidth = n3;
            this.mArtworkHeight = n;
            return true;
        }
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Invalid dimensions");
        throw illegalArgumentException;
    }

    public boolean setSynchronizationMode(int n) throws IllegalArgumentException {
        if (n != 0 && n != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown synchronization mode ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (!this.mIsRegistered) {
            Log.e(TAG, "Cannot set synchronization mode on an unregistered RemoteController");
            return false;
        }
        return true;
    }

    void startListeningToSessions() {
        ComponentName componentName = new ComponentName(this.mContext, this.mOnClientUpdateListener.getClass());
        Handler handler = null;
        if (Looper.myLooper() == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        this.mSessionManager.addOnActiveSessionsChangedListener(this.mSessionListener, componentName, UserHandle.myUserId(), handler);
        this.mSessionListener.onActiveSessionsChanged(this.mSessionManager.getActiveSessions(componentName));
    }

    void stopListeningToSessions() {
        this.mSessionManager.removeOnActiveSessionsChangedListener(this.mSessionListener);
    }

    private class EventHandler
    extends Handler {
        public EventHandler(RemoteController remoteController2, Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            boolean bl = true;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unknown event ");
                        stringBuilder.append(message.what);
                        Log.e(RemoteController.TAG, stringBuilder.toString());
                    } else {
                        RemoteController.this.onNewMediaMetadata((MediaMetadata)message.obj);
                    }
                } else {
                    RemoteController.this.onNewPlaybackState((PlaybackState)message.obj);
                }
            } else {
                RemoteController remoteController = RemoteController.this;
                if (message.arg2 != 1) {
                    bl = false;
                }
                remoteController.onClientChange(bl);
            }
        }
    }

    private class MediaControllerCallback
    extends MediaController.Callback {
        private MediaControllerCallback() {
        }

        @Override
        public void onMetadataChanged(MediaMetadata mediaMetadata) {
            RemoteController.this.onNewMediaMetadata(mediaMetadata);
        }

        @Override
        public void onPlaybackStateChanged(PlaybackState playbackState) {
            RemoteController.this.onNewPlaybackState(playbackState);
        }
    }

    public class MetadataEditor
    extends MediaMetadataEditor {
        protected MetadataEditor() {
        }

        protected MetadataEditor(Bundle bundle, long l) {
            this.mEditorMetadata = bundle;
            this.mEditableKeys = l;
            this.mEditorArtwork = (Bitmap)bundle.getParcelable(String.valueOf(100));
            if (this.mEditorArtwork != null) {
                this.cleanupBitmapFromBundle(100);
            }
            this.mMetadataChanged = true;
            this.mArtworkChanged = true;
            this.mApplied = false;
        }

        private void cleanupBitmapFromBundle(int n) {
            if (METADATA_KEYS_TYPE.get(n, -1) == 2) {
                this.mEditorMetadata.remove(String.valueOf(n));
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void apply() {
            // MONITORENTER : this
            boolean bl = this.mMetadataChanged;
            if (!bl) {
                // MONITOREXIT : this
                return;
            }
            Object object = mInfoLock;
            // MONITORENTER : object
            Object object2 = RemoteController.this.mCurrentSession;
            if (object2 != null) {
                if (this.mEditorMetadata.containsKey(String.valueOf(268435457)) && (object2 = (Rating)this.getObject(268435457, null)) != null) {
                    RemoteController.this.mCurrentSession.getTransportControls().setRating((Rating)object2);
                }
            }
            // MONITOREXIT : object
            this.mApplied = false;
            // MONITOREXIT : this
        }
    }

    public static interface OnClientUpdateListener {
        public void onClientChange(boolean var1);

        public void onClientMetadataUpdate(MetadataEditor var1);

        public void onClientPlaybackStateUpdate(int var1);

        public void onClientPlaybackStateUpdate(int var1, long var2, long var4, float var6);

        public void onClientTransportControlUpdate(int var1);
    }

    private static class PlaybackInfo {
        long mCurrentPosMs;
        float mSpeed;
        int mState;
        long mStateChangeTimeMs;

        PlaybackInfo(int n, long l, long l2, float f) {
            this.mState = n;
            this.mStateChangeTimeMs = l;
            this.mCurrentPosMs = l2;
            this.mSpeed = f;
        }
    }

    private class TopTransportSessionListener
    implements MediaSessionManager.OnActiveSessionsChangedListener {
        private TopTransportSessionListener() {
        }

        @Override
        public void onActiveSessionsChanged(List<MediaController> list) {
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                MediaController mediaController = list.get(i);
                if ((2L & mediaController.getFlags()) == 0L) continue;
                RemoteController.this.updateController(mediaController);
                return;
            }
            RemoteController.this.updateController(null);
        }
    }

}

