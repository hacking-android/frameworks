/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.os.Handler;
import android.view.Surface;
import dalvik.system.CloseGuard;

public final class RemoteDisplay {
    public static final int DISPLAY_ERROR_CONNECTION_DROPPED = 2;
    public static final int DISPLAY_ERROR_UNKOWN = 1;
    public static final int DISPLAY_FLAG_SECURE = 1;
    private final CloseGuard mGuard = CloseGuard.get();
    private final Handler mHandler;
    private final Listener mListener;
    private final String mOpPackageName;
    private long mPtr;

    private RemoteDisplay(Listener listener, Handler handler, String string2) {
        this.mListener = listener;
        this.mHandler = handler;
        this.mOpPackageName = string2;
    }

    private void dispose(boolean bl) {
        if (this.mPtr != 0L) {
            CloseGuard closeGuard = this.mGuard;
            if (closeGuard != null) {
                if (bl) {
                    closeGuard.warnIfOpen();
                } else {
                    closeGuard.close();
                }
            }
            this.nativeDispose(this.mPtr);
            this.mPtr = 0L;
        }
    }

    public static RemoteDisplay listen(String string2, Listener object, Handler handler, String string3) {
        if (string2 != null) {
            if (object != null) {
                if (handler != null) {
                    object = new RemoteDisplay((Listener)object, handler, string3);
                    RemoteDisplay.super.startListening(string2);
                    return object;
                }
                throw new IllegalArgumentException("handler must not be null");
            }
            throw new IllegalArgumentException("listener must not be null");
        }
        throw new IllegalArgumentException("iface must not be null");
    }

    private native void nativeDispose(long var1);

    private native long nativeListen(String var1, String var2);

    private native void nativePause(long var1);

    private native void nativeResume(long var1);

    @UnsupportedAppUsage
    private void notifyDisplayConnected(final Surface surface, final int n, final int n2, final int n3, final int n4) {
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                RemoteDisplay.this.mListener.onDisplayConnected(surface, n, n2, n3, n4);
            }
        });
    }

    @UnsupportedAppUsage
    private void notifyDisplayDisconnected() {
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                RemoteDisplay.this.mListener.onDisplayDisconnected();
            }
        });
    }

    @UnsupportedAppUsage
    private void notifyDisplayError(final int n) {
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                RemoteDisplay.this.mListener.onDisplayError(n);
            }
        });
    }

    private void startListening(String string2) {
        this.mPtr = this.nativeListen(string2, this.mOpPackageName);
        if (this.mPtr != 0L) {
            this.mGuard.open("dispose");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not start listening for remote display connection on \"");
        stringBuilder.append(string2);
        stringBuilder.append("\"");
        throw new IllegalStateException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public void dispose() {
        this.dispose(false);
    }

    protected void finalize() throws Throwable {
        try {
            this.dispose(true);
            return;
        }
        finally {
            super.finalize();
        }
    }

    public void pause() {
        this.nativePause(this.mPtr);
    }

    public void resume() {
        this.nativeResume(this.mPtr);
    }

    public static interface Listener {
        public void onDisplayConnected(Surface var1, int var2, int var3, int var4, int var5);

        public void onDisplayDisconnected();

        public void onDisplayError(int var1);
    }

}

