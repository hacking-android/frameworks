/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiopolicy;

import android.media.AudioManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.android.internal.util.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AudioVolumeGroupChangeHandler {
    private static final int AUDIOVOLUMEGROUP_EVENT_NEW_LISTENER = 4;
    private static final int AUDIOVOLUMEGROUP_EVENT_VOLUME_CHANGED = 1000;
    private static final String TAG = "AudioVolumeGroupChangeHandler";
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private long mJniCallback;
    private final ArrayList<AudioManager.VolumeGroupCallback> mListeners = new ArrayList();

    private native void native_finalize();

    private native void native_setup(Object var1);

    private static void postEventFromNative(Object object, int n, int n2, int n3, Object object2) {
        if ((object = (AudioVolumeGroupChangeHandler)((WeakReference)object).get()) == null) {
            return;
        }
        if ((object = ((AudioVolumeGroupChangeHandler)object).handler()) != null) {
            object2 = ((Handler)object).obtainMessage(n, n2, n3, object2);
            if (n != 4) {
                ((Handler)object).removeMessages(n);
            }
            ((Handler)object).sendMessage((Message)object2);
        }
    }

    protected void finalize() {
        this.native_finalize();
        if (this.mHandlerThread.isAlive()) {
            this.mHandlerThread.quit();
        }
    }

    Handler handler() {
        return this.mHandler;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void init() {
        synchronized (this) {
            if (this.mHandler != null) {
                return;
            }
            Handler handler = new Handler("AudioVolumeGroupChangeHandler");
            this.mHandlerThread = handler;
            this.mHandlerThread.start();
            if (this.mHandlerThread.getLooper() == null) {
                this.mHandler = null;
                return;
            }
            handler = new Handler(this.mHandlerThread.getLooper()){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Converted monitor instructions to comments
                 * Lifted jumps to return sites
                 */
                @Override
                public void handleMessage(Message message) {
                    ArrayList<AudioManager.VolumeGroupCallback> arrayList;
                    // MONITORENTER : this
                    if (message.what == 4) {
                        ArrayList<AudioManager.VolumeGroupCallback> arrayList2;
                        arrayList = arrayList2 = new ArrayList<AudioManager.VolumeGroupCallback>();
                        if (AudioVolumeGroupChangeHandler.this.mListeners.contains(message.obj)) {
                            arrayList2.add((AudioManager.VolumeGroupCallback)message.obj);
                            arrayList = arrayList2;
                        }
                    } else {
                        arrayList = AudioVolumeGroupChangeHandler.this.mListeners;
                    }
                    // MONITOREXIT : this
                    if (arrayList.isEmpty()) {
                        return;
                    }
                    if (message.what != 1000) {
                        return;
                    }
                    int n = 0;
                    while (n < arrayList.size()) {
                        ((AudioManager.VolumeGroupCallback)arrayList.get(n)).onAudioVolumeGroupChanged(message.arg1, message.arg2);
                        ++n;
                    }
                }
            };
            this.mHandler = handler;
            handler = new WeakReference(this);
            this.native_setup(handler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerListener(AudioManager.VolumeGroupCallback object) {
        Preconditions.checkNotNull(object, "volume group callback shall not be null");
        synchronized (this) {
            this.mListeners.add((AudioManager.VolumeGroupCallback)object);
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            object = handler.obtainMessage(4, 0, 0, object);
            this.mHandler.sendMessage((Message)object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterListener(AudioManager.VolumeGroupCallback volumeGroupCallback) {
        Preconditions.checkNotNull(volumeGroupCallback, "volume group callback shall not be null");
        synchronized (this) {
            this.mListeners.remove(volumeGroupCallback);
            return;
        }
    }

}

