/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.cdma;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.telephony.Rlog;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.cdma.EriInfo;
import java.util.HashMap;

public class EriManager {
    private static final boolean DBG = true;
    static final int ERI_FROM_FILE_SYSTEM = 1;
    static final int ERI_FROM_MODEM = 2;
    public static final int ERI_FROM_XML = 0;
    private static final String LOG_TAG = "EriManager";
    private static final boolean VDBG = false;
    private Context mContext;
    private EriFile mEriFile;
    private int mEriFileSource = 0;
    private boolean mIsEriFileLoaded;
    private final Phone mPhone;

    public EriManager(Phone phone, int n) {
        this.mPhone = phone;
        this.mContext = this.mPhone.getContext();
        this.mEriFileSource = n;
        this.mEriFile = new EriFile();
    }

    @UnsupportedAppUsage
    private EriDisplayInformation getEriDisplayInformation(int n, int n2) {
        Object object;
        block21 : {
            block20 : {
                if (this.mIsEriFileLoaded && (object = this.getEriInfo(n)) != null) {
                    return new EriDisplayInformation(((EriInfo)object).iconIndex, ((EriInfo)object).iconMode, ((EriInfo)object).eriText);
                }
                switch (n) {
                    default: {
                        if (this.mIsEriFileLoaded) break block20;
                        Rlog.d((String)LOG_TAG, (String)"ERI File not loaded");
                        if (n2 <= 2) break;
                        object = new EriDisplayInformation(2, 1, this.mContext.getText(17040947).toString());
                        break block21;
                    }
                    case 12: {
                        object = new EriDisplayInformation(n, 0, this.mContext.getText(17040946).toString());
                        break block21;
                    }
                    case 11: {
                        object = new EriDisplayInformation(n, 0, this.mContext.getText(17040945).toString());
                        break block21;
                    }
                    case 10: {
                        object = new EriDisplayInformation(n, 0, this.mContext.getText(17040944).toString());
                        break block21;
                    }
                    case 9: {
                        object = new EriDisplayInformation(n, 0, this.mContext.getText(17040954).toString());
                        break block21;
                    }
                    case 8: {
                        object = new EriDisplayInformation(n, 0, this.mContext.getText(17040953).toString());
                        break block21;
                    }
                    case 7: {
                        object = new EriDisplayInformation(n, 0, this.mContext.getText(17040952).toString());
                        break block21;
                    }
                    case 6: {
                        object = new EriDisplayInformation(n, 0, this.mContext.getText(17040951).toString());
                        break block21;
                    }
                    case 5: {
                        object = new EriDisplayInformation(n, 0, this.mContext.getText(17040950).toString());
                        break block21;
                    }
                    case 4: {
                        object = new EriDisplayInformation(n, 0, this.mContext.getText(17040949).toString());
                        break block21;
                    }
                    case 3: {
                        object = new EriDisplayInformation(n, 0, this.mContext.getText(17040948).toString());
                        break block21;
                    }
                    case 2: {
                        object = new EriDisplayInformation(2, 1, this.mContext.getText(17040947).toString());
                        break block21;
                    }
                    case 1: {
                        object = new EriDisplayInformation(1, 0, this.mContext.getText(17040943).toString());
                        break block21;
                    }
                    case 0: {
                        object = new EriDisplayInformation(0, 0, this.mContext.getText(17040942).toString());
                        break block21;
                    }
                }
                object = n2 != 0 ? (n2 != 1 ? (n2 != 2 ? new EriDisplayInformation(-1, -1, "ERI text") : new EriDisplayInformation(2, 1, this.mContext.getText(17040947).toString())) : new EriDisplayInformation(1, 0, this.mContext.getText(17040943).toString())) : new EriDisplayInformation(0, 0, this.mContext.getText(17040942).toString());
                break block21;
            }
            EriInfo eriInfo = this.getEriInfo(n);
            object = this.getEriInfo(n2);
            if (eriInfo == null) {
                if (object == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("ERI defRoamInd ");
                    ((StringBuilder)object).append(n2);
                    ((StringBuilder)object).append(" not found in ERI file ...on");
                    Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
                    object = new EriDisplayInformation(0, 0, this.mContext.getText(17040942).toString());
                } else {
                    object = new EriDisplayInformation(((EriInfo)object).iconIndex, ((EriInfo)object).iconMode, ((EriInfo)object).eriText);
                }
            } else {
                object = new EriDisplayInformation(eriInfo.iconIndex, eriInfo.iconMode, eriInfo.eriText);
            }
        }
        return object;
    }

    private EriInfo getEriInfo(int n) {
        if (this.mEriFile.mRoamIndTable.containsKey(n)) {
            return this.mEriFile.mRoamIndTable.get(n);
        }
        return null;
    }

    private void loadEriFileFromFileSystem() {
    }

    private void loadEriFileFromModem() {
    }

    /*
     * Exception decompiling
     */
    private void loadEriFileFromXml() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 15[UNCONDITIONALDOLOOP]
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

    public void dispose() {
        this.mEriFile = new EriFile();
        this.mIsEriFileLoaded = false;
    }

    public int getCdmaEriIconIndex(int n, int n2) {
        return this.getEriDisplayInformation((int)n, (int)n2).mEriIconIndex;
    }

    public int getCdmaEriIconMode(int n, int n2) {
        return this.getEriDisplayInformation((int)n, (int)n2).mEriIconMode;
    }

    public String getCdmaEriText(int n, int n2) {
        return this.getEriDisplayInformation((int)n, (int)n2).mEriIconText;
    }

    public int getEriFileType() {
        return this.mEriFile.mEriFileType;
    }

    public int getEriFileVersion() {
        return this.mEriFile.mVersionNumber;
    }

    public int getEriNumberOfEntries() {
        return this.mEriFile.mNumberOfEriEntries;
    }

    public boolean isEriFileLoaded() {
        return this.mIsEriFileLoaded;
    }

    public void loadEriFile() {
        int n = this.mEriFileSource;
        if (n != 1) {
            if (n != 2) {
                this.loadEriFileFromXml();
            } else {
                this.loadEriFileFromModem();
            }
        } else {
            this.loadEriFileFromFileSystem();
        }
    }

    class EriDisplayInformation {
        int mEriIconIndex;
        int mEriIconMode;
        @UnsupportedAppUsage
        String mEriIconText;

        EriDisplayInformation(int n, int n2, String string) {
            this.mEriIconIndex = n;
            this.mEriIconMode = n2;
            this.mEriIconText = string;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("EriDisplayInformation: { IconIndex: ");
            stringBuilder.append(this.mEriIconIndex);
            stringBuilder.append(" EriIconMode: ");
            stringBuilder.append(this.mEriIconMode);
            stringBuilder.append(" EriIconText: ");
            stringBuilder.append(this.mEriIconText);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    class EriFile {
        String[] mCallPromptId = new String[]{"", "", ""};
        int mEriFileType = -1;
        int mNumberOfEriEntries = 0;
        HashMap<Integer, EriInfo> mRoamIndTable = new HashMap();
        int mVersionNumber = -1;

        EriFile() {
        }
    }

}

