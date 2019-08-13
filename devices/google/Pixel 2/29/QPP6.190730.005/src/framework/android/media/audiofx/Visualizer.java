/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.lang.ref.WeakReference;

public class Visualizer {
    public static final int ALREADY_EXISTS = -2;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -4;
    public static final int ERROR_DEAD_OBJECT = -7;
    public static final int ERROR_INVALID_OPERATION = -5;
    public static final int ERROR_NO_INIT = -3;
    public static final int ERROR_NO_MEMORY = -6;
    public static final int MEASUREMENT_MODE_NONE = 0;
    public static final int MEASUREMENT_MODE_PEAK_RMS = 1;
    private static final int NATIVE_EVENT_FFT_CAPTURE = 1;
    private static final int NATIVE_EVENT_PCM_CAPTURE = 0;
    private static final int NATIVE_EVENT_SERVER_DIED = 2;
    public static final int SCALING_MODE_AS_PLAYED = 1;
    public static final int SCALING_MODE_NORMALIZED = 0;
    public static final int STATE_ENABLED = 2;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_UNINITIALIZED = 0;
    public static final int SUCCESS = 0;
    private static final String TAG = "Visualizer-JAVA";
    private OnDataCaptureListener mCaptureListener = null;
    @UnsupportedAppUsage
    private int mId;
    private long mJniData;
    private final Object mListenerLock = new Object();
    private NativeEventHandler mNativeEventHandler = null;
    private long mNativeVisualizer;
    private OnServerDiedListener mServerDiedListener = null;
    private int mState = 0;
    private final Object mStateLock = new Object();

    static {
        System.loadLibrary("audioeffect_jni");
        Visualizer.native_init();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Visualizer(int n) throws UnsupportedOperationException, RuntimeException {
        Object object = new int[1];
        Object object2 = this.mStateLock;
        synchronized (object2) {
            this.mState = 0;
            Object object3 = new WeakReference(this);
            n = this.native_setup(object3, n, (int[])object, ActivityThread.currentOpPackageName());
            if (n != 0 && n != -2) {
                ((StringBuilder)object).append("Error code ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" when initializing Visualizer.");
                Log.e(TAG, ((StringBuilder)object).toString());
                if (n != -5) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Cannot initialize Visualizer engine, error: ");
                    ((StringBuilder)object3).append(n);
                    object = new RuntimeException(((StringBuilder)object3).toString());
                    throw object;
                }
                object = new UnsupportedOperationException("Effect library not loaded");
                throw object;
            }
            this.mId = object[0];
            this.mState = this.native_getEnabled() ? 2 : 1;
            return;
        }
    }

    public static native int[] getCaptureSizeRange();

    public static native int getMaxCaptureRate();

    private final native void native_finalize();

    private final native int native_getCaptureSize();

    private final native boolean native_getEnabled();

    private final native int native_getFft(byte[] var1);

    private final native int native_getMeasurementMode();

    private final native int native_getPeakRms(MeasurementPeakRms var1);

    private final native int native_getSamplingRate();

    private final native int native_getScalingMode();

    private final native int native_getWaveForm(byte[] var1);

    private static final native void native_init();

    private final native void native_release();

    private final native int native_setCaptureSize(int var1);

    private final native int native_setEnabled(boolean var1);

    private final native int native_setMeasurementMode(int var1);

    private final native int native_setPeriodicCapture(int var1, boolean var2, boolean var3);

    private final native int native_setScalingMode(int var1);

    private final native int native_setup(Object var1, int var2, int[] var3, String var4);

    private static void postEventFromNative(Object object, int n, int n2, int n3, Object object2) {
        if ((object = (Visualizer)((WeakReference)object).get()) == null) {
            return;
        }
        NativeEventHandler nativeEventHandler = ((Visualizer)object).mNativeEventHandler;
        if (nativeEventHandler != null) {
            object2 = nativeEventHandler.obtainMessage(n, n2, n3, object2);
            ((Visualizer)object).mNativeEventHandler.sendMessage((Message)object2);
        }
    }

    protected void finalize() {
        this.native_finalize();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getCaptureSize() throws IllegalStateException {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mState != 0) {
                return this.native_getCaptureSize();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getCaptureSize() called in wrong state: ");
            stringBuilder.append(this.mState);
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean getEnabled() {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mState != 0) {
                return this.native_getEnabled();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getEnabled() called in wrong state: ");
            stringBuilder.append(this.mState);
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getFft(byte[] object) throws IllegalStateException {
        Object object2 = this.mStateLock;
        synchronized (object2) {
            if (this.mState == 2) {
                return this.native_getFft((byte[])object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getFft() called in wrong state: ");
            stringBuilder.append(this.mState);
            object = new IllegalStateException(stringBuilder.toString());
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getMeasurementMode() throws IllegalStateException {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mState != 0) {
                return this.native_getMeasurementMode();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getMeasurementMode() called in wrong state: ");
            stringBuilder.append(this.mState);
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getMeasurementPeakRms(MeasurementPeakRms object) {
        if (object == null) {
            Log.e("Visualizer-JAVA", "Cannot store measurements in a null object");
            return -4;
        }
        Object object2 = this.mStateLock;
        synchronized (object2) {
            if (this.mState == 2) {
                return this.native_getPeakRms((MeasurementPeakRms)object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("getMeasurementPeakRms() called in wrong state: ");
            ((StringBuilder)object).append(this.mState);
            IllegalStateException illegalStateException = new IllegalStateException(((StringBuilder)object).toString());
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getSamplingRate() throws IllegalStateException {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mState != 0) {
                return this.native_getSamplingRate();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getSamplingRate() called in wrong state: ");
            stringBuilder.append(this.mState);
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getScalingMode() throws IllegalStateException {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mState != 0) {
                return this.native_getScalingMode();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getScalingMode() called in wrong state: ");
            stringBuilder.append(this.mState);
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getWaveForm(byte[] object) throws IllegalStateException {
        Object object2 = this.mStateLock;
        synchronized (object2) {
            if (this.mState == 2) {
                return this.native_getWaveForm((byte[])object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getWaveForm() called in wrong state: ");
            stringBuilder.append(this.mState);
            object = new IllegalStateException(stringBuilder.toString());
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void release() {
        Object object = this.mStateLock;
        synchronized (object) {
            this.native_release();
            this.mState = 0;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int setCaptureSize(int n) throws IllegalStateException {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mState == 1) {
                return this.native_setCaptureSize(n);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setCaptureSize() called in wrong state: ");
            stringBuilder.append(this.mState);
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public int setDataCaptureListener(OnDataCaptureListener object, int n, boolean bl, boolean bl2) {
        int n2;
        Object object2 = this.mListenerLock;
        // MONITORENTER : object2
        this.mCaptureListener = object;
        // MONITOREXIT : object2
        if (object == null) {
            bl = false;
            bl2 = false;
        }
        n = n2 = this.native_setPeriodicCapture(n, bl, bl2);
        if (n2 != 0) return n;
        n = n2;
        if (object == null) return n;
        n = n2;
        if (this.mNativeEventHandler != null) return n;
        object = Looper.myLooper();
        if (object != null) {
            this.mNativeEventHandler = new NativeEventHandler(this, (Looper)object);
            return n2;
        }
        object = Looper.getMainLooper();
        if (object != null) {
            this.mNativeEventHandler = new NativeEventHandler(this, (Looper)object);
            return n2;
        }
        this.mNativeEventHandler = null;
        return -3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int setEnabled(boolean bl) throws IllegalStateException {
        Object object = this.mStateLock;
        synchronized (object) {
            int n;
            if (this.mState == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setEnabled() called in wrong state: ");
                stringBuilder.append(this.mState);
                IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                throw illegalStateException;
            }
            int n2 = 0;
            int n3 = 2;
            if (!bl || this.mState != 1) {
                n = n2;
                if (bl) return n;
                n = n2;
                if (this.mState != 2) return n;
            }
            n = n2 = this.native_setEnabled(bl);
            if (n2 != 0) return n;
            n = bl ? n3 : 1;
            this.mState = n;
            return n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int setMeasurementMode(int n) throws IllegalStateException {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mState != 0) {
                return this.native_setMeasurementMode(n);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setMeasurementMode() called in wrong state: ");
            stringBuilder.append(this.mState);
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int setScalingMode(int n) throws IllegalStateException {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mState != 0) {
                return this.native_setScalingMode(n);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setScalingMode() called in wrong state: ");
            stringBuilder.append(this.mState);
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int setServerDiedListener(OnServerDiedListener onServerDiedListener) {
        Object object = this.mListenerLock;
        synchronized (object) {
            this.mServerDiedListener = onServerDiedListener;
            return 0;
        }
    }

    public static final class MeasurementPeakRms {
        public int mPeak;
        public int mRms;
    }

    private class NativeEventHandler
    extends Handler {
        private Visualizer mVisualizer;

        public NativeEventHandler(Visualizer visualizer2, Looper looper) {
            super(looper);
            this.mVisualizer = visualizer2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void handleCaptureMessage(Message message) {
            Object object;
            byte[] arrby = Visualizer.this.mListenerLock;
            synchronized (arrby) {
                object = this.mVisualizer.mCaptureListener;
            }
            if (object == null) return;
            arrby = (byte[])message.obj;
            int n = message.arg1;
            int n2 = message.what;
            if (n2 == 0) {
                object.onWaveFormDataCapture(this.mVisualizer, arrby, n);
                return;
            }
            if (n2 != 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown native event in handleCaptureMessge: ");
                ((StringBuilder)object).append(message.what);
                Log.e(Visualizer.TAG, ((StringBuilder)object).toString());
                return;
            }
            object.onFftDataCapture(this.mVisualizer, arrby, n);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        private void handleServerDiedMessage(Message object) {
            object = Visualizer.this.mListenerLock;
            // MONITORENTER : object
            OnServerDiedListener onServerDiedListener = this.mVisualizer.mServerDiedListener;
            // MONITOREXIT : object
            if (onServerDiedListener == null) return;
            onServerDiedListener.onServerDied();
        }

        @Override
        public void handleMessage(Message message) {
            if (this.mVisualizer == null) {
                return;
            }
            int n = message.what;
            if (n != 0 && n != 1) {
                if (n != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown native event: ");
                    stringBuilder.append(message.what);
                    Log.e(Visualizer.TAG, stringBuilder.toString());
                } else {
                    this.handleServerDiedMessage(message);
                }
            } else {
                this.handleCaptureMessage(message);
            }
        }
    }

    public static interface OnDataCaptureListener {
        public void onFftDataCapture(Visualizer var1, byte[] var2, int var3);

        public void onWaveFormDataCapture(Visualizer var1, byte[] var2, int var3);
    }

    public static interface OnServerDiedListener {
        public void onServerDied();
    }

}

