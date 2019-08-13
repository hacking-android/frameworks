/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;
import java.util.UUID;

public class AudioEffect {
    public static final String ACTION_CLOSE_AUDIO_EFFECT_CONTROL_SESSION = "android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION";
    public static final String ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL = "android.media.action.DISPLAY_AUDIO_EFFECT_CONTROL_PANEL";
    public static final String ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION = "android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION";
    public static final int ALREADY_EXISTS = -2;
    public static final int CONTENT_TYPE_GAME = 2;
    public static final int CONTENT_TYPE_MOVIE = 1;
    public static final int CONTENT_TYPE_MUSIC = 0;
    public static final int CONTENT_TYPE_VOICE = 3;
    public static final String EFFECT_AUXILIARY = "Auxiliary";
    public static final String EFFECT_INSERT = "Insert";
    public static final String EFFECT_PRE_PROCESSING = "Pre Processing";
    public static final UUID EFFECT_TYPE_AEC;
    public static final UUID EFFECT_TYPE_AGC;
    public static final UUID EFFECT_TYPE_BASS_BOOST;
    public static final UUID EFFECT_TYPE_DYNAMICS_PROCESSING;
    public static final UUID EFFECT_TYPE_ENV_REVERB;
    public static final UUID EFFECT_TYPE_EQUALIZER;
    public static final UUID EFFECT_TYPE_LOUDNESS_ENHANCER;
    public static final UUID EFFECT_TYPE_NS;
    public static final UUID EFFECT_TYPE_NULL;
    public static final UUID EFFECT_TYPE_PRESET_REVERB;
    public static final UUID EFFECT_TYPE_VIRTUALIZER;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -4;
    public static final int ERROR_DEAD_OBJECT = -7;
    public static final int ERROR_INVALID_OPERATION = -5;
    public static final int ERROR_NO_INIT = -3;
    public static final int ERROR_NO_MEMORY = -6;
    public static final String EXTRA_AUDIO_SESSION = "android.media.extra.AUDIO_SESSION";
    public static final String EXTRA_CONTENT_TYPE = "android.media.extra.CONTENT_TYPE";
    public static final String EXTRA_PACKAGE_NAME = "android.media.extra.PACKAGE_NAME";
    public static final int NATIVE_EVENT_CONTROL_STATUS = 0;
    public static final int NATIVE_EVENT_ENABLED_STATUS = 1;
    public static final int NATIVE_EVENT_PARAMETER_CHANGED = 2;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_UNINITIALIZED = 0;
    public static final int SUCCESS = 0;
    private static final String TAG = "AudioEffect-JAVA";
    private OnControlStatusChangeListener mControlChangeStatusListener = null;
    private Descriptor mDescriptor;
    private OnEnableStatusChangeListener mEnableStatusChangeListener = null;
    private int mId;
    private long mJniData;
    public final Object mListenerLock = new Object();
    private long mNativeAudioEffect;
    public NativeEventHandler mNativeEventHandler = null;
    private OnParameterChangeListener mParameterChangeListener = null;
    private int mState = 0;
    private final Object mStateLock = new Object();

    static {
        System.loadLibrary("audioeffect_jni");
        AudioEffect.native_init();
        EFFECT_TYPE_ENV_REVERB = UUID.fromString("c2e5d5f0-94bd-4763-9cac-4e234d06839e");
        EFFECT_TYPE_PRESET_REVERB = UUID.fromString("47382d60-ddd8-11db-bf3a-0002a5d5c51b");
        EFFECT_TYPE_EQUALIZER = UUID.fromString("0bed4300-ddd6-11db-8f34-0002a5d5c51b");
        EFFECT_TYPE_BASS_BOOST = UUID.fromString("0634f220-ddd4-11db-a0fc-0002a5d5c51b");
        EFFECT_TYPE_VIRTUALIZER = UUID.fromString("37cc2c00-dddd-11db-8577-0002a5d5c51b");
        EFFECT_TYPE_AGC = UUID.fromString("0a8abfe0-654c-11e0-ba26-0002a5d5c51b");
        EFFECT_TYPE_AEC = UUID.fromString("7b491460-8d4d-11e0-bd61-0002a5d5c51b");
        EFFECT_TYPE_NS = UUID.fromString("58b4b260-8e06-11e0-aa8e-0002a5d5c51b");
        EFFECT_TYPE_LOUDNESS_ENHANCER = UUID.fromString("fe3199be-aed0-413f-87bb-11260eb63cf1");
        EFFECT_TYPE_DYNAMICS_PROCESSING = UUID.fromString("7261676f-6d75-7369-6364-28e2fd3ac39e");
        EFFECT_TYPE_NULL = UUID.fromString("ec7178ec-e5e1-4432-a3f4-4657e6795210");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public AudioEffect(UUID uUID, UUID object, int n, int n2) throws IllegalArgumentException, UnsupportedOperationException, RuntimeException {
        int[] arrn = new int[1];
        Object[] arrobject = new Descriptor[1];
        n = this.native_setup(new WeakReference<AudioEffect>(this), uUID.toString(), ((UUID)object).toString(), n, n2, arrn, arrobject, ActivityThread.currentOpPackageName());
        if (n != 0 && n != -2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Error code ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" when initializing AudioEffect.");
            Log.e(TAG, ((StringBuilder)object).toString());
            if (n == -5) {
                throw new UnsupportedOperationException("Effect library not loaded");
            }
            if (n != -4) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot initialize effect engine for type: ");
                ((StringBuilder)object).append(uUID);
                ((StringBuilder)object).append(" Error: ");
                ((StringBuilder)object).append(n);
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Effect type: ");
            ((StringBuilder)object).append(uUID);
            ((StringBuilder)object).append(" not supported.");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        this.mId = arrn[0];
        this.mDescriptor = arrobject[0];
        object = this.mStateLock;
        synchronized (object) {
            this.mState = 1;
            return;
        }
    }

    public static float byteArrayToFloat(byte[] arrby) {
        return AudioEffect.byteArrayToFloat(arrby, 0);
    }

    public static float byteArrayToFloat(byte[] object, int n) {
        object = ByteBuffer.wrap((byte[])object);
        ((ByteBuffer)object).order(ByteOrder.nativeOrder());
        return ((ByteBuffer)object).getFloat(n);
    }

    public static int byteArrayToInt(byte[] arrby) {
        return AudioEffect.byteArrayToInt(arrby, 0);
    }

    public static int byteArrayToInt(byte[] object, int n) {
        object = ByteBuffer.wrap((byte[])object);
        ((ByteBuffer)object).order(ByteOrder.nativeOrder());
        return ((ByteBuffer)object).getInt(n);
    }

    public static short byteArrayToShort(byte[] arrby) {
        return AudioEffect.byteArrayToShort(arrby, 0);
    }

    public static short byteArrayToShort(byte[] object, int n) {
        object = ByteBuffer.wrap((byte[])object);
        ((ByteBuffer)object).order(ByteOrder.nativeOrder());
        return ((ByteBuffer)object).getShort(n);
    }

    public static byte[] concatArrays(byte[] ... arrby) {
        int n;
        int n2 = arrby.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            n3 += arrby[n].length;
        }
        byte[] arrby2 = new byte[n3];
        n2 = arrby.length;
        n3 = 0;
        for (n = 0; n < n2; ++n) {
            byte[] arrby3 = arrby[n];
            System.arraycopy(arrby3, 0, arrby2, n3, arrby3.length);
            n3 += arrby3.length;
        }
        return arrby2;
    }

    private void createNativeEventHandler() {
        Looper looper = Looper.myLooper();
        this.mNativeEventHandler = looper != null ? new NativeEventHandler(this, looper) : ((looper = Looper.getMainLooper()) != null ? new NativeEventHandler(this, looper) : null);
    }

    public static byte[] floatToByteArray(float f) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.putFloat(f);
        return byteBuffer.array();
    }

    public static byte[] intToByteArray(int n) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.putInt(n);
        return byteBuffer.array();
    }

    public static boolean isEffectTypeAvailable(UUID uUID) {
        Descriptor[] arrdescriptor = AudioEffect.queryEffects();
        if (arrdescriptor == null) {
            return false;
        }
        for (int i = 0; i < arrdescriptor.length; ++i) {
            if (!arrdescriptor[i].type.equals(uUID)) continue;
            return true;
        }
        return false;
    }

    public static boolean isError(int n) {
        boolean bl = n < 0;
        return bl;
    }

    private final native int native_command(int var1, int var2, byte[] var3, int var4, byte[] var5);

    private final native void native_finalize();

    private final native boolean native_getEnabled();

    private final native int native_getParameter(int var1, byte[] var2, int var3, byte[] var4);

    private final native boolean native_hasControl();

    private static final native void native_init();

    private static native Object[] native_query_effects();

    private static native Object[] native_query_pre_processing(int var0);

    private final native void native_release();

    private final native int native_setEnabled(boolean var1);

    private final native int native_setParameter(int var1, byte[] var2, int var3, byte[] var4);

    private final native int native_setup(Object var1, String var2, String var3, int var4, int var5, int[] var6, Object[] var7, String var8);

    private static void postEventFromNative(Object object, int n, int n2, int n3, Object object2) {
        if ((object = (AudioEffect)((WeakReference)object).get()) == null) {
            return;
        }
        NativeEventHandler nativeEventHandler = ((AudioEffect)object).mNativeEventHandler;
        if (nativeEventHandler != null) {
            object2 = nativeEventHandler.obtainMessage(n, n2, n3, object2);
            ((AudioEffect)object).mNativeEventHandler.sendMessage((Message)object2);
        }
    }

    public static Descriptor[] queryEffects() {
        return (Descriptor[])AudioEffect.native_query_effects();
    }

    public static Descriptor[] queryPreProcessings(int n) {
        return (Descriptor[])AudioEffect.native_query_pre_processing(n);
    }

    public static byte[] shortToByteArray(short s) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.putShort(s);
        return byteBuffer.array();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void checkState(String string2) throws IllegalStateException {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mState == 1) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" called on uninitialized AudioEffect.");
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    public void checkStatus(int n) {
        if (AudioEffect.isError(n)) {
            if (n != -5) {
                if (n != -4) {
                    throw new RuntimeException("AudioEffect: set/get parameter error");
                }
                throw new IllegalArgumentException("AudioEffect: bad parameter value");
            }
            throw new UnsupportedOperationException("AudioEffect: invalid parameter operation");
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int command(int n, byte[] arrby, byte[] arrby2) throws IllegalStateException {
        this.checkState("command()");
        return this.native_command(n, arrby.length, arrby, arrby2.length, arrby2);
    }

    protected void finalize() {
        this.native_finalize();
    }

    public Descriptor getDescriptor() throws IllegalStateException {
        this.checkState("getDescriptor()");
        return this.mDescriptor;
    }

    public boolean getEnabled() throws IllegalStateException {
        this.checkState("getEnabled()");
        return this.native_getEnabled();
    }

    public int getId() throws IllegalStateException {
        this.checkState("getId()");
        return this.mId;
    }

    public int getParameter(int n, byte[] arrby) throws IllegalStateException {
        return this.getParameter(AudioEffect.intToByteArray(n), arrby);
    }

    public int getParameter(int n, int[] arrn) throws IllegalStateException {
        if (arrn.length > 2) {
            return -4;
        }
        byte[] arrby = AudioEffect.intToByteArray(n);
        byte[] arrby2 = new byte[arrn.length * 4];
        n = this.getParameter(arrby, arrby2);
        if (n != 4 && n != 8) {
            n = -1;
        } else {
            arrn[0] = AudioEffect.byteArrayToInt(arrby2);
            if (n == 8) {
                arrn[1] = AudioEffect.byteArrayToInt(arrby2, 4);
            }
            n /= 4;
        }
        return n;
    }

    public int getParameter(int n, short[] arrs) throws IllegalStateException {
        if (arrs.length > 2) {
            return -4;
        }
        byte[] arrby = AudioEffect.intToByteArray(n);
        byte[] arrby2 = new byte[arrs.length * 2];
        n = this.getParameter(arrby, arrby2);
        if (n != 2 && n != 4) {
            n = -1;
        } else {
            arrs[0] = AudioEffect.byteArrayToShort(arrby2);
            if (n == 4) {
                arrs[1] = AudioEffect.byteArrayToShort(arrby2, 2);
            }
            n /= 2;
        }
        return n;
    }

    public int getParameter(byte[] arrby, byte[] arrby2) throws IllegalStateException {
        this.checkState("getParameter()");
        return this.native_getParameter(arrby.length, arrby, arrby2.length, arrby2);
    }

    @UnsupportedAppUsage
    public int getParameter(int[] arrn, byte[] arrby) throws IllegalStateException {
        byte[] arrby2;
        if (arrn.length > 2) {
            return -4;
        }
        byte[] arrby3 = arrby2 = AudioEffect.intToByteArray(arrn[0]);
        if (arrn.length > 1) {
            arrby3 = AudioEffect.concatArrays(arrby2, AudioEffect.intToByteArray(arrn[1]));
        }
        return this.getParameter(arrby3, arrby);
    }

    @UnsupportedAppUsage
    public int getParameter(int[] arrn, int[] arrn2) throws IllegalStateException {
        if (arrn.length <= 2 && arrn2.length <= 2) {
            int n;
            byte[] arrby;
            byte[] arrby2 = arrby = AudioEffect.intToByteArray(arrn[0]);
            if (arrn.length > 1) {
                arrby2 = AudioEffect.concatArrays(arrby, AudioEffect.intToByteArray(arrn[1]));
            }
            if ((n = this.getParameter(arrby2, (byte[])(arrn = new byte[arrn2.length * 4]))) != 4 && n != 8) {
                n = -1;
            } else {
                arrn2[0] = AudioEffect.byteArrayToInt((byte[])arrn);
                if (n == 8) {
                    arrn2[1] = AudioEffect.byteArrayToInt((byte[])arrn, 4);
                }
                n /= 4;
            }
            return n;
        }
        return -4;
    }

    public int getParameter(int[] arrn, short[] arrs) throws IllegalStateException {
        if (arrn.length <= 2 && arrs.length <= 2) {
            int n;
            byte[] arrby;
            byte[] arrby2 = arrby = AudioEffect.intToByteArray(arrn[0]);
            if (arrn.length > 1) {
                arrby2 = AudioEffect.concatArrays(arrby, AudioEffect.intToByteArray(arrn[1]));
            }
            if ((n = this.getParameter(arrby2, (byte[])(arrn = new byte[arrs.length * 2]))) != 2 && n != 4) {
                n = -1;
            } else {
                arrs[0] = AudioEffect.byteArrayToShort((byte[])arrn);
                if (n == 4) {
                    arrs[1] = AudioEffect.byteArrayToShort((byte[])arrn, 2);
                }
                n /= 2;
            }
            return n;
        }
        return -4;
    }

    public boolean hasControl() throws IllegalStateException {
        this.checkState("hasControl()");
        return this.native_hasControl();
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
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setControlStatusListener(OnControlStatusChangeListener onControlStatusChangeListener) {
        Object object = this.mListenerLock;
        // MONITORENTER : object
        this.mControlChangeStatusListener = onControlStatusChangeListener;
        // MONITOREXIT : object
        if (onControlStatusChangeListener == null) return;
        if (this.mNativeEventHandler != null) return;
        this.createNativeEventHandler();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setEnableStatusListener(OnEnableStatusChangeListener onEnableStatusChangeListener) {
        Object object = this.mListenerLock;
        // MONITORENTER : object
        this.mEnableStatusChangeListener = onEnableStatusChangeListener;
        // MONITOREXIT : object
        if (onEnableStatusChangeListener == null) return;
        if (this.mNativeEventHandler != null) return;
        this.createNativeEventHandler();
    }

    public int setEnabled(boolean bl) throws IllegalStateException {
        this.checkState("setEnabled()");
        return this.native_setEnabled(bl);
    }

    public int setParameter(int n, int n2) throws IllegalStateException {
        return this.setParameter(AudioEffect.intToByteArray(n), AudioEffect.intToByteArray(n2));
    }

    public int setParameter(int n, short s) throws IllegalStateException {
        return this.setParameter(AudioEffect.intToByteArray(n), AudioEffect.shortToByteArray(s));
    }

    public int setParameter(int n, byte[] arrby) throws IllegalStateException {
        return this.setParameter(AudioEffect.intToByteArray(n), arrby);
    }

    public int setParameter(byte[] arrby, byte[] arrby2) throws IllegalStateException {
        this.checkState("setParameter()");
        return this.native_setParameter(arrby.length, arrby, arrby2.length, arrby2);
    }

    public int setParameter(int[] arrn, byte[] arrby) throws IllegalStateException {
        byte[] arrby2;
        if (arrn.length > 2) {
            return -4;
        }
        byte[] arrby3 = arrby2 = AudioEffect.intToByteArray(arrn[0]);
        if (arrn.length > 1) {
            arrby3 = AudioEffect.concatArrays(arrby2, AudioEffect.intToByteArray(arrn[1]));
        }
        return this.setParameter(arrby3, arrby);
    }

    public int setParameter(int[] arrn, int[] arrn2) throws IllegalStateException {
        if (arrn.length <= 2 && arrn2.length <= 2) {
            byte[] arrby;
            byte[] arrby2 = arrby = AudioEffect.intToByteArray(arrn[0]);
            if (arrn.length > 1) {
                arrby2 = AudioEffect.concatArrays(arrby, AudioEffect.intToByteArray(arrn[1]));
            }
            arrby = AudioEffect.intToByteArray(arrn2[0]);
            arrn = arrby;
            if (arrn2.length > 1) {
                arrn = AudioEffect.concatArrays(arrby, AudioEffect.intToByteArray(arrn2[1]));
            }
            return this.setParameter(arrby2, (byte[])arrn);
        }
        return -4;
    }

    @UnsupportedAppUsage
    public int setParameter(int[] arrn, short[] arrs) throws IllegalStateException {
        if (arrn.length <= 2 && arrs.length <= 2) {
            byte[] arrby;
            byte[] arrby2 = arrby = AudioEffect.intToByteArray(arrn[0]);
            if (arrn.length > 1) {
                arrby2 = AudioEffect.concatArrays(arrby, AudioEffect.intToByteArray(arrn[1]));
            }
            arrby = AudioEffect.shortToByteArray(arrs[0]);
            arrn = arrby;
            if (arrs.length > 1) {
                arrn = AudioEffect.concatArrays(arrby, AudioEffect.shortToByteArray(arrs[1]));
            }
            return this.setParameter(arrby2, (byte[])arrn);
        }
        return -4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setParameterListener(OnParameterChangeListener onParameterChangeListener) {
        Object object = this.mListenerLock;
        // MONITORENTER : object
        this.mParameterChangeListener = onParameterChangeListener;
        // MONITOREXIT : object
        if (onParameterChangeListener == null) return;
        if (this.mNativeEventHandler != null) return;
        this.createNativeEventHandler();
    }

    public static class Descriptor {
        public String connectMode;
        public String implementor;
        public String name;
        public UUID type;
        public UUID uuid;

        public Descriptor() {
        }

        public Descriptor(Parcel parcel) {
            this.type = UUID.fromString(parcel.readString());
            this.uuid = UUID.fromString(parcel.readString());
            this.connectMode = parcel.readString();
            this.name = parcel.readString();
            this.implementor = parcel.readString();
        }

        public Descriptor(String string2, String string3, String string4, String string5, String string6) {
            this.type = UUID.fromString(string2);
            this.uuid = UUID.fromString(string3);
            this.connectMode = string4;
            this.name = string5;
            this.implementor = string6;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && object instanceof Descriptor) {
                object = (Descriptor)object;
                if (!(this.type.equals(((Descriptor)object).type) && this.uuid.equals(((Descriptor)object).uuid) && this.connectMode.equals(((Descriptor)object).connectMode) && this.name.equals(((Descriptor)object).name) && this.implementor.equals(((Descriptor)object).implementor))) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.type, this.uuid, this.connectMode, this.name, this.implementor);
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeString(this.type.toString());
            parcel.writeString(this.uuid.toString());
            parcel.writeString(this.connectMode);
            parcel.writeString(this.name);
            parcel.writeString(this.implementor);
        }
    }

    private class NativeEventHandler
    extends Handler {
        private AudioEffect mAudioEffect;

        public NativeEventHandler(AudioEffect audioEffect2, Looper looper) {
            super(looper);
            this.mAudioEffect = audioEffect2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message arrby) {
            OnControlStatusChangeListener onControlStatusChangeListener;
            if (this.mAudioEffect == null) {
                return;
            }
            int n = arrby.what;
            boolean bl = true;
            boolean bl2 = true;
            if (n != 0) {
                OnEnableStatusChangeListener onEnableStatusChangeListener;
                if (n != 1) {
                    OnParameterChangeListener onParameterChangeListener;
                    if (n != 2) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("handleMessage() Unknown event type: ");
                        stringBuilder.append(arrby.what);
                        Log.e(AudioEffect.TAG, stringBuilder.toString());
                        return;
                    }
                    byte[] arrby2 = AudioEffect.this.mListenerLock;
                    synchronized (arrby2) {
                        onParameterChangeListener = this.mAudioEffect.mParameterChangeListener;
                    }
                    if (onParameterChangeListener == null) return;
                    int n2 = arrby.arg1;
                    byte[] arrby3 = (byte[])arrby.obj;
                    int n3 = AudioEffect.byteArrayToInt(arrby3, 0);
                    int n4 = AudioEffect.byteArrayToInt(arrby3, 4);
                    n = AudioEffect.byteArrayToInt(arrby3, 8);
                    arrby2 = new byte[n4];
                    arrby = new byte[n];
                    System.arraycopy(arrby3, 12, arrby2, 0, n4);
                    System.arraycopy(arrby3, n2, arrby, 0, n);
                    onParameterChangeListener.onParameterChange(this.mAudioEffect, n3, arrby2, arrby);
                    return;
                }
                Object object = AudioEffect.this.mListenerLock;
                synchronized (object) {
                    onEnableStatusChangeListener = this.mAudioEffect.mEnableStatusChangeListener;
                }
                if (onEnableStatusChangeListener == null) return;
                object = this.mAudioEffect;
                if (arrby.arg1 == 0) {
                    bl2 = false;
                }
                onEnableStatusChangeListener.onEnableStatusChange((AudioEffect)object, bl2);
                return;
            }
            Object object = AudioEffect.this.mListenerLock;
            synchronized (object) {
                onControlStatusChangeListener = this.mAudioEffect.mControlChangeStatusListener;
            }
            if (onControlStatusChangeListener == null) return;
            object = this.mAudioEffect;
            bl2 = arrby.arg1 != 0 ? bl : false;
            onControlStatusChangeListener.onControlStatusChange((AudioEffect)object, bl2);
        }
    }

    public static interface OnControlStatusChangeListener {
        public void onControlStatusChange(AudioEffect var1, boolean var2);
    }

    public static interface OnEnableStatusChangeListener {
        public void onEnableStatusChange(AudioEffect var1, boolean var2);
    }

    public static interface OnParameterChangeListener {
        public void onParameterChange(AudioEffect var1, int var2, byte[] var3, byte[] var4);
    }

}

