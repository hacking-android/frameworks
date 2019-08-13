/*
 * Decompiled with CFR 0.145.
 */
package sun.util.logging;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Date;
import java.util.List;
import sun.util.logging.LoggingProxy;

public class LoggingSupport {
    private static final String DEFAULT_FORMAT = "%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %1$Tp %2$s%n%4$s: %5$s%6$s%n";
    private static final String FORMAT_PROP_KEY = "java.util.logging.SimpleFormatter.format";
    private static final LoggingProxy proxy = AccessController.doPrivileged(new PrivilegedAction<LoggingProxy>(){

        @Override
        public LoggingProxy run() {
            try {
                Object object = Class.forName("java.util.logging.LoggingProxyImpl", true, null).getDeclaredField("INSTANCE");
                ((AccessibleObject)object).setAccessible(true);
                object = (LoggingProxy)((Field)object).get(null);
                return object;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError(illegalAccessException);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                throw new AssertionError(noSuchFieldException);
            }
            catch (ClassNotFoundException classNotFoundException) {
                return null;
            }
        }
    });

    private LoggingSupport() {
    }

    private static void ensureAvailable() {
        if (proxy != null) {
            return;
        }
        throw new AssertionError((Object)"Should not here");
    }

    public static Object getLevel(Object object) {
        LoggingSupport.ensureAvailable();
        return proxy.getLevel(object);
    }

    public static String getLevelName(Object object) {
        LoggingSupport.ensureAvailable();
        return proxy.getLevelName(object);
    }

    public static int getLevelValue(Object object) {
        LoggingSupport.ensureAvailable();
        return proxy.getLevelValue(object);
    }

    public static Object getLogger(String string) {
        LoggingSupport.ensureAvailable();
        return proxy.getLogger(string);
    }

    public static String getLoggerLevel(String string) {
        LoggingSupport.ensureAvailable();
        return proxy.getLoggerLevel(string);
    }

    public static List<String> getLoggerNames() {
        LoggingSupport.ensureAvailable();
        return proxy.getLoggerNames();
    }

    public static String getParentLoggerName(String string) {
        LoggingSupport.ensureAvailable();
        return proxy.getParentLoggerName(string);
    }

    public static String getSimpleFormat() {
        return LoggingSupport.getSimpleFormat(true);
    }

    static String getSimpleFormat(boolean bl) {
        Object object;
        Object object2 = object = AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                return System.getProperty(LoggingSupport.FORMAT_PROP_KEY);
            }
        });
        if (bl) {
            LoggingProxy loggingProxy = proxy;
            object2 = object;
            if (loggingProxy != null) {
                object2 = object;
                if (object == null) {
                    object2 = loggingProxy.getProperty(FORMAT_PROP_KEY);
                }
            }
        }
        if (object2 != null) {
            try {
                object = new Date();
                String.format((String)object2, object, "", "", "", "", "");
            }
            catch (IllegalArgumentException illegalArgumentException) {
                object2 = DEFAULT_FORMAT;
            }
        } else {
            object2 = DEFAULT_FORMAT;
        }
        return object2;
    }

    public static boolean isAvailable() {
        boolean bl = proxy != null;
        return bl;
    }

    public static boolean isLoggable(Object object, Object object2) {
        LoggingSupport.ensureAvailable();
        return proxy.isLoggable(object, object2);
    }

    public static void log(Object object, Object object2, String string) {
        LoggingSupport.ensureAvailable();
        proxy.log(object, object2, string);
    }

    public static void log(Object object, Object object2, String string, Throwable throwable) {
        LoggingSupport.ensureAvailable();
        proxy.log(object, object2, string, throwable);
    }

    public static void log(Object object, Object object2, String string, Object ... arrobject) {
        LoggingSupport.ensureAvailable();
        proxy.log(object, object2, string, arrobject);
    }

    public static Object parseLevel(String string) {
        LoggingSupport.ensureAvailable();
        return proxy.parseLevel(string);
    }

    public static void setLevel(Object object, Object object2) {
        LoggingSupport.ensureAvailable();
        proxy.setLevel(object, object2);
    }

    public static void setLoggerLevel(String string, String string2) {
        LoggingSupport.ensureAvailable();
        proxy.setLoggerLevel(string, string2);
    }

}

