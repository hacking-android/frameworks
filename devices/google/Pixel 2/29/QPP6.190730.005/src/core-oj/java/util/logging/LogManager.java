/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.Logging;
import java.util.logging.LoggingMXBean;
import java.util.logging.LoggingPermission;
import sun.util.logging.PlatformLogger;

public class LogManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String LOGGING_MXBEAN_NAME = "java.util.logging:type=Logging";
    private static final int MAX_ITERATIONS = 400;
    private static final Level defaultLevel = Level.INFO;
    private static LoggingMXBean loggingMXBean;
    private static final LogManager manager;
    private WeakHashMap<Object, LoggerContext> contextsMap = null;
    private final Permission controlPermission = new LoggingPermission("control", null);
    private boolean deathImminent;
    private volatile boolean initializationDone = false;
    private boolean initializedCalled = false;
    private boolean initializedGlobalHandlers = true;
    private final Map<Object, Integer> listenerMap = new HashMap<Object, Integer>();
    private final ReferenceQueue<Logger> loggerRefQueue = new ReferenceQueue();
    private volatile Properties props = new Properties();
    private volatile boolean readPrimordialConfiguration;
    private volatile Logger rootLogger;
    private final LoggerContext systemContext = new SystemLoggerContext();
    private final LoggerContext userContext = new LoggerContext();

    static {
        manager = AccessController.doPrivileged(new PrivilegedAction<LogManager>(){

            @Override
            public LogManager run() {
                Object object;
                Object object2;
                block4 : {
                    object = null;
                    String string = null;
                    object2 = null;
                    Object object3 = System.getProperty("java.util.logging.manager");
                    object2 = string;
                    if (object3 == null) break block4;
                    object2 = object3;
                    try {
                        object2 = object3 = (LogManager)LogManager.getClassInstance((String)object3).newInstance();
                    }
                    catch (Exception exception) {
                        PrintStream printStream = System.err;
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Could not load Logmanager \"");
                        ((StringBuilder)object3).append((String)object2);
                        ((StringBuilder)object3).append("\"");
                        printStream.println(((StringBuilder)object3).toString());
                        exception.printStackTrace();
                        object2 = object;
                    }
                }
                object = object2;
                if (object2 == null) {
                    object = new LogManager();
                }
                return object;
            }
        });
        loggingMXBean = null;
    }

    protected LogManager() {
        this(LogManager.checkSubclassPermissions());
    }

    private LogManager(Void object) {
        try {
            Runtime runtime = Runtime.getRuntime();
            object = new Cleaner();
            runtime.addShutdownHook((Thread)object);
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
    }

    static /* synthetic */ boolean access$700(LogManager logManager) {
        return logManager.initializedCalled;
    }

    static /* synthetic */ boolean access$800(LogManager logManager) {
        return logManager.initializationDone;
    }

    private static Void checkSubclassPermissions() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("shutdownHooks"));
            securityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
        }
        return null;
    }

    private List<LoggerContext> contexts() {
        ArrayList<LoggerContext> arrayList = new ArrayList<LoggerContext>();
        arrayList.add(this.getSystemContext());
        arrayList.add(this.getUserContext());
        return arrayList;
    }

    private static void doSetLevel(final Logger logger, final Level level) {
        if (System.getSecurityManager() == null) {
            logger.setLevel(level);
            return;
        }
        AccessController.doPrivileged(new PrivilegedAction<Object>(){

            @Override
            public Object run() {
                logger.setLevel(level);
                return null;
            }
        });
    }

    private static void doSetParent(final Logger logger, final Logger logger2) {
        if (System.getSecurityManager() == null) {
            logger.setParent(logger2);
            return;
        }
        AccessController.doPrivileged(new PrivilegedAction<Object>(){

            @Override
            public Object run() {
                logger.setParent(logger2);
                return null;
            }
        });
    }

    private static Class getClassInstance(String string) throws ClassNotFoundException {
        try {
            Class<?> class_ = ClassLoader.getSystemClassLoader().loadClass(string);
            return class_;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return Thread.currentThread().getContextClassLoader().loadClass(string);
        }
    }

    public static LogManager getLogManager() {
        LogManager logManager = manager;
        if (logManager != null) {
            logManager.ensureLogManagerInitialized();
        }
        return manager;
    }

    public static LoggingMXBean getLoggingMXBean() {
        synchronized (LogManager.class) {
            LoggingMXBean loggingMXBean;
            if (LogManager.loggingMXBean == null) {
                loggingMXBean = new Logging();
                LogManager.loggingMXBean = loggingMXBean;
            }
            loggingMXBean = LogManager.loggingMXBean;
            return loggingMXBean;
        }
    }

    private LoggerContext getUserContext() {
        return this.userContext;
    }

    private void initializeGlobalHandlers() {
        synchronized (this) {
            block6 : {
                boolean bl;
                block5 : {
                    bl = this.initializedGlobalHandlers;
                    if (!bl) break block5;
                    return;
                }
                this.initializedGlobalHandlers = true;
                bl = this.deathImminent;
                if (!bl) break block6;
                return;
            }
            this.loadLoggerHandlers(this.rootLogger, null, "handlers");
            return;
        }
    }

    private void loadLoggerHandlers(final Logger logger, String string, final String string2) {
        AccessController.doPrivileged(new PrivilegedAction<Object>(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Object run() {
                String[] arrstring = LogManager.this.parseClassNames(string2);
                int n = 0;
                while (n < arrstring.length) {
                    Appendable appendable;
                    Object object;
                    Object object2 = arrstring[n];
                    try {
                        Handler handler = (Handler)LogManager.getClassInstance((String)object2).newInstance();
                        object = LogManager.this;
                        appendable = new StringBuilder();
                        ((StringBuilder)appendable).append((String)object2);
                        ((StringBuilder)appendable).append(".level");
                        object = ((LogManager)object).getProperty(((StringBuilder)appendable).toString());
                        if (object != null) {
                            if ((object = Level.findLevel((String)object)) != null) {
                                handler.setLevel((Level)object);
                            } else {
                                appendable = System.err;
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Can't set level for ");
                                ((StringBuilder)object).append((String)object2);
                                ((PrintStream)appendable).println(((StringBuilder)object).toString());
                            }
                        }
                        logger.addHandler(handler);
                    }
                    catch (Exception exception) {
                        object = System.err;
                        appendable = new StringBuilder();
                        ((StringBuilder)appendable).append("Can't load log handler \"");
                        ((StringBuilder)appendable).append((String)object2);
                        ((StringBuilder)appendable).append("\"");
                        ((PrintStream)object).println(((StringBuilder)appendable).toString());
                        object2 = System.err;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("");
                        ((StringBuilder)object).append(exception);
                        ((PrintStream)object2).println(((StringBuilder)object).toString());
                        exception.printStackTrace();
                    }
                    ++n;
                }
                return null;
            }
        });
    }

    private String[] parseClassNames(String object) {
        if ((object = this.getProperty((String)object)) == null) {
            return new String[0];
        }
        String string = ((String)object).trim();
        int n = 0;
        object = new ArrayList();
        while (n < string.length()) {
            int n2;
            for (n2 = n; n2 < string.length() && !Character.isWhitespace(string.charAt(n2)) && string.charAt(n2) != ','; ++n2) {
            }
            String string2 = string.substring(n, n2);
            n = n2 + 1;
            if ((string2 = string2.trim()).length() == 0) continue;
            object.add(string2);
        }
        return object.toArray(new String[object.size()]);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readPrimordialConfiguration() {
        if (this.readPrimordialConfiguration) return;
        synchronized (this) {
            if (this.readPrimordialConfiguration) return;
            if (System.out == null) {
                return;
            }
            this.readPrimordialConfiguration = true;
            try {
                PrivilegedExceptionAction<Void> privilegedExceptionAction = new PrivilegedExceptionAction<Void>(){

                    @Override
                    public Void run() throws Exception {
                        LogManager.this.readConfiguration();
                        PlatformLogger.redirectPlatformLoggers();
                        return null;
                    }
                };
                AccessController.doPrivileged(privilegedExceptionAction);
            }
            catch (Exception exception) {
                // empty catch block
            }
            return;
        }
    }

    private void resetLogger(Logger logger) {
        Object object = logger.getHandlers();
        for (int i = 0; i < ((Handler[])object).length; ++i) {
            Handler handler = object[i];
            logger.removeHandler(handler);
            try {
                handler.close();
                continue;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        object = logger.getName();
        if (object != null && ((String)object).equals("")) {
            logger.setLevel(defaultLevel);
        } else {
            logger.setLevel(null);
        }
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private void setLevelsOnExistingLoggers() {
        synchronized (this) {
            Enumeration<?> enumeration = this.props.propertyNames();
            while (enumeration.hasMoreElements()) {
                Object object = (String)enumeration.nextElement();
                if (!((String)object).endsWith(".level")) continue;
                CharSequence charSequence = ((String)object).substring(0, ((String)object).length() - 6);
                Object object2 = this.getLevelProperty((String)object, null);
                if (object2 == null) {
                    object2 = System.err;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Bad level value for property: ");
                    ((StringBuilder)charSequence).append((String)object);
                    ((PrintStream)object2).println(((StringBuilder)charSequence).toString());
                    continue;
                }
                object = this.contexts().iterator();
                while (object.hasNext()) {
                    Logger logger = ((LoggerContext)object.next()).findLogger((String)charSequence);
                    if (logger == null) continue;
                    logger.setLevel((Level)object2);
                }
            }
            return;
        }
    }

    public boolean addLogger(Logger logger) {
        String string = logger.getName();
        if (string != null) {
            this.drainLoggerRefQueueBounded();
            if (this.getUserContext().addLocalLogger(logger)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(".handlers");
                this.loadLoggerHandlers(logger, string, stringBuilder.toString());
                return true;
            }
            return false;
        }
        throw new NullPointerException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void addPropertyChangeListener(PropertyChangeListener object) throws SecurityException {
        PropertyChangeListener propertyChangeListener = Objects.requireNonNull(object);
        this.checkPermission();
        object = this.listenerMap;
        synchronized (object) {
            Integer n = this.listenerMap.get(propertyChangeListener);
            int n2 = 1;
            if (n != null) {
                n2 = 1 + n;
            }
            this.listenerMap.put(propertyChangeListener, n2);
            return;
        }
    }

    public void checkAccess() throws SecurityException {
        this.checkPermission();
    }

    void checkPermission() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(this.controlPermission);
        }
    }

    Logger demandLogger(String string, String object, Class<?> class_) {
        Logger logger;
        Logger logger2 = logger = this.getLogger(string);
        if (logger == null) {
            object = new Logger(string, (String)object, class_, this, false);
            do {
                if (!this.addLogger((Logger)object)) continue;
                return object;
            } while ((logger2 = this.getLogger(string)) == null);
        }
        return logger2;
    }

    Logger demandSystemLogger(String string, String object) {
        Logger logger = this.getSystemContext().demandLogger(string, (String)object);
        while ((object = this.addLogger(logger) ? logger : this.getLogger(string)) == null) {
        }
        if (object != logger && logger.accessCheckedHandlers().length == 0) {
            AccessController.doPrivileged(new PrivilegedAction<Void>((Logger)object, logger){
                final /* synthetic */ Logger val$l;
                final /* synthetic */ Logger val$sysLogger;
                {
                    this.val$l = logger;
                    this.val$sysLogger = logger2;
                }

                @Override
                public Void run() {
                    for (Handler handler : this.val$l.accessCheckedHandlers()) {
                        this.val$sysLogger.addHandler(handler);
                    }
                    return null;
                }
            });
        }
        return logger;
    }

    final void drainLoggerRefQueueBounded() {
        Object object;
        for (int i = 0; i < 400 && (object = this.loggerRefQueue) != null && (object = (LoggerWeakRef)((ReferenceQueue)object).poll()) != null; ++i) {
            ((LoggerWeakRef)object).dispose();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void ensureLogManagerInitialized() {
        if (this.initializationDone) return;
        if (this != manager) {
            return;
        }
        synchronized (this) {
            boolean bl = this.initializedCalled;
            if (bl) return;
            if (this.initializationDone) {
                return;
            }
            this.initializedCalled = true;
            try {
                PrivilegedAction<Object> privilegedAction = new PrivilegedAction<Object>(){
                    static final /* synthetic */ boolean $assertionsDisabled = false;

                    @Override
                    public Object run() {
                        this.readPrimordialConfiguration();
                        Object object = this;
                        Objects.requireNonNull(object);
                        ((LogManager)object).rootLogger = (LogManager)object.new RootLogger();
                        object = this;
                        ((LogManager)object).addLogger(((LogManager)object).rootLogger);
                        if (!rootLogger.isLevelInitialized()) {
                            rootLogger.setLevel(defaultLevel);
                        }
                        object = Logger.global;
                        this.addLogger((Logger)object);
                        return null;
                    }
                };
                AccessController.doPrivileged(privilegedAction);
                return;
            }
            finally {
                this.initializationDone = true;
            }
        }
    }

    boolean getBooleanProperty(String string, boolean bl) {
        if ((string = this.getProperty(string)) == null) {
            return bl;
        }
        if (!(string = string.toLowerCase()).equals("true") && !string.equals("1")) {
            if (!string.equals("false") && !string.equals("0")) {
                return bl;
            }
            return false;
        }
        return true;
    }

    Filter getFilterProperty(String object, Filter filter) {
        if ((object = this.getProperty((String)object)) != null) {
            try {
                object = (Filter)LogManager.getClassInstance((String)object).newInstance();
                return object;
            }
            catch (Exception exception) {
            }
        }
        return filter;
    }

    Formatter getFormatterProperty(String object, Formatter formatter) {
        if ((object = this.getProperty((String)object)) != null) {
            try {
                object = (Formatter)LogManager.getClassInstance((String)object).newInstance();
                return object;
            }
            catch (Exception exception) {
            }
        }
        return formatter;
    }

    int getIntProperty(String string, int n) {
        if ((string = this.getProperty(string)) == null) {
            return n;
        }
        try {
            int n2 = Integer.parseInt(string.trim());
            return n2;
        }
        catch (Exception exception) {
            return n;
        }
    }

    Level getLevelProperty(String object, Level level) {
        if ((object = this.getProperty((String)object)) == null) {
            return level;
        }
        if ((object = Level.findLevel(((String)object).trim())) == null) {
            object = level;
        }
        return object;
    }

    public Logger getLogger(String string) {
        return this.getUserContext().findLogger(string);
    }

    public Enumeration<String> getLoggerNames() {
        return this.getUserContext().getLoggerNames();
    }

    public String getProperty(String string) {
        return this.props.getProperty(string);
    }

    String getStringProperty(String string, String string2) {
        if ((string = this.getProperty(string)) == null) {
            return string2;
        }
        return string.trim();
    }

    final LoggerContext getSystemContext() {
        return this.systemContext;
    }

    public void readConfiguration() throws IOException, SecurityException {
        Exception exception3;
        block11 : {
            this.checkPermission();
            Object object = System.getProperty("java.util.logging.config.class");
            if (object != null) {
                try {
                    LogManager.getClassInstance((String)object).newInstance();
                    return;
                }
                catch (Exception exception2) {
                    PrintStream printStream = System.err;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Logging configuration class \"");
                    stringBuilder.append((String)object);
                    stringBuilder.append("\" failed");
                    printStream.println(stringBuilder.toString());
                    printStream = System.err;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("");
                    ((StringBuilder)object).append(exception2);
                    printStream.println(((StringBuilder)object).toString());
                }
            }
            Object object2 = object = System.getProperty("java.util.logging.config.file");
            if (object == null) {
                object2 = System.getProperty("java.home");
                if (object2 != null) {
                    object2 = new File(new File((String)object2, "lib"), "logging.properties").getCanonicalPath();
                } else {
                    throw new Error("Can't find java.home ??");
                }
            }
            try {
                object2 = object = new FileInputStream((String)object2);
            }
            catch (Exception exception3) {
                object2 = LogManager.class.getResourceAsStream("logging.properties");
                if (object2 == null) break block11;
            }
            object = new BufferedInputStream((InputStream)object2);
            try {
                this.readConfiguration((InputStream)object);
                return;
            }
            finally {
                ((InputStream)object2).close();
            }
        }
        throw exception3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void readConfiguration(InputStream object4) throws IOException, SecurityException {
        CharSequence charSequence;
        int n;
        this.checkPermission();
        this.reset();
        this.props.load((InputStream)object4);
        Object object2 = this.parseClassNames("config");
        for (n = 0; n < ((String[])object2).length; ++n) {
            charSequence = object2[n];
            try {
                LogManager.getClassInstance((String)charSequence).newInstance();
                continue;
            }
            catch (Exception exception) {
                PrintStream printStream = System.err;
                Object object3 = new StringBuilder();
                ((StringBuilder)object3).append("Can't load config class \"");
                ((StringBuilder)object3).append((String)charSequence);
                ((StringBuilder)object3).append("\"");
                printStream.println(((StringBuilder)object3).toString());
                object3 = System.err;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("");
                ((StringBuilder)charSequence).append(exception);
                ((PrintStream)object3).println(((StringBuilder)charSequence).toString());
            }
        }
        this.setLevelsOnExistingLoggers();
        object4 = null;
        object2 = this.listenerMap;
        // MONITORENTER : object2
        if (!this.listenerMap.isEmpty()) {
            object4 = new HashMap(this.listenerMap);
        }
        // MONITOREXIT : object2
        if (object4 != null) {
            object2 = Beans.newPropertyChangeEvent(LogManager.class, null, null, null);
            for (Object object4 : object4.entrySet()) {
                charSequence = object4.getKey();
                int n2 = (Integer)object4.getValue();
                for (n = 0; n < n2; ++n) {
                    Beans.invokePropertyChange(charSequence, object2);
                }
            }
        }
        // MONITORENTER : this
        this.initializedGlobalHandlers = false;
        // MONITOREXIT : this
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) throws SecurityException {
        this.checkPermission();
        if (propertyChangeListener == null) return;
        Map<Object, Integer> map = this.listenerMap;
        synchronized (map) {
            Integer n = this.listenerMap.get(propertyChangeListener);
            if (n == null) return;
            int n2 = n;
            if (n2 == 1) {
                this.listenerMap.remove(propertyChangeListener);
            } else {
                this.listenerMap.put(propertyChangeListener, n2 - 1);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reset() throws SecurityException {
        Object object;
        this.checkPermission();
        synchronized (this) {
            object = new Properties();
            this.props = object;
            this.initializedGlobalHandlers = true;
        }
        object = this.contexts().iterator();
        block3 : while (object.hasNext()) {
            LoggerContext loggerContext = (LoggerContext)object.next();
            Enumeration<String> enumeration = loggerContext.getLoggerNames();
            do {
                if (!enumeration.hasMoreElements()) continue block3;
                Logger logger = loggerContext.findLogger(enumeration.nextElement());
                if (logger == null) continue;
                this.resetLogger(logger);
            } while (true);
            break;
        }
        return;
    }

    private static class Beans {
        private static final Class<?> propertyChangeEventClass;
        private static final Class<?> propertyChangeListenerClass;
        private static final Method propertyChangeMethod;
        private static final Constructor<?> propertyEventCtor;

        static {
            propertyChangeListenerClass = Beans.getClass("java.beans.PropertyChangeListener");
            propertyChangeEventClass = Beans.getClass("java.beans.PropertyChangeEvent");
            propertyChangeMethod = Beans.getMethod(propertyChangeListenerClass, "propertyChange", propertyChangeEventClass);
            propertyEventCtor = Beans.getConstructor(propertyChangeEventClass, Object.class, String.class, Object.class, Object.class);
        }

        private Beans() {
        }

        private static Class<?> getClass(String object) {
            try {
                object = Class.forName((String)object, true, Beans.class.getClassLoader());
                return object;
            }
            catch (ClassNotFoundException classNotFoundException) {
                return null;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static Constructor<?> getConstructor(Class<?> genericDeclaration, Class<?> ... arrclass) {
            if (genericDeclaration == null) {
                return null;
            }
            try {
                return ((Class)genericDeclaration).getDeclaredConstructor(arrclass);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                throw new AssertionError(noSuchMethodException);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static Method getMethod(Class<?> genericDeclaration, String string, Class<?> ... arrclass) {
            if (genericDeclaration == null) {
                return null;
            }
            try {
                return ((Class)genericDeclaration).getMethod(string, arrclass);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                throw new AssertionError(noSuchMethodException);
            }
        }

        static void invokePropertyChange(Object object, Object object2) {
            try {
                propertyChangeMethod.invoke(object, object2);
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                object = invocationTargetException.getCause();
                if (!(object instanceof Error)) {
                    if (object instanceof RuntimeException) {
                        throw (RuntimeException)object;
                    }
                    throw new AssertionError(invocationTargetException);
                }
                throw (Error)object;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError(illegalAccessException);
            }
        }

        static boolean isBeansPresent() {
            boolean bl = propertyChangeListenerClass != null && propertyChangeEventClass != null;
            return bl;
        }

        static Object newPropertyChangeEvent(Object object, String string, Object object2, Object object3) {
            try {
                object = propertyEventCtor.newInstance(object, string, object2, object3);
                return object;
            }
            catch (InvocationTargetException invocationTargetException) {
                object = invocationTargetException.getCause();
                if (!(object instanceof Error)) {
                    if (object instanceof RuntimeException) {
                        throw (RuntimeException)object;
                    }
                    throw new AssertionError(invocationTargetException);
                }
                throw (Error)object;
            }
            catch (IllegalAccessException | InstantiationException reflectiveOperationException) {
                throw new AssertionError(reflectiveOperationException);
            }
        }
    }

    private class Cleaner
    extends Thread {
        private Cleaner() {
            this.setContextClassLoader(null);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            manager;
            LogManager logManager = LogManager.this;
            synchronized (logManager) {
                LogManager.this.deathImminent = true;
                LogManager.this.initializedGlobalHandlers = true;
            }
            LogManager.this.reset();
        }
    }

    private static class LogNode {
        HashMap<String, LogNode> children;
        final LoggerContext context;
        LoggerWeakRef loggerRef;
        LogNode parent;

        LogNode(LogNode logNode, LoggerContext loggerContext) {
            this.parent = logNode;
            this.context = loggerContext;
        }

        void walkAndSetParent(Logger logger) {
            Object object = this.children;
            if (object == null) {
                return;
            }
            for (LogNode logNode : ((HashMap)object).values()) {
                object = logNode.loggerRef;
                object = object == null ? null : (Logger)((Reference)object).get();
                if (object == null) {
                    logNode.walkAndSetParent(logger);
                    continue;
                }
                LogManager.doSetParent((Logger)object, logger);
            }
        }
    }

    class LoggerContext {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Hashtable<String, LoggerWeakRef> namedLoggers = new Hashtable();
        private final LogNode root = new LogNode(null, this);

        private LoggerContext() {
        }

        private void ensureAllDefaultLoggers(Logger object) {
            if (this.requiresDefaultLoggers() && !((String)(object = ((Logger)object).getName())).isEmpty()) {
                this.ensureDefaultLogger(this.getRootLogger());
                if (!"global".equals(object)) {
                    this.ensureDefaultLogger(this.getGlobalLogger());
                }
            }
        }

        private void ensureDefaultLogger(Logger logger) {
            if (this.requiresDefaultLoggers() && logger != null && (logger == Logger.global || logger == LogManager.this.rootLogger)) {
                if (!this.namedLoggers.containsKey(logger.getName())) {
                    this.addLocalLogger(logger, false);
                }
                return;
            }
        }

        private void ensureInitialized() {
            if (this.requiresDefaultLoggers()) {
                this.ensureDefaultLogger(this.getRootLogger());
                this.ensureDefaultLogger(this.getGlobalLogger());
            }
        }

        private void processParentHandlers(Logger object, String string) {
            LogManager logManager = this.getOwner();
            AccessController.doPrivileged(new PrivilegedAction<Void>((Logger)object, logManager, string){
                final /* synthetic */ Logger val$logger;
                final /* synthetic */ String val$name;
                final /* synthetic */ LogManager val$owner;
                {
                    this.val$logger = logger;
                    this.val$owner = logManager;
                    this.val$name = string;
                }

                @Override
                public Void run() {
                    if (this.val$logger != this.val$owner.rootLogger) {
                        LogManager logManager = this.val$owner;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(this.val$name);
                        stringBuilder.append(".useParentHandlers");
                        if (!logManager.getBooleanProperty(stringBuilder.toString(), true)) {
                            this.val$logger.setUseParentHandlers(false);
                        }
                    }
                    return null;
                }
            });
            int n = 1;
            do {
                block6 : {
                    block5 : {
                        if ((n = string.indexOf(".", n)) < 0) {
                            return;
                        }
                        object = string.substring(0, n);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append((String)object);
                        stringBuilder.append(".level");
                        if (logManager.getProperty(stringBuilder.toString()) != null) break block5;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append((String)object);
                        stringBuilder.append(".handlers");
                        if (logManager.getProperty(stringBuilder.toString()) == null) break block6;
                    }
                    this.demandLogger((String)object, null);
                }
                ++n;
            } while (true);
        }

        boolean addLocalLogger(Logger logger) {
            return this.addLocalLogger(logger, this.requiresDefaultLoggers());
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        boolean addLocalLogger(Logger object, boolean bl) {
            synchronized (this) {
                void var2_2;
                Object object2;
                if (var2_2 != false) {
                    this.ensureAllDefaultLoggers((Logger)object);
                }
                if ((object2 = ((Logger)object).getName()) == null) {
                    object = new NullPointerException();
                    throw object;
                }
                Object object3 = this.namedLoggers.get(object2);
                if (object3 != null) {
                    if (((Reference)object3).get() != null) {
                        return false;
                    }
                    ((LoggerWeakRef)object3).dispose();
                }
                Object object4 = this.getOwner();
                ((Logger)object).setLogManager((LogManager)object4);
                Objects.requireNonNull(object4);
                LoggerWeakRef loggerWeakRef = (LogManager)object4.new LoggerWeakRef((Logger)object);
                this.namedLoggers.put((String)object2, loggerWeakRef);
                object3 = new StringBuilder();
                ((StringBuilder)object3).append((String)object2);
                ((StringBuilder)object3).append(".level");
                object3 = ((LogManager)object4).getLevelProperty(((StringBuilder)object3).toString(), null);
                if (object3 != null && !((Logger)object).isLevelInitialized()) {
                    LogManager.doSetLevel((Logger)object, (Level)object3);
                }
                this.processParentHandlers((Logger)object, (String)object2);
                LogNode logNode = this.getNode((String)object2);
                logNode.loggerRef = loggerWeakRef;
                object2 = null;
                object3 = logNode.parent;
                do {
                    object4 = object2;
                    if (object3 == null) break;
                    object4 = ((LogNode)object3).loggerRef;
                    if (object4 != null) {
                        object2 = object4 = (Logger)((Reference)object4).get();
                        if (object4 != null) break;
                    }
                    object3 = ((LogNode)object3).parent;
                } while (true);
                if (object4 != null) {
                    LogManager.doSetParent((Logger)object, (Logger)object4);
                }
                logNode.walkAndSetParent((Logger)object);
                loggerWeakRef.setNode(logNode);
                return true;
            }
        }

        Logger demandLogger(String string, String string2) {
            return this.getOwner().demandLogger(string, string2, null);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        Logger findLogger(String object) {
            synchronized (this) {
                this.ensureInitialized();
                LoggerWeakRef loggerWeakRef = this.namedLoggers.get(object);
                if (loggerWeakRef == null) {
                    return null;
                }
                object = (Logger)loggerWeakRef.get();
                if (object == null) {
                    loggerWeakRef.dispose();
                }
                return object;
            }
        }

        final Logger getGlobalLogger() {
            return Logger.global;
        }

        Enumeration<String> getLoggerNames() {
            synchronized (this) {
                this.ensureInitialized();
                Enumeration<String> enumeration = this.namedLoggers.keys();
                return enumeration;
            }
        }

        LogNode getNode(String string) {
            if (string != null && !string.equals("")) {
                LogNode logNode = this.root;
                while (string.length() > 0) {
                    LogNode logNode2;
                    String string2;
                    int n = string.indexOf(".");
                    if (n > 0) {
                        string2 = string.substring(0, n);
                        string = string.substring(n + 1);
                    } else {
                        string2 = string;
                        string = "";
                    }
                    if (logNode.children == null) {
                        logNode.children = new HashMap();
                    }
                    LogNode logNode3 = logNode2 = logNode.children.get(string2);
                    if (logNode2 == null) {
                        logNode3 = new LogNode(logNode, this);
                        logNode.children.put(string2, logNode3);
                    }
                    logNode = logNode3;
                }
                return logNode;
            }
            return this.root;
        }

        final LogManager getOwner() {
            return LogManager.this;
        }

        final Logger getRootLogger() {
            return this.getOwner().rootLogger;
        }

        void removeLoggerRef(String string, LoggerWeakRef loggerWeakRef) {
            synchronized (this) {
                this.namedLoggers.remove(string, loggerWeakRef);
                return;
            }
        }

        final boolean requiresDefaultLoggers() {
            boolean bl = this.getOwner() == manager;
            if (bl) {
                this.getOwner().ensureLogManagerInitialized();
            }
            return bl;
        }

    }

    final class LoggerWeakRef
    extends WeakReference<Logger> {
        private boolean disposed;
        private String name;
        private LogNode node;
        private WeakReference<Logger> parentRef;

        LoggerWeakRef(Logger logger) {
            super(logger, LogManager.this.loggerRefQueue);
            this.disposed = false;
            this.name = logger.getName();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void dispose() {
            Object object;
            synchronized (this) {
                if (this.disposed) {
                    return;
                }
                this.disposed = true;
            }
            LogNode logNode = this.node;
            if (logNode != null) {
                object = logNode.context;
                synchronized (object) {
                    logNode.context.removeLoggerRef(this.name, this);
                    this.name = null;
                    if (logNode.loggerRef == this) {
                        logNode.loggerRef = null;
                    }
                    this.node = null;
                }
            }
            if ((object = this.parentRef) != null) {
                if ((object = (Logger)((Reference)object).get()) != null) {
                    ((Logger)object).removeChildLogger(this);
                }
                this.parentRef = null;
            }
        }

        void setNode(LogNode logNode) {
            this.node = logNode;
        }

        void setParentRef(WeakReference<Logger> weakReference) {
            this.parentRef = weakReference;
        }
    }

    private final class RootLogger
    extends Logger {
        private RootLogger() {
            super("", null, null, LogManager.this, true);
        }

        @Override
        Handler[] accessCheckedHandlers() {
            LogManager.this.initializeGlobalHandlers();
            return super.accessCheckedHandlers();
        }

        @Override
        public void addHandler(Handler handler) {
            LogManager.this.initializeGlobalHandlers();
            super.addHandler(handler);
        }

        @Override
        public void log(LogRecord logRecord) {
            LogManager.this.initializeGlobalHandlers();
            super.log(logRecord);
        }

        @Override
        public void removeHandler(Handler handler) {
            LogManager.this.initializeGlobalHandlers();
            super.removeHandler(handler);
        }
    }

    final class SystemLoggerContext
    extends LoggerContext {
        SystemLoggerContext() {
        }

        @Override
        Logger demandLogger(String string, String object) {
            Logger logger = this.findLogger(string);
            Object object2 = logger;
            if (logger == null) {
                object2 = new Logger(string, (String)object, null, this.getOwner(), true);
                while ((object = this.addLocalLogger((Logger)object2) ? object2 : this.findLogger(string)) == null) {
                }
                object2 = object;
            }
            return object2;
        }
    }

}

