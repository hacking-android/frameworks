/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 *  android.os.SystemClock
 *  android.telephony.Rlog
 */
package com.android.server.sip;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.Rlog;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.Executor;

class SipWakeupTimer
extends BroadcastReceiver {
    private static final boolean DBG = false;
    private static final String TAG = "SipWakeupTimer";
    private static final String TRIGGER_TIME = "TriggerTime";
    private AlarmManager mAlarmManager;
    private Context mContext;
    private TreeSet<MyEvent> mEventQueue = new TreeSet<MyEvent>(new MyEventComparator());
    private Executor mExecutor;
    private PendingIntent mPendingIntent;

    public SipWakeupTimer(Context context, Executor executor) {
        this.mContext = context;
        this.mAlarmManager = (AlarmManager)context.getSystemService("alarm");
        context.registerReceiver((BroadcastReceiver)this, new IntentFilter(this.getAction()));
        this.mExecutor = executor;
    }

    private void cancelAlarm() {
        this.mAlarmManager.cancel(this.mPendingIntent);
        this.mPendingIntent = null;
    }

    private void execute(long l) {
        if (!this.stopped() && !this.mEventQueue.isEmpty()) {
            for (MyEvent myEvent : this.mEventQueue) {
                if (myEvent.mTriggerTime != l) continue;
                myEvent.mLastTriggerTime = l;
                myEvent.mTriggerTime += (long)myEvent.mPeriod;
                this.mExecutor.execute(myEvent.mCallback);
            }
            this.scheduleNext();
            return;
        }
    }

    private String getAction() {
        return ((Object)((Object)this)).toString();
    }

    private void insertEvent(MyEvent myEvent) {
        long l = SystemClock.elapsedRealtime();
        if (this.mEventQueue.isEmpty()) {
            myEvent.mTriggerTime = (long)myEvent.mPeriod + l;
            this.mEventQueue.add(myEvent);
            return;
        }
        MyEvent myEvent2 = this.mEventQueue.first();
        int n = myEvent2.mPeriod;
        if (n <= myEvent.mMaxPeriod) {
            myEvent.mPeriod = myEvent.mMaxPeriod / n * n;
            int n2 = (myEvent.mMaxPeriod - (int)(myEvent2.mTriggerTime - l)) / n;
            myEvent.mTriggerTime = myEvent2.mTriggerTime + (long)(n2 * n);
            this.mEventQueue.add(myEvent);
        } else {
            if (myEvent2.mTriggerTime < (l = (long)myEvent.mPeriod + l)) {
                myEvent.mTriggerTime = myEvent2.mTriggerTime;
                myEvent.mLastTriggerTime -= (long)myEvent.mPeriod;
            } else {
                myEvent.mTriggerTime = l;
            }
            this.mEventQueue.add(myEvent);
            this.recalculatePeriods();
        }
    }

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private void printQueue() {
        int n;
        block4 : {
            int n2 = 0;
            Iterator<MyEvent> iterator = this.mEventQueue.iterator();
            do {
                n = n2++;
                if (!iterator.hasNext()) break block4;
                MyEvent myEvent = iterator.next();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("     ");
                stringBuilder.append(myEvent);
                stringBuilder.append(": scheduled at ");
                stringBuilder.append(this.showTime(myEvent.mTriggerTime));
                stringBuilder.append(": last at ");
                stringBuilder.append(this.showTime(myEvent.mLastTriggerTime));
                this.log(stringBuilder.toString());
            } while (n2 < 5);
            n = n2;
        }
        if (this.mEventQueue.size() > n) {
            this.log("     .....");
        } else if (n == 0) {
            this.log("     <empty>");
        }
    }

    private void recalculatePeriods() {
        if (this.mEventQueue.isEmpty()) {
            return;
        }
        TreeSet<MyEvent> treeSet = this.mEventQueue.first();
        int n = ((MyEvent)treeSet).mMaxPeriod;
        long l = ((MyEvent)treeSet).mTriggerTime;
        for (MyEvent myEvent : this.mEventQueue) {
            myEvent.mPeriod = myEvent.mMaxPeriod / n * n;
            myEvent.mTriggerTime = (long)((int)(myEvent.mLastTriggerTime + (long)myEvent.mMaxPeriod - l) / n * n) + l;
        }
        treeSet = new TreeSet<MyEvent>(this.mEventQueue.comparator());
        treeSet.addAll(this.mEventQueue);
        this.mEventQueue.clear();
        this.mEventQueue = treeSet;
    }

    private void scheduleNext() {
        if (!this.stopped() && !this.mEventQueue.isEmpty()) {
            if (this.mPendingIntent == null) {
                MyEvent myEvent = this.mEventQueue.first();
                Intent intent = new Intent(this.getAction());
                intent.putExtra(TRIGGER_TIME, myEvent.mTriggerTime);
                intent = PendingIntent.getBroadcast((Context)this.mContext, (int)0, (Intent)intent, (int)134217728);
                this.mPendingIntent = intent;
                this.mAlarmManager.set(2, myEvent.mTriggerTime, (PendingIntent)intent);
                return;
            }
            throw new RuntimeException("pendingIntent is not null!");
        }
    }

    private String showTime(long l) {
        int n = (int)(l % 1000L);
        int n2 = (int)(l / 1000L);
        return String.format("%d.%d.%d", n2 / 60, n2 % 60, n);
    }

    private boolean stopped() {
        return this.mEventQueue == null;
    }

    public void cancel(Runnable object) {
        synchronized (this) {
            if (!this.stopped() && !this.mEventQueue.isEmpty()) {
                MyEvent myEvent = this.mEventQueue.first();
                Iterator<MyEvent> iterator = this.mEventQueue.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().mCallback != object) continue;
                    iterator.remove();
                }
                if (this.mEventQueue.isEmpty()) {
                    this.cancelAlarm();
                } else if (this.mEventQueue.first() != myEvent) {
                    this.cancelAlarm();
                    object = this.mEventQueue.first();
                    ((MyEvent)object).mPeriod = ((MyEvent)object).mMaxPeriod;
                    ((MyEvent)object).mTriggerTime = ((MyEvent)object).mLastTriggerTime + (long)((MyEvent)object).mPeriod;
                    this.recalculatePeriods();
                    this.scheduleNext();
                }
                return;
            }
            return;
        }
    }

    public void onReceive(Context object, Intent intent) {
        synchronized (this) {
            object = intent.getAction();
            if (this.getAction().equals(object) && intent.getExtras().containsKey(TRIGGER_TIME)) {
                this.mPendingIntent = null;
                this.execute(intent.getLongExtra(TRIGGER_TIME, -1L));
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("onReceive: unrecognized intent: ");
                ((StringBuilder)object).append((Object)intent);
                this.log(((StringBuilder)object).toString());
            }
            return;
        }
    }

    public void set(int n, Runnable runnable) {
        synchronized (this) {
            block6 : {
                boolean bl = this.stopped();
                if (!bl) break block6;
                return;
            }
            long l = SystemClock.elapsedRealtime();
            MyEvent myEvent = new MyEvent(n, runnable, l);
            this.insertEvent(myEvent);
            if (this.mEventQueue.first() == myEvent) {
                if (this.mEventQueue.size() > 1) {
                    this.cancelAlarm();
                }
                this.scheduleNext();
            }
            l = myEvent.mTriggerTime;
            return;
        }
    }

    public void stop() {
        synchronized (this) {
            this.mContext.unregisterReceiver((BroadcastReceiver)this);
            if (this.mPendingIntent != null) {
                this.mAlarmManager.cancel(this.mPendingIntent);
                this.mPendingIntent = null;
            }
            this.mEventQueue.clear();
            this.mEventQueue = null;
            return;
        }
    }

    private static class MyEvent {
        Runnable mCallback;
        long mLastTriggerTime;
        int mMaxPeriod;
        int mPeriod;
        long mTriggerTime;

        MyEvent(int n, Runnable runnable, long l) {
            this.mMaxPeriod = n;
            this.mPeriod = n;
            this.mCallback = runnable;
            this.mLastTriggerTime = l;
        }

        private String toString(Object object) {
            String string = object.toString();
            int n = string.indexOf("$");
            object = string;
            if (n > 0) {
                object = string.substring(n + 1);
            }
            return object;
        }

        public String toString() {
            String string = super.toString();
            string = string.substring(string.indexOf("@"));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(":");
            stringBuilder.append(this.mPeriod / 1000);
            stringBuilder.append(":");
            stringBuilder.append(this.mMaxPeriod / 1000);
            stringBuilder.append(":");
            stringBuilder.append(this.toString(this.mCallback));
            return stringBuilder.toString();
        }
    }

    private static class MyEventComparator
    implements Comparator<MyEvent> {
        private MyEventComparator() {
        }

        @Override
        public int compare(MyEvent myEvent, MyEvent myEvent2) {
            int n;
            if (myEvent == myEvent2) {
                return 0;
            }
            int n2 = n = myEvent.mMaxPeriod - myEvent2.mMaxPeriod;
            if (n == 0) {
                n2 = -1;
            }
            return n2;
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = this == object;
            return bl;
        }
    }

}

