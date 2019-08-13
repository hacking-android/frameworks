/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.nfc.cardemulation;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.nfc.cardemulation.AidGroup;
import android.nfc.cardemulation.CardEmulation;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

public final class ApduServiceInfo
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<ApduServiceInfo> CREATOR = new Parcelable.Creator<ApduServiceInfo>(){

        @Override
        public ApduServiceInfo createFromParcel(Parcel parcel) {
            ResolveInfo resolveInfo = ResolveInfo.CREATOR.createFromParcel(parcel);
            String string2 = parcel.readString();
            boolean bl = parcel.readInt() != 0;
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            ArrayList<AidGroup> arrayList = new ArrayList<AidGroup>();
            if (parcel.readInt() > 0) {
                parcel.readTypedList(arrayList, AidGroup.CREATOR);
            }
            ArrayList<AidGroup> arrayList2 = new ArrayList<AidGroup>();
            if (parcel.readInt() > 0) {
                parcel.readTypedList(arrayList2, AidGroup.CREATOR);
            }
            bl = parcel.readInt() != 0;
            return new ApduServiceInfo(resolveInfo, string2, arrayList, arrayList2, bl, parcel.readInt(), parcel.readInt(), parcel.readString(), string3, string4);
        }

        public ApduServiceInfo[] newArray(int n) {
            return new ApduServiceInfo[n];
        }
    };
    static final String TAG = "ApduServiceInfo";
    final int mBannerResourceId;
    final String mDescription;
    @UnsupportedAppUsage
    final HashMap<String, AidGroup> mDynamicAidGroups;
    String mOffHostName;
    final boolean mOnHost;
    final boolean mRequiresDeviceUnlock;
    @UnsupportedAppUsage
    final ResolveInfo mService;
    final String mSettingsActivityName;
    @UnsupportedAppUsage
    final HashMap<String, AidGroup> mStaticAidGroups;
    final String mStaticOffHostName;
    final int mUid;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public ApduServiceInfo(PackageManager var1_1, ResolveInfo var2_5, boolean var3_6) throws XmlPullParserException, IOException {
        block38 : {
            super();
            var4_7 = var2_5.serviceInfo;
            var5_8 = null;
            var6_9 = null;
            if (!var3_6) ** GOTO lbl18
            try {
                block40 : {
                    block39 : {
                        var7_10 = var4_7.loadXmlMetaData((PackageManager)var1_1, "android.nfc.cardemulation.host_apdu_service");
                        if (var7_10 == null) {
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            super("No android.nfc.cardemulation.host_apdu_service meta-data");
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            throw var1_1;
                        }
                        break block39;
lbl18: // 1 sources:
                        var7_10 = var4_7.loadXmlMetaData((PackageManager)var1_1, "android.nfc.cardemulation.off_host_apdu_service");
                        if (var7_10 == null) break block40;
                    }
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var8_11 = var7_10.getEventType();
                    while (var8_11 != 2 && var8_11 != 1) {
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        var8_11 = var7_10.next();
                    }
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var9_12 = var7_10.getName();
                    if (var3_6) {
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        if (!"host-apdu-service".equals(var9_12)) {
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            super("Meta-data does not start with <host-apdu-service> tag");
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            throw var1_1;
                        }
                    }
                    if (!var3_6) {
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        if (!"offhost-apdu-service".equals(var9_12)) {
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            super("Meta-data does not start with <offhost-apdu-service> tag");
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            throw var1_1;
                        }
                    }
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var9_12 = var1_1.getResourcesForApplication(var4_7.applicationInfo);
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var10_13 = Xml.asAttributeSet(var7_10);
                    if (var3_6) {
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        var1_1 = var9_12.obtainAttributes(var10_13, R.styleable.HostApduService);
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mService = var2_5;
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mDescription = var1_1.getString(0);
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mRequiresDeviceUnlock = var1_1.getBoolean(2, false);
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mBannerResourceId = var1_1.getResourceId(3, -1);
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mSettingsActivityName = var1_1.getString(1);
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mOffHostName = null;
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mStaticOffHostName = this.mOffHostName;
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        var1_1.recycle();
                    } else {
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        var1_1 = var9_12.obtainAttributes(var10_13, R.styleable.OffHostApduService);
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mService = var2_5;
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mDescription = var1_1.getString(0);
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mRequiresDeviceUnlock = false;
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mBannerResourceId = var1_1.getResourceId(2, -1);
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mSettingsActivityName = var1_1.getString(1);
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mOffHostName = var1_1.getString(3);
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        if (this.mOffHostName != null) {
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            if (this.mOffHostName.equals("eSE")) {
                                var6_9 = var7_10;
                                var5_8 = var7_10;
                                this.mOffHostName = "eSE1";
                            } else {
                                var6_9 = var7_10;
                                var5_8 = var7_10;
                                if (this.mOffHostName.equals("SIM")) {
                                    var6_9 = var7_10;
                                    var5_8 = var7_10;
                                    this.mOffHostName = "SIM1";
                                }
                            }
                        }
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        this.mStaticOffHostName = this.mOffHostName;
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        var1_1.recycle();
                    }
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var1_1 = new HashMap();
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    this.mStaticAidGroups = var1_1;
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var1_1 = new HashMap();
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    this.mDynamicAidGroups = var1_1;
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    this.mOnHost = var3_6;
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var11_14 = var7_10.getDepth();
                    var1_1 = null;
                    break block38;
                }
                var6_9 = var7_10;
                var5_8 = var7_10;
                var6_9 = var7_10;
                var5_8 = var7_10;
                super("No android.nfc.cardemulation.off_host_apdu_service meta-data");
                var6_9 = var7_10;
                var5_8 = var7_10;
                throw var1_1;
            }
            catch (Throwable var1_2) {
            }
            catch (PackageManager.NameNotFoundException var1_3) {
                var6_9 = var5_8;
                var6_9 = var5_8;
                var6_9 = var5_8;
                var1_4 = new StringBuilder();
                var6_9 = var5_8;
                var1_4.append("Unable to create context for: ");
                var6_9 = var5_8;
                var1_4.append(var4_7.packageName);
                var6_9 = var5_8;
                super(var1_4.toString());
                var6_9 = var5_8;
                throw var2_5;
            }
            if (var6_9 == null) throw var1_2;
            var6_9.close();
            throw var1_2;
        }
        do {
            block42 : {
                block41 : {
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var8_11 = var7_10.next();
                    if (var8_11 == 3) {
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        if (var7_10.getDepth() <= var11_14) break;
                    }
                    if (var8_11 == 1) break;
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var2_5 = var7_10.getName();
                    if (var8_11 == 2) {
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        if ("aid-group".equals(var2_5) && var1_1 == null) {
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            var12_15 = var9_12.obtainAttributes(var10_13, R.styleable.AidGroup);
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            var1_1 = var12_15.getString(1);
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            var13_16 = var12_15.getString(0);
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            var3_6 = "payment".equals(var1_1);
                            if (!var3_6) {
                                var1_1 = "other";
                            }
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            var2_5 = this.mStaticAidGroups.get(var1_1);
                            if (var2_5 != null) {
                                var6_9 = var7_10;
                                var5_8 = var7_10;
                                if (!"other".equals(var1_1)) {
                                    var6_9 = var7_10;
                                    var5_8 = var7_10;
                                    var6_9 = var7_10;
                                    var5_8 = var7_10;
                                    var2_5 = new StringBuilder();
                                    var6_9 = var7_10;
                                    var5_8 = var7_10;
                                    var2_5.append("Not allowing multiple aid-groups in the ");
                                    var6_9 = var7_10;
                                    var5_8 = var7_10;
                                    var2_5.append((String)var1_1);
                                    var6_9 = var7_10;
                                    var5_8 = var7_10;
                                    var2_5.append(" category");
                                    var6_9 = var7_10;
                                    var5_8 = var7_10;
                                    Log.e("ApduServiceInfo", var2_5.toString());
                                    var1_1 = null;
                                } else {
                                    var1_1 = var2_5;
                                }
                            } else {
                                var6_9 = var7_10;
                                var5_8 = var7_10;
                                var1_1 = new AidGroup((String)var1_1, (String)var13_16);
                            }
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            var12_15.recycle();
                            continue;
                        }
                    }
                    if (var8_11 == 3) {
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        if ("aid-group".equals(var2_5) && var1_1 != null) {
                            var6_9 = var7_10;
                            var5_8 = var7_10;
                            if (var1_1.aids.size() > 0) {
                                var6_9 = var7_10;
                                var5_8 = var7_10;
                                if (!this.mStaticAidGroups.containsKey(var1_1.category)) {
                                    var6_9 = var7_10;
                                    var5_8 = var7_10;
                                    this.mStaticAidGroups.put(var1_1.category, (AidGroup)var1_1);
                                }
                            } else {
                                var6_9 = var7_10;
                                var5_8 = var7_10;
                                Log.e("ApduServiceInfo", "Not adding <aid-group> with empty or invalid AIDs");
                            }
                            var1_1 = null;
                            continue;
                        }
                    }
                    if (var8_11 != 2) break block41;
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    if (!"aid-filter".equals(var2_5) || var1_1 == null) break block41;
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var13_16 = var9_12.obtainAttributes(var10_13, R.styleable.AidFilter);
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var2_5 = var13_16.getString(0).toUpperCase();
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    if (!CardEmulation.isValidAid((String)var2_5)) ** GOTO lbl-1000
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    if (!var1_1.aids.contains(var2_5)) {
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        var1_1.aids.add((String)var2_5);
                    } else lbl-1000: // 2 sources:
                    {
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        var12_15 = new StringBuilder();
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        var12_15.append("Ignoring invalid or duplicate aid: ");
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        var12_15.append((String)var2_5);
                        var6_9 = var7_10;
                        var5_8 = var7_10;
                        Log.e("ApduServiceInfo", var12_15.toString());
                    }
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var13_16.recycle();
                    continue;
                }
                if (var8_11 != 2) break block42;
                var6_9 = var7_10;
                var5_8 = var7_10;
                if (!"aid-prefix-filter".equals(var2_5) || var1_1 == null) break block42;
                var6_9 = var7_10;
                var5_8 = var7_10;
                var2_5 = var9_12.obtainAttributes(var10_13, R.styleable.AidFilter);
                var6_9 = var7_10;
                var5_8 = var7_10;
                var13_16 = var2_5.getString(0).toUpperCase().concat("*");
                var6_9 = var7_10;
                var5_8 = var7_10;
                if (!CardEmulation.isValidAid((String)var13_16)) ** GOTO lbl-1000
                var6_9 = var7_10;
                var5_8 = var7_10;
                if (!var1_1.aids.contains(var13_16)) {
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var1_1.aids.add((String)var13_16);
                } else lbl-1000: // 2 sources:
                {
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var12_15 = new StringBuilder();
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var12_15.append("Ignoring invalid or duplicate aid: ");
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    var12_15.append((String)var13_16);
                    var6_9 = var7_10;
                    var5_8 = var7_10;
                    Log.e("ApduServiceInfo", var12_15.toString());
                }
                var6_9 = var7_10;
                var5_8 = var7_10;
                var2_5.recycle();
                continue;
            }
            if (var8_11 != 2) continue;
            var6_9 = var7_10;
            var5_8 = var7_10;
            if (!var2_5.equals("aid-suffix-filter") || var1_1 == null) continue;
            var6_9 = var7_10;
            var5_8 = var7_10;
            var2_5 = var9_12.obtainAttributes(var10_13, R.styleable.AidFilter);
            var6_9 = var7_10;
            var5_8 = var7_10;
            var13_16 = var2_5.getString(0).toUpperCase().concat("#");
            var6_9 = var7_10;
            var5_8 = var7_10;
            if (!CardEmulation.isValidAid((String)var13_16)) ** GOTO lbl-1000
            var6_9 = var7_10;
            var5_8 = var7_10;
            if (!var1_1.aids.contains(var13_16)) {
                var6_9 = var7_10;
                var5_8 = var7_10;
                var1_1.aids.add((String)var13_16);
            } else lbl-1000: // 2 sources:
            {
                var6_9 = var7_10;
                var5_8 = var7_10;
                var6_9 = var7_10;
                var5_8 = var7_10;
                var12_15 = new StringBuilder();
                var6_9 = var7_10;
                var5_8 = var7_10;
                var12_15.append("Ignoring invalid or duplicate aid: ");
                var6_9 = var7_10;
                var5_8 = var7_10;
                var12_15.append((String)var13_16);
                var6_9 = var7_10;
                var5_8 = var7_10;
                Log.e("ApduServiceInfo", var12_15.toString());
            }
            var6_9 = var7_10;
            var5_8 = var7_10;
            var2_5.recycle();
        } while (true);
        var7_10.close();
        this.mUid = var4_7.applicationInfo.uid;
    }

    /*
     * WARNING - void declaration
     */
    @UnsupportedAppUsage
    public ApduServiceInfo(ResolveInfo object3, String object22, ArrayList<AidGroup> arrayList, ArrayList<AidGroup> arrayList2, boolean bl, int n, int n2, String string2, String string3, String string4) {
        void var3_9;
        void var2_5;
        void var6_12;
        void var10_16;
        void var5_11;
        void var8_14;
        void var7_13;
        void var9_15;
        void var4_10;
        this.mService = object3;
        this.mDescription = var2_5;
        this.mStaticAidGroups = new HashMap();
        this.mDynamicAidGroups = new HashMap();
        this.mOffHostName = var9_15;
        this.mStaticOffHostName = var10_16;
        boolean bl2 = var9_15 == null;
        this.mOnHost = bl2;
        this.mRequiresDeviceUnlock = var5_11;
        for (AidGroup aidGroup : var3_9) {
            this.mStaticAidGroups.put(aidGroup.category, aidGroup);
        }
        for (AidGroup aidGroup : var4_10) {
            this.mDynamicAidGroups.put(aidGroup.category, aidGroup);
        }
        this.mBannerResourceId = var6_12;
        this.mUid = var7_13;
        this.mSettingsActivityName = var8_14;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(FileDescriptor object, PrintWriter printWriter, String[] object22) {
        object = new StringBuilder();
        ((StringBuilder)object).append("    ");
        ((StringBuilder)object).append(this.getComponent());
        ((StringBuilder)object).append(" (Description: ");
        ((StringBuilder)object).append(this.getDescription());
        ((StringBuilder)object).append(")");
        printWriter.println(((StringBuilder)object).toString());
        if (this.mOnHost) {
            printWriter.println("    On Host Service");
        } else {
            printWriter.println("    Off-host Service");
            object = new StringBuilder();
            ((StringBuilder)object).append("        Current off-host SE:");
            ((StringBuilder)object).append(this.mOffHostName);
            ((StringBuilder)object).append(" static off-host SE:");
            ((StringBuilder)object).append(this.mStaticOffHostName);
            printWriter.println(((StringBuilder)object).toString());
        }
        printWriter.println("    Static AID groups:");
        for (AidGroup aidGroup : this.mStaticAidGroups.values()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("        Category: ");
            stringBuilder.append(aidGroup.category);
            printWriter.println(stringBuilder.toString());
            for (String string2 : aidGroup.aids) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("            AID: ");
                stringBuilder2.append(string2);
                printWriter.println(stringBuilder2.toString());
            }
        }
        printWriter.println("    Dynamic AID groups:");
        for (AidGroup aidGroup : this.mDynamicAidGroups.values()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("        Category: ");
            stringBuilder.append(aidGroup.category);
            printWriter.println(stringBuilder.toString());
            for (String string3 : aidGroup.aids) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("            AID: ");
                stringBuilder3.append(string3);
                printWriter.println(stringBuilder3.toString());
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("    Settings Activity: ");
        ((StringBuilder)object).append(this.mSettingsActivityName);
        printWriter.println(((StringBuilder)object).toString());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ApduServiceInfo)) {
            return false;
        }
        return ((ApduServiceInfo)object).getComponent().equals(this.getComponent());
    }

    public ArrayList<AidGroup> getAidGroups() {
        ArrayList<AidGroup> arrayList = new ArrayList<AidGroup>();
        Iterator<Map.Entry<String, AidGroup>> iterator = this.mDynamicAidGroups.entrySet().iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getValue());
        }
        for (Map.Entry<String, AidGroup> entry : this.mStaticAidGroups.entrySet()) {
            if (this.mDynamicAidGroups.containsKey(entry.getKey())) continue;
            arrayList.add(entry.getValue());
        }
        return arrayList;
    }

    public List<String> getAids() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator<AidGroup> iterator = this.getAidGroups().iterator();
        while (iterator.hasNext()) {
            arrayList.addAll(iterator.next().aids);
        }
        return arrayList;
    }

    public String getCategoryForAid(String string2) {
        for (AidGroup aidGroup : this.getAidGroups()) {
            if (!aidGroup.aids.contains(string2.toUpperCase())) continue;
            return aidGroup.category;
        }
        return null;
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    @UnsupportedAppUsage
    public String getDescription() {
        return this.mDescription;
    }

    public AidGroup getDynamicAidGroupForCategory(String string2) {
        return this.mDynamicAidGroups.get(string2);
    }

    public String getOffHostSecureElement() {
        return this.mOffHostName;
    }

    public List<String> getPrefixAids() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator<AidGroup> iterator = this.getAidGroups().iterator();
        while (iterator.hasNext()) {
            for (String string2 : iterator.next().aids) {
                if (!string2.endsWith("*")) continue;
                arrayList.add(string2);
            }
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    public String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public List<String> getSubsetAids() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator<AidGroup> iterator = this.getAidGroups().iterator();
        while (iterator.hasNext()) {
            for (String string2 : iterator.next().aids) {
                if (!string2.endsWith("#")) continue;
                arrayList.add(string2);
            }
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    public int getUid() {
        return this.mUid;
    }

    public boolean hasCategory(String string2) {
        boolean bl = this.mStaticAidGroups.containsKey(string2) || this.mDynamicAidGroups.containsKey(string2);
        return bl;
    }

    public int hashCode() {
        return this.getComponent().hashCode();
    }

    @UnsupportedAppUsage
    public boolean isOnHost() {
        return this.mOnHost;
    }

    public CharSequence loadAppLabel(PackageManager object) {
        try {
            object = ((PackageManager)object).getApplicationLabel(((PackageManager)object).getApplicationInfo(this.mService.resolvePackageName, 128));
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    @UnsupportedAppUsage
    public Drawable loadBanner(PackageManager object) {
        try {
            object = ((PackageManager)object).getResourcesForApplication(this.mService.serviceInfo.packageName).getDrawable(this.mBannerResourceId);
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e(TAG, "Could not load banner.");
            return null;
        }
        catch (Resources.NotFoundException notFoundException) {
            Log.e(TAG, "Could not load banner.");
            return null;
        }
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        return this.mService.loadLabel(packageManager);
    }

    public boolean removeDynamicAidGroupForCategory(String string2) {
        boolean bl = this.mDynamicAidGroups.remove(string2) != null;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean requiresUnlock() {
        return this.mRequiresDeviceUnlock;
    }

    public void setOffHostSecureElement(String string2) {
        this.mOffHostName = string2;
    }

    public void setOrReplaceDynamicAidGroup(AidGroup aidGroup) {
        this.mDynamicAidGroups.put(aidGroup.getCategory(), aidGroup);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ApduService: ");
        stringBuilder.append(this.getComponent());
        Iterator<AidGroup> iterator = new StringBuilder();
        ((StringBuilder)((Object)iterator)).append(", description: ");
        ((StringBuilder)((Object)iterator)).append(this.mDescription);
        stringBuilder.append(((StringBuilder)((Object)iterator)).toString());
        stringBuilder.append(", Static AID Groups: ");
        iterator = this.mStaticAidGroups.values().iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(((AidGroup)iterator.next()).toString());
        }
        stringBuilder.append(", Dynamic AID Groups: ");
        iterator = this.mDynamicAidGroups.values().iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next().toString());
        }
        return stringBuilder.toString();
    }

    public void unsetOffHostSecureElement() {
        this.mOffHostName = this.mStaticOffHostName;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mService.writeToParcel(parcel, n);
        parcel.writeString(this.mDescription);
        parcel.writeInt((int)this.mOnHost);
        parcel.writeString(this.mOffHostName);
        parcel.writeString(this.mStaticOffHostName);
        parcel.writeInt(this.mStaticAidGroups.size());
        if (this.mStaticAidGroups.size() > 0) {
            parcel.writeTypedList(new ArrayList<AidGroup>(this.mStaticAidGroups.values()));
        }
        parcel.writeInt(this.mDynamicAidGroups.size());
        if (this.mDynamicAidGroups.size() > 0) {
            parcel.writeTypedList(new ArrayList<AidGroup>(this.mDynamicAidGroups.values()));
        }
        parcel.writeInt((int)this.mRequiresDeviceUnlock);
        parcel.writeInt(this.mBannerResourceId);
        parcel.writeInt(this.mUid);
        parcel.writeString(this.mSettingsActivityName);
    }

}

