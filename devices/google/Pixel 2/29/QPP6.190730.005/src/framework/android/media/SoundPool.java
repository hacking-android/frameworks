/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.PlayerBase;
import android.media.VolumeShaper;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.AndroidRuntimeException;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class SoundPool
extends PlayerBase {
    private static final boolean DEBUG;
    private static final int SAMPLE_LOADED = 1;
    private static final String TAG = "SoundPool";
    private final AudioAttributes mAttributes;
    private EventHandler mEventHandler;
    private boolean mHasAppOpsPlayAudio;
    private final Object mLock;
    private long mNativeContext;
    private OnLoadCompleteListener mOnLoadCompleteListener;

    static {
        System.loadLibrary("soundpool");
        DEBUG = Log.isLoggable(TAG, 3);
    }

    public SoundPool(int n, int n2, int n3) {
        this(n, new AudioAttributes.Builder().setInternalLegacyStreamType(n2).build());
        PlayerBase.deprecateStreamTypeForPlayback(n2, TAG, "SoundPool()");
    }

    private SoundPool(int n, AudioAttributes audioAttributes) {
        super(audioAttributes, 3);
        if (this.native_setup(new WeakReference<SoundPool>(this), n, audioAttributes) == 0) {
            this.mLock = new Object();
            this.mAttributes = audioAttributes;
            this.baseRegisterPlayer();
            return;
        }
        throw new RuntimeException("Native setup failed");
    }

    private final native int _load(FileDescriptor var1, long var2, long var4, int var6);

    private final native void _mute(boolean var1);

    private final native int _play(int var1, float var2, float var3, int var4, int var5, float var6);

    private final native void _setVolume(int var1, float var2, float var3);

    private final native void native_release();

    private final native int native_setup(Object var1, int var2, Object var3);

    private static void postEventFromNative(Object object, int n, int n2, int n3, Object object2) {
        if ((object = (SoundPool)((WeakReference)object).get()) == null) {
            return;
        }
        EventHandler eventHandler = ((SoundPool)object).mEventHandler;
        if (eventHandler != null) {
            object2 = eventHandler.obtainMessage(n, n2, n3, object2);
            ((SoundPool)object).mEventHandler.sendMessage((Message)object2);
        }
    }

    public final native void autoPause();

    public final native void autoResume();

    protected void finalize() {
        this.release();
    }

    public int load(Context object, int n, int n2) {
        object = ((Context)object).getResources().openRawResourceFd(n);
        n = 0;
        if (object != null) {
            n = this._load(((AssetFileDescriptor)object).getFileDescriptor(), ((AssetFileDescriptor)object).getStartOffset(), ((AssetFileDescriptor)object).getLength(), n2);
            try {
                ((AssetFileDescriptor)object).close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return n;
    }

    public int load(AssetFileDescriptor assetFileDescriptor, int n) {
        if (assetFileDescriptor != null) {
            long l = assetFileDescriptor.getLength();
            if (l >= 0L) {
                return this._load(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), l, n);
            }
            throw new AndroidRuntimeException("no length for fd");
        }
        return 0;
    }

    public int load(FileDescriptor fileDescriptor, long l, long l2, int n) {
        return this._load(fileDescriptor, l, l2, n);
    }

    public int load(String string2, int n) {
        int n2;
        block6 : {
            int n3 = 0;
            int n4 = 0;
            n2 = n3;
            n2 = n3;
            File file = new File(string2);
            n2 = n3;
            ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(file, 268435456);
            n2 = n4;
            if (parcelFileDescriptor == null) break block6;
            n2 = n3;
            n2 = n = this._load(parcelFileDescriptor.getFileDescriptor(), 0L, file.length(), n);
            try {
                parcelFileDescriptor.close();
                n2 = n;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("error loading ");
                stringBuilder.append(string2);
                Log.e("SoundPool", stringBuilder.toString());
            }
        }
        return n2;
    }

    public final native void pause(int var1);

    public final int play(int n, float f, float f2, int n2, int n3, float f3) {
        this.baseStart();
        return this._play(n, f, f2, n2, n3, f3);
    }

    @Override
    int playerApplyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) {
        return -1;
    }

    @Override
    VolumeShaper.State playerGetVolumeShaperState(int n) {
        return null;
    }

    @Override
    void playerPause() {
    }

    @Override
    int playerSetAuxEffectSendLevel(boolean bl, float f) {
        return 0;
    }

    @Override
    void playerSetVolume(boolean bl, float f, float f2) {
        this._mute(bl);
    }

    @Override
    void playerStart() {
    }

    @Override
    void playerStop() {
    }

    public final void release() {
        this.baseRelease();
        this.native_release();
    }

    public final native void resume(int var1);

    public final native void setLoop(int var1, int var2);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnLoadCompleteListener(OnLoadCompleteListener onLoadCompleteListener) {
        Object object = this.mLock;
        synchronized (object) {
            EventHandler eventHandler;
            Looper looper;
            Object object2;
            this.mEventHandler = onLoadCompleteListener != null ? ((object2 = Looper.myLooper()) != null ? (eventHandler = new EventHandler((Looper)object2)) : ((looper = Looper.getMainLooper()) != null ? (object2 = new EventHandler(looper)) : null)) : null;
            this.mOnLoadCompleteListener = onLoadCompleteListener;
            return;
        }
    }

    public final native void setPriority(int var1, int var2);

    public final native void setRate(int var1, float var2);

    public void setVolume(int n, float f) {
        this.setVolume(n, f, f);
    }

    public final void setVolume(int n, float f, float f2) {
        this._setVolume(n, f, f2);
    }

    public final native void stop(int var1);

    public final native boolean unload(int var1);

    public static class Builder {
        private AudioAttributes mAudioAttributes;
        private int mMaxStreams = 1;

        public SoundPool build() {
            if (this.mAudioAttributes == null) {
                this.mAudioAttributes = new AudioAttributes.Builder().setUsage(1).build();
            }
            return new SoundPool(this.mMaxStreams, this.mAudioAttributes);
        }

        public Builder setAudioAttributes(AudioAttributes audioAttributes) throws IllegalArgumentException {
            if (audioAttributes != null) {
                this.mAudioAttributes = audioAttributes;
                return this;
            }
            throw new IllegalArgumentException("Invalid null AudioAttributes");
        }

        public Builder setMaxStreams(int n) throws IllegalArgumentException {
            if (n > 0) {
                this.mMaxStreams = n;
                return this;
            }
            throw new IllegalArgumentException("Strictly positive value required for the maximum number of streams");
        }
    }

    private final class EventHandler
    extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message message) {
            Object object;
            if (message.what != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown message type ");
                stringBuilder.append(message.what);
                Log.e(SoundPool.TAG, stringBuilder.toString());
                return;
            }
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Sample ");
                ((StringBuilder)object).append(message.arg1);
                ((StringBuilder)object).append(" loaded");
                Log.d(SoundPool.TAG, ((StringBuilder)object).toString());
            }
            object = SoundPool.this.mLock;
            synchronized (object) {
                if (SoundPool.this.mOnLoadCompleteListener != null) {
                    SoundPool.this.mOnLoadCompleteListener.onLoadComplete(SoundPool.this, message.arg1, message.arg2);
                }
                return;
            }
        }
    }

    public static interface OnLoadCompleteListener {
        public void onLoadComplete(SoundPool var1, int var2, int var3);
    }

}

