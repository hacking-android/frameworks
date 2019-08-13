/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.EGLLogWrapper;
import android.opengl.GLErrorWrapper;
import android.opengl.GLLogWrapper;
import java.io.Writer;
import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.opengles.GL;

public class GLDebugHelper {
    public static final int CONFIG_CHECK_GL_ERROR = 1;
    public static final int CONFIG_CHECK_THREAD = 2;
    public static final int CONFIG_LOG_ARGUMENT_NAMES = 4;
    public static final int ERROR_WRONG_THREAD = 28672;

    public static EGL wrap(EGL eGL, int n, Writer writer) {
        EGL eGL2 = eGL;
        if (writer != null) {
            eGL2 = new EGLLogWrapper(eGL, n, writer);
        }
        return eGL2;
    }

    public static GL wrap(GL gL, int n, Writer writer) {
        GL gL2 = gL;
        if (n != 0) {
            gL2 = new GLErrorWrapper(gL, n);
        }
        gL = gL2;
        if (writer != null) {
            boolean bl = (n & 4) != 0;
            gL = new GLLogWrapper(gL2, writer, bl);
        }
        return gL;
    }
}

