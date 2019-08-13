/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.telecom.VideoProfile;
import com.android.internal.os.SomeArgs;
import com.android.internal.telecom.IVideoCallback;

final class VideoCallbackServant {
    private static final int MSG_CHANGE_CALL_DATA_USAGE = 4;
    private static final int MSG_CHANGE_CAMERA_CAPABILITIES = 5;
    private static final int MSG_CHANGE_PEER_DIMENSIONS = 3;
    private static final int MSG_CHANGE_VIDEO_QUALITY = 6;
    private static final int MSG_HANDLE_CALL_SESSION_EVENT = 2;
    private static final int MSG_RECEIVE_SESSION_MODIFY_REQUEST = 0;
    private static final int MSG_RECEIVE_SESSION_MODIFY_RESPONSE = 1;
    private final IVideoCallback mDelegate;
    private final Handler mHandler = new Handler(){

        private void internalHandleMessage(Message object) throws RemoteException {
            switch (((Message)object).what) {
                default: {
                    break;
                }
                case 6: {
                    VideoCallbackServant.this.mDelegate.changeVideoQuality(((Message)object).arg1);
                    break;
                }
                case 5: {
                    VideoCallbackServant.this.mDelegate.changeCameraCapabilities((VideoProfile.CameraCapabilities)((Message)object).obj);
                    break;
                }
                case 4: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    try {
                        VideoCallbackServant.this.mDelegate.changeCallDataUsage((Long)someArgs.arg1);
                        break;
                    }
                    finally {
                        someArgs.recycle();
                    }
                }
                case 3: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        VideoCallbackServant.this.mDelegate.changePeerDimensions(((SomeArgs)object).argi1, ((SomeArgs)object).argi2);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                    }
                }
                case 2: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        VideoCallbackServant.this.mDelegate.handleCallSessionEvent(((SomeArgs)object).argi1);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                    }
                }
                case 1: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        VideoCallbackServant.this.mDelegate.receiveSessionModifyResponse(((SomeArgs)object).argi1, (VideoProfile)((SomeArgs)object).arg1, (VideoProfile)((SomeArgs)object).arg2);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                    }
                }
                case 0: {
                    VideoCallbackServant.this.mDelegate.receiveSessionModifyRequest((VideoProfile)((Message)object).obj);
                }
            }
        }

        @Override
        public void handleMessage(Message message) {
            try {
                this.internalHandleMessage(message);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    };
    private final IVideoCallback mStub = new IVideoCallback.Stub(){

        @Override
        public void changeCallDataUsage(long l) throws RemoteException {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = l;
            VideoCallbackServant.this.mHandler.obtainMessage(4, someArgs).sendToTarget();
        }

        @Override
        public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) throws RemoteException {
            VideoCallbackServant.this.mHandler.obtainMessage(5, cameraCapabilities).sendToTarget();
        }

        @Override
        public void changePeerDimensions(int n, int n2) throws RemoteException {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.argi1 = n;
            someArgs.argi2 = n2;
            VideoCallbackServant.this.mHandler.obtainMessage(3, someArgs).sendToTarget();
        }

        @Override
        public void changeVideoQuality(int n) throws RemoteException {
            VideoCallbackServant.this.mHandler.obtainMessage(6, n, 0).sendToTarget();
        }

        @Override
        public void handleCallSessionEvent(int n) throws RemoteException {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.argi1 = n;
            VideoCallbackServant.this.mHandler.obtainMessage(2, someArgs).sendToTarget();
        }

        @Override
        public void receiveSessionModifyRequest(VideoProfile videoProfile) throws RemoteException {
            VideoCallbackServant.this.mHandler.obtainMessage(0, videoProfile).sendToTarget();
        }

        @Override
        public void receiveSessionModifyResponse(int n, VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.argi1 = n;
            someArgs.arg1 = videoProfile;
            someArgs.arg2 = videoProfile2;
            VideoCallbackServant.this.mHandler.obtainMessage(1, someArgs).sendToTarget();
        }
    };

    public VideoCallbackServant(IVideoCallback iVideoCallback) {
        this.mDelegate = iVideoCallback;
    }

    public IVideoCallback getStub() {
        return this.mStub;
    }

}

