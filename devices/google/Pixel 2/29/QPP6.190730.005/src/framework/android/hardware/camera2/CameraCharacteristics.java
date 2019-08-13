/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Rect;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.PublicKey;
import android.hardware.camera2.impl.SyntheticKey;
import android.hardware.camera2.params.BlackLevelPattern;
import android.hardware.camera2.params.ColorSpaceTransform;
import android.hardware.camera2.params.HighSpeedVideoConfiguration;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.hardware.camera2.params.RecommendedStreamConfiguration;
import android.hardware.camera2.params.RecommendedStreamConfigurationMap;
import android.hardware.camera2.params.ReprocessFormatsMap;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.params.StreamConfigurationDuration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.utils.ArrayUtils;
import android.hardware.camera2.utils.TypeReference;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import android.util.SizeF;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class CameraCharacteristics
extends CameraMetadata<Key<?>> {
    @PublicKey
    public static final Key<int[]> COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES = new Key<int[]>("android.colorCorrection.availableAberrationModes", int[].class);
    @PublicKey
    public static final Key<int[]> CONTROL_AE_AVAILABLE_ANTIBANDING_MODES = new Key<int[]>("android.control.aeAvailableAntibandingModes", int[].class);
    @PublicKey
    public static final Key<int[]> CONTROL_AE_AVAILABLE_MODES = new Key<int[]>("android.control.aeAvailableModes", int[].class);
    @PublicKey
    public static final Key<Range<Integer>[]> CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES = new Key<Range<Integer>[]>("android.control.aeAvailableTargetFpsRanges", new TypeReference<Range<Integer>[]>(){});
    @PublicKey
    public static final Key<Range<Integer>> CONTROL_AE_COMPENSATION_RANGE = new Key<Range<Integer>>("android.control.aeCompensationRange", new TypeReference<Range<Integer>>(){});
    @PublicKey
    public static final Key<Rational> CONTROL_AE_COMPENSATION_STEP = new Key<Rational>("android.control.aeCompensationStep", Rational.class);
    @PublicKey
    public static final Key<Boolean> CONTROL_AE_LOCK_AVAILABLE;
    @PublicKey
    public static final Key<int[]> CONTROL_AF_AVAILABLE_MODES;
    @PublicKey
    public static final Key<int[]> CONTROL_AVAILABLE_EFFECTS;
    public static final Key<HighSpeedVideoConfiguration[]> CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS;
    @PublicKey
    public static final Key<int[]> CONTROL_AVAILABLE_MODES;
    @PublicKey
    public static final Key<int[]> CONTROL_AVAILABLE_SCENE_MODES;
    @PublicKey
    public static final Key<int[]> CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES;
    @PublicKey
    public static final Key<int[]> CONTROL_AWB_AVAILABLE_MODES;
    @PublicKey
    public static final Key<Boolean> CONTROL_AWB_LOCK_AVAILABLE;
    public static final Key<int[]> CONTROL_MAX_REGIONS;
    @PublicKey
    @SyntheticKey
    public static final Key<Integer> CONTROL_MAX_REGIONS_AE;
    @PublicKey
    @SyntheticKey
    public static final Key<Integer> CONTROL_MAX_REGIONS_AF;
    @PublicKey
    @SyntheticKey
    public static final Key<Integer> CONTROL_MAX_REGIONS_AWB;
    @PublicKey
    public static final Key<Range<Integer>> CONTROL_POST_RAW_SENSITIVITY_BOOST_RANGE;
    public static final Key<StreamConfigurationDuration[]> DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS;
    public static final Key<StreamConfiguration[]> DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS;
    public static final Key<StreamConfigurationDuration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS;
    public static final Key<StreamConfiguration[]> DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS;
    public static final Key<RecommendedStreamConfiguration[]> DEPTH_AVAILABLE_RECOMMENDED_DEPTH_STREAM_CONFIGURATIONS;
    @PublicKey
    public static final Key<Boolean> DEPTH_DEPTH_IS_EXCLUSIVE;
    @PublicKey
    public static final Key<int[]> DISTORTION_CORRECTION_AVAILABLE_MODES;
    @PublicKey
    public static final Key<int[]> EDGE_AVAILABLE_EDGE_MODES;
    @PublicKey
    public static final Key<Boolean> FLASH_INFO_AVAILABLE;
    public static final Key<StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS;
    public static final Key<StreamConfigurationDuration[]> HEIC_AVAILABLE_HEIC_STALL_DURATIONS;
    public static final Key<StreamConfiguration[]> HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS;
    @PublicKey
    public static final Key<int[]> HOT_PIXEL_AVAILABLE_HOT_PIXEL_MODES;
    @PublicKey
    public static final Key<Integer> INFO_SUPPORTED_HARDWARE_LEVEL;
    @PublicKey
    public static final Key<String> INFO_VERSION;
    @PublicKey
    public static final Key<Size[]> JPEG_AVAILABLE_THUMBNAIL_SIZES;
    public static final Key<int[]> LED_AVAILABLE_LEDS;
    @PublicKey
    public static final Key<float[]> LENS_DISTORTION;
    @PublicKey
    public static final Key<Integer> LENS_FACING;
    @PublicKey
    public static final Key<float[]> LENS_INFO_AVAILABLE_APERTURES;
    @PublicKey
    public static final Key<float[]> LENS_INFO_AVAILABLE_FILTER_DENSITIES;
    @PublicKey
    public static final Key<float[]> LENS_INFO_AVAILABLE_FOCAL_LENGTHS;
    @PublicKey
    public static final Key<int[]> LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION;
    @PublicKey
    public static final Key<Integer> LENS_INFO_FOCUS_DISTANCE_CALIBRATION;
    @PublicKey
    public static final Key<Float> LENS_INFO_HYPERFOCAL_DISTANCE;
    @PublicKey
    public static final Key<Float> LENS_INFO_MINIMUM_FOCUS_DISTANCE;
    public static final Key<Size> LENS_INFO_SHADING_MAP_SIZE;
    @PublicKey
    public static final Key<float[]> LENS_INTRINSIC_CALIBRATION;
    @PublicKey
    public static final Key<Integer> LENS_POSE_REFERENCE;
    @PublicKey
    public static final Key<float[]> LENS_POSE_ROTATION;
    @PublicKey
    public static final Key<float[]> LENS_POSE_TRANSLATION;
    @PublicKey
    @Deprecated
    public static final Key<float[]> LENS_RADIAL_DISTORTION;
    public static final Key<byte[]> LOGICAL_MULTI_CAMERA_PHYSICAL_IDS;
    @PublicKey
    public static final Key<Integer> LOGICAL_MULTI_CAMERA_SENSOR_SYNC_TYPE;
    @PublicKey
    public static final Key<int[]> NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES;
    @Deprecated
    public static final Key<Byte> QUIRKS_USE_PARTIAL_RESULT;
    @PublicKey
    public static final Key<Integer> REPROCESS_MAX_CAPTURE_STALL;
    @PublicKey
    public static final Key<int[]> REQUEST_AVAILABLE_CAPABILITIES;
    public static final Key<int[]> REQUEST_AVAILABLE_CHARACTERISTICS_KEYS;
    public static final Key<int[]> REQUEST_AVAILABLE_PHYSICAL_CAMERA_REQUEST_KEYS;
    public static final Key<int[]> REQUEST_AVAILABLE_REQUEST_KEYS;
    public static final Key<int[]> REQUEST_AVAILABLE_RESULT_KEYS;
    public static final Key<int[]> REQUEST_AVAILABLE_SESSION_KEYS;
    public static final Key<int[]> REQUEST_CHARACTERISTIC_KEYS_NEEDING_PERMISSION;
    @PublicKey
    public static final Key<Integer> REQUEST_MAX_NUM_INPUT_STREAMS;
    @PublicKey
    @SyntheticKey
    public static final Key<Integer> REQUEST_MAX_NUM_OUTPUT_PROC;
    @PublicKey
    @SyntheticKey
    public static final Key<Integer> REQUEST_MAX_NUM_OUTPUT_PROC_STALLING;
    @PublicKey
    @SyntheticKey
    public static final Key<Integer> REQUEST_MAX_NUM_OUTPUT_RAW;
    public static final Key<int[]> REQUEST_MAX_NUM_OUTPUT_STREAMS;
    @PublicKey
    public static final Key<Integer> REQUEST_PARTIAL_RESULT_COUNT;
    @PublicKey
    public static final Key<Byte> REQUEST_PIPELINE_MAX_DEPTH;
    @Deprecated
    public static final Key<int[]> SCALER_AVAILABLE_FORMATS;
    public static final Key<ReprocessFormatsMap> SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP;
    @Deprecated
    public static final Key<long[]> SCALER_AVAILABLE_JPEG_MIN_DURATIONS;
    @Deprecated
    public static final Key<Size[]> SCALER_AVAILABLE_JPEG_SIZES;
    @PublicKey
    public static final Key<Float> SCALER_AVAILABLE_MAX_DIGITAL_ZOOM;
    public static final Key<StreamConfigurationDuration[]> SCALER_AVAILABLE_MIN_FRAME_DURATIONS;
    @Deprecated
    public static final Key<long[]> SCALER_AVAILABLE_PROCESSED_MIN_DURATIONS;
    @Deprecated
    public static final Key<Size[]> SCALER_AVAILABLE_PROCESSED_SIZES;
    public static final Key<ReprocessFormatsMap> SCALER_AVAILABLE_RECOMMENDED_INPUT_OUTPUT_FORMATS_MAP;
    public static final Key<RecommendedStreamConfiguration[]> SCALER_AVAILABLE_RECOMMENDED_STREAM_CONFIGURATIONS;
    public static final Key<StreamConfigurationDuration[]> SCALER_AVAILABLE_STALL_DURATIONS;
    public static final Key<StreamConfiguration[]> SCALER_AVAILABLE_STREAM_CONFIGURATIONS;
    @PublicKey
    public static final Key<Integer> SCALER_CROPPING_TYPE;
    @PublicKey
    @SyntheticKey
    public static final Key<MandatoryStreamCombination[]> SCALER_MANDATORY_STREAM_COMBINATIONS;
    @PublicKey
    @SyntheticKey
    public static final Key<StreamConfigurationMap> SCALER_STREAM_CONFIGURATION_MAP;
    @PublicKey
    public static final Key<int[]> SENSOR_AVAILABLE_TEST_PATTERN_MODES;
    @PublicKey
    public static final Key<BlackLevelPattern> SENSOR_BLACK_LEVEL_PATTERN;
    @PublicKey
    public static final Key<ColorSpaceTransform> SENSOR_CALIBRATION_TRANSFORM1;
    @PublicKey
    public static final Key<ColorSpaceTransform> SENSOR_CALIBRATION_TRANSFORM2;
    @PublicKey
    public static final Key<ColorSpaceTransform> SENSOR_COLOR_TRANSFORM1;
    @PublicKey
    public static final Key<ColorSpaceTransform> SENSOR_COLOR_TRANSFORM2;
    @PublicKey
    public static final Key<ColorSpaceTransform> SENSOR_FORWARD_MATRIX1;
    @PublicKey
    public static final Key<ColorSpaceTransform> SENSOR_FORWARD_MATRIX2;
    @PublicKey
    public static final Key<Rect> SENSOR_INFO_ACTIVE_ARRAY_SIZE;
    @PublicKey
    public static final Key<Integer> SENSOR_INFO_COLOR_FILTER_ARRANGEMENT;
    @PublicKey
    public static final Key<Range<Long>> SENSOR_INFO_EXPOSURE_TIME_RANGE;
    @PublicKey
    public static final Key<Boolean> SENSOR_INFO_LENS_SHADING_APPLIED;
    @PublicKey
    public static final Key<Long> SENSOR_INFO_MAX_FRAME_DURATION;
    @PublicKey
    public static final Key<SizeF> SENSOR_INFO_PHYSICAL_SIZE;
    @PublicKey
    public static final Key<Size> SENSOR_INFO_PIXEL_ARRAY_SIZE;
    @PublicKey
    public static final Key<Rect> SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE;
    @PublicKey
    public static final Key<Range<Integer>> SENSOR_INFO_SENSITIVITY_RANGE;
    @PublicKey
    public static final Key<Integer> SENSOR_INFO_TIMESTAMP_SOURCE;
    @PublicKey
    public static final Key<Integer> SENSOR_INFO_WHITE_LEVEL;
    @PublicKey
    public static final Key<Integer> SENSOR_MAX_ANALOG_SENSITIVITY;
    @PublicKey
    public static final Key<Rect[]> SENSOR_OPTICAL_BLACK_REGIONS;
    @PublicKey
    public static final Key<Integer> SENSOR_ORIENTATION;
    @PublicKey
    public static final Key<Integer> SENSOR_REFERENCE_ILLUMINANT1;
    @PublicKey
    public static final Key<Byte> SENSOR_REFERENCE_ILLUMINANT2;
    @PublicKey
    public static final Key<int[]> SHADING_AVAILABLE_MODES;
    @PublicKey
    public static final Key<int[]> STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES;
    @PublicKey
    public static final Key<boolean[]> STATISTICS_INFO_AVAILABLE_HOT_PIXEL_MAP_MODES;
    @PublicKey
    public static final Key<int[]> STATISTICS_INFO_AVAILABLE_LENS_SHADING_MAP_MODES;
    @PublicKey
    public static final Key<int[]> STATISTICS_INFO_AVAILABLE_OIS_DATA_MODES;
    @PublicKey
    public static final Key<Integer> STATISTICS_INFO_MAX_FACE_COUNT;
    @PublicKey
    public static final Key<Integer> SYNC_MAX_LATENCY;
    @PublicKey
    public static final Key<int[]> TONEMAP_AVAILABLE_TONE_MAP_MODES;
    @PublicKey
    public static final Key<Integer> TONEMAP_MAX_CURVE_POINTS;
    private List<CaptureRequest.Key<?>> mAvailablePhysicalRequestKeys;
    private List<CaptureRequest.Key<?>> mAvailableRequestKeys;
    private List<CaptureResult.Key<?>> mAvailableResultKeys;
    private List<CaptureRequest.Key<?>> mAvailableSessionKeys;
    private List<Key<?>> mKeys;
    private List<Key<?>> mKeysNeedingPermission;
    @UnsupportedAppUsage
    private final CameraMetadataNative mProperties;
    private ArrayList<RecommendedStreamConfigurationMap> mRecommendedConfigurations;

    static {
        CONTROL_AF_AVAILABLE_MODES = new Key<int[]>("android.control.afAvailableModes", int[].class);
        CONTROL_AVAILABLE_EFFECTS = new Key<int[]>("android.control.availableEffects", int[].class);
        CONTROL_AVAILABLE_SCENE_MODES = new Key<int[]>("android.control.availableSceneModes", int[].class);
        CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES = new Key<int[]>("android.control.availableVideoStabilizationModes", int[].class);
        CONTROL_AWB_AVAILABLE_MODES = new Key<int[]>("android.control.awbAvailableModes", int[].class);
        CONTROL_MAX_REGIONS = new Key<int[]>("android.control.maxRegions", int[].class);
        CONTROL_MAX_REGIONS_AE = new Key<Integer>("android.control.maxRegionsAe", Integer.TYPE);
        CONTROL_MAX_REGIONS_AWB = new Key<Integer>("android.control.maxRegionsAwb", Integer.TYPE);
        CONTROL_MAX_REGIONS_AF = new Key<Integer>("android.control.maxRegionsAf", Integer.TYPE);
        CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS = new Key<HighSpeedVideoConfiguration[]>("android.control.availableHighSpeedVideoConfigurations", HighSpeedVideoConfiguration[].class);
        CONTROL_AE_LOCK_AVAILABLE = new Key<Boolean>("android.control.aeLockAvailable", Boolean.TYPE);
        CONTROL_AWB_LOCK_AVAILABLE = new Key<Boolean>("android.control.awbLockAvailable", Boolean.TYPE);
        CONTROL_AVAILABLE_MODES = new Key<int[]>("android.control.availableModes", int[].class);
        CONTROL_POST_RAW_SENSITIVITY_BOOST_RANGE = new Key<Range<Integer>>("android.control.postRawSensitivityBoostRange", new TypeReference<Range<Integer>>(){});
        EDGE_AVAILABLE_EDGE_MODES = new Key<int[]>("android.edge.availableEdgeModes", int[].class);
        FLASH_INFO_AVAILABLE = new Key<Boolean>("android.flash.info.available", Boolean.TYPE);
        HOT_PIXEL_AVAILABLE_HOT_PIXEL_MODES = new Key<int[]>("android.hotPixel.availableHotPixelModes", int[].class);
        JPEG_AVAILABLE_THUMBNAIL_SIZES = new Key<Size[]>("android.jpeg.availableThumbnailSizes", Size[].class);
        LENS_INFO_AVAILABLE_APERTURES = new Key<float[]>("android.lens.info.availableApertures", float[].class);
        LENS_INFO_AVAILABLE_FILTER_DENSITIES = new Key<float[]>("android.lens.info.availableFilterDensities", float[].class);
        LENS_INFO_AVAILABLE_FOCAL_LENGTHS = new Key<float[]>("android.lens.info.availableFocalLengths", float[].class);
        LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION = new Key<int[]>("android.lens.info.availableOpticalStabilization", int[].class);
        LENS_INFO_HYPERFOCAL_DISTANCE = new Key<Float>("android.lens.info.hyperfocalDistance", Float.TYPE);
        LENS_INFO_MINIMUM_FOCUS_DISTANCE = new Key<Float>("android.lens.info.minimumFocusDistance", Float.TYPE);
        LENS_INFO_SHADING_MAP_SIZE = new Key<Size>("android.lens.info.shadingMapSize", Size.class);
        LENS_INFO_FOCUS_DISTANCE_CALIBRATION = new Key<Integer>("android.lens.info.focusDistanceCalibration", Integer.TYPE);
        LENS_FACING = new Key<Integer>("android.lens.facing", Integer.TYPE);
        LENS_POSE_ROTATION = new Key<float[]>("android.lens.poseRotation", float[].class);
        LENS_POSE_TRANSLATION = new Key<float[]>("android.lens.poseTranslation", float[].class);
        LENS_INTRINSIC_CALIBRATION = new Key<float[]>("android.lens.intrinsicCalibration", float[].class);
        LENS_RADIAL_DISTORTION = new Key<float[]>("android.lens.radialDistortion", float[].class);
        LENS_POSE_REFERENCE = new Key<Integer>("android.lens.poseReference", Integer.TYPE);
        LENS_DISTORTION = new Key<float[]>("android.lens.distortion", float[].class);
        NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES = new Key<int[]>("android.noiseReduction.availableNoiseReductionModes", int[].class);
        QUIRKS_USE_PARTIAL_RESULT = new Key<Byte>("android.quirks.usePartialResult", Byte.TYPE);
        REQUEST_MAX_NUM_OUTPUT_STREAMS = new Key<int[]>("android.request.maxNumOutputStreams", int[].class);
        REQUEST_MAX_NUM_OUTPUT_RAW = new Key<Integer>("android.request.maxNumOutputRaw", Integer.TYPE);
        REQUEST_MAX_NUM_OUTPUT_PROC = new Key<Integer>("android.request.maxNumOutputProc", Integer.TYPE);
        REQUEST_MAX_NUM_OUTPUT_PROC_STALLING = new Key<Integer>("android.request.maxNumOutputProcStalling", Integer.TYPE);
        REQUEST_MAX_NUM_INPUT_STREAMS = new Key<Integer>("android.request.maxNumInputStreams", Integer.TYPE);
        REQUEST_PIPELINE_MAX_DEPTH = new Key<Byte>("android.request.pipelineMaxDepth", Byte.TYPE);
        REQUEST_PARTIAL_RESULT_COUNT = new Key<Integer>("android.request.partialResultCount", Integer.TYPE);
        REQUEST_AVAILABLE_CAPABILITIES = new Key<int[]>("android.request.availableCapabilities", int[].class);
        REQUEST_AVAILABLE_REQUEST_KEYS = new Key<int[]>("android.request.availableRequestKeys", int[].class);
        REQUEST_AVAILABLE_RESULT_KEYS = new Key<int[]>("android.request.availableResultKeys", int[].class);
        REQUEST_AVAILABLE_CHARACTERISTICS_KEYS = new Key<int[]>("android.request.availableCharacteristicsKeys", int[].class);
        REQUEST_AVAILABLE_SESSION_KEYS = new Key<int[]>("android.request.availableSessionKeys", int[].class);
        REQUEST_AVAILABLE_PHYSICAL_CAMERA_REQUEST_KEYS = new Key<int[]>("android.request.availablePhysicalCameraRequestKeys", int[].class);
        REQUEST_CHARACTERISTIC_KEYS_NEEDING_PERMISSION = new Key<int[]>("android.request.characteristicKeysNeedingPermission", int[].class);
        SCALER_AVAILABLE_FORMATS = new Key<int[]>("android.scaler.availableFormats", int[].class);
        SCALER_AVAILABLE_JPEG_MIN_DURATIONS = new Key<long[]>("android.scaler.availableJpegMinDurations", long[].class);
        SCALER_AVAILABLE_JPEG_SIZES = new Key<Size[]>("android.scaler.availableJpegSizes", Size[].class);
        SCALER_AVAILABLE_MAX_DIGITAL_ZOOM = new Key<Float>("android.scaler.availableMaxDigitalZoom", Float.TYPE);
        SCALER_AVAILABLE_PROCESSED_MIN_DURATIONS = new Key<long[]>("android.scaler.availableProcessedMinDurations", long[].class);
        SCALER_AVAILABLE_PROCESSED_SIZES = new Key<Size[]>("android.scaler.availableProcessedSizes", Size[].class);
        SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP = new Key<ReprocessFormatsMap>("android.scaler.availableInputOutputFormatsMap", ReprocessFormatsMap.class);
        SCALER_AVAILABLE_STREAM_CONFIGURATIONS = new Key<StreamConfiguration[]>("android.scaler.availableStreamConfigurations", StreamConfiguration[].class);
        SCALER_AVAILABLE_MIN_FRAME_DURATIONS = new Key<StreamConfigurationDuration[]>("android.scaler.availableMinFrameDurations", StreamConfigurationDuration[].class);
        SCALER_AVAILABLE_STALL_DURATIONS = new Key<StreamConfigurationDuration[]>("android.scaler.availableStallDurations", StreamConfigurationDuration[].class);
        SCALER_STREAM_CONFIGURATION_MAP = new Key<StreamConfigurationMap>("android.scaler.streamConfigurationMap", StreamConfigurationMap.class);
        SCALER_CROPPING_TYPE = new Key<Integer>("android.scaler.croppingType", Integer.TYPE);
        SCALER_AVAILABLE_RECOMMENDED_STREAM_CONFIGURATIONS = new Key<RecommendedStreamConfiguration[]>("android.scaler.availableRecommendedStreamConfigurations", RecommendedStreamConfiguration[].class);
        SCALER_AVAILABLE_RECOMMENDED_INPUT_OUTPUT_FORMATS_MAP = new Key<ReprocessFormatsMap>("android.scaler.availableRecommendedInputOutputFormatsMap", ReprocessFormatsMap.class);
        SCALER_MANDATORY_STREAM_COMBINATIONS = new Key<MandatoryStreamCombination[]>("android.scaler.mandatoryStreamCombinations", MandatoryStreamCombination[].class);
        SENSOR_INFO_ACTIVE_ARRAY_SIZE = new Key<Rect>("android.sensor.info.activeArraySize", Rect.class);
        SENSOR_INFO_SENSITIVITY_RANGE = new Key<Range<Integer>>("android.sensor.info.sensitivityRange", new TypeReference<Range<Integer>>(){});
        SENSOR_INFO_COLOR_FILTER_ARRANGEMENT = new Key<Integer>("android.sensor.info.colorFilterArrangement", Integer.TYPE);
        SENSOR_INFO_EXPOSURE_TIME_RANGE = new Key<Range<Long>>("android.sensor.info.exposureTimeRange", new TypeReference<Range<Long>>(){});
        SENSOR_INFO_MAX_FRAME_DURATION = new Key<Long>("android.sensor.info.maxFrameDuration", Long.TYPE);
        SENSOR_INFO_PHYSICAL_SIZE = new Key<SizeF>("android.sensor.info.physicalSize", SizeF.class);
        SENSOR_INFO_PIXEL_ARRAY_SIZE = new Key<Size>("android.sensor.info.pixelArraySize", Size.class);
        SENSOR_INFO_WHITE_LEVEL = new Key<Integer>("android.sensor.info.whiteLevel", Integer.TYPE);
        SENSOR_INFO_TIMESTAMP_SOURCE = new Key<Integer>("android.sensor.info.timestampSource", Integer.TYPE);
        SENSOR_INFO_LENS_SHADING_APPLIED = new Key<Boolean>("android.sensor.info.lensShadingApplied", Boolean.TYPE);
        SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE = new Key<Rect>("android.sensor.info.preCorrectionActiveArraySize", Rect.class);
        SENSOR_REFERENCE_ILLUMINANT1 = new Key<Integer>("android.sensor.referenceIlluminant1", Integer.TYPE);
        SENSOR_REFERENCE_ILLUMINANT2 = new Key<Byte>("android.sensor.referenceIlluminant2", Byte.TYPE);
        SENSOR_CALIBRATION_TRANSFORM1 = new Key<ColorSpaceTransform>("android.sensor.calibrationTransform1", ColorSpaceTransform.class);
        SENSOR_CALIBRATION_TRANSFORM2 = new Key<ColorSpaceTransform>("android.sensor.calibrationTransform2", ColorSpaceTransform.class);
        SENSOR_COLOR_TRANSFORM1 = new Key<ColorSpaceTransform>("android.sensor.colorTransform1", ColorSpaceTransform.class);
        SENSOR_COLOR_TRANSFORM2 = new Key<ColorSpaceTransform>("android.sensor.colorTransform2", ColorSpaceTransform.class);
        SENSOR_FORWARD_MATRIX1 = new Key<ColorSpaceTransform>("android.sensor.forwardMatrix1", ColorSpaceTransform.class);
        SENSOR_FORWARD_MATRIX2 = new Key<ColorSpaceTransform>("android.sensor.forwardMatrix2", ColorSpaceTransform.class);
        SENSOR_BLACK_LEVEL_PATTERN = new Key<BlackLevelPattern>("android.sensor.blackLevelPattern", BlackLevelPattern.class);
        SENSOR_MAX_ANALOG_SENSITIVITY = new Key<Integer>("android.sensor.maxAnalogSensitivity", Integer.TYPE);
        SENSOR_ORIENTATION = new Key<Integer>("android.sensor.orientation", Integer.TYPE);
        SENSOR_AVAILABLE_TEST_PATTERN_MODES = new Key<int[]>("android.sensor.availableTestPatternModes", int[].class);
        SENSOR_OPTICAL_BLACK_REGIONS = new Key<Rect[]>("android.sensor.opticalBlackRegions", Rect[].class);
        SHADING_AVAILABLE_MODES = new Key<int[]>("android.shading.availableModes", int[].class);
        STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES = new Key<int[]>("android.statistics.info.availableFaceDetectModes", int[].class);
        STATISTICS_INFO_MAX_FACE_COUNT = new Key<Integer>("android.statistics.info.maxFaceCount", Integer.TYPE);
        STATISTICS_INFO_AVAILABLE_HOT_PIXEL_MAP_MODES = new Key<boolean[]>("android.statistics.info.availableHotPixelMapModes", boolean[].class);
        STATISTICS_INFO_AVAILABLE_LENS_SHADING_MAP_MODES = new Key<int[]>("android.statistics.info.availableLensShadingMapModes", int[].class);
        STATISTICS_INFO_AVAILABLE_OIS_DATA_MODES = new Key<int[]>("android.statistics.info.availableOisDataModes", int[].class);
        TONEMAP_MAX_CURVE_POINTS = new Key<Integer>("android.tonemap.maxCurvePoints", Integer.TYPE);
        TONEMAP_AVAILABLE_TONE_MAP_MODES = new Key<int[]>("android.tonemap.availableToneMapModes", int[].class);
        LED_AVAILABLE_LEDS = new Key<int[]>("android.led.availableLeds", int[].class);
        INFO_SUPPORTED_HARDWARE_LEVEL = new Key<Integer>("android.info.supportedHardwareLevel", Integer.TYPE);
        INFO_VERSION = new Key<String>("android.info.version", String.class);
        SYNC_MAX_LATENCY = new Key<Integer>("android.sync.maxLatency", Integer.TYPE);
        REPROCESS_MAX_CAPTURE_STALL = new Key<Integer>("android.reprocess.maxCaptureStall", Integer.TYPE);
        DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS = new Key<StreamConfiguration[]>("android.depth.availableDepthStreamConfigurations", StreamConfiguration[].class);
        DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS = new Key<StreamConfigurationDuration[]>("android.depth.availableDepthMinFrameDurations", StreamConfigurationDuration[].class);
        DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS = new Key<StreamConfigurationDuration[]>("android.depth.availableDepthStallDurations", StreamConfigurationDuration[].class);
        DEPTH_DEPTH_IS_EXCLUSIVE = new Key<Boolean>("android.depth.depthIsExclusive", Boolean.TYPE);
        DEPTH_AVAILABLE_RECOMMENDED_DEPTH_STREAM_CONFIGURATIONS = new Key<RecommendedStreamConfiguration[]>("android.depth.availableRecommendedDepthStreamConfigurations", RecommendedStreamConfiguration[].class);
        DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS = new Key<StreamConfiguration[]>("android.depth.availableDynamicDepthStreamConfigurations", StreamConfiguration[].class);
        DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS = new Key<StreamConfigurationDuration[]>("android.depth.availableDynamicDepthMinFrameDurations", StreamConfigurationDuration[].class);
        DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS = new Key<StreamConfigurationDuration[]>("android.depth.availableDynamicDepthStallDurations", StreamConfigurationDuration[].class);
        LOGICAL_MULTI_CAMERA_PHYSICAL_IDS = new Key<byte[]>("android.logicalMultiCamera.physicalIds", byte[].class);
        LOGICAL_MULTI_CAMERA_SENSOR_SYNC_TYPE = new Key<Integer>("android.logicalMultiCamera.sensorSyncType", Integer.TYPE);
        DISTORTION_CORRECTION_AVAILABLE_MODES = new Key<int[]>("android.distortionCorrection.availableModes", int[].class);
        HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS = new Key<StreamConfiguration[]>("android.heic.availableHeicStreamConfigurations", StreamConfiguration[].class);
        HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS = new Key<StreamConfigurationDuration[]>("android.heic.availableHeicMinFrameDurations", StreamConfigurationDuration[].class);
        HEIC_AVAILABLE_HEIC_STALL_DURATIONS = new Key<StreamConfigurationDuration[]>("android.heic.availableHeicStallDurations", StreamConfigurationDuration[].class);
    }

    public CameraCharacteristics(CameraMetadataNative cameraMetadataNative) {
        this.mProperties = CameraMetadataNative.move(cameraMetadataNative);
        this.setNativeInstance(this.mProperties);
    }

    private <TKey> List<TKey> getAvailableKeyList(Class<?> class_, Class<TKey> class_2, int[] arrn, boolean bl) {
        if (!class_.equals(CameraMetadata.class)) {
            if (CameraMetadata.class.isAssignableFrom(class_)) {
                return Collections.unmodifiableList(this.getKeys(class_, class_2, null, arrn, bl));
            }
            throw new AssertionError((Object)"metadataClass must be a subclass of CameraMetadata");
        }
        throw new AssertionError((Object)"metadataClass must be a strict subclass of CameraMetadata");
    }

    public <T> T get(Key<T> key) {
        return this.mProperties.get(key);
    }

    public List<CaptureRequest.Key<?>> getAvailableCaptureRequestKeys() {
        if (this.mAvailableRequestKeys == null) {
            Class<CaptureRequest.Key> class_ = CaptureRequest.Key.class;
            int[] arrn = this.get(REQUEST_AVAILABLE_REQUEST_KEYS);
            if (arrn != null) {
                this.mAvailableRequestKeys = this.getAvailableKeyList(CaptureRequest.class, class_, arrn, true);
            } else {
                throw new AssertionError((Object)"android.request.availableRequestKeys must be non-null in the characteristics");
            }
        }
        return this.mAvailableRequestKeys;
    }

    public List<CaptureResult.Key<?>> getAvailableCaptureResultKeys() {
        if (this.mAvailableResultKeys == null) {
            Class<CaptureResult.Key> class_ = CaptureResult.Key.class;
            int[] arrn = this.get(REQUEST_AVAILABLE_RESULT_KEYS);
            if (arrn != null) {
                this.mAvailableResultKeys = this.getAvailableKeyList(CaptureResult.class, class_, arrn, true);
            } else {
                throw new AssertionError((Object)"android.request.availableResultKeys must be non-null in the characteristics");
            }
        }
        return this.mAvailableResultKeys;
    }

    public List<CaptureRequest.Key<?>> getAvailablePhysicalCameraRequestKeys() {
        if (this.mAvailablePhysicalRequestKeys == null) {
            Class<CaptureRequest.Key> class_ = CaptureRequest.Key.class;
            int[] arrn = this.get(REQUEST_AVAILABLE_PHYSICAL_CAMERA_REQUEST_KEYS);
            if (arrn == null) {
                return null;
            }
            this.mAvailablePhysicalRequestKeys = this.getAvailableKeyList(CaptureRequest.class, class_, arrn, false);
        }
        return this.mAvailablePhysicalRequestKeys;
    }

    public List<CaptureRequest.Key<?>> getAvailableSessionKeys() {
        if (this.mAvailableSessionKeys == null) {
            Class<CaptureRequest.Key> class_ = CaptureRequest.Key.class;
            int[] arrn = this.get(REQUEST_AVAILABLE_SESSION_KEYS);
            if (arrn == null) {
                return null;
            }
            this.mAvailableSessionKeys = this.getAvailableKeyList(CaptureRequest.class, class_, arrn, false);
        }
        return this.mAvailableSessionKeys;
    }

    @Override
    protected Class<Key<?>> getKeyClass() {
        return Key.class;
    }

    @Override
    public List<Key<?>> getKeys() {
        int[] arrn = this.mKeys;
        if (arrn != null) {
            return arrn;
        }
        arrn = this.get(REQUEST_AVAILABLE_CHARACTERISTICS_KEYS);
        if (arrn != null) {
            this.mKeys = Collections.unmodifiableList(this.getKeys(this.getClass(), this.getKeyClass(), this, arrn, true));
            return this.mKeys;
        }
        throw new AssertionError((Object)"android.request.availableCharacteristicsKeys must be non-null in the characteristics");
    }

    public List<Key<?>> getKeysNeedingPermission() {
        if (this.mKeysNeedingPermission == null) {
            Class<Key> class_ = Key.class;
            int[] arrn = this.get(REQUEST_CHARACTERISTIC_KEYS_NEEDING_PERMISSION);
            if (arrn == null) {
                this.mKeysNeedingPermission = Collections.unmodifiableList(new ArrayList());
                return this.mKeysNeedingPermission;
            }
            this.mKeysNeedingPermission = this.getAvailableKeyList(CameraCharacteristics.class, class_, arrn, false);
        }
        return this.mKeysNeedingPermission;
    }

    public CameraMetadataNative getNativeCopy() {
        return new CameraMetadataNative(this.mProperties);
    }

    public Set<String> getPhysicalCameraIds() {
        Object object = this.get(REQUEST_AVAILABLE_CAPABILITIES);
        if (object != null) {
            if (!ArrayUtils.contains((int[])object, 11)) {
                return Collections.emptySet();
            }
            object = this.get(LOGICAL_MULTI_CAMERA_PHYSICAL_IDS);
            try {
                object = new String((byte[])object, "UTF-8");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new AssertionError((Object)"android.logicalCam.physicalIds must be UTF-8 string");
            }
            return Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(((String)object).split("\u0000"))));
        }
        throw new AssertionError((Object)"android.request.availableCapabilities must be non-null in the characteristics");
    }

    @Override
    protected <T> T getProtected(Key<?> key) {
        return (T)this.mProperties.get(key);
    }

    public RecommendedStreamConfigurationMap getRecommendedStreamConfigurationMap(int n) {
        if (n >= 0 && n <= 6 || n >= 24 && n < 32) {
            if (this.mRecommendedConfigurations == null) {
                this.mRecommendedConfigurations = this.mProperties.getRecommendedStreamConfigurations();
                if (this.mRecommendedConfigurations == null) {
                    return null;
                }
            }
            return this.mRecommendedConfigurations.get(n);
        }
        throw new IllegalArgumentException(String.format("Invalid use case: %d", n));
    }

    public static final class Key<T> {
        private final CameraMetadataNative.Key<T> mKey;

        private Key(CameraMetadataNative.Key<?> key) {
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
            return String.format("CameraCharacteristics.Key(%s)", this.mKey.getName());
        }
    }

}

