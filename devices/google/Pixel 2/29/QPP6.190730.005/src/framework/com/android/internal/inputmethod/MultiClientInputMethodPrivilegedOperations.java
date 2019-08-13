/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.inputmethod;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.InputChannel;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.inputmethod.IMultiClientInputMethodPrivilegedOperations;
import com.android.internal.inputmethod.IMultiClientInputMethodSession;
import com.android.internal.view.IInputMethodSession;

public class MultiClientInputMethodPrivilegedOperations {
    private static final String TAG = "MultiClientInputMethodPrivilegedOperations";
    private final OpsHolder mOps = new OpsHolder();

    public void acceptClient(int n, IInputMethodSession iInputMethodSession, IMultiClientInputMethodSession iMultiClientInputMethodSession, InputChannel inputChannel) {
        IMultiClientInputMethodPrivilegedOperations iMultiClientInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iMultiClientInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iMultiClientInputMethodPrivilegedOperations.acceptClient(n, iInputMethodSession, iMultiClientInputMethodSession, inputChannel);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public IBinder createInputMethodWindowToken(int n) {
        Object object = this.mOps.getAndWarnIfNull();
        if (object == null) {
            return null;
        }
        try {
            object = object.createInputMethodWindowToken(n);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void deleteInputMethodWindowToken(IBinder iBinder) {
        IMultiClientInputMethodPrivilegedOperations iMultiClientInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iMultiClientInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iMultiClientInputMethodPrivilegedOperations.deleteInputMethodWindowToken(iBinder);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void dispose() {
        this.mOps.dispose();
    }

    public boolean isUidAllowedOnDisplay(int n, int n2) {
        IMultiClientInputMethodPrivilegedOperations iMultiClientInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iMultiClientInputMethodPrivilegedOperations == null) {
            return false;
        }
        try {
            boolean bl = iMultiClientInputMethodPrivilegedOperations.isUidAllowedOnDisplay(n, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reportImeWindowTarget(int n, int n2, IBinder iBinder) {
        IMultiClientInputMethodPrivilegedOperations iMultiClientInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iMultiClientInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iMultiClientInputMethodPrivilegedOperations.reportImeWindowTarget(n, n2, iBinder);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void set(IMultiClientInputMethodPrivilegedOperations iMultiClientInputMethodPrivilegedOperations) {
        this.mOps.set(iMultiClientInputMethodPrivilegedOperations);
    }

    public void setActive(int n, boolean bl) {
        IMultiClientInputMethodPrivilegedOperations iMultiClientInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iMultiClientInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iMultiClientInputMethodPrivilegedOperations.setActive(n, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private static final class OpsHolder {
        @GuardedBy(value={"this"})
        private IMultiClientInputMethodPrivilegedOperations mPrivOps;

        private OpsHolder() {
        }

        private static String getCallerMethodName() {
            StackTraceElement[] arrstackTraceElement = Thread.currentThread().getStackTrace();
            if (arrstackTraceElement.length <= 4) {
                return "<bottom of call stack>";
            }
            return arrstackTraceElement[4].getMethodName();
        }

        public void dispose() {
            synchronized (this) {
                this.mPrivOps = null;
                return;
            }
        }

        public IMultiClientInputMethodPrivilegedOperations getAndWarnIfNull() {
            synchronized (this) {
                Object object;
                if (this.mPrivOps == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(OpsHolder.getCallerMethodName());
                    ((StringBuilder)object).append(" is ignored. Call it within attachToken() and InputMethodService.onDestroy()");
                    Log.e(MultiClientInputMethodPrivilegedOperations.TAG, ((StringBuilder)object).toString());
                }
                object = this.mPrivOps;
                return object;
            }
        }

        public void set(IMultiClientInputMethodPrivilegedOperations iMultiClientInputMethodPrivilegedOperations) {
            synchronized (this) {
                if (this.mPrivOps == null) {
                    this.mPrivOps = iMultiClientInputMethodPrivilegedOperations;
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("IMultiClientInputMethodPrivilegedOperations must be set at most once. privOps=");
                stringBuilder.append(iMultiClientInputMethodPrivilegedOperations);
                IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                throw illegalStateException;
            }
        }
    }

}

