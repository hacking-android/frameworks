/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Printer;

public class ComponentInfo
extends PackageItemInfo {
    public ApplicationInfo applicationInfo;
    public int descriptionRes;
    public boolean directBootAware;
    public boolean enabled;
    @Deprecated
    public boolean encryptionAware;
    public boolean exported;
    public String processName;
    public String splitName;

    public ComponentInfo() {
        this.enabled = true;
        this.exported = false;
        this.directBootAware = false;
        this.encryptionAware = false;
    }

    public ComponentInfo(ComponentInfo componentInfo) {
        super(componentInfo);
        boolean bl;
        this.enabled = true;
        this.exported = false;
        this.directBootAware = false;
        this.encryptionAware = false;
        this.applicationInfo = componentInfo.applicationInfo;
        this.processName = componentInfo.processName;
        this.splitName = componentInfo.splitName;
        this.descriptionRes = componentInfo.descriptionRes;
        this.enabled = componentInfo.enabled;
        this.exported = componentInfo.exported;
        this.directBootAware = bl = componentInfo.directBootAware;
        this.encryptionAware = bl;
    }

    protected ComponentInfo(Parcel parcel) {
        super(parcel);
        boolean bl = true;
        this.enabled = true;
        this.exported = false;
        this.directBootAware = false;
        this.encryptionAware = false;
        boolean bl2 = parcel.readInt() != 0;
        if (bl2) {
            this.applicationInfo = ApplicationInfo.CREATOR.createFromParcel(parcel);
        }
        this.processName = parcel.readString();
        this.splitName = parcel.readString();
        this.descriptionRes = parcel.readInt();
        boolean bl3 = parcel.readInt() != 0;
        this.enabled = bl3;
        bl3 = parcel.readInt() != 0;
        this.exported = bl3;
        bl3 = parcel.readInt() != 0 ? bl : false;
        this.directBootAware = bl3;
        this.encryptionAware = bl3;
    }

    @Override
    protected void dumpBack(Printer printer, String string2) {
        this.dumpBack(printer, string2, 3);
    }

    void dumpBack(Printer printer, String string2, int n) {
        if ((n & 2) != 0) {
            if (this.applicationInfo != null) {
                Object object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("ApplicationInfo:");
                printer.println(((StringBuilder)object).toString());
                object = this.applicationInfo;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append("  ");
                ((ApplicationInfo)object).dump(printer, stringBuilder.toString(), n);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append("ApplicationInfo: null");
                printer.println(stringBuilder.toString());
            }
        }
        super.dumpBack(printer, string2);
    }

    @Override
    protected void dumpFront(Printer printer, String string2) {
        StringBuilder stringBuilder;
        super.dumpFront(printer, string2);
        if (this.processName != null && !this.packageName.equals(this.processName)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("processName=");
            stringBuilder.append(this.processName);
            printer.println(stringBuilder.toString());
        }
        if (this.splitName != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("splitName=");
            stringBuilder.append(this.splitName);
            printer.println(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("enabled=");
        stringBuilder.append(this.enabled);
        stringBuilder.append(" exported=");
        stringBuilder.append(this.exported);
        stringBuilder.append(" directBootAware=");
        stringBuilder.append(this.directBootAware);
        printer.println(stringBuilder.toString());
        if (this.descriptionRes != 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("description=");
            stringBuilder.append(this.descriptionRes);
            printer.println(stringBuilder.toString());
        }
    }

    @Override
    protected ApplicationInfo getApplicationInfo() {
        return this.applicationInfo;
    }

    public final int getBannerResource() {
        int n = this.banner != 0 ? this.banner : this.applicationInfo.banner;
        return n;
    }

    @UnsupportedAppUsage
    public ComponentName getComponentName() {
        return new ComponentName(this.packageName, this.name);
    }

    public final int getIconResource() {
        int n = this.icon != 0 ? this.icon : this.applicationInfo.icon;
        return n;
    }

    public final int getLogoResource() {
        int n = this.logo != 0 ? this.logo : this.applicationInfo.logo;
        return n;
    }

    public boolean isEnabled() {
        boolean bl = this.enabled && this.applicationInfo.enabled;
        return bl;
    }

    @Override
    protected Drawable loadDefaultBanner(PackageManager packageManager) {
        return this.applicationInfo.loadBanner(packageManager);
    }

    @Override
    public Drawable loadDefaultIcon(PackageManager packageManager) {
        return this.applicationInfo.loadIcon(packageManager);
    }

    @Override
    protected Drawable loadDefaultLogo(PackageManager packageManager) {
        return this.applicationInfo.loadLogo(packageManager);
    }

    @Override
    public CharSequence loadUnsafeLabel(PackageManager object) {
        CharSequence charSequence;
        if (this.nonLocalizedLabel != null) {
            return this.nonLocalizedLabel;
        }
        ApplicationInfo applicationInfo = this.applicationInfo;
        if (this.labelRes != 0 && (charSequence = ((PackageManager)object).getText(this.packageName, this.labelRes, applicationInfo)) != null) {
            return charSequence;
        }
        if (applicationInfo.nonLocalizedLabel != null) {
            return applicationInfo.nonLocalizedLabel;
        }
        if (applicationInfo.labelRes != 0 && (object = ((PackageManager)object).getText(this.packageName, applicationInfo.labelRes, applicationInfo)) != null) {
            return object;
        }
        return this.name;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        if ((n & 2) != 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.applicationInfo.writeToParcel(parcel, n);
        }
        parcel.writeString(this.processName);
        parcel.writeString(this.splitName);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt((int)this.enabled);
        parcel.writeInt((int)this.exported);
        parcel.writeInt((int)this.directBootAware);
    }
}

