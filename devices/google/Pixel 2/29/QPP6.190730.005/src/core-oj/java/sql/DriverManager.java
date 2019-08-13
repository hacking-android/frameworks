/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverInfo;
import java.sql.SQLException;
import java.sql.SQLPermission;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

public class DriverManager {
    static final SQLPermission SET_LOG_PERMISSION;
    private static volatile PrintStream logStream;
    private static final Object logSync;
    private static volatile PrintWriter logWriter;
    private static volatile int loginTimeout;
    private static final CopyOnWriteArrayList<DriverInfo> registeredDrivers;

    static {
        registeredDrivers = new CopyOnWriteArrayList();
        loginTimeout = 0;
        logWriter = null;
        logStream = null;
        logSync = new Object();
        DriverManager.loadInitialDrivers();
        DriverManager.println("JDBC DriverManager initialized");
        SET_LOG_PERMISSION = new SQLPermission("setLog");
    }

    private DriverManager() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @CallerSensitive
    public static void deregisterDriver(Driver object) throws SQLException {
        synchronized (DriverManager.class) {
            if (object == null) {
                return;
            }
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("DriverManager.deregisterDriver: ");
            ((StringBuilder)object2).append(object);
            DriverManager.println(((StringBuilder)object2).toString());
            object2 = new DriverInfo((Driver)object);
            if (registeredDrivers.contains(object2)) {
                if (!DriverManager.isDriverAllowed((Driver)object, Reflection.getCallerClass())) {
                    object = new SecurityException();
                    throw object;
                }
                registeredDrivers.remove(object2);
            } else {
                DriverManager.println("    couldn't find driver to unload");
            }
            return;
        }
    }

    @CallerSensitive
    public static Connection getConnection(String string) throws SQLException {
        return DriverManager.getConnection(string, new Properties(), Reflection.getCallerClass());
    }

    @CallerSensitive
    public static Connection getConnection(String string, String string2, String string3) throws SQLException {
        Properties properties = new Properties();
        if (string2 != null) {
            properties.put("user", string2);
        }
        if (string3 != null) {
            properties.put("password", string3);
        }
        return DriverManager.getConnection(string, properties, Reflection.getCallerClass());
    }

    @CallerSensitive
    public static Connection getConnection(String string, Properties properties) throws SQLException {
        return DriverManager.getConnection(string, properties, Reflection.getCallerClass());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static Connection getConnection(String charSequence, Properties serializable, Class<?> object) throws SQLException {
        object = object != null ? ((Class)object).getClassLoader() : null;
        // MONITORENTER : java.sql.DriverManager.class
        Object object2 = object;
        if (object == null) {
            object2 = Thread.currentThread().getContextClassLoader();
        }
        // MONITOREXIT : java.sql.DriverManager.class
        if (charSequence == null) throw new SQLException("The url cannot be null", "08001");
        object = new StringBuilder();
        ((StringBuilder)object).append("DriverManager.getConnection(\"");
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append("\")");
        DriverManager.println(((StringBuilder)object).toString());
        object = null;
        for (Object object3 : registeredDrivers) {
            Object object4;
            if (DriverManager.isDriverAllowed(((DriverInfo)object3).driver, (ClassLoader)object2)) {
                block10 : {
                    try {
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append("    trying ");
                        ((StringBuilder)object4).append(((DriverInfo)object3).driver.getClass().getName());
                        DriverManager.println(((StringBuilder)object4).toString());
                        object4 = ((DriverInfo)object3).driver.connect((String)charSequence, (Properties)serializable);
                        if (object4 != null) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("getConnection returning ");
                            stringBuilder.append(((DriverInfo)object3).driver.getClass().getName());
                            DriverManager.println(stringBuilder.toString());
                            return object4;
                        }
                        object3 = object;
                    }
                    catch (SQLException sQLException) {
                        object3 = object;
                        if (object != null) break block10;
                        object3 = sQLException;
                    }
                }
                object = object3;
                continue;
            }
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("    skipping: ");
            ((StringBuilder)object4).append(object3.getClass().getName());
            DriverManager.println(((StringBuilder)object4).toString());
        }
        if (object != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("getConnection failed: ");
            ((StringBuilder)charSequence).append(object);
            DriverManager.println(((StringBuilder)charSequence).toString());
            throw object;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("getConnection: no suitable driver found for ");
        ((StringBuilder)serializable).append((String)charSequence);
        DriverManager.println(((StringBuilder)serializable).toString());
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("No suitable driver found for ");
        ((StringBuilder)serializable).append((String)charSequence);
        throw new SQLException(((StringBuilder)serializable).toString(), "08001");
    }

    @CallerSensitive
    public static Driver getDriver(String string) throws SQLException {
        Serializable serializable = new StringBuilder();
        serializable.append("DriverManager.getDriver(\"");
        serializable.append(string);
        serializable.append("\")");
        DriverManager.println(serializable.toString());
        serializable = Reflection.getCallerClass();
        for (DriverInfo sQLException : registeredDrivers) {
            StringBuilder stringBuilder;
            if (DriverManager.isDriverAllowed(sQLException.driver, serializable)) {
                try {
                    if (!sQLException.driver.acceptsURL(string)) continue;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("getDriver returning ");
                    stringBuilder.append(sQLException.driver.getClass().getName());
                    DriverManager.println(stringBuilder.toString());
                    Driver driver = sQLException.driver;
                    return driver;
                }
                catch (SQLException sQLException2) {
                    continue;
                }
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("    skipping: ");
            stringBuilder.append(sQLException.driver.getClass().getName());
            DriverManager.println(stringBuilder.toString());
        }
        DriverManager.println("getDriver: no suitable driver");
        throw new SQLException("No suitable driver", "08001");
    }

    @CallerSensitive
    public static Enumeration<Driver> getDrivers() {
        Vector<Driver> vector = new Vector<Driver>();
        Class<?> class_ = Reflection.getCallerClass();
        for (DriverInfo driverInfo : registeredDrivers) {
            if (DriverManager.isDriverAllowed(driverInfo.driver, class_)) {
                vector.addElement(driverInfo.driver);
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("    skipping: ");
            stringBuilder.append(driverInfo.getClass().getName());
            DriverManager.println(stringBuilder.toString());
        }
        return vector.elements();
    }

    @Deprecated
    public static PrintStream getLogStream() {
        return logStream;
    }

    public static PrintWriter getLogWriter() {
        return logWriter;
    }

    public static int getLoginTimeout() {
        return loginTimeout;
    }

    private static boolean isDriverAllowed(Driver driver, Class<?> object) {
        object = object != null ? ((Class)object).getClassLoader() : null;
        return DriverManager.isDriverAllowed(driver, (ClassLoader)object);
    }

    private static boolean isDriverAllowed(Driver driver, ClassLoader object) {
        boolean bl = false;
        if (driver != null) {
            Object var3_4 = null;
            bl = true;
            try {
                object = Class.forName(driver.getClass().getName(), true, (ClassLoader)object);
            }
            catch (Exception exception) {
                object = var3_4;
            }
            if (object != driver.getClass()) {
                bl = false;
            }
        }
        return bl;
    }

    private static void loadInitialDrivers() {
        PrivilegedAction<String> privilegedAction;
        try {
            privilegedAction = new PrivilegedAction<String>(){

                @Override
                public String run() {
                    return System.getProperty("jdbc.drivers");
                }
            };
            privilegedAction = AccessController.doPrivileged(privilegedAction);
        }
        catch (Exception exception) {
            privilegedAction = null;
        }
        AccessController.doPrivileged(new PrivilegedAction<Void>(){

            @Override
            public Void run() {
                Iterator<Driver> iterator = ServiceLoader.load(Driver.class).iterator();
                try {
                    while (iterator.hasNext()) {
                        iterator.next();
                    }
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                return null;
            }
        });
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("DriverManager.initialize: jdbc.drivers = ");
        ((StringBuilder)charSequence2).append((String)((Object)privilegedAction));
        DriverManager.println(((StringBuilder)charSequence2).toString());
        if (privilegedAction != null && !((String)((Object)privilegedAction)).equals("")) {
            privilegedAction = ((String)((Object)privilegedAction)).split(":");
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("number of Drivers:");
            ((StringBuilder)charSequence2).append(((String[])privilegedAction).length);
            DriverManager.println(((StringBuilder)charSequence2).toString());
            for (CharSequence charSequence2 : privilegedAction) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DriverManager.Initialize: loading ");
                    stringBuilder.append((String)charSequence2);
                    DriverManager.println(stringBuilder.toString());
                    Class.forName((String)charSequence2, true, ClassLoader.getSystemClassLoader());
                }
                catch (Exception exception) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("DriverManager.Initialize: load failed: ");
                    ((StringBuilder)charSequence2).append(exception);
                    DriverManager.println(((StringBuilder)charSequence2).toString());
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void println(String string) {
        Object object = logSync;
        synchronized (object) {
            if (logWriter != null) {
                logWriter.println(string);
                logWriter.flush();
            }
            return;
        }
    }

    public static void registerDriver(Driver object) throws SQLException {
        synchronized (DriverManager.class) {
            if (object != null) {
                Serializable serializable = registeredDrivers;
                DriverInfo driverInfo = new DriverInfo((Driver)object);
                ((CopyOnWriteArrayList)serializable).addIfAbsent((DriverInfo)driverInfo);
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("registerDriver: ");
                ((StringBuilder)serializable).append(object);
                DriverManager.println(((StringBuilder)serializable).toString());
                return;
            }
            try {
                object = new NullPointerException();
                throw object;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
            }
        }
    }

    @Deprecated
    public static void setLogStream(PrintStream printStream) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SET_LOG_PERMISSION);
        }
        logStream = printStream;
        logWriter = printStream != null ? new PrintWriter(printStream) : null;
    }

    public static void setLogWriter(PrintWriter printWriter) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SET_LOG_PERMISSION);
        }
        logStream = null;
        logWriter = printWriter;
    }

    public static void setLoginTimeout(int n) {
        loginTimeout = n;
    }

}

