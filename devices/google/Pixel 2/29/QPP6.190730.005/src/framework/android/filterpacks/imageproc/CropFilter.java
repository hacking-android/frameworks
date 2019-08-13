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
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.format.ObjectFormat;
import android.filterfw.geometry.Quad;

public class CropFilter
extends Filter {
    @GenerateFieldPort(name="fillblack")
    private boolean mFillBlack = false;
    private final String mFragShader;
    private FrameFormat mLastFormat = null;
    @GenerateFieldPort(name="oheight")
    private int mOutputHeight = -1;
    @GenerateFieldPort(name="owidth")
    private int mOutputWidth = -1;
    private Program mProgram;

    public CropFilter(String string2) {
        super(string2);
        this.mFragShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  const vec2 lo = vec2(0.0, 0.0);\n  const vec2 hi = vec2(1.0, 1.0);\n  const vec4 black = vec4(0.0, 0.0, 0.0, 1.0);\n  bool out_of_bounds =\n    any(lessThan(v_texcoord, lo)) ||\n    any(greaterThan(v_texcoord, hi));\n  if (out_of_bounds) {\n    gl_FragColor = black;\n  } else {\n    gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n  }\n}\n";
    }

    protected void createProgram(FilterContext object, FrameFormat frameFormat) {
        FrameFormat frameFormat2 = this.mLastFormat;
        if (frameFormat2 != null && frameFormat2.getTarget() == frameFormat.getTarget()) {
            return;
        }
        this.mLastFormat = frameFormat;
        this.mProgram = null;
        if (frameFormat.getTarget() == 3) {
            this.mProgram = this.mFillBlack ? new ShaderProgram((FilterContext)object, "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  const vec2 lo = vec2(0.0, 0.0);\n  const vec2 hi = vec2(1.0, 1.0);\n  const vec4 black = vec4(0.0, 0.0, 0.0, 1.0);\n  bool out_of_bounds =\n    any(lessThan(v_texcoord, lo)) ||\n    any(greaterThan(v_texcoord, hi));\n  if (out_of_bounds) {\n    gl_FragColor = black;\n  } else {\n    gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n  }\n}\n") : ShaderProgram.createIdentity((FilterContext)object);
        }
        if (this.mProgram != null) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not create a program for crop filter ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append("!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @Override
    public FrameFormat getOutputFormat(String object, FrameFormat frameFormat) {
        object = frameFormat.mutableCopy();
        ((MutableFrameFormat)object).setDimensions(0, 0);
        return object;
    }

    @Override
    public void process(FilterContext object) {
        int n;
        int n2;
        Frame frame = this.pullInput("image");
        Object object2 = this.pullInput("box");
        this.createProgram((FilterContext)object, frame.getFormat());
        object2 = (Quad)((Frame)object2).getObjectValue();
        Object object3 = frame.getFormat().mutableCopy();
        int n3 = n = this.mOutputWidth;
        if (n == -1) {
            n3 = ((FrameFormat)object3).getWidth();
        }
        n = n2 = this.mOutputHeight;
        if (n2 == -1) {
            n = ((FrameFormat)object3).getHeight();
        }
        ((MutableFrameFormat)object3).setDimensions(n3, n);
        object = ((FilterContext)object).getFrameManager().newFrame((FrameFormat)object3);
        object3 = this.mProgram;
        if (object3 instanceof ShaderProgram) {
            ((ShaderProgram)object3).setSourceRegion((Quad)object2);
        }
        this.mProgram.process(frame, (Frame)object);
        this.pushOutput("image", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3));
        this.addMaskedInputPort("box", ObjectFormat.fromClass(Quad.class, 1));
        this.addOutputBasedOnInput("image", "image");
    }
}

