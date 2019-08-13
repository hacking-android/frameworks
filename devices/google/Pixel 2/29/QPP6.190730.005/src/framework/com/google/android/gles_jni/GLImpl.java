/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.gles_jni;

import android.app.AppGlobals;
import android.content.pm.ApplicationInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL10Ext;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;
import javax.microedition.khronos.opengles.GL11ExtensionPack;

public class GLImpl
implements GL10,
GL10Ext,
GL11,
GL11Ext,
GL11ExtensionPack {
    Buffer _colorPointer = null;
    Buffer _matrixIndexPointerOES = null;
    Buffer _normalPointer = null;
    Buffer _pointSizePointerOES = null;
    Buffer _texCoordPointer = null;
    Buffer _vertexPointer = null;
    Buffer _weightPointerOES = null;
    private boolean haveCheckedExtensions;
    private boolean have_OES_blend_equation_separate;
    private boolean have_OES_blend_subtract;
    private boolean have_OES_framebuffer_object;
    private boolean have_OES_texture_cube_map;

    static {
        GLImpl._nativeClassInit();
    }

    private static native void _nativeClassInit();

    private static boolean allowIndirectBuffers(String string2) {
        int n;
        boolean bl;
        block4 : {
            bl = false;
            int n2 = 0;
            n = 0;
            Object object = AppGlobals.getPackageManager();
            object = object.getApplicationInfo(string2, 0, UserHandle.myUserId());
            if (object == null) break block4;
            try {
                n = ((ApplicationInfo)object).targetSdkVersion;
            }
            catch (RemoteException remoteException) {
                n = n2;
            }
        }
        Log.e("OpenGLES", String.format("Application %s (SDK target %d) called a GL11 Pointer method with an indirect Buffer.", string2, n));
        if (n <= 3) {
            bl = true;
        }
        return bl;
    }

    private native void glColorPointerBounds(int var1, int var2, int var3, Buffer var4, int var5);

    private native void glMatrixIndexPointerOESBounds(int var1, int var2, int var3, Buffer var4, int var5);

    private native void glNormalPointerBounds(int var1, int var2, Buffer var3, int var4);

    private native void glPointSizePointerOESBounds(int var1, int var2, Buffer var3, int var4);

    private native void glTexCoordPointerBounds(int var1, int var2, int var3, Buffer var4, int var5);

    private native void glVertexPointerBounds(int var1, int var2, int var3, Buffer var4, int var5);

    private native void glWeightPointerOESBounds(int var1, int var2, int var3, Buffer var4, int var5);

    public native String _glGetString(int var1);

    @Override
    public native void glActiveTexture(int var1);

    @Override
    public native void glAlphaFunc(int var1, float var2);

    @Override
    public native void glAlphaFuncx(int var1, int var2);

    @Override
    public native void glBindBuffer(int var1, int var2);

    @Override
    public native void glBindFramebufferOES(int var1, int var2);

    @Override
    public native void glBindRenderbufferOES(int var1, int var2);

    @Override
    public native void glBindTexture(int var1, int var2);

    @Override
    public native void glBlendEquation(int var1);

    @Override
    public native void glBlendEquationSeparate(int var1, int var2);

    @Override
    public native void glBlendFunc(int var1, int var2);

    @Override
    public native void glBlendFuncSeparate(int var1, int var2, int var3, int var4);

    @Override
    public native void glBufferData(int var1, int var2, Buffer var3, int var4);

    @Override
    public native void glBufferSubData(int var1, int var2, int var3, Buffer var4);

    @Override
    public native int glCheckFramebufferStatusOES(int var1);

    @Override
    public native void glClear(int var1);

    @Override
    public native void glClearColor(float var1, float var2, float var3, float var4);

    @Override
    public native void glClearColorx(int var1, int var2, int var3, int var4);

    @Override
    public native void glClearDepthf(float var1);

    @Override
    public native void glClearDepthx(int var1);

    @Override
    public native void glClearStencil(int var1);

    @Override
    public native void glClientActiveTexture(int var1);

    @Override
    public native void glClipPlanef(int var1, FloatBuffer var2);

    @Override
    public native void glClipPlanef(int var1, float[] var2, int var3);

    @Override
    public native void glClipPlanex(int var1, IntBuffer var2);

    @Override
    public native void glClipPlanex(int var1, int[] var2, int var3);

    @Override
    public native void glColor4f(float var1, float var2, float var3, float var4);

    @Override
    public native void glColor4ub(byte var1, byte var2, byte var3, byte var4);

    @Override
    public native void glColor4x(int var1, int var2, int var3, int var4);

    @Override
    public native void glColorMask(boolean var1, boolean var2, boolean var3, boolean var4);

    @Override
    public native void glColorPointer(int var1, int var2, int var3, int var4);

    @Override
    public void glColorPointer(int n, int n2, int n3, Buffer buffer) {
        this.glColorPointerBounds(n, n2, n3, buffer, buffer.remaining());
        if (n == 4 && (n2 == 5126 || n2 == 5121 || n2 == 5132) && n3 >= 0) {
            this._colorPointer = buffer;
        }
    }

    @Override
    public native void glCompressedTexImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, Buffer var8);

    @Override
    public native void glCompressedTexSubImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, Buffer var9);

    @Override
    public native void glCopyTexImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8);

    @Override
    public native void glCopyTexSubImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8);

    @Override
    public native void glCullFace(int var1);

    @Override
    public native void glCurrentPaletteMatrixOES(int var1);

    @Override
    public native void glDeleteBuffers(int var1, IntBuffer var2);

    @Override
    public native void glDeleteBuffers(int var1, int[] var2, int var3);

    @Override
    public native void glDeleteFramebuffersOES(int var1, IntBuffer var2);

    @Override
    public native void glDeleteFramebuffersOES(int var1, int[] var2, int var3);

    @Override
    public native void glDeleteRenderbuffersOES(int var1, IntBuffer var2);

    @Override
    public native void glDeleteRenderbuffersOES(int var1, int[] var2, int var3);

    @Override
    public native void glDeleteTextures(int var1, IntBuffer var2);

    @Override
    public native void glDeleteTextures(int var1, int[] var2, int var3);

    @Override
    public native void glDepthFunc(int var1);

    @Override
    public native void glDepthMask(boolean var1);

    @Override
    public native void glDepthRangef(float var1, float var2);

    @Override
    public native void glDepthRangex(int var1, int var2);

    @Override
    public native void glDisable(int var1);

    @Override
    public native void glDisableClientState(int var1);

    @Override
    public native void glDrawArrays(int var1, int var2, int var3);

    @Override
    public native void glDrawElements(int var1, int var2, int var3, int var4);

    @Override
    public native void glDrawElements(int var1, int var2, int var3, Buffer var4);

    @Override
    public native void glDrawTexfOES(float var1, float var2, float var3, float var4, float var5);

    @Override
    public native void glDrawTexfvOES(FloatBuffer var1);

    @Override
    public native void glDrawTexfvOES(float[] var1, int var2);

    @Override
    public native void glDrawTexiOES(int var1, int var2, int var3, int var4, int var5);

    @Override
    public native void glDrawTexivOES(IntBuffer var1);

    @Override
    public native void glDrawTexivOES(int[] var1, int var2);

    @Override
    public native void glDrawTexsOES(short var1, short var2, short var3, short var4, short var5);

    @Override
    public native void glDrawTexsvOES(ShortBuffer var1);

    @Override
    public native void glDrawTexsvOES(short[] var1, int var2);

    @Override
    public native void glDrawTexxOES(int var1, int var2, int var3, int var4, int var5);

    @Override
    public native void glDrawTexxvOES(IntBuffer var1);

    @Override
    public native void glDrawTexxvOES(int[] var1, int var2);

    @Override
    public native void glEnable(int var1);

    @Override
    public native void glEnableClientState(int var1);

    @Override
    public native void glFinish();

    @Override
    public native void glFlush();

    @Override
    public native void glFogf(int var1, float var2);

    @Override
    public native void glFogfv(int var1, FloatBuffer var2);

    @Override
    public native void glFogfv(int var1, float[] var2, int var3);

    @Override
    public native void glFogx(int var1, int var2);

    @Override
    public native void glFogxv(int var1, IntBuffer var2);

    @Override
    public native void glFogxv(int var1, int[] var2, int var3);

    @Override
    public native void glFramebufferRenderbufferOES(int var1, int var2, int var3, int var4);

    @Override
    public native void glFramebufferTexture2DOES(int var1, int var2, int var3, int var4, int var5);

    @Override
    public native void glFrontFace(int var1);

    @Override
    public native void glFrustumf(float var1, float var2, float var3, float var4, float var5, float var6);

    @Override
    public native void glFrustumx(int var1, int var2, int var3, int var4, int var5, int var6);

    @Override
    public native void glGenBuffers(int var1, IntBuffer var2);

    @Override
    public native void glGenBuffers(int var1, int[] var2, int var3);

    @Override
    public native void glGenFramebuffersOES(int var1, IntBuffer var2);

    @Override
    public native void glGenFramebuffersOES(int var1, int[] var2, int var3);

    @Override
    public native void glGenRenderbuffersOES(int var1, IntBuffer var2);

    @Override
    public native void glGenRenderbuffersOES(int var1, int[] var2, int var3);

    @Override
    public native void glGenTextures(int var1, IntBuffer var2);

    @Override
    public native void glGenTextures(int var1, int[] var2, int var3);

    @Override
    public native void glGenerateMipmapOES(int var1);

    @Override
    public native void glGetBooleanv(int var1, IntBuffer var2);

    @Override
    public native void glGetBooleanv(int var1, boolean[] var2, int var3);

    @Override
    public native void glGetBufferParameteriv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glGetBufferParameteriv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glGetClipPlanef(int var1, FloatBuffer var2);

    @Override
    public native void glGetClipPlanef(int var1, float[] var2, int var3);

    @Override
    public native void glGetClipPlanex(int var1, IntBuffer var2);

    @Override
    public native void glGetClipPlanex(int var1, int[] var2, int var3);

    @Override
    public native int glGetError();

    @Override
    public native void glGetFixedv(int var1, IntBuffer var2);

    @Override
    public native void glGetFixedv(int var1, int[] var2, int var3);

    @Override
    public native void glGetFloatv(int var1, FloatBuffer var2);

    @Override
    public native void glGetFloatv(int var1, float[] var2, int var3);

    @Override
    public native void glGetFramebufferAttachmentParameterivOES(int var1, int var2, int var3, IntBuffer var4);

    @Override
    public native void glGetFramebufferAttachmentParameterivOES(int var1, int var2, int var3, int[] var4, int var5);

    @Override
    public native void glGetIntegerv(int var1, IntBuffer var2);

    @Override
    public native void glGetIntegerv(int var1, int[] var2, int var3);

    @Override
    public native void glGetLightfv(int var1, int var2, FloatBuffer var3);

    @Override
    public native void glGetLightfv(int var1, int var2, float[] var3, int var4);

    @Override
    public native void glGetLightxv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glGetLightxv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glGetMaterialfv(int var1, int var2, FloatBuffer var3);

    @Override
    public native void glGetMaterialfv(int var1, int var2, float[] var3, int var4);

    @Override
    public native void glGetMaterialxv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glGetMaterialxv(int var1, int var2, int[] var3, int var4);

    @Override
    public void glGetPointerv(int n, Buffer[] arrbuffer) {
        throw new UnsupportedOperationException("glGetPointerv");
    }

    @Override
    public native void glGetRenderbufferParameterivOES(int var1, int var2, IntBuffer var3);

    @Override
    public native void glGetRenderbufferParameterivOES(int var1, int var2, int[] var3, int var4);

    @Override
    public String glGetString(int n) {
        return this._glGetString(n);
    }

    @Override
    public native void glGetTexEnviv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glGetTexEnviv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glGetTexEnvxv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glGetTexEnvxv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glGetTexGenfv(int var1, int var2, FloatBuffer var3);

    @Override
    public native void glGetTexGenfv(int var1, int var2, float[] var3, int var4);

    @Override
    public native void glGetTexGeniv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glGetTexGeniv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glGetTexGenxv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glGetTexGenxv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glGetTexParameterfv(int var1, int var2, FloatBuffer var3);

    @Override
    public native void glGetTexParameterfv(int var1, int var2, float[] var3, int var4);

    @Override
    public native void glGetTexParameteriv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glGetTexParameteriv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glGetTexParameterxv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glGetTexParameterxv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glHint(int var1, int var2);

    @Override
    public native boolean glIsBuffer(int var1);

    @Override
    public native boolean glIsEnabled(int var1);

    @Override
    public native boolean glIsFramebufferOES(int var1);

    @Override
    public native boolean glIsRenderbufferOES(int var1);

    @Override
    public native boolean glIsTexture(int var1);

    @Override
    public native void glLightModelf(int var1, float var2);

    @Override
    public native void glLightModelfv(int var1, FloatBuffer var2);

    @Override
    public native void glLightModelfv(int var1, float[] var2, int var3);

    @Override
    public native void glLightModelx(int var1, int var2);

    @Override
    public native void glLightModelxv(int var1, IntBuffer var2);

    @Override
    public native void glLightModelxv(int var1, int[] var2, int var3);

    @Override
    public native void glLightf(int var1, int var2, float var3);

    @Override
    public native void glLightfv(int var1, int var2, FloatBuffer var3);

    @Override
    public native void glLightfv(int var1, int var2, float[] var3, int var4);

    @Override
    public native void glLightx(int var1, int var2, int var3);

    @Override
    public native void glLightxv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glLightxv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glLineWidth(float var1);

    @Override
    public native void glLineWidthx(int var1);

    @Override
    public native void glLoadIdentity();

    @Override
    public native void glLoadMatrixf(FloatBuffer var1);

    @Override
    public native void glLoadMatrixf(float[] var1, int var2);

    @Override
    public native void glLoadMatrixx(IntBuffer var1);

    @Override
    public native void glLoadMatrixx(int[] var1, int var2);

    @Override
    public native void glLoadPaletteFromModelViewMatrixOES();

    @Override
    public native void glLogicOp(int var1);

    @Override
    public native void glMaterialf(int var1, int var2, float var3);

    @Override
    public native void glMaterialfv(int var1, int var2, FloatBuffer var3);

    @Override
    public native void glMaterialfv(int var1, int var2, float[] var3, int var4);

    @Override
    public native void glMaterialx(int var1, int var2, int var3);

    @Override
    public native void glMaterialxv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glMaterialxv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glMatrixIndexPointerOES(int var1, int var2, int var3, int var4);

    @Override
    public void glMatrixIndexPointerOES(int n, int n2, int n3, Buffer buffer) {
        this.glMatrixIndexPointerOESBounds(n, n2, n3, buffer, buffer.remaining());
        if (!(n != 2 && n != 3 && n != 4 || n2 != 5126 && n2 != 5120 && n2 != 5122 && n2 != 5132 || n3 < 0)) {
            this._matrixIndexPointerOES = buffer;
        }
    }

    @Override
    public native void glMatrixMode(int var1);

    @Override
    public native void glMultMatrixf(FloatBuffer var1);

    @Override
    public native void glMultMatrixf(float[] var1, int var2);

    @Override
    public native void glMultMatrixx(IntBuffer var1);

    @Override
    public native void glMultMatrixx(int[] var1, int var2);

    @Override
    public native void glMultiTexCoord4f(int var1, float var2, float var3, float var4, float var5);

    @Override
    public native void glMultiTexCoord4x(int var1, int var2, int var3, int var4, int var5);

    @Override
    public native void glNormal3f(float var1, float var2, float var3);

    @Override
    public native void glNormal3x(int var1, int var2, int var3);

    @Override
    public native void glNormalPointer(int var1, int var2, int var3);

    @Override
    public void glNormalPointer(int n, int n2, Buffer buffer) {
        this.glNormalPointerBounds(n, n2, buffer, buffer.remaining());
        if ((n == 5126 || n == 5120 || n == 5122 || n == 5132) && n2 >= 0) {
            this._normalPointer = buffer;
        }
    }

    @Override
    public native void glOrthof(float var1, float var2, float var3, float var4, float var5, float var6);

    @Override
    public native void glOrthox(int var1, int var2, int var3, int var4, int var5, int var6);

    @Override
    public native void glPixelStorei(int var1, int var2);

    @Override
    public native void glPointParameterf(int var1, float var2);

    @Override
    public native void glPointParameterfv(int var1, FloatBuffer var2);

    @Override
    public native void glPointParameterfv(int var1, float[] var2, int var3);

    @Override
    public native void glPointParameterx(int var1, int var2);

    @Override
    public native void glPointParameterxv(int var1, IntBuffer var2);

    @Override
    public native void glPointParameterxv(int var1, int[] var2, int var3);

    @Override
    public native void glPointSize(float var1);

    @Override
    public void glPointSizePointerOES(int n, int n2, Buffer buffer) {
        this.glPointSizePointerOESBounds(n, n2, buffer, buffer.remaining());
        if ((n == 5126 || n == 5132) && n2 >= 0) {
            this._pointSizePointerOES = buffer;
        }
    }

    @Override
    public native void glPointSizex(int var1);

    @Override
    public native void glPolygonOffset(float var1, float var2);

    @Override
    public native void glPolygonOffsetx(int var1, int var2);

    @Override
    public native void glPopMatrix();

    @Override
    public native void glPushMatrix();

    @Override
    public native int glQueryMatrixxOES(IntBuffer var1, IntBuffer var2);

    @Override
    public native int glQueryMatrixxOES(int[] var1, int var2, int[] var3, int var4);

    @Override
    public native void glReadPixels(int var1, int var2, int var3, int var4, int var5, int var6, Buffer var7);

    @Override
    public native void glRenderbufferStorageOES(int var1, int var2, int var3, int var4);

    @Override
    public native void glRotatef(float var1, float var2, float var3, float var4);

    @Override
    public native void glRotatex(int var1, int var2, int var3, int var4);

    @Override
    public native void glSampleCoverage(float var1, boolean var2);

    @Override
    public native void glSampleCoveragex(int var1, boolean var2);

    @Override
    public native void glScalef(float var1, float var2, float var3);

    @Override
    public native void glScalex(int var1, int var2, int var3);

    @Override
    public native void glScissor(int var1, int var2, int var3, int var4);

    @Override
    public native void glShadeModel(int var1);

    @Override
    public native void glStencilFunc(int var1, int var2, int var3);

    @Override
    public native void glStencilMask(int var1);

    @Override
    public native void glStencilOp(int var1, int var2, int var3);

    @Override
    public native void glTexCoordPointer(int var1, int var2, int var3, int var4);

    @Override
    public void glTexCoordPointer(int n, int n2, int n3, Buffer buffer) {
        this.glTexCoordPointerBounds(n, n2, n3, buffer, buffer.remaining());
        if (!(n != 2 && n != 3 && n != 4 || n2 != 5126 && n2 != 5120 && n2 != 5122 && n2 != 5132 || n3 < 0)) {
            this._texCoordPointer = buffer;
        }
    }

    @Override
    public native void glTexEnvf(int var1, int var2, float var3);

    @Override
    public native void glTexEnvfv(int var1, int var2, FloatBuffer var3);

    @Override
    public native void glTexEnvfv(int var1, int var2, float[] var3, int var4);

    @Override
    public native void glTexEnvi(int var1, int var2, int var3);

    @Override
    public native void glTexEnviv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glTexEnviv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glTexEnvx(int var1, int var2, int var3);

    @Override
    public native void glTexEnvxv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glTexEnvxv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glTexGenf(int var1, int var2, float var3);

    @Override
    public native void glTexGenfv(int var1, int var2, FloatBuffer var3);

    @Override
    public native void glTexGenfv(int var1, int var2, float[] var3, int var4);

    @Override
    public native void glTexGeni(int var1, int var2, int var3);

    @Override
    public native void glTexGeniv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glTexGeniv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glTexGenx(int var1, int var2, int var3);

    @Override
    public native void glTexGenxv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glTexGenxv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glTexImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, Buffer var9);

    @Override
    public native void glTexParameterf(int var1, int var2, float var3);

    @Override
    public native void glTexParameterfv(int var1, int var2, FloatBuffer var3);

    @Override
    public native void glTexParameterfv(int var1, int var2, float[] var3, int var4);

    @Override
    public native void glTexParameteri(int var1, int var2, int var3);

    @Override
    public native void glTexParameteriv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glTexParameteriv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glTexParameterx(int var1, int var2, int var3);

    @Override
    public native void glTexParameterxv(int var1, int var2, IntBuffer var3);

    @Override
    public native void glTexParameterxv(int var1, int var2, int[] var3, int var4);

    @Override
    public native void glTexSubImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, Buffer var9);

    @Override
    public native void glTranslatef(float var1, float var2, float var3);

    @Override
    public native void glTranslatex(int var1, int var2, int var3);

    @Override
    public native void glVertexPointer(int var1, int var2, int var3, int var4);

    @Override
    public void glVertexPointer(int n, int n2, int n3, Buffer buffer) {
        this.glVertexPointerBounds(n, n2, n3, buffer, buffer.remaining());
        if (!(n != 2 && n != 3 && n != 4 || n2 != 5126 && n2 != 5120 && n2 != 5122 && n2 != 5132 || n3 < 0)) {
            this._vertexPointer = buffer;
        }
    }

    @Override
    public native void glViewport(int var1, int var2, int var3, int var4);

    @Override
    public native void glWeightPointerOES(int var1, int var2, int var3, int var4);

    @Override
    public void glWeightPointerOES(int n, int n2, int n3, Buffer buffer) {
        this.glWeightPointerOESBounds(n, n2, n3, buffer, buffer.remaining());
    }
}

