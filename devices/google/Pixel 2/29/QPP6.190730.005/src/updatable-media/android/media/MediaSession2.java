/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.media.session.MediaSessionManager
 *  android.media.session.MediaSessionManager$RemoteUserInfo
 *  android.os.BadParcelableException
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Process
 *  android.os.ResultReceiver
 *  android.util.ArrayMap
 *  android.util.ArraySet
 *  android.util.Log
 */
package android.media;

import android.app.PendingIntent;
import android.content.Context;
import android.media.Controller2Link;
import android.media.Session2Command;
import android.media.Session2CommandGroup;
import android.media.Session2Link;
import android.media.Session2Token;
import android.media._$$Lambda$MediaSession2$1$Ty9__5M_U_w4LH0UhCT90vhjsHE;
import android.media._$$Lambda$MediaSession2$4mb9hAWB7fa2IwV_RWmJ7c7__v4;
import android.media._$$Lambda$MediaSession2$53DgwUyPnHR49G3UlTPSmlD2b_I;
import android.media._$$Lambda$MediaSession2$MMa_VCh_tw_VYMOudFxbiuOiQrM;
import android.media.session.MediaSessionManager;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.ResultReceiver;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

public class MediaSession2
implements AutoCloseable {
    static final boolean DEBUG = Log.isLoggable((String)"MediaSession2", (int)3);
    private static final List<String> SESSION_ID_LIST = new ArrayList<String>();
    static final String TAG = "MediaSession2";
    final SessionCallback mCallback;
    final Executor mCallbackExecutor;
    private boolean mClosed;
    final Map<Controller2Link, ControllerInfo> mConnectedControllers = new HashMap<Controller2Link, ControllerInfo>();
    final Context mContext;
    private ForegroundServiceEventCallback mForegroundServiceEventCallback;
    final Object mLock = new Object();
    private boolean mPlaybackActive;
    private final Handler mResultHandler;
    private final PendingIntent mSessionActivity;
    private final String mSessionId;
    private final MediaSessionManager mSessionManager;
    final Session2Link mSessionStub;
    private final Session2Token mSessionToken;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    MediaSession2(Context object, String string, PendingIntent object2, Executor executor, SessionCallback sessionCallback, Bundle bundle) {
        synchronized (MediaSession2.class) {
            if (!SESSION_ID_LIST.contains(string)) {
                SESSION_ID_LIST.add(string);
                // MONITOREXIT [2, 3] lbl8 : MonitorExitStatement: MONITOREXIT : android.media.MediaSession2.class
                this.mContext = object;
                this.mSessionId = string;
                this.mSessionActivity = object2;
                this.mCallbackExecutor = executor;
                this.mCallback = sessionCallback;
                this.mSessionStub = new Session2Link(this);
                this.mSessionToken = new Session2Token(Process.myUid(), 0, object.getPackageName(), this.mSessionStub, bundle);
                this.mSessionManager = (MediaSessionManager)this.mContext.getSystemService("media_session");
                this.mResultHandler = new Handler(object.getMainLooper());
                this.mClosed = false;
                return;
            }
            ((StringBuilder)object2).append("Session ID must be unique. ID=");
            ((StringBuilder)object2).append(string);
            super(((StringBuilder)object2).toString());
            throw object;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static boolean hasCustomParcelable(Bundle bundle) {
        Throwable throwable2222;
        Parcel parcel;
        if (bundle == null) {
            return false;
        }
        Parcel parcel2 = null;
        Parcel parcel3 = null;
        parcel3 = parcel = Parcel.obtain();
        parcel2 = parcel;
        parcel.writeBundle(bundle);
        parcel3 = parcel;
        parcel2 = parcel;
        parcel.setDataPosition(0);
        parcel3 = parcel;
        parcel2 = parcel;
        parcel.readBundle(null).size();
        parcel.recycle();
        return false;
        {
            catch (Throwable throwable2222) {
            }
            catch (BadParcelableException badParcelableException) {}
            parcel3 = parcel2;
            {
                Log.d((String)TAG, (String)"Custom parcelable in bundle.", (Throwable)badParcelableException);
                if (parcel2 == null) return true;
            }
            parcel2.recycle();
            return true;
        }
        if (parcel3 == null) throw throwable2222;
        parcel3.recycle();
        throw throwable2222;
    }

    public void broadcastSessionCommand(Session2Command session2Command, Bundle bundle) {
        if (session2Command != null) {
            Iterator<ControllerInfo> iterator = this.getConnectedControllers().iterator();
            while (iterator.hasNext()) {
                iterator.next().sendSessionCommand(session2Command, bundle, null);
            }
            return;
        }
        throw new IllegalArgumentException("command shouldn't be null");
    }

    public void cancelSessionCommand(ControllerInfo controllerInfo, Object object) {
        if (controllerInfo != null) {
            if (object != null) {
                controllerInfo.cancelSessionCommand(object);
                return;
            }
            throw new IllegalArgumentException("token shouldn't be null");
        }
        throw new IllegalArgumentException("controller shouldn't be null");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void close() {
        block13 : {
            var1_1 = this.mLock;
            // MONITORENTER : var1_1
            if (!this.mClosed) break block13;
            // MONITOREXIT : var1_1
            return;
        }
        this.mClosed = true;
        var2_3 = this.getConnectedControllers();
        this.mConnectedControllers.clear();
        var3_4 = this.mForegroundServiceEventCallback;
        this.mForegroundServiceEventCallback = null;
        // MONITOREXIT : var1_1
        MediaSession2.SESSION_ID_LIST.remove(this.mSessionId);
        // MONITOREXIT : android.media.MediaSession2.class
        if (var3_4 == null) ** GOTO lbl20
        try {
            var3_4.onSessionClosed(this);
lbl20: // 2 sources:
            var1_1 = var2_3.iterator();
            while (var1_1.hasNext() != false) {
                var1_1.next().notifyDisconnected();
            }
            return;
        }
        catch (Exception var1_2) {
            // empty catch block
        }
    }

    SessionCallback getCallback() {
        return this.mCallback;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<ControllerInfo> getConnectedControllers() {
        ArrayList<ControllerInfo> arrayList = new ArrayList<ControllerInfo>();
        Object object = this.mLock;
        synchronized (object) {
            arrayList.addAll(this.mConnectedControllers.values());
            return arrayList;
        }
    }

    public String getId() {
        return this.mSessionId;
    }

    public Session2Token getToken() {
        return this.mSessionToken;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean isClosed() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mClosed;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isPlaybackActive() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mPlaybackActive;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public /* synthetic */ void lambda$onConnect$0$MediaSession2(ControllerInfo controllerInfo, Controller2Link controller2Link) {
        Object object;
        block35 : {
            block34 : {
                Object object2;
                block32 : {
                    block33 : {
                        boolean bl;
                        block30 : {
                            Object object3;
                            block31 : {
                                block28 : {
                                    Object object4;
                                    block29 : {
                                        try {
                                            bl = this.isClosed();
                                            if (!bl) break block28;
                                            if (false) return;
                                            if (!DEBUG) break block29;
                                            object4 = new StringBuilder();
                                            ((StringBuilder)object4).append("Rejecting connection or notifying that session is closed, controllerInfo=");
                                        }
                                        catch (Throwable throwable) {
                                            if (false) throw throwable;
                                            if (DEBUG) {
                                                object2 = new StringBuilder();
                                                ((StringBuilder)object2).append("Rejecting connection or notifying that session is closed, controllerInfo=");
                                                ((StringBuilder)object2).append(controllerInfo);
                                                Log.d((String)TAG, (String)((StringBuilder)object2).toString());
                                            }
                                            object2 = this.mLock;
                                            // MONITORENTER : object2
                                            this.mConnectedControllers.remove(controller2Link);
                                            // MONITOREXIT : object2
                                            controllerInfo.notifyDisconnected();
                                            throw throwable;
                                        }
                                        ((StringBuilder)object4).append(controllerInfo);
                                        Log.d((String)TAG, (String)((StringBuilder)object4).toString());
                                    }
                                    object4 = this.mLock;
                                    // MONITORENTER : object4
                                    this.mConnectedControllers.remove(controller2Link);
                                    // MONITOREXIT : object4
                                    controllerInfo.notifyDisconnected();
                                    return;
                                }
                                controllerInfo.mAllowedCommands = this.mCallback.onConnect(this, controllerInfo);
                                if (controllerInfo.mAllowedCommands != null || (bl = controllerInfo.isTrusted())) break block30;
                                if (false) return;
                                if (!DEBUG) break block31;
                                object3 = new StringBuilder();
                                ((StringBuilder)object3).append("Rejecting connection or notifying that session is closed, controllerInfo=");
                                ((StringBuilder)object3).append(controllerInfo);
                                Log.d((String)TAG, (String)((StringBuilder)object3).toString());
                            }
                            object3 = this.mLock;
                            // MONITORENTER : object3
                            this.mConnectedControllers.remove(controller2Link);
                            // MONITOREXIT : object3
                            controllerInfo.notifyDisconnected();
                            return;
                        }
                        if (controllerInfo.mAllowedCommands == null) {
                            object = new Session2CommandGroup.Builder();
                            controllerInfo.mAllowedCommands = ((Session2CommandGroup.Builder)object).build();
                        }
                        if (DEBUG) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Accepting connection: ");
                            ((StringBuilder)object).append(controllerInfo);
                            Log.d((String)TAG, (String)((StringBuilder)object).toString());
                        }
                        object = new Bundle();
                        object.putParcelable("android.media.key.SESSION2LINK", (Parcelable)this.mSessionStub);
                        object.putParcelable("android.media.key.ALLOWED_COMMANDS", (Parcelable)controllerInfo.mAllowedCommands);
                        object.putBoolean("android.media.key.PLAYBACK_ACTIVE", this.isPlaybackActive());
                        object.putBundle("android.media.key.TOKEN_EXTRAS", this.mSessionToken.getExtras());
                        bl = this.isClosed();
                        if (!bl) break block32;
                        if (false) return;
                        if (!DEBUG) break block33;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Rejecting connection or notifying that session is closed, controllerInfo=");
                        ((StringBuilder)object).append(controllerInfo);
                        Log.d((String)TAG, (String)((StringBuilder)object).toString());
                    }
                    object = this.mLock;
                    // MONITORENTER : object
                    this.mConnectedControllers.remove(controller2Link);
                    // MONITOREXIT : object
                    controllerInfo.notifyDisconnected();
                    return;
                }
                controllerInfo.notifyConnected((Bundle)object);
                object = this.mLock;
                // MONITORENTER : object
                if (!this.mConnectedControllers.containsKey(controller2Link)) break block34;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Controller ");
                ((StringBuilder)object2).append(controllerInfo);
                ((StringBuilder)object2).append(" has sent connection request multiple times");
                Log.w((String)TAG, (String)((StringBuilder)object2).toString());
            }
            this.mConnectedControllers.put(controller2Link, controllerInfo);
            // MONITOREXIT : object
            this.mCallback.onPostConnect(this, controllerInfo);
            if (true) return;
            if (!DEBUG) break block35;
            object = new StringBuilder();
            ((StringBuilder)object).append("Rejecting connection or notifying that session is closed, controllerInfo=");
            ((StringBuilder)object).append(controllerInfo);
            Log.d((String)TAG, (String)((StringBuilder)object).toString());
        }
        object = this.mLock;
        // MONITORENTER : object
        this.mConnectedControllers.remove(controller2Link);
        // MONITOREXIT : object
        controllerInfo.notifyDisconnected();
    }

    public /* synthetic */ void lambda$onDisconnect$1$MediaSession2(ControllerInfo controllerInfo) {
        this.mCallback.onDisconnected(this, controllerInfo);
    }

    public /* synthetic */ void lambda$onSessionCommand$2$MediaSession2(ControllerInfo object, int n, ResultReceiver resultReceiver, Session2Command session2Command, Bundle bundle) {
        if (!((ControllerInfo)object).removeRequestedCommandSeqNumber(n)) {
            resultReceiver.send(1, null);
            return;
        }
        object = this.mCallback.onSessionCommand(this, (ControllerInfo)object, session2Command, bundle);
        if (resultReceiver != null) {
            if (object == null) {
                resultReceiver.send(1, null);
            } else {
                resultReceiver.send(((Session2Command.Result)object).getResultCode(), ((Session2Command.Result)object).getResultData());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void onCancelCommand(Controller2Link object, int n) {
        Object object2 = this.mLock;
        // MONITORENTER : object2
        object = this.mConnectedControllers.get(object);
        // MONITOREXIT : object2
        if (object == null) {
            return;
        }
        ((ControllerInfo)object).removeRequestedCommandSeqNumber(n);
    }

    void onConnect(Controller2Link controller2Link, int n, int n2, int n3, Bundle object) {
        n3 = n;
        if (n == 0) {
            n3 = object.getInt("android.media.key.PID");
        }
        MediaSessionManager.RemoteUserInfo remoteUserInfo = new MediaSessionManager.RemoteUserInfo(object.getString("android.media.key.PACKAGE_NAME"), n3, n2);
        Bundle bundle = object.getBundle("android.media.key.CONNECTION_HINTS");
        if (bundle == null) {
            Log.w((String)TAG, (String)"connectionHints shouldn't be null.");
            object = Bundle.EMPTY;
        } else {
            object = bundle;
            if (MediaSession2.hasCustomParcelable(bundle)) {
                Log.w((String)TAG, (String)"connectionHints contain custom parcelable. Ignoring.");
                object = Bundle.EMPTY;
            }
        }
        object = new ControllerInfo(remoteUserInfo, this.mSessionManager.isTrustedForMediaControl(remoteUserInfo), controller2Link, (Bundle)object);
        this.mCallbackExecutor.execute(new _$$Lambda$MediaSession2$53DgwUyPnHR49G3UlTPSmlD2b_I(this, (ControllerInfo)object, controller2Link));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void onDisconnect(Controller2Link object, int n) {
        Object object2 = this.mLock;
        // MONITORENTER : object2
        object = this.mConnectedControllers.remove(object);
        // MONITOREXIT : object2
        if (object == null) {
            return;
        }
        this.mCallbackExecutor.execute(new _$$Lambda$MediaSession2$MMa_VCh_tw_VYMOudFxbiuOiQrM(this, (ControllerInfo)object));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void onSessionCommand(Controller2Link object, int n, Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) {
        if (object == null) {
            return;
        }
        Object object2 = this.mLock;
        // MONITORENTER : object2
        object = this.mConnectedControllers.get(object);
        // MONITOREXIT : object2
        if (object == null) {
            return;
        }
        object2 = this.mLock;
        // MONITORENTER : object2
        ((ControllerInfo)object).addRequestedCommandSeqNumber(n);
        // MONITOREXIT : object2
        this.mCallbackExecutor.execute(new _$$Lambda$MediaSession2$4mb9hAWB7fa2IwV_RWmJ7c7__v4(this, (ControllerInfo)object, n, resultReceiver, session2Command, bundle));
    }

    public Object sendSessionCommand(final ControllerInfo controllerInfo, final Session2Command session2Command, Bundle bundle) {
        if (controllerInfo != null) {
            if (session2Command != null) {
                ResultReceiver resultReceiver = new ResultReceiver(this.mResultHandler){

                    public /* synthetic */ void lambda$onReceiveResult$0$MediaSession2$1(ControllerInfo controllerInfo2, Session2Command session2Command2, int n, Bundle bundle) {
                        MediaSession2.this.mCallback.onCommandResult(MediaSession2.this, controllerInfo2, (Object)this, session2Command2, new Session2Command.Result(n, bundle));
                    }

                    protected void onReceiveResult(int n, Bundle bundle) {
                        controllerInfo.receiveCommandResult(this);
                        MediaSession2.this.mCallbackExecutor.execute(new _$$Lambda$MediaSession2$1$Ty9__5M_U_w4LH0UhCT90vhjsHE(this, controllerInfo, session2Command, n, bundle));
                    }
                };
                controllerInfo.sendSessionCommand(session2Command, bundle, resultReceiver);
                return resultReceiver;
            }
            throw new IllegalArgumentException("command shouldn't be null");
        }
        throw new IllegalArgumentException("controller shouldn't be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setForegroundServiceEventCallback(ForegroundServiceEventCallback object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mForegroundServiceEventCallback == object) {
                return;
            }
            if (this.mForegroundServiceEventCallback != null && object != null) {
                object = new IllegalStateException("A session cannot be added to multiple services");
                throw object;
            }
            this.mForegroundServiceEventCallback = object;
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
    public void setPlaybackActive(boolean bl) {
        Iterator<ControllerInfo> iterator = this.mLock;
        // MONITORENTER : iterator
        if (this.mPlaybackActive == bl) {
            // MONITOREXIT : iterator
            return;
        }
        this.mPlaybackActive = bl;
        ForegroundServiceEventCallback foregroundServiceEventCallback = this.mForegroundServiceEventCallback;
        // MONITOREXIT : iterator
        if (foregroundServiceEventCallback != null) {
            foregroundServiceEventCallback.onPlaybackActiveChanged(this, bl);
        }
        iterator = this.getConnectedControllers().iterator();
        while (iterator.hasNext()) {
            iterator.next().notifyPlaybackActiveChanged(bl);
        }
    }

    public static final class Builder {
        private SessionCallback mCallback;
        private Executor mCallbackExecutor;
        private Context mContext;
        private Bundle mExtras;
        private String mId;
        private PendingIntent mSessionActivity;

        public Builder(Context context) {
            if (context != null) {
                this.mContext = context;
                return;
            }
            throw new IllegalArgumentException("context shouldn't be null");
        }

        public MediaSession2 build() {
            if (this.mCallbackExecutor == null) {
                this.mCallbackExecutor = this.mContext.getMainExecutor();
            }
            if (this.mCallback == null) {
                this.mCallback = new SessionCallback(){};
            }
            if (this.mId == null) {
                this.mId = "";
            }
            if (this.mExtras == null) {
                this.mExtras = Bundle.EMPTY;
            }
            MediaSession2 mediaSession2 = new MediaSession2(this.mContext, this.mId, this.mSessionActivity, this.mCallbackExecutor, this.mCallback, this.mExtras);
            try {
                ((MediaSessionManager)this.mContext.getSystemService("media_session")).notifySession2Created(mediaSession2.getToken());
                return mediaSession2;
            }
            catch (Exception exception) {
                mediaSession2.close();
                throw exception;
            }
        }

        public Builder setExtras(Bundle bundle) {
            if (bundle != null) {
                if (!MediaSession2.hasCustomParcelable(bundle)) {
                    this.mExtras = new Bundle(bundle);
                    return this;
                }
                throw new IllegalArgumentException("extras shouldn't contain any custom parcelables");
            }
            throw new NullPointerException("extras shouldn't be null");
        }

        public Builder setId(String string) {
            if (string != null) {
                this.mId = string;
                return this;
            }
            throw new IllegalArgumentException("id shouldn't be null");
        }

        public Builder setSessionActivity(PendingIntent pendingIntent) {
            this.mSessionActivity = pendingIntent;
            return this;
        }

        public Builder setSessionCallback(Executor executor, SessionCallback sessionCallback) {
            this.mCallbackExecutor = executor;
            this.mCallback = sessionCallback;
            return this;
        }

    }

    public static final class ControllerInfo {
        Session2CommandGroup mAllowedCommands;
        private final Bundle mConnectionHints;
        private final Controller2Link mControllerBinder;
        private final boolean mIsTrusted;
        private final Object mLock = new Object();
        private int mNextSeqNumber;
        private ArrayMap<ResultReceiver, Integer> mPendingCommands;
        private final MediaSessionManager.RemoteUserInfo mRemoteUserInfo;
        private ArraySet<Integer> mRequestedCommandSeqNumbers;

        ControllerInfo(MediaSessionManager.RemoteUserInfo remoteUserInfo, boolean bl, Controller2Link controller2Link, Bundle bundle) {
            this.mRemoteUserInfo = remoteUserInfo;
            this.mIsTrusted = bl;
            this.mControllerBinder = controller2Link;
            this.mConnectionHints = bundle;
            this.mPendingCommands = new ArrayMap();
            this.mRequestedCommandSeqNumbers = new ArraySet();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private int getNextSeqNumber() {
            Object object = this.mLock;
            synchronized (object) {
                int n = this.mNextSeqNumber;
                this.mNextSeqNumber = n + 1;
                return n;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void addRequestedCommandSeqNumber(int n) {
            Object object = this.mLock;
            synchronized (object) {
                this.mRequestedCommandSeqNumbers.add((Object)n);
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
        void cancelSessionCommand(Object object) {
            if (this.mControllerBinder == null) {
                return;
            }
            Object object2 = this.mLock;
            // MONITORENTER : object2
            object = (Integer)this.mPendingCommands.remove(object);
            // MONITOREXIT : object2
            if (object == null) return;
            this.mControllerBinder.cancelSessionCommand((Integer)object);
        }

        public boolean equals(Object object) {
            if (!(object instanceof ControllerInfo)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            object = (ControllerInfo)object;
            if (this.mControllerBinder == null && ((ControllerInfo)object).mControllerBinder == null) {
                return this.mRemoteUserInfo.equals((Object)((ControllerInfo)object).mRemoteUserInfo);
            }
            return Objects.equals(this.mControllerBinder, ((ControllerInfo)object).mControllerBinder);
        }

        public Bundle getConnectionHints() {
            return new Bundle(this.mConnectionHints);
        }

        public String getPackageName() {
            return this.mRemoteUserInfo.getPackageName();
        }

        public MediaSessionManager.RemoteUserInfo getRemoteUserInfo() {
            return this.mRemoteUserInfo;
        }

        public int getUid() {
            return this.mRemoteUserInfo.getUid();
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.mControllerBinder, this.mRemoteUserInfo});
        }

        public boolean isTrusted() {
            return this.mIsTrusted;
        }

        void notifyConnected(Bundle bundle) {
            Controller2Link controller2Link = this.mControllerBinder;
            if (controller2Link == null) {
                return;
            }
            try {
                controller2Link.notifyConnected(this.getNextSeqNumber(), bundle);
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
        }

        void notifyDisconnected() {
            Controller2Link controller2Link = this.mControllerBinder;
            if (controller2Link == null) {
                return;
            }
            try {
                controller2Link.notifyDisconnected(this.getNextSeqNumber());
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
        }

        void notifyPlaybackActiveChanged(boolean bl) {
            Controller2Link controller2Link = this.mControllerBinder;
            if (controller2Link == null) {
                return;
            }
            try {
                controller2Link.notifyPlaybackActiveChanged(this.getNextSeqNumber(), bl);
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void receiveCommandResult(ResultReceiver resultReceiver) {
            Object object = this.mLock;
            synchronized (object) {
                this.mPendingCommands.remove((Object)resultReceiver);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        boolean removeRequestedCommandSeqNumber(int n) {
            Object object = this.mLock;
            synchronized (object) {
                return this.mRequestedCommandSeqNumbers.remove((Object)n);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        void sendSessionCommand(Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) {
            if (this.mControllerBinder == null) {
                return;
            }
            int n = this.getNextSeqNumber();
            Object object = this.mLock;
            // MONITORENTER : object
            this.mPendingCommands.put((Object)resultReceiver, (Object)n);
            // MONITOREXIT : object
            try {
                this.mControllerBinder.sendSessionCommand(n, session2Command, bundle, resultReceiver);
                return;
            }
            catch (RuntimeException runtimeException) {
                Object object2 = this.mLock;
                // MONITORENTER : object2
                this.mPendingCommands.remove((Object)resultReceiver);
                // MONITOREXIT : object2
                resultReceiver.send(-1, null);
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ControllerInfo {pkg=");
            stringBuilder.append(this.mRemoteUserInfo.getPackageName());
            stringBuilder.append(", uid=");
            stringBuilder.append(this.mRemoteUserInfo.getUid());
            stringBuilder.append(", allowedCommands=");
            stringBuilder.append(this.mAllowedCommands);
            stringBuilder.append("})");
            return stringBuilder.toString();
        }
    }

    static abstract class ForegroundServiceEventCallback {
        ForegroundServiceEventCallback() {
        }

        public void onPlaybackActiveChanged(MediaSession2 mediaSession2, boolean bl) {
        }

        public void onSessionClosed(MediaSession2 mediaSession2) {
        }
    }

    public static abstract class SessionCallback {
        public void onCommandResult(MediaSession2 mediaSession2, ControllerInfo controllerInfo, Object object, Session2Command session2Command, Session2Command.Result result) {
        }

        public Session2CommandGroup onConnect(MediaSession2 mediaSession2, ControllerInfo controllerInfo) {
            return null;
        }

        public void onDisconnected(MediaSession2 mediaSession2, ControllerInfo controllerInfo) {
        }

        public void onPostConnect(MediaSession2 mediaSession2, ControllerInfo controllerInfo) {
        }

        public Session2Command.Result onSessionCommand(MediaSession2 mediaSession2, ControllerInfo controllerInfo, Session2Command session2Command, Bundle bundle) {
            return null;
        }
    }

}

