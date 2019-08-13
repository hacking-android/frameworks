/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.app.usage.UsageEvents;
import java.util.ArrayList;

public class EventList {
    private final ArrayList<UsageEvents.Event> mEvents = new ArrayList();

    public void clear() {
        this.mEvents.clear();
    }

    public int firstIndexOnOrAfter(long l) {
        int n = this.mEvents.size();
        int n2 = n--;
        int n3 = 0;
        while (n3 <= n) {
            int n4 = n3 + n >>> 1;
            if (this.mEvents.get((int)n4).mTimeStamp >= l) {
                n = n4 - 1;
                n2 = n4;
                continue;
            }
            n3 = n4 + 1;
        }
        return n2;
    }

    public UsageEvents.Event get(int n) {
        return this.mEvents.get(n);
    }

    public void insert(UsageEvents.Event event) {
        int n = this.mEvents.size();
        if (n != 0 && event.mTimeStamp < this.mEvents.get((int)(n - 1)).mTimeStamp) {
            n = this.firstIndexOnOrAfter(event.mTimeStamp + 1L);
            this.mEvents.add(n, event);
            return;
        }
        this.mEvents.add(event);
    }

    public void merge(EventList eventList) {
        int n = eventList.size();
        for (int i = 0; i < n; ++i) {
            this.insert(eventList.get(i));
        }
    }

    public int size() {
        return this.mEvents.size();
    }
}

