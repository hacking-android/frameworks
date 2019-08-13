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

public class GLTextureTarget
extends Filter {
    @GenerateFieldPort(name="texId")
    private int mTexId;

    public GLTextureTarget(String string2) {
        super(string2);
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("frame");
        MutableFrameFormat mutableFrameFormat = ImageFormat.create(frame.getFormat().getWidth(), frame.getFormat().getHeight(), 3, 3);
        object = ((FilterContext)object).getFrameManager().newBoundFrame(mutableFrameFormat, 100, this.mTexId);
        ((Frame)object).setDataFromFrame(frame);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("frame", ImageFormat.create(3));
    }
}

