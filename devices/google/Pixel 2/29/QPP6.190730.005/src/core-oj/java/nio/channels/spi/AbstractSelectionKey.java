/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels.spi;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelector;

public abstract class AbstractSelectionKey
extends SelectionKey {
    private volatile boolean valid = true;

    protected AbstractSelectionKey() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void cancel() {
        synchronized (this) {
            if (this.valid) {
                this.valid = false;
                ((AbstractSelector)this.selector()).cancel(this);
            }
            return;
        }
    }

    void invalidate() {
        this.valid = false;
    }

    @Override
    public final boolean isValid() {
        return this.valid;
    }
}

