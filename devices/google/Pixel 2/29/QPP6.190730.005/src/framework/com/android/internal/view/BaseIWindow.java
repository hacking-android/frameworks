/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.view.DisplayCutout;
import android.view.DragEvent;
import android.view.IWindow;
import android.view.IWindowSession;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import com.android.internal.os.IResultReceiver;

public class BaseIWindow
extends IWindow.Stub {
    public int mSeq;
    private IWindowSession mSession;

    @Override
    public void closeSystemDialogs(String string2) {
    }

    @Override
    public void dispatchAppVisibility(boolean bl) {
    }

    @Override
    public void dispatchDragEvent(DragEvent dragEvent) {
        if (dragEvent.getAction() == 3) {
            try {
                this.mSession.reportDropResult(this, false);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @Override
    public void dispatchGetNewSurface() {
    }

    @Override
    public void dispatchPointerCaptureChanged(boolean bl) {
    }

    @Override
    public void dispatchSystemUiVisibilityChanged(int n, int n2, int n3, int n4) {
        this.mSeq = n;
    }

    @Override
    public void dispatchWallpaperCommand(String string2, int n, int n2, int n3, Bundle bundle, boolean bl) {
        if (bl) {
            try {
                this.mSession.wallpaperCommandComplete(this.asBinder(), null);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @Override
    public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, boolean bl) {
        if (bl) {
            try {
                this.mSession.wallpaperOffsetsComplete(this.asBinder());
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @Override
    public void dispatchWindowShown() {
    }

    @Override
    public void executeCommand(String string2, String string3, ParcelFileDescriptor parcelFileDescriptor) {
    }

    @Override
    public void insetsChanged(InsetsState insetsState) {
    }

    @Override
    public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] arrinsetsSourceControl) throws RemoteException {
    }

    @Override
    public void moved(int n, int n2) {
    }

    @Override
    public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int n) {
    }

    @Override
    public void resized(Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5, Rect rect6, boolean bl, MergedConfiguration mergedConfiguration, Rect rect7, boolean bl2, boolean bl3, int n, DisplayCutout.ParcelableWrapper parcelableWrapper) {
        if (bl) {
            try {
                this.mSession.finishDrawing(this);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public void setSession(IWindowSession iWindowSession) {
        this.mSession = iWindowSession;
    }

    @Override
    public void updatePointerIcon(float f, float f2) {
        InputManager.getInstance().setPointerIconType(1);
    }

    @Override
    public void windowFocusChanged(boolean bl, boolean bl2) {
    }
}

