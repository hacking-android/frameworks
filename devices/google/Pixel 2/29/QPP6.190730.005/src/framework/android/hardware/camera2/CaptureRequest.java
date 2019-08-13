/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Rect;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.PublicKey;
import android.hardware.camera2.impl.SyntheticKey;
import android.hardware.camera2.params.ColorSpaceTransform;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.RggbChannelVector;
import android.hardware.camera2.params.TonemapCurve;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.hardware.camera2.utils.SurfaceUtils;
import android.hardware.camera2.utils.TypeReference;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.util.SparseArray;
import android.view.Surface;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class CaptureRequest
extends CameraMetadata<Key<?>>
implements Parcelable {
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
    public static final Key<Range<Integer>> CONTROL_AE_TARGET_FPS_RANGE;
    @PublicKey
    public static final Key<Integer> CONTROL_AF_MODE;
    @PublicKey
    public static final Key<MeteringRectangle[]> CONTROL_AF_REGIONS;
    @PublicKey
    public static final Key<Integer> CONTROL_AF_TRIGGER;
    @PublicKey
    public static final Key<Boolean> CONTROL_AWB_LOCK;
    @PublicKey
    public static final Key<Integer> CONTROL_AWB_MODE;
    @PublicKey
    public static final Key<MeteringRectangle[]> CONTROL_AWB_REGIONS;
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
    public static final Parcelable.Creator<CaptureRequest> CREATOR;
    @PublicKey
    public static final Key<Integer> DISTORTION_CORRECTION_MODE;
    @PublicKey
    public static final Key<Integer> EDGE_MODE;
    @PublicKey
    public static final Key<Integer> FLASH_MODE;
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
    public static final Key<Float> LENS_FILTER_DENSITY;
    @PublicKey
    public static final Key<Float> LENS_FOCAL_LENGTH;
    @PublicKey
    public static final Key<Float> LENS_FOCUS_DISTANCE;
    @PublicKey
    public static final Key<Integer> LENS_OPTICAL_STABILIZATION_MODE;
    @PublicKey
    public static final Key<Integer> NOISE_REDUCTION_MODE;
    @PublicKey
    public static final Key<Float> REPROCESS_EFFECTIVE_EXPOSURE_FACTOR;
    public static final Key<Integer> REQUEST_ID;
    public static final int REQUEST_TYPE_COUNT = 3;
    public static final int REQUEST_TYPE_REGULAR = 0;
    public static final int REQUEST_TYPE_REPROCESS = 1;
    public static final int REQUEST_TYPE_ZSL_STILL = 2;
    @PublicKey
    public static final Key<Rect> SCALER_CROP_REGION;
    @PublicKey
    public static final Key<Long> SENSOR_EXPOSURE_TIME;
    @PublicKey
    public static final Key<Long> SENSOR_FRAME_DURATION;
    @PublicKey
    public static final Key<Integer> SENSOR_SENSITIVITY;
    @PublicKey
    public static final Key<int[]> SENSOR_TEST_PATTERN_DATA;
    @PublicKey
    public static final Key<Integer> SENSOR_TEST_PATTERN_MODE;
    @PublicKey
    public static final Key<Integer> SHADING_MODE;
    @PublicKey
    public static final Key<Integer> STATISTICS_FACE_DETECT_MODE;
    @PublicKey
    public static final Key<Boolean> STATISTICS_HOT_PIXEL_MAP_MODE;
    @PublicKey
    public static final Key<Integer> STATISTICS_LENS_SHADING_MAP_MODE;
    @PublicKey
    public static final Key<Integer> STATISTICS_OIS_DATA_MODE;
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
    private static final ArraySet<Surface> mEmptySurfaceSet;
    private final String TAG;
    private boolean mIsPartOfCHSRequestList = false;
    private boolean mIsReprocess;
    private String mLogicalCameraId;
    @UnsupportedAppUsage
    private CameraMetadataNative mLogicalCameraSettings;
    private final HashMap<String, CameraMetadataNative> mPhysicalCameraSettings = new HashMap();
    private int mReprocessableSessionId;
    private int mRequestType = -1;
    private int[] mStreamIdxArray;
    private boolean mSurfaceConverted = false;
    private int[] mSurfaceIdxArray;
    private final ArraySet<Surface> mSurfaceSet = new ArraySet();
    private final Object mSurfacesLock = new Object();
    private Object mUserTag;

    static {
        mEmptySurfaceSet = new ArraySet();
        CREATOR = new Parcelable.Creator<CaptureRequest>(){

            @Override
            public CaptureRequest createFromParcel(Parcel parcel) {
                CaptureRequest captureRequest = new CaptureRequest();
                captureRequest.readFromParcel(parcel);
                return captureRequest;
            }

            public CaptureRequest[] newArray(int n) {
                return new CaptureRequest[n];
            }
        };
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
        CONTROL_AF_MODE = new Key<Integer>("android.control.afMode", Integer.TYPE);
        CONTROL_AF_REGIONS = new Key<MeteringRectangle[]>("android.control.afRegions", MeteringRectangle[].class);
        CONTROL_AF_TRIGGER = new Key<Integer>("android.control.afTrigger", Integer.TYPE);
        CONTROL_AWB_LOCK = new Key<Boolean>("android.control.awbLock", Boolean.TYPE);
        CONTROL_AWB_MODE = new Key<Integer>("android.control.awbMode", Integer.TYPE);
        CONTROL_AWB_REGIONS = new Key<MeteringRectangle[]>("android.control.awbRegions", MeteringRectangle[].class);
        CONTROL_CAPTURE_INTENT = new Key<Integer>("android.control.captureIntent", Integer.TYPE);
        CONTROL_EFFECT_MODE = new Key<Integer>("android.control.effectMode", Integer.TYPE);
        CONTROL_MODE = new Key<Integer>("android.control.mode", Integer.TYPE);
        CONTROL_SCENE_MODE = new Key<Integer>("android.control.sceneMode", Integer.TYPE);
        CONTROL_VIDEO_STABILIZATION_MODE = new Key<Integer>("android.control.videoStabilizationMode", Integer.TYPE);
        CONTROL_POST_RAW_SENSITIVITY_BOOST = new Key<Integer>("android.control.postRawSensitivityBoost", Integer.TYPE);
        CONTROL_ENABLE_ZSL = new Key<Boolean>("android.control.enableZsl", Boolean.TYPE);
        EDGE_MODE = new Key<Integer>("android.edge.mode", Integer.TYPE);
        FLASH_MODE = new Key<Integer>("android.flash.mode", Integer.TYPE);
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
        LENS_OPTICAL_STABILIZATION_MODE = new Key<Integer>("android.lens.opticalStabilizationMode", Integer.TYPE);
        NOISE_REDUCTION_MODE = new Key<Integer>("android.noiseReduction.mode", Integer.TYPE);
        REQUEST_ID = new Key<Integer>("android.request.id", Integer.TYPE);
        SCALER_CROP_REGION = new Key<Rect>("android.scaler.cropRegion", Rect.class);
        SENSOR_EXPOSURE_TIME = new Key<Long>("android.sensor.exposureTime", Long.TYPE);
        SENSOR_FRAME_DURATION = new Key<Long>("android.sensor.frameDuration", Long.TYPE);
        SENSOR_SENSITIVITY = new Key<Integer>("android.sensor.sensitivity", Integer.TYPE);
        SENSOR_TEST_PATTERN_DATA = new Key<int[]>("android.sensor.testPatternData", int[].class);
        SENSOR_TEST_PATTERN_MODE = new Key<Integer>("android.sensor.testPatternMode", Integer.TYPE);
        SHADING_MODE = new Key<Integer>("android.shading.mode", Integer.TYPE);
        STATISTICS_FACE_DETECT_MODE = new Key<Integer>("android.statistics.faceDetectMode", Integer.TYPE);
        STATISTICS_HOT_PIXEL_MAP_MODE = new Key<Boolean>("android.statistics.hotPixelMapMode", Boolean.TYPE);
        STATISTICS_LENS_SHADING_MAP_MODE = new Key<Integer>("android.statistics.lensShadingMapMode", Integer.TYPE);
        STATISTICS_OIS_DATA_MODE = new Key<Integer>("android.statistics.oisDataMode", Integer.TYPE);
        TONEMAP_CURVE_BLUE = new Key<float[]>("android.tonemap.curveBlue", float[].class);
        TONEMAP_CURVE_GREEN = new Key<float[]>("android.tonemap.curveGreen", float[].class);
        TONEMAP_CURVE_RED = new Key<float[]>("android.tonemap.curveRed", float[].class);
        TONEMAP_CURVE = new Key<TonemapCurve>("android.tonemap.curve", TonemapCurve.class);
        TONEMAP_MODE = new Key<Integer>("android.tonemap.mode", Integer.TYPE);
        TONEMAP_GAMMA = new Key<Float>("android.tonemap.gamma", Float.TYPE);
        TONEMAP_PRESET_CURVE = new Key<Integer>("android.tonemap.presetCurve", Integer.TYPE);
        LED_TRANSMIT = new Key<Boolean>("android.led.transmit", Boolean.TYPE);
        BLACK_LEVEL_LOCK = new Key<Boolean>("android.blackLevel.lock", Boolean.TYPE);
        REPROCESS_EFFECTIVE_EXPOSURE_FACTOR = new Key<Float>("android.reprocess.effectiveExposureFactor", Float.TYPE);
        DISTORTION_CORRECTION_MODE = new Key<Integer>("android.distortionCorrection.mode", Integer.TYPE);
    }

    private CaptureRequest() {
        this.TAG = "CaptureRequest-JV";
        this.mIsReprocess = false;
        this.mReprocessableSessionId = -1;
    }

    private CaptureRequest(CaptureRequest captureRequest) {
        this.TAG = "CaptureRequest-JV";
        this.mLogicalCameraId = new String(captureRequest.mLogicalCameraId);
        for (Map.Entry<String, CameraMetadataNative> entry : captureRequest.mPhysicalCameraSettings.entrySet()) {
            this.mPhysicalCameraSettings.put(new String(entry.getKey()), new CameraMetadataNative(entry.getValue()));
        }
        this.mLogicalCameraSettings = this.mPhysicalCameraSettings.get(this.mLogicalCameraId);
        this.setNativeInstance(this.mLogicalCameraSettings);
        this.mSurfaceSet.addAll(captureRequest.mSurfaceSet);
        this.mIsReprocess = captureRequest.mIsReprocess;
        this.mIsPartOfCHSRequestList = captureRequest.mIsPartOfCHSRequestList;
        this.mReprocessableSessionId = captureRequest.mReprocessableSessionId;
        this.mUserTag = captureRequest.mUserTag;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private CaptureRequest(CameraMetadataNative object, boolean bl, int n, String object2, Set<String> set) {
        void var2_5;
        void var5_8;
        void var3_6;
        Iterator iterator;
        this.TAG = "CaptureRequest-JV";
        if (var5_8 != null) {
            if (var2_5 != false) throw new IllegalArgumentException("Create a reprocess capture request with with more than one physical camera is not supported!");
        }
        this.mLogicalCameraId = iterator;
        this.mLogicalCameraSettings = CameraMetadataNative.move((CameraMetadataNative)object);
        this.mPhysicalCameraSettings.put(this.mLogicalCameraId, this.mLogicalCameraSettings);
        if (var5_8 != null) {
            for (String string2 : var5_8) {
                this.mPhysicalCameraSettings.put(string2, new CameraMetadataNative(this.mLogicalCameraSettings));
            }
        }
        this.setNativeInstance(this.mLogicalCameraSettings);
        this.mIsReprocess = var2_5;
        if (var2_5 == false) {
            this.mReprocessableSessionId = -1;
            return;
        }
        if (var3_6 != -1) {
            this.mReprocessableSessionId = var3_6;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Create a reprocess capture request with an invalid session ID: ");
        stringBuilder.append((int)var3_6);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private boolean equals(CaptureRequest captureRequest) {
        boolean bl = captureRequest != null && Objects.equals(this.mUserTag, captureRequest.mUserTag) && this.mSurfaceSet.equals(captureRequest.mSurfaceSet) && this.mPhysicalCameraSettings.equals(captureRequest.mPhysicalCameraSettings) && this.mLogicalCameraId.equals(captureRequest.mLogicalCameraId) && this.mLogicalCameraSettings.equals(captureRequest.mLogicalCameraSettings) && this.mIsReprocess == captureRequest.mIsReprocess && this.mReprocessableSessionId == captureRequest.mReprocessableSessionId;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readFromParcel(Parcel object) {
        Object object2;
        Parcelable[] arrparcelable;
        int n;
        int n2 = ((Parcel)object).readInt();
        if (n2 <= 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Physical camera count");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" should always be positive");
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        this.mLogicalCameraId = ((Parcel)object).readString();
        this.mLogicalCameraSettings = new CameraMetadataNative();
        this.mLogicalCameraSettings.readFromParcel((Parcel)object);
        this.setNativeInstance(this.mLogicalCameraSettings);
        this.mPhysicalCameraSettings.put(this.mLogicalCameraId, this.mLogicalCameraSettings);
        for (n = 1; n < n2; ++n) {
            object2 = ((Parcel)object).readString();
            arrparcelable = new CameraMetadataNative();
            arrparcelable.readFromParcel((Parcel)object);
            this.mPhysicalCameraSettings.put((String)object2, (CameraMetadataNative)arrparcelable);
        }
        n2 = ((Parcel)object).readInt();
        boolean bl = n2 != 0;
        this.mIsReprocess = bl;
        this.mReprocessableSessionId = -1;
        object2 = this.mSurfacesLock;
        synchronized (object2) {
            this.mSurfaceSet.clear();
            arrparcelable = ((Parcel)object).readParcelableArray(Surface.class.getClassLoader());
            if (arrparcelable != null) {
                n2 = arrparcelable.length;
                for (n = 0; n < n2; ++n) {
                    Surface surface = (Surface)arrparcelable[n];
                    this.mSurfaceSet.add(surface);
                }
            }
            if (((Parcel)object).readInt() == 0) {
                return;
            }
            object = new RuntimeException("Reading cached CaptureRequest is not supported");
            throw object;
        }
    }

    public boolean containsTarget(Surface surface) {
        return this.mSurfaceSet.contains(surface);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void convertSurfaceToStreamId(SparseArray<OutputConfiguration> object) {
        Object object2 = this.mSurfacesLock;
        synchronized (object2) {
            boolean bl;
            if (this.mSurfaceConverted) {
                Log.v("CaptureRequest-JV", "Cannot convert already converted surfaces!");
                return;
            }
            this.mStreamIdxArray = new int[this.mSurfaceSet.size()];
            this.mSurfaceIdxArray = new int[this.mSurfaceSet.size()];
            int n = 0;
            Iterator<Surface> iterator = this.mSurfaceSet.iterator();
            block4 : do {
                boolean bl2;
                int n2;
                int n3;
                if (!iterator.hasNext()) {
                    this.mSurfaceConverted = true;
                    return;
                }
                Object object3 = iterator.next();
                bl = false;
                int n4 = 0;
                int n5 = 0;
                do {
                    n3 = n;
                    bl2 = bl;
                    if (n5 >= ((SparseArray)object).size()) break;
                    int n6 = ((SparseArray)object).keyAt(n5);
                    Object object4 = ((SparseArray)object).valueAt(n5);
                    n2 = 0;
                    object4 = ((OutputConfiguration)object4).getSurfaces().iterator();
                    do {
                        n3 = n;
                        bl2 = bl;
                        if (!object4.hasNext()) break;
                        if (object3 == (Surface)object4.next()) {
                            bl2 = true;
                            this.mStreamIdxArray[n] = n6;
                            this.mSurfaceIdxArray[n] = n2;
                            n3 = n + 1;
                            break;
                        }
                        ++n2;
                    } while (true);
                    if (bl2) break;
                    ++n5;
                    n = n3;
                    bl = bl2;
                } while (true);
                n = n3;
                bl = bl2;
                if (bl2) continue;
                long l = SurfaceUtils.getSurfaceId((Surface)object3);
                n5 = n4;
                do {
                    n = n3;
                    bl = bl2;
                    if (n5 >= ((SparseArray)object).size()) continue block4;
                    n4 = ((SparseArray)object).keyAt(n5);
                    object3 = (OutputConfiguration)((SparseArray)object).valueAt(n5);
                    n2 = 0;
                    object3 = ((OutputConfiguration)object3).getSurfaces().iterator();
                    do {
                        n = n3;
                        bl = bl2;
                        if (!object3.hasNext()) break;
                        if (l == SurfaceUtils.getSurfaceId((Surface)object3.next())) {
                            bl = true;
                            this.mStreamIdxArray[n3] = n4;
                            this.mSurfaceIdxArray[n3] = n2;
                            n = n3 + 1;
                            break;
                        }
                        ++n2;
                    } while (true);
                    if (bl) continue block4;
                    ++n5;
                    n3 = n;
                    bl2 = bl;
                } while (true);
            } while (bl);
            this.mStreamIdxArray = null;
            this.mSurfaceIdxArray = null;
            object = new IllegalArgumentException("CaptureRequest contains unconfigured Input/Output Surface!");
            throw object;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof CaptureRequest && this.equals((CaptureRequest)object);
        return bl;
    }

    public <T> T get(Key<T> key) {
        return this.mLogicalCameraSettings.get(key);
    }

    @Override
    protected Class<Key<?>> getKeyClass() {
        return Key.class;
    }

    @Override
    public List<Key<?>> getKeys() {
        return super.getKeys();
    }

    public String getLogicalCameraId() {
        return this.mLogicalCameraId;
    }

    public CameraMetadataNative getNativeCopy() {
        return new CameraMetadataNative(this.mLogicalCameraSettings);
    }

    @Override
    protected <T> T getProtected(Key<?> key) {
        return (T)this.mLogicalCameraSettings.get(key);
    }

    public int getReprocessableSessionId() {
        int n;
        if (this.mIsReprocess && (n = this.mReprocessableSessionId) != -1) {
            return n;
        }
        throw new IllegalStateException("Getting the reprocessable session ID for a non-reprocess capture request is illegal.");
    }

    public int getRequestType() {
        if (this.mRequestType == -1) {
            if (this.mIsReprocess) {
                this.mRequestType = 1;
            } else {
                Boolean bl = this.mLogicalCameraSettings.get(CONTROL_ENABLE_ZSL);
                int n = 0;
                int n2 = 2;
                int n3 = n;
                if (bl != null) {
                    n3 = n;
                    if (bl.booleanValue()) {
                        n3 = n;
                        if (this.mLogicalCameraSettings.get(CONTROL_CAPTURE_INTENT) == 2) {
                            n3 = 1;
                        }
                    }
                }
                n3 = n3 != 0 ? n2 : 0;
                this.mRequestType = n3;
            }
        }
        return this.mRequestType;
    }

    public Object getTag() {
        return this.mUserTag;
    }

    @UnsupportedAppUsage
    public Collection<Surface> getTargets() {
        return Collections.unmodifiableCollection(this.mSurfaceSet);
    }

    public int hashCode() {
        return HashCodeHelpers.hashCodeGeneric(this.mPhysicalCameraSettings, this.mSurfaceSet, this.mUserTag);
    }

    public boolean isPartOfCRequestList() {
        return this.mIsPartOfCHSRequestList;
    }

    public boolean isReprocess() {
        return this.mIsReprocess;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void recoverStreamIdToSurface() {
        Object object = this.mSurfacesLock;
        synchronized (object) {
            if (!this.mSurfaceConverted) {
                Log.v("CaptureRequest-JV", "Cannot convert already converted surfaces!");
                return;
            }
            this.mStreamIdxArray = null;
            this.mSurfaceIdxArray = null;
            this.mSurfaceConverted = false;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void writeToParcel(Parcel parcel, int n) {
        Map.Entry<String, CameraMetadataNative> entry2;
        parcel.writeInt(this.mPhysicalCameraSettings.size());
        parcel.writeString(this.mLogicalCameraId);
        this.mLogicalCameraSettings.writeToParcel(parcel, n);
        for (Map.Entry<String, CameraMetadataNative> entry2 : this.mPhysicalCameraSettings.entrySet()) {
            if (entry2.getKey().equals(this.mLogicalCameraId)) continue;
            parcel.writeString((String)entry2.getKey());
            ((CameraMetadataNative)entry2.getValue()).writeToParcel(parcel, n);
        }
        parcel.writeInt((int)this.mIsReprocess);
        entry2 = this.mSurfacesLock;
        synchronized (entry2) {
            ArraySet<Surface> arraySet = this.mSurfaceConverted ? mEmptySurfaceSet : this.mSurfaceSet;
            parcel.writeParcelableArray((Parcelable[])arraySet.toArray(new Surface[arraySet.size()]), n);
            if (this.mSurfaceConverted) {
                parcel.writeInt(this.mStreamIdxArray.length);
                for (n = 0; n < this.mStreamIdxArray.length; ++n) {
                    parcel.writeInt(this.mStreamIdxArray[n]);
                    parcel.writeInt(this.mSurfaceIdxArray[n]);
                }
            } else {
                parcel.writeInt(0);
            }
            return;
        }
    }

    public static final class Builder {
        private final CaptureRequest mRequest;

        public Builder(CameraMetadataNative cameraMetadataNative, boolean bl, int n, String string2, Set<String> set) {
            this.mRequest = new CaptureRequest(cameraMetadataNative, bl, n, string2, set);
        }

        public void addTarget(Surface surface) {
            this.mRequest.mSurfaceSet.add(surface);
        }

        public CaptureRequest build() {
            return new CaptureRequest(this.mRequest);
        }

        public <T> T get(Key<T> key) {
            return this.mRequest.mLogicalCameraSettings.get(key);
        }

        public <T> T getPhysicalCameraKey(Key<T> object, String string2) {
            if (this.mRequest.mPhysicalCameraSettings.containsKey(string2)) {
                return ((CameraMetadataNative)this.mRequest.mPhysicalCameraSettings.get(string2)).get(object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Physical camera id: ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" is not valid!");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public boolean isEmpty() {
            return this.mRequest.mLogicalCameraSettings.isEmpty();
        }

        public void removeTarget(Surface surface) {
            this.mRequest.mSurfaceSet.remove(surface);
        }

        public <T> void set(Key<T> key, T t) {
            this.mRequest.mLogicalCameraSettings.set(key, t);
        }

        @UnsupportedAppUsage
        public void setPartOfCHSRequestList(boolean bl) {
            this.mRequest.mIsPartOfCHSRequestList = bl;
        }

        public <T> Builder setPhysicalCameraKey(Key<T> object, T t, String string2) {
            if (this.mRequest.mPhysicalCameraSettings.containsKey(string2)) {
                ((CameraMetadataNative)this.mRequest.mPhysicalCameraSettings.get(string2)).set(object, t);
                return this;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Physical camera id: ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" is not valid!");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public void setTag(Object object) {
            this.mRequest.mUserTag = object;
        }
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
            return String.format("CaptureRequest.Key(%s)", this.mKey.getName());
        }
    }

}

