/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal;

import java.io.IOException;
import java.net.URL;

public interface URLFilter {
    public void checkURLPermitted(URL var1) throws IOException;
}

