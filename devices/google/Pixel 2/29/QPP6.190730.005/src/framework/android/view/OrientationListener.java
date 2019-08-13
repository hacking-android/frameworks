/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.Context;
import android.hardware.SensorListener;
import android.view.OrientationEventListener;

@Deprecated
public abstract class OrientationListener
implements SensorListener {
    public static final int ORIENTATION_UNKNOWN = -1;
    private OrientationEventListener mOrientationEventLis;

    public OrientationListener(Context context) {
        this.mOrientationEventLis = new OrientationEventListenerInternal(context);
    }

    public OrientationListener(Context context, int n) {
        this.mOrientationEventLis = new OrientationEventListenerInternal(context, n);
    }

    public void disable() {
        this.mOrientationEventLis.disable();
    }

    public void enable() {
        this.mOrientationEventLis.enable();
    }

    @Override
    public void onAccuracyChanged(int n, int n2) {
    }

    public abstract void onOrientationChanged(int var1);

    @Override
    public void onSensorChanged(int n, float[] arrf) {
    }

    class OrientationEventListenerInternal
    extends OrientationEventListener {
        OrientationEventListenerInternal(Context context) {
            super(context);
        }

        OrientationEventListenerInternal(Context context, int n) {
            super(context, n);
            this.registerListener(OrientationListener.this);
        }

        @Override
        public void onOrientationChanged(int n) {
            OrientationListener.this.onOrientationChanged(n);
        }
    }

}

