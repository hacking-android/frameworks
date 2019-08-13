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

public class PosterizeFilter
extends Filter {
    private final String mPosterizeShader;
    private Program mProgram;
    private int mTarget = 0;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;

    public PosterizeFilter(String string2) {
        super(string2);
        this.mPosterizeShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  vec3 pcolor;\n  pcolor.r = (color.r >= 0.5) ? 0.75 : 0.25;\n  pcolor.g = (color.g >= 0.5) ? 0.75 : 0.25;\n  pcolor.b = (color.b >= 0.5) ? 0.75 : 0.25;\n  gl_FragColor = vec4(pcolor, color.a);\n}\n";
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    public void initProgram(FilterContext object, int n) {
        if (n == 3) {
            object = new ShaderProgram((FilterContext)object, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  vec3 pcolor;\n  pcolor.r = (color.r >= 0.5) ? 0.75 : 0.25;\n  pcolor.g = (color.g >= 0.5) ? 0.75 : 0.25;\n  pcolor.b = (color.b >= 0.5) ? 0.75 : 0.25;\n  gl_FragColor = vec4(pcolor, color.a);\n}\n");
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
    public void process(FilterContext filterContext) {
        Frame frame = this.pullInput("image");
        FrameFormat frameFormat = frame.getFormat();
        Frame frame2 = filterContext.getFrameManager().newFrame(frameFormat);
        if (this.mProgram == null || frameFormat.getTarget() != this.mTarget) {
            this.initProgram(filterContext, frameFormat.getTarget());
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

