/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.Session2Token
 */
package android.media.session;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.media.IRemoteVolumeController;
import android.media.Session2Token;
import android.media.session.IActiveSessionsListener;
import android.media.session.ICallback;
import android.media.session.IOnMediaKeyListener;
import android.media.session.IOnVolumeKeyLongPressListener;
import android.media.session.ISession;
import android.media.session.ISession2TokensListener;
import android.media.session.ISessionCallback;
import android.media.session.ISessionManager;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session._$$Lambda$MediaSessionManager$Session2TokensChangedWrapper$1$4_TH2zkLY97pxK_e1EPxtPhZwdk;
import android.os.Bundle;
import android.os.Handler;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import com.android.internal.annotations.GuardedBy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MediaSessionManager {
    public static final int RESULT_MEDIA_KEY_HANDLED = 1;
    public static final int RESULT_MEDIA_KEY_NOT_HANDLED = 0;
    private static final String TAG = "SessionManager";
    private CallbackImpl mCallback;
    private Context mContext;
    @GuardedBy(value={"mLock"})
    private final ArrayMap<OnActiveSessionsChangedListener, SessionsChangedWrapper> mListeners = new ArrayMap();
    private final Object mLock = new Object();
    private OnMediaKeyListenerImpl mOnMediaKeyListener;
    private OnVolumeKeyLongPressListenerImpl mOnVolumeKeyLongPressListener;
    private final ISessionManager mService;
    @GuardedBy(value={"mLock"})
    private final ArrayMap<OnSession2TokensChangedListener, Session2TokensChangedWrapper> mSession2TokensListeners = new ArrayMap();

    public MediaSessionManager(Context context) {
        this.mContext = context;
        this.mService = ISessionManager.Stub.asInterface(ServiceManager.getService("media_session"));
    }

    private void dispatchMediaKeyEventInternal(boolean bl, KeyEvent keyEvent, boolean bl2) {
        try {
            this.mService.dispatchMediaKeyEvent(this.mContext.getPackageName(), bl, keyEvent, bl2);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Failed to send key event.", remoteException);
        }
    }

    private void dispatchVolumeKeyEventInternal(boolean bl, KeyEvent keyEvent, int n, boolean bl2) {
        try {
            this.mService.dispatchVolumeKeyEvent(this.mContext.getPackageName(), this.mContext.getOpPackageName(), bl, keyEvent, n, bl2);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Failed to send volume key event.", remoteException);
        }
    }

    public void addOnActiveSessionsChangedListener(OnActiveSessionsChangedListener onActiveSessionsChangedListener, ComponentName componentName) {
        this.addOnActiveSessionsChangedListener(onActiveSessionsChangedListener, componentName, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addOnActiveSessionsChangedListener(OnActiveSessionsChangedListener onActiveSessionsChangedListener, ComponentName componentName, int n, Handler handler) {
        if (onActiveSessionsChangedListener == null) {
            throw new IllegalArgumentException("listener may not be null");
        }
        if (handler == null) {
            handler = new Handler();
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.mListeners.get(onActiveSessionsChangedListener) != null) {
                Log.w(TAG, "Attempted to add session listener twice, ignoring.");
                return;
            }
            SessionsChangedWrapper sessionsChangedWrapper = new SessionsChangedWrapper(this.mContext, onActiveSessionsChangedListener, handler);
            try {
                this.mService.addSessionsListener(sessionsChangedWrapper.mStub, componentName, n);
                this.mListeners.put(onActiveSessionsChangedListener, sessionsChangedWrapper);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error in addOnActiveSessionsChangedListener.", remoteException);
            }
            return;
        }
    }

    public void addOnActiveSessionsChangedListener(OnActiveSessionsChangedListener onActiveSessionsChangedListener, ComponentName componentName, Handler handler) {
        this.addOnActiveSessionsChangedListener(onActiveSessionsChangedListener, componentName, UserHandle.myUserId(), handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addOnSession2TokensChangedListener(int n, OnSession2TokensChangedListener onSession2TokensChangedListener, Handler handler) {
        if (onSession2TokensChangedListener == null) {
            throw new IllegalArgumentException("listener shouldn't be null");
        }
        Object object = this.mLock;
        synchronized (object) {
            if (this.mSession2TokensListeners.get(onSession2TokensChangedListener) != null) {
                Log.w(TAG, "Attempted to add session listener twice, ignoring.");
                return;
            }
            Session2TokensChangedWrapper session2TokensChangedWrapper = new Session2TokensChangedWrapper(onSession2TokensChangedListener, handler);
            try {
                this.mService.addSession2TokensListener(session2TokensChangedWrapper.getStub(), n);
                this.mSession2TokensListeners.put(onSession2TokensChangedListener, session2TokensChangedWrapper);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error in addSessionTokensListener.", remoteException);
                remoteException.rethrowFromSystemServer();
            }
            return;
        }
    }

    public void addOnSession2TokensChangedListener(OnSession2TokensChangedListener onSession2TokensChangedListener) {
        this.addOnSession2TokensChangedListener(UserHandle.myUserId(), onSession2TokensChangedListener, new Handler());
    }

    public void addOnSession2TokensChangedListener(OnSession2TokensChangedListener onSession2TokensChangedListener, Handler handler) {
        this.addOnSession2TokensChangedListener(UserHandle.myUserId(), onSession2TokensChangedListener, handler);
    }

    public ISession createSession(MediaSession.CallbackStub iInterface, String string2, Bundle bundle) {
        try {
            iInterface = this.mService.createSession(this.mContext.getPackageName(), (ISessionCallback)iInterface, string2, bundle, UserHandle.myUserId());
            return iInterface;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void dispatchAdjustVolume(int n, int n2, int n3) {
        try {
            this.mService.dispatchAdjustVolume(this.mContext.getPackageName(), this.mContext.getOpPackageName(), n, n2, n3);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Failed to send adjust volume.", remoteException);
        }
    }

    public void dispatchMediaKeyEvent(KeyEvent keyEvent) {
        this.dispatchMediaKeyEvent(keyEvent, false);
    }

    public void dispatchMediaKeyEvent(KeyEvent keyEvent, boolean bl) {
        this.dispatchMediaKeyEventInternal(false, keyEvent, bl);
    }

    public void dispatchMediaKeyEventAsSystemService(KeyEvent keyEvent) {
        this.dispatchMediaKeyEventInternal(true, keyEvent, false);
    }

    public boolean dispatchMediaKeyEventAsSystemService(MediaSession.Token token, KeyEvent keyEvent) {
        if (token != null) {
            if (keyEvent != null) {
                if (!KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
                    return false;
                }
                try {
                    boolean bl = this.mService.dispatchMediaKeyEventToSessionAsSystemService(this.mContext.getPackageName(), token, keyEvent);
                    return bl;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Failed to send key event.", remoteException);
                    return false;
                }
            }
            throw new IllegalArgumentException("keyEvent shouldn't be null");
        }
        throw new IllegalArgumentException("sessionToken shouldn't be null");
    }

    public void dispatchVolumeKeyEvent(KeyEvent keyEvent, int n, boolean bl) {
        this.dispatchVolumeKeyEventInternal(false, keyEvent, n, bl);
    }

    public void dispatchVolumeKeyEventAsSystemService(MediaSession.Token token, KeyEvent keyEvent) {
        if (token != null) {
            if (keyEvent != null) {
                try {
                    this.mService.dispatchVolumeKeyEventToSessionAsSystemService(this.mContext.getPackageName(), this.mContext.getOpPackageName(), token, keyEvent);
                }
                catch (RemoteException remoteException) {
                    Log.wtf(TAG, "Error calling dispatchVolumeKeyEventAsSystemService", remoteException);
                }
                return;
            }
            throw new IllegalArgumentException("keyEvent shouldn't be null");
        }
        throw new IllegalArgumentException("sessionToken shouldn't be null");
    }

    public void dispatchVolumeKeyEventAsSystemService(KeyEvent keyEvent, int n) {
        this.dispatchVolumeKeyEventInternal(true, keyEvent, n, false);
    }

    public List<MediaController> getActiveSessions(ComponentName componentName) {
        return this.getActiveSessionsForUser(componentName, UserHandle.myUserId());
    }

    @UnsupportedAppUsage
    public List<MediaController> getActiveSessionsForUser(ComponentName object, int n) {
        ArrayList<MediaController> arrayList = new ArrayList<MediaController>();
        List<MediaSession.Token> list = this.mService.getSessions((ComponentName)object, n);
        int n2 = list.size();
        for (n = 0; n < n2; ++n) {
            try {
                object = new MediaController(this.mContext, list.get(n));
                arrayList.add((MediaController)object);
                continue;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Failed to get active sessions: ", remoteException);
                break;
            }
        }
        return arrayList;
    }

    public List<Session2Token> getSession2Tokens() {
        return this.getSession2Tokens(UserHandle.myUserId());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<Session2Token> getSession2Tokens(int n) {
        try {
            ArrayList<Session2Token> arrayList = this.mService.getSession2Tokens(n);
            if (arrayList != null) return ((ParceledListSlice)((Object)arrayList)).getList();
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Failed to get session tokens", remoteException);
            return new ArrayList<Session2Token>();
        }
        return new ArrayList<Session2Token>();
    }

    public boolean isGlobalPriorityActive() {
        try {
            boolean bl = this.mService.isGlobalPriorityActive();
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Failed to check if the global priority is active.", remoteException);
            return false;
        }
    }

    public boolean isTrustedForMediaControl(RemoteUserInfo remoteUserInfo) {
        if (remoteUserInfo != null) {
            if (remoteUserInfo.getPackageName() == null) {
                return false;
            }
            try {
                boolean bl = this.mService.isTrusted(remoteUserInfo.getPackageName(), remoteUserInfo.getPid(), remoteUserInfo.getUid());
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.wtf(TAG, "Cannot communicate with the service.", remoteException);
                return false;
            }
        }
        throw new IllegalArgumentException("userInfo may not be null");
    }

    public void notifySession2Created(Session2Token session2Token) {
        if (session2Token != null) {
            if (session2Token.getType() == 0) {
                try {
                    this.mService.notifySession2Created(session2Token);
                }
                catch (RemoteException remoteException) {
                    remoteException.rethrowFromSystemServer();
                }
                return;
            }
            throw new IllegalArgumentException("token's type should be TYPE_SESSION");
        }
        throw new IllegalArgumentException("token shouldn't be null");
    }

    public void registerRemoteVolumeController(IRemoteVolumeController iRemoteVolumeController) {
        try {
            this.mService.registerRemoteVolumeController(iRemoteVolumeController);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error in registerRemoteVolumeController.", remoteException);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void removeOnActiveSessionsChangedListener(OnActiveSessionsChangedListener object) {
        Throwable throwable2222;
        if (object == null) throw new IllegalArgumentException("listener may not be null");
        Object object2 = this.mLock;
        // MONITORENTER : object2
        object = this.mListeners.remove(object);
        if (object == null) {
            // MONITOREXIT : object2
            return;
        }
        this.mService.removeSessionsListener(((SessionsChangedWrapper)object).mStub);
        ((SessionsChangedWrapper)object).release();
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (RemoteException remoteException) {}
            {
                Log.e(TAG, "Error in removeOnActiveSessionsChangedListener.", remoteException);
            }
            ((SessionsChangedWrapper)object).release();
            return;
        }
        ((SessionsChangedWrapper)object).release();
        throw throwable2222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeOnSession2TokensChangedListener(OnSession2TokensChangedListener object) {
        if (object == null) throw new IllegalArgumentException("listener may not be null");
        Object object2 = this.mLock;
        synchronized (object2) {
            object = this.mSession2TokensListeners.remove(object);
        }
        if (object == null) return;
        try {
            this.mService.removeSession2TokensListener(((Session2TokensChangedWrapper)object).getStub());
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error in removeSessionTokensListener.", remoteException);
            remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    public void setCallback(Callback var1_1, Handler var2_4) {
        var3_5 = this.mLock;
        synchronized (var3_5) {
            block7 : {
                block6 : {
                    block8 : {
                        if (var1_1 != null) ** GOTO lbl13
                        this.mCallback = null;
                        this.mService.setCallback(null);
                        break block6;
                        catch (Throwable var1_2) {
                            break block7;
                        }
                        catch (RemoteException var1_3) {
                            break block8;
                        }
lbl13: // 1 sources:
                        var4_6 = var2_4;
                        if (var2_4 == null) {
                            var4_6 = new Handler();
                        }
                        this.mCallback = var2_4 = new CallbackImpl(var1_1, (Handler)var4_6);
                        this.mService.setCallback(this.mCallback);
                        break block6;
                    }
                    Log.e("SessionManager", "Failed to set media key callback", var1_3);
                }
                return;
            }
            throw var1_2;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public void setOnMediaKeyListener(OnMediaKeyListener var1_1, Handler var2_4) {
        var3_5 = this.mLock;
        synchronized (var3_5) {
            block7 : {
                block6 : {
                    block8 : {
                        if (var1_1 != null) ** GOTO lbl13
                        this.mOnMediaKeyListener = null;
                        this.mService.setOnMediaKeyListener(null);
                        break block6;
                        catch (Throwable var1_2) {
                            break block7;
                        }
                        catch (RemoteException var1_3) {
                            break block8;
                        }
lbl13: // 1 sources:
                        var4_6 = var2_4;
                        if (var2_4 == null) {
                            var4_6 = new Handler();
                        }
                        this.mOnMediaKeyListener = var2_4 = new OnMediaKeyListenerImpl(var1_1, (Handler)var4_6);
                        this.mService.setOnMediaKeyListener(this.mOnMediaKeyListener);
                        break block6;
                    }
                    Log.e("SessionManager", "Failed to set media key listener", var1_3);
                }
                return;
            }
            throw var1_2;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public void setOnVolumeKeyLongPressListener(OnVolumeKeyLongPressListener var1_1, Handler var2_4) {
        var3_5 = this.mLock;
        synchronized (var3_5) {
            block7 : {
                block6 : {
                    block8 : {
                        if (var1_1 != null) ** GOTO lbl13
                        this.mOnVolumeKeyLongPressListener = null;
                        this.mService.setOnVolumeKeyLongPressListener(null);
                        break block6;
                        catch (Throwable var1_2) {
                            break block7;
                        }
                        catch (RemoteException var1_3) {
                            break block8;
                        }
lbl13: // 1 sources:
                        var4_6 = var2_4;
                        if (var2_4 == null) {
                            var4_6 = new Handler();
                        }
                        this.mOnVolumeKeyLongPressListener = var2_4 = new OnVolumeKeyLongPressListenerImpl(var1_1, (Handler)var4_6);
                        this.mService.setOnVolumeKeyLongPressListener(this.mOnVolumeKeyLongPressListener);
                        break block6;
                    }
                    Log.e("SessionManager", "Failed to set volume key long press listener", var1_3);
                }
                return;
            }
            throw var1_2;
        }
    }

    public void unregisterRemoteVolumeController(IRemoteVolumeController iRemoteVolumeController) {
        try {
            this.mService.unregisterRemoteVolumeController(iRemoteVolumeController);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error in unregisterRemoteVolumeController.", remoteException);
        }
    }

    public static abstract class Callback {
        public abstract void onAddressedPlayerChanged(ComponentName var1);

        public abstract void onAddressedPlayerChanged(MediaSession.Token var1);

        public abstract void onMediaKeyEventDispatched(KeyEvent var1, ComponentName var2);

        public abstract void onMediaKeyEventDispatched(KeyEvent var1, MediaSession.Token var2);
    }

    private static final class CallbackImpl
    extends ICallback.Stub {
        private final Callback mCallback;
        private final Handler mHandler;

        public CallbackImpl(Callback callback, Handler handler) {
            this.mCallback = callback;
            this.mHandler = handler;
        }

        @Override
        public void onAddressedPlayerChangedToMediaButtonReceiver(final ComponentName componentName) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    CallbackImpl.this.mCallback.onAddressedPlayerChanged(componentName);
                }
            });
        }

        @Override
        public void onAddressedPlayerChangedToMediaSession(final MediaSession.Token token) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    CallbackImpl.this.mCallback.onAddressedPlayerChanged(token);
                }
            });
        }

        @Override
        public void onMediaKeyEventDispatchedToMediaButtonReceiver(final KeyEvent keyEvent, final ComponentName componentName) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    CallbackImpl.this.mCallback.onMediaKeyEventDispatched(keyEvent, componentName);
                }
            });
        }

        @Override
        public void onMediaKeyEventDispatchedToMediaSession(final KeyEvent keyEvent, final MediaSession.Token token) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    CallbackImpl.this.mCallback.onMediaKeyEventDispatched(keyEvent, token);
                }
            });
        }

    }

    public static interface OnActiveSessionsChangedListener {
        public void onActiveSessionsChanged(List<MediaController> var1);
    }

    @SystemApi
    public static interface OnMediaKeyListener {
        public boolean onMediaKey(KeyEvent var1);
    }

    private static final class OnMediaKeyListenerImpl
    extends IOnMediaKeyListener.Stub {
        private Handler mHandler;
        private OnMediaKeyListener mListener;

        public OnMediaKeyListenerImpl(OnMediaKeyListener onMediaKeyListener, Handler handler) {
            this.mListener = onMediaKeyListener;
            this.mHandler = handler;
        }

        @Override
        public void onMediaKey(final KeyEvent keyEvent, final ResultReceiver resultReceiver) {
            Handler handler;
            if (this.mListener != null && (handler = this.mHandler) != null) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        int n = OnMediaKeyListenerImpl.this.mListener.onMediaKey(keyEvent);
                        Object object = new StringBuilder();
                        ((StringBuilder)object).append("The media key listener is returned ");
                        ((StringBuilder)object).append(n != 0);
                        Log.d(MediaSessionManager.TAG, ((StringBuilder)object).toString());
                        object = resultReceiver;
                        if (object != null) {
                            ((ResultReceiver)object).send(n, null);
                        }
                    }
                });
                return;
            }
            Log.w(MediaSessionManager.TAG, "Failed to call media key listener. Either mListener or mHandler is null");
        }

    }

    public static interface OnSession2TokensChangedListener {
        public void onSession2TokensChanged(List<Session2Token> var1);
    }

    @SystemApi
    public static interface OnVolumeKeyLongPressListener {
        public void onVolumeKeyLongPress(KeyEvent var1);
    }

    private static final class OnVolumeKeyLongPressListenerImpl
    extends IOnVolumeKeyLongPressListener.Stub {
        private Handler mHandler;
        private OnVolumeKeyLongPressListener mListener;

        public OnVolumeKeyLongPressListenerImpl(OnVolumeKeyLongPressListener onVolumeKeyLongPressListener, Handler handler) {
            this.mListener = onVolumeKeyLongPressListener;
            this.mHandler = handler;
        }

        @Override
        public void onVolumeKeyLongPress(final KeyEvent keyEvent) {
            Handler handler;
            if (this.mListener != null && (handler = this.mHandler) != null) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        OnVolumeKeyLongPressListenerImpl.this.mListener.onVolumeKeyLongPress(keyEvent);
                    }
                });
                return;
            }
            Log.w(MediaSessionManager.TAG, "Failed to call volume key long-press listener. Either mListener or mHandler is null");
        }

    }

    public static final class RemoteUserInfo {
        private final String mPackageName;
        private final int mPid;
        private final int mUid;

        public RemoteUserInfo(String string2, int n, int n2) {
            this.mPackageName = string2;
            this.mPid = n;
            this.mUid = n2;
        }

        public boolean equals(Object object) {
            if (!(object instanceof RemoteUserInfo)) {
                return false;
            }
            boolean bl = true;
            if (this == object) {
                return true;
            }
            object = (RemoteUserInfo)object;
            if (!TextUtils.equals(this.mPackageName, ((RemoteUserInfo)object).mPackageName) || this.mPid != ((RemoteUserInfo)object).mPid || this.mUid != ((RemoteUserInfo)object).mUid) {
                bl = false;
            }
            return bl;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public int getPid() {
            return this.mPid;
        }

        public int getUid() {
            return this.mUid;
        }

        public int hashCode() {
            return Objects.hash(this.mPackageName, this.mPid, this.mUid);
        }
    }

    private static final class Session2TokensChangedWrapper {
        private final Handler mHandler;
        private final OnSession2TokensChangedListener mListener;
        private final ISession2TokensListener.Stub mStub = new ISession2TokensListener.Stub(){

            public /* synthetic */ void lambda$onSession2TokensChanged$0$MediaSessionManager$Session2TokensChangedWrapper$1(List list) {
                Session2TokensChangedWrapper.this.mListener.onSession2TokensChanged(list);
            }

            @Override
            public void onSession2TokensChanged(List<Session2Token> list) {
                Session2TokensChangedWrapper.this.mHandler.post(new _$$Lambda$MediaSessionManager$Session2TokensChangedWrapper$1$4_TH2zkLY97pxK_e1EPxtPhZwdk(this, list));
            }
        };

        Session2TokensChangedWrapper(OnSession2TokensChangedListener object, Handler handler) {
            this.mListener = object;
            object = handler == null ? new Handler() : new Handler(handler.getLooper());
            this.mHandler = object;
        }

        public ISession2TokensListener.Stub getStub() {
            return this.mStub;
        }

    }

    private static final class SessionsChangedWrapper {
        private Context mContext;
        private Handler mHandler;
        private OnActiveSessionsChangedListener mListener;
        private final IActiveSessionsListener.Stub mStub = new IActiveSessionsListener.Stub(){

            @Override
            public void onActiveSessionsChanged(final List<MediaSession.Token> list) {
                Handler handler = SessionsChangedWrapper.this.mHandler;
                if (handler != null) {
                    handler.post(new Runnable(){

                        @Override
                        public void run() {
                            Object object = SessionsChangedWrapper.this.mContext;
                            if (object != null) {
                                ArrayList<MediaController> arrayList = new ArrayList<MediaController>();
                                int n = list.size();
                                for (int i = 0; i < n; ++i) {
                                    arrayList.add(new MediaController((Context)object, (MediaSession.Token)list.get(i)));
                                }
                                object = SessionsChangedWrapper.this.mListener;
                                if (object != null) {
                                    object.onActiveSessionsChanged(arrayList);
                                }
                            }
                        }
                    });
                }
            }

        };

        public SessionsChangedWrapper(Context context, OnActiveSessionsChangedListener onActiveSessionsChangedListener, Handler handler) {
            this.mContext = context;
            this.mListener = onActiveSessionsChangedListener;
            this.mHandler = handler;
        }

        private void release() {
            this.mListener = null;
            this.mContext = null;
            this.mHandler = null;
        }

    }

}

