/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrintManager;
import java.util.Objects;

public final class PrintJob {
    private PrintJobInfo mCachedInfo;
    private final PrintManager mPrintManager;

    PrintJob(PrintJobInfo printJobInfo, PrintManager printManager) {
        this.mCachedInfo = printJobInfo;
        this.mPrintManager = printManager;
    }

    private boolean isInImmutableState() {
        int n = this.mCachedInfo.getState();
        boolean bl = n == 5 || n == 7;
        return bl;
    }

    public void cancel() {
        int n = this.getInfo().getState();
        if (n == 2 || n == 3 || n == 4 || n == 6) {
            this.mPrintManager.cancelPrintJob(this.mCachedInfo.getId());
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (PrintJob)object;
        return Objects.equals(this.mCachedInfo.getId(), ((PrintJob)object).mCachedInfo.getId());
    }

    public PrintJobId getId() {
        return this.mCachedInfo.getId();
    }

    public PrintJobInfo getInfo() {
        if (this.isInImmutableState()) {
            return this.mCachedInfo;
        }
        PrintJobInfo printJobInfo = this.mPrintManager.getPrintJobInfo(this.mCachedInfo.getId());
        if (printJobInfo != null) {
            this.mCachedInfo = printJobInfo;
        }
        return this.mCachedInfo;
    }

    public int hashCode() {
        PrintJobId printJobId = this.mCachedInfo.getId();
        if (printJobId == null) {
            return 0;
        }
        return printJobId.hashCode();
    }

    public boolean isBlocked() {
        boolean bl = this.getInfo().getState() == 4;
        return bl;
    }

    public boolean isCancelled() {
        boolean bl = this.getInfo().getState() == 7;
        return bl;
    }

    public boolean isCompleted() {
        boolean bl = this.getInfo().getState() == 5;
        return bl;
    }

    public boolean isFailed() {
        boolean bl = this.getInfo().getState() == 6;
        return bl;
    }

    public boolean isQueued() {
        boolean bl = this.getInfo().getState() == 2;
        return bl;
    }

    public boolean isStarted() {
        boolean bl = this.getInfo().getState() == 3;
        return bl;
    }

    public void restart() {
        if (this.isFailed()) {
            this.mPrintManager.restartPrintJob(this.mCachedInfo.getId());
        }
    }
}

