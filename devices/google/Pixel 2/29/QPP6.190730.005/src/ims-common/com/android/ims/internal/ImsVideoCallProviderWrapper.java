/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RegistrantList
 *  android.os.RemoteException
 *  android.telecom.Connection
 *  android.telecom.Connection$VideoProvider
 *  android.telecom.Log
 *  android.telecom.VideoProfile
 *  android.telecom.VideoProfile$CameraCapabilities
 *  android.view.Surface
 *  com.android.ims.internal.IImsVideoCallCallback
 *  com.android.ims.internal.IImsVideoCallCallback$Stub
 *  com.android.ims.internal.IImsVideoCallProvider
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.os.SomeArgs
 */
package com.android.ims.internal;

import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RegistrantList;
import android.os.RemoteException;
import android.telecom.Connection;
import android.telecom.Log;
import android.telecom.VideoProfile;
import android.view.Surface;
import com.android.ims.internal.IImsVideoCallCallback;
import com.android.ims.internal.IImsVideoCallProvider;
import com.android.ims.internal.VideoPauseTracker;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.SomeArgs;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ImsVideoCallProviderWrapper
extends Connection.VideoProvider {
    private static final int MSG_CHANGE_CALL_DATA_USAGE = 5;
    private static final int MSG_CHANGE_CAMERA_CAPABILITIES = 6;
    private static final int MSG_CHANGE_PEER_DIMENSIONS = 4;
    private static final int MSG_CHANGE_VIDEO_QUALITY = 7;
    private static final int MSG_HANDLE_CALL_SESSION_EVENT = 3;
    private static final int MSG_RECEIVE_SESSION_MODIFY_REQUEST = 1;
    private static final int MSG_RECEIVE_SESSION_MODIFY_RESPONSE = 2;
    private final ImsVideoCallCallback mBinder;
    private final Set<ImsVideoProviderWrapperCallback> mCallbacks = Collections.newSetFromMap(new ConcurrentHashMap(8, 0.9f, 1));
    private int mCurrentVideoState;
    private RegistrantList mDataUsageUpdateRegistrants = new RegistrantList();
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient(){

        public void binderDied() {
            ImsVideoCallProviderWrapper.this.mVideoCallProvider.asBinder().unlinkToDeath((IBinder.DeathRecipient)this, 0);
        }
    };
    private final Handler mHandler = new Handler(Looper.getMainLooper()){

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    break;
                }
                case 7: {
                    ImsVideoCallProviderWrapper.this.changeVideoQuality(message.arg1);
                    break;
                }
                case 6: {
                    ImsVideoCallProviderWrapper.this.changeCameraCapabilities((VideoProfile.CameraCapabilities)message.obj);
                    break;
                }
                case 5: {
                    ImsVideoCallProviderWrapper.this.setCallDataUsage(((Long)message.obj).longValue());
                    ImsVideoCallProviderWrapper.this.mDataUsageUpdateRegistrants.notifyResult(message.obj);
                    break;
                }
                case 4: {
                    message = (SomeArgs)message.obj;
                    try {
                        int n = (Integer)message.arg1;
                        int n2 = (Integer)message.arg2;
                        ImsVideoCallProviderWrapper.this.changePeerDimensions(n, n2);
                        break;
                    }
                    finally {
                        message.recycle();
                    }
                }
                case 3: {
                    ImsVideoCallProviderWrapper.this.handleCallSessionEvent(((Integer)message.obj).intValue());
                    break;
                }
                case 2: {
                    message = (SomeArgs)message.obj;
                    try {
                        int n = (Integer)message.arg1;
                        VideoProfile videoProfile = (VideoProfile)message.arg2;
                        VideoProfile videoProfile2 = (VideoProfile)message.arg3;
                        ImsVideoCallProviderWrapper.this.receiveSessionModifyResponse(n, videoProfile, videoProfile2);
                        for (ImsVideoProviderWrapperCallback imsVideoProviderWrapperCallback : ImsVideoCallProviderWrapper.this.mCallbacks) {
                            if (imsVideoProviderWrapperCallback == null) continue;
                            imsVideoProviderWrapperCallback.onReceiveSessionModifyResponse(n, videoProfile, videoProfile2);
                        }
                        break;
                    }
                    finally {
                        message.recycle();
                    }
                }
                case 1: {
                    message = (VideoProfile)message.obj;
                    if (!VideoProfile.isVideo((int)ImsVideoCallProviderWrapper.this.mCurrentVideoState) && VideoProfile.isVideo((int)message.getVideoState()) && !ImsVideoCallProviderWrapper.this.mIsVideoEnabled) {
                        Log.i((Object)((Object)ImsVideoCallProviderWrapper.this), (String)"receiveSessionModifyRequest: requestedVideoState=%s; rejecting as video is disabled.", (Object[])new Object[]{message.getVideoState()});
                        try {
                            IImsVideoCallProvider iImsVideoCallProvider = ImsVideoCallProviderWrapper.this.mVideoCallProvider;
                            message = new VideoProfile(0);
                            iImsVideoCallProvider.sendSessionModifyResponse((VideoProfile)message);
                        }
                        catch (RemoteException remoteException) {
                            // empty catch block
                        }
                        return;
                    }
                    ImsVideoCallProviderWrapper.this.receiveSessionModifyRequest((VideoProfile)message);
                }
            }
        }
    };
    private boolean mIsVideoEnabled = true;
    private boolean mUseVideoPauseWorkaround = false;
    private final IImsVideoCallProvider mVideoCallProvider;
    private VideoPauseTracker mVideoPauseTracker = new VideoPauseTracker();

    public ImsVideoCallProviderWrapper(IImsVideoCallProvider iImsVideoCallProvider) throws RemoteException {
        this.mVideoCallProvider = iImsVideoCallProvider;
        if (iImsVideoCallProvider != null) {
            this.mVideoCallProvider.asBinder().linkToDeath(this.mDeathRecipient, 0);
            this.mBinder = new ImsVideoCallCallback();
            this.mVideoCallProvider.setCallback((IImsVideoCallCallback)this.mBinder);
        } else {
            this.mBinder = null;
        }
    }

    @VisibleForTesting
    public ImsVideoCallProviderWrapper(IImsVideoCallProvider iImsVideoCallProvider, VideoPauseTracker videoPauseTracker) throws RemoteException {
        this(iImsVideoCallProvider);
        this.mVideoPauseTracker = videoPauseTracker;
    }

    @VisibleForTesting
    public static boolean isPauseRequest(int n, int n2) {
        boolean bl = VideoProfile.isPaused((int)n);
        boolean bl2 = VideoProfile.isPaused((int)n2);
        bl = !bl && bl2;
        return bl;
    }

    @VisibleForTesting
    public static boolean isResumeRequest(int n, int n2) {
        boolean bl = VideoProfile.isPaused((int)n);
        boolean bl2 = VideoProfile.isPaused((int)n2);
        bl2 = bl && !bl2;
        return bl2;
    }

    @VisibleForTesting
    public static boolean isTurnOffCameraRequest(int n, int n2) {
        boolean bl = VideoProfile.isTransmissionEnabled((int)n) && !VideoProfile.isTransmissionEnabled((int)n2);
        return bl;
    }

    @VisibleForTesting
    public static boolean isTurnOnCameraRequest(int n, int n2) {
        boolean bl = !VideoProfile.isTransmissionEnabled((int)n) && VideoProfile.isTransmissionEnabled((int)n2);
        return bl;
    }

    public void addImsVideoProviderCallback(ImsVideoProviderWrapperCallback imsVideoProviderWrapperCallback) {
        this.mCallbacks.add(imsVideoProviderWrapperCallback);
    }

    @VisibleForTesting
    public VideoProfile maybeFilterPauseResume(VideoProfile videoProfile, VideoProfile videoProfile2, int n) {
        int n2 = videoProfile.getVideoState();
        int n3 = videoProfile2.getVideoState();
        boolean bl = n == 1 && VideoProfile.isPaused((int)n2) && VideoProfile.isPaused((int)n3);
        boolean bl2 = ImsVideoCallProviderWrapper.isPauseRequest(n2, n3) || bl;
        boolean bl3 = ImsVideoCallProviderWrapper.isResumeRequest(n2, n3);
        if (bl2) {
            Log.i((Object)((Object)this), (String)"maybeFilterPauseResume: isPauseRequest (from=%s, to=%s)", (Object[])new Object[]{VideoProfile.videoStateToString((int)n2), VideoProfile.videoStateToString((int)n3)});
            videoProfile = videoProfile2;
            if (!this.mVideoPauseTracker.shouldPauseVideoFor(n)) {
                videoProfile = videoProfile2;
                if (!bl) {
                    videoProfile = new VideoProfile(n3 & -5, videoProfile2.getQuality());
                }
            }
        } else {
            videoProfile = videoProfile2;
            if (bl3) {
                bl3 = ImsVideoCallProviderWrapper.isTurnOffCameraRequest(n2, n3);
                boolean bl4 = ImsVideoCallProviderWrapper.isTurnOnCameraRequest(n2, n3);
                if (this.mUseVideoPauseWorkaround && (bl3 || bl4)) {
                    Log.i((Object)((Object)this), (String)"maybeFilterPauseResume: isResumeRequest, but camera turning on/off so skipping (from=%s, to=%s)", (Object[])new Object[]{VideoProfile.videoStateToString((int)n2), VideoProfile.videoStateToString((int)n3)});
                    return videoProfile2;
                }
                Log.i((Object)((Object)this), (String)"maybeFilterPauseResume: isResumeRequest (from=%s, to=%s)", (Object[])new Object[]{VideoProfile.videoStateToString((int)n2), VideoProfile.videoStateToString((int)n3)});
                videoProfile = videoProfile2;
                if (!this.mVideoPauseTracker.shouldResumeVideoFor(n)) {
                    videoProfile = new VideoProfile(n3 | 4, videoProfile2.getQuality());
                }
            }
        }
        return videoProfile;
    }

    public void onRequestCameraCapabilities() {
        try {
            this.mVideoCallProvider.requestCameraCapabilities();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onRequestConnectionDataUsage() {
        try {
            this.mVideoCallProvider.requestCallDataUsage();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onSendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) {
        if (videoProfile != null && videoProfile2 != null) {
            try {
                if (ImsVideoCallProviderWrapper.isResumeRequest(videoProfile.getVideoState(), videoProfile2.getVideoState()) && !VideoProfile.isPaused((int)this.mCurrentVideoState)) {
                    Log.i((Object)((Object)this), (String)"onSendSessionModifyRequest: fromVideoState=%s, toVideoState=%s; skipping resume request - already resumed.", (Object[])new Object[]{VideoProfile.videoStateToString((int)videoProfile.getVideoState()), VideoProfile.videoStateToString((int)videoProfile2.getVideoState())});
                    return;
                }
                videoProfile2 = this.maybeFilterPauseResume(videoProfile, videoProfile2, 1);
                videoProfile.getVideoState();
                videoProfile2.getVideoState();
                Log.i((Object)((Object)this), (String)"onSendSessionModifyRequest: fromVideoState=%s, toVideoState=%s; ", (Object[])new Object[]{VideoProfile.videoStateToString((int)videoProfile.getVideoState()), VideoProfile.videoStateToString((int)videoProfile2.getVideoState())});
                this.mVideoCallProvider.sendSessionModifyRequest(videoProfile, videoProfile2);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        Log.w((Object)((Object)this), (String)"onSendSessionModifyRequest: null profile in request.", (Object[])new Object[0]);
    }

    public void onSendSessionModifyResponse(VideoProfile videoProfile) {
        try {
            this.mVideoCallProvider.sendSessionModifyResponse(videoProfile);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onSetCamera(String string) {
        try {
            this.mVideoCallProvider.setCamera(string, Binder.getCallingUid());
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onSetDeviceOrientation(int n) {
        try {
            this.mVideoCallProvider.setDeviceOrientation(n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onSetDisplaySurface(Surface surface) {
        try {
            this.mVideoCallProvider.setDisplaySurface(surface);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onSetPauseImage(Uri uri) {
        try {
            this.mVideoCallProvider.setPauseImage(uri);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onSetPreviewSurface(Surface surface) {
        try {
            this.mVideoCallProvider.setPreviewSurface(surface);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onSetZoom(float f) {
        try {
            this.mVideoCallProvider.setZoom(f);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void onVideoStateChanged(int n) {
        if (VideoProfile.isPaused((int)this.mCurrentVideoState) && !VideoProfile.isPaused((int)n)) {
            Log.i((Object)((Object)this), (String)"onVideoStateChanged: currentVideoState=%s, newVideoState=%s, clearing pending pause requests.", (Object[])new Object[]{VideoProfile.videoStateToString((int)this.mCurrentVideoState), VideoProfile.videoStateToString((int)n)});
            this.mVideoPauseTracker.clearPauseRequests();
        } else {
            Log.d((Object)((Object)this), (String)"onVideoStateChanged: currentVideoState=%s, newVideoState=%s", (Object[])new Object[]{VideoProfile.videoStateToString((int)this.mCurrentVideoState), VideoProfile.videoStateToString((int)n)});
        }
        this.mCurrentVideoState = n;
    }

    public void pauseVideo(int n, int n2) {
        if (this.mVideoPauseTracker.shouldPauseVideoFor(n2)) {
            VideoProfile videoProfile = new VideoProfile(n);
            VideoProfile videoProfile2 = new VideoProfile(n | 4);
            try {
                Log.i((Object)((Object)this), (String)"pauseVideo: fromVideoState=%s, toVideoState=%s", (Object[])new Object[]{VideoProfile.videoStateToString((int)videoProfile.getVideoState()), VideoProfile.videoStateToString((int)videoProfile2.getVideoState())});
                this.mVideoCallProvider.sendSessionModifyRequest(videoProfile, videoProfile2);
            }
            catch (RemoteException remoteException) {}
        } else {
            Log.i((Object)((Object)this), (String)"pauseVideo: video already paused", (Object[])new Object[0]);
        }
    }

    public void registerForDataUsageUpdate(Handler handler, int n, Object object) {
        this.mDataUsageUpdateRegistrants.addUnique(handler, n, object);
    }

    public void removeImsVideoProviderCallback(ImsVideoProviderWrapperCallback imsVideoProviderWrapperCallback) {
        this.mCallbacks.remove(imsVideoProviderWrapperCallback);
    }

    public void resumeVideo(int n, int n2) {
        if (this.mVideoPauseTracker.shouldResumeVideoFor(n2)) {
            VideoProfile videoProfile = new VideoProfile(n);
            VideoProfile videoProfile2 = new VideoProfile(n & -5);
            try {
                Log.i((Object)((Object)this), (String)"resumeVideo: fromVideoState=%s, toVideoState=%s", (Object[])new Object[]{VideoProfile.videoStateToString((int)videoProfile.getVideoState()), VideoProfile.videoStateToString((int)videoProfile2.getVideoState())});
                this.mVideoCallProvider.sendSessionModifyRequest(videoProfile, videoProfile2);
            }
            catch (RemoteException remoteException) {}
        } else {
            Log.i((Object)((Object)this), (String)"resumeVideo: remaining paused (paused from other sources)", (Object[])new Object[0]);
        }
    }

    public void setIsVideoEnabled(boolean bl) {
        this.mIsVideoEnabled = bl;
    }

    public void setUseVideoPauseWorkaround(boolean bl) {
        this.mUseVideoPauseWorkaround = bl;
    }

    public void unregisterForDataUsageUpdate(Handler handler) {
        this.mDataUsageUpdateRegistrants.remove(handler);
    }

    public boolean wasVideoPausedFromSource(int n) {
        return this.mVideoPauseTracker.wasVideoPausedFromSource(n);
    }

    private final class ImsVideoCallCallback
    extends IImsVideoCallCallback.Stub {
        private ImsVideoCallCallback() {
        }

        public void changeCallDataUsage(long l) {
            ImsVideoCallProviderWrapper.this.mHandler.obtainMessage(5, (Object)l).sendToTarget();
        }

        public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) {
            ImsVideoCallProviderWrapper.this.mHandler.obtainMessage(6, (Object)cameraCapabilities).sendToTarget();
        }

        public void changePeerDimensions(int n, int n2) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = n;
            someArgs.arg2 = n2;
            ImsVideoCallProviderWrapper.this.mHandler.obtainMessage(4, (Object)someArgs).sendToTarget();
        }

        public void changeVideoQuality(int n) {
            ImsVideoCallProviderWrapper.this.mHandler.obtainMessage(7, n, 0).sendToTarget();
        }

        public void handleCallSessionEvent(int n) {
            ImsVideoCallProviderWrapper.this.mHandler.obtainMessage(3, (Object)n).sendToTarget();
        }

        public void receiveSessionModifyRequest(VideoProfile videoProfile) {
            ImsVideoCallProviderWrapper.this.mHandler.obtainMessage(1, (Object)videoProfile).sendToTarget();
        }

        public void receiveSessionModifyResponse(int n, VideoProfile videoProfile, VideoProfile videoProfile2) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = n;
            someArgs.arg2 = videoProfile;
            someArgs.arg3 = videoProfile2;
            ImsVideoCallProviderWrapper.this.mHandler.obtainMessage(2, (Object)someArgs).sendToTarget();
        }
    }

    public static interface ImsVideoProviderWrapperCallback {
        public void onReceiveSessionModifyResponse(int var1, VideoProfile var2, VideoProfile var3);
    }

}

