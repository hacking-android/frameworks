/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.os.IBinder;
import android.os.ResultReceiver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodSession;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.inputmethod.IInputMethodPrivilegedOperations;

public interface InputMethod {
    public static final String SERVICE_INTERFACE = "android.view.InputMethod";
    public static final String SERVICE_META_DATA = "android.view.im";
    public static final int SHOW_EXPLICIT = 1;
    public static final int SHOW_FORCED = 2;

    public void attachToken(IBinder var1);

    public void bindInput(InputBinding var1);

    public void changeInputMethodSubtype(InputMethodSubtype var1);

    public void createSession(SessionCallback var1);

    default public void dispatchStartInputWithToken(InputConnection inputConnection, EditorInfo editorInfo, boolean bl, IBinder iBinder, boolean bl2) {
        if (bl) {
            this.restartInput(inputConnection, editorInfo);
        } else {
            this.startInput(inputConnection, editorInfo);
        }
    }

    public void hideSoftInput(int var1, ResultReceiver var2);

    default public void initializeInternal(IBinder iBinder, int n, IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations) {
        this.updateInputMethodDisplay(n);
        this.attachToken(iBinder);
    }

    public void restartInput(InputConnection var1, EditorInfo var2);

    public void revokeSession(InputMethodSession var1);

    public void setSessionEnabled(InputMethodSession var1, boolean var2);

    public void showSoftInput(int var1, ResultReceiver var2);

    public void startInput(InputConnection var1, EditorInfo var2);

    public void unbindInput();

    default public void updateInputMethodDisplay(int n) {
    }

    public static interface SessionCallback {
        public void sessionCreated(InputMethodSession var1);
    }

}

