/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.ims.internal.IImsStreamMediaSession
 */
package com.android.ims.internal;

import com.android.ims.internal.IImsStreamMediaSession;

public class ImsStreamMediaSession {
    private static final String TAG = "ImsStreamMediaSession";
    private Listener mListener;

    ImsStreamMediaSession(IImsStreamMediaSession iImsStreamMediaSession) {
    }

    ImsStreamMediaSession(IImsStreamMediaSession iImsStreamMediaSession, Listener listener) {
        this(iImsStreamMediaSession);
        this.setListener(listener);
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public static class Listener {
    }

}

