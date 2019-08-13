/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioRouting;
import android.os.Handler;

class NativeRoutingEventHandlerDelegate {
    private AudioRouting mAudioRouting;
    private Handler mHandler;
    private AudioRouting.OnRoutingChangedListener mOnRoutingChangedListener;

    NativeRoutingEventHandlerDelegate(AudioRouting audioRouting, AudioRouting.OnRoutingChangedListener onRoutingChangedListener, Handler handler) {
        this.mAudioRouting = audioRouting;
        this.mOnRoutingChangedListener = onRoutingChangedListener;
        this.mHandler = handler;
    }

    void notifyClient() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.post(new Runnable(){

                @Override
                public void run() {
                    if (NativeRoutingEventHandlerDelegate.this.mOnRoutingChangedListener != null) {
                        NativeRoutingEventHandlerDelegate.this.mOnRoutingChangedListener.onRoutingChanged(NativeRoutingEventHandlerDelegate.this.mAudioRouting);
                    }
                }
            });
        }
    }

}

