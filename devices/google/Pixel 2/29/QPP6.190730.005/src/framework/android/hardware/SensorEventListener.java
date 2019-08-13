/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

public interface SensorEventListener {
    public void onAccuracyChanged(Sensor var1, int var2);

    public void onSensorChanged(SensorEvent var1);
}

