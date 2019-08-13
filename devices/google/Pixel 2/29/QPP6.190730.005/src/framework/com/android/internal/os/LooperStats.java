/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.CachedDeviceState;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class LooperStats
implements Looper.Observer {
    public static final String DEBUG_ENTRY_PREFIX = "__DEBUG_";
    private static final boolean DISABLED_SCREEN_STATE_TRACKING_VALUE = false;
    private static final int SESSION_POOL_SIZE = 50;
    private boolean mAddDebugEntries = false;
    private CachedDeviceState.TimeInStateStopwatch mBatteryStopwatch;
    private CachedDeviceState.Readonly mDeviceState;
    @GuardedBy(value={"mLock"})
    private final SparseArray<Entry> mEntries = new SparseArray(512);
    private final int mEntriesSizeCap;
    private final Entry mHashCollisionEntry = new Entry("HASH_COLLISION");
    private final Object mLock = new Object();
    private final Entry mOverflowEntry = new Entry("OVERFLOW");
    private int mSamplingInterval;
    private final ConcurrentLinkedQueue<DispatchSession> mSessionPool = new ConcurrentLinkedQueue();
    private long mStartCurrentTime = System.currentTimeMillis();
    private long mStartElapsedTime = SystemClock.elapsedRealtime();
    private boolean mTrackScreenInteractive = false;

    public LooperStats(int n, int n2) {
        this.mSamplingInterval = n;
        this.mEntriesSizeCap = n2;
    }

    private ExportedEntry createDebugEntry(String object, long l) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DEBUG_ENTRY_PREFIX);
        stringBuilder.append((String)object);
        object = new Entry(stringBuilder.toString());
        ((Entry)object).messageCount = 1L;
        ((Entry)object).recordedMessageCount = 1L;
        ((Entry)object).totalLatencyMicro = l;
        return new ExportedEntry((Entry)object);
    }

    private boolean deviceStateAllowsCollection() {
        CachedDeviceState.Readonly readonly = this.mDeviceState;
        boolean bl = readonly != null && !readonly.isCharging();
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Entry findEntry(Message object, boolean bl) {
        Entry entry;
        boolean bl2 = this.mTrackScreenInteractive ? this.mDeviceState.isScreenInteractive() : false;
        int n = Entry.idFor((Message)object, bl2);
        Object object2 = this.mLock;
        synchronized (object2) {
            Entry entry2;
            entry = entry2 = this.mEntries.get(n);
            if (entry2 == null) {
                if (!bl) {
                    return null;
                }
                if (this.mEntries.size() >= this.mEntriesSizeCap) {
                    return this.mOverflowEntry;
                }
                entry = new Entry((Message)object, bl2);
                this.mEntries.put(n, entry);
            }
        }
        if (entry.workSourceUid != ((Message)object).workSourceUid) return this.mHashCollisionEntry;
        if (entry.handler.getClass() != ((Message)object).getTarget().getClass()) return this.mHashCollisionEntry;
        if (entry.handler.getLooper().getThread() != ((Message)object).getTarget().getLooper().getThread()) return this.mHashCollisionEntry;
        if (entry.isInteractive == bl2) return entry;
        return this.mHashCollisionEntry;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void maybeAddSpecialEntry(List<ExportedEntry> list, Entry entry) {
        synchronized (entry) {
            if (entry.messageCount > 0L || entry.exceptionCount > 0L) {
                ExportedEntry exportedEntry = new ExportedEntry(entry);
                list.add(exportedEntry);
            }
            return;
        }
    }

    private void recycleSession(DispatchSession dispatchSession) {
        if (dispatchSession != DispatchSession.NOT_SAMPLED && this.mSessionPool.size() < 50) {
            this.mSessionPool.add(dispatchSession);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void dispatchingThrewException(Object object, Message message, Exception object2) {
        if (!this.deviceStateAllowsCollection()) {
            return;
        }
        object2 = (DispatchSession)object;
        boolean bl = object2 != DispatchSession.NOT_SAMPLED;
        object = this.findEntry(message, bl);
        if (object != null) {
            synchronized (object) {
                ++((Entry)object).exceptionCount;
            }
        }
        this.recycleSession((DispatchSession)object2);
    }

    public long getBatteryTimeMillis() {
        CachedDeviceState.TimeInStateStopwatch timeInStateStopwatch = this.mBatteryStopwatch;
        long l = timeInStateStopwatch != null ? timeInStateStopwatch.getMillis() : 0L;
        return l;
    }

    protected long getElapsedRealtimeMicro() {
        return SystemClock.elapsedRealtimeNanos() / 1000L;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<ExportedEntry> getEntries() {
        ArrayList<ExportedEntry> arrayList;
        Object object = this.mLock;
        synchronized (object) {
            int n = this.mEntries.size();
            arrayList = new ArrayList<ExportedEntry>(n);
            for (int i = 0; i < n; ++i) {
                Entry entry = this.mEntries.valueAt(i);
                synchronized (entry) {
                    ExportedEntry exportedEntry = new ExportedEntry(entry);
                    arrayList.add(exportedEntry);
                    continue;
                }
            }
        }
        this.maybeAddSpecialEntry(arrayList, this.mOverflowEntry);
        this.maybeAddSpecialEntry(arrayList, this.mHashCollisionEntry);
        if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
            arrayList.add(this.createDebugEntry("start_time_millis", this.mStartElapsedTime));
            arrayList.add(this.createDebugEntry("end_time_millis", SystemClock.elapsedRealtime()));
            arrayList.add(this.createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
            arrayList.add(this.createDebugEntry("sampling_interval", this.mSamplingInterval));
        }
        return arrayList;
    }

    public long getStartElapsedTimeMillis() {
        return this.mStartElapsedTime;
    }

    public long getStartTimeMillis() {
        return this.mStartCurrentTime;
    }

    protected long getSystemUptimeMillis() {
        return SystemClock.uptimeMillis();
    }

    protected long getThreadTimeMicro() {
        return SystemClock.currentThreadTimeMicro();
    }

    @Override
    public Object messageDispatchStarting() {
        if (this.deviceStateAllowsCollection() && this.shouldCollectDetailedData()) {
            DispatchSession dispatchSession = this.mSessionPool.poll();
            if (dispatchSession == null) {
                dispatchSession = new DispatchSession();
            }
            dispatchSession.startTimeMicro = this.getElapsedRealtimeMicro();
            dispatchSession.cpuStartMicro = this.getThreadTimeMicro();
            dispatchSession.systemUptimeMillis = this.getSystemUptimeMillis();
            return dispatchSession;
        }
        return DispatchSession.NOT_SAMPLED;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void messageDispatched(Object object, Message message) {
        if (!this.deviceStateAllowsCollection()) {
            return;
        }
        DispatchSession dispatchSession = (DispatchSession)object;
        boolean bl = dispatchSession != DispatchSession.NOT_SAMPLED;
        object = this.findEntry(message, bl);
        if (object != null) {
            synchronized (object) {
                ++((Entry)object).messageCount;
                if (dispatchSession != DispatchSession.NOT_SAMPLED) {
                    ++((Entry)object).recordedMessageCount;
                    long l = this.getElapsedRealtimeMicro() - dispatchSession.startTimeMicro;
                    long l2 = this.getThreadTimeMicro() - dispatchSession.cpuStartMicro;
                    ((Entry)object).totalLatencyMicro += l;
                    ((Entry)object).maxLatencyMicro = Math.max(((Entry)object).maxLatencyMicro, l);
                    ((Entry)object).cpuUsageMicro += l2;
                    ((Entry)object).maxCpuUsageMicro = Math.max(((Entry)object).maxCpuUsageMicro, l2);
                    if (message.getWhen() > 0L) {
                        l2 = Math.max(0L, dispatchSession.systemUptimeMillis - message.getWhen());
                        ((Entry)object).delayMillis += l2;
                        ((Entry)object).maxDelayMillis = Math.max(((Entry)object).maxDelayMillis, l2);
                        ++((Entry)object).recordedDelayMessageCount;
                    }
                }
            }
        }
        this.recycleSession(dispatchSession);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reset() {
        Object object = this.mLock;
        synchronized (object) {
            this.mEntries.clear();
        }
        object = this.mHashCollisionEntry;
        synchronized (object) {
            this.mHashCollisionEntry.reset();
        }
        object = this.mOverflowEntry;
        synchronized (object) {
            this.mOverflowEntry.reset();
        }
        this.mStartCurrentTime = System.currentTimeMillis();
        this.mStartElapsedTime = SystemClock.elapsedRealtime();
        object = this.mBatteryStopwatch;
        if (object != null) {
            ((CachedDeviceState.TimeInStateStopwatch)object).reset();
        }
    }

    public void setAddDebugEntries(boolean bl) {
        this.mAddDebugEntries = bl;
    }

    public void setDeviceState(CachedDeviceState.Readonly readonly) {
        CachedDeviceState.TimeInStateStopwatch timeInStateStopwatch = this.mBatteryStopwatch;
        if (timeInStateStopwatch != null) {
            timeInStateStopwatch.close();
        }
        this.mDeviceState = readonly;
        this.mBatteryStopwatch = readonly.createTimeOnBatteryStopwatch();
    }

    public void setSamplingInterval(int n) {
        this.mSamplingInterval = n;
    }

    public void setTrackScreenInteractive(boolean bl) {
        this.mTrackScreenInteractive = bl;
    }

    protected boolean shouldCollectDetailedData() {
        boolean bl = ThreadLocalRandom.current().nextInt() % this.mSamplingInterval == 0;
        return bl;
    }

    private static class DispatchSession {
        static final DispatchSession NOT_SAMPLED = new DispatchSession();
        public long cpuStartMicro;
        public long startTimeMicro;
        public long systemUptimeMillis;

        private DispatchSession() {
        }
    }

    private static class Entry {
        public long cpuUsageMicro;
        public long delayMillis;
        public long exceptionCount;
        public final Handler handler;
        public final boolean isInteractive;
        public long maxCpuUsageMicro;
        public long maxDelayMillis;
        public long maxLatencyMicro;
        public long messageCount;
        public final String messageName;
        public long recordedDelayMessageCount;
        public long recordedMessageCount;
        public long totalLatencyMicro;
        public final int workSourceUid;

        Entry(Message message, boolean bl) {
            this.workSourceUid = message.workSourceUid;
            this.handler = message.getTarget();
            this.messageName = this.handler.getMessageName(message);
            this.isInteractive = bl;
        }

        Entry(String string2) {
            this.workSourceUid = -1;
            this.messageName = string2;
            this.handler = null;
            this.isInteractive = false;
        }

        static int idFor(Message message, boolean bl) {
            int n = message.workSourceUid;
            int n2 = message.getTarget().getLooper().getThread().hashCode();
            int n3 = message.getTarget().getClass().hashCode();
            int n4 = bl ? 1231 : 1237;
            n4 = (((7 * 31 + n) * 31 + n2) * 31 + n3) * 31 + n4;
            if (message.getCallback() != null) {
                return n4 * 31 + message.getCallback().getClass().hashCode();
            }
            return n4 * 31 + message.what;
        }

        void reset() {
            this.messageCount = 0L;
            this.recordedMessageCount = 0L;
            this.exceptionCount = 0L;
            this.totalLatencyMicro = 0L;
            this.maxLatencyMicro = 0L;
            this.cpuUsageMicro = 0L;
            this.maxCpuUsageMicro = 0L;
            this.delayMillis = 0L;
            this.maxDelayMillis = 0L;
            this.recordedDelayMessageCount = 0L;
        }
    }

    public static class ExportedEntry {
        public final long cpuUsageMicros;
        public final long delayMillis;
        public final long exceptionCount;
        public final String handlerClassName;
        public final boolean isInteractive;
        public final long maxCpuUsageMicros;
        public final long maxDelayMillis;
        public final long maxLatencyMicros;
        public final long messageCount;
        public final String messageName;
        public final long recordedDelayMessageCount;
        public final long recordedMessageCount;
        public final String threadName;
        public final long totalLatencyMicros;
        public final int workSourceUid;

        ExportedEntry(Entry entry) {
            this.workSourceUid = entry.workSourceUid;
            if (entry.handler != null) {
                this.handlerClassName = entry.handler.getClass().getName();
                this.threadName = entry.handler.getLooper().getThread().getName();
            } else {
                this.handlerClassName = "";
                this.threadName = "";
            }
            this.isInteractive = entry.isInteractive;
            this.messageName = entry.messageName;
            this.messageCount = entry.messageCount;
            this.recordedMessageCount = entry.recordedMessageCount;
            this.exceptionCount = entry.exceptionCount;
            this.totalLatencyMicros = entry.totalLatencyMicro;
            this.maxLatencyMicros = entry.maxLatencyMicro;
            this.cpuUsageMicros = entry.cpuUsageMicro;
            this.maxCpuUsageMicros = entry.maxCpuUsageMicro;
            this.delayMillis = entry.delayMillis;
            this.maxDelayMillis = entry.maxDelayMillis;
            this.recordedDelayMessageCount = entry.recordedDelayMessageCount;
        }
    }

}

