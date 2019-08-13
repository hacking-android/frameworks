/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.UnsupportedAppUsage;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telecom.InCallService;
import android.telecom.Log;
import android.telecom.VideoProfile;
import android.view.Surface;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.SomeArgs;
import com.android.internal.telecom.IVideoCallback;
import com.android.internal.telecom.IVideoProvider;

public class VideoCallImpl
extends InCallService.VideoCall {
    private final VideoCallListenerBinder mBinder;
    private InCallService.VideoCall.Callback mCallback;
    private final String mCallingPackageName;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient(){

        @Override
        public void binderDied() {
            VideoCallImpl.this.mVideoProvider.asBinder().unlinkToDeath(this, 0);
        }
    };
    private Handler mHandler;
    private int mTargetSdkVersion;
    private final IVideoProvider mVideoProvider;
    private int mVideoQuality = 0;
    private int mVideoState = 0;

    VideoCallImpl(IVideoProvider iVideoProvider, String string2, int n) throws RemoteException {
        this.mVideoProvider = iVideoProvider;
        this.mVideoProvider.asBinder().linkToDeath(this.mDeathRecipient, 0);
        this.mBinder = new VideoCallListenerBinder();
        this.mVideoProvider.addVideoCallback(this.mBinder);
        this.mCallingPackageName = string2;
        this.setTargetSdkVersion(n);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=127403196L)
    @Override
    public void destroy() {
        this.unregisterCallback(this.mCallback);
    }

    @Override
    public void registerCallback(InCallService.VideoCall.Callback callback) {
        this.registerCallback(callback, null);
    }

    @Override
    public void registerCallback(InCallService.VideoCall.Callback callback, Handler handler) {
        this.mCallback = callback;
        this.mHandler = handler == null ? new MessageHandler(Looper.getMainLooper()) : new MessageHandler(handler.getLooper());
    }

    @Override
    public void requestCallDataUsage() {
        try {
            this.mVideoProvider.requestCallDataUsage();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void requestCameraCapabilities() {
        try {
            this.mVideoProvider.requestCameraCapabilities();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void sendSessionModifyRequest(VideoProfile videoProfile) {
        try {
            VideoProfile videoProfile2 = new VideoProfile(this.mVideoState, this.mVideoQuality);
            this.mVideoProvider.sendSessionModifyRequest(videoProfile2, videoProfile);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void sendSessionModifyResponse(VideoProfile videoProfile) {
        try {
            this.mVideoProvider.sendSessionModifyResponse(videoProfile);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void setCamera(String string2) {
        try {
            Log.w(this, "setCamera: cameraId=%s, calling=%s", string2, this.mCallingPackageName);
            this.mVideoProvider.setCamera(string2, this.mCallingPackageName, this.mTargetSdkVersion);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void setDeviceOrientation(int n) {
        try {
            this.mVideoProvider.setDeviceOrientation(n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void setDisplaySurface(Surface surface) {
        try {
            this.mVideoProvider.setDisplaySurface(surface);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void setPauseImage(Uri uri) {
        try {
            this.mVideoProvider.setPauseImage(uri);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void setPreviewSurface(Surface surface) {
        try {
            this.mVideoProvider.setPreviewSurface(surface);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @VisibleForTesting
    public void setTargetSdkVersion(int n) {
        this.mTargetSdkVersion = n;
    }

    public void setVideoState(int n) {
        this.mVideoState = n;
    }

    @Override
    public void setZoom(float f) {
        try {
            this.mVideoProvider.setZoom(f);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void unregisterCallback(InCallService.VideoCall.Callback callback) {
        if (callback != this.mCallback) {
            return;
        }
        this.mCallback = null;
        try {
            this.mVideoProvider.removeVideoCallback(this.mBinder);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private final class MessageHandler
    extends Handler {
        private static final int MSG_CHANGE_CALL_DATA_USAGE = 5;
        private static final int MSG_CHANGE_CAMERA_CAPABILITIES = 6;
        private static final int MSG_CHANGE_PEER_DIMENSIONS = 4;
        private static final int MSG_CHANGE_VIDEO_QUALITY = 7;
        private static final int MSG_HANDLE_CALL_SESSION_EVENT = 3;
        private static final int MSG_RECEIVE_SESSION_MODIFY_REQUEST = 1;
        private static final int MSG_RECEIVE_SESSION_MODIFY_RESPONSE = 2;

        public MessageHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message object) {
            if (VideoCallImpl.this.mCallback == null) {
                return;
            }
            switch (((Message)object).what) {
                default: {
                    break;
                }
                case 7: {
                    VideoCallImpl.this.mVideoQuality = ((Message)object).arg1;
                    VideoCallImpl.this.mCallback.onVideoQualityChanged(((Message)object).arg1);
                    break;
                }
                case 6: {
                    VideoCallImpl.this.mCallback.onCameraCapabilitiesChanged((VideoProfile.CameraCapabilities)((Message)object).obj);
                    break;
                }
                case 5: {
                    VideoCallImpl.this.mCallback.onCallDataUsageChanged((Long)((Message)object).obj);
                    break;
                }
                case 4: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        int n = (Integer)((SomeArgs)object).arg1;
                        int n2 = (Integer)((SomeArgs)object).arg2;
                        VideoCallImpl.this.mCallback.onPeerDimensionsChanged(n, n2);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                    }
                }
                case 3: {
                    VideoCallImpl.this.mCallback.onCallSessionEvent((Integer)((Message)object).obj);
                    break;
                }
                case 2: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        int n = (Integer)((SomeArgs)object).arg1;
                        VideoProfile videoProfile = (VideoProfile)((SomeArgs)object).arg2;
                        VideoProfile videoProfile2 = (VideoProfile)((SomeArgs)object).arg3;
                        VideoCallImpl.this.mCallback.onSessionModifyResponseReceived(n, videoProfile, videoProfile2);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                    }
                }
                case 1: {
                    VideoCallImpl.this.mCallback.onSessionModifyRequestReceived((VideoProfile)((Message)object).obj);
                }
            }
        }
    }

    private final class VideoCallListenerBinder
    extends IVideoCallback.Stub {
        private VideoCallListenerBinder() {
        }

        @Override
        public void changeCallDataUsage(long l) {
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            VideoCallImpl.this.mHandler.obtainMessage(5, l).sendToTarget();
        }

        @Override
        public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) {
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            VideoCallImpl.this.mHandler.obtainMessage(6, cameraCapabilities).sendToTarget();
        }

        @Override
        public void changePeerDimensions(int n, int n2) {
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = n;
            someArgs.arg2 = n2;
            VideoCallImpl.this.mHandler.obtainMessage(4, someArgs).sendToTarget();
        }

        @Override
        public void changeVideoQuality(int n) {
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            VideoCallImpl.this.mHandler.obtainMessage(7, n, 0).sendToTarget();
        }

        @Override
        public void handleCallSessionEvent(int n) {
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            VideoCallImpl.this.mHandler.obtainMessage(3, n).sendToTarget();
        }

        @Override
        public void receiveSessionModifyRequest(VideoProfile videoProfile) {
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            VideoCallImpl.this.mHandler.obtainMessage(1, videoProfile).sendToTarget();
        }

        @Override
        public void receiveSessionModifyResponse(int n, VideoProfile videoProfile, VideoProfile videoProfile2) {
            if (VideoCallImpl.this.mHandler == null) {
                return;
            }
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = n;
            someArgs.arg2 = videoProfile;
            someArgs.arg3 = videoProfile2;
            VideoCallImpl.this.mHandler.obtainMessage(2, someArgs).sendToTarget();
        }
    }

}

