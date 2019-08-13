/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.ExponentiallyBucketedHistogram;
import java.util.Iterator;
import java.util.LinkedList;

public class QueuedWork {
    private static final boolean DEBUG = false;
    private static final long DELAY = 100L;
    private static final String LOG_TAG = QueuedWork.class.getSimpleName();
    private static final long MAX_WAIT_TIME_MILLIS = 512L;
    private static int mNumWaits;
    @GuardedBy(value={"sLock"})
    private static final ExponentiallyBucketedHistogram mWaitTimes;
    @GuardedBy(value={"sLock"})
    private static boolean sCanDelay;
    @UnsupportedAppUsage
    @GuardedBy(value={"sLock"})
    private static final LinkedList<Runnable> sFinishers;
    @GuardedBy(value={"sLock"})
    private static Handler sHandler;
    private static final Object sLock;
    private static Object sProcessingWork;
    @GuardedBy(value={"sLock"})
    private static final LinkedList<Runnable> sWork;

    static {
        sLock = new Object();
        sProcessingWork = new Object();
        sFinishers = new LinkedList();
        sHandler = null;
        sWork = new LinkedList();
        sCanDelay = true;
        mWaitTimes = new ExponentiallyBucketedHistogram(16);
        mNumWaits = 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void addFinisher(Runnable runnable) {
        Object object = sLock;
        synchronized (object) {
            sFinishers.add(runnable);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private static Handler getHandler() {
        Object object = sLock;
        synchronized (object) {
            Handler handler;
            if (sHandler != null) return sHandler;
            HandlerThread handlerThread = new HandlerThread("queued-work-looper", -2);
            handlerThread.start();
            sHandler = handler = new QueuedWorkHandler(handlerThread.getLooper());
            return sHandler;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean hasPendingWork() {
        Object object = sLock;
        synchronized (object) {
            if (sWork.isEmpty()) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static void processPendingWork() {
        Object object = sProcessingWork;
        // MONITORENTER : object
        Iterator iterator = sLock;
        // MONITORENTER : iterator
        LinkedList linkedList = (LinkedList)sWork.clone();
        sWork.clear();
        QueuedWork.getHandler().removeMessages(1);
        // MONITOREXIT : iterator
        if (linkedList.size() > 0) {
            iterator = linkedList.iterator();
            while (iterator.hasNext()) {
                ((Runnable)iterator.next()).run();
            }
        }
        // MONITOREXIT : object
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void queue(Runnable runnable, boolean bl) {
        Handler handler = QueuedWork.getHandler();
        Object object = sLock;
        synchronized (object) {
            sWork.add(runnable);
            if (bl && sCanDelay) {
                handler.sendEmptyMessageDelayed(1, 100L);
            } else {
                handler.sendEmptyMessage(1);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void removeFinisher(Runnable runnable) {
        Object object = sLock;
        synchronized (object) {
            sFinishers.remove(runnable);
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static void waitToFinish() {
        long l = System.currentTimeMillis();
        Object object = QueuedWork.getHandler();
        Object object2 = sLock;
        // MONITORENTER : object2
        if (((Handler)object).hasMessages(1)) {
            ((Handler)object).removeMessages(1);
        }
        sCanDelay = false;
        // MONITOREXIT : object2
        object = StrictMode.allowThreadDiskWrites();
        try {
            QueuedWork.processPendingWork();
        }
        finally {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)object);
        }
        do {
            object2 = sLock;
            // MONITORENTER : object2
            object = sFinishers.poll();
            break;
        } while (true);
        catch (Throwable throwable) {
            sCanDelay = true;
            throw throwable;
        }
        {
            // MONITOREXIT : object2
            if (object == null) {
                sCanDelay = true;
                object2 = sLock;
                // MONITORENTER : object2
                l = System.currentTimeMillis() - l;
                if (l > 0L || false) {
                    mWaitTimes.add(Long.valueOf(l).intValue());
                    if (++mNumWaits % 1024 == 0 || l > 512L) {
                        mWaitTimes.log(LOG_TAG, "waited: ");
                    }
                }
                // MONITOREXIT : object2
                return;
            }
            object.run();
            continue;
        }
    }

    private static class QueuedWorkHandler
    extends Handler {
        static final int MSG_RUN = 1;

        QueuedWorkHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            if (message.what == 1) {
                QueuedWork.processPendingWork();
            }
        }
    }

}

