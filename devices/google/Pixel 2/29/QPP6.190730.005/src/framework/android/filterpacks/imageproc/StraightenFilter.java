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
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.geometry.Point;
import android.filterfw.geometry.Quad;

public class StraightenFilter
extends Filter {
    private static final float DEGREE_TO_RADIAN = 0.017453292f;
    @GenerateFieldPort(hasDefault=true, name="angle")
    private float mAngle = 0.0f;
    private int mHeight = 0;
    @GenerateFieldPort(hasDefault=true, name="maxAngle")
    private float mMaxAngle = 45.0f;
    private Program mProgram;
    private int mTarget = 0;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;
    private int mWidth = 0;

    public StraightenFilter(String string2) {
        super(string2);
    }

    private void updateParameters() {
        float f = (float)Math.cos(this.mAngle * 0.017453292f);
        float f2 = (float)Math.sin(this.mAngle * 0.017453292f);
        float f3 = this.mMaxAngle;
        if (!(f3 <= 0.0f)) {
            float f4 = f3;
            if (f3 > 90.0f) {
                f4 = 90.0f;
            }
            this.mMaxAngle = f4;
            f4 = -f;
            int n = this.mWidth;
            f3 = n;
            int n2 = this.mHeight;
            Point point = new Point(f4 * f3 + (float)n2 * f2, -f2 * (float)n - (float)n2 * f);
            n2 = this.mWidth;
            f4 = n2;
            n = this.mHeight;
            Point point2 = new Point(f4 * f + (float)n * f2, (float)n2 * f2 - (float)n * f);
            f4 = -f;
            n = this.mWidth;
            f3 = n;
            n2 = this.mHeight;
            Object object = new Point(f4 * f3 - (float)n2 * f2, -f2 * (float)n + (float)n2 * f);
            n2 = this.mWidth;
            f4 = n2;
            n = this.mHeight;
            Point point3 = new Point(f4 * f - (float)n * f2, (float)n2 * f2 + (float)n * f);
            f4 = Math.max(Math.abs(point.x), Math.abs(point2.x));
            f3 = Math.max(Math.abs(point.y), Math.abs(point2.y));
            f4 = Math.min((float)this.mWidth / f4, (float)this.mHeight / f3) * 0.5f;
            point.set(point.x * f4 / (float)this.mWidth + 0.5f, point.y * f4 / (float)this.mHeight + 0.5f);
            point2.set(point2.x * f4 / (float)this.mWidth + 0.5f, point2.y * f4 / (float)this.mHeight + 0.5f);
            ((Point)object).set(((Point)object).x * f4 / (float)this.mWidth + 0.5f, ((Point)object).y * f4 / (float)this.mHeight + 0.5f);
            point3.set(point3.x * f4 / (float)this.mWidth + 0.5f, point3.y * f4 / (float)this.mHeight + 0.5f);
            object = new Quad(point, point2, (Point)object, point3);
            ((ShaderProgram)this.mProgram).setSourceRegion((Quad)object);
            return;
        }
        throw new RuntimeException("Max angle is out of range (0-180).");
    }

    @Override
    public void fieldPortValueUpdated(String string2, FilterContext filterContext) {
        if (this.mProgram != null) {
            this.updateParameters();
        }
    }

    public void initProgram(FilterContext object, int n) {
        if (n == 3) {
            object = ShaderProgram.createIdentity((FilterContext)object);
            ((ShaderProgram)object).setMaximumTileSize(this.mTileSize);
            this.mProgram = object;
            this.mTarget = n;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Filter Sharpen does not support frames of target ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append("!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("image");
        FrameFormat frameFormat = frame.getFormat();
        if (this.mProgram == null || frameFormat.getTarget() != this.mTarget) {
            this.initProgram((FilterContext)object, frameFormat.getTarget());
        }
        if (frameFormat.getWidth() != this.mWidth || frameFormat.getHeight() != this.mHeight) {
            this.mWidth = frameFormat.getWidth();
            this.mHeight = frameFormat.getHeight();
            this.updateParameters();
        }
        object = ((FilterContext)object).getFrameManager().newFrame(frameFormat);
        this.mProgram.process(frame, (Frame)object);
        this.pushOutput("image", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3));
        this.addOutputBasedOnInput("image", "image");
    }
}

