/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.infra;

import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.IInterface;
import android.util.Slog;
import com.android.internal.infra.AbstractRemoteService;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class AbstractMultiplePendingRequestsRemoteService<S extends AbstractMultiplePendingRequestsRemoteService<S, I>, I extends IInterface>
extends AbstractRemoteService<S, I> {
    private final int mInitialCapacity;
    protected ArrayList<AbstractRemoteService.BasePendingRequest<S, I>> mPendingRequests;

    public AbstractMultiplePendingRequestsRemoteService(Context context, String string2, ComponentName componentName, int n, AbstractRemoteService.VultureCallback<S> vultureCallback, Handler handler, int n2, boolean bl, int n3) {
        super(context, string2, componentName, n, vultureCallback, handler, n2, bl);
        this.mInitialCapacity = n3;
    }

    @Override
    public void dump(String string2, PrintWriter printWriter) {
        super.dump(string2, printWriter);
        printWriter.append(string2).append("initialCapacity=").append(String.valueOf(this.mInitialCapacity)).println();
        ArrayList<AbstractRemoteService.BasePendingRequest<S, I>> arrayList = this.mPendingRequests;
        int n = arrayList == null ? 0 : arrayList.size();
        printWriter.append(string2).append("pendingRequests=").append(String.valueOf(n)).println();
    }

    @Override
    final void handleBindFailure() {
        ArrayList<AbstractRemoteService.BasePendingRequest<S, I>> arrayList = this.mPendingRequests;
        if (arrayList != null) {
            int n = arrayList.size();
            if (this.mVerbose) {
                String string2 = this.mTag;
                arrayList = new StringBuilder();
                ((StringBuilder)((Object)arrayList)).append("Sending failure to ");
                ((StringBuilder)((Object)arrayList)).append(n);
                ((StringBuilder)((Object)arrayList)).append(" pending requests");
                Slog.v(string2, ((StringBuilder)((Object)arrayList)).toString());
            }
            for (int i = 0; i < n; ++i) {
                arrayList = this.mPendingRequests.get(i);
                ((AbstractRemoteService.BasePendingRequest)((Object)arrayList)).onFailed();
                ((AbstractRemoteService.BasePendingRequest)((Object)arrayList)).finish();
            }
            this.mPendingRequests = null;
        }
    }

    @Override
    protected void handleOnDestroy() {
        ArrayList<AbstractRemoteService.BasePendingRequest<S, I>> arrayList = this.mPendingRequests;
        if (arrayList != null) {
            int n = arrayList.size();
            if (this.mVerbose) {
                arrayList = this.mTag;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Canceling ");
                stringBuilder.append(n);
                stringBuilder.append(" pending requests");
                Slog.v((String)((Object)arrayList), stringBuilder.toString());
            }
            for (int i = 0; i < n; ++i) {
                this.mPendingRequests.get(i).cancel();
            }
            this.mPendingRequests = null;
        }
    }

    @Override
    void handlePendingRequestWhileUnBound(AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest) {
        if (this.mPendingRequests == null) {
            this.mPendingRequests = new ArrayList(this.mInitialCapacity);
        }
        this.mPendingRequests.add(basePendingRequest);
        if (this.mVerbose) {
            String string2 = this.mTag;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("queued ");
            stringBuilder.append(this.mPendingRequests.size());
            stringBuilder.append(" requests; last=");
            stringBuilder.append(basePendingRequest);
            Slog.v(string2, stringBuilder.toString());
        }
    }

    @Override
    void handlePendingRequests() {
        Serializable serializable = this.mPendingRequests;
        if (serializable != null) {
            int n = ((ArrayList)serializable).size();
            if (this.mVerbose) {
                String string2 = this.mTag;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Sending ");
                ((StringBuilder)serializable).append(n);
                ((StringBuilder)serializable).append(" pending requests");
                Slog.v(string2, ((StringBuilder)serializable).toString());
            }
            for (int i = 0; i < n; ++i) {
                this.mPendingRequests.get(i).run();
            }
            this.mPendingRequests = null;
        }
    }
}

