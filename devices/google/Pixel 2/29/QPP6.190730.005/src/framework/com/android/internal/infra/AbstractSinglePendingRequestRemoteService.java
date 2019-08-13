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

public abstract class AbstractSinglePendingRequestRemoteService<S extends AbstractSinglePendingRequestRemoteService<S, I>, I extends IInterface>
extends AbstractRemoteService<S, I> {
    protected AbstractRemoteService.BasePendingRequest<S, I> mPendingRequest;

    public AbstractSinglePendingRequestRemoteService(Context context, String string2, ComponentName componentName, int n, AbstractRemoteService.VultureCallback<S> vultureCallback, Handler handler, int n2, boolean bl) {
        super(context, string2, componentName, n, vultureCallback, handler, n2, bl);
    }

    @Override
    public void dump(String object, PrintWriter printWriter) {
        super.dump((String)object, printWriter);
        object = printWriter.append((CharSequence)object).append("hasPendingRequest=");
        boolean bl = this.mPendingRequest != null;
        ((PrintWriter)object).append(String.valueOf(bl)).println();
    }

    @Override
    void handleBindFailure() {
        if (this.mPendingRequest != null) {
            if (this.mVerbose) {
                String string2 = this.mTag;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Sending failure to ");
                stringBuilder.append(this.mPendingRequest);
                Slog.v(string2, stringBuilder.toString());
            }
            this.mPendingRequest.onFailed();
            this.mPendingRequest = null;
        }
    }

    protected AbstractRemoteService.BasePendingRequest<S, I> handleCancelPendingRequest() {
        AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest = this.mPendingRequest;
        if (basePendingRequest != null) {
            basePendingRequest.cancel();
            this.mPendingRequest = null;
        }
        return basePendingRequest;
    }

    @Override
    protected void handleOnDestroy() {
        this.handleCancelPendingRequest();
    }

    @Override
    void handlePendingRequestWhileUnBound(AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest) {
        if (this.mPendingRequest != null) {
            if (this.mVerbose) {
                String string2 = this.mTag;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("handlePendingRequestWhileUnBound(): cancelling ");
                stringBuilder.append(this.mPendingRequest);
                stringBuilder.append(" to handle ");
                stringBuilder.append(basePendingRequest);
                Slog.v(string2, stringBuilder.toString());
            }
            this.mPendingRequest.cancel();
        }
        this.mPendingRequest = basePendingRequest;
    }

    @Override
    void handlePendingRequests() {
        if (this.mPendingRequest != null) {
            AbstractRemoteService.BasePendingRequest<S, I> basePendingRequest = this.mPendingRequest;
            this.mPendingRequest = null;
            this.handlePendingRequest(basePendingRequest);
        }
    }
}

