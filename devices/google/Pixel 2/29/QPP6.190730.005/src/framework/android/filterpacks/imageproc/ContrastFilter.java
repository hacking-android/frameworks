/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.FilterContext;
import android.filterfw.core.NativeProgram;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterpacks.imageproc.SimpleImageFilter;

public class ContrastFilter
extends SimpleImageFilter {
    private static final String mContrastShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform float contrast;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  color -= 0.5;\n  color *= contrast;\n  color += 0.5;\n  gl_FragColor = color;\n}\n";

    public ContrastFilter(String string2) {
        super(string2, "contrast");
    }

    @Override
    protected Program getNativeProgram(FilterContext filterContext) {
        return new NativeProgram("filterpack_imageproc", "contrast");
    }

    @Override
    protected Program getShaderProgram(FilterContext filterContext) {
        return new ShaderProgram(filterContext, mContrastShader);
    }
}

