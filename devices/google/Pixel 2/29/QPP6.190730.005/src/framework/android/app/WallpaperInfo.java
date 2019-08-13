/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.app;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Printer;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class WallpaperInfo
implements Parcelable {
    public static final Parcelable.Creator<WallpaperInfo> CREATOR = new Parcelable.Creator<WallpaperInfo>(){

        @Override
        public WallpaperInfo createFromParcel(Parcel parcel) {
            return new WallpaperInfo(parcel);
        }

        public WallpaperInfo[] newArray(int n) {
            return new WallpaperInfo[n];
        }
    };
    static final String TAG = "WallpaperInfo";
    final int mAuthorResource;
    final int mContextDescriptionResource;
    final int mContextUriResource;
    final int mDescriptionResource;
    final ResolveInfo mService;
    final String mSettingsActivityName;
    final String mSettingsSliceUri;
    final boolean mShowMetadataInPreview;
    final boolean mSupportMultipleDisplays;
    final boolean mSupportsAmbientMode;
    final int mThumbnailResource;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public WallpaperInfo(Context var1_1, ResolveInfo var2_4) throws XmlPullParserException, IOException {
        super();
        this.mService = var2_4;
        var3_6 = var2_4.serviceInfo;
        var4_7 = var1_1.getPackageManager();
        var2_4 = null;
        var1_1 = null;
        try {
            var5_8 = var3_6.loadXmlMetaData((PackageManager)var4_7, "android.service.wallpaper");
            if (var5_8 == null) ** GOTO lbl70
            var1_1 = var5_8;
            var2_4 = var5_8;
            var6_9 = var4_7.getResourcesForApplication(var3_6.applicationInfo);
            var1_1 = var5_8;
            var2_4 = var5_8;
            var4_7 = Xml.asAttributeSet((XmlPullParser)var5_8);
            do {
                var1_1 = var5_8;
                var2_4 = var5_8;
            } while ((var7_10 = var5_8.next()) != 1 && var7_10 != 2);
            var1_1 = var5_8;
            var2_4 = var5_8;
            if ("wallpaper".equals(var5_8.getName())) {
                var1_1 = var5_8;
                var2_4 = var5_8;
                var4_7 = var6_9.obtainAttributes((AttributeSet)var4_7, R.styleable.Wallpaper);
                var1_1 = var5_8;
                var2_4 = var5_8;
                this.mSettingsActivityName = var4_7.getString(1);
                var1_1 = var5_8;
                var2_4 = var5_8;
                this.mThumbnailResource = var4_7.getResourceId(2, -1);
                var1_1 = var5_8;
                var2_4 = var5_8;
                this.mAuthorResource = var4_7.getResourceId(3, -1);
                var1_1 = var5_8;
                var2_4 = var5_8;
                this.mDescriptionResource = var4_7.getResourceId(0, -1);
                var1_1 = var5_8;
                var2_4 = var5_8;
                this.mContextUriResource = var4_7.getResourceId(4, -1);
                var1_1 = var5_8;
                var2_4 = var5_8;
                this.mContextDescriptionResource = var4_7.getResourceId(5, -1);
                var1_1 = var5_8;
                var2_4 = var5_8;
                this.mShowMetadataInPreview = var4_7.getBoolean(6, false);
                var1_1 = var5_8;
                var2_4 = var5_8;
                this.mSupportsAmbientMode = var4_7.getBoolean(7, false);
                var1_1 = var5_8;
                var2_4 = var5_8;
                this.mSettingsSliceUri = var4_7.getString(8);
                var1_1 = var5_8;
                var2_4 = var5_8;
                this.mSupportMultipleDisplays = var4_7.getBoolean(9, false);
                var1_1 = var5_8;
                var2_4 = var5_8;
                var4_7.recycle();
                var5_8.close();
                return;
            }
            var1_1 = var5_8;
            var2_4 = var5_8;
            var1_1 = var5_8;
            var2_4 = var5_8;
            super("Meta-data does not start with wallpaper tag");
            var1_1 = var5_8;
            var2_4 = var5_8;
            throw var4_7;
lbl70: // 1 sources:
            var1_1 = var5_8;
            var2_4 = var5_8;
            var1_1 = var5_8;
            var2_4 = var5_8;
            super("No android.service.wallpaper meta-data");
            var1_1 = var5_8;
            var2_4 = var5_8;
            throw var4_7;
        }
        catch (Throwable var2_5) {
        }
        catch (PackageManager.NameNotFoundException var1_2) {
            var1_3 = var2_4;
            var1_3 = var2_4;
            var1_3 = var2_4;
            var5_8 = new StringBuilder();
            var1_3 = var2_4;
            var5_8.append("Unable to create context for: ");
            var1_3 = var2_4;
            var5_8.append(var3_6.packageName);
            var1_3 = var2_4;
            super(var5_8.toString());
            var1_3 = var2_4;
            throw var4_7;
        }
        if (var1_1 == null) throw var2_5;
        var1_1.close();
        throw var2_5;
    }

    WallpaperInfo(Parcel parcel) {
        this.mSettingsActivityName = parcel.readString();
        this.mThumbnailResource = parcel.readInt();
        this.mAuthorResource = parcel.readInt();
        this.mDescriptionResource = parcel.readInt();
        this.mContextUriResource = parcel.readInt();
        this.mContextDescriptionResource = parcel.readInt();
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.mShowMetadataInPreview = bl2;
        bl2 = parcel.readInt() != 0;
        this.mSupportsAmbientMode = bl2;
        this.mSettingsSliceUri = parcel.readString();
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.mSupportMultipleDisplays = bl2;
        this.mService = ResolveInfo.CREATOR.createFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(Printer printer, String string2) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("Service:");
        printer.println(((StringBuilder)object).toString());
        object = this.mService;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  ");
        ((ResolveInfo)object).dump(printer, stringBuilder.toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("mSettingsActivityName=");
        ((StringBuilder)object).append(this.mSettingsActivityName);
        printer.println(((StringBuilder)object).toString());
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public String getPackageName() {
        return this.mService.serviceInfo.packageName;
    }

    public ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public String getServiceName() {
        return this.mService.serviceInfo.name;
    }

    public String getSettingsActivity() {
        return this.mSettingsActivityName;
    }

    public Uri getSettingsSliceUri() {
        String string2 = this.mSettingsSliceUri;
        if (string2 == null) {
            return null;
        }
        return Uri.parse(string2);
    }

    public boolean getShowMetadataInPreview() {
        return this.mShowMetadataInPreview;
    }

    public CharSequence loadAuthor(PackageManager packageManager) throws Resources.NotFoundException {
        if (this.mAuthorResource > 0) {
            String string2 = this.mService.resolvePackageName;
            ApplicationInfo applicationInfo = null;
            String string3 = string2;
            if (string2 == null) {
                string3 = this.mService.serviceInfo.packageName;
                applicationInfo = this.mService.serviceInfo.applicationInfo;
            }
            return packageManager.getText(string3, this.mAuthorResource, applicationInfo);
        }
        throw new Resources.NotFoundException();
    }

    public CharSequence loadContextDescription(PackageManager packageManager) throws Resources.NotFoundException {
        if (this.mContextDescriptionResource > 0) {
            String string2 = this.mService.resolvePackageName;
            ApplicationInfo applicationInfo = null;
            String string3 = string2;
            if (string2 == null) {
                string3 = this.mService.serviceInfo.packageName;
                applicationInfo = this.mService.serviceInfo.applicationInfo;
            }
            return packageManager.getText(string3, this.mContextDescriptionResource, applicationInfo).toString();
        }
        throw new Resources.NotFoundException();
    }

    public Uri loadContextUri(PackageManager object) throws Resources.NotFoundException {
        if (this.mContextUriResource > 0) {
            String string2 = this.mService.resolvePackageName;
            ApplicationInfo applicationInfo = null;
            String string3 = string2;
            if (string2 == null) {
                string3 = this.mService.serviceInfo.packageName;
                applicationInfo = this.mService.serviceInfo.applicationInfo;
            }
            if ((object = ((PackageManager)object).getText(string3, this.mContextUriResource, applicationInfo).toString()) == null) {
                return null;
            }
            return Uri.parse((String)object);
        }
        throw new Resources.NotFoundException();
    }

    public CharSequence loadDescription(PackageManager packageManager) throws Resources.NotFoundException {
        String string2 = this.mService.resolvePackageName;
        ApplicationInfo applicationInfo = null;
        String string3 = string2;
        if (string2 == null) {
            string3 = this.mService.serviceInfo.packageName;
            applicationInfo = this.mService.serviceInfo.applicationInfo;
        }
        if (this.mService.serviceInfo.descriptionRes != 0) {
            return packageManager.getText(string3, this.mService.serviceInfo.descriptionRes, applicationInfo);
        }
        int n = this.mDescriptionResource;
        if (n > 0) {
            return packageManager.getText(string3, n, this.mService.serviceInfo.applicationInfo);
        }
        throw new Resources.NotFoundException();
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        return this.mService.loadLabel(packageManager);
    }

    public Drawable loadThumbnail(PackageManager packageManager) {
        if (this.mThumbnailResource < 0) {
            return null;
        }
        return packageManager.getDrawable(this.mService.serviceInfo.packageName, this.mThumbnailResource, this.mService.serviceInfo.applicationInfo);
    }

    @SystemApi
    public boolean supportsAmbientMode() {
        return this.mSupportsAmbientMode;
    }

    public boolean supportsMultipleDisplays() {
        return this.mSupportMultipleDisplays;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WallpaperInfo{");
        stringBuilder.append(this.mService.serviceInfo.name);
        stringBuilder.append(", settings: ");
        stringBuilder.append(this.mSettingsActivityName);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeInt(this.mThumbnailResource);
        parcel.writeInt(this.mAuthorResource);
        parcel.writeInt(this.mDescriptionResource);
        parcel.writeInt(this.mContextUriResource);
        parcel.writeInt(this.mContextDescriptionResource);
        parcel.writeInt((int)this.mShowMetadataInPreview);
        parcel.writeInt((int)this.mSupportsAmbientMode);
        parcel.writeString(this.mSettingsSliceUri);
        parcel.writeInt((int)this.mSupportMultipleDisplays);
        this.mService.writeToParcel(parcel, n);
    }

}

