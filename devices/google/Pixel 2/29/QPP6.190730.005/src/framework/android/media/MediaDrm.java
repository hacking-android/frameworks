/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.media.DeniedByServerException;
import android.media.NotProvisionedException;
import android.media.ResourceBusyException;
import android.media.UnsupportedSchemeException;
import android.media._$$Lambda$MediaDrm$4XHJHM_muz_p2PFHVhlVJb_7ccc;
import android.media._$$Lambda$MediaDrm$8rRollK1F3eENvuaBGoS8u__heQ;
import android.media._$$Lambda$MediaDrm$IvEWhXQgSYABwC6_1bdnhTJ4V2I;
import android.media._$$Lambda$MediaDrm$V4Xmxq2t4qcaWIsuRLRluTj6MT0;
import android.media._$$Lambda$MediaDrm$_FHBF1q3qSxz22Mhv8jmgjN4xt0;
import android.media._$$Lambda$MediaDrm$btxNighXxrJ0k5ooHZIA_tMesRA;
import android.media._$$Lambda$MediaDrm$dloezJ1eKxYxi1Oq_oYrMXoRpPM;
import android.media._$$Lambda$MediaDrm$o5lC7TOBZhvtA31JYaLa_MogSw4;
import android.media._$$Lambda$MediaDrm$yt6nGQRkzqmvdepRhmHi5hpgAOo;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class MediaDrm
implements AutoCloseable {
    public static final int CERTIFICATE_TYPE_NONE = 0;
    public static final int CERTIFICATE_TYPE_X509 = 1;
    private static final int DRM_EVENT = 200;
    public static final int EVENT_KEY_EXPIRED = 3;
    public static final int EVENT_KEY_REQUIRED = 2;
    public static final int EVENT_PROVISION_REQUIRED = 1;
    public static final int EVENT_SESSION_RECLAIMED = 5;
    public static final int EVENT_VENDOR_DEFINED = 4;
    private static final int EXPIRATION_UPDATE = 201;
    public static final int HDCP_LEVEL_UNKNOWN = 0;
    public static final int HDCP_NONE = 1;
    public static final int HDCP_NO_DIGITAL_OUTPUT = Integer.MAX_VALUE;
    public static final int HDCP_V1 = 2;
    public static final int HDCP_V2 = 3;
    public static final int HDCP_V2_1 = 4;
    public static final int HDCP_V2_2 = 5;
    public static final int HDCP_V2_3 = 6;
    private static final int KEY_STATUS_CHANGE = 202;
    public static final int KEY_TYPE_OFFLINE = 2;
    public static final int KEY_TYPE_RELEASE = 3;
    public static final int KEY_TYPE_STREAMING = 1;
    public static final int OFFLINE_LICENSE_STATE_RELEASED = 2;
    public static final int OFFLINE_LICENSE_STATE_UNKNOWN = 0;
    public static final int OFFLINE_LICENSE_STATE_USABLE = 1;
    private static final String PERMISSION = "android.permission.ACCESS_DRM_CERTIFICATES";
    public static final String PROPERTY_ALGORITHMS = "algorithms";
    public static final String PROPERTY_DESCRIPTION = "description";
    public static final String PROPERTY_DEVICE_UNIQUE_ID = "deviceUniqueId";
    public static final String PROPERTY_VENDOR = "vendor";
    public static final String PROPERTY_VERSION = "version";
    public static final int SECURITY_LEVEL_HW_SECURE_ALL = 5;
    public static final int SECURITY_LEVEL_HW_SECURE_CRYPTO = 3;
    public static final int SECURITY_LEVEL_HW_SECURE_DECODE = 4;
    public static final int SECURITY_LEVEL_MAX = 6;
    public static final int SECURITY_LEVEL_SW_SECURE_CRYPTO = 1;
    public static final int SECURITY_LEVEL_SW_SECURE_DECODE = 2;
    public static final int SECURITY_LEVEL_UNKNOWN = 0;
    private static final int SESSION_LOST_STATE = 203;
    private static final String TAG = "MediaDrm";
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mClosed = new AtomicBoolean();
    private final Map<Integer, ListenerWithExecutor> mListenerMap = new ConcurrentHashMap<Integer, ListenerWithExecutor>();
    private long mNativeContext;

    static {
        System.loadLibrary("media_jni");
        MediaDrm.native_init();
    }

    public MediaDrm(UUID uUID) throws UnsupportedSchemeException {
        this.native_setup(new WeakReference<MediaDrm>(this), MediaDrm.getByteArrayFromUUID(uUID), ActivityThread.currentOpPackageName());
        this.mCloseGuard.open("release");
    }

    private void clearGenericListener(int n) {
        this.mListenerMap.remove(n);
    }

    private Handler createHandler() {
        Object object = Looper.myLooper();
        object = object != null ? new Handler((Looper)object) : ((object = Looper.getMainLooper()) != null ? new Handler((Looper)object) : null);
        return object;
    }

    private Consumer<ListenerArgs> createOnEventListener(OnEventListener onEventListener) {
        return new _$$Lambda$MediaDrm$8rRollK1F3eENvuaBGoS8u__heQ(this, onEventListener);
    }

    private Consumer<ListenerArgs> createOnExpirationUpdateListener(OnExpirationUpdateListener onExpirationUpdateListener) {
        return new _$$Lambda$MediaDrm$btxNighXxrJ0k5ooHZIA_tMesRA(this, onExpirationUpdateListener);
    }

    private Consumer<ListenerArgs> createOnKeyStatusChangeListener(OnKeyStatusChangeListener onKeyStatusChangeListener) {
        return new _$$Lambda$MediaDrm$_FHBF1q3qSxz22Mhv8jmgjN4xt0(this, onKeyStatusChangeListener);
    }

    private Consumer<ListenerArgs> createOnSessionLostStateListener(OnSessionLostStateListener onSessionLostStateListener) {
        return new _$$Lambda$MediaDrm$4XHJHM_muz_p2PFHVhlVJb_7ccc(this, onSessionLostStateListener);
    }

    private static final native byte[] decryptNative(MediaDrm var0, byte[] var1, byte[] var2, byte[] var3, byte[] var4);

    private static final native byte[] encryptNative(MediaDrm var0, byte[] var1, byte[] var2, byte[] var3, byte[] var4);

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

    public static final int getMaxSecurityLevel() {
        return 6;
    }

    private native PersistableBundle getMetricsNative();

    private native ProvisionRequest getProvisionRequestNative(int var1, String var2);

    public static final boolean isCryptoSchemeSupported(UUID uUID) {
        return MediaDrm.isCryptoSchemeSupportedNative(MediaDrm.getByteArrayFromUUID(uUID), null, 0);
    }

    public static final boolean isCryptoSchemeSupported(UUID uUID, String string2) {
        return MediaDrm.isCryptoSchemeSupportedNative(MediaDrm.getByteArrayFromUUID(uUID), string2, 0);
    }

    public static final boolean isCryptoSchemeSupported(UUID uUID, String string2, int n) {
        return MediaDrm.isCryptoSchemeSupportedNative(MediaDrm.getByteArrayFromUUID(uUID), string2, n);
    }

    private static final native boolean isCryptoSchemeSupportedNative(byte[] var0, String var1, int var2);

    private List<KeyStatus> keyStatusListFromParcel(Parcel parcel) {
        int n;
        ArrayList<KeyStatus> arrayList = new ArrayList<KeyStatus>(n);
        for (n = parcel.readInt(); n > 0; --n) {
            arrayList.add(new KeyStatus(parcel.createByteArray(), parcel.readInt()));
        }
        return arrayList;
    }

    public static /* synthetic */ Consumer lambda$IvEWhXQgSYABwC6_1bdnhTJ4V2I(MediaDrm mediaDrm, OnEventListener onEventListener) {
        return mediaDrm.createOnEventListener(onEventListener);
    }

    public static /* synthetic */ Consumer lambda$V4Xmxq2t4qcaWIsuRLRluTj6MT0(MediaDrm mediaDrm, OnKeyStatusChangeListener onKeyStatusChangeListener) {
        return mediaDrm.createOnKeyStatusChangeListener(onKeyStatusChangeListener);
    }

    public static /* synthetic */ Consumer lambda$dloezJ1eKxYxi1Oq-oYrMXoRpPM(MediaDrm mediaDrm, OnExpirationUpdateListener onExpirationUpdateListener) {
        return mediaDrm.createOnExpirationUpdateListener(onExpirationUpdateListener);
    }

    public static /* synthetic */ Consumer lambda$o5lC7TOBZhvtA31JYaLa-MogSw4(MediaDrm mediaDrm, OnSessionLostStateListener onSessionLostStateListener) {
        return mediaDrm.createOnSessionLostStateListener(onSessionLostStateListener);
    }

    static /* synthetic */ void lambda$postEventFromNative$4(MediaDrm object, Object object2, ListenerWithExecutor listenerWithExecutor, int n, int n2) {
        if (((MediaDrm)object).mNativeContext == 0L) {
            Log.w("MediaDrm", "MediaDrm went away with unhandled events");
            return;
        }
        if (object2 != null && object2 instanceof Parcel) {
            object = (Parcel)object2;
            listenerWithExecutor.mConsumer.accept(new ListenerArgs((Parcel)object, n, n2));
        }
    }

    private static final native void native_init();

    private final native void native_setup(Object var1, byte[] var2, String var3);

    private static void postEventFromNative(Object object, int n, int n2, int n3, Object object2) {
        MediaDrm mediaDrm = (MediaDrm)((WeakReference)object).get();
        if (mediaDrm == null) {
            return;
        }
        switch (n) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown message type ");
                ((StringBuilder)object).append(n);
                Log.e("MediaDrm", ((StringBuilder)object).toString());
                break;
            }
            case 200: 
            case 201: 
            case 202: 
            case 203: {
                object = mediaDrm.mListenerMap.get(n);
                if (object == null) break;
                object2 = new _$$Lambda$MediaDrm$yt6nGQRkzqmvdepRhmHi5hpgAOo(mediaDrm, object2, (ListenerWithExecutor)object, n2, n3);
                ((ListenerWithExecutor)object).mExecutor.execute((Runnable)object2);
            }
        }
    }

    private native Certificate provideProvisionResponseNative(byte[] var1) throws DeniedByServerException;

    private static final native void setCipherAlgorithmNative(MediaDrm var0, byte[] var1, String var2);

    private <T> void setGenericListener(int n, Executor executor, T t, Function<T, Consumer<ListenerArgs>> function) {
        this.mListenerMap.put(n, new ListenerWithExecutor(executor, function.apply(t)));
    }

    private <T> void setListenerWithExecutor(int n, Executor executor, T t, Function<T, Consumer<ListenerArgs>> function) {
        if (executor != null && t != null) {
            this.setGenericListener(n, executor, t, function);
            return;
        }
        throw new IllegalArgumentException(String.format("executor %s listener %s", executor, t));
    }

    private <T> void setListenerWithHandler(int n, Handler handler, T t, Function<T, Consumer<ListenerArgs>> function) {
        if (t == null) {
            this.clearGenericListener(n);
        } else {
            if (handler == null) {
                handler = this.createHandler();
            }
            this.setGenericListener(n, new HandlerExecutor(handler), t, function);
        }
    }

    private static final native void setMacAlgorithmNative(MediaDrm var0, byte[] var1, String var2);

    private static final native byte[] signNative(MediaDrm var0, byte[] var1, byte[] var2, byte[] var3);

    private static final native byte[] signRSANative(MediaDrm var0, byte[] var1, String var2, byte[] var3, byte[] var4);

    private static final native boolean verifyNative(MediaDrm var0, byte[] var1, byte[] var2, byte[] var3, byte[] var4);

    public void clearOnEventListener() {
        this.clearGenericListener(200);
    }

    public void clearOnExpirationUpdateListener() {
        this.clearGenericListener(201);
    }

    public void clearOnKeyStatusChangeListener() {
        this.clearGenericListener(202);
    }

    public void clearOnSessionLostStateListener() {
        this.clearGenericListener(203);
    }

    @Override
    public void close() {
        this.release();
    }

    public native void closeSession(byte[] var1);

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.release();
            return;
        }
        finally {
            super.finalize();
        }
    }

    @UnsupportedAppUsage
    public CertificateRequest getCertificateRequest(int n, String object) {
        object = this.getProvisionRequestNative(n, (String)object);
        return new CertificateRequest(((ProvisionRequest)object).getData(), ((ProvisionRequest)object).getDefaultUrl());
    }

    public native int getConnectedHdcpLevel();

    public CryptoSession getCryptoSession(byte[] arrby, String string2, String string3) {
        return new CryptoSession(arrby, string2, string3);
    }

    public native KeyRequest getKeyRequest(byte[] var1, byte[] var2, String var3, int var4, HashMap<String, String> var5) throws NotProvisionedException;

    public native int getMaxHdcpLevel();

    public native int getMaxSessionCount();

    public PersistableBundle getMetrics() {
        return this.getMetricsNative();
    }

    public native List<byte[]> getOfflineLicenseKeySetIds();

    public native int getOfflineLicenseState(byte[] var1);

    public native int getOpenSessionCount();

    public native byte[] getPropertyByteArray(String var1);

    public native String getPropertyString(String var1);

    public ProvisionRequest getProvisionRequest() {
        return this.getProvisionRequestNative(0, "");
    }

    public native byte[] getSecureStop(byte[] var1);

    public native List<byte[]> getSecureStopIds();

    public native List<byte[]> getSecureStops();

    public native int getSecurityLevel(byte[] var1);

    public /* synthetic */ void lambda$createOnEventListener$0$MediaDrm(OnEventListener onEventListener, ListenerArgs listenerArgs) {
        byte[] arrby;
        byte[] arrby2 = arrby = listenerArgs.parcel.createByteArray();
        if (arrby.length == 0) {
            arrby2 = null;
        }
        if ((arrby = listenerArgs.parcel.createByteArray()).length == 0) {
            arrby = null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Drm event (");
        stringBuilder.append(listenerArgs.arg1);
        stringBuilder.append(",");
        stringBuilder.append(listenerArgs.arg2);
        stringBuilder.append(")");
        Log.i("MediaDrm", stringBuilder.toString());
        onEventListener.onEvent(this, arrby2, listenerArgs.arg1, listenerArgs.arg2, arrby);
    }

    public /* synthetic */ void lambda$createOnExpirationUpdateListener$2$MediaDrm(OnExpirationUpdateListener onExpirationUpdateListener, ListenerArgs object) {
        byte[] arrby = ((ListenerArgs)object).parcel.createByteArray();
        if (arrby.length > 0) {
            long l = ((ListenerArgs)object).parcel.readLong();
            object = new StringBuilder();
            ((StringBuilder)object).append("Drm key expiration update: ");
            ((StringBuilder)object).append(l);
            Log.i("MediaDrm", ((StringBuilder)object).toString());
            onExpirationUpdateListener.onExpirationUpdate(this, arrby, l);
        }
    }

    public /* synthetic */ void lambda$createOnKeyStatusChangeListener$1$MediaDrm(OnKeyStatusChangeListener onKeyStatusChangeListener, ListenerArgs listenerArgs) {
        byte[] arrby = listenerArgs.parcel.createByteArray();
        if (arrby.length > 0) {
            List<KeyStatus> list = this.keyStatusListFromParcel(listenerArgs.parcel);
            boolean bl = listenerArgs.parcel.readInt() != 0;
            Log.i("MediaDrm", "Drm key status changed");
            onKeyStatusChangeListener.onKeyStatusChange(this, arrby, list, bl);
        }
    }

    public /* synthetic */ void lambda$createOnSessionLostStateListener$3$MediaDrm(OnSessionLostStateListener onSessionLostStateListener, ListenerArgs arrby) {
        arrby = ((ListenerArgs)arrby).parcel.createByteArray();
        Log.i("MediaDrm", "Drm session lost state event: ");
        onSessionLostStateListener.onSessionLostState(this, arrby);
    }

    public final native void native_release();

    public byte[] openSession() throws NotProvisionedException, ResourceBusyException {
        return this.openSession(MediaDrm.getMaxSecurityLevel());
    }

    public native byte[] openSession(int var1) throws NotProvisionedException, ResourceBusyException;

    @UnsupportedAppUsage
    public Certificate provideCertificateResponse(byte[] arrby) throws DeniedByServerException {
        return this.provideProvisionResponseNative(arrby);
    }

    public native byte[] provideKeyResponse(byte[] var1, byte[] var2) throws NotProvisionedException, DeniedByServerException;

    public void provideProvisionResponse(byte[] arrby) throws DeniedByServerException {
        this.provideProvisionResponseNative(arrby);
    }

    public native HashMap<String, String> queryKeyStatus(byte[] var1);

    @Deprecated
    public void release() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            this.native_release();
        }
    }

    public void releaseAllSecureStops() {
        this.removeAllSecureStops();
    }

    public native void releaseSecureStops(byte[] var1);

    public native void removeAllSecureStops();

    public native void removeKeys(byte[] var1);

    public native void removeOfflineLicense(byte[] var1);

    public native void removeSecureStop(byte[] var1);

    public native void restoreKeys(byte[] var1, byte[] var2);

    public void setOnEventListener(OnEventListener onEventListener) {
        this.setOnEventListener(onEventListener, null);
    }

    public void setOnEventListener(OnEventListener onEventListener, Handler handler) {
        this.setListenerWithHandler(200, handler, onEventListener, new _$$Lambda$MediaDrm$IvEWhXQgSYABwC6_1bdnhTJ4V2I(this));
    }

    public void setOnEventListener(Executor executor, OnEventListener onEventListener) {
        this.setListenerWithExecutor(200, executor, onEventListener, new _$$Lambda$MediaDrm$IvEWhXQgSYABwC6_1bdnhTJ4V2I(this));
    }

    public void setOnExpirationUpdateListener(OnExpirationUpdateListener onExpirationUpdateListener, Handler handler) {
        this.setListenerWithHandler(201, handler, onExpirationUpdateListener, new _$$Lambda$MediaDrm$dloezJ1eKxYxi1Oq_oYrMXoRpPM(this));
    }

    public void setOnExpirationUpdateListener(Executor executor, OnExpirationUpdateListener onExpirationUpdateListener) {
        this.setListenerWithExecutor(201, executor, onExpirationUpdateListener, new _$$Lambda$MediaDrm$dloezJ1eKxYxi1Oq_oYrMXoRpPM(this));
    }

    public void setOnKeyStatusChangeListener(OnKeyStatusChangeListener onKeyStatusChangeListener, Handler handler) {
        this.setListenerWithHandler(202, handler, onKeyStatusChangeListener, new _$$Lambda$MediaDrm$V4Xmxq2t4qcaWIsuRLRluTj6MT0(this));
    }

    public void setOnKeyStatusChangeListener(Executor executor, OnKeyStatusChangeListener onKeyStatusChangeListener) {
        this.setListenerWithExecutor(202, executor, onKeyStatusChangeListener, new _$$Lambda$MediaDrm$V4Xmxq2t4qcaWIsuRLRluTj6MT0(this));
    }

    public void setOnSessionLostStateListener(OnSessionLostStateListener onSessionLostStateListener, Handler handler) {
        this.setListenerWithHandler(203, handler, onSessionLostStateListener, new _$$Lambda$MediaDrm$o5lC7TOBZhvtA31JYaLa_MogSw4(this));
    }

    public void setOnSessionLostStateListener(Executor executor, OnSessionLostStateListener onSessionLostStateListener) {
        this.setListenerWithExecutor(203, executor, onSessionLostStateListener, new _$$Lambda$MediaDrm$o5lC7TOBZhvtA31JYaLa_MogSw4(this));
    }

    public native void setPropertyByteArray(String var1, byte[] var2);

    public native void setPropertyString(String var1, String var2);

    @UnsupportedAppUsage
    public byte[] signRSA(byte[] arrby, String string2, byte[] arrby2, byte[] arrby3) {
        return MediaDrm.signRSANative(this, arrby, string2, arrby2, arrby3);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ArrayProperty {
    }

    public static final class Certificate {
        private byte[] mCertificateData;
        private byte[] mWrappedKey;

        Certificate() {
        }

        @UnsupportedAppUsage
        public byte[] getContent() {
            byte[] arrby = this.mCertificateData;
            if (arrby != null) {
                return arrby;
            }
            throw new RuntimeException("Certificate is not initialized");
        }

        @UnsupportedAppUsage
        public byte[] getWrappedPrivateKey() {
            byte[] arrby = this.mWrappedKey;
            if (arrby != null) {
                return arrby;
            }
            throw new RuntimeException("Certificate is not initialized");
        }
    }

    public static final class CertificateRequest {
        private byte[] mData;
        private String mDefaultUrl;

        CertificateRequest(byte[] arrby, String string2) {
            this.mData = arrby;
            this.mDefaultUrl = string2;
        }

        @UnsupportedAppUsage
        public byte[] getData() {
            return this.mData;
        }

        @UnsupportedAppUsage
        public String getDefaultUrl() {
            return this.mDefaultUrl;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CertificateType {
    }

    public final class CryptoSession {
        private byte[] mSessionId;

        CryptoSession(byte[] arrby, String string2, String string3) {
            this.mSessionId = arrby;
            MediaDrm.setCipherAlgorithmNative(MediaDrm.this, arrby, string2);
            MediaDrm.setMacAlgorithmNative(MediaDrm.this, arrby, string3);
        }

        public byte[] decrypt(byte[] arrby, byte[] arrby2, byte[] arrby3) {
            return MediaDrm.decryptNative(MediaDrm.this, this.mSessionId, arrby, arrby2, arrby3);
        }

        public byte[] encrypt(byte[] arrby, byte[] arrby2, byte[] arrby3) {
            return MediaDrm.encryptNative(MediaDrm.this, this.mSessionId, arrby, arrby2, arrby3);
        }

        public byte[] sign(byte[] arrby, byte[] arrby2) {
            return MediaDrm.signNative(MediaDrm.this, this.mSessionId, arrby, arrby2);
        }

        public boolean verify(byte[] arrby, byte[] arrby2, byte[] arrby3) {
            return MediaDrm.verifyNative(MediaDrm.this, this.mSessionId, arrby, arrby2, arrby3);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DrmEvent {
    }

    @Deprecated
    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface HdcpLevel {
    }

    public static final class KeyRequest {
        public static final int REQUEST_TYPE_INITIAL = 0;
        public static final int REQUEST_TYPE_NONE = 3;
        public static final int REQUEST_TYPE_RELEASE = 2;
        public static final int REQUEST_TYPE_RENEWAL = 1;
        public static final int REQUEST_TYPE_UPDATE = 4;
        private byte[] mData;
        private String mDefaultUrl;
        private int mRequestType;

        KeyRequest() {
        }

        public byte[] getData() {
            byte[] arrby = this.mData;
            if (arrby != null) {
                return arrby;
            }
            throw new RuntimeException("KeyRequest is not initialized");
        }

        public String getDefaultUrl() {
            String string2 = this.mDefaultUrl;
            if (string2 != null) {
                return string2;
            }
            throw new RuntimeException("KeyRequest is not initialized");
        }

        public int getRequestType() {
            return this.mRequestType;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface RequestType {
        }

    }

    public static final class KeyStatus {
        public static final int STATUS_EXPIRED = 1;
        public static final int STATUS_INTERNAL_ERROR = 4;
        public static final int STATUS_OUTPUT_NOT_ALLOWED = 2;
        public static final int STATUS_PENDING = 3;
        public static final int STATUS_USABLE = 0;
        public static final int STATUS_USABLE_IN_FUTURE = 5;
        private final byte[] mKeyId;
        private final int mStatusCode;

        KeyStatus(byte[] arrby, int n) {
            this.mKeyId = arrby;
            this.mStatusCode = n;
        }

        public byte[] getKeyId() {
            return this.mKeyId;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface KeyStatusCode {
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface KeyType {
    }

    private static class ListenerArgs {
        private final int arg1;
        private final int arg2;
        private final Parcel parcel;

        public ListenerArgs(Parcel parcel, int n, int n2) {
            this.parcel = parcel;
            this.arg1 = n;
            this.arg2 = n2;
        }
    }

    private static class ListenerWithExecutor {
        private final Consumer<ListenerArgs> mConsumer;
        private final Executor mExecutor;

        public ListenerWithExecutor(Executor executor, Consumer<ListenerArgs> consumer) {
            this.mExecutor = executor;
            this.mConsumer = consumer;
        }
    }

    public static final class MediaDrmStateException
    extends IllegalStateException {
        private final String mDiagnosticInfo;
        private final int mErrorCode;

        public MediaDrmStateException(int n, String string2) {
            super(string2);
            this.mErrorCode = n;
            string2 = n < 0 ? "neg_" : "";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("android.media.MediaDrm.error_");
            stringBuilder.append(string2);
            stringBuilder.append(Math.abs(n));
            this.mDiagnosticInfo = stringBuilder.toString();
        }

        public String getDiagnosticInfo() {
            return this.mDiagnosticInfo;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }
    }

    public static final class MetricsConstants {
        public static final String CLOSE_SESSION_ERROR_COUNT = "drm.mediadrm.close_session.error.count";
        public static final String CLOSE_SESSION_ERROR_LIST = "drm.mediadrm.close_session.error.list";
        public static final String CLOSE_SESSION_OK_COUNT = "drm.mediadrm.close_session.ok.count";
        public static final String EVENT_KEY_EXPIRED_COUNT = "drm.mediadrm.event.KEY_EXPIRED.count";
        public static final String EVENT_KEY_NEEDED_COUNT = "drm.mediadrm.event.KEY_NEEDED.count";
        public static final String EVENT_PROVISION_REQUIRED_COUNT = "drm.mediadrm.event.PROVISION_REQUIRED.count";
        public static final String EVENT_SESSION_RECLAIMED_COUNT = "drm.mediadrm.event.SESSION_RECLAIMED.count";
        public static final String EVENT_VENDOR_DEFINED_COUNT = "drm.mediadrm.event.VENDOR_DEFINED.count";
        public static final String GET_DEVICE_UNIQUE_ID_ERROR_COUNT = "drm.mediadrm.get_device_unique_id.error.count";
        public static final String GET_DEVICE_UNIQUE_ID_ERROR_LIST = "drm.mediadrm.get_device_unique_id.error.list";
        public static final String GET_DEVICE_UNIQUE_ID_OK_COUNT = "drm.mediadrm.get_device_unique_id.ok.count";
        public static final String GET_KEY_REQUEST_ERROR_COUNT = "drm.mediadrm.get_key_request.error.count";
        public static final String GET_KEY_REQUEST_ERROR_LIST = "drm.mediadrm.get_key_request.error.list";
        public static final String GET_KEY_REQUEST_OK_COUNT = "drm.mediadrm.get_key_request.ok.count";
        public static final String GET_KEY_REQUEST_OK_TIME_MICROS = "drm.mediadrm.get_key_request.ok.average_time_micros";
        public static final String GET_PROVISION_REQUEST_ERROR_COUNT = "drm.mediadrm.get_provision_request.error.count";
        public static final String GET_PROVISION_REQUEST_ERROR_LIST = "drm.mediadrm.get_provision_request.error.list";
        public static final String GET_PROVISION_REQUEST_OK_COUNT = "drm.mediadrm.get_provision_request.ok.count";
        public static final String KEY_STATUS_EXPIRED_COUNT = "drm.mediadrm.key_status.EXPIRED.count";
        public static final String KEY_STATUS_INTERNAL_ERROR_COUNT = "drm.mediadrm.key_status.INTERNAL_ERROR.count";
        public static final String KEY_STATUS_OUTPUT_NOT_ALLOWED_COUNT = "drm.mediadrm.key_status_change.OUTPUT_NOT_ALLOWED.count";
        public static final String KEY_STATUS_PENDING_COUNT = "drm.mediadrm.key_status_change.PENDING.count";
        public static final String KEY_STATUS_USABLE_COUNT = "drm.mediadrm.key_status_change.USABLE.count";
        public static final String OPEN_SESSION_ERROR_COUNT = "drm.mediadrm.open_session.error.count";
        public static final String OPEN_SESSION_ERROR_LIST = "drm.mediadrm.open_session.error.list";
        public static final String OPEN_SESSION_OK_COUNT = "drm.mediadrm.open_session.ok.count";
        public static final String PROVIDE_KEY_RESPONSE_ERROR_COUNT = "drm.mediadrm.provide_key_response.error.count";
        public static final String PROVIDE_KEY_RESPONSE_ERROR_LIST = "drm.mediadrm.provide_key_response.error.list";
        public static final String PROVIDE_KEY_RESPONSE_OK_COUNT = "drm.mediadrm.provide_key_response.ok.count";
        public static final String PROVIDE_KEY_RESPONSE_OK_TIME_MICROS = "drm.mediadrm.provide_key_response.ok.average_time_micros";
        public static final String PROVIDE_PROVISION_RESPONSE_ERROR_COUNT = "drm.mediadrm.provide_provision_response.error.count";
        public static final String PROVIDE_PROVISION_RESPONSE_ERROR_LIST = "drm.mediadrm.provide_provision_response.error.list";
        public static final String PROVIDE_PROVISION_RESPONSE_OK_COUNT = "drm.mediadrm.provide_provision_response.ok.count";
        public static final String SESSION_END_TIMES_MS = "drm.mediadrm.session_end_times_ms";
        public static final String SESSION_START_TIMES_MS = "drm.mediadrm.session_start_times_ms";

        private MetricsConstants() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface OfflineLicenseState {
    }

    public static interface OnEventListener {
        public void onEvent(MediaDrm var1, byte[] var2, int var3, int var4, byte[] var5);
    }

    public static interface OnExpirationUpdateListener {
        public void onExpirationUpdate(MediaDrm var1, byte[] var2, long var3);
    }

    public static interface OnKeyStatusChangeListener {
        public void onKeyStatusChange(MediaDrm var1, byte[] var2, List<KeyStatus> var3, boolean var4);
    }

    public static interface OnSessionLostStateListener {
        public void onSessionLostState(MediaDrm var1, byte[] var2);
    }

    public static final class ProvisionRequest {
        private byte[] mData;
        private String mDefaultUrl;

        ProvisionRequest() {
        }

        public byte[] getData() {
            byte[] arrby = this.mData;
            if (arrby != null) {
                return arrby;
            }
            throw new RuntimeException("ProvisionRequest is not initialized");
        }

        public String getDefaultUrl() {
            String string2 = this.mDefaultUrl;
            if (string2 != null) {
                return string2;
            }
            throw new RuntimeException("ProvisionRequest is not initialized");
        }
    }

    @Deprecated
    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SecurityLevel {
    }

    public static final class SessionException
    extends RuntimeException {
        public static final int ERROR_RESOURCE_CONTENTION = 1;
        public static final int ERROR_UNKNOWN = 0;
        private final int mErrorCode;

        public SessionException(int n, String string2) {
            super(string2);
            this.mErrorCode = n;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface SessionErrorCode {
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StringProperty {
    }

}

