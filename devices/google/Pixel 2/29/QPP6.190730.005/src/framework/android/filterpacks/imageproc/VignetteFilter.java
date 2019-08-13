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

public class VignetteFilter
extends Filter {
    private int mHeight = 0;
    private Program mProgram;
    @GenerateFieldPort(hasDefault=true, name="scale")
    private float mScale = 0.0f;
    private final float mShade;
    private final float mSlope;
    private int mTarget = 0;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;
    private final String mVignetteShader;
    private int mWidth = 0;

    public VignetteFilter(String string2) {
        super(string2);
        this.mSlope = 20.0f;
        this.mShade = 0.85f;
        this.mVignetteShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform float range;\nuniform float inv_max_dist;\nuniform float shade;\nuniform vec2 scale;\nvarying vec2 v_texcoord;\nvoid main() {\n  const float slope = 20.0;\n  vec2 coord = v_texcoord - vec2(0.5, 0.5);\n  float dist = length(coord * scale);\n  float lumen = shade / (1.0 + exp((dist * inv_max_dist - range) * slope)) + (1.0 - shade);\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  gl_FragColor = vec4(color.rgb * lumen, color.a);\n}\n";
    }

    private void initParameters() {
        if (this.mProgram != null) {
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
            float f = (float)Math.sqrt(arrf[0] * arrf[0] + arrf[1] * arrf[1]);
            this.mProgram.setHostValue("scale", arrf);
            this.mProgram.setHostValue("inv_max_dist", Float.valueOf(1.0f / (f * 0.5f)));
            this.mProgram.setHostValue("shade", Float.valueOf(0.85f));
            this.updateParameters();
        }
    }

    private void updateParameters() {
        this.mProgram.setHostValue("range", Float.valueOf(1.3f - (float)Math.sqrt(this.mScale) * 0.7f));
    }

    @Override
    public void fieldPortValueUpdated(String string2, FilterContext filterContext) {
        if (this.mProgram != null) {
            this.updateParameters();
        }
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    public void initProgram(FilterContext object, int n) {
        if (n == 3) {
            object = new ShaderProgram((FilterContext)object, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform float range;\nuniform float inv_max_dist;\nuniform float shade;\nuniform vec2 scale;\nvarying vec2 v_texcoord;\nvoid main() {\n  const float slope = 20.0;\n  vec2 coord = v_texcoord - vec2(0.5, 0.5);\n  float dist = length(coord * scale);\n  float lumen = shade / (1.0 + exp((dist * inv_max_dist - range) * slope)) + (1.0 - shade);\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  gl_FragColor = vec4(color.rgb * lumen, color.a);\n}\n");
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
            this.initParameters();
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

