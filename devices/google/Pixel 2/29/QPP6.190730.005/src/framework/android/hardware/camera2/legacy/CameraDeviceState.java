/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.legacy.RequestHolder;
import android.os.Handler;
import android.util.Log;

public class CameraDeviceState {
    private static final boolean DEBUG = false;
    public static final int NO_CAPTURE_ERROR = -1;
    private static final int STATE_CAPTURING = 4;
    private static final int STATE_CONFIGURING = 2;
    private static final int STATE_ERROR = 0;
    private static final int STATE_IDLE = 3;
    private static final int STATE_UNCONFIGURED = 1;
    private static final String TAG = "CameraDeviceState";
    private static final String[] sStateNames = new String[]{"ERROR", "UNCONFIGURED", "CONFIGURING", "IDLE", "CAPTURING"};
    private int mCurrentError = -1;
    private Handler mCurrentHandler = null;
    private CameraDeviceStateListener mCurrentListener = null;
    private RequestHolder mCurrentRequest = null;
    private int mCurrentState = 1;

    private void doStateTransition(int n) {
        this.doStateTransition(n, 0L, -1);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void doStateTransition(int n, final long l, final int n2) {
        Object object;
        if (n != this.mCurrentState) {
            CharSequence charSequence = "UNKNOWN";
            object = charSequence;
            if (n >= 0) {
                String[] arrstring = sStateNames;
                object = charSequence;
                if (n < arrstring.length) {
                    object = arrstring[n];
                }
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Legacy camera service transitioning to state ");
            ((StringBuilder)charSequence).append((String)object);
            Log.i(TAG, ((StringBuilder)charSequence).toString());
        }
        if (n != 0 && n != 3 && this.mCurrentState != n && (object = this.mCurrentHandler) != null && this.mCurrentListener != null) {
            ((Handler)object).post(new Runnable(){

                @Override
                public void run() {
                    CameraDeviceState.this.mCurrentListener.onBusy();
                }
            });
        }
        if (n != 0) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Transition to unknown state: ");
                        ((StringBuilder)object).append(n);
                        throw new IllegalStateException(((StringBuilder)object).toString());
                    }
                    n = this.mCurrentState;
                    if (n != 3 && n != 4) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Cannot call capture while in state: ");
                        ((StringBuilder)object).append(this.mCurrentState);
                        Log.e(TAG, ((StringBuilder)object).toString());
                        this.mCurrentError = 1;
                        this.doStateTransition(0);
                        return;
                    }
                    object = this.mCurrentHandler;
                    if (object != null && this.mCurrentListener != null) {
                        if (n2 != -1) {
                            ((Handler)object).post(new Runnable(){

                                @Override
                                public void run() {
                                    CameraDeviceState.this.mCurrentListener.onError(n2, null, CameraDeviceState.this.mCurrentRequest);
                                }
                            });
                        } else {
                            ((Handler)object).post(new Runnable(){

                                @Override
                                public void run() {
                                    CameraDeviceState.this.mCurrentListener.onCaptureStarted(CameraDeviceState.this.mCurrentRequest, l);
                                }
                            });
                        }
                    }
                    this.mCurrentState = 4;
                    return;
                }
                n = this.mCurrentState;
                if (n == 3) {
                    return;
                }
                if (n != 2 && n != 4) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Cannot call idle while in state: ");
                    ((StringBuilder)object).append(this.mCurrentState);
                    Log.e(TAG, ((StringBuilder)object).toString());
                    this.mCurrentError = 1;
                    this.doStateTransition(0);
                    return;
                }
                if (this.mCurrentState != 3 && (object = this.mCurrentHandler) != null && this.mCurrentListener != null) {
                    ((Handler)object).post(new Runnable(){

                        @Override
                        public void run() {
                            CameraDeviceState.this.mCurrentListener.onIdle();
                        }
                    });
                }
                this.mCurrentState = 3;
                return;
            }
            n = this.mCurrentState;
            if (n != 1 && n != 3) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot call configure while in state: ");
                ((StringBuilder)object).append(this.mCurrentState);
                Log.e(TAG, ((StringBuilder)object).toString());
                this.mCurrentError = 1;
                this.doStateTransition(0);
                return;
            }
            if (this.mCurrentState != 2 && (object = this.mCurrentHandler) != null && this.mCurrentListener != null) {
                ((Handler)object).post(new Runnable(){

                    @Override
                    public void run() {
                        CameraDeviceState.this.mCurrentListener.onConfiguring();
                    }
                });
            }
            this.mCurrentState = 2;
            return;
        }
        if (this.mCurrentState != 0 && (object = this.mCurrentHandler) != null && this.mCurrentListener != null) {
            ((Handler)object).post(new Runnable(){

                @Override
                public void run() {
                    CameraDeviceState.this.mCurrentListener.onError(CameraDeviceState.this.mCurrentError, null, CameraDeviceState.this.mCurrentRequest);
                }
            });
        }
        this.mCurrentState = 0;
    }

    public void setCameraDeviceCallbacks(Handler handler, CameraDeviceStateListener cameraDeviceStateListener) {
        synchronized (this) {
            this.mCurrentHandler = handler;
            this.mCurrentListener = cameraDeviceStateListener;
            return;
        }
    }

    public boolean setCaptureResult(RequestHolder requestHolder, CameraMetadataNative cameraMetadataNative) {
        synchronized (this) {
            boolean bl = this.setCaptureResult(requestHolder, cameraMetadataNative, -1, null);
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean setCaptureResult(RequestHolder object, CameraMetadataNative runnable, int n, Object runnable2) {
        synchronized (this) {
            int n2;
            int n3 = this.mCurrentState;
            boolean bl = true;
            boolean bl2 = true;
            if (n3 != 4) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot receive result while in state: ");
                ((StringBuilder)object).append(this.mCurrentState);
                Log.e(TAG, ((StringBuilder)object).toString());
                this.mCurrentError = 1;
                this.doStateTransition(0);
                n2 = this.mCurrentError;
                if (n2 != -1) return false;
                return bl2;
            }
            if (this.mCurrentHandler != null && this.mCurrentListener != null) {
                Runnable runnable3;
                Runnable runnable4;
                if (n2 != -1) {
                    Handler handler = this.mCurrentHandler;
                    runnable4 = new Runnable((RequestHolder)object){
                        final /* synthetic */ RequestHolder val$request;
                        {
                            this.val$request = requestHolder;
                        }

                        @Override
                        public void run() {
                            CameraDeviceState.this.mCurrentListener.onError(n2, runnable3, this.val$request);
                        }
                    };
                    handler.post(runnable4);
                } else {
                    Handler handler = this.mCurrentHandler;
                    runnable3 = new Runnable((CameraMetadataNative)((Object)runnable4), (RequestHolder)object){
                        final /* synthetic */ RequestHolder val$request;
                        final /* synthetic */ CameraMetadataNative val$result;
                        {
                            this.val$result = cameraMetadataNative;
                            this.val$request = requestHolder;
                        }

                        @Override
                        public void run() {
                            CameraDeviceState.this.mCurrentListener.onCaptureResult(this.val$result, this.val$request);
                        }
                    };
                    handler.post(runnable3);
                }
            }
            if ((n2 = this.mCurrentError) != -1) return false;
            return bl;
        }
    }

    public boolean setCaptureStart(RequestHolder requestHolder, long l, int n) {
        synchronized (this) {
            this.mCurrentRequest = requestHolder;
            this.doStateTransition(4, l, n);
            n = this.mCurrentError;
            boolean bl = n == -1;
            return bl;
        }
    }

    public boolean setConfiguring() {
        synchronized (this) {
            this.doStateTransition(2);
            int n = this.mCurrentError;
            boolean bl = n == -1;
            return bl;
        }
    }

    public void setError(int n) {
        synchronized (this) {
            this.mCurrentError = n;
            this.doStateTransition(0);
            return;
        }
    }

    public boolean setIdle() {
        synchronized (this) {
            this.doStateTransition(3);
            int n = this.mCurrentError;
            boolean bl = n == -1;
            return bl;
        }
    }

    public void setRepeatingRequestError(final long l, final int n) {
        synchronized (this) {
            Handler handler = this.mCurrentHandler;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    CameraDeviceState.this.mCurrentListener.onRepeatingRequestError(l, n);
                }
            };
            handler.post(runnable);
            return;
        }
    }

    public void setRequestQueueEmpty() {
        synchronized (this) {
            Handler handler = this.mCurrentHandler;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    CameraDeviceState.this.mCurrentListener.onRequestQueueEmpty();
                }
            };
            handler.post(runnable);
            return;
        }
    }

    public static interface CameraDeviceStateListener {
        public void onBusy();

        public void onCaptureResult(CameraMetadataNative var1, RequestHolder var2);

        public void onCaptureStarted(RequestHolder var1, long var2);

        public void onConfiguring();

        public void onError(int var1, Object var2, RequestHolder var3);

        public void onIdle();

        public void onRepeatingRequestError(long var1, int var3);

        public void onRequestQueueEmpty();
    }

}

