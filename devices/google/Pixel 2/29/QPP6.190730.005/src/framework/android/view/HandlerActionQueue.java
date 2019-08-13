/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Handler;
import com.android.internal.util.GrowingArrayUtils;

public class HandlerActionQueue {
    private HandlerAction[] mActions;
    private int mCount;

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void executeActions(Handler handler) {
        synchronized (this) {
            HandlerAction[] arrhandlerAction = this.mActions;
            int n = 0;
            int n2 = this.mCount;
            do {
                if (n >= n2) {
                    this.mActions = null;
                    this.mCount = 0;
                    return;
                }
                HandlerAction handlerAction = arrhandlerAction[n];
                handler.postDelayed(handlerAction.action, handlerAction.delay);
                ++n;
            } while (true);
        }
    }

    public long getDelay(int n) {
        if (n < this.mCount) {
            return this.mActions[n].delay;
        }
        throw new IndexOutOfBoundsException();
    }

    public Runnable getRunnable(int n) {
        if (n < this.mCount) {
            return this.mActions[n].action;
        }
        throw new IndexOutOfBoundsException();
    }

    public void post(Runnable runnable) {
        this.postDelayed(runnable, 0L);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void postDelayed(Runnable object, long l) {
        object = new HandlerAction((Runnable)object, l);
        synchronized (this) {
            if (this.mActions == null) {
                this.mActions = new HandlerAction[4];
            }
            this.mActions = (HandlerAction[])GrowingArrayUtils.append(this.mActions, this.mCount, object);
            ++this.mCount;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeCallbacks(Runnable runnable) {
        synchronized (this) {
            int n = this.mCount;
            int n2 = 0;
            HandlerAction[] arrhandlerAction = this.mActions;
            for (int i = 0; i < n; ++i) {
                if (arrhandlerAction[i].matches(runnable)) continue;
                if (n2 != i) {
                    arrhandlerAction[n2] = arrhandlerAction[i];
                }
                ++n2;
            }
            this.mCount = n2;
            while (n2 < n) {
                arrhandlerAction[n2] = null;
                ++n2;
            }
            return;
        }
    }

    public int size() {
        return this.mCount;
    }

    private static class HandlerAction {
        final Runnable action;
        final long delay;

        public HandlerAction(Runnable runnable, long l) {
            this.action = runnable;
            this.delay = l;
        }

        public boolean matches(Runnable runnable) {
            Runnable runnable2;
            boolean bl = runnable == null && this.action == null || (runnable2 = this.action) != null && runnable2.equals(runnable);
            return bl;
        }
    }

}

