/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.voice.-$
 *  android.service.voice.-$$Lambda
 *  android.service.voice.-$$Lambda$lR4OeV3qsxUC-rL-7Xl2vrhTvEo
 */
package android.service.voice;

import android.R;
import android.app.Dialog;
import android.app.DirectAction;
import android.app.Instrumentation;
import android.app.VoiceInteractor;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Region;
import android.inputmethodservice.SoftInputWindow;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.voice.-$;
import android.service.voice.IVoiceInteractionSession;
import android.service.voice._$$Lambda$VoiceInteractionSession$2YI2merL0_kdgL83g93OW541J8w;
import android.service.voice._$$Lambda$VoiceInteractionSession$9GV3ALC6LWOMyg5zazTo6TodsHU;
import android.service.voice._$$Lambda$VoiceInteractionSession$KRmvXWbKzOj6uOiuAkDjhkUvQiw;
import android.service.voice._$$Lambda$VoiceInteractionSession$ONdRuCs_OqsJCBOvPdgOMEsz684;
import android.service.voice._$$Lambda$VoiceInteractionSession$bujvs7MJfXO9xSx9M8NS3hINZ_k;
import android.service.voice._$$Lambda$VoiceInteractionSession$fvrSEzYI3LvOp_mfME5kNVi91bw;
import android.service.voice._$$Lambda$VoiceInteractionSession$sg0qPgWHpOBD2lLJ7BGNEVSsBdo;
import android.service.voice._$$Lambda$lR4OeV3qsxUC_rL_7Xl2vrhTvEo;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import com.android.internal.annotations.Immutable;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.app.IVoiceInteractorCallback;
import com.android.internal.app.IVoiceInteractorRequest;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class VoiceInteractionSession
implements KeyEvent.Callback,
ComponentCallbacks2 {
    static final boolean DEBUG = false;
    static final int MSG_CANCEL = 7;
    static final int MSG_CLOSE_SYSTEM_DIALOGS = 102;
    static final int MSG_DESTROY = 103;
    static final int MSG_HANDLE_ASSIST = 104;
    static final int MSG_HANDLE_SCREENSHOT = 105;
    static final int MSG_HIDE = 107;
    static final int MSG_ON_LOCKSCREEN_SHOWN = 108;
    static final int MSG_SHOW = 106;
    static final int MSG_START_ABORT_VOICE = 4;
    static final int MSG_START_COMMAND = 5;
    static final int MSG_START_COMPLETE_VOICE = 3;
    static final int MSG_START_CONFIRMATION = 1;
    static final int MSG_START_PICK_OPTION = 2;
    static final int MSG_SUPPORTS_COMMANDS = 6;
    static final int MSG_TASK_FINISHED = 101;
    static final int MSG_TASK_STARTED = 100;
    public static final int SHOW_SOURCE_ACTIVITY = 16;
    public static final int SHOW_SOURCE_APPLICATION = 8;
    public static final int SHOW_SOURCE_ASSIST_GESTURE = 4;
    public static final int SHOW_SOURCE_AUTOMOTIVE_SYSTEM_UI = 128;
    public static final int SHOW_SOURCE_NOTIFICATION = 64;
    public static final int SHOW_SOURCE_PUSH_TO_TALK = 32;
    public static final int SHOW_WITH_ASSIST = 1;
    public static final int SHOW_WITH_SCREENSHOT = 2;
    static final String TAG = "VoiceInteractionSession";
    final ArrayMap<IBinder, Request> mActiveRequests = new ArrayMap();
    final MyCallbacks mCallbacks = new MyCallbacks();
    FrameLayout mContentFrame;
    final Context mContext;
    final KeyEvent.DispatcherState mDispatcherState = new KeyEvent.DispatcherState();
    final HandlerCaller mHandlerCaller;
    boolean mInShowWindow;
    LayoutInflater mInflater;
    boolean mInitialized;
    final ViewTreeObserver.OnComputeInternalInsetsListener mInsetsComputer = new ViewTreeObserver.OnComputeInternalInsetsListener(){

        @Override
        public void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
            VoiceInteractionSession voiceInteractionSession = VoiceInteractionSession.this;
            voiceInteractionSession.onComputeInsets(voiceInteractionSession.mTmpInsets);
            internalInsetsInfo.contentInsets.set(VoiceInteractionSession.this.mTmpInsets.contentInsets);
            internalInsetsInfo.visibleInsets.set(VoiceInteractionSession.this.mTmpInsets.contentInsets);
            internalInsetsInfo.touchableRegion.set(VoiceInteractionSession.this.mTmpInsets.touchableRegion);
            internalInsetsInfo.setTouchableInsets(VoiceInteractionSession.this.mTmpInsets.touchableInsets);
        }
    };
    final IVoiceInteractor mInteractor = new IVoiceInteractor.Stub(){

        @Override
        public void notifyDirectActionsChanged(int n, IBinder iBinder) {
            VoiceInteractionSession.this.mHandlerCaller.getHandler().sendMessage(PooledLambda.obtainMessage(_$$Lambda$lR4OeV3qsxUC_rL_7Xl2vrhTvEo.INSTANCE, VoiceInteractionSession.this, new ActivityId(n, iBinder)));
        }

        @Override
        public void setKillCallback(ICancellationSignal iCancellationSignal) {
            VoiceInteractionSession.this.mKillCallback = iCancellationSignal;
        }

        @Override
        public IVoiceInteractorRequest startAbortVoice(String object, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) {
            object = new AbortVoiceRequest((String)object, Binder.getCallingUid(), iVoiceInteractorCallback, VoiceInteractionSession.this, prompt, bundle);
            VoiceInteractionSession.this.addRequest((Request)object);
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(4, object));
            return ((AbortVoiceRequest)object).mInterface;
        }

        @Override
        public IVoiceInteractorRequest startCommand(String object, IVoiceInteractorCallback iVoiceInteractorCallback, String string2, Bundle bundle) {
            object = new CommandRequest((String)object, Binder.getCallingUid(), iVoiceInteractorCallback, VoiceInteractionSession.this, string2, bundle);
            VoiceInteractionSession.this.addRequest((Request)object);
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(5, object));
            return ((CommandRequest)object).mInterface;
        }

        @Override
        public IVoiceInteractorRequest startCompleteVoice(String object, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) {
            object = new CompleteVoiceRequest((String)object, Binder.getCallingUid(), iVoiceInteractorCallback, VoiceInteractionSession.this, prompt, bundle);
            VoiceInteractionSession.this.addRequest((Request)object);
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(3, object));
            return ((CompleteVoiceRequest)object).mInterface;
        }

        @Override
        public IVoiceInteractorRequest startConfirmation(String object, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, Bundle bundle) {
            object = new ConfirmationRequest((String)object, Binder.getCallingUid(), iVoiceInteractorCallback, VoiceInteractionSession.this, prompt, bundle);
            VoiceInteractionSession.this.addRequest((Request)object);
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(1, object));
            return ((ConfirmationRequest)object).mInterface;
        }

        @Override
        public IVoiceInteractorRequest startPickOption(String object, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] arroption, Bundle bundle) {
            object = new PickOptionRequest((String)object, Binder.getCallingUid(), iVoiceInteractorCallback, VoiceInteractionSession.this, prompt, arroption, bundle);
            VoiceInteractionSession.this.addRequest((Request)object);
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(2, object));
            return ((PickOptionRequest)object).mInterface;
        }

        @Override
        public boolean[] supportsCommands(String object, String[] arrobject) {
            object = VoiceInteractionSession.this.mHandlerCaller.obtainMessageIOO(6, 0, arrobject, null);
            if ((object = VoiceInteractionSession.this.mHandlerCaller.sendMessageAndWait((Message)object)) != null) {
                arrobject = (boolean[])((SomeArgs)object).arg1;
                ((SomeArgs)object).recycle();
                return arrobject;
            }
            return new boolean[arrobject.length];
        }
    };
    ICancellationSignal mKillCallback;
    final Map<SafeResultListener, Consumer<Bundle>> mRemoteCallbacks = new ArrayMap<SafeResultListener, Consumer<Bundle>>();
    View mRootView;
    final IVoiceInteractionSession mSession = new IVoiceInteractionSession.Stub(){

        @Override
        public void closeSystemDialogs() {
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessage(102));
        }

        @Override
        public void destroy() {
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessage(103));
        }

        @Override
        public void handleAssist(final int n, final IBinder iBinder, final Bundle bundle, final AssistStructure assistStructure, final AssistContent assistContent, final int n2, final int n3) {
            new Thread("AssistStructure retriever"){

                @Override
                public void run() {
                    AssistStructure assistStructure3 = null;
                    Object object = assistStructure;
                    AssistStructure assistStructure2 = assistStructure3;
                    if (object != null) {
                        try {
                            ((AssistStructure)object).ensureData();
                            assistStructure2 = assistStructure3;
                        }
                        catch (Throwable throwable) {
                            Log.w(VoiceInteractionSession.TAG, "Failure retrieving AssistStructure", throwable);
                        }
                    }
                    object = SomeArgs.obtain();
                    ((SomeArgs)object).argi1 = n;
                    ((SomeArgs)object).arg1 = bundle;
                    assistStructure3 = assistStructure2 == null ? assistStructure : null;
                    ((SomeArgs)object).arg2 = assistStructure3;
                    ((SomeArgs)object).arg3 = assistStructure2;
                    ((SomeArgs)object).arg4 = assistContent;
                    ((SomeArgs)object).arg5 = iBinder;
                    ((SomeArgs)object).argi5 = n2;
                    ((SomeArgs)object).argi6 = n3;
                    VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(104, object));
                }
            }.start();
        }

        @Override
        public void handleScreenshot(Bitmap bitmap) {
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(105, bitmap));
        }

        @Override
        public void hide() {
            VoiceInteractionSession.this.mHandlerCaller.removeMessages(106);
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessage(107));
        }

        @Override
        public void onLockscreenShown() {
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessage(108));
        }

        @Override
        public void show(Bundle bundle, int n, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) {
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageIOO(106, n, bundle, iVoiceInteractionSessionShowCallback));
        }

        @Override
        public void taskFinished(Intent intent, int n) {
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageIO(101, n, intent));
        }

        @Override
        public void taskStarted(Intent intent, int n) {
            VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageIO(100, n, intent));
        }

    };
    IVoiceInteractionManagerService mSystemService;
    int mTheme = 0;
    TypedArray mThemeAttrs;
    final Insets mTmpInsets = new Insets();
    IBinder mToken;
    boolean mUiEnabled = true;
    final WeakReference<VoiceInteractionSession> mWeakRef = new WeakReference<VoiceInteractionSession>(this);
    SoftInputWindow mWindow;
    boolean mWindowAdded;
    boolean mWindowVisible;
    boolean mWindowWasVisible;

    public VoiceInteractionSession(Context context) {
        this(context, new Handler());
    }

    public VoiceInteractionSession(Context context, Handler handler) {
        this.mContext = context;
        this.mHandlerCaller = new HandlerCaller(context, handler.getLooper(), this.mCallbacks, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SafeResultListener createSafeResultListener(Consumer<Bundle> consumer) {
        synchronized (this) {
            SafeResultListener safeResultListener = new SafeResultListener(consumer, this);
            this.mRemoteCallbacks.put(safeResultListener, consumer);
            return safeResultListener;
        }
    }

    private void doOnCreate() {
        int n = this.mTheme;
        if (n == 0) {
            n = 16974848;
        }
        this.mTheme = n;
    }

    static /* synthetic */ void lambda$performDirectAction$3(CancellationSignal cancellationSignal, Bundle object) {
        if (object != null && (object = ((Bundle)object).getBinder("key_cancellation_signal")) != null) {
            cancellationSignal.setRemote(ICancellationSignal.Stub.asInterface((IBinder)object));
        }
    }

    static /* synthetic */ void lambda$performDirectAction$4(Consumer consumer, Bundle bundle) {
        consumer.accept(bundle);
    }

    static /* synthetic */ void lambda$performDirectAction$5(Consumer consumer) {
        consumer.accept(Bundle.EMPTY);
    }

    static /* synthetic */ void lambda$performDirectAction$6(Executor executor, Consumer consumer, Bundle bundle) {
        if (bundle != null) {
            executor.execute(new _$$Lambda$VoiceInteractionSession$sg0qPgWHpOBD2lLJ7BGNEVSsBdo(consumer, bundle));
        } else {
            executor.execute(new _$$Lambda$VoiceInteractionSession$bujvs7MJfXO9xSx9M8NS3hINZ_k(consumer));
        }
    }

    static /* synthetic */ void lambda$requestDirectActions$0(CancellationSignal cancellationSignal, Bundle object) {
        if (object != null && (object = ((Bundle)object).getBinder("key_cancellation_signal")) != null) {
            cancellationSignal.setRemote(ICancellationSignal.Stub.asInterface((IBinder)object));
        }
    }

    static /* synthetic */ void lambda$requestDirectActions$1(Consumer consumer, List list) {
        consumer.accept(list);
    }

    static /* synthetic */ void lambda$requestDirectActions$2(Executor executor, Consumer consumer, Bundle list) {
        if (list == null) {
            list = Collections.emptyList();
        } else if ((list = (ParceledListSlice)((Bundle)((Object)list)).getParcelable("actions_list")) != null) {
            if ((list = ((ParceledListSlice)((Object)list)).getList()) == null) {
                list = Collections.emptyList();
            }
        } else {
            list = Collections.emptyList();
        }
        executor.execute(new _$$Lambda$VoiceInteractionSession$fvrSEzYI3LvOp_mfME5kNVi91bw(consumer, list));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Consumer<Bundle> removeSafeResultListener(SafeResultListener object) {
        synchronized (this) {
            return this.mRemoteCallbacks.remove(object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void addRequest(Request request) {
        synchronized (this) {
            this.mActiveRequests.put(request.mInterface.asBinder(), request);
            return;
        }
    }

    public void closeSystemDialogs() {
        IBinder iBinder = this.mToken;
        if (iBinder != null) {
            try {
                this.mSystemService.closeSystemDialogs(iBinder);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        throw new IllegalStateException("Can't call before onCreate()");
    }

    void doCreate(IVoiceInteractionManagerService iVoiceInteractionManagerService, IBinder iBinder) {
        this.mSystemService = iVoiceInteractionManagerService;
        this.mToken = iBinder;
        this.onCreate();
    }

    void doDestroy() {
        this.onDestroy();
        ICancellationSignal iCancellationSignal = this.mKillCallback;
        if (iCancellationSignal != null) {
            try {
                iCancellationSignal.cancel();
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            this.mKillCallback = null;
        }
        if (this.mInitialized) {
            this.mRootView.getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mInsetsComputer);
            if (this.mWindowAdded) {
                this.mWindow.dismiss();
                this.mWindowAdded = false;
            }
            this.mInitialized = false;
        }
    }

    void doHide() {
        if (this.mWindowVisible) {
            this.ensureWindowHidden();
            this.mWindowVisible = false;
            this.onHide();
        }
    }

    void doOnHandleAssist(int n, IBinder iBinder, Bundle bundle, AssistStructure assistStructure, Throwable throwable, AssistContent assistContent, int n2, int n3) {
        if (throwable != null) {
            this.onAssistStructureFailure(throwable);
        }
        this.onHandleAssist(new AssistState(new ActivityId(n, iBinder), bundle, assistStructure, assistContent, n2, n3));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void doShow(Bundle object, int n, final IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) {
        if (this.mInShowWindow) {
            Log.w(TAG, "Re-entrance in to showWindow");
            return;
        }
        try {
            this.mInShowWindow = true;
            this.onPrepareShow((Bundle)object, n);
            if (!this.mWindowVisible) {
                this.ensureWindowAdded();
            }
            this.onShow((Bundle)object, n);
            if (!this.mWindowVisible) {
                this.mWindowVisible = true;
                if (this.mUiEnabled) {
                    this.mWindow.show();
                }
            }
            if (iVoiceInteractionSessionShowCallback == null) return;
            if (this.mUiEnabled) {
                this.mRootView.invalidate();
                object = this.mRootView.getViewTreeObserver();
                ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener(){

                    @Override
                    public boolean onPreDraw() {
                        VoiceInteractionSession.this.mRootView.getViewTreeObserver().removeOnPreDrawListener(this);
                        try {
                            iVoiceInteractionSessionShowCallback.onShown();
                        }
                        catch (RemoteException remoteException) {
                            Log.w(VoiceInteractionSession.TAG, "Error calling onShown", remoteException);
                        }
                        return true;
                    }
                };
                ((ViewTreeObserver)object).addOnPreDrawListener(onPreDrawListener);
                return;
            }
            try {
                iVoiceInteractionSessionShowCallback.onShown();
                return;
            }
            catch (RemoteException remoteException) {
                Log.w(TAG, "Error calling onShown", remoteException);
                return;
            }
        }
        finally {
            this.mWindowWasVisible = true;
            this.mInShowWindow = false;
        }
    }

    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.print(string2);
        printWriter.print("mToken=");
        printWriter.println(this.mToken);
        printWriter.print(string2);
        printWriter.print("mTheme=#");
        printWriter.println(Integer.toHexString(this.mTheme));
        printWriter.print(string2);
        printWriter.print("mUiEnabled=");
        printWriter.println(this.mUiEnabled);
        printWriter.print(" mInitialized=");
        printWriter.println(this.mInitialized);
        printWriter.print(string2);
        printWriter.print("mWindowAdded=");
        printWriter.print(this.mWindowAdded);
        printWriter.print(" mWindowVisible=");
        printWriter.println(this.mWindowVisible);
        printWriter.print(string2);
        printWriter.print("mWindowWasVisible=");
        printWriter.print(this.mWindowWasVisible);
        printWriter.print(" mInShowWindow=");
        printWriter.println(this.mInShowWindow);
        if (this.mActiveRequests.size() > 0) {
            printWriter.print(string2);
            printWriter.println("Active requests:");
            CharSequence charSequence = new StringBuilder();
            charSequence.append(string2);
            charSequence.append("    ");
            charSequence = charSequence.toString();
            for (int i = 0; i < this.mActiveRequests.size(); ++i) {
                Request request = this.mActiveRequests.valueAt(i);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(request);
                request.dump((String)charSequence, fileDescriptor, printWriter, arrstring);
            }
        }
    }

    void ensureWindowAdded() {
        if (this.mUiEnabled && !this.mWindowAdded) {
            this.mWindowAdded = true;
            this.ensureWindowCreated();
            View view = this.onCreateContentView();
            if (view != null) {
                this.setContentView(view);
            }
        }
    }

    void ensureWindowCreated() {
        if (this.mInitialized) {
            return;
        }
        if (this.mUiEnabled) {
            this.mInitialized = true;
            this.mInflater = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
            this.mWindow = new SoftInputWindow(this.mContext, TAG, this.mTheme, this.mCallbacks, this, this.mDispatcherState, 2031, 80, true);
            this.mWindow.getWindow().addFlags(16843008);
            this.mThemeAttrs = this.mContext.obtainStyledAttributes(R.styleable.VoiceInteractionSession);
            this.mRootView = this.mInflater.inflate(17367333, null);
            this.mRootView.setSystemUiVisibility(1792);
            this.mWindow.setContentView(this.mRootView);
            this.mRootView.getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsComputer);
            this.mContentFrame = (FrameLayout)this.mRootView.findViewById(16908290);
            this.mWindow.getWindow().setLayout(-1, -1);
            this.mWindow.setToken(this.mToken);
            return;
        }
        throw new IllegalStateException("setUiEnabled is false");
    }

    void ensureWindowHidden() {
        SoftInputWindow softInputWindow = this.mWindow;
        if (softInputWindow != null) {
            softInputWindow.hide();
        }
    }

    public void finish() {
        IBinder iBinder = this.mToken;
        if (iBinder != null) {
            try {
                this.mSystemService.finish(iBinder);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        throw new IllegalStateException("Can't call before onCreate()");
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getDisabledShowContext() {
        try {
            int n = this.mSystemService.getDisabledShowContext();
            return n;
        }
        catch (RemoteException remoteException) {
            return 0;
        }
    }

    public LayoutInflater getLayoutInflater() {
        this.ensureWindowCreated();
        return this.mInflater;
    }

    public int getUserDisabledShowContext() {
        try {
            int n = this.mSystemService.getUserDisabledShowContext();
            return n;
        }
        catch (RemoteException remoteException) {
            return 0;
        }
    }

    public Dialog getWindow() {
        this.ensureWindowCreated();
        return this.mWindow;
    }

    public void hide() {
        IBinder iBinder = this.mToken;
        if (iBinder != null) {
            try {
                this.mSystemService.hideSessionFromSession(iBinder);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        throw new IllegalStateException("Can't call before onCreate()");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean isRequestActive(IBinder iBinder) {
        synchronized (this) {
            return this.mActiveRequests.containsKey(iBinder);
        }
    }

    public void onAssistStructureFailure(Throwable throwable) {
    }

    public void onBackPressed() {
        this.hide();
    }

    public void onCancelRequest(Request request) {
    }

    public void onCloseSystemDialogs() {
        this.hide();
    }

    public void onComputeInsets(Insets insets) {
        insets.contentInsets.left = 0;
        insets.contentInsets.bottom = 0;
        insets.contentInsets.right = 0;
        View view = this.getWindow().getWindow().getDecorView();
        insets.contentInsets.top = view.getHeight();
        insets.touchableInsets = 0;
        insets.touchableRegion.setEmpty();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onCreate() {
        this.doOnCreate();
    }

    public View onCreateContentView() {
        return null;
    }

    public void onDestroy() {
    }

    public void onDirectActionsInvalidated(ActivityId activityId) {
    }

    public boolean[] onGetSupportedCommands(String[] arrstring) {
        return new boolean[arrstring.length];
    }

    @Deprecated
    public void onHandleAssist(Bundle bundle, AssistStructure assistStructure, AssistContent assistContent) {
    }

    public void onHandleAssist(AssistState assistState) {
        if (assistState.getIndex() == 0) {
            this.onHandleAssist(assistState.getAssistData(), assistState.getAssistStructure(), assistState.getAssistContent());
        } else {
            this.onHandleAssistSecondary(assistState.getAssistData(), assistState.getAssistStructure(), assistState.getAssistContent(), assistState.getIndex(), assistState.getCount());
        }
    }

    @Deprecated
    public void onHandleAssistSecondary(Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, int n, int n2) {
    }

    public void onHandleScreenshot(Bitmap bitmap) {
    }

    public void onHide() {
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

    public void onLockscreenShown() {
        this.hide();
    }

    @Override
    public void onLowMemory() {
    }

    public void onPrepareShow(Bundle bundle, int n) {
    }

    public void onRequestAbortVoice(AbortVoiceRequest abortVoiceRequest) {
    }

    public void onRequestCommand(CommandRequest commandRequest) {
    }

    public void onRequestCompleteVoice(CompleteVoiceRequest completeVoiceRequest) {
    }

    public void onRequestConfirmation(ConfirmationRequest confirmationRequest) {
    }

    public void onRequestPickOption(PickOptionRequest pickOptionRequest) {
    }

    public void onShow(Bundle bundle, int n) {
    }

    public void onTaskFinished(Intent intent, int n) {
        this.hide();
    }

    public void onTaskStarted(Intent intent, int n) {
    }

    @Override
    public void onTrimMemory(int n) {
    }

    public final void performDirectAction(DirectAction directAction, Bundle bundle, CancellationSignal object, Executor object2, Consumer<Bundle> consumer) {
        if (this.mToken != null) {
            Preconditions.checkNotNull(object2);
            Preconditions.checkNotNull(consumer);
            if (object != null) {
                ((CancellationSignal)object).throwIfCanceled();
            }
            object = object != null ? new RemoteCallback(this.createSafeResultListener(new _$$Lambda$VoiceInteractionSession$2YI2merL0_kdgL83g93OW541J8w((CancellationSignal)object))) : null;
            object2 = new RemoteCallback(this.createSafeResultListener(new _$$Lambda$VoiceInteractionSession$9GV3ALC6LWOMyg5zazTo6TodsHU((Executor)object2, consumer)));
            try {
                this.mSystemService.performDirectAction(this.mToken, directAction.getId(), bundle, directAction.getTaskId(), directAction.getActivityId(), (RemoteCallback)object, (RemoteCallback)object2);
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowFromSystemServer();
            }
            return;
        }
        throw new IllegalStateException("Can't call before onCreate()");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Request removeRequest(IBinder object) {
        synchronized (this) {
            return this.mActiveRequests.remove(object);
        }
    }

    public final void requestDirectActions(ActivityId object, CancellationSignal object2, Executor executor, Consumer<List<DirectAction>> consumer) {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(consumer);
        if (this.mToken != null) {
            if (object2 != null) {
                ((CancellationSignal)object2).throwIfCanceled();
            }
            object2 = object2 != null ? new RemoteCallback(new _$$Lambda$VoiceInteractionSession$KRmvXWbKzOj6uOiuAkDjhkUvQiw((CancellationSignal)object2)) : null;
            try {
                IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mSystemService;
                IBinder iBinder = this.mToken;
                int n = ((ActivityId)object).getTaskId();
                IBinder iBinder2 = ((ActivityId)object).getAssistToken();
                object = new _$$Lambda$VoiceInteractionSession$ONdRuCs_OqsJCBOvPdgOMEsz684(executor, consumer);
                RemoteCallback remoteCallback = new RemoteCallback(this.createSafeResultListener((Consumer<Bundle>)object));
                iVoiceInteractionManagerService.requestDirectActions(iBinder, n, iBinder2, (RemoteCallback)object2, remoteCallback);
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowFromSystemServer();
            }
            return;
        }
        throw new IllegalStateException("Can't call before onCreate()");
    }

    public void setContentView(View view) {
        this.ensureWindowCreated();
        this.mContentFrame.removeAllViews();
        this.mContentFrame.addView(view, new FrameLayout.LayoutParams(-1, -1));
        this.mContentFrame.requestApplyInsets();
    }

    public void setDisabledShowContext(int n) {
        try {
            this.mSystemService.setDisabledShowContext(n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void setKeepAwake(boolean bl) {
        IBinder iBinder = this.mToken;
        if (iBinder != null) {
            try {
                this.mSystemService.setKeepAwake(iBinder, bl);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        throw new IllegalStateException("Can't call before onCreate()");
    }

    public void setTheme(int n) {
        if (this.mWindow == null) {
            this.mTheme = n;
            return;
        }
        throw new IllegalStateException("Must be called before onCreate()");
    }

    public void setUiEnabled(boolean bl) {
        if (this.mUiEnabled != bl) {
            this.mUiEnabled = bl;
            if (this.mWindowVisible) {
                if (bl) {
                    this.ensureWindowAdded();
                    this.mWindow.show();
                } else {
                    this.ensureWindowHidden();
                }
            }
        }
    }

    public void show(Bundle bundle, int n) {
        IBinder iBinder = this.mToken;
        if (iBinder != null) {
            try {
                this.mSystemService.showSessionFromSession(iBinder, bundle, n);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        throw new IllegalStateException("Can't call before onCreate()");
    }

    public void startAssistantActivity(Intent intent) {
        if (this.mToken != null) {
            try {
                intent.migrateExtraStreamToClipData();
                intent.prepareToLeaveProcess(this.mContext);
                Instrumentation.checkStartActivityResult(this.mSystemService.startAssistantActivity(this.mToken, intent, intent.resolveType(this.mContext.getContentResolver())), intent);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        throw new IllegalStateException("Can't call before onCreate()");
    }

    public void startVoiceActivity(Intent intent) {
        if (this.mToken != null) {
            try {
                intent.migrateExtraStreamToClipData();
                intent.prepareToLeaveProcess(this.mContext);
                Instrumentation.checkStartActivityResult(this.mSystemService.startVoiceActivity(this.mToken, intent, intent.resolveType(this.mContext.getContentResolver())), intent);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        throw new IllegalStateException("Can't call before onCreate()");
    }

    public static final class AbortVoiceRequest
    extends Request {
        final VoiceInteractor.Prompt mPrompt;

        AbortVoiceRequest(String string2, int n, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractionSession voiceInteractionSession, VoiceInteractor.Prompt prompt, Bundle bundle) {
            super(string2, n, iVoiceInteractorCallback, voiceInteractionSession, bundle);
            this.mPrompt = prompt;
        }

        @Override
        void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            super.dump(string2, fileDescriptor, printWriter, arrstring);
            printWriter.print(string2);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
        }

        @Deprecated
        public CharSequence getMessage() {
            Object object = this.mPrompt;
            object = object != null ? ((VoiceInteractor.Prompt)object).getVoicePromptAt(0) : null;
            return object;
        }

        public VoiceInteractor.Prompt getVoicePrompt() {
            return this.mPrompt;
        }

        public void sendAbortResult(Bundle bundle) {
            try {
                this.finishRequest();
                this.mCallback.deliverAbortVoiceResult(this.mInterface, bundle);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public static class ActivityId {
        private final IBinder mAssistToken;
        private final int mTaskId;

        ActivityId(int n, IBinder iBinder) {
            this.mTaskId = n;
            this.mAssistToken = iBinder;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                ActivityId activityId = (ActivityId)object;
                if (this.mTaskId != activityId.mTaskId) {
                    return false;
                }
                object = this.mAssistToken;
                if (object != null) {
                    bl = object.equals(activityId.mAssistToken);
                } else if (activityId.mAssistToken != null) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        IBinder getAssistToken() {
            return this.mAssistToken;
        }

        int getTaskId() {
            return this.mTaskId;
        }

        public int hashCode() {
            int n = this.mTaskId;
            IBinder iBinder = this.mAssistToken;
            int n2 = iBinder != null ? iBinder.hashCode() : 0;
            return n * 31 + n2;
        }
    }

    @Immutable
    public static final class AssistState {
        private final ActivityId mActivityId;
        private final AssistContent mContent;
        private final int mCount;
        private final Bundle mData;
        private final int mIndex;
        private final AssistStructure mStructure;

        AssistState(ActivityId activityId, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, int n, int n2) {
            this.mActivityId = activityId;
            this.mIndex = n;
            this.mCount = n2;
            this.mData = bundle;
            this.mStructure = assistStructure;
            this.mContent = assistContent;
        }

        public ActivityId getActivityId() {
            return this.mActivityId;
        }

        public AssistContent getAssistContent() {
            return this.mContent;
        }

        public Bundle getAssistData() {
            return this.mData;
        }

        public AssistStructure getAssistStructure() {
            return this.mStructure;
        }

        public int getCount() {
            return this.mCount;
        }

        public int getIndex() {
            return this.mIndex;
        }

        public boolean isFocused() {
            boolean bl = this.mIndex == 0;
            return bl;
        }
    }

    public static final class CommandRequest
    extends Request {
        final String mCommand;

        CommandRequest(String string2, int n, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractionSession voiceInteractionSession, String string3, Bundle bundle) {
            super(string2, n, iVoiceInteractorCallback, voiceInteractionSession, bundle);
            this.mCommand = string3;
        }

        @Override
        void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            super.dump(string2, fileDescriptor, printWriter, arrstring);
            printWriter.print(string2);
            printWriter.print("mCommand=");
            printWriter.println(this.mCommand);
        }

        public String getCommand() {
            return this.mCommand;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        void sendCommandResult(boolean var1_1, Bundle var2_2) {
            if (!var1_1) ** GOTO lbl4
            try {
                this.finishRequest();
lbl4: // 2 sources:
                this.mCallback.deliverCommandResult(this.mInterface, var1_1, var2_2);
                return;
            }
            catch (RemoteException var2_3) {
                // empty catch block
            }
        }

        public void sendIntermediateResult(Bundle bundle) {
            this.sendCommandResult(false, bundle);
        }

        public void sendResult(Bundle bundle) {
            this.sendCommandResult(true, bundle);
        }
    }

    public static final class CompleteVoiceRequest
    extends Request {
        final VoiceInteractor.Prompt mPrompt;

        CompleteVoiceRequest(String string2, int n, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractionSession voiceInteractionSession, VoiceInteractor.Prompt prompt, Bundle bundle) {
            super(string2, n, iVoiceInteractorCallback, voiceInteractionSession, bundle);
            this.mPrompt = prompt;
        }

        @Override
        void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            super.dump(string2, fileDescriptor, printWriter, arrstring);
            printWriter.print(string2);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
        }

        @Deprecated
        public CharSequence getMessage() {
            Object object = this.mPrompt;
            object = object != null ? ((VoiceInteractor.Prompt)object).getVoicePromptAt(0) : null;
            return object;
        }

        public VoiceInteractor.Prompt getVoicePrompt() {
            return this.mPrompt;
        }

        public void sendCompleteResult(Bundle bundle) {
            try {
                this.finishRequest();
                this.mCallback.deliverCompleteVoiceResult(this.mInterface, bundle);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public static final class ConfirmationRequest
    extends Request {
        final VoiceInteractor.Prompt mPrompt;

        ConfirmationRequest(String string2, int n, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractionSession voiceInteractionSession, VoiceInteractor.Prompt prompt, Bundle bundle) {
            super(string2, n, iVoiceInteractorCallback, voiceInteractionSession, bundle);
            this.mPrompt = prompt;
        }

        @Override
        void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            super.dump(string2, fileDescriptor, printWriter, arrstring);
            printWriter.print(string2);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
        }

        @Deprecated
        public CharSequence getPrompt() {
            Object object = this.mPrompt;
            object = object != null ? ((VoiceInteractor.Prompt)object).getVoicePromptAt(0) : null;
            return object;
        }

        public VoiceInteractor.Prompt getVoicePrompt() {
            return this.mPrompt;
        }

        public void sendConfirmationResult(boolean bl, Bundle bundle) {
            try {
                this.finishRequest();
                this.mCallback.deliverConfirmationResult(this.mInterface, bl, bundle);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public static final class Insets {
        public static final int TOUCHABLE_INSETS_CONTENT = 1;
        public static final int TOUCHABLE_INSETS_FRAME = 0;
        public static final int TOUCHABLE_INSETS_REGION = 3;
        public final Rect contentInsets = new Rect();
        public int touchableInsets;
        public final Region touchableRegion = new Region();
    }

    class MyCallbacks
    implements HandlerCaller.Callback,
    SoftInputWindow.Callback {
        MyCallbacks() {
        }

        @Override
        public void executeMessage(Message object) {
            SomeArgs someArgs = null;
            int n = ((Message)object).what;
            block0 : switch (n) {
                default: {
                    switch (n) {
                        default: {
                            object = someArgs;
                            break block0;
                        }
                        case 108: {
                            VoiceInteractionSession.this.onLockscreenShown();
                            object = someArgs;
                            break block0;
                        }
                        case 107: {
                            VoiceInteractionSession.this.doHide();
                            object = someArgs;
                            break block0;
                        }
                        case 106: {
                            someArgs = (SomeArgs)((Message)object).obj;
                            VoiceInteractionSession.this.doShow((Bundle)someArgs.arg1, ((Message)object).arg1, (IVoiceInteractionSessionShowCallback)someArgs.arg2);
                            object = someArgs;
                            break block0;
                        }
                        case 105: {
                            VoiceInteractionSession.this.onHandleScreenshot((Bitmap)((Message)object).obj);
                            object = someArgs;
                            break block0;
                        }
                        case 104: {
                            object = (SomeArgs)((Message)object).obj;
                            VoiceInteractionSession.this.doOnHandleAssist(((SomeArgs)object).argi1, (IBinder)((SomeArgs)object).arg5, (Bundle)((SomeArgs)object).arg1, (AssistStructure)((SomeArgs)object).arg2, (Throwable)((SomeArgs)object).arg3, (AssistContent)((SomeArgs)object).arg4, ((SomeArgs)object).argi5, ((SomeArgs)object).argi6);
                            break block0;
                        }
                        case 103: {
                            VoiceInteractionSession.this.doDestroy();
                            object = someArgs;
                            break block0;
                        }
                        case 102: {
                            VoiceInteractionSession.this.onCloseSystemDialogs();
                            object = someArgs;
                            break block0;
                        }
                        case 101: {
                            VoiceInteractionSession.this.onTaskFinished((Intent)((Message)object).obj, ((Message)object).arg1);
                            object = someArgs;
                            break block0;
                        }
                        case 100: 
                    }
                    VoiceInteractionSession.this.onTaskStarted((Intent)((Message)object).obj, ((Message)object).arg1);
                    object = someArgs;
                    break;
                }
                case 7: {
                    VoiceInteractionSession.this.onCancelRequest((Request)((Message)object).obj);
                    object = someArgs;
                    break;
                }
                case 6: {
                    object = (SomeArgs)((Message)object).obj;
                    ((SomeArgs)object).arg1 = VoiceInteractionSession.this.onGetSupportedCommands((String[])((SomeArgs)object).arg1);
                    ((SomeArgs)object).complete();
                    object = null;
                    break;
                }
                case 5: {
                    VoiceInteractionSession.this.onRequestCommand((CommandRequest)((Message)object).obj);
                    object = someArgs;
                    break;
                }
                case 4: {
                    VoiceInteractionSession.this.onRequestAbortVoice((AbortVoiceRequest)((Message)object).obj);
                    object = someArgs;
                    break;
                }
                case 3: {
                    VoiceInteractionSession.this.onRequestCompleteVoice((CompleteVoiceRequest)((Message)object).obj);
                    object = someArgs;
                    break;
                }
                case 2: {
                    VoiceInteractionSession.this.onRequestPickOption((PickOptionRequest)((Message)object).obj);
                    object = someArgs;
                    break;
                }
                case 1: {
                    VoiceInteractionSession.this.onRequestConfirmation((ConfirmationRequest)((Message)object).obj);
                    object = someArgs;
                }
            }
            if (object != null) {
                ((SomeArgs)object).recycle();
            }
        }

        @Override
        public void onBackPressed() {
            VoiceInteractionSession.this.onBackPressed();
        }
    }

    public static final class PickOptionRequest
    extends Request {
        final VoiceInteractor.PickOptionRequest.Option[] mOptions;
        final VoiceInteractor.Prompt mPrompt;

        PickOptionRequest(String string2, int n, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractionSession voiceInteractionSession, VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] arroption, Bundle bundle) {
            super(string2, n, iVoiceInteractorCallback, voiceInteractionSession, bundle);
            this.mPrompt = prompt;
            this.mOptions = arroption;
        }

        @Override
        void dump(String string2, FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
            super.dump(string2, (FileDescriptor)object, printWriter, arrstring);
            printWriter.print(string2);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mOptions != null) {
                printWriter.print(string2);
                printWriter.println("Options:");
                for (int i = 0; i < ((VoiceInteractor.PickOptionRequest.Option[])(object = this.mOptions)).length; ++i) {
                    object = object[i];
                    printWriter.print(string2);
                    printWriter.print("  #");
                    printWriter.print(i);
                    printWriter.println(":");
                    printWriter.print(string2);
                    printWriter.print("    mLabel=");
                    printWriter.println(((VoiceInteractor.PickOptionRequest.Option)object).getLabel());
                    printWriter.print(string2);
                    printWriter.print("    mIndex=");
                    printWriter.println(((VoiceInteractor.PickOptionRequest.Option)object).getIndex());
                    if (((VoiceInteractor.PickOptionRequest.Option)object).countSynonyms() > 0) {
                        printWriter.print(string2);
                        printWriter.println("    Synonyms:");
                        for (int j = 0; j < ((VoiceInteractor.PickOptionRequest.Option)object).countSynonyms(); ++j) {
                            printWriter.print(string2);
                            printWriter.print("      #");
                            printWriter.print(j);
                            printWriter.print(": ");
                            printWriter.println(((VoiceInteractor.PickOptionRequest.Option)object).getSynonymAt(j));
                        }
                    }
                    if (((VoiceInteractor.PickOptionRequest.Option)object).getExtras() == null) continue;
                    printWriter.print(string2);
                    printWriter.print("    mExtras=");
                    printWriter.println(((VoiceInteractor.PickOptionRequest.Option)object).getExtras());
                }
            }
        }

        public VoiceInteractor.PickOptionRequest.Option[] getOptions() {
            return this.mOptions;
        }

        @Deprecated
        public CharSequence getPrompt() {
            Object object = this.mPrompt;
            object = object != null ? ((VoiceInteractor.Prompt)object).getVoicePromptAt(0) : null;
            return object;
        }

        public VoiceInteractor.Prompt getVoicePrompt() {
            return this.mPrompt;
        }

        public void sendIntermediatePickOptionResult(VoiceInteractor.PickOptionRequest.Option[] arroption, Bundle bundle) {
            this.sendPickOptionResult(false, arroption, bundle);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        void sendPickOptionResult(boolean var1_1, VoiceInteractor.PickOptionRequest.Option[] var2_2, Bundle var3_4) {
            if (!var1_1) ** GOTO lbl4
            try {
                this.finishRequest();
lbl4: // 2 sources:
                this.mCallback.deliverPickOptionResult(this.mInterface, var1_1, var2_2, var3_4);
                return;
            }
            catch (RemoteException var2_3) {
                // empty catch block
            }
        }

        public void sendPickOptionResult(VoiceInteractor.PickOptionRequest.Option[] arroption, Bundle bundle) {
            this.sendPickOptionResult(true, arroption, bundle);
        }
    }

    public static class Request {
        final IVoiceInteractorCallback mCallback;
        final String mCallingPackage;
        final int mCallingUid;
        final Bundle mExtras;
        final IVoiceInteractorRequest mInterface = new IVoiceInteractorRequest.Stub(){

            @Override
            public void cancel() throws RemoteException {
                VoiceInteractionSession voiceInteractionSession = (VoiceInteractionSession)Request.this.mSession.get();
                if (voiceInteractionSession != null) {
                    voiceInteractionSession.mHandlerCaller.sendMessage(voiceInteractionSession.mHandlerCaller.obtainMessageO(7, Request.this));
                }
            }
        };
        final WeakReference<VoiceInteractionSession> mSession;

        Request(String string2, int n, IVoiceInteractorCallback iVoiceInteractorCallback, VoiceInteractionSession voiceInteractionSession, Bundle bundle) {
            this.mCallingPackage = string2;
            this.mCallingUid = n;
            this.mCallback = iVoiceInteractorCallback;
            this.mSession = voiceInteractionSession.mWeakRef;
            this.mExtras = bundle;
        }

        public void cancel() {
            try {
                this.finishRequest();
                this.mCallback.deliverCancel(this.mInterface);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            printWriter.print(string2);
            printWriter.print("mInterface=");
            printWriter.println(this.mInterface.asBinder());
            printWriter.print(string2);
            printWriter.print("mCallingPackage=");
            printWriter.print(this.mCallingPackage);
            printWriter.print(" mCallingUid=");
            UserHandle.formatUid(printWriter, this.mCallingUid);
            printWriter.println();
            printWriter.print(string2);
            printWriter.print("mCallback=");
            printWriter.println(this.mCallback.asBinder());
            if (this.mExtras != null) {
                printWriter.print(string2);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        void finishRequest() {
            Object object = (VoiceInteractionSession)this.mSession.get();
            if (object != null) {
                if ((object = ((VoiceInteractionSession)object).removeRequest(this.mInterface.asBinder())) != null) {
                    if (object == this) {
                        return;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Current active request ");
                    stringBuilder.append(object);
                    stringBuilder.append(" not same as calling request ");
                    stringBuilder.append(this);
                    throw new IllegalStateException(stringBuilder.toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Request not active: ");
                ((StringBuilder)object).append(this);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            throw new IllegalStateException("VoiceInteractionSession has been destroyed");
        }

        public String getCallingPackage() {
            return this.mCallingPackage;
        }

        public int getCallingUid() {
            return this.mCallingUid;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public boolean isActive() {
            VoiceInteractionSession voiceInteractionSession = (VoiceInteractionSession)this.mSession.get();
            if (voiceInteractionSession == null) {
                return false;
            }
            return voiceInteractionSession.isRequestActive(this.mInterface.asBinder());
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            DebugUtils.buildShortClassTag(this, stringBuilder);
            stringBuilder.append(" ");
            stringBuilder.append(this.mInterface.asBinder());
            stringBuilder.append(" pkg=");
            stringBuilder.append(this.mCallingPackage);
            stringBuilder.append(" uid=");
            UserHandle.formatUid(stringBuilder, this.mCallingUid);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

    }

    private static class SafeResultListener
    implements RemoteCallback.OnResultListener {
        private final WeakReference<VoiceInteractionSession> mWeakSession;

        SafeResultListener(Consumer<Bundle> consumer, VoiceInteractionSession voiceInteractionSession) {
            this.mWeakSession = new WeakReference<VoiceInteractionSession>(voiceInteractionSession);
        }

        @Override
        public void onResult(Bundle bundle) {
            Object object = (VoiceInteractionSession)this.mWeakSession.get();
            if (object != null && (object = ((VoiceInteractionSession)object).removeSafeResultListener(this)) != null) {
                object.accept(bundle);
            }
        }
    }

}

