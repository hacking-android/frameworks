/*
 * Decompiled with CFR 0.145.
 */
package android.media.session;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.media.AudioAttributes;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.ISessionController;
import android.media.session.ISessionControllerCallback;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class MediaController {
    private static final int MSG_DESTROYED = 8;
    private static final int MSG_EVENT = 1;
    private static final int MSG_UPDATE_EXTRAS = 7;
    private static final int MSG_UPDATE_METADATA = 3;
    private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
    private static final int MSG_UPDATE_QUEUE = 5;
    private static final int MSG_UPDATE_QUEUE_TITLE = 6;
    private static final int MSG_UPDATE_VOLUME = 4;
    private static final String TAG = "MediaController";
    private final ArrayList<MessageHandler> mCallbacks = new ArrayList();
    private boolean mCbRegistered = false;
    private final CallbackStub mCbStub = new CallbackStub(this);
    private final Context mContext;
    private final Object mLock = new Object();
    private String mPackageName;
    private final ISessionController mSessionBinder;
    private Bundle mSessionInfo;
    private String mTag;
    private final MediaSession.Token mToken;
    private final TransportControls mTransportControls;

    public MediaController(Context context, MediaSession.Token token) {
        if (context != null) {
            if (token != null) {
                if (token.getBinder() != null) {
                    this.mSessionBinder = token.getBinder();
                    this.mTransportControls = new TransportControls();
                    this.mToken = token;
                    this.mContext = context;
                    return;
                }
                throw new IllegalArgumentException("token.getBinder() shouldn't be null");
            }
            throw new IllegalArgumentException("token shouldn't be null");
        }
        throw new IllegalArgumentException("context shouldn't be null");
    }

    private void addCallbackLocked(Callback object, Handler handler) {
        if (this.getHandlerForCallbackLocked((Callback)object) != null) {
            Log.w(TAG, "Callback is already added, ignoring");
            return;
        }
        object = new MessageHandler(handler.getLooper(), (Callback)object);
        this.mCallbacks.add((MessageHandler)object);
        ((MessageHandler)object).mRegistered = true;
        if (!this.mCbRegistered) {
            try {
                this.mSessionBinder.registerCallback(this.mContext.getPackageName(), this.mCbStub);
                this.mCbRegistered = true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Dead object in registerCallback", remoteException);
            }
        }
    }

    private MessageHandler getHandlerForCallbackLocked(Callback callback) {
        if (callback != null) {
            for (int i = this.mCallbacks.size() - 1; i >= 0; --i) {
                MessageHandler messageHandler = this.mCallbacks.get(i);
                if (callback != messageHandler.mCallback) continue;
                return messageHandler;
            }
            return null;
        }
        throw new IllegalArgumentException("Callback cannot be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void postMessage(int n, Object object, Bundle bundle) {
        Object object2 = this.mLock;
        synchronized (object2) {
            int n2 = this.mCallbacks.size() - 1;
            while (n2 >= 0) {
                this.mCallbacks.get(n2).post(n, object, bundle);
                --n2;
            }
            return;
        }
    }

    private boolean removeCallbackLocked(Callback callback) {
        boolean bl = false;
        for (int i = this.mCallbacks.size() - 1; i >= 0; --i) {
            MessageHandler messageHandler = this.mCallbacks.get(i);
            if (callback != messageHandler.mCallback) continue;
            this.mCallbacks.remove(i);
            bl = true;
            messageHandler.mRegistered = false;
        }
        if (this.mCbRegistered && this.mCallbacks.size() == 0) {
            try {
                this.mSessionBinder.unregisterCallback(this.mCbStub);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Dead object in removeCallbackLocked");
            }
            this.mCbRegistered = false;
        }
        return bl;
    }

    public void adjustVolume(int n, int n2) {
        try {
            this.mSessionBinder.adjustVolume(this.mContext.getPackageName(), this.mContext.getOpPackageName(), this.mCbStub, n, n2);
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Error calling adjustVolumeBy.", remoteException);
        }
    }

    @UnsupportedAppUsage
    public boolean controlsSameSession(MediaController mediaController) {
        boolean bl = false;
        if (mediaController == null) {
            return false;
        }
        if (this.mSessionBinder.asBinder() == mediaController.getSessionBinder().asBinder()) {
            bl = true;
        }
        return bl;
    }

    public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        if (keyEvent != null) {
            if (!KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
                return false;
            }
            try {
                boolean bl = this.mSessionBinder.sendMediaButton(this.mContext.getPackageName(), this.mCbStub, keyEvent);
                return bl;
            }
            catch (RemoteException remoteException) {
                return false;
            }
        }
        throw new IllegalArgumentException("KeyEvent may not be null");
    }

    public Bundle getExtras() {
        try {
            Bundle bundle = this.mSessionBinder.getExtras();
            return bundle;
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Error calling getExtras", remoteException);
            return null;
        }
    }

    public long getFlags() {
        try {
            long l = this.mSessionBinder.getFlags();
            return l;
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Error calling getFlags.", remoteException);
            return 0L;
        }
    }

    public MediaMetadata getMetadata() {
        try {
            MediaMetadata mediaMetadata = this.mSessionBinder.getMetadata();
            return mediaMetadata;
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Error calling getMetadata.", remoteException);
            return null;
        }
    }

    public String getPackageName() {
        if (this.mPackageName == null) {
            try {
                this.mPackageName = this.mSessionBinder.getPackageName();
            }
            catch (RemoteException remoteException) {
                Log.d(TAG, "Dead object in getPackageName.", remoteException);
            }
        }
        return this.mPackageName;
    }

    public PlaybackInfo getPlaybackInfo() {
        try {
            PlaybackInfo playbackInfo = this.mSessionBinder.getVolumeAttributes();
            return playbackInfo;
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Error calling getAudioInfo.", remoteException);
            return null;
        }
    }

    public PlaybackState getPlaybackState() {
        try {
            PlaybackState playbackState = this.mSessionBinder.getPlaybackState();
            return playbackState;
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Error calling getPlaybackState.", remoteException);
            return null;
        }
    }

    public List<MediaSession.QueueItem> getQueue() {
        List list;
        block3 : {
            ParceledListSlice parceledListSlice;
            list = null;
            try {
                parceledListSlice = this.mSessionBinder.getQueue();
                if (parceledListSlice == null) break block3;
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Error calling getQueue.", remoteException);
                return null;
            }
            list = parceledListSlice.getList();
        }
        return list;
    }

    public CharSequence getQueueTitle() {
        try {
            CharSequence charSequence = this.mSessionBinder.getQueueTitle();
            return charSequence;
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Error calling getQueueTitle", remoteException);
            return null;
        }
    }

    public int getRatingType() {
        try {
            int n = this.mSessionBinder.getRatingType();
            return n;
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Error calling getRatingType.", remoteException);
            return 0;
        }
    }

    public PendingIntent getSessionActivity() {
        try {
            PendingIntent pendingIntent = this.mSessionBinder.getLaunchPendingIntent();
            return pendingIntent;
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Error calling getPendingIntent.", remoteException);
            return null;
        }
    }

    ISessionController getSessionBinder() {
        return this.mSessionBinder;
    }

    public Bundle getSessionInfo() {
        Bundle bundle = this.mSessionInfo;
        if (bundle != null) {
            return new Bundle(bundle);
        }
        try {
            this.mSessionInfo = this.mSessionBinder.getSessionInfo();
        }
        catch (RemoteException remoteException) {
            Log.d(TAG, "Dead object in getSessionInfo.", remoteException);
        }
        bundle = this.mSessionInfo;
        if (bundle == null) {
            Log.w(TAG, "sessionInfo shouldn't be null.");
            this.mSessionInfo = Bundle.EMPTY;
        } else if (MediaSession.hasCustomParcelable(bundle)) {
            Log.w(TAG, "sessionInfo contains custom parcelable. Ignoring.");
            this.mSessionInfo = Bundle.EMPTY;
        }
        return new Bundle(this.mSessionInfo);
    }

    public MediaSession.Token getSessionToken() {
        return this.mToken;
    }

    public String getTag() {
        if (this.mTag == null) {
            try {
                this.mTag = this.mSessionBinder.getTag();
            }
            catch (RemoteException remoteException) {
                Log.d(TAG, "Dead object in getTag.", remoteException);
            }
        }
        return this.mTag;
    }

    public TransportControls getTransportControls() {
        return this.mTransportControls;
    }

    public void registerCallback(Callback callback) {
        this.registerCallback(callback, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerCallback(Callback callback, Handler handler) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        if (handler == null) {
            handler = new Handler();
        }
        Object object = this.mLock;
        synchronized (object) {
            this.addCallbackLocked(callback, handler);
            return;
        }
    }

    public void sendCommand(String string2, Bundle bundle, ResultReceiver resultReceiver) {
        if (!TextUtils.isEmpty(string2)) {
            try {
                this.mSessionBinder.sendCommand(this.mContext.getPackageName(), this.mCbStub, string2, bundle, resultReceiver);
            }
            catch (RemoteException remoteException) {
                Log.d(TAG, "Dead object in sendCommand.", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("command cannot be null or empty");
    }

    public void setVolumeTo(int n, int n2) {
        try {
            this.mSessionBinder.setVolumeTo(this.mContext.getPackageName(), this.mContext.getOpPackageName(), this.mCbStub, n, n2);
        }
        catch (RemoteException remoteException) {
            Log.wtf(TAG, "Error calling setVolumeTo.", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterCallback(Callback callback) {
        if (callback != null) {
            Object object = this.mLock;
            synchronized (object) {
                this.removeCallbackLocked(callback);
                return;
            }
        }
        throw new IllegalArgumentException("callback must not be null");
    }

    public static abstract class Callback {
        public void onAudioInfoChanged(PlaybackInfo playbackInfo) {
        }

        public void onExtrasChanged(Bundle bundle) {
        }

        public void onMetadataChanged(MediaMetadata mediaMetadata) {
        }

        public void onPlaybackStateChanged(PlaybackState playbackState) {
        }

        public void onQueueChanged(List<MediaSession.QueueItem> list) {
        }

        public void onQueueTitleChanged(CharSequence charSequence) {
        }

        public void onSessionDestroyed() {
        }

        public void onSessionEvent(String string2, Bundle bundle) {
        }
    }

    private static final class CallbackStub
    extends ISessionControllerCallback.Stub {
        private final WeakReference<MediaController> mController;

        CallbackStub(MediaController mediaController) {
            this.mController = new WeakReference<MediaController>(mediaController);
        }

        @Override
        public void onEvent(String string2, Bundle bundle) {
            MediaController mediaController = (MediaController)this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(1, string2, bundle);
            }
        }

        @Override
        public void onExtrasChanged(Bundle bundle) {
            MediaController mediaController = (MediaController)this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(7, bundle, null);
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadata mediaMetadata) {
            MediaController mediaController = (MediaController)this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(3, mediaMetadata, null);
            }
        }

        @Override
        public void onPlaybackStateChanged(PlaybackState playbackState) {
            MediaController mediaController = (MediaController)this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(2, playbackState, null);
            }
        }

        @Override
        public void onQueueChanged(ParceledListSlice parceledListSlice) {
            MediaController mediaController = (MediaController)this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(5, parceledListSlice, null);
            }
        }

        @Override
        public void onQueueTitleChanged(CharSequence charSequence) {
            MediaController mediaController = (MediaController)this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(6, charSequence, null);
            }
        }

        @Override
        public void onSessionDestroyed() {
            MediaController mediaController = (MediaController)this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(8, null, null);
            }
        }

        @Override
        public void onVolumeInfoChanged(PlaybackInfo playbackInfo) {
            MediaController mediaController = (MediaController)this.mController.get();
            if (mediaController != null) {
                mediaController.postMessage(4, playbackInfo, null);
            }
        }
    }

    private static final class MessageHandler
    extends Handler {
        private final Callback mCallback;
        private boolean mRegistered = false;

        MessageHandler(Looper looper, Callback callback) {
            super(looper);
            this.mCallback = callback;
        }

        @Override
        public void handleMessage(Message object) {
            if (!this.mRegistered) {
                return;
            }
            switch (((Message)object).what) {
                default: {
                    break;
                }
                case 8: {
                    this.mCallback.onSessionDestroyed();
                    break;
                }
                case 7: {
                    this.mCallback.onExtrasChanged((Bundle)((Message)object).obj);
                    break;
                }
                case 6: {
                    this.mCallback.onQueueTitleChanged((CharSequence)((Message)object).obj);
                    break;
                }
                case 5: {
                    Callback callback = this.mCallback;
                    object = ((Message)object).obj == null ? null : ((ParceledListSlice)((Message)object).obj).getList();
                    callback.onQueueChanged((List<MediaSession.QueueItem>)object);
                    break;
                }
                case 4: {
                    this.mCallback.onAudioInfoChanged((PlaybackInfo)((Message)object).obj);
                    break;
                }
                case 3: {
                    this.mCallback.onMetadataChanged((MediaMetadata)((Message)object).obj);
                    break;
                }
                case 2: {
                    this.mCallback.onPlaybackStateChanged((PlaybackState)((Message)object).obj);
                    break;
                }
                case 1: {
                    this.mCallback.onSessionEvent((String)((Message)object).obj, ((Message)object).getData());
                }
            }
        }

        public void post(int n, Object object, Bundle bundle) {
            object = this.obtainMessage(n, object);
            ((Message)object).setAsynchronous(true);
            ((Message)object).setData(bundle);
            ((Message)object).sendToTarget();
        }
    }

    public static final class PlaybackInfo
    implements Parcelable {
        public static final Parcelable.Creator<PlaybackInfo> CREATOR = new Parcelable.Creator<PlaybackInfo>(){

            @Override
            public PlaybackInfo createFromParcel(Parcel parcel) {
                return new PlaybackInfo(parcel);
            }

            public PlaybackInfo[] newArray(int n) {
                return new PlaybackInfo[n];
            }
        };
        public static final int PLAYBACK_TYPE_LOCAL = 1;
        public static final int PLAYBACK_TYPE_REMOTE = 2;
        private final AudioAttributes mAudioAttrs;
        private final int mCurrentVolume;
        private final int mMaxVolume;
        private final int mVolumeControl;
        private final int mVolumeType;

        public PlaybackInfo(int n, int n2, int n3, int n4, AudioAttributes audioAttributes) {
            this.mVolumeType = n;
            this.mVolumeControl = n2;
            this.mMaxVolume = n3;
            this.mCurrentVolume = n4;
            this.mAudioAttrs = audioAttributes;
        }

        PlaybackInfo(Parcel parcel) {
            this.mVolumeType = parcel.readInt();
            this.mVolumeControl = parcel.readInt();
            this.mMaxVolume = parcel.readInt();
            this.mCurrentVolume = parcel.readInt();
            this.mAudioAttrs = (AudioAttributes)parcel.readParcelable(null);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public AudioAttributes getAudioAttributes() {
            return this.mAudioAttrs;
        }

        public int getCurrentVolume() {
            return this.mCurrentVolume;
        }

        public int getMaxVolume() {
            return this.mMaxVolume;
        }

        public int getPlaybackType() {
            return this.mVolumeType;
        }

        public int getVolumeControl() {
            return this.mVolumeControl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("volumeType=");
            stringBuilder.append(this.mVolumeType);
            stringBuilder.append(", volumeControl=");
            stringBuilder.append(this.mVolumeControl);
            stringBuilder.append(", maxVolume=");
            stringBuilder.append(this.mMaxVolume);
            stringBuilder.append(", currentVolume=");
            stringBuilder.append(this.mCurrentVolume);
            stringBuilder.append(", audioAttrs=");
            stringBuilder.append(this.mAudioAttrs);
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mVolumeType);
            parcel.writeInt(this.mVolumeControl);
            parcel.writeInt(this.mMaxVolume);
            parcel.writeInt(this.mCurrentVolume);
            parcel.writeParcelable(this.mAudioAttrs, n);
        }

    }

    public final class TransportControls {
        private static final String TAG = "TransportController";

        private TransportControls() {
        }

        public void fastForward() {
            try {
                MediaController.this.mSessionBinder.fastForward(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub);
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Error calling fastForward.", remoteException);
            }
        }

        public void pause() {
            try {
                MediaController.this.mSessionBinder.pause(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub);
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Error calling pause.", remoteException);
            }
        }

        public void play() {
            try {
                MediaController.this.mSessionBinder.play(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub);
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Error calling play.", remoteException);
            }
        }

        public void playFromMediaId(String string2, Bundle object) {
            if (!TextUtils.isEmpty(string2)) {
                try {
                    MediaController.this.mSessionBinder.playFromMediaId(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub, string2, (Bundle)object);
                }
                catch (RemoteException remoteException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Error calling play(");
                    ((StringBuilder)object).append(string2);
                    ((StringBuilder)object).append(").");
                    Log.wtf(TAG, ((StringBuilder)object).toString(), remoteException);
                }
                return;
            }
            throw new IllegalArgumentException("You must specify a non-empty String for playFromMediaId.");
        }

        public void playFromSearch(String string2, Bundle object) {
            String string3 = string2;
            if (string2 == null) {
                string3 = "";
            }
            try {
                MediaController.this.mSessionBinder.playFromSearch(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub, string3, (Bundle)object);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error calling play(");
                ((StringBuilder)object).append(string3);
                ((StringBuilder)object).append(").");
                Log.wtf(TAG, ((StringBuilder)object).toString(), remoteException);
            }
        }

        public void playFromUri(Uri uri, Bundle object) {
            if (uri != null && !Uri.EMPTY.equals(uri)) {
                try {
                    MediaController.this.mSessionBinder.playFromUri(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub, uri, (Bundle)object);
                }
                catch (RemoteException remoteException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Error calling play(");
                    ((StringBuilder)object).append(uri);
                    ((StringBuilder)object).append(").");
                    Log.wtf(TAG, ((StringBuilder)object).toString(), remoteException);
                }
                return;
            }
            throw new IllegalArgumentException("You must specify a non-empty Uri for playFromUri.");
        }

        public void prepare() {
            try {
                MediaController.this.mSessionBinder.prepare(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub);
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Error calling prepare.", remoteException);
            }
        }

        public void prepareFromMediaId(String string2, Bundle object) {
            if (!TextUtils.isEmpty(string2)) {
                try {
                    MediaController.this.mSessionBinder.prepareFromMediaId(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub, string2, (Bundle)object);
                }
                catch (RemoteException remoteException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Error calling prepare(");
                    ((StringBuilder)object).append(string2);
                    ((StringBuilder)object).append(").");
                    Log.wtf(TAG, ((StringBuilder)object).toString(), remoteException);
                }
                return;
            }
            throw new IllegalArgumentException("You must specify a non-empty String for prepareFromMediaId.");
        }

        public void prepareFromSearch(String string2, Bundle object) {
            String string3 = string2;
            if (string2 == null) {
                string3 = "";
            }
            try {
                MediaController.this.mSessionBinder.prepareFromSearch(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub, string3, (Bundle)object);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error calling prepare(");
                ((StringBuilder)object).append(string3);
                ((StringBuilder)object).append(").");
                Log.wtf(TAG, ((StringBuilder)object).toString(), remoteException);
            }
        }

        public void prepareFromUri(Uri uri, Bundle bundle) {
            if (uri != null && !Uri.EMPTY.equals(uri)) {
                try {
                    MediaController.this.mSessionBinder.prepareFromUri(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub, uri, bundle);
                }
                catch (RemoteException remoteException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error calling prepare(");
                    stringBuilder.append(uri);
                    stringBuilder.append(").");
                    Log.wtf(TAG, stringBuilder.toString(), remoteException);
                }
                return;
            }
            throw new IllegalArgumentException("You must specify a non-empty Uri for prepareFromUri.");
        }

        public void rewind() {
            try {
                MediaController.this.mSessionBinder.rewind(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub);
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Error calling rewind.", remoteException);
            }
        }

        public void seekTo(long l) {
            try {
                MediaController.this.mSessionBinder.seekTo(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub, l);
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Error calling seekTo.", remoteException);
            }
        }

        public void sendCustomAction(PlaybackState.CustomAction customAction, Bundle bundle) {
            if (customAction != null) {
                this.sendCustomAction(customAction.getAction(), bundle);
                return;
            }
            throw new IllegalArgumentException("CustomAction cannot be null.");
        }

        public void sendCustomAction(String string2, Bundle bundle) {
            if (!TextUtils.isEmpty(string2)) {
                try {
                    MediaController.this.mSessionBinder.sendCustomAction(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub, string2, bundle);
                }
                catch (RemoteException remoteException) {
                    Log.d(TAG, "Dead object in sendCustomAction.", remoteException);
                }
                return;
            }
            throw new IllegalArgumentException("CustomAction cannot be null.");
        }

        public void setPlaybackSpeed(float f) {
            if (f != 0.0f) {
                try {
                    MediaController.this.mSessionBinder.setPlaybackSpeed(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub, f);
                }
                catch (RemoteException remoteException) {
                    Log.wtf(TAG, "Error calling setPlaybackSpeed.", remoteException);
                }
                return;
            }
            throw new IllegalArgumentException("speed must not be zero");
        }

        public void setRating(Rating rating) {
            try {
                MediaController.this.mSessionBinder.rate(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub, rating);
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Error calling rate.", remoteException);
            }
        }

        public void skipToNext() {
            try {
                MediaController.this.mSessionBinder.next(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub);
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Error calling next.", remoteException);
            }
        }

        public void skipToPrevious() {
            try {
                MediaController.this.mSessionBinder.previous(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub);
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Error calling previous.", remoteException);
            }
        }

        public void skipToQueueItem(long l) {
            try {
                MediaController.this.mSessionBinder.skipToQueueItem(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub, l);
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error calling skipToItem(");
                stringBuilder.append(l);
                stringBuilder.append(").");
                Log.wtf(TAG, stringBuilder.toString(), remoteException);
            }
        }

        public void stop() {
            try {
                MediaController.this.mSessionBinder.stop(MediaController.this.mContext.getPackageName(), MediaController.this.mCbStub);
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Error calling stop.", remoteException);
            }
        }
    }

}

