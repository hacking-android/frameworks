/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.Call;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.http.HttpEngine;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class Dispatcher {
    private final Deque<Call> executedCalls = new ArrayDeque<Call>();
    private ExecutorService executorService;
    private int maxRequests = 64;
    private int maxRequestsPerHost = 5;
    private final Deque<Call.AsyncCall> readyCalls = new ArrayDeque<Call.AsyncCall>();
    private final Deque<Call.AsyncCall> runningCalls = new ArrayDeque<Call.AsyncCall>();

    public Dispatcher() {
    }

    public Dispatcher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    private void promoteCalls() {
        if (this.runningCalls.size() >= this.maxRequests) {
            return;
        }
        if (this.readyCalls.isEmpty()) {
            return;
        }
        Iterator<Call.AsyncCall> iterator = this.readyCalls.iterator();
        while (iterator.hasNext()) {
            Call.AsyncCall asyncCall = iterator.next();
            if (this.runningCallsForHost(asyncCall) < this.maxRequestsPerHost) {
                iterator.remove();
                this.runningCalls.add(asyncCall);
                this.getExecutorService().execute(asyncCall);
            }
            if (this.runningCalls.size() < this.maxRequests) continue;
            return;
        }
    }

    private int runningCallsForHost(Call.AsyncCall asyncCall) {
        int n = 0;
        Iterator<Call.AsyncCall> iterator = this.runningCalls.iterator();
        while (iterator.hasNext()) {
            int n2 = n;
            if (iterator.next().host().equals(asyncCall.host())) {
                n2 = n + 1;
            }
            n = n2;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancel(Object object) {
        synchronized (this) {
            for (Call.AsyncCall asyncCall : this.readyCalls) {
                if (!Util.equal(object, asyncCall.tag())) continue;
                asyncCall.cancel();
            }
            for (Call.AsyncCall asyncCall : this.runningCalls) {
                if (!Util.equal(object, asyncCall.tag())) continue;
                asyncCall.get().canceled = true;
                HttpEngine httpEngine = asyncCall.get().engine;
                if (httpEngine == null) continue;
                httpEngine.cancel();
            }
            Iterator<Call> iterator = this.executedCalls.iterator();
            while (iterator.hasNext()) {
                Call call = iterator.next();
                if (!Util.equal(object, call.tag())) continue;
                call.cancel();
            }
            return;
        }
    }

    void enqueue(Call.AsyncCall asyncCall) {
        synchronized (this) {
            if (this.runningCalls.size() < this.maxRequests && this.runningCallsForHost(asyncCall) < this.maxRequestsPerHost) {
                this.runningCalls.add(asyncCall);
                this.getExecutorService().execute(asyncCall);
            } else {
                this.readyCalls.add(asyncCall);
            }
            return;
        }
    }

    void executed(Call call) {
        synchronized (this) {
            this.executedCalls.add(call);
            return;
        }
    }

    void finished(Call.AsyncCall object) {
        synchronized (this) {
            if (this.runningCalls.remove(object)) {
                this.promoteCalls();
                return;
            }
            object = new AssertionError((Object)"AsyncCall wasn't running!");
            throw object;
        }
    }

    void finished(Call object) {
        synchronized (this) {
            block4 : {
                boolean bl = this.executedCalls.remove(object);
                if (!bl) break block4;
                return;
            }
            object = new AssertionError((Object)"Call wasn't in-flight!");
            throw object;
        }
    }

    public ExecutorService getExecutorService() {
        synchronized (this) {
            Object object;
            if (this.executorService == null) {
                object = TimeUnit.SECONDS;
                SynchronousQueue<Runnable> synchronousQueue = new SynchronousQueue<Runnable>();
                ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, (TimeUnit)((Object)object), synchronousQueue, Util.threadFactory("OkHttp Dispatcher", false));
                this.executorService = threadPoolExecutor;
            }
            object = this.executorService;
            return object;
        }
    }

    public int getMaxRequests() {
        synchronized (this) {
            int n = this.maxRequests;
            return n;
        }
    }

    public int getMaxRequestsPerHost() {
        synchronized (this) {
            int n = this.maxRequestsPerHost;
            return n;
        }
    }

    public int getQueuedCallCount() {
        synchronized (this) {
            int n = this.readyCalls.size();
            return n;
        }
    }

    public int getRunningCallCount() {
        synchronized (this) {
            int n = this.runningCalls.size();
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMaxRequests(int n) {
        synchronized (this) {
            Throwable throwable2;
            if (n >= 1) {
                try {
                    this.maxRequests = n;
                    this.promoteCalls();
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("max < 1: ");
                stringBuilder.append(n);
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
                throw illegalArgumentException;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMaxRequestsPerHost(int n) {
        synchronized (this) {
            Throwable throwable2;
            if (n >= 1) {
                try {
                    this.maxRequestsPerHost = n;
                    this.promoteCalls();
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("max < 1: ");
                stringBuilder.append(n);
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
                throw illegalArgumentException;
            }
            throw throwable2;
        }
    }
}

