/*
 * Decompiled with CFR 0.145.
 */
package android.printservice;

import android.content.Context;
import android.os.RemoteException;
import android.print.PrintDocumentInfo;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.printservice.IPrintServiceClient;
import android.printservice.PrintDocument;
import android.printservice.PrintService;
import android.util.Log;

public final class PrintJob {
    private static final String LOG_TAG = "PrintJob";
    private PrintJobInfo mCachedInfo;
    private final Context mContext;
    private final PrintDocument mDocument;
    private final IPrintServiceClient mPrintServiceClient;

    PrintJob(Context context, PrintJobInfo printJobInfo, IPrintServiceClient iPrintServiceClient) {
        this.mContext = context;
        this.mCachedInfo = printJobInfo;
        this.mPrintServiceClient = iPrintServiceClient;
        this.mDocument = new PrintDocument(this.mCachedInfo.getId(), iPrintServiceClient, printJobInfo.getDocumentInfo());
    }

    private boolean isInImmutableState() {
        int n = this.mCachedInfo.getState();
        boolean bl = n == 5 || n == 7 || n == 6;
        return bl;
    }

    private boolean setState(int n, String string2) {
        try {
            if (this.mPrintServiceClient.setPrintJobState(this.mCachedInfo.getId(), n, string2)) {
                this.mCachedInfo.setState(n);
                this.mCachedInfo.setStatus(string2);
                return true;
            }
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error setting the state of job: ");
            stringBuilder.append(this.mCachedInfo.getId());
            Log.e(LOG_TAG, stringBuilder.toString(), remoteException);
        }
        return false;
    }

    public boolean block(String string2) {
        PrintService.throwIfNotCalledOnMainThread();
        int n = this.getInfo().getState();
        if (n != 3 && n != 4) {
            return false;
        }
        return this.setState(4, string2);
    }

    public boolean cancel() {
        PrintService.throwIfNotCalledOnMainThread();
        if (!this.isInImmutableState()) {
            return this.setState(7, null);
        }
        return false;
    }

    public boolean complete() {
        PrintService.throwIfNotCalledOnMainThread();
        if (this.isStarted()) {
            return this.setState(5, null);
        }
        return false;
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
        return this.mCachedInfo.getId().equals(((PrintJob)object).mCachedInfo.getId());
    }

    public boolean fail(String string2) {
        PrintService.throwIfNotCalledOnMainThread();
        if (!this.isInImmutableState()) {
            return this.setState(6, string2);
        }
        return false;
    }

    public int getAdvancedIntOption(String string2) {
        PrintService.throwIfNotCalledOnMainThread();
        return this.getInfo().getAdvancedIntOption(string2);
    }

    public String getAdvancedStringOption(String string2) {
        PrintService.throwIfNotCalledOnMainThread();
        return this.getInfo().getAdvancedStringOption(string2);
    }

    public PrintDocument getDocument() {
        PrintService.throwIfNotCalledOnMainThread();
        return this.mDocument;
    }

    public PrintJobId getId() {
        PrintService.throwIfNotCalledOnMainThread();
        return this.mCachedInfo.getId();
    }

    public PrintJobInfo getInfo() {
        PrintService.throwIfNotCalledOnMainThread();
        if (this.isInImmutableState()) {
            return this.mCachedInfo;
        }
        PrintJobInfo printJobInfo = null;
        try {
            PrintJobInfo printJobInfo2;
            printJobInfo = printJobInfo2 = this.mPrintServiceClient.getPrintJobInfo(this.mCachedInfo.getId());
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't get info for job: ");
            stringBuilder.append(this.mCachedInfo.getId());
            Log.e(LOG_TAG, stringBuilder.toString(), remoteException);
        }
        if (printJobInfo != null) {
            this.mCachedInfo = printJobInfo;
        }
        return this.mCachedInfo;
    }

    public String getTag() {
        PrintService.throwIfNotCalledOnMainThread();
        return this.getInfo().getTag();
    }

    public boolean hasAdvancedOption(String string2) {
        PrintService.throwIfNotCalledOnMainThread();
        return this.getInfo().hasAdvancedOption(string2);
    }

    public int hashCode() {
        return this.mCachedInfo.getId().hashCode();
    }

    public boolean isBlocked() {
        PrintService.throwIfNotCalledOnMainThread();
        boolean bl = this.getInfo().getState() == 4;
        return bl;
    }

    public boolean isCancelled() {
        PrintService.throwIfNotCalledOnMainThread();
        boolean bl = this.getInfo().getState() == 7;
        return bl;
    }

    public boolean isCompleted() {
        PrintService.throwIfNotCalledOnMainThread();
        boolean bl = this.getInfo().getState() == 5;
        return bl;
    }

    public boolean isFailed() {
        PrintService.throwIfNotCalledOnMainThread();
        boolean bl = this.getInfo().getState() == 6;
        return bl;
    }

    public boolean isQueued() {
        PrintService.throwIfNotCalledOnMainThread();
        boolean bl = this.getInfo().getState() == 2;
        return bl;
    }

    public boolean isStarted() {
        PrintService.throwIfNotCalledOnMainThread();
        boolean bl = this.getInfo().getState() == 3;
        return bl;
    }

    public void setProgress(float f) {
        PrintService.throwIfNotCalledOnMainThread();
        try {
            this.mPrintServiceClient.setProgress(this.mCachedInfo.getId(), f);
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error setting progress for job: ");
            stringBuilder.append(this.mCachedInfo.getId());
            Log.e(LOG_TAG, stringBuilder.toString(), remoteException);
        }
    }

    public void setStatus(int n) {
        PrintService.throwIfNotCalledOnMainThread();
        try {
            this.mPrintServiceClient.setStatusRes(this.mCachedInfo.getId(), n, this.mContext.getPackageName());
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error setting status for job: ");
            stringBuilder.append(this.mCachedInfo.getId());
            Log.e(LOG_TAG, stringBuilder.toString(), remoteException);
        }
    }

    public void setStatus(CharSequence charSequence) {
        PrintService.throwIfNotCalledOnMainThread();
        try {
            this.mPrintServiceClient.setStatus(this.mCachedInfo.getId(), charSequence);
        }
        catch (RemoteException remoteException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Error setting status for job: ");
            ((StringBuilder)charSequence).append(this.mCachedInfo.getId());
            Log.e(LOG_TAG, ((StringBuilder)charSequence).toString(), remoteException);
        }
    }

    public boolean setTag(String string2) {
        PrintService.throwIfNotCalledOnMainThread();
        if (this.isInImmutableState()) {
            return false;
        }
        try {
            boolean bl = this.mPrintServiceClient.setPrintJobTag(this.mCachedInfo.getId(), string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error setting tag for job: ");
            stringBuilder.append(this.mCachedInfo.getId());
            Log.e(LOG_TAG, stringBuilder.toString(), remoteException);
            return false;
        }
    }

    public boolean start() {
        PrintService.throwIfNotCalledOnMainThread();
        int n = this.getInfo().getState();
        if (n != 2 && n != 4) {
            return false;
        }
        return this.setState(3, null);
    }
}

