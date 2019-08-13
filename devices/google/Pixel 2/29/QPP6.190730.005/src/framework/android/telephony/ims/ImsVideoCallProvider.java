/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telecom.VideoProfile;
import android.view.Surface;
import com.android.ims.internal.IImsVideoCallCallback;
import com.android.ims.internal.IImsVideoCallProvider;
import com.android.internal.os.SomeArgs;

@SystemApi
public abstract class ImsVideoCallProvider {
    private static final int MSG_REQUEST_CALL_DATA_USAGE = 10;
    private static final int MSG_REQUEST_CAMERA_CAPABILITIES = 9;
    private static final int MSG_SEND_SESSION_MODIFY_REQUEST = 7;
    private static final int MSG_SEND_SESSION_MODIFY_RESPONSE = 8;
    private static final int MSG_SET_CALLBACK = 1;
    private static final int MSG_SET_CAMERA = 2;
    private static final int MSG_SET_DEVICE_ORIENTATION = 5;
    private static final int MSG_SET_DISPLAY_SURFACE = 4;
    private static final int MSG_SET_PAUSE_IMAGE = 11;
    private static final int MSG_SET_PREVIEW_SURFACE = 3;
    private static final int MSG_SET_ZOOM = 6;
    private final ImsVideoCallProviderBinder mBinder = new ImsVideoCallProviderBinder();
    private IImsVideoCallCallback mCallback;
    private final Handler mProviderHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message object) {
            switch (((Message)object).what) {
                default: {
                    break;
                }
                case 11: {
                    ImsVideoCallProvider.this.onSetPauseImage((Uri)((Message)object).obj);
                    break;
                }
                case 10: {
                    ImsVideoCallProvider.this.onRequestCallDataUsage();
                    break;
                }
                case 9: {
                    ImsVideoCallProvider.this.onRequestCameraCapabilities();
                    break;
                }
                case 8: {
                    ImsVideoCallProvider.this.onSendSessionModifyResponse((VideoProfile)((Message)object).obj);
                    break;
                }
                case 7: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        VideoProfile videoProfile = (VideoProfile)((SomeArgs)object).arg1;
                        VideoProfile videoProfile2 = (VideoProfile)((SomeArgs)object).arg2;
                        ImsVideoCallProvider.this.onSendSessionModifyRequest(videoProfile, videoProfile2);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                    }
                }
                case 6: {
                    ImsVideoCallProvider.this.onSetZoom(((Float)((Message)object).obj).floatValue());
                    break;
                }
                case 5: {
                    ImsVideoCallProvider.this.onSetDeviceOrientation(((Message)object).arg1);
                    break;
                }
                case 4: {
                    ImsVideoCallProvider.this.onSetDisplaySurface((Surface)((Message)object).obj);
                    break;
                }
                case 3: {
                    ImsVideoCallProvider.this.onSetPreviewSurface((Surface)((Message)object).obj);
                    break;
                }
                case 2: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    try {
                        ImsVideoCallProvider.this.onSetCamera((String)someArgs.arg1);
                        ImsVideoCallProvider.this.onSetCamera((String)someArgs.arg1, someArgs.argi1);
                        break;
                    }
                    finally {
                        someArgs.recycle();
                    }
                }
                case 1: {
                    ImsVideoCallProvider.this.mCallback = (IImsVideoCallCallback)((Message)object).obj;
                }
            }
        }
    };

    public void changeCallDataUsage(long l) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.changeCallDataUsage(l);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.changeCameraCapabilities(cameraCapabilities);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public void changePeerDimensions(int n, int n2) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.changePeerDimensions(n, n2);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public void changeVideoQuality(int n) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.changeVideoQuality(n);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @UnsupportedAppUsage
    public final IImsVideoCallProvider getInterface() {
        return this.mBinder;
    }

    public void handleCallSessionEvent(int n) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.handleCallSessionEvent(n);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public abstract void onRequestCallDataUsage();

    public abstract void onRequestCameraCapabilities();

    public abstract void onSendSessionModifyRequest(VideoProfile var1, VideoProfile var2);

    public abstract void onSendSessionModifyResponse(VideoProfile var1);

    public abstract void onSetCamera(String var1);

    public void onSetCamera(String string2, int n) {
    }

    public abstract void onSetDeviceOrientation(int var1);

    public abstract void onSetDisplaySurface(Surface var1);

    public abstract void onSetPauseImage(Uri var1);

    public abstract void onSetPreviewSurface(Surface var1);

    public abstract void onSetZoom(float var1);

    public void receiveSessionModifyRequest(VideoProfile videoProfile) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.receiveSessionModifyRequest(videoProfile);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public void receiveSessionModifyResponse(int n, VideoProfile videoProfile, VideoProfile videoProfile2) {
        IImsVideoCallCallback iImsVideoCallCallback = this.mCallback;
        if (iImsVideoCallCallback != null) {
            try {
                iImsVideoCallCallback.receiveSessionModifyResponse(n, videoProfile, videoProfile2);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    private final class ImsVideoCallProviderBinder
    extends IImsVideoCallProvider.Stub {
        private ImsVideoCallProviderBinder() {
        }

        @Override
        public void requestCallDataUsage() {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(10).sendToTarget();
        }

        @Override
        public void requestCameraCapabilities() {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(9).sendToTarget();
        }

        @Override
        public void sendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = videoProfile;
            someArgs.arg2 = videoProfile2;
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(7, someArgs).sendToTarget();
        }

        @Override
        public void sendSessionModifyResponse(VideoProfile videoProfile) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(8, videoProfile).sendToTarget();
        }

        @Override
        public void setCallback(IImsVideoCallCallback iImsVideoCallCallback) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(1, iImsVideoCallCallback).sendToTarget();
        }

        @Override
        public void setCamera(String string2, int n) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = string2;
            someArgs.argi1 = n;
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(2, someArgs).sendToTarget();
        }

        @Override
        public void setDeviceOrientation(int n) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(5, n, 0).sendToTarget();
        }

        @Override
        public void setDisplaySurface(Surface surface) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(4, surface).sendToTarget();
        }

        @Override
        public void setPauseImage(Uri uri) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(11, uri).sendToTarget();
        }

        @Override
        public void setPreviewSurface(Surface surface) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(3, surface).sendToTarget();
        }

        @Override
        public void setZoom(float f) {
            ImsVideoCallProvider.this.mProviderHandler.obtainMessage(6, Float.valueOf(f)).sendToTarget();
        }
    }

}

