/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.KeyValueMap;
import java.util.Arrays;

public class MutableFrameFormat
extends FrameFormat {
    public MutableFrameFormat() {
    }

    @UnsupportedAppUsage
    public MutableFrameFormat(int n, int n2) {
        super(n, n2);
    }

    public void setBaseType(int n) {
        this.mBaseType = n;
        this.mBytesPerSample = MutableFrameFormat.bytesPerSampleOf(n);
    }

    @UnsupportedAppUsage
    public void setBytesPerSample(int n) {
        this.mBytesPerSample = n;
        this.mSize = -1;
    }

    public void setDimensionCount(int n) {
        this.mDimensions = new int[n];
    }

    public void setDimensions(int n) {
        this.mDimensions = new int[]{n};
        this.mSize = -1;
    }

    @UnsupportedAppUsage
    public void setDimensions(int n, int n2) {
        this.mDimensions = new int[]{n, n2};
        this.mSize = -1;
    }

    public void setDimensions(int n, int n2, int n3) {
        this.mDimensions = new int[]{n, n2, n3};
        this.mSize = -1;
    }

    public void setDimensions(int[] object) {
        object = object == null ? null : Arrays.copyOf(object, ((int[])object).length);
        this.mDimensions = object;
        this.mSize = -1;
    }

    public void setMetaValue(String string2, Object object) {
        if (this.mMetaData == null) {
            this.mMetaData = new KeyValueMap();
        }
        this.mMetaData.put(string2, object);
    }

    public void setObjectClass(Class class_) {
        this.mObjectClass = class_;
    }

    public void setTarget(int n) {
        this.mTarget = n;
    }
}

