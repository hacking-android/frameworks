/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.legacy.LegacyMetadataMapper;
import android.hardware.camera2.legacy.LegacyRequest;
import android.hardware.camera2.legacy.ParameterUtils;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.utils.ListUtils;
import android.hardware.camera2.utils.ParamsUtils;
import android.location.Location;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LegacyRequestMapper {
    private static final boolean DEBUG = false;
    private static final byte DEFAULT_JPEG_QUALITY = 85;
    private static final String TAG = "LegacyRequestMapper";

    private static boolean checkForCompleteGpsData(Location location) {
        boolean bl = location != null && location.getProvider() != null && location.getTime() != 0L;
        return bl;
    }

    private static String convertAeAntiBandingModeToLegacy(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "auto";
                }
                return "60hz";
            }
            return "50hz";
        }
        return "off";
    }

    private static int[] convertAeFpsRangeToLegacy(Range<Integer> range) {
        return new int[]{range.getLower() * 1000, range.getUpper() * 1000};
    }

    private static String convertAwbModeToLegacy(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("convertAwbModeToLegacy - unrecognized control.awbMode");
                stringBuilder.append(n);
                Log.w(TAG, stringBuilder.toString());
                return "auto";
            }
            case 8: {
                return "shade";
            }
            case 7: {
                return "twilight";
            }
            case 6: {
                return "cloudy-daylight";
            }
            case 5: {
                return "daylight";
            }
            case 4: {
                return "warm-fluorescent";
            }
            case 3: {
                return "fluorescent";
            }
            case 2: {
                return "incandescent";
            }
            case 1: 
        }
        return "auto";
    }

    private static List<Camera.Area> convertMeteringRegionsToLegacy(Rect object, ParameterUtils.ZoomData zoomData, MeteringRectangle[] object2, int n, String string2) {
        if (object2 != null && n > 0) {
            ArrayList<MeteringRectangle> arrayList = new ArrayList<MeteringRectangle>();
            for (MeteringRectangle meteringRectangle : object2) {
                if (meteringRectangle.getMeteringWeight() == 0) continue;
                arrayList.add(meteringRectangle);
            }
            if (arrayList.size() == 0) {
                Log.w(TAG, "Only received metering rectangles with weight 0.");
                return Arrays.asList(ParameterUtils.CAMERA_AREA_DEFAULT);
            }
            int n2 = Math.min(n, arrayList.size());
            object2 = new ArrayList(n2);
            for (int i = 0; i < n2; ++i) {
                MeteringRectangle meteringRectangle;
                meteringRectangle = (MeteringRectangle)arrayList.get(i);
                object2.add(ParameterUtils.convertMeteringRectangleToLegacy((Rect)object, (MeteringRectangle)meteringRectangle, (ParameterUtils.ZoomData)zoomData).meteringArea);
            }
            if (n < arrayList.size()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("convertMeteringRegionsToLegacy - Too many requested ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" regions, ignoring all beyond the first ");
                ((StringBuilder)object).append(n);
                Log.w(TAG, ((StringBuilder)object).toString());
            }
            return object2;
        }
        if (n > 0) {
            return Arrays.asList(ParameterUtils.CAMERA_AREA_DEFAULT);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void convertRequestMetadata(LegacyRequest object) {
        int n;
        int n2;
        CameraCharacteristics cameraCharacteristics = ((LegacyRequest)object).characteristics;
        CaptureRequest captureRequest = ((LegacyRequest)object).captureRequest;
        Object object2 = ((LegacyRequest)object).previewSize;
        Camera.Parameters parameters = ((LegacyRequest)object).parameters;
        Object object3 = cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        object2 = ParameterUtils.convertScalerCropRegion((Rect)object3, captureRequest.get(CaptureRequest.SCALER_CROP_REGION), (Size)object2, parameters);
        if (parameters.isZoomSupported()) {
            parameters.setZoom(((ParameterUtils.ZoomData)object2).zoomIndex);
        }
        if ((n2 = ParamsUtils.getOrDefault(captureRequest, CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE, 1).intValue()) != 1 && n2 != 2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("convertRequestToMetadata - Ignoring unsupported colorCorrection.aberrationMode = ");
            ((StringBuilder)object).append(n2);
            Log.w(TAG, ((StringBuilder)object).toString());
        }
        object = (object = captureRequest.get(CaptureRequest.CONTROL_AE_ANTIBANDING_MODE)) != null ? LegacyRequestMapper.convertAeAntiBandingModeToLegacy((Integer)object) : ListUtils.listSelectFirstFrom(parameters.getSupportedAntibanding(), new String[]{"auto", "off", "50hz", "60hz"});
        if (object != null) {
            parameters.setAntibanding((String)object);
        }
        object = captureRequest.get(CaptureRequest.CONTROL_AE_REGIONS);
        if (captureRequest.get(CaptureRequest.CONTROL_AWB_REGIONS) != null) {
            Log.w(TAG, "convertRequestMetadata - control.awbRegions setting is not supported, ignoring value");
        }
        n2 = parameters.getMaxNumMeteringAreas();
        object = LegacyRequestMapper.convertMeteringRegionsToLegacy((Rect)object3, (ParameterUtils.ZoomData)object2, (MeteringRectangle[])object, n2, "AE");
        if (n2 > 0) {
            parameters.setMeteringAreas((List<Camera.Area>)object);
        }
        object = captureRequest.get(CaptureRequest.CONTROL_AF_REGIONS);
        n2 = parameters.getMaxNumFocusAreas();
        object = LegacyRequestMapper.convertMeteringRegionsToLegacy((Rect)object3, (ParameterUtils.ZoomData)object2, (MeteringRectangle[])object, n2, "AF");
        if (n2 > 0) {
            parameters.setFocusAreas((List<Camera.Area>)object);
        }
        if ((object = captureRequest.get(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE)) != null) {
            block37 : {
                object = LegacyRequestMapper.convertAeFpsRangeToLegacy((Range<Integer>)object);
                object2 = null;
                for (int[] arrn : parameters.getSupportedPreviewFpsRange()) {
                    n2 = arrn[0];
                    object3 = object;
                    n = (int)Math.floor((double)n2 / 1000.0);
                    n2 = (int)Math.ceil((double)arrn[1] / 1000.0);
                    if (object3[0] == n * 1000 && object3[1] == n2 * 1000) {
                        object3 = arrn;
                        break block37;
                    }
                    object = object3;
                }
                object3 = object2;
            }
            if (object3 != null) {
                parameters.setPreviewFpsRange((int)object3[0], (int)object3[1]);
            } else {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Unsupported FPS range set [");
                ((StringBuilder)object3).append((int)object[0]);
                ((StringBuilder)object3).append(",");
                ((StringBuilder)object3).append((int)object[1]);
                ((StringBuilder)object3).append("]");
                Log.w(TAG, ((StringBuilder)object3).toString());
            }
        }
        object = cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE);
        n2 = n = ParamsUtils.getOrDefault(captureRequest, CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, 0).intValue();
        if (!((Range)object).contains(n)) {
            Log.w(TAG, "convertRequestMetadata - control.aeExposureCompensation is out of range, ignoring value");
            n2 = 0;
        }
        parameters.setExposureCompensation(n2);
        object = LegacyRequestMapper.getIfSupported(captureRequest, CaptureRequest.CONTROL_AE_LOCK, false, parameters.isAutoExposureLockSupported(), false);
        if (object != null) {
            parameters.setAutoExposureLock((Boolean)object);
        }
        LegacyRequestMapper.mapAeAndFlashMode(captureRequest, parameters);
        n2 = ParamsUtils.getOrDefault(captureRequest, CaptureRequest.CONTROL_AF_MODE, 0);
        object = LegacyMetadataMapper.convertAfModeToLegacy(n2, parameters.getSupportedFocusModes());
        if (object != null) {
            parameters.setFocusMode((String)object);
        }
        object = CaptureRequest.CONTROL_AWB_MODE;
        boolean bl = parameters.getSupportedWhiteBalance() != null;
        if ((object = LegacyRequestMapper.getIfSupported(captureRequest, object, 1, bl, 1)) != null) {
            parameters.setWhiteBalance(LegacyRequestMapper.convertAwbModeToLegacy((Integer)object));
        }
        if ((object = LegacyRequestMapper.getIfSupported(captureRequest, CaptureRequest.CONTROL_AWB_LOCK, false, parameters.isAutoWhiteBalanceLockSupported(), false)) != null) {
            parameters.setAutoWhiteBalanceLock((Boolean)object);
        }
        bl = (n2 = LegacyRequestMapper.filterSupportedCaptureIntent(ParamsUtils.getOrDefault(captureRequest, CaptureRequest.CONTROL_CAPTURE_INTENT, 1))) == 3 || n2 == 4;
        parameters.setRecordingHint(bl);
        object = LegacyRequestMapper.getIfSupported(captureRequest, CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, 0, parameters.isVideoStabilizationSupported(), 0);
        if (object != null) {
            bl = (Integer)object == 1;
            parameters.setVideoStabilization(bl);
        }
        bl = ListUtils.listContains(parameters.getSupportedFocusModes(), "infinity");
        object = LegacyRequestMapper.getIfSupported(captureRequest, CaptureRequest.LENS_FOCUS_DISTANCE, Float.valueOf(0.0f), bl, Float.valueOf(0.0f));
        if (object == null || ((Float)object).floatValue() != 0.0f) {
            object = new StringBuilder();
            ((StringBuilder)object).append("convertRequestToMetadata - Ignoring android.lens.focusDistance ");
            ((StringBuilder)object).append(bl);
            ((StringBuilder)object).append(", only 0.0f is supported");
            Log.w(TAG, ((StringBuilder)object).toString());
        }
        if (parameters.getSupportedSceneModes() != null) {
            n2 = ParamsUtils.getOrDefault(captureRequest, CaptureRequest.CONTROL_MODE, 1);
            if (n2 != 1) {
                if (n2 != 2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Control mode ");
                    ((StringBuilder)object).append(n2);
                    ((StringBuilder)object).append(" is unsupported, defaulting to AUTO");
                    Log.w(TAG, ((StringBuilder)object).toString());
                    object = "auto";
                } else {
                    n2 = ParamsUtils.getOrDefault(captureRequest, CaptureRequest.CONTROL_SCENE_MODE, 0);
                    object = LegacyMetadataMapper.convertSceneModeToLegacy(n2);
                    if (object == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Skipping unknown requested scene mode: ");
                        ((StringBuilder)object).append(n2);
                        Log.w(TAG, ((StringBuilder)object).toString());
                        object = "auto";
                    }
                }
            } else {
                object = "auto";
            }
            parameters.setSceneMode((String)object);
        }
        if (parameters.getSupportedColorEffects() != null) {
            n2 = ParamsUtils.getOrDefault(captureRequest, CaptureRequest.CONTROL_EFFECT_MODE, 0);
            object = LegacyMetadataMapper.convertEffectModeToLegacy(n2);
            if (object != null) {
                parameters.setColorEffect((String)object);
            } else {
                parameters.setColorEffect("none");
                object = new StringBuilder();
                ((StringBuilder)object).append("Skipping unknown requested effect mode: ");
                ((StringBuilder)object).append(n2);
                Log.w(TAG, ((StringBuilder)object).toString());
            }
        }
        if ((n2 = ParamsUtils.getOrDefault(captureRequest, CaptureRequest.SENSOR_TEST_PATTERN_MODE, 0).intValue()) != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("convertRequestToMetadata - ignoring sensor.testPatternMode ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append("; only OFF is supported");
            Log.w(TAG, ((StringBuilder)object).toString());
        }
        if ((object = captureRequest.get(CaptureRequest.JPEG_GPS_LOCATION)) != null) {
            if (LegacyRequestMapper.checkForCompleteGpsData((Location)object)) {
                parameters.setGpsAltitude(((Location)object).getAltitude());
                parameters.setGpsLatitude(((Location)object).getLatitude());
                parameters.setGpsLongitude(((Location)object).getLongitude());
                parameters.setGpsProcessingMethod(((Location)object).getProvider().toUpperCase());
                parameters.setGpsTimestamp(((Location)object).getTime());
            } else {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Incomplete GPS parameters provided in location ");
                ((StringBuilder)object3).append(object);
                Log.w(TAG, ((StringBuilder)object3).toString());
            }
        } else {
            parameters.removeGpsData();
        }
        object = captureRequest.get(CaptureRequest.JPEG_ORIENTATION);
        object3 = CaptureRequest.JPEG_ORIENTATION;
        n2 = object == null ? 0 : (Integer)object;
        parameters.setRotation(ParamsUtils.getOrDefault(captureRequest, object3, n2));
        parameters.setJpegQuality(ParamsUtils.getOrDefault(captureRequest, CaptureRequest.JPEG_QUALITY, (byte)85) & 255);
        parameters.setJpegThumbnailQuality(ParamsUtils.getOrDefault(captureRequest, CaptureRequest.JPEG_THUMBNAIL_QUALITY, (byte)85) & 255);
        object3 = parameters.getSupportedJpegThumbnailSizes();
        if (object3 != null && object3.size() > 0) {
            object = captureRequest.get(CaptureRequest.JPEG_THUMBNAIL_SIZE);
            n2 = object != null && !ParameterUtils.containsSize((List<Camera.Size>)object3, ((Size)object).getWidth(), ((Size)object).getHeight()) ? 1 : 0;
            if (n2 != 0) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Invalid JPEG thumbnail size set ");
                ((StringBuilder)object3).append(object);
                ((StringBuilder)object3).append(", skipping thumbnail...");
                Log.w(TAG, ((StringBuilder)object3).toString());
            }
            if (object != null && n2 == 0) {
                parameters.setJpegThumbnailSize(((Size)object).getWidth(), ((Size)object).getHeight());
            } else {
                parameters.setJpegThumbnailSize(0, 0);
            }
        }
        if ((n2 = ParamsUtils.getOrDefault(captureRequest, CaptureRequest.NOISE_REDUCTION_MODE, 1).intValue()) != 1 && n2 != 2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("convertRequestToMetadata - Ignoring unsupported noiseReduction.mode = ");
            ((StringBuilder)object).append(n2);
            Log.w(TAG, ((StringBuilder)object).toString());
        }
    }

    static int filterSupportedCaptureIntent(int n) {
        block4 : {
            StringBuilder stringBuilder;
            switch (n) {
                default: {
                    break;
                }
                case 5: 
                case 6: {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported control.captureIntent value ");
                    stringBuilder.append(1);
                    stringBuilder.append("; default to PREVIEW");
                    Log.w(TAG, stringBuilder.toString());
                    break;
                }
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 4: {
                    break block4;
                }
            }
            n = 1;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown control.captureIntent value ");
            stringBuilder.append(1);
            stringBuilder.append("; default to PREVIEW");
            Log.w(TAG, stringBuilder.toString());
        }
        return n;
    }

    private static <T> T getIfSupported(CaptureRequest captureRequest, CaptureRequest.Key<T> key, T object, boolean bl, T t) {
        captureRequest = ParamsUtils.getOrDefault(captureRequest, key, object);
        if (!bl) {
            if (!Objects.equals(captureRequest, t)) {
                object = new StringBuilder();
                ((StringBuilder)object).append(key.getName());
                ((StringBuilder)object).append(" is not supported; ignoring requested value ");
                ((StringBuilder)object).append(captureRequest);
                Log.w(TAG, ((StringBuilder)object).toString());
            }
            return null;
        }
        return (T)captureRequest;
    }

    private static void mapAeAndFlashMode(CaptureRequest object, Camera.Parameters parameters) {
        int n = ParamsUtils.getOrDefault((CaptureRequest)object, CaptureRequest.FLASH_MODE, 0);
        int n2 = ParamsUtils.getOrDefault((CaptureRequest)object, CaptureRequest.CONTROL_AE_MODE, 1);
        List<String> list = parameters.getSupportedFlashModes();
        String string2 = null;
        if (ListUtils.listContains(list, "off")) {
            string2 = "off";
        }
        if (n2 == 1) {
            if (n == 2) {
                if (ListUtils.listContains(list, "torch")) {
                    object = "torch";
                } else {
                    Log.w(TAG, "mapAeAndFlashMode - Ignore flash.mode == TORCH;camera does not support it");
                    object = string2;
                }
            } else {
                object = string2;
                if (n == 1) {
                    if (ListUtils.listContains(list, "on")) {
                        object = "on";
                    } else {
                        Log.w(TAG, "mapAeAndFlashMode - Ignore flash.mode == SINGLE;camera does not support it");
                        object = string2;
                    }
                }
            }
        } else if (n2 == 3) {
            if (ListUtils.listContains(list, "on")) {
                object = "on";
            } else {
                Log.w(TAG, "mapAeAndFlashMode - Ignore control.aeMode == ON_ALWAYS_FLASH;camera does not support it");
                object = string2;
            }
        } else if (n2 == 2) {
            if (ListUtils.listContains(list, "auto")) {
                object = "auto";
            } else {
                Log.w(TAG, "mapAeAndFlashMode - Ignore control.aeMode == ON_AUTO_FLASH;camera does not support it");
                object = string2;
            }
        } else {
            object = string2;
            if (n2 == 4) {
                if (ListUtils.listContains(list, "red-eye")) {
                    object = "red-eye";
                } else {
                    Log.w(TAG, "mapAeAndFlashMode - Ignore control.aeMode == ON_AUTO_FLASH_REDEYE;camera does not support it");
                    object = string2;
                }
            }
        }
        if (object != null) {
            parameters.setFlashMode((String)object);
        }
    }
}

