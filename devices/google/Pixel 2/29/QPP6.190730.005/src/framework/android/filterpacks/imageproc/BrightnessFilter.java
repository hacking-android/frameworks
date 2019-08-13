/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.FilterContext;
import android.filterfw.core.NativeProgram;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterpacks.imageproc.SimpleImageFilter;

public class BrightnessFilter
extends SimpleImageFilter {
    private static final String mBrightnessShader = "precision mediump float;\nuniform sampler2D tex_sampler_0;\nuniform float brightness;\nvarying vec2 v_texcoord;\nvoid main() {\n  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n  gl_FragColor = brightness * color;\n}\n";

    public BrightnessFilter(String string2) {
        super(string2, "brightness");
    }

    @Override
    protected Program getNativeProgram(FilterContext filterContext) {
        return new NativeProgram("filterpack_imageproc", "brightness");
    }

    @Override
    protected Program getShaderProgram(FilterContext filterContext) {
        return new ShaderProgram(filterContext, mBrightnessShader);
    }
}

