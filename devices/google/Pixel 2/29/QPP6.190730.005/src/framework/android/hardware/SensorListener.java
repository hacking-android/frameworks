/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

@Deprecated
public interface SensorListener {
    public void onAccuracyChanged(int var1, int var2);

    public void onSensorChanged(int var1, float[] var2);
}

