/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.format.ObjectFormat;

public class ObjectSource
extends Filter {
    private Frame mFrame;
    @GenerateFieldPort(name="object")
    private Object mObject;
    @GenerateFinalPort(hasDefault=true, name="format")
    private FrameFormat mOutputFormat = FrameFormat.unspecified();
    @GenerateFieldPort(hasDefault=true, name="repeatFrame")
    boolean mRepeatFrame = false;

    public ObjectSource(String string2) {
        super(string2);
    }

    @Override
    public void fieldPortValueUpdated(String object, FilterContext filterContext) {
        if (((String)object).equals("object") && (object = this.mFrame) != null) {
            ((Frame)object).release();
            this.mFrame = null;
        }
    }

    @Override
    public void process(FilterContext filterContext) {
        if (this.mFrame == null) {
            Object object = this.mObject;
            if (object != null) {
                object = ObjectFormat.fromObject(object, 1);
                this.mFrame = filterContext.getFrameManager().newFrame((FrameFormat)object);
                this.mFrame.setObjectValue(this.mObject);
                this.mFrame.setTimestamp(-1L);
            } else {
                throw new NullPointerException("ObjectSource producing frame with no object set!");
            }
        }
        this.pushOutput("frame", this.mFrame);
        if (!this.mRepeatFrame) {
            this.closeOutputPort("frame");
        }
    }

    @Override
    public void setupPorts() {
        this.addOutputPort("frame", this.mOutputFormat);
    }

    @Override
    public void tearDown(FilterContext filterContext) {
        this.mFrame.release();
    }
}

