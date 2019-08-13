/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.annotation.SystemApi;
import android.content.Intent;
import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.DvbDeviceInfo;
import android.media.tv.ITvInputClient;
import android.media.tv.ITvInputHardware;
import android.media.tv.ITvInputHardwareCallback;
import android.media.tv.ITvInputManager;
import android.media.tv.ITvInputManagerCallback;
import android.media.tv.TvContentRating;
import android.media.tv.TvContentRatingSystemInfo;
import android.media.tv.TvInputHardwareInfo;
import android.media.tv.TvInputInfo;
import android.media.tv.TvStreamConfig;
import android.media.tv.TvTrackInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pools;
import android.util.SparseArray;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventSender;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class TvInputManager {
    public static final String ACTION_BLOCKED_RATINGS_CHANGED = "android.media.tv.action.BLOCKED_RATINGS_CHANGED";
    public static final String ACTION_PARENTAL_CONTROLS_ENABLED_CHANGED = "android.media.tv.action.PARENTAL_CONTROLS_ENABLED_CHANGED";
    public static final String ACTION_QUERY_CONTENT_RATING_SYSTEMS = "android.media.tv.action.QUERY_CONTENT_RATING_SYSTEMS";
    public static final String ACTION_SETUP_INPUTS = "android.media.tv.action.SETUP_INPUTS";
    public static final String ACTION_VIEW_RECORDING_SCHEDULES = "android.media.tv.action.VIEW_RECORDING_SCHEDULES";
    public static final int DVB_DEVICE_DEMUX = 0;
    public static final int DVB_DEVICE_DVR = 1;
    static final int DVB_DEVICE_END = 2;
    public static final int DVB_DEVICE_FRONTEND = 2;
    static final int DVB_DEVICE_START = 0;
    public static final int INPUT_STATE_CONNECTED = 0;
    public static final int INPUT_STATE_CONNECTED_STANDBY = 1;
    public static final int INPUT_STATE_DISCONNECTED = 2;
    public static final String META_DATA_CONTENT_RATING_SYSTEMS = "android.media.tv.metadata.CONTENT_RATING_SYSTEMS";
    static final int RECORDING_ERROR_END = 2;
    public static final int RECORDING_ERROR_INSUFFICIENT_SPACE = 1;
    public static final int RECORDING_ERROR_RESOURCE_BUSY = 2;
    static final int RECORDING_ERROR_START = 0;
    public static final int RECORDING_ERROR_UNKNOWN = 0;
    private static final String TAG = "TvInputManager";
    public static final long TIME_SHIFT_INVALID_TIME = Long.MIN_VALUE;
    public static final int TIME_SHIFT_STATUS_AVAILABLE = 3;
    public static final int TIME_SHIFT_STATUS_UNAVAILABLE = 2;
    public static final int TIME_SHIFT_STATUS_UNKNOWN = 0;
    public static final int TIME_SHIFT_STATUS_UNSUPPORTED = 1;
    public static final int VIDEO_UNAVAILABLE_REASON_AUDIO_ONLY = 4;
    public static final int VIDEO_UNAVAILABLE_REASON_BUFFERING = 3;
    static final int VIDEO_UNAVAILABLE_REASON_END = 5;
    public static final int VIDEO_UNAVAILABLE_REASON_NOT_CONNECTED = 5;
    static final int VIDEO_UNAVAILABLE_REASON_START = 0;
    public static final int VIDEO_UNAVAILABLE_REASON_TUNING = 1;
    public static final int VIDEO_UNAVAILABLE_REASON_UNKNOWN = 0;
    public static final int VIDEO_UNAVAILABLE_REASON_WEAK_SIGNAL = 2;
    private final List<TvInputCallbackRecord> mCallbackRecords = new LinkedList<TvInputCallbackRecord>();
    private final ITvInputClient mClient;
    private final Object mLock = new Object();
    private int mNextSeq;
    private final ITvInputManager mService;
    private final SparseArray<SessionCallbackRecord> mSessionCallbackRecordMap = new SparseArray();
    private final Map<String, Integer> mStateMap = new ArrayMap<String, Integer>();
    private final int mUserId;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public TvInputManager(ITvInputManager object, int n) {
        Object object2;
        Iterator<TvInputInfo> iterator;
        this.mService = object;
        this.mUserId = n;
        this.mClient = new ITvInputClient.Stub(){

            private void postVideoSizeChangedIfNeededLocked(SessionCallbackRecord sessionCallbackRecord) {
                TvTrackInfo tvTrackInfo = sessionCallbackRecord.mSession.getVideoTrackToNotify();
                if (tvTrackInfo != null) {
                    sessionCallbackRecord.postVideoSizeChanged(tvTrackInfo.getVideoWidth(), tvTrackInfo.getVideoHeight());
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onChannelRetuned(Uri object, int n) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n);
                    if (sessionCallbackRecord == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq ");
                        ((StringBuilder)object).append(n);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    sessionCallbackRecord.postChannelRetuned((Uri)object);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onContentAllowed(int n) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    Object object = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq ");
                        ((StringBuilder)object).append(n);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    ((SessionCallbackRecord)object).postContentAllowed();
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onContentBlocked(String charSequence, int n) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n);
                    if (sessionCallbackRecord == null) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Callback not found for seq ");
                        ((StringBuilder)charSequence).append(n);
                        Log.e(TvInputManager.TAG, ((StringBuilder)charSequence).toString());
                        return;
                    }
                    sessionCallbackRecord.postContentBlocked(TvContentRating.unflattenFromString((String)charSequence));
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onError(int n, int n2) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    Object object = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n2);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq ");
                        ((StringBuilder)object).append(n2);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    ((SessionCallbackRecord)object).postError(n);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onLayoutSurface(int n, int n2, int n3, int n4, int n5) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    Object object = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n5);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq ");
                        ((StringBuilder)object).append(n5);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    ((SessionCallbackRecord)object).postLayoutSurface(n, n2, n3, n4);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onRecordingStopped(Uri object, int n) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n);
                    if (sessionCallbackRecord == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq ");
                        ((StringBuilder)object).append(n);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    sessionCallbackRecord.postRecordingStopped((Uri)object);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onSessionCreated(String object, IBinder iBinder, InputChannel inputChannel, int n) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n);
                    if (sessionCallbackRecord == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for ");
                        ((StringBuilder)object).append(iBinder);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    object = null;
                    if (iBinder != null) {
                        object = new Session(iBinder, inputChannel, TvInputManager.this.mService, TvInputManager.this.mUserId, n, TvInputManager.this.mSessionCallbackRecordMap);
                    } else {
                        TvInputManager.this.mSessionCallbackRecordMap.delete(n);
                    }
                    sessionCallbackRecord.postSessionCreated((Session)object);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onSessionEvent(String charSequence, Bundle bundle, int n) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n);
                    if (sessionCallbackRecord == null) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Callback not found for seq ");
                        ((StringBuilder)charSequence).append(n);
                        Log.e(TvInputManager.TAG, ((StringBuilder)charSequence).toString());
                        return;
                    }
                    sessionCallbackRecord.postSessionEvent((String)charSequence, bundle);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onSessionReleased(int n) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    Object object = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n);
                    TvInputManager.this.mSessionCallbackRecordMap.delete(n);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq:");
                        ((StringBuilder)object).append(n);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    ((SessionCallbackRecord)object).mSession.releaseInternal();
                    ((SessionCallbackRecord)object).postSessionReleased();
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onTimeShiftCurrentPositionChanged(long l, int n) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    Object object = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq ");
                        ((StringBuilder)object).append(n);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    ((SessionCallbackRecord)object).postTimeShiftCurrentPositionChanged(l);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onTimeShiftStartPositionChanged(long l, int n) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    Object object = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq ");
                        ((StringBuilder)object).append(n);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    ((SessionCallbackRecord)object).postTimeShiftStartPositionChanged(l);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onTimeShiftStatusChanged(int n, int n2) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    Object object = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n2);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq ");
                        ((StringBuilder)object).append(n2);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    ((SessionCallbackRecord)object).postTimeShiftStatusChanged(n);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onTrackSelected(int n, String charSequence, int n2) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n2);
                    if (sessionCallbackRecord == null) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Callback not found for seq ");
                        ((StringBuilder)charSequence).append(n2);
                        Log.e(TvInputManager.TAG, ((StringBuilder)charSequence).toString());
                        return;
                    }
                    if (sessionCallbackRecord.mSession.updateTrackSelection(n, (String)charSequence)) {
                        sessionCallbackRecord.postTrackSelected(n, (String)charSequence);
                        this.postVideoSizeChangedIfNeededLocked(sessionCallbackRecord);
                    }
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onTracksChanged(List<TvTrackInfo> object, int n) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n);
                    if (sessionCallbackRecord == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq ");
                        ((StringBuilder)object).append(n);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    if (sessionCallbackRecord.mSession.updateTracks((List<TvTrackInfo>)object)) {
                        sessionCallbackRecord.postTracksChanged((List<TvTrackInfo>)object);
                        this.postVideoSizeChangedIfNeededLocked(sessionCallbackRecord);
                    }
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onTuned(int n, Uri object) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    SessionCallbackRecord sessionCallbackRecord = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n);
                    if (sessionCallbackRecord == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq ");
                        ((StringBuilder)object).append(n);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    sessionCallbackRecord.postTuned((Uri)object);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onVideoAvailable(int n) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    Object object = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq ");
                        ((StringBuilder)object).append(n);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    ((SessionCallbackRecord)object).postVideoAvailable();
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onVideoUnavailable(int n, int n2) {
                SparseArray sparseArray = TvInputManager.this.mSessionCallbackRecordMap;
                synchronized (sparseArray) {
                    Object object = (SessionCallbackRecord)TvInputManager.this.mSessionCallbackRecordMap.get(n2);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Callback not found for seq ");
                        ((StringBuilder)object).append(n2);
                        Log.e(TvInputManager.TAG, ((StringBuilder)object).toString());
                        return;
                    }
                    ((SessionCallbackRecord)object).postVideoUnavailable(n);
                    return;
                }
            }
        };
        object = new ITvInputManagerCallback.Stub(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onInputAdded(String string2) {
                Object object = TvInputManager.this.mLock;
                synchronized (object) {
                    TvInputManager.this.mStateMap.put(string2, 0);
                    Iterator iterator = TvInputManager.this.mCallbackRecords.iterator();
                    while (iterator.hasNext()) {
                        ((TvInputCallbackRecord)iterator.next()).postInputAdded(string2);
                    }
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onInputRemoved(String string2) {
                Object object = TvInputManager.this.mLock;
                synchronized (object) {
                    TvInputManager.this.mStateMap.remove(string2);
                    Iterator iterator = TvInputManager.this.mCallbackRecords.iterator();
                    while (iterator.hasNext()) {
                        ((TvInputCallbackRecord)iterator.next()).postInputRemoved(string2);
                    }
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onInputStateChanged(String string2, int n) {
                Object object = TvInputManager.this.mLock;
                synchronized (object) {
                    TvInputManager.this.mStateMap.put(string2, n);
                    Iterator iterator = TvInputManager.this.mCallbackRecords.iterator();
                    while (iterator.hasNext()) {
                        ((TvInputCallbackRecord)iterator.next()).postInputStateChanged(string2, n);
                    }
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onInputUpdated(String string2) {
                Object object = TvInputManager.this.mLock;
                synchronized (object) {
                    Iterator iterator = TvInputManager.this.mCallbackRecords.iterator();
                    while (iterator.hasNext()) {
                        ((TvInputCallbackRecord)iterator.next()).postInputUpdated(string2);
                    }
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onTvInputInfoUpdated(TvInputInfo tvInputInfo) {
                Object object = TvInputManager.this.mLock;
                synchronized (object) {
                    Iterator iterator = TvInputManager.this.mCallbackRecords.iterator();
                    while (iterator.hasNext()) {
                        ((TvInputCallbackRecord)iterator.next()).postTvInputInfoUpdated(tvInputInfo);
                    }
                    return;
                }
            }
        };
        try {
            if (this.mService == null) return;
            this.mService.registerCallback((ITvInputManagerCallback)object, this.mUserId);
            object2 = this.mService.getTvInputList(this.mUserId);
            object = this.mLock;
            // MONITORENTER : object
            iterator = object2.iterator();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        while (iterator.hasNext()) {
            object2 = iterator.next().getId();
            this.mStateMap.put((String)object2, this.mService.getTvInputState((String)object2, this.mUserId));
        }
        // MONITOREXIT : object
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void createSessionInternal(String string2, boolean bl, SessionCallback object, Handler object2) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(object2);
        object2 = new SessionCallbackRecord((SessionCallback)object, (Handler)object2);
        object = this.mSessionCallbackRecordMap;
        synchronized (object) {
            int n = this.mNextSeq;
            this.mNextSeq = n + 1;
            this.mSessionCallbackRecordMap.put(n, (SessionCallbackRecord)object2);
            try {
                this.mService.createSession(this.mClient, string2, bl, n, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public Hardware acquireTvInputHardware(int n, TvInputInfo object, final HardwareCallback hardwareCallback) {
        try {
            ITvInputManager iTvInputManager = this.mService;
            ITvInputHardwareCallback.Stub stub = new ITvInputHardwareCallback.Stub(){

                @Override
                public void onReleased() {
                    hardwareCallback.onReleased();
                }

                @Override
                public void onStreamConfigChanged(TvStreamConfig[] arrtvStreamConfig) {
                    hardwareCallback.onStreamConfigChanged(arrtvStreamConfig);
                }
            };
            object = new Hardware(iTvInputManager.acquireTvInputHardware(n, stub, (TvInputInfo)object, this.mUserId));
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Hardware acquireTvInputHardware(int n, HardwareCallback hardwareCallback, TvInputInfo tvInputInfo) {
        return this.acquireTvInputHardware(n, tvInputInfo, hardwareCallback);
    }

    @SystemApi
    public void addBlockedRating(TvContentRating tvContentRating) {
        Preconditions.checkNotNull(tvContentRating);
        try {
            this.mService.addBlockedRating(tvContentRating.flattenToString(), this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean captureFrame(String string2, Surface surface, TvStreamConfig tvStreamConfig) {
        try {
            boolean bl = this.mService.captureFrame(string2, surface, tvStreamConfig, this.mUserId);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void createRecordingSession(String string2, SessionCallback sessionCallback, Handler handler) {
        this.createSessionInternal(string2, true, sessionCallback, handler);
    }

    public void createSession(String string2, SessionCallback sessionCallback, Handler handler) {
        this.createSessionInternal(string2, false, sessionCallback, handler);
    }

    @SystemApi
    public List<TvStreamConfig> getAvailableTvStreamConfigList(String object) {
        try {
            object = this.mService.getAvailableTvStreamConfigList((String)object, this.mUserId);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<TvContentRating> getBlockedRatings() {
        try {
            ArrayList<TvContentRating> arrayList = new ArrayList<TvContentRating>();
            Iterator<String> iterator = this.mService.getBlockedRatings(this.mUserId).iterator();
            while (iterator.hasNext()) {
                arrayList.add(TvContentRating.unflattenFromString(iterator.next()));
            }
            return arrayList;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<DvbDeviceInfo> getDvbDeviceList() {
        try {
            List<DvbDeviceInfo> list = this.mService.getDvbDeviceList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<TvInputHardwareInfo> getHardwareList() {
        try {
            List<TvInputHardwareInfo> list = this.mService.getHardwareList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getInputState(String string2) {
        Preconditions.checkNotNull(string2);
        Object object = this.mLock;
        synchronized (object) {
            Serializable serializable = this.mStateMap.get(string2);
            if (serializable != null) return (Integer)serializable;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Unrecognized input ID: ");
            ((StringBuilder)serializable).append(string2);
            Log.w(TAG, ((StringBuilder)serializable).toString());
            return 2;
        }
    }

    @SystemApi
    public List<TvContentRatingSystemInfo> getTvContentRatingSystemList() {
        try {
            List<TvContentRatingSystemInfo> list = this.mService.getTvContentRatingSystemList(this.mUserId);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public TvInputInfo getTvInputInfo(String object) {
        Preconditions.checkNotNull(object);
        try {
            object = this.mService.getTvInputInfo((String)object, this.mUserId);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<TvInputInfo> getTvInputList() {
        try {
            List<TvInputInfo> list = this.mService.getTvInputList(this.mUserId);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isParentalControlsEnabled() {
        try {
            boolean bl = this.mService.isParentalControlsEnabled(this.mUserId);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isRatingBlocked(TvContentRating tvContentRating) {
        Preconditions.checkNotNull(tvContentRating);
        try {
            boolean bl = this.mService.isRatingBlocked(tvContentRating.flattenToString(), this.mUserId);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isSingleSessionActive() {
        try {
            boolean bl = this.mService.isSingleSessionActive(this.mUserId);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void notifyPreviewProgramAddedToWatchNext(String string2, long l, long l2) {
        Intent intent = new Intent();
        intent.setAction("android.media.tv.action.PREVIEW_PROGRAM_ADDED_TO_WATCH_NEXT");
        intent.putExtra("android.media.tv.extra.PREVIEW_PROGRAM_ID", l);
        intent.putExtra("android.media.tv.extra.WATCH_NEXT_PROGRAM_ID", l2);
        intent.setPackage(string2);
        try {
            this.mService.sendTvInputNotifyIntent(intent, this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void notifyPreviewProgramBrowsableDisabled(String string2, long l) {
        Intent intent = new Intent();
        intent.setAction("android.media.tv.action.PREVIEW_PROGRAM_BROWSABLE_DISABLED");
        intent.putExtra("android.media.tv.extra.PREVIEW_PROGRAM_ID", l);
        intent.setPackage(string2);
        try {
            this.mService.sendTvInputNotifyIntent(intent, this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void notifyWatchNextProgramBrowsableDisabled(String string2, long l) {
        Intent intent = new Intent();
        intent.setAction("android.media.tv.action.WATCH_NEXT_PROGRAM_BROWSABLE_DISABLED");
        intent.putExtra("android.media.tv.extra.WATCH_NEXT_PROGRAM_ID", l);
        intent.setPackage(string2);
        try {
            this.mService.sendTvInputNotifyIntent(intent, this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public ParcelFileDescriptor openDvbDevice(DvbDeviceInfo var1_1, int var2_3) {
        if (var2_3 < 0 || 2 < var2_3) ** GOTO lbl4
        try {
            return this.mService.openDvbDevice((DvbDeviceInfo)var1_1, var2_3);
lbl4: // 1 sources:
            var1_1 = new StringBuilder();
            var1_1.append("Invalid DVB device: ");
            var1_1.append(var2_3);
            var3_4 = new IllegalArgumentException(var1_1.toString());
            throw var3_4;
        }
        catch (RemoteException var1_2) {
            throw var1_2.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerCallback(TvInputCallback tvInputCallback, Handler handler) {
        Preconditions.checkNotNull(tvInputCallback);
        Preconditions.checkNotNull(handler);
        Object object = this.mLock;
        synchronized (object) {
            List<TvInputCallbackRecord> list = this.mCallbackRecords;
            TvInputCallbackRecord tvInputCallbackRecord = new TvInputCallbackRecord(tvInputCallback, handler);
            list.add(tvInputCallbackRecord);
            return;
        }
    }

    @SystemApi
    public void releaseTvInputHardware(int n, Hardware hardware) {
        try {
            this.mService.releaseTvInputHardware(n, hardware.getInterface(), this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void removeBlockedRating(TvContentRating tvContentRating) {
        Preconditions.checkNotNull(tvContentRating);
        try {
            this.mService.removeBlockedRating(tvContentRating.flattenToString(), this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestChannelBrowsable(Uri uri) {
        try {
            this.mService.requestChannelBrowsable(uri, this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setParentalControlsEnabled(boolean bl) {
        try {
            this.mService.setParentalControlsEnabled(bl, this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterCallback(TvInputCallback tvInputCallback) {
        Preconditions.checkNotNull(tvInputCallback);
        Object object = this.mLock;
        synchronized (object) {
            Iterator<TvInputCallbackRecord> iterator = this.mCallbackRecords.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getCallback() != tvInputCallback) continue;
                iterator.remove();
                break;
            }
            return;
        }
    }

    public void updateTvInputInfo(TvInputInfo tvInputInfo) {
        Preconditions.checkNotNull(tvInputInfo);
        try {
            this.mService.updateTvInputInfo(tvInputInfo, this.mUserId);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static final class Hardware {
        private final ITvInputHardware mInterface;

        private Hardware(ITvInputHardware iTvInputHardware) {
            this.mInterface = iTvInputHardware;
        }

        private ITvInputHardware getInterface() {
            return this.mInterface;
        }

        @SystemApi
        public boolean dispatchKeyEventToHdmi(KeyEvent keyEvent) {
            return false;
        }

        public void overrideAudioSink(int n, String string2, int n2, int n3, int n4) {
            try {
                this.mInterface.overrideAudioSink(n, string2, n2, n3, n4);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        public void setStreamVolume(float f) {
            try {
                this.mInterface.setStreamVolume(f);
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }

        public boolean setSurface(Surface surface, TvStreamConfig tvStreamConfig) {
            try {
                boolean bl = this.mInterface.setSurface(surface, tvStreamConfig);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }
    }

    @SystemApi
    public static abstract class HardwareCallback {
        public abstract void onReleased();

        public abstract void onStreamConfigChanged(TvStreamConfig[] var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InputState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RecordingError {
    }

    public static final class Session {
        static final int DISPATCH_HANDLED = 1;
        static final int DISPATCH_IN_PROGRESS = -1;
        static final int DISPATCH_NOT_HANDLED = 0;
        private static final long INPUT_SESSION_NOT_RESPONDING_TIMEOUT = 2500L;
        private final List<TvTrackInfo> mAudioTracks = new ArrayList<TvTrackInfo>();
        private InputChannel mChannel;
        private final InputEventHandler mHandler = new InputEventHandler(Looper.getMainLooper());
        private final Object mMetadataLock = new Object();
        private final Pools.Pool<PendingEvent> mPendingEventPool = new Pools.SimplePool<PendingEvent>(20);
        private final SparseArray<PendingEvent> mPendingEvents = new SparseArray(20);
        private String mSelectedAudioTrackId;
        private String mSelectedSubtitleTrackId;
        private String mSelectedVideoTrackId;
        private TvInputEventSender mSender;
        private final int mSeq;
        private final ITvInputManager mService;
        private final SparseArray<SessionCallbackRecord> mSessionCallbackRecordMap;
        private final List<TvTrackInfo> mSubtitleTracks = new ArrayList<TvTrackInfo>();
        private IBinder mToken;
        private final int mUserId;
        private int mVideoHeight;
        private final List<TvTrackInfo> mVideoTracks = new ArrayList<TvTrackInfo>();
        private int mVideoWidth;

        private Session(IBinder iBinder, InputChannel inputChannel, ITvInputManager iTvInputManager, int n, int n2, SparseArray<SessionCallbackRecord> sparseArray) {
            this.mToken = iBinder;
            this.mChannel = inputChannel;
            this.mService = iTvInputManager;
            this.mUserId = n;
            this.mSeq = n2;
            this.mSessionCallbackRecordMap = sparseArray;
        }

        private boolean containsTrack(List<TvTrackInfo> object, String string2) {
            object = object.iterator();
            while (object.hasNext()) {
                if (!((TvTrackInfo)object.next()).getId().equals(string2)) continue;
                return true;
            }
            return false;
        }

        private void flushPendingEventsLocked() {
            this.mHandler.removeMessages(3);
            int n = this.mPendingEvents.size();
            for (int i = 0; i < n; ++i) {
                int n2 = this.mPendingEvents.keyAt(i);
                Message message = this.mHandler.obtainMessage(3, n2, 0);
                message.setAsynchronous(true);
                message.sendToTarget();
            }
        }

        private PendingEvent obtainPendingEventLocked(InputEvent inputEvent, Object object, FinishedInputEventCallback finishedInputEventCallback, Handler handler) {
            PendingEvent pendingEvent;
            PendingEvent pendingEvent2 = pendingEvent = this.mPendingEventPool.acquire();
            if (pendingEvent == null) {
                pendingEvent2 = new PendingEvent();
            }
            pendingEvent2.mEvent = inputEvent;
            pendingEvent2.mEventToken = object;
            pendingEvent2.mCallback = finishedInputEventCallback;
            pendingEvent2.mEventHandler = handler;
            return pendingEvent2;
        }

        private void recyclePendingEventLocked(PendingEvent pendingEvent) {
            pendingEvent.recycle();
            this.mPendingEventPool.release(pendingEvent);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void releaseInternal() {
            this.mToken = null;
            InputEventHandler inputEventHandler = this.mHandler;
            synchronized (inputEventHandler) {
                if (this.mChannel != null) {
                    if (this.mSender != null) {
                        this.flushPendingEventsLocked();
                        this.mSender.dispose();
                        this.mSender = null;
                    }
                    this.mChannel.dispose();
                    this.mChannel = null;
                }
            }
            SparseArray<SessionCallbackRecord> sparseArray = this.mSessionCallbackRecordMap;
            synchronized (sparseArray) {
                this.mSessionCallbackRecordMap.delete(this.mSeq);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendInputEventAndReportResultOnMainLooper(PendingEvent pendingEvent) {
            InputEventHandler inputEventHandler = this.mHandler;
            synchronized (inputEventHandler) {
                if (this.sendInputEventOnMainLooperLocked(pendingEvent) == -1) {
                    return;
                }
            }
            this.invokeFinishedInputEventCallback(pendingEvent, false);
        }

        private int sendInputEventOnMainLooperLocked(PendingEvent object) {
            Parcelable parcelable = this.mChannel;
            if (parcelable != null) {
                int n;
                if (this.mSender == null) {
                    this.mSender = new TvInputEventSender((InputChannel)parcelable, this.mHandler.getLooper());
                }
                if (this.mSender.sendInputEvent(n = ((InputEvent)(parcelable = ((PendingEvent)object).mEvent)).getSequenceNumber(), (InputEvent)parcelable)) {
                    this.mPendingEvents.put(n, (PendingEvent)object);
                    object = this.mHandler.obtainMessage(2, object);
                    ((Message)object).setAsynchronous(true);
                    this.mHandler.sendMessageDelayed((Message)object, 2500L);
                    return -1;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to send input event to session: ");
                ((StringBuilder)object).append(this.mToken);
                ((StringBuilder)object).append(" dropping:");
                ((StringBuilder)object).append(parcelable);
                Log.w(TvInputManager.TAG, ((StringBuilder)object).toString());
            }
            return 0;
        }

        void createOverlayView(View view, Rect rect) {
            Preconditions.checkNotNull(view);
            Preconditions.checkNotNull(rect);
            if (view.getWindowToken() != null) {
                IBinder iBinder = this.mToken;
                if (iBinder == null) {
                    Log.w(TvInputManager.TAG, "The session has been already released");
                    return;
                }
                try {
                    this.mService.createOverlayView(iBinder, view.getWindowToken(), rect, this.mUserId);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalStateException("view must be attached to a window");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public int dispatchInputEvent(InputEvent object, Object object2, FinishedInputEventCallback finishedInputEventCallback, Handler handler) {
            Preconditions.checkNotNull(object);
            Preconditions.checkNotNull(finishedInputEventCallback);
            Preconditions.checkNotNull(handler);
            InputEventHandler inputEventHandler = this.mHandler;
            synchronized (inputEventHandler) {
                if (this.mChannel == null) {
                    return 0;
                }
                object = this.obtainPendingEventLocked((InputEvent)object, object2, finishedInputEventCallback, handler);
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    return this.sendInputEventOnMainLooperLocked((PendingEvent)object);
                }
                object = this.mHandler.obtainMessage(1, object);
                ((Message)object).setAsynchronous(true);
                this.mHandler.sendMessage((Message)object);
                return -1;
            }
        }

        public void dispatchSurfaceChanged(int n, int n2, int n3) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.dispatchSurfaceChanged(iBinder, n, n2, n3, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void finishedInputEvent(int n, boolean bl, boolean bl2) {
            PendingEvent pendingEvent;
            InputEventHandler inputEventHandler = this.mHandler;
            synchronized (inputEventHandler) {
                n = this.mPendingEvents.indexOfKey(n);
                if (n < 0) {
                    return;
                }
                pendingEvent = this.mPendingEvents.valueAt(n);
                this.mPendingEvents.removeAt(n);
                if (bl2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Timeout waiting for session to handle input event after 2500 ms: ");
                    stringBuilder.append(this.mToken);
                    Log.w(TvInputManager.TAG, stringBuilder.toString());
                } else {
                    this.mHandler.removeMessages(2, pendingEvent);
                }
            }
            this.invokeFinishedInputEventCallback(pendingEvent, bl);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public String getSelectedTrack(int n) {
            Object object = this.mMetadataLock;
            // MONITORENTER : object
            if (n == 0) {
                String string2 = this.mSelectedAudioTrackId;
                // MONITOREXIT : object
                return string2;
            }
            if (n == 1) {
                String string3 = this.mSelectedVideoTrackId;
                // MONITOREXIT : object
                return string3;
            }
            if (n == 2) {
                String string4 = this.mSelectedSubtitleTrackId;
                // MONITOREXIT : object
                return string4;
            }
            // MONITOREXIT : object
            object = new StringBuilder();
            ((StringBuilder)object).append("invalid type: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        IBinder getToken() {
            return this.mToken;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public List<TvTrackInfo> getTracks(int n) {
            Object object = this.mMetadataLock;
            // MONITORENTER : object
            if (n == 0) {
                if (this.mAudioTracks == null) {
                    // MONITOREXIT : object
                    return null;
                }
                ArrayList<TvTrackInfo> arrayList = new ArrayList<TvTrackInfo>(this.mAudioTracks);
                // MONITOREXIT : object
                return arrayList;
            }
            if (n == 1) {
                if (this.mVideoTracks == null) {
                    // MONITOREXIT : object
                    return null;
                }
                ArrayList<TvTrackInfo> arrayList = new ArrayList<TvTrackInfo>(this.mVideoTracks);
                // MONITOREXIT : object
                return arrayList;
            }
            if (n == 2) {
                if (this.mSubtitleTracks == null) {
                    // MONITOREXIT : object
                    return null;
                }
                ArrayList<TvTrackInfo> arrayList = new ArrayList<TvTrackInfo>(this.mSubtitleTracks);
                // MONITOREXIT : object
                return arrayList;
            }
            // MONITOREXIT : object
            object = new StringBuilder();
            ((StringBuilder)object).append("invalid type: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        TvTrackInfo getVideoTrackToNotify() {
            Object object = this.mMetadataLock;
            synchronized (object) {
                if (!this.mVideoTracks.isEmpty() && this.mSelectedVideoTrackId != null) {
                    for (TvTrackInfo tvTrackInfo : this.mVideoTracks) {
                        if (!tvTrackInfo.getId().equals(this.mSelectedVideoTrackId)) continue;
                        int n = tvTrackInfo.getVideoWidth();
                        int n2 = tvTrackInfo.getVideoHeight();
                        if (this.mVideoWidth == n && this.mVideoHeight == n2) continue;
                        this.mVideoWidth = n;
                        this.mVideoHeight = n2;
                        return tvTrackInfo;
                    }
                }
                return null;
            }
        }

        void invokeFinishedInputEventCallback(PendingEvent object, boolean bl) {
            ((PendingEvent)object).mHandled = bl;
            if (((PendingEvent)object).mEventHandler.getLooper().isCurrentThread()) {
                ((PendingEvent)object).run();
            } else {
                object = Message.obtain(((PendingEvent)object).mEventHandler, (Runnable)object);
                ((Message)object).setAsynchronous(true);
                ((Message)object).sendToTarget();
            }
        }

        void relayoutOverlayView(Rect rect) {
            Preconditions.checkNotNull(rect);
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.relayoutOverlayView(iBinder, rect, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public void release() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.releaseSession(iBinder, this.mUserId);
                this.releaseInternal();
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        void removeOverlayView() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.removeOverlayView(iBinder, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void selectTrack(int var1_1, String var2_2) {
            block13 : {
                block15 : {
                    var3_5 = this.mMetadataLock;
                    // MONITORENTER : var3_5
                    if (var1_1 != 0) ** GOTO lbl17
                    if (var2_2 == null) break;
                    if (!this.containsTrack(this.mAudioTracks, (String)var2_2)) {
                        var4_6 = new StringBuilder();
                        var4_6.append("Invalid audio trackId: ");
                        var4_6.append((String)var2_2);
                        Log.w("TvInputManager", var4_6.toString());
                        // MONITOREXIT : var3_5
                        return;
                    }
                    break block15;
lbl17: // 1 sources:
                    if (var1_1 == 1) {
                        if (var2_2 != null && !this.containsTrack(this.mVideoTracks, (String)var2_2)) {
                            var4_7 = new StringBuilder();
                            var4_7.append("Invalid video trackId: ");
                            var4_7.append((String)var2_2);
                            Log.w("TvInputManager", var4_7.toString());
                            // MONITOREXIT : var3_5
                            return;
                        }
                    } else {
                        if (var1_1 == 2) {
                            if (var2_2 != null && !this.containsTrack(this.mSubtitleTracks, (String)var2_2)) {
                                var4_8 = new StringBuilder();
                                var4_8.append("Invalid subtitle trackId: ");
                                var4_8.append((String)var2_2);
                                Log.w("TvInputManager", var4_8.toString());
                                // MONITOREXIT : var3_5
                                return;
                            } else {
                                ** GOTO lbl41
                            }
                        }
                        break block13;
                    }
                }
                // MONITOREXIT : var3_5
                var3_5 = this.mToken;
                if (var3_5 == null) {
                    Log.w("TvInputManager", "The session has been already released");
                    return;
                }
                try {
                    this.mService.selectTrack((IBinder)var3_5, var1_1, (String)var2_2, this.mUserId);
                    return;
                }
                catch (RemoteException var2_3) {
                    throw var2_3.rethrowFromSystemServer();
                }
            }
            var2_2 = new StringBuilder();
            var2_2.append("invalid type: ");
            var2_2.append(var1_1);
            var4_9 = new IllegalArgumentException(var2_2.toString());
            throw var4_9;
        }

        public void sendAppPrivateCommand(String string2, Bundle bundle) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.sendAppPrivateCommand(iBinder, string2, bundle, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public void setCaptionEnabled(boolean bl) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setCaptionEnabled(iBinder, bl, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        void setMain() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setMainSession(iBinder, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public void setStreamVolume(float var1_1) {
            var2_2 = this.mToken;
            if (var2_2 == null) {
                Log.w("TvInputManager", "The session has been already released");
                return;
            }
            if (var1_1 < 0.0f || var1_1 > 1.0f) ** GOTO lbl10
            try {
                this.mService.setVolume((IBinder)var2_2, var1_1, this.mUserId);
                return;
lbl10: // 1 sources:
                var2_2 = new IllegalArgumentException("volume should be between 0.0f and 1.0f");
                throw var2_2;
            }
            catch (RemoteException var2_3) {
                throw var2_3.rethrowFromSystemServer();
            }
        }

        public void setSurface(Surface surface) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.setSurface(iBinder, surface, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        void startRecording(Uri uri) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.startRecording(iBinder, uri, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        void stopRecording() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.stopRecording(iBinder, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        void timeShiftEnablePositionTracking(boolean bl) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftEnablePositionTracking(iBinder, bl, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        void timeShiftPause() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftPause(iBinder, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        void timeShiftPlay(Uri uri) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftPlay(iBinder, uri, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        void timeShiftResume() {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftResume(iBinder, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        void timeShiftSeekTo(long l) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftSeekTo(iBinder, l, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        void timeShiftSetPlaybackParams(PlaybackParams playbackParams) {
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.timeShiftSetPlaybackParams(iBinder, playbackParams, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public void tune(Uri uri) {
            this.tune(uri, null);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void tune(Uri uri, Bundle bundle) {
            Preconditions.checkNotNull(uri);
            if (this.mToken == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            Object object = this.mMetadataLock;
            synchronized (object) {
                this.mAudioTracks.clear();
                this.mVideoTracks.clear();
                this.mSubtitleTracks.clear();
                this.mSelectedAudioTrackId = null;
                this.mSelectedVideoTrackId = null;
                this.mSelectedSubtitleTrackId = null;
                this.mVideoWidth = 0;
                this.mVideoHeight = 0;
            }
            try {
                this.mService.tune(this.mToken, uri, bundle, this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        void unblockContent(TvContentRating tvContentRating) {
            Preconditions.checkNotNull(tvContentRating);
            IBinder iBinder = this.mToken;
            if (iBinder == null) {
                Log.w(TvInputManager.TAG, "The session has been already released");
                return;
            }
            try {
                this.mService.unblockContent(iBinder, tvContentRating.flattenToString(), this.mUserId);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        boolean updateTrackSelection(int n, String string2) {
            Object object = this.mMetadataLock;
            synchronized (object) {
                Throwable throwable2;
                block7 : {
                    if (n == 0) {
                        try {
                            if (!TextUtils.equals(string2, this.mSelectedAudioTrackId)) {
                                this.mSelectedAudioTrackId = string2;
                                return true;
                            }
                        }
                        catch (Throwable throwable2) {
                            break block7;
                        }
                    }
                    if (n == 1 && !TextUtils.equals(string2, this.mSelectedVideoTrackId)) {
                        this.mSelectedVideoTrackId = string2;
                        return true;
                    }
                    if (n == 2 && !TextUtils.equals(string2, this.mSelectedSubtitleTrackId)) {
                        this.mSelectedSubtitleTrackId = string2;
                        return true;
                    }
                    return false;
                }
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        boolean updateTracks(List<TvTrackInfo> object) {
            Object object2 = this.mMetadataLock;
            synchronized (object2) {
                boolean bl;
                this.mAudioTracks.clear();
                this.mVideoTracks.clear();
                this.mSubtitleTracks.clear();
                object = object.iterator();
                do {
                    boolean bl2 = object.hasNext();
                    bl = true;
                    if (!bl2) break;
                    TvTrackInfo tvTrackInfo = (TvTrackInfo)object.next();
                    if (tvTrackInfo.getType() == 0) {
                        this.mAudioTracks.add(tvTrackInfo);
                        continue;
                    }
                    if (tvTrackInfo.getType() == 1) {
                        this.mVideoTracks.add(tvTrackInfo);
                        continue;
                    }
                    if (tvTrackInfo.getType() != 2) continue;
                    this.mSubtitleTracks.add(tvTrackInfo);
                } while (true);
                if (!this.mAudioTracks.isEmpty()) return bl;
                if (!this.mVideoTracks.isEmpty()) return bl;
                if (this.mSubtitleTracks.isEmpty()) return false;
                return bl;
            }
        }

        public static interface FinishedInputEventCallback {
            public void onFinishedInputEvent(Object var1, boolean var2);
        }

        private final class InputEventHandler
        extends Handler {
            public static final int MSG_FLUSH_INPUT_EVENT = 3;
            public static final int MSG_SEND_INPUT_EVENT = 1;
            public static final int MSG_TIMEOUT_INPUT_EVENT = 2;

            InputEventHandler(Looper looper) {
                super(looper, null, true);
            }

            @Override
            public void handleMessage(Message message) {
                int n = message.what;
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            return;
                        }
                        Session.this.finishedInputEvent(message.arg1, false, false);
                        return;
                    }
                    Session.this.finishedInputEvent(message.arg1, false, true);
                    return;
                }
                Session.this.sendInputEventAndReportResultOnMainLooper((PendingEvent)message.obj);
            }
        }

        private final class PendingEvent
        implements Runnable {
            public FinishedInputEventCallback mCallback;
            public InputEvent mEvent;
            public Handler mEventHandler;
            public Object mEventToken;
            public boolean mHandled;

            private PendingEvent() {
            }

            public void recycle() {
                this.mEvent = null;
                this.mEventToken = null;
                this.mCallback = null;
                this.mEventHandler = null;
                this.mHandled = false;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                this.mCallback.onFinishedInputEvent(this.mEventToken, this.mHandled);
                Handler handler = this.mEventHandler;
                synchronized (handler) {
                    Session.this.recyclePendingEventLocked(this);
                    return;
                }
            }
        }

        private final class TvInputEventSender
        extends InputEventSender {
            public TvInputEventSender(InputChannel inputChannel, Looper looper) {
                super(inputChannel, looper);
            }

            @Override
            public void onInputEventFinished(int n, boolean bl) {
                Session.this.finishedInputEvent(n, bl, false);
            }
        }

    }

    public static abstract class SessionCallback {
        public void onChannelRetuned(Session session, Uri uri) {
        }

        public void onContentAllowed(Session session) {
        }

        public void onContentBlocked(Session session, TvContentRating tvContentRating) {
        }

        void onError(Session session, int n) {
        }

        public void onLayoutSurface(Session session, int n, int n2, int n3, int n4) {
        }

        void onRecordingStopped(Session session, Uri uri) {
        }

        public void onSessionCreated(Session session) {
        }

        public void onSessionEvent(Session session, String string2, Bundle bundle) {
        }

        public void onSessionReleased(Session session) {
        }

        public void onTimeShiftCurrentPositionChanged(Session session, long l) {
        }

        public void onTimeShiftStartPositionChanged(Session session, long l) {
        }

        public void onTimeShiftStatusChanged(Session session, int n) {
        }

        public void onTrackSelected(Session session, int n, String string2) {
        }

        public void onTracksChanged(Session session, List<TvTrackInfo> list) {
        }

        void onTuned(Session session, Uri uri) {
        }

        public void onVideoAvailable(Session session) {
        }

        public void onVideoSizeChanged(Session session, int n, int n2) {
        }

        public void onVideoUnavailable(Session session, int n) {
        }
    }

    private static final class SessionCallbackRecord {
        private final Handler mHandler;
        private Session mSession;
        private final SessionCallback mSessionCallback;

        SessionCallbackRecord(SessionCallback sessionCallback, Handler handler) {
            this.mSessionCallback = sessionCallback;
            this.mHandler = handler;
        }

        void postChannelRetuned(final Uri uri) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onChannelRetuned(SessionCallbackRecord.this.mSession, uri);
                }
            });
        }

        void postContentAllowed() {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onContentAllowed(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postContentBlocked(final TvContentRating tvContentRating) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onContentBlocked(SessionCallbackRecord.this.mSession, tvContentRating);
                }
            });
        }

        void postError(final int n) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onError(SessionCallbackRecord.this.mSession, n);
                }
            });
        }

        void postLayoutSurface(final int n, final int n2, final int n3, final int n4) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onLayoutSurface(SessionCallbackRecord.this.mSession, n, n2, n3, n4);
                }
            });
        }

        void postRecordingStopped(final Uri uri) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onRecordingStopped(SessionCallbackRecord.this.mSession, uri);
                }
            });
        }

        void postSessionCreated(final Session session) {
            this.mSession = session;
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionCreated(session);
                }
            });
        }

        void postSessionEvent(final String string2, final Bundle bundle) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionEvent(SessionCallbackRecord.this.mSession, string2, bundle);
                }
            });
        }

        void postSessionReleased() {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onSessionReleased(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postTimeShiftCurrentPositionChanged(final long l) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTimeShiftCurrentPositionChanged(SessionCallbackRecord.this.mSession, l);
                }
            });
        }

        void postTimeShiftStartPositionChanged(final long l) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTimeShiftStartPositionChanged(SessionCallbackRecord.this.mSession, l);
                }
            });
        }

        void postTimeShiftStatusChanged(final int n) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTimeShiftStatusChanged(SessionCallbackRecord.this.mSession, n);
                }
            });
        }

        void postTrackSelected(final int n, final String string2) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTrackSelected(SessionCallbackRecord.this.mSession, n, string2);
                }
            });
        }

        void postTracksChanged(final List<TvTrackInfo> list) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTracksChanged(SessionCallbackRecord.this.mSession, list);
                }
            });
        }

        void postTuned(final Uri uri) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onTuned(SessionCallbackRecord.this.mSession, uri);
                }
            });
        }

        void postVideoAvailable() {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onVideoAvailable(SessionCallbackRecord.this.mSession);
                }
            });
        }

        void postVideoSizeChanged(final int n, final int n2) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onVideoSizeChanged(SessionCallbackRecord.this.mSession, n, n2);
                }
            });
        }

        void postVideoUnavailable(final int n) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    SessionCallbackRecord.this.mSessionCallback.onVideoUnavailable(SessionCallbackRecord.this.mSession, n);
                }
            });
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TimeShiftStatus {
    }

    public static abstract class TvInputCallback {
        public void onInputAdded(String string2) {
        }

        public void onInputRemoved(String string2) {
        }

        public void onInputStateChanged(String string2, int n) {
        }

        public void onInputUpdated(String string2) {
        }

        public void onTvInputInfoUpdated(TvInputInfo tvInputInfo) {
        }
    }

    private static final class TvInputCallbackRecord {
        private final TvInputCallback mCallback;
        private final Handler mHandler;

        public TvInputCallbackRecord(TvInputCallback tvInputCallback, Handler handler) {
            this.mCallback = tvInputCallback;
            this.mHandler = handler;
        }

        public TvInputCallback getCallback() {
            return this.mCallback;
        }

        public void postInputAdded(final String string2) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    TvInputCallbackRecord.this.mCallback.onInputAdded(string2);
                }
            });
        }

        public void postInputRemoved(final String string2) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    TvInputCallbackRecord.this.mCallback.onInputRemoved(string2);
                }
            });
        }

        public void postInputStateChanged(final String string2, final int n) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    TvInputCallbackRecord.this.mCallback.onInputStateChanged(string2, n);
                }
            });
        }

        public void postInputUpdated(final String string2) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    TvInputCallbackRecord.this.mCallback.onInputUpdated(string2);
                }
            });
        }

        public void postTvInputInfoUpdated(final TvInputInfo tvInputInfo) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    TvInputCallbackRecord.this.mCallback.onTvInputInfoUpdated(tvInputInfo);
                }
            });
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface VideoUnavailableReason {
    }

}

