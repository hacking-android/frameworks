/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.media.MediaFormat;
import android.media.MediaTimeProvider;
import android.media.SubtitleData;
import android.os.Handler;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

public abstract class SubtitleTrack
implements MediaTimeProvider.OnMediaTimeListener {
    private static final String TAG = "SubtitleTrack";
    public boolean DEBUG = false;
    protected final Vector<Cue> mActiveCues = new Vector();
    protected CueList mCues;
    private MediaFormat mFormat;
    protected Handler mHandler = new Handler();
    private long mLastTimeMs;
    private long mLastUpdateTimeMs;
    private long mNextScheduledTimeMs = -1L;
    private Runnable mRunnable;
    protected final LongSparseArray<Run> mRunsByEndTime = new LongSparseArray();
    protected final LongSparseArray<Run> mRunsByID = new LongSparseArray();
    protected MediaTimeProvider mTimeProvider;
    protected boolean mVisible;

    public SubtitleTrack(MediaFormat mediaFormat) {
        this.mFormat = mediaFormat;
        this.mCues = new CueList();
        this.clearActiveCues();
        this.mLastTimeMs = -1L;
    }

    private void removeRunsByEndTimeIndex(int n) {
        Object object = this.mRunsByEndTime.valueAt(n);
        while (object != null) {
            Object object2 = ((Run)object).mFirstCue;
            while (object2 != null) {
                this.mCues.remove((Cue)object2);
                Cue cue = ((Cue)object2).mNextInRun;
                ((Cue)object2).mNextInRun = null;
                object2 = cue;
            }
            this.mRunsByID.remove(((Run)object).mRunID);
            object2 = ((Run)object).mNextRunAtEndTimeMs;
            ((Run)object).mPrevRunAtEndTimeMs = null;
            ((Run)object).mNextRunAtEndTimeMs = null;
            object = object2;
        }
        this.mRunsByEndTime.removeAt(n);
    }

    private void takeTime(long l) {
        synchronized (this) {
            this.mLastTimeMs = l;
            return;
        }
    }

    protected boolean addCue(Cue runnable) {
        synchronized (this) {
            block22 : {
                Object object;
                long l;
                block21 : {
                    block18 : {
                        block20 : {
                            Run run;
                            block19 : {
                                this.mCues.add((Cue)((Object)runnable));
                                if (((Cue)runnable).mRunID == 0L) break block18;
                                run = this.mRunsByID.get(((Cue)runnable).mRunID);
                                if (run != null) break block19;
                                object = new Run();
                                this.mRunsByID.put(((Cue)runnable).mRunID, (Run)object);
                                ((Run)object).mEndTimeMs = ((Cue)runnable).mEndTimeMs;
                                break block20;
                            }
                            object = run;
                            if (run.mEndTimeMs >= ((Cue)runnable).mEndTimeMs) break block20;
                            run.mEndTimeMs = ((Cue)runnable).mEndTimeMs;
                            object = run;
                        }
                        ((Cue)runnable).mNextInRun = ((Run)object).mFirstCue;
                        ((Run)object).mFirstCue = runnable;
                    }
                    long l2 = -1L;
                    object = this.mTimeProvider;
                    l = l2;
                    if (object == null) break block21;
                    try {
                        l = this.mTimeProvider.getCurrentTimeUs(false, true) / 1000L;
                    }
                    catch (IllegalStateException illegalStateException) {
                        l = l2;
                    }
                }
                if (this.DEBUG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("mVisible=");
                    ((StringBuilder)object).append(this.mVisible);
                    ((StringBuilder)object).append(", ");
                    ((StringBuilder)object).append(((Cue)runnable).mStartTimeMs);
                    ((StringBuilder)object).append(" <= ");
                    ((StringBuilder)object).append(l);
                    ((StringBuilder)object).append(", ");
                    ((StringBuilder)object).append(((Cue)runnable).mEndTimeMs);
                    ((StringBuilder)object).append(" >= ");
                    ((StringBuilder)object).append(this.mLastTimeMs);
                    Log.v(TAG, ((StringBuilder)object).toString());
                }
                if (!this.mVisible || ((Cue)runnable).mStartTimeMs > l || ((Cue)runnable).mEndTimeMs < this.mLastTimeMs) break block22;
                if (this.mRunnable != null) {
                    this.mHandler.removeCallbacks(this.mRunnable);
                }
                runnable = new Runnable(){

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void run() {
                        SubtitleTrack subtitleTrack = this;
                        synchronized (subtitleTrack) {
                            SubtitleTrack.this.mRunnable = null;
                            SubtitleTrack.this.updateActiveCues(true, l);
                            SubtitleTrack.this.updateView(SubtitleTrack.this.mActiveCues);
                            return;
                        }
                    }
                };
                this.mRunnable = runnable;
                if (this.mHandler.postDelayed(this.mRunnable, 10L)) {
                    if (this.DEBUG) {
                        Log.v(TAG, "scheduling update");
                    }
                } else if (this.DEBUG) {
                    Log.w(TAG, "failed to schedule subtitle view update");
                }
                return true;
            }
            if (this.mVisible && ((Cue)runnable).mEndTimeMs >= this.mLastTimeMs && (((Cue)runnable).mStartTimeMs < this.mNextScheduledTimeMs || this.mNextScheduledTimeMs < 0L)) {
                this.scheduleTimedEvents();
            }
            return false;
        }
    }

    protected void clearActiveCues() {
        synchronized (this) {
            if (this.DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Clearing ");
                stringBuilder.append(this.mActiveCues.size());
                stringBuilder.append(" active cues");
                Log.v(TAG, stringBuilder.toString());
            }
            this.mActiveCues.clear();
            this.mLastUpdateTimeMs = -1L;
            return;
        }
    }

    protected void finalize() throws Throwable {
        for (int i = this.mRunsByEndTime.size() - 1; i >= 0; --i) {
            this.removeRunsByEndTimeIndex(i);
        }
        super.finalize();
    }

    protected void finishedRun(long l) {
        Run run;
        if (l != 0L && l != -1L && (run = this.mRunsByID.get(l)) != null) {
            run.storeByEndTimeMs(this.mRunsByEndTime);
        }
    }

    public final MediaFormat getFormat() {
        return this.mFormat;
    }

    public abstract RenderingWidget getRenderingWidget();

    public int getTrackType() {
        int n = this.getRenderingWidget() == null ? 3 : 4;
        return n;
    }

    public void hide() {
        if (!this.mVisible) {
            return;
        }
        Object object = this.mTimeProvider;
        if (object != null) {
            object.cancelNotifications(this);
        }
        if ((object = this.getRenderingWidget()) != null) {
            object.setVisible(false);
        }
        this.mVisible = false;
    }

    protected void onData(SubtitleData subtitleData) {
        long l = subtitleData.getStartTimeUs() + 1L;
        this.onData(subtitleData.getData(), true, l);
        this.setRunDiscardTimeMs(l, (subtitleData.getStartTimeUs() + subtitleData.getDurationUs()) / 1000L);
    }

    public abstract void onData(byte[] var1, boolean var2, long var3);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onSeek(long l) {
        if (this.DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onSeek ");
            stringBuilder.append(l);
            Log.d("SubtitleTrack", stringBuilder.toString());
        }
        synchronized (this) {
            this.updateActiveCues(true, l /= 1000L);
            this.takeTime(l);
        }
        this.updateView(this.mActiveCues);
        this.scheduleTimedEvents();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onStop() {
        synchronized (this) {
            if (this.DEBUG) {
                Log.d("SubtitleTrack", "onStop");
            }
            this.clearActiveCues();
            this.mLastTimeMs = -1L;
        }
        this.updateView(this.mActiveCues);
        this.mNextScheduledTimeMs = -1L;
        MediaTimeProvider mediaTimeProvider = this.mTimeProvider;
        if (mediaTimeProvider != null) {
            mediaTimeProvider.notifyAt(-1L, this);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onTimedEvent(long l) {
        if (this.DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onTimedEvent ");
            stringBuilder.append(l);
            Log.d("SubtitleTrack", stringBuilder.toString());
        }
        synchronized (this) {
            this.updateActiveCues(false, l /= 1000L);
            this.takeTime(l);
        }
        this.updateView(this.mActiveCues);
        this.scheduleTimedEvents();
    }

    protected void scheduleTimedEvents() {
        if (this.mTimeProvider != null) {
            Object object;
            this.mNextScheduledTimeMs = this.mCues.nextTimeAfter(this.mLastTimeMs);
            if (this.DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("sched @");
                ((StringBuilder)object).append(this.mNextScheduledTimeMs);
                ((StringBuilder)object).append(" after ");
                ((StringBuilder)object).append(this.mLastTimeMs);
                Log.d("SubtitleTrack", ((StringBuilder)object).toString());
            }
            object = this.mTimeProvider;
            long l = this.mNextScheduledTimeMs;
            l = l >= 0L ? (l *= 1000L) : -1L;
            object.notifyAt(l, this);
        }
    }

    public void setRunDiscardTimeMs(long l, long l2) {
        Run run;
        if (l != 0L && l != -1L && (run = this.mRunsByID.get(l)) != null) {
            run.mEndTimeMs = l2;
            run.storeByEndTimeMs(this.mRunsByEndTime);
        }
    }

    public void setTimeProvider(MediaTimeProvider mediaTimeProvider) {
        synchronized (this) {
            block6 : {
                MediaTimeProvider mediaTimeProvider2 = this.mTimeProvider;
                if (mediaTimeProvider2 != mediaTimeProvider) break block6;
                return;
            }
            if (this.mTimeProvider != null) {
                this.mTimeProvider.cancelNotifications(this);
            }
            this.mTimeProvider = mediaTimeProvider;
            if (this.mTimeProvider != null) {
                this.mTimeProvider.scheduleUpdate(this);
            }
            return;
        }
    }

    public void show() {
        if (this.mVisible) {
            return;
        }
        this.mVisible = true;
        Object object = this.getRenderingWidget();
        if (object != null) {
            object.setVisible(true);
        }
        if ((object = this.mTimeProvider) != null) {
            object.scheduleUpdate(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void updateActiveCues(boolean bl, long l) {
        synchronized (this) {
            if (bl || this.mLastUpdateTimeMs > l) {
                this.clearActiveCues();
            }
            Iterator<Pair<Long, Cue>> iterator = this.mCues.entriesBetween(this.mLastUpdateTimeMs, l).iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                Cue cue = (Cue)((Pair)object).second;
                if (cue.mEndTimeMs == (Long)((Pair)object).first) {
                    if (this.DEBUG) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Removing ");
                        ((StringBuilder)object).append(cue);
                        Log.v("SubtitleTrack", ((StringBuilder)object).toString());
                    }
                    this.mActiveCues.remove(cue);
                    if (cue.mRunID != 0L) continue;
                    iterator.remove();
                    continue;
                }
                if (cue.mStartTimeMs == (Long)((Pair)object).first) {
                    if (this.DEBUG) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Adding ");
                        ((StringBuilder)object).append(cue);
                        Log.v("SubtitleTrack", ((StringBuilder)object).toString());
                    }
                    if (cue.mInnerTimesMs != null) {
                        cue.onTime(l);
                    }
                    this.mActiveCues.add(cue);
                    continue;
                }
                if (cue.mInnerTimesMs == null) continue;
                cue.onTime(l);
            }
            while (this.mRunsByEndTime.size() > 0 && this.mRunsByEndTime.keyAt(0) <= l) {
                this.removeRunsByEndTimeIndex(0);
            }
            this.mLastUpdateTimeMs = l;
            return;
        }
    }

    public abstract void updateView(Vector<Cue> var1);

    public static class Cue {
        public long mEndTimeMs;
        public long[] mInnerTimesMs;
        public Cue mNextInRun;
        public long mRunID;
        public long mStartTimeMs;

        public void onTime(long l) {
        }
    }

    static class CueList {
        private static final String TAG = "CueList";
        public boolean DEBUG = false;
        private SortedMap<Long, Vector<Cue>> mCues = new TreeMap<Long, Vector<Cue>>();

        CueList() {
        }

        private boolean addEvent(Cue cue, long l) {
            Vector<Cue> vector;
            Vector<Cue> vector2 = (Vector<Cue>)this.mCues.get(l);
            if (vector2 == null) {
                vector = new Vector<Cue>(2);
                this.mCues.put(l, vector);
            } else {
                vector = vector2;
                if (vector2.contains(cue)) {
                    return false;
                }
            }
            vector.add(cue);
            return true;
        }

        private void removeEvent(Cue cue, long l) {
            Vector vector = (Vector)this.mCues.get(l);
            if (vector != null) {
                vector.remove(cue);
                if (vector.size() == 0) {
                    this.mCues.remove(l);
                }
            }
        }

        public void add(Cue cue) {
            if (cue.mStartTimeMs >= cue.mEndTimeMs) {
                return;
            }
            if (!this.addEvent(cue, cue.mStartTimeMs)) {
                return;
            }
            long l = cue.mStartTimeMs;
            if (cue.mInnerTimesMs != null) {
                for (long l2 : cue.mInnerTimesMs) {
                    long l3 = l;
                    if (l2 > l) {
                        l3 = l;
                        if (l2 < cue.mEndTimeMs) {
                            this.addEvent(cue, l2);
                            l3 = l2;
                        }
                    }
                    l = l3;
                }
            }
            this.addEvent(cue, cue.mEndTimeMs);
        }

        public Iterable<Pair<Long, Cue>> entriesBetween(final long l, final long l2) {
            return new Iterable<Pair<Long, Cue>>(){

                @Override
                public Iterator<Pair<Long, Cue>> iterator() {
                    Object object;
                    if (CueList.this.DEBUG) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("slice (");
                        ((StringBuilder)object).append(l);
                        ((StringBuilder)object).append(", ");
                        ((StringBuilder)object).append(l2);
                        ((StringBuilder)object).append("]=");
                        Log.d(CueList.TAG, ((StringBuilder)object).toString());
                    }
                    try {
                        object = new EntryIterator(CueList.this.mCues.subMap(l + 1L, l2 + 1L));
                        return object;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        return new EntryIterator(null);
                    }
                }
            };
        }

        public long nextTimeAfter(long l) {
            block4 : {
                SortedMap<Long, Vector<Cue>> sortedMap = this.mCues.tailMap(1L + l);
                if (sortedMap == null) break block4;
                try {
                    l = sortedMap.firstKey();
                    return l;
                }
                catch (NoSuchElementException noSuchElementException) {
                    return -1L;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    return -1L;
                }
            }
            return -1L;
        }

        public void remove(Cue cue) {
            this.removeEvent(cue, cue.mStartTimeMs);
            if (cue.mInnerTimesMs != null) {
                long[] arrl = cue.mInnerTimesMs;
                int n = arrl.length;
                for (int i = 0; i < n; ++i) {
                    this.removeEvent(cue, arrl[i]);
                }
            }
            this.removeEvent(cue, cue.mEndTimeMs);
        }

        class EntryIterator
        implements Iterator<Pair<Long, Cue>> {
            private long mCurrentTimeMs;
            private boolean mDone;
            private Pair<Long, Cue> mLastEntry;
            private Iterator<Cue> mLastListIterator;
            private Iterator<Cue> mListIterator;
            private SortedMap<Long, Vector<Cue>> mRemainingCues;

            public EntryIterator(SortedMap<Long, Vector<Cue>> sortedMap) {
                if (((CueList)CueList.this).DEBUG) {
                    CueList.this = new StringBuilder();
                    ((StringBuilder)CueList.this).append(sortedMap);
                    ((StringBuilder)CueList.this).append("");
                    Log.v(CueList.TAG, ((StringBuilder)CueList.this).toString());
                }
                this.mRemainingCues = sortedMap;
                this.mLastListIterator = null;
                this.nextKey();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            private void nextKey() {
                block6 : {
                    do {
                        if (this.mRemainingCues == null) break block6;
                        this.mCurrentTimeMs = this.mRemainingCues.firstKey();
                        this.mListIterator = ((Vector)this.mRemainingCues.get(this.mCurrentTimeMs)).iterator();
                        try {
                            this.mRemainingCues = this.mRemainingCues.tailMap(this.mCurrentTimeMs + 1L);
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            this.mRemainingCues = null;
                        }
                        this.mDone = false;
                    } while (!this.mListIterator.hasNext());
                    return;
                }
                try {
                    NoSuchElementException noSuchElementException = new NoSuchElementException("");
                    throw noSuchElementException;
                }
                catch (NoSuchElementException noSuchElementException) {
                    this.mDone = true;
                    this.mRemainingCues = null;
                    this.mListIterator = null;
                    return;
                }
            }

            @Override
            public boolean hasNext() {
                return this.mDone ^ true;
            }

            @Override
            public Pair<Long, Cue> next() {
                if (!this.mDone) {
                    this.mLastEntry = new Pair<Long, Cue>(this.mCurrentTimeMs, this.mListIterator.next());
                    Iterator<Cue> iterator = this.mListIterator;
                    this.mLastListIterator = iterator;
                    if (!iterator.hasNext()) {
                        this.nextKey();
                    }
                    return this.mLastEntry;
                }
                throw new NoSuchElementException("");
            }

            @Override
            public void remove() {
                if (this.mLastListIterator != null && ((Cue)this.mLastEntry.second).mEndTimeMs == (Long)this.mLastEntry.first) {
                    this.mLastListIterator.remove();
                    this.mLastListIterator = null;
                    if (((Vector)CueList.this.mCues.get(this.mLastEntry.first)).size() == 0) {
                        CueList.this.mCues.remove(this.mLastEntry.first);
                    }
                    Cue cue = (Cue)this.mLastEntry.second;
                    CueList.this.removeEvent(cue, cue.mStartTimeMs);
                    if (cue.mInnerTimesMs != null) {
                        for (long l : cue.mInnerTimesMs) {
                            CueList.this.removeEvent(cue, l);
                        }
                    }
                    return;
                }
                throw new IllegalStateException("");
            }
        }

    }

    public static interface RenderingWidget {
        @UnsupportedAppUsage
        public void draw(Canvas var1);

        @UnsupportedAppUsage
        public void onAttachedToWindow();

        @UnsupportedAppUsage
        public void onDetachedFromWindow();

        @UnsupportedAppUsage
        public void setOnChangedListener(OnChangedListener var1);

        @UnsupportedAppUsage
        public void setSize(int var1, int var2);

        public void setVisible(boolean var1);

        public static interface OnChangedListener {
            public void onChanged(RenderingWidget var1);
        }

    }

    private static class Run {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public long mEndTimeMs = -1L;
        public Cue mFirstCue;
        public Run mNextRunAtEndTimeMs;
        public Run mPrevRunAtEndTimeMs;
        public long mRunID = 0L;
        private long mStoredEndTimeMs = -1L;

        private Run() {
        }

        public void removeAtEndTimeMs() {
            Run run = this.mPrevRunAtEndTimeMs;
            Run run2 = this.mPrevRunAtEndTimeMs;
            if (run2 != null) {
                run2.mNextRunAtEndTimeMs = this.mNextRunAtEndTimeMs;
                this.mPrevRunAtEndTimeMs = null;
            }
            if ((run2 = this.mNextRunAtEndTimeMs) != null) {
                run2.mPrevRunAtEndTimeMs = run;
                this.mNextRunAtEndTimeMs = null;
            }
        }

        public void storeByEndTimeMs(LongSparseArray<Run> longSparseArray) {
            Run run;
            long l;
            int n = longSparseArray.indexOfKey(this.mStoredEndTimeMs);
            if (n >= 0) {
                if (this.mPrevRunAtEndTimeMs == null) {
                    run = this.mNextRunAtEndTimeMs;
                    if (run == null) {
                        longSparseArray.removeAt(n);
                    } else {
                        longSparseArray.setValueAt(n, run);
                    }
                }
                this.removeAtEndTimeMs();
            }
            if ((l = this.mEndTimeMs) >= 0L) {
                this.mPrevRunAtEndTimeMs = null;
                this.mNextRunAtEndTimeMs = longSparseArray.get(l);
                run = this.mNextRunAtEndTimeMs;
                if (run != null) {
                    run.mPrevRunAtEndTimeMs = this;
                }
                longSparseArray.put(this.mEndTimeMs, this);
                this.mStoredEndTimeMs = this.mEndTimeMs;
            }
        }
    }

}

