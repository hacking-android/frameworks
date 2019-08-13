/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.view.textservice;

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
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.PrintWriterPrinter;
import android.util.Printer;
import android.util.Slog;
import android.util.Xml;
import android.view.textservice.SpellCheckerSubtype;
import com.android.internal.R;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class SpellCheckerInfo
implements Parcelable {
    public static final Parcelable.Creator<SpellCheckerInfo> CREATOR;
    private static final String TAG;
    private final String mId;
    private final int mLabel;
    private final ResolveInfo mService;
    private final String mSettingsActivityName;
    private final ArrayList<SpellCheckerSubtype> mSubtypes;

    static {
        TAG = SpellCheckerInfo.class.getSimpleName();
        CREATOR = new Parcelable.Creator<SpellCheckerInfo>(){

            @Override
            public SpellCheckerInfo createFromParcel(Parcel parcel) {
                return new SpellCheckerInfo(parcel);
            }

            public SpellCheckerInfo[] newArray(int n) {
                return new SpellCheckerInfo[n];
            }
        };
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public SpellCheckerInfo(Context var1_1, ResolveInfo var2_2) throws XmlPullParserException, IOException {
        super();
        this.mSubtypes = new ArrayList<E>();
        this.mService = var2_2;
        var3_4 = var2_2.serviceInfo;
        this.mId = new ComponentName(var3_4.packageName, var3_4.name).flattenToShortString();
        var4_5 /* !! */  = var1_1.getPackageManager();
        var2_2 = null;
        var1_1 = null;
        try {
            var5_8 = var3_4.loadXmlMetaData(var4_5 /* !! */ , "android.view.textservice.scs");
            if (var5_8 == null) ** GOTO lbl87
            var1_1 = var5_8;
            var2_2 = var5_8;
            var4_5 /* !! */  = var4_5 /* !! */ .getResourcesForApplication(var3_4.applicationInfo);
            var1_1 = var5_8;
            var2_2 = var5_8;
            var6_9 = Xml.asAttributeSet((XmlPullParser)var5_8);
            do {
                var1_1 = var5_8;
                var2_2 = var5_8;
            } while ((var7_10 = var5_8.next()) != 1 && var7_10 != 2);
            var1_1 = var5_8;
            var2_2 = var5_8;
            if ("spell-checker".equals(var5_8.getName())) {
                var1_1 = var5_8;
                var2_2 = var5_8;
                var8_11 = var4_5 /* !! */ .obtainAttributes(var6_9, R.styleable.SpellChecker);
                var1_1 = var5_8;
                var2_2 = var5_8;
                var9_12 = var8_11.getResourceId(0, 0);
                var1_1 = var5_8;
                var2_2 = var5_8;
                var10_13 = var8_11.getString(1);
                var1_1 = var5_8;
                var2_2 = var5_8;
                var8_11.recycle();
                var1_1 = var5_8;
                var2_2 = var5_8;
                var7_10 = var5_8.getDepth();
                do {
                    var1_1 = var5_8;
                    var2_2 = var5_8;
                    var11_15 = var5_8.next();
                    if (var11_15 == 3) {
                        var1_1 = var5_8;
                        var2_2 = var5_8;
                        if (var5_8.getDepth() <= var7_10) break;
                    }
                    if (var11_15 == 1) break;
                    if (var11_15 != 2) continue;
                    var1_1 = var5_8;
                    var2_2 = var5_8;
                    if (!"subtype".equals(var5_8.getName())) {
                        var1_1 = var5_8;
                        var2_2 = var5_8;
                        var1_1 = var5_8;
                        var2_2 = var5_8;
                        super("Meta-data in spell-checker does not start with subtype tag");
                        var1_1 = var5_8;
                        var2_2 = var5_8;
                        throw var4_5 /* !! */ ;
                    }
                    var1_1 = var5_8;
                    var2_2 = var5_8;
                    var8_11 = var4_5 /* !! */ .obtainAttributes(var6_9, R.styleable.SpellChecker_Subtype);
                    var1_1 = var5_8;
                    var2_2 = var5_8;
                    var1_1 = var5_8;
                    var2_2 = var5_8;
                    var12_16 = new SpellCheckerSubtype(var8_11.getResourceId(0, 0), var8_11.getString(1), var8_11.getString(4), var8_11.getString(2), var8_11.getInt(3, 0));
                    var1_1 = var5_8;
                    var2_2 = var5_8;
                    this.mSubtypes.add(var12_16);
                } while (true);
                var5_8.close();
                this.mLabel = var9_12;
                this.mSettingsActivityName = var10_13;
                return;
            }
            var1_1 = var5_8;
            var2_2 = var5_8;
            var1_1 = var5_8;
            var2_2 = var5_8;
            super("Meta-data does not start with spell-checker tag");
            var1_1 = var5_8;
            var2_2 = var5_8;
            throw var4_5 /* !! */ ;
lbl87: // 1 sources:
            var1_1 = var5_8;
            var2_2 = var5_8;
            var1_1 = var5_8;
            var2_2 = var5_8;
            super("No android.view.textservice.scs meta-data");
            var1_1 = var5_8;
            var2_2 = var5_8;
            throw var4_5 /* !! */ ;
        }
        catch (Throwable var2_3) {
        }
        catch (Exception var4_6) {
            var1_1 = var2_2;
            var5_8 = SpellCheckerInfo.TAG;
            var1_1 = var2_2;
            var1_1 = var2_2;
            var10_14 = new StringBuilder();
            var1_1 = var2_2;
            var10_14.append("Caught exception: ");
            var1_1 = var2_2;
            var10_14.append(var4_6);
            var1_1 = var2_2;
            Slog.e((String)var5_8, var10_14.toString());
            var1_1 = var2_2;
            var1_1 = var2_2;
            var1_1 = var2_2;
            var5_8 = new StringBuilder();
            var1_1 = var2_2;
            var5_8.append("Unable to create context for: ");
            var1_1 = var2_2;
            var5_8.append(var3_4.packageName);
            var1_1 = var2_2;
            var4_7 = new XmlPullParserException(var5_8.toString());
            var1_1 = var2_2;
            throw var4_7;
        }
        if (var1_1 == null) throw var2_3;
        var1_1.close();
        throw var2_3;
    }

    public SpellCheckerInfo(Parcel parcel) {
        this.mSubtypes = new ArrayList();
        this.mLabel = parcel.readInt();
        this.mId = parcel.readString();
        this.mSettingsActivityName = parcel.readString();
        this.mService = ResolveInfo.CREATOR.createFromParcel(parcel);
        parcel.readTypedList(this.mSubtypes, SpellCheckerSubtype.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(PrintWriter printWriter, String string2) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("mId=");
        ((StringBuilder)object).append(this.mId);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("mSettingsActivityName=");
        ((StringBuilder)object).append(this.mSettingsActivityName);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("Service:");
        printWriter.println(((StringBuilder)object).toString());
        ResolveInfo resolveInfo = this.mService;
        Object object2 = new PrintWriterPrinter(printWriter);
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("  ");
        resolveInfo.dump((Printer)object2, ((StringBuilder)object).toString());
        int n = this.getSubtypeCount();
        for (int i = 0; i < n; ++i) {
            object = this.getSubtypeAt(i);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("  Subtype #");
            ((StringBuilder)object2).append(i);
            ((StringBuilder)object2).append(":");
            printWriter.println(((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("    locale=");
            ((StringBuilder)object2).append(((SpellCheckerSubtype)object).getLocale());
            ((StringBuilder)object2).append(" languageTag=");
            ((StringBuilder)object2).append(((SpellCheckerSubtype)object).getLanguageTag());
            printWriter.println(((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("    extraValue=");
            ((StringBuilder)object2).append(((SpellCheckerSubtype)object).getExtraValue());
            printWriter.println(((StringBuilder)object2).toString());
        }
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public String getId() {
        return this.mId;
    }

    public String getPackageName() {
        return this.mService.serviceInfo.packageName;
    }

    public ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public String getSettingsActivity() {
        return this.mSettingsActivityName;
    }

    public SpellCheckerSubtype getSubtypeAt(int n) {
        return this.mSubtypes.get(n);
    }

    public int getSubtypeCount() {
        return this.mSubtypes.size();
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        if (this.mLabel != 0 && packageManager != null) {
            return packageManager.getText(this.getPackageName(), this.mLabel, this.mService.serviceInfo.applicationInfo);
        }
        return "";
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mLabel);
        parcel.writeString(this.mId);
        parcel.writeString(this.mSettingsActivityName);
        this.mService.writeToParcel(parcel, n);
        parcel.writeTypedList(this.mSubtypes);
    }

}

