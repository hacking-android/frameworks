/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.hardware.Camera;

public class CamcorderProfile {
    public static final int QUALITY_1080P = 6;
    public static final int QUALITY_2160P = 8;
    public static final int QUALITY_2K = 12;
    public static final int QUALITY_480P = 4;
    public static final int QUALITY_4KDCI = 10;
    public static final int QUALITY_720P = 5;
    public static final int QUALITY_CIF = 3;
    public static final int QUALITY_HIGH = 1;
    public static final int QUALITY_HIGH_SPEED_1080P = 2004;
    public static final int QUALITY_HIGH_SPEED_2160P = 2005;
    public static final int QUALITY_HIGH_SPEED_480P = 2002;
    public static final int QUALITY_HIGH_SPEED_4KDCI = 2008;
    public static final int QUALITY_HIGH_SPEED_720P = 2003;
    public static final int QUALITY_HIGH_SPEED_CIF = 2006;
    public static final int QUALITY_HIGH_SPEED_HIGH = 2001;
    private static final int QUALITY_HIGH_SPEED_LIST_END = 2008;
    private static final int QUALITY_HIGH_SPEED_LIST_START = 2000;
    public static final int QUALITY_HIGH_SPEED_LOW = 2000;
    public static final int QUALITY_HIGH_SPEED_VGA = 2007;
    private static final int QUALITY_LIST_END = 12;
    private static final int QUALITY_LIST_START = 0;
    public static final int QUALITY_LOW = 0;
    public static final int QUALITY_QCIF = 2;
    public static final int QUALITY_QHD = 11;
    public static final int QUALITY_QVGA = 7;
    public static final int QUALITY_TIME_LAPSE_1080P = 1006;
    public static final int QUALITY_TIME_LAPSE_2160P = 1008;
    public static final int QUALITY_TIME_LAPSE_2K = 1012;
    public static final int QUALITY_TIME_LAPSE_480P = 1004;
    public static final int QUALITY_TIME_LAPSE_4KDCI = 1010;
    public static final int QUALITY_TIME_LAPSE_720P = 1005;
    public static final int QUALITY_TIME_LAPSE_CIF = 1003;
    public static final int QUALITY_TIME_LAPSE_HIGH = 1001;
    private static final int QUALITY_TIME_LAPSE_LIST_END = 1012;
    private static final int QUALITY_TIME_LAPSE_LIST_START = 1000;
    public static final int QUALITY_TIME_LAPSE_LOW = 1000;
    public static final int QUALITY_TIME_LAPSE_QCIF = 1002;
    public static final int QUALITY_TIME_LAPSE_QHD = 1011;
    public static final int QUALITY_TIME_LAPSE_QVGA = 1007;
    public static final int QUALITY_TIME_LAPSE_VGA = 1009;
    public static final int QUALITY_VGA = 9;
    public int audioBitRate;
    public int audioChannels;
    public int audioCodec;
    public int audioSampleRate;
    public int duration;
    public int fileFormat;
    public int quality;
    public int videoBitRate;
    public int videoCodec;
    public int videoFrameHeight;
    public int videoFrameRate;
    public int videoFrameWidth;

    static {
        System.loadLibrary("media_jni");
        CamcorderProfile.native_init();
    }

    private CamcorderProfile(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        this.duration = n;
        this.quality = n2;
        this.fileFormat = n3;
        this.videoCodec = n4;
        this.videoBitRate = n5;
        this.videoFrameRate = n6;
        this.videoFrameWidth = n7;
        this.videoFrameHeight = n8;
        this.audioCodec = n9;
        this.audioBitRate = n10;
        this.audioSampleRate = n11;
        this.audioChannels = n12;
    }

    public static CamcorderProfile get(int n) {
        int n2 = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < n2; ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing != 0) continue;
            return CamcorderProfile.get(i, n);
        }
        return null;
    }

    public static CamcorderProfile get(int n, int n2) {
        if (n2 >= 0 && n2 <= 12 || n2 >= 1000 && n2 <= 1012 || n2 >= 2000 && n2 <= 2008) {
            return CamcorderProfile.native_get_camcorder_profile(n, n2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported quality level: ");
        stringBuilder.append(n2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static boolean hasProfile(int n) {
        int n2 = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < n2; ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing != 0) continue;
            return CamcorderProfile.hasProfile(i, n);
        }
        return false;
    }

    public static boolean hasProfile(int n, int n2) {
        return CamcorderProfile.native_has_camcorder_profile(n, n2);
    }

    @UnsupportedAppUsage
    private static final native CamcorderProfile native_get_camcorder_profile(int var0, int var1);

    private static final native boolean native_has_camcorder_profile(int var0, int var1);

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static final native void native_init();
}

