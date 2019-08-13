/*
 * Decompiled with CFR 0.145.
 */
package android.metrics;

import android.annotation.SystemApi;
import android.metrics.LogMaker;
import android.util.EventLog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@SystemApi
public class MetricsReader {
    private int[] LOGTAGS = new int[]{524292};
    private int mCheckpointTag = -1;
    private Queue<LogMaker> mPendingQueue = new LinkedList<LogMaker>();
    private LogReader mReader = new LogReader();
    private Queue<LogMaker> mSeenQueue = new LinkedList<LogMaker>();

    public void checkpoint() {
        this.mCheckpointTag = (int)(System.currentTimeMillis() % Integer.MAX_VALUE);
        this.mReader.writeCheckpoint(this.mCheckpointTag);
        this.mPendingQueue.clear();
        this.mSeenQueue.clear();
    }

    public boolean hasNext() {
        return this.mPendingQueue.isEmpty() ^ true;
    }

    public LogMaker next() {
        LogMaker logMaker = this.mPendingQueue.poll();
        if (logMaker != null) {
            this.mSeenQueue.offer(logMaker);
        }
        return logMaker;
    }

    public void read(long l) {
        Object object = new ArrayList<Event>();
        try {
            this.mReader.readEvents(this.LOGTAGS, l, (Collection<Event>)object);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        this.mPendingQueue.clear();
        this.mSeenQueue.clear();
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            Event event = (Event)object.next();
            l = event.getTimeMillis();
            Object object2 = event.getData();
            Object object3 = object2 instanceof Object[] ? (Object[])object2 : new Object[]{object2};
            object3 = new LogMaker((Object[])object3).setTimestamp(l).setUid(event.getUid()).setProcessId(event.getProcessId());
            if (((LogMaker)object3).getCategory() == 920) {
                if (((LogMaker)object3).getSubtype() != this.mCheckpointTag) continue;
                this.mPendingQueue.clear();
                continue;
            }
            this.mPendingQueue.offer((LogMaker)object3);
        }
    }

    public void reset() {
        this.mSeenQueue.addAll(this.mPendingQueue);
        this.mPendingQueue.clear();
        this.mCheckpointTag = -1;
        Queue<LogMaker> queue = this.mPendingQueue;
        this.mPendingQueue = this.mSeenQueue;
        this.mSeenQueue = queue;
    }

    @VisibleForTesting
    public void setLogReader(LogReader logReader) {
        this.mReader = logReader;
    }

    @VisibleForTesting
    public static class Event {
        Object mData;
        int mPid;
        long mTimeMillis;
        int mUid;

        public Event(long l, int n, int n2, Object object) {
            this.mTimeMillis = l;
            this.mPid = n;
            this.mUid = n2;
            this.mData = object;
        }

        Event(EventLog.Event event) {
            this.mTimeMillis = TimeUnit.MILLISECONDS.convert(event.getTimeNanos(), TimeUnit.NANOSECONDS);
            this.mPid = event.getProcessId();
            this.mUid = event.getUid();
            this.mData = event.getData();
        }

        public Object getData() {
            return this.mData;
        }

        public int getProcessId() {
            return this.mPid;
        }

        public long getTimeMillis() {
            return this.mTimeMillis;
        }

        public int getUid() {
            return this.mUid;
        }

        public void setData(Object object) {
            this.mData = object;
        }
    }

    @VisibleForTesting
    public static class LogReader {
        public void readEvents(int[] object, long l, Collection<Event> collection) throws IOException {
            ArrayList<EventLog.Event> arrayList = new ArrayList<EventLog.Event>();
            EventLog.readEventsOnWrapping((int[])object, TimeUnit.NANOSECONDS.convert(l, TimeUnit.MILLISECONDS), arrayList);
            object = arrayList.iterator();
            while (object.hasNext()) {
                collection.add(new Event((EventLog.Event)object.next()));
            }
        }

        public void writeCheckpoint(int n) {
            new MetricsLogger().action(920, n);
        }
    }

}

