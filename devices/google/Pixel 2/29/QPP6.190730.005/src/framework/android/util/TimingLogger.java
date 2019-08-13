/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;

public class TimingLogger {
    private boolean mDisabled;
    private String mLabel;
    ArrayList<String> mSplitLabels;
    ArrayList<Long> mSplits;
    private String mTag;

    public TimingLogger(String string2, String string3) {
        this.reset(string2, string3);
    }

    public void addSplit(String string2) {
        if (this.mDisabled) {
            return;
        }
        long l = SystemClock.elapsedRealtime();
        this.mSplits.add(l);
        this.mSplitLabels.add(string2);
    }

    public void dumpToLog() {
        long l;
        if (this.mDisabled) {
            return;
        }
        CharSequence charSequence = this.mTag;
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append(this.mLabel);
        charSequence2.append(": begin");
        Log.d((String)charSequence, charSequence2.toString());
        long l2 = l = this.mSplits.get(0).longValue();
        for (int i = 1; i < this.mSplits.size(); ++i) {
            l2 = this.mSplits.get(i);
            charSequence2 = this.mSplitLabels.get(i);
            long l3 = this.mSplits.get(i - 1);
            String string2 = this.mTag;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.mLabel);
            ((StringBuilder)charSequence).append(":      ");
            ((StringBuilder)charSequence).append(l2 - l3);
            ((StringBuilder)charSequence).append(" ms, ");
            ((StringBuilder)charSequence).append((String)charSequence2);
            Log.d(string2, ((StringBuilder)charSequence).toString());
        }
        charSequence2 = this.mTag;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this.mLabel);
        ((StringBuilder)charSequence).append(": end, ");
        ((StringBuilder)charSequence).append(l2 - l);
        ((StringBuilder)charSequence).append(" ms");
        Log.d((String)charSequence2, ((StringBuilder)charSequence).toString());
    }

    public void reset() {
        this.mDisabled = Log.isLoggable(this.mTag, 2) ^ true;
        if (this.mDisabled) {
            return;
        }
        ArrayList<Long> arrayList = this.mSplits;
        if (arrayList == null) {
            this.mSplits = new ArrayList();
            this.mSplitLabels = new ArrayList();
        } else {
            arrayList.clear();
            this.mSplitLabels.clear();
        }
        this.addSplit(null);
    }

    public void reset(String string2, String string3) {
        this.mTag = string2;
        this.mLabel = string3;
        this.reset();
    }
}

