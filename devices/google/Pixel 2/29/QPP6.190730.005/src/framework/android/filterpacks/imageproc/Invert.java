/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.FilterContext;
import android.filterfw.core.NativeProgram;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterpacks.imageproc.SimpleImageFilter;

public class Invert
extends SimpleImageFilter {
    private static final String mInvertShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  gl_FragColor.r = 1.0 - color.r;\n  gl_FragColor.g = 1.0 - color.g;\n  gl_FragColor.b = 1.0 - color.b;\n  gl_FragColor.a = color.a;\n}\n";

    public Invert(String string2) {
        super(string2, null);
    }

    @Override
    protected Program getNativeProgram(FilterContext filterContext) {
        return new NativeProgram("filterpack_imageproc", "invert");
    }

    @Override
    protected Program getShaderProgram(FilterContext filterContext) {
        return new ShaderProgram(filterContext, mInvertShader);
    }
}

