/*
 * Decompiled with CFR 0.145.
 */
package android.media.projection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.media.projection.IMediaProjectionWatcherCallback;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;
import java.util.Map;

public final class MediaProjectionManager {
    public static final String EXTRA_APP_TOKEN = "android.media.projection.extra.EXTRA_APP_TOKEN";
    public static final String EXTRA_MEDIA_PROJECTION = "android.media.projection.extra.EXTRA_MEDIA_PROJECTION";
    private static final String TAG = "MediaProjectionManager";
    public static final int TYPE_MIRRORING = 1;
    public static final int TYPE_PRESENTATION = 2;
    public static final int TYPE_SCREEN_CAPTURE = 0;
    private Map<Callback, CallbackDelegate> mCallbacks;
    private Context mContext;
    private IMediaProjectionManager mService;

    public MediaProjectionManager(Context context) {
        this.mContext = context;
        this.mService = IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection"));
        this.mCallbacks = new ArrayMap<Callback, CallbackDelegate>();
    }

    public void addCallback(Callback callback, Handler object) {
        if (callback != null) {
            object = new CallbackDelegate(callback, (Handler)object);
            this.mCallbacks.put(callback, (CallbackDelegate)object);
            try {
                this.mService.addCallback((IMediaProjectionWatcherCallback)object);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Unable to add callbacks to MediaProjection service", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("callback must not be null");
    }

    public Intent createScreenCaptureIntent() {
        Intent intent = new Intent();
        intent.setComponent(ComponentName.unflattenFromString(this.mContext.getResources().getString(17039751)));
        return intent;
    }

    public MediaProjectionInfo getActiveProjectionInfo() {
        try {
            MediaProjectionInfo mediaProjectionInfo = this.mService.getActiveProjectionInfo();
            return mediaProjectionInfo;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Unable to get the active projection info", remoteException);
            return null;
        }
    }

    public MediaProjection getMediaProjection(int n, Intent object) {
        if (n == -1 && object != null) {
            if ((object = ((Intent)object).getIBinderExtra(EXTRA_MEDIA_PROJECTION)) == null) {
                return null;
            }
            return new MediaProjection(this.mContext, IMediaProjection.Stub.asInterface((IBinder)object));
        }
        return null;
    }

    public void removeCallback(Callback object) {
        if (object != null) {
            if ((object = this.mCallbacks.remove(object)) != null) {
                try {
                    this.mService.removeCallback((IMediaProjectionWatcherCallback)object);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Unable to add callbacks to MediaProjection service", remoteException);
                }
            }
            return;
        }
        throw new IllegalArgumentException("callback must not be null");
    }

    public void stopActiveProjection() {
        try {
            this.mService.stopActiveProjection();
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Unable to stop the currently active media projection", remoteException);
        }
    }

    public static abstract class Callback {
        public abstract void onStart(MediaProjectionInfo var1);

        public abstract void onStop(MediaProjectionInfo var1);
    }

    private static final class CallbackDelegate
    extends IMediaProjectionWatcherCallback.Stub {
        private Callback mCallback;
        private Handler mHandler;

        public CallbackDelegate(Callback object, Handler handler) {
            this.mCallback = object;
            object = handler;
            if (handler == null) {
                object = new Handler();
            }
            this.mHandler = object;
        }

        @Override
        public void onStart(final MediaProjectionInfo mediaProjectionInfo) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    CallbackDelegate.this.mCallback.onStart(mediaProjectionInfo);
                }
            });
        }

        @Override
        public void onStop(final MediaProjectionInfo mediaProjectionInfo) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    CallbackDelegate.this.mCallback.onStop(mediaProjectionInfo);
                }
            });
        }

    }

}

