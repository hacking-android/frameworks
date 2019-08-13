/*
 * Decompiled with CFR 0.145.
 */
package android.media.projection;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionCallback;
import android.os.Handler;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Surface;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class MediaProjection {
    private static final String TAG = "MediaProjection";
    private final Map<Callback, CallbackRecord> mCallbacks = new ArrayMap<Callback, CallbackRecord>();
    private final Context mContext;
    private final IMediaProjection mImpl;

    public MediaProjection(Context object, IMediaProjection iInterface) {
        this.mContext = object;
        this.mImpl = iInterface;
        try {
            object = this.mImpl;
            object.start((IMediaProjectionCallback)iInterface);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("Failed to start media projection", remoteException);
        }
    }

    public VirtualDisplay createVirtualDisplay(String string2, int n, int n2, int n3, int n4, Surface surface, VirtualDisplay.Callback callback, Handler handler) {
        return ((DisplayManager)this.mContext.getSystemService("display")).createVirtualDisplay(this, string2, n, n2, n3, surface, n4, callback, handler, null);
    }

    public VirtualDisplay createVirtualDisplay(String string2, int n, int n2, int n3, boolean bl, Surface surface, VirtualDisplay.Callback callback, Handler handler) {
        DisplayManager displayManager = (DisplayManager)this.mContext.getSystemService("display");
        int n4 = bl ? 4 : 0;
        return displayManager.createVirtualDisplay(this, string2, n, n2, n3, surface, n4 | 16 | 2, callback, handler, null);
    }

    public IMediaProjection getProjection() {
        return this.mImpl;
    }

    public void registerCallback(Callback callback, Handler handler) {
        if (callback != null) {
            Handler handler2 = handler;
            if (handler == null) {
                handler2 = new Handler();
            }
            this.mCallbacks.put(callback, new CallbackRecord(callback, handler2));
            return;
        }
        throw new IllegalArgumentException("callback should not be null");
    }

    public void stop() {
        try {
            this.mImpl.stop();
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Unable to stop projection", remoteException);
        }
    }

    public void unregisterCallback(Callback callback) {
        if (callback != null) {
            this.mCallbacks.remove(callback);
            return;
        }
        throw new IllegalArgumentException("callback should not be null");
    }

    public static abstract class Callback {
        public void onStop() {
        }
    }

    private static final class CallbackRecord {
        private final Callback mCallback;
        private final Handler mHandler;

        public CallbackRecord(Callback callback, Handler handler) {
            this.mCallback = callback;
            this.mHandler = handler;
        }

        public void onStop() {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    CallbackRecord.this.mCallback.onStop();
                }
            });
        }

    }

    private final class MediaProjectionCallback
    extends IMediaProjectionCallback.Stub {
        private MediaProjectionCallback() {
        }

        @Override
        public void onStop() {
            Iterator iterator = MediaProjection.this.mCallbacks.values().iterator();
            while (iterator.hasNext()) {
                ((CallbackRecord)iterator.next()).onStop();
            }
        }
    }

}

