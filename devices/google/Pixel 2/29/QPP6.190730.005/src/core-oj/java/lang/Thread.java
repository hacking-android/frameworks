/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  dalvik.system.VMStack
 *  libcore.util.EmptyArray
 */
package java.lang;

import dalvik.annotation.optimization.FastNative;
import dalvik.system.VMStack;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import libcore.util.EmptyArray;
import sun.nio.ch.Interruptible;
import sun.reflect.CallerSensitive;

public class Thread
implements Runnable {
    private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
    public static final int MAX_PRIORITY = 10;
    public static final int MIN_PRIORITY = 1;
    public static final int NORM_PRIORITY = 5;
    private static final RuntimePermission SUBCLASS_IMPLEMENTATION_PERMISSION = new RuntimePermission("enableContextClassLoaderOverride");
    private static volatile UncaughtExceptionHandler defaultUncaughtExceptionHandler;
    private static int threadInitNumber;
    private static long threadSeqNumber;
    private static volatile UncaughtExceptionHandler uncaughtExceptionPreHandler;
    private volatile Interruptible blocker;
    private final Object blockerLock = new Object();
    private ClassLoader contextClassLoader;
    private boolean daemon = false;
    private long eetop;
    private ThreadGroup group;
    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;
    private AccessControlContext inheritedAccessControlContext;
    private final Object lock = new Object();
    private volatile String name;
    private volatile long nativePeer;
    volatile Object parkBlocker;
    private int priority;
    private boolean single_step;
    private long stackSize;
    boolean started = false;
    private boolean stillborn = false;
    private boolean systemDaemon = false;
    private Runnable target;
    int threadLocalRandomProbe;
    int threadLocalRandomSecondarySeed;
    long threadLocalRandomSeed;
    ThreadLocal.ThreadLocalMap threadLocals = null;
    private Thread threadQ;
    private long tid;
    private volatile UncaughtExceptionHandler uncaughtExceptionHandler;
    private boolean unparkedBeforeStart;

    public Thread() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Thread-");
        stringBuilder.append(Thread.nextThreadNum());
        this.init(null, null, stringBuilder.toString(), 0L);
    }

    public Thread(Runnable runnable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Thread-");
        stringBuilder.append(Thread.nextThreadNum());
        this.init(null, runnable, stringBuilder.toString(), 0L);
    }

    public Thread(Runnable runnable, String string) {
        this.init(null, runnable, string, 0L);
    }

    Thread(Runnable runnable, AccessControlContext accessControlContext) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Thread-");
        stringBuilder.append(Thread.nextThreadNum());
        this.init(null, runnable, stringBuilder.toString(), 0L, accessControlContext);
    }

    public Thread(String string) {
        this.init(null, null, string, 0L);
    }

    public Thread(ThreadGroup threadGroup, Runnable runnable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Thread-");
        stringBuilder.append(Thread.nextThreadNum());
        this.init(threadGroup, runnable, stringBuilder.toString(), 0L);
    }

    public Thread(ThreadGroup threadGroup, Runnable runnable, String string) {
        this.init(threadGroup, runnable, string, 0L);
    }

    public Thread(ThreadGroup threadGroup, Runnable runnable, String string, long l) {
        this.init(threadGroup, runnable, string, l);
    }

    public Thread(ThreadGroup threadGroup, String string) {
        this.init(threadGroup, null, string, 0L);
    }

    Thread(ThreadGroup object, String string, int n, boolean bl) {
        this.group = object;
        this.group.addUnstarted();
        object = string;
        if (string == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Thread-");
            ((StringBuilder)object).append(Thread.nextThreadNum());
            object = ((StringBuilder)object).toString();
        }
        this.name = object;
        this.priority = n;
        this.daemon = bl;
        this.init2(Thread.currentThread());
        this.tid = Thread.nextThreadID();
    }

    public static int activeCount() {
        return Thread.currentThread().getThreadGroup().activeCount();
    }

    private static boolean auditSubclass(final Class<?> class_) {
        return AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

            @Override
            public Boolean run() {
                for (Class class_2 = class_; class_2 != Thread.class; class_2 = class_2.getSuperclass()) {
                    try {
                        class_2.getDeclaredMethod("getContextClassLoader", new Class[0]);
                        Boolean bl = Boolean.TRUE;
                        return bl;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        try {
                            class_2.getDeclaredMethod("setContextClassLoader", ClassLoader.class);
                            Boolean bl = Boolean.TRUE;
                            return bl;
                        }
                        catch (NoSuchMethodException noSuchMethodException2) {
                            continue;
                        }
                    }
                }
                return Boolean.FALSE;
            }
        });
    }

    @FastNative
    public static native Thread currentThread();

    public static void dumpStack() {
        new Exception("Stack trace").printStackTrace();
    }

    public static int enumerate(Thread[] arrthread) {
        return Thread.currentThread().getThreadGroup().enumerate(arrthread);
    }

    private void exit() {
        ThreadGroup threadGroup = this.group;
        if (threadGroup != null) {
            threadGroup.threadTerminated(this);
            this.group = null;
        }
        this.target = null;
        this.threadLocals = null;
        this.inheritableThreadLocals = null;
        this.inheritedAccessControlContext = null;
        this.blocker = null;
        this.uncaughtExceptionHandler = null;
    }

    public static Map<Thread, StackTraceElement[]> getAllStackTraces() {
        int n = ThreadGroup.systemThreadGroup.activeCount();
        Thread[] arrthread = new Thread[n / 2 + n];
        int n2 = ThreadGroup.systemThreadGroup.enumerate(arrthread);
        HashMap<Thread, StackTraceElement[]> hashMap = new HashMap<Thread, StackTraceElement[]>();
        for (n = 0; n < n2; ++n) {
            StackTraceElement[] arrstackTraceElement = arrthread[n].getStackTrace();
            hashMap.put(arrthread[n], arrstackTraceElement);
        }
        return hashMap;
    }

    public static UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
        return defaultUncaughtExceptionHandler;
    }

    public static UncaughtExceptionHandler getUncaughtExceptionPreHandler() {
        return uncaughtExceptionPreHandler;
    }

    public static native boolean holdsLock(Object var0);

    private void init(ThreadGroup threadGroup, Runnable runnable, String string, long l) {
        this.init(threadGroup, runnable, string, l, null);
    }

    private void init(ThreadGroup threadGroup, Runnable runnable, String object, long l, AccessControlContext object2) {
        if (object != null) {
            this.name = object;
            object2 = Thread.currentThread();
            object = threadGroup;
            if (threadGroup == null) {
                object = ((Thread)object2).getThreadGroup();
            }
            ((ThreadGroup)object).addUnstarted();
            this.group = object;
            this.daemon = ((Thread)object2).isDaemon();
            this.priority = ((Thread)object2).getPriority();
            this.target = runnable;
            this.init2((Thread)object2);
            this.stackSize = l;
            this.tid = Thread.nextThreadID();
            return;
        }
        throw new NullPointerException("name cannot be null");
    }

    private void init2(Thread object) {
        this.contextClassLoader = ((Thread)object).getContextClassLoader();
        this.inheritedAccessControlContext = AccessController.getContext();
        object = ((Thread)object).inheritableThreadLocals;
        if (object != null) {
            this.inheritableThreadLocals = ThreadLocal.createInheritedMap((ThreadLocal.ThreadLocalMap)object);
        }
    }

    @FastNative
    private native void interrupt0();

    @FastNative
    public static native boolean interrupted();

    private static boolean isCCLOverridden(Class<?> class_) {
        Boolean bl;
        if (class_ == Thread.class) {
            return false;
        }
        Thread.processQueue(Caches.subclassAuditsQueue, Caches.subclassAudits);
        WeakClassKey weakClassKey = new WeakClassKey(class_, Caches.subclassAuditsQueue);
        Boolean bl2 = bl = (Boolean)Caches.subclassAudits.get(weakClassKey);
        if (bl == null) {
            bl2 = Thread.auditSubclass(class_);
            Caches.subclassAudits.putIfAbsent(weakClassKey, bl2);
        }
        return bl2;
    }

    private static native void nativeCreate(Thread var0, long var1, boolean var3);

    private native int nativeGetStatus(boolean var1);

    private static long nextThreadID() {
        synchronized (Thread.class) {
            long l;
            threadSeqNumber = l = threadSeqNumber + 1L;
            return l;
        }
    }

    private static int nextThreadNum() {
        synchronized (Thread.class) {
            int n = threadInitNumber;
            threadInitNumber = n + 1;
            return n;
        }
    }

    static void processQueue(ReferenceQueue<Class<?>> referenceQueue, ConcurrentMap<? extends WeakReference<Class<?>>, ?> concurrentMap) {
        Reference<Class<?>> reference;
        while ((reference = referenceQueue.poll()) != null) {
            concurrentMap.remove(reference);
        }
    }

    public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        defaultUncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    private native void setNativeName(String var1);

    private native void setPriority0(int var1);

    public static void setUncaughtExceptionPreHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        uncaughtExceptionPreHandler = uncaughtExceptionHandler;
    }

    public static void sleep(long l) throws InterruptedException {
        Thread.sleep(l, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void sleep(long l, int n) throws InterruptedException {
        if (l < 0L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("millis < 0: ");
            stringBuilder.append(l);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (n < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("nanos < 0: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (n > 999999) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("nanos > 999999: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (l == 0L && n == 0) {
            if (!Thread.interrupted()) {
                return;
            }
            throw new InterruptedException();
        }
        long l2 = System.nanoTime();
        long l3 = n;
        Object object = Thread.currentThread().lock;
        synchronized (object) {
            long l4 = l * 1000000L + l3;
            l3 = l;
            l = l4;
            do {
                Thread.sleep(object, l3, n);
                l3 = System.nanoTime();
                l2 = l3 - l2;
                if (l2 >= l) {
                    return;
                }
                l -= l2;
                l2 = l3;
                l3 = l / 1000000L;
                n = (int)(l % 1000000L);
            } while (true);
        }
    }

    @FastNative
    private static native void sleep(Object var0, long var1, int var3) throws InterruptedException;

    public static native void yield();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void blockedOn(Interruptible interruptible) {
        Object object = this.blockerLock;
        synchronized (object) {
            this.blocker = interruptible;
            return;
        }
    }

    public final void checkAccess() {
    }

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Deprecated
    public int countStackFrames() {
        return this.getStackTrace().length;
    }

    @Deprecated
    public void destroy() {
        throw new UnsupportedOperationException();
    }

    public final void dispatchUncaughtException(Throwable throwable) {
        UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getUncaughtExceptionPreHandler();
        if (uncaughtExceptionHandler != null) {
            try {
                uncaughtExceptionHandler.uncaughtException(this, throwable);
            }
            catch (Error | RuntimeException throwable2) {
                // empty catch block
            }
        }
        this.getUncaughtExceptionHandler().uncaughtException(this, throwable);
    }

    @CallerSensitive
    public ClassLoader getContextClassLoader() {
        return this.contextClassLoader;
    }

    public long getId() {
        return this.tid;
    }

    public final String getName() {
        return this.name;
    }

    public final int getPriority() {
        return this.priority;
    }

    public StackTraceElement[] getStackTrace() {
        StackTraceElement[] arrstackTraceElement = VMStack.getThreadStackTrace((Thread)this);
        if (arrstackTraceElement == null) {
            arrstackTraceElement = EmptyArray.STACK_TRACE_ELEMENT;
        }
        return arrstackTraceElement;
    }

    public State getState() {
        return State.values()[this.nativeGetStatus(this.started)];
    }

    public final ThreadGroup getThreadGroup() {
        if (this.getState() == State.TERMINATED) {
            return null;
        }
        return this.group;
    }

    public UncaughtExceptionHandler getUncaughtExceptionHandler() {
        UncaughtExceptionHandler uncaughtExceptionHandler = this.uncaughtExceptionHandler != null ? this.uncaughtExceptionHandler : this.group;
        return uncaughtExceptionHandler;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void interrupt() {
        if (this != Thread.currentThread()) {
            this.checkAccess();
        }
        Object object = this.blockerLock;
        synchronized (object) {
            Interruptible interruptible = this.blocker;
            if (interruptible != null) {
                this.interrupt0();
                interruptible.interrupt(this);
                return;
            }
        }
        this.interrupt0();
    }

    public final boolean isAlive() {
        boolean bl = this.nativePeer != 0L;
        return bl;
    }

    public final boolean isDaemon() {
        return this.daemon;
    }

    @FastNative
    public native boolean isInterrupted();

    public final void join() throws InterruptedException {
        this.join(0L);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void join(long l) throws InterruptedException {
        Object object = this.lock;
        synchronized (object) {
            long l2 = System.currentTimeMillis();
            long l3 = 0L;
            if (l < 0L) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("timeout value is negative");
                throw illegalArgumentException;
            }
            if (l == 0L) {
                while (this.isAlive()) {
                    this.lock.wait(0L);
                }
            } else {
                while (this.isAlive() && (l3 = l - l3) > 0L) {
                    this.lock.wait(l3);
                    l3 = System.currentTimeMillis() - l2;
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
    public final void join(long l, int n) throws InterruptedException {
        Object object = this.lock;
        synchronized (object) {
            block6 : {
                long l2;
                block8 : {
                    block7 : {
                        if (l < 0L) {
                            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("timeout value is negative");
                            throw illegalArgumentException;
                        }
                        if (n < 0 || n > 999999) break block6;
                        if (n >= 500000) break block7;
                        l2 = l;
                        if (n == 0) break block8;
                        l2 = l;
                        if (l != 0L) break block8;
                    }
                    l2 = l + 1L;
                }
                this.join(l2);
                return;
            }
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("nanosecond timeout value out of range");
            throw illegalArgumentException;
        }
    }

    @Deprecated
    public final void resume() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        Runnable runnable = this.target;
        if (runnable != null) {
            runnable.run();
        }
    }

    public void setContextClassLoader(ClassLoader classLoader) {
        this.contextClassLoader = classLoader;
    }

    public final void setDaemon(boolean bl) {
        this.checkAccess();
        if (!this.isAlive()) {
            this.daemon = bl;
            return;
        }
        throw new IllegalThreadStateException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setName(String object) {
        synchronized (this) {
            this.checkAccess();
            if (object == null) {
                object = new NullPointerException("name cannot be null");
                throw object;
            }
            this.name = object;
            if (this.isAlive()) {
                this.setNativeName((String)object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setPriority(int n) {
        this.checkAccess();
        if (n <= 10 && n >= 1) {
            ThreadGroup threadGroup = this.getThreadGroup();
            if (threadGroup != null) {
                if (n > threadGroup.getMaxPriority()) {
                    n = threadGroup.getMaxPriority();
                }
                synchronized (this) {
                    this.priority = n;
                    if (this.isAlive()) {
                        this.setPriority0(n);
                    }
                }
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Priority out of range: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    final void setSystemDaemon(boolean bl) {
        this.checkAccess();
        if (!this.isAlive() && this.isDaemon()) {
            this.systemDaemon = bl;
            return;
        }
        throw new IllegalThreadStateException();
    }

    public void setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.checkAccess();
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void start() {
        synchronized (this) {
            if (this.started) {
                IllegalThreadStateException illegalThreadStateException = new IllegalThreadStateException();
                throw illegalThreadStateException;
            }
            this.group.add(this);
            this.started = false;
            Thread.nativeCreate(this, this.stackSize, this.daemon);
            this.started = true;
            return;
            finally {
                try {
                    if (!this.started) {
                        this.group.threadStartFailed(this);
                    }
                }
                catch (Throwable throwable) {}
            }
        }
    }

    @Deprecated
    public final void stop() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void stop(Throwable throwable) {
        synchronized (this) {
            throwable = new UnsupportedOperationException();
            throw throwable;
        }
    }

    @Deprecated
    public final void suspend() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        Object object = this.getThreadGroup();
        if (object != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Thread[");
            stringBuilder.append(this.getName());
            stringBuilder.append(",");
            stringBuilder.append(this.getPriority());
            stringBuilder.append(",");
            stringBuilder.append(((ThreadGroup)object).getName());
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Thread[");
        ((StringBuilder)object).append(this.getName());
        ((StringBuilder)object).append(",");
        ((StringBuilder)object).append(this.getPriority());
        ((StringBuilder)object).append(",]");
        return ((StringBuilder)object).toString();
    }

    private static class Caches {
        static final ConcurrentMap<WeakClassKey, Boolean> subclassAudits = new ConcurrentHashMap<WeakClassKey, Boolean>();
        static final ReferenceQueue<Class<?>> subclassAuditsQueue = new ReferenceQueue();

        private Caches() {
        }
    }

    public static enum State {
        NEW,
        RUNNABLE,
        BLOCKED,
        WAITING,
        TIMED_WAITING,
        TERMINATED;
        
    }

    @FunctionalInterface
    public static interface UncaughtExceptionHandler {
        public void uncaughtException(Thread var1, Throwable var2);
    }

    static class WeakClassKey
    extends WeakReference<Class<?>> {
        private final int hash;

        WeakClassKey(Class<?> class_, ReferenceQueue<Class<?>> referenceQueue) {
            super(class_, referenceQueue);
            this.hash = System.identityHashCode(class_);
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (object instanceof WeakClassKey) {
                Object t = this.get();
                if (t == null || t != ((WeakClassKey)object).get()) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return this.hash;
        }
    }

}

