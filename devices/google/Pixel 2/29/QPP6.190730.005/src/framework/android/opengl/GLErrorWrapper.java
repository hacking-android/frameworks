/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.GLException;
import android.opengl.GLWrapperBase;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL10Ext;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;
import javax.microedition.khronos.opengles.GL11ExtensionPack;

class GLErrorWrapper
extends GLWrapperBase {
    boolean mCheckError;
    boolean mCheckThread;
    Thread mOurThread;

    public GLErrorWrapper(GL gL, int n) {
        super(gL);
        boolean bl = false;
        boolean bl2 = (n & 1) != 0;
        this.mCheckError = bl2;
        bl2 = bl;
        if ((n & 2) != 0) {
            bl2 = true;
        }
        this.mCheckThread = bl2;
    }

    private void checkError() {
        int n;
        if (this.mCheckError && (n = this.mgl.glGetError()) != 0) {
            throw new GLException(n);
        }
    }

    private void checkThread() {
        if (this.mCheckThread) {
            Thread thread = Thread.currentThread();
            Thread thread2 = this.mOurThread;
            if (thread2 == null) {
                this.mOurThread = thread;
            } else if (!thread2.equals(thread)) {
                throw new GLException(28672, "OpenGL method called from wrong thread.");
            }
        }
    }

    @Override
    public void glActiveTexture(int n) {
        this.checkThread();
        this.mgl.glActiveTexture(n);
        this.checkError();
    }

    @Override
    public void glAlphaFunc(int n, float f) {
        this.checkThread();
        this.mgl.glAlphaFunc(n, f);
        this.checkError();
    }

    @Override
    public void glAlphaFuncx(int n, int n2) {
        this.checkThread();
        this.mgl.glAlphaFuncx(n, n2);
        this.checkError();
    }

    @Override
    public void glBindBuffer(int n, int n2) {
        this.checkThread();
        this.mgl11.glBindBuffer(n, n2);
        this.checkError();
    }

    @Override
    public void glBindFramebufferOES(int n, int n2) {
        this.checkThread();
        this.mgl11ExtensionPack.glBindFramebufferOES(n, n2);
        this.checkError();
    }

    @Override
    public void glBindRenderbufferOES(int n, int n2) {
        this.checkThread();
        this.mgl11ExtensionPack.glBindRenderbufferOES(n, n2);
        this.checkError();
    }

    @Override
    public void glBindTexture(int n, int n2) {
        this.checkThread();
        this.mgl.glBindTexture(n, n2);
        this.checkError();
    }

    @Override
    public void glBlendEquation(int n) {
        this.checkThread();
        this.mgl11ExtensionPack.glBlendEquation(n);
        this.checkError();
    }

    @Override
    public void glBlendEquationSeparate(int n, int n2) {
        this.checkThread();
        this.mgl11ExtensionPack.glBlendEquationSeparate(n, n2);
        this.checkError();
    }

    @Override
    public void glBlendFunc(int n, int n2) {
        this.checkThread();
        this.mgl.glBlendFunc(n, n2);
        this.checkError();
    }

    @Override
    public void glBlendFuncSeparate(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl11ExtensionPack.glBlendFuncSeparate(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glBufferData(int n, int n2, Buffer buffer, int n3) {
        this.checkThread();
        this.mgl11.glBufferData(n, n2, buffer, n3);
        this.checkError();
    }

    @Override
    public void glBufferSubData(int n, int n2, int n3, Buffer buffer) {
        this.checkThread();
        this.mgl11.glBufferSubData(n, n2, n3, buffer);
        this.checkError();
    }

    @Override
    public int glCheckFramebufferStatusOES(int n) {
        this.checkThread();
        n = this.mgl11ExtensionPack.glCheckFramebufferStatusOES(n);
        this.checkError();
        return n;
    }

    @Override
    public void glClear(int n) {
        this.checkThread();
        this.mgl.glClear(n);
        this.checkError();
    }

    @Override
    public void glClearColor(float f, float f2, float f3, float f4) {
        this.checkThread();
        this.mgl.glClearColor(f, f2, f3, f4);
        this.checkError();
    }

    @Override
    public void glClearColorx(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl.glClearColorx(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glClearDepthf(float f) {
        this.checkThread();
        this.mgl.glClearDepthf(f);
        this.checkError();
    }

    @Override
    public void glClearDepthx(int n) {
        this.checkThread();
        this.mgl.glClearDepthx(n);
        this.checkError();
    }

    @Override
    public void glClearStencil(int n) {
        this.checkThread();
        this.mgl.glClearStencil(n);
        this.checkError();
    }

    @Override
    public void glClientActiveTexture(int n) {
        this.checkThread();
        this.mgl.glClientActiveTexture(n);
        this.checkError();
    }

    @Override
    public void glClipPlanef(int n, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl11.glClipPlanef(n, floatBuffer);
        this.checkError();
    }

    @Override
    public void glClipPlanef(int n, float[] arrf, int n2) {
        this.checkThread();
        this.mgl11.glClipPlanef(n, arrf, n2);
        this.checkError();
    }

    @Override
    public void glClipPlanex(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glClipPlanex(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glClipPlanex(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl11.glClipPlanex(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glColor4f(float f, float f2, float f3, float f4) {
        this.checkThread();
        this.mgl.glColor4f(f, f2, f3, f4);
        this.checkError();
    }

    @Override
    public void glColor4ub(byte by, byte by2, byte by3, byte by4) {
        this.checkThread();
        this.mgl11.glColor4ub(by, by2, by3, by4);
        this.checkError();
    }

    @Override
    public void glColor4x(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl.glColor4x(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glColorMask(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.checkThread();
        this.mgl.glColorMask(bl, bl2, bl3, bl4);
        this.checkError();
    }

    @Override
    public void glColorPointer(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl11.glColorPointer(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glColorPointer(int n, int n2, int n3, Buffer buffer) {
        this.checkThread();
        this.mgl.glColorPointer(n, n2, n3, buffer);
        this.checkError();
    }

    @Override
    public void glCompressedTexImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, Buffer buffer) {
        this.checkThread();
        this.mgl.glCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, buffer);
        this.checkError();
    }

    @Override
    public void glCompressedTexSubImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, Buffer buffer) {
        this.checkThread();
        this.mgl.glCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.checkError();
    }

    @Override
    public void glCopyTexImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this.checkThread();
        this.mgl.glCopyTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        this.checkError();
    }

    @Override
    public void glCopyTexSubImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this.checkThread();
        this.mgl.glCopyTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        this.checkError();
    }

    @Override
    public void glCullFace(int n) {
        this.checkThread();
        this.mgl.glCullFace(n);
        this.checkError();
    }

    @Override
    public void glCurrentPaletteMatrixOES(int n) {
        this.checkThread();
        this.mgl11Ext.glCurrentPaletteMatrixOES(n);
        this.checkError();
    }

    @Override
    public void glDeleteBuffers(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glDeleteBuffers(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glDeleteBuffers(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl11.glDeleteBuffers(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glDeleteFramebuffersOES(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11ExtensionPack.glDeleteFramebuffersOES(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glDeleteFramebuffersOES(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl11ExtensionPack.glDeleteFramebuffersOES(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glDeleteRenderbuffersOES(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11ExtensionPack.glDeleteRenderbuffersOES(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glDeleteRenderbuffersOES(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl11ExtensionPack.glDeleteRenderbuffersOES(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glDeleteTextures(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl.glDeleteTextures(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glDeleteTextures(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl.glDeleteTextures(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glDepthFunc(int n) {
        this.checkThread();
        this.mgl.glDepthFunc(n);
        this.checkError();
    }

    @Override
    public void glDepthMask(boolean bl) {
        this.checkThread();
        this.mgl.glDepthMask(bl);
        this.checkError();
    }

    @Override
    public void glDepthRangef(float f, float f2) {
        this.checkThread();
        this.mgl.glDepthRangef(f, f2);
        this.checkError();
    }

    @Override
    public void glDepthRangex(int n, int n2) {
        this.checkThread();
        this.mgl.glDepthRangex(n, n2);
        this.checkError();
    }

    @Override
    public void glDisable(int n) {
        this.checkThread();
        this.mgl.glDisable(n);
        this.checkError();
    }

    @Override
    public void glDisableClientState(int n) {
        this.checkThread();
        this.mgl.glDisableClientState(n);
        this.checkError();
    }

    @Override
    public void glDrawArrays(int n, int n2, int n3) {
        this.checkThread();
        this.mgl.glDrawArrays(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glDrawElements(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl11.glDrawElements(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glDrawElements(int n, int n2, int n3, Buffer buffer) {
        this.checkThread();
        this.mgl.glDrawElements(n, n2, n3, buffer);
        this.checkError();
    }

    @Override
    public void glDrawTexfOES(float f, float f2, float f3, float f4, float f5) {
        this.checkThread();
        this.mgl11Ext.glDrawTexfOES(f, f2, f3, f4, f5);
        this.checkError();
    }

    @Override
    public void glDrawTexfvOES(FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl11Ext.glDrawTexfvOES(floatBuffer);
        this.checkError();
    }

    @Override
    public void glDrawTexfvOES(float[] arrf, int n) {
        this.checkThread();
        this.mgl11Ext.glDrawTexfvOES(arrf, n);
        this.checkError();
    }

    @Override
    public void glDrawTexiOES(int n, int n2, int n3, int n4, int n5) {
        this.checkThread();
        this.mgl11Ext.glDrawTexiOES(n, n2, n3, n4, n5);
        this.checkError();
    }

    @Override
    public void glDrawTexivOES(IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11Ext.glDrawTexivOES(intBuffer);
        this.checkError();
    }

    @Override
    public void glDrawTexivOES(int[] arrn, int n) {
        this.checkThread();
        this.mgl11Ext.glDrawTexivOES(arrn, n);
        this.checkError();
    }

    @Override
    public void glDrawTexsOES(short s, short s2, short s3, short s4, short s5) {
        this.checkThread();
        this.mgl11Ext.glDrawTexsOES(s, s2, s3, s4, s5);
        this.checkError();
    }

    @Override
    public void glDrawTexsvOES(ShortBuffer shortBuffer) {
        this.checkThread();
        this.mgl11Ext.glDrawTexsvOES(shortBuffer);
        this.checkError();
    }

    @Override
    public void glDrawTexsvOES(short[] arrs, int n) {
        this.checkThread();
        this.mgl11Ext.glDrawTexsvOES(arrs, n);
        this.checkError();
    }

    @Override
    public void glDrawTexxOES(int n, int n2, int n3, int n4, int n5) {
        this.checkThread();
        this.mgl11Ext.glDrawTexxOES(n, n2, n3, n4, n5);
        this.checkError();
    }

    @Override
    public void glDrawTexxvOES(IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11Ext.glDrawTexxvOES(intBuffer);
        this.checkError();
    }

    @Override
    public void glDrawTexxvOES(int[] arrn, int n) {
        this.checkThread();
        this.mgl11Ext.glDrawTexxvOES(arrn, n);
        this.checkError();
    }

    @Override
    public void glEnable(int n) {
        this.checkThread();
        this.mgl.glEnable(n);
        this.checkError();
    }

    @Override
    public void glEnableClientState(int n) {
        this.checkThread();
        this.mgl.glEnableClientState(n);
        this.checkError();
    }

    @Override
    public void glFinish() {
        this.checkThread();
        this.mgl.glFinish();
        this.checkError();
    }

    @Override
    public void glFlush() {
        this.checkThread();
        this.mgl.glFlush();
        this.checkError();
    }

    @Override
    public void glFogf(int n, float f) {
        this.checkThread();
        this.mgl.glFogf(n, f);
        this.checkError();
    }

    @Override
    public void glFogfv(int n, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl.glFogfv(n, floatBuffer);
        this.checkError();
    }

    @Override
    public void glFogfv(int n, float[] arrf, int n2) {
        this.checkThread();
        this.mgl.glFogfv(n, arrf, n2);
        this.checkError();
    }

    @Override
    public void glFogx(int n, int n2) {
        this.checkThread();
        this.mgl.glFogx(n, n2);
        this.checkError();
    }

    @Override
    public void glFogxv(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl.glFogxv(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glFogxv(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl.glFogxv(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glFramebufferRenderbufferOES(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl11ExtensionPack.glFramebufferRenderbufferOES(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glFramebufferTexture2DOES(int n, int n2, int n3, int n4, int n5) {
        this.checkThread();
        this.mgl11ExtensionPack.glFramebufferTexture2DOES(n, n2, n3, n4, n5);
        this.checkError();
    }

    @Override
    public void glFrontFace(int n) {
        this.checkThread();
        this.mgl.glFrontFace(n);
        this.checkError();
    }

    @Override
    public void glFrustumf(float f, float f2, float f3, float f4, float f5, float f6) {
        this.checkThread();
        this.mgl.glFrustumf(f, f2, f3, f4, f5, f6);
        this.checkError();
    }

    @Override
    public void glFrustumx(int n, int n2, int n3, int n4, int n5, int n6) {
        this.checkThread();
        this.mgl.glFrustumx(n, n2, n3, n4, n5, n6);
        this.checkError();
    }

    @Override
    public void glGenBuffers(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glGenBuffers(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGenBuffers(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl11.glGenBuffers(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glGenFramebuffersOES(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11ExtensionPack.glGenFramebuffersOES(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGenFramebuffersOES(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl11ExtensionPack.glGenFramebuffersOES(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glGenRenderbuffersOES(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11ExtensionPack.glGenRenderbuffersOES(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGenRenderbuffersOES(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl11ExtensionPack.glGenRenderbuffersOES(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glGenTextures(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl.glGenTextures(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGenTextures(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl.glGenTextures(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glGenerateMipmapOES(int n) {
        this.checkThread();
        this.mgl11ExtensionPack.glGenerateMipmapOES(n);
        this.checkError();
    }

    @Override
    public void glGetBooleanv(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glGetBooleanv(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetBooleanv(int n, boolean[] arrbl, int n2) {
        this.checkThread();
        this.mgl11.glGetBooleanv(n, arrbl, n2);
        this.checkError();
    }

    @Override
    public void glGetBufferParameteriv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glGetBufferParameteriv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetBufferParameteriv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11.glGetBufferParameteriv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetClipPlanef(int n, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl11.glGetClipPlanef(n, floatBuffer);
        this.checkError();
    }

    @Override
    public void glGetClipPlanef(int n, float[] arrf, int n2) {
        this.checkThread();
        this.mgl11.glGetClipPlanef(n, arrf, n2);
        this.checkError();
    }

    @Override
    public void glGetClipPlanex(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glGetClipPlanex(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetClipPlanex(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl11.glGetClipPlanex(n, arrn, n2);
        this.checkError();
    }

    @Override
    public int glGetError() {
        this.checkThread();
        return this.mgl.glGetError();
    }

    @Override
    public void glGetFixedv(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glGetFixedv(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetFixedv(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl11.glGetFixedv(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glGetFloatv(int n, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl11.glGetFloatv(n, floatBuffer);
        this.checkError();
    }

    @Override
    public void glGetFloatv(int n, float[] arrf, int n2) {
        this.checkThread();
        this.mgl11.glGetFloatv(n, arrf, n2);
        this.checkError();
    }

    @Override
    public void glGetFramebufferAttachmentParameterivOES(int n, int n2, int n3, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11ExtensionPack.glGetFramebufferAttachmentParameterivOES(n, n2, n3, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetFramebufferAttachmentParameterivOES(int n, int n2, int n3, int[] arrn, int n4) {
        this.checkThread();
        this.mgl11ExtensionPack.glGetFramebufferAttachmentParameterivOES(n, n2, n3, arrn, n4);
        this.checkError();
    }

    @Override
    public void glGetIntegerv(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl.glGetIntegerv(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetIntegerv(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl.glGetIntegerv(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glGetLightfv(int n, int n2, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl11.glGetLightfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glGetLightfv(int n, int n2, float[] arrf, int n3) {
        this.checkThread();
        this.mgl11.glGetLightfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glGetLightxv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glGetLightxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetLightxv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11.glGetLightxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetMaterialfv(int n, int n2, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl11.glGetMaterialfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glGetMaterialfv(int n, int n2, float[] arrf, int n3) {
        this.checkThread();
        this.mgl11.glGetMaterialfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glGetMaterialxv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glGetMaterialxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetMaterialxv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11.glGetMaterialxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetPointerv(int n, Buffer[] arrbuffer) {
        this.checkThread();
        this.mgl11.glGetPointerv(n, arrbuffer);
        this.checkError();
    }

    @Override
    public void glGetRenderbufferParameterivOES(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11ExtensionPack.glGetRenderbufferParameterivOES(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetRenderbufferParameterivOES(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11ExtensionPack.glGetRenderbufferParameterivOES(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public String glGetString(int n) {
        this.checkThread();
        String string2 = this.mgl.glGetString(n);
        this.checkError();
        return string2;
    }

    @Override
    public void glGetTexEnviv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glGetTexEnviv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexEnviv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11.glGetTexEnviv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetTexEnvxv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glGetTexEnvxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexEnvxv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11.glGetTexEnvxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetTexGenfv(int n, int n2, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl11ExtensionPack.glGetTexGenfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexGenfv(int n, int n2, float[] arrf, int n3) {
        this.checkThread();
        this.mgl11ExtensionPack.glGetTexGenfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glGetTexGeniv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11ExtensionPack.glGetTexGeniv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexGeniv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11ExtensionPack.glGetTexGeniv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetTexGenxv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11ExtensionPack.glGetTexGenxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexGenxv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11ExtensionPack.glGetTexGenxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetTexParameterfv(int n, int n2, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl11.glGetTexParameterfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexParameterfv(int n, int n2, float[] arrf, int n3) {
        this.checkThread();
        this.mgl11.glGetTexParameterfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glGetTexParameteriv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glGetTexParameteriv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexParameteriv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11.glGetTexParameteriv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetTexParameterxv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glGetTexParameterxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexParameterxv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11.glGetTexParameterxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glHint(int n, int n2) {
        this.checkThread();
        this.mgl.glHint(n, n2);
        this.checkError();
    }

    @Override
    public boolean glIsBuffer(int n) {
        this.checkThread();
        boolean bl = this.mgl11.glIsBuffer(n);
        this.checkError();
        return bl;
    }

    @Override
    public boolean glIsEnabled(int n) {
        this.checkThread();
        boolean bl = this.mgl11.glIsEnabled(n);
        this.checkError();
        return bl;
    }

    @Override
    public boolean glIsFramebufferOES(int n) {
        this.checkThread();
        boolean bl = this.mgl11ExtensionPack.glIsFramebufferOES(n);
        this.checkError();
        return bl;
    }

    @Override
    public boolean glIsRenderbufferOES(int n) {
        this.checkThread();
        this.mgl11ExtensionPack.glIsRenderbufferOES(n);
        this.checkError();
        return false;
    }

    @Override
    public boolean glIsTexture(int n) {
        this.checkThread();
        boolean bl = this.mgl11.glIsTexture(n);
        this.checkError();
        return bl;
    }

    @Override
    public void glLightModelf(int n, float f) {
        this.checkThread();
        this.mgl.glLightModelf(n, f);
        this.checkError();
    }

    @Override
    public void glLightModelfv(int n, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl.glLightModelfv(n, floatBuffer);
        this.checkError();
    }

    @Override
    public void glLightModelfv(int n, float[] arrf, int n2) {
        this.checkThread();
        this.mgl.glLightModelfv(n, arrf, n2);
        this.checkError();
    }

    @Override
    public void glLightModelx(int n, int n2) {
        this.checkThread();
        this.mgl.glLightModelx(n, n2);
        this.checkError();
    }

    @Override
    public void glLightModelxv(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl.glLightModelxv(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glLightModelxv(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl.glLightModelxv(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glLightf(int n, int n2, float f) {
        this.checkThread();
        this.mgl.glLightf(n, n2, f);
        this.checkError();
    }

    @Override
    public void glLightfv(int n, int n2, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl.glLightfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glLightfv(int n, int n2, float[] arrf, int n3) {
        this.checkThread();
        this.mgl.glLightfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glLightx(int n, int n2, int n3) {
        this.checkThread();
        this.mgl.glLightx(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glLightxv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl.glLightxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glLightxv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl.glLightxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glLineWidth(float f) {
        this.checkThread();
        this.mgl.glLineWidth(f);
        this.checkError();
    }

    @Override
    public void glLineWidthx(int n) {
        this.checkThread();
        this.mgl.glLineWidthx(n);
        this.checkError();
    }

    @Override
    public void glLoadIdentity() {
        this.checkThread();
        this.mgl.glLoadIdentity();
        this.checkError();
    }

    @Override
    public void glLoadMatrixf(FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl.glLoadMatrixf(floatBuffer);
        this.checkError();
    }

    @Override
    public void glLoadMatrixf(float[] arrf, int n) {
        this.checkThread();
        this.mgl.glLoadMatrixf(arrf, n);
        this.checkError();
    }

    @Override
    public void glLoadMatrixx(IntBuffer intBuffer) {
        this.checkThread();
        this.mgl.glLoadMatrixx(intBuffer);
        this.checkError();
    }

    @Override
    public void glLoadMatrixx(int[] arrn, int n) {
        this.checkThread();
        this.mgl.glLoadMatrixx(arrn, n);
        this.checkError();
    }

    @Override
    public void glLoadPaletteFromModelViewMatrixOES() {
        this.checkThread();
        this.mgl11Ext.glLoadPaletteFromModelViewMatrixOES();
        this.checkError();
    }

    @Override
    public void glLogicOp(int n) {
        this.checkThread();
        this.mgl.glLogicOp(n);
        this.checkError();
    }

    @Override
    public void glMaterialf(int n, int n2, float f) {
        this.checkThread();
        this.mgl.glMaterialf(n, n2, f);
        this.checkError();
    }

    @Override
    public void glMaterialfv(int n, int n2, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl.glMaterialfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glMaterialfv(int n, int n2, float[] arrf, int n3) {
        this.checkThread();
        this.mgl.glMaterialfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glMaterialx(int n, int n2, int n3) {
        this.checkThread();
        this.mgl.glMaterialx(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glMaterialxv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl.glMaterialxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glMaterialxv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl.glMaterialxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glMatrixIndexPointerOES(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl11Ext.glMatrixIndexPointerOES(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glMatrixIndexPointerOES(int n, int n2, int n3, Buffer buffer) {
        this.checkThread();
        this.mgl11Ext.glMatrixIndexPointerOES(n, n2, n3, buffer);
        this.checkError();
    }

    @Override
    public void glMatrixMode(int n) {
        this.checkThread();
        this.mgl.glMatrixMode(n);
        this.checkError();
    }

    @Override
    public void glMultMatrixf(FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl.glMultMatrixf(floatBuffer);
        this.checkError();
    }

    @Override
    public void glMultMatrixf(float[] arrf, int n) {
        this.checkThread();
        this.mgl.glMultMatrixf(arrf, n);
        this.checkError();
    }

    @Override
    public void glMultMatrixx(IntBuffer intBuffer) {
        this.checkThread();
        this.mgl.glMultMatrixx(intBuffer);
        this.checkError();
    }

    @Override
    public void glMultMatrixx(int[] arrn, int n) {
        this.checkThread();
        this.mgl.glMultMatrixx(arrn, n);
        this.checkError();
    }

    @Override
    public void glMultiTexCoord4f(int n, float f, float f2, float f3, float f4) {
        this.checkThread();
        this.mgl.glMultiTexCoord4f(n, f, f2, f3, f4);
        this.checkError();
    }

    @Override
    public void glMultiTexCoord4x(int n, int n2, int n3, int n4, int n5) {
        this.checkThread();
        this.mgl.glMultiTexCoord4x(n, n2, n3, n4, n5);
        this.checkError();
    }

    @Override
    public void glNormal3f(float f, float f2, float f3) {
        this.checkThread();
        this.mgl.glNormal3f(f, f2, f3);
        this.checkError();
    }

    @Override
    public void glNormal3x(int n, int n2, int n3) {
        this.checkThread();
        this.mgl.glNormal3x(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glNormalPointer(int n, int n2, int n3) {
        this.checkThread();
        this.mgl11.glNormalPointer(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glNormalPointer(int n, int n2, Buffer buffer) {
        this.checkThread();
        this.mgl.glNormalPointer(n, n2, buffer);
        this.checkError();
    }

    @Override
    public void glOrthof(float f, float f2, float f3, float f4, float f5, float f6) {
        this.checkThread();
        this.mgl.glOrthof(f, f2, f3, f4, f5, f6);
        this.checkError();
    }

    @Override
    public void glOrthox(int n, int n2, int n3, int n4, int n5, int n6) {
        this.checkThread();
        this.mgl.glOrthox(n, n2, n3, n4, n5, n6);
        this.checkError();
    }

    @Override
    public void glPixelStorei(int n, int n2) {
        this.checkThread();
        this.mgl.glPixelStorei(n, n2);
        this.checkError();
    }

    @Override
    public void glPointParameterf(int n, float f) {
        this.checkThread();
        this.mgl11.glPointParameterf(n, f);
        this.checkError();
    }

    @Override
    public void glPointParameterfv(int n, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl11.glPointParameterfv(n, floatBuffer);
        this.checkError();
    }

    @Override
    public void glPointParameterfv(int n, float[] arrf, int n2) {
        this.checkThread();
        this.mgl11.glPointParameterfv(n, arrf, n2);
        this.checkError();
    }

    @Override
    public void glPointParameterx(int n, int n2) {
        this.checkThread();
        this.mgl11.glPointParameterx(n, n2);
        this.checkError();
    }

    @Override
    public void glPointParameterxv(int n, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glPointParameterxv(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glPointParameterxv(int n, int[] arrn, int n2) {
        this.checkThread();
        this.mgl11.glPointParameterxv(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glPointSize(float f) {
        this.checkThread();
        this.mgl.glPointSize(f);
        this.checkError();
    }

    @Override
    public void glPointSizePointerOES(int n, int n2, Buffer buffer) {
        this.checkThread();
        this.mgl11.glPointSizePointerOES(n, n2, buffer);
        this.checkError();
    }

    @Override
    public void glPointSizex(int n) {
        this.checkThread();
        this.mgl.glPointSizex(n);
        this.checkError();
    }

    @Override
    public void glPolygonOffset(float f, float f2) {
        this.checkThread();
        this.mgl.glPolygonOffset(f, f2);
        this.checkError();
    }

    @Override
    public void glPolygonOffsetx(int n, int n2) {
        this.checkThread();
        this.mgl.glPolygonOffsetx(n, n2);
        this.checkError();
    }

    @Override
    public void glPopMatrix() {
        this.checkThread();
        this.mgl.glPopMatrix();
        this.checkError();
    }

    @Override
    public void glPushMatrix() {
        this.checkThread();
        this.mgl.glPushMatrix();
        this.checkError();
    }

    @Override
    public int glQueryMatrixxOES(IntBuffer intBuffer, IntBuffer intBuffer2) {
        this.checkThread();
        int n = this.mgl10Ext.glQueryMatrixxOES(intBuffer, intBuffer2);
        this.checkError();
        return n;
    }

    @Override
    public int glQueryMatrixxOES(int[] arrn, int n, int[] arrn2, int n2) {
        this.checkThread();
        n = this.mgl10Ext.glQueryMatrixxOES(arrn, n, arrn2, n2);
        this.checkError();
        return n;
    }

    @Override
    public void glReadPixels(int n, int n2, int n3, int n4, int n5, int n6, Buffer buffer) {
        this.checkThread();
        this.mgl.glReadPixels(n, n2, n3, n4, n5, n6, buffer);
        this.checkError();
    }

    @Override
    public void glRenderbufferStorageOES(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl11ExtensionPack.glRenderbufferStorageOES(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glRotatef(float f, float f2, float f3, float f4) {
        this.checkThread();
        this.mgl.glRotatef(f, f2, f3, f4);
        this.checkError();
    }

    @Override
    public void glRotatex(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl.glRotatex(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glSampleCoverage(float f, boolean bl) {
        this.checkThread();
        this.mgl.glSampleCoverage(f, bl);
        this.checkError();
    }

    @Override
    public void glSampleCoveragex(int n, boolean bl) {
        this.checkThread();
        this.mgl.glSampleCoveragex(n, bl);
        this.checkError();
    }

    @Override
    public void glScalef(float f, float f2, float f3) {
        this.checkThread();
        this.mgl.glScalef(f, f2, f3);
        this.checkError();
    }

    @Override
    public void glScalex(int n, int n2, int n3) {
        this.checkThread();
        this.mgl.glScalex(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glScissor(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl.glScissor(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glShadeModel(int n) {
        this.checkThread();
        this.mgl.glShadeModel(n);
        this.checkError();
    }

    @Override
    public void glStencilFunc(int n, int n2, int n3) {
        this.checkThread();
        this.mgl.glStencilFunc(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glStencilMask(int n) {
        this.checkThread();
        this.mgl.glStencilMask(n);
        this.checkError();
    }

    @Override
    public void glStencilOp(int n, int n2, int n3) {
        this.checkThread();
        this.mgl.glStencilOp(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexCoordPointer(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl11.glTexCoordPointer(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glTexCoordPointer(int n, int n2, int n3, Buffer buffer) {
        this.checkThread();
        this.mgl.glTexCoordPointer(n, n2, n3, buffer);
        this.checkError();
    }

    @Override
    public void glTexEnvf(int n, int n2, float f) {
        this.checkThread();
        this.mgl.glTexEnvf(n, n2, f);
        this.checkError();
    }

    @Override
    public void glTexEnvfv(int n, int n2, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl.glTexEnvfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glTexEnvfv(int n, int n2, float[] arrf, int n3) {
        this.checkThread();
        this.mgl.glTexEnvfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glTexEnvi(int n, int n2, int n3) {
        this.checkThread();
        this.mgl11.glTexEnvi(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexEnviv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glTexEnviv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glTexEnviv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11.glTexEnviv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glTexEnvx(int n, int n2, int n3) {
        this.checkThread();
        this.mgl.glTexEnvx(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexEnvxv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl.glTexEnvxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glTexEnvxv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl.glTexEnvxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glTexGenf(int n, int n2, float f) {
        this.checkThread();
        this.mgl11ExtensionPack.glTexGenf(n, n2, f);
        this.checkError();
    }

    @Override
    public void glTexGenfv(int n, int n2, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl11ExtensionPack.glTexGenfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glTexGenfv(int n, int n2, float[] arrf, int n3) {
        this.checkThread();
        this.mgl11ExtensionPack.glTexGenfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glTexGeni(int n, int n2, int n3) {
        this.checkThread();
        this.mgl11ExtensionPack.glTexGeni(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexGeniv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11ExtensionPack.glTexGeniv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glTexGeniv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11ExtensionPack.glTexGeniv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glTexGenx(int n, int n2, int n3) {
        this.checkThread();
        this.mgl11ExtensionPack.glTexGenx(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexGenxv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11ExtensionPack.glTexGenxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glTexGenxv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11ExtensionPack.glTexGenxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glTexImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, Buffer buffer) {
        this.checkThread();
        this.mgl.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.checkError();
    }

    @Override
    public void glTexParameterf(int n, int n2, float f) {
        this.checkThread();
        this.mgl.glTexParameterf(n, n2, f);
        this.checkError();
    }

    @Override
    public void glTexParameterfv(int n, int n2, FloatBuffer floatBuffer) {
        this.checkThread();
        this.mgl11.glTexParameterfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glTexParameterfv(int n, int n2, float[] arrf, int n3) {
        this.checkThread();
        this.mgl11.glTexParameterfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glTexParameteri(int n, int n2, int n3) {
        this.checkThread();
        this.mgl11.glTexParameteri(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexParameteriv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glTexParameteriv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glTexParameteriv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11.glTexParameteriv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glTexParameterx(int n, int n2, int n3) {
        this.checkThread();
        this.mgl.glTexParameterx(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexParameterxv(int n, int n2, IntBuffer intBuffer) {
        this.checkThread();
        this.mgl11.glTexParameterxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glTexParameterxv(int n, int n2, int[] arrn, int n3) {
        this.checkThread();
        this.mgl11.glTexParameterxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glTexSubImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, Buffer buffer) {
        this.checkThread();
        this.mgl.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.checkError();
    }

    @Override
    public void glTranslatef(float f, float f2, float f3) {
        this.checkThread();
        this.mgl.glTranslatef(f, f2, f3);
        this.checkError();
    }

    @Override
    public void glTranslatex(int n, int n2, int n3) {
        this.checkThread();
        this.mgl.glTranslatex(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glVertexPointer(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl11.glVertexPointer(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glVertexPointer(int n, int n2, int n3, Buffer buffer) {
        this.checkThread();
        this.mgl.glVertexPointer(n, n2, n3, buffer);
        this.checkError();
    }

    @Override
    public void glViewport(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl.glViewport(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glWeightPointerOES(int n, int n2, int n3, int n4) {
        this.checkThread();
        this.mgl11Ext.glWeightPointerOES(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glWeightPointerOES(int n, int n2, int n3, Buffer buffer) {
        this.checkThread();
        this.mgl11Ext.glWeightPointerOES(n, n2, n3, buffer);
        this.checkError();
    }
}

