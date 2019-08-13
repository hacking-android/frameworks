/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.os.Process
 *  android.os.RemoteException
 *  android.os.ResultReceiver
 *  android.util.ArrayMap
 *  android.util.ArraySet
 *  android.util.Log
 */
package android.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Controller2Link;
import android.media.IMediaSession2Service;
import android.media.MediaSession2;
import android.media.Session2Command;
import android.media.Session2CommandGroup;
import android.media.Session2Link;
import android.media.Session2Token;
import android.media._$$Lambda$MediaController2$2$EnHkDbm447JB5jE2N1MAwo_NzSA;
import android.media._$$Lambda$MediaController2$PSnpjlMlhb1Gdn0LBWml9HGT5NI;
import android.media._$$Lambda$MediaController2$aCop6CAoGD8ANYtOAEuZEKNJc5I;
import android.media._$$Lambda$MediaController2$gnK9yj9twHASv8Ka73nuD8kdCG8;
import android.media._$$Lambda$MediaController2$o4uceEhANIC4PwNDbuKRY_Ai7Hc;
import android.media._$$Lambda$MediaController2$xEUlntVt97CZoJlEuKOIZKoTjdA;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import java.util.concurrent.Executor;

public class MediaController2
implements AutoCloseable {
    static final boolean DEBUG = Log.isLoggable((String)"MediaController2", (int)3);
    static final String TAG = "MediaController2";
    private Session2CommandGroup mAllowedCommands;
    final ControllerCallback mCallback;
    private final Executor mCallbackExecutor;
    private boolean mClosed;
    private Session2Token mConnectedToken;
    private final Context mContext;
    private final Controller2Link mControllerStub;
    private final IBinder.DeathRecipient mDeathRecipient = new _$$Lambda$MediaController2$PSnpjlMlhb1Gdn0LBWml9HGT5NI(this);
    private final Object mLock = new Object();
    private int mNextSeqNumber;
    private ArrayMap<ResultReceiver, Integer> mPendingCommands;
    private boolean mPlaybackActive;
    private ArraySet<Integer> mRequestedCommandSeqNumbers;
    private final Handler mResultHandler;
    private final SessionServiceConnection mServiceConnection;
    private Session2Link mSessionBinder;
    private final Session2Token mSessionToken;

    MediaController2(Context context, Session2Token session2Token, Bundle bundle, Executor executor, ControllerCallback controllerCallback) {
        if (context != null) {
            if (session2Token != null) {
                boolean bl;
                this.mContext = context;
                this.mSessionToken = session2Token;
                if (executor == null) {
                    executor = context.getMainExecutor();
                }
                this.mCallbackExecutor = executor;
                if (controllerCallback == null) {
                    controllerCallback = new ControllerCallback(){};
                }
                this.mCallback = controllerCallback;
                this.mControllerStub = new Controller2Link(this);
                this.mResultHandler = new Handler(context.getMainLooper());
                this.mNextSeqNumber = 0;
                this.mPendingCommands = new ArrayMap();
                this.mRequestedCommandSeqNumbers = new ArraySet();
                if (session2Token.getType() == 0) {
                    this.mServiceConnection = null;
                    bl = this.requestConnectToSession(bundle);
                } else {
                    this.mServiceConnection = new SessionServiceConnection(bundle);
                    bl = this.requestConnectToService();
                }
                if (!bl) {
                    this.close();
                }
                return;
            }
            throw new IllegalArgumentException("token shouldn't be null");
        }
        throw new IllegalArgumentException("context shouldn't be null");
    }

    static /* synthetic */ Session2Token access$300(MediaController2 mediaController2) {
        return mediaController2.mSessionToken;
    }

    static /* synthetic */ Bundle access$400(MediaController2 mediaController2, Bundle bundle) {
        return mediaController2.createConnectionRequest(bundle);
    }

    static /* synthetic */ Controller2Link access$500(MediaController2 mediaController2) {
        return mediaController2.mControllerStub;
    }

    static /* synthetic */ int access$600(MediaController2 mediaController2) {
        return mediaController2.getNextSeqNumber();
    }

    private Bundle createConnectionRequest(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        bundle2.putString("android.media.key.PACKAGE_NAME", this.mContext.getPackageName());
        bundle2.putInt("android.media.key.PID", Process.myPid());
        bundle2.putBundle("android.media.key.CONNECTION_HINTS", bundle);
        return bundle2;
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
    private boolean requestConnectToService() {
        Object object = new Intent("android.media.MediaSession2Service");
        object.setClassName(this.mSessionToken.getPackageName(), this.mSessionToken.getServiceName());
        Object object2 = this.mLock;
        synchronized (object2) {
            if (!this.mContext.bindService((Intent)object, (ServiceConnection)this.mServiceConnection, 1)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("bind to ");
                ((StringBuilder)object).append(this.mSessionToken);
                ((StringBuilder)object).append(" failed");
                Log.w((String)TAG, (String)((StringBuilder)object).toString());
                return false;
            }
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("bind to ");
                ((StringBuilder)object).append(this.mSessionToken);
                ((StringBuilder)object).append(" succeeded");
                Log.d((String)TAG, (String)((StringBuilder)object).toString());
            }
            return true;
        }
    }

    private boolean requestConnectToSession(Bundle bundle) {
        Session2Link session2Link = this.mSessionToken.getSessionLink();
        bundle = this.createConnectionRequest(bundle);
        try {
            session2Link.connect(this.mControllerStub, this.getNextSeqNumber(), bundle);
            return true;
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)TAG, (String)"Failed to call connection request", (Throwable)runtimeException);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancelSessionCommand(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("token shouldn't be null");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mSessionBinder == null) {
                return;
            }
            if ((object = (Integer)this.mPendingCommands.remove(object)) != null) {
                this.mSessionBinder.cancelSessionCommand(this.mControllerStub, (Integer)object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        Object object = this.mLock;
        synchronized (object) {
            Object object2;
            if (this.mClosed) {
                return;
            }
            this.mClosed = true;
            if (this.mServiceConnection != null) {
                this.mContext.unbindService((ServiceConnection)this.mServiceConnection);
            }
            if ((object2 = this.mSessionBinder) != null) {
                try {
                    this.mSessionBinder.disconnect(this.mControllerStub, this.getNextSeqNumber());
                    this.mSessionBinder.unlinkToDeath(this.mDeathRecipient, 0);
                }
                catch (RuntimeException runtimeException) {
                    // empty catch block
                }
            }
            this.mConnectedToken = null;
            this.mPendingCommands.clear();
            this.mRequestedCommandSeqNumbers.clear();
            object2 = this.mCallbackExecutor;
            _$$Lambda$MediaController2$xEUlntVt97CZoJlEuKOIZKoTjdA _$$Lambda$MediaController2$xEUlntVt97CZoJlEuKOIZKoTjdA = new _$$Lambda$MediaController2$xEUlntVt97CZoJlEuKOIZKoTjdA(this);
            object2.execute(_$$Lambda$MediaController2$xEUlntVt97CZoJlEuKOIZKoTjdA);
            this.mSessionBinder = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Session2Token getConnectedToken() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mConnectedToken;
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

    public /* synthetic */ void lambda$close$1$MediaController2() {
        this.mCallback.onDisconnected(this);
    }

    public /* synthetic */ void lambda$new$0$MediaController2() {
        this.close();
    }

    public /* synthetic */ void lambda$onConnected$2$MediaController2(Session2CommandGroup session2CommandGroup) {
        this.mCallback.onConnected(this, session2CommandGroup);
    }

    public /* synthetic */ void lambda$onPlaybackActiveChanged$3$MediaController2(boolean bl) {
        this.mCallback.onPlaybackActiveChanged(this, bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public /* synthetic */ void lambda$onSessionCommand$4$MediaController2(int n, ResultReceiver resultReceiver, Session2Command object, Bundle bundle) {
        Object object2 = this.mLock;
        // MONITORENTER : object2
        n = !this.mRequestedCommandSeqNumbers.remove((Object)n) ? 1 : 0;
        // MONITOREXIT : object2
        if (n != 0) {
            if (resultReceiver == null) return;
            resultReceiver.send(1, null);
            return;
        }
        object = this.mCallback.onSessionCommand(this, (Session2Command)object, bundle);
        if (resultReceiver == null) return;
        if (object == null) {
            resultReceiver.send(1, null);
            return;
        }
        resultReceiver.send(((Session2Command.Result)object).getResultCode(), ((Session2Command.Result)object).getResultData());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onCancelCommand(int n) {
        Object object = this.mLock;
        synchronized (object) {
            this.mRequestedCommandSeqNumbers.remove((Object)n);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onConnected(int n, Bundle bundle) {
        Object object;
        Session2Link session2Link = (Session2Link)bundle.getParcelable("android.media.key.SESSION2LINK");
        Session2CommandGroup session2CommandGroup = (Session2CommandGroup)bundle.getParcelable("android.media.key.ALLOWED_COMMANDS");
        boolean bl = bundle.getBoolean("android.media.key.PLAYBACK_ACTIVE");
        if ((bundle = bundle.getBundle("android.media.key.TOKEN_EXTRAS")) == null) {
            Log.w((String)TAG, (String)"extras shouldn't be null.");
            bundle = Bundle.EMPTY;
        } else if (MediaSession2.hasCustomParcelable(bundle)) {
            Log.w((String)TAG, (String)"extras contain custom parcelable. Ignoring.");
            bundle = Bundle.EMPTY;
        }
        if (DEBUG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("notifyConnected sessionBinder=");
            ((StringBuilder)object).append(session2Link);
            ((StringBuilder)object).append(", allowedCommands=");
            ((StringBuilder)object).append(session2CommandGroup);
            Log.d((String)TAG, (String)((StringBuilder)object).toString());
        }
        if (session2Link != null && session2CommandGroup != null) {
            object = this.mLock;
            synchronized (object) {
                Session2Token session2Token;
                this.mSessionBinder = session2Link;
                this.mAllowedCommands = session2CommandGroup;
                this.mPlaybackActive = bl;
                session2Link.linkToDeath(this.mDeathRecipient, 0);
                this.mConnectedToken = session2Token = new Session2Token(this.mSessionToken.getUid(), 0, this.mSessionToken.getPackageName(), session2Link, bundle);
            }
            this.mCallbackExecutor.execute(new _$$Lambda$MediaController2$gnK9yj9twHASv8Ka73nuD8kdCG8(this, session2CommandGroup));
            return;
        }
        this.close();
    }

    void onDisconnected(int n) {
        this.close();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onPlaybackActiveChanged(int n, boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            this.mPlaybackActive = bl;
        }
        this.mCallbackExecutor.execute(new _$$Lambda$MediaController2$aCop6CAoGD8ANYtOAEuZEKNJc5I(this, bl));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onSessionCommand(int n, Session2Command session2Command, Bundle bundle, ResultReceiver resultReceiver) {
        Object object = this.mLock;
        synchronized (object) {
            this.mRequestedCommandSeqNumbers.add((Object)n);
        }
        this.mCallbackExecutor.execute(new _$$Lambda$MediaController2$o4uceEhANIC4PwNDbuKRY_Ai7Hc(this, n, resultReceiver, session2Command, bundle));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object sendSessionCommand(final Session2Command session2Command, Bundle bundle) {
        if (session2Command == null) {
            throw new IllegalArgumentException("command shouldn't be null");
        }
        ResultReceiver resultReceiver = new ResultReceiver(this.mResultHandler){

            public /* synthetic */ void lambda$onReceiveResult$0$MediaController2$2(Session2Command session2Command2, int n, Bundle bundle) {
                MediaController2.this.mCallback.onCommandResult(MediaController2.this, (Object)this, session2Command2, new Session2Command.Result(n, bundle));
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            protected void onReceiveResult(int n, Bundle bundle) {
                Object object = MediaController2.this.mLock;
                synchronized (object) {
                    MediaController2.this.mPendingCommands.remove((Object)this);
                }
                MediaController2.this.mCallbackExecutor.execute(new _$$Lambda$MediaController2$2$EnHkDbm447JB5jE2N1MAwo_NzSA(this, session2Command, n, bundle));
            }
        };
        Object object = this.mLock;
        synchronized (object) {
            if (this.mSessionBinder != null) {
                int n = this.getNextSeqNumber();
                this.mPendingCommands.put((Object)resultReceiver, (Object)n);
                try {
                    this.mSessionBinder.sendSessionCommand(this.mControllerStub, n, session2Command, bundle, resultReceiver);
                }
                catch (RuntimeException runtimeException) {
                    this.mPendingCommands.remove((Object)resultReceiver);
                    resultReceiver.send(-1, null);
                }
            }
            return resultReceiver;
        }
    }

    public static final class Builder {
        private ControllerCallback mCallback;
        private Executor mCallbackExecutor;
        private Bundle mConnectionHints;
        private Context mContext;
        private Session2Token mToken;

        public Builder(Context context, Session2Token session2Token) {
            if (context != null) {
                if (session2Token != null) {
                    this.mContext = context;
                    this.mToken = session2Token;
                    return;
                }
                throw new IllegalArgumentException("token shouldn't be null");
            }
            throw new IllegalArgumentException("context shouldn't be null");
        }

        public MediaController2 build() {
            if (this.mCallbackExecutor == null) {
                this.mCallbackExecutor = this.mContext.getMainExecutor();
            }
            if (this.mCallback == null) {
                this.mCallback = new ControllerCallback(){};
            }
            if (this.mConnectionHints == null) {
                this.mConnectionHints = Bundle.EMPTY;
            }
            return new MediaController2(this.mContext, this.mToken, this.mConnectionHints, this.mCallbackExecutor, this.mCallback);
        }

        public Builder setConnectionHints(Bundle bundle) {
            if (bundle != null) {
                if (!MediaSession2.hasCustomParcelable(bundle)) {
                    this.mConnectionHints = new Bundle(bundle);
                    return this;
                }
                throw new IllegalArgumentException("connectionHints shouldn't contain any custom parcelables");
            }
            throw new IllegalArgumentException("connectionHints shouldn't be null");
        }

        public Builder setControllerCallback(Executor executor, ControllerCallback controllerCallback) {
            if (executor != null) {
                if (controllerCallback != null) {
                    this.mCallbackExecutor = executor;
                    this.mCallback = controllerCallback;
                    return this;
                }
                throw new IllegalArgumentException("callback shouldn't be null");
            }
            throw new IllegalArgumentException("executor shouldn't be null");
        }

    }

    public static abstract class ControllerCallback {
        public void onCommandResult(MediaController2 mediaController2, Object object, Session2Command session2Command, Session2Command.Result result) {
        }

        public void onConnected(MediaController2 mediaController2, Session2CommandGroup session2CommandGroup) {
        }

        public void onDisconnected(MediaController2 mediaController2) {
        }

        public void onPlaybackActiveChanged(MediaController2 mediaController2, boolean bl) {
        }

        public Session2Command.Result onSessionCommand(MediaController2 mediaController2, Session2Command session2Command, Bundle bundle) {
            return null;
        }
    }

    private class SessionServiceConnection
    implements ServiceConnection {
        private final Bundle mConnectionHints;

        SessionServiceConnection(Bundle bundle) {
            this.mConnectionHints = bundle;
        }

        public void onBindingDied(ComponentName componentName) {
            MediaController2.this.close();
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public void onServiceConnected(ComponentName var1_1, IBinder var2_3) {
            if (MediaController2.DEBUG) {
                var3_5 = new StringBuilder();
                var3_5.append("onServiceConnected ");
                var3_5.append((Object)var1_1);
                var3_5.append(" ");
                var3_5.append(this);
                Log.d((String)"MediaController2", (String)var3_5.toString());
            }
            if (!MediaController2.access$300(MediaController2.this).getPackageName().equals(var1_1.getPackageName())) {
                var2_3 = new StringBuilder();
                var2_3.append("Expected connection to ");
                var2_3.append(MediaController2.access$300(MediaController2.this).getPackageName());
                var2_3.append(" but is connected to ");
                var2_3.append((Object)var1_1);
                Log.wtf((String)"MediaController2", (String)var2_3.toString());
                if (false != false) return;
                MediaController2.this.close();
                return;
            }
            var3_5 = IMediaSession2Service.Stub.asInterface((IBinder)var2_3);
            if (var3_5 == null) {
                Log.wtf((String)"MediaController2", (String)"Service interface is missing.");
                if (false != false) return;
                MediaController2.this.close();
                return;
            }
            var2_3 = MediaController2.access$400(MediaController2.this, this.mConnectionHints);
            var3_5.connect(MediaController2.access$500(MediaController2.this), MediaController2.access$600(MediaController2.this), (Bundle)var2_3);
            if (true != false) return;
            MediaController2.this.close();
            return;
            {
                catch (RemoteException var2_4) {}
                {
                    var3_5 = new StringBuilder();
                    var3_5.append("Service ");
                    var3_5.append((Object)var1_1);
                    var3_5.append(" has died prematurely");
                    Log.w((String)"MediaController2", (String)var3_5.toString(), (Throwable)var2_4);
                    if (false != false) return;
                }
            }
            ** finally { 
lbl53: // 1 sources:
            if (false != false) throw var1_2;
            MediaController2.this.close();
            throw var1_2;
        }

        public void onServiceDisconnected(ComponentName componentName) {
            if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Session service ");
                stringBuilder.append((Object)componentName);
                stringBuilder.append(" is disconnected.");
                Log.w((String)MediaController2.TAG, (String)stringBuilder.toString());
            }
            MediaController2.this.close();
        }
    }

}

