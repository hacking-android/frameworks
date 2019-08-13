/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.inputmethodservice.-$
 *  android.inputmethodservice.-$$Lambda
 *  android.inputmethodservice.-$$Lambda$0tnQSRQlZ73hLobz1ZfjUIoiCl0
 *  android.inputmethodservice.-$$Lambda$50K3nJOOPDYkhKRI6jLQ5NjnbLU
 *  android.inputmethodservice.-$$Lambda$BAvs3tw1MzE4gOJqYOA5MCJasPE
 *  android.inputmethodservice.-$$Lambda$GapYa6Lyify6RwP-rgkklzmDV8I
 *  android.inputmethodservice.-$$Lambda$RawqPImrGiEy8dXqjapbiFcFS9w
 *  android.inputmethodservice.-$$Lambda$Xt9K6cDxkSefTfR7zi9ni-dRFZ8
 *  android.inputmethodservice.-$$Lambda$m1uOlwS-mRsg9KSUY6vV9l9ksWc
 *  android.inputmethodservice.-$$Lambda$nzQNVb4Z0e33hB95nNP1BM-A3r4
 *  android.inputmethodservice.-$$Lambda$zVy_pAXuQfncxA_AL_8DWyVpYXc
 */
package android.inputmethodservice;

import android.graphics.Rect;
import android.inputmethodservice.-$;
import android.inputmethodservice.AbstractInputMethodService;
import android.inputmethodservice.MultiClientInputMethodServiceDelegate;
import android.inputmethodservice._$$Lambda$0tnQSRQlZ73hLobz1ZfjUIoiCl0;
import android.inputmethodservice._$$Lambda$50K3nJOOPDYkhKRI6jLQ5NjnbLU;
import android.inputmethodservice._$$Lambda$BAvs3tw1MzE4gOJqYOA5MCJasPE;
import android.inputmethodservice._$$Lambda$GapYa6Lyify6RwP_rgkklzmDV8I;
import android.inputmethodservice._$$Lambda$RawqPImrGiEy8dXqjapbiFcFS9w;
import android.inputmethodservice._$$Lambda$Xt9K6cDxkSefTfR7zi9ni_dRFZ8;
import android.inputmethodservice._$$Lambda$m1uOlwS_mRsg9KSUY6vV9l9ksWc;
import android.inputmethodservice._$$Lambda$nzQNVb4Z0e33hB95nNP1BM_A3r4;
import android.inputmethodservice._$$Lambda$zVy_pAXuQfncxA_AL_8DWyVpYXc;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ResultReceiver;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.InputConnection;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.inputmethod.IMultiClientInputMethodSession;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.view.IInputContext;
import com.android.internal.view.IInputMethodSession;
import com.android.internal.view.InputConnectionWrapper;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

final class MultiClientInputMethodClientCallbackAdaptor {
    static final boolean DEBUG = false;
    static final String TAG = MultiClientInputMethodClientCallbackAdaptor.class.getSimpleName();
    @GuardedBy(value={"mSessionLock"})
    CallbackImpl mCallbackImpl;
    @GuardedBy(value={"mSessionLock"})
    KeyEvent.DispatcherState mDispatcherState;
    private final AtomicBoolean mFinished = new AtomicBoolean(false);
    @GuardedBy(value={"mSessionLock"})
    Handler mHandler;
    @GuardedBy(value={"mSessionLock"})
    InputEventReceiver mInputEventReceiver;
    @GuardedBy(value={"mSessionLock"})
    InputChannel mReadChannel;
    private final Object mSessionLock = new Object();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    MultiClientInputMethodClientCallbackAdaptor(MultiClientInputMethodServiceDelegate.ClientCallback object, Looper looper, KeyEvent.DispatcherState dispatcherState, InputChannel inputChannel) {
        Object object2 = this.mSessionLock;
        synchronized (object2) {
            CallbackImpl callbackImpl;
            this.mCallbackImpl = callbackImpl = new CallbackImpl(this, (MultiClientInputMethodServiceDelegate.ClientCallback)object);
            this.mDispatcherState = dispatcherState;
            this.mHandler = object = new Handler(looper, null, true);
            this.mReadChannel = inputChannel;
            this.mInputEventReceiver = object = new ImeInputEventReceiver(this.mReadChannel, this.mHandler.getLooper(), this.mFinished, this.mDispatcherState, this.mCallbackImpl.mOriginalCallback);
            return;
        }
    }

    private static void reportNotSupported() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    IInputMethodSession.Stub createIInputMethodSession() {
        Object object = this.mSessionLock;
        synchronized (object) {
            return new InputMethodSessionImpl(this.mSessionLock, this.mCallbackImpl, this.mHandler, this.mFinished);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    IMultiClientInputMethodSession.Stub createIMultiClientInputMethodSession() {
        Object object = this.mSessionLock;
        synchronized (object) {
            return new MultiClientInputMethodSessionImpl(this.mSessionLock, this.mCallbackImpl, this.mHandler, this.mFinished);
        }
    }

    private static final class CallbackImpl {
        private final MultiClientInputMethodClientCallbackAdaptor mCallbackAdaptor;
        private boolean mFinished = false;
        private final MultiClientInputMethodServiceDelegate.ClientCallback mOriginalCallback;

        CallbackImpl(MultiClientInputMethodClientCallbackAdaptor multiClientInputMethodClientCallbackAdaptor, MultiClientInputMethodServiceDelegate.ClientCallback clientCallback) {
            this.mCallbackAdaptor = multiClientInputMethodClientCallbackAdaptor;
            this.mOriginalCallback = clientCallback;
        }

        void appPrivateCommand(String string2, Bundle bundle) {
            if (this.mFinished) {
                return;
            }
            this.mOriginalCallback.onAppPrivateCommand(string2, bundle);
        }

        void displayCompletions(CompletionInfo[] arrcompletionInfo) {
            if (this.mFinished) {
                return;
            }
            this.mOriginalCallback.onDisplayCompletions(arrcompletionInfo);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void finishSession() {
            if (this.mFinished) {
                return;
            }
            this.mFinished = true;
            this.mOriginalCallback.onFinishSession();
            Object object = this.mCallbackAdaptor.mSessionLock;
            synchronized (object) {
                this.mCallbackAdaptor.mDispatcherState = null;
                if (this.mCallbackAdaptor.mReadChannel != null) {
                    this.mCallbackAdaptor.mReadChannel.dispose();
                    this.mCallbackAdaptor.mReadChannel = null;
                }
                this.mCallbackAdaptor.mInputEventReceiver = null;
                return;
            }
        }

        void hideSoftInput(int n, ResultReceiver resultReceiver) {
            if (this.mFinished) {
                return;
            }
            this.mOriginalCallback.onHideSoftInput(n, resultReceiver);
        }

        void showSoftInput(int n, ResultReceiver resultReceiver) {
            if (this.mFinished) {
                return;
            }
            this.mOriginalCallback.onShowSoftInput(n, resultReceiver);
        }

        void startInputOrWindowGainedFocus(SomeArgs someArgs) {
            block4 : {
                boolean bl = this.mFinished;
                if (!bl) break block4;
                someArgs.recycle();
                return;
            }
            try {
                InputConnectionWrapper inputConnectionWrapper = (InputConnectionWrapper)someArgs.arg1;
                EditorInfo editorInfo = (EditorInfo)someArgs.arg2;
                int n = someArgs.argi1;
                int n2 = someArgs.argi2;
                int n3 = someArgs.argi3;
                this.mOriginalCallback.onStartInputOrWindowGainedFocus(inputConnectionWrapper, editorInfo, n, n2, n3);
                return;
            }
            finally {
                someArgs.recycle();
            }
        }

        void toggleSoftInput(int n, int n2) {
            if (this.mFinished) {
                return;
            }
            this.mOriginalCallback.onToggleSoftInput(n, n2);
        }

        void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) {
            if (this.mFinished) {
                return;
            }
            this.mOriginalCallback.onUpdateCursorAnchorInfo(cursorAnchorInfo);
        }

        void updateSelection(SomeArgs someArgs) {
            block4 : {
                boolean bl = this.mFinished;
                if (!bl) break block4;
                someArgs.recycle();
                return;
            }
            try {
                this.mOriginalCallback.onUpdateSelection(someArgs.argi1, someArgs.argi2, someArgs.argi3, someArgs.argi4, someArgs.argi5, someArgs.argi6);
                return;
            }
            finally {
                someArgs.recycle();
            }
        }
    }

    private static final class ImeInputEventReceiver
    extends InputEventReceiver {
        private final MultiClientInputMethodServiceDelegate.ClientCallback mClientCallback;
        private final KeyEvent.DispatcherState mDispatcherState;
        private final AtomicBoolean mFinished;
        private final KeyEventCallbackAdaptor mKeyEventCallbackAdaptor;

        ImeInputEventReceiver(InputChannel inputChannel, Looper looper, AtomicBoolean atomicBoolean, KeyEvent.DispatcherState dispatcherState, MultiClientInputMethodServiceDelegate.ClientCallback clientCallback) {
            super(inputChannel, looper);
            this.mFinished = atomicBoolean;
            this.mDispatcherState = dispatcherState;
            this.mClientCallback = clientCallback;
            this.mKeyEventCallbackAdaptor = new KeyEventCallbackAdaptor(clientCallback);
        }

        @Override
        public void onInputEvent(InputEvent inputEvent) {
            boolean bl;
            if (this.mFinished.get()) {
                this.finishInputEvent(inputEvent, false);
                return;
            }
            try {
                MotionEvent motionEvent;
                bl = inputEvent instanceof KeyEvent ? ((KeyEvent)inputEvent).dispatch(this.mKeyEventCallbackAdaptor, this.mDispatcherState, this.mKeyEventCallbackAdaptor) : ((motionEvent = (MotionEvent)inputEvent).isFromSource(4) ? this.mClientCallback.onTrackballEvent(motionEvent) : this.mClientCallback.onGenericMotionEvent(motionEvent));
            }
            catch (Throwable throwable) {
                this.finishInputEvent(inputEvent, false);
                throw throwable;
            }
            this.finishInputEvent(inputEvent, bl);
        }
    }

    private static final class InputMethodSessionImpl
    extends IInputMethodSession.Stub {
        @GuardedBy(value={"mSessionLock"})
        private CallbackImpl mCallbackImpl;
        @GuardedBy(value={"mSessionLock"})
        private Handler mHandler;
        private final AtomicBoolean mSessionFinished;
        private final Object mSessionLock;

        InputMethodSessionImpl(Object object, CallbackImpl callbackImpl, Handler handler, AtomicBoolean atomicBoolean) {
            this.mSessionLock = object;
            this.mCallbackImpl = callbackImpl;
            this.mHandler = handler;
            this.mSessionFinished = atomicBoolean;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void appPrivateCommand(String string2, Bundle bundle) {
            Object object = this.mSessionLock;
            synchronized (object) {
                if (this.mCallbackImpl != null && this.mHandler != null) {
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$nzQNVb4Z0e33hB95nNP1BM_A3r4.INSTANCE, this.mCallbackImpl, string2, bundle));
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
        @Override
        public void displayCompletions(CompletionInfo[] arrcompletionInfo) {
            Object object = this.mSessionLock;
            synchronized (object) {
                if (this.mCallbackImpl != null && this.mHandler != null) {
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$RawqPImrGiEy8dXqjapbiFcFS9w.INSTANCE, this.mCallbackImpl, arrcompletionInfo));
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
        @Override
        public void finishSession() {
            Object object = this.mSessionLock;
            synchronized (object) {
                if (this.mCallbackImpl != null && this.mHandler != null) {
                    this.mSessionFinished.set(true);
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$50K3nJOOPDYkhKRI6jLQ5NjnbLU.INSTANCE, this.mCallbackImpl));
                    this.mCallbackImpl = null;
                    this.mHandler = null;
                    return;
                }
                return;
            }
        }

        @Override
        public final void notifyImeHidden() {
            MultiClientInputMethodClientCallbackAdaptor.reportNotSupported();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void toggleSoftInput(int n, int n2) {
            Object object = this.mSessionLock;
            synchronized (object) {
                if (this.mCallbackImpl != null && this.mHandler != null) {
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$GapYa6Lyify6RwP_rgkklzmDV8I.INSTANCE, this.mCallbackImpl, n, n2));
                    return;
                }
                return;
            }
        }

        @Override
        public void updateCursor(Rect rect) {
            MultiClientInputMethodClientCallbackAdaptor.reportNotSupported();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) {
            Object object = this.mSessionLock;
            synchronized (object) {
                if (this.mCallbackImpl != null && this.mHandler != null) {
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$BAvs3tw1MzE4gOJqYOA5MCJasPE.INSTANCE, this.mCallbackImpl, cursorAnchorInfo));
                    return;
                }
                return;
            }
        }

        @Override
        public void updateExtractedText(int n, ExtractedText extractedText) {
            MultiClientInputMethodClientCallbackAdaptor.reportNotSupported();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void updateSelection(int n, int n2, int n3, int n4, int n5, int n6) {
            Object object = this.mSessionLock;
            synchronized (object) {
                if (this.mCallbackImpl != null && this.mHandler != null) {
                    SomeArgs someArgs = SomeArgs.obtain();
                    someArgs.argi1 = n;
                    someArgs.argi2 = n2;
                    someArgs.argi3 = n3;
                    someArgs.argi4 = n4;
                    someArgs.argi5 = n5;
                    someArgs.argi6 = n6;
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$zVy_pAXuQfncxA_AL_8DWyVpYXc.INSTANCE, this.mCallbackImpl, someArgs));
                    return;
                }
                return;
            }
        }

        @Override
        public void viewClicked(boolean bl) {
            MultiClientInputMethodClientCallbackAdaptor.reportNotSupported();
        }
    }

    private static final class KeyEventCallbackAdaptor
    implements KeyEvent.Callback {
        private final MultiClientInputMethodServiceDelegate.ClientCallback mLocalCallback;

        KeyEventCallbackAdaptor(MultiClientInputMethodServiceDelegate.ClientCallback clientCallback) {
            this.mLocalCallback = clientCallback;
        }

        @Override
        public boolean onKeyDown(int n, KeyEvent keyEvent) {
            return this.mLocalCallback.onKeyDown(n, keyEvent);
        }

        @Override
        public boolean onKeyLongPress(int n, KeyEvent keyEvent) {
            return this.mLocalCallback.onKeyLongPress(n, keyEvent);
        }

        @Override
        public boolean onKeyMultiple(int n, int n2, KeyEvent keyEvent) {
            return this.mLocalCallback.onKeyMultiple(n, keyEvent);
        }

        @Override
        public boolean onKeyUp(int n, KeyEvent keyEvent) {
            return this.mLocalCallback.onKeyUp(n, keyEvent);
        }
    }

    private static final class MultiClientInputMethodSessionImpl
    extends IMultiClientInputMethodSession.Stub {
        @GuardedBy(value={"mSessionLock"})
        private CallbackImpl mCallbackImpl;
        @GuardedBy(value={"mSessionLock"})
        private Handler mHandler;
        private final AtomicBoolean mSessionFinished;
        private final Object mSessionLock;

        MultiClientInputMethodSessionImpl(Object object, CallbackImpl callbackImpl, Handler handler, AtomicBoolean atomicBoolean) {
            this.mSessionLock = object;
            this.mCallbackImpl = callbackImpl;
            this.mHandler = handler;
            this.mSessionFinished = atomicBoolean;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void hideSoftInput(int n, ResultReceiver resultReceiver) {
            Object object = this.mSessionLock;
            synchronized (object) {
                if (this.mCallbackImpl != null && this.mHandler != null) {
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$0tnQSRQlZ73hLobz1ZfjUIoiCl0.INSTANCE, this.mCallbackImpl, n, resultReceiver));
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
        @Override
        public void showSoftInput(int n, ResultReceiver resultReceiver) {
            Object object = this.mSessionLock;
            synchronized (object) {
                if (this.mCallbackImpl != null && this.mHandler != null) {
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$m1uOlwS_mRsg9KSUY6vV9l9ksWc.INSTANCE, this.mCallbackImpl, n, resultReceiver));
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
        @Override
        public void startInputOrWindowGainedFocus(IInputContext object, int n, EditorInfo editorInfo, int n2, int n3, int n4) {
            Object object2 = this.mSessionLock;
            synchronized (object2) {
                if (this.mCallbackImpl != null && this.mHandler != null) {
                    SomeArgs someArgs = SomeArgs.obtain();
                    Object var10_10 = null;
                    WeakReference<Object> weakReference = new WeakReference<Object>(null);
                    object = object == null ? var10_10 : new InputConnectionWrapper(weakReference, (IInputContext)object, n, this.mSessionFinished);
                    someArgs.arg1 = object;
                    someArgs.arg2 = editorInfo;
                    someArgs.argi1 = n2;
                    someArgs.argi2 = n3;
                    someArgs.argi3 = n4;
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$Xt9K6cDxkSefTfR7zi9ni_dRFZ8.INSTANCE, this.mCallbackImpl, someArgs));
                    return;
                }
                return;
            }
        }
    }

}

