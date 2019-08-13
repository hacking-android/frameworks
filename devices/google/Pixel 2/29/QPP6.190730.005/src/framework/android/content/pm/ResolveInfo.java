/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Printer;
import android.util.Slog;
import java.text.Collator;
import java.util.Comparator;

public class ResolveInfo
implements Parcelable {
    public static final Parcelable.Creator<ResolveInfo> CREATOR = new Parcelable.Creator<ResolveInfo>(){

        @Override
        public ResolveInfo createFromParcel(Parcel parcel) {
            return new ResolveInfo(parcel);
        }

        public ResolveInfo[] newArray(int n) {
            return new ResolveInfo[n];
        }
    };
    private static final String TAG = "ResolveInfo";
    public ActivityInfo activityInfo;
    public AuxiliaryResolveInfo auxiliaryInfo;
    public IntentFilter filter;
    @SystemApi
    public boolean handleAllWebDataURI;
    public int icon;
    public int iconResourceId;
    @Deprecated
    public boolean instantAppAvailable;
    public boolean isDefault;
    public boolean isInstantAppAvailable;
    public int labelRes;
    public int match;
    public boolean noResourceId;
    public CharSequence nonLocalizedLabel;
    public int preferredOrder;
    public int priority;
    public ProviderInfo providerInfo;
    public String resolvePackageName;
    public ServiceInfo serviceInfo;
    public int specificIndex = -1;
    @UnsupportedAppUsage
    public boolean system;
    @UnsupportedAppUsage
    public int targetUserId;

    public ResolveInfo() {
        this.targetUserId = -2;
    }

    public ResolveInfo(ResolveInfo resolveInfo) {
        this.activityInfo = resolveInfo.activityInfo;
        this.serviceInfo = resolveInfo.serviceInfo;
        this.providerInfo = resolveInfo.providerInfo;
        this.filter = resolveInfo.filter;
        this.priority = resolveInfo.priority;
        this.preferredOrder = resolveInfo.preferredOrder;
        this.match = resolveInfo.match;
        this.specificIndex = resolveInfo.specificIndex;
        this.labelRes = resolveInfo.labelRes;
        this.nonLocalizedLabel = resolveInfo.nonLocalizedLabel;
        this.icon = resolveInfo.icon;
        this.resolvePackageName = resolveInfo.resolvePackageName;
        this.noResourceId = resolveInfo.noResourceId;
        this.iconResourceId = resolveInfo.iconResourceId;
        this.system = resolveInfo.system;
        this.targetUserId = resolveInfo.targetUserId;
        this.handleAllWebDataURI = resolveInfo.handleAllWebDataURI;
        this.instantAppAvailable = this.isInstantAppAvailable = resolveInfo.isInstantAppAvailable;
    }

    private ResolveInfo(Parcel parcel) {
        this.activityInfo = null;
        this.serviceInfo = null;
        this.providerInfo = null;
        int n = parcel.readInt();
        boolean bl = true;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    Slog.w(TAG, "Missing ComponentInfo!");
                } else {
                    this.providerInfo = ProviderInfo.CREATOR.createFromParcel(parcel);
                }
            } else {
                this.serviceInfo = ServiceInfo.CREATOR.createFromParcel(parcel);
            }
        } else {
            this.activityInfo = ActivityInfo.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.filter = IntentFilter.CREATOR.createFromParcel(parcel);
        }
        this.priority = parcel.readInt();
        this.preferredOrder = parcel.readInt();
        this.match = parcel.readInt();
        this.specificIndex = parcel.readInt();
        this.labelRes = parcel.readInt();
        this.nonLocalizedLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.icon = parcel.readInt();
        this.resolvePackageName = parcel.readString();
        this.targetUserId = parcel.readInt();
        boolean bl2 = parcel.readInt() != 0;
        this.system = bl2;
        bl2 = parcel.readInt() != 0;
        this.noResourceId = bl2;
        this.iconResourceId = parcel.readInt();
        bl2 = parcel.readInt() != 0;
        this.handleAllWebDataURI = bl2;
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.isInstantAppAvailable = bl2;
        this.instantAppAvailable = bl2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(Printer printer, String string2) {
        this.dump(printer, string2, 3);
    }

    public void dump(Printer printer, String string2, int n) {
        Object object;
        Object object2;
        if (this.filter != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("Filter:");
            printer.println(((StringBuilder)object).toString());
            object = this.filter;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  ");
            ((IntentFilter)object).dump(printer, ((StringBuilder)object2).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("priority=");
        ((StringBuilder)object).append(this.priority);
        ((StringBuilder)object).append(" preferredOrder=");
        ((StringBuilder)object).append(this.preferredOrder);
        ((StringBuilder)object).append(" match=0x");
        ((StringBuilder)object).append(Integer.toHexString(this.match));
        ((StringBuilder)object).append(" specificIndex=");
        ((StringBuilder)object).append(this.specificIndex);
        ((StringBuilder)object).append(" isDefault=");
        ((StringBuilder)object).append(this.isDefault);
        printer.println(((StringBuilder)object).toString());
        if (this.resolvePackageName != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("resolvePackageName=");
            ((StringBuilder)object).append(this.resolvePackageName);
            printer.println(((StringBuilder)object).toString());
        }
        if (this.labelRes != 0 || this.nonLocalizedLabel != null || this.icon != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("labelRes=0x");
            ((StringBuilder)object).append(Integer.toHexString(this.labelRes));
            ((StringBuilder)object).append(" nonLocalizedLabel=");
            ((StringBuilder)object).append((Object)this.nonLocalizedLabel);
            ((StringBuilder)object).append(" icon=0x");
            ((StringBuilder)object).append(Integer.toHexString(this.icon));
            printer.println(((StringBuilder)object).toString());
        }
        if (this.activityInfo != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("ActivityInfo:");
            printer.println(((StringBuilder)object).toString());
            object2 = this.activityInfo;
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("  ");
            ((ActivityInfo)object2).dump(printer, ((StringBuilder)object).toString(), n);
        } else if (this.serviceInfo != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("ServiceInfo:");
            printer.println(((StringBuilder)object).toString());
            object2 = this.serviceInfo;
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("  ");
            ((ServiceInfo)object2).dump(printer, ((StringBuilder)object).toString(), n);
        } else if (this.providerInfo != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("ProviderInfo:");
            printer.println(((StringBuilder)object).toString());
            object2 = this.providerInfo;
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("  ");
            ((ProviderInfo)object2).dump(printer, ((StringBuilder)object).toString(), n);
        }
    }

    @UnsupportedAppUsage
    public ComponentInfo getComponentInfo() {
        ComponentInfo componentInfo = this.activityInfo;
        if (componentInfo != null) {
            return componentInfo;
        }
        componentInfo = this.serviceInfo;
        if (componentInfo != null) {
            return componentInfo;
        }
        componentInfo = this.providerInfo;
        if (componentInfo != null) {
            return componentInfo;
        }
        throw new IllegalStateException("Missing ComponentInfo!");
    }

    public final int getIconResource() {
        if (this.noResourceId) {
            return 0;
        }
        return this.getIconResourceInternal();
    }

    final int getIconResourceInternal() {
        int n = this.iconResourceId;
        if (n != 0) {
            return n;
        }
        ComponentInfo componentInfo = this.getComponentInfo();
        if (componentInfo != null) {
            return componentInfo.getIconResource();
        }
        return 0;
    }

    public Drawable loadIcon(PackageManager packageManager) {
        Drawable drawable2 = null;
        Object object = this.resolvePackageName;
        Object object2 = drawable2;
        if (object != null) {
            int n = this.iconResourceId;
            object2 = drawable2;
            if (n != 0) {
                object2 = packageManager.getDrawable((String)object, n, null);
            }
        }
        object = this.getComponentInfo();
        drawable2 = object2;
        if (object2 == null) {
            drawable2 = object2;
            if (this.iconResourceId != 0) {
                object2 = ((ComponentInfo)object).applicationInfo;
                drawable2 = packageManager.getDrawable(((ComponentInfo)object).packageName, this.iconResourceId, (ApplicationInfo)object2);
            }
        }
        if (drawable2 != null) {
            return packageManager.getUserBadgedIcon(drawable2, new UserHandle(packageManager.getUserId()));
        }
        return ((PackageItemInfo)object).loadIcon(packageManager);
    }

    public CharSequence loadLabel(PackageManager object) {
        int n;
        Object object2 = this.nonLocalizedLabel;
        if (object2 != null) {
            return object2;
        }
        object2 = this.resolvePackageName;
        if (object2 != null && (n = this.labelRes) != 0 && (object2 = ((PackageManager)object).getText((String)object2, n, null)) != null) {
            return object2.toString().trim();
        }
        object2 = this.getComponentInfo();
        Object object3 = ((ComponentInfo)object2).applicationInfo;
        if (this.labelRes != 0 && (object3 = ((PackageManager)object).getText(((ComponentInfo)object2).packageName, this.labelRes, (ApplicationInfo)object3)) != null) {
            return object3.toString().trim();
        }
        object = object2 = ((PackageItemInfo)object2).loadLabel((PackageManager)object);
        if (object2 != null) {
            object = object2.toString().trim();
        }
        return object;
    }

    public int resolveIconResId() {
        int n = this.icon;
        if (n != 0) {
            return n;
        }
        ComponentInfo componentInfo = this.getComponentInfo();
        if (componentInfo.icon != 0) {
            return componentInfo.icon;
        }
        return componentInfo.applicationInfo.icon;
    }

    public int resolveLabelResId() {
        int n = this.labelRes;
        if (n != 0) {
            return n;
        }
        ComponentInfo componentInfo = this.getComponentInfo();
        if (componentInfo.labelRes != 0) {
            return componentInfo.labelRes;
        }
        return componentInfo.applicationInfo.labelRes;
    }

    public String toString() {
        ComponentInfo componentInfo = this.getComponentInfo();
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("ResolveInfo{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(' ');
        ComponentName.appendShortString(stringBuilder, componentInfo.packageName, componentInfo.name);
        if (this.priority != 0) {
            stringBuilder.append(" p=");
            stringBuilder.append(this.priority);
        }
        if (this.preferredOrder != 0) {
            stringBuilder.append(" o=");
            stringBuilder.append(this.preferredOrder);
        }
        stringBuilder.append(" m=0x");
        stringBuilder.append(Integer.toHexString(this.match));
        if (this.targetUserId != -2) {
            stringBuilder.append(" targetUserId=");
            stringBuilder.append(this.targetUserId);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.activityInfo != null) {
            parcel.writeInt(1);
            this.activityInfo.writeToParcel(parcel, n);
        } else if (this.serviceInfo != null) {
            parcel.writeInt(2);
            this.serviceInfo.writeToParcel(parcel, n);
        } else if (this.providerInfo != null) {
            parcel.writeInt(3);
            this.providerInfo.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        if (this.filter != null) {
            parcel.writeInt(1);
            this.filter.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.priority);
        parcel.writeInt(this.preferredOrder);
        parcel.writeInt(this.match);
        parcel.writeInt(this.specificIndex);
        parcel.writeInt(this.labelRes);
        TextUtils.writeToParcel(this.nonLocalizedLabel, parcel, n);
        parcel.writeInt(this.icon);
        parcel.writeString(this.resolvePackageName);
        parcel.writeInt(this.targetUserId);
        parcel.writeInt((int)this.system);
        parcel.writeInt((int)this.noResourceId);
        parcel.writeInt(this.iconResourceId);
        parcel.writeInt((int)this.handleAllWebDataURI);
        parcel.writeInt((int)this.isInstantAppAvailable);
    }

    public static class DisplayNameComparator
    implements Comparator<ResolveInfo> {
        private final Collator mCollator = Collator.getInstance();
        private PackageManager mPM;

        public DisplayNameComparator(PackageManager packageManager) {
            this.mPM = packageManager;
            this.mCollator.setStrength(0);
        }

        @Override
        public final int compare(ResolveInfo object, ResolveInfo resolveInfo) {
            CharSequence charSequence;
            if (((ResolveInfo)object).targetUserId != -2) {
                return 1;
            }
            if (resolveInfo.targetUserId != -2) {
                return -1;
            }
            CharSequence charSequence2 = charSequence = ((ResolveInfo)object).loadLabel(this.mPM);
            if (charSequence == null) {
                charSequence2 = object.activityInfo.name;
            }
            charSequence = resolveInfo.loadLabel(this.mPM);
            object = charSequence;
            if (charSequence == null) {
                object = resolveInfo.activityInfo.name;
            }
            return this.mCollator.compare(charSequence2.toString(), object.toString());
        }
    }

}

