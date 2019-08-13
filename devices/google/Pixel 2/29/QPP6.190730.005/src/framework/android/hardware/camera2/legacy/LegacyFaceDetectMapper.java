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
import android.hardware.camera2.legacy.LegacyRequest;
import android.hardware.camera2.legacy.ParameterUtils;
import android.hardware.camera2.params.Face;
import android.hardware.camera2.utils.ParamsUtils;
import android.util.Log;
import android.util.Size;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;

public class LegacyFaceDetectMapper {
    private static final boolean DEBUG = false;
    private static String TAG = "LegacyFaceDetectMapper";
    private final Camera mCamera;
    private boolean mFaceDetectEnabled = false;
    private boolean mFaceDetectReporting = false;
    private boolean mFaceDetectScenePriority = false;
    private final boolean mFaceDetectSupported;
    private Camera.Face[] mFaces;
    private Camera.Face[] mFacesPrev;
    private final Object mLock = new Object();

    public LegacyFaceDetectMapper(Camera camera, CameraCharacteristics cameraCharacteristics) {
        this.mCamera = Preconditions.checkNotNull(camera, "camera must not be null");
        Preconditions.checkNotNull(cameraCharacteristics, "characteristics must not be null");
        this.mFaceDetectSupported = ArrayUtils.contains(cameraCharacteristics.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES), 1);
        if (!this.mFaceDetectSupported) {
            return;
        }
        this.mCamera.setFaceDetectionListener(new Camera.FaceDetectionListener(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onFaceDetection(Camera.Face[] arrface, Camera object) {
                int n = arrface == null ? 0 : arrface.length;
                object = LegacyFaceDetectMapper.this.mLock;
                synchronized (object) {
                    if (LegacyFaceDetectMapper.this.mFaceDetectEnabled) {
                        LegacyFaceDetectMapper.this.mFaces = arrface;
                    } else if (n > 0) {
                        Log.d(TAG, "onFaceDetection - Ignored some incoming faces sinceface detection was disabled");
                    }
                    return;
                }
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void mapResultFaces(CameraMetadataNative cameraMetadataNative, LegacyRequest object) {
        Camera.Face[] arrface;
        Object object22;
        boolean bl;
        int n;
        Preconditions.checkNotNull(cameraMetadataNative, "result must not be null");
        Preconditions.checkNotNull(object, "legacyRequest must not be null");
        Object object3 = this.mLock;
        synchronized (object3) {
            n = this.mFaceDetectReporting ? 1 : 0;
            arrface = this.mFaceDetectReporting ? this.mFaces : null;
            bl = this.mFaceDetectScenePriority;
            object22 = this.mFacesPrev;
            this.mFacesPrev = arrface;
        }
        CameraCharacteristics cameraCharacteristics = ((LegacyRequest)object).characteristics;
        object22 = ((LegacyRequest)object).captureRequest;
        object3 = ((LegacyRequest)object).previewSize;
        Object object4 = ((LegacyRequest)object).parameters;
        object = cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        object3 = ParameterUtils.convertScalerCropRegion((Rect)object, ((CaptureRequest)object22).get(CaptureRequest.SCALER_CROP_REGION), (Size)object3, (Camera.Parameters)object4);
        object4 = new ArrayList();
        if (arrface != null) {
            for (Object object22 : arrface) {
                if (object22 != null) {
                    object4.add(ParameterUtils.convertFaceFromLegacy((Camera.Face)object22, (Rect)object, (ParameterUtils.ZoomData)object3));
                    continue;
                }
                Log.w(TAG, "mapResultFaces - read NULL face from camera1 device");
            }
        }
        cameraMetadataNative.set(CaptureResult.STATISTICS_FACES, object4.toArray(new Face[0]));
        cameraMetadataNative.set(CaptureResult.STATISTICS_FACE_DETECT_MODE, Integer.valueOf(n));
        if (bl) {
            cameraMetadataNative.set(CaptureResult.CONTROL_SCENE_MODE, Integer.valueOf(1));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void processFaceDetectMode(CaptureRequest object, Camera.Parameters object2) {
        Preconditions.checkNotNull(object, "captureRequest must not be null");
        CaptureRequest.Key<Integer> key = CaptureRequest.STATISTICS_FACE_DETECT_MODE;
        boolean bl = false;
        object2 = 0;
        int n = (Integer)ParamsUtils.getOrDefault((CaptureRequest)object, key, object2);
        if (n != 0 && !this.mFaceDetectSupported) {
            Log.w(TAG, "processFaceDetectMode - Ignoring statistics.faceDetectMode; face detection is not available");
            return;
        }
        key = CaptureRequest.CONTROL_SCENE_MODE;
        int n2 = (Integer)ParamsUtils.getOrDefault((CaptureRequest)object, key, object2);
        if (n2 == 1 && !this.mFaceDetectSupported) {
            Log.w(TAG, "processFaceDetectMode - ignoring control.sceneMode == FACE_PRIORITY; face detection is not available");
            return;
        }
        if (n != 0 && n != 1) {
            if (n != 2) {
                object = TAG;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("processFaceDetectMode - ignoring unknown statistics.faceDetectMode = ");
                ((StringBuilder)object2).append(n);
                Log.w((String)object, ((StringBuilder)object2).toString());
                return;
            }
            Log.w(TAG, "processFaceDetectMode - statistics.faceDetectMode == FULL unsupported, downgrading to SIMPLE");
        }
        boolean bl2 = n != 0 || n2 == 1;
        object2 = this.mLock;
        synchronized (object2) {
            if (bl2 != this.mFaceDetectEnabled) {
                if (bl2) {
                    this.mCamera.startFaceDetection();
                } else {
                    this.mCamera.stopFaceDetection();
                    this.mFaces = null;
                }
                this.mFaceDetectEnabled = bl2;
                bl2 = n2 == 1;
                this.mFaceDetectScenePriority = bl2;
                bl2 = bl;
                if (n != 0) {
                    bl2 = true;
                }
                this.mFaceDetectReporting = bl2;
            }
            return;
        }
    }

}

