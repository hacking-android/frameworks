/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.view.IRotationWatcher;
import android.view.IWindowManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

final class LegacySensorManager {
    private static boolean sInitialized;
    private static int sRotation;
    private static IWindowManager sWindowManager;
    private final HashMap<SensorListener, LegacyListener> mLegacyListenersMap = new HashMap();
    private final SensorManager mSensorManager;

    static {
        sRotation = 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public LegacySensorManager(SensorManager stub) {
        this.mSensorManager = stub;
        synchronized (SensorManager.class) {
            if (!sInitialized && (stub = (sWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window")))) != null) {
                try {
                    IWindowManager iWindowManager = sWindowManager;
                    stub = new IRotationWatcher.Stub(){

                        @Override
                        public void onRotationChanged(int n) {
                            LegacySensorManager.onRotationChanged(n);
                        }
                    };
                    sRotation = iWindowManager.watchRotation(stub, 0);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static int getRotation() {
        synchronized (SensorManager.class) {
            return sRotation;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void onRotationChanged(int n) {
        synchronized (SensorManager.class) {
            sRotation = n;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean registerLegacyListener(int n, int n2, SensorListener sensorListener, int n3, int n4) {
        boolean bl;
        boolean bl2 = bl = false;
        if ((n3 & n) == 0) return bl2;
        Sensor sensor = this.mSensorManager.getDefaultSensor(n2);
        bl2 = bl;
        if (sensor == null) return bl2;
        HashMap<SensorListener, LegacyListener> hashMap = this.mLegacyListenersMap;
        synchronized (hashMap) {
            LegacyListener legacyListener;
            LegacyListener legacyListener2 = legacyListener = this.mLegacyListenersMap.get(sensorListener);
            if (legacyListener == null) {
                legacyListener2 = new LegacyListener(sensorListener);
                this.mLegacyListenersMap.put(sensorListener, legacyListener2);
            }
            if (!legacyListener2.registerSensor(n)) return true;
            return this.mSensorManager.registerListener(legacyListener2, sensor, n4);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void unregisterLegacyListener(int n, int n2, SensorListener sensorListener, int n3) {
        if ((n3 & n) == 0) return;
        Sensor sensor = this.mSensorManager.getDefaultSensor(n2);
        if (sensor == null) return;
        HashMap<SensorListener, LegacyListener> hashMap = this.mLegacyListenersMap;
        synchronized (hashMap) {
            LegacyListener legacyListener = this.mLegacyListenersMap.get(sensorListener);
            if (legacyListener == null) return;
            if (!legacyListener.unregisterSensor(n)) return;
            this.mSensorManager.unregisterListener(legacyListener, sensor);
            if (legacyListener.hasSensors()) return;
            this.mLegacyListenersMap.remove(sensorListener);
            return;
        }
    }

    public int getSensors() {
        int n = 0;
        Iterator<Sensor> iterator = this.mSensorManager.getFullSensorList().iterator();
        while (iterator.hasNext()) {
            int n2 = iterator.next().getType();
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) continue;
                    n |= 129;
                    continue;
                }
                n |= 8;
                continue;
            }
            n |= 2;
        }
        return n;
    }

    public boolean registerListener(SensorListener sensorListener, int n, int n2) {
        boolean bl = false;
        if (sensorListener == null) {
            return false;
        }
        boolean bl2 = this.registerLegacyListener(2, 1, sensorListener, n, n2) || false;
        bl2 = this.registerLegacyListener(8, 2, sensorListener, n, n2) || bl2;
        bl2 = this.registerLegacyListener(128, 3, sensorListener, n, n2) || bl2;
        bl2 = this.registerLegacyListener(1, 3, sensorListener, n, n2) || bl2;
        if (this.registerLegacyListener(4, 7, sensorListener, n, n2) || bl2) {
            bl = true;
        }
        return bl;
    }

    public void unregisterListener(SensorListener sensorListener, int n) {
        if (sensorListener == null) {
            return;
        }
        this.unregisterLegacyListener(2, 1, sensorListener, n);
        this.unregisterLegacyListener(8, 2, sensorListener, n);
        this.unregisterLegacyListener(128, 3, sensorListener, n);
        this.unregisterLegacyListener(1, 3, sensorListener, n);
        this.unregisterLegacyListener(4, 7, sensorListener, n);
    }

    private static final class LegacyListener
    implements SensorEventListener {
        private int mSensors;
        private SensorListener mTarget;
        private float[] mValues = new float[6];
        private final LmsFilter mYawfilter = new LmsFilter();

        LegacyListener(SensorListener sensorListener) {
            this.mTarget = sensorListener;
            this.mSensors = 0;
        }

        private static int getLegacySensorType(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 7) {
                            return 0;
                        }
                        return 4;
                    }
                    return 128;
                }
                return 8;
            }
            return 2;
        }

        private static boolean hasOrientationSensor(int n) {
            boolean bl = (n & 129) != 0;
            return bl;
        }

        private void mapSensorDataToWindow(int n, float[] arrf, int n2) {
            block7 : {
                float f;
                float f2;
                float f3;
                block8 : {
                    block9 : {
                        block4 : {
                            block5 : {
                                block6 : {
                                    block3 : {
                                        block0 : {
                                            block1 : {
                                                block2 : {
                                                    f2 = arrf[0];
                                                    f = arrf[1];
                                                    f3 = arrf[2];
                                                    if (n == 1) break block0;
                                                    if (n == 2) break block1;
                                                    if (n == 8) break block2;
                                                    if (n == 128) break block0;
                                                    break block3;
                                                }
                                                f2 = -f2;
                                                f = -f;
                                                break block3;
                                            }
                                            f2 = -f2;
                                            f = -f;
                                            f3 = -f3;
                                            break block3;
                                        }
                                        f3 = -f3;
                                    }
                                    arrf[0] = f2;
                                    arrf[1] = f;
                                    arrf[2] = f3;
                                    arrf[3] = f2;
                                    arrf[4] = f;
                                    arrf[5] = f3;
                                    if ((n2 & 1) == 0) break block4;
                                    if (n == 1) break block5;
                                    if (n == 2 || n == 8) break block6;
                                    if (n == 128) break block5;
                                    break block4;
                                }
                                arrf[0] = -f;
                                arrf[1] = f2;
                                arrf[2] = f3;
                                break block4;
                            }
                            int n3 = f2 < 270.0f ? 90 : -270;
                            arrf[0] = (float)n3 + f2;
                            arrf[1] = f3;
                            arrf[2] = f;
                        }
                        if ((n2 & 2) == 0) break block7;
                        f2 = arrf[0];
                        f3 = arrf[1];
                        f = arrf[2];
                        if (n == 1) break block8;
                        if (n == 2 || n == 8) break block9;
                        if (n == 128) break block8;
                        break block7;
                    }
                    arrf[0] = -f2;
                    arrf[1] = -f3;
                    arrf[2] = f;
                    break block7;
                }
                f2 = f2 >= 180.0f ? (f2 -= 180.0f) : 180.0f + f2;
                arrf[0] = f2;
                arrf[1] = -f3;
                arrf[2] = -f;
            }
        }

        boolean hasSensors() {
            boolean bl = this.mSensors != 0;
            return bl;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int n) {
            try {
                this.mTarget.onAccuracyChanged(LegacyListener.getLegacySensorType(sensor.getType()), n);
            }
            catch (AbstractMethodError abstractMethodError) {
                // empty catch block
            }
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] arrf = this.mValues;
            arrf[0] = sensorEvent.values[0];
            arrf[1] = sensorEvent.values[1];
            arrf[2] = sensorEvent.values[2];
            int n = sensorEvent.sensor.getType();
            int n2 = LegacyListener.getLegacySensorType(n);
            this.mapSensorDataToWindow(n2, arrf, LegacySensorManager.getRotation());
            if (n == 3) {
                if ((this.mSensors & 128) != 0) {
                    this.mTarget.onSensorChanged(128, arrf);
                }
                if ((this.mSensors & 1) != 0) {
                    arrf[0] = this.mYawfilter.filter(sensorEvent.timestamp, arrf[0]);
                    this.mTarget.onSensorChanged(1, arrf);
                }
            } else {
                this.mTarget.onSensorChanged(n2, arrf);
            }
        }

        boolean registerSensor(int n) {
            int n2 = this.mSensors;
            if ((n2 & n) != 0) {
                return false;
            }
            boolean bl = LegacyListener.hasOrientationSensor(n2);
            this.mSensors |= n;
            return !bl || !LegacyListener.hasOrientationSensor(n);
        }

        boolean unregisterSensor(int n) {
            int n2 = this.mSensors;
            if ((n2 & n) == 0) {
                return false;
            }
            this.mSensors = n2 & n;
            return !LegacyListener.hasOrientationSensor(n) || !LegacyListener.hasOrientationSensor(this.mSensors);
        }
    }

    private static final class LmsFilter {
        private static final int COUNT = 12;
        private static final float PREDICTION_RATIO = 0.33333334f;
        private static final float PREDICTION_TIME = 0.08f;
        private static final int SENSORS_RATE_MS = 20;
        private int mIndex = 12;
        private long[] mT = new long[24];
        private float[] mV = new float[24];

        public float filter(long l, float f) {
            float f2 = f;
            float f3 = this.mV[this.mIndex];
            if (f2 - f3 > 180.0f) {
                f = f2 - 360.0f;
            } else {
                f = f2;
                if (f3 - f2 > 180.0f) {
                    f = f2 + 360.0f;
                }
            }
            ++this.mIndex;
            if (this.mIndex >= 24) {
                this.mIndex = 12;
            }
            float[] arrf = this.mV;
            int n = this.mIndex;
            arrf[n] = f;
            long[] arrl = this.mT;
            arrl[n] = l;
            arrf[n - 12] = f;
            arrl[n - 12] = l;
            f = 0.0f;
            float f4 = 0.0f;
            f2 = 0.0f;
            float f5 = 0.0f;
            f3 = 0.0f;
            for (n = 0; n < 11; ++n) {
                int n2 = this.mIndex - 1 - n;
                float f6 = this.mV[n2];
                arrf = this.mT;
                float f7 = (float)(arrf[n2] / 2L + arrf[n2 + 1] / 2L - l) * 1.0E-9f;
                float f8 = (float)(arrf[n2] - arrf[n2 + 1]) * 1.0E-9f;
                f8 *= f8;
                f3 += f6 * f8;
                f5 += f7 * f8 * f7;
                f2 += f7 * f8;
                f4 += f7 * f8 * f6;
                f += f8;
            }
            f4 = (f3 * f5 + f2 * f4) / (f * f5 + f2 * f2);
            f3 = (f2 = (0.08f * ((f * f4 - f3) / f2) + f4) * 0.0027777778f) >= 0.0f ? f2 : -f2;
            f = f2;
            if (f3 >= 0.5f) {
                f = f2 - (float)Math.ceil(0.5f + f2) + 1.0f;
            }
            f2 = f;
            if (f < 0.0f) {
                f2 = f + 1.0f;
            }
            return f2 * 360.0f;
        }
    }

}

