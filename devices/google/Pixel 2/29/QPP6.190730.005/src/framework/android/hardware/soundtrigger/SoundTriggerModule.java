/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.soundtrigger;

import android.annotation.UnsupportedAppUsage;
import android.hardware.soundtrigger.SoundTrigger;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

public class SoundTriggerModule {
    private static final int EVENT_RECOGNITION = 1;
    private static final int EVENT_SERVICE_DIED = 2;
    private static final int EVENT_SERVICE_STATE_CHANGE = 4;
    private static final int EVENT_SOUNDMODEL = 3;
    private NativeEventHandlerDelegate mEventHandlerDelegate;
    @UnsupportedAppUsage
    private int mId;
    @UnsupportedAppUsage
    private long mNativeContext;

    SoundTriggerModule(int n, SoundTrigger.StatusListener statusListener, Handler handler) {
        this.mId = n;
        this.mEventHandlerDelegate = new NativeEventHandlerDelegate(statusListener, handler);
        this.native_setup(SoundTrigger.getCurrentOpPackageName(), new WeakReference<SoundTriggerModule>(this));
    }

    private native void native_finalize();

    private native void native_setup(String var1, Object var2);

    @UnsupportedAppUsage
    private static void postEventFromNative(Object object, int n, int n2, int n3, Object object2) {
        if ((object = (SoundTriggerModule)((WeakReference)object).get()) == null) {
            return;
        }
        object = ((SoundTriggerModule)object).mEventHandlerDelegate;
        if (object != null && (object = ((NativeEventHandlerDelegate)object).handler()) != null) {
            ((Handler)object).sendMessage(((Handler)object).obtainMessage(n, n2, n3, object2));
        }
    }

    @UnsupportedAppUsage
    public native void detach();

    protected void finalize() {
        this.native_finalize();
    }

    public native int getModelState(int var1);

    @UnsupportedAppUsage
    public native int loadSoundModel(SoundTrigger.SoundModel var1, int[] var2);

    @UnsupportedAppUsage
    public native int startRecognition(int var1, SoundTrigger.RecognitionConfig var2);

    @UnsupportedAppUsage
    public native int stopRecognition(int var1);

    @UnsupportedAppUsage
    public native int unloadSoundModel(int var1);

    private class NativeEventHandlerDelegate {
        private final Handler mHandler;

        NativeEventHandlerDelegate(final SoundTrigger.StatusListener statusListener, Handler object) {
            object = object != null ? ((Handler)object).getLooper() : Looper.getMainLooper();
            this.mHandler = object != null ? new Handler((Looper)object){

                @Override
                public void handleMessage(Message object) {
                    int n = ((Message)object).what;
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                SoundTrigger.StatusListener statusListener2;
                                if (n == 4 && (statusListener2 = statusListener) != null) {
                                    statusListener2.onServiceStateChange(((Message)object).arg1);
                                }
                            } else {
                                SoundTrigger.StatusListener statusListener3 = statusListener;
                                if (statusListener3 != null) {
                                    statusListener3.onSoundModelUpdate((SoundTrigger.SoundModelEvent)((Message)object).obj);
                                }
                            }
                        } else {
                            object = statusListener;
                            if (object != null) {
                                object.onServiceDied();
                            }
                        }
                    } else {
                        SoundTrigger.StatusListener statusListener4 = statusListener;
                        if (statusListener4 != null) {
                            statusListener4.onRecognition((SoundTrigger.RecognitionEvent)((Message)object).obj);
                        }
                    }
                }
            } : null;
        }

        Handler handler() {
            return this.mHandler;
        }

    }

}

