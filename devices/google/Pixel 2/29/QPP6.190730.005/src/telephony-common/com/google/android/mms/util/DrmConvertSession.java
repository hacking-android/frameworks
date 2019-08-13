/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.drm.DrmConvertedStatus
 *  android.drm.DrmManagerClient
 *  android.util.Log
 */
package com.google.android.mms.util;

import android.content.Context;
import android.drm.DrmConvertedStatus;
import android.drm.DrmManagerClient;
import android.util.Log;

public class DrmConvertSession {
    private static final String TAG = "DrmConvertSession";
    private int mConvertSessionId;
    private DrmManagerClient mDrmClient;

    private DrmConvertSession(DrmManagerClient drmManagerClient, int n) {
        this.mDrmClient = drmManagerClient;
        this.mConvertSessionId = n;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static DrmConvertSession open(Context context, String string) {
        Object object = null;
        Context context3 = null;
        Context context4 = null;
        int n2 = -1;
        Context context2 = context3;
        int n = n2;
        if (context != null) {
            context2 = context3;
            n = n2;
            if (string != null) {
                context2 = context3;
                n = n2;
                if (!string.equals("")) {
                    context2 = context4;
                    context3 = object;
                    context2 = context4;
                    context3 = object;
                    DrmManagerClient drmManagerClient = new DrmManagerClient(context);
                    context = drmManagerClient;
                    try {
                        n = context.openConvertSession(string);
                        context2 = context;
                    }
                    catch (IllegalStateException illegalStateException) {
                        context2 = context;
                        context3 = context;
                        try {
                            Log.w((String)TAG, (String)"Could not access Open DrmFramework.", (Throwable)illegalStateException);
                            context2 = context;
                            n = n2;
                            catch (IllegalArgumentException illegalArgumentException) {
                                context2 = context;
                                context3 = context;
                                context2 = context;
                                context3 = context;
                                object = new StringBuilder();
                                context2 = context;
                                context3 = context;
                                ((StringBuilder)object).append("Conversion of Mimetype: ");
                                context2 = context;
                                context3 = context;
                                ((StringBuilder)object).append(string);
                                context2 = context;
                                context3 = context;
                                ((StringBuilder)object).append(" is not supported.");
                                context2 = context;
                                context3 = context;
                                Log.w((String)TAG, (String)((StringBuilder)object).toString(), (Throwable)illegalArgumentException);
                                context2 = context;
                                n = n2;
                            }
                        }
                        catch (IllegalStateException illegalStateException2) {
                            Log.w((String)TAG, (String)"DrmManagerClient didn't initialize properly.");
                            n = n2;
                        }
                        catch (IllegalArgumentException illegalArgumentException2) {
                            Log.w((String)TAG, (String)"DrmManagerClient instance could not be created, context is Illegal.");
                            n = n2;
                            context2 = context3;
                        }
                    }
                }
            }
        }
        if (context2 == null) return null;
        if (n >= 0) return new DrmConvertSession((DrmManagerClient)context2, n);
        return null;
    }

    /*
     * Exception decompiling
     */
    public int close(String var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 4 blocks at once
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public byte[] convert(byte[] object, int n) {
        DrmConvertedStatus drmConvertedStatus;
        Object var3_4 = null;
        Object var4_5 = null;
        if (object == null) throw new IllegalArgumentException("Parameter inBuffer is null");
        if (n != ((Object)object).length) {
            drmConvertedStatus = new byte[n];
            System.arraycopy(object, 0, (Object)drmConvertedStatus, 0, n);
            drmConvertedStatus = this.mDrmClient.convertData(this.mConvertSessionId, (byte[])drmConvertedStatus);
        } else {
            drmConvertedStatus = this.mDrmClient.convertData(this.mConvertSessionId, (byte[])object);
        }
        object = var4_5;
        if (drmConvertedStatus == null) return object;
        object = var4_5;
        if (drmConvertedStatus.statusCode != 1) return object;
        object = var4_5;
        try {
            if (drmConvertedStatus.convertedData == null) return object;
            return drmConvertedStatus.convertedData;
        }
        catch (IllegalStateException illegalStateException) {
            drmConvertedStatus = new StringBuilder();
            drmConvertedStatus.append("Could not convert data. Convertsession: ");
            drmConvertedStatus.append(this.mConvertSessionId);
            Log.w((String)TAG, (String)drmConvertedStatus.toString(), (Throwable)illegalStateException);
            return var3_4;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Buffer with data to convert is illegal. Convertsession: ");
            ((StringBuilder)object).append(this.mConvertSessionId);
            Log.w((String)TAG, (String)((StringBuilder)object).toString(), (Throwable)illegalArgumentException);
            return var4_5;
        }
    }
}

