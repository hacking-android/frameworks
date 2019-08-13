/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.Rlog;
import android.text.TextUtils;
import com.android.internal.telephony.uicc.IccUtils;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
public final class UiccAccessRule
implements Parcelable {
    public static final Parcelable.Creator<UiccAccessRule> CREATOR = new Parcelable.Creator<UiccAccessRule>(){

        @Override
        public UiccAccessRule createFromParcel(Parcel parcel) {
            return new UiccAccessRule(parcel);
        }

        public UiccAccessRule[] newArray(int n) {
            return new UiccAccessRule[n];
        }
    };
    private static final int ENCODING_VERSION = 1;
    private static final String TAG = "UiccAccessRule";
    private final long mAccessType;
    private final byte[] mCertificateHash;
    private final String mPackageName;

    UiccAccessRule(Parcel parcel) {
        this.mCertificateHash = parcel.createByteArray();
        this.mPackageName = parcel.readString();
        this.mAccessType = parcel.readLong();
    }

    public UiccAccessRule(byte[] arrby, String string2, long l) {
        this.mCertificateHash = arrby;
        this.mPackageName = string2;
        this.mAccessType = l;
    }

    /*
     * Exception decompiling
     */
    public static UiccAccessRule[] decodeRules(byte[] var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    public static byte[] encodeRules(UiccAccessRule[] arrobject) {
        if (arrobject == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(1);
        dataOutputStream.writeInt(arrobject.length);
        for (Object object : arrobject) {
            dataOutputStream.writeInt(((UiccAccessRule)object).mCertificateHash.length);
            dataOutputStream.write(((UiccAccessRule)object).mCertificateHash);
            if (((UiccAccessRule)object).mPackageName != null) {
                dataOutputStream.writeBoolean(true);
                dataOutputStream.writeUTF(((UiccAccessRule)object).mPackageName);
            } else {
                dataOutputStream.writeBoolean(false);
            }
            dataOutputStream.writeLong(((UiccAccessRule)object).mAccessType);
        }
        try {
            dataOutputStream.close();
            byte[] iOException = byteArrayOutputStream.toByteArray();
            return iOException;
        }
        catch (IOException iOException) {
            throw new IllegalStateException("ByteArrayOutputStream should never lead to an IOException", iOException);
        }
    }

    private static byte[] getCertHash(Signature object, String string2) {
        try {
            object = MessageDigest.getInstance(string2).digest(((Signature)object).toByteArray());
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("NoSuchAlgorithmException: ");
            ((StringBuilder)object).append(noSuchAlgorithmException);
            Rlog.e(TAG, ((StringBuilder)object).toString());
            return null;
        }
    }

    private boolean matches(byte[] arrby, String string2) {
        boolean bl = arrby != null && Arrays.equals(this.mCertificateHash, arrby) && (TextUtils.isEmpty(this.mPackageName) || this.mPackageName.equals(string2));
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (UiccAccessRule)object;
            if (!Arrays.equals(this.mCertificateHash, ((UiccAccessRule)object).mCertificateHash) || !Objects.equals(this.mPackageName, ((UiccAccessRule)object).mPackageName) || this.mAccessType != ((UiccAccessRule)object).mAccessType) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getCarrierPrivilegeStatus(PackageInfo packageInfo) {
        if (packageInfo.signatures != null && packageInfo.signatures.length != 0) {
            Signature[] arrsignature = packageInfo.signatures;
            int n = arrsignature.length;
            for (int i = 0; i < n; ++i) {
                int n2 = this.getCarrierPrivilegeStatus(arrsignature[i], packageInfo.packageName);
                if (n2 == 0) continue;
                return n2;
            }
            return 0;
        }
        throw new IllegalArgumentException("Must use GET_SIGNATURES when looking up package info");
    }

    public int getCarrierPrivilegeStatus(Signature arrby, String string2) {
        byte[] arrby2 = UiccAccessRule.getCertHash((Signature)arrby, "SHA-1");
        arrby = UiccAccessRule.getCertHash((Signature)arrby, "SHA-256");
        return this.matches(arrby2, string2) || this.matches(arrby, string2);
        {
        }
    }

    public String getCertificateHexString() {
        return IccUtils.bytesToHexString(this.mCertificateHash);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int hashCode() {
        return ((1 * 31 + Arrays.hashCode(this.mCertificateHash)) * 31 + Objects.hashCode(this.mPackageName)) * 31 + Objects.hashCode(this.mAccessType);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cert: ");
        stringBuilder.append(IccUtils.bytesToHexString(this.mCertificateHash));
        stringBuilder.append(" pkg: ");
        stringBuilder.append(this.mPackageName);
        stringBuilder.append(" access: ");
        stringBuilder.append(this.mAccessType);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.mCertificateHash);
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mAccessType);
    }

}

