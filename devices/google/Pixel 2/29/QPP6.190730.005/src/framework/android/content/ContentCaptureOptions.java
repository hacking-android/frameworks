/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.app.ActivityThread;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.Log;
import android.view.contentcapture.ContentCaptureManager;
import com.android.internal.annotations.VisibleForTesting;
import java.io.PrintWriter;

public final class ContentCaptureOptions
implements Parcelable {
    public static final Parcelable.Creator<ContentCaptureOptions> CREATOR;
    private static final String TAG;
    public final int idleFlushingFrequencyMs;
    public final boolean lite;
    public final int logHistorySize;
    public final int loggingLevel;
    public final int maxBufferSize;
    public final int textChangeFlushingFrequencyMs;
    public final ArraySet<ComponentName> whitelistedComponents;

    static {
        TAG = ContentCaptureOptions.class.getSimpleName();
        CREATOR = new Parcelable.Creator<ContentCaptureOptions>(){

            @Override
            public ContentCaptureOptions createFromParcel(Parcel parcel) {
                boolean bl = parcel.readBoolean();
                int n = parcel.readInt();
                if (bl) {
                    return new ContentCaptureOptions(n);
                }
                return new ContentCaptureOptions(n, parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readArraySet(null));
            }

            public ContentCaptureOptions[] newArray(int n) {
                return new ContentCaptureOptions[n];
            }
        };
    }

    public ContentCaptureOptions(int n) {
        this(true, n, 0, 0, 0, 0, null);
    }

    public ContentCaptureOptions(int n, int n2, int n3, int n4, int n5, ArraySet<ComponentName> arraySet) {
        this(false, n, n2, n3, n4, n5, arraySet);
    }

    @VisibleForTesting
    public ContentCaptureOptions(ArraySet<ComponentName> arraySet) {
        this(2, 100, 5000, 1000, 10, arraySet);
    }

    private ContentCaptureOptions(boolean bl, int n, int n2, int n3, int n4, int n5, ArraySet<ComponentName> arraySet) {
        this.lite = bl;
        this.loggingLevel = n;
        this.maxBufferSize = n2;
        this.idleFlushingFrequencyMs = n3;
        this.textChangeFlushingFrequencyMs = n4;
        this.logHistorySize = n5;
        this.whitelistedComponents = arraySet;
    }

    public static ContentCaptureOptions forWhitelistingItself() {
        Object object = ActivityThread.currentActivityThread();
        if (object != null) {
            if ("android.contentcaptureservice.cts".equals(object = ((ActivityThread)object).getApplication().getPackageName())) {
                ContentCaptureOptions contentCaptureOptions = new ContentCaptureOptions(null);
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("forWhitelistingItself(");
                stringBuilder.append((String)object);
                stringBuilder.append("): ");
                stringBuilder.append(contentCaptureOptions);
                Log.i(string2, stringBuilder.toString());
                return contentCaptureOptions;
            }
            String string3 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("forWhitelistingItself(): called by ");
            stringBuilder.append((String)object);
            Log.e(string3, stringBuilder.toString());
            throw new SecurityException("Thou shall not pass!");
        }
        throw new IllegalStateException("No ActivityThread");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dumpShort(PrintWriter printWriter) {
        printWriter.print("logLvl=");
        printWriter.print(this.loggingLevel);
        if (this.lite) {
            printWriter.print(", lite");
            return;
        }
        printWriter.print(", bufferSize=");
        printWriter.print(this.maxBufferSize);
        printWriter.print(", idle=");
        printWriter.print(this.idleFlushingFrequencyMs);
        printWriter.print(", textIdle=");
        printWriter.print(this.textChangeFlushingFrequencyMs);
        printWriter.print(", logSize=");
        printWriter.print(this.logHistorySize);
        if (this.whitelistedComponents != null) {
            printWriter.print(", whitelisted=");
            printWriter.print(this.whitelistedComponents);
        }
    }

    @VisibleForTesting
    public boolean isWhitelisted(Context context) {
        if (this.whitelistedComponents == null) {
            return true;
        }
        Object object = context.getContentCaptureClient();
        if (object == null) {
            object = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("isWhitelisted(): no ContentCaptureClient on ");
            stringBuilder.append(context);
            Log.w((String)object, stringBuilder.toString());
            return false;
        }
        return this.whitelistedComponents.contains(object.contentCaptureClientGetComponentName());
    }

    public String toString() {
        if (this.lite) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ContentCaptureOptions [loggingLevel=");
            stringBuilder.append(this.loggingLevel);
            stringBuilder.append(" (lite)]");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder("ContentCaptureOptions [");
        stringBuilder.append("loggingLevel=");
        stringBuilder.append(this.loggingLevel);
        stringBuilder.append(", maxBufferSize=");
        stringBuilder.append(this.maxBufferSize);
        stringBuilder.append(", idleFlushingFrequencyMs=");
        stringBuilder.append(this.idleFlushingFrequencyMs);
        stringBuilder.append(", textChangeFlushingFrequencyMs=");
        stringBuilder.append(this.textChangeFlushingFrequencyMs);
        stringBuilder.append(", logHistorySize=");
        stringBuilder.append(this.logHistorySize);
        if (this.whitelistedComponents != null) {
            stringBuilder.append(", whitelisted=");
            stringBuilder.append(this.whitelistedComponents);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBoolean(this.lite);
        parcel.writeInt(this.loggingLevel);
        if (this.lite) {
            return;
        }
        parcel.writeInt(this.maxBufferSize);
        parcel.writeInt(this.idleFlushingFrequencyMs);
        parcel.writeInt(this.textChangeFlushingFrequencyMs);
        parcel.writeInt(this.logHistorySize);
        parcel.writeArraySet(this.whitelistedComponents);
    }

}

