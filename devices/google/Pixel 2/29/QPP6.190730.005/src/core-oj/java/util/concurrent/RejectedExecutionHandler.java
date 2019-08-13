/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.ThreadPoolExecutor;

public interface RejectedExecutionHandler {
    public void rejectedExecution(Runnable var1, ThreadPoolExecutor var2);
}

