/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLFrame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.format.ObjectFormat;
import android.filterfw.geometry.Point;
import android.filterfw.geometry.Quad;
import android.opengl.GLES20;

public class DrawRectFilter
extends Filter {
    @GenerateFieldPort(hasDefault=true, name="colorBlue")
    private float mColorBlue = 0.0f;
    @GenerateFieldPort(hasDefault=true, name="colorGreen")
    private float mColorGreen = 0.8f;
    @GenerateFieldPort(hasDefault=true, name="colorRed")
    private float mColorRed = 0.8f;
    private final String mFixedColorFragmentShader;
    private ShaderProgram mProgram;
    private final String mVertexShader;

    public DrawRectFilter(String string2) {
        super(string2);
        this.mVertexShader = "attribute vec4 aPosition;\nvoid main() {\n  gl_Position = aPosition;\n}\n";
        this.mFixedColorFragmentShader = "precision mediump float;\nuniform vec4 color;\nvoid main() {\n  gl_FragColor = color;\n}\n";
    }

    private void renderBox(Quad quad) {
        float f = this.mColorRed;
        float f2 = this.mColorGreen;
        float f3 = this.mColorBlue;
        float f4 = quad.p0.x;
        float f5 = quad.p0.y;
        float f6 = quad.p1.x;
        float f7 = quad.p1.y;
        float f8 = quad.p3.x;
        float f9 = quad.p3.y;
        float f10 = quad.p2.x;
        float f11 = quad.p2.y;
        this.mProgram.setHostValue("color", new float[]{f, f2, f3, 1.0f});
        this.mProgram.setAttributeValues("aPosition", new float[]{f4, f5, f6, f7, f8, f9, f10, f11}, 2);
        this.mProgram.setVertexCount(4);
        this.mProgram.beginDrawing();
        GLES20.glLineWidth(1.0f);
        GLES20.glDrawArrays(2, 0, 4);
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override
    public void prepare(FilterContext filterContext) {
        this.mProgram = new ShaderProgram(filterContext, "attribute vec4 aPosition;\nvoid main() {\n  gl_Position = aPosition;\n}\n", "precision mediump float;\nuniform vec4 color;\nvoid main() {\n  gl_FragColor = color;\n}\n");
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("image");
        Quad quad = ((Quad)this.pullInput("box").getObjectValue()).scaled(2.0f).translated(-1.0f, -1.0f);
        object = (GLFrame)((FilterContext)object).getFrameManager().duplicateFrame(frame);
        ((GLFrame)object).focus();
        this.renderBox(quad);
        this.pushOutput("image", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3, 3));
        this.addMaskedInputPort("box", ObjectFormat.fromClass(Quad.class, 1));
        this.addOutputBasedOnInput("image", "image");
    }
}

