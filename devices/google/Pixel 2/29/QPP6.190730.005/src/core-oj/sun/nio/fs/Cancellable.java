/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.util.concurrent.ExecutionException;
import sun.misc.Unsafe;

abstract class Cancellable
implements Runnable {
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private boolean completed;
    private Throwable exception;
    private final Object lock = new Object();
    private final long pollingAddress = unsafe.allocateMemory(4L);

    protected Cancellable() {
        unsafe.putIntVolatile(null, this.pollingAddress, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Throwable exception() {
        Object object = this.lock;
        synchronized (object) {
            return this.exception;
        }
    }

    static void runInterruptibly(Cancellable object) throws ExecutionException {
        Thread thread = new Thread((Runnable)object);
        thread.start();
        boolean bl = false;
        while (thread.isAlive()) {
            try {
                thread.join();
            }
            catch (InterruptedException interruptedException) {
                bl = true;
                ((Cancellable)object).cancel();
            }
        }
        if (bl) {
            Thread.currentThread().interrupt();
        }
        if ((object = ((Cancellable)object).exception()) == null) {
            return;
        }
        throw new ExecutionException((Throwable)object);
    }

    protected long addressToPollForCancel() {
        return this.pollingAddress;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void cancel() {
        Object object = this.lock;
        synchronized (object) {
            if (!this.completed) {
                unsafe.putIntVolatile(null, this.pollingAddress, this.cancelValue());
            }
            return;
        }
    }

    protected int cancelValue() {
        return Integer.MAX_VALUE;
    }

    abstract void implRun() throws Throwable;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void run() {
        Object object;
        try {
            this.implRun();
            object = this.lock;
        }
        catch (Throwable throwable) {
            try {
                Object object2 = this.lock;
                synchronized (object2) {
                    this.exception = throwable;
                }
            }
            catch (Throwable throwable2) {
                Object object3 = this.lock;
                synchronized (object3) {
                    this.completed = true;
                    unsafe.freeMemory(this.pollingAddress);
                    throw throwable2;
                }
            }
            Object object4 = this.lock;
            synchronized (object4) {
                this.completed = true;
                unsafe.freeMemory(this.pollingAddress);
                return;
            }
        }
        synchronized (object) {
            this.completed = true;
            unsafe.freeMemory(this.pollingAddress);
            return;
        }
    }
}

