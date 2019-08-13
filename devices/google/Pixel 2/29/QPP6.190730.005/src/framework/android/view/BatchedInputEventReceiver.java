/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Looper;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEventReceiver;

public class BatchedInputEventReceiver
extends InputEventReceiver {
    private final BatchedInputRunnable mBatchedInputRunnable = new BatchedInputRunnable();
    private boolean mBatchedInputScheduled;
    Choreographer mChoreographer;

    @UnsupportedAppUsage
    public BatchedInputEventReceiver(InputChannel inputChannel, Looper looper, Choreographer choreographer) {
        super(inputChannel, looper);
        this.mChoreographer = choreographer;
    }

    private void scheduleBatchedInput() {
        if (!this.mBatchedInputScheduled) {
            this.mBatchedInputScheduled = true;
            this.mChoreographer.postCallback(0, this.mBatchedInputRunnable, null);
        }
    }

    private void unscheduleBatchedInput() {
        if (this.mBatchedInputScheduled) {
            this.mBatchedInputScheduled = false;
            this.mChoreographer.removeCallbacks(0, this.mBatchedInputRunnable, null);
        }
    }

    @Override
    public void dispose() {
        this.unscheduleBatchedInput();
        super.dispose();
    }

    void doConsumeBatchedInput(long l) {
        if (this.mBatchedInputScheduled) {
            this.mBatchedInputScheduled = false;
            if (this.consumeBatchedInputEvents(l) && l != -1L) {
                this.scheduleBatchedInput();
            }
        }
    }

    @Override
    public void onBatchedInputEventPending() {
        this.scheduleBatchedInput();
    }

    private final class BatchedInputRunnable
    implements Runnable {
        private BatchedInputRunnable() {
        }

        @Override
        public void run() {
            BatchedInputEventReceiver batchedInputEventReceiver = BatchedInputEventReceiver.this;
            batchedInputEventReceiver.doConsumeBatchedInput(batchedInputEventReceiver.mChoreographer.getFrameTimeNanos());
        }
    }

}

