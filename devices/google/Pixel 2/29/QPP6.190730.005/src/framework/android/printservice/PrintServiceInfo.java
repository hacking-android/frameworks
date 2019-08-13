/*
 * Decompiled with CFR 0.145.
 */
package android.printservice;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class PrintServiceInfo
implements Parcelable {
    public static final Parcelable.Creator<PrintServiceInfo> CREATOR;
    private static final String LOG_TAG;
    private static final String TAG_PRINT_SERVICE = "print-service";
    private final String mAddPrintersActivityName;
    private final String mAdvancedPrintOptionsActivityName;
    private final String mId;
    private boolean mIsEnabled;
    private final ResolveInfo mResolveInfo;
    private final String mSettingsActivityName;

    static {
        LOG_TAG = PrintServiceInfo.class.getSimpleName();
        CREATOR = new Parcelable.Creator<PrintServiceInfo>(){

            @Override
            public PrintServiceInfo createFromParcel(Parcel parcel) {
                return new PrintServiceInfo(parcel);
            }

            public PrintServiceInfo[] newArray(int n) {
                return new PrintServiceInfo[n];
            }
        };
    }

    public PrintServiceInfo(ResolveInfo resolveInfo, String string2, String string3, String string4) {
        this.mId = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name).flattenToString();
        this.mResolveInfo = resolveInfo;
        this.mSettingsActivityName = string2;
        this.mAddPrintersActivityName = string3;
        this.mAdvancedPrintOptionsActivityName = string4;
    }

    public PrintServiceInfo(Parcel parcel) {
        this.mId = parcel.readString();
        boolean bl = parcel.readByte() != 0;
        this.mIsEnabled = bl;
        this.mResolveInfo = (ResolveInfo)parcel.readParcelable(null);
        this.mSettingsActivityName = parcel.readString();
        this.mAddPrintersActivityName = parcel.readString();
        this.mAdvancedPrintOptionsActivityName = parcel.readString();
    }

    /*
     * Exception decompiling
     */
    public static PrintServiceInfo create(Context var0, ResolveInfo var1_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[WHILELOOP]], but top level block is 0[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (PrintServiceInfo)object;
        String string2 = this.mId;
        return !(string2 == null ? ((PrintServiceInfo)object).mId != null : !string2.equals(((PrintServiceInfo)object).mId));
    }

    public String getAddPrintersActivityName() {
        return this.mAddPrintersActivityName;
    }

    public String getAdvancedOptionsActivityName() {
        return this.mAdvancedPrintOptionsActivityName;
    }

    public ComponentName getComponentName() {
        return new ComponentName(this.mResolveInfo.serviceInfo.packageName, this.mResolveInfo.serviceInfo.name);
    }

    public String getId() {
        return this.mId;
    }

    public ResolveInfo getResolveInfo() {
        return this.mResolveInfo;
    }

    public String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public int hashCode() {
        String string2 = this.mId;
        int n = string2 == null ? 0 : string2.hashCode();
        return n + 31;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public void setIsEnabled(boolean bl) {
        this.mIsEnabled = bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PrintServiceInfo{");
        stringBuilder.append("id=");
        stringBuilder.append(this.mId);
        stringBuilder.append("isEnabled=");
        stringBuilder.append(this.mIsEnabled);
        stringBuilder.append(", resolveInfo=");
        stringBuilder.append(this.mResolveInfo);
        stringBuilder.append(", settingsActivityName=");
        stringBuilder.append(this.mSettingsActivityName);
        stringBuilder.append(", addPrintersActivityName=");
        stringBuilder.append(this.mAddPrintersActivityName);
        stringBuilder.append(", advancedPrintOptionsActivityName=");
        stringBuilder.append(this.mAdvancedPrintOptionsActivityName);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mId);
        parcel.writeByte((byte)(this.mIsEnabled ? 1 : 0));
        parcel.writeParcelable(this.mResolveInfo, 0);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeString(this.mAddPrintersActivityName);
        parcel.writeString(this.mAdvancedPrintOptionsActivityName);
    }

}

