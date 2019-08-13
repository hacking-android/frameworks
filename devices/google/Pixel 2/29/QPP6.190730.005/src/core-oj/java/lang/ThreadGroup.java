/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.PrintStream;
import java.util.Arrays;
import sun.misc.VM;

public class ThreadGroup
implements Thread.UncaughtExceptionHandler {
    static final ThreadGroup mainThreadGroup;
    static final ThreadGroup systemThreadGroup;
    boolean daemon;
    boolean destroyed;
    ThreadGroup[] groups;
    int maxPriority;
    int nUnstartedThreads = 0;
    String name;
    int ngroups;
    int nthreads;
    private final ThreadGroup parent;
    Thread[] threads;
    boolean vmAllowSuspension;

    static {
        systemThreadGroup = new ThreadGroup();
        mainThreadGroup = new ThreadGroup(systemThreadGroup, "main");
    }

    private ThreadGroup() {
        this.name = "system";
        this.maxPriority = 10;
        this.parent = null;
    }

    public ThreadGroup(String string) {
        this(Thread.currentThread().getThreadGroup(), string);
    }

    public ThreadGroup(ThreadGroup threadGroup, String string) {
        this(ThreadGroup.checkParentAccess(threadGroup), threadGroup, string);
    }

    private ThreadGroup(Void void_, ThreadGroup threadGroup, String string) {
        this.name = string;
        this.maxPriority = threadGroup.maxPriority;
        this.daemon = threadGroup.daemon;
        this.vmAllowSuspension = threadGroup.vmAllowSuspension;
        this.parent = threadGroup;
        threadGroup.add(this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void add(ThreadGroup object) {
        synchronized (this) {
            if (this.destroyed) {
                object = new IllegalThreadStateException();
                throw object;
            }
            if (this.groups == null) {
                this.groups = new ThreadGroup[4];
            } else if (this.ngroups == this.groups.length) {
                this.groups = Arrays.copyOf(this.groups, this.ngroups * 2);
            }
            this.groups[this.ngroups] = object;
            ++this.ngroups;
            return;
        }
    }

    private static Void checkParentAccess(ThreadGroup threadGroup) {
        threadGroup.checkAccess();
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private int enumerate(Thread[] arrthread, int n, boolean bl) {
        int n2;
        int n3;
        int n4 = 0;
        ThreadGroup[] arrthreadGroup = null;
        // MONITORENTER : this
        if (this.destroyed) {
            // MONITOREXIT : this
            return 0;
        }
        int n5 = n3 = this.nthreads;
        if (n3 > arrthread.length - n) {
            n5 = arrthread.length - n;
        }
        for (n2 = 0; n2 < n5; ++n2) {
            boolean bl2 = this.threads[n2].isAlive();
            n3 = n;
            if (bl2) {
                arrthread[n] = this.threads[n2];
                n3 = n + 1;
            }
            n = n3;
        }
        n5 = n4;
        if (bl) {
            n5 = this.ngroups;
            arrthreadGroup = this.groups != null ? Arrays.copyOf(this.groups, n5) : null;
        }
        // MONITOREXIT : this
        n3 = n;
        if (!bl) return n3;
        n2 = 0;
        do {
            n3 = n;
            if (n2 >= n5) return n3;
            n = arrthreadGroup[n2].enumerate(arrthread, n, true);
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int enumerate(ThreadGroup[] arrthreadGroup, int n, boolean bl) {
        int n2;
        int n3;
        int n4 = 0;
        ThreadGroup[] arrthreadGroup2 = null;
        synchronized (this) {
            if (this.destroyed) {
                return 0;
            }
            n3 = n2 = this.ngroups;
            if (n2 > arrthreadGroup.length - n) {
                n3 = arrthreadGroup.length - n;
            }
            n2 = n;
            if (n3 > 0) {
                System.arraycopy(this.groups, 0, arrthreadGroup, n, n3);
                n2 = n + n3;
            }
            n = n4;
            if (bl) {
                n = this.ngroups;
                arrthreadGroup2 = this.groups != null ? Arrays.copyOf(this.groups, n) : null;
            }
        }
        n4 = n2;
        if (bl) {
            n3 = 0;
            do {
                n4 = n2;
                if (n3 >= n) break;
                n2 = arrthreadGroup2[n3].enumerate(arrthreadGroup, n2, true);
                ++n3;
            } while (true);
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void remove(Thread arrthread) {
        synchronized (this) {
            if (this.destroyed) {
                return;
            }
            for (int i = 0; i < this.nthreads; ++i) {
                int n;
                if (this.threads[i] != arrthread) continue;
                Thread[] arrthread2 = this.threads;
                arrthread = this.threads;
                this.nthreads = n = this.nthreads - 1;
                System.arraycopy(arrthread2, i + 1, arrthread, i, n - i);
                this.threads[this.nthreads] = null;
                break;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void remove(ThreadGroup threadGroup) {
        synchronized (this) {
            if (this.destroyed) {
                return;
            }
            for (int i = 0; i < this.ngroups; ++i) {
                if (this.groups[i] != threadGroup) continue;
                --this.ngroups;
                System.arraycopy(this.groups, i + 1, this.groups, i, this.ngroups - i);
                this.groups[this.ngroups] = null;
                break;
            }
            if (this.nthreads == 0) {
                this.notifyAll();
            }
            if (this.daemon && this.nthreads == 0 && this.nUnstartedThreads == 0 && this.ngroups == 0) {
                this.destroy();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean stopOrSuspend(boolean bl) {
        int n;
        int n2;
        boolean bl2 = false;
        Thread thread = Thread.currentThread();
        ThreadGroup[] arrthreadGroup = null;
        synchronized (this) {
            this.checkAccess();
            for (n = 0; n < this.nthreads; ++n) {
                if (this.threads[n] == thread) {
                    bl2 = true;
                    continue;
                }
                if (bl) {
                    this.threads[n].suspend();
                    continue;
                }
                this.threads[n].stop();
            }
            n2 = this.ngroups;
            if (this.groups != null) {
                arrthreadGroup = Arrays.copyOf(this.groups, n2);
            }
        }
        n = 0;
        while (n < n2) {
            bl2 = arrthreadGroup[n].stopOrSuspend(bl) || bl2;
            ++n;
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int activeCount() {
        int n;
        ThreadGroup[] arrthreadGroup;
        int n2;
        synchronized (this) {
            if (this.destroyed) {
                return 0;
            }
            n = this.nthreads;
            n2 = this.ngroups;
            arrthreadGroup = this.groups != null ? Arrays.copyOf(this.groups, n2) : null;
        }
        int n3 = 0;
        while (n3 < n2) {
            n += arrthreadGroup[n3].activeCount();
            ++n3;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int activeGroupCount() {
        int n;
        ThreadGroup[] arrthreadGroup;
        synchronized (this) {
            if (this.destroyed) {
                return 0;
            }
            n = this.ngroups;
            arrthreadGroup = this.groups != null ? Arrays.copyOf(this.groups, n) : null;
        }
        int n2 = n;
        int n3 = 0;
        while (n3 < n) {
            n2 += arrthreadGroup[n3].activeGroupCount();
            ++n3;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void add(Thread object) {
        synchronized (this) {
            if (this.destroyed) {
                object = new IllegalThreadStateException();
                throw object;
            }
            if (this.threads == null) {
                this.threads = new Thread[4];
            } else if (this.nthreads == this.threads.length) {
                this.threads = Arrays.copyOf(this.threads, this.nthreads * 2);
            }
            this.threads[this.nthreads] = object;
            ++this.nthreads;
            --this.nUnstartedThreads;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void addUnstarted() {
        synchronized (this) {
            if (!this.destroyed) {
                ++this.nUnstartedThreads;
                return;
            }
            IllegalThreadStateException illegalThreadStateException = new IllegalThreadStateException();
            throw illegalThreadStateException;
        }
    }

    @Deprecated
    public boolean allowThreadSuspension(boolean bl) {
        this.vmAllowSuspension = bl;
        if (!bl) {
            VM.unsuspendSomeThreads();
        }
        return true;
    }

    public final void checkAccess() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void destroy() {
        synchronized (this) {
            this.checkAccess();
            if (!this.destroyed && this.nthreads <= 0) {
                int n = this.ngroups;
                Object object = this.groups != null ? Arrays.copyOf(this.groups, n) : null;
                if (this.parent != null) {
                    this.destroyed = true;
                    this.ngroups = 0;
                    this.groups = null;
                    this.nthreads = 0;
                    this.threads = null;
                }
                // MONITOREXIT [2, 6] lbl12 : MonitorExitStatement: MONITOREXIT : this
                for (int i = 0; i < n; ++i) {
                    object[i].destroy();
                }
                object = this.parent;
                if (object != null) {
                    ThreadGroup.super.remove(this);
                }
                return;
            }
            IllegalThreadStateException illegalThreadStateException = new IllegalThreadStateException();
            throw illegalThreadStateException;
        }
    }

    public int enumerate(Thread[] arrthread) {
        this.checkAccess();
        return this.enumerate(arrthread, 0, true);
    }

    public int enumerate(Thread[] arrthread, boolean bl) {
        this.checkAccess();
        return this.enumerate(arrthread, 0, bl);
    }

    public int enumerate(ThreadGroup[] arrthreadGroup) {
        this.checkAccess();
        return this.enumerate(arrthreadGroup, 0, true);
    }

    public int enumerate(ThreadGroup[] arrthreadGroup, boolean bl) {
        this.checkAccess();
        return this.enumerate(arrthreadGroup, 0, bl);
    }

    public final int getMaxPriority() {
        return this.maxPriority;
    }

    public final String getName() {
        return this.name;
    }

    public final ThreadGroup getParent() {
        ThreadGroup threadGroup = this.parent;
        if (threadGroup != null) {
            threadGroup.checkAccess();
        }
        return this.parent;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void interrupt() {
        int n;
        ThreadGroup[] arrthreadGroup;
        int n2;
        synchronized (this) {
            this.checkAccess();
            for (n = 0; n < this.nthreads; ++n) {
                this.threads[n].interrupt();
            }
            n2 = this.ngroups;
            arrthreadGroup = this.groups != null ? Arrays.copyOf(this.groups, n2) : null;
        }
        n = 0;
        while (n < n2) {
            arrthreadGroup[n].interrupt();
            ++n;
        }
        return;
    }

    public final boolean isDaemon() {
        return this.daemon;
    }

    public boolean isDestroyed() {
        synchronized (this) {
            boolean bl = this.destroyed;
            return bl;
        }
    }

    public void list() {
        this.list(System.out, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void list(PrintStream printStream, int n) {
        int n2;
        int n3;
        ThreadGroup[] arrthreadGroup;
        synchronized (this) {
            for (n3 = 0; n3 < n; ++n3) {
                printStream.print(" ");
            }
            printStream.println(this);
            n2 = n + 4;
            for (n = 0; n < this.nthreads; ++n) {
                for (n3 = 0; n3 < n2; ++n3) {
                    printStream.print(" ");
                }
                printStream.println(this.threads[n]);
            }
            n3 = this.ngroups;
            arrthreadGroup = this.groups != null ? Arrays.copyOf(this.groups, n3) : null;
        }
        n = 0;
        while (n < n3) {
            arrthreadGroup[n].list(printStream, n2);
            ++n;
        }
        return;
    }

    public final boolean parentOf(ThreadGroup threadGroup) {
        while (threadGroup != null) {
            if (threadGroup == this) {
                return true;
            }
            threadGroup = threadGroup.parent;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public final void resume() {
        int n;
        ThreadGroup[] arrthreadGroup;
        int n2;
        synchronized (this) {
            this.checkAccess();
            for (n = 0; n < this.nthreads; ++n) {
                this.threads[n].resume();
            }
            n2 = this.ngroups;
            arrthreadGroup = this.groups != null ? Arrays.copyOf(this.groups, n2) : null;
        }
        n = 0;
        while (n < n2) {
            arrthreadGroup[n].resume();
            ++n;
        }
        return;
    }

    public final void setDaemon(boolean bl) {
        this.checkAccess();
        this.daemon = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setMaxPriority(int n) {
        ThreadGroup[] arrthreadGroup;
        int n2;
        int n3;
        synchronized (this) {
            this.checkAccess();
            n3 = n;
            if (n < 1) {
                n3 = 1;
            }
            n = n3;
            if (n3 > 10) {
                n = 10;
            }
            n3 = this.parent != null ? Math.min(n, this.parent.maxPriority) : n;
            this.maxPriority = n3;
            n2 = this.ngroups;
            arrthreadGroup = this.groups != null ? Arrays.copyOf(this.groups, n2) : null;
        }
        n3 = 0;
        while (n3 < n2) {
            arrthreadGroup[n3].setMaxPriority(n);
            ++n3;
        }
        return;
    }

    @Deprecated
    public final void stop() {
        if (this.stopOrSuspend(false)) {
            Thread.currentThread().stop();
        }
    }

    @Deprecated
    public final void suspend() {
        if (this.stopOrSuspend(true)) {
            Thread.currentThread().suspend();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void threadStartFailed(Thread thread) {
        synchronized (this) {
            this.remove(thread);
            ++this.nUnstartedThreads;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void threadTerminated(Thread thread) {
        synchronized (this) {
            this.remove(thread);
            if (this.nthreads == 0) {
                this.notifyAll();
            }
            if (this.daemon && this.nthreads == 0 && this.nUnstartedThreads == 0 && this.ngroups == 0) {
                this.destroy();
            }
            return;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[name=");
        stringBuilder.append(this.getName());
        stringBuilder.append(",maxpri=");
        stringBuilder.append(this.maxPriority);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Object object = this.parent;
        if (object != null) {
            ((ThreadGroup)object).uncaughtException(thread, throwable);
        } else {
            object = Thread.getDefaultUncaughtExceptionHandler();
            if (object != null) {
                object.uncaughtException(thread, throwable);
            } else if (!(throwable instanceof ThreadDeath)) {
                object = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception in thread \"");
                stringBuilder.append(thread.getName());
                stringBuilder.append("\" ");
                ((PrintStream)object).print(stringBuilder.toString());
                throwable.printStackTrace(System.err);
            }
        }
    }
}

