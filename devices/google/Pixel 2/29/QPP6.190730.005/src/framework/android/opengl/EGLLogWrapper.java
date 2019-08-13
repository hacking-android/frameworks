/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.GLException;
import java.io.IOException;
import java.io.Writer;
import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGL11;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

class EGLLogWrapper
implements EGL11 {
    private int mArgCount;
    boolean mCheckError;
    private EGL10 mEgl10;
    Writer mLog;
    boolean mLogArgumentNames;

    public EGLLogWrapper(EGL eGL, int n, Writer writer) {
        this.mEgl10 = (EGL10)eGL;
        this.mLog = writer;
        boolean bl = false;
        boolean bl2 = (n & 4) != 0;
        this.mLogArgumentNames = bl2;
        bl2 = bl;
        if ((n & 1) != 0) {
            bl2 = true;
        }
        this.mCheckError = bl2;
    }

    private void arg(String string2, int n) {
        this.arg(string2, Integer.toString(n));
    }

    private void arg(String string2, Object object) {
        this.arg(string2, this.toString(object));
    }

    private void arg(String string2, String string3) {
        int n = this.mArgCount;
        this.mArgCount = n + 1;
        if (n > 0) {
            this.log(", ");
        }
        if (this.mLogArgumentNames) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("=");
            this.log(stringBuilder.toString());
        }
        this.log(string3);
    }

    private void arg(String string2, EGLContext eGLContext) {
        if (eGLContext == EGL10.EGL_NO_CONTEXT) {
            this.arg(string2, "EGL10.EGL_NO_CONTEXT");
        } else {
            this.arg(string2, this.toString(eGLContext));
        }
    }

    private void arg(String string2, EGLDisplay eGLDisplay) {
        if (eGLDisplay == EGL10.EGL_DEFAULT_DISPLAY) {
            this.arg(string2, "EGL10.EGL_DEFAULT_DISPLAY");
        } else if (eGLDisplay == EGL_NO_DISPLAY) {
            this.arg(string2, "EGL10.EGL_NO_DISPLAY");
        } else {
            this.arg(string2, this.toString(eGLDisplay));
        }
    }

    private void arg(String string2, EGLSurface eGLSurface) {
        if (eGLSurface == EGL10.EGL_NO_SURFACE) {
            this.arg(string2, "EGL10.EGL_NO_SURFACE");
        } else {
            this.arg(string2, this.toString(eGLSurface));
        }
    }

    private void arg(String string2, int[] arrn) {
        if (arrn == null) {
            this.arg(string2, "null");
        } else {
            this.arg(string2, this.toString(arrn.length, arrn, 0));
        }
    }

    private void arg(String string2, Object[] arrobject) {
        if (arrobject == null) {
            this.arg(string2, "null");
        } else {
            this.arg(string2, this.toString(arrobject.length, arrobject, 0));
        }
    }

    private void begin(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append('(');
        this.log(stringBuilder.toString());
        this.mArgCount = 0;
    }

    private void checkError() {
        int n = this.mEgl10.eglGetError();
        if (n != 12288) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("eglError: ");
            charSequence.append(EGLLogWrapper.getErrorString(n));
            charSequence = charSequence.toString();
            this.logLine((String)charSequence);
            if (this.mCheckError) {
                throw new GLException(n, (String)charSequence);
            }
        }
    }

    private void end() {
        this.log(");\n");
        this.flush();
    }

    private void flush() {
        try {
            this.mLog.flush();
        }
        catch (IOException iOException) {
            this.mLog = null;
        }
    }

    public static String getErrorString(int n) {
        switch (n) {
            default: {
                return EGLLogWrapper.getHex(n);
            }
            case 12302: {
                return "EGL_CONTEXT_LOST";
            }
            case 12301: {
                return "EGL_BAD_SURFACE";
            }
            case 12300: {
                return "EGL_BAD_PARAMETER";
            }
            case 12299: {
                return "EGL_BAD_NATIVE_WINDOW";
            }
            case 12298: {
                return "EGL_BAD_NATIVE_PIXMAP";
            }
            case 12297: {
                return "EGL_BAD_MATCH";
            }
            case 12296: {
                return "EGL_BAD_DISPLAY";
            }
            case 12295: {
                return "EGL_BAD_CURRENT_SURFACE";
            }
            case 12294: {
                return "EGL_BAD_CONTEXT";
            }
            case 12293: {
                return "EGL_BAD_CONFIG";
            }
            case 12292: {
                return "EGL_BAD_ATTRIBUTE";
            }
            case 12291: {
                return "EGL_BAD_ALLOC";
            }
            case 12290: {
                return "EGL_BAD_ACCESS";
            }
            case 12289: {
                return "EGL_NOT_INITIALIZED";
            }
            case 12288: 
        }
        return "EGL_SUCCESS";
    }

    private static String getHex(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }

    private void log(String string2) {
        try {
            this.mLog.write(string2);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void logLine(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append('\n');
        this.log(stringBuilder.toString());
    }

    private void returns(int n) {
        this.returns(Integer.toString(n));
    }

    private void returns(Object object) {
        this.returns(this.toString(object));
    }

    private void returns(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" returns ");
        stringBuilder.append(string2);
        stringBuilder.append(";\n");
        this.log(stringBuilder.toString());
        this.flush();
    }

    private void returns(boolean bl) {
        this.returns(Boolean.toString(bl));
    }

    private String toString(int n, int[] arrn, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        int n3 = arrn.length;
        for (int i = 0; i < n; ++i) {
            int n4 = n2 + i;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" [");
            stringBuilder2.append(n4);
            stringBuilder2.append("] = ");
            stringBuilder.append(stringBuilder2.toString());
            if (n4 >= 0 && n4 < n3) {
                stringBuilder.append(arrn[n4]);
            } else {
                stringBuilder.append("out of bounds");
            }
            stringBuilder.append('\n');
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private String toString(int n, Object[] arrobject, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        int n3 = arrobject.length;
        for (int i = 0; i < n; ++i) {
            int n4 = n2 + i;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" [");
            stringBuilder2.append(n4);
            stringBuilder2.append("] = ");
            stringBuilder.append(stringBuilder2.toString());
            if (n4 >= 0 && n4 < n3) {
                stringBuilder.append(arrobject[n4]);
            } else {
                stringBuilder.append("out of bounds");
            }
            stringBuilder.append('\n');
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private String toString(Object object) {
        if (object == null) {
            return "null";
        }
        return object.toString();
    }

    @Override
    public boolean eglChooseConfig(EGLDisplay eGLDisplay, int[] arrn, EGLConfig[] arreGLConfig, int n, int[] arrn2) {
        this.begin("eglChooseConfig");
        this.arg("display", eGLDisplay);
        this.arg("attrib_list", arrn);
        this.arg("config_size", n);
        this.end();
        boolean bl = this.mEgl10.eglChooseConfig(eGLDisplay, arrn, arreGLConfig, n, arrn2);
        this.arg("configs", arreGLConfig);
        this.arg("num_config", arrn2);
        this.returns(bl);
        this.checkError();
        return bl;
    }

    @Override
    public boolean eglCopyBuffers(EGLDisplay eGLDisplay, EGLSurface eGLSurface, Object object) {
        this.begin("eglCopyBuffers");
        this.arg("display", eGLDisplay);
        this.arg("surface", eGLSurface);
        this.arg("native_pixmap", object);
        this.end();
        boolean bl = this.mEgl10.eglCopyBuffers(eGLDisplay, eGLSurface, object);
        this.returns(bl);
        this.checkError();
        return bl;
    }

    @Override
    public EGLContext eglCreateContext(EGLDisplay object, EGLConfig eGLConfig, EGLContext eGLContext, int[] arrn) {
        this.begin("eglCreateContext");
        this.arg("display", (EGLDisplay)object);
        this.arg("config", eGLConfig);
        this.arg("share_context", eGLContext);
        this.arg("attrib_list", arrn);
        this.end();
        object = this.mEgl10.eglCreateContext((EGLDisplay)object, eGLConfig, eGLContext, arrn);
        this.returns(object);
        this.checkError();
        return object;
    }

    @Override
    public EGLSurface eglCreatePbufferSurface(EGLDisplay object, EGLConfig eGLConfig, int[] arrn) {
        this.begin("eglCreatePbufferSurface");
        this.arg("display", (EGLDisplay)object);
        this.arg("config", eGLConfig);
        this.arg("attrib_list", arrn);
        this.end();
        object = this.mEgl10.eglCreatePbufferSurface((EGLDisplay)object, eGLConfig, arrn);
        this.returns(object);
        this.checkError();
        return object;
    }

    @Override
    public EGLSurface eglCreatePixmapSurface(EGLDisplay object, EGLConfig eGLConfig, Object object2, int[] arrn) {
        this.begin("eglCreatePixmapSurface");
        this.arg("display", (EGLDisplay)object);
        this.arg("config", eGLConfig);
        this.arg("native_pixmap", object2);
        this.arg("attrib_list", arrn);
        this.end();
        object = this.mEgl10.eglCreatePixmapSurface((EGLDisplay)object, eGLConfig, object2, arrn);
        this.returns(object);
        this.checkError();
        return object;
    }

    @Override
    public EGLSurface eglCreateWindowSurface(EGLDisplay object, EGLConfig eGLConfig, Object object2, int[] arrn) {
        this.begin("eglCreateWindowSurface");
        this.arg("display", (EGLDisplay)object);
        this.arg("config", eGLConfig);
        this.arg("native_window", object2);
        this.arg("attrib_list", arrn);
        this.end();
        object = this.mEgl10.eglCreateWindowSurface((EGLDisplay)object, eGLConfig, object2, arrn);
        this.returns(object);
        this.checkError();
        return object;
    }

    @Override
    public boolean eglDestroyContext(EGLDisplay eGLDisplay, EGLContext eGLContext) {
        this.begin("eglDestroyContext");
        this.arg("display", eGLDisplay);
        this.arg("context", eGLContext);
        this.end();
        boolean bl = this.mEgl10.eglDestroyContext(eGLDisplay, eGLContext);
        this.returns(bl);
        this.checkError();
        return bl;
    }

    @Override
    public boolean eglDestroySurface(EGLDisplay eGLDisplay, EGLSurface eGLSurface) {
        this.begin("eglDestroySurface");
        this.arg("display", eGLDisplay);
        this.arg("surface", eGLSurface);
        this.end();
        boolean bl = this.mEgl10.eglDestroySurface(eGLDisplay, eGLSurface);
        this.returns(bl);
        this.checkError();
        return bl;
    }

    @Override
    public boolean eglGetConfigAttrib(EGLDisplay eGLDisplay, EGLConfig eGLConfig, int n, int[] arrn) {
        this.begin("eglGetConfigAttrib");
        this.arg("display", eGLDisplay);
        this.arg("config", eGLConfig);
        this.arg("attribute", n);
        this.end();
        boolean bl = this.mEgl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, n, arrn);
        this.arg("value", arrn);
        this.returns(bl);
        this.checkError();
        return false;
    }

    @Override
    public boolean eglGetConfigs(EGLDisplay eGLDisplay, EGLConfig[] arreGLConfig, int n, int[] arrn) {
        this.begin("eglGetConfigs");
        this.arg("display", eGLDisplay);
        this.arg("config_size", n);
        this.end();
        boolean bl = this.mEgl10.eglGetConfigs(eGLDisplay, arreGLConfig, n, arrn);
        this.arg("configs", arreGLConfig);
        this.arg("num_config", arrn);
        this.returns(bl);
        this.checkError();
        return bl;
    }

    @Override
    public EGLContext eglGetCurrentContext() {
        this.begin("eglGetCurrentContext");
        this.end();
        EGLContext eGLContext = this.mEgl10.eglGetCurrentContext();
        this.returns(eGLContext);
        this.checkError();
        return eGLContext;
    }

    @Override
    public EGLDisplay eglGetCurrentDisplay() {
        this.begin("eglGetCurrentDisplay");
        this.end();
        EGLDisplay eGLDisplay = this.mEgl10.eglGetCurrentDisplay();
        this.returns(eGLDisplay);
        this.checkError();
        return eGLDisplay;
    }

    @Override
    public EGLSurface eglGetCurrentSurface(int n) {
        this.begin("eglGetCurrentSurface");
        this.arg("readdraw", n);
        this.end();
        EGLSurface eGLSurface = this.mEgl10.eglGetCurrentSurface(n);
        this.returns(eGLSurface);
        this.checkError();
        return eGLSurface;
    }

    @Override
    public EGLDisplay eglGetDisplay(Object object) {
        this.begin("eglGetDisplay");
        this.arg("native_display", object);
        this.end();
        object = this.mEgl10.eglGetDisplay(object);
        this.returns(object);
        this.checkError();
        return object;
    }

    @Override
    public int eglGetError() {
        this.begin("eglGetError");
        this.end();
        int n = this.mEgl10.eglGetError();
        this.returns(EGLLogWrapper.getErrorString(n));
        return n;
    }

    @Override
    public boolean eglInitialize(EGLDisplay eGLDisplay, int[] arrn) {
        this.begin("eglInitialize");
        this.arg("display", eGLDisplay);
        this.end();
        boolean bl = this.mEgl10.eglInitialize(eGLDisplay, arrn);
        this.returns(bl);
        this.arg("major_minor", arrn);
        this.checkError();
        return bl;
    }

    @Override
    public boolean eglMakeCurrent(EGLDisplay eGLDisplay, EGLSurface eGLSurface, EGLSurface eGLSurface2, EGLContext eGLContext) {
        this.begin("eglMakeCurrent");
        this.arg("display", eGLDisplay);
        this.arg("draw", eGLSurface);
        this.arg("read", eGLSurface2);
        this.arg("context", eGLContext);
        this.end();
        boolean bl = this.mEgl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface2, eGLContext);
        this.returns(bl);
        this.checkError();
        return bl;
    }

    @Override
    public boolean eglQueryContext(EGLDisplay eGLDisplay, EGLContext eGLContext, int n, int[] arrn) {
        this.begin("eglQueryContext");
        this.arg("display", eGLDisplay);
        this.arg("context", eGLContext);
        this.arg("attribute", n);
        this.end();
        boolean bl = this.mEgl10.eglQueryContext(eGLDisplay, eGLContext, n, arrn);
        this.returns(arrn[0]);
        this.returns(bl);
        this.checkError();
        return bl;
    }

    @Override
    public String eglQueryString(EGLDisplay object, int n) {
        this.begin("eglQueryString");
        this.arg("display", (EGLDisplay)object);
        this.arg("name", n);
        this.end();
        object = this.mEgl10.eglQueryString((EGLDisplay)object, n);
        this.returns((String)object);
        this.checkError();
        return object;
    }

    @Override
    public boolean eglQuerySurface(EGLDisplay eGLDisplay, EGLSurface eGLSurface, int n, int[] arrn) {
        this.begin("eglQuerySurface");
        this.arg("display", eGLDisplay);
        this.arg("surface", eGLSurface);
        this.arg("attribute", n);
        this.end();
        boolean bl = this.mEgl10.eglQuerySurface(eGLDisplay, eGLSurface, n, arrn);
        this.returns(arrn[0]);
        this.returns(bl);
        this.checkError();
        return bl;
    }

    @Override
    public boolean eglReleaseThread() {
        this.begin("eglReleaseThread");
        this.end();
        boolean bl = this.mEgl10.eglReleaseThread();
        this.returns(bl);
        this.checkError();
        return bl;
    }

    @Override
    public boolean eglSwapBuffers(EGLDisplay eGLDisplay, EGLSurface eGLSurface) {
        this.begin("eglSwapBuffers");
        this.arg("display", eGLDisplay);
        this.arg("surface", eGLSurface);
        this.end();
        boolean bl = this.mEgl10.eglSwapBuffers(eGLDisplay, eGLSurface);
        this.returns(bl);
        this.checkError();
        return bl;
    }

    @Override
    public boolean eglTerminate(EGLDisplay eGLDisplay) {
        this.begin("eglTerminate");
        this.arg("display", eGLDisplay);
        this.end();
        boolean bl = this.mEgl10.eglTerminate(eGLDisplay);
        this.returns(bl);
        this.checkError();
        return bl;
    }

    @Override
    public boolean eglWaitGL() {
        this.begin("eglWaitGL");
        this.end();
        boolean bl = this.mEgl10.eglWaitGL();
        this.returns(bl);
        this.checkError();
        return bl;
    }

    @Override
    public boolean eglWaitNative(int n, Object object) {
        this.begin("eglWaitNative");
        this.arg("engine", n);
        this.arg("bindTarget", object);
        this.end();
        boolean bl = this.mEgl10.eglWaitNative(n, object);
        this.returns(bl);
        this.checkError();
        return bl;
    }
}

