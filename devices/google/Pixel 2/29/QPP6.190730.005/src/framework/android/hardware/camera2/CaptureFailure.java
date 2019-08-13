/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.hardware.camera2.CaptureRequest;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CaptureFailure {
    public static final int REASON_ERROR = 0;
    public static final int REASON_FLUSHED = 1;
    private final boolean mDropped;
    private final String mErrorPhysicalCameraId;
    private final long mFrameNumber;
    private final int mReason;
    private final CaptureRequest mRequest;
    private final int mSequenceId;

    public CaptureFailure(CaptureRequest captureRequest, int n, boolean bl, int n2, long l, String string2) {
        this.mRequest = captureRequest;
        this.mReason = n;
        this.mDropped = bl;
        this.mSequenceId = n2;
        this.mFrameNumber = l;
        this.mErrorPhysicalCameraId = string2;
    }

    public long getFrameNumber() {
        return this.mFrameNumber;
    }

    public String getPhysicalCameraId() {
        return this.mErrorPhysicalCameraId;
    }

    public int getReason() {
        return this.mReason;
    }

    public CaptureRequest getRequest() {
        return this.mRequest;
    }

    public int getSequenceId() {
        return this.mSequenceId;
    }

    public boolean wasImageCaptured() {
        return this.mDropped ^ true;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FailureReason {
    }

}

