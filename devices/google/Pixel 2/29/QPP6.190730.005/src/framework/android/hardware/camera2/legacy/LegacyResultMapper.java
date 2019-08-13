/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.legacy.LegacyMetadataMapper;
import android.hardware.camera2.legacy.LegacyRequest;
import android.hardware.camera2.legacy.LegacyRequestMapper;
import android.hardware.camera2.legacy.ParameterUtils;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.utils.ParamsUtils;
import android.location.Location;
import android.util.Log;
import android.util.Size;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LegacyResultMapper {
    private static final boolean DEBUG = false;
    private static final String TAG = "LegacyResultMapper";
    private LegacyRequest mCachedRequest = null;
    private CameraMetadataNative mCachedResult = null;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static int convertLegacyAfMode(String var0) {
        block19 : {
            if (var0 == null) {
                Log.w("LegacyResultMapper", "convertLegacyAfMode - no AF mode, default to OFF");
                return 0;
            }
            var1_1 = -1;
            switch (var0.hashCode()) {
                case 910005312: {
                    if (!var0.equals("continuous-picture")) break;
                    var1_1 = 1;
                    ** break;
                }
                case 173173288: {
                    if (!var0.equals("infinity")) break;
                    var1_1 = 6;
                    ** break;
                }
                case 103652300: {
                    if (!var0.equals("macro")) break;
                    var1_1 = 4;
                    ** break;
                }
                case 97445748: {
                    if (!var0.equals("fixed")) break;
                    var1_1 = 5;
                    ** break;
                }
                case 3108534: {
                    if (!var0.equals("edof")) break;
                    var1_1 = 3;
                    ** break;
                }
                case 3005871: {
                    if (!var0.equals("auto")) break;
                    var1_1 = 0;
                    ** break;
                }
                case -194628547: {
                    if (!var0.equals("continuous-video")) break;
                    var1_1 = 2;
                    break block19;
                }
            }
            ** break;
        }
        switch (var1_1) {
            default: {
                var2_2 = new StringBuilder();
                var2_2.append("convertLegacyAfMode - unknown mode ");
                var2_2.append(var0);
                var2_2.append(" , ignoring");
                Log.w("LegacyResultMapper", var2_2.toString());
                return 0;
            }
            case 6: {
                return 0;
            }
            case 5: {
                return 0;
            }
            case 4: {
                return 2;
            }
            case 3: {
                return 5;
            }
            case 2: {
                return 3;
            }
            case 1: {
                return 4;
            }
            case 0: 
        }
        return 1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static int convertLegacyAwbMode(String var0) {
        block21 : {
            if (var0 == null) {
                return 1;
            }
            var1_1 = -1;
            switch (var0.hashCode()) {
                case 1942983418: {
                    if (!var0.equals("daylight")) break;
                    var1_1 = 4;
                    ** break;
                }
                case 1902580840: {
                    if (!var0.equals("fluorescent")) break;
                    var1_1 = 2;
                    ** break;
                }
                case 1650323088: {
                    if (!var0.equals("twilight")) break;
                    var1_1 = 6;
                    ** break;
                }
                case 474934723: {
                    if (!var0.equals("cloudy-daylight")) break;
                    var1_1 = 5;
                    ** break;
                }
                case 109399597: {
                    if (!var0.equals("shade")) break;
                    var1_1 = 7;
                    ** break;
                }
                case 3005871: {
                    if (!var0.equals("auto")) break;
                    var1_1 = 0;
                    ** break;
                }
                case -719316704: {
                    if (!var0.equals("warm-fluorescent")) break;
                    var1_1 = 3;
                    ** break;
                }
                case -939299377: {
                    if (!var0.equals("incandescent")) break;
                    var1_1 = 1;
                    break block21;
                }
            }
            ** break;
        }
        switch (var1_1) {
            default: {
                var2_2 = new StringBuilder();
                var2_2.append("convertAwbMode - unrecognized WB mode ");
                var2_2.append(var0);
                Log.w("LegacyResultMapper", var2_2.toString());
                return 1;
            }
            case 7: {
                return 8;
            }
            case 6: {
                return 7;
            }
            case 5: {
                return 6;
            }
            case 4: {
                return 5;
            }
            case 3: {
                return 4;
            }
            case 2: {
                return 3;
            }
            case 1: {
                return 2;
            }
            case 0: 
        }
        return 1;
    }

    private static CameraMetadataNative convertResultMetadata(LegacyRequest object) {
        Object object2 = ((LegacyRequest)object).characteristics;
        CaptureRequest captureRequest = ((LegacyRequest)object).captureRequest;
        Object object3 = ((LegacyRequest)object).previewSize;
        Camera.Parameters parameters = ((LegacyRequest)object).parameters;
        object = new CameraMetadataNative();
        Object object4 = ((CameraCharacteristics)object2).get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        object3 = ParameterUtils.convertScalerCropRegion((Rect)object4, captureRequest.get(CaptureRequest.SCALER_CROP_REGION), (Size)object3, parameters);
        ((CameraMetadataNative)object).set(CaptureResult.COLOR_CORRECTION_ABERRATION_MODE, captureRequest.get(CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE));
        LegacyResultMapper.mapAe((CameraMetadataNative)object, (CameraCharacteristics)object2, captureRequest, (Rect)object4, (ParameterUtils.ZoomData)object3, parameters);
        LegacyResultMapper.mapAf((CameraMetadataNative)object, (Rect)object4, (ParameterUtils.ZoomData)object3, parameters);
        LegacyResultMapper.mapAwb((CameraMetadataNative)object, parameters);
        Object object5 = CaptureRequest.CONTROL_CAPTURE_INTENT;
        int n = 1;
        object4 = 1;
        int n2 = LegacyRequestMapper.filterSupportedCaptureIntent((Integer)ParamsUtils.getOrDefault(captureRequest, object5, object4));
        ((CameraMetadataNative)object).set(CaptureResult.CONTROL_CAPTURE_INTENT, Integer.valueOf(n2));
        object5 = CaptureRequest.CONTROL_MODE;
        if ((Integer)ParamsUtils.getOrDefault(captureRequest, object5, object4) == 2) {
            ((CameraMetadataNative)object).set(CaptureResult.CONTROL_MODE, Integer.valueOf(2));
        } else {
            ((CameraMetadataNative)object).set(CaptureResult.CONTROL_MODE, object4);
        }
        object5 = parameters.getSceneMode();
        n2 = LegacyMetadataMapper.convertSceneModeFromLegacy((String)object5);
        if (n2 != -1) {
            ((CameraMetadataNative)object).set(CaptureResult.CONTROL_SCENE_MODE, Integer.valueOf(n2));
        } else {
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("Unknown scene mode ");
            ((StringBuilder)object4).append((String)object5);
            ((StringBuilder)object4).append(" returned by camera HAL, setting to disabled.");
            Log.w(TAG, ((StringBuilder)object4).toString());
            ((CameraMetadataNative)object).set(CaptureResult.CONTROL_SCENE_MODE, Integer.valueOf(0));
        }
        object4 = parameters.getColorEffect();
        n2 = LegacyMetadataMapper.convertEffectModeFromLegacy((String)object4);
        if (n2 != -1) {
            ((CameraMetadataNative)object).set(CaptureResult.CONTROL_EFFECT_MODE, Integer.valueOf(n2));
        } else {
            object5 = new StringBuilder();
            ((StringBuilder)object5).append("Unknown effect mode ");
            ((StringBuilder)object5).append((String)object4);
            ((StringBuilder)object5).append(" returned by camera HAL, setting to off.");
            Log.w(TAG, ((StringBuilder)object5).toString());
            ((CameraMetadataNative)object).set(CaptureResult.CONTROL_EFFECT_MODE, Integer.valueOf(0));
        }
        if (!parameters.isVideoStabilizationSupported() || !parameters.getVideoStabilization()) {
            n = 0;
        }
        ((CameraMetadataNative)object).set(CaptureResult.CONTROL_VIDEO_STABILIZATION_MODE, Integer.valueOf(n));
        if ("infinity".equals(parameters.getFocusMode())) {
            ((CameraMetadataNative)object).set(CaptureResult.LENS_FOCUS_DISTANCE, Float.valueOf(0.0f));
        }
        ((CameraMetadataNative)object).set(CaptureResult.LENS_FOCAL_LENGTH, Float.valueOf(parameters.getFocalLength()));
        ((CameraMetadataNative)object).set(CaptureResult.REQUEST_PIPELINE_DEPTH, ((CameraCharacteristics)object2).get(CameraCharacteristics.REQUEST_PIPELINE_MAX_DEPTH));
        LegacyResultMapper.mapScaler((CameraMetadataNative)object, (ParameterUtils.ZoomData)object3, parameters);
        ((CameraMetadataNative)object).set(CaptureResult.SENSOR_TEST_PATTERN_MODE, Integer.valueOf(0));
        ((CameraMetadataNative)object).set(CaptureResult.JPEG_GPS_LOCATION, captureRequest.get(CaptureRequest.JPEG_GPS_LOCATION));
        ((CameraMetadataNative)object).set(CaptureResult.JPEG_ORIENTATION, captureRequest.get(CaptureRequest.JPEG_ORIENTATION));
        ((CameraMetadataNative)object).set(CaptureResult.JPEG_QUALITY, Byte.valueOf((byte)parameters.getJpegQuality()));
        ((CameraMetadataNative)object).set(CaptureResult.JPEG_THUMBNAIL_QUALITY, Byte.valueOf((byte)parameters.getJpegThumbnailQuality()));
        object2 = parameters.getJpegThumbnailSize();
        if (object2 != null) {
            ((CameraMetadataNative)object).set(CaptureResult.JPEG_THUMBNAIL_SIZE, ParameterUtils.convertSize((Camera.Size)object2));
        } else {
            Log.w(TAG, "Null thumbnail size received from parameters.");
        }
        ((CameraMetadataNative)object).set(CaptureResult.NOISE_REDUCTION_MODE, captureRequest.get(CaptureRequest.NOISE_REDUCTION_MODE));
        return object;
    }

    private static MeteringRectangle[] getMeteringRectangles(Rect rect, ParameterUtils.ZoomData zoomData, List<Camera.Area> object, String object2) {
        object2 = new ArrayList();
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                Camera.Area area = (Camera.Area)object.next();
                object2.add(ParameterUtils.convertCameraAreaToActiveArrayRectangle(rect, zoomData, area).toMetering());
            }
        }
        return object2.toArray(new MeteringRectangle[0]);
    }

    private static void mapAe(CameraMetadataNative cameraMetadataNative, CameraCharacteristics arrmeteringRectangle, CaptureRequest object, Rect rect, ParameterUtils.ZoomData zoomData, Camera.Parameters parameters) {
        int n = LegacyMetadataMapper.convertAntiBandingModeOrDefault(parameters.getAntibanding());
        cameraMetadataNative.set(CaptureResult.CONTROL_AE_ANTIBANDING_MODE, Integer.valueOf(n));
        cameraMetadataNative.set(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(parameters.getExposureCompensation()));
        boolean bl = parameters.isAutoExposureLockSupported() ? parameters.getAutoExposureLock() : false;
        cameraMetadataNative.set(CaptureResult.CONTROL_AE_LOCK, Boolean.valueOf(bl));
        Boolean bl2 = ((CaptureRequest)object).get(CaptureRequest.CONTROL_AE_LOCK);
        if (bl2 != null && bl2 != bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("mapAe - android.control.aeLock was requested to ");
            ((StringBuilder)object).append(bl2);
            ((StringBuilder)object).append(" but resulted in ");
            ((StringBuilder)object).append(bl);
            Log.w(TAG, ((StringBuilder)object).toString());
        }
        LegacyResultMapper.mapAeAndFlashMode(cameraMetadataNative, (CameraCharacteristics)arrmeteringRectangle, parameters);
        if (parameters.getMaxNumMeteringAreas() > 0) {
            arrmeteringRectangle = LegacyResultMapper.getMeteringRectangles(rect, zoomData, parameters.getMeteringAreas(), "AE");
            cameraMetadataNative.set(CaptureResult.CONTROL_AE_REGIONS, arrmeteringRectangle);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void mapAeAndFlashMode(CameraMetadataNative var0, CameraCharacteristics var1_1, Camera.Parameters var2_2) {
        block16 : {
            block17 : {
                var3_3 = 0;
                var4_4 = var1_1.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                var5_5 = 0;
                var1_1 = var4_4 != false ? null : Integer.valueOf(0);
                var6_6 = 1;
                var7_7 = var2_2.getFlashMode();
                var8_8 = var3_3;
                var9_9 = var1_1;
                var10_10 = var6_6;
                if (var7_7 == null) break block16;
                switch (var7_7.hashCode()) {
                    case 1081542389: {
                        if (!var7_7.equals("red-eye")) break;
                        var5_5 = 3;
                        break block17;
                    }
                    case 110547964: {
                        if (!var7_7.equals("torch")) break;
                        var5_5 = 4;
                        break block17;
                    }
                    case 3005871: {
                        if (!var7_7.equals("auto")) break;
                        var5_5 = 1;
                        break block17;
                    }
                    case 109935: {
                        if (!var7_7.equals("off")) break;
                        break block17;
                    }
                    case 3551: {
                        if (!var7_7.equals("on")) break;
                        var5_5 = 2;
                        break block17;
                    }
                }
                ** break;
lbl32: // 1 sources:
                var5_5 = -1;
            }
            var8_8 = var3_3;
            var9_9 = var1_1;
            var10_10 = var6_6;
            if (var5_5 != 0) {
                if (var5_5 != 1) {
                    if (var5_5 != 2) {
                        if (var5_5 != 3) {
                            if (var5_5 != 4) {
                                var9_9 = new StringBuilder();
                                var9_9.append("mapAeAndFlashMode - Ignoring unknown flash mode ");
                                var9_9.append(var2_2.getFlashMode());
                                Log.w("LegacyResultMapper", var9_9.toString());
                                var8_8 = var3_3;
                                var9_9 = var1_1;
                                var10_10 = var6_6;
                            } else {
                                var8_8 = 2;
                                var9_9 = 3;
                                var10_10 = var6_6;
                            }
                        } else {
                            var10_10 = 4;
                            var8_8 = var3_3;
                            var9_9 = var1_1;
                        }
                    } else {
                        var8_8 = 1;
                        var10_10 = 3;
                        var9_9 = 3;
                    }
                } else {
                    var10_10 = 2;
                    var9_9 = var1_1;
                    var8_8 = var3_3;
                }
            }
        }
        var0.set(CaptureResult.FLASH_STATE, var9_9);
        var0.set(CaptureResult.FLASH_MODE, Integer.valueOf(var8_8));
        var0.set(CaptureResult.CONTROL_AE_MODE, Integer.valueOf(var10_10));
    }

    private static void mapAf(CameraMetadataNative cameraMetadataNative, Rect arrmeteringRectangle, ParameterUtils.ZoomData zoomData, Camera.Parameters parameters) {
        cameraMetadataNative.set(CaptureResult.CONTROL_AF_MODE, Integer.valueOf(LegacyResultMapper.convertLegacyAfMode(parameters.getFocusMode())));
        if (parameters.getMaxNumFocusAreas() > 0) {
            arrmeteringRectangle = LegacyResultMapper.getMeteringRectangles((Rect)arrmeteringRectangle, zoomData, parameters.getFocusAreas(), "AF");
            cameraMetadataNative.set(CaptureResult.CONTROL_AF_REGIONS, arrmeteringRectangle);
        }
    }

    private static void mapAwb(CameraMetadataNative cameraMetadataNative, Camera.Parameters parameters) {
        boolean bl = parameters.isAutoWhiteBalanceLockSupported() ? parameters.getAutoWhiteBalanceLock() : false;
        cameraMetadataNative.set(CaptureResult.CONTROL_AWB_LOCK, Boolean.valueOf(bl));
        int n = LegacyResultMapper.convertLegacyAwbMode(parameters.getWhiteBalance());
        cameraMetadataNative.set(CaptureResult.CONTROL_AWB_MODE, Integer.valueOf(n));
    }

    private static void mapScaler(CameraMetadataNative cameraMetadataNative, ParameterUtils.ZoomData zoomData, Camera.Parameters parameters) {
        cameraMetadataNative.set(CaptureResult.SCALER_CROP_REGION, zoomData.reportedCrop);
    }

    public CameraMetadataNative cachedConvertResultMetadata(LegacyRequest object, long l) {
        if (this.mCachedRequest != null && ((LegacyRequest)object).parameters.same(this.mCachedRequest.parameters) && ((LegacyRequest)object).captureRequest.equals((Object)this.mCachedRequest.captureRequest)) {
            object = new CameraMetadataNative(this.mCachedResult);
        } else {
            CameraMetadataNative cameraMetadataNative = LegacyResultMapper.convertResultMetadata((LegacyRequest)object);
            this.mCachedRequest = object;
            this.mCachedResult = new CameraMetadataNative(cameraMetadataNative);
            object = cameraMetadataNative;
        }
        ((CameraMetadataNative)object).set(CaptureResult.SENSOR_TIMESTAMP, Long.valueOf(l));
        return object;
    }
}

