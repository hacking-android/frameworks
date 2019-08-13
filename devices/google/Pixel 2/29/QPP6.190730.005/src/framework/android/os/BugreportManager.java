/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Binder;
import android.os.BugreportParams;
import android.os.IDumpstate;
import android.os.IDumpstateListener;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os._$$Lambda$BugreportManager$DumpstateListener$Vi18nEKTiYzuC_I5Io1XCZxd88w;
import android.os._$$Lambda$BugreportManager$DumpstateListener$XpZbAM9CYGe3tPOak0Nw_HdFQ8I;
import android.os._$$Lambda$BugreportManager$DumpstateListener$srBmWyEMI_89xDivmKB4DtiSQ2A;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
public final class BugreportManager {
    private static final String TAG = "BugreportManager";
    private final IDumpstate mBinder;
    private final Context mContext;

    public BugreportManager(Context context, IDumpstate iDumpstate) {
        this.mContext = context;
        this.mBinder = iDumpstate;
    }

    public void cancelBugreport() {
        try {
            this.mBinder.cancelBugreport();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Exception decompiling
     */
    public void startBugreport(ParcelFileDescriptor var1_1, ParcelFileDescriptor var2_2, BugreportParams var3_6, Executor var4_7, BugreportCallback var5_8) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
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

    public static abstract class BugreportCallback {
        public static final int BUGREPORT_ERROR_ANOTHER_REPORT_IN_PROGRESS = 5;
        public static final int BUGREPORT_ERROR_INVALID_INPUT = 1;
        public static final int BUGREPORT_ERROR_RUNTIME = 2;
        public static final int BUGREPORT_ERROR_USER_CONSENT_TIMED_OUT = 4;
        public static final int BUGREPORT_ERROR_USER_DENIED_CONSENT = 3;

        public void onError(int n) {
        }

        public void onFinished() {
        }

        public void onProgress(float f) {
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface BugreportErrorCode {
        }

    }

    private final class DumpstateListener
    extends IDumpstateListener.Stub {
        private final BugreportCallback mCallback;
        private final Executor mExecutor;

        DumpstateListener(Executor executor, BugreportCallback bugreportCallback) {
            this.mExecutor = executor;
            this.mCallback = bugreportCallback;
        }

        public /* synthetic */ void lambda$onError$1$BugreportManager$DumpstateListener(int n) {
            this.mCallback.onError(n);
        }

        public /* synthetic */ void lambda$onFinished$2$BugreportManager$DumpstateListener() {
            this.mCallback.onFinished();
        }

        public /* synthetic */ void lambda$onProgress$0$BugreportManager$DumpstateListener(int n) {
            this.mCallback.onProgress(n);
        }

        @Override
        public void onError(int n) throws RemoteException {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$BugreportManager$DumpstateListener$srBmWyEMI_89xDivmKB4DtiSQ2A _$$Lambda$BugreportManager$DumpstateListener$srBmWyEMI_89xDivmKB4DtiSQ2A = new _$$Lambda$BugreportManager$DumpstateListener$srBmWyEMI_89xDivmKB4DtiSQ2A(this, n);
                executor.execute(_$$Lambda$BugreportManager$DumpstateListener$srBmWyEMI_89xDivmKB4DtiSQ2A);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void onFinished() throws RemoteException {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$BugreportManager$DumpstateListener$XpZbAM9CYGe3tPOak0Nw_HdFQ8I _$$Lambda$BugreportManager$DumpstateListener$XpZbAM9CYGe3tPOak0Nw_HdFQ8I = new _$$Lambda$BugreportManager$DumpstateListener$XpZbAM9CYGe3tPOak0Nw_HdFQ8I(this);
                executor.execute(_$$Lambda$BugreportManager$DumpstateListener$XpZbAM9CYGe3tPOak0Nw_HdFQ8I);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void onMaxProgressUpdated(int n) throws RemoteException {
        }

        @Override
        public void onProgress(int n) throws RemoteException {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$BugreportManager$DumpstateListener$Vi18nEKTiYzuC_I5Io1XCZxd88w _$$Lambda$BugreportManager$DumpstateListener$Vi18nEKTiYzuC_I5Io1XCZxd88w = new _$$Lambda$BugreportManager$DumpstateListener$Vi18nEKTiYzuC_I5Io1XCZxd88w(this, n);
                executor.execute(_$$Lambda$BugreportManager$DumpstateListener$Vi18nEKTiYzuC_I5Io1XCZxd88w);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void onProgressUpdated(int n) throws RemoteException {
        }

        @Override
        public void onSectionComplete(String string2, int n, int n2, int n3) throws RemoteException {
        }
    }

}

