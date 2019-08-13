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
import java.util.Date;
import java.util.Random;

public class GrainFilter
extends Filter {
    private static final int RAND_THRESHOLD = 128;
    private Program mGrainProgram;
    private final String mGrainShader;
    private int mHeight = 0;
    private Program mNoiseProgram;
    private final String mNoiseShader;
    private Random mRandom = new Random(new Date().getTime());
    @GenerateFieldPort(hasDefault=true, name="strength")
    private float mScale = 0.0f;
    private int mTarget = 0;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;
    private int mWidth = 0;

    public GrainFilter(String string2) {
        super(string2);
        this.mNoiseShader = "precision mediump float;\nuniform vec2 seed;\nvarying vec2 v_texcoord;\nfloat rand(vec2 loc) {\n  float theta1 = dot(loc, vec2(0.9898, 0.233));\n  float theta2 = dot(loc, vec2(12.0, 78.0));\n  float value = cos(theta1) * sin(theta2) + sin(theta1) * cos(theta2);\n  float temp = mod(197.0 * value, 1.0) + value;\n  float part1 = mod(220.0 * temp, 1.0) + temp;\n  float part2 = value * 0.5453;\n  float part3 = cos(theta1 + theta2) * 0.43758;\n  return fract(part1 + part2 + part3);\n}\nvoid main() {\n  gl_FragColor = vec4(rand(v_texcoord + seed), 0.0, 0.0, 1.0);\n}\n";
        this.mGrainShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float scale;\nuniform float stepX;\nuniform float stepY;\nvarying vec2 v_texcoord;\nvoid main() {\n  float noise = texture2D(tex_sampler_1, v_texcoord + vec2(-stepX, -stepY)).r * 0.224;\n  noise += texture2D(tex_sampler_1, v_texcoord + vec2(-stepX, stepY)).r * 0.224;\n  noise += texture2D(tex_sampler_1, v_texcoord + vec2(stepX, -stepY)).r * 0.224;\n  noise += texture2D(tex_sampler_1, v_texcoord + vec2(stepX, stepY)).r * 0.224;\n  noise += 0.4448;\n  noise *= scale;\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float energy = 0.33333 * color.r + 0.33333 * color.g + 0.33333 * color.b;\n  float mask = (1.0 - sqrt(energy));\n  float weight = 1.0 - 1.333 * mask * noise;\n  gl_FragColor = vec4(color.rgb * weight, color.a);\n}\n";
    }

    private void updateFrameSize(int n, int n2) {
        this.mWidth = n;
        this.mHeight = n2;
        Program program = this.mGrainProgram;
        if (program != null) {
            program.setHostValue("stepX", Float.valueOf(0.5f / (float)this.mWidth));
            this.mGrainProgram.setHostValue("stepY", Float.valueOf(0.5f / (float)this.mHeight));
            this.updateParameters();
        }
    }

    private void updateParameters() {
        float f = this.mRandom.nextFloat();
        float f2 = this.mRandom.nextFloat();
        this.mNoiseProgram.setHostValue("seed", new float[]{f, f2});
        this.mGrainProgram.setHostValue("scale", Float.valueOf(this.mScale));
    }

    @Override
    public void fieldPortValueUpdated(String string2, FilterContext filterContext) {
        if (this.mGrainProgram != null && this.mNoiseProgram != null) {
            this.updateParameters();
        }
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    public void initProgram(FilterContext object, int n) {
        if (n == 3) {
            ShaderProgram shaderProgram = new ShaderProgram((FilterContext)object, "precision mediump float;\nuniform vec2 seed;\nvarying vec2 v_texcoord;\nfloat rand(vec2 loc) {\n  float theta1 = dot(loc, vec2(0.9898, 0.233));\n  float theta2 = dot(loc, vec2(12.0, 78.0));\n  float value = cos(theta1) * sin(theta2) + sin(theta1) * cos(theta2);\n  float temp = mod(197.0 * value, 1.0) + value;\n  float part1 = mod(220.0 * temp, 1.0) + temp;\n  float part2 = value * 0.5453;\n  float part3 = cos(theta1 + theta2) * 0.43758;\n  return fract(part1 + part2 + part3);\n}\nvoid main() {\n  gl_FragColor = vec4(rand(v_texcoord + seed), 0.0, 0.0, 1.0);\n}\n");
            shaderProgram.setMaximumTileSize(this.mTileSize);
            this.mNoiseProgram = shaderProgram;
            object = new ShaderProgram((FilterContext)object, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform sampler2D tex_sampler_1;\nuniform float scale;\nuniform float stepX;\nuniform float stepY;\nvarying vec2 v_texcoord;\nvoid main() {\n  float noise = texture2D(tex_sampler_1, v_texcoord + vec2(-stepX, -stepY)).r * 0.224;\n  noise += texture2D(tex_sampler_1, v_texcoord + vec2(-stepX, stepY)).r * 0.224;\n  noise += texture2D(tex_sampler_1, v_texcoord + vec2(stepX, -stepY)).r * 0.224;\n  noise += texture2D(tex_sampler_1, v_texcoord + vec2(stepX, stepY)).r * 0.224;\n  noise += 0.4448;\n  noise *= scale;\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float energy = 0.33333 * color.r + 0.33333 * color.g + 0.33333 * color.b;\n  float mask = (1.0 - sqrt(energy));\n  float weight = 1.0 - 1.333 * mask * noise;\n  gl_FragColor = vec4(color.rgb * weight, color.a);\n}\n");
            ((ShaderProgram)object).setMaximumTileSize(this.mTileSize);
            this.mGrainProgram = object;
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
    public void process(FilterContext filterContext) {
        Frame frame = this.pullInput("image");
        FrameFormat frameFormat = frame.getFormat();
        ImageFormat.create(frameFormat.getWidth() / 2, frameFormat.getHeight() / 2, 3, 3);
        Frame frame2 = filterContext.getFrameManager().newFrame(frameFormat);
        Frame frame3 = filterContext.getFrameManager().newFrame(frameFormat);
        if (this.mNoiseProgram == null || this.mGrainProgram == null || frameFormat.getTarget() != this.mTarget) {
            this.initProgram(filterContext, frameFormat.getTarget());
            this.updateParameters();
        }
        if (frameFormat.getWidth() != this.mWidth || frameFormat.getHeight() != this.mHeight) {
            this.updateFrameSize(frameFormat.getWidth(), frameFormat.getHeight());
        }
        this.mNoiseProgram.process(new Frame[0], frame2);
        this.mGrainProgram.process(new Frame[]{frame, frame2}, frame3);
        this.pushOutput("image", frame3);
        frame3.release();
        frame2.release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3));
        this.addOutputBasedOnInput("image", "image");
    }
}

