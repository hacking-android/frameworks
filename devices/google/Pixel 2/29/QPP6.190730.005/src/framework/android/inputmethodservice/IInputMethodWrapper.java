/*
 * Decompiled with CFR 0.145.
 */
package android.inputmethodservice;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.inputmethodservice.AbstractInputMethodService;
import android.inputmethodservice.IInputMethodSessionWrapper;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.InputChannel;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodSession;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.inputmethod.IInputMethodPrivilegedOperations;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import com.android.internal.view.IInputContext;
import com.android.internal.view.IInputMethod;
import com.android.internal.view.IInputMethodSession;
import com.android.internal.view.IInputSessionCallback;
import com.android.internal.view.InputConnectionWrapper;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

class IInputMethodWrapper
extends IInputMethod.Stub
implements HandlerCaller.Callback {
    private static final int DO_CHANGE_INPUTMETHOD_SUBTYPE = 80;
    private static final int DO_CREATE_SESSION = 40;
    private static final int DO_DUMP = 1;
    private static final int DO_HIDE_SOFT_INPUT = 70;
    private static final int DO_INITIALIZE_INTERNAL = 10;
    private static final int DO_REVOKE_SESSION = 50;
    private static final int DO_SET_INPUT_CONTEXT = 20;
    private static final int DO_SET_SESSION_ENABLED = 45;
    private static final int DO_SHOW_SOFT_INPUT = 60;
    private static final int DO_START_INPUT = 32;
    private static final int DO_UNSET_INPUT_CONTEXT = 30;
    private static final String TAG = "InputMethodWrapper";
    @UnsupportedAppUsage
    final HandlerCaller mCaller;
    final Context mContext;
    final WeakReference<InputMethod> mInputMethod;
    AtomicBoolean mIsUnbindIssued = null;
    final WeakReference<AbstractInputMethodService> mTarget;
    final int mTargetSdkVersion;

    public IInputMethodWrapper(AbstractInputMethodService abstractInputMethodService, InputMethod inputMethod) {
        this.mTarget = new WeakReference<AbstractInputMethodService>(abstractInputMethodService);
        this.mContext = abstractInputMethodService.getApplicationContext();
        this.mCaller = new HandlerCaller(this.mContext, null, this, true);
        this.mInputMethod = new WeakReference<InputMethod>(inputMethod);
        this.mTargetSdkVersion = abstractInputMethodService.getApplicationInfo().targetSdkVersion;
    }

    @Override
    public void bindInput(InputBinding inputBinding) {
        if (this.mIsUnbindIssued != null) {
            Log.e(TAG, "bindInput must be paired with unbindInput.");
        }
        this.mIsUnbindIssued = new AtomicBoolean();
        inputBinding = new InputBinding(new InputConnectionWrapper(this.mTarget, IInputContext.Stub.asInterface(inputBinding.getConnectionToken()), 0, this.mIsUnbindIssued), inputBinding);
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(20, inputBinding));
    }

    @Override
    public void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(80, inputMethodSubtype));
    }

    @Override
    public void createSession(InputChannel inputChannel, IInputSessionCallback iInputSessionCallback) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(40, inputChannel, iInputSessionCallback));
    }

    @Override
    protected void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        Object object2 = (AbstractInputMethodService)this.mTarget.get();
        if (object2 == null) {
            return;
        }
        if (((ContextWrapper)object2).checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Permission Denial: can't dump InputMethodManager from from pid=");
            ((StringBuilder)object).append(Binder.getCallingPid());
            ((StringBuilder)object).append(", uid=");
            ((StringBuilder)object).append(Binder.getCallingUid());
            printWriter.println(((StringBuilder)object).toString());
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        object2 = this.mCaller;
        ((HandlerCaller)object2).executeOrSendMessage(((HandlerCaller)object2).obtainMessageOOOO(1, object, printWriter, arrstring, countDownLatch));
        try {
            if (!countDownLatch.await(5L, TimeUnit.SECONDS)) {
                printWriter.println("Timeout waiting for dump");
            }
        }
        catch (InterruptedException interruptedException) {
            printWriter.println("Interrupted waiting for dump");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void executeMessage(Message object) {
        Object object2 = (InputMethod)this.mInputMethod.get();
        boolean bl = true;
        if (object2 == null && ((Message)object).what != 1) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Input method reference was null, ignoring message: ");
            ((StringBuilder)object2).append(((Message)object).what);
            Log.w(TAG, ((StringBuilder)object2).toString());
            return;
        }
        switch (((Message)object).what) {
            default: {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unhandled message code: ");
                ((StringBuilder)object2).append(((Message)object).what);
                Log.w(TAG, ((StringBuilder)object2).toString());
                return;
            }
            case 80: {
                object2.changeInputMethodSubtype((InputMethodSubtype)((Message)object).obj);
                return;
            }
            case 70: {
                object2.hideSoftInput(((Message)object).arg1, (ResultReceiver)((Message)object).obj);
                return;
            }
            case 60: {
                object2.showSoftInput(((Message)object).arg1, (ResultReceiver)((Message)object).obj);
                return;
            }
            case 50: {
                object2.revokeSession((InputMethodSession)((Message)object).obj);
                return;
            }
            case 45: {
                InputMethodSession inputMethodSession = (InputMethodSession)((Message)object).obj;
                if (((Message)object).arg1 == 0) {
                    bl = false;
                }
                object2.setSessionEnabled(inputMethodSession, bl);
                return;
            }
            case 40: {
                object = (SomeArgs)((Message)object).obj;
                object2.createSession(new InputMethodSessionCallbackWrapper(this.mContext, (InputChannel)((SomeArgs)object).arg1, (IInputSessionCallback)((SomeArgs)object).arg2));
                ((SomeArgs)object).recycle();
                return;
            }
            case 32: {
                SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                IBinder iBinder = (IBinder)someArgs.arg1;
                IInputContext iInputContext = (IInputContext)someArgs.arg2;
                EditorInfo editorInfo = (EditorInfo)someArgs.arg3;
                object = (AtomicBoolean)someArgs.arg4;
                SomeArgs someArgs2 = (SomeArgs)someArgs.arg5;
                object = iInputContext != null ? new InputConnectionWrapper(this.mTarget, iInputContext, someArgs2.argi3, (AtomicBoolean)object) : null;
                editorInfo.makeCompatible(this.mTargetSdkVersion);
                bl = someArgs2.argi1 == 1;
                boolean bl2 = someArgs2.argi2 == 1;
                object2.dispatchStartInputWithToken((InputConnection)object, editorInfo, bl, iBinder, bl2);
                someArgs.recycle();
                someArgs2.recycle();
                return;
            }
            case 30: {
                object2.unbindInput();
                return;
            }
            case 20: {
                object2.bindInput((InputBinding)((Message)object).obj);
                return;
            }
            case 10: {
                SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                try {
                    object2.initializeInternal((IBinder)someArgs.arg1, ((Message)object).arg1, (IInputMethodPrivilegedOperations)someArgs.arg2);
                    return;
                }
                finally {
                    someArgs.recycle();
                }
            }
            case 1: 
        }
        object2 = (AbstractInputMethodService)this.mTarget.get();
        if (object2 == null) {
            return;
        }
        object = (SomeArgs)((Message)object).obj;
        try {
            ((AbstractInputMethodService)object2).dump((FileDescriptor)((SomeArgs)object).arg1, (PrintWriter)((SomeArgs)object).arg2, (String[])((SomeArgs)object).arg3);
        }
        catch (RuntimeException runtimeException) {
            PrintWriter printWriter = (PrintWriter)((SomeArgs)object).arg2;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception: ");
            stringBuilder.append(runtimeException);
            printWriter.println(stringBuilder.toString());
        }
        object2 = ((SomeArgs)object).arg4;
        synchronized (object2) {
            ((CountDownLatch)((SomeArgs)object).arg4).countDown();
        }
        ((SomeArgs)object).recycle();
    }

    @Override
    public void hideSoftInput(int n, ResultReceiver resultReceiver) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIO(70, n, resultReceiver));
    }

    @Override
    public void initializeInternal(IBinder iBinder, int n, IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIOO(10, n, iBinder, iInputMethodPrivilegedOperations));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void revokeSession(IInputMethodSession iInputMethodSession) {
        try {
            Object object = ((IInputMethodSessionWrapper)iInputMethodSession).getInternalInputMethodSession();
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Session is already finished: ");
                ((StringBuilder)object).append(iInputMethodSession);
                Log.w(TAG, ((StringBuilder)object).toString());
                return;
            }
            this.mCaller.executeOrSendMessage(this.mCaller.obtainMessageO(50, object));
            return;
        }
        catch (ClassCastException classCastException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Incoming session not of correct type: ");
            stringBuilder.append(iInputMethodSession);
            Log.w(TAG, stringBuilder.toString(), classCastException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setSessionEnabled(IInputMethodSession iInputMethodSession, boolean bl) {
        try {
            InputMethodSession inputMethodSession = ((IInputMethodSessionWrapper)iInputMethodSession).getInternalInputMethodSession();
            if (inputMethodSession == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Session is already finished: ");
                stringBuilder.append(iInputMethodSession);
                Log.w(TAG, stringBuilder.toString());
                return;
            }
            HandlerCaller handlerCaller = this.mCaller;
            HandlerCaller handlerCaller2 = this.mCaller;
            int n = bl ? 1 : 0;
            handlerCaller.executeOrSendMessage(handlerCaller2.obtainMessageIO(45, n, inputMethodSession));
            return;
        }
        catch (ClassCastException classCastException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Incoming session not of correct type: ");
            stringBuilder.append(iInputMethodSession);
            Log.w(TAG, stringBuilder.toString(), classCastException);
        }
    }

    @Override
    public void showSoftInput(int n, ResultReceiver resultReceiver) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIO(60, n, resultReceiver));
    }

    @Override
    public void startInput(IBinder iBinder, IInputContext iInputContext, int n, EditorInfo editorInfo, boolean bl, boolean bl2) {
        if (this.mIsUnbindIssued == null) {
            Log.e(TAG, "startInput must be called after bindInput.");
            this.mIsUnbindIssued = new AtomicBoolean();
        }
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.argi1 = bl ? 1 : 0;
        someArgs.argi2 = bl2 ? 1 : 0;
        someArgs.argi3 = n;
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOOOOO(32, iBinder, iInputContext, editorInfo, this.mIsUnbindIssued, someArgs));
    }

    @Override
    public void unbindInput() {
        Object object = this.mIsUnbindIssued;
        if (object != null) {
            ((AtomicBoolean)object).set(true);
            this.mIsUnbindIssued = null;
        } else {
            Log.e(TAG, "unbindInput must be paired with bindInput.");
        }
        object = this.mCaller;
        ((HandlerCaller)object).executeOrSendMessage(((HandlerCaller)object).obtainMessage(30));
    }

    static final class InputMethodSessionCallbackWrapper
    implements InputMethod.SessionCallback {
        final IInputSessionCallback mCb;
        final InputChannel mChannel;
        final Context mContext;

        InputMethodSessionCallbackWrapper(Context context, InputChannel inputChannel, IInputSessionCallback iInputSessionCallback) {
            this.mContext = context;
            this.mChannel = inputChannel;
            this.mCb = iInputSessionCallback;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void sessionCreated(InputMethodSession var1_1) {
            if (var1_1 == null) ** GOTO lbl6
            try {
                var2_3 = new IInputMethodSessionWrapper(this.mContext, var1_1, this.mChannel);
                this.mCb.sessionCreated(var2_3);
                return;
lbl6: // 1 sources:
                if (this.mChannel != null) {
                    this.mChannel.dispose();
                }
                this.mCb.sessionCreated(null);
                return;
            }
            catch (RemoteException var1_2) {
                // empty catch block
            }
        }
    }

}

