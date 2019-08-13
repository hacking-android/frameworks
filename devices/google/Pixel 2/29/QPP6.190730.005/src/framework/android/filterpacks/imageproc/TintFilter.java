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
import android.graphics.Color;

public class TintFilter
extends Filter {
    private Program mProgram;
    private int mTarget = 0;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;
    @GenerateFieldPort(hasDefault=true, name="tint")
    private int mTint = -16776961;
    private final String mTintShader;

    public TintFilter(String string2) {
        super(string2);
        this.mTintShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform vec3 tint;\nuniform vec3 color_ratio;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float avg_color = dot(color_ratio, color.rgb);\n  vec3 new_color = min(0.8 * avg_color + 0.2 * tint, 1.0);\n  gl_FragColor = vec4(new_color.rgb, color.a);\n}\n";
    }

    private void initParameters() {
        this.mProgram.setHostValue("color_ratio", new float[]{0.21f, 0.71f, 0.07f});
        this.updateParameters();
    }

    private void updateParameters() {
        float f = (float)Color.red(this.mTint) / 255.0f;
        float f2 = (float)Color.green(this.mTint) / 255.0f;
        float f3 = (float)Color.blue(this.mTint) / 255.0f;
        this.mProgram.setHostValue("tint", new float[]{f, f2, f3});
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
            object = new ShaderProgram((FilterContext)object, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform vec3 tint;\nuniform vec3 color_ratio;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float avg_color = dot(color_ratio, color.rgb);\n  vec3 new_color = min(0.8 * avg_color + 0.2 * tint, 1.0);\n  gl_FragColor = vec4(new_color.rgb, color.a);\n}\n");
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

