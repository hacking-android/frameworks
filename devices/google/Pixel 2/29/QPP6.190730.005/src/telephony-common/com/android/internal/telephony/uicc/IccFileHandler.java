/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.uicc.IccConstants;
import com.android.internal.telephony.uicc.IccException;
import com.android.internal.telephony.uicc.IccIoResult;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccCardApplication;
import java.util.ArrayList;

public abstract class IccFileHandler
extends Handler
implements IccConstants {
    protected static final int COMMAND_GET_RESPONSE = 192;
    protected static final int COMMAND_READ_BINARY = 176;
    protected static final int COMMAND_READ_RECORD = 178;
    protected static final int COMMAND_SEEK = 162;
    protected static final int COMMAND_UPDATE_BINARY = 214;
    protected static final int COMMAND_UPDATE_RECORD = 220;
    protected static final int EF_TYPE_CYCLIC = 3;
    protected static final int EF_TYPE_LINEAR_FIXED = 1;
    protected static final int EF_TYPE_TRANSPARENT = 0;
    protected static final int EVENT_GET_BINARY_SIZE_DONE = 4;
    protected static final int EVENT_GET_EF_LINEAR_RECORD_SIZE_DONE = 8;
    protected static final int EVENT_GET_RECORD_SIZE_DONE = 6;
    protected static final int EVENT_GET_RECORD_SIZE_IMG_DONE = 11;
    protected static final int EVENT_READ_BINARY_DONE = 5;
    protected static final int EVENT_READ_ICON_DONE = 10;
    protected static final int EVENT_READ_IMG_DONE = 9;
    protected static final int EVENT_READ_RECORD_DONE = 7;
    protected static final int GET_RESPONSE_EF_IMG_SIZE_BYTES = 10;
    protected static final int GET_RESPONSE_EF_SIZE_BYTES = 15;
    protected static final int READ_RECORD_MODE_ABSOLUTE = 4;
    protected static final int RESPONSE_DATA_ACCESS_CONDITION_1 = 8;
    protected static final int RESPONSE_DATA_ACCESS_CONDITION_2 = 9;
    protected static final int RESPONSE_DATA_ACCESS_CONDITION_3 = 10;
    protected static final int RESPONSE_DATA_FILE_ID_1 = 4;
    protected static final int RESPONSE_DATA_FILE_ID_2 = 5;
    protected static final int RESPONSE_DATA_FILE_SIZE_1 = 2;
    protected static final int RESPONSE_DATA_FILE_SIZE_2 = 3;
    protected static final int RESPONSE_DATA_FILE_STATUS = 11;
    protected static final int RESPONSE_DATA_FILE_TYPE = 6;
    protected static final int RESPONSE_DATA_LENGTH = 12;
    protected static final int RESPONSE_DATA_RECORD_LENGTH = 14;
    protected static final int RESPONSE_DATA_RFU_1 = 0;
    protected static final int RESPONSE_DATA_RFU_2 = 1;
    protected static final int RESPONSE_DATA_RFU_3 = 7;
    protected static final int RESPONSE_DATA_STRUCTURE = 13;
    protected static final int TYPE_DF = 2;
    protected static final int TYPE_EF = 4;
    protected static final int TYPE_MF = 1;
    protected static final int TYPE_RFU = 0;
    private static final boolean VDBG = false;
    @UnsupportedAppUsage
    protected final String mAid;
    @UnsupportedAppUsage
    protected final CommandsInterface mCi;
    @UnsupportedAppUsage
    protected final UiccCardApplication mParentApp;

    protected IccFileHandler(UiccCardApplication uiccCardApplication, String string, CommandsInterface commandsInterface) {
        this.mParentApp = uiccCardApplication;
        this.mAid = string;
        this.mCi = commandsInterface;
    }

    private boolean processException(Message message, AsyncResult object) {
        boolean bl = false;
        IccIoResult iccIoResult = (IccIoResult)object.result;
        if (object.exception != null) {
            this.sendResult(message, null, object.exception);
            bl = true;
        } else {
            object = iccIoResult.getException();
            if (object != null) {
                this.sendResult(message, null, (Throwable)object);
                bl = true;
            }
        }
        return bl;
    }

    private void sendResult(Message message, Object object, Throwable throwable) {
        if (message == null) {
            return;
        }
        AsyncResult.forMessage((Message)message, (Object)object, (Throwable)throwable);
        message.sendToTarget();
    }

    public void dispose() {
    }

    protected String getCommonIccEFPath(int n) {
        if (n != 12037 && n != 12258) {
            if (n != 20256) {
                if (n != 20272) {
                    if (n != 28480 && n != 28645 && n != 28474 && n != 28475) {
                        switch (n) {
                            default: {
                                return null;
                            }
                            case 28489: 
                            case 28490: 
                            case 28491: 
                            case 28492: 
                        }
                    }
                    return "3F007F10";
                }
                return "3F007F105F3A";
            }
            return "3F007F105F50";
        }
        return "3F00";
    }

    @UnsupportedAppUsage
    public void getEFLinearRecordSize(int n, Message message) {
        this.getEFLinearRecordSize(n, this.getEFPath(n), message);
    }

    @UnsupportedAppUsage
    public void getEFLinearRecordSize(int n, String string, Message message) {
        if (string == null) {
            string = this.getEFPath(n);
        }
        message = this.obtainMessage(8, (Object)new LoadLinearFixedContext(n, string, message));
        this.mCi.iccIOForApp(192, n, string, 0, 0, 15, null, null, this.mAid, message);
    }

    @UnsupportedAppUsage
    protected abstract String getEFPath(int var1);

    /*
     * Exception decompiling
     */
    public void handleMessage(Message var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 7[CASE]
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

    public void loadEFImgLinearFixed(int n, Message message) {
        message = this.obtainMessage(11, (Object)new LoadLinearFixedContext(20256, n, message));
        this.mCi.iccIOForApp(192, 20256, this.getEFPath(20256), n, 4, 10, null, null, this.mAid, message);
    }

    public void loadEFImgTransparent(int n, int n2, int n3, int n4, Message object) {
        Message message = this.obtainMessage(10, n, 0, object);
        object = new StringBuilder();
        ((StringBuilder)object).append("IccFileHandler: loadEFImgTransparent fileid = ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" filePath = ");
        ((StringBuilder)object).append(this.getEFPath(20256));
        ((StringBuilder)object).append(" highOffset = ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(" lowOffset = ");
        ((StringBuilder)object).append(n3);
        ((StringBuilder)object).append(" length = ");
        ((StringBuilder)object).append(n4);
        this.logd(((StringBuilder)object).toString());
        this.mCi.iccIOForApp(176, n, this.getEFPath(20256), n2, n3, n4, null, null, this.mAid, message);
    }

    @UnsupportedAppUsage
    public void loadEFLinearFixed(int n, int n2, Message message) {
        this.loadEFLinearFixed(n, this.getEFPath(n), n2, message);
    }

    @UnsupportedAppUsage
    public void loadEFLinearFixed(int n, String string, int n2, Message message) {
        if (string == null) {
            string = this.getEFPath(n);
        }
        message = this.obtainMessage(6, (Object)new LoadLinearFixedContext(n, n2, string, message));
        this.mCi.iccIOForApp(192, n, string, 0, 0, 15, null, null, this.mAid, message);
    }

    @UnsupportedAppUsage
    public void loadEFLinearFixedAll(int n, Message message) {
        this.loadEFLinearFixedAll(n, this.getEFPath(n), message);
    }

    @UnsupportedAppUsage
    public void loadEFLinearFixedAll(int n, String string, Message message) {
        if (string == null) {
            string = this.getEFPath(n);
        }
        message = this.obtainMessage(6, (Object)new LoadLinearFixedContext(n, string, message));
        this.mCi.iccIOForApp(192, n, string, 0, 0, 15, null, null, this.mAid, message);
    }

    public void loadEFTransparent(int n, int n2, Message message) {
        message = this.obtainMessage(5, n, 0, (Object)message);
        this.mCi.iccIOForApp(176, n, this.getEFPath(n), 0, 0, n2, null, null, this.mAid, message);
    }

    @UnsupportedAppUsage
    public void loadEFTransparent(int n, Message message) {
        message = this.obtainMessage(4, n, 0, (Object)message);
        this.mCi.iccIOForApp(192, n, this.getEFPath(n), 0, 0, 15, null, null, this.mAid, message);
    }

    protected abstract void logd(String var1);

    protected abstract void loge(String var1);

    @UnsupportedAppUsage
    public void updateEFLinearFixed(int n, int n2, byte[] arrby, String string, Message message) {
        this.mCi.iccIOForApp(220, n, this.getEFPath(n), n2, 4, arrby.length, IccUtils.bytesToHexString((byte[])arrby), string, this.mAid, message);
    }

    @UnsupportedAppUsage
    public void updateEFLinearFixed(int n, String string, int n2, byte[] arrby, String string2, Message message) {
        if (string == null) {
            string = this.getEFPath(n);
        }
        this.mCi.iccIOForApp(220, n, string, n2, 4, arrby.length, IccUtils.bytesToHexString((byte[])arrby), string2, this.mAid, message);
    }

    @UnsupportedAppUsage
    public void updateEFTransparent(int n, byte[] arrby, Message message) {
        this.mCi.iccIOForApp(214, n, this.getEFPath(n), 0, 0, arrby.length, IccUtils.bytesToHexString((byte[])arrby), null, this.mAid, message);
    }

    static class LoadLinearFixedContext {
        @UnsupportedAppUsage
        int mCountRecords;
        int mEfid;
        boolean mLoadAll;
        Message mOnLoaded;
        String mPath;
        @UnsupportedAppUsage
        int mRecordNum;
        @UnsupportedAppUsage
        int mRecordSize;
        @UnsupportedAppUsage
        ArrayList<byte[]> results;

        @UnsupportedAppUsage
        LoadLinearFixedContext(int n, int n2, Message message) {
            this.mEfid = n;
            this.mRecordNum = n2;
            this.mOnLoaded = message;
            this.mLoadAll = false;
            this.mPath = null;
        }

        LoadLinearFixedContext(int n, int n2, String string, Message message) {
            this.mEfid = n;
            this.mRecordNum = n2;
            this.mOnLoaded = message;
            this.mLoadAll = false;
            this.mPath = string;
        }

        LoadLinearFixedContext(int n, Message message) {
            this.mEfid = n;
            this.mRecordNum = 1;
            this.mLoadAll = true;
            this.mOnLoaded = message;
            this.mPath = null;
        }

        LoadLinearFixedContext(int n, String string, Message message) {
            this.mEfid = n;
            this.mRecordNum = 1;
            this.mLoadAll = true;
            this.mOnLoaded = message;
            this.mPath = string;
        }
    }

}

