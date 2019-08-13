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

public class DuotoneFilter
extends Filter {
    private final String mDuotoneShader;
    @GenerateFieldPort(hasDefault=true, name="first_color")
    private int mFirstColor = -65536;
    private Program mProgram;
    @GenerateFieldPort(hasDefault=true, name="second_color")
    private int mSecondColor = -256;
    private int mTarget = 0;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;

    public DuotoneFilter(String string2) {
        super(string2);
        this.mDuotoneShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform vec3 first;\nuniform vec3 second;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float energy = (color.r + color.g + color.b) * 0.3333;\n  vec3 new_color = (1.0 - energy) * first + energy * second;\n  gl_FragColor = vec4(new_color.rgb, color.a);\n}\n";
    }

    private void updateParameters() {
        float f = (float)Color.red(this.mFirstColor) / 255.0f;
        float f2 = (float)Color.green(this.mFirstColor) / 255.0f;
        float f3 = (float)Color.blue(this.mFirstColor) / 255.0f;
        float f4 = (float)Color.red(this.mSecondColor) / 255.0f;
        float f5 = (float)Color.green(this.mSecondColor) / 255.0f;
        float f6 = (float)Color.blue(this.mSecondColor) / 255.0f;
        this.mProgram.setHostValue("first", new float[]{f, f2, f3});
        this.mProgram.setHostValue("second", new float[]{f4, f5, f6});
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    public void initProgram(FilterContext object, int n) {
        if (n == 3) {
            object = new ShaderProgram((FilterContext)object, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform vec3 first;\nuniform vec3 second;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float energy = (color.r + color.g + color.b) * 0.3333;\n  vec3 new_color = (1.0 - energy) * first + energy * second;\n  gl_FragColor = vec4(new_color.rgb, color.a);\n}\n");
            ((ShaderProgram)object).setMaximumTileSize(this.mTileSize);
            this.mProgram = object;
            this.mTarget = n;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Filter Duotone does not support frames of target ");
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
        this.updateParameters();
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

