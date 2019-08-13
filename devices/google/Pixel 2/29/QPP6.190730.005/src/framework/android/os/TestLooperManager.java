/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.ArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class TestLooperManager {
    private static final ArraySet<Looper> sHeldLoopers = new ArraySet();
    private final LinkedBlockingQueue<MessageExecution> mExecuteQueue = new LinkedBlockingQueue();
    private final Looper mLooper;
    private boolean mLooperBlocked;
    private final MessageQueue mQueue;
    private boolean mReleased;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public TestLooperManager(Looper object) {
        ArraySet<Looper> arraySet = sHeldLoopers;
        synchronized (arraySet) {
            if (!sHeldLoopers.contains(object)) {
                sHeldLoopers.add((Looper)object);
                // MONITOREXIT [2, 3] lbl8 : MonitorExitStatement: MONITOREXIT : var2_2
                this.mLooper = object;
                this.mQueue = this.mLooper.getQueue();
                new Handler((Looper)object).post(new LooperHolder());
                return;
            }
            object = new RuntimeException("TestLooperManager already held for this looper");
            throw object;
        }
    }

    private void checkReleased() {
        if (!this.mReleased) {
            return;
        }
        throw new RuntimeException("release() has already be called");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void execute(Message object) {
        this.checkReleased();
        if (Looper.myLooper() == this.mLooper) {
            ((Message)object).target.dispatchMessage((Message)object);
            return;
        }
        MessageExecution messageExecution = new MessageExecution();
        messageExecution.m = (Message)object;
        synchronized (messageExecution) {
            this.mExecuteQueue.add(messageExecution);
            try {
                messageExecution.wait();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            if (messageExecution.response == null) {
                return;
            }
            object = new RuntimeException(messageExecution.response);
            throw object;
        }
    }

    public MessageQueue getMessageQueue() {
        this.checkReleased();
        return this.mQueue;
    }

    @Deprecated
    public MessageQueue getQueue() {
        return this.getMessageQueue();
    }

    public boolean hasMessages(Handler handler, Object object, int n) {
        this.checkReleased();
        return this.mQueue.hasMessages(handler, n, object);
    }

    public boolean hasMessages(Handler handler, Object object, Runnable runnable) {
        this.checkReleased();
        return this.mQueue.hasMessages(handler, runnable, object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Message next() {
        Throwable throwable22;
        do {
            if (this.mLooperBlocked) {
                this.checkReleased();
                return this.mQueue.next();
            }
            synchronized (this) {
                try {
                    try {
                        this.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    continue;
                }
                catch (Throwable throwable22) {}
                break;
            }
        } while (true);
        {
            throw throwable22;
        }
    }

    public void recycle(Message message) {
        this.checkReleased();
        message.recycleUnchecked();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void release() {
        ArraySet<Looper> arraySet = sHeldLoopers;
        synchronized (arraySet) {
            sHeldLoopers.remove(this.mLooper);
        }
        this.checkReleased();
        this.mReleased = true;
        this.mExecuteQueue.add(new MessageExecution());
    }

    private class LooperHolder
    implements Runnable {
        private LooperHolder() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void processMessage(MessageExecution messageExecution) {
            synchronized (messageExecution) {
                try {
                    MessageExecution.access$200((MessageExecution)messageExecution).target.dispatchMessage(messageExecution.m);
                    messageExecution.response = null;
                }
                catch (Throwable throwable) {
                    messageExecution.response = throwable;
                }
                messageExecution.notifyAll();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            TestLooperManager testLooperManager = TestLooperManager.this;
            synchronized (testLooperManager) {
                TestLooperManager.this.mLooperBlocked = true;
                TestLooperManager.this.notify();
            }
            do {
                if (TestLooperManager.this.mReleased) {
                    testLooperManager = TestLooperManager.this;
                    synchronized (testLooperManager) {
                        TestLooperManager.this.mLooperBlocked = false;
                        return;
                    }
                }
                try {
                    MessageExecution messageExecution = (MessageExecution)TestLooperManager.this.mExecuteQueue.take();
                    if (messageExecution.m == null) continue;
                    this.processMessage(messageExecution);
                }
                catch (InterruptedException interruptedException) {
                }
            } while (true);
        }
    }

    private static class MessageExecution {
        private Message m;
        private Throwable response;

        private MessageExecution() {
        }
    }

}

