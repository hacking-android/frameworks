/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AndroidRuntimeException;
import android.util.Log;
import java.io.FileDescriptor;
import java.lang.ref.WeakReference;

public class JetPlayer {
    private static final int JET_EVENT = 1;
    private static final int JET_EVENT_CHAN_MASK = 245760;
    private static final int JET_EVENT_CHAN_SHIFT = 14;
    private static final int JET_EVENT_CTRL_MASK = 16256;
    private static final int JET_EVENT_CTRL_SHIFT = 7;
    private static final int JET_EVENT_SEG_MASK = -16777216;
    private static final int JET_EVENT_SEG_SHIFT = 24;
    private static final int JET_EVENT_TRACK_MASK = 16515072;
    private static final int JET_EVENT_TRACK_SHIFT = 18;
    private static final int JET_EVENT_VAL_MASK = 127;
    private static final int JET_NUMQUEUEDSEGMENT_UPDATE = 3;
    private static final int JET_OUTPUT_CHANNEL_CONFIG = 12;
    private static final int JET_OUTPUT_RATE = 22050;
    private static final int JET_PAUSE_UPDATE = 4;
    private static final int JET_USERID_UPDATE = 2;
    private static int MAXTRACKS = 0;
    private static final String TAG = "JetPlayer-J";
    private static JetPlayer singletonRef;
    private NativeEventHandler mEventHandler = null;
    private final Object mEventListenerLock = new Object();
    private Looper mInitializationLooper = null;
    private OnJetEventListener mJetEventListener = null;
    @UnsupportedAppUsage
    private long mNativePlayerInJavaObj;

    static {
        MAXTRACKS = 32;
    }

    private JetPlayer() {
        int n;
        Looper looper;
        this.mInitializationLooper = looper = Looper.myLooper();
        if (looper == null) {
            this.mInitializationLooper = Looper.getMainLooper();
        }
        if ((n = AudioTrack.getMinBufferSize(22050, 12, 2)) != -1 && n != -2) {
            this.native_setup(new WeakReference<JetPlayer>(this), JetPlayer.getMaxTracks(), Math.max(1200, n / (AudioFormat.getBytesPerSample(2) * 2)));
        }
    }

    public static JetPlayer getJetPlayer() {
        if (singletonRef == null) {
            singletonRef = new JetPlayer();
        }
        return singletonRef;
    }

    public static int getMaxTracks() {
        return MAXTRACKS;
    }

    private static void logd(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ android.media.JetPlayer ] ");
        stringBuilder.append(string2);
        Log.d(TAG, stringBuilder.toString());
    }

    private static void loge(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ android.media.JetPlayer ] ");
        stringBuilder.append(string2);
        Log.e(TAG, stringBuilder.toString());
    }

    private final native boolean native_clearQueue();

    private final native boolean native_closeJetFile();

    private final native void native_finalize();

    private final native boolean native_loadJetFromFile(String var1);

    private final native boolean native_loadJetFromFileD(FileDescriptor var1, long var2, long var4);

    private final native boolean native_pauseJet();

    private final native boolean native_playJet();

    private final native boolean native_queueJetSegment(int var1, int var2, int var3, int var4, int var5, byte var6);

    private final native boolean native_queueJetSegmentMuteArray(int var1, int var2, int var3, int var4, boolean[] var5, byte var6);

    private final native void native_release();

    private final native boolean native_setMuteArray(boolean[] var1, boolean var2);

    private final native boolean native_setMuteFlag(int var1, boolean var2, boolean var3);

    private final native boolean native_setMuteFlags(int var1, boolean var2);

    private final native boolean native_setup(Object var1, int var2, int var3);

    private final native boolean native_triggerClip(int var1);

    @UnsupportedAppUsage
    private static void postEventFromNative(Object object, int n, int n2, int n3) {
        Object object2;
        if ((object = (JetPlayer)((WeakReference)object).get()) != null && (object2 = ((JetPlayer)object).mEventHandler) != null) {
            object2 = ((Handler)object2).obtainMessage(n, n2, n3, null);
            ((JetPlayer)object).mEventHandler.sendMessage((Message)object2);
        }
    }

    public boolean clearQueue() {
        return this.native_clearQueue();
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public boolean closeJetFile() {
        return this.native_closeJetFile();
    }

    protected void finalize() {
        this.native_finalize();
    }

    public boolean loadJetFile(AssetFileDescriptor assetFileDescriptor) {
        long l = assetFileDescriptor.getLength();
        if (l >= 0L) {
            return this.native_loadJetFromFileD(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), l);
        }
        throw new AndroidRuntimeException("no length for fd");
    }

    public boolean loadJetFile(String string2) {
        return this.native_loadJetFromFile(string2);
    }

    public boolean pause() {
        return this.native_pauseJet();
    }

    public boolean play() {
        return this.native_playJet();
    }

    public boolean queueJetSegment(int n, int n2, int n3, int n4, int n5, byte by) {
        return this.native_queueJetSegment(n, n2, n3, n4, n5, by);
    }

    public boolean queueJetSegmentMuteArray(int n, int n2, int n3, int n4, boolean[] arrbl, byte by) {
        if (arrbl.length != JetPlayer.getMaxTracks()) {
            return false;
        }
        return this.native_queueJetSegmentMuteArray(n, n2, n3, n4, arrbl, by);
    }

    public void release() {
        this.native_release();
        singletonRef = null;
    }

    public void setEventListener(OnJetEventListener onJetEventListener) {
        this.setEventListener(onJetEventListener, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setEventListener(OnJetEventListener object, Handler handler) {
        Object object2 = this.mEventListenerLock;
        synchronized (object2) {
            this.mJetEventListener = object;
            this.mEventHandler = object != null ? (handler != null ? (object = new NativeEventHandler(this, handler.getLooper())) : (object = new NativeEventHandler(this, this.mInitializationLooper))) : null;
            return;
        }
    }

    public boolean setMuteArray(boolean[] arrbl, boolean bl) {
        if (arrbl.length != JetPlayer.getMaxTracks()) {
            return false;
        }
        return this.native_setMuteArray(arrbl, bl);
    }

    public boolean setMuteFlag(int n, boolean bl, boolean bl2) {
        return this.native_setMuteFlag(n, bl, bl2);
    }

    public boolean setMuteFlags(int n, boolean bl) {
        return this.native_setMuteFlags(n, bl);
    }

    public boolean triggerClip(int n) {
        return this.native_triggerClip(n);
    }

    private class NativeEventHandler
    extends Handler {
        private JetPlayer mJet;

        public NativeEventHandler(JetPlayer jetPlayer2, Looper looper) {
            super(looper);
            this.mJet = jetPlayer2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message message) {
            OnJetEventListener onJetEventListener;
            Object object = JetPlayer.this.mEventListenerLock;
            synchronized (object) {
                onJetEventListener = this.mJet.mJetEventListener;
            }
            int n = message.what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unknown message type ");
                            ((StringBuilder)object).append(message.what);
                            JetPlayer.loge(((StringBuilder)object).toString());
                            return;
                        }
                        if (onJetEventListener != null) {
                            onJetEventListener.onJetPauseUpdate(this.mJet, message.arg1);
                        }
                        return;
                    }
                    if (onJetEventListener != null) {
                        onJetEventListener.onJetNumQueuedSegmentUpdate(this.mJet, message.arg1);
                    }
                    return;
                }
                if (onJetEventListener != null) {
                    onJetEventListener.onJetUserIdUpdate(this.mJet, message.arg1, message.arg2);
                }
                return;
            }
            if (onJetEventListener != null) {
                JetPlayer.this.mJetEventListener.onJetEvent(this.mJet, (short)((message.arg1 & -16777216) >> 24), (byte)((message.arg1 & 16515072) >> 18), (byte)(((message.arg1 & 245760) >> 14) + 1), (byte)((message.arg1 & 16256) >> 7), (byte)(message.arg1 & 127));
            }
        }
    }

    public static interface OnJetEventListener {
        public void onJetEvent(JetPlayer var1, short var2, byte var3, byte var4, byte var5, byte var6);

        public void onJetNumQueuedSegmentUpdate(JetPlayer var1, int var2);

        public void onJetPauseUpdate(JetPlayer var1, int var2);

        public void onJetUserIdUpdate(JetPlayer var1, int var2, int var3);
    }

}

