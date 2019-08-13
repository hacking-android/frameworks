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
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.format.ImageFormat;

public class GLTextureSource
extends Filter {
    private Frame mFrame;
    @GenerateFieldPort(name="height")
    private int mHeight;
    @GenerateFieldPort(hasDefault=true, name="repeatFrame")
    private boolean mRepeatFrame = false;
    @GenerateFieldPort(name="texId")
    private int mTexId;
    @GenerateFieldPort(hasDefault=true, name="timestamp")
    private long mTimestamp = -1L;
    @GenerateFieldPort(name="width")
    private int mWidth;

    public GLTextureSource(String string2) {
        super(string2);
    }

    @Override
    public void fieldPortValueUpdated(String object, FilterContext filterContext) {
        object = this.mFrame;
        if (object != null) {
            ((Frame)object).release();
            this.mFrame = null;
        }
    }

    @Override
    public void process(FilterContext filterContext) {
        if (this.mFrame == null) {
            MutableFrameFormat mutableFrameFormat = ImageFormat.create(this.mWidth, this.mHeight, 3, 3);
            this.mFrame = filterContext.getFrameManager().newBoundFrame(mutableFrameFormat, 100, this.mTexId);
            this.mFrame.setTimestamp(this.mTimestamp);
        }
        this.pushOutput("frame", this.mFrame);
        if (!this.mRepeatFrame) {
            this.closeOutputPort("frame");
        }
    }

    @Override
    public void setupPorts() {
        this.addOutputPort("frame", ImageFormat.create(3, 3));
    }

    @Override
    public void tearDown(FilterContext object) {
        object = this.mFrame;
        if (object != null) {
            ((Frame)object).release();
        }
    }
}

