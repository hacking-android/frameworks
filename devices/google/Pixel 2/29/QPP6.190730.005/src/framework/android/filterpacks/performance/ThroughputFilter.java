/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.performance;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.format.ObjectFormat;
import android.filterpacks.performance.Throughput;
import android.os.SystemClock;

public class ThroughputFilter
extends Filter {
    private long mLastTime = 0L;
    private FrameFormat mOutputFormat;
    @GenerateFieldPort(hasDefault=true, name="period")
    private int mPeriod = 5;
    private int mPeriodFrameCount = 0;
    private int mTotalFrameCount = 0;

    public ThroughputFilter(String string2) {
        super(string2);
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override
    public void open(FilterContext filterContext) {
        this.mTotalFrameCount = 0;
        this.mPeriodFrameCount = 0;
        this.mLastTime = 0L;
    }

    @Override
    public void process(FilterContext object) {
        long l;
        Object object2 = this.pullInput("frame");
        this.pushOutput("frame", (Frame)object2);
        ++this.mTotalFrameCount;
        ++this.mPeriodFrameCount;
        if (this.mLastTime == 0L) {
            this.mLastTime = SystemClock.elapsedRealtime();
        }
        if ((l = SystemClock.elapsedRealtime()) - this.mLastTime >= (long)(this.mPeriod * 1000)) {
            object2 = ((Frame)object2).getFormat();
            int n = ((FrameFormat)object2).getWidth();
            int n2 = ((FrameFormat)object2).getHeight();
            object2 = new Throughput(this.mTotalFrameCount, this.mPeriodFrameCount, this.mPeriod, n * n2);
            object = ((FilterContext)object).getFrameManager().newFrame(this.mOutputFormat);
            ((Frame)object).setObjectValue(object2);
            this.pushOutput("throughput", (Frame)object);
            this.mLastTime = l;
            this.mPeriodFrameCount = 0;
        }
    }

    @Override
    public void setupPorts() {
        this.addInputPort("frame");
        this.mOutputFormat = ObjectFormat.fromClass(Throughput.class, 1);
        this.addOutputBasedOnInput("frame", "frame");
        this.addOutputPort("throughput", this.mOutputFormat);
    }
}

