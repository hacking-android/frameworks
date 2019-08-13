/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.hardware.Sensor;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SensorAdditionalInfo {
    public static final int TYPE_CUSTOM_INFO = 268435456;
    public static final int TYPE_DEBUG_INFO = 1073741824;
    public static final int TYPE_DOCK_STATE = 196610;
    public static final int TYPE_FRAME_BEGIN = 0;
    public static final int TYPE_FRAME_END = 1;
    public static final int TYPE_HIGH_PERFORMANCE_MODE = 196611;
    public static final int TYPE_INTERNAL_TEMPERATURE = 65537;
    public static final int TYPE_LOCAL_GEOMAGNETIC_FIELD = 196608;
    public static final int TYPE_LOCAL_GRAVITY = 196609;
    public static final int TYPE_MAGNETIC_FIELD_CALIBRATION = 196612;
    public static final int TYPE_SAMPLING = 65540;
    public static final int TYPE_SENSOR_PLACEMENT = 65539;
    public static final int TYPE_UNTRACKED_DELAY = 65536;
    public static final int TYPE_VEC3_CALIBRATION = 65538;
    public final float[] floatValues;
    public final int[] intValues;
    public final Sensor sensor;
    public final int serial;
    public final int type;

    SensorAdditionalInfo(Sensor sensor, int n, int n2, int[] arrn, float[] arrf) {
        this.sensor = sensor;
        this.type = n;
        this.serial = n2;
        this.intValues = arrn;
        this.floatValues = arrf;
    }

    public static SensorAdditionalInfo createCustomInfo(Sensor sensor, int n, float[] object) {
        if (n >= 268435456 && n < 1073741824 && sensor != null) {
            return new SensorAdditionalInfo(sensor, n, 0, null, (float[])object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("invalid parameter(s): type: ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append("; sensor: ");
        ((StringBuilder)object).append(sensor);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static SensorAdditionalInfo createLocalGeomagneticField(float f, float f2, float f3) {
        if (!(f < 10.0f || f > 100.0f || f2 < 0.0f || (double)f2 > 3.141592653589793 || (double)f3 < -1.5707963267948966 || (double)f3 > 1.5707963267948966)) {
            return new SensorAdditionalInfo(null, 196608, 0, null, new float[]{f, f2, f3});
        }
        throw new IllegalArgumentException("Geomagnetic field info out of range");
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AdditionalInfoType {
    }

}

