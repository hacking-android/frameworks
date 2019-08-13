/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.legacy.LegacyCameraDevice;
import android.hardware.camera2.legacy.LegacyExceptionUtils;
import android.util.Log;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.util.Collection;

public class RequestHolder {
    private static final String TAG = "RequestHolder";
    private volatile boolean mFailed = false;
    private final long mFrameNumber;
    private final Collection<Long> mJpegSurfaceIds;
    private final int mNumJpegTargets;
    private final int mNumPreviewTargets;
    private boolean mOutputAbandoned = false;
    private final boolean mRepeating;
    private final CaptureRequest mRequest;
    private final int mRequestId;
    private final int mSubsequeceId;

    private RequestHolder(int n, int n2, CaptureRequest captureRequest, boolean bl, long l, int n3, int n4, Collection<Long> collection) {
        this.mRepeating = bl;
        this.mRequest = captureRequest;
        this.mRequestId = n;
        this.mSubsequeceId = n2;
        this.mFrameNumber = l;
        this.mNumJpegTargets = n3;
        this.mNumPreviewTargets = n4;
        this.mJpegSurfaceIds = collection;
    }

    public void failRequest() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Capture failed for request: ");
        stringBuilder.append(this.getRequestId());
        Log.w(TAG, stringBuilder.toString());
        this.mFailed = true;
    }

    public long getFrameNumber() {
        return this.mFrameNumber;
    }

    public Collection<Surface> getHolderTargets() {
        return this.getRequest().getTargets();
    }

    public CaptureRequest getRequest() {
        return this.mRequest;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public int getSubsequeceId() {
        return this.mSubsequeceId;
    }

    public boolean hasJpegTargets() {
        boolean bl = this.mNumJpegTargets > 0;
        return bl;
    }

    public boolean hasPreviewTargets() {
        boolean bl = this.mNumPreviewTargets > 0;
        return bl;
    }

    public boolean isOutputAbandoned() {
        return this.mOutputAbandoned;
    }

    public boolean isRepeating() {
        return this.mRepeating;
    }

    public boolean jpegType(Surface surface) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        return LegacyCameraDevice.containsSurfaceId(surface, this.mJpegSurfaceIds);
    }

    public int numJpegTargets() {
        return this.mNumJpegTargets;
    }

    public int numPreviewTargets() {
        return this.mNumPreviewTargets;
    }

    public boolean requestFailed() {
        return this.mFailed;
    }

    public void setOutputAbandoned() {
        this.mOutputAbandoned = true;
    }

    public static final class Builder {
        private final Collection<Long> mJpegSurfaceIds;
        private final int mNumJpegTargets;
        private final int mNumPreviewTargets;
        private final boolean mRepeating;
        private final CaptureRequest mRequest;
        private final int mRequestId;
        private final int mSubsequenceId;

        public Builder(int n, int n2, CaptureRequest captureRequest, boolean bl, Collection<Long> collection) {
            Preconditions.checkNotNull(captureRequest, "request must not be null");
            this.mRequestId = n;
            this.mSubsequenceId = n2;
            this.mRequest = captureRequest;
            this.mRepeating = bl;
            this.mJpegSurfaceIds = collection;
            this.mNumJpegTargets = this.numJpegTargets(this.mRequest);
            this.mNumPreviewTargets = this.numPreviewTargets(this.mRequest);
        }

        private boolean jpegType(Surface surface) throws LegacyExceptionUtils.BufferQueueAbandonedException {
            return LegacyCameraDevice.containsSurfaceId(surface, this.mJpegSurfaceIds);
        }

        private int numJpegTargets(CaptureRequest object) {
            int n = 0;
            for (Surface surface : ((CaptureRequest)object).getTargets()) {
                int n2;
                try {
                    boolean bl = this.jpegType(surface);
                    n2 = n;
                    if (bl) {
                        n2 = n + 1;
                    }
                }
                catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                    Log.d(RequestHolder.TAG, "Surface abandoned, skipping...", bufferQueueAbandonedException);
                    n2 = n;
                }
                n = n2;
            }
            return n;
        }

        private int numPreviewTargets(CaptureRequest object) {
            int n = 0;
            for (Surface surface : ((CaptureRequest)object).getTargets()) {
                try {
                    boolean bl = this.previewType(surface);
                    int n2 = n;
                    if (bl) {
                        n2 = n + 1;
                    }
                    n = n2;
                }
                catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                    Log.d(RequestHolder.TAG, "Surface abandoned, skipping...", bufferQueueAbandonedException);
                }
            }
            return n;
        }

        private boolean previewType(Surface surface) throws LegacyExceptionUtils.BufferQueueAbandonedException {
            return this.jpegType(surface) ^ true;
        }

        public RequestHolder build(long l) {
            return new RequestHolder(this.mRequestId, this.mSubsequenceId, this.mRequest, this.mRepeating, l, this.mNumJpegTargets, this.mNumPreviewTargets, this.mJpegSurfaceIds);
        }
    }

}

