/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.app.admin;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Printer;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public final class DeviceAdminInfo
implements Parcelable {
    public static final Parcelable.Creator<DeviceAdminInfo> CREATOR;
    static final String TAG = "DeviceAdminInfo";
    public static final int USES_ENCRYPTED_STORAGE = 7;
    public static final int USES_POLICY_DEVICE_OWNER = -2;
    public static final int USES_POLICY_DISABLE_CAMERA = 8;
    public static final int USES_POLICY_DISABLE_KEYGUARD_FEATURES = 9;
    public static final int USES_POLICY_EXPIRE_PASSWORD = 6;
    public static final int USES_POLICY_FORCE_LOCK = 3;
    public static final int USES_POLICY_LIMIT_PASSWORD = 0;
    public static final int USES_POLICY_PROFILE_OWNER = -1;
    public static final int USES_POLICY_RESET_PASSWORD = 2;
    public static final int USES_POLICY_SETS_GLOBAL_PROXY = 5;
    public static final int USES_POLICY_WATCH_LOGIN = 1;
    public static final int USES_POLICY_WIPE_DATA = 4;
    static HashMap<String, Integer> sKnownPolicies;
    static ArrayList<PolicyInfo> sPoliciesDisplayOrder;
    static SparseArray<PolicyInfo> sRevKnownPolicies;
    final ActivityInfo mActivityInfo;
    boolean mSupportsTransferOwnership;
    int mUsesPolicies;
    boolean mVisible;

    static {
        sPoliciesDisplayOrder = new ArrayList();
        sKnownPolicies = new HashMap();
        sRevKnownPolicies = new SparseArray();
        sPoliciesDisplayOrder.add(new PolicyInfo(4, "wipe-data", 17040869, 17040858, 17040870, 17040859));
        sPoliciesDisplayOrder.add(new PolicyInfo(2, "reset-password", 17040866, 17040854));
        sPoliciesDisplayOrder.add(new PolicyInfo(0, "limit-password", 17040865, 17040853));
        sPoliciesDisplayOrder.add(new PolicyInfo(1, "watch-login", 17040868, 17040856, 17040868, 17040857));
        sPoliciesDisplayOrder.add(new PolicyInfo(3, "force-lock", 17040864, 17040852));
        sPoliciesDisplayOrder.add(new PolicyInfo(5, "set-global-proxy", 17040867, 17040855));
        sPoliciesDisplayOrder.add(new PolicyInfo(6, "expire-password", 17040863, 17040851));
        sPoliciesDisplayOrder.add(new PolicyInfo(7, "encrypted-storage", 17040862, 17040850));
        sPoliciesDisplayOrder.add(new PolicyInfo(8, "disable-camera", 17040860, 17040848));
        sPoliciesDisplayOrder.add(new PolicyInfo(9, "disable-keyguard-features", 17040861, 17040849));
        for (int i = 0; i < sPoliciesDisplayOrder.size(); ++i) {
            PolicyInfo policyInfo = sPoliciesDisplayOrder.get(i);
            sRevKnownPolicies.put(policyInfo.ident, policyInfo);
            sKnownPolicies.put(policyInfo.tag, policyInfo.ident);
        }
        CREATOR = new Parcelable.Creator<DeviceAdminInfo>(){

            @Override
            public DeviceAdminInfo createFromParcel(Parcel parcel) {
                return new DeviceAdminInfo(parcel);
            }

            public DeviceAdminInfo[] newArray(int n) {
                return new DeviceAdminInfo[n];
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
    public DeviceAdminInfo(Context var1_1, ActivityInfo var2_5) throws XmlPullParserException, IOException {
        block15 : {
            super();
            this.mActivityInfo = var2_5;
            var1_1 /* !! */  = var1_1 /* !! */ .getPackageManager();
            var3_6 = null;
            var2_5 = null;
            try {
                var4_7 = this.mActivityInfo.loadXmlMetaData((PackageManager)var1_1 /* !! */ , "android.app.device_admin");
                if (var4_7 == null) ** GOTO lbl46
                var2_5 = var4_7;
                var3_6 = var4_7;
                var1_1 /* !! */  = var1_1 /* !! */ .getResourcesForApplication(this.mActivityInfo.applicationInfo);
                var2_5 = var4_7;
                var3_6 = var4_7;
                var5_8 = Xml.asAttributeSet((XmlPullParser)var4_7);
                do {
                    var2_5 = var4_7;
                    var3_6 = var4_7;
                    var6_9 = var4_7.next();
                    var7_10 = 1;
                } while (var6_9 != 1 && var6_9 != 2);
                var2_5 = var4_7;
                var3_6 = var4_7;
                if ("device-admin".equals(var4_7.getName())) {
                    var2_5 = var4_7;
                    var3_6 = var4_7;
                    var5_8 = var1_1 /* !! */ .obtainAttributes((AttributeSet)var5_8, R.styleable.DeviceAdmin);
                    var2_5 = var4_7;
                    var3_6 = var4_7;
                    this.mVisible = var5_8.getBoolean(0, true);
                    var2_5 = var4_7;
                    var3_6 = var4_7;
                    var5_8.recycle();
                    var2_5 = var4_7;
                    var3_6 = var4_7;
                    var6_9 = var4_7.getDepth();
                    break block15;
                }
                var2_5 = var4_7;
                var3_6 = var4_7;
                var2_5 = var4_7;
                var3_6 = var4_7;
                var1_1 /* !! */  = new XmlPullParserException("Meta-data does not start with device-admin tag");
                var2_5 = var4_7;
                var3_6 = var4_7;
                throw var1_1 /* !! */ ;
lbl46: // 1 sources:
                var2_5 = var4_7;
                var3_6 = var4_7;
                var2_5 = var4_7;
                var3_6 = var4_7;
                var1_1 /* !! */  = new XmlPullParserException("No android.app.device_admin meta-data");
                var2_5 = var4_7;
                var3_6 = var4_7;
                throw var1_1 /* !! */ ;
            }
            catch (Throwable var1_2) {
            }
            catch (PackageManager.NameNotFoundException var1_3) {
                var2_5 = var3_6;
                var2_5 = var3_6;
                var2_5 = var3_6;
                var4_7 = new StringBuilder();
                var2_5 = var3_6;
                var4_7.append("Unable to create context for: ");
                var2_5 = var3_6;
                var4_7.append(this.mActivityInfo.packageName);
                var2_5 = var3_6;
                var1_4 = new XmlPullParserException(var4_7.toString());
                var2_5 = var3_6;
                throw var1_4;
            }
            if (var2_5 == null) throw var1_2;
            var2_5.close();
            throw var1_2;
        }
        do {
            block19 : {
                block18 : {
                    block16 : {
                        block17 : {
                            var2_5 = var4_7;
                            var3_6 = var4_7;
                            var8_11 = var4_7.next();
                            if (var8_11 == var7_10) break block16;
                            if (var8_11 != 3) break block17;
                            var2_5 = var4_7;
                            var3_6 = var4_7;
                            if (var4_7.getDepth() <= var6_9) break block16;
                        }
                        if (var8_11 == 3 || var8_11 == 4) continue;
                        var2_5 = var4_7;
                        var3_6 = var4_7;
                        var5_8 = var4_7.getName();
                        var2_5 = var4_7;
                        var3_6 = var4_7;
                        if (var5_8.equals("uses-policies")) break block18;
                        var2_5 = var4_7;
                        var3_6 = var4_7;
                        if (var5_8.equals("support-transfer-ownership")) {
                            var2_5 = var4_7;
                            var3_6 = var4_7;
                            if (var4_7.next() != 3) {
                                var2_5 = var4_7;
                                var3_6 = var4_7;
                                var2_5 = var4_7;
                                var3_6 = var4_7;
                                var1_1 /* !! */  = new XmlPullParserException("support-transfer-ownership tag must be empty.");
                                var2_5 = var4_7;
                                var3_6 = var4_7;
                                throw var1_1 /* !! */ ;
                            }
                            var2_5 = var4_7;
                            var3_6 = var4_7;
                            this.mSupportsTransferOwnership = true;
                        }
                        break block19;
                    }
                    var4_7.close();
                    return;
                }
                var2_5 = var4_7;
                var3_6 = var4_7;
                var8_11 = var4_7.getDepth();
                do {
                    var2_5 = var4_7;
                    var3_6 = var4_7;
                    var9_12 = var4_7.next();
                    if (var9_12 == var7_10) break;
                    if (var9_12 == 3) {
                        var2_5 = var4_7;
                        var3_6 = var4_7;
                        if (var4_7.getDepth() <= var8_11) break;
                    }
                    if (var9_12 != 3 && var9_12 != 4) {
                        var2_5 = var4_7;
                        var3_6 = var4_7;
                        var5_8 = var4_7.getName();
                        var2_5 = var4_7;
                        var3_6 = var4_7;
                        var10_13 = DeviceAdminInfo.sKnownPolicies.get(var5_8);
                        if (var10_13 != null) {
                            var2_5 = var4_7;
                            var3_6 = var4_7;
                            this.mUsesPolicies |= var7_10 << var10_13.intValue();
                        } else {
                            var2_5 = var4_7;
                            var3_6 = var4_7;
                            var2_5 = var4_7;
                            var3_6 = var4_7;
                            super();
                            var2_5 = var4_7;
                            var3_6 = var4_7;
                            var10_13.append("Unknown tag under uses-policies of ");
                            var2_5 = var4_7;
                            var3_6 = var4_7;
                            var10_13.append(this.getComponent());
                            var2_5 = var4_7;
                            var3_6 = var4_7;
                            var10_13.append(": ");
                            var2_5 = var4_7;
                            var3_6 = var4_7;
                            var10_13.append((String)var5_8);
                            var2_5 = var4_7;
                            var3_6 = var4_7;
                            Log.w("DeviceAdminInfo", var10_13.toString());
                        }
                    }
                    var7_10 = 1;
                } while (true);
            }
            var7_10 = 1;
        } while (true);
    }

    public DeviceAdminInfo(Context context, ResolveInfo resolveInfo) throws XmlPullParserException, IOException {
        this(context, resolveInfo.activityInfo);
    }

    DeviceAdminInfo(Parcel parcel) {
        this.mActivityInfo = ActivityInfo.CREATOR.createFromParcel(parcel);
        this.mUsesPolicies = parcel.readInt();
        this.mSupportsTransferOwnership = parcel.readBoolean();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(Printer printer, String string2) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("Receiver:");
        printer.println(((StringBuilder)object).toString());
        object = this.mActivityInfo;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  ");
        ((ActivityInfo)object).dump(printer, stringBuilder.toString());
    }

    public ActivityInfo getActivityInfo() {
        return this.mActivityInfo;
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mActivityInfo.packageName, this.mActivityInfo.name);
    }

    public String getPackageName() {
        return this.mActivityInfo.packageName;
    }

    public String getReceiverName() {
        return this.mActivityInfo.name;
    }

    public String getTagForPolicy(int n) {
        return DeviceAdminInfo.sRevKnownPolicies.get((int)n).tag;
    }

    @UnsupportedAppUsage
    public ArrayList<PolicyInfo> getUsedPolicies() {
        ArrayList<PolicyInfo> arrayList = new ArrayList<PolicyInfo>();
        for (int i = 0; i < sPoliciesDisplayOrder.size(); ++i) {
            PolicyInfo policyInfo = sPoliciesDisplayOrder.get(i);
            if (!this.usesPolicy(policyInfo.ident)) continue;
            arrayList.add(policyInfo);
        }
        return arrayList;
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public CharSequence loadDescription(PackageManager packageManager) throws Resources.NotFoundException {
        if (this.mActivityInfo.descriptionRes != 0) {
            return packageManager.getText(this.mActivityInfo.packageName, this.mActivityInfo.descriptionRes, this.mActivityInfo.applicationInfo);
        }
        throw new Resources.NotFoundException();
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mActivityInfo.loadIcon(packageManager);
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        return this.mActivityInfo.loadLabel(packageManager);
    }

    public void readPoliciesFromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        this.mUsesPolicies = Integer.parseInt(xmlPullParser.getAttributeValue(null, "flags"));
    }

    public boolean supportsTransferOwnership() {
        return this.mSupportsTransferOwnership;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DeviceAdminInfo{");
        stringBuilder.append(this.mActivityInfo.name);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean usesPolicy(int n) {
        int n2 = this.mUsesPolicies;
        boolean bl = true;
        if ((n2 & 1 << n) == 0) {
            bl = false;
        }
        return bl;
    }

    public void writePoliciesToXml(XmlSerializer xmlSerializer) throws IllegalArgumentException, IllegalStateException, IOException {
        xmlSerializer.attribute(null, "flags", Integer.toString(this.mUsesPolicies));
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mActivityInfo.writeToParcel(parcel, n);
        parcel.writeInt(this.mUsesPolicies);
        parcel.writeBoolean(this.mSupportsTransferOwnership);
    }

    public static class PolicyInfo {
        public final int description;
        public final int descriptionForSecondaryUsers;
        public final int ident;
        public final int label;
        public final int labelForSecondaryUsers;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public final String tag;

        public PolicyInfo(int n, String string2, int n2, int n3) {
            this(n, string2, n2, n3, n2, n3);
        }

        public PolicyInfo(int n, String string2, int n2, int n3, int n4, int n5) {
            this.ident = n;
            this.tag = string2;
            this.label = n2;
            this.description = n3;
            this.labelForSecondaryUsers = n4;
            this.descriptionForSecondaryUsers = n5;
        }
    }

}

