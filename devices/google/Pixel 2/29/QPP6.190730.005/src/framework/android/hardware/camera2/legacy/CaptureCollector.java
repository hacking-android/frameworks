/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.legacy.CameraDeviceState;
import android.hardware.camera2.legacy.LegacyExceptionUtils;
import android.hardware.camera2.legacy.LegacyRequest;
import android.hardware.camera2.legacy.RequestHolder;
import android.util.Log;
import android.util.MutableLong;
import android.util.Pair;
import android.view.Surface;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CaptureCollector {
    private static final boolean DEBUG = false;
    private static final int FLAG_RECEIVED_ALL_JPEG = 3;
    private static final int FLAG_RECEIVED_ALL_PREVIEW = 12;
    private static final int FLAG_RECEIVED_JPEG = 1;
    private static final int FLAG_RECEIVED_JPEG_TS = 2;
    private static final int FLAG_RECEIVED_PREVIEW = 4;
    private static final int FLAG_RECEIVED_PREVIEW_TS = 8;
    private static final int MAX_JPEGS_IN_FLIGHT = 1;
    private static final String TAG = "CaptureCollector";
    private final TreeSet<CaptureHolder> mActiveRequests;
    private final ArrayList<CaptureHolder> mCompletedRequests = new ArrayList();
    private final CameraDeviceState mDeviceState;
    private int mInFlight = 0;
    private int mInFlightPreviews = 0;
    private final Condition mIsEmpty;
    private final ArrayDeque<CaptureHolder> mJpegCaptureQueue;
    private final ArrayDeque<CaptureHolder> mJpegProduceQueue;
    private final ReentrantLock mLock = new ReentrantLock();
    private final int mMaxInFlight;
    private final Condition mNotFull;
    private final ArrayDeque<CaptureHolder> mPreviewCaptureQueue;
    private final ArrayDeque<CaptureHolder> mPreviewProduceQueue;
    private final Condition mPreviewsEmpty;

    public CaptureCollector(int n, CameraDeviceState cameraDeviceState) {
        this.mMaxInFlight = n;
        this.mJpegCaptureQueue = new ArrayDeque(1);
        this.mJpegProduceQueue = new ArrayDeque(1);
        this.mPreviewCaptureQueue = new ArrayDeque(this.mMaxInFlight);
        this.mPreviewProduceQueue = new ArrayDeque(this.mMaxInFlight);
        this.mActiveRequests = new TreeSet();
        this.mIsEmpty = this.mLock.newCondition();
        this.mNotFull = this.mLock.newCondition();
        this.mPreviewsEmpty = this.mLock.newCondition();
        this.mDeviceState = cameraDeviceState;
    }

    private void onPreviewCompleted() {
        --this.mInFlightPreviews;
        int n = this.mInFlightPreviews;
        if (n >= 0) {
            if (n == 0) {
                this.mPreviewsEmpty.signalAll();
            }
            return;
        }
        throw new IllegalStateException("More preview captures completed than requests queued.");
    }

    private void onRequestCompleted(CaptureHolder captureHolder) {
        captureHolder.mRequest;
        --this.mInFlight;
        if (this.mInFlight >= 0) {
            this.mCompletedRequests.add(captureHolder);
            this.mActiveRequests.remove(captureHolder);
            this.mNotFull.signalAll();
            if (this.mInFlight == 0) {
                this.mIsEmpty.signalAll();
            }
            return;
        }
        throw new IllegalStateException("More captures completed than requests queued.");
    }

    private boolean removeRequestIfCompleted(RequestHolder requestHolder, MutableLong mutableLong) {
        int n = 0;
        for (CaptureHolder captureHolder : this.mCompletedRequests) {
            if (captureHolder.mRequest.equals(requestHolder)) {
                mutableLong.value = captureHolder.mTimestamp;
                this.mCompletedRequests.remove(n);
                return true;
            }
            ++n;
        }
        return false;
    }

    public void failAll() {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            CaptureHolder captureHolder;
            while ((captureHolder = this.mActiveRequests.pollFirst()) != null) {
                captureHolder.setPreviewFailed();
                captureHolder.setJpegFailed();
            }
            this.mPreviewCaptureQueue.clear();
            this.mPreviewProduceQueue.clear();
            this.mJpegCaptureQueue.clear();
            this.mJpegProduceQueue.clear();
            return;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public void failNextJpeg() {
        ReentrantLock reentrantLock;
        CaptureHolder captureHolder;
        block7 : {
            CaptureHolder captureHolder2;
            block6 : {
                reentrantLock = this.mLock;
                reentrantLock.lock();
                captureHolder = this.mJpegCaptureQueue.peek();
                captureHolder2 = this.mJpegProduceQueue.peek();
                if (captureHolder == null) break block6;
                if (captureHolder2 == null) break block7;
                if (captureHolder.compareTo(captureHolder2) <= 0) break block7;
            }
            captureHolder = captureHolder2;
        }
        if (captureHolder != null) {
            this.mJpegCaptureQueue.remove(captureHolder);
            this.mJpegProduceQueue.remove(captureHolder);
            this.mActiveRequests.remove(captureHolder);
            captureHolder.setJpegFailed();
        }
        return;
        finally {
            reentrantLock.unlock();
        }
    }

    public void failNextPreview() {
        ReentrantLock reentrantLock;
        CaptureHolder captureHolder;
        block7 : {
            CaptureHolder captureHolder2;
            block6 : {
                reentrantLock = this.mLock;
                reentrantLock.lock();
                captureHolder = this.mPreviewCaptureQueue.peek();
                captureHolder2 = this.mPreviewProduceQueue.peek();
                if (captureHolder == null) break block6;
                if (captureHolder2 == null) break block7;
                if (captureHolder.compareTo(captureHolder2) <= 0) break block7;
            }
            captureHolder = captureHolder2;
        }
        if (captureHolder != null) {
            this.mPreviewCaptureQueue.remove(captureHolder);
            this.mPreviewProduceQueue.remove(captureHolder);
            this.mActiveRequests.remove(captureHolder);
            captureHolder.setPreviewFailed();
        }
        return;
        finally {
            reentrantLock.unlock();
        }
    }

    public boolean hasPendingPreviewCaptures() {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            boolean bl = this.mPreviewCaptureQueue.isEmpty();
            return bl ^ true;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public RequestHolder jpegCaptured(long l) {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            Object object = this.mJpegCaptureQueue.poll();
            if (object == null) {
                Log.w(TAG, "jpegCaptured called with no jpeg request on queue!");
                return null;
            }
            ((CaptureHolder)object).setJpegTimestamp(l);
            object = ((CaptureHolder)object).mRequest;
            return object;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public Pair<RequestHolder, Long> jpegProduced() {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            Object object = this.mJpegProduceQueue.poll();
            if (object == null) {
                Log.w(TAG, "jpegProduced called with no jpeg request on queue!");
                return null;
            }
            ((CaptureHolder)object).setJpegProduced();
            object = new Pair<RequestHolder, Long>(((CaptureHolder)object).mRequest, ((CaptureHolder)object).mTimestamp);
            return object;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public Pair<RequestHolder, Long> previewCaptured(long l) {
        Object object;
        ReentrantLock reentrantLock;
        block4 : {
            reentrantLock = this.mLock;
            reentrantLock.lock();
            object = this.mPreviewCaptureQueue.poll();
            if (object != null) break block4;
            reentrantLock.unlock();
            return null;
        }
        try {
            ((CaptureHolder)object).setPreviewTimestamp(l);
            object = new Pair<RequestHolder, Long>(((CaptureHolder)object).mRequest, ((CaptureHolder)object).mTimestamp);
            return object;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public RequestHolder previewProduced() {
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            Object object = this.mPreviewProduceQueue.poll();
            if (object == null) {
                Log.w(TAG, "previewProduced called with no preview request on queue!");
                return null;
            }
            ((CaptureHolder)object).setPreviewProduced();
            object = ((CaptureHolder)object).mRequest;
            return object;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean queueRequest(RequestHolder object, LegacyRequest object2, long l, TimeUnit timeUnit) throws InterruptedException {
        object2 = new CaptureHolder((RequestHolder)object, (LegacyRequest)object2);
        long l2 = timeUnit.toNanos(l);
        object = this.mLock;
        ((ReentrantLock)object).lock();
        try {
            int n;
            if (!((CaptureHolder)object2).needsJpeg && !((CaptureHolder)object2).needsPreview) {
                object2 = new IllegalStateException("Request must target at least one output surface!");
                throw object2;
            }
            l = l2;
            if (((CaptureHolder)object2).needsJpeg) {
                l = l2;
                while ((n = this.mInFlight) > 0) {
                    if (l <= 0L) {
                        ((ReentrantLock)object).unlock();
                        return false;
                    }
                    l = this.mIsEmpty.awaitNanos(l);
                }
                this.mJpegCaptureQueue.add((CaptureHolder)object2);
                this.mJpegProduceQueue.add((CaptureHolder)object2);
            }
            if (((CaptureHolder)object2).needsPreview) {
                int n2;
                while ((n = this.mInFlight) >= (n2 = this.mMaxInFlight)) {
                    if (l <= 0L) {
                        ((ReentrantLock)object).unlock();
                        return false;
                    }
                    l = this.mNotFull.awaitNanos(l);
                }
                this.mPreviewCaptureQueue.add((CaptureHolder)object2);
                this.mPreviewProduceQueue.add((CaptureHolder)object2);
                ++this.mInFlightPreviews;
            }
            this.mActiveRequests.add((CaptureHolder)object2);
            ++this.mInFlight;
            ((ReentrantLock)object).unlock();
            return true;
        }
        catch (Throwable throwable) {
            ((ReentrantLock)object).unlock();
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean waitForEmpty(long l, TimeUnit timeUnit) throws InterruptedException {
        l = timeUnit.toNanos(l);
        ReentrantLock reentrantLock = this.mLock;
        reentrantLock.lock();
        try {
            int n;
            while ((n = this.mInFlight) > 0) {
                if (l <= 0L) {
                    reentrantLock.unlock();
                    return false;
                }
                l = this.mIsEmpty.awaitNanos(l);
            }
            reentrantLock.unlock();
            return true;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean waitForPreviewsEmpty(long l, TimeUnit object) throws InterruptedException {
        l = ((TimeUnit)((Object)object)).toNanos(l);
        object = this.mLock;
        ((ReentrantLock)object).lock();
        try {
            int n;
            while ((n = this.mInFlightPreviews) > 0) {
                if (l <= 0L) {
                    ((ReentrantLock)object).unlock();
                    return false;
                }
                l = this.mPreviewsEmpty.awaitNanos(l);
            }
            ((ReentrantLock)object).unlock();
            return true;
        }
        catch (Throwable throwable) {
            ((ReentrantLock)object).unlock();
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean waitForRequestCompleted(RequestHolder requestHolder, long l, TimeUnit object, MutableLong mutableLong) throws InterruptedException {
        l = ((TimeUnit)((Object)object)).toNanos(l);
        object = this.mLock;
        ((ReentrantLock)object).lock();
        try {
            boolean bl;
            while (!(bl = this.removeRequestIfCompleted(requestHolder, mutableLong))) {
                if (l <= 0L) {
                    ((ReentrantLock)object).unlock();
                    return false;
                }
                l = this.mNotFull.awaitNanos(l);
            }
            ((ReentrantLock)object).unlock();
            return true;
        }
        catch (Throwable throwable) {
            ((ReentrantLock)object).unlock();
            throw throwable;
        }
    }

    private class CaptureHolder
    implements Comparable<CaptureHolder> {
        private boolean mCompleted = false;
        private boolean mFailedJpeg = false;
        private boolean mFailedPreview = false;
        private boolean mHasStarted = false;
        private final LegacyRequest mLegacy;
        private boolean mPreviewCompleted = false;
        private int mReceivedFlags = 0;
        private final RequestHolder mRequest;
        private long mTimestamp = 0L;
        public final boolean needsJpeg;
        public final boolean needsPreview;

        public CaptureHolder(RequestHolder requestHolder, LegacyRequest legacyRequest) {
            this.mRequest = requestHolder;
            this.mLegacy = legacyRequest;
            this.needsJpeg = requestHolder.hasJpegTargets();
            this.needsPreview = requestHolder.hasPreviewTargets();
        }

        @Override
        public int compareTo(CaptureHolder captureHolder) {
            int n = this.mRequest.getFrameNumber() > captureHolder.mRequest.getFrameNumber() ? 1 : (this.mRequest.getFrameNumber() == captureHolder.mRequest.getFrameNumber() ? 0 : -1);
            return n;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof CaptureHolder && this.compareTo((CaptureHolder)object) == 0;
            return bl;
        }

        public boolean isCompleted() {
            boolean bl = this.needsJpeg == this.isJpegCompleted() && this.needsPreview == this.isPreviewCompleted();
            return bl;
        }

        public boolean isJpegCompleted() {
            boolean bl = (this.mReceivedFlags & 3) == 3;
            return bl;
        }

        public boolean isPreviewCompleted() {
            boolean bl = (this.mReceivedFlags & 12) == 12;
            return bl;
        }

        public void setJpegFailed() {
            if (this.needsJpeg && !this.isJpegCompleted()) {
                this.mFailedJpeg = true;
                this.mReceivedFlags = 1 | this.mReceivedFlags;
                this.mReceivedFlags |= 2;
                this.tryComplete();
                return;
            }
        }

        public void setJpegProduced() {
            if (this.needsJpeg) {
                if (!this.isCompleted()) {
                    this.mReceivedFlags |= 1;
                    this.tryComplete();
                    return;
                }
                throw new IllegalStateException("setJpegProduced called on already completed request.");
            }
            throw new IllegalStateException("setJpegProduced called for capture with no jpeg targets.");
        }

        public void setJpegTimestamp(long l) {
            if (this.needsJpeg) {
                if (!this.isCompleted()) {
                    this.mReceivedFlags |= 2;
                    if (this.mTimestamp == 0L) {
                        this.mTimestamp = l;
                    }
                    if (!this.mHasStarted) {
                        this.mHasStarted = true;
                        CaptureCollector.this.mDeviceState.setCaptureStart(this.mRequest, this.mTimestamp, -1);
                    }
                    this.tryComplete();
                    return;
                }
                throw new IllegalStateException("setJpegTimestamp called on already completed request.");
            }
            throw new IllegalStateException("setJpegTimestamp called for capture with no jpeg targets.");
        }

        public void setPreviewFailed() {
            if (this.needsPreview && !this.isPreviewCompleted()) {
                this.mFailedPreview = true;
                this.mReceivedFlags |= 4;
                this.mReceivedFlags |= 8;
                this.tryComplete();
                return;
            }
        }

        public void setPreviewProduced() {
            if (this.needsPreview) {
                if (!this.isCompleted()) {
                    this.mReceivedFlags |= 4;
                    this.tryComplete();
                    return;
                }
                throw new IllegalStateException("setPreviewProduced called on already completed request.");
            }
            throw new IllegalStateException("setPreviewProduced called for capture with no preview targets.");
        }

        public void setPreviewTimestamp(long l) {
            if (this.needsPreview) {
                if (!this.isCompleted()) {
                    this.mReceivedFlags |= 8;
                    if (this.mTimestamp == 0L) {
                        this.mTimestamp = l;
                    }
                    if (!this.needsJpeg && !this.mHasStarted) {
                        this.mHasStarted = true;
                        CaptureCollector.this.mDeviceState.setCaptureStart(this.mRequest, this.mTimestamp, -1);
                    }
                    this.tryComplete();
                    return;
                }
                throw new IllegalStateException("setPreviewTimestamp called on already completed request.");
            }
            throw new IllegalStateException("setPreviewTimestamp called for capture with no preview targets.");
        }

        public void tryComplete() {
            if (!this.mPreviewCompleted && this.needsPreview && this.isPreviewCompleted()) {
                CaptureCollector.this.onPreviewCompleted();
                this.mPreviewCompleted = true;
            }
            if (this.isCompleted() && !this.mCompleted) {
                if (this.mFailedPreview || this.mFailedJpeg) {
                    if (!this.mHasStarted) {
                        this.mRequest.failRequest();
                        CaptureCollector.this.mDeviceState.setCaptureStart(this.mRequest, this.mTimestamp, 3);
                    } else {
                        for (Surface surface : this.mRequest.getRequest().getTargets()) {
                            try {
                                if (this.mRequest.jpegType(surface)) {
                                    if (!this.mFailedJpeg) continue;
                                    CaptureCollector.this.mDeviceState.setCaptureResult(this.mRequest, null, 5, surface);
                                    continue;
                                }
                                if (!this.mFailedPreview) continue;
                                CaptureCollector.this.mDeviceState.setCaptureResult(this.mRequest, null, 5, surface);
                            }
                            catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Unexpected exception when querying Surface: ");
                                stringBuilder.append(bufferQueueAbandonedException);
                                Log.e(CaptureCollector.TAG, stringBuilder.toString());
                            }
                        }
                    }
                }
                CaptureCollector.this.onRequestCompleted(this);
                this.mCompleted = true;
            }
        }
    }

}

