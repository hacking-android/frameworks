/*
 * Decompiled with CFR 0.145.
 */
package sun.util.logging;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import sun.util.logging.LoggingSupport;

public class PlatformLogger {
    private static final int ALL = Integer.MIN_VALUE;
    private static final int CONFIG = 700;
    private static final Level DEFAULT_LEVEL = Level.INFO;
    private static final int FINE = 500;
    private static final int FINER = 400;
    private static final int FINEST = 300;
    private static final int INFO = 800;
    private static final int OFF = Integer.MAX_VALUE;
    private static final int SEVERE = 1000;
    private static final int WARNING = 900;
    private static Map<String, WeakReference<PlatformLogger>> loggers;
    private static boolean loggingEnabled;
    private volatile JavaLoggerProxy javaLoggerProxy;
    private volatile LoggerProxy loggerProxy;

    static {
        loggingEnabled = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

            @Override
            public Boolean run() {
                String string = System.getProperty("java.util.logging.config.class");
                String string2 = System.getProperty("java.util.logging.config.file");
                boolean bl = string != null || string2 != null;
                return bl;
            }
        });
        loggers = new HashMap<String, WeakReference<PlatformLogger>>();
    }

    private PlatformLogger(String object) {
        if (loggingEnabled) {
            this.javaLoggerProxy = object = new JavaLoggerProxy((String)object);
            this.loggerProxy = object;
        } else {
            this.loggerProxy = new DefaultLoggerProxy((String)object);
        }
    }

    public static PlatformLogger getLogger(String string) {
        synchronized (PlatformLogger.class) {
            PlatformLogger platformLogger;
            Object object;
            block6 : {
                platformLogger = null;
                object = loggers.get(string);
                if (object == null) break block6;
                platformLogger = (PlatformLogger)((Reference)object).get();
            }
            object = platformLogger;
            if (platformLogger == null) {
                platformLogger = new PlatformLogger(string);
                object = loggers;
                WeakReference<PlatformLogger> weakReference = new WeakReference<PlatformLogger>(platformLogger);
                object.put(string, weakReference);
                object = platformLogger;
            }
            return object;
        }
    }

    public static void redirectPlatformLoggers() {
        synchronized (PlatformLogger.class) {
            if (!loggingEnabled) {
                if (!LoggingSupport.isAvailable()) {
                } else {
                    loggingEnabled = true;
                    Iterator<Map.Entry<String, WeakReference<PlatformLogger>>> iterator = loggers.entrySet().iterator();
                    while (iterator.hasNext()) {
                        PlatformLogger platformLogger = (PlatformLogger)iterator.next().getValue().get();
                        if (platformLogger == null) continue;
                        platformLogger.redirectToJavaLoggerProxy();
                    }
                    return;
                }
            }
            return;
        }
    }

    private void redirectToJavaLoggerProxy() {
        LoggerProxy loggerProxy = (DefaultLoggerProxy)DefaultLoggerProxy.class.cast(this.loggerProxy);
        loggerProxy = new JavaLoggerProxy(loggerProxy.name, loggerProxy.level);
        this.javaLoggerProxy = loggerProxy;
        this.loggerProxy = loggerProxy;
    }

    public void config(String string) {
        this.loggerProxy.doLog(Level.CONFIG, string);
    }

    public void config(String string, Throwable throwable) {
        this.loggerProxy.doLog(Level.CONFIG, string, throwable);
    }

    public void config(String string, Object ... arrobject) {
        this.loggerProxy.doLog(Level.CONFIG, string, arrobject);
    }

    public void fine(String string) {
        this.loggerProxy.doLog(Level.FINE, string);
    }

    public void fine(String string, Throwable throwable) {
        this.loggerProxy.doLog(Level.FINE, string, throwable);
    }

    public void fine(String string, Object ... arrobject) {
        this.loggerProxy.doLog(Level.FINE, string, arrobject);
    }

    public void finer(String string) {
        this.loggerProxy.doLog(Level.FINER, string);
    }

    public void finer(String string, Throwable throwable) {
        this.loggerProxy.doLog(Level.FINER, string, throwable);
    }

    public void finer(String string, Object ... arrobject) {
        this.loggerProxy.doLog(Level.FINER, string, arrobject);
    }

    public void finest(String string) {
        this.loggerProxy.doLog(Level.FINEST, string);
    }

    public void finest(String string, Throwable throwable) {
        this.loggerProxy.doLog(Level.FINEST, string, throwable);
    }

    public void finest(String string, Object ... arrobject) {
        this.loggerProxy.doLog(Level.FINEST, string, arrobject);
    }

    public String getName() {
        return this.loggerProxy.name;
    }

    public void info(String string) {
        this.loggerProxy.doLog(Level.INFO, string);
    }

    public void info(String string, Throwable throwable) {
        this.loggerProxy.doLog(Level.INFO, string, throwable);
    }

    public void info(String string, Object ... arrobject) {
        this.loggerProxy.doLog(Level.INFO, string, arrobject);
    }

    public boolean isEnabled() {
        return this.loggerProxy.isEnabled();
    }

    public boolean isLoggable(Level level) {
        if (level != null) {
            JavaLoggerProxy javaLoggerProxy = this.javaLoggerProxy;
            boolean bl = javaLoggerProxy != null ? javaLoggerProxy.isLoggable(level) : this.loggerProxy.isLoggable(level);
            return bl;
        }
        throw new NullPointerException();
    }

    public Level level() {
        return this.loggerProxy.getLevel();
    }

    public void setLevel(Level level) {
        this.loggerProxy.setLevel(level);
    }

    public void severe(String string) {
        this.loggerProxy.doLog(Level.SEVERE, string);
    }

    public void severe(String string, Throwable throwable) {
        this.loggerProxy.doLog(Level.SEVERE, string, throwable);
    }

    public void severe(String string, Object ... arrobject) {
        this.loggerProxy.doLog(Level.SEVERE, string, arrobject);
    }

    public void warning(String string) {
        this.loggerProxy.doLog(Level.WARNING, string);
    }

    public void warning(String string, Throwable throwable) {
        this.loggerProxy.doLog(Level.WARNING, string, throwable);
    }

    public void warning(String string, Object ... arrobject) {
        this.loggerProxy.doLog(Level.WARNING, string, arrobject);
    }

    private static final class DefaultLoggerProxy
    extends LoggerProxy {
        private static final String formatString = LoggingSupport.getSimpleFormat(false);
        private Date date = new Date();
        volatile Level effectiveLevel = this.deriveEffectiveLevel(null);
        volatile Level level = null;

        DefaultLoggerProxy(String string) {
            super(string);
        }

        private Level deriveEffectiveLevel(Level level) {
            block0 : {
                if (level != null) break block0;
                level = DEFAULT_LEVEL;
            }
            return level;
        }

        /*
         * WARNING - void declaration
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private String format(Level object, String string, Throwable throwable) {
            synchronized (this) {
                void var2_4;
                void var3_5;
                this.date.setTime(System.currentTimeMillis());
                Object object2 = "";
                if (var3_5 == null) return String.format(formatString, this.date, this.getCallerInfo(), this.name, ((Enum)object).name(), var2_4, object2);
                StringWriter stringWriter = new StringWriter();
                object2 = new PrintWriter(stringWriter);
                ((PrintWriter)object2).println();
                var3_5.printStackTrace((PrintWriter)object2);
                ((PrintWriter)object2).close();
                object2 = stringWriter.toString();
                return String.format(formatString, this.date, this.getCallerInfo(), this.name, ((Enum)object).name(), var2_4, object2);
            }
        }

        private String formatMessage(String string, Object ... object) {
            block4 : {
                if (object != null) {
                    block5 : {
                        try {
                            if (((Object[])object).length == 0) break block4;
                            if (string.indexOf("{0") >= 0 || string.indexOf("{1") >= 0 || string.indexOf("{2") >= 0 || string.indexOf("{3") >= 0) break block5;
                            return string;
                        }
                        catch (Exception exception) {
                            return string;
                        }
                    }
                    object = MessageFormat.format(string, object);
                    return object;
                }
            }
            return string;
        }

        private String getCallerInfo() {
            Object object;
            Object var1_1 = null;
            Serializable serializable = null;
            Object object2 = new Throwable();
            boolean bl = true;
            StackTraceElement[] arrstackTraceElement = ((Throwable)object2).getStackTrace();
            int n = arrstackTraceElement.length;
            int n2 = 0;
            do {
                object2 = var1_1;
                object = serializable;
                if (n2 >= n) break;
                object = arrstackTraceElement[n2];
                object2 = ((StackTraceElement)object).getClassName();
                if (bl) {
                    if (((String)object2).equals("sun.util.logging.PlatformLogger")) {
                        bl = false;
                    }
                } else if (!((String)object2).equals("sun.util.logging.PlatformLogger")) {
                    object = ((StackTraceElement)object).getMethodName();
                    break;
                }
                ++n2;
            } while (true);
            if (object2 != null) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append((String)object2);
                ((StringBuilder)serializable).append(" ");
                ((StringBuilder)serializable).append((String)object);
                return ((StringBuilder)serializable).toString();
            }
            return this.name;
        }

        private static PrintStream outputStream() {
            return System.err;
        }

        @Override
        void doLog(Level level, String string) {
            if (this.isLoggable(level)) {
                DefaultLoggerProxy.outputStream().print(this.format(level, string, null));
            }
        }

        @Override
        void doLog(Level level, String string, Throwable throwable) {
            if (this.isLoggable(level)) {
                DefaultLoggerProxy.outputStream().print(this.format(level, string, throwable));
            }
        }

        @Override
        void doLog(Level level, String string, Object ... arrobject) {
            if (this.isLoggable(level)) {
                string = this.formatMessage(string, arrobject);
                DefaultLoggerProxy.outputStream().print(this.format(level, string, null));
            }
        }

        @Override
        Level getLevel() {
            return this.level;
        }

        @Override
        boolean isEnabled() {
            boolean bl = this.effectiveLevel != Level.OFF;
            return bl;
        }

        @Override
        boolean isLoggable(Level level) {
            Level level2 = this.effectiveLevel;
            boolean bl = level.intValue() >= level2.intValue() && level2 != Level.OFF;
            return bl;
        }

        @Override
        void setLevel(Level level) {
            if (this.level != level) {
                this.level = level;
                this.effectiveLevel = this.deriveEffectiveLevel(level);
            }
        }
    }

    private static final class JavaLoggerProxy
    extends LoggerProxy {
        private final Object javaLogger;

        static {
            for (Level level : Level.values()) {
                level.javaLevel = LoggingSupport.parseLevel(level.name());
            }
        }

        JavaLoggerProxy(String string) {
            this(string, null);
        }

        JavaLoggerProxy(String string, Level level) {
            super(string);
            this.javaLogger = LoggingSupport.getLogger(string);
            if (level != null) {
                LoggingSupport.setLevel(this.javaLogger, level.javaLevel);
            }
        }

        @Override
        void doLog(Level level, String string) {
            LoggingSupport.log(this.javaLogger, level.javaLevel, string);
        }

        @Override
        void doLog(Level level, String string, Throwable throwable) {
            LoggingSupport.log(this.javaLogger, level.javaLevel, string, throwable);
        }

        @Override
        void doLog(Level level, String string, Object ... arrobject) {
            if (!this.isLoggable(level)) {
                return;
            }
            int n = arrobject != null ? arrobject.length : 0;
            Object[] arrobject2 = new String[n];
            for (int i = 0; i < n; ++i) {
                arrobject2[i] = String.valueOf(arrobject[i]);
            }
            LoggingSupport.log(this.javaLogger, level.javaLevel, string, arrobject2);
        }

        @Override
        Level getLevel() {
            Object object = LoggingSupport.getLevel(this.javaLogger);
            if (object == null) {
                return null;
            }
            try {
                Level level = Level.valueOf(LoggingSupport.getLevelName(object));
                return level;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return Level.valueOf(LoggingSupport.getLevelValue(object));
            }
        }

        @Override
        boolean isEnabled() {
            return LoggingSupport.isLoggable(this.javaLogger, Level.OFF.javaLevel);
        }

        @Override
        boolean isLoggable(Level level) {
            return LoggingSupport.isLoggable(this.javaLogger, level.javaLevel);
        }

        @Override
        void setLevel(Level object) {
            Object object2 = this.javaLogger;
            object = object == null ? null : object.javaLevel;
            LoggingSupport.setLevel(object2, object);
        }
    }

    public static enum Level {
        ALL,
        FINEST,
        FINER,
        FINE,
        CONFIG,
        INFO,
        WARNING,
        SEVERE,
        OFF;
        
        private static final int[] LEVEL_VALUES;
        Object javaLevel;

        static {
            LEVEL_VALUES = new int[]{Integer.MIN_VALUE, 300, 400, 500, 700, 800, 900, 1000, Integer.MAX_VALUE};
        }

        public static Level valueOf(String string) {
            return Enum.valueOf(Level.class, string);
        }

        public int intValue() {
            return LEVEL_VALUES[this.ordinal()];
        }
    }

    private static abstract class LoggerProxy {
        final String name;

        protected LoggerProxy(String string) {
            this.name = string;
        }

        abstract void doLog(Level var1, String var2);

        abstract void doLog(Level var1, String var2, Throwable var3);

        abstract void doLog(Level var1, String var2, Object ... var3);

        abstract Level getLevel();

        abstract boolean isEnabled();

        abstract boolean isLoggable(Level var1);

        abstract void setLevel(Level var1);
    }

}

