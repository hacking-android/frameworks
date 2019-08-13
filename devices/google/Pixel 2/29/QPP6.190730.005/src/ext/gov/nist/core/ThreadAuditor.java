/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ThreadAuditor {
    private long pingIntervalInMillisecs = 0L;
    private Map<Thread, ThreadHandle> threadHandles = new HashMap<Thread, ThreadHandle>();

    public ThreadHandle addCurrentThread() {
        synchronized (this) {
            ThreadHandle threadHandle = new ThreadHandle(this);
            if (this.isEnabled()) {
                this.threadHandles.put(Thread.currentThread(), threadHandle);
            }
            return threadHandle;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String auditThreads() {
        synchronized (this) {
            CharSequence charSequence = null;
            Iterator<ThreadHandle> iterator = this.threadHandles.values().iterator();
            while (iterator.hasNext()) {
                ThreadHandle threadHandle = iterator.next();
                String string = charSequence;
                if (!threadHandle.isThreadActive()) {
                    Thread thread = threadHandle.getThread();
                    string = charSequence;
                    if (charSequence == null) {
                        string = "Thread Auditor Report:\n";
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append("   Thread [");
                    ((StringBuilder)charSequence).append(thread.getName());
                    ((StringBuilder)charSequence).append("] has failed to respond to an audit request.\n");
                    string = ((StringBuilder)charSequence).toString();
                }
                threadHandle.setThreadActive(false);
                charSequence = string;
            }
            return charSequence;
        }
    }

    public long getPingIntervalInMillisecs() {
        return this.pingIntervalInMillisecs;
    }

    public boolean isEnabled() {
        boolean bl = this.pingIntervalInMillisecs > 0L;
        return bl;
    }

    public void ping(ThreadHandle threadHandle) {
        synchronized (this) {
            threadHandle.setThreadActive(true);
            return;
        }
    }

    public void removeThread(Thread thread) {
        synchronized (this) {
            this.threadHandles.remove(thread);
            return;
        }
    }

    public void reset() {
        synchronized (this) {
            this.threadHandles.clear();
            return;
        }
    }

    public void setPingIntervalInMillisecs(long l) {
        this.pingIntervalInMillisecs = l;
    }

    public String toString() {
        synchronized (this) {
            String string = "Thread Auditor - List of monitored threads:\n";
            for (ThreadHandle threadHandle : this.threadHandles.values()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append("   ");
                stringBuilder.append(threadHandle.toString());
                stringBuilder.append("\n");
                string = stringBuilder.toString();
            }
            return string;
        }
    }

    public class ThreadHandle {
        private boolean isThreadActive = false;
        private Thread thread = Thread.currentThread();
        private ThreadAuditor threadAuditor;

        public ThreadHandle(ThreadAuditor threadAuditor2) {
            this.threadAuditor = threadAuditor2;
        }

        public long getPingIntervalInMillisecs() {
            return this.threadAuditor.getPingIntervalInMillisecs();
        }

        public Thread getThread() {
            return this.thread;
        }

        public boolean isThreadActive() {
            return this.isThreadActive;
        }

        public void ping() {
            this.threadAuditor.ping(this);
        }

        protected void setThreadActive(boolean bl) {
            this.isThreadActive = bl;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Thread Name: ");
            stringBuffer.append(this.thread.getName());
            stringBuffer.append(", Alive: ");
            return stringBuffer.append(this.thread.isAlive()).toString();
        }
    }

}

