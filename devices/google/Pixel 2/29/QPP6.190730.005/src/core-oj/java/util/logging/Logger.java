/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

public class Logger {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String GLOBAL_LOGGER_NAME = "global";
    private static final LoggerBundle NO_RESOURCE_BUNDLE;
    private static final LoggerBundle SYSTEM_BUNDLE;
    static final String SYSTEM_LOGGER_RB_NAME = "sun.util.logging.resources.logging";
    private static final Handler[] emptyHandlers;
    @Deprecated
    public static final Logger global;
    private static final int offValue;
    private static final Object treeLock;
    private boolean anonymous;
    private WeakReference<ClassLoader> callersClassLoaderRef;
    private ResourceBundle catalog;
    private Locale catalogLocale;
    private String catalogName;
    private volatile Filter filter;
    private final CopyOnWriteArrayList<Handler> handlers = new CopyOnWriteArrayList();
    private final boolean isSystemLogger;
    private ArrayList<LogManager.LoggerWeakRef> kids;
    private volatile Level levelObject;
    private volatile int levelValue;
    private volatile LoggerBundle loggerBundle = NO_RESOURCE_BUNDLE;
    private volatile LogManager manager;
    private String name;
    private volatile Logger parent;
    private volatile boolean useParentHandlers = true;

    static {
        emptyHandlers = new Handler[0];
        offValue = Level.OFF.intValue();
        SYSTEM_BUNDLE = new LoggerBundle(SYSTEM_LOGGER_RB_NAME, null);
        NO_RESOURCE_BUNDLE = new LoggerBundle(null, null);
        treeLock = new Object();
        global = new Logger(GLOBAL_LOGGER_NAME);
    }

    private Logger(String string) {
        this.name = string;
        this.isSystemLogger = true;
        this.levelValue = Level.INFO.intValue();
    }

    protected Logger(String string, String string2) {
        this(string, string2, null, LogManager.getLogManager(), false);
    }

    Logger(String string, String string2, Class<?> class_, LogManager logManager, boolean bl) {
        this.manager = logManager;
        this.isSystemLogger = bl;
        this.setupResourceInfo(string2, class_);
        this.name = string;
        this.levelValue = Level.INFO.intValue();
    }

    private void checkPermission() throws SecurityException {
        if (!this.anonymous) {
            if (this.manager == null) {
                this.manager = LogManager.getLogManager();
            }
            this.manager.checkPermission();
        }
    }

    private static Logger demandLogger(String string, String string2, Class<?> class_) {
        LogManager logManager = LogManager.getLogManager();
        if (System.getSecurityManager() != null && !SystemLoggerHelper.disableCallerCheck && class_.getClassLoader() == null) {
            return logManager.demandSystemLogger(string, string2);
        }
        return logManager.demandLogger(string, string2, class_);
    }

    private void doLog(LogRecord logRecord) {
        logRecord.setLoggerName(this.name);
        Object object = this.getEffectiveLoggerBundle();
        ResourceBundle resourceBundle = ((LoggerBundle)object).userBundle;
        object = ((LoggerBundle)object).resourceBundleName;
        if (object != null && resourceBundle != null) {
            logRecord.setResourceBundleName((String)object);
            logRecord.setResourceBundle(resourceBundle);
        }
        this.log(logRecord);
    }

    private void doLog(LogRecord logRecord, String string) {
        logRecord.setLoggerName(this.name);
        if (string != null) {
            logRecord.setResourceBundleName(string);
            logRecord.setResourceBundle(this.findResourceBundle(string, false));
        }
        this.log(logRecord);
    }

    private void doLog(LogRecord logRecord, ResourceBundle resourceBundle) {
        logRecord.setLoggerName(this.name);
        if (resourceBundle != null) {
            logRecord.setResourceBundleName(resourceBundle.getBaseBundleName());
            logRecord.setResourceBundle(resourceBundle);
        }
        this.log(logRecord);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void doSetParent(Logger object) {
        Object object2 = treeLock;
        synchronized (object2) {
            ArrayList arrayList;
            Object object3;
            block6 : {
                object3 = null;
                if (this.parent != null) {
                    arrayList = this.parent.kids.iterator();
                    do {
                        object3 = null;
                        if (!arrayList.hasNext()) break block6;
                    } while ((Logger)((Reference)(object3 = arrayList.next())).get() != this);
                    arrayList.remove();
                }
            }
            this.parent = object;
            if (this.parent.kids == null) {
                object = this.parent;
                arrayList = new ArrayList(2);
                ((Logger)object).kids = arrayList;
            }
            object = object3;
            if (object3 == null) {
                object3 = this.manager;
                Objects.requireNonNull(object3);
                object = (LogManager)object3.new LogManager.LoggerWeakRef(this);
            }
            object3 = new WeakReference(this.parent);
            ((LogManager.LoggerWeakRef)object).setParentRef((WeakReference<Logger>)object3);
            this.parent.kids.add((LogManager.LoggerWeakRef)object);
            this.updateEffectiveLevel();
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ResourceBundle findResourceBundle(String object, boolean bl) {
        synchronized (this) {
            Object object2;
            if (object == null) {
                return null;
            }
            Locale locale = Locale.getDefault();
            Object object3 = this.loggerBundle;
            if (((LoggerBundle)object3).userBundle != null && ((String)object).equals(((LoggerBundle)object3).resourceBundleName)) {
                return ((LoggerBundle)object3).userBundle;
            }
            if (this.catalog != null && locale.equals(this.catalogLocale) && ((String)object).equals(this.catalogName)) {
                return this.catalog;
            }
            if (((String)object).equals(SYSTEM_LOGGER_RB_NAME)) {
                this.catalog = Logger.findSystemResourceBundle(locale);
                this.catalogName = object;
                this.catalogLocale = locale;
                return this.catalog;
            }
            object3 = object2 = Thread.currentThread().getContextClassLoader();
            if (object2 == null) {
                object3 = ClassLoader.getSystemClassLoader();
            }
            try {
                this.catalog = ResourceBundle.getBundle((String)object, locale, (ClassLoader)object3);
                this.catalogName = object;
                this.catalogLocale = locale;
                return this.catalog;
            }
            catch (MissingResourceException missingResourceException) {
                void var2_3;
                if (var2_3 == false) {
                    return null;
                }
                ClassLoader classLoader = this.getCallersClassLoader();
                if (classLoader == null) return null;
                if (classLoader != object3) {
                    try {
                        this.catalog = ResourceBundle.getBundle((String)object, locale, classLoader);
                        this.catalogName = object;
                        this.catalogLocale = locale;
                        return this.catalog;
                    }
                    catch (MissingResourceException missingResourceException2) {
                        return null;
                    }
                }
                return null;
            }
        }
    }

    private static ResourceBundle findSystemResourceBundle(final Locale locale) {
        return AccessController.doPrivileged(new PrivilegedAction<ResourceBundle>(){

            @Override
            public ResourceBundle run() {
                try {
                    ResourceBundle resourceBundle = ResourceBundle.getBundle(Logger.SYSTEM_LOGGER_RB_NAME, locale, ClassLoader.getSystemClassLoader());
                    return resourceBundle;
                }
                catch (MissingResourceException missingResourceException) {
                    throw new InternalError(missingResourceException.toString());
                }
            }
        });
    }

    public static Logger getAnonymousLogger() {
        return Logger.getAnonymousLogger(null);
    }

    @CallerSensitive
    public static Logger getAnonymousLogger(String object) {
        LogManager logManager = LogManager.getLogManager();
        logManager.drainLoggerRefQueueBounded();
        object = new Logger(null, (String)object, Reflection.getCallerClass(), logManager, false);
        ((Logger)object).anonymous = true;
        Logger.super.doSetParent(logManager.getLogger(""));
        return object;
    }

    private ClassLoader getCallersClassLoader() {
        WeakReference<ClassLoader> weakReference = this.callersClassLoaderRef;
        weakReference = weakReference != null ? (ClassLoader)weakReference.get() : null;
        return weakReference;
    }

    private LoggerBundle getEffectiveLoggerBundle() {
        Object object = this.loggerBundle;
        if (((LoggerBundle)object).isSystemBundle()) {
            return SYSTEM_BUNDLE;
        }
        Object object2 = this.getResourceBundle();
        if (object2 != null && object2 == ((LoggerBundle)object).userBundle) {
            return object;
        }
        if (object2 != null) {
            return LoggerBundle.get(this.getResourceBundleName(), (ResourceBundle)object2);
        }
        object = this.parent;
        while (object != null) {
            object2 = ((Logger)object).loggerBundle;
            if (((LoggerBundle)object2).isSystemBundle()) {
                return SYSTEM_BUNDLE;
            }
            if (((LoggerBundle)object2).userBundle != null) {
                return object2;
            }
            object2 = this.isSystemLogger ? (((Logger)object).isSystemLogger ? ((LoggerBundle)object2).resourceBundleName : null) : ((Logger)object).getResourceBundleName();
            if (object2 != null) {
                return LoggerBundle.get((String)object2, this.findResourceBundle((String)object2, true));
            }
            object2 = this.isSystemLogger ? ((Logger)object).parent : ((Logger)object).getParent();
            object = object2;
        }
        return NO_RESOURCE_BUNDLE;
    }

    public static final Logger getGlobal() {
        LogManager.getLogManager();
        return global;
    }

    @CallerSensitive
    public static Logger getLogger(String string) {
        return Logger.demandLogger(string, null, Reflection.getCallerClass());
    }

    @CallerSensitive
    public static Logger getLogger(String object, String string) {
        Class<?> class_ = Reflection.getCallerClass();
        object = Logger.demandLogger((String)object, string, class_);
        Logger.super.setupResourceInfo(string, class_);
        return object;
    }

    static Logger getPlatformLogger(String string) {
        return LogManager.getLogManager().demandSystemLogger(string, SYSTEM_LOGGER_RB_NAME);
    }

    private void setCallersClassLoaderRef(Class<?> object) {
        if ((object = object != null ? ((Class)object).getClassLoader() : null) != null) {
            this.callersClassLoaderRef = new WeakReference<Object>(object);
        }
    }

    private void setupResourceInfo(String string, Class<?> serializable) {
        synchronized (this) {
            Object object;
            block8 : {
                block9 : {
                    object = this.loggerBundle;
                    if (((LoggerBundle)object).resourceBundleName == null) break block8;
                    boolean bl = ((LoggerBundle)object).resourceBundleName.equals(string);
                    if (!bl) break block9;
                    return;
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append(((LoggerBundle)object).resourceBundleName);
                ((StringBuilder)serializable).append(" != ");
                ((StringBuilder)serializable).append(string);
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)serializable).toString());
                throw illegalArgumentException;
            }
            if (string == null) {
                return;
            }
            this.setCallersClassLoaderRef((Class<?>)serializable);
            if (this.isSystemLogger && this.getCallersClassLoader() != null) {
                this.checkPermission();
            }
            if (this.findResourceBundle(string, true) != null) {
                this.loggerBundle = LoggerBundle.get(string, null);
                return;
            }
            this.callersClassLoaderRef = null;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Can't find ");
            ((StringBuilder)serializable).append(string);
            ((StringBuilder)serializable).append(" bundle");
            object = new MissingResourceException(((StringBuilder)serializable).toString(), string, "");
            throw object;
        }
    }

    private void updateEffectiveLevel() {
        int n = this.levelObject != null ? this.levelObject.intValue() : (this.parent != null ? this.parent.levelValue : Level.INFO.intValue());
        if (this.levelValue == n) {
            return;
        }
        this.levelValue = n;
        if (this.kids != null) {
            for (n = 0; n < this.kids.size(); ++n) {
                Logger logger = (Logger)this.kids.get(n).get();
                if (logger == null) continue;
                logger.updateEffectiveLevel();
            }
        }
    }

    Handler[] accessCheckedHandlers() {
        return this.handlers.toArray(emptyHandlers);
    }

    public void addHandler(Handler handler) throws SecurityException {
        handler.getClass();
        this.checkPermission();
        this.handlers.add(handler);
    }

    public void config(String string) {
        this.log(Level.CONFIG, string);
    }

    public void config(Supplier<String> supplier) {
        this.log(Level.CONFIG, supplier);
    }

    public void entering(String string, String string2) {
        this.logp(Level.FINER, string, string2, "ENTRY");
    }

    public void entering(String string, String string2, Object object) {
        this.logp(Level.FINER, string, string2, "ENTRY {0}", object);
    }

    public void entering(String string, String string2, Object[] arrobject) {
        String string3 = "ENTRY";
        if (arrobject == null) {
            this.logp(Level.FINER, string, string2, "ENTRY");
            return;
        }
        if (!this.isLoggable(Level.FINER)) {
            return;
        }
        for (int i = 0; i < arrobject.length; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string3);
            stringBuilder.append(" {");
            stringBuilder.append(i);
            stringBuilder.append("}");
            string3 = stringBuilder.toString();
        }
        this.logp(Level.FINER, string, string2, string3, arrobject);
    }

    public void exiting(String string, String string2) {
        this.logp(Level.FINER, string, string2, "RETURN");
    }

    public void exiting(String string, String string2, Object object) {
        this.logp(Level.FINER, string, string2, "RETURN {0}", object);
    }

    public void fine(String string) {
        this.log(Level.FINE, string);
    }

    public void fine(Supplier<String> supplier) {
        this.log(Level.FINE, supplier);
    }

    public void finer(String string) {
        this.log(Level.FINER, string);
    }

    public void finer(Supplier<String> supplier) {
        this.log(Level.FINER, supplier);
    }

    public void finest(String string) {
        this.log(Level.FINEST, string);
    }

    public void finest(Supplier<String> supplier) {
        this.log(Level.FINEST, supplier);
    }

    public Filter getFilter() {
        return this.filter;
    }

    public Handler[] getHandlers() {
        return this.accessCheckedHandlers();
    }

    public Level getLevel() {
        return this.levelObject;
    }

    public String getName() {
        return this.name;
    }

    public Logger getParent() {
        return this.parent;
    }

    public ResourceBundle getResourceBundle() {
        return this.findResourceBundle(this.getResourceBundleName(), true);
    }

    public String getResourceBundleName() {
        return this.loggerBundle.resourceBundleName;
    }

    public boolean getUseParentHandlers() {
        return this.useParentHandlers;
    }

    public void info(String string) {
        this.log(Level.INFO, string);
    }

    public void info(Supplier<String> supplier) {
        this.log(Level.INFO, supplier);
    }

    final boolean isLevelInitialized() {
        boolean bl = this.levelObject != null;
        return bl;
    }

    public boolean isLoggable(Level level) {
        return level.intValue() >= this.levelValue && this.levelValue != offValue;
        {
        }
    }

    public void log(Level level, String string) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(new LogRecord(level, string));
    }

    public void log(Level serializable, String string, Object object) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string);
        ((LogRecord)serializable).setParameters(new Object[]{object});
        this.doLog((LogRecord)serializable);
    }

    public void log(Level serializable, String string, Throwable throwable) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string);
        ((LogRecord)serializable).setThrown(throwable);
        this.doLog((LogRecord)serializable);
    }

    public void log(Level serializable, String string, Object[] arrobject) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string);
        ((LogRecord)serializable).setParameters(arrobject);
        this.doLog((LogRecord)serializable);
    }

    public void log(Level serializable, Throwable throwable, Supplier<String> supplier) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, supplier.get());
        ((LogRecord)serializable).setThrown(throwable);
        this.doLog((LogRecord)serializable);
    }

    public void log(Level level, Supplier<String> supplier) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(new LogRecord(level, supplier.get()));
    }

    public void log(LogRecord logRecord) {
        if (!this.isLoggable(logRecord.getLevel())) {
            return;
        }
        Object object = this.filter;
        if (object != null && !object.isLoggable(logRecord)) {
            return;
        }
        object = this;
        while (object != null) {
            Handler[] arrhandler = this.isSystemLogger ? ((Logger)object).accessCheckedHandlers() : ((Logger)object).getHandlers();
            int n = arrhandler.length;
            for (int i = 0; i < n; ++i) {
                arrhandler[i].publish(logRecord);
            }
            boolean bl = this.isSystemLogger ? ((Logger)object).useParentHandlers : ((Logger)object).getUseParentHandlers();
            if (!bl) break;
            if (this.isSystemLogger) {
                object = ((Logger)object).parent;
                continue;
            }
            object = ((Logger)object).getParent();
        }
    }

    public void logp(Level serializable, String string, String string2, String string3) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string3);
        ((LogRecord)serializable).setSourceClassName(string);
        ((LogRecord)serializable).setSourceMethodName(string2);
        this.doLog((LogRecord)serializable);
    }

    public void logp(Level serializable, String string, String string2, String string3, Object object) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string3);
        ((LogRecord)serializable).setSourceClassName(string);
        ((LogRecord)serializable).setSourceMethodName(string2);
        ((LogRecord)serializable).setParameters(new Object[]{object});
        this.doLog((LogRecord)serializable);
    }

    public void logp(Level serializable, String string, String string2, String string3, Throwable throwable) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string3);
        ((LogRecord)serializable).setSourceClassName(string);
        ((LogRecord)serializable).setSourceMethodName(string2);
        ((LogRecord)serializable).setThrown(throwable);
        this.doLog((LogRecord)serializable);
    }

    public void logp(Level serializable, String string, String string2, String string3, Object[] arrobject) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string3);
        ((LogRecord)serializable).setSourceClassName(string);
        ((LogRecord)serializable).setSourceMethodName(string2);
        ((LogRecord)serializable).setParameters(arrobject);
        this.doLog((LogRecord)serializable);
    }

    public void logp(Level serializable, String string, String string2, Throwable throwable, Supplier<String> supplier) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, supplier.get());
        ((LogRecord)serializable).setSourceClassName(string);
        ((LogRecord)serializable).setSourceMethodName(string2);
        ((LogRecord)serializable).setThrown(throwable);
        this.doLog((LogRecord)serializable);
    }

    public void logp(Level serializable, String string, String string2, Supplier<String> supplier) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, supplier.get());
        ((LogRecord)serializable).setSourceClassName(string);
        ((LogRecord)serializable).setSourceMethodName(string2);
        this.doLog((LogRecord)serializable);
    }

    @Deprecated
    public void logrb(Level serializable, String string, String string2, String string3, String string4) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string4);
        ((LogRecord)serializable).setSourceClassName(string);
        ((LogRecord)serializable).setSourceMethodName(string2);
        this.doLog((LogRecord)serializable, string3);
    }

    @Deprecated
    public void logrb(Level serializable, String string, String string2, String string3, String string4, Object object) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string4);
        ((LogRecord)serializable).setSourceClassName(string);
        ((LogRecord)serializable).setSourceMethodName(string2);
        ((LogRecord)serializable).setParameters(new Object[]{object});
        this.doLog((LogRecord)serializable, string3);
    }

    @Deprecated
    public void logrb(Level serializable, String string, String string2, String string3, String string4, Throwable throwable) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string4);
        ((LogRecord)serializable).setSourceClassName(string);
        ((LogRecord)serializable).setSourceMethodName(string2);
        ((LogRecord)serializable).setThrown(throwable);
        this.doLog((LogRecord)serializable, string3);
    }

    @Deprecated
    public void logrb(Level serializable, String string, String string2, String string3, String string4, Object[] arrobject) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string4);
        ((LogRecord)serializable).setSourceClassName(string);
        ((LogRecord)serializable).setSourceMethodName(string2);
        ((LogRecord)serializable).setParameters(arrobject);
        this.doLog((LogRecord)serializable, string3);
    }

    public void logrb(Level serializable, String string, String string2, ResourceBundle resourceBundle, String string3, Throwable throwable) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string3);
        ((LogRecord)serializable).setSourceClassName(string);
        ((LogRecord)serializable).setSourceMethodName(string2);
        ((LogRecord)serializable).setThrown(throwable);
        this.doLog((LogRecord)serializable, resourceBundle);
    }

    public void logrb(Level serializable, String string, String string2, ResourceBundle resourceBundle, String string3, Object ... arrobject) {
        if (!this.isLoggable((Level)serializable)) {
            return;
        }
        serializable = new LogRecord((Level)serializable, string3);
        ((LogRecord)serializable).setSourceClassName(string);
        ((LogRecord)serializable).setSourceMethodName(string2);
        if (arrobject != null && arrobject.length != 0) {
            ((LogRecord)serializable).setParameters(arrobject);
        }
        this.doLog((LogRecord)serializable, resourceBundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void removeChildLogger(LogManager.LoggerWeakRef loggerWeakRef) {
        Object object = treeLock;
        synchronized (object) {
            Iterator<LogManager.LoggerWeakRef> iterator = this.kids.iterator();
            do {
                if (iterator.hasNext()) continue;
                return;
            } while (iterator.next() != loggerWeakRef);
            iterator.remove();
            return;
        }
    }

    public void removeHandler(Handler handler) throws SecurityException {
        this.checkPermission();
        if (handler == null) {
            return;
        }
        this.handlers.remove(handler);
    }

    public void setFilter(Filter filter) throws SecurityException {
        this.checkPermission();
        this.filter = filter;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setLevel(Level level) throws SecurityException {
        this.checkPermission();
        Object object = treeLock;
        synchronized (object) {
            this.levelObject = level;
            this.updateEffectiveLevel();
            return;
        }
    }

    void setLogManager(LogManager logManager) {
        this.manager = logManager;
    }

    public void setParent(Logger logger) {
        if (logger != null) {
            if (this.manager == null) {
                this.manager = LogManager.getLogManager();
            }
            this.manager.checkPermission();
            this.doSetParent(logger);
            return;
        }
        throw new NullPointerException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setResourceBundle(ResourceBundle object) {
        this.checkPermission();
        String string = ((ResourceBundle)object).getBaseBundleName();
        if (string != null && !string.isEmpty()) {
            synchronized (this) {
                LoggerBundle loggerBundle = this.loggerBundle;
                boolean bl = loggerBundle.resourceBundleName == null || loggerBundle.resourceBundleName.equals(string);
                if (bl) {
                    this.loggerBundle = LoggerBundle.get(string, (ResourceBundle)object);
                    return;
                }
                object = new IllegalArgumentException("can't replace resource bundle");
                throw object;
            }
        }
        throw new IllegalArgumentException("resource bundle must have a name");
    }

    public void setUseParentHandlers(boolean bl) {
        this.checkPermission();
        this.useParentHandlers = bl;
    }

    public void severe(String string) {
        this.log(Level.SEVERE, string);
    }

    public void severe(Supplier<String> supplier) {
        this.log(Level.SEVERE, supplier);
    }

    public void throwing(String string, String string2, Throwable throwable) {
        if (!this.isLoggable(Level.FINER)) {
            return;
        }
        LogRecord logRecord = new LogRecord(Level.FINER, "THROW");
        logRecord.setSourceClassName(string);
        logRecord.setSourceMethodName(string2);
        logRecord.setThrown(throwable);
        this.doLog(logRecord);
    }

    public void warning(String string) {
        this.log(Level.WARNING, string);
    }

    public void warning(Supplier<String> supplier) {
        this.log(Level.WARNING, supplier);
    }

    private static final class LoggerBundle {
        final String resourceBundleName;
        final ResourceBundle userBundle;

        private LoggerBundle(String string, ResourceBundle resourceBundle) {
            this.resourceBundleName = string;
            this.userBundle = resourceBundle;
        }

        static LoggerBundle get(String string, ResourceBundle resourceBundle) {
            if (string == null && resourceBundle == null) {
                return NO_RESOURCE_BUNDLE;
            }
            if (Logger.SYSTEM_LOGGER_RB_NAME.equals(string) && resourceBundle == null) {
                return SYSTEM_BUNDLE;
            }
            return new LoggerBundle(string, resourceBundle);
        }

        boolean isSystemBundle() {
            return Logger.SYSTEM_LOGGER_RB_NAME.equals(this.resourceBundleName);
        }
    }

    private static class SystemLoggerHelper {
        static boolean disableCallerCheck = SystemLoggerHelper.getBooleanProperty("sun.util.logging.disableCallerCheck");

        private SystemLoggerHelper() {
        }

        private static boolean getBooleanProperty(final String string) {
            return Boolean.valueOf(AccessController.doPrivileged(new PrivilegedAction<String>(){

                @Override
                public String run() {
                    return System.getProperty(string);
                }
            }));
        }

    }

}

