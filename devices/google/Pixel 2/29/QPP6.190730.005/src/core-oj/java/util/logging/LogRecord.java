/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMStack
 */
package java.util.logging;

import dalvik.system.VMStack;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

public class LogRecord
implements Serializable {
    private static final int MIN_SEQUENTIAL_THREAD_ID = 1073741823;
    private static final AtomicLong globalSequenceNumber = new AtomicLong(0L);
    private static final AtomicInteger nextThreadId = new AtomicInteger(1073741823);
    private static final long serialVersionUID = 5372048053134512534L;
    private static final ThreadLocal<Integer> threadIds = new ThreadLocal();
    private Level level;
    private String loggerName;
    private String message;
    private long millis;
    private transient boolean needToInferCaller;
    private transient Object[] parameters;
    private transient ResourceBundle resourceBundle;
    private String resourceBundleName;
    private long sequenceNumber;
    private String sourceClassName;
    private String sourceMethodName;
    private int threadID;
    private Throwable thrown;

    public LogRecord(Level level, String string) {
        level.getClass();
        this.level = level;
        this.message = string;
        this.sequenceNumber = globalSequenceNumber.getAndIncrement();
        this.threadID = this.defaultThreadID();
        this.millis = System.currentTimeMillis();
        this.needToInferCaller = true;
    }

    private int defaultThreadID() {
        Integer n;
        long l = Thread.currentThread().getId();
        if (l < 0x3FFFFFFFL) {
            return (int)l;
        }
        Integer n2 = n = threadIds.get();
        if (n == null) {
            n2 = nextThreadId.getAndIncrement();
            threadIds.set(n2);
        }
        return n2;
    }

    private void inferCaller() {
        this.needToInferCaller = false;
        StackTraceElement[] arrstackTraceElement = VMStack.getThreadStackTrace((Thread)Thread.currentThread());
        int n = arrstackTraceElement.length;
        boolean bl = true;
        for (int i = 0; i < n; ++i) {
            boolean bl2;
            StackTraceElement stackTraceElement = arrstackTraceElement[i];
            String string = stackTraceElement.getClassName();
            boolean bl3 = this.isLoggerImplFrame(string);
            if (bl) {
                bl2 = bl;
                if (bl3) {
                    bl2 = false;
                }
            } else {
                bl2 = bl;
                if (!bl3) {
                    bl2 = bl;
                    if (!string.startsWith("java.lang.reflect.")) {
                        bl2 = bl;
                        if (!string.startsWith("sun.reflect.")) {
                            this.setSourceClassName(string);
                            this.setSourceMethodName(stackTraceElement.getMethodName());
                            return;
                        }
                    }
                }
            }
            bl = bl2;
        }
    }

    private boolean isLoggerImplFrame(String string) {
        boolean bl = string.equals("java.util.logging.Logger") || string.startsWith("java.util.logging.LoggingProxyImpl") || string.startsWith("sun.util.logging.");
        return bl;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        int n = ((ObjectInputStream)object).readByte();
        int n2 = ((ObjectInputStream)object).readByte();
        if (n == 1) {
            n = ((ObjectInputStream)object).readInt();
            if (n >= -1) {
                if (n == -1) {
                    this.parameters = null;
                } else if (n < 255) {
                    Object[] arrobject;
                    this.parameters = new Object[n];
                    for (n2 = 0; n2 < (arrobject = this.parameters).length; ++n2) {
                        arrobject[n2] = ((ObjectInputStream)object).readObject();
                    }
                } else {
                    ArrayList<Object> arrayList = new ArrayList<Object>(Math.min(n, 1024));
                    for (n2 = 0; n2 < n; ++n2) {
                        arrayList.add(((ObjectInputStream)object).readObject());
                    }
                    this.parameters = arrayList.toArray(new Object[arrayList.size()]);
                }
                object = this.resourceBundleName;
                if (object != null) {
                    try {
                        this.resourceBundle = ResourceBundle.getBundle((String)object, Locale.getDefault(), ClassLoader.getSystemClassLoader());
                    }
                    catch (MissingResourceException missingResourceException) {
                        try {
                            this.resourceBundle = ResourceBundle.getBundle(this.resourceBundleName, Locale.getDefault(), Thread.currentThread().getContextClassLoader());
                        }
                        catch (MissingResourceException missingResourceException2) {
                            this.resourceBundle = null;
                        }
                    }
                }
                this.needToInferCaller = false;
                return;
            }
            throw new NegativeArraySizeException();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("LogRecord: bad version: ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(".");
        ((StringBuilder)object).append(n2);
        throw new IOException(((StringBuilder)object).toString());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeByte(1);
        objectOutputStream.writeByte(0);
        Object[] arrobject = this.parameters;
        if (arrobject == null) {
            objectOutputStream.writeInt(-1);
            return;
        }
        objectOutputStream.writeInt(arrobject.length);
        for (int i = 0; i < (arrobject = this.parameters).length; ++i) {
            if (arrobject[i] == null) {
                objectOutputStream.writeObject(null);
                continue;
            }
            objectOutputStream.writeObject(arrobject[i].toString());
        }
    }

    public Level getLevel() {
        return this.level;
    }

    public String getLoggerName() {
        return this.loggerName;
    }

    public String getMessage() {
        return this.message;
    }

    public long getMillis() {
        return this.millis;
    }

    public Object[] getParameters() {
        return this.parameters;
    }

    public ResourceBundle getResourceBundle() {
        return this.resourceBundle;
    }

    public String getResourceBundleName() {
        return this.resourceBundleName;
    }

    public long getSequenceNumber() {
        return this.sequenceNumber;
    }

    public String getSourceClassName() {
        if (this.needToInferCaller) {
            this.inferCaller();
        }
        return this.sourceClassName;
    }

    public String getSourceMethodName() {
        if (this.needToInferCaller) {
            this.inferCaller();
        }
        return this.sourceMethodName;
    }

    public int getThreadID() {
        return this.threadID;
    }

    public Throwable getThrown() {
        return this.thrown;
    }

    public void setLevel(Level level) {
        if (level != null) {
            this.level = level;
            return;
        }
        throw new NullPointerException();
    }

    public void setLoggerName(String string) {
        this.loggerName = string;
    }

    public void setMessage(String string) {
        this.message = string;
    }

    public void setMillis(long l) {
        this.millis = l;
    }

    public void setParameters(Object[] arrobject) {
        this.parameters = arrobject;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void setResourceBundleName(String string) {
        this.resourceBundleName = string;
    }

    public void setSequenceNumber(long l) {
        this.sequenceNumber = l;
    }

    public void setSourceClassName(String string) {
        this.sourceClassName = string;
        this.needToInferCaller = false;
    }

    public void setSourceMethodName(String string) {
        this.sourceMethodName = string;
        this.needToInferCaller = false;
    }

    public void setThreadID(int n) {
        this.threadID = n;
    }

    public void setThrown(Throwable throwable) {
        this.thrown = throwable;
    }
}

