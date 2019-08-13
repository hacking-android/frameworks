/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.os.AsyncTask;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget._$$Lambda$TTC7hNz7BTsLwhNRb2L5kl_7mdU;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class LockPatternChecker {
    @Deprecated
    @UnsupportedAppUsage
    public static AsyncTask<?, ?, ?> checkPassword(LockPatternUtils lockPatternUtils, String object, int n, OnCheckCallback onCheckCallback) {
        object = object != null ? object.getBytes() : null;
        return LockPatternChecker.checkPassword(lockPatternUtils, object, n, onCheckCallback);
    }

    public static AsyncTask<?, ?, ?> checkPassword(LockPatternUtils object, final byte[] arrby, final int n, final OnCheckCallback onCheckCallback) {
        object = new AsyncTask<Void, Void, Boolean>(){
            private int mThrottleTimeout;

            protected Boolean doInBackground(Void ... object) {
                boolean bl;
                try {
                    LockPatternUtils lockPatternUtils = LockPatternUtils.this;
                    byte[] arrby2 = arrby;
                    int n2 = n;
                    OnCheckCallback onCheckCallback2 = onCheckCallback;
                    Objects.requireNonNull(onCheckCallback2);
                    object = new _$$Lambda$TTC7hNz7BTsLwhNRb2L5kl_7mdU(onCheckCallback2);
                    bl = lockPatternUtils.checkPassword(arrby2, n2, (LockPatternUtils.CheckCredentialProgressCallback)object);
                }
                catch (LockPatternUtils.RequestThrottledException requestThrottledException) {
                    this.mThrottleTimeout = requestThrottledException.getTimeoutMs();
                    return false;
                }
                return bl;
            }

            @Override
            protected void onCancelled() {
                onCheckCallback.onCancelled();
            }

            @Override
            protected void onPostExecute(Boolean bl) {
                onCheckCallback.onChecked(bl, this.mThrottleTimeout);
            }
        };
        ((AsyncTask)object).execute(new Void[0]);
        return object;
    }

    public static AsyncTask<?, ?, ?> checkPattern(LockPatternUtils object, List<LockPatternView.Cell> list, int n, OnCheckCallback onCheckCallback) {
        object = new AsyncTask<Void, Void, Boolean>((LockPatternUtils)object, n, onCheckCallback){
            private int mThrottleTimeout;
            private List<LockPatternView.Cell> patternCopy;
            final /* synthetic */ OnCheckCallback val$callback;
            final /* synthetic */ int val$userId;
            final /* synthetic */ LockPatternUtils val$utils;
            {
                this.val$utils = lockPatternUtils;
                this.val$userId = n;
                this.val$callback = onCheckCallback;
            }

            protected Boolean doInBackground(Void ... object) {
                boolean bl;
                try {
                    LockPatternUtils lockPatternUtils = this.val$utils;
                    List<LockPatternView.Cell> list = this.patternCopy;
                    int n = this.val$userId;
                    OnCheckCallback onCheckCallback = this.val$callback;
                    Objects.requireNonNull(onCheckCallback);
                    object = new _$$Lambda$TTC7hNz7BTsLwhNRb2L5kl_7mdU(onCheckCallback);
                    bl = lockPatternUtils.checkPattern(list, n, (LockPatternUtils.CheckCredentialProgressCallback)object);
                }
                catch (LockPatternUtils.RequestThrottledException requestThrottledException) {
                    this.mThrottleTimeout = requestThrottledException.getTimeoutMs();
                    return false;
                }
                return bl;
            }

            @Override
            protected void onCancelled() {
                this.val$callback.onCancelled();
            }

            @Override
            protected void onPostExecute(Boolean bl) {
                this.val$callback.onChecked(bl, this.mThrottleTimeout);
            }

            @Override
            protected void onPreExecute() {
                this.patternCopy = new ArrayList<LockPatternView.Cell>(List.this);
            }
        };
        ((AsyncTask)object).execute(new Void[0]);
        return object;
    }

    @Deprecated
    public static AsyncTask<?, ?, ?> verifyPassword(LockPatternUtils lockPatternUtils, String object, long l, int n, OnVerifyCallback onVerifyCallback) {
        object = object != null ? object.getBytes() : null;
        return LockPatternChecker.verifyPassword(lockPatternUtils, object, l, n, onVerifyCallback);
    }

    public static AsyncTask<?, ?, ?> verifyPassword(LockPatternUtils object, final byte[] arrby, final long l, final int n, final OnVerifyCallback onVerifyCallback) {
        object = new AsyncTask<Void, Void, byte[]>(){
            private int mThrottleTimeout;

            protected byte[] doInBackground(Void ... arrobject) {
                try {
                    arrobject = LockPatternUtils.this.verifyPassword(arrby, l, n);
                    return arrobject;
                }
                catch (LockPatternUtils.RequestThrottledException requestThrottledException) {
                    this.mThrottleTimeout = requestThrottledException.getTimeoutMs();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(byte[] arrby2) {
                onVerifyCallback.onVerified(arrby2, this.mThrottleTimeout);
            }
        };
        ((AsyncTask)object).execute(new Void[0]);
        return object;
    }

    public static AsyncTask<?, ?, ?> verifyPattern(LockPatternUtils object, List<LockPatternView.Cell> list, long l, int n, OnVerifyCallback onVerifyCallback) {
        object = new AsyncTask<Void, Void, byte[]>((LockPatternUtils)object, l, n, onVerifyCallback){
            private int mThrottleTimeout;
            private List<LockPatternView.Cell> patternCopy;
            final /* synthetic */ OnVerifyCallback val$callback;
            final /* synthetic */ long val$challenge;
            final /* synthetic */ int val$userId;
            final /* synthetic */ LockPatternUtils val$utils;
            {
                this.val$utils = lockPatternUtils;
                this.val$challenge = l;
                this.val$userId = n;
                this.val$callback = onVerifyCallback;
            }

            protected byte[] doInBackground(Void ... arrobject) {
                try {
                    arrobject = this.val$utils.verifyPattern(this.patternCopy, this.val$challenge, this.val$userId);
                    return arrobject;
                }
                catch (LockPatternUtils.RequestThrottledException requestThrottledException) {
                    this.mThrottleTimeout = requestThrottledException.getTimeoutMs();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(byte[] arrby) {
                this.val$callback.onVerified(arrby, this.mThrottleTimeout);
            }

            @Override
            protected void onPreExecute() {
                this.patternCopy = new ArrayList<LockPatternView.Cell>(List.this);
            }
        };
        ((AsyncTask)object).execute(new Void[0]);
        return object;
    }

    public static AsyncTask<?, ?, ?> verifyTiedProfileChallenge(LockPatternUtils object, final byte[] arrby, final boolean bl, final long l, final int n, final OnVerifyCallback onVerifyCallback) {
        object = new AsyncTask<Void, Void, byte[]>(){
            private int mThrottleTimeout;

            protected byte[] doInBackground(Void ... arrobject) {
                try {
                    arrobject = LockPatternUtils.this.verifyTiedProfileChallenge(arrby, bl, l, n);
                    return arrobject;
                }
                catch (LockPatternUtils.RequestThrottledException requestThrottledException) {
                    this.mThrottleTimeout = requestThrottledException.getTimeoutMs();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(byte[] arrby2) {
                onVerifyCallback.onVerified(arrby2, this.mThrottleTimeout);
            }
        };
        ((AsyncTask)object).execute(new Void[0]);
        return object;
    }

    public static interface OnCheckCallback {
        default public void onCancelled() {
        }

        public void onChecked(boolean var1, int var2);

        default public void onEarlyMatched() {
        }
    }

    public static interface OnVerifyCallback {
        public void onVerified(byte[] var1, int var2);
    }

}

