/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.Request;
import com.android.okhttp.Response;
import java.io.IOException;

public interface Callback {
    public void onFailure(Request var1, IOException var2);

    public void onResponse(Response var1) throws IOException;
}

