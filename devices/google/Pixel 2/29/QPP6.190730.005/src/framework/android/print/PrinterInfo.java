/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.print.PrintManager;
import android.print.PrinterCapabilitiesInfo;
import android.print.PrinterId;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PrinterInfo
implements Parcelable {
    public static final Parcelable.Creator<PrinterInfo> CREATOR = new Parcelable.Creator<PrinterInfo>(){

        @Override
        public PrinterInfo createFromParcel(Parcel parcel) {
            return new PrinterInfo(parcel);
        }

        public PrinterInfo[] newArray(int n) {
            return new PrinterInfo[n];
        }
    };
    public static final int STATUS_BUSY = 2;
    public static final int STATUS_IDLE = 1;
    public static final int STATUS_UNAVAILABLE = 3;
    private final PrinterCapabilitiesInfo mCapabilities;
    private final int mCustomPrinterIconGen;
    private final String mDescription;
    private final boolean mHasCustomPrinterIcon;
    private final int mIconResourceId;
    private final PrinterId mId;
    private final PendingIntent mInfoIntent;
    private final String mName;
    private final int mStatus;

    private PrinterInfo(Parcel parcel) {
        this.mId = PrinterInfo.checkPrinterId((PrinterId)parcel.readParcelable(null));
        this.mName = PrinterInfo.checkName(parcel.readString());
        this.mStatus = PrinterInfo.checkStatus(parcel.readInt());
        this.mDescription = parcel.readString();
        this.mCapabilities = (PrinterCapabilitiesInfo)parcel.readParcelable(null);
        this.mIconResourceId = parcel.readInt();
        boolean bl = parcel.readByte() != 0;
        this.mHasCustomPrinterIcon = bl;
        this.mCustomPrinterIconGen = parcel.readInt();
        this.mInfoIntent = (PendingIntent)parcel.readParcelable(null);
    }

    private PrinterInfo(PrinterId printerId, String string2, int n, int n2, boolean bl, String string3, PendingIntent pendingIntent, PrinterCapabilitiesInfo printerCapabilitiesInfo, int n3) {
        this.mId = printerId;
        this.mName = string2;
        this.mStatus = n;
        this.mIconResourceId = n2;
        this.mHasCustomPrinterIcon = bl;
        this.mDescription = string3;
        this.mInfoIntent = pendingIntent;
        this.mCapabilities = printerCapabilitiesInfo;
        this.mCustomPrinterIconGen = n3;
    }

    private static String checkName(String string2) {
        return Preconditions.checkStringNotEmpty(string2, "name cannot be empty.");
    }

    private static PrinterId checkPrinterId(PrinterId printerId) {
        return Preconditions.checkNotNull(printerId, "printerId cannot be null.");
    }

    private static int checkStatus(int n) {
        if (n != 1 && n != 2 && n != 3) {
            throw new IllegalArgumentException("status is invalid.");
        }
        return n;
    }

    @Override
    public int describeContents() {
        return 0;
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
        if (!this.equalsIgnoringStatus((PrinterInfo)(object = (PrinterInfo)object))) {
            return false;
        }
        return this.mStatus == ((PrinterInfo)object).mStatus;
    }

    public boolean equalsIgnoringStatus(PrinterInfo printerInfo) {
        if (!this.mId.equals(printerInfo.mId)) {
            return false;
        }
        if (!this.mName.equals(printerInfo.mName)) {
            return false;
        }
        if (!TextUtils.equals(this.mDescription, printerInfo.mDescription)) {
            return false;
        }
        Parcelable parcelable = this.mCapabilities;
        if (parcelable == null ? printerInfo.mCapabilities != null : !((PrinterCapabilitiesInfo)parcelable).equals(printerInfo.mCapabilities)) {
            return false;
        }
        if (this.mIconResourceId != printerInfo.mIconResourceId) {
            return false;
        }
        if (this.mHasCustomPrinterIcon != printerInfo.mHasCustomPrinterIcon) {
            return false;
        }
        if (this.mCustomPrinterIconGen != printerInfo.mCustomPrinterIconGen) {
            return false;
        }
        parcelable = this.mInfoIntent;
        return !(parcelable == null ? printerInfo.mInfoIntent != null : !((PendingIntent)parcelable).equals(printerInfo.mInfoIntent));
    }

    public PrinterCapabilitiesInfo getCapabilities() {
        return this.mCapabilities;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public boolean getHasCustomPrinterIcon() {
        return this.mHasCustomPrinterIcon;
    }

    public PrinterId getId() {
        return this.mId;
    }

    public PendingIntent getInfoIntent() {
        return this.mInfoIntent;
    }

    public String getName() {
        return this.mName;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int hashCode() {
        int n = this.mId.hashCode();
        int n2 = this.mName.hashCode();
        int n3 = this.mStatus;
        Object object = this.mDescription;
        int n4 = 0;
        int n5 = object != null ? ((String)object).hashCode() : 0;
        object = this.mCapabilities;
        int n6 = object != null ? ((PrinterCapabilitiesInfo)object).hashCode() : 0;
        int n7 = this.mIconResourceId;
        int n8 = this.mHasCustomPrinterIcon;
        int n9 = this.mCustomPrinterIconGen;
        object = this.mInfoIntent;
        if (object != null) {
            n4 = ((PendingIntent)object).hashCode();
        }
        return ((((((((1 * 31 + n) * 31 + n2) * 31 + n3) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n4;
    }

    public Drawable loadIcon(Context object) {
        Parcelable parcelable;
        Object object2 = null;
        PackageManager packageManager = ((Context)object).getPackageManager();
        Object object3 = object2;
        if (this.mHasCustomPrinterIcon) {
            parcelable = ((PrintManager)((Context)object).getSystemService("print")).getCustomPrinterIcon(this.mId);
            object3 = object2;
            if (parcelable != null) {
                object3 = ((Icon)parcelable).loadDrawable((Context)object);
            }
        }
        object2 = object3;
        if (object3 == null) {
            block10 : {
                object2 = object3;
                String string2 = this.mId.getServiceName().getPackageName();
                object2 = object3;
                parcelable = packageManager.getPackageInfo((String)string2, (int)0).applicationInfo;
                object = object3;
                object2 = object3;
                if (this.mIconResourceId == 0) break block10;
                object2 = object3;
                try {
                    object = packageManager.getDrawable(string2, this.mIconResourceId, (ApplicationInfo)parcelable);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    // empty catch block
                }
            }
            object3 = object;
            if (object == null) {
                object2 = object;
                object3 = ((PackageItemInfo)((Object)parcelable)).loadIcon(packageManager);
            }
            object2 = object3;
        }
        return object2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PrinterInfo{");
        stringBuilder.append("id=");
        stringBuilder.append(this.mId);
        stringBuilder.append(", name=");
        stringBuilder.append(this.mName);
        stringBuilder.append(", status=");
        stringBuilder.append(this.mStatus);
        stringBuilder.append(", description=");
        stringBuilder.append(this.mDescription);
        stringBuilder.append(", capabilities=");
        stringBuilder.append(this.mCapabilities);
        stringBuilder.append(", iconResId=");
        stringBuilder.append(this.mIconResourceId);
        stringBuilder.append(", hasCustomPrinterIcon=");
        stringBuilder.append(this.mHasCustomPrinterIcon);
        stringBuilder.append(", customPrinterIconGen=");
        stringBuilder.append(this.mCustomPrinterIconGen);
        stringBuilder.append(", infoIntent=");
        stringBuilder.append(this.mInfoIntent);
        stringBuilder.append("\"}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mId, n);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mStatus);
        parcel.writeString(this.mDescription);
        parcel.writeParcelable(this.mCapabilities, n);
        parcel.writeInt(this.mIconResourceId);
        parcel.writeByte((byte)(this.mHasCustomPrinterIcon ? 1 : 0));
        parcel.writeInt(this.mCustomPrinterIconGen);
        parcel.writeParcelable(this.mInfoIntent, n);
    }

    public static final class Builder {
        private PrinterCapabilitiesInfo mCapabilities;
        private int mCustomPrinterIconGen;
        private String mDescription;
        private boolean mHasCustomPrinterIcon;
        private int mIconResourceId;
        private PendingIntent mInfoIntent;
        private String mName;
        private PrinterId mPrinterId;
        private int mStatus;

        public Builder(PrinterId printerId, String string2, int n) {
            this.mPrinterId = PrinterInfo.checkPrinterId(printerId);
            this.mName = PrinterInfo.checkName(string2);
            this.mStatus = PrinterInfo.checkStatus(n);
        }

        public Builder(PrinterInfo printerInfo) {
            this.mPrinterId = printerInfo.mId;
            this.mName = printerInfo.mName;
            this.mStatus = printerInfo.mStatus;
            this.mIconResourceId = printerInfo.mIconResourceId;
            this.mHasCustomPrinterIcon = printerInfo.mHasCustomPrinterIcon;
            this.mDescription = printerInfo.mDescription;
            this.mInfoIntent = printerInfo.mInfoIntent;
            this.mCapabilities = printerInfo.mCapabilities;
            this.mCustomPrinterIconGen = printerInfo.mCustomPrinterIconGen;
        }

        public PrinterInfo build() {
            return new PrinterInfo(this.mPrinterId, this.mName, this.mStatus, this.mIconResourceId, this.mHasCustomPrinterIcon, this.mDescription, this.mInfoIntent, this.mCapabilities, this.mCustomPrinterIconGen);
        }

        public Builder incCustomPrinterIconGen() {
            ++this.mCustomPrinterIconGen;
            return this;
        }

        public Builder setCapabilities(PrinterCapabilitiesInfo printerCapabilitiesInfo) {
            this.mCapabilities = printerCapabilitiesInfo;
            return this;
        }

        public Builder setDescription(String string2) {
            this.mDescription = string2;
            return this;
        }

        public Builder setHasCustomPrinterIcon(boolean bl) {
            this.mHasCustomPrinterIcon = bl;
            return this;
        }

        public Builder setIconResourceId(int n) {
            this.mIconResourceId = Preconditions.checkArgumentNonnegative(n, "iconResourceId can't be negative");
            return this;
        }

        public Builder setInfoIntent(PendingIntent pendingIntent) {
            this.mInfoIntent = pendingIntent;
            return this;
        }

        public Builder setName(String string2) {
            this.mName = PrinterInfo.checkName(string2);
            return this;
        }

        public Builder setStatus(int n) {
            this.mStatus = PrinterInfo.checkStatus(n);
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Status {
    }

}

