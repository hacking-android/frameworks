/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.inputmethod;

import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.inputmethod.IInputContentUriToken;
import com.android.internal.inputmethod.IInputMethodPrivilegedOperations;

public final class InputMethodPrivilegedOperations {
    private static final String TAG = "InputMethodPrivilegedOperations";
    private final OpsHolder mOps = new OpsHolder();

    public void applyImeVisibility(boolean bl) {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iInputMethodPrivilegedOperations.applyImeVisibility(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public IInputContentUriToken createInputContentUriToken(Uri object, String string2) {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return null;
        }
        try {
            object = iInputMethodPrivilegedOperations.createInputContentUriToken((Uri)object, string2);
            return object;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public void hideMySoftInput(int n) {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iInputMethodPrivilegedOperations.hideMySoftInput(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void notifyUserAction() {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iInputMethodPrivilegedOperations.notifyUserAction();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reportFullscreenMode(boolean bl) {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iInputMethodPrivilegedOperations.reportFullscreenMode(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reportPreRendered(EditorInfo editorInfo) {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iInputMethodPrivilegedOperations.reportPreRendered(editorInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reportStartInput(IBinder iBinder) {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iInputMethodPrivilegedOperations.reportStartInput(iBinder);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void set(IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations) {
        this.mOps.set(iInputMethodPrivilegedOperations);
    }

    public void setImeWindowStatus(int n, int n2) {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iInputMethodPrivilegedOperations.setImeWindowStatus(n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setInputMethod(String string2) {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iInputMethodPrivilegedOperations.setInputMethod(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setInputMethodAndSubtype(String string2, InputMethodSubtype inputMethodSubtype) {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iInputMethodPrivilegedOperations.setInputMethodAndSubtype(string2, inputMethodSubtype);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean shouldOfferSwitchingToNextInputMethod() {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return false;
        }
        try {
            boolean bl = iInputMethodPrivilegedOperations.shouldOfferSwitchingToNextInputMethod();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void showMySoftInput(int n) {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iInputMethodPrivilegedOperations.showMySoftInput(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean switchToNextInputMethod(boolean bl) {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return false;
        }
        try {
            bl = iInputMethodPrivilegedOperations.switchToNextInputMethod(bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean switchToPreviousInputMethod() {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return false;
        }
        try {
            boolean bl = iInputMethodPrivilegedOperations.switchToPreviousInputMethod();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void updateStatusIcon(String string2, int n) {
        IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations = this.mOps.getAndWarnIfNull();
        if (iInputMethodPrivilegedOperations == null) {
            return;
        }
        try {
            iInputMethodPrivilegedOperations.updateStatusIcon(string2, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private static final class OpsHolder {
        @GuardedBy(value={"this"})
        private IInputMethodPrivilegedOperations mPrivOps;

        private OpsHolder() {
        }

        private static String getCallerMethodName() {
            StackTraceElement[] arrstackTraceElement = Thread.currentThread().getStackTrace();
            if (arrstackTraceElement.length <= 4) {
                return "<bottom of call stack>";
            }
            return arrstackTraceElement[4].getMethodName();
        }

        public IInputMethodPrivilegedOperations getAndWarnIfNull() {
            synchronized (this) {
                Object object;
                if (this.mPrivOps == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(OpsHolder.getCallerMethodName());
                    ((StringBuilder)object).append(" is ignored. Call it within attachToken() and InputMethodService.onDestroy()");
                    Log.e(InputMethodPrivilegedOperations.TAG, ((StringBuilder)object).toString());
                }
                object = this.mPrivOps;
                return object;
            }
        }

        public void set(IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations) {
            synchronized (this) {
                if (this.mPrivOps == null) {
                    this.mPrivOps = iInputMethodPrivilegedOperations;
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("IInputMethodPrivilegedOperations must be set at most once. privOps=");
                stringBuilder.append(iInputMethodPrivilegedOperations);
                IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                throw illegalStateException;
            }
        }
    }

}

