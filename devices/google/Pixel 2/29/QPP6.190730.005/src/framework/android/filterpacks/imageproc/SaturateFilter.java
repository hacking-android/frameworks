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

public class SaturateFilter
extends Filter {
    private Program mBenProgram;
    private final String mBenSaturateShader;
    private Program mHerfProgram;
    private final String mHerfSaturateShader;
    @GenerateFieldPort(hasDefault=true, name="scale")
    private float mScale = 0.0f;
    private int mTarget = 0;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;

    public SaturateFilter(String string2) {
        super(string2);
        this.mBenSaturateShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform float scale;\nuniform float shift;\nuniform vec3 weights;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float kv = dot(color.rgb, weights) + shift;\n  vec3 new_color = scale * color.rgb + (1.0 - scale) * kv;\n  gl_FragColor = vec4(new_color, color.a);\n}\n";
        this.mHerfSaturateShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform vec3 weights;\nuniform vec3 exponents;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float de = dot(color.rgb, weights);\n  float inv_de = 1.0 / de;\n  vec3 new_color = de * pow(color.rgb * inv_de, exponents);\n  float max_color = max(max(max(new_color.r, new_color.g), new_color.b), 1.0);\n  gl_FragColor = vec4(new_color / max_color, color.a);\n}\n";
    }

    private void initParameters() {
        float[] arrf;
        float[] arrf2 = arrf = new float[3];
        arrf2[0] = 0.25f;
        arrf2[1] = 0.625f;
        arrf2[2] = 0.125f;
        this.mBenProgram.setHostValue("weights", arrf);
        this.mBenProgram.setHostValue("shift", Float.valueOf(0.003921569f));
        this.mHerfProgram.setHostValue("weights", arrf);
        this.updateParameters();
    }

    private void updateParameters() {
        float f = this.mScale;
        if (f > 0.0f) {
            this.mHerfProgram.setHostValue("exponents", new float[]{0.9f * f + 1.0f, 2.1f * f + 1.0f, f * 2.7f + 1.0f});
        } else {
            this.mBenProgram.setHostValue("scale", Float.valueOf(f + 1.0f));
        }
    }

    @Override
    public void fieldPortValueUpdated(String string2, FilterContext filterContext) {
        if (this.mBenProgram != null && this.mHerfProgram != null) {
            this.updateParameters();
        }
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    public void initProgram(FilterContext object, int n) {
        if (n == 3) {
            ShaderProgram shaderProgram = new ShaderProgram((FilterContext)object, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform float scale;\nuniform float shift;\nuniform vec3 weights;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float kv = dot(color.rgb, weights) + shift;\n  vec3 new_color = scale * color.rgb + (1.0 - scale) * kv;\n  gl_FragColor = vec4(new_color, color.a);\n}\n");
            shaderProgram.setMaximumTileSize(this.mTileSize);
            this.mBenProgram = shaderProgram;
            object = new ShaderProgram((FilterContext)object, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform vec3 weights;\nuniform vec3 exponents;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float de = dot(color.rgb, weights);\n  float inv_de = 1.0 / de;\n  vec3 new_color = de * pow(color.rgb * inv_de, exponents);\n  float max_color = max(max(max(new_color.r, new_color.g), new_color.b), 1.0);\n  gl_FragColor = vec4(new_color / max_color, color.a);\n}\n");
            ((ShaderProgram)object).setMaximumTileSize(this.mTileSize);
            this.mHerfProgram = object;
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
        if (this.mBenProgram == null || frameFormat.getTarget() != this.mTarget) {
            this.initProgram((FilterContext)object, frameFormat.getTarget());
            this.initParameters();
        }
        object = ((FilterContext)object).getFrameManager().newFrame(frameFormat);
        if (this.mScale > 0.0f) {
            this.mHerfProgram.process(frame, (Frame)object);
        } else {
            this.mBenProgram.process(frame, (Frame)object);
        }
        this.pushOutput("image", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3));
        this.addOutputBasedOnInput("image", "image");
    }
}

