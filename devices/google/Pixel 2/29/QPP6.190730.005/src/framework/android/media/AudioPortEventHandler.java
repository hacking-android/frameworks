/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioManager;
import android.media.AudioPatch;
import android.media.AudioPort;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

class AudioPortEventHandler {
    private static final int AUDIOPORT_EVENT_NEW_LISTENER = 4;
    private static final int AUDIOPORT_EVENT_PATCH_LIST_UPDATED = 2;
    private static final int AUDIOPORT_EVENT_PORT_LIST_UPDATED = 1;
    private static final int AUDIOPORT_EVENT_SERVICE_DIED = 3;
    private static final long RESCHEDULE_MESSAGE_DELAY_MS = 100L;
    private static final String TAG = "AudioPortEventHandler";
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    @UnsupportedAppUsage
    private long mJniCallback;
    private final ArrayList<AudioManager.OnAudioPortUpdateListener> mListeners = new ArrayList();

    AudioPortEventHandler() {
    }

    private native void native_finalize();

    private native void native_setup(Object var1);

    @UnsupportedAppUsage
    private static void postEventFromNative(Object object, int n, int n2, int n3, Object object2) {
        if ((object = (AudioPortEventHandler)((WeakReference)object).get()) == null) {
            return;
        }
        if ((object = ((AudioPortEventHandler)object).handler()) != null) {
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
    void init() {
        synchronized (this) {
            if (this.mHandler != null) {
                return;
            }
            Handler handler = new Handler("AudioPortEventHandler");
            this.mHandlerThread = handler;
            this.mHandlerThread.start();
            if (this.mHandlerThread.getLooper() != null) {
                handler = new Handler(this.mHandlerThread.getLooper()){

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     * Converted monitor instructions to comments
                     * Lifted jumps to return sites
                     */
                    @Override
                    public void handleMessage(Message arraudioPatch) {
                        ArrayList<AudioPatch> arrayList;
                        ArrayList<AudioPatch> arrayList2;
                        // MONITORENTER : this
                        if (arraudioPatch.what == 4) {
                            arrayList = arrayList2 = new ArrayList<AudioPatch>();
                            if (AudioPortEventHandler.this.mListeners.contains(arraudioPatch.obj)) {
                                arrayList2.add((AudioPatch)((Object)((AudioManager.OnAudioPortUpdateListener)arraudioPatch.obj)));
                                arrayList = arrayList2;
                            }
                        } else {
                            arrayList = AudioPortEventHandler.this.mListeners;
                        }
                        // MONITOREXIT : this
                        if (arraudioPatch.what == 1 || arraudioPatch.what == 2 || arraudioPatch.what == 3) {
                            AudioManager.resetAudioPortGeneration();
                        }
                        if (arrayList.isEmpty()) {
                            return;
                        }
                        AudioPort[] arraudioPort = new ArrayList();
                        arrayList2 = new ArrayList();
                        if (arraudioPatch.what != 3 && AudioManager.updateAudioPortCache(arraudioPort, arrayList2, null) != 0) {
                            this.sendMessageDelayed(this.obtainMessage(arraudioPatch.what, arraudioPatch.obj), 100L);
                            return;
                        }
                        int n = arraudioPatch.what;
                        if (n != 1) {
                            if (n != 2) {
                                if (n != 3) {
                                    if (n != 4) {
                                        return;
                                    }
                                } else {
                                    n = 0;
                                    while (n < arrayList.size()) {
                                        ((AudioManager.OnAudioPortUpdateListener)((Object)arrayList.get(n))).onServiceDied();
                                        ++n;
                                    }
                                    return;
                                }
                            }
                        } else {
                            arraudioPort = arraudioPort.toArray(new AudioPort[0]);
                            for (n = 0; n < arrayList.size(); ++n) {
                                ((AudioManager.OnAudioPortUpdateListener)((Object)arrayList.get(n))).onAudioPortListUpdate(arraudioPort);
                            }
                            if (arraudioPatch.what == 1) {
                                return;
                            }
                        }
                        arraudioPatch = arrayList2.toArray(new AudioPatch[0]);
                        n = 0;
                        while (n < arrayList.size()) {
                            ((AudioManager.OnAudioPortUpdateListener)((Object)arrayList.get(n))).onAudioPatchListUpdate(arraudioPatch);
                            ++n;
                        }
                    }
                };
                this.mHandler = handler;
                handler = new WeakReference(this);
                this.native_setup(handler);
            } else {
                this.mHandler = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void registerListener(AudioManager.OnAudioPortUpdateListener object) {
        synchronized (this) {
            this.mListeners.add((AudioManager.OnAudioPortUpdateListener)object);
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
    void unregisterListener(AudioManager.OnAudioPortUpdateListener onAudioPortUpdateListener) {
        synchronized (this) {
            this.mListeners.remove(onAudioPortUpdateListener);
            return;
        }
    }

}

