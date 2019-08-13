/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.hardware.Sensor;

public final class TriggerEvent {
    public Sensor sensor;
    public long timestamp;
    public final float[] values;

    TriggerEvent(int n) {
        this.values = new float[n];
    }
}

