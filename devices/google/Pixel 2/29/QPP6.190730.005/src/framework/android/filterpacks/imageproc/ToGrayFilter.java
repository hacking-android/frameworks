/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.FilterContext;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterpacks.imageproc.SimpleImageFilter;

public class ToGrayFilter
extends SimpleImageFilter {
    private static final String mColorToGray4Shader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  float y = dot(color, vec4(0.299, 0.587, 0.114, 0));\n  gl_FragColor = vec4(y, y, y, color.a);\n}\n";
    @GenerateFieldPort(hasDefault=true, name="invertSource")
    private boolean mInvertSource = false;
    private MutableFrameFormat mOutputFormat;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;

    public ToGrayFilter(String string2) {
        super(string2, null);
    }

    @Override
    protected Program getNativeProgram(FilterContext filterContext) {
        throw new RuntimeException("Native toGray not implemented yet!");
    }

    @Override
    protected Program getShaderProgram(FilterContext object) {
        int n = this.getInputFormat("image").getBytesPerSample();
        if (n == 4) {
            object = new ShaderProgram((FilterContext)object, mColorToGray4Shader);
            ((ShaderProgram)object).setMaximumTileSize(this.mTileSize);
            if (this.mInvertSource) {
                ((ShaderProgram)object).setSourceRect(0.0f, 1.0f, 1.0f, -1.0f);
            }
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unsupported GL input channels: ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append("! Channels must be 4!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3, 3));
        this.addOutputBasedOnInput("image", "image");
    }
}

