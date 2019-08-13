/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.CaptureResultExtras;
import android.hardware.camera2.impl.PublicKey;
import android.hardware.camera2.impl.SyntheticKey;
import android.hardware.camera2.params.ColorSpaceTransform;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.params.LensShadingMap;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.OisSample;
import android.hardware.camera2.params.RggbChannelVector;
import android.hardware.camera2.params.TonemapCurve;
import android.hardware.camera2.utils.TypeReference;
import android.location.Location;
import android.util.Pair;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import java.util.List;

public class CaptureResult
extends CameraMetadata<Key<?>> {
    @PublicKey
    public static final Key<Boolean> BLACK_LEVEL_LOCK;
    @PublicKey
    public static final Key<Integer> COLOR_CORRECTION_ABERRATION_MODE;
    @PublicKey
    public static final Key<RggbChannelVector> COLOR_CORRECTION_GAINS;
    @PublicKey
    public static final Key<Integer> COLOR_CORRECTION_MODE;
    @PublicKey
    public static final Key<ColorSpaceTransform> COLOR_CORRECTION_TRANSFORM;
    @PublicKey
    public static final Key<Integer> CONTROL_AE_ANTIBANDING_MODE;
    @PublicKey
    public static final Key<Integer> CONTROL_AE_EXPOSURE_COMPENSATION;
    @PublicKey
    public static final Key<Boolean> CONTROL_AE_LOCK;
    @PublicKey
    public static final Key<Integer> CONTROL_AE_MODE;
    @PublicKey
    public static final Key<Integer> CONTROL_AE_PRECAPTURE_TRIGGER;
    @PublicKey
    public static final Key<MeteringRectangle[]> CONTROL_AE_REGIONS;
    @PublicKey
    public static final Key<Integer> CONTROL_AE_STATE;
    @PublicKey
    public static final Key<Range<Integer>> CONTROL_AE_TARGET_FPS_RANGE;
    @PublicKey
    public static final Key<Integer> CONTROL_AF_MODE;
    @PublicKey
    public static final Key<MeteringRectangle[]> CONTROL_AF_REGIONS;
    @PublicKey
    public static final Key<Integer> CONTROL_AF_SCENE_CHANGE;
    @PublicKey
    public static final Key<Integer> CONTROL_AF_STATE;
    @PublicKey
    public static final Key<Integer> CONTROL_AF_TRIGGER;
    @PublicKey
    public static final Key<Boolean> CONTROL_AWB_LOCK;
    @PublicKey
    public static final Key<Integer> CONTROL_AWB_MODE;
    @PublicKey
    public static final Key<MeteringRectangle[]> CONTROL_AWB_REGIONS;
    @PublicKey
    public static final Key<Integer> CONTROL_AWB_STATE;
    @PublicKey
    public static final Key<Integer> CONTROL_CAPTURE_INTENT;
    @PublicKey
    public static final Key<Integer> CONTROL_EFFECT_MODE;
    @PublicKey
    public static final Key<Boolean> CONTROL_ENABLE_ZSL;
    @PublicKey
    public static final Key<Integer> CONTROL_MODE;
    @PublicKey
    public static final Key<Integer> CONTROL_POST_RAW_SENSITIVITY_BOOST;
    @PublicKey
    public static final Key<Integer> CONTROL_SCENE_MODE;
    @PublicKey
    public static final Key<Integer> CONTROL_VIDEO_STABILIZATION_MODE;
    @PublicKey
    public static final Key<Integer> DISTORTION_CORRECTION_MODE;
    @PublicKey
    public static final Key<Integer> EDGE_MODE;
    @PublicKey
    public static final Key<Integer> FLASH_MODE;
    @PublicKey
    public static final Key<Integer> FLASH_STATE;
    @PublicKey
    public static final Key<Integer> HOT_PIXEL_MODE;
    public static final Key<double[]> JPEG_GPS_COORDINATES;
    @PublicKey
    @SyntheticKey
    public static final Key<Location> JPEG_GPS_LOCATION;
    public static final Key<String> JPEG_GPS_PROCESSING_METHOD;
    public static final Key<Long> JPEG_GPS_TIMESTAMP;
    @PublicKey
    public static final Key<Integer> JPEG_ORIENTATION;
    @PublicKey
    public static final Key<Byte> JPEG_QUALITY;
    @PublicKey
    public static final Key<Byte> JPEG_THUMBNAIL_QUALITY;
    @PublicKey
    public static final Key<Size> JPEG_THUMBNAIL_SIZE;
    public static final Key<Boolean> LED_TRANSMIT;
    @PublicKey
    public static final Key<Float> LENS_APERTURE;
    @PublicKey
    public static final Key<float[]> LENS_DISTORTION;
    @PublicKey
    public static final Key<Float> LENS_FILTER_DENSITY;
    @PublicKey
    public static final Key<Float> LENS_FOCAL_LENGTH;
    @PublicKey
    public static final Key<Float> LENS_FOCUS_DISTANCE;
    @PublicKey
    public static final Key<Pair<Float, Float>> LENS_FOCUS_RANGE;
    @PublicKey
    public static final Key<float[]> LENS_INTRINSIC_CALIBRATION;
    @PublicKey
    public static final Key<Integer> LENS_OPTICAL_STABILIZATION_MODE;
    @PublicKey
    public static final Key<float[]> LENS_POSE_ROTATION;
    @PublicKey
    public static final Key<float[]> LENS_POSE_TRANSLATION;
    @PublicKey
    @Deprecated
    public static final Key<float[]> LENS_RADIAL_DISTORTION;
    @PublicKey
    public static final Key<Integer> LENS_STATE;
    @PublicKey
    public static final Key<String> LOGICAL_MULTI_CAMERA_ACTIVE_PHYSICAL_ID;
    @PublicKey
    public static final Key<Integer> NOISE_REDUCTION_MODE;
    @Deprecated
    public static final Key<Boolean> QUIRKS_PARTIAL_RESULT;
    @PublicKey
    public static final Key<Float> REPROCESS_EFFECTIVE_EXPOSURE_FACTOR;
    @Deprecated
    public static final Key<Integer> REQUEST_FRAME_COUNT;
    public static final Key<Integer> REQUEST_ID;
    @PublicKey
    public static final Key<Byte> REQUEST_PIPELINE_DEPTH;
    @PublicKey
    public static final Key<Rect> SCALER_CROP_REGION;
    @PublicKey
    public static final Key<float[]> SENSOR_DYNAMIC_BLACK_LEVEL;
    @PublicKey
    public static final Key<Integer> SENSOR_DYNAMIC_WHITE_LEVEL;
    @PublicKey
    public static final Key<Long> SENSOR_EXPOSURE_TIME;
    @PublicKey
    public static final Key<Long> SENSOR_FRAME_DURATION;
    @PublicKey
    public static final Key<Float> SENSOR_GREEN_SPLIT;
    @PublicKey
    public static final Key<Rational[]> SENSOR_NEUTRAL_COLOR_POINT;
    @PublicKey
    public static final Key<Pair<Double, Double>[]> SENSOR_NOISE_PROFILE;
    @PublicKey
    public static final Key<Long> SENSOR_ROLLING_SHUTTER_SKEW;
    @PublicKey
    public static final Key<Integer> SENSOR_SENSITIVITY;
    @PublicKey
    public static final Key<int[]> SENSOR_TEST_PATTERN_DATA;
    @PublicKey
    public static final Key<Integer> SENSOR_TEST_PATTERN_MODE;
    @PublicKey
    public static final Key<Long> SENSOR_TIMESTAMP;
    @PublicKey
    public static final Key<Integer> SHADING_MODE;
    @PublicKey
    @SyntheticKey
    public static final Key<Face[]> STATISTICS_FACES;
    @PublicKey
    public static final Key<Integer> STATISTICS_FACE_DETECT_MODE;
    public static final Key<int[]> STATISTICS_FACE_IDS;
    public static final Key<int[]> STATISTICS_FACE_LANDMARKS;
    public static final Key<Rect[]> STATISTICS_FACE_RECTANGLES;
    public static final Key<byte[]> STATISTICS_FACE_SCORES;
    @PublicKey
    public static final Key<Point[]> STATISTICS_HOT_PIXEL_MAP;
    @PublicKey
    public static final Key<Boolean> STATISTICS_HOT_PIXEL_MAP_MODE;
    @PublicKey
    public static final Key<LensShadingMap> STATISTICS_LENS_SHADING_CORRECTION_MAP;
    public static final Key<float[]> STATISTICS_LENS_SHADING_MAP;
    @PublicKey
    public static final Key<Integer> STATISTICS_LENS_SHADING_MAP_MODE;
    @PublicKey
    public static final Key<Integer> STATISTICS_OIS_DATA_MODE;
    @PublicKey
    @SyntheticKey
    public static final Key<OisSample[]> STATISTICS_OIS_SAMPLES;
    public static final Key<long[]> STATISTICS_OIS_TIMESTAMPS;
    public static final Key<float[]> STATISTICS_OIS_X_SHIFTS;
    public static final Key<float[]> STATISTICS_OIS_Y_SHIFTS;
    @Deprecated
    public static final Key<float[]> STATISTICS_PREDICTED_COLOR_GAINS;
    @Deprecated
    public static final Key<Rational[]> STATISTICS_PREDICTED_COLOR_TRANSFORM;
    @PublicKey
    public static final Key<Integer> STATISTICS_SCENE_FLICKER;
    public static final Key<Long> SYNC_FRAME_NUMBER;
    private static final String TAG = "CaptureResult";
    @PublicKey
    @SyntheticKey
    public static final Key<TonemapCurve> TONEMAP_CURVE;
    public static final Key<float[]> TONEMAP_CURVE_BLUE;
    public static final Key<float[]> TONEMAP_CURVE_GREEN;
    public static final Key<float[]> TONEMAP_CURVE_RED;
    @PublicKey
    public static final Key<Float> TONEMAP_GAMMA;
    @PublicKey
    public static final Key<Integer> TONEMAP_MODE;
    @PublicKey
    public static final Key<Integer> TONEMAP_PRESET_CURVE;
    private static final boolean VERBOSE = false;
    private final long mFrameNumber;
    private final CaptureRequest mRequest;
    @UnsupportedAppUsage
    private final CameraMetadataNative mResults;
    private final int mSequenceId;

    static {
        COLOR_CORRECTION_MODE = new Key<Integer>("android.colorCorrection.mode", Integer.TYPE);
        COLOR_CORRECTION_TRANSFORM = new Key<ColorSpaceTransform>("android.colorCorrection.transform", ColorSpaceTransform.class);
        COLOR_CORRECTION_GAINS = new Key<RggbChannelVector>("android.colorCorrection.gains", RggbChannelVector.class);
        COLOR_CORRECTION_ABERRATION_MODE = new Key<Integer>("android.colorCorrection.aberrationMode", Integer.TYPE);
        CONTROL_AE_ANTIBANDING_MODE = new Key<Integer>("android.control.aeAntibandingMode", Integer.TYPE);
        CONTROL_AE_EXPOSURE_COMPENSATION = new Key<Integer>("android.control.aeExposureCompensation", Integer.TYPE);
        CONTROL_AE_LOCK = new Key<Boolean>("android.control.aeLock", Boolean.TYPE);
        CONTROL_AE_MODE = new Key<Integer>("android.control.aeMode", Integer.TYPE);
        CONTROL_AE_REGIONS = new Key<MeteringRectangle[]>("android.control.aeRegions", MeteringRectangle[].class);
        CONTROL_AE_TARGET_FPS_RANGE = new Key<Range<Integer>>("android.control.aeTargetFpsRange", new TypeReference<Range<Integer>>(){});
        CONTROL_AE_PRECAPTURE_TRIGGER = new Key<Integer>("android.control.aePrecaptureTrigger", Integer.TYPE);
        CONTROL_AE_STATE = new Key<Integer>("android.control.aeState", Integer.TYPE);
        CONTROL_AF_MODE = new Key<Integer>("android.control.afMode", Integer.TYPE);
        CONTROL_AF_REGIONS = new Key<MeteringRectangle[]>("android.control.afRegions", MeteringRectangle[].class);
        CONTROL_AF_TRIGGER = new Key<Integer>("android.control.afTrigger", Integer.TYPE);
        CONTROL_AF_STATE = new Key<Integer>("android.control.afState", Integer.TYPE);
        CONTROL_AWB_LOCK = new Key<Boolean>("android.control.awbLock", Boolean.TYPE);
        CONTROL_AWB_MODE = new Key<Integer>("android.control.awbMode", Integer.TYPE);
        CONTROL_AWB_REGIONS = new Key<MeteringRectangle[]>("android.control.awbRegions", MeteringRectangle[].class);
        CONTROL_CAPTURE_INTENT = new Key<Integer>("android.control.captureIntent", Integer.TYPE);
        CONTROL_AWB_STATE = new Key<Integer>("android.control.awbState", Integer.TYPE);
        CONTROL_EFFECT_MODE = new Key<Integer>("android.control.effectMode", Integer.TYPE);
        CONTROL_MODE = new Key<Integer>("android.control.mode", Integer.TYPE);
        CONTROL_SCENE_MODE = new Key<Integer>("android.control.sceneMode", Integer.TYPE);
        CONTROL_VIDEO_STABILIZATION_MODE = new Key<Integer>("android.control.videoStabilizationMode", Integer.TYPE);
        CONTROL_POST_RAW_SENSITIVITY_BOOST = new Key<Integer>("android.control.postRawSensitivityBoost", Integer.TYPE);
        CONTROL_ENABLE_ZSL = new Key<Boolean>("android.control.enableZsl", Boolean.TYPE);
        CONTROL_AF_SCENE_CHANGE = new Key<Integer>("android.control.afSceneChange", Integer.TYPE);
        EDGE_MODE = new Key<Integer>("android.edge.mode", Integer.TYPE);
        FLASH_MODE = new Key<Integer>("android.flash.mode", Integer.TYPE);
        FLASH_STATE = new Key<Integer>("android.flash.state", Integer.TYPE);
        HOT_PIXEL_MODE = new Key<Integer>("android.hotPixel.mode", Integer.TYPE);
        JPEG_GPS_LOCATION = new Key<Location>("android.jpeg.gpsLocation", Location.class);
        JPEG_GPS_COORDINATES = new Key<double[]>("android.jpeg.gpsCoordinates", double[].class);
        JPEG_GPS_PROCESSING_METHOD = new Key<String>("android.jpeg.gpsProcessingMethod", String.class);
        JPEG_GPS_TIMESTAMP = new Key<Long>("android.jpeg.gpsTimestamp", Long.TYPE);
        JPEG_ORIENTATION = new Key<Integer>("android.jpeg.orientation", Integer.TYPE);
        JPEG_QUALITY = new Key<Byte>("android.jpeg.quality", Byte.TYPE);
        JPEG_THUMBNAIL_QUALITY = new Key<Byte>("android.jpeg.thumbnailQuality", Byte.TYPE);
        JPEG_THUMBNAIL_SIZE = new Key<Size>("android.jpeg.thumbnailSize", Size.class);
        LENS_APERTURE = new Key<Float>("android.lens.aperture", Float.TYPE);
        LENS_FILTER_DENSITY = new Key<Float>("android.lens.filterDensity", Float.TYPE);
        LENS_FOCAL_LENGTH = new Key<Float>("android.lens.focalLength", Float.TYPE);
        LENS_FOCUS_DISTANCE = new Key<Float>("android.lens.focusDistance", Float.TYPE);
        LENS_FOCUS_RANGE = new Key<Pair<Float, Float>>("android.lens.focusRange", new TypeReference<Pair<Float, Float>>(){});
        LENS_OPTICAL_STABILIZATION_MODE = new Key<Integer>("android.lens.opticalStabilizationMode", Integer.TYPE);
        LENS_STATE = new Key<Integer>("android.lens.state", Integer.TYPE);
        LENS_POSE_ROTATION = new Key<float[]>("android.lens.poseRotation", float[].class);
        LENS_POSE_TRANSLATION = new Key<float[]>("android.lens.poseTranslation", float[].class);
        LENS_INTRINSIC_CALIBRATION = new Key<float[]>("android.lens.intrinsicCalibration", float[].class);
        LENS_RADIAL_DISTORTION = new Key<float[]>("android.lens.radialDistortion", float[].class);
        LENS_DISTORTION = new Key<float[]>("android.lens.distortion", float[].class);
        NOISE_REDUCTION_MODE = new Key<Integer>("android.noiseReduction.mode", Integer.TYPE);
        QUIRKS_PARTIAL_RESULT = new Key<Boolean>("android.quirks.partialResult", Boolean.TYPE);
        REQUEST_FRAME_COUNT = new Key<Integer>("android.request.frameCount", Integer.TYPE);
        REQUEST_ID = new Key<Integer>("android.request.id", Integer.TYPE);
        REQUEST_PIPELINE_DEPTH = new Key<Byte>("android.request.pipelineDepth", Byte.TYPE);
        SCALER_CROP_REGION = new Key<Rect>("android.scaler.cropRegion", Rect.class);
        SENSOR_EXPOSURE_TIME = new Key<Long>("android.sensor.exposureTime", Long.TYPE);
        SENSOR_FRAME_DURATION = new Key<Long>("android.sensor.frameDuration", Long.TYPE);
        SENSOR_SENSITIVITY = new Key<Integer>("android.sensor.sensitivity", Integer.TYPE);
        SENSOR_TIMESTAMP = new Key<Long>("android.sensor.timestamp", Long.TYPE);
        SENSOR_NEUTRAL_COLOR_POINT = new Key<Rational[]>("android.sensor.neutralColorPoint", Rational[].class);
        SENSOR_NOISE_PROFILE = new Key<Pair<Double, Double>[]>("android.sensor.noiseProfile", new TypeReference<Pair<Double, Double>[]>(){});
        SENSOR_GREEN_SPLIT = new Key<Float>("android.sensor.greenSplit", Float.TYPE);
        SENSOR_TEST_PATTERN_DATA = new Key<int[]>("android.sensor.testPatternData", int[].class);
        SENSOR_TEST_PATTERN_MODE = new Key<Integer>("android.sensor.testPatternMode", Integer.TYPE);
        SENSOR_ROLLING_SHUTTER_SKEW = new Key<Long>("android.sensor.rollingShutterSkew", Long.TYPE);
        SENSOR_DYNAMIC_BLACK_LEVEL = new Key<float[]>("android.sensor.dynamicBlackLevel", float[].class);
        SENSOR_DYNAMIC_WHITE_LEVEL = new Key<Integer>("android.sensor.dynamicWhiteLevel", Integer.TYPE);
        SHADING_MODE = new Key<Integer>("android.shading.mode", Integer.TYPE);
        STATISTICS_FACE_DETECT_MODE = new Key<Integer>("android.statistics.faceDetectMode", Integer.TYPE);
        STATISTICS_FACE_IDS = new Key<int[]>("android.statistics.faceIds", int[].class);
        STATISTICS_FACE_LANDMARKS = new Key<int[]>("android.statistics.faceLandmarks", int[].class);
        STATISTICS_FACE_RECTANGLES = new Key<Rect[]>("android.statistics.faceRectangles", Rect[].class);
        STATISTICS_FACE_SCORES = new Key<byte[]>("android.statistics.faceScores", byte[].class);
        STATISTICS_FACES = new Key<Face[]>("android.statistics.faces", Face[].class);
        STATISTICS_LENS_SHADING_CORRECTION_MAP = new Key<LensShadingMap>("android.statistics.lensShadingCorrectionMap", LensShadingMap.class);
        STATISTICS_LENS_SHADING_MAP = new Key<float[]>("android.statistics.lensShadingMap", float[].class);
        STATISTICS_PREDICTED_COLOR_GAINS = new Key<float[]>("android.statistics.predictedColorGains", float[].class);
        STATISTICS_PREDICTED_COLOR_TRANSFORM = new Key<Rational[]>("android.statistics.predictedColorTransform", Rational[].class);
        STATISTICS_SCENE_FLICKER = new Key<Integer>("android.statistics.sceneFlicker", Integer.TYPE);
        STATISTICS_HOT_PIXEL_MAP_MODE = new Key<Boolean>("android.statistics.hotPixelMapMode", Boolean.TYPE);
        STATISTICS_HOT_PIXEL_MAP = new Key<Point[]>("android.statistics.hotPixelMap", Point[].class);
        STATISTICS_LENS_SHADING_MAP_MODE = new Key<Integer>("android.statistics.lensShadingMapMode", Integer.TYPE);
        STATISTICS_OIS_DATA_MODE = new Key<Integer>("android.statistics.oisDataMode", Integer.TYPE);
        STATISTICS_OIS_TIMESTAMPS = new Key<long[]>("android.statistics.oisTimestamps", long[].class);
        STATISTICS_OIS_X_SHIFTS = new Key<float[]>("android.statistics.oisXShifts", float[].class);
        STATISTICS_OIS_Y_SHIFTS = new Key<float[]>("android.statistics.oisYShifts", float[].class);
        STATISTICS_OIS_SAMPLES = new Key<OisSample[]>("android.statistics.oisSamples", OisSample[].class);
        TONEMAP_CURVE_BLUE = new Key<float[]>("android.tonemap.curveBlue", float[].class);
        TONEMAP_CURVE_GREEN = new Key<float[]>("android.tonemap.curveGreen", float[].class);
        TONEMAP_CURVE_RED = new Key<float[]>("android.tonemap.curveRed", float[].class);
        TONEMAP_CURVE = new Key<TonemapCurve>("android.tonemap.curve", TonemapCurve.class);
        TONEMAP_MODE = new Key<Integer>("android.tonemap.mode", Integer.TYPE);
        TONEMAP_GAMMA = new Key<Float>("android.tonemap.gamma", Float.TYPE);
        TONEMAP_PRESET_CURVE = new Key<Integer>("android.tonemap.presetCurve", Integer.TYPE);
        LED_TRANSMIT = new Key<Boolean>("android.led.transmit", Boolean.TYPE);
        BLACK_LEVEL_LOCK = new Key<Boolean>("android.blackLevel.lock", Boolean.TYPE);
        SYNC_FRAME_NUMBER = new Key<Long>("android.sync.frameNumber", Long.TYPE);
        REPROCESS_EFFECTIVE_EXPOSURE_FACTOR = new Key<Float>("android.reprocess.effectiveExposureFactor", Float.TYPE);
        LOGICAL_MULTI_CAMERA_ACTIVE_PHYSICAL_ID = new Key<String>("android.logicalMultiCamera.activePhysicalId", String.class);
        DISTORTION_CORRECTION_MODE = new Key<Integer>("android.distortionCorrection.mode", Integer.TYPE);
    }

    public CaptureResult(CameraMetadataNative cameraMetadataNative, int n) {
        if (cameraMetadataNative != null) {
            this.mResults = CameraMetadataNative.move(cameraMetadataNative);
            if (!this.mResults.isEmpty()) {
                this.setNativeInstance(this.mResults);
                this.mRequest = null;
                this.mSequenceId = n;
                this.mFrameNumber = -1L;
                return;
            }
            throw new AssertionError((Object)"Results must not be empty");
        }
        throw new IllegalArgumentException("results was null");
    }

    public CaptureResult(CameraMetadataNative cameraMetadataNative, CaptureRequest captureRequest, CaptureResultExtras captureResultExtras) {
        if (cameraMetadataNative != null) {
            if (captureRequest != null) {
                if (captureResultExtras != null) {
                    this.mResults = CameraMetadataNative.move(cameraMetadataNative);
                    if (!this.mResults.isEmpty()) {
                        this.setNativeInstance(this.mResults);
                        this.mRequest = captureRequest;
                        this.mSequenceId = captureResultExtras.getRequestId();
                        this.mFrameNumber = captureResultExtras.getFrameNumber();
                        return;
                    }
                    throw new AssertionError((Object)"Results must not be empty");
                }
                throw new IllegalArgumentException("extras was null");
            }
            throw new IllegalArgumentException("parent was null");
        }
        throw new IllegalArgumentException("results was null");
    }

    public void dumpToLog() {
        this.mResults.dumpToLog();
    }

    public <T> T get(Key<T> key) {
        return this.mResults.get(key);
    }

    public long getFrameNumber() {
        return this.mFrameNumber;
    }

    @Override
    protected Class<Key<?>> getKeyClass() {
        return Key.class;
    }

    @Override
    public List<Key<?>> getKeys() {
        return super.getKeys();
    }

    public CameraMetadataNative getNativeCopy() {
        return new CameraMetadataNative(this.mResults);
    }

    @Override
    protected <T> T getProtected(Key<?> key) {
        return (T)this.mResults.get(key);
    }

    public CaptureRequest getRequest() {
        return this.mRequest;
    }

    public int getSequenceId() {
        return this.mSequenceId;
    }

    public static final class Key<T> {
        private final CameraMetadataNative.Key<T> mKey;

        Key(CameraMetadataNative.Key<?> key) {
            this.mKey = key;
        }

        @UnsupportedAppUsage
        public Key(String string2, TypeReference<T> typeReference) {
            this.mKey = new CameraMetadataNative.Key<T>(string2, typeReference);
        }

        public Key(String string2, Class<T> class_) {
            this.mKey = new CameraMetadataNative.Key<T>(string2, class_);
        }

        @UnsupportedAppUsage
        public Key(String string2, Class<T> class_, long l) {
            this.mKey = new CameraMetadataNative.Key<T>(string2, class_, l);
        }

        public Key(String string2, String string3, Class<T> class_) {
            this.mKey = new CameraMetadataNative.Key<T>(string2, string3, class_);
        }

        public final boolean equals(Object object) {
            boolean bl = object instanceof Key && ((Key)object).mKey.equals(this.mKey);
            return bl;
        }

        public String getName() {
            return this.mKey.getName();
        }

        @UnsupportedAppUsage
        public CameraMetadataNative.Key<T> getNativeKey() {
            return this.mKey;
        }

        public long getVendorId() {
            return this.mKey.getVendorId();
        }

        public final int hashCode() {
            return this.mKey.hashCode();
        }

        public String toString() {
            return String.format("CaptureResult.Key(%s)", this.mKey.getName());
        }
    }

}

