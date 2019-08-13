/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.CameraInfo;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.legacy.LegacyRequest;
import android.hardware.camera2.legacy.LegacyRequestMapper;
import android.hardware.camera2.legacy.ParameterUtils;
import android.hardware.camera2.legacy.SizeAreaComparator;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.params.StreamConfigurationDuration;
import android.hardware.camera2.utils.ArrayUtils;
import android.hardware.camera2.utils.ListUtils;
import android.hardware.camera2.utils.ParamsUtils;
import android.util.Log;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import android.util.SizeF;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LegacyMetadataMapper {
    private static final long APPROXIMATE_CAPTURE_DELAY_MS = 200L;
    private static final long APPROXIMATE_JPEG_ENCODE_TIME_MS = 600L;
    private static final long APPROXIMATE_SENSOR_AREA_PX = 0x800000L;
    private static final boolean DEBUG = false;
    public static final int HAL_PIXEL_FORMAT_BGRA_8888 = 5;
    public static final int HAL_PIXEL_FORMAT_BLOB = 33;
    public static final int HAL_PIXEL_FORMAT_IMPLEMENTATION_DEFINED = 34;
    public static final int HAL_PIXEL_FORMAT_RGBA_8888 = 1;
    private static final float LENS_INFO_MINIMUM_FOCUS_DISTANCE_FIXED_FOCUS = 0.0f;
    static final boolean LIE_ABOUT_AE_MAX_REGIONS = false;
    static final boolean LIE_ABOUT_AE_STATE = false;
    static final boolean LIE_ABOUT_AF = false;
    static final boolean LIE_ABOUT_AF_MAX_REGIONS = false;
    static final boolean LIE_ABOUT_AWB = false;
    static final boolean LIE_ABOUT_AWB_STATE = false;
    private static final long NS_PER_MS = 1000000L;
    private static final float PREVIEW_ASPECT_RATIO_TOLERANCE = 0.01f;
    private static final int REQUEST_MAX_NUM_INPUT_STREAMS_COUNT = 0;
    private static final int REQUEST_MAX_NUM_OUTPUT_STREAMS_COUNT_PROC = 3;
    private static final int REQUEST_MAX_NUM_OUTPUT_STREAMS_COUNT_PROC_STALL = 1;
    private static final int REQUEST_MAX_NUM_OUTPUT_STREAMS_COUNT_RAW = 0;
    private static final int REQUEST_PIPELINE_MAX_DEPTH_HAL1 = 3;
    private static final int REQUEST_PIPELINE_MAX_DEPTH_OURS = 3;
    private static final String TAG = "LegacyMetadataMapper";
    static final int UNKNOWN_MODE = -1;
    private static final int[] sAllowedTemplates;
    private static final int[] sEffectModes;
    private static final String[] sLegacyEffectMode;
    private static final String[] sLegacySceneModes;
    private static final int[] sSceneModes;

    static {
        sLegacySceneModes = new String[]{"auto", "action", "portrait", "landscape", "night", "night-portrait", "theatre", "beach", "snow", "sunset", "steadyphoto", "fireworks", "sports", "party", "candlelight", "barcode", "hdr"};
        sSceneModes = new int[]{0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18};
        sLegacyEffectMode = new String[]{"none", "mono", "negative", "solarize", "sepia", "posterize", "whiteboard", "blackboard", "aqua"};
        sEffectModes = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        sAllowedTemplates = new int[]{1, 2, 3};
    }

    private static void appendStreamConfig(ArrayList<StreamConfiguration> arrayList, int n, List<Camera.Size> object) {
        object = object.iterator();
        while (object.hasNext()) {
            Camera.Size size = (Camera.Size)object.next();
            arrayList.add(new StreamConfiguration(n, size.width, size.height, false));
        }
    }

    private static long calculateJpegStallDuration(Camera.Size size) {
        return (long)size.width * (long)size.height * 71L + 200000000L;
    }

    private static int[] convertAeFpsRangeToLegacy(Range<Integer> range) {
        return new int[]{range.getLower(), range.getUpper()};
    }

    static String convertAfModeToLegacy(int n, List<String> list) {
        if (list != null && !list.isEmpty()) {
            String string2 = null;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) {
                                if (n == 5) {
                                    string2 = "edof";
                                }
                            } else {
                                string2 = "continuous-picture";
                            }
                        } else {
                            string2 = "continuous-video";
                        }
                    } else {
                        string2 = "macro";
                    }
                } else {
                    string2 = "auto";
                }
            } else {
                string2 = list.contains("fixed") ? "fixed" : "infinity";
            }
            String string3 = string2;
            if (!list.contains(string2)) {
                string3 = list.get(0);
                Log.w(TAG, String.format("convertAfModeToLegacy - ignoring unsupported mode %d, defaulting to %s", n, string3));
            }
            return string3;
        }
        Log.w(TAG, "No focus modes supported; API1 bug");
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static int convertAntiBandingMode(String var0) {
        block7 : {
            if (var0 == null) {
                return -1;
            }
            switch (var0.hashCode()) {
                case 3005871: {
                    if (!var0.equals("auto")) break;
                    var1_1 = 3;
                    break block7;
                }
                case 1658188: {
                    if (!var0.equals("60hz")) break;
                    var1_1 = 2;
                    break block7;
                }
                case 1628397: {
                    if (!var0.equals("50hz")) break;
                    var1_1 = 1;
                    break block7;
                }
                case 109935: {
                    if (!var0.equals("off")) break;
                    var1_1 = 0;
                    break block7;
                }
            }
            ** break;
lbl21: // 1 sources:
            var1_1 = -1;
        }
        if (var1_1 == 0) return 0;
        if (var1_1 == 1) return 1;
        if (var1_1 == 2) return 2;
        if (var1_1 == 3) return 3;
        var2_2 = new StringBuilder();
        var2_2.append("convertAntiBandingMode - Unknown antibanding mode ");
        var2_2.append(var0);
        Log.w("LegacyMetadataMapper", var2_2.toString());
        return -1;
    }

    static int convertAntiBandingModeOrDefault(String string2) {
        int n = LegacyMetadataMapper.convertAntiBandingMode(string2);
        if (n == -1) {
            return 0;
        }
        return n;
    }

    static int convertEffectModeFromLegacy(String string2) {
        if (string2 == null) {
            return 0;
        }
        int n = ArrayUtils.getArrayIndex(sLegacyEffectMode, string2);
        if (n < 0) {
            return -1;
        }
        return sEffectModes[n];
    }

    static String convertEffectModeToLegacy(int n) {
        if ((n = ArrayUtils.getArrayIndex(sEffectModes, n)) < 0) {
            return null;
        }
        return sLegacyEffectMode[n];
    }

    public static void convertRequestMetadata(LegacyRequest legacyRequest) {
        LegacyRequestMapper.convertRequestMetadata(legacyRequest);
    }

    static int convertSceneModeFromLegacy(String string2) {
        if (string2 == null) {
            return 0;
        }
        int n = ArrayUtils.getArrayIndex(sLegacySceneModes, string2);
        if (n < 0) {
            return -1;
        }
        return sSceneModes[n];
    }

    static String convertSceneModeToLegacy(int n) {
        if (n == 1) {
            return "auto";
        }
        if ((n = ArrayUtils.getArrayIndex(sSceneModes, n)) < 0) {
            return null;
        }
        return sLegacySceneModes[n];
    }

    public static CameraCharacteristics createCharacteristics(Camera.Parameters object, Camera.CameraInfo cameraInfo, int n, Size size) {
        Preconditions.checkNotNull(object, "parameters must not be null");
        Preconditions.checkNotNull(cameraInfo, "info must not be null");
        object = ((Camera.Parameters)object).flatten();
        CameraInfo cameraInfo2 = new CameraInfo();
        cameraInfo2.info = cameraInfo;
        return LegacyMetadataMapper.createCharacteristics((String)object, cameraInfo2, n, size);
    }

    public static CameraCharacteristics createCharacteristics(String string2, CameraInfo object, int n, Size size) {
        Preconditions.checkNotNull(string2, "parameters must not be null");
        Preconditions.checkNotNull(object, "info must not be null");
        Preconditions.checkNotNull(((CameraInfo)object).info, "info.info must not be null");
        CameraMetadataNative cameraMetadataNative = new CameraMetadataNative();
        LegacyMetadataMapper.mapCharacteristicsFromInfo(cameraMetadataNative, ((CameraInfo)object).info);
        object = Camera.getEmptyParameters();
        ((Camera.Parameters)object).unflatten(string2);
        LegacyMetadataMapper.mapCharacteristicsFromParameters(cameraMetadataNative, (Camera.Parameters)object);
        cameraMetadataNative.setCameraId(n);
        cameraMetadataNative.setDisplaySize(size);
        return new CameraCharacteristics(cameraMetadataNative);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static CameraMetadataNative createRequestTemplate(CameraCharacteristics var0, int var1_1) {
        block18 : {
            block19 : {
                block17 : {
                    if (ArrayUtils.contains(LegacyMetadataMapper.sAllowedTemplates, (int)var1_5) == false) throw new IllegalArgumentException("templateId out of range");
                    var2_6 = new CameraMetadataNative();
                    var2_6.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(1));
                    var2_6.set(CaptureRequest.CONTROL_AE_ANTIBANDING_MODE, Integer.valueOf(3));
                    var2_6.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(0));
                    var2_6.set(CaptureRequest.CONTROL_AE_LOCK, Boolean.valueOf(false));
                    var2_6.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, Integer.valueOf(0));
                    var2_6.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(0));
                    var2_6.set(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(1));
                    var2_6.set(CaptureRequest.CONTROL_AWB_LOCK, Boolean.valueOf(false));
                    var3_7 = var0.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
                    var4_10 = new MeteringRectangle[]{new MeteringRectangle(0, 0, var3_7.width() - 1, var3_7.height() - 1, 0)};
                    var2_6.set(CaptureRequest.CONTROL_AE_REGIONS, var4_10);
                    var2_6.set(CaptureRequest.CONTROL_AWB_REGIONS, var4_10);
                    var2_6.set(CaptureRequest.CONTROL_AF_REGIONS, var4_10);
                    if (var1_5 != true) {
                        if (var1_5 != 2) {
                            if (var1_5 != 3) throw new AssertionError((Object)"Impossible; keep in sync with sAllowedTemplates");
                            var5_11 = 3;
                        } else {
                            var5_11 = 2;
                        }
                    } else {
                        var5_11 = 1;
                    }
                    var2_6.set(CaptureRequest.CONTROL_CAPTURE_INTENT, Integer.valueOf(var5_11));
                    var2_6.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(1));
                    var2_6.set(CaptureRequest.CONTROL_MODE, Integer.valueOf(1));
                    var4_10 = var0.get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
                    if (var4_10 == null || var4_10.floatValue() != 0.0f) break block17;
                    var5_11 = 0;
                    break block18;
                }
                if (var1_5 == 3 || var1_5 == 4) break block19;
                if (var1_5 != true && var1_5 != 2 || !ArrayUtils.contains(var0.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES), 4)) ** GOTO lbl-1000
                var5_11 = 4;
                break block18;
            }
            if (ArrayUtils.contains(var0.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES), 3)) {
                var5_11 = 3;
            } else lbl-1000: // 2 sources:
            {
                var5_11 = 1;
            }
        }
        var2_6.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(var5_11));
        var6_12 = var0.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
        var7_13 = var6_12[0];
        for (Range<Integer> var3_9 : var6_12) {
            if (var7_13.getUpper() < var3_9.getUpper()) {
                var4_10 = var3_9;
            } else {
                var4_10 = var7_13;
                if (var7_13.getUpper() == var3_9.getUpper()) {
                    var4_10 = var7_13;
                    if ((Integer)var7_13.getLower() < var3_9.getLower()) {
                        var4_10 = var3_9;
                    }
                }
            }
            var7_13 = var4_10;
        }
        var2_6.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, var7_13);
        var2_6.set(CaptureRequest.CONTROL_SCENE_MODE, Integer.valueOf(0));
        var2_6.set(CaptureRequest.STATISTICS_FACE_DETECT_MODE, Integer.valueOf(0));
        var2_6.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
        if (var1_5 == 2) {
            var2_6.set(CaptureRequest.NOISE_REDUCTION_MODE, Integer.valueOf(2));
        } else {
            var2_6.set(CaptureRequest.NOISE_REDUCTION_MODE, Integer.valueOf(1));
        }
        if (var1_5 == 2) {
            var2_6.set(CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE, Integer.valueOf(2));
        } else {
            var2_6.set(CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE, Integer.valueOf(1));
        }
        var2_6.set(CaptureRequest.LENS_FOCAL_LENGTH, Float.valueOf(var0.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS)[0]));
        var0_1 = var0.get(CameraCharacteristics.JPEG_AVAILABLE_THUMBNAIL_SIZES);
        var4_10 = CaptureRequest.JPEG_THUMBNAIL_SIZE;
        if (var0_1.length > 1) {
            var0_2 = var0_1[1];
        } else {
            var0_3 = var0_1[0];
        }
        var2_6.set(var4_10, var0_4);
        return var2_6;
    }

    private static int[] getTagsForKeys(CameraCharacteristics.Key<?>[] arrkey) {
        int[] arrn = new int[arrkey.length];
        for (int i = 0; i < arrkey.length; ++i) {
            arrn[i] = arrkey[i].getNativeKey().getTag();
        }
        return arrn;
    }

    private static int[] getTagsForKeys(CaptureRequest.Key<?>[] arrkey) {
        int[] arrn = new int[arrkey.length];
        for (int i = 0; i < arrkey.length; ++i) {
            arrn[i] = arrkey[i].getNativeKey().getTag();
        }
        return arrn;
    }

    private static int[] getTagsForKeys(CaptureResult.Key<?>[] arrkey) {
        int[] arrn = new int[arrkey.length];
        for (int i = 0; i < arrkey.length; ++i) {
            arrn[i] = arrkey[i].getNativeKey().getTag();
        }
        return arrn;
    }

    private static void mapCharacteristicsFromInfo(CameraMetadataNative cameraMetadataNative, Camera.CameraInfo cameraInfo) {
        CameraCharacteristics.Key<Integer> key = CameraCharacteristics.LENS_FACING;
        int n = cameraInfo.facing == 0 ? 1 : 0;
        cameraMetadataNative.set(key, Integer.valueOf(n));
        cameraMetadataNative.set(CameraCharacteristics.SENSOR_ORIENTATION, Integer.valueOf(cameraInfo.orientation));
    }

    private static void mapCharacteristicsFromParameters(CameraMetadataNative cameraMetadataNative, Camera.Parameters parameters) {
        cameraMetadataNative.set(CameraCharacteristics.COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES, new int[]{1, 2});
        LegacyMetadataMapper.mapControlAe(cameraMetadataNative, parameters);
        LegacyMetadataMapper.mapControlAf(cameraMetadataNative, parameters);
        LegacyMetadataMapper.mapControlAwb(cameraMetadataNative, parameters);
        LegacyMetadataMapper.mapControlOther(cameraMetadataNative, parameters);
        LegacyMetadataMapper.mapLens(cameraMetadataNative, parameters);
        LegacyMetadataMapper.mapFlash(cameraMetadataNative, parameters);
        LegacyMetadataMapper.mapJpeg(cameraMetadataNative, parameters);
        cameraMetadataNative.set(CameraCharacteristics.NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES, new int[]{1, 2});
        LegacyMetadataMapper.mapScaler(cameraMetadataNative, parameters);
        LegacyMetadataMapper.mapSensor(cameraMetadataNative, parameters);
        LegacyMetadataMapper.mapStatistics(cameraMetadataNative, parameters);
        LegacyMetadataMapper.mapSync(cameraMetadataNative, parameters);
        cameraMetadataNative.set(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL, Integer.valueOf(2));
        LegacyMetadataMapper.mapScalerStreamConfigs(cameraMetadataNative, parameters);
        LegacyMetadataMapper.mapRequest(cameraMetadataNative, parameters);
    }

    private static void mapControlAe(CameraMetadataNative cameraMetadataNative, Camera.Parameters parameters) {
        block7 : {
            block8 : {
                Object object;
                int n;
                block10 : {
                    block9 : {
                        Object object2 = parameters.getSupportedAntibanding();
                        if (object2 != null && object2.size() > 0) {
                            object = new int[object2.size()];
                            n = 0;
                            object2 = object2.iterator();
                            while (object2.hasNext()) {
                                object[n] = LegacyMetadataMapper.convertAntiBandingMode((String)object2.next());
                                ++n;
                            }
                            cameraMetadataNative.set(CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES, Arrays.copyOf((int[])object, n));
                        } else {
                            cameraMetadataNative.set(CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES, new int[0]);
                        }
                        object = parameters.getSupportedPreviewFpsRange();
                        if (object == null) break block7;
                        n = object.size();
                        if (n <= 0) break block8;
                        Range[] arrrange = new Range[n];
                        n = 0;
                        object2 = object.iterator();
                        while (object2.hasNext()) {
                            int[] arrn = (int[])object2.next();
                            arrrange[n] = Range.create((int)Math.floor((double)arrn[0] / 1000.0), (int)Math.ceil((double)arrn[1] / 1000.0));
                            ++n;
                        }
                        cameraMetadataNative.set(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES, arrrange);
                        object2 = ArrayUtils.convertStringListToIntArray(parameters.getSupportedFlashModes(), new String[]{"off", "auto", "on", "red-eye", "torch"}, new int[]{1, 2, 3, 4});
                        if (object2 == null) break block9;
                        object = object2;
                        if (((int[])object2).length != 0) break block10;
                    }
                    object = new int[]{1};
                }
                cameraMetadataNative.set(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES, object);
                n = parameters.getMinExposureCompensation();
                int n2 = parameters.getMaxExposureCompensation();
                cameraMetadataNative.set(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE, Range.create(n, n2));
                float f = parameters.getExposureCompensationStep();
                cameraMetadataNative.set(CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP, ParamsUtils.createRational(f));
                boolean bl = parameters.isAutoExposureLockSupported();
                cameraMetadataNative.set(CameraCharacteristics.CONTROL_AE_LOCK_AVAILABLE, Boolean.valueOf(bl));
                return;
            }
            throw new AssertionError((Object)"At least one FPS range must be supported.");
        }
        throw new AssertionError((Object)"Supported FPS ranges cannot be null.");
    }

    private static void mapControlAf(CameraMetadataNative cameraMetadataNative, Camera.Parameters arrayList) {
        block3 : {
            block2 : {
                List<Integer> list = ArrayUtils.convertStringListToIntList(((Camera.Parameters)((Object)arrayList)).getSupportedFocusModes(), new String[]{"auto", "continuous-picture", "continuous-video", "edof", "infinity", "macro", "fixed"}, new int[]{1, 4, 3, 5, 0, 2, 0});
                if (list == null) break block2;
                arrayList = list;
                if (list.size() != 0) break block3;
            }
            Log.w(TAG, "No AF modes supported (HAL bug); defaulting to AF_MODE_OFF only");
            arrayList = new ArrayList<Integer>(1);
            arrayList.add(0);
        }
        cameraMetadataNative.set(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES, ArrayUtils.toIntArray((List<Integer>)arrayList));
    }

    private static void mapControlAwb(CameraMetadataNative cameraMetadataNative, Camera.Parameters parameters) {
        List<Integer> list;
        block3 : {
            block2 : {
                List<Integer> list2 = ArrayUtils.convertStringListToIntList(parameters.getSupportedWhiteBalance(), new String[]{"auto", "incandescent", "fluorescent", "warm-fluorescent", "daylight", "cloudy-daylight", "twilight", "shade"}, new int[]{1, 2, 3, 4, 5, 6, 7, 8});
                if (list2 == null) break block2;
                list = list2;
                if (list2.size() != 0) break block3;
            }
            Log.w(TAG, "No AWB modes supported (HAL bug); defaulting to AWB_MODE_AUTO only");
            list = new ArrayList<Integer>(1);
            list.add(1);
        }
        cameraMetadataNative.set(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES, ArrayUtils.toIntArray(list));
        boolean bl = parameters.isAutoWhiteBalanceLockSupported();
        cameraMetadataNative.set(CameraCharacteristics.CONTROL_AWB_LOCK_AVAILABLE, Boolean.valueOf(bl));
    }

    private static void mapControlOther(CameraMetadataNative cameraMetadataNative, Camera.Parameters object) {
        Object object2;
        if (((Camera.Parameters)object).isVideoStabilizationSupported()) {
            object2 = new int[2];
            int[] arrn = object2;
            arrn[0] = 0;
            arrn[1] = 1;
        } else {
            object2 = new int[]{0};
        }
        cameraMetadataNative.set(CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES, object2);
        int n = ((Camera.Parameters)object).getMaxNumMeteringAreas();
        int n2 = ((Camera.Parameters)object).getMaxNumFocusAreas();
        cameraMetadataNative.set(CameraCharacteristics.CONTROL_MAX_REGIONS, new int[]{n, 0, n2});
        object2 = ((Camera.Parameters)object).getSupportedColorEffects();
        object2 = object2 == null ? new int[0] : ArrayUtils.convertStringListToIntArray((List<String>)object2, sLegacyEffectMode, sEffectModes);
        cameraMetadataNative.set(CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS, object2);
        int n3 = ((Camera.Parameters)object).getMaxNumDetectedFaces();
        List<String> list = ((Camera.Parameters)object).getSupportedSceneModes();
        object = object2 = ArrayUtils.convertStringListToIntList(list, sLegacySceneModes, sSceneModes);
        if (list != null) {
            object = object2;
            if (list.size() == 1) {
                object = object2;
                if (list.get(0).equals("auto")) {
                    object = null;
                }
            }
        }
        n = n2 = 1;
        if (object == null) {
            n = n2;
            if (n3 == 0) {
                n = 0;
            }
        }
        if (n != 0) {
            object2 = object;
            if (object == null) {
                object2 = new ArrayList();
            }
            if (n3 > 0) {
                object2.add(1);
            }
            if (object2.contains(0)) {
                while (object2.remove(new Integer(0))) {
                }
            }
            cameraMetadataNative.set(CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES, ArrayUtils.toIntArray((List<Integer>)object2));
        } else {
            cameraMetadataNative.set(CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES, new int[]{0});
        }
        object2 = CameraCharacteristics.CONTROL_AVAILABLE_MODES;
        if (n != 0) {
            Object object3 = object = new int[2];
            object3[0] = true;
            object3[1] = 2;
        } else {
            object = new int[]{true};
        }
        cameraMetadataNative.set(object2, object);
    }

    private static void mapFlash(CameraMetadataNative cameraMetadataNative, Camera.Parameters object) {
        boolean bl = false;
        if ((object = ((Camera.Parameters)object).getSupportedFlashModes()) != null) {
            bl = ListUtils.listElementsEqualTo(object, "off") ^ true;
        }
        cameraMetadataNative.set(CameraCharacteristics.FLASH_INFO_AVAILABLE, Boolean.valueOf(bl));
    }

    private static void mapJpeg(CameraMetadataNative cameraMetadataNative, Camera.Parameters arrsize) {
        if ((arrsize = arrsize.getSupportedJpegThumbnailSizes()) != null) {
            arrsize = ParameterUtils.convertSizeListToArray((List<Camera.Size>)arrsize);
            Arrays.sort(arrsize, new android.hardware.camera2.utils.SizeAreaComparator());
            cameraMetadataNative.set(CameraCharacteristics.JPEG_AVAILABLE_THUMBNAIL_SIZES, arrsize);
        }
    }

    private static void mapLens(CameraMetadataNative cameraMetadataNative, Camera.Parameters parameters) {
        if ("fixed".equals(parameters.getFocusMode())) {
            cameraMetadataNative.set(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE, Float.valueOf(0.0f));
        }
        float f = parameters.getFocalLength();
        cameraMetadataNative.set(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS, new float[]{f});
    }

    private static void mapRequest(CameraMetadataNative cameraMetadataNative, Camera.Parameters arrkey) {
        cameraMetadataNative.set(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES, new int[]{0});
        Object object = new ArrayList(Arrays.asList(CameraCharacteristics.COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES, CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES, CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES, CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES, CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE, CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP, CameraCharacteristics.CONTROL_AE_LOCK_AVAILABLE, CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES, CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS, CameraCharacteristics.CONTROL_AVAILABLE_MODES, CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES, CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES, CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES, CameraCharacteristics.CONTROL_AWB_LOCK_AVAILABLE, CameraCharacteristics.CONTROL_MAX_REGIONS, CameraCharacteristics.FLASH_INFO_AVAILABLE, CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL, CameraCharacteristics.JPEG_AVAILABLE_THUMBNAIL_SIZES, CameraCharacteristics.LENS_FACING, CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS, CameraCharacteristics.NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES, CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES, CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_STREAMS, CameraCharacteristics.REQUEST_PARTIAL_RESULT_COUNT, CameraCharacteristics.REQUEST_PIPELINE_MAX_DEPTH, CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM, CameraCharacteristics.SCALER_CROPPING_TYPE, CameraCharacteristics.SENSOR_AVAILABLE_TEST_PATTERN_MODES, CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE, CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE, CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE, CameraCharacteristics.SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE, CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE, CameraCharacteristics.SENSOR_ORIENTATION, CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES, CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT, CameraCharacteristics.SYNC_MAX_LATENCY));
        if (cameraMetadataNative.get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE) != null) {
            object.add(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
        }
        cameraMetadataNative.set(CameraCharacteristics.REQUEST_AVAILABLE_CHARACTERISTICS_KEYS, LegacyMetadataMapper.getTagsForKeys(object.toArray(new CameraCharacteristics.Key[0])));
        ArrayList<CaptureRequest.Key> arrayList = new ArrayList<CaptureRequest.Key>(Arrays.asList(CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE, CaptureRequest.CONTROL_AE_ANTIBANDING_MODE, CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, CaptureRequest.CONTROL_AE_LOCK, CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_TRIGGER, CaptureRequest.CONTROL_AWB_LOCK, CaptureRequest.CONTROL_AWB_MODE, CaptureRequest.CONTROL_CAPTURE_INTENT, CaptureRequest.CONTROL_EFFECT_MODE, CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_SCENE_MODE, CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, CaptureRequest.FLASH_MODE, CaptureRequest.JPEG_GPS_COORDINATES, CaptureRequest.JPEG_GPS_PROCESSING_METHOD, CaptureRequest.JPEG_GPS_TIMESTAMP, CaptureRequest.JPEG_ORIENTATION, CaptureRequest.JPEG_QUALITY, CaptureRequest.JPEG_THUMBNAIL_QUALITY, CaptureRequest.JPEG_THUMBNAIL_SIZE, CaptureRequest.LENS_FOCAL_LENGTH, CaptureRequest.NOISE_REDUCTION_MODE, CaptureRequest.SCALER_CROP_REGION, CaptureRequest.STATISTICS_FACE_DETECT_MODE));
        if (arrkey.getMaxNumMeteringAreas() > 0) {
            arrayList.add(CaptureRequest.CONTROL_AE_REGIONS);
        }
        if (arrkey.getMaxNumFocusAreas() > 0) {
            arrayList.add(CaptureRequest.CONTROL_AF_REGIONS);
        }
        object = new CaptureRequest.Key[arrayList.size()];
        arrayList.toArray((T[])object);
        cameraMetadataNative.set(CameraCharacteristics.REQUEST_AVAILABLE_REQUEST_KEYS, LegacyMetadataMapper.getTagsForKeys(object));
        object = new ArrayList<CaptureResult.Key>(Arrays.asList(CaptureResult.COLOR_CORRECTION_ABERRATION_MODE, CaptureResult.CONTROL_AE_ANTIBANDING_MODE, CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION, CaptureResult.CONTROL_AE_LOCK, CaptureResult.CONTROL_AE_MODE, CaptureResult.CONTROL_AF_MODE, CaptureResult.CONTROL_AF_STATE, CaptureResult.CONTROL_AWB_MODE, CaptureResult.CONTROL_AWB_LOCK, CaptureResult.CONTROL_MODE, CaptureResult.FLASH_MODE, CaptureResult.JPEG_GPS_COORDINATES, CaptureResult.JPEG_GPS_PROCESSING_METHOD, CaptureResult.JPEG_GPS_TIMESTAMP, CaptureResult.JPEG_ORIENTATION, CaptureResult.JPEG_QUALITY, CaptureResult.JPEG_THUMBNAIL_QUALITY, CaptureResult.LENS_FOCAL_LENGTH, CaptureResult.NOISE_REDUCTION_MODE, CaptureResult.REQUEST_PIPELINE_DEPTH, CaptureResult.SCALER_CROP_REGION, CaptureResult.SENSOR_TIMESTAMP, CaptureResult.STATISTICS_FACE_DETECT_MODE));
        if (arrkey.getMaxNumMeteringAreas() > 0) {
            object.add(CaptureResult.CONTROL_AE_REGIONS);
        }
        if (arrkey.getMaxNumFocusAreas() > 0) {
            object.add(CaptureResult.CONTROL_AF_REGIONS);
        }
        arrkey = new CaptureResult.Key[object.size()];
        object.toArray(arrkey);
        cameraMetadataNative.set(CameraCharacteristics.REQUEST_AVAILABLE_RESULT_KEYS, LegacyMetadataMapper.getTagsForKeys(arrkey));
        cameraMetadataNative.set(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_STREAMS, new int[]{0, 3, 1});
        cameraMetadataNative.set(CameraCharacteristics.REQUEST_MAX_NUM_INPUT_STREAMS, Integer.valueOf(0));
        cameraMetadataNative.set(CameraCharacteristics.REQUEST_PARTIAL_RESULT_COUNT, Integer.valueOf(1));
        cameraMetadataNative.set(CameraCharacteristics.REQUEST_PIPELINE_MAX_DEPTH, Byte.valueOf((byte)6));
    }

    private static void mapScaler(CameraMetadataNative cameraMetadataNative, Camera.Parameters parameters) {
        cameraMetadataNative.set(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM, Float.valueOf(ParameterUtils.getMaxZoomRatio(parameters)));
        cameraMetadataNative.set(CameraCharacteristics.SCALER_CROPPING_TYPE, Integer.valueOf(0));
    }

    /*
     * WARNING - void declaration
     */
    private static void mapScalerStreamConfigs(CameraMetadataNative cameraMetadataNative, Camera.Parameters arrstreamConfigurationDuration) {
        void var6_12;
        int n;
        ArrayList<StreamConfiguration> arrayList = new ArrayList<StreamConfiguration>();
        Iterator<Camera.Size> iterator = arrstreamConfigurationDuration.getSupportedPreviewSizes();
        List<Camera.Size> list = arrstreamConfigurationDuration.getSupportedPictureSizes();
        SizeAreaComparator sizeAreaComparator = new SizeAreaComparator();
        Collections.sort(iterator, sizeAreaComparator);
        Camera.Size object2 = SizeAreaComparator.findLargestByArea(list);
        float f = (float)object2.width * 1.0f / (float)object2.height;
        while (!iterator.isEmpty()) {
            n = iterator.size() - 1;
            Camera.Size size = iterator.get(n);
            if (!(Math.abs(f - (float)size.width * 1.0f / (float)size.height) >= 0.01f)) break;
            iterator.remove(n);
        }
        List<Camera.Size> list2 = iterator;
        if (iterator.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mapScalerStreamConfigs - failed to find any preview size matching JPEG aspect ratio ");
            stringBuilder.append(f);
            Log.w(TAG, stringBuilder.toString());
            List<Camera.Size> list3 = arrstreamConfigurationDuration.getSupportedPreviewSizes();
        }
        Collections.sort(var6_12, Collections.reverseOrder(sizeAreaComparator));
        LegacyMetadataMapper.appendStreamConfig(arrayList, 34, (List<Camera.Size>)var6_12);
        LegacyMetadataMapper.appendStreamConfig(arrayList, 35, (List<Camera.Size>)var6_12);
        iterator = arrstreamConfigurationDuration.getSupportedPreviewFormats().iterator();
        while (iterator.hasNext()) {
            n = (Integer)iterator.next();
            if (!ImageFormat.isPublicFormat(n) || n == 17) continue;
            LegacyMetadataMapper.appendStreamConfig(arrayList, n, (List<Camera.Size>)var6_12);
        }
        LegacyMetadataMapper.appendStreamConfig(arrayList, 33, arrstreamConfigurationDuration.getSupportedPictureSizes());
        cameraMetadataNative.set(CameraCharacteristics.SCALER_AVAILABLE_STREAM_CONFIGURATIONS, arrayList.toArray(new StreamConfiguration[0]));
        cameraMetadataNative.set(CameraCharacteristics.SCALER_AVAILABLE_MIN_FRAME_DURATIONS, new StreamConfigurationDuration[0]);
        arrstreamConfigurationDuration = new StreamConfigurationDuration[list.size()];
        n = 0;
        long l = -1L;
        for (Camera.Size size : list) {
            long l2 = LegacyMetadataMapper.calculateJpegStallDuration(size);
            arrstreamConfigurationDuration[n] = new StreamConfigurationDuration(33, size.width, size.height, l2);
            long l3 = l;
            if (l < l2) {
                l3 = l2;
            }
            ++n;
            l = l3;
        }
        cameraMetadataNative.set(CameraCharacteristics.SCALER_AVAILABLE_STALL_DURATIONS, arrstreamConfigurationDuration);
        cameraMetadataNative.set(CameraCharacteristics.SENSOR_INFO_MAX_FRAME_DURATION, Long.valueOf(l));
    }

    private static void mapSensor(CameraMetadataNative cameraMetadataNative, Camera.Parameters parameters) {
        Size size = ParameterUtils.getLargestSupportedJpegSizeByArea(parameters);
        Rect rect = ParamsUtils.createRect(size);
        cameraMetadataNative.set(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE, rect);
        cameraMetadataNative.set(CameraCharacteristics.SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE, rect);
        cameraMetadataNative.set(CameraCharacteristics.SENSOR_AVAILABLE_TEST_PATTERN_MODES, new int[]{0});
        cameraMetadataNative.set(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE, size);
        float f = parameters.getFocalLength();
        double d = (double)parameters.getHorizontalViewAngle() * 3.141592653589793 / 180.0;
        double d2 = (double)parameters.getVerticalViewAngle() * 3.141592653589793 / 180.0;
        float f2 = (float)Math.abs((double)(f * 2.0f) * Math.tan(d2 / 2.0));
        f = (float)Math.abs((double)(2.0f * f) * Math.tan(d / 2.0));
        cameraMetadataNative.set(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE, new SizeF(f, f2));
        cameraMetadataNative.set(CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE, Integer.valueOf(0));
    }

    private static void mapStatistics(CameraMetadataNative cameraMetadataNative, Camera.Parameters parameters) {
        int[] arrn;
        if (parameters.getMaxNumDetectedFaces() > 0) {
            int[] arrn2 = arrn = new int[2];
            arrn2[0] = 0;
            arrn2[1] = 1;
        } else {
            arrn = new int[]{0};
        }
        cameraMetadataNative.set(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES, arrn);
        cameraMetadataNative.set(CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT, Integer.valueOf(parameters.getMaxNumDetectedFaces()));
    }

    private static void mapSync(CameraMetadataNative cameraMetadataNative, Camera.Parameters parameters) {
        cameraMetadataNative.set(CameraCharacteristics.SYNC_MAX_LATENCY, Integer.valueOf(-1));
    }
}

