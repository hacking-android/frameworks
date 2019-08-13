/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.OrientationListener;

public abstract class OrientationEventListener {
    private static final boolean DEBUG = false;
    public static final int ORIENTATION_UNKNOWN = -1;
    private static final String TAG = "OrientationEventListener";
    private static final boolean localLOGV = false;
    private boolean mEnabled = false;
    private OrientationListener mOldListener;
    private int mOrientation = -1;
    private int mRate;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener;
    private SensorManager mSensorManager;

    public OrientationEventListener(Context context) {
        this(context, 3);
    }

    public OrientationEventListener(Context context, int n) {
        this.mSensorManager = (SensorManager)context.getSystemService("sensor");
        this.mRate = n;
        this.mSensor = this.mSensorManager.getDefaultSensor(1);
        if (this.mSensor != null) {
            this.mSensorEventListener = new SensorEventListenerImpl();
        }
    }

    public boolean canDetectOrientation() {
        boolean bl = this.mSensor != null;
        return bl;
    }

    public void disable() {
        if (this.mSensor == null) {
            Log.w(TAG, "Cannot detect sensors. Invalid disable");
            return;
        }
        if (this.mEnabled) {
            this.mSensorManager.unregisterListener(this.mSensorEventListener);
            this.mEnabled = false;
        }
    }

    public void enable() {
        Sensor sensor = this.mSensor;
        if (sensor == null) {
            Log.w(TAG, "Cannot detect sensors. Not enabled");
            return;
        }
        if (!this.mEnabled) {
            this.mSensorManager.registerListener(this.mSensorEventListener, sensor, this.mRate);
            this.mEnabled = true;
        }
    }

    public abstract void onOrientationChanged(int var1);

    void registerListener(OrientationListener orientationListener) {
        this.mOldListener = orientationListener;
    }

    class SensorEventListenerImpl
    implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        SensorEventListenerImpl() {
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int n) {
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] arrf = sensorEvent.values;
            int n = -1;
            float f = -arrf[0];
            float f2 = -arrf[1];
            float f3 = -arrf[2];
            if (4.0f * (f * f + f2 * f2) >= f3 * f3) {
                int n2;
                n = 90 - Math.round((float)Math.atan2(-f2, f) * 57.29578f);
                do {
                    n2 = n;
                    if (n < 360) break;
                    n -= 360;
                } while (true);
                do {
                    n = n2;
                    if (n2 >= 0) break;
                    n2 += 360;
                } while (true);
            }
            if (OrientationEventListener.this.mOldListener != null) {
                OrientationEventListener.this.mOldListener.onSensorChanged(1, sensorEvent.values);
            }
            if (n != OrientationEventListener.this.mOrientation) {
                OrientationEventListener.this.mOrientation = n;
                OrientationEventListener.this.onOrientationChanged(n);
            }
        }
    }

}

