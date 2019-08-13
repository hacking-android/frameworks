/*
 * Decompiled with CFR 0.145.
 */
package android.media.session;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.VolumeProvider;
import android.media.session.ISession;
import android.media.session.ISessionCallback;
import android.media.session.ISessionController;
import android.media.session.ISessionControllerCallback;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

public final class MediaSession {
    public static final int FLAG_EXCLUSIVE_GLOBAL_PRIORITY = 65536;
    @Deprecated
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
    @Deprecated
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    public static final int INVALID_PID = -1;
    public static final int INVALID_UID = -1;
    static final String TAG = "MediaSession";
    private boolean mActive = false;
    private final ISession mBinder;
    @UnsupportedAppUsage
    private CallbackMessageHandler mCallback;
    private final CallbackStub mCbStub;
    private final MediaController mController;
    private final Object mLock = new Object();
    private final int mMaxBitmapSize;
    private PlaybackState mPlaybackState;
    private final Token mSessionToken;
    private VolumeProvider mVolumeProvider;

    public MediaSession(Context context, String string2) {
        this(context, string2, null);
    }

    public MediaSession(Context context, String object, Bundle bundle) {
        if (context != null) {
            if (!TextUtils.isEmpty((CharSequence)object)) {
                if (!MediaSession.hasCustomParcelable(bundle)) {
                    this.mMaxBitmapSize = context.getResources().getDimensionPixelSize(17105061);
                    this.mCbStub = new CallbackStub(this);
                    MediaSessionManager mediaSessionManager = (MediaSessionManager)context.getSystemService("media_session");
                    try {
                        this.mBinder = mediaSessionManager.createSession(this.mCbStub, (String)object, bundle);
                        this.mSessionToken = object = new Token(this.mBinder.getController());
                        this.mController = object = new MediaController(context, this.mSessionToken);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        throw new RuntimeException("Remote error creating session.", remoteException);
                    }
                }
                throw new IllegalArgumentException("sessionInfo shouldn't contain any custom parcelables");
            }
            throw new IllegalArgumentException("tag cannot be null or empty");
        }
        throw new IllegalArgumentException("context cannot be null.");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static boolean hasCustomParcelable(Bundle bundle) {
        Throwable throwable2222;
        Parcel parcel;
        if (bundle == null) {
            return false;
        }
        Parcel parcel2 = null;
        Parcel parcel3 = null;
        parcel3 = parcel = Parcel.obtain();
        parcel2 = parcel;
        parcel.writeBundle(bundle);
        parcel3 = parcel;
        parcel2 = parcel;
        parcel.setDataPosition(0);
        parcel3 = parcel;
        parcel2 = parcel;
        parcel.readBundle(null).size();
        parcel.recycle();
        return false;
        {
            catch (Throwable throwable2222) {
            }
            catch (BadParcelableException badParcelableException) {}
            parcel3 = parcel2;
            {
                Log.d(TAG, "Custom parcelable in bundle.", badParcelableException);
                if (parcel2 == null) return true;
                parcel2.recycle();
                return true;
            }
        }
        if (parcel3 == null) throw throwable2222;
        parcel3.recycle();
        throw throwable2222;
    }

    public static boolean isActiveState(int n) {
        switch (n) {
            default: {
                return false;
            }
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 8: 
            case 9: 
            case 10: 
        }
        return true;
    }

    void dispatchAdjustVolume(MediaSessionManager.RemoteUserInfo remoteUserInfo, int n) {
        this.postToCallback(remoteUserInfo, 22, n, null);
    }

    void dispatchCommand(MediaSessionManager.RemoteUserInfo remoteUserInfo, String string2, Bundle bundle, ResultReceiver resultReceiver) {
        this.postToCallback(remoteUserInfo, 1, new Command(string2, bundle, resultReceiver), null);
    }

    void dispatchCustomAction(MediaSessionManager.RemoteUserInfo remoteUserInfo, String string2, Bundle bundle) {
        this.postToCallback(remoteUserInfo, 21, string2, bundle);
    }

    void dispatchFastForward(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        this.postToCallback(remoteUserInfo, 16, null, null);
    }

    void dispatchMediaButton(MediaSessionManager.RemoteUserInfo remoteUserInfo, Intent intent) {
        this.postToCallback(remoteUserInfo, 2, intent, null);
    }

    void dispatchMediaButtonDelayed(MediaSessionManager.RemoteUserInfo remoteUserInfo, Intent intent, long l) {
        this.postToCallbackDelayed(remoteUserInfo, 24, intent, null, l);
    }

    void dispatchNext(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        this.postToCallback(remoteUserInfo, 14, null, null);
    }

    void dispatchPause(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        this.postToCallback(remoteUserInfo, 12, null, null);
    }

    void dispatchPlay(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        this.postToCallback(remoteUserInfo, 7, null, null);
    }

    void dispatchPlayFromMediaId(MediaSessionManager.RemoteUserInfo remoteUserInfo, String string2, Bundle bundle) {
        this.postToCallback(remoteUserInfo, 8, string2, bundle);
    }

    void dispatchPlayFromSearch(MediaSessionManager.RemoteUserInfo remoteUserInfo, String string2, Bundle bundle) {
        this.postToCallback(remoteUserInfo, 9, string2, bundle);
    }

    void dispatchPlayFromUri(MediaSessionManager.RemoteUserInfo remoteUserInfo, Uri uri, Bundle bundle) {
        this.postToCallback(remoteUserInfo, 10, uri, bundle);
    }

    void dispatchPrepare(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        this.postToCallback(remoteUserInfo, 3, null, null);
    }

    void dispatchPrepareFromMediaId(MediaSessionManager.RemoteUserInfo remoteUserInfo, String string2, Bundle bundle) {
        this.postToCallback(remoteUserInfo, 4, string2, bundle);
    }

    void dispatchPrepareFromSearch(MediaSessionManager.RemoteUserInfo remoteUserInfo, String string2, Bundle bundle) {
        this.postToCallback(remoteUserInfo, 5, string2, bundle);
    }

    void dispatchPrepareFromUri(MediaSessionManager.RemoteUserInfo remoteUserInfo, Uri uri, Bundle bundle) {
        this.postToCallback(remoteUserInfo, 6, uri, bundle);
    }

    void dispatchPrevious(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        this.postToCallback(remoteUserInfo, 15, null, null);
    }

    void dispatchRate(MediaSessionManager.RemoteUserInfo remoteUserInfo, Rating rating) {
        this.postToCallback(remoteUserInfo, 19, rating, null);
    }

    void dispatchRewind(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        this.postToCallback(remoteUserInfo, 17, null, null);
    }

    void dispatchSeekTo(MediaSessionManager.RemoteUserInfo remoteUserInfo, long l) {
        this.postToCallback(remoteUserInfo, 18, l, null);
    }

    void dispatchSetPlaybackSpeed(MediaSessionManager.RemoteUserInfo remoteUserInfo, float f) {
        this.postToCallback(remoteUserInfo, 20, Float.valueOf(f), null);
    }

    void dispatchSetVolumeTo(MediaSessionManager.RemoteUserInfo remoteUserInfo, int n) {
        this.postToCallback(remoteUserInfo, 23, n, null);
    }

    void dispatchSkipToItem(MediaSessionManager.RemoteUserInfo remoteUserInfo, long l) {
        this.postToCallback(remoteUserInfo, 11, l, null);
    }

    void dispatchStop(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        this.postToCallback(remoteUserInfo, 13, null, null);
    }

    @UnsupportedAppUsage
    public String getCallingPackage() {
        CallbackMessageHandler callbackMessageHandler = this.mCallback;
        if (callbackMessageHandler != null && callbackMessageHandler.mCurrentControllerInfo != null) {
            return this.mCallback.mCurrentControllerInfo.getPackageName();
        }
        return null;
    }

    public MediaController getController() {
        return this.mController;
    }

    public final MediaSessionManager.RemoteUserInfo getCurrentControllerInfo() {
        CallbackMessageHandler callbackMessageHandler = this.mCallback;
        if (callbackMessageHandler != null && callbackMessageHandler.mCurrentControllerInfo != null) {
            return this.mCallback.mCurrentControllerInfo;
        }
        throw new IllegalStateException("This should be called inside of MediaSession.Callback methods");
    }

    public Token getSessionToken() {
        return this.mSessionToken;
    }

    public boolean isActive() {
        return this.mActive;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyRemoteVolumeChanged(VolumeProvider volumeProvider) {
        Object object = this.mLock;
        synchronized (object) {
            if (volumeProvider != null && volumeProvider == this.mVolumeProvider) {
                // MONITOREXIT [3, 5] lbl4 : MonitorExitStatement: MONITOREXIT : var2_3
                try {
                    this.mBinder.setCurrentVolume(volumeProvider.getCurrentVolume());
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Error in notifyVolumeChanged", remoteException);
                }
                return;
            }
            Log.w(TAG, "Received update from stale volume provider");
            return;
        }
    }

    void postToCallback(MediaSessionManager.RemoteUserInfo remoteUserInfo, int n, Object object, Bundle bundle) {
        this.postToCallbackDelayed(remoteUserInfo, n, object, bundle, 0L);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void postToCallbackDelayed(MediaSessionManager.RemoteUserInfo remoteUserInfo, int n, Object object, Bundle bundle, long l) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mCallback != null) {
                this.mCallback.post(remoteUserInfo, n, object, bundle, l);
            }
            return;
        }
    }

    public void release() {
        try {
            this.mBinder.destroySession();
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Error releasing session: ", remoteException);
        }
    }

    public void sendSessionEvent(String string2, Bundle bundle) {
        if (!TextUtils.isEmpty(string2)) {
            try {
                this.mBinder.sendEvent(string2, bundle);
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Error sending event", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("event cannot be null or empty");
    }

    public void setActive(boolean bl) {
        if (this.mActive == bl) {
            return;
        }
        try {
            this.mBinder.setActive(bl);
            this.mActive = bl;
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Failure in setActive.", remoteException);
        }
    }

    public void setCallback(Callback callback) {
        this.setCallback(callback, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setCallback(Callback callback, Handler handler) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mCallback != null) {
                this.mCallback.mCallback.mSession = null;
                this.mCallback.removeCallbacksAndMessages(null);
            }
            if (callback == null) {
                this.mCallback = null;
                return;
            }
            Handler handler2 = handler;
            if (handler == null) {
                handler2 = new Handler();
            }
            callback.mSession = this;
            handler = new CallbackMessageHandler(handler2.getLooper(), callback);
            this.mCallback = handler;
            return;
        }
    }

    public void setExtras(Bundle bundle) {
        try {
            this.mBinder.setExtras(bundle);
        }
        catch (RemoteException remoteException) {
            Log.wtf("Dead object in setExtras.", remoteException);
        }
    }

    public void setFlags(int n) {
        try {
            this.mBinder.setFlags(n);
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Failure in setFlags.", remoteException);
        }
    }

    public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        try {
            this.mBinder.setMediaButtonReceiver(pendingIntent);
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Failure in setMediaButtonReceiver.", remoteException);
        }
    }

    public void setMetadata(MediaMetadata object) {
        long l = -1L;
        int n = 0;
        MediaDescription mediaDescription = null;
        long l2 = l;
        MediaMetadata mediaMetadata = object;
        if (object != null) {
            mediaMetadata = new MediaMetadata.Builder((MediaMetadata)object, this.mMaxBitmapSize).build();
            if (mediaMetadata.containsKey("android.media.metadata.DURATION")) {
                l = mediaMetadata.getLong("android.media.metadata.DURATION");
            }
            n = mediaMetadata.size();
            mediaDescription = mediaMetadata.getDescription();
            l2 = l;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("size=");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(", description=");
        ((StringBuilder)object).append(mediaDescription);
        object = ((StringBuilder)object).toString();
        try {
            this.mBinder.setMetadata(mediaMetadata, l2, (String)object);
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Dead object in setPlaybackState.", remoteException);
        }
    }

    public void setPlaybackState(PlaybackState playbackState) {
        this.mPlaybackState = playbackState;
        try {
            this.mBinder.setPlaybackState(playbackState);
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Dead object in setPlaybackState.", remoteException);
        }
    }

    public void setPlaybackToLocal(AudioAttributes audioAttributes) {
        if (audioAttributes != null) {
            try {
                this.mBinder.setPlaybackToLocal(audioAttributes);
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Failure in setPlaybackToLocal.", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("Attributes cannot be null for local playback.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPlaybackToRemote(VolumeProvider volumeProvider) {
        if (volumeProvider == null) throw new IllegalArgumentException("volumeProvider may not be null!");
        Object object = this.mLock;
        synchronized (object) {
            this.mVolumeProvider = volumeProvider;
        }
        volumeProvider.setCallback(new VolumeProvider.Callback(){

            @Override
            public void onVolumeChanged(VolumeProvider volumeProvider) {
                MediaSession.this.notifyRemoteVolumeChanged(volumeProvider);
            }
        });
        try {
            this.mBinder.setPlaybackToRemote(volumeProvider.getVolumeControl(), volumeProvider.getMaxVolume());
            this.mBinder.setCurrentVolume(volumeProvider.getCurrentVolume());
            return;
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Failure in setPlaybackToRemote.", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setQueue(List<QueueItem> parceledListSlice) {
        try {
            ISession iSession = this.mBinder;
            parceledListSlice = parceledListSlice == null ? null : new ParceledListSlice(parceledListSlice);
            iSession.setQueue(parceledListSlice);
            return;
        }
        catch (RemoteException remoteException) {
            Log.wtf("Dead object in setQueue.", remoteException);
        }
    }

    public void setQueueTitle(CharSequence charSequence) {
        try {
            this.mBinder.setQueueTitle(charSequence);
        }
        catch (RemoteException remoteException) {
            Log.wtf("Dead object in setQueueTitle.", remoteException);
        }
    }

    public void setRatingType(int n) {
        try {
            this.mBinder.setRatingType(n);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error in setRatingType.", remoteException);
        }
    }

    public void setSessionActivity(PendingIntent pendingIntent) {
        try {
            this.mBinder.setLaunchPendingIntent(pendingIntent);
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Failure in setLaunchPendingIntent.", remoteException);
        }
    }

    public static abstract class Callback {
        private CallbackMessageHandler mHandler;
        private boolean mMediaPlayPauseKeyPending;
        private MediaSession mSession;

        private void handleMediaPlayPauseKeySingleTapIfPending() {
            if (!this.mMediaPlayPauseKeyPending) {
                return;
            }
            boolean bl = false;
            this.mMediaPlayPauseKeyPending = false;
            this.mHandler.removeMessages(24);
            PlaybackState playbackState = this.mSession.mPlaybackState;
            long l = playbackState == null ? 0L : playbackState.getActions();
            boolean bl2 = playbackState != null && playbackState.getState() == 3;
            boolean bl3 = (516L & l) != 0L;
            if ((514L & l) != 0L) {
                bl = true;
            }
            if (bl2 && bl) {
                this.onPause();
            } else if (!bl2 && bl3) {
                this.onPlay();
            }
        }

        public void onCommand(String string2, Bundle bundle, ResultReceiver resultReceiver) {
        }

        public void onCustomAction(String string2, Bundle bundle) {
        }

        public void onFastForward() {
        }

        public boolean onMediaButtonEvent(Intent intent) {
            block19 : {
                KeyEvent keyEvent;
                Object object;
                long l;
                block20 : {
                    block21 : {
                        block22 : {
                            if (this.mSession == null || this.mHandler == null || !"android.intent.action.MEDIA_BUTTON".equals(intent.getAction()) || (keyEvent = (KeyEvent)intent.getParcelableExtra("android.intent.extra.KEY_EVENT")) == null || keyEvent.getAction() != 0) break block19;
                            object = this.mSession.mPlaybackState;
                            l = object == null ? 0L : ((PlaybackState)object).getActions();
                            int n = keyEvent.getKeyCode();
                            if (n == 79 || n == 85) break block20;
                            this.handleMediaPlayPauseKeySingleTapIfPending();
                            n = keyEvent.getKeyCode();
                            if (n == 126) break block21;
                            if (n == 127) break block22;
                            switch (n) {
                                default: {
                                    break block19;
                                }
                                case 90: {
                                    if ((64L & l) != 0L) {
                                        this.onFastForward();
                                        return true;
                                    }
                                    break block19;
                                }
                                case 89: {
                                    if ((8L & l) != 0L) {
                                        this.onRewind();
                                        return true;
                                    }
                                    break block19;
                                }
                                case 88: {
                                    if ((16L & l) != 0L) {
                                        this.onSkipToPrevious();
                                        return true;
                                    }
                                    break block19;
                                }
                                case 87: {
                                    if ((l & 32L) != 0L) {
                                        this.onSkipToNext();
                                        return true;
                                    }
                                    break block19;
                                }
                                case 86: {
                                    if ((1L & l) != 0L) {
                                        this.onStop();
                                        return true;
                                    }
                                    break block19;
                                }
                            }
                        }
                        if ((2L & l) != 0L) {
                            this.onPause();
                            return true;
                        }
                        break block19;
                    }
                    if ((4L & l) != 0L) {
                        this.onPlay();
                        return true;
                    }
                    break block19;
                }
                if (keyEvent.getRepeatCount() > 0) {
                    this.handleMediaPlayPauseKeySingleTapIfPending();
                } else if (this.mMediaPlayPauseKeyPending) {
                    this.mHandler.removeMessages(24);
                    this.mMediaPlayPauseKeyPending = false;
                    if ((l & 32L) != 0L) {
                        this.onSkipToNext();
                    }
                } else {
                    this.mMediaPlayPauseKeyPending = true;
                    object = this.mSession;
                    ((MediaSession)object).dispatchMediaButtonDelayed(((MediaSession)object).getCurrentControllerInfo(), intent, ViewConfiguration.getDoubleTapTimeout());
                }
                return true;
            }
            return false;
        }

        public void onPause() {
        }

        public void onPlay() {
        }

        public void onPlayFromMediaId(String string2, Bundle bundle) {
        }

        public void onPlayFromSearch(String string2, Bundle bundle) {
        }

        public void onPlayFromUri(Uri uri, Bundle bundle) {
        }

        public void onPrepare() {
        }

        public void onPrepareFromMediaId(String string2, Bundle bundle) {
        }

        public void onPrepareFromSearch(String string2, Bundle bundle) {
        }

        public void onPrepareFromUri(Uri uri, Bundle bundle) {
        }

        public void onRewind() {
        }

        public void onSeekTo(long l) {
        }

        public void onSetPlaybackSpeed(float f) {
        }

        public void onSetRating(Rating rating) {
        }

        public void onSkipToNext() {
        }

        public void onSkipToPrevious() {
        }

        public void onSkipToQueueItem(long l) {
        }

        public void onStop() {
        }
    }

    private class CallbackMessageHandler
    extends Handler {
        private static final int MSG_ADJUST_VOLUME = 22;
        private static final int MSG_COMMAND = 1;
        private static final int MSG_CUSTOM_ACTION = 21;
        private static final int MSG_FAST_FORWARD = 16;
        private static final int MSG_MEDIA_BUTTON = 2;
        private static final int MSG_NEXT = 14;
        private static final int MSG_PAUSE = 12;
        private static final int MSG_PLAY = 7;
        private static final int MSG_PLAY_MEDIA_ID = 8;
        private static final int MSG_PLAY_PAUSE_KEY_DOUBLE_TAP_TIMEOUT = 24;
        private static final int MSG_PLAY_SEARCH = 9;
        private static final int MSG_PLAY_URI = 10;
        private static final int MSG_PREPARE = 3;
        private static final int MSG_PREPARE_MEDIA_ID = 4;
        private static final int MSG_PREPARE_SEARCH = 5;
        private static final int MSG_PREPARE_URI = 6;
        private static final int MSG_PREVIOUS = 15;
        private static final int MSG_RATE = 19;
        private static final int MSG_REWIND = 17;
        private static final int MSG_SEEK_TO = 18;
        private static final int MSG_SET_PLAYBACK_SPEED = 20;
        private static final int MSG_SET_VOLUME = 23;
        private static final int MSG_SKIP_TO_ITEM = 11;
        private static final int MSG_STOP = 13;
        private Callback mCallback;
        private MediaSessionManager.RemoteUserInfo mCurrentControllerInfo;

        CallbackMessageHandler(Looper looper, Callback callback) {
            super(looper);
            this.mCallback = callback;
            this.mCallback.mHandler = this;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message object) {
            this.mCurrentControllerInfo = (MediaSessionManager.RemoteUserInfo)((Pair)object.obj).first;
            Object s = ((Pair)object.obj).second;
            switch (((Message)object).what) {
                default: {
                    break;
                }
                case 24: {
                    this.mCallback.handleMediaPlayPauseKeySingleTapIfPending();
                    break;
                }
                case 23: {
                    VolumeProvider volumeProvider;
                    object = MediaSession.this.mLock;
                    synchronized (object) {
                        volumeProvider = MediaSession.this.mVolumeProvider;
                        if (volumeProvider == null) break;
                    }
                    volumeProvider.onSetVolumeTo((Integer)s);
                    break;
                }
                case 22: {
                    VolumeProvider volumeProvider;
                    object = MediaSession.this.mLock;
                    synchronized (object) {
                        volumeProvider = MediaSession.this.mVolumeProvider;
                        if (volumeProvider == null) break;
                    }
                    volumeProvider.onAdjustVolume((Integer)s);
                    break;
                }
                case 21: {
                    this.mCallback.onCustomAction((String)s, ((Message)object).getData());
                    break;
                }
                case 20: {
                    this.mCallback.onSetPlaybackSpeed(((Float)s).floatValue());
                    break;
                }
                case 19: {
                    this.mCallback.onSetRating((Rating)s);
                    break;
                }
                case 18: {
                    this.mCallback.onSeekTo((Long)s);
                    break;
                }
                case 17: {
                    this.mCallback.onRewind();
                    break;
                }
                case 16: {
                    this.mCallback.onFastForward();
                    break;
                }
                case 15: {
                    this.mCallback.onSkipToPrevious();
                    break;
                }
                case 14: {
                    this.mCallback.onSkipToNext();
                    break;
                }
                case 13: {
                    this.mCallback.onStop();
                    break;
                }
                case 12: {
                    this.mCallback.onPause();
                    break;
                }
                case 11: {
                    this.mCallback.onSkipToQueueItem((Long)s);
                    break;
                }
                case 10: {
                    this.mCallback.onPlayFromUri((Uri)s, ((Message)object).getData());
                    break;
                }
                case 9: {
                    this.mCallback.onPlayFromSearch((String)s, ((Message)object).getData());
                    break;
                }
                case 8: {
                    this.mCallback.onPlayFromMediaId((String)s, ((Message)object).getData());
                    break;
                }
                case 7: {
                    this.mCallback.onPlay();
                    break;
                }
                case 6: {
                    this.mCallback.onPrepareFromUri((Uri)s, ((Message)object).getData());
                    break;
                }
                case 5: {
                    this.mCallback.onPrepareFromSearch((String)s, ((Message)object).getData());
                    break;
                }
                case 4: {
                    this.mCallback.onPrepareFromMediaId((String)s, ((Message)object).getData());
                    break;
                }
                case 3: {
                    this.mCallback.onPrepare();
                    break;
                }
                case 2: {
                    this.mCallback.onMediaButtonEvent((Intent)s);
                    break;
                }
                case 1: {
                    object = (Command)s;
                    this.mCallback.onCommand(((Command)object).command, ((Command)object).extras, ((Command)object).stub);
                }
            }
            this.mCurrentControllerInfo = null;
        }

        void post(MediaSessionManager.RemoteUserInfo object, int n, Object object2, Bundle bundle, long l) {
            object = this.obtainMessage(n, Pair.create(object, object2));
            ((Message)object).setAsynchronous(true);
            ((Message)object).setData(bundle);
            if (l > 0L) {
                this.sendMessageDelayed((Message)object, l);
            } else {
                this.sendMessage((Message)object);
            }
        }
    }

    public static class CallbackStub
    extends ISessionCallback.Stub {
        private WeakReference<MediaSession> mMediaSession;

        public CallbackStub(MediaSession mediaSession) {
            this.mMediaSession = new WeakReference<MediaSession>(mediaSession);
        }

        private static MediaSessionManager.RemoteUserInfo createRemoteUserInfo(String string2, int n, int n2) {
            return new MediaSessionManager.RemoteUserInfo(string2, n, n2);
        }

        @Override
        public void onAdjustVolume(String string2, int n, int n2, ISessionControllerCallback object, int n3) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchAdjustVolume(CallbackStub.createRemoteUserInfo(string2, n, n2), n3);
            }
        }

        @Override
        public void onCommand(String string2, int n, int n2, ISessionControllerCallback object, String string3, Bundle bundle, ResultReceiver resultReceiver) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchCommand(CallbackStub.createRemoteUserInfo(string2, n, n2), string3, bundle, resultReceiver);
            }
        }

        @Override
        public void onCustomAction(String string2, int n, int n2, ISessionControllerCallback object, String string3, Bundle bundle) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchCustomAction(CallbackStub.createRemoteUserInfo(string2, n, n2), string3, bundle);
            }
        }

        @Override
        public void onFastForward(String string2, int n, int n2, ISessionControllerCallback object) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchFastForward(CallbackStub.createRemoteUserInfo(string2, n, n2));
            }
        }

        @Override
        public void onMediaButton(String string2, int n, int n2, Intent intent, int n3, ResultReceiver resultReceiver) {
            MediaSession mediaSession = (MediaSession)this.mMediaSession.get();
            if (mediaSession != null) {
                try {
                    mediaSession.dispatchMediaButton(CallbackStub.createRemoteUserInfo(string2, n, n2), intent);
                }
                finally {
                    if (resultReceiver != null) {
                        resultReceiver.send(n3, null);
                    }
                }
            }
        }

        @Override
        public void onMediaButtonFromController(String string2, int n, int n2, ISessionControllerCallback object, Intent intent) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchMediaButton(CallbackStub.createRemoteUserInfo(string2, n, n2), intent);
            }
        }

        @Override
        public void onNext(String string2, int n, int n2, ISessionControllerCallback object) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchNext(CallbackStub.createRemoteUserInfo(string2, n, n2));
            }
        }

        @Override
        public void onPause(String string2, int n, int n2, ISessionControllerCallback object) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchPause(CallbackStub.createRemoteUserInfo(string2, n, n2));
            }
        }

        @Override
        public void onPlay(String string2, int n, int n2, ISessionControllerCallback object) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchPlay(CallbackStub.createRemoteUserInfo(string2, n, n2));
            }
        }

        @Override
        public void onPlayFromMediaId(String string2, int n, int n2, ISessionControllerCallback object, String string3, Bundle bundle) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchPlayFromMediaId(CallbackStub.createRemoteUserInfo(string2, n, n2), string3, bundle);
            }
        }

        @Override
        public void onPlayFromSearch(String string2, int n, int n2, ISessionControllerCallback object, String string3, Bundle bundle) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchPlayFromSearch(CallbackStub.createRemoteUserInfo(string2, n, n2), string3, bundle);
            }
        }

        @Override
        public void onPlayFromUri(String string2, int n, int n2, ISessionControllerCallback object, Uri uri, Bundle bundle) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchPlayFromUri(CallbackStub.createRemoteUserInfo(string2, n, n2), uri, bundle);
            }
        }

        @Override
        public void onPrepare(String string2, int n, int n2, ISessionControllerCallback object) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchPrepare(CallbackStub.createRemoteUserInfo(string2, n, n2));
            }
        }

        @Override
        public void onPrepareFromMediaId(String string2, int n, int n2, ISessionControllerCallback object, String string3, Bundle bundle) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchPrepareFromMediaId(CallbackStub.createRemoteUserInfo(string2, n, n2), string3, bundle);
            }
        }

        @Override
        public void onPrepareFromSearch(String string2, int n, int n2, ISessionControllerCallback object, String string3, Bundle bundle) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchPrepareFromSearch(CallbackStub.createRemoteUserInfo(string2, n, n2), string3, bundle);
            }
        }

        @Override
        public void onPrepareFromUri(String string2, int n, int n2, ISessionControllerCallback object, Uri uri, Bundle bundle) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchPrepareFromUri(CallbackStub.createRemoteUserInfo(string2, n, n2), uri, bundle);
            }
        }

        @Override
        public void onPrevious(String string2, int n, int n2, ISessionControllerCallback object) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchPrevious(CallbackStub.createRemoteUserInfo(string2, n, n2));
            }
        }

        @Override
        public void onRate(String string2, int n, int n2, ISessionControllerCallback object, Rating rating) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchRate(CallbackStub.createRemoteUserInfo(string2, n, n2), rating);
            }
        }

        @Override
        public void onRewind(String string2, int n, int n2, ISessionControllerCallback object) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchRewind(CallbackStub.createRemoteUserInfo(string2, n, n2));
            }
        }

        @Override
        public void onSeekTo(String string2, int n, int n2, ISessionControllerCallback object, long l) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchSeekTo(CallbackStub.createRemoteUserInfo(string2, n, n2), l);
            }
        }

        @Override
        public void onSetPlaybackSpeed(String string2, int n, int n2, ISessionControllerCallback object, float f) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchSetPlaybackSpeed(CallbackStub.createRemoteUserInfo(string2, n, n2), f);
            }
        }

        @Override
        public void onSetVolumeTo(String string2, int n, int n2, ISessionControllerCallback object, int n3) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchSetVolumeTo(CallbackStub.createRemoteUserInfo(string2, n, n2), n3);
            }
        }

        @Override
        public void onSkipToTrack(String string2, int n, int n2, ISessionControllerCallback object, long l) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchSkipToItem(CallbackStub.createRemoteUserInfo(string2, n, n2), l);
            }
        }

        @Override
        public void onStop(String string2, int n, int n2, ISessionControllerCallback object) {
            object = (MediaSession)this.mMediaSession.get();
            if (object != null) {
                ((MediaSession)object).dispatchStop(CallbackStub.createRemoteUserInfo(string2, n, n2));
            }
        }
    }

    private static final class Command {
        public final String command;
        public final Bundle extras;
        public final ResultReceiver stub;

        Command(String string2, Bundle bundle, ResultReceiver resultReceiver) {
            this.command = string2;
            this.extras = bundle;
            this.stub = resultReceiver;
        }
    }

    public static final class QueueItem
    implements Parcelable {
        public static final Parcelable.Creator<QueueItem> CREATOR = new Parcelable.Creator<QueueItem>(){

            @Override
            public QueueItem createFromParcel(Parcel parcel) {
                return new QueueItem(parcel);
            }

            public QueueItem[] newArray(int n) {
                return new QueueItem[n];
            }
        };
        public static final int UNKNOWN_ID = -1;
        private final MediaDescription mDescription;
        @UnsupportedAppUsage
        private final long mId;

        public QueueItem(MediaDescription mediaDescription, long l) {
            if (mediaDescription != null) {
                if (l != -1L) {
                    this.mDescription = mediaDescription;
                    this.mId = l;
                    return;
                }
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
            throw new IllegalArgumentException("Description cannot be null.");
        }

        private QueueItem(Parcel parcel) {
            this.mDescription = MediaDescription.CREATOR.createFromParcel(parcel);
            this.mId = parcel.readLong();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (object == null) {
                return false;
            }
            if (!(object instanceof QueueItem)) {
                return false;
            }
            object = (QueueItem)object;
            if (this.mId != ((QueueItem)object).mId) {
                return false;
            }
            return Objects.equals(this.mDescription, ((QueueItem)object).mDescription);
        }

        public MediaDescription getDescription() {
            return this.mDescription;
        }

        public long getQueueId() {
            return this.mId;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MediaSession.QueueItem {Description=");
            stringBuilder.append(this.mDescription);
            stringBuilder.append(", Id=");
            stringBuilder.append(this.mId);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            this.mDescription.writeToParcel(parcel, n);
            parcel.writeLong(this.mId);
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SessionFlags {
    }

    public static final class Token
    implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>(){

            @Override
            public Token createFromParcel(Parcel parcel) {
                return new Token(parcel);
            }

            public Token[] newArray(int n) {
                return new Token[n];
            }
        };
        private final ISessionController mBinder;
        private final int mUid;

        public Token(ISessionController iSessionController) {
            this.mUid = Process.myUid();
            this.mBinder = iSessionController;
        }

        Token(Parcel parcel) {
            this.mUid = parcel.readInt();
            this.mBinder = ISessionController.Stub.asInterface(parcel.readStrongBinder());
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
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (Token)object;
            if (this.mUid != ((Token)object).mUid) {
                return false;
            }
            ISessionController iSessionController = this.mBinder;
            if (iSessionController != null && ((Token)object).mBinder != null) {
                return Objects.equals(iSessionController.asBinder(), ((Token)object).mBinder.asBinder());
            }
            if (this.mBinder != ((Token)object).mBinder) {
                bl = false;
            }
            return bl;
        }

        public ISessionController getBinder() {
            return this.mBinder;
        }

        public int getUid() {
            return this.mUid;
        }

        public int hashCode() {
            int n = this.mUid;
            ISessionController iSessionController = this.mBinder;
            int n2 = iSessionController == null ? 0 : iSessionController.asBinder().hashCode();
            return n * 31 + n2;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mUid);
            parcel.writeStrongBinder(this.mBinder.asBinder());
        }

    }

}

