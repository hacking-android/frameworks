/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  libcore.io.IoBridge
 *  libcore.io.Streams
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.AudioRouting;
import android.media.AudioSystem;
import android.media.DeniedByServerException;
import android.media.MediaDataSource;
import android.media.MediaDrm;
import android.media.MediaDrmException;
import android.media.MediaFormat;
import android.media.MediaHTTPService;
import android.media.MediaTimeProvider;
import android.media.MediaTimestamp;
import android.media.Metadata;
import android.media.NativeRoutingEventHandlerDelegate;
import android.media.NotProvisionedException;
import android.media.PlaybackParams;
import android.media.PlayerBase;
import android.media.ResourceBusyException;
import android.media.RingtoneManager;
import android.media.SRTRenderer;
import android.media.SubtitleController;
import android.media.SubtitleData;
import android.media.SubtitleTrack;
import android.media.SyncParams;
import android.media.TimedMetaData;
import android.media.TimedText;
import android.media.UnsupportedSchemeException;
import android.media.VolumeAutomation;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import libcore.io.IoBridge;
import libcore.io.Streams;

public class MediaPlayer
extends PlayerBase
implements SubtitleController.Listener,
VolumeAutomation,
AudioRouting {
    public static final boolean APPLY_METADATA_FILTER = true;
    @UnsupportedAppUsage
    public static final boolean BYPASS_METADATA_FILTER = false;
    private static final String IMEDIA_PLAYER = "android.media.IMediaPlayer";
    private static final int INVOKE_ID_ADD_EXTERNAL_SOURCE = 2;
    private static final int INVOKE_ID_ADD_EXTERNAL_SOURCE_FD = 3;
    private static final int INVOKE_ID_DESELECT_TRACK = 5;
    private static final int INVOKE_ID_GET_SELECTED_TRACK = 7;
    private static final int INVOKE_ID_GET_TRACK_INFO = 1;
    private static final int INVOKE_ID_SELECT_TRACK = 4;
    private static final int INVOKE_ID_SET_VIDEO_SCALE_MODE = 6;
    private static final int KEY_PARAMETER_AUDIO_ATTRIBUTES = 1400;
    private static final int MEDIA_AUDIO_ROUTING_CHANGED = 10000;
    private static final int MEDIA_BUFFERING_UPDATE = 3;
    private static final int MEDIA_DRM_INFO = 210;
    private static final int MEDIA_ERROR = 100;
    public static final int MEDIA_ERROR_IO = -1004;
    public static final int MEDIA_ERROR_MALFORMED = -1007;
    public static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_ERROR_SYSTEM = Integer.MIN_VALUE;
    public static final int MEDIA_ERROR_TIMED_OUT = -110;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MEDIA_ERROR_UNSUPPORTED = -1010;
    private static final int MEDIA_INFO = 200;
    public static final int MEDIA_INFO_AUDIO_NOT_PLAYING = 804;
    public static final int MEDIA_INFO_BAD_INTERLEAVING = 800;
    public static final int MEDIA_INFO_BUFFERING_END = 702;
    public static final int MEDIA_INFO_BUFFERING_START = 701;
    @UnsupportedAppUsage
    public static final int MEDIA_INFO_EXTERNAL_METADATA_UPDATE = 803;
    public static final int MEDIA_INFO_METADATA_UPDATE = 802;
    public static final int MEDIA_INFO_NETWORK_BANDWIDTH = 703;
    public static final int MEDIA_INFO_NOT_SEEKABLE = 801;
    public static final int MEDIA_INFO_STARTED_AS_NEXT = 2;
    public static final int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;
    @UnsupportedAppUsage
    public static final int MEDIA_INFO_TIMED_TEXT_ERROR = 900;
    public static final int MEDIA_INFO_UNKNOWN = 1;
    public static final int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;
    public static final int MEDIA_INFO_VIDEO_NOT_PLAYING = 805;
    public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3;
    public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;
    private static final int MEDIA_META_DATA = 202;
    public static final String MEDIA_MIMETYPE_TEXT_CEA_608 = "text/cea-608";
    public static final String MEDIA_MIMETYPE_TEXT_CEA_708 = "text/cea-708";
    public static final String MEDIA_MIMETYPE_TEXT_SUBRIP = "application/x-subrip";
    public static final String MEDIA_MIMETYPE_TEXT_VTT = "text/vtt";
    private static final int MEDIA_NOP = 0;
    private static final int MEDIA_NOTIFY_TIME = 98;
    private static final int MEDIA_PAUSED = 7;
    private static final int MEDIA_PLAYBACK_COMPLETE = 2;
    private static final int MEDIA_PREPARED = 1;
    private static final int MEDIA_SEEK_COMPLETE = 4;
    private static final int MEDIA_SET_VIDEO_SIZE = 5;
    private static final int MEDIA_SKIPPED = 9;
    private static final int MEDIA_STARTED = 6;
    private static final int MEDIA_STOPPED = 8;
    private static final int MEDIA_SUBTITLE_DATA = 201;
    private static final int MEDIA_TIMED_TEXT = 99;
    private static final int MEDIA_TIME_DISCONTINUITY = 211;
    @UnsupportedAppUsage
    public static final boolean METADATA_ALL = false;
    public static final boolean METADATA_UPDATE_ONLY = true;
    public static final int PLAYBACK_RATE_AUDIO_MODE_DEFAULT = 0;
    public static final int PLAYBACK_RATE_AUDIO_MODE_RESAMPLE = 2;
    public static final int PLAYBACK_RATE_AUDIO_MODE_STRETCH = 1;
    public static final int PREPARE_DRM_STATUS_PREPARATION_ERROR = 3;
    public static final int PREPARE_DRM_STATUS_PROVISIONING_NETWORK_ERROR = 1;
    public static final int PREPARE_DRM_STATUS_PROVISIONING_SERVER_ERROR = 2;
    public static final int PREPARE_DRM_STATUS_SUCCESS = 0;
    public static final int SEEK_CLOSEST = 3;
    public static final int SEEK_CLOSEST_SYNC = 2;
    public static final int SEEK_NEXT_SYNC = 1;
    public static final int SEEK_PREVIOUS_SYNC = 0;
    private static final String TAG = "MediaPlayer";
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT = 1;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING = 2;
    private boolean mActiveDrmScheme;
    private boolean mDrmConfigAllowed;
    private DrmInfo mDrmInfo;
    private boolean mDrmInfoResolved;
    private final Object mDrmLock = new Object();
    private MediaDrm mDrmObj;
    private boolean mDrmProvisioningInProgress;
    private ProvisioningThread mDrmProvisioningThread;
    private byte[] mDrmSessionId;
    private UUID mDrmUUID;
    @UnsupportedAppUsage
    private EventHandler mEventHandler;
    private Handler mExtSubtitleDataHandler;
    private OnSubtitleDataListener mExtSubtitleDataListener;
    private BitSet mInbandTrackIndices = new BitSet();
    private Vector<Pair<Integer, SubtitleTrack>> mIndexTrackPairs = new Vector();
    private final OnSubtitleDataListener mIntSubtitleDataListener = new OnSubtitleDataListener(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onSubtitleData(MediaPlayer object, SubtitleData subtitleData) {
            int n = subtitleData.getTrackIndex();
            object = MediaPlayer.this.mIndexTrackPairs;
            synchronized (object) {
                Iterator iterator = MediaPlayer.this.mIndexTrackPairs.iterator();
                while (iterator.hasNext()) {
                    Pair pair = (Pair)iterator.next();
                    if (pair.first == null || (Integer)pair.first != n || pair.second == null) continue;
                    ((SubtitleTrack)pair.second).onData(subtitleData);
                }
                return;
            }
        }
    };
    private int mListenerContext;
    private long mNativeContext;
    private long mNativeSurfaceTexture;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private final OnCompletionListener mOnCompletionInternalListener = new OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            MediaPlayer.this.baseStop();
        }
    };
    @UnsupportedAppUsage
    private OnCompletionListener mOnCompletionListener;
    private OnDrmConfigHelper mOnDrmConfigHelper;
    private OnDrmInfoHandlerDelegate mOnDrmInfoHandlerDelegate;
    private OnDrmPreparedHandlerDelegate mOnDrmPreparedHandlerDelegate;
    @UnsupportedAppUsage
    private OnErrorListener mOnErrorListener;
    @UnsupportedAppUsage
    private OnInfoListener mOnInfoListener;
    private Handler mOnMediaTimeDiscontinuityHandler;
    private OnMediaTimeDiscontinuityListener mOnMediaTimeDiscontinuityListener;
    @UnsupportedAppUsage
    private OnPreparedListener mOnPreparedListener;
    @UnsupportedAppUsage
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnTimedMetaDataAvailableListener mOnTimedMetaDataAvailableListener;
    @UnsupportedAppUsage
    private OnTimedTextListener mOnTimedTextListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private Vector<InputStream> mOpenSubtitleSources;
    private AudioDeviceInfo mPreferredDevice = null;
    private boolean mPrepareDrmInProgress;
    @GuardedBy(value={"mRoutingChangeListeners"})
    private ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> mRoutingChangeListeners = new ArrayMap();
    private boolean mScreenOnWhilePlaying;
    private int mSelectedSubtitleTrackIndex = -1;
    private boolean mStayAwake;
    private int mStreamType = Integer.MIN_VALUE;
    private SubtitleController mSubtitleController;
    private boolean mSubtitleDataListenerDisabled;
    private SurfaceHolder mSurfaceHolder;
    private TimeProvider mTimeProvider;
    private final Object mTimeProviderLock = new Object();
    private int mUsage = -1;
    private PowerManager.WakeLock mWakeLock = null;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static {
        System.loadLibrary("media_jni");
        MediaPlayer.native_init();
    }

    public MediaPlayer() {
        super(new AudioAttributes.Builder().build(), 2);
        Looper looper = Looper.myLooper();
        this.mEventHandler = looper != null ? new EventHandler(this, looper) : ((looper = Looper.getMainLooper()) != null ? new EventHandler(this, looper) : null);
        this.mTimeProvider = new TimeProvider(this);
        this.mOpenSubtitleSources = new Vector();
        this.native_setup(new WeakReference<MediaPlayer>(this));
        this.baseRegisterPlayer();
    }

    private int HandleProvisioninig(UUID serializable) {
        int n;
        if (this.mDrmProvisioningInProgress) {
            Log.e(TAG, "HandleProvisioninig: Unexpected mDrmProvisioningInProgress");
            return 3;
        }
        MediaDrm.ProvisionRequest provisionRequest = this.mDrmObj.getProvisionRequest();
        if (provisionRequest == null) {
            Log.e(TAG, "HandleProvisioninig: getProvisionRequest returned null.");
            return 3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HandleProvisioninig provReq  data: ");
        stringBuilder.append(provisionRequest.getData());
        stringBuilder.append(" url: ");
        stringBuilder.append(provisionRequest.getDefaultUrl());
        Log.v(TAG, stringBuilder.toString());
        this.mDrmProvisioningInProgress = true;
        this.mDrmProvisioningThread = new ProvisioningThread().initialize(provisionRequest, (UUID)serializable, this);
        this.mDrmProvisioningThread.start();
        if (this.mOnDrmPreparedHandlerDelegate != null) {
            n = 0;
        } else {
            try {
                this.mDrmProvisioningThread.join();
            }
            catch (Exception exception) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("HandleProvisioninig: Thread.join Exception ");
                ((StringBuilder)serializable).append(exception);
                Log.w(TAG, ((StringBuilder)serializable).toString());
            }
            n = this.mDrmProvisioningThread.status();
            this.mDrmProvisioningThread = null;
        }
        return n;
    }

    private native int _getAudioStreamType() throws IllegalStateException;

    private native void _notifyAt(long var1);

    private native void _pause() throws IllegalStateException;

    private native void _prepare() throws IOException, IllegalStateException;

    private native void _prepareDrm(byte[] var1, byte[] var2);

    private native void _release();

    private native void _releaseDrm();

    private native void _reset();

    private final native void _seekTo(long var1, int var3);

    private native void _setAudioStreamType(int var1);

    private native void _setAuxEffectSendLevel(float var1);

    private native void _setDataSource(MediaDataSource var1) throws IllegalArgumentException, IllegalStateException;

    private native void _setDataSource(FileDescriptor var1, long var2, long var4) throws IOException, IllegalArgumentException, IllegalStateException;

    private native void _setVideoSurface(Surface var1);

    private native void _setVolume(float var1, float var2);

    private native void _start() throws IllegalStateException;

    private native void _stop() throws IllegalStateException;

    static /* synthetic */ OnPreparedListener access$1000(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnPreparedListener;
    }

    static /* synthetic */ OnDrmInfoHandlerDelegate access$1100(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnDrmInfoHandlerDelegate;
    }

    static /* synthetic */ DrmInfo access$1300(MediaPlayer mediaPlayer) {
        return mediaPlayer.mDrmInfo;
    }

    static /* synthetic */ OnCompletionListener access$1500(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnCompletionInternalListener;
    }

    static /* synthetic */ OnCompletionListener access$1600(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnCompletionListener;
    }

    static /* synthetic */ void access$1700(MediaPlayer mediaPlayer, boolean bl) {
        mediaPlayer.stayAwake(bl);
    }

    static /* synthetic */ OnBufferingUpdateListener access$1800(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnBufferingUpdateListener;
    }

    static /* synthetic */ OnSeekCompleteListener access$1900(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnSeekCompleteListener;
    }

    static /* synthetic */ OnVideoSizeChangedListener access$2000(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnVideoSizeChangedListener;
    }

    static /* synthetic */ OnErrorListener access$2100(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnErrorListener;
    }

    static /* synthetic */ OnInfoListener access$2200(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnInfoListener;
    }

    static /* synthetic */ OnTimedTextListener access$2300(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnTimedTextListener;
    }

    static /* synthetic */ boolean access$2400(MediaPlayer mediaPlayer) {
        return mediaPlayer.mSubtitleDataListenerDisabled;
    }

    static /* synthetic */ OnSubtitleDataListener access$2500(MediaPlayer mediaPlayer) {
        return mediaPlayer.mExtSubtitleDataListener;
    }

    static /* synthetic */ Handler access$2600(MediaPlayer mediaPlayer) {
        return mediaPlayer.mExtSubtitleDataHandler;
    }

    static /* synthetic */ OnSubtitleDataListener access$2700(MediaPlayer mediaPlayer) {
        return mediaPlayer.mIntSubtitleDataListener;
    }

    static /* synthetic */ OnTimedMetaDataAvailableListener access$2900(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnTimedMetaDataAvailableListener;
    }

    static /* synthetic */ ArrayMap access$3000(MediaPlayer mediaPlayer) {
        return mediaPlayer.mRoutingChangeListeners;
    }

    static /* synthetic */ OnMediaTimeDiscontinuityListener access$3100(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnMediaTimeDiscontinuityListener;
    }

    static /* synthetic */ Handler access$3200(MediaPlayer mediaPlayer) {
        return mediaPlayer.mOnMediaTimeDiscontinuityHandler;
    }

    static /* synthetic */ MediaDrm access$3900(MediaPlayer mediaPlayer) {
        return mediaPlayer.mDrmObj;
    }

    static /* synthetic */ boolean access$4000(MediaPlayer mediaPlayer, UUID uUID) {
        return mediaPlayer.resumePrepareDrm(uUID);
    }

    static /* synthetic */ boolean access$4102(MediaPlayer mediaPlayer, boolean bl) {
        mediaPlayer.mDrmProvisioningInProgress = bl;
        return bl;
    }

    static /* synthetic */ boolean access$4202(MediaPlayer mediaPlayer, boolean bl) {
        mediaPlayer.mPrepareDrmInProgress = bl;
        return bl;
    }

    static /* synthetic */ void access$4300(MediaPlayer mediaPlayer) {
        mediaPlayer.cleanDrmObj();
    }

    static /* synthetic */ long access$800(MediaPlayer mediaPlayer) {
        return mediaPlayer.mNativeContext;
    }

    static /* synthetic */ void access$900(MediaPlayer mediaPlayer) {
        mediaPlayer.scanInternalSubtitleTracks();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean attemptDataSource(ContentResolver object, Uri uri) {
        AssetFileDescriptor assetFileDescriptor = ((ContentResolver)object).openAssetFileDescriptor(uri, "r");
        try {
            this.setDataSource(assetFileDescriptor);
            if (assetFileDescriptor == null) return true;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (assetFileDescriptor == null) throw throwable2;
                try {
                    MediaPlayer.$closeResource(throwable, assetFileDescriptor);
                    throw throwable2;
                }
                catch (IOException | NullPointerException | SecurityException exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Couldn't open ");
                    object = uri == null ? "null uri" : uri.toSafeString();
                    stringBuilder.append((String)object);
                    Log.w("MediaPlayer", stringBuilder.toString(), exception);
                    return false;
                }
            }
        }
        MediaPlayer.$closeResource(null, assetFileDescriptor);
        return true;
    }

    private static boolean availableMimeTypeForExternalSource(String string2) {
        return "application/x-subrip".equals(string2);
    }

    private void cleanDrmObj() {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("cleanDrmObj: mDrmObj=");
        ((StringBuilder)object).append(this.mDrmObj);
        ((StringBuilder)object).append(" mDrmSessionId=");
        ((StringBuilder)object).append(this.mDrmSessionId);
        Log.v("MediaPlayer", ((StringBuilder)object).toString());
        object = this.mDrmSessionId;
        if (object != null) {
            this.mDrmObj.closeSession((byte[])object);
            this.mDrmSessionId = null;
        }
        if ((object = this.mDrmObj) != null) {
            ((MediaDrm)object).release();
            this.mDrmObj = null;
        }
    }

    public static MediaPlayer create(Context context, int n) {
        int n2 = AudioSystem.newAudioSessionId();
        if (n2 <= 0) {
            n2 = 0;
        }
        return MediaPlayer.create(context, n, null, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static MediaPlayer create(Context object, int n, AudioAttributes audioAttributes, int n2) {
        try {
            AssetFileDescriptor assetFileDescriptor = ((Context)object).getResources().openRawResourceFd(n);
            if (assetFileDescriptor == null) {
                return null;
            }
            MediaPlayer mediaPlayer = new MediaPlayer();
            if (audioAttributes != null) {
                object = audioAttributes;
            } else {
                object = new AudioAttributes.Builder();
                object = ((AudioAttributes.Builder)object).build();
            }
            mediaPlayer.setAudioAttributes((AudioAttributes)object);
            mediaPlayer.setAudioSessionId(n2);
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            assetFileDescriptor.close();
            mediaPlayer.prepare();
            return mediaPlayer;
        }
        catch (SecurityException securityException) {
            Log.d("MediaPlayer", "create failed:", securityException);
            return null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.d("MediaPlayer", "create failed:", illegalArgumentException);
            return null;
        }
        catch (IOException iOException) {
            Log.d("MediaPlayer", "create failed:", iOException);
        }
        return null;
    }

    public static MediaPlayer create(Context context, Uri uri) {
        return MediaPlayer.create(context, uri, null);
    }

    public static MediaPlayer create(Context context, Uri uri, SurfaceHolder surfaceHolder) {
        int n = AudioSystem.newAudioSessionId();
        if (n <= 0) {
            n = 0;
        }
        return MediaPlayer.create(context, uri, surfaceHolder, null, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static MediaPlayer create(Context context, Uri uri, SurfaceHolder surfaceHolder, AudioAttributes object, int n) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            if (object == null) {
                object = new AudioAttributes.Builder();
                object = ((AudioAttributes.Builder)object).build();
            }
            mediaPlayer.setAudioAttributes((AudioAttributes)object);
            mediaPlayer.setAudioSessionId(n);
            mediaPlayer.setDataSource(context, uri);
            if (surfaceHolder != null) {
                mediaPlayer.setDisplay(surfaceHolder);
            }
            mediaPlayer.prepare();
            return mediaPlayer;
        }
        catch (SecurityException securityException) {
            Log.d("MediaPlayer", "create failed:", securityException);
            return null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.d("MediaPlayer", "create failed:", illegalArgumentException);
            return null;
        }
        catch (IOException iOException) {
            Log.d("MediaPlayer", "create failed:", iOException);
        }
        return null;
    }

    @GuardedBy(value={"mRoutingChangeListeners"})
    private void enableNativeRoutingCallbacksLocked(boolean bl) {
        if (this.mRoutingChangeListeners.size() == 0) {
            this.native_enableDeviceCallback(bl);
        }
    }

    private int getAudioStreamType() {
        if (this.mStreamType == Integer.MIN_VALUE) {
            this.mStreamType = this._getAudioStreamType();
        }
        return this.mStreamType;
    }

    private static final byte[] getByteArrayFromUUID(UUID arrby) {
        long l = arrby.getMostSignificantBits();
        long l2 = arrby.getLeastSignificantBits();
        arrby = new byte[16];
        for (int i = 0; i < 8; ++i) {
            arrby[i] = (byte)(l >>> (7 - i) * 8);
            arrby[i + 8] = (byte)(l2 >>> (7 - i) * 8);
        }
        return arrby;
    }

    private TrackInfo[] getInbandTrackInfo() throws IllegalStateException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
            parcel.writeInterfaceToken("android.media.IMediaPlayer");
            parcel.writeInt(1);
            this.invoke(parcel, parcel2);
            TrackInfo[] arrtrackInfo = parcel2.createTypedArray(TrackInfo.CREATOR);
            return arrtrackInfo;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    private boolean isVideoScalingModeSupported(int n) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = n == 2 ? bl : false;
        }
        return bl2;
    }

    private native void nativeSetDataSource(IBinder var1, String var2, String[] var3, String[] var4) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    private native int native_applyVolumeShaper(VolumeShaper.Configuration var1, VolumeShaper.Operation var2);

    private final native void native_enableDeviceCallback(boolean var1);

    private final native void native_finalize();

    private final native boolean native_getMetadata(boolean var1, boolean var2, Parcel var3);

    private native PersistableBundle native_getMetrics();

    private final native int native_getRoutedDeviceId();

    private native VolumeShaper.State native_getVolumeShaperState(int var1);

    private static final native void native_init();

    private final native int native_invoke(Parcel var1, Parcel var2);

    public static native int native_pullBatteryData(Parcel var0);

    private final native int native_setMetadataFilter(Parcel var1);

    private final native boolean native_setOutputDevice(int var1);

    private final native int native_setRetransmitEndpoint(String var1, int var2);

    private final native void native_setup(Object var1);

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void populateInbandTracks() {
        TrackInfo[] arrtrackInfo = this.getInbandTrackInfo();
        Vector<Pair<Integer, SubtitleTrack>> vector = this.mIndexTrackPairs;
        synchronized (vector) {
            int n = 0;
            while (n < arrtrackInfo.length) {
                if (!this.mInbandTrackIndices.get(n)) {
                    Object object;
                    this.mInbandTrackIndices.set(n);
                    if (arrtrackInfo[n] == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("unexpected NULL track at index ");
                        ((StringBuilder)object).append(n);
                        Log.w("MediaPlayer", ((StringBuilder)object).toString());
                    }
                    if (arrtrackInfo[n] != null && arrtrackInfo[n].getTrackType() == 4) {
                        object = this.mSubtitleController.addTrack(arrtrackInfo[n].getFormat());
                        this.mIndexTrackPairs.add(Pair.create(n, object));
                    } else {
                        this.mIndexTrackPairs.add(Pair.create(n, null));
                    }
                }
                ++n;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void postEventFromNative(Object object, int n, int n2, int n3, Object object2) {
        Object object3;
        if ((object = (MediaPlayer)((WeakReference)object).get()) == null) {
            return;
        }
        if (n != 1) {
            if (n != 200) {
                if (n == 210) {
                    Log.v("MediaPlayer", "postEventFromNative MEDIA_DRM_INFO");
                    if (object2 instanceof Parcel) {
                        DrmInfo drmInfo = new DrmInfo((Parcel)object2);
                        object3 = ((MediaPlayer)object).mDrmLock;
                        synchronized (object3) {
                            ((MediaPlayer)object).mDrmInfo = drmInfo;
                        }
                    } else {
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("MEDIA_DRM_INFO msg.obj of unexpected type ");
                        ((StringBuilder)object3).append(object2);
                        Log.w("MediaPlayer", ((StringBuilder)object3).toString());
                    }
                }
            } else if (n2 == 2) {
                new Thread(new Runnable(){

                    @Override
                    public void run() {
                        MediaPlayer.this.start();
                    }
                }).start();
                Thread.yield();
            }
        } else {
            object3 = ((MediaPlayer)object).mDrmLock;
            synchronized (object3) {
                ((MediaPlayer)object).mDrmInfoResolved = true;
            }
        }
        object3 = ((MediaPlayer)object).mEventHandler;
        if (object3 != null) {
            object2 = ((Handler)object3).obtainMessage(n, n2, n3, object2);
            ((MediaPlayer)object).mEventHandler.sendMessage((Message)object2);
        }
    }

    private void prepareDrm_createDrmStep(UUID serializable) throws UnsupportedSchemeException {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("prepareDrm_createDrmStep: UUID: ");
        ((StringBuilder)object).append(serializable);
        Log.v("MediaPlayer", ((StringBuilder)object).toString());
        try {
            this.mDrmObj = object = new MediaDrm((UUID)serializable);
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("prepareDrm_createDrmStep: Created mDrmObj=");
            ((StringBuilder)serializable).append(this.mDrmObj);
            Log.v("MediaPlayer", ((StringBuilder)serializable).toString());
            return;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("prepareDrm_createDrmStep: MediaDrm failed with ");
            ((StringBuilder)object).append(exception);
            Log.e("MediaPlayer", ((StringBuilder)object).toString());
            throw exception;
        }
    }

    private void prepareDrm_openSessionStep(UUID uUID) throws NotProvisionedException, ResourceBusyException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("prepareDrm_openSessionStep: uuid: ");
        stringBuilder.append(uUID);
        Log.v("MediaPlayer", stringBuilder.toString());
        try {
            this.mDrmSessionId = this.mDrmObj.openSession();
            stringBuilder = new StringBuilder();
            stringBuilder.append("prepareDrm_openSessionStep: mDrmSessionId=");
            stringBuilder.append(this.mDrmSessionId);
            Log.v("MediaPlayer", stringBuilder.toString());
            this._prepareDrm(MediaPlayer.getByteArrayFromUUID(uUID), this.mDrmSessionId);
            Log.v("MediaPlayer", "prepareDrm_openSessionStep: _prepareDrm/Crypto succeeded");
            return;
        }
        catch (Exception exception) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("prepareDrm_openSessionStep: open/crypto failed with ");
            stringBuilder.append(exception);
            Log.e("MediaPlayer", stringBuilder.toString());
            throw exception;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void resetDrmState() {
        Object object = this.mDrmLock;
        synchronized (object) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("resetDrmState:  mDrmInfo=");
            ((StringBuilder)object2).append(this.mDrmInfo);
            ((StringBuilder)object2).append(" mDrmProvisioningThread=");
            ((StringBuilder)object2).append(this.mDrmProvisioningThread);
            ((StringBuilder)object2).append(" mPrepareDrmInProgress=");
            ((StringBuilder)object2).append(this.mPrepareDrmInProgress);
            ((StringBuilder)object2).append(" mActiveDrmScheme=");
            ((StringBuilder)object2).append(this.mActiveDrmScheme);
            Log.v("MediaPlayer", ((StringBuilder)object2).toString());
            this.mDrmInfoResolved = false;
            this.mDrmInfo = null;
            object2 = this.mDrmProvisioningThread;
            if (object2 != null) {
                try {
                    this.mDrmProvisioningThread.join();
                }
                catch (InterruptedException interruptedException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("resetDrmState: ProvThread.join Exception ");
                    stringBuilder.append(interruptedException);
                    Log.w("MediaPlayer", stringBuilder.toString());
                }
                this.mDrmProvisioningThread = null;
            }
            this.mPrepareDrmInProgress = false;
            this.mActiveDrmScheme = false;
            this.cleanDrmObj();
            return;
        }
    }

    private boolean resumePrepareDrm(UUID uUID) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("resumePrepareDrm: uuid: ");
        stringBuilder.append(uUID);
        Log.v("MediaPlayer", stringBuilder.toString());
        boolean bl = false;
        try {
            this.prepareDrm_openSessionStep(uUID);
            this.mDrmUUID = uUID;
            this.mActiveDrmScheme = true;
            bl = true;
        }
        catch (Exception exception) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("HandleProvisioninig: Thread run _prepareDrm resume failed with ");
            stringBuilder.append(exception);
            Log.w("MediaPlayer", stringBuilder.toString());
        }
        return bl;
    }

    private void scanInternalSubtitleTracks() {
        this.setSubtitleAnchor();
        this.populateInbandTracks();
        SubtitleController subtitleController = this.mSubtitleController;
        if (subtitleController != null) {
            subtitleController.selectDefaultTrack();
        }
    }

    private void selectOrDeselectInbandTrack(int n, boolean bl) throws IllegalStateException {
        Parcel parcel = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        parcel.writeInterfaceToken("android.media.IMediaPlayer");
        int n2 = bl ? 4 : 5;
        try {
            parcel.writeInt(n2);
            parcel.writeInt(n);
            this.invoke(parcel, parcel2);
            return;
        }
        finally {
            parcel.recycle();
            parcel2.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void selectOrDeselectTrack(int n, boolean bl) throws IllegalStateException {
        Object object;
        this.populateInbandTracks();
        try {
            object = this.mIndexTrackPairs.get(n);
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return;
        }
        SubtitleTrack subtitleTrack = (SubtitleTrack)((Pair)object).second;
        if (subtitleTrack == null) {
            this.selectOrDeselectInbandTrack((Integer)((Pair)object).first, bl);
            return;
        }
        object = this.mSubtitleController;
        if (object == null) {
            return;
        }
        if (!bl) {
            if (((SubtitleController)object).getSelectedTrack() == subtitleTrack) {
                this.mSubtitleController.selectTrack(null);
                return;
            }
            Log.w("MediaPlayer", "trying to deselect track that was not selected");
            return;
        }
        if (subtitleTrack.getTrackType() == 3) {
            n = this.getSelectedTrack(3);
            object = this.mIndexTrackPairs;
            synchronized (object) {
                if (n >= 0 && n < this.mIndexTrackPairs.size()) {
                    Pair<Integer, SubtitleTrack> pair = this.mIndexTrackPairs.get(n);
                    if (pair.first != null && pair.second == null) {
                        this.selectOrDeselectInbandTrack((Integer)pair.first, false);
                    }
                }
            }
        }
        this.mSubtitleController.selectTrack(subtitleTrack);
    }

    @UnsupportedAppUsage
    private void setDataSource(String string2, Map<String, String> object, List<HttpCookie> list) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        Object object2 = null;
        String[] arrstring = null;
        if (object != null) {
            String[] arrstring2 = new String[object.size()];
            String[] arrstring3 = new String[object.size()];
            int n = 0;
            object = object.entrySet().iterator();
            do {
                object2 = arrstring2;
                arrstring = arrstring3;
                if (!object.hasNext()) break;
                object2 = (Map.Entry)object.next();
                arrstring2[n] = (String)object2.getKey();
                arrstring3[n] = (String)object2.getValue();
                ++n;
            } while (true);
        }
        this.setDataSource(string2, (String[])object2, arrstring, list);
    }

    @UnsupportedAppUsage
    private void setDataSource(String string2, String[] arrstring, String[] object, List<HttpCookie> list) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        Object object2 = Uri.parse(string2);
        String string3 = ((Uri)object2).getScheme();
        if ("file".equals(string3)) {
            object2 = ((Uri)object2).getPath();
        } else {
            object2 = string2;
            if (string3 != null) {
                this.nativeSetDataSource(MediaHTTPService.createHttpServiceBinderIfNecessary(string2, list), string2, arrstring, (String[])object);
                return;
            }
        }
        object = new FileInputStream(new File((String)object2));
        try {
            this.setDataSource(((FileInputStream)object).getFD());
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                MediaPlayer.$closeResource(throwable, (AutoCloseable)object);
                throw throwable2;
            }
        }
        MediaPlayer.$closeResource(null, (AutoCloseable)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setOnMediaTimeDiscontinuityListenerInt(OnMediaTimeDiscontinuityListener onMediaTimeDiscontinuityListener, Handler handler) {
        synchronized (this) {
            this.mOnMediaTimeDiscontinuityListener = onMediaTimeDiscontinuityListener;
            this.mOnMediaTimeDiscontinuityHandler = handler;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setOnSubtitleDataListenerInt(OnSubtitleDataListener onSubtitleDataListener, Handler handler) {
        synchronized (this) {
            this.mExtSubtitleDataListener = onSubtitleDataListener;
            this.mExtSubtitleDataHandler = handler;
            return;
        }
    }

    @UnsupportedAppUsage
    private native boolean setParameter(int var1, Parcel var2);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void setSubtitleAnchor() {
        synchronized (this) {
            if (this.mSubtitleController != null || ActivityThread.currentApplication() == null) return;
            final TimeProvider timeProvider = (TimeProvider)this.getMediaTimeProvider();
            final HandlerThread handlerThread = new HandlerThread("SetSubtitleAnchorThread");
            handlerThread.start();
            Handler handler = new Handler(handlerThread.getLooper());
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    Application application = ActivityThread.currentApplication();
                    MediaPlayer mediaPlayer = MediaPlayer.this;
                    mediaPlayer.mSubtitleController = new SubtitleController(application, timeProvider, mediaPlayer);
                    MediaPlayer.this.mSubtitleController.setAnchor(new SubtitleController.Anchor(){

                        @Override
                        public Looper getSubtitleLooper() {
                            return timeProvider.mEventHandler.getLooper();
                        }

                        @Override
                        public void setSubtitleWidget(SubtitleTrack.RenderingWidget renderingWidget) {
                        }
                    });
                    handlerThread.getLooper().quitSafely();
                }

            };
            handler.post(runnable);
            try {
                handlerThread.join();
                return;
            }
            catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                Log.w("MediaPlayer", "failed to join SetSubtitleAnchorThread");
                return;
            }
        }
    }

    private void startImpl() {
        this.baseStart();
        this.stayAwake(true);
        this._start();
    }

    private void stayAwake(boolean bl) {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            if (bl && !wakeLock.isHeld()) {
                this.mWakeLock.acquire();
            } else if (!bl && this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
        }
        this.mStayAwake = bl;
        this.updateSurfaceScreenOn();
    }

    private void updateSurfaceScreenOn() {
        SurfaceHolder surfaceHolder = this.mSurfaceHolder;
        if (surfaceHolder != null) {
            boolean bl = this.mScreenOnWhilePlaying && this.mStayAwake;
            surfaceHolder.setKeepScreenOn(bl);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addOnRoutingChangedListener(AudioRouting.OnRoutingChangedListener onRoutingChangedListener, Handler handler) {
        ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> arrayMap = this.mRoutingChangeListeners;
        synchronized (arrayMap) {
            if (onRoutingChangedListener != null && !this.mRoutingChangeListeners.containsKey(onRoutingChangedListener)) {
                this.enableNativeRoutingCallbacksLocked(true);
                ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> arrayMap2 = this.mRoutingChangeListeners;
                if (handler == null) {
                    handler = this.mEventHandler;
                }
                NativeRoutingEventHandlerDelegate nativeRoutingEventHandlerDelegate = new NativeRoutingEventHandlerDelegate(this, onRoutingChangedListener, handler);
                arrayMap2.put(onRoutingChangedListener, nativeRoutingEventHandlerDelegate);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void addSubtitleSource(final InputStream inputStream, final MediaFormat mediaFormat) throws IllegalStateException {
        Object object;
        if (inputStream != null) {
            object = this.mOpenSubtitleSources;
            synchronized (object) {
                this.mOpenSubtitleSources.add(inputStream);
            }
        } else {
            Log.w("MediaPlayer", "addSubtitleSource called with null InputStream");
        }
        this.getMediaTimeProvider();
        object = new HandlerThread("SubtitleReadThread", 9);
        ((Thread)object).start();
        new Handler(((HandlerThread)object).getLooper()).post(new Runnable((HandlerThread)object){
            final /* synthetic */ HandlerThread val$thread;
            {
                this.val$thread = handlerThread;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            private int addTrack() {
                if (inputStream == null) return 901;
                if (MediaPlayer.this.mSubtitleController == null) {
                    return 901;
                }
                SubtitleTrack subtitleTrack = MediaPlayer.this.mSubtitleController.addTrack(mediaFormat);
                if (subtitleTrack == null) {
                    return 901;
                }
                Object object = new Scanner(inputStream, "UTF-8");
                String string2 = ((Scanner)object).useDelimiter("\\A").next();
                Object object2 = MediaPlayer.this.mOpenSubtitleSources;
                synchronized (object2) {
                    MediaPlayer.this.mOpenSubtitleSources.remove(inputStream);
                }
                ((Scanner)object).close();
                object2 = MediaPlayer.this.mIndexTrackPairs;
                synchronized (object2) {
                    MediaPlayer.this.mIndexTrackPairs.add(Pair.create(null, subtitleTrack));
                }
                object2 = MediaPlayer.this.mTimeProviderLock;
                synchronized (object2) {
                    if (MediaPlayer.this.mTimeProvider == null) return 803;
                    object = MediaPlayer.this.mTimeProvider.mEventHandler;
                    ((Handler)object).sendMessage(((Handler)object).obtainMessage(1, 4, 0, Pair.create(subtitleTrack, string2.getBytes())));
                    return 803;
                }
            }

            @Override
            public void run() {
                int n = this.addTrack();
                if (MediaPlayer.this.mEventHandler != null) {
                    Message message = MediaPlayer.this.mEventHandler.obtainMessage(200, n, 0, null);
                    MediaPlayer.this.mEventHandler.sendMessage(message);
                }
                this.val$thread.getLooper().quitSafely();
            }
        });
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void addTimedTextSource(Context object, Uri uri, String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        block6 : {
            Object object3;
            Object object2;
            Object object4;
            block5 : {
                object4 = uri.getScheme();
                if (object4 == null || ((String)object4).equals("file")) break block6;
                object4 = null;
                object2 = null;
                object3 = null;
                object = ((Context)object).getContentResolver().openAssetFileDescriptor(uri, "r");
                if (object != null) break block5;
                if (object == null) return;
                ((AssetFileDescriptor)object).close();
                return;
            }
            object3 = object;
            object4 = object;
            object2 = object;
            try {
                this.addTimedTextSource(((AssetFileDescriptor)object).getFileDescriptor(), string2);
            }
            catch (Throwable throwable) {
                if (object3 == null) throw throwable;
                ((AssetFileDescriptor)object3).close();
                throw throwable;
            }
            catch (IOException iOException) {
                block7 : {
                    if (object4 == null) return;
                    break block7;
                    catch (SecurityException securityException) {
                        if (object2 == null) return;
                        object4 = object2;
                    }
                }
                ((AssetFileDescriptor)object4).close();
                return;
            }
            ((AssetFileDescriptor)object).close();
            return;
        }
        this.addTimedTextSource(uri.getPath(), string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addTimedTextSource(FileDescriptor object, long l, long l2, String object2) throws IllegalArgumentException, IllegalStateException {
        Object object3;
        block7 : {
            if (!MediaPlayer.availableMimeTypeForExternalSource((String)object2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Illegal mimeType for timed text source: ");
                ((StringBuilder)object).append((String)object2);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            try {
                object = Os.dup((FileDescriptor)object);
                object3 = new MediaFormat();
                ((MediaFormat)object3).setString("mime", (String)object2);
                ((MediaFormat)object3).setInteger("is-timed-text", 1);
                if (this.mSubtitleController == null) {
                    this.setSubtitleAnchor();
                }
                if (this.mSubtitleController.hasRendererFor((MediaFormat)object3)) break block7;
            }
            catch (ErrnoException errnoException) {
                Log.e("MediaPlayer", errnoException.getMessage(), errnoException);
                throw new RuntimeException(errnoException);
            }
            object2 = ActivityThread.currentApplication();
            this.mSubtitleController.registerRenderer(new SRTRenderer((Context)object2, this.mEventHandler));
        }
        object2 = this.mSubtitleController.addTrack((MediaFormat)object3);
        object3 = this.mIndexTrackPairs;
        synchronized (object3) {
            this.mIndexTrackPairs.add(Pair.create(null, object2));
        }
        this.getMediaTimeProvider();
        object3 = new HandlerThread("TimedTextReadThread", 9);
        ((Thread)object3).start();
        new Handler(((HandlerThread)object3).getLooper()).post(new Runnable((FileDescriptor)object, l, l2, (SubtitleTrack)object2, (HandlerThread)object3){
            final /* synthetic */ FileDescriptor val$dupedFd;
            final /* synthetic */ long val$length2;
            final /* synthetic */ long val$offset2;
            final /* synthetic */ HandlerThread val$thread;
            final /* synthetic */ SubtitleTrack val$track;
            {
                this.val$dupedFd = fileDescriptor;
                this.val$offset2 = l;
                this.val$length2 = l2;
                this.val$track = subtitleTrack;
                this.val$thread = handlerThread;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            private int addTrack() {
                ByteArrayOutputStream byteArrayOutputStream;
                int n;
                Object object;
                block16 : {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        Os.lseek((FileDescriptor)this.val$dupedFd, (long)this.val$offset2, (int)OsConstants.SEEK_SET);
                        object = new byte[4096];
                        break block16;
                    }
                    catch (Throwable throwable) {
                    }
                    catch (Exception exception) {
                        Log.e(MediaPlayer.TAG, exception.getMessage(), exception);
                        try {
                            Os.close((FileDescriptor)this.val$dupedFd);
                            return 900;
                        }
                        catch (ErrnoException errnoException) {
                            Log.e(MediaPlayer.TAG, errnoException.getMessage(), errnoException);
                        }
                        return 900;
                    }
                    try {
                        Os.close((FileDescriptor)this.val$dupedFd);
                        throw throwable;
                    }
                    catch (ErrnoException errnoException) {
                        Log.e(MediaPlayer.TAG, errnoException.getMessage(), errnoException);
                    }
                    throw throwable;
                }
                for (long i = 0L; i < this.val$length2; i += (long)n) {
                    n = (int)Math.min((long)((byte[])object).length, this.val$length2 - i);
                    if ((n = IoBridge.read((FileDescriptor)this.val$dupedFd, (byte[])object, (int)0, (int)n)) < 0) break;
                    byteArrayOutputStream.write((byte[])object, 0, n);
                }
                object = MediaPlayer.this.mTimeProviderLock;
                synchronized (object) {
                    if (MediaPlayer.this.mTimeProvider != null) {
                        Handler handler = MediaPlayer.this.mTimeProvider.mEventHandler;
                        handler.sendMessage(handler.obtainMessage(1, 4, 0, Pair.create(this.val$track, byteArrayOutputStream.toByteArray())));
                    }
                }
                try {
                    Os.close((FileDescriptor)this.val$dupedFd);
                    return 803;
                }
                catch (ErrnoException errnoException) {
                    Log.e(MediaPlayer.TAG, errnoException.getMessage(), errnoException);
                }
                return 803;
            }

            @Override
            public void run() {
                int n = this.addTrack();
                if (MediaPlayer.this.mEventHandler != null) {
                    Message message = MediaPlayer.this.mEventHandler.obtainMessage(200, n, 0, null);
                    MediaPlayer.this.mEventHandler.sendMessage(message);
                }
                this.val$thread.getLooper().quitSafely();
            }
        });
    }

    public void addTimedTextSource(FileDescriptor fileDescriptor, String string2) throws IllegalArgumentException, IllegalStateException {
        this.addTimedTextSource(fileDescriptor, 0L, 0x7FFFFFFFFFFFFFFL, string2);
    }

    public void addTimedTextSource(String object, String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        if (MediaPlayer.availableMimeTypeForExternalSource(string2)) {
            object = new FileInputStream(new File((String)object));
            try {
                this.addTimedTextSource(((FileInputStream)object).getFD(), string2);
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    MediaPlayer.$closeResource(throwable, (AutoCloseable)object);
                    throw throwable2;
                }
            }
            MediaPlayer.$closeResource(null, (AutoCloseable)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Illegal mimeType for timed text source: ");
        ((StringBuilder)object).append(string2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public native void attachAuxEffect(int var1);

    public void clearOnMediaTimeDiscontinuityListener() {
        this.setOnMediaTimeDiscontinuityListenerInt(null, null);
    }

    public void clearOnSubtitleDataListener() {
        this.setOnSubtitleDataListenerInt(null, null);
    }

    @Override
    public VolumeShaper createVolumeShaper(VolumeShaper.Configuration configuration) {
        return new VolumeShaper(configuration, this);
    }

    public void deselectTrack(int n) throws IllegalStateException {
        this.selectOrDeselectTrack(n, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public PlaybackParams easyPlaybackParams(float f, int n) {
        Object object = new PlaybackParams();
        ((PlaybackParams)object).allowDefaults();
        if (n == 0) {
            ((PlaybackParams)object).setSpeed(f).setPitch(1.0f);
            return object;
        }
        if (n == 1) {
            ((PlaybackParams)object).setSpeed(f).setPitch(1.0f).setAudioFallbackMode(2);
            return object;
        }
        if (n == 2) {
            ((PlaybackParams)object).setSpeed(f).setPitch(f);
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Audio playback mode ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" is not supported");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    protected void finalize() {
        this.baseRelease();
        this.native_finalize();
    }

    public native int getAudioSessionId();

    public native int getCurrentPosition();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public DrmInfo getDrmInfo() {
        Object object = null;
        Object object2 = this.mDrmLock;
        synchronized (object2) {
            if (!this.mDrmInfoResolved && this.mDrmInfo == null) {
                Log.v("MediaPlayer", "The Player has not been prepared yet");
                object = new IllegalStateException("The Player has not been prepared yet");
                throw object;
            }
            if (this.mDrmInfo == null) return object;
            return this.mDrmInfo.makeCopy();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public String getDrmPropertyString(String object) throws NoDrmSchemeException {
        String string2;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("getDrmPropertyString: propertyName: ");
        ((StringBuilder)object2).append((String)object);
        Log.v("MediaPlayer", ((StringBuilder)object2).toString());
        object2 = this.mDrmLock;
        // MONITORENTER : object2
        if (!this.mActiveDrmScheme && !this.mDrmConfigAllowed) {
            Log.w("MediaPlayer", "getDrmPropertyString NoDrmSchemeException");
            object = new NoDrmSchemeException("getDrmPropertyString: Has to prepareDrm() first.");
            throw object;
        }
        try {
            string2 = this.mDrmObj.getPropertyString((String)object);
            // MONITOREXIT : object2
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("getDrmPropertyString: propertyName: ");
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getDrmPropertyString Exception ");
            stringBuilder.append(exception);
            Log.w("MediaPlayer", stringBuilder.toString());
            throw exception;
        }
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(" --> value: ");
        ((StringBuilder)object2).append(string2);
        Log.v("MediaPlayer", ((StringBuilder)object2).toString());
        return string2;
    }

    public native int getDuration();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MediaDrm.KeyRequest getKeyRequest(byte[] object, byte[] object2, String string2, int n, Map<String, String> object3) throws NoDrmSchemeException {
        Serializable serializable = new StringBuilder();
        serializable.append("getKeyRequest:  keySetId: ");
        serializable.append(object);
        serializable.append(" initData:");
        serializable.append(object2);
        serializable.append(" mimeType: ");
        serializable.append(string2);
        serializable.append(" keyType: ");
        serializable.append(n);
        serializable.append(" optionalParameters: ");
        serializable.append(object3);
        Log.v("MediaPlayer", serializable.toString());
        Object object4 = this.mDrmLock;
        synchronized (object4) {
            block10 : {
                Exception exception2;
                block9 : {
                    boolean bl = this.mActiveDrmScheme;
                    if (!bl) {
                        Log.e("MediaPlayer", "getKeyRequest NoDrmSchemeException");
                        object = new NoDrmSchemeException("getKeyRequest: Has to set a DRM scheme first.");
                        throw object;
                    }
                    if (n != 3) {
                        try {
                            object = this.mDrmSessionId;
                        }
                        catch (Exception exception2) {
                            break block9;
                        }
                        catch (NotProvisionedException notProvisionedException) {
                            break block10;
                        }
                    }
                    if (object3 != null) {
                        serializable = new HashMap(object3);
                        object3 = serializable;
                    } else {
                        object3 = null;
                    }
                    object2 = this.mDrmObj.getKeyRequest((byte[])object, (byte[])object2, string2, n, (HashMap<String, String>)object3);
                    object = new StringBuilder();
                    ((StringBuilder)object).append("getKeyRequest:   --> request: ");
                    ((StringBuilder)object).append(object2);
                    Log.v("MediaPlayer", ((StringBuilder)object).toString());
                    return object2;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("getKeyRequest Exception ");
                ((StringBuilder)object).append(exception2);
                Log.w("MediaPlayer", ((StringBuilder)object).toString());
                throw exception2;
            }
            Log.w("MediaPlayer", "getKeyRequest NotProvisionedException: Unexpected. Shouldn't have reached here.");
            IllegalStateException illegalStateException = new IllegalStateException("getKeyRequest: Unexpected provisioning error.");
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public MediaTimeProvider getMediaTimeProvider() {
        Object object = this.mTimeProviderLock;
        synchronized (object) {
            TimeProvider timeProvider;
            if (this.mTimeProvider != null) return this.mTimeProvider;
            this.mTimeProvider = timeProvider = new TimeProvider(this);
            return this.mTimeProvider;
        }
    }

    @UnsupportedAppUsage
    public Metadata getMetadata(boolean bl, boolean bl2) {
        Parcel parcel = Parcel.obtain();
        Metadata metadata = new Metadata();
        if (!this.native_getMetadata(bl, bl2, parcel)) {
            parcel.recycle();
            return null;
        }
        if (!metadata.parse(parcel)) {
            parcel.recycle();
            return null;
        }
        return metadata;
    }

    public PersistableBundle getMetrics() {
        return this.native_getMetrics();
    }

    public native PlaybackParams getPlaybackParams();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AudioDeviceInfo getPreferredDevice() {
        synchronized (this) {
            return this.mPreferredDevice;
        }
    }

    @Override
    public AudioDeviceInfo getRoutedDevice() {
        int n = this.native_getRoutedDeviceId();
        if (n == 0) {
            return null;
        }
        AudioDeviceInfo[] arraudioDeviceInfo = AudioManager.getDevicesStatic(2);
        for (int i = 0; i < arraudioDeviceInfo.length; ++i) {
            if (arraudioDeviceInfo[i].getId() != n) continue;
            return arraudioDeviceInfo[i];
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getSelectedTrack(int n) throws IllegalStateException {
        Object object;
        int n2;
        Vector<Pair<Integer, SubtitleTrack>> vector = this.mSubtitleController;
        int n3 = 0;
        if (vector != null && (n == 4 || n == 3) && (object = this.mSubtitleController.getSelectedTrack()) != null) {
            vector = this.mIndexTrackPairs;
            synchronized (vector) {
                for (n2 = 0; n2 < this.mIndexTrackPairs.size(); ++n2) {
                    if (this.mIndexTrackPairs.get((int)n2).second != object || ((SubtitleTrack)object).getTrackType() != n) continue;
                    return n2;
                }
            }
        }
        object = Parcel.obtain();
        vector = Parcel.obtain();
        try {
            ((Parcel)object).writeInterfaceToken("android.media.IMediaPlayer");
            ((Parcel)object).writeInt(7);
            ((Parcel)object).writeInt(n);
            this.invoke((Parcel)object, (Parcel)((Object)vector));
            n2 = ((Parcel)((Object)vector)).readInt();
            Vector<Pair<Integer, SubtitleTrack>> vector2 = this.mIndexTrackPairs;
            synchronized (vector2) {
                n = n3;
            }
        }
        catch (Throwable throwable) {
            ((Parcel)object).recycle();
            ((Parcel)((Object)vector)).recycle();
            throw throwable;
        }
        {
            do {
                if (n >= this.mIndexTrackPairs.size()) {
                    // MONITOREXIT [8, 11, 13] lbl29 : MonitorExitStatement: MONITOREXIT : var6_6
                    ((Parcel)object).recycle();
                    ((Parcel)((Object)vector)).recycle();
                    return -1;
                }
                Pair<Integer, SubtitleTrack> pair = this.mIndexTrackPairs.get(n);
                if (pair.first != null && (Integer)pair.first == n2) {
                    // MONITOREXIT [8, 11, 12] lbl35 : MonitorExitStatement: MONITOREXIT : var6_6
                    ((Parcel)object).recycle();
                    ((Parcel)((Object)vector)).recycle();
                    return n;
                }
                ++n;
            } while (true);
        }
    }

    public native SyncParams getSyncParams();

    public MediaTimestamp getTimestamp() {
        long l;
        float f;
        long l2;
        try {
            l2 = this.getCurrentPosition();
            l = System.nanoTime();
            f = this.isPlaying() ? this.getPlaybackParams().getSpeed() : 0.0f;
        }
        catch (IllegalStateException illegalStateException) {
            return null;
        }
        MediaTimestamp mediaTimestamp = new MediaTimestamp(l2 * 1000L, l, f);
        return mediaTimestamp;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public TrackInfo[] getTrackInfo() throws IllegalStateException {
        TrackInfo[] arrtrackInfo = this.getInbandTrackInfo();
        Vector<Pair<Integer, SubtitleTrack>> vector = this.mIndexTrackPairs;
        synchronized (vector) {
            TrackInfo[] arrtrackInfo2 = new TrackInfo[this.mIndexTrackPairs.size()];
            int n = 0;
            while (n < arrtrackInfo2.length) {
                Object object = this.mIndexTrackPairs.get(n);
                if (((Pair)object).first != null) {
                    arrtrackInfo2[n] = arrtrackInfo[(Integer)((Pair)object).first];
                } else {
                    object = (SubtitleTrack)((Pair)object).second;
                    arrtrackInfo2[n] = new TrackInfo(((SubtitleTrack)object).getTrackType(), ((SubtitleTrack)object).getFormat());
                }
                ++n;
            }
            return arrtrackInfo2;
        }
    }

    public native int getVideoHeight();

    public native int getVideoWidth();

    @UnsupportedAppUsage
    public void invoke(Parcel object, Parcel parcel) {
        int n = this.native_invoke((Parcel)object, parcel);
        parcel.setDataPosition(0);
        if (n == 0) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("failure code: ");
        ((StringBuilder)object).append(n);
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public native boolean isLooping();

    public native boolean isPlaying();

    @UnsupportedAppUsage
    public Parcel newRequest() {
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken("android.media.IMediaPlayer");
        return parcel;
    }

    public void notifyAt(long l) {
        this._notifyAt(l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void onSubtitleTrackSelected(SubtitleTrack subtitleTrack) {
        int n = this.mSelectedSubtitleTrackIndex;
        if (n >= 0) {
            try {
                this.selectOrDeselectInbandTrack(n, false);
            }
            catch (IllegalStateException illegalStateException) {
                // empty catch block
            }
            this.mSelectedSubtitleTrackIndex = -1;
        }
        // MONITORENTER : this
        this.mSubtitleDataListenerDisabled = true;
        // MONITOREXIT : this
        if (subtitleTrack == null) {
            return;
        }
        Vector<Pair<Integer, SubtitleTrack>> vector = this.mIndexTrackPairs;
        // MONITORENTER : vector
        for (Pair<Integer, SubtitleTrack> pair : this.mIndexTrackPairs) {
            if (pair.first == null || pair.second != subtitleTrack) continue;
            this.mSelectedSubtitleTrackIndex = (Integer)pair.first;
            break;
        }
        // MONITOREXIT : vector
        n = this.mSelectedSubtitleTrackIndex;
        if (n < 0) return;
        try {
            this.selectOrDeselectInbandTrack(n, true);
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
        this.mSubtitleDataListenerDisabled = false;
        // MONITOREXIT : this
    }

    public void pause() throws IllegalStateException {
        this.stayAwake(false);
        this._pause();
        this.basePause();
    }

    @Override
    int playerApplyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) {
        return this.native_applyVolumeShaper(configuration, operation);
    }

    @Override
    VolumeShaper.State playerGetVolumeShaperState(int n) {
        return this.native_getVolumeShaperState(n);
    }

    @Override
    void playerPause() {
        this.pause();
    }

    @Override
    int playerSetAuxEffectSendLevel(boolean bl, float f) {
        if (bl) {
            f = 0.0f;
        }
        this._setAuxEffectSendLevel(f);
        return 0;
    }

    @Override
    void playerSetVolume(boolean bl, float f, float f2) {
        float f3 = 0.0f;
        if (bl) {
            f = 0.0f;
        }
        if (bl) {
            f2 = f3;
        }
        this._setVolume(f, f2);
    }

    @Override
    void playerStart() {
        this.start();
    }

    @Override
    void playerStop() {
        this.stop();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void prepare() throws IOException, IllegalStateException {
        this._prepare();
        this.scanInternalSubtitleTracks();
        Object object = this.mDrmLock;
        synchronized (object) {
            this.mDrmInfoResolved = true;
            return;
        }
    }

    public native void prepareAsync() throws IllegalStateException;

    /*
     * Exception decompiling
     */
    public void prepareDrm(UUID var1_1) throws UnsupportedSchemeException, ResourceBusyException, ProvisioningNetworkErrorException, ProvisioningServerErrorException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] provideKeyResponse(byte[] object, byte[] object2) throws NoDrmSchemeException, DeniedByServerException {
        byte[] arrby = new StringBuilder();
        arrby.append("provideKeyResponse: keySetId: ");
        arrby.append(object);
        arrby.append(" response: ");
        arrby.append(object2);
        Log.v("MediaPlayer", arrby.toString());
        Object object3 = this.mDrmLock;
        synchronized (object3) {
            block9 : {
                Exception exception2;
                block8 : {
                    block7 : {
                        boolean bl = this.mActiveDrmScheme;
                        if (!bl) {
                            Log.e("MediaPlayer", "getKeyRequest NoDrmSchemeException");
                            object = new NoDrmSchemeException("getKeyRequest: Has to set a DRM scheme first.");
                            throw object;
                        }
                        if (object == null) {
                            try {
                                arrby = this.mDrmSessionId;
                                break block7;
                            }
                            catch (Exception exception2) {
                                break block8;
                            }
                            catch (NotProvisionedException notProvisionedException) {
                                break block9;
                            }
                        }
                        arrby = object;
                    }
                    arrby = this.mDrmObj.provideKeyResponse(arrby, (byte[])object2);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("provideKeyResponse: keySetId: ");
                    stringBuilder.append(object);
                    stringBuilder.append(" response: ");
                    stringBuilder.append(object2);
                    stringBuilder.append(" --> ");
                    stringBuilder.append(arrby);
                    Log.v("MediaPlayer", stringBuilder.toString());
                    return arrby;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("provideKeyResponse Exception ");
                ((StringBuilder)object2).append(exception2);
                Log.w("MediaPlayer", ((StringBuilder)object2).toString());
                throw exception2;
            }
            Log.w("MediaPlayer", "provideKeyResponse NotProvisionedException: Unexpected. Shouldn't have reached here.");
            IllegalStateException illegalStateException = new IllegalStateException("provideKeyResponse: Unexpected provisioning error.");
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void release() {
        this.baseRelease();
        this.stayAwake(false);
        this.updateSurfaceScreenOn();
        this.mOnPreparedListener = null;
        this.mOnBufferingUpdateListener = null;
        this.mOnCompletionListener = null;
        this.mOnSeekCompleteListener = null;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mOnVideoSizeChangedListener = null;
        this.mOnTimedTextListener = null;
        Object object = this.mTimeProviderLock;
        synchronized (object) {
            if (this.mTimeProvider != null) {
                this.mTimeProvider.close();
                this.mTimeProvider = null;
            }
        }
        synchronized (this) {
            this.mSubtitleDataListenerDisabled = false;
            this.mExtSubtitleDataListener = null;
            this.mExtSubtitleDataHandler = null;
            this.mOnMediaTimeDiscontinuityListener = null;
            this.mOnMediaTimeDiscontinuityHandler = null;
        }
        this.mOnDrmConfigHelper = null;
        this.mOnDrmInfoHandlerDelegate = null;
        this.mOnDrmPreparedHandlerDelegate = null;
        this.resetDrmState();
        this._release();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void releaseDrm() throws NoDrmSchemeException {
        Log.v("MediaPlayer", "releaseDrm:");
        Object object = this.mDrmLock;
        // MONITORENTER : object
        boolean bl = this.mActiveDrmScheme;
        if (!bl) {
            Log.e("MediaPlayer", "releaseDrm(): No active DRM scheme to release.");
            NoDrmSchemeException noDrmSchemeException = new NoDrmSchemeException("releaseDrm: No active DRM scheme to release.");
            throw noDrmSchemeException;
        }
        try {
            this._releaseDrm();
            this.cleanDrmObj();
            this.mActiveDrmScheme = false;
            return;
        }
        catch (Exception exception) {
            Log.e("MediaPlayer", "releaseDrm: Exception ", exception);
        }
        return;
        catch (IllegalStateException illegalStateException) {
            Log.w("MediaPlayer", "releaseDrm: Exception ", illegalStateException);
            IllegalStateException illegalStateException2 = new IllegalStateException("releaseDrm: The player is not in a valid state.");
            throw illegalStateException2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void removeOnRoutingChangedListener(AudioRouting.OnRoutingChangedListener onRoutingChangedListener) {
        ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> arrayMap = this.mRoutingChangeListeners;
        synchronized (arrayMap) {
            if (this.mRoutingChangeListeners.containsKey(onRoutingChangedListener)) {
                this.mRoutingChangeListeners.remove(onRoutingChangedListener);
                this.enableNativeRoutingCallbacksLocked(false);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reset() {
        this.mSelectedSubtitleTrackIndex = -1;
        Vector<Pair<Integer, SubtitleTrack>> vector = this.mOpenSubtitleSources;
        synchronized (vector) {
            for (InputStream inputStream : this.mOpenSubtitleSources) {
                try {
                    inputStream.close();
                }
                catch (IOException iOException) {}
            }
            this.mOpenSubtitleSources.clear();
        }
        vector = this.mSubtitleController;
        if (vector != null) {
            ((SubtitleController)((Object)vector)).reset();
        }
        vector = this.mTimeProviderLock;
        synchronized (vector) {
            if (this.mTimeProvider != null) {
                this.mTimeProvider.close();
                this.mTimeProvider = null;
            }
        }
        this.stayAwake(false);
        this._reset();
        vector = this.mEventHandler;
        if (vector != null) {
            ((Handler)((Object)vector)).removeCallbacksAndMessages(null);
        }
        vector = this.mIndexTrackPairs;
        synchronized (vector) {
            this.mIndexTrackPairs.clear();
            this.mInbandTrackIndices.clear();
        }
        this.resetDrmState();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void restoreKeys(byte[] object) throws NoDrmSchemeException {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("restoreKeys: keySetId: ");
        ((StringBuilder)object2).append(object);
        Log.v("MediaPlayer", ((StringBuilder)object2).toString());
        object2 = this.mDrmLock;
        synchronized (object2) {
            boolean bl = this.mActiveDrmScheme;
            if (!bl) {
                Log.w("MediaPlayer", "restoreKeys NoDrmSchemeException");
                object = new NoDrmSchemeException("restoreKeys: Has to set a DRM scheme first.");
                throw object;
            }
            try {
                this.mDrmObj.restoreKeys(this.mDrmSessionId, (byte[])object);
                return;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("restoreKeys Exception ");
                ((StringBuilder)object).append(exception);
                Log.w("MediaPlayer", ((StringBuilder)object).toString());
                throw exception;
            }
        }
    }

    public void seekTo(int n) throws IllegalStateException {
        this.seekTo(n, 0);
    }

    public void seekTo(long l, int n) {
        if (n >= 0 && n <= 3) {
            long l2;
            if (l > Integer.MAX_VALUE) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("seekTo offset ");
                stringBuilder.append(l);
                stringBuilder.append(" is too large, cap to ");
                stringBuilder.append(Integer.MAX_VALUE);
                Log.w("MediaPlayer", stringBuilder.toString());
                l2 = Integer.MAX_VALUE;
            } else {
                l2 = l;
                if (l < Integer.MIN_VALUE) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("seekTo offset ");
                    stringBuilder.append(l);
                    stringBuilder.append(" is too small, cap to ");
                    stringBuilder.append(Integer.MIN_VALUE);
                    Log.w("MediaPlayer", stringBuilder.toString());
                    l2 = Integer.MIN_VALUE;
                }
            }
            this._seekTo(l2, n);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal seek mode: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void selectTrack(int n) throws IllegalStateException {
        this.selectOrDeselectTrack(n, true);
    }

    public void setAudioAttributes(AudioAttributes audioAttributes) throws IllegalArgumentException {
        if (audioAttributes != null) {
            this.baseUpdateAudioAttributes(audioAttributes);
            this.mUsage = audioAttributes.getUsage();
            Parcel parcel = Parcel.obtain();
            audioAttributes.writeToParcel(parcel, 1);
            this.setParameter(1400, parcel);
            parcel.recycle();
            return;
        }
        throw new IllegalArgumentException("Cannot set AudioAttributes to null");
    }

    public native void setAudioSessionId(int var1) throws IllegalArgumentException, IllegalStateException;

    public void setAudioStreamType(int n) {
        MediaPlayer.deprecateStreamTypeForPlayback(n, "MediaPlayer", "setAudioStreamType()");
        this.baseUpdateAudioAttributes(new AudioAttributes.Builder().setInternalLegacyStreamType(n).build());
        this._setAudioStreamType(n);
        this.mStreamType = n;
    }

    public void setAuxEffectSendLevel(float f) {
        this.baseSetAuxEffectSendLevel(f);
    }

    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.setDataSource(context, uri, null, null);
    }

    public void setDataSource(Context context, Uri uri, Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.setDataSource(context, uri, map, null);
    }

    public void setDataSource(Context object, Uri uri, Map<String, String> map, List<HttpCookie> list) throws IOException {
        if (object != null) {
            if (uri != null) {
                Object object2;
                if (list != null && (object2 = CookieHandler.getDefault()) != null && !(object2 instanceof CookieManager)) {
                    throw new IllegalArgumentException("The cookie handler has to be of CookieManager type when cookies are provided.");
                }
                object2 = ((Context)object).getContentResolver();
                String string2 = uri.getScheme();
                Object object3 = ContentProvider.getAuthorityWithoutUserId(uri.getAuthority());
                if ("file".equals(string2)) {
                    this.setDataSource(uri.getPath());
                    return;
                }
                if ("content".equals(string2) && "settings".equals(object3)) {
                    int n = RingtoneManager.getDefaultType(uri);
                    object3 = RingtoneManager.getCacheForType(n, ((Context)object).getUserId());
                    object = RingtoneManager.getActualDefaultRingtoneUri((Context)object, n);
                    if (this.attemptDataSource((ContentResolver)object2, (Uri)object3)) {
                        return;
                    }
                    if (this.attemptDataSource((ContentResolver)object2, (Uri)object)) {
                        return;
                    }
                    this.setDataSource(uri.toString(), map, list);
                } else {
                    if (this.attemptDataSource((ContentResolver)object2, uri)) {
                        return;
                    }
                    this.setDataSource(uri.toString(), map, list);
                }
                return;
            }
            throw new NullPointerException("uri param can not be null.");
        }
        throw new NullPointerException("context param can not be null.");
    }

    public void setDataSource(AssetFileDescriptor assetFileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException {
        Preconditions.checkNotNull(assetFileDescriptor);
        if (assetFileDescriptor.getDeclaredLength() < 0L) {
            this.setDataSource(assetFileDescriptor.getFileDescriptor());
        } else {
            this.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getDeclaredLength());
        }
    }

    public void setDataSource(MediaDataSource mediaDataSource) throws IllegalArgumentException, IllegalStateException {
        this._setDataSource(mediaDataSource);
    }

    public void setDataSource(FileDescriptor fileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException {
        this.setDataSource(fileDescriptor, 0L, 0x7FFFFFFFFFFFFFFL);
    }

    public void setDataSource(FileDescriptor fileDescriptor, long l, long l2) throws IOException, IllegalArgumentException, IllegalStateException {
        this._setDataSource(fileDescriptor, l, l2);
    }

    public void setDataSource(String string2) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.setDataSource(string2, null, null);
    }

    @UnsupportedAppUsage
    public void setDataSource(String string2, Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.setDataSource(string2, map, null);
    }

    public void setDisplay(SurfaceHolder object) {
        this.mSurfaceHolder = object;
        object = object != null ? object.getSurface() : null;
        this._setVideoSurface((Surface)object);
        this.updateSurfaceScreenOn();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setDrmPropertyString(String object, String string2) throws NoDrmSchemeException {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("setDrmPropertyString: propertyName: ");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(" value: ");
        ((StringBuilder)object2).append(string2);
        Log.v("MediaPlayer", ((StringBuilder)object2).toString());
        object2 = this.mDrmLock;
        synchronized (object2) {
            if (!this.mActiveDrmScheme && !this.mDrmConfigAllowed) {
                Log.w("MediaPlayer", "setDrmPropertyString NoDrmSchemeException");
                object = new NoDrmSchemeException("setDrmPropertyString: Has to prepareDrm() first.");
                throw object;
            }
            try {
                this.mDrmObj.setPropertyString((String)object, string2);
                return;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("setDrmPropertyString Exception ");
                ((StringBuilder)object).append(exception);
                Log.w("MediaPlayer", ((StringBuilder)object).toString());
                throw exception;
            }
        }
    }

    public native void setLooping(boolean var1);

    public int setMetadataFilter(Set<Integer> iterator, Set<Integer> set) {
        Parcel parcel = this.newRequest();
        int n = parcel.dataSize() + (iterator.size() + 1 + 1 + set.size()) * 4;
        if (parcel.dataCapacity() < n) {
            parcel.setDataCapacity(n);
        }
        parcel.writeInt(iterator.size());
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            parcel.writeInt((Integer)iterator.next());
        }
        parcel.writeInt(set.size());
        iterator = set.iterator();
        while (iterator.hasNext()) {
            parcel.writeInt(iterator.next());
        }
        return this.native_setMetadataFilter(parcel);
    }

    public native void setNextMediaPlayer(MediaPlayer var1);

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        this.mOnBufferingUpdateListener = onBufferingUpdateListener;
    }

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnDrmConfigHelper(OnDrmConfigHelper onDrmConfigHelper) {
        Object object = this.mDrmLock;
        synchronized (object) {
            this.mOnDrmConfigHelper = onDrmConfigHelper;
            return;
        }
    }

    public void setOnDrmInfoListener(OnDrmInfoListener onDrmInfoListener) {
        this.setOnDrmInfoListener(onDrmInfoListener, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnDrmInfoListener(OnDrmInfoListener onDrmInfoListener, Handler handler) {
        Object object = this.mDrmLock;
        synchronized (object) {
            OnDrmInfoHandlerDelegate onDrmInfoHandlerDelegate;
            this.mOnDrmInfoHandlerDelegate = onDrmInfoListener != null ? (onDrmInfoHandlerDelegate = new OnDrmInfoHandlerDelegate(this, onDrmInfoListener, handler)) : null;
            return;
        }
    }

    public void setOnDrmPreparedListener(OnDrmPreparedListener onDrmPreparedListener) {
        this.setOnDrmPreparedListener(onDrmPreparedListener, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnDrmPreparedListener(OnDrmPreparedListener onDrmPreparedListener, Handler handler) {
        Object object = this.mDrmLock;
        synchronized (object) {
            OnDrmPreparedHandlerDelegate onDrmPreparedHandlerDelegate;
            this.mOnDrmPreparedHandlerDelegate = onDrmPreparedListener != null ? (onDrmPreparedHandlerDelegate = new OnDrmPreparedHandlerDelegate(this, onDrmPreparedListener, handler)) : null;
            return;
        }
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public void setOnMediaTimeDiscontinuityListener(OnMediaTimeDiscontinuityListener onMediaTimeDiscontinuityListener) {
        if (onMediaTimeDiscontinuityListener != null) {
            this.setOnMediaTimeDiscontinuityListenerInt(onMediaTimeDiscontinuityListener, null);
            return;
        }
        throw new IllegalArgumentException("Illegal null listener");
    }

    public void setOnMediaTimeDiscontinuityListener(OnMediaTimeDiscontinuityListener onMediaTimeDiscontinuityListener, Handler handler) {
        if (onMediaTimeDiscontinuityListener != null) {
            if (handler != null) {
                this.setOnMediaTimeDiscontinuityListenerInt(onMediaTimeDiscontinuityListener, handler);
                return;
            }
            throw new IllegalArgumentException("Illegal null handler");
        }
        throw new IllegalArgumentException("Illegal null listener");
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        this.mOnSeekCompleteListener = onSeekCompleteListener;
    }

    public void setOnSubtitleDataListener(OnSubtitleDataListener onSubtitleDataListener) {
        if (onSubtitleDataListener != null) {
            this.setOnSubtitleDataListenerInt(onSubtitleDataListener, null);
            return;
        }
        throw new IllegalArgumentException("Illegal null listener");
    }

    public void setOnSubtitleDataListener(OnSubtitleDataListener onSubtitleDataListener, Handler handler) {
        if (onSubtitleDataListener != null) {
            if (handler != null) {
                this.setOnSubtitleDataListenerInt(onSubtitleDataListener, handler);
                return;
            }
            throw new IllegalArgumentException("Illegal null handler");
        }
        throw new IllegalArgumentException("Illegal null listener");
    }

    public void setOnTimedMetaDataAvailableListener(OnTimedMetaDataAvailableListener onTimedMetaDataAvailableListener) {
        this.mOnTimedMetaDataAvailableListener = onTimedMetaDataAvailableListener;
    }

    public void setOnTimedTextListener(OnTimedTextListener onTimedTextListener) {
        this.mOnTimedTextListener = onTimedTextListener;
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.mOnVideoSizeChangedListener = onVideoSizeChangedListener;
    }

    public native void setPlaybackParams(PlaybackParams var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean setPreferredDevice(AudioDeviceInfo audioDeviceInfo) {
        boolean bl;
        int n = 0;
        if (audioDeviceInfo != null && !audioDeviceInfo.isSink()) {
            return false;
        }
        if (audioDeviceInfo != null) {
            n = audioDeviceInfo.getId();
        }
        if (!(bl = this.native_setOutputDevice(n))) return bl;
        synchronized (this) {
            this.mPreferredDevice = audioDeviceInfo;
            return bl;
        }
    }

    @UnsupportedAppUsage
    public void setRetransmitEndpoint(InetSocketAddress serializable) throws IllegalStateException, IllegalArgumentException {
        String string2 = null;
        int n = 0;
        if (serializable != null) {
            string2 = ((InetSocketAddress)serializable).getAddress().getHostAddress();
            n = ((InetSocketAddress)serializable).getPort();
        }
        if ((n = this.native_setRetransmitEndpoint(string2, n)) == 0) {
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Illegal re-transmit endpoint; native ret ");
        ((StringBuilder)serializable).append(n);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public void setScreenOnWhilePlaying(boolean bl) {
        if (this.mScreenOnWhilePlaying != bl) {
            if (bl && this.mSurfaceHolder == null) {
                Log.w("MediaPlayer", "setScreenOnWhilePlaying(true) is ineffective without a SurfaceHolder");
            }
            this.mScreenOnWhilePlaying = bl;
            this.updateSurfaceScreenOn();
        }
    }

    @UnsupportedAppUsage
    public void setSubtitleAnchor(SubtitleController subtitleController, SubtitleController.Anchor anchor) {
        this.mSubtitleController = subtitleController;
        this.mSubtitleController.setAnchor(anchor);
    }

    public void setSurface(Surface surface) {
        if (this.mScreenOnWhilePlaying && surface != null) {
            Log.w("MediaPlayer", "setScreenOnWhilePlaying(true) is ineffective for Surface");
        }
        this.mSurfaceHolder = null;
        this._setVideoSurface(surface);
        this.updateSurfaceScreenOn();
    }

    public native void setSyncParams(SyncParams var1);

    public void setVideoScalingMode(int n) {
        if (this.isVideoScalingModeSupported(n)) {
            Parcel parcel = Parcel.obtain();
            Parcel parcel2 = Parcel.obtain();
            try {
                parcel.writeInterfaceToken("android.media.IMediaPlayer");
                parcel.writeInt(6);
                parcel.writeInt(n);
                this.invoke(parcel, parcel2);
                return;
            }
            finally {
                parcel.recycle();
                parcel2.recycle();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Scaling mode ");
        stringBuilder.append(n);
        stringBuilder.append(" is not supported");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setVolume(float f) {
        this.setVolume(f, f);
    }

    public void setVolume(float f, float f2) {
        this.baseSetVolume(f, f2);
    }

    public void setWakeMode(Context object, int n) {
        boolean bl = false;
        boolean bl2 = false;
        if (SystemProperties.getBoolean("audio.offload.ignore_setawake", false)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("IGNORING setWakeMode ");
            ((StringBuilder)object).append(n);
            Log.w("MediaPlayer", ((StringBuilder)object).toString());
            return;
        }
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            bl = bl2;
            if (wakeLock.isHeld()) {
                bl = true;
                this.mWakeLock.release();
            }
            this.mWakeLock = null;
        }
        this.mWakeLock = ((PowerManager)((Context)object).getSystemService("power")).newWakeLock(536870912 | n, MediaPlayer.class.getName());
        this.mWakeLock.setReferenceCounted(false);
        if (bl) {
            this.mWakeLock.acquire();
        }
    }

    public void start() throws IllegalStateException {
        final int n = this.getStartDelayMs();
        if (n == 0) {
            this.startImpl();
        } else {
            new Thread(){

                @Override
                public void run() {
                    try {
                        Thread.sleep(n);
                    }
                    catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    MediaPlayer.this.baseSetStartDelayMs(0);
                    try {
                        MediaPlayer.this.startImpl();
                    }
                    catch (IllegalStateException illegalStateException) {
                        // empty catch block
                    }
                }
            }.start();
        }
    }

    public void stop() throws IllegalStateException {
        this.stayAwake(false);
        this._stop();
        this.baseStop();
    }

    public static final class DrmInfo {
        private Map<UUID, byte[]> mapPssh;
        private UUID[] supportedSchemes;

        private DrmInfo(Parcel object) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("DrmInfo(");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(") size ");
            ((StringBuilder)object2).append(((Parcel)object).dataSize());
            Log.v(MediaPlayer.TAG, ((StringBuilder)object2).toString());
            int n = ((Parcel)object).readInt();
            byte[] arrby = new byte[n];
            ((Parcel)object).readByteArray(arrby);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("DrmInfo() PSSH: ");
            ((StringBuilder)object2).append(this.arrToHex(arrby));
            Log.v(MediaPlayer.TAG, ((StringBuilder)object2).toString());
            this.mapPssh = this.parsePSSH(arrby, n);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("DrmInfo() PSSH: ");
            ((StringBuilder)object2).append(this.mapPssh);
            Log.v(MediaPlayer.TAG, ((StringBuilder)object2).toString());
            int n2 = ((Parcel)object).readInt();
            this.supportedSchemes = new UUID[n2];
            for (int i = 0; i < n2; ++i) {
                object2 = new byte[16];
                ((Parcel)object).readByteArray((byte[])object2);
                this.supportedSchemes[i] = this.bytesToUUID((byte[])object2);
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("DrmInfo() supportedScheme[");
                ((StringBuilder)object2).append(i);
                ((StringBuilder)object2).append("]: ");
                ((StringBuilder)object2).append(this.supportedSchemes[i]);
                Log.v(MediaPlayer.TAG, ((StringBuilder)object2).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("DrmInfo() Parcel psshsize: ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" supportedDRMsCount: ");
            ((StringBuilder)object).append(n2);
            Log.v(MediaPlayer.TAG, ((StringBuilder)object).toString());
        }

        private DrmInfo(Map<UUID, byte[]> map, UUID[] arruUID) {
            this.mapPssh = map;
            this.supportedSchemes = arruUID;
        }

        private String arrToHex(byte[] arrby) {
            String string2 = "0x";
            for (int i = 0; i < arrby.length; ++i) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(String.format("%02x", arrby[i]));
                string2 = stringBuilder.toString();
            }
            return string2;
        }

        private UUID bytesToUUID(byte[] arrby) {
            long l = 0L;
            long l2 = 0L;
            for (int i = 0; i < 8; ++i) {
                l |= ((long)arrby[i] & 255L) << (7 - i) * 8;
                l2 |= ((long)arrby[i + 8] & 255L) << (7 - i) * 8;
            }
            return new UUID(l, l2);
        }

        private DrmInfo makeCopy() {
            return new DrmInfo(this.mapPssh, this.supportedSchemes);
        }

        private Map<UUID, byte[]> parsePSSH(byte[] arrby, int n) {
            HashMap<UUID, byte[]> hashMap = new HashMap<UUID, byte[]>();
            int n2 = n;
            int n3 = 0;
            int n4 = 0;
            while (n2 > 0) {
                if (n2 < 16) {
                    Log.w(MediaPlayer.TAG, String.format("parsePSSH: len is too short to parse UUID: (%d < 16) pssh: %d", n2, n));
                    return null;
                }
                UUID uUID = this.bytesToUUID(Arrays.copyOfRange(arrby, n4, n4 + 16));
                n4 += 16;
                int n5 = n2 - 16;
                if (n5 < 4) {
                    Log.w(MediaPlayer.TAG, String.format("parsePSSH: len is too short to parse datalen: (%d < 4) pssh: %d", n5, n));
                    return null;
                }
                byte[] arrby2 = Arrays.copyOfRange(arrby, n4, n4 + 4);
                n2 = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? (arrby2[3] & 255) << 24 | (arrby2[2] & 255) << 16 | (arrby2[1] & 255) << 8 | arrby2[0] & 255 : (arrby2[0] & 255) << 24 | (arrby2[1] & 255) << 16 | (arrby2[2] & 255) << 8 | arrby2[3] & 255;
                n4 += 4;
                if ((n5 -= 4) < n2) {
                    Log.w(MediaPlayer.TAG, String.format("parsePSSH: len is too short to parse data: (%d < %d) pssh: %d", n5, n2, n));
                    return null;
                }
                arrby2 = Arrays.copyOfRange(arrby, n4, n4 + n2);
                n4 += n2;
                n2 = n5 - n2;
                Log.v(MediaPlayer.TAG, String.format("parsePSSH[%d]: <%s, %s> pssh: %d", n3, uUID, this.arrToHex(arrby2), n));
                ++n3;
                hashMap.put(uUID, arrby2);
            }
            return hashMap;
        }

        public Map<UUID, byte[]> getPssh() {
            return this.mapPssh;
        }

        public UUID[] getSupportedSchemes() {
            return this.supportedSchemes;
        }
    }

    private class EventHandler
    extends Handler {
        private MediaPlayer mMediaPlayer;

        public EventHandler(MediaPlayer mediaPlayer2, Looper looper) {
            super(looper);
            this.mMediaPlayer = mediaPlayer2;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void handleMessage(Message var1_1) {
            block61 : {
                block62 : {
                    block63 : {
                        if (MediaPlayer.access$800(this.mMediaPlayer) == 0L) {
                            Log.w("MediaPlayer", "mediaplayer went away with unhandled events");
                            return;
                        }
                        var2_3 = var1_1.what;
                        if (var2_3 == 210) break block61;
                        var3_4 = false;
                        var4_5 = false;
                        if (var2_3 == 211) break block62;
                        if (var2_3 == 10000) break block63;
                        switch (var2_3) {
                            default: {
                                switch (var2_3) {
                                    default: {
                                        switch (var2_3) {
                                            default: {
                                                var5_6 = new StringBuilder();
                                                var5_6.append("Unknown message type ");
                                                var5_6.append(var1_1.what);
                                                Log.e("MediaPlayer", var5_6.toString());
                                                return;
                                            }
                                            case 202: {
                                                var5_7 = MediaPlayer.access$2900(MediaPlayer.this);
                                                if (var5_7 == null) {
                                                    return;
                                                }
                                                if (var1_1.obj instanceof Parcel == false) return;
                                                var6_19 = (Parcel)var1_1.obj;
                                                var1_1 = TimedMetaData.createTimedMetaDataFromParcel(var6_19);
                                                var6_19.recycle();
                                                var5_7.onTimedMetaDataAvailable(this.mMediaPlayer, (TimedMetaData)var1_1);
                                                return;
                                            }
                                            case 201: {
                                                // MONITORENTER : this
                                                if (MediaPlayer.access$2400(MediaPlayer.this)) {
                                                    // MONITOREXIT : this
                                                    return;
                                                }
                                                var6_20 = MediaPlayer.access$2500(MediaPlayer.this);
                                                var5_8 = MediaPlayer.access$2600(MediaPlayer.this);
                                                // MONITOREXIT : this
                                                if (var1_1.obj instanceof Parcel == false) return;
                                                var7_24 = (Parcel)var1_1.obj;
                                                var1_1 = new SubtitleData(var7_24);
                                                var7_24.recycle();
                                                MediaPlayer.access$2700(MediaPlayer.this).onSubtitleData(this.mMediaPlayer, (SubtitleData)var1_1);
                                                if (var6_20 == null) return;
                                                if (var5_8 == null) {
                                                    var6_20.onSubtitleData(this.mMediaPlayer, (SubtitleData)var1_1);
                                                    return;
                                                }
                                                var5_8.post(new Runnable((SubtitleData)var1_1){
                                                    final /* synthetic */ SubtitleData val$data;
                                                    {
                                                        this.val$data = subtitleData;
                                                    }

                                                    @Override
                                                    public void run() {
                                                        var6_20.onSubtitleData(EventHandler.this.mMediaPlayer, this.val$data);
                                                    }
                                                });
                                                return;
                                            }
                                            case 200: 
                                        }
                                        var2_3 = var1_1.arg1;
                                        if (var2_3 == 802) ** GOTO lbl86
                                        if (var2_3 == 803) ** GOTO lbl92
                                        switch (var2_3) {
                                            default: {
                                                ** break;
                                            }
                                            case 701: 
                                            case 702: {
                                                var5_9 = MediaPlayer.access$600(MediaPlayer.this);
                                                if (var5_9 == null) break;
                                                if (var1_1.arg1 == 701) {
                                                    var4_5 = true;
                                                }
                                                var5_9.onBuffering(var4_5);
                                                ** break;
                                            }
                                            case 700: {
                                                var5_9 = new StringBuilder();
                                                var5_9.append("Info (");
                                                var5_9.append(var1_1.arg1);
                                                var5_9.append(",");
                                                var5_9.append(var1_1.arg2);
                                                var5_9.append(")");
                                                Log.i("MediaPlayer", var5_9.toString());
                                                ** break;
lbl84: // 3 sources:
                                                break;
                                            }
                                        }
                                        ** GOTO lbl95
lbl86: // 1 sources:
                                        try {
                                            MediaPlayer.access$900(MediaPlayer.this);
                                        }
                                        catch (RuntimeException var5_10) {
                                            this.sendMessage(this.obtainMessage(100, 1, -1010, null));
                                        }
lbl92: // 3 sources:
                                        var1_1.arg1 = 802;
                                        if (MediaPlayer.access$100(MediaPlayer.this) != null) {
                                            MediaPlayer.access$100(MediaPlayer.this).selectDefaultTrack();
                                        }
lbl95: // 4 sources:
                                        if ((var5_9 = MediaPlayer.access$2200(MediaPlayer.this)) == null) return;
                                        var5_9.onInfo(this.mMediaPlayer, var1_1.arg1, var1_1.arg2);
                                        return;
                                    }
                                    case 100: {
                                        var5_11 = new StringBuilder();
                                        var5_11.append("Error (");
                                        var5_11.append(var1_1.arg1);
                                        var5_11.append(",");
                                        var5_11.append(var1_1.arg2);
                                        var5_11.append(")");
                                        Log.e("MediaPlayer", var5_11.toString());
                                        var4_5 = false;
                                        var5_11 = MediaPlayer.access$2100(MediaPlayer.this);
                                        if (var5_11 != null) {
                                            var4_5 = var5_11.onError(this.mMediaPlayer, var1_1.arg1, var1_1.arg2);
                                        }
                                        MediaPlayer.access$1500(MediaPlayer.this).onCompletion(this.mMediaPlayer);
                                        var1_1 = MediaPlayer.access$1600(MediaPlayer.this);
                                        if (var1_1 != null && !var4_5) {
                                            var1_1.onCompletion(this.mMediaPlayer);
                                        }
                                        MediaPlayer.access$1700(MediaPlayer.this, false);
                                        return;
                                    }
                                    case 99: {
                                        var5_12 = MediaPlayer.access$2300(MediaPlayer.this);
                                        if (var5_12 == null) {
                                            return;
                                        }
                                        if (var1_1.obj == null) {
                                            var5_12.onTimedText(this.mMediaPlayer, null);
                                            return;
                                        }
                                        if (var1_1.obj instanceof Parcel == false) return;
                                        var6_21 = (Parcel)var1_1.obj;
                                        var1_1 = new TimedText(var6_21);
                                        var6_21.recycle();
                                        var5_12.onTimedText(this.mMediaPlayer, (TimedText)var1_1);
                                        return;
                                    }
                                    case 98: 
                                }
                                var1_1 = MediaPlayer.access$600(MediaPlayer.this);
                                if (var1_1 == null) return;
                                var1_1.onNotifyTime();
                                return;
                            }
                            case 8: {
                                var1_1 = MediaPlayer.access$600(MediaPlayer.this);
                                if (var1_1 == null) return;
                                var1_1.onStopped();
                                return;
                            }
                            case 6: 
                            case 7: {
                                var5_13 = MediaPlayer.access$600(MediaPlayer.this);
                                if (var5_13 == null) return;
                                var4_5 = var3_4;
                                if (var1_1.what == 7) {
                                    var4_5 = true;
                                }
                                var5_13.onPaused(var4_5);
                                return;
                            }
                            case 5: {
                                var5_14 = MediaPlayer.access$2000(MediaPlayer.this);
                                if (var5_14 == null) return;
                                var5_14.onVideoSizeChanged(this.mMediaPlayer, var1_1.arg1, var1_1.arg2);
                                return;
                            }
                            case 4: {
                                var1_1 = MediaPlayer.access$1900(MediaPlayer.this);
                                if (var1_1 != null) {
                                    var1_1.onSeekComplete(this.mMediaPlayer);
                                }
                            }
                            case 9: {
                                var1_1 = MediaPlayer.access$600(MediaPlayer.this);
                                if (var1_1 == null) return;
                                var1_1.onSeekComplete(this.mMediaPlayer);
                                return;
                            }
                            case 3: {
                                var5_15 = MediaPlayer.access$1800(MediaPlayer.this);
                                if (var5_15 == null) return;
                                var5_15.onBufferingUpdate(this.mMediaPlayer, var1_1.arg1);
                                return;
                            }
                            case 2: {
                                MediaPlayer.access$1500(MediaPlayer.this).onCompletion(this.mMediaPlayer);
                                var1_1 = MediaPlayer.access$1600(MediaPlayer.this);
                                if (var1_1 != null) {
                                    var1_1.onCompletion(this.mMediaPlayer);
                                }
                                MediaPlayer.access$1700(MediaPlayer.this, false);
                                return;
                            }
                            case 1: {
                                try {
                                    MediaPlayer.access$900(MediaPlayer.this);
                                }
                                catch (RuntimeException var1_2) {
                                    this.sendMessage(this.obtainMessage(100, 1, -1010, null));
                                }
                                var1_1 = MediaPlayer.access$1000(MediaPlayer.this);
                                if (var1_1 == null) return;
                                var1_1.onPrepared(this.mMediaPlayer);
                                return;
                            }
                            case 0: 
                        }
                        return;
                    }
                    AudioManager.resetAudioPortGeneration();
                    var1_1 = MediaPlayer.access$3000(MediaPlayer.this);
                    // MONITORENTER : var1_1
                    var5_16 = MediaPlayer.access$3000(MediaPlayer.this).values().iterator();
                    do {
                        if (!var5_16.hasNext()) {
                            // MONITOREXIT : var1_1
                            return;
                        }
                        ((NativeRoutingEventHandlerDelegate)var5_16.next()).notifyClient();
                    } while (true);
                }
                // MONITORENTER : this
                var5_17 = MediaPlayer.access$3100(MediaPlayer.this);
                var6_22 = MediaPlayer.access$3200(MediaPlayer.this);
                // MONITOREXIT : this
                if (var5_17 == null) {
                    return;
                }
                if (var1_1.obj instanceof Parcel == false) return;
                var1_1 = (Parcel)var1_1.obj;
                var1_1.setDataPosition(0);
                var8_25 = var1_1.readLong();
                var10_26 = var1_1.readLong();
                var12_27 = var1_1.readFloat();
                var1_1.recycle();
                var1_1 = var8_25 != -1L && var10_26 != -1L ? new MediaTimestamp(var8_25, var10_26 * 1000L, var12_27) : MediaTimestamp.TIMESTAMP_UNKNOWN;
                if (var6_22 == null) {
                    var5_17.onMediaTimeDiscontinuity(this.mMediaPlayer, (MediaTimestamp)var1_1);
                    return;
                }
                var6_22.post(new Runnable((MediaTimestamp)var1_1){
                    final /* synthetic */ MediaTimestamp val$timestamp;
                    {
                        this.val$timestamp = mediaTimestamp;
                    }

                    @Override
                    public void run() {
                        var5_17.onMediaTimeDiscontinuity(EventHandler.this.mMediaPlayer, this.val$timestamp);
                    }
                });
                return;
            }
            var5_18 = new StringBuilder();
            var5_18.append("MEDIA_DRM_INFO ");
            var5_18.append(MediaPlayer.access$1100(MediaPlayer.this));
            Log.v("MediaPlayer", var5_18.toString());
            if (var1_1.obj == null) {
                Log.w("MediaPlayer", "MEDIA_DRM_INFO msg.obj=NULL");
                return;
            }
            if (!(var1_1.obj instanceof Parcel)) {
                var5_18 = new StringBuilder();
                var5_18.append("MEDIA_DRM_INFO msg.obj of unexpected type ");
                var5_18.append(var1_1.obj);
                Log.w("MediaPlayer", var5_18.toString());
                return;
            }
            var5_18 = null;
            var6_23 = MediaPlayer.access$1200(MediaPlayer.this);
            // MONITORENTER : var6_23
            var1_1 = var5_18;
            if (MediaPlayer.access$1100(MediaPlayer.this) != null) {
                var1_1 = var5_18;
                if (MediaPlayer.access$1300(MediaPlayer.this) != null) {
                    var1_1 = DrmInfo.access$1400(MediaPlayer.access$1300(MediaPlayer.this));
                }
            }
            var5_18 = MediaPlayer.access$1100(MediaPlayer.this);
            // MONITOREXIT : var6_23
            if (var5_18 == null) return;
            var5_18.notifyClient((DrmInfo)var1_1);
        }

    }

    public static final class MetricsConstants {
        public static final String CODEC_AUDIO = "android.media.mediaplayer.audio.codec";
        public static final String CODEC_VIDEO = "android.media.mediaplayer.video.codec";
        public static final String DURATION = "android.media.mediaplayer.durationMs";
        public static final String ERRORS = "android.media.mediaplayer.err";
        public static final String ERROR_CODE = "android.media.mediaplayer.errcode";
        public static final String FRAMES = "android.media.mediaplayer.frames";
        public static final String FRAMES_DROPPED = "android.media.mediaplayer.dropped";
        public static final String HEIGHT = "android.media.mediaplayer.height";
        public static final String MIME_TYPE_AUDIO = "android.media.mediaplayer.audio.mime";
        public static final String MIME_TYPE_VIDEO = "android.media.mediaplayer.video.mime";
        public static final String PLAYING = "android.media.mediaplayer.playingMs";
        public static final String WIDTH = "android.media.mediaplayer.width";

        private MetricsConstants() {
        }
    }

    public static final class NoDrmSchemeException
    extends MediaDrmException {
        public NoDrmSchemeException(String string2) {
            super(string2);
        }
    }

    public static interface OnBufferingUpdateListener {
        public void onBufferingUpdate(MediaPlayer var1, int var2);
    }

    public static interface OnCompletionListener {
        public void onCompletion(MediaPlayer var1);
    }

    public static interface OnDrmConfigHelper {
        public void onDrmConfig(MediaPlayer var1);
    }

    private class OnDrmInfoHandlerDelegate {
        private Handler mHandler;
        private MediaPlayer mMediaPlayer;
        private OnDrmInfoListener mOnDrmInfoListener;

        OnDrmInfoHandlerDelegate(MediaPlayer mediaPlayer2, OnDrmInfoListener onDrmInfoListener, Handler handler) {
            this.mMediaPlayer = mediaPlayer2;
            this.mOnDrmInfoListener = onDrmInfoListener;
            if (handler != null) {
                this.mHandler = handler;
            }
        }

        void notifyClient(final DrmInfo drmInfo) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        OnDrmInfoHandlerDelegate.this.mOnDrmInfoListener.onDrmInfo(OnDrmInfoHandlerDelegate.this.mMediaPlayer, drmInfo);
                    }
                });
            } else {
                this.mOnDrmInfoListener.onDrmInfo(this.mMediaPlayer, drmInfo);
            }
        }

    }

    public static interface OnDrmInfoListener {
        public void onDrmInfo(MediaPlayer var1, DrmInfo var2);
    }

    private class OnDrmPreparedHandlerDelegate {
        private Handler mHandler;
        private MediaPlayer mMediaPlayer;
        private OnDrmPreparedListener mOnDrmPreparedListener;

        OnDrmPreparedHandlerDelegate(MediaPlayer mediaPlayer2, OnDrmPreparedListener onDrmPreparedListener, Handler handler) {
            this.mMediaPlayer = mediaPlayer2;
            this.mOnDrmPreparedListener = onDrmPreparedListener;
            if (handler != null) {
                this.mHandler = handler;
            } else if (MediaPlayer.this.mEventHandler != null) {
                this.mHandler = MediaPlayer.this.mEventHandler;
            } else {
                Log.e(MediaPlayer.TAG, "OnDrmPreparedHandlerDelegate: Unexpected null mEventHandler");
            }
        }

        void notifyClient(final int n) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        OnDrmPreparedHandlerDelegate.this.mOnDrmPreparedListener.onDrmPrepared(OnDrmPreparedHandlerDelegate.this.mMediaPlayer, n);
                    }
                });
            } else {
                Log.e(MediaPlayer.TAG, "OnDrmPreparedHandlerDelegate:notifyClient: Unexpected null mHandler");
            }
        }

    }

    public static interface OnDrmPreparedListener {
        public void onDrmPrepared(MediaPlayer var1, int var2);
    }

    public static interface OnErrorListener {
        public boolean onError(MediaPlayer var1, int var2, int var3);
    }

    public static interface OnInfoListener {
        public boolean onInfo(MediaPlayer var1, int var2, int var3);
    }

    public static interface OnMediaTimeDiscontinuityListener {
        public void onMediaTimeDiscontinuity(MediaPlayer var1, MediaTimestamp var2);
    }

    public static interface OnPreparedListener {
        public void onPrepared(MediaPlayer var1);
    }

    public static interface OnSeekCompleteListener {
        public void onSeekComplete(MediaPlayer var1);
    }

    public static interface OnSubtitleDataListener {
        public void onSubtitleData(MediaPlayer var1, SubtitleData var2);
    }

    public static interface OnTimedMetaDataAvailableListener {
        public void onTimedMetaDataAvailable(MediaPlayer var1, TimedMetaData var2);
    }

    public static interface OnTimedTextListener {
        public void onTimedText(MediaPlayer var1, TimedText var2);
    }

    public static interface OnVideoSizeChangedListener {
        public void onVideoSizeChanged(MediaPlayer var1, int var2, int var3);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PlaybackRateAudioMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PrepareDrmStatusCode {
    }

    public static final class ProvisioningNetworkErrorException
    extends MediaDrmException {
        public ProvisioningNetworkErrorException(String string2) {
            super(string2);
        }
    }

    public static final class ProvisioningServerErrorException
    extends MediaDrmException {
        public ProvisioningServerErrorException(String string2) {
            super(string2);
        }
    }

    private class ProvisioningThread
    extends Thread {
        public static final int TIMEOUT_MS = 60000;
        private Object drmLock;
        private boolean finished;
        private MediaPlayer mediaPlayer;
        private OnDrmPreparedHandlerDelegate onDrmPreparedHandlerDelegate;
        private int status;
        private String urlStr;
        private UUID uuid;

        private ProvisioningThread() {
        }

        public ProvisioningThread initialize(MediaDrm.ProvisionRequest object, UUID uUID, MediaPlayer object2) {
            this.drmLock = ((MediaPlayer)object2).mDrmLock;
            this.onDrmPreparedHandlerDelegate = ((MediaPlayer)object2).mOnDrmPreparedHandlerDelegate;
            this.mediaPlayer = object2;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(((MediaDrm.ProvisionRequest)object).getDefaultUrl());
            ((StringBuilder)object2).append("&signedRequest=");
            ((StringBuilder)object2).append(new String(((MediaDrm.ProvisionRequest)object).getData()));
            this.urlStr = ((StringBuilder)object2).toString();
            this.uuid = uUID;
            this.status = 3;
            object = new StringBuilder();
            ((StringBuilder)object).append("HandleProvisioninig: Thread is initialised url: ");
            ((StringBuilder)object).append(this.urlStr);
            Log.v(MediaPlayer.TAG, ((StringBuilder)object).toString());
            return this;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            var1_1 = null;
            var2_2 = null;
            var3_5 = null;
            var4_6 = 0;
            var5_7 = var2_2;
            var5_7 = var2_2;
            var6_9 = new URL(this.urlStr);
            var5_7 = var2_2;
            var7_10 = (HttpURLConnection)var6_9.openConnection();
            var8_11 = var3_5;
            var2_2 = var1_1;
            var7_10.setRequestMethod("POST");
            var8_11 = var3_5;
            var2_2 = var1_1;
            var7_10.setDoOutput(false);
            var8_11 = var3_5;
            var2_2 = var1_1;
            var7_10.setDoInput(true);
            var8_11 = var3_5;
            var2_2 = var1_1;
            var7_10.setConnectTimeout(60000);
            var8_11 = var3_5;
            var2_2 = var1_1;
            var7_10.setReadTimeout(60000);
            var8_11 = var3_5;
            var2_2 = var1_1;
            var7_10.connect();
            var8_11 = var3_5;
            var2_2 = var1_1;
            var8_11 = var3_5 = Streams.readFully((InputStream)var7_10.getInputStream());
            var2_2 = var3_5;
            var8_11 = var3_5;
            var2_2 = var3_5;
            var5_7 = new StringBuilder();
            var8_11 = var3_5;
            var2_2 = var3_5;
            var5_7.append("HandleProvisioninig: Thread run: response ");
            var8_11 = var3_5;
            var2_2 = var3_5;
            var5_7.append(var3_5.length);
            var8_11 = var3_5;
            var2_2 = var3_5;
            var5_7.append(" ");
            var8_11 = var3_5;
            var2_2 = var3_5;
            var5_7.append(var3_5);
            var8_11 = var3_5;
            var2_2 = var3_5;
            Log.v("MediaPlayer", var5_7.toString());
            var5_7 = var3_5;
            var7_10.disconnect();
            var2_2 = var3_5;
            ** GOTO lbl93
            {
                catch (Throwable var2_3) {
                    ** GOTO lbl89
                }
                catch (Exception var5_8) {}
                var8_11 = var2_2;
                {
                    this.status = 1;
                    var8_11 = var2_2;
                    var8_11 = var2_2;
                    var3_5 = new StringBuilder();
                    var8_11 = var2_2;
                    var3_5.append("HandleProvisioninig: Thread run: connect ");
                    var8_11 = var2_2;
                    var3_5.append(var5_8);
                    var8_11 = var2_2;
                    var3_5.append(" url: ");
                    var8_11 = var2_2;
                    var3_5.append(var6_9);
                    var8_11 = var2_2;
                    Log.w("MediaPlayer", var3_5.toString());
                    var5_7 = var2_2;
                }
                try {
                    block22 : {
                        var7_10.disconnect();
                        break block22;
lbl89: // 1 sources:
                        var5_7 = var8_11;
                        var7_10.disconnect();
                        var5_7 = var8_11;
                        throw var2_3;
                    }
                    var5_7 = var2_2;
                }
                catch (Exception var8_12) {
                    this.status = 1;
                    var2_2 = new StringBuilder();
                    var2_2.append("HandleProvisioninig: Thread run: openConnection ");
                    var2_2.append(var8_12);
                    Log.w("MediaPlayer", var2_2.toString());
                }
            }
            var9_13 = var4_6;
            if (var5_7 != null) {
                try {
                    MediaPlayer.access$3900(MediaPlayer.this).provideProvisionResponse((byte[])var5_7);
                    Log.v("MediaPlayer", "HandleProvisioninig: Thread run: provideProvisionResponse SUCCEEDED!");
                    var9_13 = 1;
                }
                catch (Exception var2_4) {
                    this.status = 2;
                    var5_7 = new StringBuilder();
                    var5_7.append("HandleProvisioninig: Thread run: provideProvisionResponse ");
                    var5_7.append(var2_4);
                    Log.w("MediaPlayer", var5_7.toString());
                    var9_13 = var4_6;
                }
            }
            var10_14 = false;
            var11_15 = false;
            var5_7 = this.onDrmPreparedHandlerDelegate;
            var4_6 = 3;
            if (var5_7 != null) {
                var2_2 = this.drmLock;
                // MONITORENTER : var2_2
                if (var9_13 != 0) {
                    var11_15 = MediaPlayer.access$4000(this.mediaPlayer, this.uuid);
                    if (var11_15) {
                        var4_6 = 0;
                    }
                    this.status = var4_6;
                }
                MediaPlayer.access$4102(this.mediaPlayer, false);
                MediaPlayer.access$4202(this.mediaPlayer, false);
                if (!var11_15) {
                    MediaPlayer.access$4300(MediaPlayer.this);
                }
                // MONITOREXIT : var2_2
                this.onDrmPreparedHandlerDelegate.notifyClient(this.status);
            } else {
                var11_15 = var10_14;
                if (var9_13 != 0) {
                    var11_15 = MediaPlayer.access$4000(this.mediaPlayer, this.uuid);
                    if (var11_15) {
                        var4_6 = 0;
                    }
                    this.status = var4_6;
                }
                MediaPlayer.access$4102(this.mediaPlayer, false);
                MediaPlayer.access$4202(this.mediaPlayer, false);
                if (!var11_15) {
                    MediaPlayer.access$4300(MediaPlayer.this);
                }
            }
            this.finished = true;
        }

        public int status() {
            return this.status;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SeekMode {
    }

    static class TimeProvider
    implements OnSeekCompleteListener,
    MediaTimeProvider {
        private static final long MAX_EARLY_CALLBACK_US = 1000L;
        private static final long MAX_NS_WITHOUT_POSITION_CHECK = 5000000000L;
        private static final int NOTIFY = 1;
        private static final int NOTIFY_SEEK = 3;
        private static final int NOTIFY_STOP = 2;
        private static final int NOTIFY_TIME = 0;
        private static final int NOTIFY_TRACK_DATA = 4;
        private static final String TAG = "MTP";
        private static final long TIME_ADJUSTMENT_RATE = 2L;
        public boolean DEBUG = false;
        private boolean mBuffering;
        private Handler mEventHandler;
        private HandlerThread mHandlerThread;
        private long mLastReportedTime;
        private long mLastTimeUs = 0L;
        private MediaTimeProvider.OnMediaTimeListener[] mListeners;
        private boolean mPaused = true;
        private boolean mPausing = false;
        private MediaPlayer mPlayer;
        private boolean mRefresh = false;
        private boolean mSeeking = false;
        private boolean mStopped = true;
        private long[] mTimes;

        public TimeProvider(MediaPlayer object) {
            this.mPlayer = object;
            try {
                this.getCurrentTimeUs(true, false);
            }
            catch (IllegalStateException illegalStateException) {
                this.mRefresh = true;
            }
            Looper looper = Looper.myLooper();
            object = looper;
            if (looper == null) {
                looper = Looper.getMainLooper();
                object = looper;
                if (looper == null) {
                    this.mHandlerThread = new HandlerThread("MediaPlayerMTPEventThread", -2);
                    this.mHandlerThread.start();
                    object = this.mHandlerThread.getLooper();
                }
            }
            this.mEventHandler = new EventHandler((Looper)object);
            this.mListeners = new MediaTimeProvider.OnMediaTimeListener[0];
            this.mTimes = new long[0];
            this.mLastTimeUs = 0L;
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void notifySeek() {
            synchronized (this) {
                this.mSeeking = false;
                try {
                    long l = this.getCurrentTimeUs(true, false);
                    if (this.DEBUG) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("onSeekComplete at ");
                        stringBuilder.append(l);
                        Log.d(TAG, stringBuilder.toString());
                    }
                    for (MediaTimeProvider.OnMediaTimeListener onMediaTimeListener : this.mListeners) {
                        if (onMediaTimeListener == null) break;
                        onMediaTimeListener.onSeek(l);
                    }
                }
                catch (IllegalStateException illegalStateException) {
                    if (this.DEBUG) {
                        Log.d(TAG, "onSeekComplete but no player");
                    }
                    this.mPausing = true;
                    this.notifyTimedEvent(false);
                }
                return;
            }
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void notifyStop() {
            synchronized (this) {
                for (MediaTimeProvider.OnMediaTimeListener onMediaTimeListener : this.mListeners) {
                    if (onMediaTimeListener == null) break;
                    onMediaTimeListener.onStop();
                }
                return;
            }
        }

        /*
         * Exception decompiling
         */
        private void notifyTimedEvent(boolean var1_1) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[TRYBLOCK]], but top level block is 10[FORLOOP]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        private void notifyTrackData(Pair<SubtitleTrack, byte[]> pair) {
            synchronized (this) {
                ((SubtitleTrack)pair.first).onData((byte[])pair.second, true, -1L);
                return;
            }
        }

        private int registerListener(MediaTimeProvider.OnMediaTimeListener onMediaTimeListener) {
            int n;
            MediaTimeProvider.OnMediaTimeListener[] arronMediaTimeListener;
            for (n = 0; n < (arronMediaTimeListener = this.mListeners).length && arronMediaTimeListener[n] != onMediaTimeListener && arronMediaTimeListener[n] != null; ++n) {
            }
            Object[] arrobject = this.mListeners;
            if (n >= arrobject.length) {
                arronMediaTimeListener = new MediaTimeProvider.OnMediaTimeListener[n + 1];
                long[] arrl = new long[n + 1];
                System.arraycopy(arrobject, 0, arronMediaTimeListener, 0, arrobject.length);
                arrobject = this.mTimes;
                System.arraycopy(arrobject, 0, arrl, 0, arrobject.length);
                this.mListeners = arronMediaTimeListener;
                this.mTimes = arrl;
            }
            if ((arronMediaTimeListener = this.mListeners)[n] == null) {
                arronMediaTimeListener[n] = onMediaTimeListener;
                this.mTimes[n] = -1L;
            }
            return n;
        }

        private void scheduleNotification(int n, long l) {
            Object object;
            if (this.mSeeking && n == 0) {
                return;
            }
            if (this.DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("scheduleNotification ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" in ");
                ((StringBuilder)object).append(l);
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            this.mEventHandler.removeMessages(1);
            object = this.mEventHandler.obtainMessage(1, n, 0);
            this.mEventHandler.sendMessageDelayed((Message)object, (int)(l / 1000L));
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void cancelNotifications(MediaTimeProvider.OnMediaTimeListener onMediaTimeListener) {
            synchronized (this) {
                for (int i = 0; i < this.mListeners.length; ++i) {
                    if (this.mListeners[i] == onMediaTimeListener) {
                        System.arraycopy(this.mListeners, i + 1, this.mListeners, i, this.mListeners.length - i - 1);
                        System.arraycopy(this.mTimes, i + 1, this.mTimes, i, this.mTimes.length - i - 1);
                        this.mListeners[this.mListeners.length - 1] = null;
                        this.mTimes[this.mTimes.length - 1] = -1L;
                        break;
                    }
                    if (this.mListeners[i] == null) break;
                }
                this.scheduleNotification(0, 0L);
                return;
            }
        }

        public void close() {
            this.mEventHandler.removeMessages(1);
            HandlerThread handlerThread = this.mHandlerThread;
            if (handlerThread != null) {
                handlerThread.quitSafely();
                this.mHandlerThread = null;
            }
        }

        protected void finalize() {
            HandlerThread handlerThread = this.mHandlerThread;
            if (handlerThread != null) {
                handlerThread.quitSafely();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public long getCurrentTimeUs(boolean bl, boolean bl2) throws IllegalStateException {
            synchronized (this) {
                block8 : {
                    if (this.mPaused && !bl) {
                        return this.mLastReportedTime;
                    }
                    try {
                        this.mLastTimeUs = (long)this.mPlayer.getCurrentPosition() * 1000L;
                        bl = !this.mPlayer.isPlaying() || this.mBuffering;
                        this.mPaused = bl;
                        if (this.DEBUG) {
                            StringBuilder stringBuilder = new StringBuilder();
                            String string2 = this.mPaused ? "paused" : "playing";
                            stringBuilder.append(string2);
                            stringBuilder.append(" at ");
                            stringBuilder.append(this.mLastTimeUs);
                            Log.v(TAG, stringBuilder.toString());
                        }
                        if (!bl2 || this.mLastTimeUs >= this.mLastReportedTime) break block8;
                        if (this.mLastReportedTime - this.mLastTimeUs <= 1000000L) return this.mLastReportedTime;
                    }
                    catch (IllegalStateException illegalStateException) {
                        if (!this.mPausing) {
                            throw illegalStateException;
                        }
                        this.mPausing = false;
                        if (!bl2 || this.mLastReportedTime < this.mLastTimeUs) {
                            this.mLastReportedTime = this.mLastTimeUs;
                        }
                        this.mPaused = true;
                        if (!this.DEBUG) return this.mLastReportedTime;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("illegal state, but pausing: estimating at ");
                        stringBuilder.append(this.mLastReportedTime);
                        Log.d(TAG, stringBuilder.toString());
                        return this.mLastReportedTime;
                    }
                    this.mStopped = false;
                    this.mSeeking = true;
                    this.scheduleNotification(3, 0L);
                    return this.mLastReportedTime;
                }
                this.mLastReportedTime = this.mLastTimeUs;
                return this.mLastReportedTime;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void notifyAt(long l, MediaTimeProvider.OnMediaTimeListener onMediaTimeListener) {
            synchronized (this) {
                if (this.DEBUG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("notifyAt ");
                    stringBuilder.append(l);
                    Log.d(TAG, stringBuilder.toString());
                }
                this.mTimes[this.registerListener((MediaTimeProvider.OnMediaTimeListener)onMediaTimeListener)] = l;
                this.scheduleNotification(0, 0L);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onBuffering(boolean bl) {
            synchronized (this) {
                if (this.DEBUG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onBuffering: ");
                    stringBuilder.append(bl);
                    Log.d(TAG, stringBuilder.toString());
                }
                this.mBuffering = bl;
                this.scheduleNotification(0, 0L);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onNewPlayer() {
            if (!this.mRefresh) return;
            synchronized (this) {
                this.mStopped = false;
                this.mSeeking = true;
                this.mBuffering = false;
                this.scheduleNotification(3, 0L);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onNotifyTime() {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.d(TAG, "onNotifyTime: ");
                }
                this.scheduleNotification(0, 0L);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onPaused(boolean bl) {
            synchronized (this) {
                if (this.DEBUG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onPaused: ");
                    stringBuilder.append(bl);
                    Log.d(TAG, stringBuilder.toString());
                }
                if (this.mStopped) {
                    this.mStopped = false;
                    this.mSeeking = true;
                    this.scheduleNotification(3, 0L);
                } else {
                    this.mPausing = bl;
                    this.mSeeking = false;
                    this.scheduleNotification(0, 0L);
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
        public void onSeekComplete(MediaPlayer mediaPlayer) {
            synchronized (this) {
                this.mStopped = false;
                this.mSeeking = true;
                this.scheduleNotification(3, 0L);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onStopped() {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.d(TAG, "onStopped");
                }
                this.mPaused = true;
                this.mStopped = true;
                this.mSeeking = false;
                this.mBuffering = false;
                this.scheduleNotification(2, 0L);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void scheduleUpdate(MediaTimeProvider.OnMediaTimeListener onMediaTimeListener) {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.d(TAG, "scheduleUpdate");
                }
                int n = this.registerListener(onMediaTimeListener);
                if (!this.mStopped) {
                    this.mTimes[n] = 0L;
                    this.scheduleNotification(0, 0L);
                }
                return;
            }
        }

        private class EventHandler
        extends Handler {
            public EventHandler(Looper looper) {
                super(looper);
            }

            @Override
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    int n = message.arg1;
                    if (n != 0) {
                        if (n != 2) {
                            if (n != 3) {
                                if (n == 4) {
                                    TimeProvider.this.notifyTrackData((Pair)message.obj);
                                }
                            } else {
                                TimeProvider.this.notifySeek();
                            }
                        } else {
                            TimeProvider.this.notifyStop();
                        }
                    } else {
                        TimeProvider.this.notifyTimedEvent(true);
                    }
                }
            }
        }

    }

    public static class TrackInfo
    implements Parcelable {
        @UnsupportedAppUsage
        static final Parcelable.Creator<TrackInfo> CREATOR = new Parcelable.Creator<TrackInfo>(){

            @Override
            public TrackInfo createFromParcel(Parcel parcel) {
                return new TrackInfo(parcel);
            }

            public TrackInfo[] newArray(int n) {
                return new TrackInfo[n];
            }
        };
        public static final int MEDIA_TRACK_TYPE_AUDIO = 2;
        public static final int MEDIA_TRACK_TYPE_METADATA = 5;
        public static final int MEDIA_TRACK_TYPE_SUBTITLE = 4;
        public static final int MEDIA_TRACK_TYPE_TIMEDTEXT = 3;
        public static final int MEDIA_TRACK_TYPE_UNKNOWN = 0;
        public static final int MEDIA_TRACK_TYPE_VIDEO = 1;
        final MediaFormat mFormat;
        final int mTrackType;

        TrackInfo(int n, MediaFormat mediaFormat) {
            this.mTrackType = n;
            this.mFormat = mediaFormat;
        }

        TrackInfo(Parcel parcel) {
            this.mTrackType = parcel.readInt();
            this.mFormat = MediaFormat.createSubtitleFormat(parcel.readString(), parcel.readString());
            if (this.mTrackType == 4) {
                this.mFormat.setInteger("is-autoselect", parcel.readInt());
                this.mFormat.setInteger("is-default", parcel.readInt());
                this.mFormat.setInteger("is-forced-subtitle", parcel.readInt());
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public MediaFormat getFormat() {
            int n = this.mTrackType;
            if (n != 3 && n != 4) {
                return null;
            }
            return this.mFormat;
        }

        public String getLanguage() {
            String string2;
            block0 : {
                string2 = this.mFormat.getString("language");
                if (string2 != null) break block0;
                string2 = "und";
            }
            return string2;
        }

        public int getTrackType() {
            return this.mTrackType;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append(this.getClass().getName());
            stringBuilder.append('{');
            int n = this.mTrackType;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            stringBuilder.append("UNKNOWN");
                        } else {
                            stringBuilder.append("SUBTITLE");
                        }
                    } else {
                        stringBuilder.append("TIMEDTEXT");
                    }
                } else {
                    stringBuilder.append("AUDIO");
                }
            } else {
                stringBuilder.append("VIDEO");
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", ");
            stringBuilder2.append(this.mFormat.toString());
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mTrackType);
            parcel.writeString(this.mFormat.getString("mime"));
            parcel.writeString(this.getLanguage());
            if (this.mTrackType == 4) {
                parcel.writeInt(this.mFormat.getInteger("is-autoselect"));
                parcel.writeInt(this.mFormat.getInteger("is-default"));
                parcel.writeInt(this.mFormat.getInteger("is-forced-subtitle"));
            }
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface TrackType {
        }

    }

}

