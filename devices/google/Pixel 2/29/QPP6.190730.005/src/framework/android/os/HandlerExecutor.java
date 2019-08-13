/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Handler;
import com.android.internal.util.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public class HandlerExecutor
implements Executor {
    private final Handler mHandler;

    public HandlerExecutor(Handler handler) {
        this.mHandler = Preconditions.checkNotNull(handler);
    }

    @Override
    public void execute(Runnable object) {
        if (this.mHandler.post((Runnable)object)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this.mHandler);
        ((StringBuilder)object).append(" is shutting down");
        throw new RejectedExecutionException(((StringBuilder)object).toString());
    }
}

