/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.geometry.Point;
import android.filterfw.geometry.Quad;

public class FixedRotationFilter
extends Filter {
    private ShaderProgram mProgram = null;
    @GenerateFieldPort(hasDefault=true, name="rotation")
    private int mRotation = 0;

    public FixedRotationFilter(String string2) {
        super(string2);
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("image");
        if (this.mRotation == 0) {
            this.pushOutput("image", frame);
            return;
        }
        Object object2 = frame.getFormat();
        if (this.mProgram == null) {
            this.mProgram = ShaderProgram.createIdentity((FilterContext)object);
        }
        MutableFrameFormat mutableFrameFormat = ((FrameFormat)object2).mutableCopy();
        int n = ((FrameFormat)object2).getWidth();
        int n2 = ((FrameFormat)object2).getHeight();
        object2 = new Point(0.0f, 0.0f);
        Point point = new Point(1.0f, 0.0f);
        Point point2 = new Point(0.0f, 1.0f);
        Point point3 = new Point(1.0f, 1.0f);
        int n3 = Math.round((float)this.mRotation / 90.0f) % 4;
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    object2 = new Quad((Point)object2, point, point2, point3);
                } else {
                    object2 = new Quad(point, point3, (Point)object2, point2);
                    mutableFrameFormat.setDimensions(n2, n);
                }
            } else {
                object2 = new Quad(point3, point2, point, (Point)object2);
            }
        } else {
            object2 = new Quad(point2, (Point)object2, point3, point);
            mutableFrameFormat.setDimensions(n2, n);
        }
        object = ((FilterContext)object).getFrameManager().newFrame(mutableFrameFormat);
        this.mProgram.setSourceRegion((Quad)object2);
        this.mProgram.process(frame, (Frame)object);
        this.pushOutput("image", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3, 3));
        this.addOutputBasedOnInput("image", "image");
    }
}

