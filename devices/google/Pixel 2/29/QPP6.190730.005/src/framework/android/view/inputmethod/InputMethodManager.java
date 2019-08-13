/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.inputmethod.-$
 *  android.view.inputmethod.-$$Lambda
 *  android.view.inputmethod.-$$Lambda$InputMethodManager
 *  android.view.inputmethod.-$$Lambda$InputMethodManager$iDWn3IGSUFqIcs8Py42UhfrshxI
 *  android.view.inputmethod.-$$Lambda$InputMethodManager$pvWYFFVbHzZCDCCTiZVM09Xls4w
 */
package android.view.inputmethod;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.Application;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.style.SuggestionSpan;
import android.util.Log;
import android.util.Pools;
import android.util.PrintWriterPrinter;
import android.util.Printer;
import android.util.SparseArray;
import android.view.ImeInsetsSourceConsumer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventSender;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.autofill.AutofillManager;
import android.view.inputmethod.-$;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import android.view.inputmethod._$$Lambda$InputMethodManager$iDWn3IGSUFqIcs8Py42UhfrshxI;
import android.view.inputmethod._$$Lambda$InputMethodManager$pvWYFFVbHzZCDCCTiZVM09Xls4w;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry;
import com.android.internal.os.SomeArgs;
import com.android.internal.view.IInputConnectionWrapper;
import com.android.internal.view.IInputContext;
import com.android.internal.view.IInputMethodClient;
import com.android.internal.view.IInputMethodManager;
import com.android.internal.view.IInputMethodSession;
import com.android.internal.view.InputBindResult;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class InputMethodManager {
    static final boolean DEBUG = false;
    public static final int DISPATCH_HANDLED = 1;
    public static final int DISPATCH_IN_PROGRESS = -1;
    public static final int DISPATCH_NOT_HANDLED = 0;
    public static final int HIDE_IMPLICIT_ONLY = 1;
    public static final int HIDE_NOT_ALWAYS = 2;
    static final long INPUT_METHOD_NOT_RESPONDING_TIMEOUT = 2500L;
    static final int MSG_APPLY_IME_VISIBILITY = 20;
    static final int MSG_BIND = 2;
    static final int MSG_DUMP = 1;
    static final int MSG_FLUSH_INPUT_EVENT = 7;
    static final int MSG_REPORT_FULLSCREEN_MODE = 10;
    static final int MSG_REPORT_PRE_RENDERED = 15;
    static final int MSG_SEND_INPUT_EVENT = 5;
    static final int MSG_SET_ACTIVE = 4;
    static final int MSG_TIMEOUT_INPUT_EVENT = 6;
    static final int MSG_UNBIND = 3;
    static final int MSG_UPDATE_ACTIVITY_VIEW_TO_SCREEN_MATRIX = 30;
    private static final int NOT_A_SUBTYPE_ID = -1;
    static final String PENDING_EVENT_COUNTER = "aq:imm";
    private static final int REQUEST_UPDATE_CURSOR_ANCHOR_INFO_NONE = 0;
    public static final int RESULT_HIDDEN = 3;
    public static final int RESULT_SHOWN = 2;
    public static final int RESULT_UNCHANGED_HIDDEN = 1;
    public static final int RESULT_UNCHANGED_SHOWN = 0;
    public static final int SHOW_FORCED = 2;
    public static final int SHOW_IMPLICIT = 1;
    public static final int SHOW_IM_PICKER_MODE_AUTO = 0;
    public static final int SHOW_IM_PICKER_MODE_EXCLUDE_AUXILIARY_SUBTYPES = 2;
    public static final int SHOW_IM_PICKER_MODE_INCLUDE_AUXILIARY_SUBTYPES = 1;
    private static final String SUBTYPE_MODE_VOICE = "voice";
    static final String TAG = "InputMethodManager";
    @Deprecated
    @UnsupportedAppUsage
    @GuardedBy(value={"sLock"})
    static InputMethodManager sInstance;
    @GuardedBy(value={"sLock"})
    private static final SparseArray<InputMethodManager> sInstanceMap;
    private static final Object sLock;
    boolean mActive = false;
    private Matrix mActivityViewToScreenMatrix = null;
    int mBindSequence = -1;
    final IInputMethodClient.Stub mClient = new IInputMethodClient.Stub(){

        @Override
        public void applyImeVisibility(boolean bl) {
            InputMethodManager.this.mH.obtainMessage(20, (int)bl, 0).sendToTarget();
        }

        @Override
        protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = fileDescriptor;
            someArgs.arg2 = printWriter;
            someArgs.arg3 = arrstring;
            someArgs.arg4 = countDownLatch;
            InputMethodManager.this.mH.sendMessage(InputMethodManager.this.mH.obtainMessage(1, someArgs));
            try {
                if (!countDownLatch.await(5L, TimeUnit.SECONDS)) {
                    printWriter.println("Timeout waiting for dump");
                }
            }
            catch (InterruptedException interruptedException) {
                printWriter.println("Interrupted waiting for dump");
            }
        }

        @Override
        public void onBindMethod(InputBindResult inputBindResult) {
            InputMethodManager.this.mH.obtainMessage(2, inputBindResult).sendToTarget();
        }

        @Override
        public void onUnbindMethod(int n, int n2) {
            InputMethodManager.this.mH.obtainMessage(3, n, n2).sendToTarget();
        }

        @Override
        public void reportFullscreenMode(boolean bl) {
            InputMethodManager.this.mH.obtainMessage(10, (int)bl, 0).sendToTarget();
        }

        @Override
        public void reportPreRendered(EditorInfo editorInfo) {
            InputMethodManager.this.mH.obtainMessage(15, 0, 0, editorInfo).sendToTarget();
        }

        @Override
        public void setActive(boolean bl, boolean bl2) {
            InputMethodManager.this.mH.obtainMessage(4, (int)bl, (int)bl2).sendToTarget();
        }

        @Override
        public void updateActivityViewToScreenMatrix(int n, float[] arrf) {
            InputMethodManager.this.mH.obtainMessage(30, n, 0, arrf).sendToTarget();
        }
    };
    CompletionInfo[] mCompletions;
    InputChannel mCurChannel;
    @UnsupportedAppUsage
    String mCurId;
    @UnsupportedAppUsage
    IInputMethodSession mCurMethod;
    @UnsupportedAppUsage
    View mCurRootView;
    ImeInputEventSender mCurSender;
    EditorInfo mCurrentTextBoxAttribute;
    private CursorAnchorInfo mCursorAnchorInfo = null;
    int mCursorCandEnd;
    int mCursorCandStart;
    @UnsupportedAppUsage
    Rect mCursorRect = new Rect();
    int mCursorSelEnd;
    int mCursorSelStart;
    private final int mDisplayId;
    final InputConnection mDummyInputConnection = new BaseInputConnection(this, false);
    boolean mFullscreenMode;
    @UnsupportedAppUsage(maxTargetSdk=28)
    final H mH;
    final IInputContext mIInputContext;
    private ImeInsetsSourceConsumer mImeInsetsConsumer;
    final Looper mMainLooper;
    @UnsupportedAppUsage(maxTargetSdk=28)
    View mNextServedView;
    final Pools.Pool<PendingEvent> mPendingEventPool = new Pools.SimplePool<PendingEvent>(20);
    final SparseArray<PendingEvent> mPendingEvents = new SparseArray(20);
    private int mRequestUpdateCursorAnchorInfoMonitorMode = 0;
    boolean mRestartOnNextWindowFocus = true;
    boolean mServedConnecting;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    ControlledInputConnectionWrapper mServedInputConnectionWrapper;
    @UnsupportedAppUsage(maxTargetSdk=28)
    View mServedView;
    @UnsupportedAppUsage
    final IInputMethodManager mService;
    @UnsupportedAppUsage
    Rect mTmpCursorRect = new Rect();

    static {
        sLock = new Object();
        sInstanceMap = new SparseArray();
    }

    private InputMethodManager(IInputMethodManager iInputMethodManager, int n, Looper looper) {
        this.mService = iInputMethodManager;
        this.mMainLooper = looper;
        this.mH = new H(looper);
        this.mDisplayId = n;
        this.mIInputContext = new ControlledInputConnectionWrapper(looper, this.mDummyInputConnection, this);
    }

    private static boolean canStartInput(View view) {
        boolean bl = view.hasWindowFocus() || InputMethodManager.isAutofillUIShowing(view);
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private boolean checkFocusNoStartInput(boolean bl) {
        if (this.mServedView == this.mNextServedView && !bl) {
            return false;
        }
        H h = this.mH;
        // MONITORENTER : h
        if (this.mServedView == this.mNextServedView && !bl) {
            // MONITOREXIT : h
            return false;
        }
        if (this.mNextServedView == null) {
            this.finishInputLocked();
            this.closeCurrentInput();
            // MONITOREXIT : h
            return false;
        }
        ControlledInputConnectionWrapper controlledInputConnectionWrapper = this.mServedInputConnectionWrapper;
        this.mServedView = this.mNextServedView;
        this.mCurrentTextBoxAttribute = null;
        this.mCompletions = null;
        this.mServedConnecting = true;
        if (!this.mServedView.onCheckIsTextEditor()) {
            this.maybeCallServedViewChangedLocked(null);
        }
        // MONITOREXIT : h
        if (controlledInputConnectionWrapper == null) return true;
        controlledInputConnectionWrapper.finishComposingText();
        return true;
    }

    private static InputMethodManager createInstance(int n, Looper object) {
        object = InputMethodManager.isInEditMode() ? InputMethodManager.createStubInstance(n, (Looper)object) : InputMethodManager.createRealInstance(n, (Looper)object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static InputMethodManager createRealInstance(int n, Looper object) {
        IInputMethodManager iInputMethodManager;
        try {
            iInputMethodManager = IInputMethodManager.Stub.asInterface(ServiceManager.getServiceOrThrow("input_method"));
            object = new InputMethodManager(iInputMethodManager, n, (Looper)object);
        }
        catch (ServiceManager.ServiceNotFoundException serviceNotFoundException) {
            throw new IllegalStateException(serviceNotFoundException);
        }
        long l = Binder.clearCallingIdentity();
        try {
            try {
                iInputMethodManager.addClient(((InputMethodManager)object).mClient, ((InputMethodManager)object).mIInputContext, n);
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowFromSystemServer();
            }
        }
        catch (Throwable throwable2) {}
        Binder.restoreCallingIdentity(l);
        return object;
        Binder.restoreCallingIdentity(l);
        throw throwable2;
    }

    private static InputMethodManager createStubInstance(int n, Looper looper) {
        ClassLoader classLoader = IInputMethodManager.class.getClassLoader();
        -$.Lambda.InputMethodManager.iDWn3IGSUFqIcs8Py42UhfrshxI iDWn3IGSUFqIcs8Py42UhfrshxI2 = _$$Lambda$InputMethodManager$iDWn3IGSUFqIcs8Py42UhfrshxI.INSTANCE;
        return new InputMethodManager((IInputMethodManager)Proxy.newProxyInstance(classLoader, new Class[]{IInputMethodManager.class}, (InvocationHandler)iDWn3IGSUFqIcs8Py42UhfrshxI2), n, looper);
    }

    private static String dumpViewInfo(View view) {
        if (view == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(view);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(",focus=");
        stringBuilder2.append(view.hasFocus());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(",windowFocus=");
        stringBuilder2.append(view.hasWindowFocus());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(",autofillUiShowing=");
        stringBuilder2.append(InputMethodManager.isAutofillUIShowing(view));
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(",window=");
        stringBuilder2.append(view.getWindowToken());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(",displayId=");
        stringBuilder2.append(view.getContext().getDisplayId());
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(",temporaryDetach=");
        stringBuilder2.append(view.isTemporarilyDetached());
        stringBuilder.append(stringBuilder2.toString());
        return stringBuilder.toString();
    }

    public static void ensureDefaultInstanceForDefaultDisplayIfNecessary() {
        InputMethodManager.forContextInternal(0, Looper.getMainLooper());
    }

    private void flushPendingEventsLocked() {
        this.mH.removeMessages(7);
        int n = this.mPendingEvents.size();
        for (int i = 0; i < n; ++i) {
            int n2 = this.mPendingEvents.keyAt(i);
            Message message = this.mH.obtainMessage(7, n2, 0);
            message.setAsynchronous(true);
            message.sendToTarget();
        }
    }

    public static InputMethodManager forContext(Context object) {
        int n = ((Context)object).getDisplayId();
        object = n == 0 ? Looper.getMainLooper() : ((Context)object).getMainLooper();
        return InputMethodManager.forContextInternal(n, (Looper)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static InputMethodManager forContextInternal(int n, Looper object) {
        boolean bl = n == 0;
        Object object2 = sLock;
        synchronized (object2) {
            InputMethodManager inputMethodManager = sInstanceMap.get(n);
            if (inputMethodManager != null) {
                return inputMethodManager;
            }
            object = InputMethodManager.createInstance(n, (Looper)object);
            if (sInstance == null && bl) {
                sInstance = object;
            }
            sInstanceMap.put(n, (InputMethodManager)object);
            return object;
        }
    }

    private InputMethodManager getFallbackInputMethodManagerIfNecessary(View object) {
        if (object == null) {
            return null;
        }
        Object object2 = ((View)object).getViewRootImpl();
        if (object2 == null) {
            return null;
        }
        int n = ((ViewRootImpl)object2).getDisplayId();
        if (n == this.mDisplayId) {
            return null;
        }
        object2 = ((ViewRootImpl)object2).mContext.getSystemService(InputMethodManager.class);
        if (object2 == null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("b/117267690: Failed to get non-null fallback IMM. view=");
            ((StringBuilder)object2).append(object);
            Log.e(TAG, ((StringBuilder)object2).toString());
            return null;
        }
        if (((InputMethodManager)object2).mDisplayId != n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("b/117267690: Failed to get fallback IMM with expected displayId=");
            stringBuilder.append(n);
            stringBuilder.append(" actual IMM#displayId=");
            stringBuilder.append(((InputMethodManager)object2).mDisplayId);
            stringBuilder.append(" view=");
            stringBuilder.append(object);
            Log.e(TAG, stringBuilder.toString());
            return null;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("b/117267690: Display ID mismatch found. ViewRootImpl displayId=");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" InputMethodManager displayId=");
        ((StringBuilder)object).append(this.mDisplayId);
        ((StringBuilder)object).append(". Use the right InputMethodManager instance to avoid performance overhead.");
        Log.w(TAG, ((StringBuilder)object).toString(), new Throwable());
        return object2;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static InputMethodManager getInstance() {
        Log.w(TAG, "InputMethodManager.getInstance() is deprecated because it cannot be compatible with multi-display. Use context.getSystemService(InputMethodManager.class) instead.", new Throwable());
        InputMethodManager.ensureDefaultInstanceForDefaultDisplayIfNecessary();
        return InputMethodManager.peekInstance();
    }

    private static boolean isAutofillUIShowing(View object) {
        boolean bl = (object = ((View)object).getContext().getSystemService(AutofillManager.class)) != null && ((AutofillManager)object).isAutofillUiShowing();
        return bl;
    }

    private static boolean isInEditMode() {
        return false;
    }

    static /* synthetic */ Object lambda$createStubInstance$0(Object object, Method genericDeclaration, Object[] object2) throws Throwable {
        genericDeclaration = genericDeclaration.getReturnType();
        object2 = Boolean.TYPE;
        object = 0;
        if (genericDeclaration == object2) {
            return false;
        }
        if (genericDeclaration == Integer.TYPE) {
            return object;
        }
        if (genericDeclaration == Long.TYPE) {
            return 0L;
        }
        if (genericDeclaration == Short.TYPE) {
            return object;
        }
        if (genericDeclaration == Character.TYPE) {
            return object;
        }
        if (genericDeclaration == Byte.TYPE) {
            return object;
        }
        if (genericDeclaration == Float.TYPE) {
            return Float.valueOf(0.0f);
        }
        if (genericDeclaration == Double.TYPE) {
            return 0.0;
        }
        return null;
    }

    static /* synthetic */ int lambda$getShortcutInputMethodsAndSubtypes$2(InputMethodInfo inputMethodInfo) {
        return inputMethodInfo.isSystem() ^ true;
    }

    private void maybeCallServedViewChangedLocked(EditorInfo editorInfo) {
        ImeInsetsSourceConsumer imeInsetsSourceConsumer = this.mImeInsetsConsumer;
        if (imeInsetsSourceConsumer != null) {
            imeInsetsSourceConsumer.onServedEditorChanged(editorInfo);
        }
    }

    private PendingEvent obtainPendingEventLocked(InputEvent inputEvent, Object object, String string2, FinishedInputEventCallback finishedInputEventCallback, Handler handler) {
        PendingEvent pendingEvent;
        PendingEvent pendingEvent2 = pendingEvent = this.mPendingEventPool.acquire();
        if (pendingEvent == null) {
            pendingEvent2 = new PendingEvent();
        }
        pendingEvent2.mEvent = inputEvent;
        pendingEvent2.mToken = object;
        pendingEvent2.mInputMethodId = string2;
        pendingEvent2.mCallback = finishedInputEventCallback;
        pendingEvent2.mHandler = handler;
        return pendingEvent2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @UnsupportedAppUsage
    public static InputMethodManager peekInstance() {
        Log.w(TAG, "InputMethodManager.peekInstance() is deprecated because it cannot be compatible with multi-display. Use context.getSystemService(InputMethodManager.class) instead.", new Throwable());
        Object object = sLock;
        synchronized (object) {
            return sInstance;
        }
    }

    private void recyclePendingEventLocked(PendingEvent pendingEvent) {
        pendingEvent.recycle();
        this.mPendingEventPool.release(pendingEvent);
    }

    static void scheduleCheckFocusLocked(View object) {
        if ((object = ((View)object).getViewRootImpl()) != null) {
            ((ViewRootImpl)object).dispatchCheckFocus();
        }
    }

    private void showInputMethodPickerLocked() {
        try {
            this.mService.showInputMethodPickerFromClient(this.mClient, 0);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void tearDownEditMode() {
        if (InputMethodManager.isInEditMode()) {
            Object object = sLock;
            synchronized (object) {
                sInstance = null;
                return;
            }
        }
        throw new UnsupportedOperationException("This method must be called only from layoutlib");
    }

    @UnsupportedAppUsage
    public void checkFocus() {
        if (this.checkFocusNoStartInput(false)) {
            this.startInputInner(4, null, 0, 0, 0);
        }
    }

    void clearBindingLocked() {
        this.clearConnectionLocked();
        this.setInputChannelLocked(null);
        this.mBindSequence = -1;
        this.mCurId = null;
        this.mCurMethod = null;
    }

    void clearConnectionLocked() {
        this.mCurrentTextBoxAttribute = null;
        ControlledInputConnectionWrapper controlledInputConnectionWrapper = this.mServedInputConnectionWrapper;
        if (controlledInputConnectionWrapper != null) {
            controlledInputConnectionWrapper.deactivate();
            this.mServedInputConnectionWrapper = null;
        }
    }

    @UnsupportedAppUsage
    void closeCurrentInput() {
        try {
            this.mService.hideSoftInput(this.mClient, 2, null);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int dispatchInputEvent(InputEvent object, Object object2, FinishedInputEventCallback finishedInputEventCallback, Handler handler) {
        H h = this.mH;
        synchronized (h) {
            KeyEvent keyEvent;
            if (this.mCurMethod == null) {
                return 0;
            }
            if (object instanceof KeyEvent && (keyEvent = (KeyEvent)object).getAction() == 0 && keyEvent.getKeyCode() == 63 && keyEvent.getRepeatCount() == 0) {
                this.showInputMethodPickerLocked();
                return 1;
            }
            object = this.obtainPendingEventLocked((InputEvent)object, object2, this.mCurId, finishedInputEventCallback, handler);
            if (this.mMainLooper.isCurrentThread()) {
                return this.sendInputEventOnMainLooperLocked((PendingEvent)object);
            }
            object = this.mH.obtainMessage(5, object);
            ((Message)object).setAsynchronous(true);
            this.mH.sendMessage((Message)object);
            return -1;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dispatchKeyEventFromInputMethod(View object, KeyEvent keyEvent) {
        Object object2 = this.getFallbackInputMethodManagerIfNecessary((View)object);
        if (object2 != null) {
            ((InputMethodManager)object2).dispatchKeyEventFromInputMethod((View)object, keyEvent);
            return;
        }
        H h = this.mH;
        synchronized (h) {
            Throwable throwable2;
            block9 : {
                block8 : {
                    if (object != null) {
                        try {
                            object = ((View)object).getViewRootImpl();
                            break block8;
                        }
                        catch (Throwable throwable2) {
                            break block9;
                        }
                    }
                    object = null;
                }
                object2 = object;
                if (object == null) {
                    object2 = object;
                    if (this.mServedView != null) {
                        object2 = this.mServedView.getViewRootImpl();
                    }
                }
                if (object2 != null) {
                    ((ViewRootImpl)object2).dispatchKeyFromIme(keyEvent);
                }
                return;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void displayCompletions(View object, CompletionInfo[] arrcompletionInfo) {
        Object object2 = this.getFallbackInputMethodManagerIfNecessary((View)object);
        if (object2 != null) {
            ((InputMethodManager)object2).displayCompletions((View)object, arrcompletionInfo);
            return;
        }
        this.checkFocus();
        object2 = this.mH;
        synchronized (object2) {
            if (!(this.mServedView == object || this.mServedView != null && this.mServedView.checkInputConnectionProxy((View)object))) {
                return;
            }
            this.mCompletions = arrcompletionInfo;
            object = this.mCurMethod;
            if (object != null) {
                try {
                    this.mCurMethod.displayCompletions(this.mCompletions);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            return;
        }
    }

    void doDump(FileDescriptor object, PrintWriter appendable, String[] arrstring) {
        object = new PrintWriterPrinter((PrintWriter)appendable);
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("Input method client state for ");
        ((StringBuilder)appendable).append(this);
        ((StringBuilder)appendable).append(":");
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mService=");
        ((StringBuilder)appendable).append(this.mService);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mMainLooper=");
        ((StringBuilder)appendable).append(this.mMainLooper);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mIInputContext=");
        ((StringBuilder)appendable).append(this.mIInputContext);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mActive=");
        ((StringBuilder)appendable).append(this.mActive);
        ((StringBuilder)appendable).append(" mRestartOnNextWindowFocus=");
        ((StringBuilder)appendable).append(this.mRestartOnNextWindowFocus);
        ((StringBuilder)appendable).append(" mBindSequence=");
        ((StringBuilder)appendable).append(this.mBindSequence);
        ((StringBuilder)appendable).append(" mCurId=");
        ((StringBuilder)appendable).append(this.mCurId);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mFullscreenMode=");
        ((StringBuilder)appendable).append(this.mFullscreenMode);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mCurMethod=");
        ((StringBuilder)appendable).append(this.mCurMethod);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mCurRootView=");
        ((StringBuilder)appendable).append(this.mCurRootView);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mServedView=");
        ((StringBuilder)appendable).append(this.mServedView);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mNextServedView=");
        ((StringBuilder)appendable).append(this.mNextServedView);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mServedConnecting=");
        ((StringBuilder)appendable).append(this.mServedConnecting);
        object.println(((StringBuilder)appendable).toString());
        if (this.mCurrentTextBoxAttribute != null) {
            object.println("  mCurrentTextBoxAttribute:");
            this.mCurrentTextBoxAttribute.dump((Printer)object, "    ");
        } else {
            object.println("  mCurrentTextBoxAttribute: null");
        }
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mServedInputConnectionWrapper=");
        ((StringBuilder)appendable).append(this.mServedInputConnectionWrapper);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mCompletions=");
        ((StringBuilder)appendable).append(Arrays.toString(this.mCompletions));
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mCursorRect=");
        ((StringBuilder)appendable).append(this.mCursorRect);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mCursorSelStart=");
        ((StringBuilder)appendable).append(this.mCursorSelStart);
        ((StringBuilder)appendable).append(" mCursorSelEnd=");
        ((StringBuilder)appendable).append(this.mCursorSelEnd);
        ((StringBuilder)appendable).append(" mCursorCandStart=");
        ((StringBuilder)appendable).append(this.mCursorCandStart);
        ((StringBuilder)appendable).append(" mCursorCandEnd=");
        ((StringBuilder)appendable).append(this.mCursorCandEnd);
        object.println(((StringBuilder)appendable).toString());
    }

    @UnsupportedAppUsage
    void finishInputLocked() {
        this.mNextServedView = null;
        this.mActivityViewToScreenMatrix = null;
        if (this.mServedView != null) {
            this.mServedView = null;
            this.mCompletions = null;
            this.mServedConnecting = false;
            this.clearConnectionLocked();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void finishedInputEvent(int n, boolean bl, boolean bl2) {
        PendingEvent pendingEvent;
        H h = this.mH;
        synchronized (h) {
            n = this.mPendingEvents.indexOfKey(n);
            if (n < 0) {
                return;
            }
            pendingEvent = this.mPendingEvents.valueAt(n);
            this.mPendingEvents.removeAt(n);
            Trace.traceCounter(4L, PENDING_EVENT_COUNTER, this.mPendingEvents.size());
            if (bl2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Timeout waiting for IME to handle input event after 2500 ms: ");
                stringBuilder.append(pendingEvent.mInputMethodId);
                Log.w(TAG, stringBuilder.toString());
            } else {
                this.mH.removeMessages(6, pendingEvent);
            }
        }
        this.invokeFinishedInputEventCallback(pendingEvent, bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void focusIn(View view) {
        H h = this.mH;
        synchronized (h) {
            this.focusInLocked(view);
            return;
        }
    }

    void focusInLocked(View view) {
        if (view != null && view.isTemporarilyDetached()) {
            return;
        }
        if (this.mCurRootView != view.getRootView()) {
            return;
        }
        this.mNextServedView = view;
        InputMethodManager.scheduleCheckFocusLocked(view);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void focusOut(View object) {
        object = this.mH;
        synchronized (object) {
            View view = this.mServedView;
            return;
        }
    }

    @UnsupportedAppUsage
    public IInputMethodClient getClient() {
        return this.mClient;
    }

    public InputMethodSubtype getCurrentInputMethodSubtype() {
        try {
            InputMethodSubtype inputMethodSubtype = this.mService.getCurrentInputMethodSubtype();
            return inputMethodSubtype;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public List<InputMethodInfo> getEnabledInputMethodList() {
        try {
            List<InputMethodInfo> list = this.mService.getEnabledInputMethodList(UserHandle.myUserId());
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<InputMethodInfo> getEnabledInputMethodListAsUser(int n) {
        try {
            List<InputMethodInfo> list = this.mService.getEnabledInputMethodList(n);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(InputMethodInfo list, boolean bl) {
        IInputMethodManager iInputMethodManager;
        block3 : {
            try {
                iInputMethodManager = this.mService;
                if (list != null) break block3;
                list = null;
                return iInputMethodManager.getEnabledInputMethodSubtypeList((String)((Object)list), bl);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        list = ((InputMethodInfo)((Object)list)).getId();
        return iInputMethodManager.getEnabledInputMethodSubtypeList((String)((Object)list), bl);
    }

    @UnsupportedAppUsage
    public IInputContext getInputContext() {
        return this.mIInputContext;
    }

    public List<InputMethodInfo> getInputMethodList() {
        try {
            List<InputMethodInfo> list = this.mService.getInputMethodList(UserHandle.myUserId());
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<InputMethodInfo> getInputMethodListAsUser(int n) {
        try {
            List<InputMethodInfo> list = this.mService.getInputMethodList(n);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int getInputMethodWindowVisibleHeight() {
        try {
            int n = this.mService.getInputMethodWindowVisibleHeight();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public InputMethodSubtype getLastInputMethodSubtype() {
        try {
            InputMethodSubtype inputMethodSubtype = this.mService.getLastInputMethodSubtype();
            return inputMethodSubtype;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Map<InputMethodInfo, List<InputMethodSubtype>> getShortcutInputMethodsAndSubtypes() {
        List<InputMethodInfo> list = this.getEnabledInputMethodList();
        list.sort(Comparator.comparingInt(_$$Lambda$InputMethodManager$pvWYFFVbHzZCDCCTiZVM09Xls4w.INSTANCE));
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            InputMethodInfo inputMethodInfo = list.get(i);
            int n2 = this.getEnabledInputMethodSubtypeList(inputMethodInfo, true).size();
            for (int j = 0; j < n2; ++j) {
                InputMethodSubtype inputMethodSubtype = inputMethodInfo.getSubtypeAt(j);
                if (!SUBTYPE_MODE_VOICE.equals(inputMethodSubtype.getMode())) continue;
                return Collections.singletonMap(inputMethodInfo, Collections.singletonList(inputMethodSubtype));
            }
        }
        return Collections.emptyMap();
    }

    @Deprecated
    public void hideSoftInputFromInputMethod(IBinder iBinder, int n) {
        InputMethodPrivilegedOperationsRegistry.get(iBinder).hideMySoftInput(n);
    }

    public boolean hideSoftInputFromWindow(IBinder iBinder, int n) {
        return this.hideSoftInputFromWindow(iBinder, n, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean hideSoftInputFromWindow(IBinder iBinder, int n, ResultReceiver resultReceiver) {
        this.checkFocus();
        H h = this.mH;
        synchronized (h) {
            if (this.mServedView == null) return false;
            IBinder iBinder2 = this.mServedView.getWindowToken();
            if (iBinder2 == iBinder) {
                try {
                    return this.mService.hideSoftInput(this.mClient, n, resultReceiver);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            return false;
        }
    }

    @Deprecated
    public void hideStatusIcon(IBinder iBinder) {
        InputMethodPrivilegedOperationsRegistry.get(iBinder).updateStatusIcon(null, 0);
    }

    void invokeFinishedInputEventCallback(PendingEvent object, boolean bl) {
        ((PendingEvent)object).mHandled = bl;
        if (((PendingEvent)object).mHandler.getLooper().isCurrentThread()) {
            ((PendingEvent)object).run();
        } else {
            object = Message.obtain(((PendingEvent)object).mHandler, (Runnable)object);
            ((Message)object).setAsynchronous(true);
            ((Message)object).sendToTarget();
        }
    }

    public boolean isAcceptingText() {
        this.checkFocus();
        ControlledInputConnectionWrapper controlledInputConnectionWrapper = this.mServedInputConnectionWrapper;
        boolean bl = controlledInputConnectionWrapper != null && controlledInputConnectionWrapper.getInputConnection() != null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isActive() {
        this.checkFocus();
        H h = this.mH;
        synchronized (h) {
            if (this.mServedView == null) return false;
            if (this.mCurrentTextBoxAttribute == null) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isActive(View view) {
        Object object = this.getFallbackInputMethodManagerIfNecessary(view);
        if (object != null) {
            return ((InputMethodManager)object).isActive(view);
        }
        this.checkFocus();
        object = this.mH;
        synchronized (object) {
            if (this.mServedView != view) {
                if (this.mServedView == null) return false;
                if (!this.mServedView.checkInputConnectionProxy(view)) return false;
            }
            if (this.mCurrentTextBoxAttribute == null) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public boolean isCursorAnchorInfoEnabled() {
        H h = this.mH;
        synchronized (h) {
            int n = this.mRequestUpdateCursorAnchorInfoMonitorMode;
            boolean bl = true;
            n = (n & 1) != 0 ? 1 : 0;
            boolean bl2 = (this.mRequestUpdateCursorAnchorInfoMonitorMode & 2) != 0;
            boolean bl3 = bl;
            if (n != 0) return bl3;
            if (!bl2) return false;
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isFullscreenMode() {
        H h = this.mH;
        synchronized (h) {
            return this.mFullscreenMode;
        }
    }

    public boolean isInputMethodPickerShown() {
        try {
            boolean bl = this.mService.isInputMethodPickerShownForTest();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean isWatchingCursor(View view) {
        return false;
    }

    public /* synthetic */ void lambda$startInputInner$1$InputMethodManager(int n) {
        this.startInputInner(n, null, 0, 0, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void notifyImeHidden() {
        H h = this.mH;
        synchronized (h) {
            try {
                try {
                    if (this.mCurMethod != null) {
                        this.mCurMethod.notifyImeHidden();
                    }
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public void notifySuggestionPicked(SuggestionSpan suggestionSpan, String string2, int n) {
        Log.w(TAG, "notifySuggestionPicked() is deprecated.  Does nothing.");
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=114740982L)
    public void notifyUserAction() {
        Log.w(TAG, "notifyUserAction() is a hidden method, which is now just a stub method that does nothing.  Leave comments in b.android.com/114740982 if your  application still depends on the previous behavior of this method.");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void onPostWindowFocus(View view, View object, int n, boolean bl, int n2) {
        int n3;
        boolean bl2;
        block15 : {
            int n4;
            H h = this.mH;
            // MONITORENTER : h
            if (this.mRestartOnNextWindowFocus) {
                this.mRestartOnNextWindowFocus = false;
                bl2 = true;
            } else {
                bl2 = false;
            }
            Object object2 = object != null ? object : view;
            this.focusInLocked((View)object2);
            // MONITOREXIT : h
            n3 = 0;
            if (object == null) break block15;
            n3 = n4 = false | true;
            if (((View)object).onCheckIsTextEditor()) {
                n3 = n4 | 2;
            }
        }
        if (bl) {
            n3 |= 4;
        }
        if (this.checkFocusNoStartInput(bl2) && this.startInputInner(1, view.getWindowToken(), n3, n, n2)) {
            return;
        }
        object = this.mH;
        // MONITORENTER : object
        this.mService.startInputOrWindowGainedFocus(2, this.mClient, view.getWindowToken(), n3, n, n2, null, null, 0, view.getContext().getApplicationInfo().targetSdkVersion);
        // MONITOREXIT : object
        return;
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void onPreWindowFocus(View view, boolean bl) {
        H h = this.mH;
        synchronized (h) {
            Throwable throwable2;
            block7 : {
                if (view == null) {
                    try {
                        this.mCurRootView = null;
                    }
                    catch (Throwable throwable2) {
                        break block7;
                    }
                }
                if (bl) {
                    this.mCurRootView = view;
                } else if (view == this.mCurRootView) {
                    this.mCurRootView = null;
                }
                return;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onViewDetachedFromWindow(View view) {
        H h = this.mH;
        synchronized (h) {
            if (this.mServedView == view) {
                this.mNextServedView = null;
                InputMethodManager.scheduleCheckFocusLocked(view);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerImeConsumer(ImeInsetsSourceConsumer imeInsetsSourceConsumer) {
        if (imeInsetsSourceConsumer != null) {
            H h = this.mH;
            synchronized (h) {
                this.mImeInsetsConsumer = imeInsetsSourceConsumer;
                return;
            }
        }
        throw new IllegalStateException("ImeInsetsSourceConsumer cannot be null.");
    }

    @Deprecated
    @UnsupportedAppUsage
    public void registerSuggestionSpansForNotification(SuggestionSpan[] arrsuggestionSpan) {
        Log.w(TAG, "registerSuggestionSpansForNotification() is deprecated.  Does nothing.");
    }

    public void reportActivityView(int n, Matrix arrf) {
        if (arrf == null) {
            arrf = null;
        } else {
            float[] arrf2 = new float[9];
            arrf.getValues(arrf2);
            arrf = arrf2;
        }
        try {
            this.mService.reportActivityView(this.mClient, n, arrf);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean requestImeShow(ResultReceiver resultReceiver) {
        H h = this.mH;
        synchronized (h) {
            if (this.mServedView == null) {
                return false;
            }
            this.showSoftInput(this.mServedView, 0, resultReceiver);
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void restartInput(View view) {
        Object object = this.getFallbackInputMethodManagerIfNecessary(view);
        if (object != null) {
            ((InputMethodManager)object).restartInput(view);
            return;
        }
        this.checkFocus();
        object = this.mH;
        synchronized (object) {
            if (!(this.mServedView == view || this.mServedView != null && this.mServedView.checkInputConnectionProxy(view))) {
                return;
            }
            this.mServedConnecting = true;
        }
        this.startInputInner(3, null, 0, 0, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendAppPrivateCommand(View object, String string2, Bundle bundle) {
        Object object2 = this.getFallbackInputMethodManagerIfNecessary((View)object);
        if (object2 != null) {
            ((InputMethodManager)object2).sendAppPrivateCommand((View)object, string2, bundle);
            return;
        }
        this.checkFocus();
        object2 = this.mH;
        synchronized (object2) {
            if ((this.mServedView == object || this.mServedView != null && this.mServedView.checkInputConnectionProxy((View)object)) && this.mCurrentTextBoxAttribute != null && (object = this.mCurMethod) != null) {
                try {
                    this.mCurMethod.appPrivateCommand(string2, bundle);
                }
                catch (RemoteException remoteException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("IME died: ");
                    ((StringBuilder)object).append(this.mCurId);
                    Log.w(TAG, ((StringBuilder)object).toString(), remoteException);
                }
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void sendInputEventAndReportResultOnMainLooper(PendingEvent pendingEvent) {
        boolean bl;
        H h = this.mH;
        synchronized (h) {
            int n = this.sendInputEventOnMainLooperLocked(pendingEvent);
            if (n == -1) {
                return;
            }
            bl = true;
            if (n != 1) {
                bl = false;
            }
        }
        this.invokeFinishedInputEventCallback(pendingEvent, bl);
    }

    int sendInputEventOnMainLooperLocked(PendingEvent object) {
        Parcelable parcelable = this.mCurChannel;
        if (parcelable != null) {
            int n;
            if (this.mCurSender == null) {
                this.mCurSender = new ImeInputEventSender((InputChannel)parcelable, this.mH.getLooper());
            }
            if (this.mCurSender.sendInputEvent(n = ((InputEvent)(parcelable = ((PendingEvent)object).mEvent)).getSequenceNumber(), (InputEvent)parcelable)) {
                this.mPendingEvents.put(n, (PendingEvent)object);
                Trace.traceCounter(4L, PENDING_EVENT_COUNTER, this.mPendingEvents.size());
                object = this.mH.obtainMessage(6, n, 0, object);
                ((Message)object).setAsynchronous(true);
                this.mH.sendMessageDelayed((Message)object, 2500L);
                return -1;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to send input event to IME: ");
            ((StringBuilder)object).append(this.mCurId);
            ((StringBuilder)object).append(" dropping: ");
            ((StringBuilder)object).append(parcelable);
            Log.w(TAG, ((StringBuilder)object).toString());
        }
        return 0;
    }

    @Deprecated
    public void setAdditionalInputMethodSubtypes(String string2, InputMethodSubtype[] arrinputMethodSubtype) {
        try {
            this.mService.setAdditionalInputMethodSubtypes(string2, arrinputMethodSubtype);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean setCurrentInputMethodSubtype(InputMethodSubtype inputMethodSubtype) {
        int n;
        if (Process.myUid() == 1000) {
            Log.w(TAG, "System process should not call setCurrentInputMethodSubtype() because almost always it is a bug under multi-user / multi-profile environment. Consider directly interacting with InputMethodManagerService via LocalServices.");
            return false;
        }
        if (inputMethodSubtype == null) {
            return false;
        }
        Object object = ActivityThread.currentApplication();
        if (object == null) {
            return false;
        }
        if (((Context)object).checkSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            return false;
        }
        Object object2 = Settings.Secure.getString((ContentResolver)(object = ((Context)object).getContentResolver()), "default_input_method");
        if (ComponentName.unflattenFromString((String)object2) == null) {
            return false;
        }
        try {
            object2 = this.mService.getEnabledInputMethodSubtypeList((String)object2, true);
            n = object2.size();
        }
        catch (RemoteException remoteException) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            InputMethodSubtype inputMethodSubtype2 = (InputMethodSubtype)object2.get(i);
            if (!inputMethodSubtype2.equals(inputMethodSubtype)) continue;
            Settings.Secure.putInt((ContentResolver)object, "selected_input_method_subtype", inputMethodSubtype2.hashCode());
            return true;
        }
        return false;
    }

    void setInputChannelLocked(InputChannel inputChannel) {
        if (this.mCurChannel != inputChannel) {
            InputChannel inputChannel2;
            if (this.mCurSender != null) {
                this.flushPendingEventsLocked();
                this.mCurSender.dispose();
                this.mCurSender = null;
            }
            if ((inputChannel2 = this.mCurChannel) != null) {
                inputChannel2.dispose();
            }
            this.mCurChannel = inputChannel;
        }
    }

    @Deprecated
    public void setInputMethod(IBinder object, String string2) {
        if (object == null) {
            boolean bl;
            if (string2 == null) {
                return;
            }
            if (Process.myUid() == 1000) {
                Log.w(TAG, "System process should not be calling setInputMethod() because almost always it is a bug under multi-user / multi-profile environment. Consider interacting with InputMethodManagerService directly via LocalServices.");
                return;
            }
            Application application = ActivityThread.currentApplication();
            if (application == null) {
                return;
            }
            if (((Context)application).checkSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                return;
            }
            object = this.getEnabledInputMethodList();
            int n = object.size();
            boolean bl2 = false;
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= n) break;
                if (string2.equals(((InputMethodInfo)object.get(n2)).getId())) {
                    bl = true;
                    break;
                }
                ++n2;
            } while (true);
            if (!bl) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Ignoring setInputMethod(null, ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(") because the specified id not found in enabled IMEs.");
                Log.e(TAG, ((StringBuilder)object).toString());
                return;
            }
            Log.w(TAG, "The undocumented behavior that setInputMethod() accepts null token when the caller has WRITE_SECURE_SETTINGS is deprecated. This behavior may be completely removed in a future version.  Update secure settings directly instead.");
            object = ((Context)application).getContentResolver();
            Settings.Secure.putInt((ContentResolver)object, "selected_input_method_subtype", -1);
            Settings.Secure.putString((ContentResolver)object, "default_input_method", string2);
            return;
        }
        InputMethodPrivilegedOperationsRegistry.get((IBinder)object).setInputMethod(string2);
    }

    @Deprecated
    public void setInputMethodAndSubtype(IBinder iBinder, String string2, InputMethodSubtype inputMethodSubtype) {
        if (iBinder == null) {
            Log.e(TAG, "setInputMethodAndSubtype() does not accept null token on Android Q and later.");
            return;
        }
        InputMethodPrivilegedOperationsRegistry.get(iBinder).setInputMethodAndSubtype(string2, inputMethodSubtype);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void setUpdateCursorAnchorInfoMode(int n) {
        H h = this.mH;
        synchronized (h) {
            this.mRequestUpdateCursorAnchorInfoMonitorMode = n;
            return;
        }
    }

    @Deprecated
    public boolean shouldOfferSwitchingToNextInputMethod(IBinder iBinder) {
        return InputMethodPrivilegedOperationsRegistry.get(iBinder).shouldOfferSwitchingToNextInputMethod();
    }

    public void showInputMethodAndSubtypeEnabler(String string2) {
        try {
            this.mService.showInputMethodAndSubtypeEnablerFromClient(this.mClient, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void showInputMethodPicker() {
        H h = this.mH;
        synchronized (h) {
            this.showInputMethodPickerLocked();
            return;
        }
    }

    public void showInputMethodPickerFromSystem(boolean bl, int n) {
        int n2 = bl ? 1 : 2;
        try {
            this.mService.showInputMethodPickerFromSystem(this.mClient, n2, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean showSoftInput(View view, int n) {
        InputMethodManager inputMethodManager = this.getFallbackInputMethodManagerIfNecessary(view);
        if (inputMethodManager != null) {
            return inputMethodManager.showSoftInput(view, n);
        }
        return this.showSoftInput(view, n, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean showSoftInput(View view, int n, ResultReceiver resultReceiver) {
        Object object = this.getFallbackInputMethodManagerIfNecessary(view);
        if (object != null) {
            return ((InputMethodManager)object).showSoftInput(view, n, resultReceiver);
        }
        this.checkFocus();
        object = this.mH;
        synchronized (object) {
            if (this.mServedView != view) {
                if (this.mServedView == null) return false;
                if (!this.mServedView.checkInputConnectionProxy(view)) {
                    return false;
                }
            }
            try {
                return this.mService.showSoftInput(this.mClient, n, resultReceiver);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public void showSoftInputFromInputMethod(IBinder iBinder, int n) {
        InputMethodPrivilegedOperationsRegistry.get(iBinder).showMySoftInput(n);
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768499L)
    public void showSoftInputUnchecked(int n, ResultReceiver resultReceiver) {
        try {
            Log.w(TAG, "showSoftInputUnchecked() is a hidden method, which will be removed soon. If you are using android.support.v7.widget.SearchView, please update to version 26.0 or newer version.");
            this.mService.showSoftInput(this.mClient, n, resultReceiver);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void showStatusIcon(IBinder iBinder, String string2, int n) {
        InputMethodPrivilegedOperationsRegistry.get(iBinder).updateStatusIcon(string2, n);
    }

    /*
     * Exception decompiling
     */
    boolean startInputInner(int var1_1, IBinder var2_2, int var3_5, int var4_6, int var5_7) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 6[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Deprecated
    public boolean switchToLastInputMethod(IBinder iBinder) {
        return InputMethodPrivilegedOperationsRegistry.get(iBinder).switchToPreviousInputMethod();
    }

    @Deprecated
    public boolean switchToNextInputMethod(IBinder iBinder, boolean bl) {
        return InputMethodPrivilegedOperationsRegistry.get(iBinder).switchToNextInputMethod(bl);
    }

    public void toggleSoftInput(int n, int n2) {
        IInputMethodSession iInputMethodSession = this.mCurMethod;
        if (iInputMethodSession != null) {
            try {
                iInputMethodSession.toggleSoftInput(n, n2);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void toggleSoftInputFromWindow(IBinder object, int n, int n2) {
        H h = this.mH;
        synchronized (h) {
            if (this.mServedView != null && this.mServedView.getWindowToken() == object) {
                object = this.mCurMethod;
                if (object != null) {
                    try {
                        this.mCurMethod.toggleSoftInput(n, n2);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterImeConsumer(ImeInsetsSourceConsumer imeInsetsSourceConsumer) {
        if (imeInsetsSourceConsumer == null) {
            throw new IllegalStateException("ImeInsetsSourceConsumer cannot be null.");
        }
        H h = this.mH;
        synchronized (h) {
            if (this.mImeInsetsConsumer == imeInsetsSourceConsumer) {
                this.mImeInsetsConsumer = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void updateCursor(View object, int n, int n2, int n3, int n4) {
        Object object2 = this.getFallbackInputMethodManagerIfNecessary((View)object);
        if (object2 != null) {
            ((InputMethodManager)object2).updateCursor((View)object, n, n2, n3, n4);
            return;
        }
        this.checkFocus();
        object2 = this.mH;
        synchronized (object2) {
            if ((this.mServedView == object || this.mServedView != null && this.mServedView.checkInputConnectionProxy((View)object)) && this.mCurrentTextBoxAttribute != null && this.mCurMethod != null) {
                this.mTmpCursorRect.set(n, n2, n3, n4);
                boolean bl = this.mCursorRect.equals(this.mTmpCursorRect);
                if (!bl) {
                    try {
                        this.mCurMethod.updateCursor(this.mTmpCursorRect);
                        this.mCursorRect.set(this.mTmpCursorRect);
                    }
                    catch (RemoteException remoteException) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("IME died: ");
                        ((StringBuilder)object).append(this.mCurId);
                        Log.w(TAG, ((StringBuilder)object).toString(), remoteException);
                    }
                }
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateCursorAnchorInfo(View view, CursorAnchorInfo object) {
        if (view == null) return;
        if (object == null) {
            return;
        }
        Object object2 = this.getFallbackInputMethodManagerIfNecessary(view);
        if (object2 != null) {
            ((InputMethodManager)object2).updateCursorAnchorInfo(view, (CursorAnchorInfo)object);
            return;
        }
        this.checkFocus();
        object2 = this.mH;
        synchronized (object2) {
            if (this.mServedView != view) {
                if (this.mServedView == null) return;
                if (!this.mServedView.checkInputConnectionProxy(view)) return;
            }
            if (this.mCurrentTextBoxAttribute == null) return;
            if (this.mCurMethod == null) {
                return;
            }
            int n = this.mRequestUpdateCursorAnchorInfoMonitorMode;
            boolean bl = true;
            if ((n & 1) == 0) {
                bl = false;
            }
            if (!bl && Objects.equals(this.mCursorAnchorInfo, object)) {
                return;
            }
            try {
                if (this.mActivityViewToScreenMatrix != null) {
                    this.mCurMethod.updateCursorAnchorInfo(CursorAnchorInfo.createForAdditionalParentMatrix((CursorAnchorInfo)object, this.mActivityViewToScreenMatrix));
                } else {
                    this.mCurMethod.updateCursorAnchorInfo((CursorAnchorInfo)object);
                }
                this.mCursorAnchorInfo = object;
                this.mRequestUpdateCursorAnchorInfoMonitorMode &= -2;
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("IME died: ");
                ((StringBuilder)object).append(this.mCurId);
                Log.w(TAG, ((StringBuilder)object).toString(), remoteException);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateExtractedText(View object, int n, ExtractedText extractedText) {
        Object object2 = this.getFallbackInputMethodManagerIfNecessary((View)object);
        if (object2 != null) {
            ((InputMethodManager)object2).updateExtractedText((View)object, n, extractedText);
            return;
        }
        this.checkFocus();
        object2 = this.mH;
        synchronized (object2) {
            if (!(this.mServedView == object || this.mServedView != null && this.mServedView.checkInputConnectionProxy((View)object))) {
                return;
            }
            object = this.mCurMethod;
            if (object != null) {
                try {
                    this.mCurMethod.updateExtractedText(n, extractedText);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateSelection(View view, int n, int n2, int n3, int n4) {
        Object object = this.getFallbackInputMethodManagerIfNecessary(view);
        if (object != null) {
            ((InputMethodManager)object).updateSelection(view, n, n2, n3, n4);
            return;
        }
        this.checkFocus();
        object = this.mH;
        synchronized (object) {
            if ((this.mServedView == view || this.mServedView != null && this.mServedView.checkInputConnectionProxy(view)) && this.mCurrentTextBoxAttribute != null && this.mCurMethod != null) {
                int n5;
                if (this.mCursorSelStart != n || this.mCursorSelEnd != n2 || this.mCursorCandStart != n3 || (n5 = this.mCursorCandEnd) != n4) {
                    try {
                        int n6 = this.mCursorSelStart;
                        n5 = this.mCursorSelEnd;
                        this.mCursorSelStart = n;
                        this.mCursorSelEnd = n2;
                        this.mCursorCandStart = n3;
                        this.mCursorCandEnd = n4;
                        this.mCurMethod.updateSelection(n6, n5, n, n2, n3, n4);
                    }
                    catch (RemoteException remoteException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("IME died: ");
                        stringBuilder.append(this.mCurId);
                        Log.w(TAG, stringBuilder.toString(), remoteException);
                    }
                }
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void viewClicked(View object) {
        Object object2 = this.getFallbackInputMethodManagerIfNecessary((View)object);
        if (object2 != null) {
            ((InputMethodManager)object2).viewClicked((View)object);
            return;
        }
        boolean bl = this.mServedView != this.mNextServedView;
        this.checkFocus();
        object2 = this.mH;
        synchronized (object2) {
            if ((this.mServedView == object || this.mServedView != null && this.mServedView.checkInputConnectionProxy((View)object)) && this.mCurrentTextBoxAttribute != null && (object = this.mCurMethod) != null) {
                try {
                    this.mCurMethod.viewClicked(bl);
                }
                catch (RemoteException remoteException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("IME died: ");
                    stringBuilder.append(this.mCurId);
                    Log.w(TAG, stringBuilder.toString(), remoteException);
                }
                return;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void windowDismissed(IBinder iBinder) {
        this.checkFocus();
        H h = this.mH;
        synchronized (h) {
            if (this.mServedView != null && this.mServedView.getWindowToken() == iBinder) {
                this.finishInputLocked();
            }
            if (this.mCurRootView != null && this.mCurRootView.getWindowToken() == iBinder) {
                this.mCurRootView = null;
            }
            return;
        }
    }

    private static class ControlledInputConnectionWrapper
    extends IInputConnectionWrapper {
        private final InputMethodManager mParentInputMethodManager;

        public ControlledInputConnectionWrapper(Looper looper, InputConnection inputConnection, InputMethodManager inputMethodManager) {
            super(looper, inputConnection);
            this.mParentInputMethodManager = inputMethodManager;
        }

        void deactivate() {
            if (this.isFinished()) {
                return;
            }
            this.closeConnection();
        }

        @Override
        public boolean isActive() {
            boolean bl = this.mParentInputMethodManager.mActive && !this.isFinished();
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ControlledInputConnectionWrapper{connection=");
            stringBuilder.append(this.getInputConnection());
            stringBuilder.append(" finished=");
            stringBuilder.append(this.isFinished());
            stringBuilder.append(" mParentInputMethodManager.mActive=");
            stringBuilder.append(this.mParentInputMethodManager.mActive);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    public static interface FinishedInputEventCallback {
        public void onFinishedInputEvent(Object var1, boolean var2);
    }

    class H
    extends Handler {
        H(Looper looper) {
            super(looper, null, true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            boolean bl = true;
            boolean bl2 = true;
            int n2 = 1;
            boolean bl3 = false;
            if (n != 10) {
                if (n != 15) {
                    if (n != 20) {
                        if (n != 30) {
                            Object object2;
                            switch (n) {
                                default: {
                                    return;
                                }
                                case 7: {
                                    InputMethodManager.this.finishedInputEvent(((Message)object).arg1, false, false);
                                    return;
                                }
                                case 6: {
                                    InputMethodManager.this.finishedInputEvent(((Message)object).arg1, false, true);
                                    return;
                                }
                                case 5: {
                                    InputMethodManager.this.sendInputEventAndReportResultOnMainLooper((PendingEvent)((Message)object).obj);
                                    return;
                                }
                                case 4: {
                                    bl = ((Message)object).arg1 != 0;
                                    if (((Message)object).arg2 != 0) {
                                        bl3 = true;
                                    }
                                    object = InputMethodManager.this.mH;
                                    synchronized (object) {
                                        InputMethodManager.this.mActive = bl;
                                        InputMethodManager.this.mFullscreenMode = bl3;
                                        if (!bl) {
                                            InputMethodManager.this.mRestartOnNextWindowFocus = true;
                                            try {
                                                InputMethodManager.this.mIInputContext.finishComposingText();
                                            }
                                            catch (RemoteException remoteException) {
                                                // empty catch block
                                            }
                                        }
                                        if (InputMethodManager.this.mServedView == null) return;
                                        if (!InputMethodManager.canStartInput(InputMethodManager.this.mServedView)) return;
                                        if (!InputMethodManager.this.checkFocusNoStartInput(InputMethodManager.this.mRestartOnNextWindowFocus)) return;
                                        n2 = bl ? 7 : 8;
                                        InputMethodManager.this.startInputInner(n2, null, 0, 0, 0);
                                        return;
                                    }
                                }
                                case 3: {
                                    n = ((Message)object).arg1;
                                    n2 = ((Message)object).arg2;
                                    object = InputMethodManager.this.mH;
                                    synchronized (object) {
                                        if (InputMethodManager.this.mBindSequence != n) {
                                            return;
                                        }
                                        InputMethodManager.this.clearBindingLocked();
                                        if (InputMethodManager.this.mServedView != null && InputMethodManager.this.mServedView.isFocused()) {
                                            InputMethodManager.this.mServedConnecting = true;
                                        }
                                        bl = InputMethodManager.this.mActive;
                                    }
                                    if (!bl) return;
                                    InputMethodManager.this.startInputInner(6, null, 0, 0, 0);
                                    return;
                                }
                                case 2: {
                                    InputBindResult inputBindResult = (InputBindResult)((Message)object).obj;
                                    object = InputMethodManager.this.mH;
                                    synchronized (object) {
                                        if (InputMethodManager.this.mBindSequence >= 0 && InputMethodManager.this.mBindSequence == inputBindResult.sequence) {
                                            InputMethodManager.this.mRequestUpdateCursorAnchorInfoMonitorMode = 0;
                                            InputMethodManager.this.setInputChannelLocked(inputBindResult.channel);
                                            InputMethodManager.this.mCurMethod = inputBindResult.method;
                                            InputMethodManager.this.mCurId = inputBindResult.id;
                                            InputMethodManager.this.mBindSequence = inputBindResult.sequence;
                                            InputMethodManager.this.mActivityViewToScreenMatrix = inputBindResult.getActivityViewToScreenMatrix();
                                            // MONITOREXIT [48, 49, 50, 18, 23, 41, 30, 47] lbl75 : MonitorExitStatement: MONITOREXIT : var1_1
                                            InputMethodManager.this.startInputInner(5, null, 0, 0, 0);
                                            return;
                                        }
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("Ignoring onBind: cur seq=");
                                        stringBuilder.append(InputMethodManager.this.mBindSequence);
                                        stringBuilder.append(", given seq=");
                                        stringBuilder.append(inputBindResult.sequence);
                                        Log.w(InputMethodManager.TAG, stringBuilder.toString());
                                        if (inputBindResult.channel == null) return;
                                        if (inputBindResult.channel == InputMethodManager.this.mCurChannel) return;
                                        inputBindResult.channel.dispose();
                                        return;
                                    }
                                }
                                case 1: 
                            }
                            object = (SomeArgs)((Message)object).obj;
                            try {
                                InputMethodManager.this.doDump((FileDescriptor)((SomeArgs)object).arg1, (PrintWriter)((SomeArgs)object).arg2, (String[])((SomeArgs)object).arg3);
                            }
                            catch (RuntimeException runtimeException) {
                                object2 = (PrintWriter)((SomeArgs)object).arg2;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Exception: ");
                                stringBuilder.append(runtimeException);
                                ((PrintWriter)object2).println(stringBuilder.toString());
                            }
                            object2 = ((SomeArgs)object).arg4;
                            synchronized (object2) {
                                ((CountDownLatch)((SomeArgs)object).arg4).countDown();
                            }
                            ((SomeArgs)object).recycle();
                            return;
                        }
                        float[] arrf = (float[])((Message)object).obj;
                        n = ((Message)object).arg1;
                        object = InputMethodManager.this.mH;
                        synchronized (object) {
                            if (InputMethodManager.this.mBindSequence != n) {
                                return;
                            }
                            if (arrf == null) {
                                InputMethodManager.this.mActivityViewToScreenMatrix = null;
                                return;
                            }
                            Object object3 = new float[9];
                            InputMethodManager.this.mActivityViewToScreenMatrix.getValues((float[])object3);
                            if (Arrays.equals((float[])object3, arrf)) {
                                return;
                            }
                            InputMethodManager.this.mActivityViewToScreenMatrix.setValues(arrf);
                            if (InputMethodManager.this.mCursorAnchorInfo == null) return;
                            if (InputMethodManager.this.mCurMethod == null) return;
                            if (InputMethodManager.this.mServedInputConnectionWrapper != null) {
                                if ((InputMethodManager.this.mRequestUpdateCursorAnchorInfoMonitorMode & 2) == 0) return;
                                if (n2 == 0) {
                                    return;
                                }
                                try {
                                    InputMethodManager.this.mCurMethod.updateCursorAnchorInfo(CursorAnchorInfo.createForAdditionalParentMatrix(InputMethodManager.this.mCursorAnchorInfo, InputMethodManager.this.mActivityViewToScreenMatrix));
                                }
                                catch (RemoteException remoteException) {
                                    object3 = new StringBuilder();
                                    ((StringBuilder)object3).append("IME died: ");
                                    ((StringBuilder)object3).append(InputMethodManager.this.mCurId);
                                    Log.w(InputMethodManager.TAG, ((StringBuilder)object3).toString(), remoteException);
                                }
                                return;
                            }
                            return;
                        }
                    }
                    H h = InputMethodManager.this.mH;
                    synchronized (h) {
                        if (InputMethodManager.this.mImeInsetsConsumer == null) return;
                        ImeInsetsSourceConsumer imeInsetsSourceConsumer = InputMethodManager.this.mImeInsetsConsumer;
                        if (((Message)object).arg1 == 0) {
                            bl = false;
                        }
                        imeInsetsSourceConsumer.applyImeVisibility(bl);
                        return;
                    }
                }
                H h = InputMethodManager.this.mH;
                synchronized (h) {
                    if (InputMethodManager.this.mImeInsetsConsumer == null) return;
                    InputMethodManager.this.mImeInsetsConsumer.onPreRendered((EditorInfo)((Message)object).obj);
                    return;
                }
            }
            bl = ((Message)object).arg1 != 0 ? bl2 : false;
            object = null;
            H h = InputMethodManager.this.mH;
            synchronized (h) {
                InputMethodManager.this.mFullscreenMode = bl;
                if (InputMethodManager.this.mServedInputConnectionWrapper != null) {
                    object = InputMethodManager.this.mServedInputConnectionWrapper.getInputConnection();
                }
            }
            if (object == null) return;
            object.reportFullscreenMode(bl);
        }
    }

    private final class ImeInputEventSender
    extends InputEventSender {
        public ImeInputEventSender(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        @Override
        public void onInputEventFinished(int n, boolean bl) {
            InputMethodManager.this.finishedInputEvent(n, bl, false);
        }
    }

    private final class PendingEvent
    implements Runnable {
        public FinishedInputEventCallback mCallback;
        public InputEvent mEvent;
        public boolean mHandled;
        public Handler mHandler;
        public String mInputMethodId;
        public Object mToken;

        private PendingEvent() {
        }

        public void recycle() {
            this.mEvent = null;
            this.mToken = null;
            this.mInputMethodId = null;
            this.mCallback = null;
            this.mHandler = null;
            this.mHandled = false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            this.mCallback.onFinishedInputEvent(this.mToken, this.mHandled);
            H h = InputMethodManager.this.mH;
            synchronized (h) {
                InputMethodManager.this.recyclePendingEventLocked(this);
                return;
            }
        }
    }

}

