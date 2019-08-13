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

public class ToPackedGrayFilter
extends Filter {
    private final String mColorToPackedGrayShader;
    @GenerateFieldPort(hasDefault=true, name="keepAspectRatio")
    private boolean mKeepAspectRatio = false;
    @GenerateFieldPort(hasDefault=true, name="oheight")
    private int mOHeight = 0;
    @GenerateFieldPort(hasDefault=true, name="owidth")
    private int mOWidth = 0;
    private Program mProgram;

    public ToPackedGrayFilter(String string2) {
        super(string2);
        this.mColorToPackedGrayShader = "precision mediump float;\nconst vec4 coeff_y = vec4(0.299, 0.587, 0.114, 0);\nuniform sampler2D tex_sampler_0;\nuniform float pix_stride;\nvarying vec2 v_texcoord;\nvoid main() {\n  for (int i = 0; i < 4; ++i) {\n    vec4 p = texture2D(tex_sampler_0,\n                       v_texcoord + vec2(pix_stride * float(i), 0.0));\n    gl_FragColor[i] = dot(p, coeff_y);\n  }\n}\n";
    }

    private void checkOutputDimensions(int n, int n2) {
        if (n > 0 && n2 > 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid output dimensions: ");
        stringBuilder.append(n);
        stringBuilder.append(" ");
        stringBuilder.append(n2);
        throw new RuntimeException(stringBuilder.toString());
    }

    private FrameFormat convertInputFormat(FrameFormat frameFormat) {
        int n = this.mOWidth;
        int n2 = this.mOHeight;
        int n3 = frameFormat.getWidth();
        int n4 = frameFormat.getHeight();
        if (this.mOWidth == 0) {
            n = n3;
        }
        if (this.mOHeight == 0) {
            n2 = n4;
        }
        int n5 = n;
        int n6 = n2;
        if (this.mKeepAspectRatio) {
            if (n3 > n4) {
                n5 = Math.max(n, n2);
                n6 = n5 * n4 / n3;
            } else {
                n6 = Math.max(n, n2);
                n5 = n6 * n3 / n4;
            }
        }
        n = 4;
        n5 = n5 > 0 && n5 < 4 ? n : 4 * (n5 / 4);
        return ImageFormat.create(n5, n6, 1, 2);
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return this.convertInputFormat(frameFormat);
    }

    @Override
    public void prepare(FilterContext filterContext) {
        this.mProgram = new ShaderProgram(filterContext, "precision mediump float;\nconst vec4 coeff_y = vec4(0.299, 0.587, 0.114, 0);\nuniform sampler2D tex_sampler_0;\nuniform float pix_stride;\nvarying vec2 v_texcoord;\nvoid main() {\n  for (int i = 0; i < 4; ++i) {\n    vec4 p = texture2D(tex_sampler_0,\n                       v_texcoord + vec2(pix_stride * float(i), 0.0));\n    gl_FragColor[i] = dot(p, coeff_y);\n  }\n}\n");
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("image");
        Object object2 = frame.getFormat();
        FrameFormat frameFormat = this.convertInputFormat((FrameFormat)object2);
        int n = frameFormat.getWidth();
        int n2 = frameFormat.getHeight();
        this.checkOutputDimensions(n, n2);
        this.mProgram.setHostValue("pix_stride", Float.valueOf(1.0f / (float)n));
        object2 = ((FrameFormat)object2).mutableCopy();
        ((MutableFrameFormat)object2).setDimensions(n / 4, n2);
        object2 = ((FilterContext)object).getFrameManager().newFrame((FrameFormat)object2);
        this.mProgram.process(frame, (Frame)object2);
        object = ((FilterContext)object).getFrameManager().newFrame(frameFormat);
        ((Frame)object).setDataFromFrame((Frame)object2);
        ((Frame)object2).release();
        this.pushOutput("image", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3, 3));
        this.addOutputBasedOnInput("image", "image");
    }
}

