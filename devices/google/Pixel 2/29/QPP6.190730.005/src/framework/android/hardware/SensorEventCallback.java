/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;

public abstract class SensorEventCallback
implements SensorEventListener2 {
    @Override
    public void onAccuracyChanged(Sensor sensor, int n) {
    }

    @Override
    public void onFlushCompleted(Sensor sensor) {
    }

    public void onSensorAdditionalInfo(SensorAdditionalInfo sensorAdditionalInfo) {
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
    }
}

