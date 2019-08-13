/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.nfc.cardemulation;

import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.nfc.cardemulation.NfcFCardEmulation;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import org.xmlpull.v1.XmlPullParserException;

public final class NfcFServiceInfo
implements Parcelable {
    public static final Parcelable.Creator<NfcFServiceInfo> CREATOR = new Parcelable.Creator<NfcFServiceInfo>(){

        @Override
        public NfcFServiceInfo createFromParcel(Parcel parcel) {
            ResolveInfo resolveInfo = ResolveInfo.CREATOR.createFromParcel(parcel);
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            String string4 = parcel.readInt() != 0 ? parcel.readString() : null;
            String string5 = parcel.readString();
            String string6 = parcel.readInt() != 0 ? parcel.readString() : null;
            return new NfcFServiceInfo(resolveInfo, string2, string3, string4, string5, string6, parcel.readInt(), parcel.readString());
        }

        public NfcFServiceInfo[] newArray(int n) {
            return new NfcFServiceInfo[n];
        }
    };
    private static final String DEFAULT_T3T_PMM = "FFFFFFFFFFFFFFFF";
    static final String TAG = "NfcFServiceInfo";
    final String mDescription;
    String mDynamicNfcid2;
    String mDynamicSystemCode;
    final String mNfcid2;
    final ResolveInfo mService;
    final String mSystemCode;
    final String mT3tPmm;
    final int mUid;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public NfcFServiceInfo(PackageManager var1_1, ResolveInfo var2_5) throws XmlPullParserException, IOException {
        block21 : {
            super();
            var3_6 = var2_5.serviceInfo;
            var4_7 = null;
            var5_8 = null;
            try {
                var6_9 = var3_6.loadXmlMetaData((PackageManager)var1_1, "android.nfc.cardemulation.host_nfcf_service");
                if (var6_9 == null) ** GOTO lbl59
                var5_8 = var6_9;
                var4_7 = var6_9;
                var7_10 = var6_9.getEventType();
                while (var7_10 != 2 && var7_10 != 1) {
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    var7_10 = var6_9.next();
                }
                var5_8 = var6_9;
                var4_7 = var6_9;
                if ("host-nfcf-service".equals(var6_9.getName())) {
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    var8_11 = var1_1.getResourcesForApplication(var3_6.applicationInfo);
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    var9_12 = Xml.asAttributeSet(var6_9);
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    var1_1 = var8_11.obtainAttributes(var9_12, R.styleable.HostNfcFService);
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    this.mService = var2_5;
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    this.mDescription = var1_1.getString(0);
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    this.mDynamicSystemCode = null;
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    this.mDynamicNfcid2 = null;
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    var1_1.recycle();
                    var10_13 = null;
                    var2_5 = null;
                    var1_1 = null;
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    var7_10 = var6_9.getDepth();
                    break block21;
                }
                var5_8 = var6_9;
                var4_7 = var6_9;
                var5_8 = var6_9;
                var4_7 = var6_9;
                super("Meta-data does not start with <host-nfcf-service> tag");
                var5_8 = var6_9;
                var4_7 = var6_9;
                throw var1_1;
lbl59: // 1 sources:
                var5_8 = var6_9;
                var4_7 = var6_9;
                var5_8 = var6_9;
                var4_7 = var6_9;
                super("No android.nfc.cardemulation.host_nfcf_service meta-data");
                var5_8 = var6_9;
                var4_7 = var6_9;
                throw var1_1;
            }
            catch (Throwable var1_2) {
            }
            catch (PackageManager.NameNotFoundException var1_3) {
                var5_8 = var4_7;
                var5_8 = var4_7;
                var5_8 = var4_7;
                var1_4 = new StringBuilder();
                var5_8 = var4_7;
                var1_4.append("Unable to create context for: ");
                var5_8 = var4_7;
                var1_4.append(var3_6.packageName);
                var5_8 = var4_7;
                super(var1_4.toString());
                var5_8 = var4_7;
                throw var2_5;
            }
            if (var5_8 == null) throw var1_2;
            var5_8.close();
            throw var1_2;
        }
        do {
            var5_8 = var6_9;
            var4_7 = var6_9;
            var11_14 = var6_9.next();
            var12_15 = "NULL";
            if (var11_14 == 3) {
                var5_8 = var6_9;
                var4_7 = var6_9;
                if (var6_9.getDepth() <= var7_10) break;
            }
            if (var11_14 == 1) break;
            var5_8 = var6_9;
            var4_7 = var6_9;
            var12_15 = var6_9.getName();
            if (var11_14 == 2) {
                var5_8 = var6_9;
                var4_7 = var6_9;
                if ("system-code-filter".equals(var12_15) && var10_13 == null) {
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    var12_15 = var8_11.obtainAttributes(var9_12, R.styleable.SystemCodeFilter);
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    var10_13 = var12_15.getString(0).toUpperCase();
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    if (!NfcFCardEmulation.isValidSystemCode(var10_13)) {
                        var5_8 = var6_9;
                        var4_7 = var6_9;
                        if (!var10_13.equalsIgnoreCase("NULL")) {
                            var5_8 = var6_9;
                            var4_7 = var6_9;
                            var5_8 = var6_9;
                            var4_7 = var6_9;
                            var13_16 = new StringBuilder();
                            var5_8 = var6_9;
                            var4_7 = var6_9;
                            var13_16.append("Invalid System Code: ");
                            var5_8 = var6_9;
                            var4_7 = var6_9;
                            var13_16.append(var10_13);
                            var5_8 = var6_9;
                            var4_7 = var6_9;
                            Log.e("NfcFServiceInfo", var13_16.toString());
                            var10_13 = null;
                        }
                    }
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    var12_15.recycle();
                    continue;
                }
            }
            if (var11_14 == 2) {
                var5_8 = var6_9;
                var4_7 = var6_9;
                if ("nfcid2-filter".equals(var12_15) && var2_5 == null) {
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    var12_15 = var8_11.obtainAttributes(var9_12, R.styleable.Nfcid2Filter);
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    var2_5 = var12_15.getString(0).toUpperCase();
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    if (!var2_5.equalsIgnoreCase("RANDOM")) {
                        var5_8 = var6_9;
                        var4_7 = var6_9;
                        if (!var2_5.equalsIgnoreCase("NULL")) {
                            var5_8 = var6_9;
                            var4_7 = var6_9;
                            if (!NfcFCardEmulation.isValidNfcid2((String)var2_5)) {
                                var5_8 = var6_9;
                                var4_7 = var6_9;
                                var5_8 = var6_9;
                                var4_7 = var6_9;
                                var13_16 = new StringBuilder();
                                var5_8 = var6_9;
                                var4_7 = var6_9;
                                var13_16.append("Invalid NFCID2: ");
                                var5_8 = var6_9;
                                var4_7 = var6_9;
                                var13_16.append((String)var2_5);
                                var5_8 = var6_9;
                                var4_7 = var6_9;
                                Log.e("NfcFServiceInfo", var13_16.toString());
                                var2_5 = null;
                            }
                        }
                    }
                    var5_8 = var6_9;
                    var4_7 = var6_9;
                    var12_15.recycle();
                    continue;
                }
            }
            if (var11_14 != 2) continue;
            var5_8 = var6_9;
            var4_7 = var6_9;
            if (!var12_15.equals("t3tPmm-filter") || var1_1 != null) continue;
            var5_8 = var6_9;
            var4_7 = var6_9;
            var12_15 = var8_11.obtainAttributes(var9_12, R.styleable.T3tPmmFilter);
            var5_8 = var6_9;
            var4_7 = var6_9;
            var1_1 = var12_15.getString(0).toUpperCase();
            var5_8 = var6_9;
            var4_7 = var6_9;
            var12_15.recycle();
        } while (true);
        if (var10_13 == null) {
            var10_13 = "NULL";
        }
        var5_8 = var6_9;
        var4_7 = var6_9;
        this.mSystemCode = var10_13;
        if (var2_5 == null) {
            var2_5 = var12_15;
        }
        var5_8 = var6_9;
        var4_7 = var6_9;
        this.mNfcid2 = var2_5;
        if (var1_1 == null) {
            var1_1 = "FFFFFFFFFFFFFFFF";
        }
        var5_8 = var6_9;
        var4_7 = var6_9;
        this.mT3tPmm = var1_1;
        var6_9.close();
        this.mUid = var3_6.applicationInfo.uid;
    }

    public NfcFServiceInfo(ResolveInfo resolveInfo, String string2, String string3, String string4, String string5, String string6, int n, String string7) {
        this.mService = resolveInfo;
        this.mDescription = string2;
        this.mSystemCode = string3;
        this.mDynamicSystemCode = string4;
        this.mNfcid2 = string5;
        this.mDynamicNfcid2 = string6;
        this.mUid = n;
        this.mT3tPmm = string7;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        object = new StringBuilder();
        ((StringBuilder)object).append("    ");
        ((StringBuilder)object).append(this.getComponent());
        ((StringBuilder)object).append(" (Description: ");
        ((StringBuilder)object).append(this.getDescription());
        ((StringBuilder)object).append(")");
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("    System Code: ");
        ((StringBuilder)object).append(this.getSystemCode());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("    NFCID2: ");
        ((StringBuilder)object).append(this.getNfcid2());
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("    T3tPmm: ");
        ((StringBuilder)object).append(this.getT3tPmm());
        printWriter.println(((StringBuilder)object).toString());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof NfcFServiceInfo)) {
            return false;
        }
        if (!((NfcFServiceInfo)(object = (NfcFServiceInfo)object)).getComponent().equals(this.getComponent())) {
            return false;
        }
        if (!((NfcFServiceInfo)object).mSystemCode.equalsIgnoreCase(this.mSystemCode)) {
            return false;
        }
        if (!((NfcFServiceInfo)object).mNfcid2.equalsIgnoreCase(this.mNfcid2)) {
            return false;
        }
        return ((NfcFServiceInfo)object).mT3tPmm.equalsIgnoreCase(this.mT3tPmm);
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getNfcid2() {
        String string2;
        String string3 = string2 = this.mDynamicNfcid2;
        if (string2 == null) {
            string3 = this.mNfcid2;
        }
        return string3;
    }

    public String getSystemCode() {
        String string2;
        String string3 = string2 = this.mDynamicSystemCode;
        if (string2 == null) {
            string3 = this.mSystemCode;
        }
        return string3;
    }

    public String getT3tPmm() {
        return this.mT3tPmm;
    }

    public int getUid() {
        return this.mUid;
    }

    public int hashCode() {
        return this.getComponent().hashCode();
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        return this.mService.loadLabel(packageManager);
    }

    public void setOrReplaceDynamicNfcid2(String string2) {
        this.mDynamicNfcid2 = string2;
    }

    public void setOrReplaceDynamicSystemCode(String string2) {
        this.mDynamicSystemCode = string2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("NfcFService: ");
        stringBuilder.append(this.getComponent());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", description: ");
        stringBuilder2.append(this.mDescription);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", System Code: ");
        stringBuilder2.append(this.mSystemCode);
        stringBuilder.append(stringBuilder2.toString());
        if (this.mDynamicSystemCode != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", dynamic System Code: ");
            stringBuilder2.append(this.mDynamicSystemCode);
            stringBuilder.append(stringBuilder2.toString());
        }
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", NFCID2: ");
        stringBuilder2.append(this.mNfcid2);
        stringBuilder.append(stringBuilder2.toString());
        if (this.mDynamicNfcid2 != null) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", dynamic NFCID2: ");
            stringBuilder2.append(this.mDynamicNfcid2);
            stringBuilder.append(stringBuilder2.toString());
        }
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", T3T PMM:");
        stringBuilder2.append(this.mT3tPmm);
        stringBuilder.append(stringBuilder2.toString());
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mService.writeToParcel(parcel, n);
        parcel.writeString(this.mDescription);
        parcel.writeString(this.mSystemCode);
        String string2 = this.mDynamicSystemCode;
        int n2 = 1;
        n = string2 != null ? 1 : 0;
        parcel.writeInt(n);
        string2 = this.mDynamicSystemCode;
        if (string2 != null) {
            parcel.writeString(string2);
        }
        parcel.writeString(this.mNfcid2);
        n = this.mDynamicNfcid2 != null ? n2 : 0;
        parcel.writeInt(n);
        string2 = this.mDynamicNfcid2;
        if (string2 != null) {
            parcel.writeString(string2);
        }
        parcel.writeInt(this.mUid);
        parcel.writeString(this.mT3tPmm);
    }

}

