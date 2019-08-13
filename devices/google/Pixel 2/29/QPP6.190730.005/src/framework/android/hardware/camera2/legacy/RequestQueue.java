/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.legacy.BurstHolder;
import android.hardware.camera2.utils.SubmitInfo;
import android.util.Log;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;

public class RequestQueue {
    private static final long INVALID_FRAME = -1L;
    private static final String TAG = "RequestQueue";
    private long mCurrentFrameNumber = 0L;
    private long mCurrentRepeatingFrameNumber = -1L;
    private int mCurrentRequestId = 0;
    private final List<Long> mJpegSurfaceIds;
    private BurstHolder mRepeatingRequest = null;
    private final ArrayDeque<BurstHolder> mRequestQueue = new ArrayDeque();

    public RequestQueue(List<Long> list) {
        this.mJpegSurfaceIds = list;
    }

    private long calculateLastFrame(int n) {
        long l = this.mCurrentFrameNumber;
        for (BurstHolder burstHolder : this.mRequestQueue) {
            l += (long)burstHolder.getNumberOfRequests();
            if (burstHolder.getRequestId() != n) continue;
            return l - 1L;
        }
        throw new IllegalStateException("At least one request must be in the queue to calculate frame number");
    }

    public RequestQueueEntry getNext() {
        synchronized (this) {
            Object object;
            BurstHolder burstHolder;
            boolean bl;
            block10 : {
                block9 : {
                    block8 : {
                        object = this.mRequestQueue.poll();
                        if (object == null) break block8;
                        if (this.mRequestQueue.size() != 0) break block8;
                        bl = true;
                        break block9;
                    }
                    bl = false;
                }
                burstHolder = object;
                if (object == null) {
                    burstHolder = object;
                    if (this.mRepeatingRequest == null) break block10;
                    burstHolder = this.mRepeatingRequest;
                    this.mCurrentRepeatingFrameNumber = this.mCurrentFrameNumber + (long)burstHolder.getNumberOfRequests();
                }
            }
            if (burstHolder == null) {
                return null;
            }
            object = new RequestQueueEntry(burstHolder, this.mCurrentFrameNumber, bl);
            this.mCurrentFrameNumber += (long)burstHolder.getNumberOfRequests();
            return object;
        }
    }

    public long stopRepeating() {
        synchronized (this) {
            block4 : {
                if (this.mRepeatingRequest != null) break block4;
                Log.e(TAG, "cancel failed: no repeating request exists.");
                return -1L;
            }
            long l = this.stopRepeating(this.mRepeatingRequest.getRequestId());
            return l;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long stopRepeating(int n) {
        synchronized (this) {
            long l = -1L;
            if (this.mRepeatingRequest != null && this.mRepeatingRequest.getRequestId() == n) {
                this.mRepeatingRequest = null;
                l = this.mCurrentRepeatingFrameNumber == -1L ? -1L : this.mCurrentRepeatingFrameNumber - 1L;
                this.mCurrentRepeatingFrameNumber = -1L;
                Log.i(TAG, "Repeating capture request cancelled.");
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("cancel failed: no repeating request exists for request id: ");
                stringBuilder.append(n);
                Log.e(TAG, stringBuilder.toString());
            }
            return l;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SubmitInfo submit(CaptureRequest[] arrcaptureRequest, boolean bl) {
        synchronized (this) {
            int n = this.mCurrentRequestId;
            this.mCurrentRequestId = n + 1;
            BurstHolder burstHolder = new BurstHolder(n, bl, arrcaptureRequest, this.mJpegSurfaceIds);
            long l = -1L;
            if (burstHolder.isRepeating()) {
                Log.i(TAG, "Repeating capture request set.");
                if (this.mRepeatingRequest != null) {
                    l = this.mCurrentRepeatingFrameNumber == -1L ? -1L : this.mCurrentRepeatingFrameNumber - 1L;
                }
                this.mCurrentRepeatingFrameNumber = -1L;
                this.mRepeatingRequest = burstHolder;
                return new SubmitInfo(n, l);
            } else {
                this.mRequestQueue.offer(burstHolder);
                l = this.calculateLastFrame(burstHolder.getRequestId());
            }
            return new SubmitInfo(n, l);
        }
    }

    public final class RequestQueueEntry {
        private final BurstHolder mBurstHolder;
        private final Long mFrameNumber;
        private final boolean mQueueEmpty;

        public RequestQueueEntry(BurstHolder burstHolder, Long l, boolean bl) {
            this.mBurstHolder = burstHolder;
            this.mFrameNumber = l;
            this.mQueueEmpty = bl;
        }

        public BurstHolder getBurstHolder() {
            return this.mBurstHolder;
        }

        public Long getFrameNumber() {
            return this.mFrameNumber;
        }

        public boolean isQueueEmpty() {
            return this.mQueueEmpty;
        }
    }

}

