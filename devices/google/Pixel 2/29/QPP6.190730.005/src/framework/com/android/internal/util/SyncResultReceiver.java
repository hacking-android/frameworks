/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcelable;
import com.android.internal.os.IResultReceiver;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class SyncResultReceiver
extends IResultReceiver.Stub {
    private static final String EXTRA = "EXTRA";
    private Bundle mBundle;
    private final CountDownLatch mLatch = new CountDownLatch(1);
    private int mResult;
    private final int mTimeoutMs;

    public SyncResultReceiver(int n) {
        this.mTimeoutMs = n;
    }

    public static Bundle bundleFor(int n) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA, n);
        return bundle;
    }

    public static Bundle bundleFor(Parcelable parcelable) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA, parcelable);
        return bundle;
    }

    public static Bundle bundleFor(String string2) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA, string2);
        return bundle;
    }

    public static Bundle bundleFor(ArrayList<? extends Parcelable> arrayList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA, arrayList);
        return bundle;
    }

    public static Bundle bundleFor(String[] arrstring) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(EXTRA, arrstring);
        return bundle;
    }

    private void waitResult() throws TimeoutException {
        try {
            if (this.mLatch.await(this.mTimeoutMs, TimeUnit.MILLISECONDS)) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not called in ");
            stringBuilder.append(this.mTimeoutMs);
            stringBuilder.append("ms");
            TimeoutException timeoutException = new TimeoutException(stringBuilder.toString());
            throw timeoutException;
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw new TimeoutException("Interrupted");
        }
    }

    public int getIntResult() throws TimeoutException {
        this.waitResult();
        return this.mResult;
    }

    public int getOptionalExtraIntResult(int n) throws TimeoutException {
        this.waitResult();
        Bundle bundle = this.mBundle;
        if (bundle != null && bundle.containsKey(EXTRA)) {
            return this.mBundle.getInt(EXTRA);
        }
        return n;
    }

    public <P extends Parcelable> ArrayList<P> getParcelableListResult() throws TimeoutException {
        this.waitResult();
        Cloneable cloneable = this.mBundle;
        cloneable = cloneable == null ? null : cloneable.getParcelableArrayList(EXTRA);
        return cloneable;
    }

    public <P extends Parcelable> P getParcelableResult() throws TimeoutException {
        this.waitResult();
        Bundle bundle = this.mBundle;
        bundle = bundle == null ? null : bundle.getParcelable(EXTRA);
        return (P)bundle;
    }

    public String[] getStringArrayResult() throws TimeoutException {
        this.waitResult();
        Object object = this.mBundle;
        object = object == null ? null : object.getStringArray(EXTRA);
        return object;
    }

    public String getStringResult() throws TimeoutException {
        this.waitResult();
        Object object = this.mBundle;
        object = object == null ? null : ((BaseBundle)object).getString(EXTRA);
        return object;
    }

    @Override
    public void send(int n, Bundle bundle) {
        this.mResult = n;
        this.mBundle = bundle;
        this.mLatch.countDown();
    }

    public static final class TimeoutException
    extends RuntimeException {
        private TimeoutException(String string2) {
            super(string2);
        }
    }

}

