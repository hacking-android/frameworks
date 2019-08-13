/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.GetCommand;
import android.hardware.camera2.impl.SetCommand;
import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.MarshalRegistry;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.marshal.impl.MarshalQueryableArray;
import android.hardware.camera2.marshal.impl.MarshalQueryableBlackLevelPattern;
import android.hardware.camera2.marshal.impl.MarshalQueryableBoolean;
import android.hardware.camera2.marshal.impl.MarshalQueryableColorSpaceTransform;
import android.hardware.camera2.marshal.impl.MarshalQueryableEnum;
import android.hardware.camera2.marshal.impl.MarshalQueryableHighSpeedVideoConfiguration;
import android.hardware.camera2.marshal.impl.MarshalQueryableMeteringRectangle;
import android.hardware.camera2.marshal.impl.MarshalQueryableNativeByteToInteger;
import android.hardware.camera2.marshal.impl.MarshalQueryablePair;
import android.hardware.camera2.marshal.impl.MarshalQueryableParcelable;
import android.hardware.camera2.marshal.impl.MarshalQueryablePrimitive;
import android.hardware.camera2.marshal.impl.MarshalQueryableRange;
import android.hardware.camera2.marshal.impl.MarshalQueryableRecommendedStreamConfiguration;
import android.hardware.camera2.marshal.impl.MarshalQueryableRect;
import android.hardware.camera2.marshal.impl.MarshalQueryableReprocessFormatsMap;
import android.hardware.camera2.marshal.impl.MarshalQueryableRggbChannelVector;
import android.hardware.camera2.marshal.impl.MarshalQueryableSize;
import android.hardware.camera2.marshal.impl.MarshalQueryableSizeF;
import android.hardware.camera2.marshal.impl.MarshalQueryableStreamConfiguration;
import android.hardware.camera2.marshal.impl.MarshalQueryableStreamConfigurationDuration;
import android.hardware.camera2.marshal.impl.MarshalQueryableString;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.params.HighSpeedVideoConfiguration;
import android.hardware.camera2.params.LensShadingMap;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.hardware.camera2.params.OisSample;
import android.hardware.camera2.params.RecommendedStreamConfiguration;
import android.hardware.camera2.params.RecommendedStreamConfigurationMap;
import android.hardware.camera2.params.ReprocessFormatsMap;
import android.hardware.camera2.params.StreamConfiguration;
import android.hardware.camera2.params.StreamConfigurationDuration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.hardware.camera2.params.TonemapCurve;
import android.hardware.camera2.utils.TypeReference;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ServiceSpecificException;
import android.util.Log;
import android.util.Size;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CameraMetadataNative
implements Parcelable {
    private static final String CELLID_PROCESS = "CELLID";
    public static final Parcelable.Creator<CameraMetadataNative> CREATOR = new Parcelable.Creator<CameraMetadataNative>(){

        @Override
        public CameraMetadataNative createFromParcel(Parcel parcel) {
            CameraMetadataNative cameraMetadataNative = new CameraMetadataNative();
            cameraMetadataNative.readFromParcel(parcel);
            return cameraMetadataNative;
        }

        public CameraMetadataNative[] newArray(int n) {
            return new CameraMetadataNative[n];
        }
    };
    private static final boolean DEBUG = false;
    private static final int FACE_LANDMARK_SIZE = 6;
    private static final String GPS_PROCESS = "GPS";
    public static final int NATIVE_JPEG_FORMAT = 33;
    public static final int NUM_TYPES = 6;
    private static final String TAG = "CameraMetadataJV";
    public static final int TYPE_BYTE = 0;
    public static final int TYPE_DOUBLE = 4;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_INT32 = 1;
    public static final int TYPE_INT64 = 3;
    public static final int TYPE_RATIONAL = 5;
    private static final HashMap<Key<?>, GetCommand> sGetCommandMap = new HashMap();
    private static final HashMap<Key<?>, SetCommand> sSetCommandMap;
    private int mCameraId = -1;
    private Size mDisplaySize = new Size(0, 0);
    @UnsupportedAppUsage
    private long mMetadataPtr;

    static {
        sGetCommandMap.put(CameraCharacteristics.SCALER_AVAILABLE_FORMATS.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getAvailableFormats();
            }
        });
        sGetCommandMap.put(CaptureResult.STATISTICS_FACES.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getFaces();
            }
        });
        sGetCommandMap.put(CaptureResult.STATISTICS_FACE_RECTANGLES.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getFaceRectangles();
            }
        });
        sGetCommandMap.put(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getStreamConfigurationMap();
            }
        });
        sGetCommandMap.put(CameraCharacteristics.SCALER_MANDATORY_STREAM_COMBINATIONS.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getMandatoryStreamCombinations();
            }
        });
        sGetCommandMap.put(CameraCharacteristics.CONTROL_MAX_REGIONS_AE.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getMaxRegions(key);
            }
        });
        sGetCommandMap.put(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getMaxRegions(key);
            }
        });
        sGetCommandMap.put(CameraCharacteristics.CONTROL_MAX_REGIONS_AF.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getMaxRegions(key);
            }
        });
        sGetCommandMap.put(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_RAW.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getMaxNumOutputs(key);
            }
        });
        sGetCommandMap.put(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getMaxNumOutputs(key);
            }
        });
        sGetCommandMap.put(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC_STALLING.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getMaxNumOutputs(key);
            }
        });
        sGetCommandMap.put(CaptureRequest.TONEMAP_CURVE.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getTonemapCurve();
            }
        });
        sGetCommandMap.put(CaptureResult.JPEG_GPS_LOCATION.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getGpsLocation();
            }
        });
        sGetCommandMap.put(CaptureResult.STATISTICS_LENS_SHADING_CORRECTION_MAP.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getLensShadingMap();
            }
        });
        sGetCommandMap.put(CaptureResult.STATISTICS_OIS_SAMPLES.getNativeKey(), new GetCommand(){

            @Override
            public <T> T getValue(CameraMetadataNative cameraMetadataNative, Key<T> key) {
                return (T)cameraMetadataNative.getOisSamples();
            }
        });
        sSetCommandMap = new HashMap();
        sSetCommandMap.put(CameraCharacteristics.SCALER_AVAILABLE_FORMATS.getNativeKey(), new SetCommand(){

            @Override
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setAvailableFormats((int[])t);
            }
        });
        sSetCommandMap.put(CaptureResult.STATISTICS_FACE_RECTANGLES.getNativeKey(), new SetCommand(){

            @Override
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setFaceRectangles((Rect[])t);
            }
        });
        sSetCommandMap.put(CaptureResult.STATISTICS_FACES.getNativeKey(), new SetCommand(){

            @Override
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setFaces((Face[])t);
            }
        });
        sSetCommandMap.put(CaptureRequest.TONEMAP_CURVE.getNativeKey(), new SetCommand(){

            @Override
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setTonemapCurve((TonemapCurve)t);
            }
        });
        sSetCommandMap.put(CaptureResult.JPEG_GPS_LOCATION.getNativeKey(), new SetCommand(){

            @Override
            public <T> void setValue(CameraMetadataNative cameraMetadataNative, T t) {
                cameraMetadataNative.setGpsLocation((Location)t);
            }
        });
        CameraMetadataNative.registerAllMarshalers();
    }

    public CameraMetadataNative() {
        this.mMetadataPtr = this.nativeAllocate();
        if (this.mMetadataPtr != 0L) {
            return;
        }
        throw new OutOfMemoryError("Failed to allocate native CameraMetadata");
    }

    public CameraMetadataNative(CameraMetadataNative cameraMetadataNative) {
        this.mMetadataPtr = this.nativeAllocateCopy(cameraMetadataNative);
        if (this.mMetadataPtr != 0L) {
            return;
        }
        throw new OutOfMemoryError("Failed to allocate native CameraMetadata");
    }

    private static boolean areValuesAllNull(Object ... arrobject) {
        int n = arrobject.length;
        for (int i = 0; i < n; ++i) {
            if (arrobject[i] == null) continue;
            return false;
        }
        return true;
    }

    private void close() {
        this.nativeClose();
        this.mMetadataPtr = 0L;
    }

    private int[] getAvailableFormats() {
        int[] arrn = this.getBase(CameraCharacteristics.SCALER_AVAILABLE_FORMATS);
        if (arrn != null) {
            for (int i = 0; i < arrn.length; ++i) {
                if (arrn[i] != 33) continue;
                arrn[i] = 256;
            }
        }
        return arrn;
    }

    private <T> T getBase(CameraCharacteristics.Key<T> key) {
        return this.getBase(key.getNativeKey());
    }

    private <T> T getBase(CaptureRequest.Key<T> key) {
        return this.getBase(key.getNativeKey());
    }

    private <T> T getBase(CaptureResult.Key<T> key) {
        return this.getBase(key.getNativeKey());
    }

    private <T> T getBase(Key<T> key) {
        byte[] arrby;
        int n = this.nativeGetTagFromKeyLocal(key.getName());
        byte[] arrby2 = arrby = this.readValues(n);
        if (arrby == null) {
            if (key.mFallbackName == null) {
                return null;
            }
            n = this.nativeGetTagFromKeyLocal(key.mFallbackName);
            arrby2 = arrby = this.readValues(n);
            if (arrby == null) {
                return null;
            }
        }
        return CameraMetadataNative.getMarshalerForKey(key, this.nativeGetTypeFromTagLocal(n)).unmarshal(ByteBuffer.wrap(arrby2).order(ByteOrder.nativeOrder()));
    }

    private Rect[] getFaceRectangles() {
        Rect[] arrrect = this.getBase(CaptureResult.STATISTICS_FACE_RECTANGLES);
        if (arrrect == null) {
            return null;
        }
        Rect[] arrrect2 = new Rect[arrrect.length];
        for (int i = 0; i < arrrect.length; ++i) {
            arrrect2[i] = new Rect(arrrect[i].left, arrrect[i].top, arrrect[i].right - arrrect[i].left, arrrect[i].bottom - arrrect[i].top);
        }
        return arrrect2;
    }

    private Face[] getFaces() {
        Serializable serializable;
        Object object = this.get(CaptureResult.STATISTICS_FACE_DETECT_MODE);
        byte[] arrby = this.get(CaptureResult.STATISTICS_FACE_SCORES);
        Rect[] arrrect = this.get(CaptureResult.STATISTICS_FACE_RECTANGLES);
        int[] arrn = this.get(CaptureResult.STATISTICS_FACE_IDS);
        int[] arrn2 = this.get(CaptureResult.STATISTICS_FACE_LANDMARKS);
        if (CameraMetadataNative.areValuesAllNull(object, arrby, arrrect, arrn, arrn2)) {
            return null;
        }
        if (object == null) {
            Log.w(TAG, "Face detect mode metadata is null, assuming the mode is SIMPLE");
            serializable = Integer.valueOf(1);
        } else if (object.intValue() > 2) {
            serializable = Integer.valueOf(2);
        } else {
            if (object.intValue() == 0) {
                return new Face[0];
            }
            serializable = object;
            if (object.intValue() != 1) {
                serializable = object;
                if (object.intValue() != 2) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Unknown face detect mode: ");
                    ((StringBuilder)serializable).append(object);
                    Log.w(TAG, ((StringBuilder)serializable).toString());
                    return new Face[0];
                }
            }
        }
        if (arrby != null && arrrect != null) {
            if (arrby.length != arrrect.length) {
                Log.w(TAG, String.format("Face score size(%d) doesn match face rectangle size(%d)!", arrby.length, arrrect.length));
            }
            int n = Math.min(arrby.length, arrrect.length);
            object = serializable;
            int n2 = n;
            if ((Integer)serializable == 2) {
                if (arrn != null && arrn2 != null) {
                    if (arrn.length != n || arrn2.length != n * 6) {
                        Log.w(TAG, String.format("Face id size(%d), or face landmark size(%d) don'tmatch face number(%d)!", arrn.length, arrn2.length * 6, n));
                    }
                    n2 = Math.min(Math.min(n, arrn.length), arrn2.length / 6);
                    object = serializable;
                } else {
                    Log.w(TAG, "Expect face ids and landmarks to be non-null for FULL mode,fallback to SIMPLE mode");
                    object = 1;
                    n2 = n;
                }
            }
            serializable = new ArrayList();
            if (object.intValue() == 1) {
                for (n = 0; n < n2; ++n) {
                    if (arrby[n] > 100 || arrby[n] < 1) continue;
                    ((ArrayList)serializable).add(new Face(arrrect[n], arrby[n]));
                }
            } else {
                for (n = 0; n < n2; ++n) {
                    if (arrby[n] > 100 || arrby[n] < 1 || arrn[n] < 0) continue;
                    object = new Point(arrn2[n * 6], arrn2[n * 6 + 1]);
                    Point point = new Point(arrn2[n * 6 + 2], arrn2[n * 6 + 3]);
                    Point point2 = new Point(arrn2[n * 6 + 4], arrn2[n * 6 + 5]);
                    ((ArrayList)serializable).add(new Face(arrrect[n], arrby[n], arrn[n], (Point)object, point, point2));
                }
            }
            object = new Face[((ArrayList)serializable).size()];
            ((ArrayList)serializable).toArray((T[])object);
            return object;
        }
        Log.w(TAG, "Expect face scores and rectangles to be non-null");
        return new Face[0];
    }

    private Location getGpsLocation() {
        Object object = this.get(CaptureResult.JPEG_GPS_PROCESSING_METHOD);
        double[] arrd = this.get(CaptureResult.JPEG_GPS_COORDINATES);
        Long l = this.get(CaptureResult.JPEG_GPS_TIMESTAMP);
        if (CameraMetadataNative.areValuesAllNull(object, arrd, l)) {
            return null;
        }
        object = new Location(CameraMetadataNative.translateProcessToLocationProvider((String)object));
        if (l != null) {
            ((Location)object).setTime(l * 1000L);
        } else {
            Log.w(TAG, "getGpsLocation - No timestamp for GPS location.");
        }
        if (arrd != null) {
            ((Location)object).setLatitude(arrd[0]);
            ((Location)object).setLongitude(arrd[1]);
            ((Location)object).setAltitude(arrd[2]);
        } else {
            Log.w(TAG, "getGpsLocation - No coordinates for GPS location");
        }
        return object;
    }

    private LensShadingMap getLensShadingMap() {
        float[] arrf = this.getBase(CaptureResult.STATISTICS_LENS_SHADING_MAP);
        Size size = this.get(CameraCharacteristics.LENS_INFO_SHADING_MAP_SIZE);
        if (arrf == null) {
            return null;
        }
        if (size == null) {
            Log.w(TAG, "getLensShadingMap - Lens shading map size was null.");
            return null;
        }
        return new LensShadingMap(arrf, size.getHeight(), size.getWidth());
    }

    private MandatoryStreamCombination[] getMandatoryStreamCombinations() {
        int n;
        Object object = this.getBase(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.ensureCapacity(((int[])object).length);
        int n2 = ((int[])object).length;
        for (n = 0; n < n2; ++n) {
            arrayList.add(new Integer(object[n]));
        }
        n = this.getBase(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        object = new MandatoryStreamCombination.Builder(this.mCameraId, n, this.mDisplaySize, arrayList, this.getStreamConfigurationMap()).getAvailableMandatoryStreamCombinations();
        if (object != null && !object.isEmpty()) {
            return object.toArray(new MandatoryStreamCombination[object.size()]);
        }
        return null;
    }

    private static <T> Marshaler<T> getMarshalerForKey(Key<T> key, int n) {
        return MarshalRegistry.getMarshaler(key.getTypeReference(), n);
    }

    private <T> Integer getMaxNumOutputs(Key<T> key) {
        Object object = this.getBase(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_STREAMS);
        if (object == null) {
            return null;
        }
        if (key.equals(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_RAW)) {
            return object[0];
        }
        if (key.equals(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC)) {
            return object[1];
        }
        if (key.equals(CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC_STALLING)) {
            return object[2];
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid key ");
        ((StringBuilder)object).append(key);
        throw new AssertionError((Object)((StringBuilder)object).toString());
    }

    private <T> Integer getMaxRegions(Key<T> key) {
        Object object = this.getBase(CameraCharacteristics.CONTROL_MAX_REGIONS);
        if (object == null) {
            return null;
        }
        if (key.equals(CameraCharacteristics.CONTROL_MAX_REGIONS_AE)) {
            return object[0];
        }
        if (key.equals(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB)) {
            return object[1];
        }
        if (key.equals(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)) {
            return object[2];
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid key ");
        ((StringBuilder)object).append(key);
        throw new AssertionError((Object)((StringBuilder)object).toString());
    }

    public static int getNativeType(int n, long l) {
        return CameraMetadataNative.nativeGetTypeFromTag(n, l);
    }

    private OisSample[] getOisSamples() {
        long[] arrl = this.getBase(CaptureResult.STATISTICS_OIS_TIMESTAMPS);
        float[] arrf = this.getBase(CaptureResult.STATISTICS_OIS_X_SHIFTS);
        float[] arrf2 = this.getBase(CaptureResult.STATISTICS_OIS_Y_SHIFTS);
        if (arrl == null) {
            if (arrf == null) {
                if (arrf2 == null) {
                    return null;
                }
                throw new AssertionError((Object)"timestamps is null but yShifts is not");
            }
            throw new AssertionError((Object)"timestamps is null but xShifts is not");
        }
        if (arrf != null) {
            if (arrf2 != null) {
                if (arrf.length == arrl.length) {
                    if (arrf2.length == arrl.length) {
                        OisSample[] arroisSample = new OisSample[arrl.length];
                        for (int i = 0; i < arrl.length; ++i) {
                            arroisSample[i] = new OisSample(arrl[i], arrf[i], arrf2[i]);
                        }
                        return arroisSample;
                    }
                    throw new AssertionError((Object)String.format("timestamps has %d entries but yShifts has %d", arrl.length, arrf2.length));
                }
                throw new AssertionError((Object)String.format("timestamps has %d entries but xShifts has %d", arrl.length, arrf.length));
            }
            throw new AssertionError((Object)"timestamps is not null but yShifts is");
        }
        throw new AssertionError((Object)"timestamps is not null but xShifts is");
    }

    private StreamConfigurationMap getStreamConfigurationMap() {
        return new StreamConfigurationMap(this.getBase(CameraCharacteristics.SCALER_AVAILABLE_STREAM_CONFIGURATIONS), this.getBase(CameraCharacteristics.SCALER_AVAILABLE_MIN_FRAME_DURATIONS), this.getBase(CameraCharacteristics.SCALER_AVAILABLE_STALL_DURATIONS), this.getBase(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STREAM_CONFIGURATIONS), this.getBase(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_MIN_FRAME_DURATIONS), this.getBase(CameraCharacteristics.DEPTH_AVAILABLE_DEPTH_STALL_DURATIONS), this.getBase(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STREAM_CONFIGURATIONS), this.getBase(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_MIN_FRAME_DURATIONS), this.getBase(CameraCharacteristics.DEPTH_AVAILABLE_DYNAMIC_DEPTH_STALL_DURATIONS), this.getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_STREAM_CONFIGURATIONS), this.getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_MIN_FRAME_DURATIONS), this.getBase(CameraCharacteristics.HEIC_AVAILABLE_HEIC_STALL_DURATIONS), this.getBase(CameraCharacteristics.CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS), this.getBase(CameraCharacteristics.SCALER_AVAILABLE_INPUT_OUTPUT_FORMATS_MAP), this.isBurstSupported());
    }

    public static int getTag(String string2) {
        return CameraMetadataNative.nativeGetTagFromKey(string2, Long.MAX_VALUE);
    }

    public static int getTag(String string2, long l) {
        return CameraMetadataNative.nativeGetTagFromKey(string2, l);
    }

    private <T> TonemapCurve getTonemapCurve() {
        float[] arrf = this.getBase(CaptureRequest.TONEMAP_CURVE_RED);
        float[] arrf2 = this.getBase(CaptureRequest.TONEMAP_CURVE_GREEN);
        float[] arrf3 = this.getBase(CaptureRequest.TONEMAP_CURVE_BLUE);
        if (CameraMetadataNative.areValuesAllNull(arrf, arrf2, arrf3)) {
            return null;
        }
        if (arrf != null && arrf2 != null && arrf3 != null) {
            return new TonemapCurve(arrf, arrf2, arrf3);
        }
        Log.w(TAG, "getTonemapCurve - missing tone curve components");
        return null;
    }

    private boolean isBurstSupported() {
        boolean bl;
        boolean bl2 = false;
        int[] arrn = this.getBase(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
        int n = arrn.length;
        int n2 = 0;
        do {
            bl = bl2;
            if (n2 >= n) break;
            if (arrn[n2] == 6) {
                bl = true;
                break;
            }
            ++n2;
        } while (true);
        return bl;
    }

    public static CameraMetadataNative move(CameraMetadataNative cameraMetadataNative) {
        CameraMetadataNative cameraMetadataNative2 = new CameraMetadataNative();
        cameraMetadataNative2.swap(cameraMetadataNative);
        return cameraMetadataNative2;
    }

    private native long nativeAllocate();

    private native long nativeAllocateCopy(CameraMetadataNative var1) throws NullPointerException;

    private synchronized native void nativeClose();

    private synchronized native void nativeDump() throws IOException;

    private synchronized native ArrayList nativeGetAllVendorKeys(Class var1);

    private synchronized native int nativeGetEntryCount();

    private static native int nativeGetTagFromKey(String var0, long var1) throws IllegalArgumentException;

    @UnsupportedAppUsage
    private synchronized native int nativeGetTagFromKeyLocal(String var1) throws IllegalArgumentException;

    private static native int nativeGetTypeFromTag(int var0, long var1) throws IllegalArgumentException;

    @UnsupportedAppUsage
    private synchronized native int nativeGetTypeFromTagLocal(int var1) throws IllegalArgumentException;

    private synchronized native boolean nativeIsEmpty();

    private synchronized native void nativeReadFromParcel(Parcel var1);

    @UnsupportedAppUsage
    private synchronized native byte[] nativeReadValues(int var1);

    private static native int nativeSetupGlobalVendorTagDescriptor();

    private synchronized native void nativeSwap(CameraMetadataNative var1) throws NullPointerException;

    private synchronized native void nativeWriteToParcel(Parcel var1);

    private synchronized native void nativeWriteValues(int var1, byte[] var2);

    /*
     * WARNING - void declaration
     */
    private void parseRecommendedConfigurations(RecommendedStreamConfiguration[] arrrecommendedStreamConfiguration, StreamConfigurationMap streamConfigurationMap, boolean bl, ArrayList<ArrayList<StreamConfiguration>> arrayList, ArrayList<ArrayList<StreamConfigurationDuration>> arrayList2, ArrayList<ArrayList<StreamConfigurationDuration>> arrayList3, boolean[] arrbl) {
        int n;
        arrayList.ensureCapacity(32);
        arrayList2.ensureCapacity(32);
        arrayList3.ensureCapacity(32);
        for (n = 0; n < 32; ++n) {
            arrayList.add(new ArrayList<E>());
            arrayList2.add(new ArrayList<E>());
            arrayList3.add(new ArrayList<E>());
        }
        for (RecommendedStreamConfiguration recommendedStreamConfiguration : arrrecommendedStreamConfiguration) {
            int n2 = recommendedStreamConfiguration.getWidth();
            int n3 = recommendedStreamConfiguration.getHeight();
            int n4 = recommendedStreamConfiguration.getFormat();
            n = bl ? StreamConfigurationMap.depthFormatToPublic(n4) : StreamConfigurationMap.imageFormatToPublic(n4);
            Object object = new Size(n2, n3);
            int n5 = recommendedStreamConfiguration.getUsecaseBitmap();
            if (!recommendedStreamConfiguration.isInput()) {
                StreamConfiguration streamConfiguration = new StreamConfiguration(n4, n2, n3, false);
                long l = streamConfigurationMap.getOutputMinFrameDuration(n, (Size)object);
                if (l > 0L) {
                    StreamConfigurationDuration streamConfigurationDuration = new StreamConfigurationDuration(n4, n2, n3, l);
                } else {
                    Object var11_14 = null;
                }
                int n6 = n;
                long l2 = streamConfigurationMap.getOutputStallDuration(n6, (Size)object);
                object = l2 > 0L ? new StreamConfigurationDuration(n4, n2, n3, l2) : null;
                for (n = 0; n < 32; ++n) {
                    if ((n5 & 1 << n) == 0) continue;
                    arrayList.get(n).add(streamConfiguration);
                    if (l > 0L) {
                        void var11_15;
                        arrayList2.get(n).add((StreamConfigurationDuration)var11_15);
                    }
                    if (l2 > 0L) {
                        arrayList3.get(n).add((StreamConfigurationDuration)object);
                    }
                    if (arrbl == null || arrbl[n] || n6 != 34) continue;
                    arrbl[n] = true;
                }
                continue;
            }
            if (n5 == 16) {
                arrayList.get(4).add(new StreamConfiguration(n4, n2, n3, true));
                continue;
            }
            throw new IllegalArgumentException("Recommended input stream configurations should only be advertised in the ZSL use case!");
        }
    }

    private static void registerAllMarshalers() {
        MarshalQueryable[] arrmarshalQueryable = new MarshalQueryable[21];
        MarshalQueryablePrimitive<T> marshalQueryablePrimitive = new MarshalQueryablePrimitive<T>();
        arrmarshalQueryable[0] = marshalQueryablePrimitive;
        arrmarshalQueryable[1] = new MarshalQueryableEnum<T>();
        arrmarshalQueryable[2] = new MarshalQueryableArray<T>();
        arrmarshalQueryable[3] = new MarshalQueryableBoolean();
        arrmarshalQueryable[4] = new MarshalQueryableNativeByteToInteger();
        arrmarshalQueryable[5] = new MarshalQueryableRect();
        arrmarshalQueryable[6] = new MarshalQueryableSize();
        arrmarshalQueryable[7] = new MarshalQueryableSizeF();
        arrmarshalQueryable[8] = new MarshalQueryableString();
        arrmarshalQueryable[9] = new MarshalQueryableReprocessFormatsMap();
        arrmarshalQueryable[10] = new MarshalQueryableRange<T>();
        arrmarshalQueryable[11] = new MarshalQueryablePair<T1, T2>();
        arrmarshalQueryable[12] = new MarshalQueryableMeteringRectangle();
        arrmarshalQueryable[13] = new MarshalQueryableColorSpaceTransform();
        arrmarshalQueryable[14] = new MarshalQueryableStreamConfiguration();
        arrmarshalQueryable[15] = new MarshalQueryableStreamConfigurationDuration();
        arrmarshalQueryable[16] = new MarshalQueryableRggbChannelVector();
        arrmarshalQueryable[17] = new MarshalQueryableBlackLevelPattern();
        arrmarshalQueryable[18] = new MarshalQueryableHighSpeedVideoConfiguration();
        arrmarshalQueryable[19] = new MarshalQueryableRecommendedStreamConfiguration();
        arrmarshalQueryable[20] = new MarshalQueryableParcelable<T>();
        int n = arrmarshalQueryable.length;
        for (int i = 0; i < n; ++i) {
            MarshalRegistry.registerMarshalQueryable(arrmarshalQueryable[i]);
        }
    }

    private boolean setAvailableFormats(int[] arrn) {
        if (arrn == null) {
            return false;
        }
        int[] arrn2 = new int[arrn.length];
        for (int i = 0; i < arrn.length; ++i) {
            arrn2[i] = arrn[i];
            if (arrn[i] != 256) continue;
            arrn2[i] = 33;
        }
        this.setBase(CameraCharacteristics.SCALER_AVAILABLE_FORMATS, arrn2);
        return true;
    }

    private <T> void setBase(CameraCharacteristics.Key<T> key, T t) {
        this.setBase(key.getNativeKey(), t);
    }

    private <T> void setBase(CaptureRequest.Key<T> key, T t) {
        this.setBase(key.getNativeKey(), t);
    }

    private <T> void setBase(CaptureResult.Key<T> key, T t) {
        this.setBase(key.getNativeKey(), t);
    }

    private <T> void setBase(Key<T> arrby, T t) {
        int n = this.nativeGetTagFromKeyLocal(arrby.getName());
        if (t == null) {
            this.writeValues(n, null);
            return;
        }
        Marshaler<T> marshaler = CameraMetadataNative.getMarshalerForKey(arrby, this.nativeGetTypeFromTagLocal(n));
        arrby = new byte[marshaler.calculateMarshalSize(t)];
        marshaler.marshal(t, ByteBuffer.wrap(arrby).order(ByteOrder.nativeOrder()));
        this.writeValues(n, arrby);
    }

    private boolean setFaceRectangles(Rect[] arrrect) {
        if (arrrect == null) {
            return false;
        }
        Rect[] arrrect2 = new Rect[arrrect.length];
        for (int i = 0; i < arrrect2.length; ++i) {
            arrrect2[i] = new Rect(arrrect[i].left, arrrect[i].top, arrrect[i].right + arrrect[i].left, arrrect[i].bottom + arrrect[i].top);
        }
        this.setBase(CaptureResult.STATISTICS_FACE_RECTANGLES, arrrect2);
        return true;
    }

    private boolean setFaces(Face[] arrface) {
        int n;
        int[] arrn;
        int n2;
        int n3 = 0;
        if (arrface == null) {
            return false;
        }
        int n4 = arrface.length;
        int n5 = arrface.length;
        boolean bl = true;
        for (n2 = 0; n2 < n5; ++n2) {
            arrn = arrface[n2];
            if (arrn == null) {
                n = n4 - 1;
                Log.w("CameraMetadataJV", "setFaces - null face detected, skipping");
            } else {
                n = n4;
                if (arrn.getId() == -1) {
                    bl = false;
                    n = n4;
                }
            }
            n4 = n;
        }
        Rect[] arrrect = new Rect[n4];
        byte[] arrby = new byte[n4];
        int[] arrn2 = null;
        arrn = null;
        if (bl) {
            arrn2 = new int[n4];
            arrn = new int[n4 * 6];
        }
        n4 = 0;
        n = arrface.length;
        for (n2 = n3; n2 < n; ++n2) {
            Face face = arrface[n2];
            if (face == null) continue;
            arrrect[n4] = face.getBounds();
            arrby[n4] = (byte)face.getScore();
            if (bl) {
                arrn2[n4] = face.getId();
                n5 = 0 + 1;
                arrn[n4 * 6 + 0] = face.getLeftEyePosition().x;
                n3 = n5 + 1;
                arrn[n4 * 6 + n5] = face.getLeftEyePosition().y;
                n5 = n3 + 1;
                arrn[n4 * 6 + n3] = face.getRightEyePosition().x;
                n3 = n5 + 1;
                arrn[n4 * 6 + n5] = face.getRightEyePosition().y;
                n5 = n3 + 1;
                arrn[n4 * 6 + n3] = face.getMouthPosition().x;
                arrn[n4 * 6 + n5] = face.getMouthPosition().y;
            }
            ++n4;
        }
        this.set(CaptureResult.STATISTICS_FACE_RECTANGLES, arrrect);
        this.set(CaptureResult.STATISTICS_FACE_IDS, arrn2);
        this.set(CaptureResult.STATISTICS_FACE_LANDMARKS, arrn);
        this.set(CaptureResult.STATISTICS_FACE_SCORES, arrby);
        return true;
    }

    private boolean setGpsLocation(Location location) {
        if (location == null) {
            return false;
        }
        double d = location.getLatitude();
        double d2 = location.getLongitude();
        double d3 = location.getAltitude();
        String string2 = CameraMetadataNative.translateLocationProviderToProcess(location.getProvider());
        long l = location.getTime() / 1000L;
        this.set(CaptureRequest.JPEG_GPS_TIMESTAMP, Long.valueOf(l));
        this.set(CaptureRequest.JPEG_GPS_COORDINATES, new double[]{d, d2, d3});
        if (string2 == null) {
            Log.w("CameraMetadataJV", "setGpsLocation - No process method, Location is not from a GPS or NETWORKprovider");
        } else {
            this.setBase(CaptureRequest.JPEG_GPS_PROCESSING_METHOD, string2);
        }
        return true;
    }

    private <T> boolean setTonemapCurve(TonemapCurve tonemapCurve) {
        if (tonemapCurve == null) {
            return false;
        }
        float[][] arrarrf = new float[3][];
        for (int i = 0; i <= 2; ++i) {
            arrarrf[i] = new float[tonemapCurve.getPointCount(i) * 2];
            tonemapCurve.copyColorCurve(i, arrarrf[i], 0);
        }
        this.setBase(CaptureRequest.TONEMAP_CURVE_RED, arrarrf[0]);
        this.setBase(CaptureRequest.TONEMAP_CURVE_GREEN, arrarrf[1]);
        this.setBase(CaptureRequest.TONEMAP_CURVE_BLUE, arrarrf[2]);
        return true;
    }

    public static void setupGlobalVendorTagDescriptor() throws ServiceSpecificException {
        int n = CameraMetadataNative.nativeSetupGlobalVendorTagDescriptor();
        if (n == 0) {
            return;
        }
        throw new ServiceSpecificException(n, "Failure to set up global vendor tags");
    }

    private static String translateLocationProviderToProcess(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = -1;
        int n2 = string2.hashCode();
        if (n2 != 102570) {
            if (n2 == 1843485230 && string2.equals("network")) {
                n = 1;
            }
        } else if (string2.equals("gps")) {
            n = 0;
        }
        if (n != 0) {
            if (n != 1) {
                return null;
            }
            return "CELLID";
        }
        return "GPS";
    }

    private static String translateProcessToLocationProvider(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = -1;
        int n2 = string2.hashCode();
        if (n2 != 70794) {
            if (n2 == 1984215549 && string2.equals("CELLID")) {
                n = 1;
            }
        } else if (string2.equals("GPS")) {
            n = 0;
        }
        if (n != 0) {
            if (n != 1) {
                return null;
            }
            return "network";
        }
        return "gps";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dumpToLog() {
        try {
            this.nativeDump();
        }
        catch (IOException iOException) {
            Log.wtf("CameraMetadataJV", "Dump logging failed", iOException);
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public <T> T get(CameraCharacteristics.Key<T> key) {
        return this.get(key.getNativeKey());
    }

    public <T> T get(CaptureRequest.Key<T> key) {
        return this.get(key.getNativeKey());
    }

    public <T> T get(CaptureResult.Key<T> key) {
        return this.get(key.getNativeKey());
    }

    public <T> T get(Key<T> key) {
        Preconditions.checkNotNull(key, "key must not be null");
        GetCommand getCommand = sGetCommandMap.get(key);
        if (getCommand != null) {
            return getCommand.getValue(this, key);
        }
        return this.getBase(key);
    }

    public <K> ArrayList<K> getAllVendorKeys(Class<K> class_) {
        if (class_ != null) {
            return this.nativeGetAllVendorKeys(class_);
        }
        throw new NullPointerException();
    }

    public int getEntryCount() {
        return this.nativeGetEntryCount();
    }

    public ArrayList<RecommendedStreamConfigurationMap> getRecommendedStreamConfigurations() {
        RecommendedStreamConfiguration[] arrrecommendedStreamConfiguration = this.getBase(CameraCharacteristics.SCALER_AVAILABLE_RECOMMENDED_STREAM_CONFIGURATIONS);
        RecommendedStreamConfiguration[] arrrecommendedStreamConfiguration2 = this.getBase(CameraCharacteristics.DEPTH_AVAILABLE_RECOMMENDED_DEPTH_STREAM_CONFIGURATIONS);
        if (arrrecommendedStreamConfiguration == null && arrrecommendedStreamConfiguration2 == null) {
            return null;
        }
        Object object = this.getStreamConfigurationMap();
        ArrayList<RecommendedStreamConfigurationMap> arrayList = new ArrayList<RecommendedStreamConfigurationMap>();
        ArrayList<ArrayList<StreamConfiguration>> arrayList2 = new ArrayList<ArrayList<StreamConfiguration>>();
        Object object2 = new ArrayList<ArrayList<StreamConfigurationDuration>>();
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList3 = new ArrayList<ArrayList<StreamConfigurationDuration>>();
        boolean[] arrbl = new boolean[32];
        if (arrrecommendedStreamConfiguration != null) {
            try {
                this.parseRecommendedConfigurations(arrrecommendedStreamConfiguration, (StreamConfigurationMap)object, false, arrayList2, (ArrayList<ArrayList<StreamConfigurationDuration>>)object2, arrayList3, arrbl);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.e("CameraMetadataJV", "Failed parsing the recommended stream configurations!");
                return null;
            }
        }
        ArrayList<ArrayList<StreamConfiguration>> arrayList4 = new ArrayList<ArrayList<StreamConfiguration>>();
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList5 = new ArrayList<ArrayList<StreamConfigurationDuration>>();
        ArrayList<ArrayList<StreamConfigurationDuration>> arrayList6 = new ArrayList<ArrayList<StreamConfigurationDuration>>();
        if (arrrecommendedStreamConfiguration2 != null) {
            try {
                this.parseRecommendedConfigurations(arrrecommendedStreamConfiguration2, (StreamConfigurationMap)object, true, arrayList4, arrayList5, arrayList6, null);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.e("CameraMetadataJV", "Failed parsing the recommended depth stream configurations!");
                return null;
            }
        }
        ReprocessFormatsMap reprocessFormatsMap = this.getBase(CameraCharacteristics.SCALER_AVAILABLE_RECOMMENDED_INPUT_OUTPUT_FORMATS_MAP);
        HighSpeedVideoConfiguration[] arrhighSpeedVideoConfiguration = this.getBase(CameraCharacteristics.CONTROL_AVAILABLE_HIGH_SPEED_VIDEO_CONFIGURATIONS);
        boolean bl = this.isBurstSupported();
        arrayList.ensureCapacity(32);
        for (int i = 0; i < 32; ++i) {
            block15 : {
                block10 : {
                    block14 : {
                        StreamConfigurationData streamConfigurationData;
                        block11 : {
                            block12 : {
                                block13 : {
                                    streamConfigurationData = new StreamConfigurationData();
                                    if (arrrecommendedStreamConfiguration != null) {
                                        this.initializeStreamConfigurationData(arrayList2.get(i), (ArrayList<StreamConfigurationDuration>)((ArrayList)object2).get(i), arrayList3.get(i), streamConfigurationData);
                                    }
                                    object = object2;
                                    object2 = new StreamConfigurationData();
                                    if (arrrecommendedStreamConfiguration2 != null) {
                                        this.initializeStreamConfigurationData(arrayList4.get(i), arrayList5.get(i), arrayList6.get(i), (StreamConfigurationData)object2);
                                    }
                                    if ((streamConfigurationData.streamConfigurationArray == null || streamConfigurationData.streamConfigurationArray.length == 0) && (((StreamConfigurationData)object2).streamConfigurationArray == null || ((StreamConfigurationData)object2).streamConfigurationArray.length == 0)) break block10;
                                    if (i == 0) break block11;
                                    if (i == 1) break block12;
                                    if (i == 2) break block11;
                                    if (i == 4) break block13;
                                    if (i == 5 || i == 6) break block11;
                                    object2 = new StreamConfigurationMap(streamConfigurationData.streamConfigurationArray, streamConfigurationData.minDurationArray, streamConfigurationData.stallDurationArray, ((StreamConfigurationData)object2).streamConfigurationArray, ((StreamConfigurationData)object2).minDurationArray, ((StreamConfigurationData)object2).stallDurationArray, null, null, null, null, null, null, null, null, bl, arrbl[i]);
                                    break block14;
                                }
                                object2 = new StreamConfigurationMap(streamConfigurationData.streamConfigurationArray, streamConfigurationData.minDurationArray, streamConfigurationData.stallDurationArray, ((StreamConfigurationData)object2).streamConfigurationArray, ((StreamConfigurationData)object2).minDurationArray, ((StreamConfigurationData)object2).stallDurationArray, null, null, null, null, null, null, null, reprocessFormatsMap, bl, arrbl[i]);
                                break block14;
                            }
                            object2 = new StreamConfigurationMap(streamConfigurationData.streamConfigurationArray, streamConfigurationData.minDurationArray, streamConfigurationData.stallDurationArray, null, null, null, null, null, null, null, null, null, arrhighSpeedVideoConfiguration, null, bl, arrbl[i]);
                            break block14;
                        }
                        object2 = new StreamConfigurationMap(streamConfigurationData.streamConfigurationArray, streamConfigurationData.minDurationArray, streamConfigurationData.stallDurationArray, null, null, null, null, null, null, null, null, null, null, null, bl, arrbl[i]);
                    }
                    arrayList.add(new RecommendedStreamConfigurationMap((StreamConfigurationMap)object2, i, arrbl[i]));
                    break block15;
                }
                arrayList.add(null);
            }
            object2 = object;
        }
        return arrayList;
    }

    public void initializeStreamConfigurationData(ArrayList<StreamConfiguration> arrayList, ArrayList<StreamConfigurationDuration> arrayList2, ArrayList<StreamConfigurationDuration> arrayList3, StreamConfigurationData streamConfigurationData) {
        if (streamConfigurationData != null && arrayList != null) {
            streamConfigurationData.streamConfigurationArray = new StreamConfiguration[arrayList.size()];
            streamConfigurationData.streamConfigurationArray = arrayList.toArray(streamConfigurationData.streamConfigurationArray);
            if (arrayList2 != null && !arrayList2.isEmpty()) {
                streamConfigurationData.minDurationArray = new StreamConfigurationDuration[arrayList2.size()];
                streamConfigurationData.minDurationArray = arrayList2.toArray(streamConfigurationData.minDurationArray);
            } else {
                streamConfigurationData.minDurationArray = new StreamConfigurationDuration[0];
            }
            if (arrayList3 != null && !arrayList3.isEmpty()) {
                streamConfigurationData.stallDurationArray = new StreamConfigurationDuration[arrayList3.size()];
                streamConfigurationData.stallDurationArray = arrayList3.toArray(streamConfigurationData.stallDurationArray);
            } else {
                streamConfigurationData.stallDurationArray = new StreamConfigurationDuration[0];
            }
            return;
        }
    }

    public boolean isEmpty() {
        return this.nativeIsEmpty();
    }

    public void readFromParcel(Parcel parcel) {
        this.nativeReadFromParcel(parcel);
    }

    public byte[] readValues(int n) {
        return this.nativeReadValues(n);
    }

    public <T> void set(CameraCharacteristics.Key<T> key, T t) {
        this.set(key.getNativeKey(), t);
    }

    public <T> void set(CaptureRequest.Key<T> key, T t) {
        this.set(key.getNativeKey(), t);
    }

    public <T> void set(CaptureResult.Key<T> key, T t) {
        this.set(key.getNativeKey(), t);
    }

    public <T> void set(Key<T> key, T t) {
        SetCommand setCommand = sSetCommandMap.get(key);
        if (setCommand != null) {
            setCommand.setValue(this, t);
            return;
        }
        this.setBase(key, t);
    }

    public void setCameraId(int n) {
        this.mCameraId = n;
    }

    public void setDisplaySize(Size size) {
        this.mDisplaySize = size;
    }

    public void swap(CameraMetadataNative cameraMetadataNative) {
        this.nativeSwap(cameraMetadataNative);
        this.mCameraId = cameraMetadataNative.mCameraId;
        this.mDisplaySize = cameraMetadataNative.mDisplaySize;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.nativeWriteToParcel(parcel);
    }

    public void writeValues(int n, byte[] arrby) {
        this.nativeWriteValues(n, arrby);
    }

    public static class Key<T> {
        private final String mFallbackName;
        private boolean mHasTag;
        private final int mHash;
        private final String mName;
        private int mTag;
        private final Class<T> mType;
        private final TypeReference<T> mTypeReference;
        private long mVendorId = Long.MAX_VALUE;

        public Key(String string2, TypeReference<T> typeReference) {
            if (string2 != null) {
                if (typeReference != null) {
                    this.mName = string2;
                    this.mFallbackName = null;
                    this.mType = typeReference.getRawType();
                    this.mTypeReference = typeReference;
                    this.mHash = this.mName.hashCode() ^ this.mTypeReference.hashCode();
                    return;
                }
                throw new NullPointerException("TypeReference needs to be non-null");
            }
            throw new NullPointerException("Key needs a valid name");
        }

        public Key(String string2, Class<T> class_) {
            if (string2 != null) {
                if (class_ != null) {
                    this.mName = string2;
                    this.mFallbackName = null;
                    this.mType = class_;
                    this.mTypeReference = TypeReference.createSpecializedTypeReference(class_);
                    this.mHash = this.mName.hashCode() ^ this.mTypeReference.hashCode();
                    return;
                }
                throw new NullPointerException("Type needs to be non-null");
            }
            throw new NullPointerException("Key needs a valid name");
        }

        public Key(String string2, Class<T> class_, long l) {
            if (string2 != null) {
                if (class_ != null) {
                    this.mName = string2;
                    this.mFallbackName = null;
                    this.mType = class_;
                    this.mVendorId = l;
                    this.mTypeReference = TypeReference.createSpecializedTypeReference(class_);
                    this.mHash = this.mName.hashCode() ^ this.mTypeReference.hashCode();
                    return;
                }
                throw new NullPointerException("Type needs to be non-null");
            }
            throw new NullPointerException("Key needs a valid name");
        }

        public Key(String string2, String string3, Class<T> class_) {
            if (string2 != null) {
                if (class_ != null) {
                    this.mName = string2;
                    this.mFallbackName = string3;
                    this.mType = class_;
                    this.mTypeReference = TypeReference.createSpecializedTypeReference(class_);
                    this.mHash = this.mName.hashCode() ^ this.mTypeReference.hashCode();
                    return;
                }
                throw new NullPointerException("Type needs to be non-null");
            }
            throw new NullPointerException("Key needs a valid name");
        }

        public final boolean equals(Object key) {
            block5 : {
                block10 : {
                    boolean bl;
                    block7 : {
                        block9 : {
                            block8 : {
                                block6 : {
                                    bl = true;
                                    if (this == key) {
                                        return true;
                                    }
                                    if (key == null || this.hashCode() != ((Object)key).hashCode()) break block5;
                                    if (!(key instanceof CaptureResult.Key)) break block6;
                                    key = ((CaptureResult.Key)((Object)key)).getNativeKey();
                                    break block7;
                                }
                                if (!(key instanceof CaptureRequest.Key)) break block8;
                                key = ((CaptureRequest.Key)((Object)key)).getNativeKey();
                                break block7;
                            }
                            if (!(key instanceof CameraCharacteristics.Key)) break block9;
                            key = ((CameraCharacteristics.Key)((Object)key)).getNativeKey();
                            break block7;
                        }
                        if (!(key instanceof Key)) break block10;
                    }
                    if (!this.mName.equals(key.mName) || !this.mTypeReference.equals(key.mTypeReference)) {
                        bl = false;
                    }
                    return bl;
                }
                return false;
            }
            return false;
        }

        public final String getName() {
            return this.mName;
        }

        @UnsupportedAppUsage
        public final int getTag() {
            if (!this.mHasTag) {
                this.mTag = CameraMetadataNative.getTag(this.mName, this.mVendorId);
                this.mHasTag = true;
            }
            return this.mTag;
        }

        public final Class<T> getType() {
            return this.mType;
        }

        public final TypeReference<T> getTypeReference() {
            return this.mTypeReference;
        }

        public final long getVendorId() {
            return this.mVendorId;
        }

        public final int hashCode() {
            return this.mHash;
        }
    }

    private class StreamConfigurationData {
        StreamConfigurationDuration[] minDurationArray = null;
        StreamConfigurationDuration[] stallDurationArray = null;
        StreamConfiguration[] streamConfigurationArray = null;

        private StreamConfigurationData() {
        }
    }

}

