/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.view.inputmethod;

import android.annotation.UnsupportedAppUsage;
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
import android.util.Printer;
import android.util.Slog;
import android.util.Xml;
import android.view.inputmethod.InputMethodSubtype;
import android.view.inputmethod.InputMethodSubtypeArray;
import com.android.internal.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class InputMethodInfo
implements Parcelable {
    public static final Parcelable.Creator<InputMethodInfo> CREATOR = new Parcelable.Creator<InputMethodInfo>(){

        @Override
        public InputMethodInfo createFromParcel(Parcel parcel) {
            return new InputMethodInfo(parcel);
        }

        public InputMethodInfo[] newArray(int n) {
            return new InputMethodInfo[n];
        }
    };
    static final String TAG = "InputMethodInfo";
    private final boolean mForceDefault;
    final String mId;
    private final boolean mIsAuxIme;
    final int mIsDefaultResId;
    final boolean mIsVrOnly;
    final ResolveInfo mService;
    final String mSettingsActivityName;
    @UnsupportedAppUsage
    private final InputMethodSubtypeArray mSubtypes;
    private final boolean mSupportsSwitchingToNextInputMethod;

    public InputMethodInfo(Context context, ResolveInfo resolveInfo) throws XmlPullParserException, IOException {
        this(context, resolveInfo, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public InputMethodInfo(Context var1_1, ResolveInfo var2_13, List<InputMethodSubtype> var3_14) throws XmlPullParserException, IOException {
        block33 : {
            block34 : {
                block31 : {
                    block32 : {
                        block30 : {
                            block28 : {
                                block27 : {
                                    super();
                                    this.mService = var2_13;
                                    var4_15 = var2_13.serviceInfo;
                                    this.mId = InputMethodInfo.computeId((ResolveInfo)var2_13);
                                    var5_16 = true;
                                    var6_17 = true;
                                    this.mForceDefault = false;
                                    var1_1 = var1_1.getPackageManager();
                                    var2_13 = null;
                                    var7_18 = null;
                                    var8_19 = new ArrayList<InputMethodSubtype>();
                                    var9_20 = var6_17;
                                    var10_21 = var1_1;
                                    var10_21 = var7_18;
                                    var9_20 = var5_16;
                                    var11_22 = var1_1;
                                    var11_22 = var2_13;
                                    var2_13 = var4_15.loadXmlMetaData((PackageManager)var1_1, "android.view.im");
                                    if (var2_13 == null) ** GOTO lbl94
                                    var9_20 = var6_17;
                                    var10_21 = var1_1;
                                    var10_21 = var2_13;
                                    var9_20 = var5_16;
                                    var11_22 = var1_1;
                                    var11_22 = var2_13;
                                    var12_23 = var1_1.getResourcesForApplication(var4_15.applicationInfo);
                                    var9_20 = var6_17;
                                    var10_21 = var1_1;
                                    var10_21 = var2_13;
                                    var9_20 = var5_16;
                                    var11_22 = var1_1;
                                    var11_22 = var2_13;
                                    var13_24 = Xml.asAttributeSet((XmlPullParser)var2_13);
                                    do {
                                        var9_20 = var6_17;
                                        var10_21 = var1_1;
                                        var10_21 = var2_13;
                                        var9_20 = var5_16;
                                        var11_22 = var1_1;
                                        var11_22 = var2_13;
                                    } while ((var14_25 = var2_13.next()) != 1 && var14_25 != 2);
                                    var9_20 = var6_17;
                                    var10_21 = var1_1;
                                    var10_21 = var2_13;
                                    var9_20 = var5_16;
                                    var11_22 = var1_1;
                                    var11_22 = var2_13;
                                    if (!"input-method".equals(var2_13.getName())) break block27;
                                    var9_20 = var6_17;
                                    var10_21 = var1_1;
                                    var10_21 = var2_13;
                                    var9_20 = var5_16;
                                    var11_22 = var1_1;
                                    var11_22 = var2_13;
                                    var7_18 = var12_23.obtainAttributes(var13_24, R.styleable.InputMethod);
                                    var9_20 = var6_17;
                                    var10_21 = var1_1;
                                    var10_21 = var2_13;
                                    var9_20 = var5_16;
                                    var11_22 = var1_1;
                                    var11_22 = var2_13;
                                    var15_26 = var7_18.getString(1);
                                    var6_17 = true;
                                    var10_21 = var1_1;
                                    var10_21 = var1_1;
                                    var9_20 = var6_17;
                                    var5_16 = var7_18.getBoolean(3, false);
                                    var10_21 = var1_1;
                                    var10_21 = var1_1;
                                    var9_20 = var6_17;
                                    var16_27 = var7_18.getResourceId(0, 0);
                                    var10_21 = var1_1;
                                    var10_21 = var1_1;
                                    var9_20 = var6_17;
                                    var17_28 = var7_18.getBoolean(2, false);
                                    var10_21 = var1_1;
                                    var10_21 = var1_1;
                                    var9_20 = var6_17;
                                    var7_18.recycle();
                                    var10_21 = var1_1;
                                    var10_21 = var1_1;
                                    var9_20 = var6_17;
                                    var14_25 = var2_13.getDepth();
                                    break block28;
                                }
                                var9_20 = var6_17 = true;
                                var9_20 = var6_17;
                                var1_1 = new XmlPullParserException("Meta-data does not start with input-method tag");
                                var9_20 = var6_17;
                                throw var1_1;
lbl94: // 1 sources:
                                var9_20 = var6_17 = true;
                                var9_20 = var6_17;
                                var1_1 = new XmlPullParserException("No android.view.im meta-data");
                                var9_20 = var6_17;
                                throw var1_1;
                            }
                            do {
                                block29 : {
                                    var9_20 = var6_17;
                                    var10_21 = var1_1;
                                    var10_21 = var2_13;
                                    var9_20 = var6_17;
                                    var11_22 = var1_1;
                                    var11_22 = var2_13;
                                    var18_29 = var2_13.next();
                                    if (var18_29 != 3) break block29;
                                    try {
                                        var19_30 = var2_13.getDepth();
                                        if (var19_30 <= var14_25) break block30;
                                    }
                                    catch (Throwable var1_2) {
                                        break block31;
                                    }
                                    catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException var1_3) {
                                        break block32;
                                    }
                                }
                                if (var18_29 == 1) break block30;
                                if (var18_29 != 2) continue;
                                var10_21 = var1_1;
                                var10_21 = var1_1;
                                var9_20 = var6_17;
                                if (!"subtype".equals(var2_13.getName())) break;
                                var10_21 = var1_1;
                                var10_21 = var1_1;
                                var9_20 = var6_17;
                                var20_31 = var12_23.obtainAttributes(var13_24, R.styleable.InputMethod_Subtype);
                                var10_21 = var1_1;
                                var10_21 = var1_1;
                                var9_20 = var6_17;
                                var10_21 = var1_1;
                                var10_21 = var1_1;
                                var9_20 = var6_17;
                                var11_22 = new InputMethodSubtype.InputMethodSubtypeBuilder();
                                var9_20 = var6_17;
                                var11_22 = var11_22.setSubtypeNameResId(var20_31.getResourceId(0, 0)).setSubtypeIconResId(var20_31.getResourceId(1, 0)).setLanguageTag(var20_31.getString(9)).setSubtypeLocale(var20_31.getString(2)).setSubtypeMode(var20_31.getString(3)).setSubtypeExtraValue(var20_31.getString(4)).setIsAuxiliary(var20_31.getBoolean(5, false)).setOverridesImplicitlyEnabledSubtype(var20_31.getBoolean(6, false)).setSubtypeId(var20_31.getInt(7, 0)).setIsAsciiCapable(var20_31.getBoolean(8, false)).build();
                                var9_20 = var6_17;
                                var21_32 = var11_22.isAuxiliary();
                                if (!var21_32) {
                                    var6_17 = false;
                                }
                                var9_20 = var6_17;
                                var10_21 = var2_13;
                                try {
                                    var8_19.add((InputMethodSubtype)var11_22);
                                }
                                catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException var1_4) {
                                    break block32;
                                }
                            } while (true);
                            var9_20 = var6_17;
                            try {
                                var9_20 = var6_17;
                                var1_1 = new XmlPullParserException("Meta-data in input-method does not start with subtype tag");
                                var9_20 = var6_17;
                                throw var1_1;
                            }
                            catch (Throwable var1_7) {}
                            catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException var1_8) {
                                var6_17 = var9_20;
                                return var6_17;
                            }
                        }
                        var2_13.close();
                        if (var8_19.size() == 0) {
                            var6_17 = false;
                        }
                        if (var3_14 == null) break block33;
                        var18_29 = var3_14.size();
                        break block34;
                        catch (Throwable var1_5) {
                            break block31;
                        }
                        catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException var1_6) {
                            var6_17 = var9_20;
                            break block32;
                        }
                        ** finally { 
lbl176: // 1 sources:
                        break block31;
                        catch (Throwable var1_9) {
                            var2_13 = var10_21;
                            break block31;
                        }
                        catch (PackageManager.NameNotFoundException | IndexOutOfBoundsException | NumberFormatException var1_10) {
                            var2_13 = var11_22;
                            var6_17 = var9_20;
                        }
                    }
                    var9_20 = var6_17;
                    var10_21 = var2_13;
                    try {
                        var9_20 = var6_17;
                        var10_21 = var2_13;
                        var9_20 = var6_17;
                        var10_21 = var2_13;
                        var3_14 = new StringBuilder();
                        var9_20 = var6_17;
                        var10_21 = var2_13;
                        var3_14.append("Unable to create context for: ");
                        var9_20 = var6_17;
                        var10_21 = var2_13;
                        var3_14.append(var4_15.packageName);
                        var9_20 = var6_17;
                        var10_21 = var2_13;
                        var1_1 = new XmlPullParserException(var3_14.toString());
                        var9_20 = var6_17;
                        var10_21 = var2_13;
                        throw var1_1;
                    }
                    catch (Throwable var1_11) {
                        var2_13 = var10_21;
                    }
                }
                if (var2_13 == null) throw var1_12;
                var2_13.close();
                throw var1_12;
            }
            for (var14_25 = 0; var14_25 < var18_29; ++var14_25) {
                var1_1 = (InputMethodSubtype)var3_14.get(var14_25);
                if (!var8_19.contains(var1_1)) {
                    var8_19.add((InputMethodSubtype)var1_1);
                    continue;
                }
                var2_13 = new StringBuilder();
                var2_13.append("Duplicated subtype definition found: ");
                var2_13.append(var1_1.getLocale());
                var2_13.append(", ");
                var2_13.append(var1_1.getMode());
                Slog.w("InputMethodInfo", var2_13.toString());
            }
        }
        this.mSubtypes = new InputMethodSubtypeArray(var8_19);
        this.mSettingsActivityName = var15_26;
        this.mIsDefaultResId = var16_27;
        this.mIsAuxIme = var6_17;
        this.mSupportsSwitchingToNextInputMethod = var17_28;
        this.mIsVrOnly = var5_16;
    }

    public InputMethodInfo(ResolveInfo resolveInfo, boolean bl, String string2, List<InputMethodSubtype> list, int n, boolean bl2) {
        this(resolveInfo, bl, string2, list, n, bl2, true, false);
    }

    public InputMethodInfo(ResolveInfo resolveInfo, boolean bl, String string2, List<InputMethodSubtype> list, int n, boolean bl2, boolean bl3, boolean bl4) {
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        this.mService = resolveInfo;
        this.mId = new ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString();
        this.mSettingsActivityName = string2;
        this.mIsDefaultResId = n;
        this.mIsAuxIme = bl;
        this.mSubtypes = new InputMethodSubtypeArray(list);
        this.mForceDefault = bl2;
        this.mSupportsSwitchingToNextInputMethod = bl3;
        this.mIsVrOnly = bl4;
    }

    InputMethodInfo(Parcel parcel) {
        this.mId = parcel.readString();
        this.mSettingsActivityName = parcel.readString();
        this.mIsDefaultResId = parcel.readInt();
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n == 1;
        this.mIsAuxIme = bl2;
        bl2 = parcel.readInt() == 1 ? bl : false;
        this.mSupportsSwitchingToNextInputMethod = bl2;
        this.mIsVrOnly = parcel.readBoolean();
        this.mService = ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mSubtypes = new InputMethodSubtypeArray(parcel);
        this.mForceDefault = false;
    }

    public InputMethodInfo(String string2, String string3, CharSequence charSequence, String string4) {
        this(InputMethodInfo.buildDummyResolveInfo(string2, string3, charSequence), false, string4, null, 0, false, true, false);
    }

    private static ResolveInfo buildDummyResolveInfo(String string2, String string3, CharSequence charSequence) {
        ResolveInfo resolveInfo = new ResolveInfo();
        ServiceInfo serviceInfo = new ServiceInfo();
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.packageName = string2;
        applicationInfo.enabled = true;
        serviceInfo.applicationInfo = applicationInfo;
        serviceInfo.enabled = true;
        serviceInfo.packageName = string2;
        serviceInfo.name = string3;
        serviceInfo.exported = true;
        serviceInfo.nonLocalizedLabel = charSequence;
        resolveInfo.serviceInfo = serviceInfo;
        return resolveInfo;
    }

    public static String computeId(ResolveInfo parcelable) {
        parcelable = parcelable.serviceInfo;
        return new ComponentName(((ServiceInfo)parcelable).packageName, ((ServiceInfo)parcelable).name).flattenToShortString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(Printer printer, String string2) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("mId=");
        ((StringBuilder)object).append(this.mId);
        ((StringBuilder)object).append(" mSettingsActivityName=");
        ((StringBuilder)object).append(this.mSettingsActivityName);
        ((StringBuilder)object).append(" mIsVrOnly=");
        ((StringBuilder)object).append(this.mIsVrOnly);
        ((StringBuilder)object).append(" mSupportsSwitchingToNextInputMethod=");
        ((StringBuilder)object).append(this.mSupportsSwitchingToNextInputMethod);
        printer.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("mIsDefaultResId=0x");
        ((StringBuilder)object).append(Integer.toHexString(this.mIsDefaultResId));
        printer.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("Service:");
        printer.println(((StringBuilder)object).toString());
        object = this.mService;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  ");
        ((ResolveInfo)object).dump(printer, stringBuilder.toString());
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof InputMethodInfo)) {
            return false;
        }
        object = (InputMethodInfo)object;
        return this.mId.equals(((InputMethodInfo)object).mId);
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public String getId() {
        return this.mId;
    }

    public int getIsDefaultResourceId() {
        return this.mIsDefaultResId;
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

    public InputMethodSubtype getSubtypeAt(int n) {
        return this.mSubtypes.get(n);
    }

    public int getSubtypeCount() {
        return this.mSubtypes.getCount();
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    public boolean isAuxiliaryIme() {
        return this.mIsAuxIme;
    }

    @UnsupportedAppUsage
    public boolean isDefault(Context context) {
        block4 : {
            if (this.mForceDefault) {
                return true;
            }
            if (this.getIsDefaultResourceId() != 0) break block4;
            return false;
        }
        try {
            boolean bl = context.createPackageContext(this.getPackageName(), 0).getResources().getBoolean(this.getIsDefaultResourceId());
            return bl;
        }
        catch (PackageManager.NameNotFoundException | Resources.NotFoundException exception) {
            return false;
        }
    }

    public boolean isSystem() {
        int n = this.mService.serviceInfo.applicationInfo.flags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isVrOnly() {
        return this.mIsVrOnly;
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        return this.mService.loadLabel(packageManager);
    }

    public boolean supportsSwitchingToNextInputMethod() {
        return this.mSupportsSwitchingToNextInputMethod;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("InputMethodInfo{");
        stringBuilder.append(this.mId);
        stringBuilder.append(", settings: ");
        stringBuilder.append(this.mSettingsActivityName);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mId);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeInt(this.mIsDefaultResId);
        parcel.writeInt((int)this.mIsAuxIme);
        parcel.writeInt((int)this.mSupportsSwitchingToNextInputMethod);
        parcel.writeBoolean(this.mIsVrOnly);
        this.mService.writeToParcel(parcel, n);
        this.mSubtypes.writeToParcel(parcel);
    }

}

