/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal;

public abstract class NamedRunnable
implements Runnable {
    protected final String name;

    public NamedRunnable(String string, Object ... arrobject) {
        this.name = String.format(string, arrobject);
    }

    protected abstract void execute();

    @Override
    public final void run() {
        String string = Thread.currentThread().getName();
        Thread.currentThread().setName(this.name);
        try {
            this.execute();
            return;
        }
        finally {
            Thread.currentThread().setName(string);
        }
    }
}

