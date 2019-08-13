/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.media.PlaybackParams;
import android.media.tv.ITvInputService;
import android.media.tv.ITvInputServiceCallback;
import android.media.tv.ITvInputSession;
import android.media.tv.ITvInputSessionCallback;
import android.media.tv.ITvInputSessionWrapper;
import android.media.tv.TvContentRating;
import android.media.tv.TvContract;
import android.media.tv.TvInputHardwareInfo;
import android.media.tv.TvInputInfo;
import android.media.tv.TvInputManager;
import android.media.tv.TvTrackInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

public abstract class TvInputService
extends Service {
    private static final boolean DEBUG = false;
    private static final int DETACH_OVERLAY_VIEW_TIMEOUT_MS = 5000;
    public static final String SERVICE_INTERFACE = "android.media.tv.TvInputService";
    public static final String SERVICE_META_DATA = "android.media.tv.input";
    private static final String TAG = "TvInputService";
    private final RemoteCallbackList<ITvInputServiceCallback> mCallbacks = new RemoteCallbackList();
    private final Handler mServiceHandler = new ServiceHandler();
    private TvInputManager mTvInputManager;

    public static boolean isNavigationKey(int n) {
        if (n != 61 && n != 62 && n != 66 && n != 92 && n != 93 && n != 122 && n != 123) {
            switch (n) {
                default: {
                    return false;
                }
                case 19: 
                case 20: 
                case 21: 
                case 22: 
                case 23: 
            }
        }
        return true;
    }

    private boolean isPassthroughInput(String object) {
        if (this.mTvInputManager == null) {
            this.mTvInputManager = (TvInputManager)this.getSystemService("tv_input");
        }
        boolean bl = (object = this.mTvInputManager.getTvInputInfo((String)object)) != null && ((TvInputInfo)object).isPassthroughInput();
        return bl;
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return new ITvInputService.Stub(){

            @Override
            public void createRecordingSession(ITvInputSessionCallback iTvInputSessionCallback, String string2) {
                if (iTvInputSessionCallback == null) {
                    return;
                }
                SomeArgs someArgs = SomeArgs.obtain();
                someArgs.arg1 = iTvInputSessionCallback;
                someArgs.arg2 = string2;
                TvInputService.this.mServiceHandler.obtainMessage(3, someArgs).sendToTarget();
            }

            @Override
            public void createSession(InputChannel inputChannel, ITvInputSessionCallback iTvInputSessionCallback, String string2) {
                if (inputChannel == null) {
                    Log.w(TvInputService.TAG, "Creating session without input channel");
                }
                if (iTvInputSessionCallback == null) {
                    return;
                }
                SomeArgs someArgs = SomeArgs.obtain();
                someArgs.arg1 = inputChannel;
                someArgs.arg2 = iTvInputSessionCallback;
                someArgs.arg3 = string2;
                TvInputService.this.mServiceHandler.obtainMessage(1, someArgs).sendToTarget();
            }

            @Override
            public void notifyHardwareAdded(TvInputHardwareInfo tvInputHardwareInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(4, tvInputHardwareInfo).sendToTarget();
            }

            @Override
            public void notifyHardwareRemoved(TvInputHardwareInfo tvInputHardwareInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(5, tvInputHardwareInfo).sendToTarget();
            }

            @Override
            public void notifyHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(6, hdmiDeviceInfo).sendToTarget();
            }

            @Override
            public void notifyHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) {
                TvInputService.this.mServiceHandler.obtainMessage(7, hdmiDeviceInfo).sendToTarget();
            }

            @Override
            public void registerCallback(ITvInputServiceCallback iTvInputServiceCallback) {
                if (iTvInputServiceCallback != null) {
                    TvInputService.this.mCallbacks.register(iTvInputServiceCallback);
                }
            }

            @Override
            public void unregisterCallback(ITvInputServiceCallback iTvInputServiceCallback) {
                if (iTvInputServiceCallback != null) {
                    TvInputService.this.mCallbacks.unregister(iTvInputServiceCallback);
                }
            }
        };
    }

    public RecordingSession onCreateRecordingSession(String string2) {
        return null;
    }

    public abstract Session onCreateSession(String var1);

    @SystemApi
    public TvInputInfo onHardwareAdded(TvInputHardwareInfo tvInputHardwareInfo) {
        return null;
    }

    @SystemApi
    public String onHardwareRemoved(TvInputHardwareInfo tvInputHardwareInfo) {
        return null;
    }

    @SystemApi
    public TvInputInfo onHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) {
        return null;
    }

    @SystemApi
    public String onHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) {
        return null;
    }

    public static abstract class HardwareSession
    extends Session {
        private TvInputManager.Session mHardwareSession;
        private final TvInputManager.SessionCallback mHardwareSessionCallback = new TvInputManager.SessionCallback(){

            @Override
            public void onSessionCreated(TvInputManager.Session session) {
                HardwareSession.this.mHardwareSession = session;
                SomeArgs someArgs = SomeArgs.obtain();
                if (session != null) {
                    HardwareSession hardwareSession = HardwareSession.this;
                    someArgs.arg1 = hardwareSession;
                    someArgs.arg2 = hardwareSession.mProxySession;
                    someArgs.arg3 = HardwareSession.this.mProxySessionCallback;
                    someArgs.arg4 = session.getToken();
                    session.tune(TvContract.buildChannelUriForPassthroughInput(HardwareSession.this.getHardwareInputId()));
                } else {
                    someArgs.arg1 = null;
                    someArgs.arg2 = null;
                    someArgs.arg3 = HardwareSession.this.mProxySessionCallback;
                    someArgs.arg4 = null;
                    HardwareSession.this.onRelease();
                }
                HardwareSession.this.mServiceHandler.obtainMessage(2, someArgs).sendToTarget();
            }

            @Override
            public void onVideoAvailable(TvInputManager.Session session) {
                if (HardwareSession.this.mHardwareSession == session) {
                    HardwareSession.this.onHardwareVideoAvailable();
                }
            }

            @Override
            public void onVideoUnavailable(TvInputManager.Session session, int n) {
                if (HardwareSession.this.mHardwareSession == session) {
                    HardwareSession.this.onHardwareVideoUnavailable(n);
                }
            }
        };
        private ITvInputSession mProxySession;
        private ITvInputSessionCallback mProxySessionCallback;
        private Handler mServiceHandler;

        public HardwareSession(Context context) {
            super(context);
        }

        public abstract String getHardwareInputId();

        public void onHardwareVideoAvailable() {
        }

        public void onHardwareVideoUnavailable(int n) {
        }

        @Override
        public final boolean onSetSurface(Surface surface) {
            Log.e("TvInputService", "onSetSurface() should not be called in HardwareProxySession.");
            return false;
        }

        @Override
        void release() {
            TvInputManager.Session session = this.mHardwareSession;
            if (session != null) {
                session.release();
                this.mHardwareSession = null;
            }
            super.release();
        }

    }

    private static final class OverlayViewCleanUpTask
    extends AsyncTask<View, Void, Void> {
        private OverlayViewCleanUpTask() {
        }

        protected Void doInBackground(View ... object) {
            object = object[0];
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException interruptedException) {
                return null;
            }
            if (this.isCancelled()) {
                return null;
            }
            if (((View)object).isAttachedToWindow()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Time out on releasing overlay view. Killing ");
                stringBuilder.append(((View)object).getContext().getPackageName());
                Log.e(TvInputService.TAG, stringBuilder.toString());
                Process.killProcess(Process.myPid());
            }
            return null;
        }
    }

    public static abstract class RecordingSession {
        final Handler mHandler;
        private final Object mLock = new Object();
        private final List<Runnable> mPendingActions = new ArrayList<Runnable>();
        private ITvInputSessionCallback mSessionCallback;

        public RecordingSession(Context context) {
            this.mHandler = new Handler(context.getMainLooper());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void executeOrPostRunnableOnMainThread(Runnable runnable) {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mSessionCallback == null) {
                    this.mPendingActions.add(runnable);
                } else if (this.mHandler.getLooper().isCurrentThread()) {
                    runnable.run();
                } else {
                    this.mHandler.post(runnable);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void initialize(ITvInputSessionCallback object) {
            Object object2 = this.mLock;
            synchronized (object2) {
                this.mSessionCallback = object;
                object = this.mPendingActions.iterator();
                do {
                    if (!object.hasNext()) {
                        this.mPendingActions.clear();
                        return;
                    }
                    ((Runnable)object.next()).run();
                } while (true);
            }
        }

        void appPrivateCommand(String string2, Bundle bundle) {
            this.onAppPrivateCommand(string2, bundle);
        }

        public void notifyError(int n) {
            int n2;
            block3 : {
                block2 : {
                    if (n < 0) break block2;
                    n2 = n;
                    if (n <= 2) break block3;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("notifyError - invalid error code (");
                stringBuilder.append(n);
                stringBuilder.append(") is changed to RECORDING_ERROR_UNKNOWN.");
                Log.w(TvInputService.TAG, stringBuilder.toString());
                n2 = 0;
            }
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (RecordingSession.this.mSessionCallback != null) {
                            RecordingSession.this.mSessionCallback.onError(n2);
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyError", remoteException);
                    }
                }
            });
        }

        public void notifyRecordingStopped(final Uri uri) {
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (RecordingSession.this.mSessionCallback != null) {
                            RecordingSession.this.mSessionCallback.onRecordingStopped(uri);
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyRecordingStopped", remoteException);
                    }
                }
            });
        }

        @SystemApi
        public void notifySessionEvent(final String string2, final Bundle bundle) {
            Preconditions.checkNotNull(string2);
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (RecordingSession.this.mSessionCallback != null) {
                            RecordingSession.this.mSessionCallback.onSessionEvent(string2, bundle);
                        }
                    }
                    catch (RemoteException remoteException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("error in sending event (event=");
                        stringBuilder.append(string2);
                        stringBuilder.append(")");
                        Log.w(TvInputService.TAG, stringBuilder.toString(), remoteException);
                    }
                }
            });
        }

        public void notifyTuned(final Uri uri) {
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (RecordingSession.this.mSessionCallback != null) {
                            RecordingSession.this.mSessionCallback.onTuned(uri);
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyTuned", remoteException);
                    }
                }
            });
        }

        public void onAppPrivateCommand(String string2, Bundle bundle) {
        }

        public abstract void onRelease();

        public abstract void onStartRecording(Uri var1);

        public abstract void onStopRecording();

        public abstract void onTune(Uri var1);

        public void onTune(Uri uri, Bundle bundle) {
            this.onTune(uri);
        }

        void release() {
            this.onRelease();
        }

        void startRecording(Uri uri) {
            this.onStartRecording(uri);
        }

        void stopRecording() {
            this.onStopRecording();
        }

        void tune(Uri uri, Bundle bundle) {
            this.onTune(uri, bundle);
        }

    }

    @SuppressLint(value={"HandlerLeak"})
    private final class ServiceHandler
    extends Handler {
        private static final int DO_ADD_HARDWARE_INPUT = 4;
        private static final int DO_ADD_HDMI_INPUT = 6;
        private static final int DO_CREATE_RECORDING_SESSION = 3;
        private static final int DO_CREATE_SESSION = 1;
        private static final int DO_NOTIFY_SESSION_CREATED = 2;
        private static final int DO_REMOVE_HARDWARE_INPUT = 5;
        private static final int DO_REMOVE_HDMI_INPUT = 7;

        private ServiceHandler() {
        }

        private void broadcastAddHardwareInput(int n, TvInputInfo tvInputInfo) {
            int n2 = TvInputService.this.mCallbacks.beginBroadcast();
            for (int i = 0; i < n2; ++i) {
                try {
                    ((ITvInputServiceCallback)TvInputService.this.mCallbacks.getBroadcastItem(i)).addHardwareInput(n, tvInputInfo);
                    continue;
                }
                catch (RemoteException remoteException) {
                    Log.e(TvInputService.TAG, "error in broadcastAddHardwareInput", remoteException);
                }
            }
            TvInputService.this.mCallbacks.finishBroadcast();
        }

        private void broadcastAddHdmiInput(int n, TvInputInfo tvInputInfo) {
            int n2 = TvInputService.this.mCallbacks.beginBroadcast();
            for (int i = 0; i < n2; ++i) {
                try {
                    ((ITvInputServiceCallback)TvInputService.this.mCallbacks.getBroadcastItem(i)).addHdmiInput(n, tvInputInfo);
                    continue;
                }
                catch (RemoteException remoteException) {
                    Log.e(TvInputService.TAG, "error in broadcastAddHdmiInput", remoteException);
                }
            }
            TvInputService.this.mCallbacks.finishBroadcast();
        }

        private void broadcastRemoveHardwareInput(String string2) {
            int n = TvInputService.this.mCallbacks.beginBroadcast();
            for (int i = 0; i < n; ++i) {
                try {
                    ((ITvInputServiceCallback)TvInputService.this.mCallbacks.getBroadcastItem(i)).removeHardwareInput(string2);
                    continue;
                }
                catch (RemoteException remoteException) {
                    Log.e(TvInputService.TAG, "error in broadcastRemoveHardwareInput", remoteException);
                }
            }
            TvInputService.this.mCallbacks.finishBroadcast();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public final void handleMessage(Message object) {
            switch (((Message)object).what) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unhandled message code: ");
                    stringBuilder.append(((Message)object).what);
                    Log.w(TvInputService.TAG, stringBuilder.toString());
                    return;
                }
                case 7: {
                    object = (HdmiDeviceInfo)((Message)object).obj;
                    object = TvInputService.this.onHdmiDeviceRemoved((HdmiDeviceInfo)object);
                    if (object == null) return;
                    this.broadcastRemoveHardwareInput((String)object);
                    return;
                }
                case 6: {
                    object = (HdmiDeviceInfo)((Message)object).obj;
                    TvInputInfo tvInputInfo = TvInputService.this.onHdmiDeviceAdded((HdmiDeviceInfo)object);
                    if (tvInputInfo == null) return;
                    this.broadcastAddHdmiInput(((HdmiDeviceInfo)object).getId(), tvInputInfo);
                    return;
                }
                case 5: {
                    object = (TvInputHardwareInfo)((Message)object).obj;
                    object = TvInputService.this.onHardwareRemoved((TvInputHardwareInfo)object);
                    if (object == null) return;
                    this.broadcastRemoveHardwareInput((String)object);
                    return;
                }
                case 4: {
                    TvInputHardwareInfo tvInputHardwareInfo = (TvInputHardwareInfo)((Message)object).obj;
                    object = TvInputService.this.onHardwareAdded(tvInputHardwareInfo);
                    if (object == null) return;
                    this.broadcastAddHardwareInput(tvInputHardwareInfo.getDeviceId(), (TvInputInfo)object);
                    return;
                }
                case 3: {
                    Object object2 = (SomeArgs)((Message)object).obj;
                    object = (ITvInputSessionCallback)((SomeArgs)object2).arg1;
                    Object object3 = (String)((SomeArgs)object2).arg2;
                    ((SomeArgs)object2).recycle();
                    object2 = TvInputService.this.onCreateRecordingSession((String)object3);
                    if (object2 == null) {
                        try {
                            object.onSessionCreated(null, null);
                            return;
                        }
                        catch (RemoteException remoteException) {
                            Log.e(TvInputService.TAG, "error in onSessionCreated", remoteException);
                        }
                        return;
                    }
                    object3 = new ITvInputSessionWrapper(TvInputService.this, (RecordingSession)object2);
                    try {
                        object.onSessionCreated((ITvInputSession)object3, null);
                    }
                    catch (RemoteException remoteException) {
                        Log.e(TvInputService.TAG, "error in onSessionCreated", remoteException);
                    }
                    ((RecordingSession)object2).initialize((ITvInputSessionCallback)object);
                    return;
                }
                case 2: {
                    object = (SomeArgs)((Message)object).obj;
                    Session session = (Session)((SomeArgs)object).arg1;
                    ITvInputSession iTvInputSession = (ITvInputSession)((SomeArgs)object).arg2;
                    ITvInputSessionCallback iTvInputSessionCallback = (ITvInputSessionCallback)((SomeArgs)object).arg3;
                    IBinder iBinder = (IBinder)((SomeArgs)object).arg4;
                    try {
                        iTvInputSessionCallback.onSessionCreated(iTvInputSession, iBinder);
                    }
                    catch (RemoteException remoteException) {
                        Log.e(TvInputService.TAG, "error in onSessionCreated", remoteException);
                    }
                    if (session != null) {
                        session.initialize(iTvInputSessionCallback);
                    }
                    ((SomeArgs)object).recycle();
                    return;
                }
                case 1: 
            }
            Object object4 = (SomeArgs)((Message)object).obj;
            Object object5 = (InputChannel)((SomeArgs)object4).arg1;
            object = (ITvInputSessionCallback)((SomeArgs)object4).arg2;
            Object object6 = (String)((SomeArgs)object4).arg3;
            ((SomeArgs)object4).recycle();
            object4 = TvInputService.this.onCreateSession((String)object6);
            if (object4 == null) {
                try {
                    object.onSessionCreated(null, null);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.e(TvInputService.TAG, "error in onSessionCreated", remoteException);
                }
                return;
            }
            object6 = new ITvInputSessionWrapper(TvInputService.this, (Session)object4, (InputChannel)object5);
            if (object4 instanceof HardwareSession) {
                HardwareSession hardwareSession = (HardwareSession)object4;
                object5 = hardwareSession.getHardwareInputId();
                if (!TextUtils.isEmpty((CharSequence)object5) && TvInputService.this.isPassthroughInput((String)object5)) {
                    hardwareSession.mProxySession = (ITvInputSession)object6;
                    hardwareSession.mProxySessionCallback = (ITvInputSessionCallback)object;
                    hardwareSession.mServiceHandler = TvInputService.this.mServiceHandler;
                    object = (TvInputManager)TvInputService.this.getSystemService("tv_input");
                    ((TvInputManager)object).createSession((String)object5, hardwareSession.mHardwareSessionCallback, TvInputService.this.mServiceHandler);
                    return;
                }
                if (TextUtils.isEmpty((CharSequence)object5)) {
                    Log.w(TvInputService.TAG, "Hardware input id is not setup yet.");
                } else {
                    object6 = new StringBuilder();
                    ((StringBuilder)object6).append("Invalid hardware input id : ");
                    ((StringBuilder)object6).append((String)object5);
                    Log.w(TvInputService.TAG, ((StringBuilder)object6).toString());
                }
                ((Session)object4).onRelease();
                try {
                    object.onSessionCreated(null, null);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.e(TvInputService.TAG, "error in onSessionCreated", remoteException);
                }
                return;
            }
            object5 = SomeArgs.obtain();
            ((SomeArgs)object5).arg1 = object4;
            ((SomeArgs)object5).arg2 = object6;
            ((SomeArgs)object5).arg3 = object;
            ((SomeArgs)object5).arg4 = null;
            TvInputService.this.mServiceHandler.obtainMessage(2, object5).sendToTarget();
        }
    }

    public static abstract class Session
    implements KeyEvent.Callback {
        private static final int POSITION_UPDATE_INTERVAL_MS = 1000;
        private final Context mContext;
        private long mCurrentPositionMs = Long.MIN_VALUE;
        private final KeyEvent.DispatcherState mDispatcherState = new KeyEvent.DispatcherState();
        final Handler mHandler;
        private final Object mLock = new Object();
        @UnsupportedAppUsage
        private Rect mOverlayFrame;
        private View mOverlayView;
        private OverlayViewCleanUpTask mOverlayViewCleanUpTask;
        private FrameLayout mOverlayViewContainer;
        private boolean mOverlayViewEnabled;
        private final List<Runnable> mPendingActions = new ArrayList<Runnable>();
        private ITvInputSessionCallback mSessionCallback;
        private long mStartPositionMs = Long.MIN_VALUE;
        private Surface mSurface;
        private final TimeShiftPositionTrackingRunnable mTimeShiftPositionTrackingRunnable = new TimeShiftPositionTrackingRunnable();
        private final WindowManager mWindowManager;
        private WindowManager.LayoutParams mWindowParams;
        private IBinder mWindowToken;

        public Session(Context context) {
            this.mContext = context;
            this.mWindowManager = (WindowManager)context.getSystemService("window");
            this.mHandler = new Handler(context.getMainLooper());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void executeOrPostRunnableOnMainThread(Runnable runnable) {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mSessionCallback == null) {
                    this.mPendingActions.add(runnable);
                } else if (this.mHandler.getLooper().isCurrentThread()) {
                    runnable.run();
                } else {
                    this.mHandler.post(runnable);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void initialize(ITvInputSessionCallback object) {
            Object object2 = this.mLock;
            synchronized (object2) {
                this.mSessionCallback = object;
                object = this.mPendingActions.iterator();
                do {
                    if (!object.hasNext()) {
                        this.mPendingActions.clear();
                        return;
                    }
                    ((Runnable)object.next()).run();
                } while (true);
            }
        }

        private void notifyTimeShiftCurrentPositionChanged(final long l) {
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftCurrentPositionChanged(l);
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyTimeShiftCurrentPositionChanged", remoteException);
                    }
                }
            });
        }

        private void notifyTimeShiftStartPositionChanged(final long l) {
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftStartPositionChanged(l);
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyTimeShiftStartPositionChanged", remoteException);
                    }
                }
            });
        }

        void appPrivateCommand(String string2, Bundle bundle) {
            this.onAppPrivateCommand(string2, bundle);
        }

        void createOverlayView(IBinder iBinder, Rect parcelable) {
            if (this.mOverlayViewContainer != null) {
                this.removeOverlayView(false);
            }
            this.mWindowToken = iBinder;
            this.mOverlayFrame = parcelable;
            this.onOverlayViewSizeChanged(parcelable.right - parcelable.left, parcelable.bottom - parcelable.top);
            if (!this.mOverlayViewEnabled) {
                return;
            }
            this.mOverlayView = this.onCreateOverlayView();
            if (this.mOverlayView == null) {
                return;
            }
            OverlayViewCleanUpTask overlayViewCleanUpTask = this.mOverlayViewCleanUpTask;
            if (overlayViewCleanUpTask != null) {
                overlayViewCleanUpTask.cancel(true);
                this.mOverlayViewCleanUpTask = null;
            }
            this.mOverlayViewContainer = new FrameLayout(this.mContext.getApplicationContext());
            this.mOverlayViewContainer.addView(this.mOverlayView);
            int n = 536;
            if (ActivityManager.isHighEndGfx()) {
                n = 536 | 16777216;
            }
            this.mWindowParams = new WindowManager.LayoutParams(parcelable.right - parcelable.left, parcelable.bottom - parcelable.top, parcelable.left, parcelable.top, 1004, n, -2);
            parcelable = this.mWindowParams;
            ((WindowManager.LayoutParams)parcelable).privateFlags |= 64;
            parcelable = this.mWindowParams;
            ((WindowManager.LayoutParams)parcelable).gravity = 8388659;
            ((WindowManager.LayoutParams)parcelable).token = iBinder;
            this.mWindowManager.addView(this.mOverlayViewContainer, (ViewGroup.LayoutParams)((Object)parcelable));
        }

        int dispatchInputEvent(InputEvent inputEvent, InputEventReceiver inputEventReceiver) {
            Object object;
            boolean bl = false;
            boolean bl2 = false;
            int n = 0;
            int n2 = 0;
            if (inputEvent instanceof KeyEvent) {
                object = (KeyEvent)inputEvent;
                if (((KeyEvent)object).dispatch(this, this.mDispatcherState, this)) {
                    return 1;
                }
                bl2 = TvInputService.isNavigationKey(((KeyEvent)object).getKeyCode());
                n2 = !KeyEvent.isMediaSessionKey(((KeyEvent)object).getKeyCode()) && ((KeyEvent)object).getKeyCode() != 222 ? 0 : 1;
            } else if (inputEvent instanceof MotionEvent) {
                object = (MotionEvent)inputEvent;
                n2 = ((MotionEvent)object).getSource();
                if (((MotionEvent)object).isTouchEvent()) {
                    bl2 = bl;
                    n2 = n;
                    if (this.onTouchEvent((MotionEvent)object)) {
                        return 1;
                    }
                } else if ((n2 & 4) != 0) {
                    bl2 = bl;
                    n2 = n;
                    if (this.onTrackballEvent((MotionEvent)object)) {
                        return 1;
                    }
                } else {
                    bl2 = bl;
                    n2 = n;
                    if (this.onGenericMotionEvent((MotionEvent)object)) {
                        return 1;
                    }
                }
            }
            object = this.mOverlayViewContainer;
            if (object != null && ((View)object).isAttachedToWindow() && n2 == 0) {
                if (!this.mOverlayViewContainer.hasWindowFocus()) {
                    this.mOverlayViewContainer.getViewRootImpl().windowFocusChanged(true, true);
                }
                if (bl2 && this.mOverlayViewContainer.hasFocusable()) {
                    this.mOverlayViewContainer.getViewRootImpl().dispatchInputEvent(inputEvent);
                    return 1;
                }
                this.mOverlayViewContainer.getViewRootImpl().dispatchInputEvent(inputEvent, inputEventReceiver);
                return -1;
            }
            return 0;
        }

        void dispatchSurfaceChanged(int n, int n2, int n3) {
            this.onSurfaceChanged(n, n2, n3);
        }

        public void layoutSurface(final int n, final int n2, final int n3, final int n4) {
            if (n <= n3 && n2 <= n4) {
                this.executeOrPostRunnableOnMainThread(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            if (Session.this.mSessionCallback != null) {
                                Session.this.mSessionCallback.onLayoutSurface(n, n2, n3, n4);
                            }
                        }
                        catch (RemoteException remoteException) {
                            Log.w(TvInputService.TAG, "error in layoutSurface", remoteException);
                        }
                    }
                });
                return;
            }
            throw new IllegalArgumentException("Invalid parameter");
        }

        public void notifyChannelRetuned(final Uri uri) {
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onChannelRetuned(uri);
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyChannelRetuned", remoteException);
                    }
                }
            });
        }

        public void notifyContentAllowed() {
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onContentAllowed();
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyContentAllowed", remoteException);
                    }
                }
            });
        }

        public void notifyContentBlocked(final TvContentRating tvContentRating) {
            Preconditions.checkNotNull(tvContentRating);
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onContentBlocked(tvContentRating.flattenToString());
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyContentBlocked", remoteException);
                    }
                }
            });
        }

        @SystemApi
        public void notifySessionEvent(final String string2, final Bundle bundle) {
            Preconditions.checkNotNull(string2);
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onSessionEvent(string2, bundle);
                        }
                    }
                    catch (RemoteException remoteException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("error in sending event (event=");
                        stringBuilder.append(string2);
                        stringBuilder.append(")");
                        Log.w(TvInputService.TAG, stringBuilder.toString(), remoteException);
                    }
                }
            });
        }

        public void notifyTimeShiftStatusChanged(final int n) {
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    Session session = Session.this;
                    boolean bl = n == 3;
                    session.timeShiftEnablePositionTracking(bl);
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTimeShiftStatusChanged(n);
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyTimeShiftStatusChanged", remoteException);
                    }
                }
            });
        }

        public void notifyTrackSelected(final int n, final String string2) {
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTrackSelected(n, string2);
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyTrackSelected", remoteException);
                    }
                }
            });
        }

        public void notifyTracksChanged(List<TvTrackInfo> list) {
            this.executeOrPostRunnableOnMainThread(new Runnable(new ArrayList<TvTrackInfo>(list)){
                final /* synthetic */ List val$tracksCopy;
                {
                    this.val$tracksCopy = list;
                }

                @Override
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onTracksChanged(this.val$tracksCopy);
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyTracksChanged", remoteException);
                    }
                }
            });
        }

        public void notifyVideoAvailable() {
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onVideoAvailable();
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyVideoAvailable", remoteException);
                    }
                }
            });
        }

        public void notifyVideoUnavailable(final int n) {
            if (n < 0 || n > 5) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("notifyVideoUnavailable - unknown reason: ");
                stringBuilder.append(n);
                Log.e(TvInputService.TAG, stringBuilder.toString());
            }
            this.executeOrPostRunnableOnMainThread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (Session.this.mSessionCallback != null) {
                            Session.this.mSessionCallback.onVideoUnavailable(n);
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.w(TvInputService.TAG, "error in notifyVideoUnavailable", remoteException);
                    }
                }
            });
        }

        public void onAppPrivateCommand(String string2, Bundle bundle) {
        }

        public View onCreateOverlayView() {
            return null;
        }

        public boolean onGenericMotionEvent(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onKeyDown(int n, KeyEvent keyEvent) {
            return false;
        }

        @Override
        public boolean onKeyLongPress(int n, KeyEvent keyEvent) {
            return false;
        }

        @Override
        public boolean onKeyMultiple(int n, int n2, KeyEvent keyEvent) {
            return false;
        }

        @Override
        public boolean onKeyUp(int n, KeyEvent keyEvent) {
            return false;
        }

        public void onOverlayViewSizeChanged(int n, int n2) {
        }

        public abstract void onRelease();

        public boolean onSelectTrack(int n, String string2) {
            return false;
        }

        public abstract void onSetCaptionEnabled(boolean var1);

        @SystemApi
        public void onSetMain(boolean bl) {
        }

        public abstract void onSetStreamVolume(float var1);

        public abstract boolean onSetSurface(Surface var1);

        public void onSurfaceChanged(int n, int n2, int n3) {
        }

        public long onTimeShiftGetCurrentPosition() {
            return Long.MIN_VALUE;
        }

        public long onTimeShiftGetStartPosition() {
            return Long.MIN_VALUE;
        }

        public void onTimeShiftPause() {
        }

        public void onTimeShiftPlay(Uri uri) {
        }

        public void onTimeShiftResume() {
        }

        public void onTimeShiftSeekTo(long l) {
        }

        public void onTimeShiftSetPlaybackParams(PlaybackParams playbackParams) {
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        public boolean onTrackballEvent(MotionEvent motionEvent) {
            return false;
        }

        public abstract boolean onTune(Uri var1);

        public boolean onTune(Uri uri, Bundle bundle) {
            return this.onTune(uri);
        }

        public void onUnblockContent(TvContentRating tvContentRating) {
        }

        void relayoutOverlayView(Rect rect) {
            Rect rect2 = this.mOverlayFrame;
            if (rect2 == null || rect2.width() != rect.width() || this.mOverlayFrame.height() != rect.height()) {
                this.onOverlayViewSizeChanged(rect.right - rect.left, rect.bottom - rect.top);
            }
            this.mOverlayFrame = rect;
            if (this.mOverlayViewEnabled && this.mOverlayViewContainer != null) {
                this.mWindowParams.x = rect.left;
                this.mWindowParams.y = rect.top;
                this.mWindowParams.width = rect.right - rect.left;
                this.mWindowParams.height = rect.bottom - rect.top;
                this.mWindowManager.updateViewLayout(this.mOverlayViewContainer, this.mWindowParams);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void release() {
            this.onRelease();
            Object object = this.mSurface;
            if (object != null) {
                ((Surface)object).release();
                this.mSurface = null;
            }
            object = this.mLock;
            synchronized (object) {
                this.mSessionCallback = null;
                this.mPendingActions.clear();
            }
            this.removeOverlayView(true);
            this.mHandler.removeCallbacks(this.mTimeShiftPositionTrackingRunnable);
        }

        void removeOverlayView(boolean bl) {
            FrameLayout frameLayout;
            if (bl) {
                this.mWindowToken = null;
                this.mOverlayFrame = null;
            }
            if ((frameLayout = this.mOverlayViewContainer) != null) {
                frameLayout.removeView(this.mOverlayView);
                this.mOverlayView = null;
                this.mWindowManager.removeView(this.mOverlayViewContainer);
                this.mOverlayViewContainer = null;
                this.mWindowParams = null;
            }
        }

        void scheduleOverlayViewCleanup() {
            FrameLayout frameLayout = this.mOverlayViewContainer;
            if (frameLayout != null) {
                this.mOverlayViewCleanUpTask = new OverlayViewCleanUpTask();
                this.mOverlayViewCleanUpTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, frameLayout);
            }
        }

        void selectTrack(int n, String string2) {
            this.onSelectTrack(n, string2);
        }

        void setCaptionEnabled(boolean bl) {
            this.onSetCaptionEnabled(bl);
        }

        void setMain(boolean bl) {
            this.onSetMain(bl);
        }

        public void setOverlayViewEnabled(final boolean bl) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    if (bl == Session.this.mOverlayViewEnabled) {
                        return;
                    }
                    Session.this.mOverlayViewEnabled = bl;
                    if (bl) {
                        if (Session.this.mWindowToken != null) {
                            Session session = Session.this;
                            session.createOverlayView(session.mWindowToken, Session.this.mOverlayFrame);
                        }
                    } else {
                        Session.this.removeOverlayView(false);
                    }
                }
            });
        }

        void setStreamVolume(float f) {
            this.onSetStreamVolume(f);
        }

        void setSurface(Surface surface) {
            this.onSetSurface(surface);
            Surface surface2 = this.mSurface;
            if (surface2 != null) {
                surface2.release();
            }
            this.mSurface = surface;
        }

        void timeShiftEnablePositionTracking(boolean bl) {
            if (bl) {
                this.mHandler.post(this.mTimeShiftPositionTrackingRunnable);
            } else {
                this.mHandler.removeCallbacks(this.mTimeShiftPositionTrackingRunnable);
                this.mStartPositionMs = Long.MIN_VALUE;
                this.mCurrentPositionMs = Long.MIN_VALUE;
            }
        }

        void timeShiftPause() {
            this.onTimeShiftPause();
        }

        void timeShiftPlay(Uri uri) {
            this.mCurrentPositionMs = 0L;
            this.onTimeShiftPlay(uri);
        }

        void timeShiftResume() {
            this.onTimeShiftResume();
        }

        void timeShiftSeekTo(long l) {
            this.onTimeShiftSeekTo(l);
        }

        void timeShiftSetPlaybackParams(PlaybackParams playbackParams) {
            this.onTimeShiftSetPlaybackParams(playbackParams);
        }

        void tune(Uri uri, Bundle bundle) {
            this.mCurrentPositionMs = Long.MIN_VALUE;
            this.onTune(uri, bundle);
        }

        void unblockContent(String string2) {
            this.onUnblockContent(TvContentRating.unflattenFromString(string2));
        }

        private final class TimeShiftPositionTrackingRunnable
        implements Runnable {
            private TimeShiftPositionTrackingRunnable() {
            }

            @Override
            public void run() {
                long l;
                long l2 = Session.this.onTimeShiftGetStartPosition();
                if (Session.this.mStartPositionMs == Long.MIN_VALUE || Session.this.mStartPositionMs != l2) {
                    Session.this.mStartPositionMs = l2;
                    Session.this.notifyTimeShiftStartPositionChanged(l2);
                }
                l2 = l = Session.this.onTimeShiftGetCurrentPosition();
                if (l < Session.this.mStartPositionMs) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Current position (");
                    stringBuilder.append(l);
                    stringBuilder.append(") cannot be earlier than start position (");
                    stringBuilder.append(Session.this.mStartPositionMs);
                    stringBuilder.append("). Reset to the start position.");
                    Log.w(TvInputService.TAG, stringBuilder.toString());
                    l2 = Session.this.mStartPositionMs;
                }
                if (Session.this.mCurrentPositionMs == Long.MIN_VALUE || Session.this.mCurrentPositionMs != l2) {
                    Session.this.mCurrentPositionMs = l2;
                    Session.this.notifyTimeShiftCurrentPositionChanged(l2);
                }
                Session.this.mHandler.removeCallbacks(Session.this.mTimeShiftPositionTrackingRunnable);
                Session.this.mHandler.postDelayed(Session.this.mTimeShiftPositionTrackingRunnable, 1000L);
            }
        }

    }

}

