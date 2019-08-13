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

public class CrossProcessFilter
extends Filter {
    private final String mCrossProcessShader;
    private Program mProgram;
    private int mTarget = 0;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;

    public CrossProcessFilter(String string2) {
        super(string2);
        this.mCrossProcessShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  vec3 ncolor = vec3(0.0, 0.0, 0.0);\n  float value;\n  if (color.r < 0.5) {\n    value = color.r;\n  } else {\n    value = 1.0 - color.r;\n  }\n  float red = 4.0 * value * value * value;\n  if (color.r < 0.5) {\n    ncolor.r = red;\n  } else {\n    ncolor.r = 1.0 - red;\n  }\n  if (color.g < 0.5) {\n    value = color.g;\n  } else {\n    value = 1.0 - color.g;\n  }\n  float green = 2.0 * value * value;\n  if (color.g < 0.5) {\n    ncolor.g = green;\n  } else {\n    ncolor.g = 1.0 - green;\n  }\n  ncolor.b = color.b * 0.5 + 0.25;\n  gl_FragColor = vec4(ncolor.rgb, color.a);\n}\n";
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    public void initProgram(FilterContext object, int n) {
        if (n == 3) {
            object = new ShaderProgram((FilterContext)object, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  vec3 ncolor = vec3(0.0, 0.0, 0.0);\n  float value;\n  if (color.r < 0.5) {\n    value = color.r;\n  } else {\n    value = 1.0 - color.r;\n  }\n  float red = 4.0 * value * value * value;\n  if (color.r < 0.5) {\n    ncolor.r = red;\n  } else {\n    ncolor.r = 1.0 - red;\n  }\n  if (color.g < 0.5) {\n    value = color.g;\n  } else {\n    value = 1.0 - color.g;\n  }\n  float green = 2.0 * value * value;\n  if (color.g < 0.5) {\n    ncolor.g = green;\n  } else {\n    ncolor.g = 1.0 - green;\n  }\n  ncolor.b = color.b * 0.5 + 0.25;\n  gl_FragColor = vec4(ncolor.rgb, color.a);\n}\n");
            ((ShaderProgram)object).setMaximumTileSize(this.mTileSize);
            this.mProgram = object;
            this.mTarget = n;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Filter CrossProcess does not support frames of target ");
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

