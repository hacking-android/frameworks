/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GLEnvironment;
import android.filterfw.core.GLFrame;
import android.filterfw.core.NativeAllocatorTag;
import android.filterfw.core.Program;
import android.filterfw.core.StopWatchMap;
import android.filterfw.core.VertexFrame;
import android.filterfw.geometry.Point;
import android.filterfw.geometry.Quad;
import android.opengl.GLES20;

public class ShaderProgram
extends Program {
    private GLEnvironment mGLEnvironment;
    private int mMaxTileSize = 0;
    private StopWatchMap mTimer = null;
    private int shaderProgramId;

    static {
        System.loadLibrary("filterfw");
    }

    private ShaderProgram() {
    }

    @UnsupportedAppUsage
    public ShaderProgram(FilterContext filterContext, String string2) {
        this.mGLEnvironment = ShaderProgram.getGLEnvironment(filterContext);
        this.allocate(this.mGLEnvironment, null, string2);
        if (this.compileAndLink()) {
            this.setTimer();
            return;
        }
        throw new RuntimeException("Could not compile and link shader!");
    }

    public ShaderProgram(FilterContext filterContext, String string2, String string3) {
        this.mGLEnvironment = ShaderProgram.getGLEnvironment(filterContext);
        this.allocate(this.mGLEnvironment, string2, string3);
        if (this.compileAndLink()) {
            this.setTimer();
            return;
        }
        throw new RuntimeException("Could not compile and link shader!");
    }

    private ShaderProgram(NativeAllocatorTag nativeAllocatorTag) {
    }

    private native boolean allocate(GLEnvironment var1, String var2, String var3);

    private native boolean beginShaderDrawing();

    private native boolean compileAndLink();

    @UnsupportedAppUsage
    public static ShaderProgram createIdentity(FilterContext object) {
        object = ShaderProgram.nativeCreateIdentity(ShaderProgram.getGLEnvironment((FilterContext)object));
        ShaderProgram.super.setTimer();
        return object;
    }

    private native boolean deallocate();

    private static GLEnvironment getGLEnvironment(FilterContext object) {
        if ((object = object != null ? ((FilterContext)object).getGLEnvironment() : null) != null) {
            return object;
        }
        throw new NullPointerException("Attempting to create ShaderProgram with no GL environment in place!");
    }

    private native Object getUniformValue(String var1);

    private static native ShaderProgram nativeCreateIdentity(GLEnvironment var0);

    private native boolean setShaderAttributeValues(String var1, float[] var2, int var3);

    private native boolean setShaderAttributeVertexFrame(String var1, VertexFrame var2, int var3, int var4, int var5, int var6, boolean var7);

    private native boolean setShaderBlendEnabled(boolean var1);

    private native boolean setShaderBlendFunc(int var1, int var2);

    private native boolean setShaderClearColor(float var1, float var2, float var3);

    private native boolean setShaderClearsOutput(boolean var1);

    private native boolean setShaderDrawMode(int var1);

    private native boolean setShaderTileCounts(int var1, int var2);

    private native boolean setShaderVertexCount(int var1);

    private native boolean setTargetRegion(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8);

    private void setTimer() {
        this.mTimer = new StopWatchMap();
    }

    private native boolean setUniformValue(String var1, Object var2);

    private native boolean shaderProcess(GLFrame[] var1, GLFrame var2);

    public void beginDrawing() {
        if (this.beginShaderDrawing()) {
            return;
        }
        throw new RuntimeException("Could not prepare shader-program for drawing!");
    }

    protected void finalize() throws Throwable {
        this.deallocate();
    }

    public GLEnvironment getGLEnvironment() {
        return this.mGLEnvironment;
    }

    @Override
    public Object getHostValue(String string2) {
        return this.getUniformValue(string2);
    }

    @UnsupportedAppUsage
    @Override
    public void process(Frame[] object, Frame frame) {
        int n;
        if (this.mTimer.LOG_MFF_RUNNING_TIMES) {
            this.mTimer.start("glFinish");
            GLES20.glFinish();
            this.mTimer.stop("glFinish");
        }
        GLFrame[] arrgLFrame = new GLFrame[((Frame[])object).length];
        for (n = 0; n < ((Frame[])object).length; ++n) {
            if (object[n] instanceof GLFrame) {
                arrgLFrame[n] = (GLFrame)object[n];
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("ShaderProgram got non-GL frame as input ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("!");
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        if (frame instanceof GLFrame) {
            object = (GLFrame)frame;
            if (this.mMaxTileSize > 0) {
                int n2 = frame.getFormat().getWidth();
                n = this.mMaxTileSize;
                int n3 = (n2 + n - 1) / n;
                n2 = frame.getFormat().getHeight();
                n = this.mMaxTileSize;
                this.setShaderTileCounts(n3, (n2 + n - 1) / n);
            }
            if (this.shaderProcess(arrgLFrame, (GLFrame)object)) {
                if (this.mTimer.LOG_MFF_RUNNING_TIMES) {
                    GLES20.glFinish();
                }
                return;
            }
            throw new RuntimeException("Error executing ShaderProgram!");
        }
        throw new RuntimeException("ShaderProgram got non-GL output frame!");
    }

    public void setAttributeValues(String string2, VertexFrame object, int n, int n2, int n3, int n4, boolean bl) {
        if (this.setShaderAttributeVertexFrame(string2, (VertexFrame)object, n, n2, n3, n4, bl)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Error setting attribute value for attribute '");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("'!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public void setAttributeValues(String string2, float[] object, int n) {
        if (this.setShaderAttributeValues(string2, (float[])object, n)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Error setting attribute value for attribute '");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("'!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public void setBlendEnabled(boolean bl) {
        if (this.setShaderBlendEnabled(bl)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not set Blending ");
        stringBuilder.append(bl);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    public void setBlendFunc(int n, int n2) {
        if (this.setShaderBlendFunc(n, n2)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not set BlendFunc ");
        stringBuilder.append(n);
        stringBuilder.append(",");
        stringBuilder.append(n2);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    public void setClearColor(float f, float f2, float f3) {
        if (this.setShaderClearColor(f, f2, f3)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not set clear color to ");
        stringBuilder.append(f);
        stringBuilder.append(",");
        stringBuilder.append(f2);
        stringBuilder.append(",");
        stringBuilder.append(f3);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    public void setClearsOutput(boolean bl) {
        if (this.setShaderClearsOutput(bl)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not set clears-output flag to ");
        stringBuilder.append(bl);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    public void setDrawMode(int n) {
        if (this.setShaderDrawMode(n)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not set GL draw-mode to ");
        stringBuilder.append(n);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    @Override
    public void setHostValue(String string2, Object object) {
        if (this.setUniformValue(string2, object)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Error setting uniform value for variable '");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("'!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public void setMaximumTileSize(int n) {
        this.mMaxTileSize = n;
    }

    @UnsupportedAppUsage
    public void setSourceRect(float f, float f2, float f3, float f4) {
        this.setSourceRegion(f, f2, f + f3, f2, f, f2 + f4, f + f3, f2 + f4);
    }

    @UnsupportedAppUsage
    public void setSourceRegion(Quad quad) {
        this.setSourceRegion(quad.p0.x, quad.p0.y, quad.p1.x, quad.p1.y, quad.p2.x, quad.p2.y, quad.p3.x, quad.p3.y);
    }

    public native boolean setSourceRegion(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8);

    public void setTargetRect(float f, float f2, float f3, float f4) {
        this.setTargetRegion(f, f2, f + f3, f2, f, f2 + f4, f + f3, f2 + f4);
    }

    public void setTargetRegion(Quad quad) {
        this.setTargetRegion(quad.p0.x, quad.p0.y, quad.p1.x, quad.p1.y, quad.p2.x, quad.p2.y, quad.p3.x, quad.p3.y);
    }

    public void setVertexCount(int n) {
        if (this.setShaderVertexCount(n)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not set GL vertex count to ");
        stringBuilder.append(n);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }
}

