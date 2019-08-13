/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioTrack;
import android.media.MediaTimestamp;
import android.media.PlaybackParams;
import android.media.SyncParams;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class MediaSync {
    private static final int CB_RETURN_AUDIO_BUFFER = 1;
    private static final int EVENT_CALLBACK = 1;
    private static final int EVENT_SET_CALLBACK = 2;
    public static final int MEDIASYNC_ERROR_AUDIOTRACK_FAIL = 1;
    public static final int MEDIASYNC_ERROR_SURFACE_FAIL = 2;
    private static final String TAG = "MediaSync";
    private List<AudioBuffer> mAudioBuffers = new LinkedList<AudioBuffer>();
    private Handler mAudioHandler = null;
    private final Object mAudioLock = new Object();
    private Looper mAudioLooper = null;
    private Thread mAudioThread = null;
    private AudioTrack mAudioTrack = null;
    private Callback mCallback = null;
    private Handler mCallbackHandler = null;
    private final Object mCallbackLock = new Object();
    private long mNativeContext;
    private OnErrorListener mOnErrorListener = null;
    private Handler mOnErrorListenerHandler = null;
    private final Object mOnErrorListenerLock = new Object();
    private float mPlaybackRate = 0.0f;

    static {
        System.loadLibrary("media_jni");
        MediaSync.native_init();
    }

    public MediaSync() {
        this.native_setup();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void createAudioThread() {
        this.mAudioThread = new Thread(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Looper.prepare();
                Object object = MediaSync.this.mAudioLock;
                synchronized (object) {
                    MediaSync.this.mAudioLooper = Looper.myLooper();
                    MediaSync mediaSync = MediaSync.this;
                    Handler handler = new Handler();
                    mediaSync.mAudioHandler = handler;
                    MediaSync.this.mAudioLock.notify();
                }
                Looper.loop();
            }
        };
        this.mAudioThread.start();
        Object object = this.mAudioLock;
        synchronized (object) {
            try {
                try {
                    this.mAudioLock.wait();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    private final native void native_finalize();

    private final native void native_flush();

    private final native long native_getPlayTimeForPendingAudioFrames();

    private final native boolean native_getTimestamp(MediaTimestamp var1);

    private static final native void native_init();

    private final native void native_release();

    private final native void native_setAudioTrack(AudioTrack var1);

    private native float native_setPlaybackParams(PlaybackParams var1);

    private final native void native_setSurface(Surface var1);

    private native float native_setSyncParams(SyncParams var1);

    private final native void native_setup();

    private final native void native_updateQueuedAudioData(int var1, long var2);

    private void postRenderAudio(long l) {
        this.mAudioHandler.postDelayed(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object = MediaSync.this.mAudioLock;
                synchronized (object) {
                    int n;
                    if ((double)MediaSync.this.mPlaybackRate == 0.0) {
                        return;
                    }
                    if (MediaSync.this.mAudioBuffers.isEmpty()) {
                        return;
                    }
                    AudioBuffer audioBuffer = (AudioBuffer)MediaSync.this.mAudioBuffers.get(0);
                    int n2 = audioBuffer.mByteBuffer.remaining();
                    if (n2 > 0 && (n = MediaSync.this.mAudioTrack.getPlayState()) != 3) {
                        try {
                            MediaSync.this.mAudioTrack.play();
                        }
                        catch (IllegalStateException illegalStateException) {
                            Log.w(MediaSync.TAG, "could not start audio track");
                        }
                    }
                    if ((n = MediaSync.this.mAudioTrack.write(audioBuffer.mByteBuffer, n2, 1)) > 0) {
                        if (audioBuffer.mPresentationTimeUs != -1L) {
                            MediaSync.this.native_updateQueuedAudioData(n2, audioBuffer.mPresentationTimeUs);
                            audioBuffer.mPresentationTimeUs = -1L;
                        }
                        if (n == n2) {
                            MediaSync.this.postReturnByteBuffer(audioBuffer);
                            MediaSync.this.mAudioBuffers.remove(0);
                            if (!MediaSync.this.mAudioBuffers.isEmpty()) {
                                MediaSync.this.postRenderAudio(0L);
                            }
                            return;
                        }
                    }
                    long l = TimeUnit.MICROSECONDS.toMillis(MediaSync.this.native_getPlayTimeForPendingAudioFrames());
                    MediaSync.this.postRenderAudio(l / 2L);
                    return;
                }
            }
        }, l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void postReturnByteBuffer(final AudioBuffer audioBuffer) {
        Object object = this.mCallbackLock;
        synchronized (object) {
            if (this.mCallbackHandler != null) {
                Handler handler = this.mCallbackHandler;
                Runnable runnable = new Runnable(){

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void run() {
                        Object object = MediaSync.this.mCallbackLock;
                        synchronized (object) {
                            Callback callback = MediaSync.this.mCallback;
                            if (MediaSync.this.mCallbackHandler != null && MediaSync.this.mCallbackHandler.getLooper().getThread() == Thread.currentThread()) {
                                // MONITOREXIT [2, 4] lbl5 : MonitorExitStatement: MONITOREXIT : var1_1
                                if (callback != null) {
                                    callback.onAudioBufferConsumed(this, audioBuffer.mByteBuffer, audioBuffer.mBufferIndex);
                                }
                                return;
                            }
                            return;
                        }
                    }
                };
                handler.post(runnable);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void returnAudioBuffers() {
        Object object = this.mAudioLock;
        synchronized (object) {
            Iterator<AudioBuffer> iterator = this.mAudioBuffers.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.mAudioBuffers.clear();
                    return;
                }
                this.postReturnByteBuffer(iterator.next());
            } while (true);
        }
    }

    public final native Surface createInputSurface();

    protected void finalize() {
        this.native_finalize();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void flush() {
        Object object = this.mAudioLock;
        synchronized (object) {
            this.mAudioBuffers.clear();
            this.mCallbackHandler.removeCallbacksAndMessages(null);
        }
        object = this.mAudioTrack;
        if (object != null) {
            ((AudioTrack)object).pause();
            this.mAudioTrack.flush();
            this.mAudioTrack.stop();
        }
        this.native_flush();
    }

    public native PlaybackParams getPlaybackParams();

    public native SyncParams getSyncParams();

    public MediaTimestamp getTimestamp() {
        try {
            MediaTimestamp mediaTimestamp = new MediaTimestamp();
            boolean bl = this.native_getTimestamp(mediaTimestamp);
            if (bl) {
                return mediaTimestamp;
            }
            return null;
        }
        catch (IllegalStateException illegalStateException) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void queueAudio(ByteBuffer byteBuffer, int n, long l) {
        if (this.mAudioTrack == null) throw new IllegalStateException("AudioTrack is NOT set or audio thread is not created");
        if (this.mAudioThread == null) throw new IllegalStateException("AudioTrack is NOT set or audio thread is not created");
        Object object = this.mAudioLock;
        // MONITORENTER : object
        List<AudioBuffer> list = this.mAudioBuffers;
        AudioBuffer audioBuffer = new AudioBuffer(byteBuffer, n, l);
        list.add(audioBuffer);
        // MONITOREXIT : object
        if ((double)this.mPlaybackRate == 0.0) return;
        this.postRenderAudio(0L);
    }

    public final void release() {
        Looper looper;
        this.returnAudioBuffers();
        if (this.mAudioThread != null && (looper = this.mAudioLooper) != null) {
            looper.quit();
        }
        this.setCallback(null, null);
        this.native_release();
    }

    public void setAudioTrack(AudioTrack audioTrack) {
        this.native_setAudioTrack(audioTrack);
        this.mAudioTrack = audioTrack;
        if (audioTrack != null && this.mAudioThread == null) {
            this.createAudioThread();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setCallback(Callback callback, Handler object) {
        Object object2 = this.mCallbackLock;
        synchronized (object2) {
            if (object != null) {
                this.mCallbackHandler = object;
            } else {
                Object object3;
                object = object3 = Looper.myLooper();
                if (object3 == null) {
                    object = Looper.getMainLooper();
                }
                this.mCallbackHandler = object == null ? null : (object3 = new Handler((Looper)object));
            }
            this.mCallback = callback;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnErrorListener(OnErrorListener onErrorListener, Handler object) {
        Object object2 = this.mOnErrorListenerLock;
        synchronized (object2) {
            if (object != null) {
                this.mOnErrorListenerHandler = object;
            } else {
                Object object3;
                object = object3 = Looper.myLooper();
                if (object3 == null) {
                    object = Looper.getMainLooper();
                }
                this.mOnErrorListenerHandler = object == null ? null : (object3 = new Handler((Looper)object));
            }
            this.mOnErrorListener = onErrorListener;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setPlaybackParams(PlaybackParams playbackParams) {
        Object object = this.mAudioLock;
        // MONITORENTER : object
        this.mPlaybackRate = this.native_setPlaybackParams(playbackParams);
        // MONITOREXIT : object
        if ((double)this.mPlaybackRate == 0.0) return;
        if (this.mAudioThread == null) return;
        this.postRenderAudio(0L);
    }

    public void setSurface(Surface surface) {
        this.native_setSurface(surface);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setSyncParams(SyncParams syncParams) {
        Object object = this.mAudioLock;
        // MONITORENTER : object
        this.mPlaybackRate = this.native_setSyncParams(syncParams);
        // MONITOREXIT : object
        if ((double)this.mPlaybackRate == 0.0) return;
        if (this.mAudioThread == null) return;
        this.postRenderAudio(0L);
    }

    private static class AudioBuffer {
        public int mBufferIndex;
        public ByteBuffer mByteBuffer;
        long mPresentationTimeUs;

        public AudioBuffer(ByteBuffer byteBuffer, int n, long l) {
            this.mByteBuffer = byteBuffer;
            this.mBufferIndex = n;
            this.mPresentationTimeUs = l;
        }
    }

    public static abstract class Callback {
        public abstract void onAudioBufferConsumed(MediaSync var1, ByteBuffer var2, int var3);
    }

    public static interface OnErrorListener {
        public void onError(MediaSync var1, int var2, int var3);
    }

}

