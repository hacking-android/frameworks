/*
 * Decompiled with CFR 0.145.
 */
package android.service.media;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

public abstract class CameraPrewarmService
extends Service {
    public static final String ACTION_PREWARM = "android.service.media.CameraPrewarmService.ACTION_PREWARM";
    public static final int MSG_CAMERA_FIRED = 1;
    private boolean mCameraIntentFired;
    private final Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message message) {
            if (message.what != 1) {
                super.handleMessage(message);
            } else {
                CameraPrewarmService.this.mCameraIntentFired = true;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        if (ACTION_PREWARM.equals(intent.getAction())) {
            this.onPrewarm();
            return new Messenger(this.mHandler).getBinder();
        }
        return null;
    }

    public abstract void onCooldown(boolean var1);

    public abstract void onPrewarm();

    @Override
    public boolean onUnbind(Intent intent) {
        if ("android.service.media.CameraPrewarmService.ACTION_PREWARM".equals(intent.getAction())) {
            this.onCooldown(this.mCameraIntentFired);
        }
        return false;
    }

}

