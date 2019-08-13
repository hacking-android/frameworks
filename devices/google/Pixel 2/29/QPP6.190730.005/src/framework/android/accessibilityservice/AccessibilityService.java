/*
 * Decompiled with CFR 0.145.
 */
package android.accessibilityservice;

import android.accessibilityservice.AccessibilityButtonController;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.FingerprintGestureController;
import android.accessibilityservice.GestureDescription;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.IAccessibilityServiceConnection;
import android.annotation.UnsupportedAppUsage;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.graphics.Region;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.WindowManagerImpl;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public abstract class AccessibilityService
extends Service {
    public static final int GESTURE_SWIPE_DOWN = 2;
    public static final int GESTURE_SWIPE_DOWN_AND_LEFT = 15;
    public static final int GESTURE_SWIPE_DOWN_AND_RIGHT = 16;
    public static final int GESTURE_SWIPE_DOWN_AND_UP = 8;
    public static final int GESTURE_SWIPE_LEFT = 3;
    public static final int GESTURE_SWIPE_LEFT_AND_DOWN = 10;
    public static final int GESTURE_SWIPE_LEFT_AND_RIGHT = 5;
    public static final int GESTURE_SWIPE_LEFT_AND_UP = 9;
    public static final int GESTURE_SWIPE_RIGHT = 4;
    public static final int GESTURE_SWIPE_RIGHT_AND_DOWN = 12;
    public static final int GESTURE_SWIPE_RIGHT_AND_LEFT = 6;
    public static final int GESTURE_SWIPE_RIGHT_AND_UP = 11;
    public static final int GESTURE_SWIPE_UP = 1;
    public static final int GESTURE_SWIPE_UP_AND_DOWN = 7;
    public static final int GESTURE_SWIPE_UP_AND_LEFT = 13;
    public static final int GESTURE_SWIPE_UP_AND_RIGHT = 14;
    public static final int GLOBAL_ACTION_BACK = 1;
    public static final int GLOBAL_ACTION_HOME = 2;
    public static final int GLOBAL_ACTION_LOCK_SCREEN = 8;
    public static final int GLOBAL_ACTION_NOTIFICATIONS = 4;
    public static final int GLOBAL_ACTION_POWER_DIALOG = 6;
    public static final int GLOBAL_ACTION_QUICK_SETTINGS = 5;
    public static final int GLOBAL_ACTION_RECENTS = 3;
    public static final int GLOBAL_ACTION_TAKE_SCREENSHOT = 9;
    public static final int GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN = 7;
    private static final String LOG_TAG = "AccessibilityService";
    public static final String SERVICE_INTERFACE = "android.accessibilityservice.AccessibilityService";
    public static final String SERVICE_META_DATA = "android.accessibilityservice";
    public static final int SHOW_MODE_AUTO = 0;
    public static final int SHOW_MODE_HARD_KEYBOARD_ORIGINAL_VALUE = 536870912;
    public static final int SHOW_MODE_HARD_KEYBOARD_OVERRIDDEN = 1073741824;
    public static final int SHOW_MODE_HIDDEN = 1;
    public static final int SHOW_MODE_IGNORE_HARD_KEYBOARD = 2;
    public static final int SHOW_MODE_MASK = 3;
    private AccessibilityButtonController mAccessibilityButtonController;
    private int mConnectionId = -1;
    private FingerprintGestureController mFingerprintGestureController;
    private SparseArray<GestureResultCallbackInfo> mGestureStatusCallbackInfos;
    private int mGestureStatusCallbackSequence;
    @UnsupportedAppUsage
    private AccessibilityServiceInfo mInfo;
    private final Object mLock = new Object();
    private final SparseArray<MagnificationController> mMagnificationControllers = new SparseArray(0);
    private SoftKeyboardController mSoftKeyboardController;
    private WindowManager mWindowManager;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private IBinder mWindowToken;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dispatchServiceConnected() {
        Object object = this.mLock;
        synchronized (object) {
            for (int i = 0; i < this.mMagnificationControllers.size(); ++i) {
                this.mMagnificationControllers.valueAt(i).onServiceConnectedLocked();
            }
        }
        object = this.mSoftKeyboardController;
        if (object != null) {
            ((SoftKeyboardController)object).onServiceConnected();
        }
        this.onServiceConnected();
    }

    private void onAccessibilityButtonAvailabilityChanged(boolean bl) {
        this.getAccessibilityButtonController().dispatchAccessibilityButtonAvailabilityChanged(bl);
    }

    private void onAccessibilityButtonClicked() {
        this.getAccessibilityButtonController().dispatchAccessibilityButtonClicked();
    }

    private void onFingerprintCapturingGesturesChanged(boolean bl) {
        this.getFingerprintGestureController().onGestureDetectionActiveChanged(bl);
    }

    private void onFingerprintGesture(int n) {
        this.getFingerprintGestureController().onGesture(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void onMagnificationChanged(int n, Region region, float f, float f2, float f3) {
        Object object = this.mLock;
        // MONITORENTER : object
        MagnificationController magnificationController = this.mMagnificationControllers.get(n);
        // MONITOREXIT : object
        if (magnificationController == null) return;
        magnificationController.dispatchMagnificationChanged(region, f, f2, f3);
    }

    private void onSoftKeyboardShowModeChanged(int n) {
        SoftKeyboardController softKeyboardController = this.mSoftKeyboardController;
        if (softKeyboardController != null) {
            softKeyboardController.dispatchSoftKeyboardShowModeChanged(n);
        }
    }

    private void sendServiceInfo() {
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        AccessibilityServiceInfo accessibilityServiceInfo = this.mInfo;
        if (accessibilityServiceInfo != null && iAccessibilityServiceConnection != null) {
            try {
                iAccessibilityServiceConnection.setServiceInfo(accessibilityServiceInfo);
                this.mInfo = null;
                AccessibilityInteractionClient.getInstance().clearCache();
            }
            catch (RemoteException remoteException) {
                Log.w(LOG_TAG, "Error while setting AccessibilityServiceInfo", remoteException);
                remoteException.rethrowFromSystemServer();
            }
        }
    }

    public final void disableSelf() {
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (iAccessibilityServiceConnection != null) {
            try {
                iAccessibilityServiceConnection.disableSelf();
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException(remoteException);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean dispatchGesture(GestureDescription parceledListSlice, GestureResultCallback gestureResultCallback, Handler handler) {
        int n;
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (iAccessibilityServiceConnection == null) {
            return false;
        }
        List<GestureDescription.GestureStep> list = GestureDescription.MotionEventGenerator.getGestureStepsFromGestureDescription((GestureDescription)((Object)parceledListSlice), 100);
        try {
            Object object = this.mLock;
            synchronized (object) {
                ++this.mGestureStatusCallbackSequence;
                if (gestureResultCallback != null) {
                    SparseArray sparseArray;
                    if (this.mGestureStatusCallbackInfos == null) {
                        sparseArray = new SparseArray();
                        this.mGestureStatusCallbackInfos = sparseArray;
                    }
                    sparseArray = new SparseArray((GestureDescription)parceledListSlice, gestureResultCallback, handler);
                    this.mGestureStatusCallbackInfos.put(this.mGestureStatusCallbackSequence, (GestureResultCallbackInfo)((Object)sparseArray));
                }
                n = this.mGestureStatusCallbackSequence;
                parceledListSlice = new ParceledListSlice<GestureDescription.GestureStep>(list);
            }
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
        {
            iAccessibilityServiceConnection.sendGesture(n, parceledListSlice);
            return true;
        }
    }

    public AccessibilityNodeInfo findFocus(int n) {
        return AccessibilityInteractionClient.getInstance().findFocus(this.mConnectionId, -2, AccessibilityNodeInfo.ROOT_NODE_ID, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final AccessibilityButtonController getAccessibilityButtonController() {
        Object object = this.mLock;
        synchronized (object) {
            AccessibilityButtonController accessibilityButtonController;
            if (this.mAccessibilityButtonController != null) return this.mAccessibilityButtonController;
            AccessibilityInteractionClient.getInstance();
            this.mAccessibilityButtonController = accessibilityButtonController = new AccessibilityButtonController(AccessibilityInteractionClient.getConnection(this.mConnectionId));
            return this.mAccessibilityButtonController;
        }
    }

    public final FingerprintGestureController getFingerprintGestureController() {
        if (this.mFingerprintGestureController == null) {
            AccessibilityInteractionClient.getInstance();
            this.mFingerprintGestureController = new FingerprintGestureController(AccessibilityInteractionClient.getConnection(this.mConnectionId));
        }
        return this.mFingerprintGestureController;
    }

    public final MagnificationController getMagnificationController() {
        return this.getMagnificationController(0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final MagnificationController getMagnificationController(int n) {
        Object object = this.mLock;
        synchronized (object) {
            MagnificationController magnificationController;
            MagnificationController magnificationController2 = magnificationController = this.mMagnificationControllers.get(n);
            if (magnificationController == null) {
                magnificationController2 = new MagnificationController(this, this.mLock, n);
                this.mMagnificationControllers.put(n, magnificationController2);
            }
            return magnificationController2;
        }
    }

    public AccessibilityNodeInfo getRootInActiveWindow() {
        return AccessibilityInteractionClient.getInstance().getRootInActiveWindow(this.mConnectionId);
    }

    public final AccessibilityServiceInfo getServiceInfo() {
        AccessibilityInteractionClient.getInstance();
        Object object = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (object != null) {
            try {
                object = object.getServiceInfo();
                return object;
            }
            catch (RemoteException remoteException) {
                Log.w(LOG_TAG, "Error while getting AccessibilityServiceInfo", remoteException);
                remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final SoftKeyboardController getSoftKeyboardController() {
        Object object = this.mLock;
        synchronized (object) {
            SoftKeyboardController softKeyboardController;
            if (this.mSoftKeyboardController != null) return this.mSoftKeyboardController;
            this.mSoftKeyboardController = softKeyboardController = new SoftKeyboardController(this, this.mLock);
            return this.mSoftKeyboardController;
        }
    }

    @Override
    public Object getSystemService(String string2) {
        if (this.getBaseContext() != null) {
            if ("window".equals(string2)) {
                if (this.mWindowManager == null) {
                    this.mWindowManager = (WindowManager)this.getBaseContext().getSystemService(string2);
                }
                return this.mWindowManager;
            }
            return super.getSystemService(string2);
        }
        throw new IllegalStateException("System services not available to Activities before onCreate()");
    }

    public List<AccessibilityWindowInfo> getWindows() {
        return AccessibilityInteractionClient.getInstance().getWindows(this.mConnectionId);
    }

    public abstract void onAccessibilityEvent(AccessibilityEvent var1);

    @Override
    public final IBinder onBind(Intent intent) {
        return new IAccessibilityServiceClientWrapper(this, this.getMainLooper(), new Callbacks(){

            @Override
            public void init(int n, IBinder iBinder) {
                AccessibilityService.this.mConnectionId = n;
                AccessibilityService.this.mWindowToken = iBinder;
                ((WindowManagerImpl)AccessibilityService.this.getSystemService("window")).setDefaultToken(iBinder);
            }

            @Override
            public void onAccessibilityButtonAvailabilityChanged(boolean bl) {
                AccessibilityService.this.onAccessibilityButtonAvailabilityChanged(bl);
            }

            @Override
            public void onAccessibilityButtonClicked() {
                AccessibilityService.this.onAccessibilityButtonClicked();
            }

            @Override
            public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
                AccessibilityService.this.onAccessibilityEvent(accessibilityEvent);
            }

            @Override
            public void onFingerprintCapturingGesturesChanged(boolean bl) {
                AccessibilityService.this.onFingerprintCapturingGesturesChanged(bl);
            }

            @Override
            public void onFingerprintGesture(int n) {
                AccessibilityService.this.onFingerprintGesture(n);
            }

            @Override
            public boolean onGesture(int n) {
                return AccessibilityService.this.onGesture(n);
            }

            @Override
            public void onInterrupt() {
                AccessibilityService.this.onInterrupt();
            }

            @Override
            public boolean onKeyEvent(KeyEvent keyEvent) {
                return AccessibilityService.this.onKeyEvent(keyEvent);
            }

            @Override
            public void onMagnificationChanged(int n, Region region, float f, float f2, float f3) {
                AccessibilityService.this.onMagnificationChanged(n, region, f, f2, f3);
            }

            @Override
            public void onPerformGestureResult(int n, boolean bl) {
                AccessibilityService.this.onPerformGestureResult(n, bl);
            }

            @Override
            public void onServiceConnected() {
                AccessibilityService.this.dispatchServiceConnected();
            }

            @Override
            public void onSoftKeyboardShowModeChanged(int n) {
                AccessibilityService.this.onSoftKeyboardShowModeChanged(n);
            }
        });
    }

    protected boolean onGesture(int n) {
        return false;
    }

    public abstract void onInterrupt();

    protected boolean onKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onPerformGestureResult(int n, final boolean bl) {
        GestureResultCallbackInfo gestureResultCallbackInfo;
        if (this.mGestureStatusCallbackInfos == null) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            gestureResultCallbackInfo = this.mGestureStatusCallbackInfos.get(n);
        }
        if (gestureResultCallbackInfo == null) return;
        if (gestureResultCallbackInfo.gestureDescription == null) return;
        if (gestureResultCallbackInfo.callback == null) return;
        if (gestureResultCallbackInfo.handler != null) {
            gestureResultCallbackInfo.handler.post(new Runnable(){

                @Override
                public void run() {
                    if (bl) {
                        gestureResultCallbackInfo.callback.onCompleted(gestureResultCallbackInfo.gestureDescription);
                    } else {
                        gestureResultCallbackInfo.callback.onCancelled(gestureResultCallbackInfo.gestureDescription);
                    }
                }
            });
            return;
        }
        if (bl) {
            gestureResultCallbackInfo.callback.onCompleted(gestureResultCallbackInfo.gestureDescription);
            return;
        }
        gestureResultCallbackInfo.callback.onCancelled(gestureResultCallbackInfo.gestureDescription);
    }

    protected void onServiceConnected() {
    }

    public final boolean performGlobalAction(int n) {
        AccessibilityInteractionClient.getInstance();
        IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
        if (iAccessibilityServiceConnection != null) {
            try {
                boolean bl = iAccessibilityServiceConnection.performGlobalAction(n);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.w("AccessibilityService", "Error while calling performGlobalAction", remoteException);
                remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public final void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) {
        this.mInfo = accessibilityServiceInfo;
        this.sendServiceInfo();
    }

    public static interface Callbacks {
        public void init(int var1, IBinder var2);

        public void onAccessibilityButtonAvailabilityChanged(boolean var1);

        public void onAccessibilityButtonClicked();

        public void onAccessibilityEvent(AccessibilityEvent var1);

        public void onFingerprintCapturingGesturesChanged(boolean var1);

        public void onFingerprintGesture(int var1);

        public boolean onGesture(int var1);

        public void onInterrupt();

        public boolean onKeyEvent(KeyEvent var1);

        public void onMagnificationChanged(int var1, Region var2, float var3, float var4, float var5);

        public void onPerformGestureResult(int var1, boolean var2);

        public void onServiceConnected();

        public void onSoftKeyboardShowModeChanged(int var1);
    }

    public static abstract class GestureResultCallback {
        public void onCancelled(GestureDescription gestureDescription) {
        }

        public void onCompleted(GestureDescription gestureDescription) {
        }
    }

    private static class GestureResultCallbackInfo {
        GestureResultCallback callback;
        GestureDescription gestureDescription;
        Handler handler;

        GestureResultCallbackInfo(GestureDescription gestureDescription, GestureResultCallback gestureResultCallback, Handler handler) {
            this.gestureDescription = gestureDescription;
            this.callback = gestureResultCallback;
            this.handler = handler;
        }
    }

    public static class IAccessibilityServiceClientWrapper
    extends IAccessibilityServiceClient.Stub
    implements HandlerCaller.Callback {
        private static final int DO_ACCESSIBILITY_BUTTON_AVAILABILITY_CHANGED = 13;
        private static final int DO_ACCESSIBILITY_BUTTON_CLICKED = 12;
        private static final int DO_CLEAR_ACCESSIBILITY_CACHE = 5;
        private static final int DO_GESTURE_COMPLETE = 9;
        private static final int DO_INIT = 1;
        private static final int DO_ON_ACCESSIBILITY_EVENT = 3;
        private static final int DO_ON_FINGERPRINT_ACTIVE_CHANGED = 10;
        private static final int DO_ON_FINGERPRINT_GESTURE = 11;
        private static final int DO_ON_GESTURE = 4;
        private static final int DO_ON_INTERRUPT = 2;
        private static final int DO_ON_KEY_EVENT = 6;
        private static final int DO_ON_MAGNIFICATION_CHANGED = 7;
        private static final int DO_ON_SOFT_KEYBOARD_SHOW_MODE_CHANGED = 8;
        private final Callbacks mCallback;
        private final HandlerCaller mCaller;
        private int mConnectionId = -1;

        public IAccessibilityServiceClientWrapper(Context context, Looper looper, Callbacks callbacks) {
            this.mCallback = callbacks;
            this.mCaller = new HandlerCaller(context, looper, this, true);
        }

        @Override
        public void clearAccessibilityCache() {
            Message message = this.mCaller.obtainMessage(5);
            this.mCaller.sendMessage(message);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void executeMessage(Message object) {
            int n = ((Message)object).what;
            boolean bl = false;
            boolean bl2 = false;
            int n2 = 0;
            boolean bl3 = false;
            switch (n) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown message type ");
                    stringBuilder.append(((Message)object).what);
                    Log.w(AccessibilityService.LOG_TAG, stringBuilder.toString());
                    return;
                }
                case 13: {
                    if (this.mConnectionId == -1) return;
                    if (((Message)object).arg1 != 0) {
                        bl3 = true;
                    }
                    this.mCallback.onAccessibilityButtonAvailabilityChanged(bl3);
                    return;
                }
                case 12: {
                    if (this.mConnectionId == -1) return;
                    this.mCallback.onAccessibilityButtonClicked();
                    return;
                }
                case 11: {
                    if (this.mConnectionId == -1) return;
                    this.mCallback.onFingerprintGesture(((Message)object).arg1);
                    return;
                }
                case 10: {
                    if (this.mConnectionId == -1) return;
                    Callbacks callbacks = this.mCallback;
                    bl3 = bl;
                    if (((Message)object).arg1 == 1) {
                        bl3 = true;
                    }
                    callbacks.onFingerprintCapturingGesturesChanged(bl3);
                    return;
                }
                case 9: {
                    if (this.mConnectionId == -1) return;
                    bl3 = bl2;
                    if (((Message)object).arg2 == 1) {
                        bl3 = true;
                    }
                    this.mCallback.onPerformGestureResult(((Message)object).arg1, bl3);
                    return;
                }
                case 8: {
                    if (this.mConnectionId == -1) return;
                    n2 = ((Message)object).arg1;
                    this.mCallback.onSoftKeyboardShowModeChanged(n2);
                    return;
                }
                case 7: {
                    if (this.mConnectionId == -1) return;
                    object = (SomeArgs)((Message)object).obj;
                    Region region = (Region)((SomeArgs)object).arg1;
                    float f = ((Float)((SomeArgs)object).arg2).floatValue();
                    float f2 = ((Float)((SomeArgs)object).arg3).floatValue();
                    float f3 = ((Float)((SomeArgs)object).arg4).floatValue();
                    n2 = ((SomeArgs)object).argi1;
                    ((SomeArgs)object).recycle();
                    this.mCallback.onMagnificationChanged(n2, region, f, f2, f3);
                    return;
                }
                case 6: {
                    KeyEvent keyEvent = (KeyEvent)((Message)object).obj;
                    AccessibilityInteractionClient.getInstance();
                    IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mConnectionId);
                    if (iAccessibilityServiceConnection == null) return;
                    bl3 = this.mCallback.onKeyEvent(keyEvent);
                    n2 = ((Message)object).arg1;
                    try {
                        iAccessibilityServiceConnection.setOnKeyEventResult(bl3, n2);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                    return;
                    finally {
                        try {
                            keyEvent.recycle();
                        }
                        catch (IllegalStateException illegalStateException) {}
                    }
                }
                case 5: {
                    AccessibilityInteractionClient.getInstance().clearCache();
                    return;
                }
                case 4: {
                    if (this.mConnectionId == -1) return;
                    n2 = ((Message)object).arg1;
                    this.mCallback.onGesture(n2);
                    return;
                }
                case 3: {
                    AccessibilityEvent accessibilityEvent = (AccessibilityEvent)((Message)object).obj;
                    if (((Message)object).arg1 != 0) {
                        n2 = 1;
                    }
                    if (accessibilityEvent == null) return;
                    AccessibilityInteractionClient.getInstance().onAccessibilityEvent(accessibilityEvent);
                    if (n2 != 0 && this.mConnectionId != -1) {
                        this.mCallback.onAccessibilityEvent(accessibilityEvent);
                    }
                    try {
                        accessibilityEvent.recycle();
                        return;
                    }
                    catch (IllegalStateException illegalStateException) {
                        // empty catch block
                    }
                    return;
                }
                case 2: {
                    if (this.mConnectionId == -1) return;
                    this.mCallback.onInterrupt();
                    return;
                }
                case 1: 
            }
            this.mConnectionId = ((Message)object).arg1;
            SomeArgs someArgs = (SomeArgs)((Message)object).obj;
            IAccessibilityServiceConnection iAccessibilityServiceConnection = (IAccessibilityServiceConnection)someArgs.arg1;
            object = (IBinder)someArgs.arg2;
            someArgs.recycle();
            if (iAccessibilityServiceConnection != null) {
                AccessibilityInteractionClient.getInstance();
                AccessibilityInteractionClient.addConnection(this.mConnectionId, iAccessibilityServiceConnection);
                this.mCallback.init(this.mConnectionId, (IBinder)object);
                this.mCallback.onServiceConnected();
                return;
            } else {
                AccessibilityInteractionClient.getInstance();
                AccessibilityInteractionClient.removeConnection(this.mConnectionId);
                this.mConnectionId = -1;
                AccessibilityInteractionClient.getInstance().clearCache();
                this.mCallback.init(-1, null);
            }
        }

        @Override
        public void init(IAccessibilityServiceConnection object, int n, IBinder iBinder) {
            object = this.mCaller.obtainMessageIOO(1, n, object, iBinder);
            this.mCaller.sendMessage((Message)object);
        }

        @Override
        public void onAccessibilityButtonAvailabilityChanged(boolean bl) {
            Object object = this.mCaller;
            object = ((HandlerCaller)object).obtainMessageI(13, (int)bl);
            this.mCaller.sendMessage((Message)object);
        }

        @Override
        public void onAccessibilityButtonClicked() {
            Message message = this.mCaller.obtainMessage(12);
            this.mCaller.sendMessage(message);
        }

        @Override
        public void onAccessibilityEvent(AccessibilityEvent parcelable, boolean bl) {
            parcelable = this.mCaller.obtainMessageBO(3, bl, parcelable);
            this.mCaller.sendMessage((Message)parcelable);
        }

        @Override
        public void onFingerprintCapturingGesturesChanged(boolean bl) {
            HandlerCaller handlerCaller = this.mCaller;
            handlerCaller.sendMessage(handlerCaller.obtainMessageI(10, (int)bl));
        }

        @Override
        public void onFingerprintGesture(int n) {
            HandlerCaller handlerCaller = this.mCaller;
            handlerCaller.sendMessage(handlerCaller.obtainMessageI(11, n));
        }

        @Override
        public void onGesture(int n) {
            Message message = this.mCaller.obtainMessageI(4, n);
            this.mCaller.sendMessage(message);
        }

        @Override
        public void onInterrupt() {
            Message message = this.mCaller.obtainMessage(2);
            this.mCaller.sendMessage(message);
        }

        @Override
        public void onKeyEvent(KeyEvent parcelable, int n) {
            parcelable = this.mCaller.obtainMessageIO(6, n, parcelable);
            this.mCaller.sendMessage((Message)parcelable);
        }

        @Override
        public void onMagnificationChanged(int n, Region parcelable, float f, float f2, float f3) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = parcelable;
            someArgs.arg2 = Float.valueOf(f);
            someArgs.arg3 = Float.valueOf(f2);
            someArgs.arg4 = Float.valueOf(f3);
            someArgs.argi1 = n;
            parcelable = this.mCaller.obtainMessageO(7, someArgs);
            this.mCaller.sendMessage((Message)parcelable);
        }

        @Override
        public void onPerformGestureResult(int n, boolean bl) {
            Object object = this.mCaller;
            object = ((HandlerCaller)object).obtainMessageII(9, n, (int)bl);
            this.mCaller.sendMessage((Message)object);
        }

        @Override
        public void onSoftKeyboardShowModeChanged(int n) {
            Message message = this.mCaller.obtainMessageI(8, n);
            this.mCaller.sendMessage(message);
        }
    }

    public static final class MagnificationController {
        private final int mDisplayId;
        private ArrayMap<OnMagnificationChangedListener, Handler> mListeners;
        private final Object mLock;
        private final AccessibilityService mService;

        MagnificationController(AccessibilityService accessibilityService, Object object, int n) {
            this.mService = accessibilityService;
            this.mLock = object;
            this.mDisplayId = n;
        }

        private void setMagnificationCallbackEnabled(boolean bl) {
            AccessibilityInteractionClient.getInstance();
            IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (iAccessibilityServiceConnection != null) {
                try {
                    iAccessibilityServiceConnection.setMagnificationCallbackEnabled(this.mDisplayId, bl);
                }
                catch (RemoteException remoteException) {
                    throw new RuntimeException(remoteException);
                }
            }
        }

        public void addListener(OnMagnificationChangedListener onMagnificationChangedListener) {
            this.addListener(onMagnificationChangedListener, null);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void addListener(OnMagnificationChangedListener onMagnificationChangedListener, Handler handler) {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mListeners == null) {
                    ArrayMap arrayMap = new ArrayMap();
                    this.mListeners = arrayMap;
                }
                boolean bl = this.mListeners.isEmpty();
                this.mListeners.put(onMagnificationChangedListener, handler);
                if (bl) {
                    this.setMagnificationCallbackEnabled(true);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void dispatchMagnificationChanged(Region region, float f, float f2, float f3) {
            ArrayMap<OnMagnificationChangedListener, Handler> arrayMap;
            Object object = this.mLock;
            synchronized (object) {
                if (this.mListeners != null && !this.mListeners.isEmpty()) {
                    arrayMap = new ArrayMap<OnMagnificationChangedListener, Handler>(this.mListeners);
                } else {
                    Slog.d(AccessibilityService.LOG_TAG, "Received magnification changed callback with no listeners registered!");
                    this.setMagnificationCallbackEnabled(false);
                    return;
                }
            }
            int n = arrayMap.size();
            int n2 = 0;
            while (n2 < n) {
                object = arrayMap.keyAt(n2);
                Handler handler = arrayMap.valueAt(n2);
                if (handler != null) {
                    handler.post(new Runnable((OnMagnificationChangedListener)object, region, f, f2, f3){
                        final /* synthetic */ float val$centerX;
                        final /* synthetic */ float val$centerY;
                        final /* synthetic */ OnMagnificationChangedListener val$listener;
                        final /* synthetic */ Region val$region;
                        final /* synthetic */ float val$scale;
                        {
                            this.val$listener = onMagnificationChangedListener;
                            this.val$region = region;
                            this.val$scale = f;
                            this.val$centerX = f2;
                            this.val$centerY = f3;
                        }

                        @Override
                        public void run() {
                            this.val$listener.onMagnificationChanged(MagnificationController.this, this.val$region, this.val$scale, this.val$centerX, this.val$centerY);
                        }
                    });
                } else {
                    object.onMagnificationChanged(this, region, f, f2, f3);
                }
                ++n2;
            }
            return;
        }

        public float getCenterX() {
            AccessibilityInteractionClient.getInstance();
            IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (iAccessibilityServiceConnection != null) {
                try {
                    float f = iAccessibilityServiceConnection.getMagnificationCenterX(this.mDisplayId);
                    return f;
                }
                catch (RemoteException remoteException) {
                    Log.w(AccessibilityService.LOG_TAG, "Failed to obtain center X", remoteException);
                    remoteException.rethrowFromSystemServer();
                }
            }
            return 0.0f;
        }

        public float getCenterY() {
            AccessibilityInteractionClient.getInstance();
            IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (iAccessibilityServiceConnection != null) {
                try {
                    float f = iAccessibilityServiceConnection.getMagnificationCenterY(this.mDisplayId);
                    return f;
                }
                catch (RemoteException remoteException) {
                    Log.w(AccessibilityService.LOG_TAG, "Failed to obtain center Y", remoteException);
                    remoteException.rethrowFromSystemServer();
                }
            }
            return 0.0f;
        }

        public Region getMagnificationRegion() {
            AccessibilityInteractionClient.getInstance();
            Object object = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (object != null) {
                try {
                    object = object.getMagnificationRegion(this.mDisplayId);
                    return object;
                }
                catch (RemoteException remoteException) {
                    Log.w(AccessibilityService.LOG_TAG, "Failed to obtain magnified region", remoteException);
                    remoteException.rethrowFromSystemServer();
                }
            }
            return Region.obtain();
        }

        public float getScale() {
            AccessibilityInteractionClient.getInstance();
            IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (iAccessibilityServiceConnection != null) {
                try {
                    float f = iAccessibilityServiceConnection.getMagnificationScale(this.mDisplayId);
                    return f;
                }
                catch (RemoteException remoteException) {
                    Log.w(AccessibilityService.LOG_TAG, "Failed to obtain scale", remoteException);
                    remoteException.rethrowFromSystemServer();
                }
            }
            return 1.0f;
        }

        void onServiceConnectedLocked() {
            ArrayMap<OnMagnificationChangedListener, Handler> arrayMap = this.mListeners;
            if (arrayMap != null && !arrayMap.isEmpty()) {
                this.setMagnificationCallbackEnabled(true);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean removeListener(OnMagnificationChangedListener onMagnificationChangedListener) {
            if (this.mListeners == null) {
                return false;
            }
            Object object = this.mLock;
            synchronized (object) {
                int n = this.mListeners.indexOfKey(onMagnificationChangedListener);
                if (n < 0) return false;
                boolean bl = true;
                if (bl) {
                    this.mListeners.removeAt(n);
                }
                if (!bl) return bl;
                if (!this.mListeners.isEmpty()) return bl;
                this.setMagnificationCallbackEnabled(false);
                return bl;
            }
        }

        public boolean reset(boolean bl) {
            AccessibilityInteractionClient.getInstance();
            IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (iAccessibilityServiceConnection != null) {
                try {
                    bl = iAccessibilityServiceConnection.resetMagnification(this.mDisplayId, bl);
                    return bl;
                }
                catch (RemoteException remoteException) {
                    Log.w(AccessibilityService.LOG_TAG, "Failed to reset", remoteException);
                    remoteException.rethrowFromSystemServer();
                }
            }
            return false;
        }

        public boolean setCenter(float f, float f2, boolean bl) {
            AccessibilityInteractionClient.getInstance();
            IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (iAccessibilityServiceConnection != null) {
                try {
                    bl = iAccessibilityServiceConnection.setMagnificationScaleAndCenter(this.mDisplayId, Float.NaN, f, f2, bl);
                    return bl;
                }
                catch (RemoteException remoteException) {
                    Log.w(AccessibilityService.LOG_TAG, "Failed to set center", remoteException);
                    remoteException.rethrowFromSystemServer();
                }
            }
            return false;
        }

        public boolean setScale(float f, boolean bl) {
            AccessibilityInteractionClient.getInstance();
            IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (iAccessibilityServiceConnection != null) {
                try {
                    bl = iAccessibilityServiceConnection.setMagnificationScaleAndCenter(this.mDisplayId, f, Float.NaN, Float.NaN, bl);
                    return bl;
                }
                catch (RemoteException remoteException) {
                    Log.w(AccessibilityService.LOG_TAG, "Failed to set scale", remoteException);
                    remoteException.rethrowFromSystemServer();
                }
            }
            return false;
        }

        public static interface OnMagnificationChangedListener {
            public void onMagnificationChanged(MagnificationController var1, Region var2, float var3, float var4, float var5);
        }

    }

    public static final class SoftKeyboardController {
        private ArrayMap<OnShowModeChangedListener, Handler> mListeners;
        private final Object mLock;
        private final AccessibilityService mService;

        SoftKeyboardController(AccessibilityService accessibilityService, Object object) {
            this.mService = accessibilityService;
            this.mLock = object;
        }

        private void setSoftKeyboardCallbackEnabled(boolean bl) {
            AccessibilityInteractionClient.getInstance();
            IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (iAccessibilityServiceConnection != null) {
                try {
                    iAccessibilityServiceConnection.setSoftKeyboardCallbackEnabled(bl);
                }
                catch (RemoteException remoteException) {
                    throw new RuntimeException(remoteException);
                }
            }
        }

        public void addOnShowModeChangedListener(OnShowModeChangedListener onShowModeChangedListener) {
            this.addOnShowModeChangedListener(onShowModeChangedListener, null);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void addOnShowModeChangedListener(OnShowModeChangedListener onShowModeChangedListener, Handler handler) {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mListeners == null) {
                    ArrayMap arrayMap = new ArrayMap();
                    this.mListeners = arrayMap;
                }
                boolean bl = this.mListeners.isEmpty();
                this.mListeners.put(onShowModeChangedListener, handler);
                if (bl) {
                    this.setSoftKeyboardCallbackEnabled(true);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void dispatchSoftKeyboardShowModeChanged(final int n) {
            ArrayMap<OnShowModeChangedListener, Handler> arrayMap;
            Object object = this.mLock;
            synchronized (object) {
                if (this.mListeners != null && !this.mListeners.isEmpty()) {
                    arrayMap = new ArrayMap<OnShowModeChangedListener, Handler>(this.mListeners);
                } else {
                    Slog.w(AccessibilityService.LOG_TAG, "Received soft keyboard show mode changed callback with no listeners registered!");
                    this.setSoftKeyboardCallbackEnabled(false);
                    return;
                }
            }
            int n2 = 0;
            int n3 = arrayMap.size();
            while (n2 < n3) {
                final OnShowModeChangedListener onShowModeChangedListener = arrayMap.keyAt(n2);
                object = arrayMap.valueAt(n2);
                if (object != null) {
                    ((Handler)object).post(new Runnable(){

                        @Override
                        public void run() {
                            onShowModeChangedListener.onShowModeChanged(SoftKeyboardController.this, n);
                        }
                    });
                } else {
                    onShowModeChangedListener.onShowModeChanged(this, n);
                }
                ++n2;
            }
            return;
        }

        public int getShowMode() {
            AccessibilityInteractionClient.getInstance();
            IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (iAccessibilityServiceConnection != null) {
                try {
                    int n = iAccessibilityServiceConnection.getSoftKeyboardShowMode();
                    return n;
                }
                catch (RemoteException remoteException) {
                    Log.w(AccessibilityService.LOG_TAG, "Failed to set soft keyboard behavior", remoteException);
                    remoteException.rethrowFromSystemServer();
                }
            }
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void onServiceConnected() {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mListeners != null && !this.mListeners.isEmpty()) {
                    this.setSoftKeyboardCallbackEnabled(true);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean removeOnShowModeChangedListener(OnShowModeChangedListener onShowModeChangedListener) {
            if (this.mListeners == null) {
                return false;
            }
            Object object = this.mLock;
            synchronized (object) {
                int n = this.mListeners.indexOfKey(onShowModeChangedListener);
                if (n < 0) return false;
                boolean bl = true;
                if (bl) {
                    this.mListeners.removeAt(n);
                }
                if (!bl) return bl;
                if (!this.mListeners.isEmpty()) return bl;
                this.setSoftKeyboardCallbackEnabled(false);
                return bl;
            }
        }

        public boolean setShowMode(int n) {
            AccessibilityInteractionClient.getInstance();
            IAccessibilityServiceConnection iAccessibilityServiceConnection = AccessibilityInteractionClient.getConnection(this.mService.mConnectionId);
            if (iAccessibilityServiceConnection != null) {
                try {
                    boolean bl = iAccessibilityServiceConnection.setSoftKeyboardShowMode(n);
                    return bl;
                }
                catch (RemoteException remoteException) {
                    Log.w(AccessibilityService.LOG_TAG, "Failed to set soft keyboard behavior", remoteException);
                    remoteException.rethrowFromSystemServer();
                }
            }
            return false;
        }

        public static interface OnShowModeChangedListener {
            public void onShowModeChanged(SoftKeyboardController var1, int var2);
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SoftKeyboardShowMode {
    }

}

