/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.os.DeadSystemException;
import com.android.internal.os.RuntimeInit;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.LineBreakBufferedWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.UnknownHostException;

public final class Log {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int LOG_ID_CRASH = 4;
    public static final int LOG_ID_EVENTS = 2;
    public static final int LOG_ID_MAIN = 0;
    public static final int LOG_ID_RADIO = 1;
    public static final int LOG_ID_SYSTEM = 3;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static TerribleFailureHandler sWtfHandler = new TerribleFailureHandler(){

        @Override
        public void onTerribleFailure(String string2, TerribleFailure terribleFailure, boolean bl) {
            RuntimeInit.wtf(string2, terribleFailure, bl);
        }
    };

    private Log() {
    }

    static /* synthetic */ int access$000() {
        return Log.logger_entry_max_payload_native();
    }

    public static int d(String string2, String string3) {
        return Log.println_native(0, 3, string2, string3);
    }

    public static int d(String string2, String string3, Throwable throwable) {
        return Log.printlns(0, 3, string2, string3, throwable);
    }

    public static int e(String string2, String string3) {
        return Log.println_native(0, 6, string2, string3);
    }

    public static int e(String string2, String string3, Throwable throwable) {
        return Log.printlns(0, 6, string2, string3, throwable);
    }

    public static String getStackTraceString(Throwable throwable) {
        Object object;
        if (throwable == null) {
            return "";
        }
        for (object = throwable; object != null; object = object.getCause()) {
            if (!(object instanceof UnknownHostException)) continue;
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        object = new FastPrintWriter(stringWriter, false, 256);
        throwable.printStackTrace((PrintWriter)object);
        ((PrintWriter)object).flush();
        return stringWriter.toString();
    }

    public static int i(String string2, String string3) {
        return Log.println_native(0, 4, string2, string3);
    }

    public static int i(String string2, String string3, Throwable throwable) {
        return Log.printlns(0, 4, string2, string3, throwable);
    }

    public static native boolean isLoggable(String var0, int var1);

    private static native int logger_entry_max_payload_native();

    public static int println(int n, String string2, String string3) {
        return Log.println_native(0, n, string2, string3);
    }

    @UnsupportedAppUsage
    public static native int println_native(int var0, int var1, String var2, String var3);

    public static int printlns(int n, int n2, String object, String string2, Throwable throwable) {
        ImmediateLogWriter immediateLogWriter = new ImmediateLogWriter(n, n2, (String)object);
        n2 = PreloadHolder.LOGGER_ENTRY_MAX_PAYLOAD;
        n = object != null ? ((String)object).length() : 0;
        LineBreakBufferedWriter lineBreakBufferedWriter = new LineBreakBufferedWriter(immediateLogWriter, Math.max(n2 - 2 - n - 32, 100));
        lineBreakBufferedWriter.println(string2);
        if (throwable != null) {
            for (object = throwable; object != null && !(object instanceof UnknownHostException); object = object.getCause()) {
                if (!(object instanceof DeadSystemException)) continue;
                lineBreakBufferedWriter.println("DeadSystemException: The system died; earlier logs will point to the root cause");
                break;
            }
            if (object == null) {
                throwable.printStackTrace(lineBreakBufferedWriter);
            }
        }
        lineBreakBufferedWriter.flush();
        return immediateLogWriter.getWritten();
    }

    public static TerribleFailureHandler setWtfHandler(TerribleFailureHandler terribleFailureHandler) {
        if (terribleFailureHandler != null) {
            TerribleFailureHandler terribleFailureHandler2 = sWtfHandler;
            sWtfHandler = terribleFailureHandler;
            return terribleFailureHandler2;
        }
        throw new NullPointerException("handler == null");
    }

    public static int v(String string2, String string3) {
        return Log.println_native(0, 2, string2, string3);
    }

    public static int v(String string2, String string3, Throwable throwable) {
        return Log.printlns(0, 2, string2, string3, throwable);
    }

    public static int w(String string2, String string3) {
        return Log.println_native(0, 5, string2, string3);
    }

    public static int w(String string2, String string3, Throwable throwable) {
        return Log.printlns(0, 5, string2, string3, throwable);
    }

    public static int w(String string2, Throwable throwable) {
        return Log.printlns(0, 5, string2, "", throwable);
    }

    @UnsupportedAppUsage
    static int wtf(int n, String string2, String string3, Throwable throwable, boolean bl, boolean bl2) {
        TerribleFailure terribleFailure = new TerribleFailure(string3, throwable);
        if (bl) {
            throwable = terribleFailure;
        }
        n = Log.printlns(n, 6, string2, string3, throwable);
        sWtfHandler.onTerribleFailure(string2, terribleFailure, bl2);
        return n;
    }

    public static int wtf(String string2, String string3) {
        return Log.wtf(0, string2, string3, null, false, false);
    }

    public static int wtf(String string2, String string3, Throwable throwable) {
        return Log.wtf(0, string2, string3, throwable, false, false);
    }

    public static int wtf(String string2, Throwable throwable) {
        return Log.wtf(0, string2, throwable.getMessage(), throwable, false, false);
    }

    static void wtfQuiet(int n, String string2, String object, boolean bl) {
        object = new TerribleFailure((String)object, null);
        sWtfHandler.onTerribleFailure(string2, (TerribleFailure)object, bl);
    }

    public static int wtfStack(String string2, String string3) {
        return Log.wtf(0, string2, string3, null, true, false);
    }

    private static class ImmediateLogWriter
    extends Writer {
        private int bufID;
        private int priority;
        private String tag;
        private int written = 0;

        public ImmediateLogWriter(int n, int n2, String string2) {
            this.bufID = n;
            this.priority = n2;
            this.tag = string2;
        }

        @Override
        public void close() {
        }

        @Override
        public void flush() {
        }

        public int getWritten() {
            return this.written;
        }

        @Override
        public void write(char[] arrc, int n, int n2) {
            this.written += Log.println_native(this.bufID, this.priority, this.tag, new String(arrc, n, n2));
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Level {
    }

    static class PreloadHolder {
        public static final int LOGGER_ENTRY_MAX_PAYLOAD = Log.access$000();

        PreloadHolder() {
        }
    }

    public static class TerribleFailure
    extends Exception {
        TerribleFailure(String string2, Throwable throwable) {
            super(string2, throwable);
        }
    }

    public static interface TerribleFailureHandler {
        public void onTerribleFailure(String var1, TerribleFailure var2, boolean var3);
    }

}

