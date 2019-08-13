/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public final class LocalLog {
    private final Deque<String> mLog;
    private final int mMaxLines;

    @UnsupportedAppUsage
    public LocalLog(int n) {
        this.mMaxLines = Math.max(0, n);
        this.mLog = new ArrayDeque<String>(this.mMaxLines);
    }

    private void append(String string2) {
        synchronized (this) {
            while (this.mLog.size() >= this.mMaxLines) {
                this.mLog.remove();
            }
            this.mLog.add(string2);
            return;
        }
    }

    @UnsupportedAppUsage
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        synchronized (this) {
            this.dump(printWriter);
            return;
        }
    }

    public void dump(PrintWriter printWriter) {
        synchronized (this) {
            Iterator<String> iterator = this.mLog.iterator();
            while (iterator.hasNext()) {
                printWriter.println(iterator.next());
            }
            return;
        }
    }

    @UnsupportedAppUsage
    public void log(String string2) {
        if (this.mMaxLines <= 0) {
            return;
        }
        this.append(String.format("%s - %s", LocalDateTime.now(), string2));
    }

    @UnsupportedAppUsage
    public ReadOnlyLocalLog readOnlyLocalLog() {
        return new ReadOnlyLocalLog(this);
    }

    public void reverseDump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        synchronized (this) {
            this.reverseDump(printWriter);
            return;
        }
    }

    public void reverseDump(PrintWriter printWriter) {
        synchronized (this) {
            Iterator<String> iterator = this.mLog.descendingIterator();
            while (iterator.hasNext()) {
                printWriter.println(iterator.next());
            }
            return;
        }
    }

    public static class ReadOnlyLocalLog {
        private final LocalLog mLog;

        ReadOnlyLocalLog(LocalLog localLog) {
            this.mLog = localLog;
        }

        @UnsupportedAppUsage
        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            this.mLog.dump(printWriter);
        }

        public void dump(PrintWriter printWriter) {
            this.mLog.dump(printWriter);
        }

        public void reverseDump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            this.mLog.reverseDump(printWriter);
        }

        public void reverseDump(PrintWriter printWriter) {
            this.mLog.reverseDump(printWriter);
        }
    }

}

