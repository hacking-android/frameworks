/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiofx;

import android.app.ActivityThread;
import android.media.audiofx.DefaultEffect;
import android.util.Log;
import java.io.Serializable;
import java.util.UUID;

public class StreamDefaultEffect
extends DefaultEffect {
    private static final String TAG = "StreamDefaultEffect-JAVA";

    static {
        System.loadLibrary("audioeffect_jni");
    }

    public StreamDefaultEffect(UUID uUID, UUID serializable, int n, int n2) {
        int[] arrn = new int[1];
        n = this.native_setup(uUID.toString(), ((UUID)serializable).toString(), n, n2, ActivityThread.currentOpPackageName(), arrn);
        if (n != 0) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Error code ");
            ((StringBuilder)serializable).append(n);
            ((StringBuilder)serializable).append(" when initializing StreamDefaultEffect");
            Log.e(TAG, ((StringBuilder)serializable).toString());
            if (n != -5) {
                if (n != -4) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Cannot initialize effect engine for type: ");
                    ((StringBuilder)serializable).append(uUID);
                    ((StringBuilder)serializable).append(" Error: ");
                    ((StringBuilder)serializable).append(n);
                    throw new RuntimeException(((StringBuilder)serializable).toString());
                }
                throw new IllegalArgumentException("Stream usage, type uuid, or implementation uuid not supported.");
            }
            throw new UnsupportedOperationException("Effect library not loaded");
        }
        this.mId = arrn[0];
    }

    private final native void native_release(int var1);

    private final native int native_setup(String var1, String var2, int var3, int var4, String var5, int[] var6);

    protected void finalize() {
        this.release();
    }

    public void release() {
        this.native_release(this.mId);
    }
}

