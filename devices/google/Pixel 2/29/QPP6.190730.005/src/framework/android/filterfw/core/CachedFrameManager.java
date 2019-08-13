/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.SimpleFrameManager;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class CachedFrameManager
extends SimpleFrameManager {
    private SortedMap<Integer, Frame> mAvailableFrames = new TreeMap<Integer, Frame>();
    private int mStorageCapacity = 25165824;
    private int mStorageSize = 0;
    private int mTimeStamp = 0;

    private void dropOldestFrame() {
        int n = this.mAvailableFrames.firstKey();
        Frame frame = (Frame)this.mAvailableFrames.get(n);
        this.mStorageSize -= frame.getFormat().getSize();
        frame.releaseNativeAllocation();
        this.mAvailableFrames.remove(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Frame findAvailableFrame(FrameFormat frameFormat, int n, long l) {
        SortedMap<Integer, Frame> sortedMap = this.mAvailableFrames;
        synchronized (sortedMap) {
            Frame frame;
            Map.Entry<Integer, Frame> entry;
            Iterator<Map.Entry<Integer, Frame>> iterator = this.mAvailableFrames.entrySet().iterator();
            do {
                if (iterator.hasNext()) continue;
                return null;
            } while (!(frame = (entry = iterator.next()).getValue()).getFormat().isReplaceableBy(frameFormat) || n != frame.getBindingType() || n != 0 && l != frame.getBindingId());
            super.retainFrame(frame);
            this.mAvailableFrames.remove(entry.getKey());
            frame.onFrameFetch();
            frame.reset(frameFormat);
            this.mStorageSize -= frameFormat.getSize();
            return frame;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean storeFrame(Frame frame) {
        SortedMap<Integer, Frame> sortedMap = this.mAvailableFrames;
        synchronized (sortedMap) {
            int n = frame.getFormat().getSize();
            if (n > this.mStorageCapacity) {
                return false;
            }
            int n2 = this.mStorageSize + n;
            do {
                if (n2 <= this.mStorageCapacity) {
                    frame.onFrameStore();
                    this.mStorageSize = n2;
                    this.mAvailableFrames.put(this.mTimeStamp, frame);
                    ++this.mTimeStamp;
                    return true;
                }
                this.dropOldestFrame();
                n2 = this.mStorageSize + n;
            } while (true);
        }
    }

    public void clearCache() {
        Iterator<Frame> iterator = this.mAvailableFrames.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().releaseNativeAllocation();
        }
        this.mAvailableFrames.clear();
    }

    @Override
    public Frame newBoundFrame(FrameFormat frameFormat, int n, long l) {
        Frame frame;
        Frame frame2 = frame = this.findAvailableFrame(frameFormat, n, l);
        if (frame == null) {
            frame2 = super.newBoundFrame(frameFormat, n, l);
        }
        frame2.setTimestamp(-2L);
        return frame2;
    }

    @Override
    public Frame newFrame(FrameFormat frameFormat) {
        Frame frame;
        Frame frame2 = frame = this.findAvailableFrame(frameFormat, 0, 0L);
        if (frame == null) {
            frame2 = super.newFrame(frameFormat);
        }
        frame2.setTimestamp(-2L);
        return frame2;
    }

    @Override
    public Frame releaseFrame(Frame frame) {
        if (frame.isReusable()) {
            int n = frame.decRefCount();
            if (n == 0 && frame.hasNativeAllocation()) {
                if (!this.storeFrame(frame)) {
                    frame.releaseNativeAllocation();
                }
                return null;
            }
            if (n < 0) {
                throw new RuntimeException("Frame reference count dropped below 0!");
            }
        } else {
            super.releaseFrame(frame);
        }
        return frame;
    }

    @Override
    public Frame retainFrame(Frame frame) {
        return super.retainFrame(frame);
    }

    @Override
    public void tearDown() {
        this.clearCache();
    }
}

