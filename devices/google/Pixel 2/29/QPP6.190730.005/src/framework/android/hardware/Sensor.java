/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import java.util.UUID;

public final class Sensor {
    private static final int ADDITIONAL_INFO_MASK = 64;
    private static final int ADDITIONAL_INFO_SHIFT = 6;
    private static final int DATA_INJECTION_MASK = 16;
    private static final int DATA_INJECTION_SHIFT = 4;
    private static final int DIRECT_CHANNEL_MASK = 3072;
    private static final int DIRECT_CHANNEL_SHIFT = 10;
    private static final int DIRECT_REPORT_MASK = 896;
    private static final int DIRECT_REPORT_SHIFT = 7;
    private static final int DYNAMIC_SENSOR_MASK = 32;
    private static final int DYNAMIC_SENSOR_SHIFT = 5;
    public static final int REPORTING_MODE_CONTINUOUS = 0;
    private static final int REPORTING_MODE_MASK = 14;
    public static final int REPORTING_MODE_ONE_SHOT = 2;
    public static final int REPORTING_MODE_ON_CHANGE = 1;
    private static final int REPORTING_MODE_SHIFT = 1;
    public static final int REPORTING_MODE_SPECIAL_TRIGGER = 3;
    private static final int SENSOR_FLAG_WAKE_UP_SENSOR = 1;
    public static final String SENSOR_STRING_TYPE_TILT_DETECTOR = "android.sensor.tilt_detector";
    public static final String STRING_TYPE_ACCELEROMETER = "android.sensor.accelerometer";
    public static final String STRING_TYPE_ACCELEROMETER_UNCALIBRATED = "android.sensor.accelerometer_uncalibrated";
    public static final String STRING_TYPE_AMBIENT_TEMPERATURE = "android.sensor.ambient_temperature";
    public static final String STRING_TYPE_DEVICE_ORIENTATION = "android.sensor.device_orientation";
    @SystemApi
    public static final String STRING_TYPE_DYNAMIC_SENSOR_META = "android.sensor.dynamic_sensor_meta";
    public static final String STRING_TYPE_GAME_ROTATION_VECTOR = "android.sensor.game_rotation_vector";
    public static final String STRING_TYPE_GEOMAGNETIC_ROTATION_VECTOR = "android.sensor.geomagnetic_rotation_vector";
    public static final String STRING_TYPE_GLANCE_GESTURE = "android.sensor.glance_gesture";
    public static final String STRING_TYPE_GRAVITY = "android.sensor.gravity";
    public static final String STRING_TYPE_GYROSCOPE = "android.sensor.gyroscope";
    public static final String STRING_TYPE_GYROSCOPE_UNCALIBRATED = "android.sensor.gyroscope_uncalibrated";
    public static final String STRING_TYPE_HEART_BEAT = "android.sensor.heart_beat";
    public static final String STRING_TYPE_HEART_RATE = "android.sensor.heart_rate";
    public static final String STRING_TYPE_LIGHT = "android.sensor.light";
    public static final String STRING_TYPE_LINEAR_ACCELERATION = "android.sensor.linear_acceleration";
    public static final String STRING_TYPE_LOW_LATENCY_OFFBODY_DETECT = "android.sensor.low_latency_offbody_detect";
    public static final String STRING_TYPE_MAGNETIC_FIELD = "android.sensor.magnetic_field";
    public static final String STRING_TYPE_MAGNETIC_FIELD_UNCALIBRATED = "android.sensor.magnetic_field_uncalibrated";
    public static final String STRING_TYPE_MOTION_DETECT = "android.sensor.motion_detect";
    @Deprecated
    public static final String STRING_TYPE_ORIENTATION = "android.sensor.orientation";
    public static final String STRING_TYPE_PICK_UP_GESTURE = "android.sensor.pick_up_gesture";
    public static final String STRING_TYPE_POSE_6DOF = "android.sensor.pose_6dof";
    public static final String STRING_TYPE_PRESSURE = "android.sensor.pressure";
    public static final String STRING_TYPE_PROXIMITY = "android.sensor.proximity";
    public static final String STRING_TYPE_RELATIVE_HUMIDITY = "android.sensor.relative_humidity";
    public static final String STRING_TYPE_ROTATION_VECTOR = "android.sensor.rotation_vector";
    public static final String STRING_TYPE_SIGNIFICANT_MOTION = "android.sensor.significant_motion";
    public static final String STRING_TYPE_STATIONARY_DETECT = "android.sensor.stationary_detect";
    public static final String STRING_TYPE_STEP_COUNTER = "android.sensor.step_counter";
    public static final String STRING_TYPE_STEP_DETECTOR = "android.sensor.step_detector";
    @Deprecated
    public static final String STRING_TYPE_TEMPERATURE = "android.sensor.temperature";
    public static final String STRING_TYPE_WAKE_GESTURE = "android.sensor.wake_gesture";
    @SystemApi
    public static final String STRING_TYPE_WRIST_TILT_GESTURE = "android.sensor.wrist_tilt_gesture";
    public static final int TYPE_ACCELEROMETER = 1;
    public static final int TYPE_ACCELEROMETER_UNCALIBRATED = 35;
    public static final int TYPE_ALL = -1;
    public static final int TYPE_AMBIENT_TEMPERATURE = 13;
    @UnsupportedAppUsage
    public static final int TYPE_DEVICE_ORIENTATION = 27;
    public static final int TYPE_DEVICE_PRIVATE_BASE = 65536;
    @SystemApi
    public static final int TYPE_DYNAMIC_SENSOR_META = 32;
    public static final int TYPE_GAME_ROTATION_VECTOR = 15;
    public static final int TYPE_GEOMAGNETIC_ROTATION_VECTOR = 20;
    public static final int TYPE_GLANCE_GESTURE = 24;
    public static final int TYPE_GRAVITY = 9;
    public static final int TYPE_GYROSCOPE = 4;
    public static final int TYPE_GYROSCOPE_UNCALIBRATED = 16;
    public static final int TYPE_HEART_BEAT = 31;
    public static final int TYPE_HEART_RATE = 21;
    public static final int TYPE_LIGHT = 5;
    public static final int TYPE_LINEAR_ACCELERATION = 10;
    public static final int TYPE_LOW_LATENCY_OFFBODY_DETECT = 34;
    public static final int TYPE_MAGNETIC_FIELD = 2;
    public static final int TYPE_MAGNETIC_FIELD_UNCALIBRATED = 14;
    public static final int TYPE_MOTION_DETECT = 30;
    @Deprecated
    public static final int TYPE_ORIENTATION = 3;
    @UnsupportedAppUsage
    public static final int TYPE_PICK_UP_GESTURE = 25;
    public static final int TYPE_POSE_6DOF = 28;
    public static final int TYPE_PRESSURE = 6;
    public static final int TYPE_PROXIMITY = 8;
    public static final int TYPE_RELATIVE_HUMIDITY = 12;
    public static final int TYPE_ROTATION_VECTOR = 11;
    public static final int TYPE_SIGNIFICANT_MOTION = 17;
    public static final int TYPE_STATIONARY_DETECT = 29;
    public static final int TYPE_STEP_COUNTER = 19;
    public static final int TYPE_STEP_DETECTOR = 18;
    @Deprecated
    public static final int TYPE_TEMPERATURE = 7;
    public static final int TYPE_TILT_DETECTOR = 22;
    public static final int TYPE_WAKE_GESTURE = 23;
    @SystemApi
    public static final int TYPE_WRIST_TILT_GESTURE = 26;
    private static final int[] sSensorReportingModes = new int[]{0, 3, 3, 3, 3, 1, 1, 1, 1, 3, 3, 5, 1, 1, 6, 4, 6, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 16, 1, 1, 1, 2, 16, 1, 6};
    private int mFifoMaxEventCount;
    private int mFifoReservedEventCount;
    @UnsupportedAppUsage
    private int mFlags;
    private int mHandle;
    private int mId;
    private int mMaxDelay;
    private float mMaxRange;
    private int mMinDelay;
    private String mName;
    private float mPower;
    private String mRequiredPermission;
    private float mResolution;
    private String mStringType;
    private int mType;
    private String mVendor;
    private int mVersion;

    Sensor() {
    }

    static int getMaxLengthValuesArray(Sensor arrn, int n) {
        if (arrn.mType == 11 && n <= 17) {
            return 3;
        }
        n = arrn.mType;
        arrn = sSensorReportingModes;
        if (n >= arrn.length) {
            return 16;
        }
        return arrn[n];
    }

    private boolean setType(int n) {
        this.mType = n;
        if ((n = this.mType) != 27) {
            if (n != 32) {
                if (n != 34) {
                    if (n != 35) {
                        switch (n) {
                            default: {
                                return false;
                            }
                            case 25: {
                                this.mStringType = STRING_TYPE_PICK_UP_GESTURE;
                                return true;
                            }
                            case 24: {
                                this.mStringType = STRING_TYPE_GLANCE_GESTURE;
                                return true;
                            }
                            case 23: {
                                this.mStringType = STRING_TYPE_WAKE_GESTURE;
                                return true;
                            }
                            case 22: {
                                this.mStringType = SENSOR_STRING_TYPE_TILT_DETECTOR;
                                return true;
                            }
                            case 21: {
                                this.mStringType = STRING_TYPE_HEART_RATE;
                                return true;
                            }
                            case 20: {
                                this.mStringType = STRING_TYPE_GEOMAGNETIC_ROTATION_VECTOR;
                                return true;
                            }
                            case 19: {
                                this.mStringType = STRING_TYPE_STEP_COUNTER;
                                return true;
                            }
                            case 18: {
                                this.mStringType = STRING_TYPE_STEP_DETECTOR;
                                return true;
                            }
                            case 17: {
                                this.mStringType = STRING_TYPE_SIGNIFICANT_MOTION;
                                return true;
                            }
                            case 16: {
                                this.mStringType = STRING_TYPE_GYROSCOPE_UNCALIBRATED;
                                return true;
                            }
                            case 15: {
                                this.mStringType = STRING_TYPE_GAME_ROTATION_VECTOR;
                                return true;
                            }
                            case 14: {
                                this.mStringType = STRING_TYPE_MAGNETIC_FIELD_UNCALIBRATED;
                                return true;
                            }
                            case 13: {
                                this.mStringType = STRING_TYPE_AMBIENT_TEMPERATURE;
                                return true;
                            }
                            case 12: {
                                this.mStringType = STRING_TYPE_RELATIVE_HUMIDITY;
                                return true;
                            }
                            case 11: {
                                this.mStringType = STRING_TYPE_ROTATION_VECTOR;
                                return true;
                            }
                            case 10: {
                                this.mStringType = STRING_TYPE_LINEAR_ACCELERATION;
                                return true;
                            }
                            case 9: {
                                this.mStringType = STRING_TYPE_GRAVITY;
                                return true;
                            }
                            case 8: {
                                this.mStringType = STRING_TYPE_PROXIMITY;
                                return true;
                            }
                            case 7: {
                                this.mStringType = STRING_TYPE_TEMPERATURE;
                                return true;
                            }
                            case 6: {
                                this.mStringType = STRING_TYPE_PRESSURE;
                                return true;
                            }
                            case 5: {
                                this.mStringType = STRING_TYPE_LIGHT;
                                return true;
                            }
                            case 4: {
                                this.mStringType = STRING_TYPE_GYROSCOPE;
                                return true;
                            }
                            case 3: {
                                this.mStringType = STRING_TYPE_ORIENTATION;
                                return true;
                            }
                            case 2: {
                                this.mStringType = STRING_TYPE_MAGNETIC_FIELD;
                                return true;
                            }
                            case 1: 
                        }
                        this.mStringType = STRING_TYPE_ACCELEROMETER;
                        return true;
                    }
                    this.mStringType = STRING_TYPE_ACCELEROMETER_UNCALIBRATED;
                    return true;
                }
                this.mStringType = STRING_TYPE_LOW_LATENCY_OFFBODY_DETECT;
                return true;
            }
            this.mStringType = STRING_TYPE_DYNAMIC_SENSOR_META;
            return true;
        }
        this.mStringType = STRING_TYPE_DEVICE_ORIENTATION;
        return true;
    }

    private void setUuid(long l, long l2) {
        this.mId = (int)l;
    }

    public int getFifoMaxEventCount() {
        return this.mFifoMaxEventCount;
    }

    public int getFifoReservedEventCount() {
        return this.mFifoReservedEventCount;
    }

    @UnsupportedAppUsage
    public int getHandle() {
        return this.mHandle;
    }

    public int getHighestDirectReportRateLevel() {
        int n = (this.mFlags & 896) >> 7;
        int n2 = 3;
        if (n > 3) {
            n = n2;
        }
        return n;
    }

    public int getId() {
        return this.mId;
    }

    public int getMaxDelay() {
        return this.mMaxDelay;
    }

    public float getMaximumRange() {
        return this.mMaxRange;
    }

    public int getMinDelay() {
        return this.mMinDelay;
    }

    public String getName() {
        return this.mName;
    }

    public float getPower() {
        return this.mPower;
    }

    public int getReportingMode() {
        return (this.mFlags & 14) >> 1;
    }

    public String getRequiredPermission() {
        return this.mRequiredPermission;
    }

    public float getResolution() {
        return this.mResolution;
    }

    public String getStringType() {
        return this.mStringType;
    }

    public int getType() {
        return this.mType;
    }

    @SystemApi
    public UUID getUuid() {
        throw new UnsupportedOperationException();
    }

    public String getVendor() {
        return this.mVendor;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public boolean isAdditionalInfoSupported() {
        boolean bl = (this.mFlags & 64) != 0;
        return bl;
    }

    @SystemApi
    public boolean isDataInjectionSupported() {
        boolean bl = (this.mFlags & 16) >> 4 != 0;
        return bl;
    }

    public boolean isDirectChannelTypeSupported(int n) {
        boolean bl = false;
        boolean bl2 = false;
        if (n != 1) {
            if (n != 2) {
                return false;
            }
            if ((this.mFlags & 2048) > 0) {
                bl2 = true;
            }
            return bl2;
        }
        bl2 = bl;
        if ((this.mFlags & 1024) > 0) {
            bl2 = true;
        }
        return bl2;
    }

    public boolean isDynamicSensor() {
        boolean bl = (this.mFlags & 32) != 0;
        return bl;
    }

    public boolean isWakeUpSensor() {
        int n = this.mFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    void setRange(float f, float f2) {
        this.mMaxRange = f;
        this.mResolution = f2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{Sensor name=\"");
        stringBuilder.append(this.mName);
        stringBuilder.append("\", vendor=\"");
        stringBuilder.append(this.mVendor);
        stringBuilder.append("\", version=");
        stringBuilder.append(this.mVersion);
        stringBuilder.append(", type=");
        stringBuilder.append(this.mType);
        stringBuilder.append(", maxRange=");
        stringBuilder.append(this.mMaxRange);
        stringBuilder.append(", resolution=");
        stringBuilder.append(this.mResolution);
        stringBuilder.append(", power=");
        stringBuilder.append(this.mPower);
        stringBuilder.append(", minDelay=");
        stringBuilder.append(this.mMinDelay);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

