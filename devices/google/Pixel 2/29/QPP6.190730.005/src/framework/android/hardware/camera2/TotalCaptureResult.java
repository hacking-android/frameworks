/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.CaptureResultExtras;
import android.hardware.camera2.impl.PhysicalCaptureResultInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TotalCaptureResult
extends CaptureResult {
    private final List<CaptureResult> mPartialResults;
    private final HashMap<String, CaptureResult> mPhysicalCaptureResults;
    private final int mSessionId;

    public TotalCaptureResult(CameraMetadataNative cameraMetadataNative, int n) {
        super(cameraMetadataNative, n);
        this.mPartialResults = new ArrayList<CaptureResult>();
        this.mSessionId = -1;
        this.mPhysicalCaptureResults = new HashMap();
    }

    /*
     * WARNING - void declaration
     */
    public TotalCaptureResult(CameraMetadataNative object, CaptureRequest captureRequest, CaptureResultExtras captureResultExtras, List<CaptureResult> object22, int n, PhysicalCaptureResultInfo[] arrphysicalCaptureResultInfo) {
        super((CameraMetadataNative)object, captureRequest, captureResultExtras);
        void var6_8;
        int n2;
        this.mPartialResults = object22 == null ? new ArrayList<CaptureResult>() : object22;
        this.mSessionId = n2;
        this.mPhysicalCaptureResults = new HashMap();
        for (void var4_6 : var6_8) {
            object = new CaptureResult(var4_6.getCameraMetadata(), captureRequest, captureResultExtras);
            this.mPhysicalCaptureResults.put(var4_6.getCameraId(), (CaptureResult)object);
        }
    }

    public List<CaptureResult> getPartialResults() {
        return Collections.unmodifiableList(this.mPartialResults);
    }

    public Map<String, CaptureResult> getPhysicalCameraResults() {
        return Collections.unmodifiableMap(this.mPhysicalCaptureResults);
    }

    public int getSessionId() {
        return this.mSessionId;
    }
}

