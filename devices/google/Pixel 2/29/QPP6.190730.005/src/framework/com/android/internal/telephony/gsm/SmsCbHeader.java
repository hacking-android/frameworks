/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.gsm;

import android.telephony.SmsCbCmasInfo;
import android.telephony.SmsCbEtwsInfo;
import java.util.Arrays;

public class SmsCbHeader {
    static final int FORMAT_ETWS_PRIMARY = 3;
    static final int FORMAT_GSM = 1;
    static final int FORMAT_UMTS = 2;
    private static final int MESSAGE_TYPE_CBS_MESSAGE = 1;
    static final int PDU_HEADER_LENGTH = 6;
    private static final int PDU_LENGTH_ETWS = 56;
    private static final int PDU_LENGTH_GSM = 88;
    private final SmsCbCmasInfo mCmasInfo;
    private final int mDataCodingScheme;
    private final SmsCbEtwsInfo mEtwsInfo;
    private final int mFormat;
    private final int mGeographicalScope;
    private final int mMessageIdentifier;
    private final int mNrOfPages;
    private final int mPageIndex;
    private final int mSerialNumber;

    public SmsCbHeader(byte[] object) throws IllegalArgumentException {
        block11 : {
            int n;
            block16 : {
                int n2;
                int n3;
                int n4;
                block15 : {
                    block12 : {
                        block14 : {
                            block13 : {
                                if (object == null || ((byte[])object).length < 6) break block11;
                                if (((byte[])object).length > 88) break block12;
                                this.mGeographicalScope = (object[0] & 192) >>> 6;
                                this.mSerialNumber = (object[0] & 255) << 8 | object[1] & 255;
                                this.mMessageIdentifier = (object[2] & 255) << 8 | object[3] & 255;
                                if (this.isEtwsMessage() && ((Object)object).length <= 56) {
                                    this.mFormat = 3;
                                    this.mDataCodingScheme = -1;
                                    this.mPageIndex = -1;
                                    this.mNrOfPages = -1;
                                    boolean bl = (object[4] & true) != 0;
                                    boolean bl2 = (object[5] & 128) != 0;
                                    Object object2 = object[4];
                                    object = ((Object)object).length > 6 ? Arrays.copyOfRange((byte[])object, 6, ((Object)object).length) : null;
                                    this.mEtwsInfo = new SmsCbEtwsInfo((object2 & 254) >>> 1, bl, bl2, true, (byte[])object);
                                    this.mCmasInfo = null;
                                    return;
                                }
                                this.mFormat = 1;
                                this.mDataCodingScheme = object[4] & 255;
                                n4 = (object[5] & 240) >>> 4;
                                n2 = object[5] & 15;
                                if (n4 == 0 || n2 == 0) break block13;
                                n3 = n4;
                                n = n2;
                                if (n4 <= n2) break block14;
                            }
                            n3 = 1;
                            n = 1;
                        }
                        this.mPageIndex = n3;
                        this.mNrOfPages = n;
                        break block15;
                    }
                    this.mFormat = 2;
                    n = object[0];
                    if (n != 1) break block16;
                    this.mMessageIdentifier = (object[1] & 255) << 8 | object[2] & 255;
                    this.mGeographicalScope = (object[3] & 192) >>> 6;
                    this.mSerialNumber = (object[3] & 255) << 8 | object[4] & 255;
                    this.mDataCodingScheme = object[5] & 255;
                    this.mPageIndex = 1;
                    this.mNrOfPages = 1;
                }
                if (this.isEtwsMessage()) {
                    boolean bl = this.isEtwsEmergencyUserAlert();
                    boolean bl3 = this.isEtwsPopupAlert();
                    this.mEtwsInfo = new SmsCbEtwsInfo(this.getEtwsWarningType(), bl, bl3, false, null);
                    this.mCmasInfo = null;
                } else if (this.isCmasMessage()) {
                    n4 = this.getCmasMessageClass();
                    n2 = this.getCmasSeverity();
                    n3 = this.getCmasUrgency();
                    n = this.getCmasCertainty();
                    this.mEtwsInfo = null;
                    this.mCmasInfo = new SmsCbCmasInfo(n4, -1, -1, n2, n3, n);
                } else {
                    this.mEtwsInfo = null;
                    this.mCmasInfo = null;
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported message type ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("Illegal PDU");
    }

    /*
     * Exception decompiling
     */
    private int getCmasCertainty() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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

    private int getCmasMessageClass() {
        switch (this.mMessageIdentifier) {
            default: {
                return -1;
            }
            case 4382: 
            case 4395: {
                return 6;
            }
            case 4381: 
            case 4394: {
                return 5;
            }
            case 4380: 
            case 4393: {
                return 4;
            }
            case 4379: 
            case 4392: {
                return 3;
            }
            case 4373: 
            case 4374: 
            case 4375: 
            case 4376: 
            case 4377: 
            case 4378: 
            case 4386: 
            case 4387: 
            case 4388: 
            case 4389: 
            case 4390: 
            case 4391: {
                return 2;
            }
            case 4371: 
            case 4372: 
            case 4384: 
            case 4385: {
                return 1;
            }
            case 4370: 
            case 4383: 
        }
        return 0;
    }

    /*
     * Exception decompiling
     */
    private int getCmasSeverity() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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

    /*
     * Exception decompiling
     */
    private int getCmasUrgency() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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

    private int getEtwsWarningType() {
        return this.mMessageIdentifier - 4352;
    }

    private boolean isCmasMessage() {
        int n = this.mMessageIdentifier;
        boolean bl = n >= 4370 && n <= 4399;
        return bl;
    }

    private boolean isEtwsEmergencyUserAlert() {
        boolean bl = (this.mSerialNumber & 8192) != 0;
        return bl;
    }

    private boolean isEtwsMessage() {
        boolean bl = (this.mMessageIdentifier & 65528) == 4352;
        return bl;
    }

    private boolean isEtwsPopupAlert() {
        boolean bl = (this.mSerialNumber & 4096) != 0;
        return bl;
    }

    SmsCbCmasInfo getCmasInfo() {
        return this.mCmasInfo;
    }

    int getDataCodingScheme() {
        return this.mDataCodingScheme;
    }

    SmsCbEtwsInfo getEtwsInfo() {
        return this.mEtwsInfo;
    }

    int getGeographicalScope() {
        return this.mGeographicalScope;
    }

    int getNumberOfPages() {
        return this.mNrOfPages;
    }

    int getPageIndex() {
        return this.mPageIndex;
    }

    int getSerialNumber() {
        return this.mSerialNumber;
    }

    int getServiceCategory() {
        return this.mMessageIdentifier;
    }

    boolean isEmergencyMessage() {
        int n = this.mMessageIdentifier;
        boolean bl = n >= 4352 && n <= 6399;
        return bl;
    }

    boolean isEtwsPrimaryNotification() {
        boolean bl = this.mFormat == 3;
        return bl;
    }

    boolean isUmtsFormat() {
        boolean bl = this.mFormat == 2;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SmsCbHeader{GS=");
        stringBuilder.append(this.mGeographicalScope);
        stringBuilder.append(", serialNumber=0x");
        stringBuilder.append(Integer.toHexString(this.mSerialNumber));
        stringBuilder.append(", messageIdentifier=0x");
        stringBuilder.append(Integer.toHexString(this.mMessageIdentifier));
        stringBuilder.append(", format=");
        stringBuilder.append(this.mFormat);
        stringBuilder.append(", DCS=0x");
        stringBuilder.append(Integer.toHexString(this.mDataCodingScheme));
        stringBuilder.append(", page ");
        stringBuilder.append(this.mPageIndex);
        stringBuilder.append(" of ");
        stringBuilder.append(this.mNrOfPages);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

