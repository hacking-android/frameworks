/*
 * Decompiled with CFR 0.145.
 */
package android.appwidget;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ResourceId;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AppWidgetProviderInfo
implements Parcelable {
    public static final Parcelable.Creator<AppWidgetProviderInfo> CREATOR = new Parcelable.Creator<AppWidgetProviderInfo>(){

        @Override
        public AppWidgetProviderInfo createFromParcel(Parcel parcel) {
            return new AppWidgetProviderInfo(parcel);
        }

        public AppWidgetProviderInfo[] newArray(int n) {
            return new AppWidgetProviderInfo[n];
        }
    };
    public static final int RESIZE_BOTH = 3;
    public static final int RESIZE_HORIZONTAL = 1;
    public static final int RESIZE_NONE = 0;
    public static final int RESIZE_VERTICAL = 2;
    public static final int WIDGET_CATEGORY_HOME_SCREEN = 1;
    public static final int WIDGET_CATEGORY_KEYGUARD = 2;
    public static final int WIDGET_CATEGORY_SEARCHBOX = 4;
    public static final int WIDGET_FEATURE_HIDE_FROM_PICKER = 2;
    public static final int WIDGET_FEATURE_RECONFIGURABLE = 1;
    public int autoAdvanceViewId;
    public ComponentName configure;
    public int icon;
    public int initialKeyguardLayout;
    public int initialLayout;
    @Deprecated
    public String label;
    public int minHeight;
    public int minResizeHeight;
    public int minResizeWidth;
    public int minWidth;
    public int previewImage;
    public ComponentName provider;
    @UnsupportedAppUsage
    public ActivityInfo providerInfo;
    public int resizeMode;
    public int updatePeriodMillis;
    public int widgetCategory;
    public int widgetFeatures;

    public AppWidgetProviderInfo() {
    }

    public AppWidgetProviderInfo(Parcel parcel) {
        this.provider = parcel.readTypedObject(ComponentName.CREATOR);
        this.minWidth = parcel.readInt();
        this.minHeight = parcel.readInt();
        this.minResizeWidth = parcel.readInt();
        this.minResizeHeight = parcel.readInt();
        this.updatePeriodMillis = parcel.readInt();
        this.initialLayout = parcel.readInt();
        this.initialKeyguardLayout = parcel.readInt();
        this.configure = parcel.readTypedObject(ComponentName.CREATOR);
        this.label = parcel.readString();
        this.icon = parcel.readInt();
        this.previewImage = parcel.readInt();
        this.autoAdvanceViewId = parcel.readInt();
        this.resizeMode = parcel.readInt();
        this.widgetCategory = parcel.readInt();
        this.providerInfo = parcel.readTypedObject(ActivityInfo.CREATOR);
        this.widgetFeatures = parcel.readInt();
    }

    private Drawable loadDrawable(Context context, int n, int n2, boolean bl) {
        Drawable drawable2;
        block4 : {
            int n3;
            Object object;
            block5 : {
                drawable2 = null;
                object = context.getPackageManager().getResourcesForApplication(this.providerInfo.applicationInfo);
                if (!ResourceId.isValid(n2)) break block4;
                n3 = n;
                if (n >= 0) break block5;
                n3 = 0;
            }
            try {
                object = ((Resources)object).getDrawableForDensity(n2, n3, null);
                return object;
            }
            catch (PackageManager.NameNotFoundException | Resources.NotFoundException exception) {
                // empty catch block
            }
        }
        if (bl) {
            drawable2 = this.providerInfo.loadIcon(context.getPackageManager());
        }
        return drawable2;
    }

    public AppWidgetProviderInfo clone() {
        int n;
        AppWidgetProviderInfo appWidgetProviderInfo = new AppWidgetProviderInfo();
        ComponentName componentName = this.provider;
        Object var3_3 = null;
        componentName = componentName == null ? null : componentName.clone();
        appWidgetProviderInfo.provider = componentName;
        appWidgetProviderInfo.minWidth = this.minWidth;
        appWidgetProviderInfo.minHeight = this.minHeight;
        appWidgetProviderInfo.minResizeWidth = n = this.minResizeHeight;
        appWidgetProviderInfo.minResizeHeight = n;
        appWidgetProviderInfo.updatePeriodMillis = this.updatePeriodMillis;
        appWidgetProviderInfo.initialLayout = this.initialLayout;
        appWidgetProviderInfo.initialKeyguardLayout = this.initialKeyguardLayout;
        componentName = this.configure;
        componentName = componentName == null ? var3_3 : componentName.clone();
        appWidgetProviderInfo.configure = componentName;
        appWidgetProviderInfo.label = this.label;
        appWidgetProviderInfo.icon = this.icon;
        appWidgetProviderInfo.previewImage = this.previewImage;
        appWidgetProviderInfo.autoAdvanceViewId = this.autoAdvanceViewId;
        appWidgetProviderInfo.resizeMode = this.resizeMode;
        appWidgetProviderInfo.widgetCategory = this.widgetCategory;
        appWidgetProviderInfo.providerInfo = this.providerInfo;
        appWidgetProviderInfo.widgetFeatures = this.widgetFeatures;
        return appWidgetProviderInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public final UserHandle getProfile() {
        return new UserHandle(UserHandle.getUserId(this.providerInfo.applicationInfo.uid));
    }

    public final Drawable loadIcon(Context context, int n) {
        return this.loadDrawable(context, n, this.providerInfo.getIconResource(), true);
    }

    public final String loadLabel(PackageManager object) {
        if ((object = this.providerInfo.loadLabel((PackageManager)object)) != null) {
            return object.toString().trim();
        }
        return null;
    }

    public final Drawable loadPreviewImage(Context context, int n) {
        return this.loadDrawable(context, n, this.previewImage, false);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AppWidgetProviderInfo(");
        stringBuilder.append(this.getProfile());
        stringBuilder.append('/');
        stringBuilder.append(this.provider);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public void updateDimensions(DisplayMetrics displayMetrics) {
        this.minWidth = TypedValue.complexToDimensionPixelSize(this.minWidth, displayMetrics);
        this.minHeight = TypedValue.complexToDimensionPixelSize(this.minHeight, displayMetrics);
        this.minResizeWidth = TypedValue.complexToDimensionPixelSize(this.minResizeWidth, displayMetrics);
        this.minResizeHeight = TypedValue.complexToDimensionPixelSize(this.minResizeHeight, displayMetrics);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedObject(this.provider, n);
        parcel.writeInt(this.minWidth);
        parcel.writeInt(this.minHeight);
        parcel.writeInt(this.minResizeWidth);
        parcel.writeInt(this.minResizeHeight);
        parcel.writeInt(this.updatePeriodMillis);
        parcel.writeInt(this.initialLayout);
        parcel.writeInt(this.initialKeyguardLayout);
        parcel.writeTypedObject(this.configure, n);
        parcel.writeString(this.label);
        parcel.writeInt(this.icon);
        parcel.writeInt(this.previewImage);
        parcel.writeInt(this.autoAdvanceViewId);
        parcel.writeInt(this.resizeMode);
        parcel.writeInt(this.widgetCategory);
        parcel.writeTypedObject(this.providerInfo, n);
        parcel.writeInt(this.widgetFeatures);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CategoryFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FeatureFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ResizeModeFlags {
    }

}

