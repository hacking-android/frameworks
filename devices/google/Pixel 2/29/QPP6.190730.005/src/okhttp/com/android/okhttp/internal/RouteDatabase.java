/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal;

import com.android.okhttp.Route;
import java.util.LinkedHashSet;
import java.util.Set;

public final class RouteDatabase {
    private final Set<Route> failedRoutes = new LinkedHashSet<Route>();

    public void connected(Route route) {
        synchronized (this) {
            this.failedRoutes.remove(route);
            return;
        }
    }

    public void failed(Route route) {
        synchronized (this) {
            this.failedRoutes.add(route);
            return;
        }
    }

    public int failedRoutesCount() {
        synchronized (this) {
            int n = this.failedRoutes.size();
            return n;
        }
    }

    public boolean shouldPostpone(Route route) {
        synchronized (this) {
            boolean bl = this.failedRoutes.contains(route);
            return bl;
        }
    }
}

