/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.annotation.SystemApi;
import android.hardware.HardwareBuffer;
import android.hardware.LegacySensorManager;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.TriggerEventListener;
import android.os.Handler;
import android.os.MemoryFile;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class SensorManager {
    public static final int AXIS_MINUS_X = 129;
    public static final int AXIS_MINUS_Y = 130;
    public static final int AXIS_MINUS_Z = 131;
    public static final int AXIS_X = 1;
    public static final int AXIS_Y = 2;
    public static final int AXIS_Z = 3;
    @Deprecated
    public static final int DATA_X = 0;
    @Deprecated
    public static final int DATA_Y = 1;
    @Deprecated
    public static final int DATA_Z = 2;
    public static final float GRAVITY_DEATH_STAR_I = 3.5303614E-7f;
    public static final float GRAVITY_EARTH = 9.80665f;
    public static final float GRAVITY_JUPITER = 23.12f;
    public static final float GRAVITY_MARS = 3.71f;
    public static final float GRAVITY_MERCURY = 3.7f;
    public static final float GRAVITY_MOON = 1.6f;
    public static final float GRAVITY_NEPTUNE = 11.0f;
    public static final float GRAVITY_PLUTO = 0.6f;
    public static final float GRAVITY_SATURN = 8.96f;
    public static final float GRAVITY_SUN = 275.0f;
    public static final float GRAVITY_THE_ISLAND = 4.815162f;
    public static final float GRAVITY_URANUS = 8.69f;
    public static final float GRAVITY_VENUS = 8.87f;
    public static final float LIGHT_CLOUDY = 100.0f;
    public static final float LIGHT_FULLMOON = 0.25f;
    public static final float LIGHT_NO_MOON = 0.001f;
    public static final float LIGHT_OVERCAST = 10000.0f;
    public static final float LIGHT_SHADE = 20000.0f;
    public static final float LIGHT_SUNLIGHT = 110000.0f;
    public static final float LIGHT_SUNLIGHT_MAX = 120000.0f;
    public static final float LIGHT_SUNRISE = 400.0f;
    public static final float MAGNETIC_FIELD_EARTH_MAX = 60.0f;
    public static final float MAGNETIC_FIELD_EARTH_MIN = 30.0f;
    public static final float PRESSURE_STANDARD_ATMOSPHERE = 1013.25f;
    @Deprecated
    public static final int RAW_DATA_INDEX = 3;
    @Deprecated
    public static final int RAW_DATA_X = 3;
    @Deprecated
    public static final int RAW_DATA_Y = 4;
    @Deprecated
    public static final int RAW_DATA_Z = 5;
    @Deprecated
    public static final int SENSOR_ACCELEROMETER = 2;
    @Deprecated
    public static final int SENSOR_ALL = 127;
    public static final int SENSOR_DELAY_FASTEST = 0;
    public static final int SENSOR_DELAY_GAME = 1;
    public static final int SENSOR_DELAY_NORMAL = 3;
    public static final int SENSOR_DELAY_UI = 2;
    @Deprecated
    public static final int SENSOR_LIGHT = 16;
    @Deprecated
    public static final int SENSOR_MAGNETIC_FIELD = 8;
    @Deprecated
    public static final int SENSOR_MAX = 64;
    @Deprecated
    public static final int SENSOR_MIN = 1;
    @Deprecated
    public static final int SENSOR_ORIENTATION = 1;
    @Deprecated
    public static final int SENSOR_ORIENTATION_RAW = 128;
    @Deprecated
    public static final int SENSOR_PROXIMITY = 32;
    public static final int SENSOR_STATUS_ACCURACY_HIGH = 3;
    public static final int SENSOR_STATUS_ACCURACY_LOW = 1;
    public static final int SENSOR_STATUS_ACCURACY_MEDIUM = 2;
    public static final int SENSOR_STATUS_NO_CONTACT = -1;
    public static final int SENSOR_STATUS_UNRELIABLE = 0;
    @Deprecated
    public static final int SENSOR_TEMPERATURE = 4;
    @Deprecated
    public static final int SENSOR_TRICORDER = 64;
    public static final float STANDARD_GRAVITY = 9.80665f;
    protected static final String TAG = "SensorManager";
    private static final float[] sTempMatrix = new float[16];
    private LegacySensorManager mLegacySensorManager;
    private final SparseArray<List<Sensor>> mSensorListByType = new SparseArray();

    public static float getAltitude(float f, float f2) {
        return (1.0f - (float)Math.pow(f2 / f, 0.19029495120048523)) * 44330.0f;
    }

    public static void getAngleChange(float[] arrf, float[] arrf2, float[] arrf3) {
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = 0.0f;
        float f10 = 0.0f;
        float f11 = 0.0f;
        float f12 = 0.0f;
        float f13 = 0.0f;
        float f14 = 0.0f;
        float f15 = 0.0f;
        float f16 = 0.0f;
        float f17 = 0.0f;
        float f18 = 0.0f;
        if (arrf2.length == 9) {
            f = arrf2[0];
            f2 = arrf2[1];
            f3 = arrf2[2];
            f4 = arrf2[3];
            f5 = arrf2[4];
            f6 = arrf2[5];
            f7 = arrf2[6];
            f8 = arrf2[7];
            f9 = arrf2[8];
        } else if (arrf2.length == 16) {
            f = arrf2[0];
            f2 = arrf2[1];
            f3 = arrf2[2];
            f4 = arrf2[4];
            f5 = arrf2[5];
            f6 = arrf2[6];
            f7 = arrf2[8];
            f8 = arrf2[9];
            f9 = arrf2[10];
        }
        if (arrf3.length == 9) {
            f10 = arrf3[0];
            f11 = arrf3[1];
            f12 = arrf3[2];
            f13 = arrf3[3];
            f14 = arrf3[4];
            f15 = arrf3[5];
            f16 = arrf3[6];
            f17 = arrf3[7];
            f18 = arrf3[8];
        } else if (arrf3.length == 16) {
            f10 = arrf3[0];
            f11 = arrf3[1];
            f12 = arrf3[2];
            f13 = arrf3[4];
            f14 = arrf3[5];
            f15 = arrf3[6];
            f16 = arrf3[8];
            f17 = arrf3[9];
            f18 = arrf3[10];
        }
        arrf[0] = (float)Math.atan2(f10 * f2 + f13 * f5 + f16 * f8, f11 * f2 + f14 * f5 + f17 * f8);
        arrf[1] = (float)Math.asin(-(f12 * f2 + f15 * f5 + f18 * f8));
        arrf[2] = (float)Math.atan2(-(f12 * f + f15 * f4 + f18 * f7), f12 * f3 + f15 * f6 + f18 * f9);
    }

    private static int getDelay(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        n = 200000;
                    }
                } else {
                    n = 66667;
                }
            } else {
                n = 20000;
            }
        } else {
            n = 0;
        }
        return n;
    }

    public static float getInclination(float[] arrf) {
        if (arrf.length == 9) {
            return (float)Math.atan2(arrf[5], arrf[4]);
        }
        return (float)Math.atan2(arrf[6], arrf[5]);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private LegacySensorManager getLegacySensorManager() {
        SparseArray<List<Sensor>> sparseArray = this.mSensorListByType;
        synchronized (sparseArray) {
            LegacySensorManager legacySensorManager;
            if (this.mLegacySensorManager != null) return this.mLegacySensorManager;
            Log.i(TAG, "This application is using deprecated SensorManager API which will be removed someday.  Please consider switching to the new API.");
            this.mLegacySensorManager = legacySensorManager = new LegacySensorManager(this);
            return this.mLegacySensorManager;
        }
    }

    public static float[] getOrientation(float[] arrf, float[] arrf2) {
        if (arrf.length == 9) {
            arrf2[0] = (float)Math.atan2(arrf[1], arrf[4]);
            arrf2[1] = (float)Math.asin(-arrf[7]);
            arrf2[2] = (float)Math.atan2(-arrf[6], arrf[8]);
        } else {
            arrf2[0] = (float)Math.atan2(arrf[1], arrf[5]);
            arrf2[1] = (float)Math.asin(-arrf[9]);
            arrf2[2] = (float)Math.atan2(-arrf[8], arrf[10]);
        }
        return arrf2;
    }

    public static void getQuaternionFromVector(float[] arrf, float[] arrf2) {
        if (arrf2.length >= 4) {
            arrf[0] = arrf2[3];
        } else {
            arrf[0] = 1.0f - arrf2[0] * arrf2[0] - arrf2[1] * arrf2[1] - arrf2[2] * arrf2[2];
            float f = arrf[0];
            float f2 = 0.0f;
            if (f > 0.0f) {
                f2 = (float)Math.sqrt(arrf[0]);
            }
            arrf[0] = f2;
        }
        arrf[1] = arrf2[0];
        arrf[2] = arrf2[1];
        arrf[3] = arrf2[2];
    }

    public static boolean getRotationMatrix(float[] arrf, float[] arrf2, float[] arrf3, float[] arrf4) {
        block6 : {
            float f;
            float f2;
            block7 : {
                float f3 = arrf3[0];
                float f4 = arrf3[1];
                float f5 = arrf3[2];
                if (f3 * f3 + f4 * f4 + f5 * f5 < 0.96236104f) {
                    return false;
                }
                float f6 = arrf4[1];
                float f7 = arrf4[2];
                float f8 = f6 * f5 - f7 * f4;
                f2 = arrf4[0];
                f = f7 * f3 - f2 * f5;
                float f9 = f2 * f4 - f6 * f3;
                float f10 = (float)Math.sqrt(f8 * f8 + f * f + f9 * f9);
                if (f10 < 0.1f) {
                    return false;
                }
                f10 = 1.0f / f10;
                f8 *= f10;
                f *= f10;
                f9 *= f10;
                f10 = 1.0f / (float)Math.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
                float f11 = (f4 *= f10) * f9 - (f5 *= f10) * f;
                float f12 = f5 * f8 - (f3 *= f10) * f9;
                f10 = f3 * f - f4 * f8;
                if (arrf != null) {
                    if (arrf.length == 9) {
                        arrf[0] = f8;
                        arrf[1] = f;
                        arrf[2] = f9;
                        arrf[3] = f11;
                        arrf[4] = f12;
                        arrf[5] = f10;
                        arrf[6] = f3;
                        arrf[7] = f4;
                        arrf[8] = f5;
                    } else if (arrf.length == 16) {
                        arrf[0] = f8;
                        arrf[1] = f;
                        arrf[2] = f9;
                        arrf[3] = 0.0f;
                        arrf[4] = f11;
                        arrf[5] = f12;
                        arrf[6] = f10;
                        arrf[7] = 0.0f;
                        arrf[8] = f3;
                        arrf[9] = f4;
                        arrf[10] = f5;
                        arrf[11] = 0.0f;
                        arrf[12] = 0.0f;
                        arrf[13] = 0.0f;
                        arrf[14] = 0.0f;
                        arrf[15] = 1.0f;
                    }
                }
                if (arrf2 == null) break block6;
                f8 = 1.0f / (float)Math.sqrt(f2 * f2 + f6 * f6 + f7 * f7);
                f = (f2 * f11 + f6 * f12 + f7 * f10) * f8;
                f2 = (f2 * f3 + f6 * f4 + f7 * f5) * f8;
                if (arrf2.length != 9) break block7;
                arrf2[0] = 1.0f;
                arrf2[1] = 0.0f;
                arrf2[2] = 0.0f;
                arrf2[3] = 0.0f;
                arrf2[4] = f;
                arrf2[5] = f2;
                arrf2[6] = 0.0f;
                arrf2[7] = -f2;
                arrf2[8] = f;
                break block6;
            }
            if (arrf2.length != 16) break block6;
            arrf2[0] = 1.0f;
            arrf2[1] = 0.0f;
            arrf2[2] = 0.0f;
            arrf2[4] = 0.0f;
            arrf2[5] = f;
            arrf2[6] = f2;
            arrf2[8] = 0.0f;
            arrf2[9] = -f2;
            arrf2[10] = f;
            arrf2[14] = 0.0f;
            arrf2[13] = 0.0f;
            arrf2[12] = 0.0f;
            arrf2[11] = 0.0f;
            arrf2[7] = 0.0f;
            arrf2[3] = 0.0f;
            arrf2[15] = 1.0f;
        }
        return true;
    }

    public static void getRotationMatrixFromVector(float[] arrf, float[] arrf2) {
        float f;
        float f2 = arrf2[0];
        float f3 = arrf2[1];
        float f4 = arrf2[2];
        f = arrf2.length >= 4 ? arrf2[3] : ((f = 1.0f - f2 * f2 - f3 * f3 - f4 * f4) > 0.0f ? (float)Math.sqrt(f) : 0.0f);
        float f5 = f2 * 2.0f * f2;
        float f6 = f3 * 2.0f * f3;
        float f7 = f4 * 2.0f * f4;
        float f8 = f2 * 2.0f * f3;
        float f9 = f4 * 2.0f * f;
        float f10 = f2 * 2.0f * f4;
        float f11 = f3 * 2.0f * f;
        f4 = f3 * 2.0f * f4;
        f = 2.0f * f2 * f;
        if (arrf.length == 9) {
            arrf[0] = 1.0f - f6 - f7;
            arrf[1] = f8 - f9;
            arrf[2] = f10 + f11;
            arrf[3] = f8 + f9;
            arrf[4] = 1.0f - f5 - f7;
            arrf[5] = f4 - f;
            arrf[6] = f10 - f11;
            arrf[7] = f4 + f;
            arrf[8] = 1.0f - f5 - f6;
        } else if (arrf.length == 16) {
            arrf[0] = 1.0f - f6 - f7;
            arrf[1] = f8 - f9;
            arrf[2] = f10 + f11;
            arrf[3] = 0.0f;
            arrf[4] = f8 + f9;
            arrf[5] = 1.0f - f5 - f7;
            arrf[6] = f4 - f;
            arrf[7] = 0.0f;
            arrf[8] = f10 - f11;
            arrf[9] = f4 + f;
            arrf[10] = 1.0f - f5 - f6;
            arrf[11] = 0.0f;
            arrf[14] = 0.0f;
            arrf[13] = 0.0f;
            arrf[12] = 0.0f;
            arrf[15] = 1.0f;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean remapCoordinateSystem(float[] arrf, int n, int n2, float[] arrf2) {
        if (arrf != arrf2) return SensorManager.remapCoordinateSystemImpl(arrf, n, n2, arrf2);
        float[] arrf3 = sTempMatrix;
        synchronized (arrf3) {
            if (!SensorManager.remapCoordinateSystemImpl(arrf, n, n2, arrf3)) return SensorManager.remapCoordinateSystemImpl(arrf, n, n2, arrf2);
            n2 = arrf2.length;
            n = 0;
            while (n < n2) {
                arrf2[n] = arrf3[n];
                ++n;
            }
            return true;
        }
    }

    private static boolean remapCoordinateSystemImpl(float[] arrf, int n, int n2, float[] arrf2) {
        int n3 = arrf2.length;
        int n4 = arrf.length;
        boolean bl = false;
        if (n4 != n3) {
            return false;
        }
        if ((n & 124) == 0 && (n2 & 124) == 0) {
            if ((n & 3) != 0 && (n2 & 3) != 0) {
                if ((n & 3) == (n2 & 3)) {
                    return false;
                }
                int n5 = n ^ n2;
                int n6 = (n & 3) - 1;
                int n7 = (n2 & 3) - 1;
                int n8 = (n5 & 3) - 1;
                n4 = n5;
                if ((n6 ^ (n8 + 1) % 3 | n7 ^ (n8 + 2) % 3) != 0) {
                    n4 = n5 ^ 128;
                }
                n = n >= 128 ? 1 : 0;
                n2 = n2 >= 128 ? 1 : 0;
                if (n4 >= 128) {
                    bl = true;
                }
                n4 = n3 == 16 ? 4 : 3;
                for (n5 = 0; n5 < 3; ++n5) {
                    int n9 = n5 * n4;
                    for (int i = 0; i < 3; ++i) {
                        float f;
                        if (n6 == i) {
                            f = n != 0 ? -arrf[n9 + 0] : arrf[n9 + 0];
                            arrf2[n9 + i] = f;
                        }
                        if (n7 == i) {
                            f = n2 != 0 ? -arrf[n9 + 1] : arrf[n9 + 1];
                            arrf2[n9 + i] = f;
                        }
                        if (n8 != i) continue;
                        int n10 = n9 + 2;
                        f = bl ? -arrf[n10] : arrf[n10];
                        arrf2[n9 + i] = f;
                    }
                }
                if (n3 == 16) {
                    arrf2[14] = 0.0f;
                    arrf2[13] = 0.0f;
                    arrf2[12] = 0.0f;
                    arrf2[11] = 0.0f;
                    arrf2[7] = 0.0f;
                    arrf2[3] = 0.0f;
                    arrf2[15] = 1.0f;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean cancelTriggerSensor(TriggerEventListener triggerEventListener, Sensor sensor) {
        return this.cancelTriggerSensorImpl(triggerEventListener, sensor, true);
    }

    protected abstract boolean cancelTriggerSensorImpl(TriggerEventListener var1, Sensor var2, boolean var3);

    @Deprecated
    public int configureDirectChannel(SensorDirectChannel sensorDirectChannel, Sensor sensor, int n) {
        return this.configureDirectChannelImpl(sensorDirectChannel, sensor, n);
    }

    protected abstract int configureDirectChannelImpl(SensorDirectChannel var1, Sensor var2, int var3);

    public SensorDirectChannel createDirectChannel(HardwareBuffer hardwareBuffer) {
        return this.createDirectChannelImpl(null, hardwareBuffer);
    }

    public SensorDirectChannel createDirectChannel(MemoryFile memoryFile) {
        return this.createDirectChannelImpl(memoryFile, null);
    }

    protected abstract SensorDirectChannel createDirectChannelImpl(MemoryFile var1, HardwareBuffer var2);

    void destroyDirectChannel(SensorDirectChannel sensorDirectChannel) {
        this.destroyDirectChannelImpl(sensorDirectChannel);
    }

    protected abstract void destroyDirectChannelImpl(SensorDirectChannel var1);

    public boolean flush(SensorEventListener sensorEventListener) {
        return this.flushImpl(sensorEventListener);
    }

    protected abstract boolean flushImpl(SensorEventListener var1);

    public Sensor getDefaultSensor(int n) {
        Object object = this.getSensorList(n);
        boolean bl = false;
        if (n == 8 || n == 17 || n == 22 || n == 23 || n == 24 || n == 25 || n == 26 || n == 32) {
            bl = true;
        }
        object = object.iterator();
        while (object.hasNext()) {
            Sensor sensor = (Sensor)object.next();
            if (sensor.isWakeUpSensor() != bl) continue;
            return sensor;
        }
        return null;
    }

    public Sensor getDefaultSensor(int n, boolean bl) {
        for (Sensor sensor : this.getSensorList(n)) {
            if (sensor.isWakeUpSensor() != bl) continue;
            return sensor;
        }
        return null;
    }

    public List<Sensor> getDynamicSensorList(int n) {
        Object object = this.getFullDynamicSensorList();
        if (n == -1) {
            return Collections.unmodifiableList(object);
        }
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Iterator<Sensor> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (((Sensor)object).getType() != n) continue;
            arrayList.add(object);
        }
        return Collections.unmodifiableList(arrayList);
    }

    protected abstract List<Sensor> getFullDynamicSensorList();

    protected abstract List<Sensor> getFullSensorList();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<Sensor> getSensorList(int n) {
        Object object = this.getFullSensorList();
        SparseArray<List<Sensor>> sparseArray = this.mSensorListByType;
        synchronized (sparseArray) {
            Object object2 = this.mSensorListByType.get(n);
            List<Object> list = object2;
            if (object2 == null) {
                if (n != -1) {
                    list = new List<Object>();
                    object2 = object.iterator();
                    do {
                        object = list;
                        if (!object2.hasNext()) break;
                        object = (Sensor)object2.next();
                        if (((Sensor)object).getType() != n) continue;
                        list.add(object);
                    } while (true);
                }
                list = Collections.unmodifiableList(object);
                this.mSensorListByType.append(n, list);
            }
            return list;
        }
    }

    @Deprecated
    public int getSensors() {
        return this.getLegacySensorManager().getSensors();
    }

    @SystemApi
    public boolean initDataInjection(boolean bl) {
        return this.initDataInjectionImpl(bl);
    }

    protected abstract boolean initDataInjectionImpl(boolean var1);

    @SystemApi
    public boolean injectSensorData(Sensor sensor, float[] arrf, int n, long l) {
        if (sensor != null) {
            if (sensor.isDataInjectionSupported()) {
                if (arrf != null) {
                    int n2 = Sensor.getMaxLengthValuesArray(sensor, 23);
                    if (arrf.length == n2) {
                        if (n >= -1 && n <= 3) {
                            if (l > 0L) {
                                return this.injectSensorDataImpl(sensor, arrf, n, l);
                            }
                            throw new IllegalArgumentException("Negative or zero sensor timestamp");
                        }
                        throw new IllegalArgumentException("Invalid sensor accuracy");
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Wrong number of values for sensor ");
                    stringBuilder.append(sensor.getName());
                    stringBuilder.append(" actual=");
                    stringBuilder.append(arrf.length);
                    stringBuilder.append(" expected=");
                    stringBuilder.append(n2);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                throw new IllegalArgumentException("sensor data cannot be null");
            }
            throw new IllegalArgumentException("sensor does not support data injection");
        }
        throw new IllegalArgumentException("sensor cannot be null");
    }

    protected abstract boolean injectSensorDataImpl(Sensor var1, float[] var2, int var3, long var4);

    public boolean isDynamicSensorDiscoverySupported() {
        boolean bl = this.getSensorList(32).size() > 0;
        return bl;
    }

    public void registerDynamicSensorCallback(DynamicSensorCallback dynamicSensorCallback) {
        this.registerDynamicSensorCallback(dynamicSensorCallback, null);
    }

    public void registerDynamicSensorCallback(DynamicSensorCallback dynamicSensorCallback, Handler handler) {
        this.registerDynamicSensorCallbackImpl(dynamicSensorCallback, handler);
    }

    protected abstract void registerDynamicSensorCallbackImpl(DynamicSensorCallback var1, Handler var2);

    public boolean registerListener(SensorEventListener sensorEventListener, Sensor sensor, int n) {
        return this.registerListener(sensorEventListener, sensor, n, null);
    }

    public boolean registerListener(SensorEventListener sensorEventListener, Sensor sensor, int n, int n2) {
        return this.registerListenerImpl(sensorEventListener, sensor, SensorManager.getDelay(n), null, n2, 0);
    }

    public boolean registerListener(SensorEventListener sensorEventListener, Sensor sensor, int n, int n2, Handler handler) {
        return this.registerListenerImpl(sensorEventListener, sensor, SensorManager.getDelay(n), handler, n2, 0);
    }

    public boolean registerListener(SensorEventListener sensorEventListener, Sensor sensor, int n, Handler handler) {
        return this.registerListenerImpl(sensorEventListener, sensor, SensorManager.getDelay(n), handler, 0, 0);
    }

    @Deprecated
    public boolean registerListener(SensorListener sensorListener, int n) {
        return this.registerListener(sensorListener, n, 3);
    }

    @Deprecated
    public boolean registerListener(SensorListener sensorListener, int n, int n2) {
        return this.getLegacySensorManager().registerListener(sensorListener, n, n2);
    }

    protected abstract boolean registerListenerImpl(SensorEventListener var1, Sensor var2, int var3, Handler var4, int var5, int var6);

    public boolean requestTriggerSensor(TriggerEventListener triggerEventListener, Sensor sensor) {
        return this.requestTriggerSensorImpl(triggerEventListener, sensor);
    }

    protected abstract boolean requestTriggerSensorImpl(TriggerEventListener var1, Sensor var2);

    public boolean setOperationParameter(SensorAdditionalInfo sensorAdditionalInfo) {
        return this.setOperationParameterImpl(sensorAdditionalInfo);
    }

    protected abstract boolean setOperationParameterImpl(SensorAdditionalInfo var1);

    public void unregisterDynamicSensorCallback(DynamicSensorCallback dynamicSensorCallback) {
        this.unregisterDynamicSensorCallbackImpl(dynamicSensorCallback);
    }

    protected abstract void unregisterDynamicSensorCallbackImpl(DynamicSensorCallback var1);

    public void unregisterListener(SensorEventListener sensorEventListener) {
        if (sensorEventListener == null) {
            return;
        }
        this.unregisterListenerImpl(sensorEventListener, null);
    }

    public void unregisterListener(SensorEventListener sensorEventListener, Sensor sensor) {
        if (sensorEventListener != null && sensor != null) {
            this.unregisterListenerImpl(sensorEventListener, sensor);
            return;
        }
    }

    @Deprecated
    public void unregisterListener(SensorListener sensorListener) {
        this.unregisterListener(sensorListener, 255);
    }

    @Deprecated
    public void unregisterListener(SensorListener sensorListener, int n) {
        this.getLegacySensorManager().unregisterListener(sensorListener, n);
    }

    protected abstract void unregisterListenerImpl(SensorEventListener var1, Sensor var2);

    public static abstract class DynamicSensorCallback {
        public void onDynamicSensorConnected(Sensor sensor) {
        }

        public void onDynamicSensorDisconnected(Sensor sensor) {
        }
    }

}

