/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.PrintStream;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class Cleaner
extends PhantomReference<Object> {
    private static final ReferenceQueue<Object> dummyQueue = new ReferenceQueue();
    private static Cleaner first = null;
    private Cleaner next = null;
    private Cleaner prev = null;
    private final Runnable thunk;

    private Cleaner(Object object, Runnable runnable) {
        super(object, dummyQueue);
        this.thunk = runnable;
    }

    private static Cleaner add(Cleaner cleaner) {
        synchronized (Cleaner.class) {
            if (first != null) {
                cleaner.next = first;
                Cleaner.first.prev = cleaner;
            }
            first = cleaner;
            return cleaner;
        }
    }

    public static Cleaner create(Object object, Runnable runnable) {
        if (runnable == null) {
            return null;
        }
        return Cleaner.add(new Cleaner(object, runnable));
    }

    private static boolean remove(Cleaner cleaner) {
        synchronized (Cleaner.class) {
            Cleaner cleaner2;
            block11 : {
                cleaner2 = cleaner.next;
                if (cleaner2 != cleaner) break block11;
                return false;
            }
            if (first == cleaner) {
                cleaner2 = cleaner.next;
                first = cleaner2 != null ? cleaner2 : cleaner.prev;
            }
            if ((cleaner2 = cleaner.next) != null) {
                cleaner2.prev = cleaner.prev;
            }
            if ((cleaner2 = cleaner.prev) != null) {
                cleaner2.next = cleaner.next;
            }
            cleaner.next = cleaner;
            cleaner.prev = cleaner;
            return true;
        }
    }

    public void clean() {
        if (!Cleaner.remove(this)) {
            return;
        }
        try {
            this.thunk.run();
        }
        catch (Throwable throwable) {
            AccessController.doPrivileged(new PrivilegedAction<Void>(){

                @Override
                public Void run() {
                    if (System.err != null) {
                        new Error("Cleaner terminated abnormally", throwable).printStackTrace();
                    }
                    System.exit(1);
                    return null;
                }
            });
        }
    }

}

