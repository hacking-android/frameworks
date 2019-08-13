/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telecom.Logging.-$
 *  android.telecom.Logging.-$$Lambda
 *  android.telecom.Logging.-$$Lambda$EventManager
 *  android.telecom.Logging.-$$Lambda$EventManager$weOtitr8e1cZeiy1aDSqzNoKaY8
 */
package android.telecom.Logging;

import android.telecom.Log;
import android.telecom.Logging.-$;
import android.telecom.Logging.SessionManager;
import android.telecom.Logging.TimedEvent;
import android.telecom.Logging._$$Lambda$EventManager$uddp6XAJ90VBwdTiuzBdSYelntQ;
import android.telecom.Logging._$$Lambda$EventManager$weOtitr8e1cZeiy1aDSqzNoKaY8;
import android.text.TextUtils;
import android.util.Pair;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.IndentingPrintWriter;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class EventManager {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    @VisibleForTesting
    public static final int DEFAULT_EVENTS_TO_CACHE = 10;
    public static final String TAG = "Logging.Events";
    private static final Object mSync = new Object();
    private final Map<Loggable, EventRecord> mCallEventRecordMap = new HashMap<Loggable, EventRecord>();
    private List<EventListener> mEventListeners = new ArrayList<EventListener>();
    private LinkedBlockingQueue<EventRecord> mEventRecords = new LinkedBlockingQueue(10);
    private SessionManager.ISessionIdQueryHandler mSessionIdHandler;
    private final Map<String, List<TimedEventPair>> requestResponsePairs = new HashMap<String, List<TimedEventPair>>();

    public EventManager(SessionManager.ISessionIdQueryHandler iSessionIdQueryHandler) {
        this.mSessionIdHandler = iSessionIdQueryHandler;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void addEventRecord(EventRecord eventRecord) {
        Object object;
        Object object2 = eventRecord.getRecordEntry();
        if (this.mEventRecords.remainingCapacity() == 0 && (object = this.mEventRecords.poll()) != null) {
            this.mCallEventRecordMap.remove(((EventRecord)object).getRecordEntry());
        }
        this.mEventRecords.add(eventRecord);
        this.mCallEventRecordMap.put((Loggable)object2, eventRecord);
        object2 = mSync;
        synchronized (object2) {
            object = this.mEventListeners.iterator();
            while (object.hasNext()) {
                ((EventListener)object.next()).eventRecordAdded(eventRecord);
            }
            return;
        }
    }

    static /* synthetic */ long lambda$dumpEventsTimeline$0(Pair pair) {
        return ((Event)pair.second).time;
    }

    public void addRequestResponsePair(TimedEventPair timedEventPair) {
        if (this.requestResponsePairs.containsKey(timedEventPair.mRequest)) {
            this.requestResponsePairs.get(timedEventPair.mRequest).add(timedEventPair);
        } else {
            ArrayList<TimedEventPair> arrayList = new ArrayList<TimedEventPair>();
            arrayList.add(timedEventPair);
            this.requestResponsePairs.put(timedEventPair.mRequest, arrayList);
        }
    }

    public void changeEventCacheSize(int n) {
        LinkedBlockingQueue<EventRecord> linkedBlockingQueue = this.mEventRecords;
        this.mEventRecords = new LinkedBlockingQueue(n);
        this.mCallEventRecordMap.clear();
        linkedBlockingQueue.forEach(new _$$Lambda$EventManager$uddp6XAJ90VBwdTiuzBdSYelntQ(this));
    }

    public void dumpEvents(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Historical Events:");
        indentingPrintWriter.increaseIndent();
        Iterator<EventRecord> iterator = this.mEventRecords.iterator();
        while (iterator.hasNext()) {
            iterator.next().dump(indentingPrintWriter);
        }
        indentingPrintWriter.decreaseIndent();
    }

    public void dumpEventsTimeline(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Historical Events (sorted by time):");
        ArrayList<Pair<Loggable, Event>> arrayList = new ArrayList<Pair<Loggable, Event>>();
        for (EventRecord eventRecord : this.mEventRecords) {
            for (Event event : eventRecord.getEvents()) {
                arrayList.add(new Pair<Loggable, Event>(eventRecord.getRecordEntry(), event));
            }
        }
        arrayList.sort(Comparator.comparingLong(_$$Lambda$EventManager$weOtitr8e1cZeiy1aDSqzNoKaY8.INSTANCE));
        indentingPrintWriter.increaseIndent();
        for (Pair pair : arrayList) {
            indentingPrintWriter.print(((Event)pair.second).timestampString);
            indentingPrintWriter.print(",");
            indentingPrintWriter.print(((Loggable)pair.first).getId());
            indentingPrintWriter.print(",");
            indentingPrintWriter.print(((Event)pair.second).eventId);
            indentingPrintWriter.print(",");
            indentingPrintWriter.println(((Event)pair.second).data);
        }
        indentingPrintWriter.decreaseIndent();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void event(Loggable loggable, String string2, Object object) {
        String string3 = this.mSessionIdHandler.getSessionId();
        if (loggable == null) {
            Log.i(TAG, "Non-call EVENT: %s, %s", string2, object);
            return;
        }
        LinkedBlockingQueue<EventRecord> linkedBlockingQueue = this.mEventRecords;
        synchronized (linkedBlockingQueue) {
            if (!this.mCallEventRecordMap.containsKey(loggable)) {
                EventRecord eventRecord = new EventRecord(loggable);
                this.addEventRecord(eventRecord);
            }
            this.mCallEventRecordMap.get(loggable).addEvent(string2, string3, object);
            return;
        }
    }

    public void event(Loggable loggable, String string2, String string3, Object ... object) {
        if (object != null) {
            try {
                if (((Object[])object).length != 0) {
                    String string4;
                    string3 = string4 = String.format(Locale.US, string3, (Object[])object);
                }
            }
            catch (IllegalFormatException illegalFormatException) {
                Log.e(this, (Throwable)illegalFormatException, "IllegalFormatException: formatString='%s' numArgs=%d", string3, ((Object[])object).length);
                object = new StringBuilder();
                ((StringBuilder)object).append(string3);
                ((StringBuilder)object).append(" (An error occurred while formatting the message.)");
                string3 = ((StringBuilder)object).toString();
            }
        }
        this.event(loggable, string2, string3);
    }

    @VisibleForTesting
    public Map<Loggable, EventRecord> getCallEventRecordMap() {
        return this.mCallEventRecordMap;
    }

    @VisibleForTesting
    public LinkedBlockingQueue<EventRecord> getEventRecords() {
        return this.mEventRecords;
    }

    public /* synthetic */ void lambda$changeEventCacheSize$1$EventManager(EventRecord eventRecord) {
        EventRecord eventRecord2;
        Loggable loggable = eventRecord.getRecordEntry();
        if (this.mEventRecords.remainingCapacity() == 0 && (eventRecord2 = this.mEventRecords.poll()) != null) {
            this.mCallEventRecordMap.remove(eventRecord2.getRecordEntry());
        }
        this.mEventRecords.add(eventRecord);
        this.mCallEventRecordMap.put(loggable, eventRecord);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerEventListener(EventListener eventListener) {
        if (eventListener == null) return;
        Object object = mSync;
        synchronized (object) {
            this.mEventListeners.add(eventListener);
            return;
        }
    }

    public static class Event {
        public Object data;
        public String eventId;
        public String sessionId;
        public long time;
        public final String timestampString;

        public Event(String string2, String string3, long l, Object object) {
            this.eventId = string2;
            this.sessionId = string3;
            this.time = l;
            this.timestampString = ZonedDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneId.systemDefault()).format(DATE_TIME_FORMATTER);
            this.data = object;
        }
    }

    public static interface EventListener {
        public void eventRecordAdded(EventRecord var1);
    }

    public class EventRecord {
        private final List<Event> mEvents = Collections.synchronizedList(new LinkedList());
        private final Loggable mRecordEntry;

        public EventRecord(Loggable loggable) {
            this.mRecordEntry = loggable;
        }

        public void addEvent(String string2, String string3, Object object) {
            this.mEvents.add(new Event(string2, string3, System.currentTimeMillis(), object));
            Log.i("Event", "RecordEntry %s: %s, %s", this.mRecordEntry.getId(), string2, object);
        }

        public void dump(IndentingPrintWriter indentingPrintWriter) {
            Object object;
            Object object2;
            indentingPrintWriter.print(this.mRecordEntry.getDescription());
            indentingPrintWriter.increaseIndent();
            for (Event event : this.getEvents()) {
                indentingPrintWriter.print(event.timestampString);
                indentingPrintWriter.print(" - ");
                indentingPrintWriter.print(event.eventId);
                if (event.data != null) {
                    indentingPrintWriter.print(" (");
                    object2 = object = event.data;
                    if (object instanceof Loggable) {
                        EventRecord eventRecord = (EventRecord)EventManager.this.mCallEventRecordMap.get(object);
                        object2 = object;
                        if (eventRecord != null) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("RecordEntry ");
                            ((StringBuilder)object2).append(eventRecord.mRecordEntry.getId());
                            object2 = ((StringBuilder)object2).toString();
                        }
                    }
                    indentingPrintWriter.print(object2);
                    indentingPrintWriter.print(")");
                }
                if (!TextUtils.isEmpty(event.sessionId)) {
                    indentingPrintWriter.print(":");
                    indentingPrintWriter.print(event.sessionId);
                }
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println("Timings (average for this call, milliseconds):");
            indentingPrintWriter.increaseIndent();
            object2 = EventTiming.averageTimings(this.extractEventTimings());
            object = new ArrayList(object2.keySet());
            Collections.sort(object);
            object = object.iterator();
            while (object.hasNext()) {
                String string2 = (String)object.next();
                indentingPrintWriter.printf("%s: %.2f\n", string2, object2.get(string2));
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public List<EventTiming> extractEventTimings() {
            if (this.mEvents == null) {
                return Collections.emptyList();
            }
            LinkedList<EventTiming> linkedList = new LinkedList<EventTiming>();
            HashMap<String, PendingResponse> hashMap = new HashMap<String, PendingResponse>();
            List<Event> list = this.mEvents;
            synchronized (list) {
                Iterator<Event> iterator = this.mEvents.iterator();
                while (iterator.hasNext()) {
                    Object object;
                    long l;
                    Object object2 = iterator.next();
                    if (EventManager.this.requestResponsePairs.containsKey(((Event)object2).eventId)) {
                        object = ((List)EventManager.this.requestResponsePairs.get(((Event)object2).eventId)).iterator();
                        while (object.hasNext()) {
                            TimedEventPair timedEventPair = (TimedEventPair)object.next();
                            String string2 = timedEventPair.mResponse;
                            PendingResponse pendingResponse = new PendingResponse(((Event)object2).eventId, ((Event)object2).time, timedEventPair.mTimeoutMillis, timedEventPair.mName);
                            hashMap.put(string2, pendingResponse);
                        }
                    }
                    if ((object = (PendingResponse)hashMap.remove(((Event)object2).eventId)) == null || (l = ((Event)object2).time - ((PendingResponse)object).requestEventTimeMillis) >= ((PendingResponse)object).timeoutMillis) continue;
                    object2 = new EventTiming(((PendingResponse)object).name, l);
                    linkedList.add((EventTiming)object2);
                }
                return linkedList;
            }
        }

        public List<Event> getEvents() {
            return new LinkedList<Event>(this.mEvents);
        }

        public Loggable getRecordEntry() {
            return this.mRecordEntry;
        }

        public class EventTiming
        extends TimedEvent<String> {
            public String name;
            public long time;

            public EventTiming(String string2, long l) {
                this.name = string2;
                this.time = l;
            }

            @Override
            public String getKey() {
                return this.name;
            }

            @Override
            public long getTime() {
                return this.time;
            }
        }

        private class PendingResponse {
            String name;
            String requestEventId;
            long requestEventTimeMillis;
            long timeoutMillis;

            public PendingResponse(String string2, long l, long l2, String string3) {
                this.requestEventId = string2;
                this.requestEventTimeMillis = l;
                this.timeoutMillis = l2;
                this.name = string3;
            }
        }

    }

    public static interface Loggable {
        public String getDescription();

        public String getId();
    }

    public static class TimedEventPair {
        private static final long DEFAULT_TIMEOUT = 3000L;
        String mName;
        String mRequest;
        String mResponse;
        long mTimeoutMillis = 3000L;

        public TimedEventPair(String string2, String string3, String string4) {
            this.mRequest = string2;
            this.mResponse = string3;
            this.mName = string4;
        }

        public TimedEventPair(String string2, String string3, String string4, long l) {
            this.mRequest = string2;
            this.mResponse = string3;
            this.mName = string4;
            this.mTimeoutMillis = l;
        }
    }

}

