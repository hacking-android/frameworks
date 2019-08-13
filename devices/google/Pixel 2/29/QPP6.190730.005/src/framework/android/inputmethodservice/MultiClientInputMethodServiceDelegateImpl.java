/*
 * Decompiled with CFR 0.145.
 */
package android.inputmethodservice;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor;
import android.inputmethodservice.MultiClientInputMethodServiceDelegate;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.InputChannel;
import android.view.KeyEvent;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.inputmethod.IMultiClientInputMethod;
import com.android.internal.inputmethod.IMultiClientInputMethodPrivilegedOperations;
import com.android.internal.inputmethod.IMultiClientInputMethodSession;
import com.android.internal.inputmethod.MultiClientInputMethodPrivilegedOperations;
import com.android.internal.view.IInputMethodSession;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

final class MultiClientInputMethodServiceDelegateImpl {
    private static final String TAG = "MultiClientInputMethodServiceDelegateImpl";
    private final Context mContext;
    @GuardedBy(value={"mLock"})
    private int mInitializationPhase = 1;
    private final Object mLock = new Object();
    private final MultiClientInputMethodPrivilegedOperations mPrivOps = new MultiClientInputMethodPrivilegedOperations();
    private final MultiClientInputMethodServiceDelegate.ServiceCallback mServiceCallback;

    MultiClientInputMethodServiceDelegateImpl(Context context, MultiClientInputMethodServiceDelegate.ServiceCallback serviceCallback) {
        this.mContext = context;
        this.mServiceCallback = serviceCallback;
    }

    void acceptClient(int n, MultiClientInputMethodServiceDelegate.ClientCallback clientCallback, KeyEvent.DispatcherState dispatcherState, Looper looper) {
        Object object = InputChannel.openInputChannelPair("MSIMS-session");
        InputChannel inputChannel = object[0];
        object = object[1];
        try {
            MultiClientInputMethodClientCallbackAdaptor multiClientInputMethodClientCallbackAdaptor = new MultiClientInputMethodClientCallbackAdaptor(clientCallback, looper, dispatcherState, (InputChannel)object);
            this.mPrivOps.acceptClient(n, multiClientInputMethodClientCallbackAdaptor.createIInputMethodSession(), multiClientInputMethodClientCallbackAdaptor.createIMultiClientInputMethodSession(), inputChannel);
            return;
        }
        finally {
            inputChannel.dispose();
        }
    }

    IBinder createInputMethodWindowToken(int n) {
        return this.mPrivOps.createInputMethodWindowToken(n);
    }

    boolean isUidAllowedOnDisplay(int n, int n2) {
        return this.mPrivOps.isUidAllowedOnDisplay(n, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    IBinder onBind(Intent object) {
        object = this.mLock;
        synchronized (object) {
            if (this.mInitializationPhase != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unexpected state=");
                stringBuilder.append(this.mInitializationPhase);
                Log.e(TAG, stringBuilder.toString());
                return null;
            }
            this.mInitializationPhase = 2;
            return new ServiceImpl(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onDestroy() {
        Object object = this.mLock;
        synchronized (object) {
            int n = this.mInitializationPhase;
            if (n != 1 && n != 4) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unexpected state=");
                stringBuilder.append(this.mInitializationPhase);
                Log.e(TAG, stringBuilder.toString());
            } else {
                this.mInitializationPhase = 5;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean onUnbind(Intent object) {
        object = this.mLock;
        synchronized (object) {
            int n = this.mInitializationPhase;
            if (n != 2 && n != 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unexpected state=");
                stringBuilder.append(this.mInitializationPhase);
                Log.e(TAG, stringBuilder.toString());
            } else {
                this.mInitializationPhase = 4;
                this.mPrivOps.dispose();
            }
            return false;
        }
    }

    void reportImeWindowTarget(int n, int n2, IBinder iBinder) {
        this.mPrivOps.reportImeWindowTarget(n, n2, iBinder);
    }

    void setActive(int n, boolean bl) {
        this.mPrivOps.setActive(n, bl);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface InitializationPhase {
        public static final int INITIALIZE_CALLED = 3;
        public static final int INSTANTIATED = 1;
        public static final int ON_BIND_CALLED = 2;
        public static final int ON_DESTROY_CALLED = 5;
        public static final int ON_UNBIND_CALLED = 4;
    }

    private static final class ServiceImpl
    extends IMultiClientInputMethod.Stub {
        private final WeakReference<MultiClientInputMethodServiceDelegateImpl> mImpl;

        ServiceImpl(MultiClientInputMethodServiceDelegateImpl multiClientInputMethodServiceDelegateImpl) {
            this.mImpl = new WeakReference<MultiClientInputMethodServiceDelegateImpl>(multiClientInputMethodServiceDelegateImpl);
        }

        @Override
        public void addClient(int n, int n2, int n3, int n4) {
            MultiClientInputMethodServiceDelegateImpl multiClientInputMethodServiceDelegateImpl = (MultiClientInputMethodServiceDelegateImpl)this.mImpl.get();
            if (multiClientInputMethodServiceDelegateImpl == null) {
                return;
            }
            multiClientInputMethodServiceDelegateImpl.mServiceCallback.addClient(n, n2, n3, n4);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void initialize(IMultiClientInputMethodPrivilegedOperations object) {
            MultiClientInputMethodServiceDelegateImpl multiClientInputMethodServiceDelegateImpl = (MultiClientInputMethodServiceDelegateImpl)this.mImpl.get();
            if (multiClientInputMethodServiceDelegateImpl == null) {
                return;
            }
            Object object2 = multiClientInputMethodServiceDelegateImpl.mLock;
            synchronized (object2) {
                if (multiClientInputMethodServiceDelegateImpl.mInitializationPhase != 2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("unexpected state=");
                    ((StringBuilder)object).append(multiClientInputMethodServiceDelegateImpl.mInitializationPhase);
                    Log.e(MultiClientInputMethodServiceDelegateImpl.TAG, ((StringBuilder)object).toString());
                } else {
                    multiClientInputMethodServiceDelegateImpl.mPrivOps.set((IMultiClientInputMethodPrivilegedOperations)object);
                    multiClientInputMethodServiceDelegateImpl.mInitializationPhase = 3;
                    multiClientInputMethodServiceDelegateImpl.mServiceCallback.initialized();
                }
                return;
            }
        }

        @Override
        public void removeClient(int n) {
            MultiClientInputMethodServiceDelegateImpl multiClientInputMethodServiceDelegateImpl = (MultiClientInputMethodServiceDelegateImpl)this.mImpl.get();
            if (multiClientInputMethodServiceDelegateImpl == null) {
                return;
            }
            multiClientInputMethodServiceDelegateImpl.mServiceCallback.removeClient(n);
        }
    }

}

