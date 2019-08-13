/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentInfo;
import android.print.PrintJobId;
import android.print.PrinterId;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public final class PrintJobInfo
implements Parcelable {
    public static final Parcelable.Creator<PrintJobInfo> CREATOR = new Parcelable.Creator<PrintJobInfo>(){

        @Override
        public PrintJobInfo createFromParcel(Parcel parcel) {
            return new PrintJobInfo(parcel);
        }

        public PrintJobInfo[] newArray(int n) {
            return new PrintJobInfo[n];
        }
    };
    public static final int STATE_ANY = -1;
    public static final int STATE_ANY_ACTIVE = -3;
    public static final int STATE_ANY_SCHEDULED = -4;
    public static final int STATE_ANY_VISIBLE_TO_CLIENTS = -2;
    public static final int STATE_BLOCKED = 4;
    public static final int STATE_CANCELED = 7;
    public static final int STATE_COMPLETED = 5;
    public static final int STATE_CREATED = 1;
    public static final int STATE_FAILED = 6;
    public static final int STATE_QUEUED = 2;
    public static final int STATE_STARTED = 3;
    private Bundle mAdvancedOptions;
    private int mAppId;
    private PrintAttributes mAttributes;
    private boolean mCanceling;
    private int mCopies;
    private long mCreationTime;
    private PrintDocumentInfo mDocumentInfo;
    private PrintJobId mId;
    private String mLabel;
    private PageRange[] mPageRanges;
    private PrinterId mPrinterId;
    private String mPrinterName;
    private float mProgress;
    private int mState;
    private CharSequence mStatus;
    private int mStatusRes;
    private CharSequence mStatusResAppPackageName;
    private String mTag;

    public PrintJobInfo() {
        this.mProgress = -1.0f;
    }

    private PrintJobInfo(Parcel object) {
        this.mId = (PrintJobId)((Parcel)object).readParcelable(null);
        this.mLabel = ((Parcel)object).readString();
        this.mPrinterId = (PrinterId)((Parcel)object).readParcelable(null);
        this.mPrinterName = ((Parcel)object).readString();
        this.mState = ((Parcel)object).readInt();
        this.mAppId = ((Parcel)object).readInt();
        this.mTag = ((Parcel)object).readString();
        this.mCreationTime = ((Parcel)object).readLong();
        this.mCopies = ((Parcel)object).readInt();
        Parcelable[] arrparcelable = ((Parcel)object).readParcelableArray(null);
        if (arrparcelable != null) {
            this.mPageRanges = new PageRange[arrparcelable.length];
            for (int i = 0; i < arrparcelable.length; ++i) {
                this.mPageRanges[i] = (PageRange)arrparcelable[i];
            }
        }
        this.mAttributes = (PrintAttributes)((Parcel)object).readParcelable(null);
        this.mDocumentInfo = (PrintDocumentInfo)((Parcel)object).readParcelable(null);
        this.mProgress = ((Parcel)object).readFloat();
        this.mStatus = ((Parcel)object).readCharSequence();
        this.mStatusRes = ((Parcel)object).readInt();
        this.mStatusResAppPackageName = ((Parcel)object).readCharSequence();
        boolean bl = ((Parcel)object).readInt() == 1;
        this.mCanceling = bl;
        this.mAdvancedOptions = ((Parcel)object).readBundle();
        object = this.mAdvancedOptions;
        if (object != null) {
            Preconditions.checkArgument(((BaseBundle)object).containsKey(null) ^ true);
        }
    }

    public PrintJobInfo(PrintJobInfo printJobInfo) {
        this.mId = printJobInfo.mId;
        this.mLabel = printJobInfo.mLabel;
        this.mPrinterId = printJobInfo.mPrinterId;
        this.mPrinterName = printJobInfo.mPrinterName;
        this.mState = printJobInfo.mState;
        this.mAppId = printJobInfo.mAppId;
        this.mTag = printJobInfo.mTag;
        this.mCreationTime = printJobInfo.mCreationTime;
        this.mCopies = printJobInfo.mCopies;
        this.mPageRanges = printJobInfo.mPageRanges;
        this.mAttributes = printJobInfo.mAttributes;
        this.mDocumentInfo = printJobInfo.mDocumentInfo;
        this.mProgress = printJobInfo.mProgress;
        this.mStatus = printJobInfo.mStatus;
        this.mStatusRes = printJobInfo.mStatusRes;
        this.mStatusResAppPackageName = printJobInfo.mStatusResAppPackageName;
        this.mCanceling = printJobInfo.mCanceling;
        this.mAdvancedOptions = printJobInfo.mAdvancedOptions;
    }

    public static String stateToString(int n) {
        switch (n) {
            default: {
                return "STATE_UNKNOWN";
            }
            case 7: {
                return "STATE_CANCELED";
            }
            case 6: {
                return "STATE_FAILED";
            }
            case 5: {
                return "STATE_COMPLETED";
            }
            case 4: {
                return "STATE_BLOCKED";
            }
            case 3: {
                return "STATE_STARTED";
            }
            case 2: {
                return "STATE_QUEUED";
            }
            case 1: 
        }
        return "STATE_CREATED";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAdvancedIntOption(String string2) {
        Bundle bundle = this.mAdvancedOptions;
        if (bundle != null) {
            return bundle.getInt(string2);
        }
        return 0;
    }

    @UnsupportedAppUsage
    public Bundle getAdvancedOptions() {
        return this.mAdvancedOptions;
    }

    public String getAdvancedStringOption(String string2) {
        Bundle bundle = this.mAdvancedOptions;
        if (bundle != null) {
            return bundle.getString(string2);
        }
        return null;
    }

    public int getAppId() {
        return this.mAppId;
    }

    public PrintAttributes getAttributes() {
        return this.mAttributes;
    }

    public int getCopies() {
        return this.mCopies;
    }

    public long getCreationTime() {
        return this.mCreationTime;
    }

    @UnsupportedAppUsage
    public PrintDocumentInfo getDocumentInfo() {
        return this.mDocumentInfo;
    }

    public PrintJobId getId() {
        return this.mId;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public PageRange[] getPages() {
        return this.mPageRanges;
    }

    public PrinterId getPrinterId() {
        return this.mPrinterId;
    }

    public String getPrinterName() {
        return this.mPrinterName;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public int getState() {
        return this.mState;
    }

    public CharSequence getStatus(PackageManager object) {
        if (this.mStatusRes == 0) {
            return this.mStatus;
        }
        try {
            object = ((PackageManager)object).getResourcesForApplication(this.mStatusResAppPackageName.toString()).getString(this.mStatusRes);
            return object;
        }
        catch (PackageManager.NameNotFoundException | Resources.NotFoundException exception) {
            return null;
        }
    }

    public String getTag() {
        return this.mTag;
    }

    public boolean hasAdvancedOption(String string2) {
        Bundle bundle = this.mAdvancedOptions;
        boolean bl = bundle != null && bundle.containsKey(string2);
        return bl;
    }

    public boolean isCancelling() {
        return this.mCanceling;
    }

    public void setAdvancedOptions(Bundle bundle) {
        this.mAdvancedOptions = bundle;
    }

    public void setAppId(int n) {
        this.mAppId = n;
    }

    public void setAttributes(PrintAttributes printAttributes) {
        this.mAttributes = printAttributes;
    }

    public void setCancelling(boolean bl) {
        this.mCanceling = bl;
    }

    public void setCopies(int n) {
        if (n >= 1) {
            this.mCopies = n;
            return;
        }
        throw new IllegalArgumentException("Copies must be more than one.");
    }

    public void setCreationTime(long l) {
        if (l >= 0L) {
            this.mCreationTime = l;
            return;
        }
        throw new IllegalArgumentException("creationTime must be non-negative.");
    }

    public void setDocumentInfo(PrintDocumentInfo printDocumentInfo) {
        this.mDocumentInfo = printDocumentInfo;
    }

    public void setId(PrintJobId printJobId) {
        this.mId = printJobId;
    }

    public void setLabel(String string2) {
        this.mLabel = string2;
    }

    public void setPages(PageRange[] arrpageRange) {
        this.mPageRanges = arrpageRange;
    }

    public void setPrinterId(PrinterId printerId) {
        this.mPrinterId = printerId;
    }

    public void setPrinterName(String string2) {
        this.mPrinterName = string2;
    }

    public void setProgress(float f) {
        Preconditions.checkArgumentInRange(f, 0.0f, 1.0f, "progress");
        this.mProgress = f;
    }

    public void setState(int n) {
        this.mState = n;
    }

    public void setStatus(int n, CharSequence charSequence) {
        this.mStatus = null;
        this.mStatusRes = n;
        this.mStatusResAppPackageName = charSequence;
    }

    public void setStatus(CharSequence charSequence) {
        this.mStatusRes = 0;
        this.mStatusResAppPackageName = null;
        this.mStatus = charSequence;
    }

    public void setTag(String string2) {
        this.mTag = string2;
    }

    public boolean shouldStayAwake() {
        int n;
        boolean bl = this.mCanceling || (n = this.mState) == 3 || n == 2;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PrintJobInfo{");
        stringBuilder.append("label: ");
        stringBuilder.append(this.mLabel);
        stringBuilder.append(", id: ");
        stringBuilder.append(this.mId);
        stringBuilder.append(", state: ");
        stringBuilder.append(PrintJobInfo.stateToString(this.mState));
        Object object = new StringBuilder();
        ((StringBuilder)object).append(", printer: ");
        ((StringBuilder)object).append(this.mPrinterId);
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder.append(", tag: ");
        stringBuilder.append(this.mTag);
        object = new StringBuilder();
        ((StringBuilder)object).append(", creationTime: ");
        ((StringBuilder)object).append(this.mCreationTime);
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder.append(", copies: ");
        stringBuilder.append(this.mCopies);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", attributes: ");
        object = this.mAttributes;
        Object var4_4 = null;
        object = object != null ? ((PrintAttributes)object).toString() : null;
        stringBuilder2.append((String)object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", documentInfo: ");
        object = this.mDocumentInfo;
        object = object != null ? ((PrintDocumentInfo)object).toString() : null;
        stringBuilder2.append((String)object);
        stringBuilder.append(stringBuilder2.toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", cancelling: ");
        ((StringBuilder)object).append(this.mCanceling);
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", pages: ");
        object = this.mPageRanges;
        object = object != null ? Arrays.toString((Object[])object) : null;
        stringBuilder2.append((String)object);
        stringBuilder.append(stringBuilder2.toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", hasAdvancedOptions: ");
        boolean bl = this.mAdvancedOptions != null;
        ((StringBuilder)object).append(bl);
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", progress: ");
        ((StringBuilder)object).append(this.mProgress);
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", status: ");
        object = this.mStatus;
        object = object != null ? object.toString() : null;
        stringBuilder2.append((String)object);
        stringBuilder.append(stringBuilder2.toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", statusRes: ");
        ((StringBuilder)object).append(this.mStatusRes);
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", statusResAppPackageName: ");
        object = this.mStatusResAppPackageName;
        object = object != null ? object.toString() : var4_4;
        stringBuilder2.append((String)object);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mId, n);
        parcel.writeString(this.mLabel);
        parcel.writeParcelable(this.mPrinterId, n);
        parcel.writeString(this.mPrinterName);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mAppId);
        parcel.writeString(this.mTag);
        parcel.writeLong(this.mCreationTime);
        parcel.writeInt(this.mCopies);
        parcel.writeParcelableArray((Parcelable[])this.mPageRanges, n);
        parcel.writeParcelable(this.mAttributes, n);
        parcel.writeParcelable(this.mDocumentInfo, 0);
        parcel.writeFloat(this.mProgress);
        parcel.writeCharSequence(this.mStatus);
        parcel.writeInt(this.mStatusRes);
        parcel.writeCharSequence(this.mStatusResAppPackageName);
        parcel.writeInt((int)this.mCanceling);
        parcel.writeBundle(this.mAdvancedOptions);
    }

    public static final class Builder {
        private final PrintJobInfo mPrototype;

        public Builder(PrintJobInfo printJobInfo) {
            printJobInfo = printJobInfo != null ? new PrintJobInfo(printJobInfo) : new PrintJobInfo();
            this.mPrototype = printJobInfo;
        }

        public PrintJobInfo build() {
            return this.mPrototype;
        }

        public void putAdvancedOption(String string2, int n) {
            if (this.mPrototype.mAdvancedOptions == null) {
                this.mPrototype.mAdvancedOptions = new Bundle();
            }
            this.mPrototype.mAdvancedOptions.putInt(string2, n);
        }

        public void putAdvancedOption(String string2, String string3) {
            Preconditions.checkNotNull(string2, "key cannot be null");
            if (this.mPrototype.mAdvancedOptions == null) {
                this.mPrototype.mAdvancedOptions = new Bundle();
            }
            this.mPrototype.mAdvancedOptions.putString(string2, string3);
        }

        public void setAttributes(PrintAttributes printAttributes) {
            this.mPrototype.mAttributes = printAttributes;
        }

        public void setCopies(int n) {
            this.mPrototype.mCopies = n;
        }

        public void setPages(PageRange[] arrpageRange) {
            this.mPrototype.mPageRanges = arrpageRange;
        }

        public void setProgress(float f) {
            Preconditions.checkArgumentInRange(f, 0.0f, 1.0f, "progress");
            this.mPrototype.mProgress = f;
        }

        public void setStatus(CharSequence charSequence) {
            this.mPrototype.mStatus = charSequence;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface State {
    }

}

