/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import java.util.ArrayList;

public final class SensorType {
    public static final int ACCELEROMETER = 1;
    public static final int AMBIENT_LIGHT_SENSOR = 6;
    public static final int AUDIO = 768;
    public static final int BAROMETER = 4;
    public static final int BLE = 1280;
    public static final int CAMERA = 1024;
    public static final int GPS = 256;
    public static final int GYROSCOPE = 2;
    public static final int INSTANT_MOTION_DETECT = 8;
    public static final int MAGNETOMETER = 3;
    public static final int PRIVATE_SENSOR_BASE = 65536;
    public static final int PROXIMITY_SENSOR = 5;
    public static final int RESERVED = 0;
    public static final int STATIONARY_DETECT = 7;
    public static final int WIFI = 512;
    public static final int WWAN = 1536;

    public static final String dumpBitfield(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        arrayList.add("RESERVED");
        if ((n & 1) == 1) {
            arrayList.add("ACCELEROMETER");
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 2) {
            arrayList.add("GYROSCOPE");
            n3 = n2 | 2;
        }
        n2 = n3;
        if ((n & 3) == 3) {
            arrayList.add("MAGNETOMETER");
            n2 = n3 | 3;
        }
        n3 = n2;
        if ((n & 4) == 4) {
            arrayList.add("BAROMETER");
            n3 = n2 | 4;
        }
        n2 = n3;
        if ((n & 5) == 5) {
            arrayList.add("PROXIMITY_SENSOR");
            n2 = n3 | 5;
        }
        n3 = n2;
        if ((n & 6) == 6) {
            arrayList.add("AMBIENT_LIGHT_SENSOR");
            n3 = n2 | 6;
        }
        n2 = n3;
        if ((n & 7) == 7) {
            arrayList.add("STATIONARY_DETECT");
            n2 = n3 | 7;
        }
        int n4 = n2;
        if ((n & 8) == 8) {
            arrayList.add("INSTANT_MOTION_DETECT");
            n4 = n2 | 8;
        }
        n3 = n4;
        if ((n & 256) == 256) {
            arrayList.add("GPS");
            n3 = n4 | 256;
        }
        n2 = n3;
        if ((n & 512) == 512) {
            arrayList.add("WIFI");
            n2 = n3 | 512;
        }
        n3 = n2;
        if ((n & 768) == 768) {
            arrayList.add("AUDIO");
            n3 = n2 | 768;
        }
        n2 = n3;
        if ((n & 1024) == 1024) {
            arrayList.add("CAMERA");
            n2 = n3 | 1024;
        }
        n3 = n2;
        if ((n & 1280) == 1280) {
            arrayList.add("BLE");
            n3 = n2 | 1280;
        }
        n2 = n3;
        if ((n & 1536) == 1536) {
            arrayList.add("WWAN");
            n2 = n3 | 1536;
        }
        n3 = n2;
        if ((n & 65536) == 65536) {
            arrayList.add("PRIVATE_SENSOR_BASE");
            n3 = n2 | 65536;
        }
        if (n != n3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            stringBuilder.append(Integer.toHexString(n3 & n));
            arrayList.add(stringBuilder.toString());
        }
        return String.join((CharSequence)" | ", arrayList);
    }

    public static final String toString(int n) {
        if (n == 0) {
            return "RESERVED";
        }
        if (n == 1) {
            return "ACCELEROMETER";
        }
        if (n == 2) {
            return "GYROSCOPE";
        }
        if (n == 3) {
            return "MAGNETOMETER";
        }
        if (n == 4) {
            return "BAROMETER";
        }
        if (n == 5) {
            return "PROXIMITY_SENSOR";
        }
        if (n == 6) {
            return "AMBIENT_LIGHT_SENSOR";
        }
        if (n == 7) {
            return "STATIONARY_DETECT";
        }
        if (n == 8) {
            return "INSTANT_MOTION_DETECT";
        }
        if (n == 256) {
            return "GPS";
        }
        if (n == 512) {
            return "WIFI";
        }
        if (n == 768) {
            return "AUDIO";
        }
        if (n == 1024) {
            return "CAMERA";
        }
        if (n == 1280) {
            return "BLE";
        }
        if (n == 1536) {
            return "WWAN";
        }
        if (n == 65536) {
            return "PRIVATE_SENSOR_BASE";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }
}

