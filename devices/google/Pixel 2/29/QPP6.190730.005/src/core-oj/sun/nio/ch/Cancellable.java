/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import sun.nio.ch.PendingFuture;

interface Cancellable {
    public void onCancel(PendingFuture<?, ?> var1);
}

