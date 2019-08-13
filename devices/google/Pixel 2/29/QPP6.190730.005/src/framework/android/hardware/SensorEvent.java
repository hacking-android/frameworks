/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.annotation.UnsupportedAppUsage;
import android.hardware.Sensor;

public class SensorEvent {
    public int accuracy;
    public Sensor sensor;
    public long timestamp;
    public final float[] values;

    @UnsupportedAppUsage
    SensorEvent(int n) {
        this.values = new float[n];
    }
}

