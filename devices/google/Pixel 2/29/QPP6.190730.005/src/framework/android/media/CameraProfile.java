/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.hardware.Camera;
import java.util.Arrays;
import java.util.HashMap;

public class CameraProfile {
    public static final int QUALITY_HIGH = 2;
    public static final int QUALITY_LOW = 0;
    public static final int QUALITY_MEDIUM = 1;
    private static final HashMap<Integer, int[]> sCache = new HashMap();

    static {
        System.loadLibrary("media_jni");
        CameraProfile.native_init();
    }

    private static int[] getImageEncodingQualityLevels(int n) {
        int n2 = CameraProfile.native_get_num_image_encoding_quality_levels(n);
        if (n2 == 3) {
            int[] arrn = new int[n2];
            for (int i = 0; i < n2; ++i) {
                arrn[i] = CameraProfile.native_get_image_encoding_quality_level(n, i);
            }
            Arrays.sort(arrn);
            return arrn;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected Jpeg encoding quality levels ");
        stringBuilder.append(n2);
        throw new RuntimeException(stringBuilder.toString());
    }

    public static int getJpegEncodingQualityParameter(int n) {
        int n2 = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < n2; ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing != 0) continue;
            return CameraProfile.getJpegEncodingQualityParameter(i, n);
        }
        return 0;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int getJpegEncodingQualityParameter(int n, int n2) {
        if (n2 >= 0 && n2 <= 2) {
            HashMap<Integer, int[]> hashMap = sCache;
            synchronized (hashMap) {
                int[] arrn;
                int[] arrn2 = arrn = sCache.get(n);
                if (arrn != null) return arrn2[n2];
                arrn2 = CameraProfile.getImageEncodingQualityLevels(n);
                sCache.put(n, arrn2);
                return arrn2[n2];
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported quality level: ");
        stringBuilder.append(n2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static final native int native_get_image_encoding_quality_level(int var0, int var1);

    private static final native int native_get_num_image_encoding_quality_levels(int var0);

    private static final native void native_init();
}

