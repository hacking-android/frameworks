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

public class FisheyeFilter
extends Filter {
    private static final String TAG = "FisheyeFilter";
    private static final String mFisheyeShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform vec2 scale;\nuniform float alpha;\nuniform float radius2;\nuniform float factor;\nvarying vec2 v_texcoord;\nvoid main() {\n  const float m_pi_2 = 1.570963;\n  const float min_dist = 0.01;\n  vec2 coord = v_texcoord - vec2(0.5, 0.5);\n  float dist = length(coord * scale);\n  dist = max(dist, min_dist);\n  float radian = m_pi_2 - atan(alpha * sqrt(radius2 - dist * dist), dist);\n  float scalar = radian * factor / dist;\n  vec2 new_coord = coord * scalar + vec2(0.5, 0.5);\n  gl_FragColor = texture2D(tex_sampler_0, new_coord);\n}\n";
    private int mHeight = 0;
    private Program mProgram;
    @GenerateFieldPort(hasDefault=true, name="scale")
    private float mScale = 0.0f;
    private int mTarget = 0;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;
    private int mWidth = 0;

    public FisheyeFilter(String string2) {
        super(string2);
    }

    private void updateFrameSize(int n, int n2) {
        this.mWidth = n;
        this.mHeight = n2;
        this.updateProgramParams();
    }

    private void updateProgramParams() {
        float[] arrf = new float[2];
        int n = this.mWidth;
        int n2 = this.mHeight;
        if (n > n2) {
            arrf[0] = 1.0f;
            arrf[1] = (float)n2 / (float)n;
        } else {
            arrf[0] = (float)n / (float)n2;
            arrf[1] = 1.0f;
        }
        float f = this.mScale * 2.0f + 0.75f;
        float f2 = (arrf[0] * arrf[0] + arrf[1] * arrf[1]) * 0.25f;
        float f3 = (float)Math.sqrt(f2);
        float f4 = 1.15f * f3;
        f4 *= f4;
        f2 = f3 / (1.5707964f - (float)Math.atan(f / f3 * (float)Math.sqrt(f4 - f2)));
        this.mProgram.setHostValue("scale", arrf);
        this.mProgram.setHostValue("radius2", Float.valueOf(f4));
        this.mProgram.setHostValue("factor", Float.valueOf(f2));
        this.mProgram.setHostValue("alpha", Float.valueOf(f));
    }

    @Override
    public void fieldPortValueUpdated(String string2, FilterContext filterContext) {
        if (this.mProgram != null) {
            this.updateProgramParams();
        }
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    public void initProgram(FilterContext object, int n) {
        if (n == 3) {
            object = new ShaderProgram((FilterContext)object, mFisheyeShader);
            ((ShaderProgram)object).setMaximumTileSize(this.mTileSize);
            this.mProgram = object;
            this.mTarget = n;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Filter FisheyeFilter does not support frames of target ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append("!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @Override
    public void process(FilterContext filterContext) {
        Frame frame = this.pullInput("image");
        FrameFormat frameFormat = frame.getFormat();
        Frame frame2 = filterContext.getFrameManager().newFrame(frameFormat);
        if (this.mProgram == null || frameFormat.getTarget() != this.mTarget) {
            this.initProgram(filterContext, frameFormat.getTarget());
        }
        if (frameFormat.getWidth() != this.mWidth || frameFormat.getHeight() != this.mHeight) {
            this.updateFrameSize(frameFormat.getWidth(), frameFormat.getHeight());
        }
        this.mProgram.process(frame, frame2);
        this.pushOutput("image", frame2);
        frame2.release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3));
        this.addOutputBasedOnInput("image", "image");
    }
}

