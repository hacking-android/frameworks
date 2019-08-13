/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Printer;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.Preconditions;
import java.text.Collator;
import java.util.Comparator;

public class PackageItemInfo {
    public static final float DEFAULT_MAX_LABEL_SIZE_PX = 500.0f;
    public static final int DUMP_FLAG_ALL = 3;
    public static final int DUMP_FLAG_APPLICATION = 2;
    public static final int DUMP_FLAG_DETAILS = 1;
    private static final int MAX_SAFE_LABEL_LENGTH = 50000;
    @SystemApi
    @Deprecated
    public static final int SAFE_LABEL_FLAG_FIRST_LINE = 4;
    @SystemApi
    @Deprecated
    public static final int SAFE_LABEL_FLAG_SINGLE_LINE = 2;
    @SystemApi
    @Deprecated
    public static final int SAFE_LABEL_FLAG_TRIM = 1;
    private static volatile boolean sForceSafeLabels = false;
    public int banner;
    public int icon;
    public int labelRes;
    public int logo;
    public Bundle metaData;
    public String name;
    public CharSequence nonLocalizedLabel;
    public String packageName;
    public int showUserIcon;

    public PackageItemInfo() {
        this.showUserIcon = -10000;
    }

    public PackageItemInfo(PackageItemInfo packageItemInfo) {
        this.name = packageItemInfo.name;
        CharSequence charSequence = this.name;
        if (charSequence != null) {
            this.name = ((String)charSequence).trim();
        }
        this.packageName = packageItemInfo.packageName;
        this.labelRes = packageItemInfo.labelRes;
        this.nonLocalizedLabel = packageItemInfo.nonLocalizedLabel;
        charSequence = this.nonLocalizedLabel;
        if (charSequence != null) {
            this.nonLocalizedLabel = charSequence.toString().trim();
        }
        this.icon = packageItemInfo.icon;
        this.banner = packageItemInfo.banner;
        this.logo = packageItemInfo.logo;
        this.metaData = packageItemInfo.metaData;
        this.showUserIcon = packageItemInfo.showUserIcon;
    }

    protected PackageItemInfo(Parcel parcel) {
        this.name = parcel.readString();
        this.packageName = parcel.readString();
        this.labelRes = parcel.readInt();
        this.nonLocalizedLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.icon = parcel.readInt();
        this.logo = parcel.readInt();
        this.metaData = parcel.readBundle();
        this.banner = parcel.readInt();
        this.showUserIcon = parcel.readInt();
    }

    @SystemApi
    public static void forceSafeLabels() {
        sForceSafeLabels = true;
    }

    protected void dumpBack(Printer printer, String string2) {
    }

    protected void dumpFront(Printer printer, String string2) {
        StringBuilder stringBuilder;
        if (this.name != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("name=");
            stringBuilder.append(this.name);
            printer.println(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("packageName=");
        stringBuilder.append(this.packageName);
        printer.println(stringBuilder.toString());
        if (this.labelRes != 0 || this.nonLocalizedLabel != null || this.icon != 0 || this.banner != 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("labelRes=0x");
            stringBuilder.append(Integer.toHexString(this.labelRes));
            stringBuilder.append(" nonLocalizedLabel=");
            stringBuilder.append((Object)this.nonLocalizedLabel);
            stringBuilder.append(" icon=0x");
            stringBuilder.append(Integer.toHexString(this.icon));
            stringBuilder.append(" banner=0x");
            stringBuilder.append(Integer.toHexString(this.banner));
            printer.println(stringBuilder.toString());
        }
    }

    protected ApplicationInfo getApplicationInfo() {
        return null;
    }

    public Drawable loadBanner(PackageManager packageManager) {
        Drawable drawable2;
        int n = this.banner;
        if (n != 0 && (drawable2 = packageManager.getDrawable(this.packageName, n, this.getApplicationInfo())) != null) {
            return drawable2;
        }
        return this.loadDefaultBanner(packageManager);
    }

    protected Drawable loadDefaultBanner(PackageManager packageManager) {
        return null;
    }

    public Drawable loadDefaultIcon(PackageManager packageManager) {
        return packageManager.getDefaultActivityIcon();
    }

    protected Drawable loadDefaultLogo(PackageManager packageManager) {
        return null;
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return packageManager.loadItemIcon(this, this.getApplicationInfo());
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        if (sForceSafeLabels) {
            return this.loadSafeLabel(packageManager, 500.0f, 5);
        }
        return this.loadUnsafeLabel(packageManager);
    }

    public Drawable loadLogo(PackageManager packageManager) {
        Drawable drawable2;
        int n = this.logo;
        if (n != 0 && (drawable2 = packageManager.getDrawable(this.packageName, n, this.getApplicationInfo())) != null) {
            return drawable2;
        }
        return this.loadDefaultLogo(packageManager);
    }

    @SystemApi
    @Deprecated
    public CharSequence loadSafeLabel(PackageManager packageManager) {
        return this.loadSafeLabel(packageManager, 500.0f, 5);
    }

    @SystemApi
    public CharSequence loadSafeLabel(PackageManager packageManager, float f, int n) {
        Preconditions.checkNotNull(packageManager);
        return TextUtils.makeSafeForPresentation(this.loadUnsafeLabel(packageManager).toString(), 50000, f, n);
    }

    public Drawable loadUnbadgedIcon(PackageManager packageManager) {
        return packageManager.loadUnbadgedItemIcon(this, this.getApplicationInfo());
    }

    public CharSequence loadUnsafeLabel(PackageManager object) {
        CharSequence charSequence = this.nonLocalizedLabel;
        if (charSequence != null) {
            return charSequence;
        }
        int n = this.labelRes;
        if (n != 0 && (object = ((PackageManager)object).getText(this.packageName, n, this.getApplicationInfo())) != null) {
            return object.toString().trim();
        }
        object = this.name;
        if (object != null) {
            return object;
        }
        return this.packageName;
    }

    public XmlResourceParser loadXmlMetaData(PackageManager packageManager, String string2) {
        int n;
        Bundle bundle = this.metaData;
        if (bundle != null && (n = bundle.getInt(string2)) != 0) {
            return packageManager.getXml(this.packageName, n, this.getApplicationInfo());
        }
        return null;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.name);
        parcel.writeString(this.packageName);
        parcel.writeInt(this.labelRes);
        TextUtils.writeToParcel(this.nonLocalizedLabel, parcel, n);
        parcel.writeInt(this.icon);
        parcel.writeInt(this.logo);
        parcel.writeBundle(this.metaData);
        parcel.writeInt(this.banner);
        parcel.writeInt(this.showUserIcon);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l, int n) {
        l = protoOutputStream.start(l);
        CharSequence charSequence = this.name;
        if (charSequence != null) {
            protoOutputStream.write(1138166333441L, (String)charSequence);
        }
        protoOutputStream.write(1138166333442L, this.packageName);
        protoOutputStream.write(1120986464259L, this.labelRes);
        charSequence = this.nonLocalizedLabel;
        if (charSequence != null) {
            protoOutputStream.write(1138166333444L, charSequence.toString());
        }
        protoOutputStream.write(1120986464261L, this.icon);
        protoOutputStream.write(1120986464262L, this.banner);
        protoOutputStream.end(l);
    }

    public static class DisplayNameComparator
    implements Comparator<PackageItemInfo> {
        private PackageManager mPM;
        private final Collator sCollator = Collator.getInstance();

        public DisplayNameComparator(PackageManager packageManager) {
            this.mPM = packageManager;
        }

        @Override
        public final int compare(PackageItemInfo object, PackageItemInfo packageItemInfo) {
            CharSequence charSequence;
            CharSequence charSequence2 = charSequence = ((PackageItemInfo)object).loadLabel(this.mPM);
            if (charSequence == null) {
                charSequence2 = ((PackageItemInfo)object).name;
            }
            charSequence = packageItemInfo.loadLabel(this.mPM);
            object = charSequence;
            if (charSequence == null) {
                object = packageItemInfo.name;
            }
            return this.sCollator.compare(charSequence2.toString(), object.toString());
        }
    }

}

