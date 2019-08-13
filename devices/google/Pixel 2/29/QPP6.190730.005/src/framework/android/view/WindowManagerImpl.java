/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Region;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.Display;
import android.view.KeyboardShortcutGroup;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.os.IResultReceiver;
import java.util.ArrayList;
import java.util.List;

public final class WindowManagerImpl
implements WindowManager {
    private final Context mContext;
    private IBinder mDefaultToken;
    @UnsupportedAppUsage
    private final WindowManagerGlobal mGlobal = WindowManagerGlobal.getInstance();
    private final Window mParentWindow;

    public WindowManagerImpl(Context context) {
        this(context, null);
    }

    private WindowManagerImpl(Context context, Window window) {
        this.mContext = context;
        this.mParentWindow = window;
    }

    private void applyDefaultToken(ViewGroup.LayoutParams layoutParams) {
        if (this.mDefaultToken != null && this.mParentWindow == null) {
            if (layoutParams instanceof WindowManager.LayoutParams) {
                layoutParams = (WindowManager.LayoutParams)layoutParams;
                if (((WindowManager.LayoutParams)layoutParams).token == null) {
                    ((WindowManager.LayoutParams)layoutParams).token = this.mDefaultToken;
                }
            } else {
                throw new IllegalArgumentException("Params must be WindowManager.LayoutParams");
            }
        }
    }

    @Override
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        this.applyDefaultToken(layoutParams);
        this.mGlobal.addView(view, layoutParams, this.mContext.getDisplay(), this.mParentWindow);
    }

    public WindowManagerImpl createLocalWindowManager(Window window) {
        return new WindowManagerImpl(this.mContext, window);
    }

    public WindowManagerImpl createPresentationWindowManager(Context context) {
        return new WindowManagerImpl(context, this.mParentWindow);
    }

    @Override
    public Region getCurrentImeTouchRegion() {
        try {
            Region region = WindowManagerGlobal.getWindowManagerService().getCurrentImeTouchRegion();
            return region;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @Override
    public Display getDefaultDisplay() {
        return this.mContext.getDisplay();
    }

    @Override
    public void removeView(View view) {
        this.mGlobal.removeView(view, false);
    }

    @Override
    public void removeViewImmediate(View view) {
        this.mGlobal.removeView(view, true);
    }

    @Override
    public void requestAppKeyboardShortcuts(WindowManager.KeyboardShortcutsReceiver object, int n) {
        object = new IResultReceiver.Stub((WindowManager.KeyboardShortcutsReceiver)object){
            final /* synthetic */ WindowManager.KeyboardShortcutsReceiver val$receiver;
            {
                this.val$receiver = keyboardShortcutsReceiver;
            }

            @Override
            public void send(int n, Bundle cloneable) throws RemoteException {
                cloneable = cloneable.getParcelableArrayList("shortcuts_array");
                this.val$receiver.onKeyboardShortcutsReceived((List<KeyboardShortcutGroup>)((Object)cloneable));
            }
        };
        try {
            WindowManagerGlobal.getWindowManagerService().requestAppKeyboardShortcuts((IResultReceiver)object, n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void setDefaultToken(IBinder iBinder) {
        this.mDefaultToken = iBinder;
    }

    @Override
    public void setShouldShowIme(int n, boolean bl) {
        try {
            WindowManagerGlobal.getWindowManagerService().setShouldShowIme(n, bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void setShouldShowSystemDecors(int n, boolean bl) {
        try {
            WindowManagerGlobal.getWindowManagerService().setShouldShowSystemDecors(n, bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void setShouldShowWithInsecureKeyguard(int n, boolean bl) {
        try {
            WindowManagerGlobal.getWindowManagerService().setShouldShowWithInsecureKeyguard(n, bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public boolean shouldShowIme(int n) {
        try {
            boolean bl = WindowManagerGlobal.getWindowManagerService().shouldShowIme(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean shouldShowSystemDecors(int n) {
        try {
            boolean bl = WindowManagerGlobal.getWindowManagerService().shouldShowSystemDecors(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public void updateViewLayout(View view, ViewGroup.LayoutParams layoutParams) {
        this.applyDefaultToken(layoutParams);
        this.mGlobal.updateViewLayout(view, layoutParams);
    }

}

