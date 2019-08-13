/*
 * Decompiled with CFR 0.145.
 */
package android.inputmethodservice;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.MultiClientInputMethodServiceDelegateImpl;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public final class MultiClientInputMethodServiceDelegate {
    public static final int INVALID_CLIENT_ID = -1;
    public static final int INVALID_WINDOW_HANDLE = -1;
    public static final String SERVICE_INTERFACE = "android.inputmethodservice.MultiClientInputMethodService";
    private final MultiClientInputMethodServiceDelegateImpl mImpl;

    private MultiClientInputMethodServiceDelegate(Context context, ServiceCallback serviceCallback) {
        this.mImpl = new MultiClientInputMethodServiceDelegateImpl(context, serviceCallback);
    }

    public static MultiClientInputMethodServiceDelegate create(Context context, ServiceCallback serviceCallback) {
        return new MultiClientInputMethodServiceDelegate(context, serviceCallback);
    }

    public void acceptClient(int n, ClientCallback clientCallback, KeyEvent.DispatcherState dispatcherState, Looper looper) {
        this.mImpl.acceptClient(n, clientCallback, dispatcherState, looper);
    }

    public IBinder createInputMethodWindowToken(int n) {
        return this.mImpl.createInputMethodWindowToken(n);
    }

    public boolean isUidAllowedOnDisplay(int n, int n2) {
        return this.mImpl.isUidAllowedOnDisplay(n, n2);
    }

    public IBinder onBind(Intent intent) {
        return this.mImpl.onBind(intent);
    }

    public void onDestroy() {
        this.mImpl.onDestroy();
    }

    public boolean onUnbind(Intent intent) {
        return this.mImpl.onUnbind(intent);
    }

    public void reportImeWindowTarget(int n, int n2, IBinder iBinder) {
        this.mImpl.reportImeWindowTarget(n, n2, iBinder);
    }

    public void setActive(int n, boolean bl) {
        this.mImpl.setActive(n, bl);
    }

    public static interface ClientCallback {
        public void onAppPrivateCommand(String var1, Bundle var2);

        public void onDisplayCompletions(CompletionInfo[] var1);

        public void onFinishSession();

        public boolean onGenericMotionEvent(MotionEvent var1);

        public void onHideSoftInput(int var1, ResultReceiver var2);

        public boolean onKeyDown(int var1, KeyEvent var2);

        public boolean onKeyLongPress(int var1, KeyEvent var2);

        public boolean onKeyMultiple(int var1, KeyEvent var2);

        public boolean onKeyUp(int var1, KeyEvent var2);

        public void onShowSoftInput(int var1, ResultReceiver var2);

        public void onStartInputOrWindowGainedFocus(InputConnection var1, EditorInfo var2, int var3, int var4, int var5);

        public void onToggleSoftInput(int var1, int var2);

        public boolean onTrackballEvent(MotionEvent var1);

        public void onUpdateCursorAnchorInfo(CursorAnchorInfo var1);

        public void onUpdateSelection(int var1, int var2, int var3, int var4, int var5, int var6);
    }

    public static interface ServiceCallback {
        public void addClient(int var1, int var2, int var3, int var4);

        public void initialized();

        public void removeClient(int var1);
    }

}

