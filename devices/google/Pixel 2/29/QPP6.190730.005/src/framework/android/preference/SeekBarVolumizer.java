/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.preference.-$
 *  android.preference.-$$Lambda
 *  android.preference.-$$Lambda$SeekBarVolumizer
 *  android.preference.-$$Lambda$SeekBarVolumizer$ezNr2aLs33rVlzIsAVW8OXqqpIs
 *  android.preference.-$$Lambda$SeekBarVolumizer$pv2-5S-FjgAtIix6Vp68yZJoqvQ
 *  android.preference.-$$Lambda$_14QHG018Z6p13d3hzJuGTWnNeo
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.audiopolicy.AudioProductStrategy;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.-$;
import android.preference.VolumePreference;
import android.preference._$$Lambda$SeekBarVolumizer$ezNr2aLs33rVlzIsAVW8OXqqpIs;
import android.preference._$$Lambda$SeekBarVolumizer$pv2_5S_FjgAtIix6Vp68yZJoqvQ;
import android.preference._$$Lambda$_14QHG018Z6p13d3hzJuGTWnNeo;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.util.Log;
import android.widget.SeekBar;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.SomeArgs;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Deprecated
public class SeekBarVolumizer
implements SeekBar.OnSeekBarChangeListener,
Handler.Callback {
    private static final int CHECK_RINGTONE_PLAYBACK_DELAY_MS = 1000;
    private static final int MSG_GROUP_VOLUME_CHANGED = 1;
    private static final int MSG_INIT_SAMPLE = 3;
    private static final int MSG_SET_STREAM_VOLUME = 0;
    private static final int MSG_START_SAMPLE = 1;
    private static final int MSG_STOP_SAMPLE = 2;
    private static final String TAG = "SeekBarVolumizer";
    private boolean mAffectedByRingerMode;
    private boolean mAllowAlarms;
    private boolean mAllowMedia;
    private boolean mAllowRinger;
    private AudioAttributes mAttributes;
    @UnsupportedAppUsage
    private final AudioManager mAudioManager;
    private final Callback mCallback;
    @UnsupportedAppUsage
    private final Context mContext;
    private final Uri mDefaultUri;
    private Handler mHandler;
    private int mLastAudibleStreamVolume;
    @UnsupportedAppUsage
    private int mLastProgress = -1;
    private final int mMaxStreamVolume;
    private boolean mMuted;
    private final NotificationManager mNotificationManager;
    private boolean mNotificationOrRing;
    private NotificationManager.Policy mNotificationPolicy;
    @UnsupportedAppUsage
    private int mOriginalStreamVolume;
    private boolean mPlaySample;
    private final Receiver mReceiver = new Receiver();
    private int mRingerMode;
    @UnsupportedAppUsage
    @GuardedBy(value={"this"})
    private Ringtone mRingtone;
    @UnsupportedAppUsage
    private SeekBar mSeekBar;
    @UnsupportedAppUsage
    private final int mStreamType;
    private final H mUiHandler = new H();
    private int mVolumeBeforeMute = -1;
    private final AudioManager.VolumeGroupCallback mVolumeGroupCallback = new AudioManager.VolumeGroupCallback(){

        @Override
        public void onAudioVolumeGroupChanged(int n, int n2) {
            if (SeekBarVolumizer.this.mHandler == null) {
                return;
            }
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = n;
            someArgs.arg2 = n2;
            SeekBarVolumizer.this.mVolumeHandler.sendMessage(SeekBarVolumizer.this.mHandler.obtainMessage(1, someArgs));
        }
    };
    private int mVolumeGroupId;
    private final Handler mVolumeHandler = new VolumeHandler();
    private Observer mVolumeObserver;
    private int mZenMode;

    @UnsupportedAppUsage
    public SeekBarVolumizer(Context context, int n, Uri uri, Callback callback) {
        this(context, n, uri, callback, true);
    }

    public SeekBarVolumizer(Context object, int n, Uri uri, Callback callback, boolean bl) {
        this.mContext = object;
        this.mAudioManager = ((Context)object).getSystemService(AudioManager.class);
        this.mNotificationManager = ((Context)object).getSystemService(NotificationManager.class);
        this.mNotificationPolicy = this.mNotificationManager.getConsolidatedNotificationPolicy();
        int n2 = this.mNotificationPolicy.priorityCategories;
        boolean bl2 = false;
        boolean bl3 = (n2 & 32) != 0;
        this.mAllowAlarms = bl3;
        bl3 = bl2;
        if ((this.mNotificationPolicy.priorityCategories & 64) != 0) {
            bl3 = true;
        }
        this.mAllowMedia = bl3;
        this.mAllowRinger = ZenModeConfig.areAllPriorityOnlyNotificationZenSoundsMuted(this.mNotificationPolicy) ^ true;
        this.mStreamType = n;
        this.mAffectedByRingerMode = this.mAudioManager.isStreamAffectedByRingerMode(this.mStreamType);
        this.mNotificationOrRing = SeekBarVolumizer.isNotificationOrRing(this.mStreamType);
        if (this.mNotificationOrRing) {
            this.mRingerMode = this.mAudioManager.getRingerModeInternal();
        }
        this.mZenMode = this.mNotificationManager.getZenMode();
        if (this.hasAudioProductStrategies()) {
            this.mVolumeGroupId = this.getVolumeGroupIdForLegacyStreamType(this.mStreamType);
            this.mAttributes = this.getAudioAttributesForLegacyStreamType(this.mStreamType);
        }
        this.mMaxStreamVolume = this.mAudioManager.getStreamMaxVolume(this.mStreamType);
        this.mCallback = callback;
        this.mOriginalStreamVolume = this.mAudioManager.getStreamVolume(this.mStreamType);
        this.mLastAudibleStreamVolume = this.mAudioManager.getLastAudibleStreamVolume(this.mStreamType);
        this.mMuted = this.mAudioManager.isStreamMute(this.mStreamType);
        this.mPlaySample = bl;
        object = this.mCallback;
        if (object != null) {
            object.onMuted(this.mMuted, this.isZenMuted());
        }
        object = uri;
        if (uri == null) {
            n = this.mStreamType;
            object = n == 2 ? Settings.System.DEFAULT_RINGTONE_URI : (n == 5 ? Settings.System.DEFAULT_NOTIFICATION_URI : Settings.System.DEFAULT_ALARM_ALERT_URI);
        }
        this.mDefaultUri = object;
    }

    private AudioAttributes getAudioAttributesForLegacyStreamType(int n) {
        Iterator<AudioProductStrategy> iterator = AudioManager.getAudioProductStrategies().iterator();
        while (iterator.hasNext()) {
            AudioAttributes audioAttributes = iterator.next().getAudioAttributesForLegacyStreamType(n);
            if (audioAttributes == null) continue;
            return audioAttributes;
        }
        return new AudioAttributes.Builder().setContentType(0).setUsage(0).build();
    }

    private int getVolumeGroupIdForLegacyStreamType(int n) {
        Iterator<AudioProductStrategy> iterator = AudioManager.getAudioProductStrategies().iterator();
        while (iterator.hasNext()) {
            int n2 = iterator.next().getVolumeGroupIdForLegacyStreamType(n);
            if (n2 == -1) continue;
            return n2;
        }
        return AudioManager.getAudioProductStrategies().stream().map(_$$Lambda$SeekBarVolumizer$ezNr2aLs33rVlzIsAVW8OXqqpIs.INSTANCE).filter(_$$Lambda$SeekBarVolumizer$pv2_5S_FjgAtIix6Vp68yZJoqvQ.INSTANCE).findFirst().orElse(-1);
    }

    private boolean hasAudioProductStrategies() {
        boolean bl = AudioManager.getAudioProductStrategies().size() > 0;
        return bl;
    }

    private static boolean isAlarmsStream(int n) {
        boolean bl = n == 4;
        return bl;
    }

    private static boolean isMediaStream(int n) {
        boolean bl = n == 3;
        return bl;
    }

    private static boolean isNotificationOrRing(int n) {
        boolean bl = n == 2 || n == 5;
        return bl;
    }

    private boolean isZenMuted() {
        boolean bl;
        block0 : {
            int n;
            boolean bl2 = this.mNotificationOrRing;
            bl = true;
            if (bl2 && this.mZenMode == 3 || (n = this.mZenMode) == 2 || n == 1 && (!this.mAllowAlarms && SeekBarVolumizer.isAlarmsStream(this.mStreamType) || !this.mAllowMedia && SeekBarVolumizer.isMediaStream(this.mStreamType) || !this.mAllowRinger && SeekBarVolumizer.isNotificationOrRing(this.mStreamType))) break block0;
            bl = false;
        }
        return bl;
    }

    static /* synthetic */ Integer lambda$getVolumeGroupIdForLegacyStreamType$0(AudioProductStrategy audioProductStrategy) {
        return audioProductStrategy.getVolumeGroupIdForAudioAttributes(AudioProductStrategy.sDefaultAttributes);
    }

    static /* synthetic */ boolean lambda$getVolumeGroupIdForLegacyStreamType$1(Integer n) {
        boolean bl = n != -1;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onInitSample() {
        synchronized (this) {
            this.mRingtone = RingtoneManager.getRingtone(this.mContext, this.mDefaultUri);
            if (this.mRingtone != null) {
                this.mRingtone.setStreamType(this.mStreamType);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onStartSample() {
        if (this.isSamplePlaying()) return;
        Object object = this.mCallback;
        if (object != null) {
            object.onSampleStarting(this);
        }
        synchronized (this) {
            object = this.mRingtone;
            if (object == null) return;
            try {
                object = this.mRingtone;
                AudioAttributes.Builder builder = new AudioAttributes.Builder(this.mRingtone.getAudioAttributes());
                ((Ringtone)object).setAudioAttributes(builder.setFlags(128).build());
                this.mRingtone.play();
            }
            catch (Throwable throwable) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error playing ringtone, stream ");
                stringBuilder.append(this.mStreamType);
                Log.w(TAG, stringBuilder.toString(), throwable);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onStopSample() {
        synchronized (this) {
            if (this.mRingtone != null) {
                this.mRingtone.stop();
            }
            return;
        }
    }

    private void postSetVolume(int n) {
        Handler handler = this.mHandler;
        if (handler == null) {
            return;
        }
        this.mLastProgress = n;
        handler.removeMessages(0);
        handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(0));
    }

    private void postStartSample() {
        Object object = this.mHandler;
        if (object == null) {
            return;
        }
        ((Handler)object).removeMessages(1);
        Handler handler = this.mHandler;
        object = handler.obtainMessage(1);
        long l = this.isSamplePlaying() ? 1000L : 0L;
        handler.sendMessageDelayed((Message)object, l);
    }

    private void postStopSample() {
        Handler handler = this.mHandler;
        if (handler == null) {
            return;
        }
        handler.removeMessages(1);
        this.mHandler.removeMessages(2);
        handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(2));
    }

    private void registerVolumeGroupCb() {
        if (this.mVolumeGroupId != -1) {
            this.mAudioManager.registerVolumeGroupCallback((Executor)_$$Lambda$_14QHG018Z6p13d3hzJuGTWnNeo.INSTANCE, this.mVolumeGroupCallback);
            this.updateSlider();
        }
    }

    private void unregisterVolumeGroupCb() {
        if (this.mVolumeGroupId != -1) {
            this.mAudioManager.unregisterVolumeGroupCallback(this.mVolumeGroupCallback);
        }
    }

    private void updateSlider() {
        AudioManager audioManager;
        if (this.mSeekBar != null && (audioManager = this.mAudioManager) != null) {
            int n = audioManager.getStreamVolume(this.mStreamType);
            int n2 = this.mAudioManager.getLastAudibleStreamVolume(this.mStreamType);
            boolean bl = this.mAudioManager.isStreamMute(this.mStreamType);
            this.mUiHandler.postUpdateSlider(n, n2, bl);
        }
    }

    public void changeVolumeBy(int n) {
        this.mSeekBar.incrementProgressBy(n);
        this.postSetVolume(this.mSeekBar.getProgress());
        this.postStartSample();
        this.mVolumeBeforeMute = -1;
    }

    public SeekBar getSeekBar() {
        return this.mSeekBar;
    }

    @Override
    public boolean handleMessage(Message message) {
        int n = message.what;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("invalid SeekBarVolumizer message: ");
                        stringBuilder.append(message.what);
                        Log.e(TAG, stringBuilder.toString());
                    } else if (this.mPlaySample) {
                        this.onInitSample();
                    }
                } else if (this.mPlaySample) {
                    this.onStopSample();
                }
            } else if (this.mPlaySample) {
                this.onStartSample();
            }
        } else {
            if (this.mMuted && this.mLastProgress > 0) {
                this.mAudioManager.adjustStreamVolume(this.mStreamType, 100, 0);
            } else if (!this.mMuted && this.mLastProgress == 0) {
                this.mAudioManager.adjustStreamVolume(this.mStreamType, -100, 0);
            }
            this.mAudioManager.setStreamVolume(this.mStreamType, this.mLastProgress, 1024);
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isSamplePlaying() {
        synchronized (this) {
            if (this.mRingtone == null) return false;
            if (!this.mRingtone.isPlaying()) return false;
            return true;
        }
    }

    public void muteVolume() {
        int n = this.mVolumeBeforeMute;
        if (n != -1) {
            this.mSeekBar.setProgress(n, true);
            this.postSetVolume(this.mVolumeBeforeMute);
            this.postStartSample();
            this.mVolumeBeforeMute = -1;
        } else {
            this.mVolumeBeforeMute = this.mSeekBar.getProgress();
            this.mSeekBar.setProgress(0, true);
            this.postStopSample();
            this.postSetVolume(0);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
        Callback callback;
        if (bl) {
            this.postSetVolume(n);
        }
        if ((callback = this.mCallback) != null) {
            callback.onProgressChanged(seekBar, n, bl);
        }
    }

    public void onRestoreInstanceState(VolumePreference.VolumeStore volumeStore) {
        if (volumeStore.volume != -1) {
            this.mOriginalStreamVolume = volumeStore.originalVolume;
            this.mLastProgress = volumeStore.volume;
            this.postSetVolume(this.mLastProgress);
        }
    }

    public void onSaveInstanceState(VolumePreference.VolumeStore volumeStore) {
        int n = this.mLastProgress;
        if (n >= 0) {
            volumeStore.volume = n;
            volumeStore.originalVolume = this.mOriginalStreamVolume;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        this.postStartSample();
    }

    public void revertVolume() {
        this.mAudioManager.setStreamVolume(this.mStreamType, this.mOriginalStreamVolume, 0);
    }

    public void setSeekBar(SeekBar seekBar) {
        SeekBar seekBar2 = this.mSeekBar;
        if (seekBar2 != null) {
            seekBar2.setOnSeekBarChangeListener(null);
        }
        this.mSeekBar = seekBar;
        this.mSeekBar.setOnSeekBarChangeListener(null);
        this.mSeekBar.setMax(this.mMaxStreamVolume);
        this.updateSeekBar();
        this.mSeekBar.setOnSeekBarChangeListener(this);
    }

    public void start() {
        if (this.mHandler != null) {
            return;
        }
        HandlerThread handlerThread = new HandlerThread("SeekBarVolumizer.CallbackHandler");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper(), this);
        this.mHandler.sendEmptyMessage(3);
        this.mVolumeObserver = new Observer(this.mHandler);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.VOLUME_SETTINGS_INT[this.mStreamType]), false, this.mVolumeObserver);
        this.mReceiver.setListening(true);
        if (this.hasAudioProductStrategies()) {
            this.registerVolumeGroupCb();
        }
    }

    public void startSample() {
        this.postStartSample();
    }

    @UnsupportedAppUsage
    public void stop() {
        if (this.mHandler == null) {
            return;
        }
        this.postStopSample();
        this.mContext.getContentResolver().unregisterContentObserver(this.mVolumeObserver);
        this.mReceiver.setListening(false);
        if (this.hasAudioProductStrategies()) {
            this.unregisterVolumeGroupCb();
        }
        this.mSeekBar.setOnSeekBarChangeListener(null);
        this.mHandler.getLooper().quitSafely();
        this.mHandler = null;
        this.mVolumeObserver = null;
    }

    public void stopSample() {
        this.postStopSample();
    }

    protected void updateSeekBar() {
        boolean bl = this.isZenMuted();
        this.mSeekBar.setEnabled(bl ^ true);
        if (bl) {
            this.mSeekBar.setProgress(this.mLastAudibleStreamVolume, true);
        } else if (this.mNotificationOrRing && this.mRingerMode == 1) {
            this.mSeekBar.setProgress(0, true);
        } else if (this.mMuted) {
            this.mSeekBar.setProgress(0, true);
        } else {
            SeekBar seekBar = this.mSeekBar;
            int n = this.mLastProgress;
            if (n <= -1) {
                n = this.mOriginalStreamVolume;
            }
            seekBar.setProgress(n, true);
        }
    }

    public static interface Callback {
        public void onMuted(boolean var1, boolean var2);

        public void onProgressChanged(SeekBar var1, int var2, boolean var3);

        public void onSampleStarting(SeekBarVolumizer var1);
    }

    private final class H
    extends Handler {
        private static final int UPDATE_SLIDER = 1;

        private H() {
        }

        @Override
        public void handleMessage(Message message) {
            if (message.what == 1 && SeekBarVolumizer.this.mSeekBar != null) {
                SeekBarVolumizer.this.mLastProgress = message.arg1;
                SeekBarVolumizer.this.mLastAudibleStreamVolume = message.arg2;
                boolean bl = (Boolean)message.obj;
                if (bl != SeekBarVolumizer.this.mMuted) {
                    SeekBarVolumizer.this.mMuted = bl;
                    if (SeekBarVolumizer.this.mCallback != null) {
                        SeekBarVolumizer.this.mCallback.onMuted(SeekBarVolumizer.this.mMuted, SeekBarVolumizer.this.isZenMuted());
                    }
                }
                SeekBarVolumizer.this.updateSeekBar();
            }
        }

        public void postUpdateSlider(int n, int n2, boolean bl) {
            this.obtainMessage(1, n, n2, new Boolean(bl)).sendToTarget();
        }
    }

    private final class Observer
    extends ContentObserver {
        public Observer(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean bl) {
            super.onChange(bl);
            SeekBarVolumizer.this.updateSlider();
        }
    }

    private final class Receiver
    extends BroadcastReceiver {
        private boolean mListening;

        private Receiver() {
        }

        private void updateVolumeSlider(int n, int n2) {
            boolean bl = SeekBarVolumizer.this.mNotificationOrRing;
            boolean bl2 = true;
            bl = bl ? SeekBarVolumizer.isNotificationOrRing(n) : n == SeekBarVolumizer.this.mStreamType;
            if (SeekBarVolumizer.this.mSeekBar != null && bl && n2 != -1) {
                bl = bl2;
                if (!SeekBarVolumizer.this.mAudioManager.isStreamMute(SeekBarVolumizer.this.mStreamType)) {
                    bl = n2 == 0 ? bl2 : false;
                }
                SeekBarVolumizer.this.mUiHandler.postUpdateSlider(n2, SeekBarVolumizer.this.mLastAudibleStreamVolume, bl);
            }
        }

        @Override
        public void onReceive(Context object, Intent intent) {
            object = intent.getAction();
            if ("android.media.VOLUME_CHANGED_ACTION".equals(object)) {
                int n = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                int n2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", -1);
                if (SeekBarVolumizer.this.hasAudioProductStrategies()) {
                    this.updateVolumeSlider(n, n2);
                }
            } else if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(object)) {
                if (SeekBarVolumizer.this.mNotificationOrRing) {
                    object = SeekBarVolumizer.this;
                    ((SeekBarVolumizer)object).mRingerMode = ((SeekBarVolumizer)object).mAudioManager.getRingerModeInternal();
                }
                if (SeekBarVolumizer.this.mAffectedByRingerMode) {
                    SeekBarVolumizer.this.updateSlider();
                }
            } else if ("android.media.STREAM_DEVICES_CHANGED_ACTION".equals(object)) {
                int n = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                if (SeekBarVolumizer.this.hasAudioProductStrategies()) {
                    this.updateVolumeSlider(n, SeekBarVolumizer.this.mAudioManager.getStreamVolume(n));
                } else {
                    int n3 = SeekBarVolumizer.this.getVolumeGroupIdForLegacyStreamType(n);
                    if (n3 != -1 && n3 == SeekBarVolumizer.this.mVolumeGroupId) {
                        this.updateVolumeSlider(n, SeekBarVolumizer.this.mAudioManager.getStreamVolume(n));
                    }
                }
            } else if ("android.app.action.INTERRUPTION_FILTER_CHANGED".equals(object)) {
                object = SeekBarVolumizer.this;
                ((SeekBarVolumizer)object).mZenMode = ((SeekBarVolumizer)object).mNotificationManager.getZenMode();
                SeekBarVolumizer.this.updateSlider();
            } else if ("android.app.action.NOTIFICATION_POLICY_CHANGED".equals(object)) {
                object = SeekBarVolumizer.this;
                ((SeekBarVolumizer)object).mNotificationPolicy = ((SeekBarVolumizer)object).mNotificationManager.getConsolidatedNotificationPolicy();
                object = SeekBarVolumizer.this;
                int n = SeekBarVolumizer.access$2200((SeekBarVolumizer)object).priorityCategories;
                boolean bl = false;
                boolean bl2 = (n & 32) != 0;
                ((SeekBarVolumizer)object).mAllowAlarms = bl2;
                object = SeekBarVolumizer.this;
                bl2 = bl;
                if ((SeekBarVolumizer.access$2200((SeekBarVolumizer)object).priorityCategories & 64) != 0) {
                    bl2 = true;
                }
                ((SeekBarVolumizer)object).mAllowMedia = bl2;
                object = SeekBarVolumizer.this;
                ((SeekBarVolumizer)object).mAllowRinger = ZenModeConfig.areAllPriorityOnlyNotificationZenSoundsMuted(((SeekBarVolumizer)object).mNotificationPolicy) ^ true;
                SeekBarVolumizer.this.updateSlider();
            }
        }

        public void setListening(boolean bl) {
            if (this.mListening == bl) {
                return;
            }
            this.mListening = bl;
            if (bl) {
                IntentFilter intentFilter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
                intentFilter.addAction("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");
                intentFilter.addAction("android.app.action.INTERRUPTION_FILTER_CHANGED");
                intentFilter.addAction("android.app.action.NOTIFICATION_POLICY_CHANGED");
                intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
                SeekBarVolumizer.this.mContext.registerReceiver(this, intentFilter);
            } else {
                SeekBarVolumizer.this.mContext.unregisterReceiver(this);
            }
        }
    }

    private class VolumeHandler
    extends Handler {
        private VolumeHandler() {
        }

        @Override
        public void handleMessage(Message message) {
            block3 : {
                block2 : {
                    SomeArgs someArgs = (SomeArgs)message.obj;
                    if (message.what != 1) break block2;
                    int n = (Integer)someArgs.arg1;
                    if (SeekBarVolumizer.this.mVolumeGroupId != n || SeekBarVolumizer.this.mVolumeGroupId == -1) break block3;
                    SeekBarVolumizer.this.updateSlider();
                }
                return;
            }
        }
    }

}

