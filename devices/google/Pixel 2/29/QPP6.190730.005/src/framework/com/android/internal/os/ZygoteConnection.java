/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructPollfd
 *  dalvik.system.VMRuntime
 *  dalvik.system.VMRuntime$HiddenApiUsageLogger
 *  libcore.io.IoUtils
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ApplicationInfo;
import android.metrics.LogMaker;
import android.net.Credentials;
import android.net.LocalSocket;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.Trace;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructPollfd;
import android.util.Log;
import android.util.StatsLog;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.WrapperInit;
import com.android.internal.os.Zygote;
import com.android.internal.os.ZygoteArguments;
import com.android.internal.os.ZygoteInit;
import com.android.internal.os.ZygoteSecurityException;
import com.android.internal.os.ZygoteServer;
import com.android.internal.os._$$Lambda$ZygoteConnection$KxVsZ_s4KsanePOHCU5JcuypPik;
import com.android.internal.os._$$Lambda$ZygoteConnection$xjqM7qW7vAjTqh2tR5XRF5Vn5mk;
import dalvik.system.VMRuntime;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import libcore.io.IoUtils;

class ZygoteConnection {
    private static final String TAG = "Zygote";
    private final String abiList;
    private boolean isEof;
    @UnsupportedAppUsage
    private final LocalSocket mSocket;
    @UnsupportedAppUsage
    private final DataOutputStream mSocketOutStream;
    private final BufferedReader mSocketReader;
    @UnsupportedAppUsage
    private final Credentials peer;

    ZygoteConnection(LocalSocket localSocket, String string2) throws IOException {
        this.mSocket = localSocket;
        this.abiList = string2;
        this.mSocketOutStream = new DataOutputStream(localSocket.getOutputStream());
        this.mSocketReader = new BufferedReader(new InputStreamReader(localSocket.getInputStream()), 256);
        this.mSocket.setSoTimeout(1000);
        try {
            this.peer = this.mSocket.getPeerCredentials();
            this.isEof = false;
            return;
        }
        catch (IOException iOException) {
            Log.e(TAG, "Cannot read peer credentials", iOException);
            throw iOException;
        }
    }

    private void handleAbiListQuery() {
        try {
            byte[] arrby = this.abiList.getBytes(StandardCharsets.US_ASCII);
            this.mSocketOutStream.writeInt(arrby.length);
            this.mSocketOutStream.write(arrby);
            return;
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Error writing to command socket", iOException);
        }
    }

    private Runnable handleApiBlacklistExemptions(ZygoteServer zygoteServer, String[] arrstring) {
        return this.stateChangeWithUsapPoolReset(zygoteServer, new _$$Lambda$ZygoteConnection$xjqM7qW7vAjTqh2tR5XRF5Vn5mk(arrstring));
    }

    private Runnable handleChildProc(ZygoteArguments zygoteArguments, FileDescriptor[] arrfileDescriptor, FileDescriptor fileDescriptor, boolean bl) {
        this.closeSocket();
        if (arrfileDescriptor != null) {
            Os.dup2((FileDescriptor)arrfileDescriptor[0], (int)OsConstants.STDIN_FILENO);
            Os.dup2((FileDescriptor)arrfileDescriptor[1], (int)OsConstants.STDOUT_FILENO);
            Os.dup2((FileDescriptor)arrfileDescriptor[2], (int)OsConstants.STDERR_FILENO);
            int n = arrfileDescriptor.length;
            for (int i = 0; i < n; ++i) {
                try {
                    IoUtils.closeQuietly((FileDescriptor)arrfileDescriptor[i]);
                    continue;
                }
                catch (ErrnoException errnoException) {
                    Log.e(TAG, "Error reopening stdio", errnoException);
                    break;
                }
            }
        }
        if (zygoteArguments.mNiceName != null) {
            Process.setArgV0(zygoteArguments.mNiceName);
        }
        Trace.traceEnd(64L);
        if (zygoteArguments.mInvokeWith == null) {
            if (!bl) {
                return ZygoteInit.zygoteInit(zygoteArguments.mTargetSdkVersion, zygoteArguments.mRemainingArgs, null);
            }
            return ZygoteInit.childZygoteInit(zygoteArguments.mTargetSdkVersion, zygoteArguments.mRemainingArgs, null);
        }
        WrapperInit.execApplication(zygoteArguments.mInvokeWith, zygoteArguments.mNiceName, zygoteArguments.mTargetSdkVersion, VMRuntime.getCurrentInstructionSet(), fileDescriptor, zygoteArguments.mRemainingArgs);
        throw new IllegalStateException("WrapperInit.execApplication unexpectedly returned");
    }

    private Runnable handleHiddenApiAccessLogSampleRate(ZygoteServer zygoteServer, int n, int n2) {
        return this.stateChangeWithUsapPoolReset(zygoteServer, new _$$Lambda$ZygoteConnection$KxVsZ_s4KsanePOHCU5JcuypPik(n, n2));
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void handleParentProc(int n, FileDescriptor[] object, FileDescriptor object2) {
        boolean bl;
        int n2;
        block39 : {
            block37 : {
                boolean bl2;
                int n3;
                block35 : {
                    block36 : {
                        n2 = n;
                        if (n2 > 0) {
                            this.setChildPgid(n);
                        }
                        int n4 = 0;
                        if (object != null) {
                            n3 = ((Object)object).length;
                            for (n = 0; n < n3; ++n) {
                                IoUtils.closeQuietly((FileDescriptor)object[n]);
                            }
                        }
                        boolean bl3 = false;
                        bl = false;
                        if (object2 == null || n2 <= 0) break block37;
                        n = -1;
                        bl2 = bl3;
                        n3 = n;
                        Object object3 = new StructPollfd[1];
                        bl2 = bl3;
                        n3 = n;
                        bl2 = bl3;
                        n3 = n;
                        object = new StructPollfd();
                        object3[0] = object;
                        bl2 = bl3;
                        n3 = n;
                        object = new byte[4];
                        int n5 = 30000;
                        int n6 = 0;
                        bl2 = bl3;
                        n3 = n;
                        long l = System.nanoTime();
                        do {
                            block38 : {
                                block33 : {
                                    block34 : {
                                        bl2 = bl;
                                        n3 = n;
                                        if (n6 >= ((Object)object).length || n5 <= 0) break;
                                        bl2 = bl;
                                        n3 = n;
                                        object3[n4].fd = object2;
                                        bl2 = bl;
                                        n3 = n;
                                        ((StructPollfd)object3[n4]).events = (short)OsConstants.POLLIN;
                                        bl2 = bl;
                                        n3 = n;
                                        ((StructPollfd)object3[0]).revents = (short)(false ? 1 : 0);
                                        bl2 = bl;
                                        n3 = n;
                                        ((StructPollfd)object3[0]).userData = null;
                                        bl2 = bl;
                                        n3 = n;
                                        n4 = Os.poll((StructPollfd[])object3, (int)n5);
                                        bl2 = bl;
                                        n3 = n;
                                        long l2 = System.nanoTime();
                                        n5 = 30000 - (int)((l2 - l) / 1000000L);
                                        if (n4 <= 0) break block33;
                                        if ((((StructPollfd)object3[0]).revents & OsConstants.POLLIN) == 0) break;
                                        n3 = Os.read((FileDescriptor)object2, (byte[])object, (int)n6, (int)1);
                                        if (n3 < 0) break block34;
                                        n3 = n6 + n3;
                                        break block38;
                                    }
                                    object = new RuntimeException("Some error");
                                    throw object;
                                }
                                n3 = n6;
                                if (n4 == 0) {
                                    Log.w(TAG, "Timed out waiting for child.");
                                    n3 = n6;
                                }
                            }
                            n4 = 0;
                            n6 = n3;
                        } while (true);
                        bl2 = bl;
                        try {
                            if (n6 == ((Object)object).length) {
                                object2 = new ByteArrayInputStream((byte[])object);
                                object3 = new DataInputStream((InputStream)object2);
                                n = n3 = ((DataInputStream)object3).readInt();
                            }
                            if (n != -1) break block35;
                        }
                        catch (Exception exception) {}
                        try {
                            Log.w(TAG, "Error reading pid from wrapped process, child may have died");
                            break block35;
                        }
                        catch (Exception exception) {
                            bl = bl2;
                            break block36;
                        }
                        break block36;
                        catch (Exception exception) {
                            bl = bl2;
                            n = n3;
                        }
                    }
                    Log.w(TAG, "Error reading pid from wrapped process, child may have died", (Throwable)object);
                    bl2 = bl;
                }
                bl = bl2;
                if (n > 0) {
                    n3 = n;
                    while (n3 > 0 && n3 != n2) {
                        n3 = Process.getParentPid(n3);
                    }
                    if (n3 > 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Wrapped process has pid ");
                        ((StringBuilder)object).append(n);
                        Log.i(TAG, ((StringBuilder)object).toString());
                        bl = true;
                        n2 = n;
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Wrapped process reported a pid that is not a child of the process that we forked: childPid=");
                        ((StringBuilder)object).append(n2);
                        ((StringBuilder)object).append(" innerPid=");
                        ((StringBuilder)object).append(n);
                        Log.w(TAG, ((StringBuilder)object).toString());
                        bl = bl2;
                    }
                }
                break block39;
            }
            bl = false;
        }
        try {
            this.mSocketOutStream.writeInt(n2);
            this.mSocketOutStream.writeBoolean(bl);
            return;
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Error writing to command socket", iOException);
        }
    }

    private void handlePidQuery() {
        try {
            byte[] arrby = String.valueOf(Process.myPid()).getBytes(StandardCharsets.US_ASCII);
            this.mSocketOutStream.writeInt(arrby.length);
            this.mSocketOutStream.write(arrby);
            return;
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Error writing to command socket", iOException);
        }
    }

    private void handlePreload() {
        try {
            if (this.isPreloadComplete()) {
                this.mSocketOutStream.writeInt(1);
            } else {
                this.preload();
                this.mSocketOutStream.writeInt(0);
            }
            return;
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Error writing to command socket", iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Runnable handleUsapPoolStatusChange(ZygoteServer zygoteServer, boolean bl) {
        try {
            Runnable runnable = zygoteServer.setUsapPoolStatus(bl, this.mSocket);
            if (runnable == null) {
                this.mSocketOutStream.writeInt(0);
                return runnable;
            }
            zygoteServer.setForkChild();
            return runnable;
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Error writing to command socket", iOException);
        }
    }

    static /* synthetic */ void lambda$handleApiBlacklistExemptions$0(String[] arrstring) {
        ZygoteInit.setApiBlacklistExemptions(arrstring);
    }

    static /* synthetic */ void lambda$handleHiddenApiAccessLogSampleRate$1(int n, int n2) {
        ZygoteInit.setHiddenApiAccessLogSampleRate(Math.max(n, n2));
        HiddenApiUsageLogger.setHiddenApiAccessLogSampleRates(n, n2);
        ZygoteInit.setHiddenApiUsageLogger(HiddenApiUsageLogger.getInstance());
    }

    private void setChildPgid(int n) {
        try {
            Os.setpgid((int)n, (int)Os.getpgid((int)this.peer.getPid()));
        }
        catch (ErrnoException errnoException) {
            Log.i(TAG, "Zygote: setpgid failed. This is normal if peer is not in our session");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Runnable stateChangeWithUsapPoolReset(ZygoteServer zygoteServer, Runnable runnable) {
        try {
            boolean bl = zygoteServer.isUsapPoolEnabled();
            if (bl) {
                Log.i(TAG, "Emptying USAP Pool due to state change.");
                Zygote.emptyUsapPool();
            }
            runnable.run();
            if (zygoteServer.isUsapPoolEnabled()) {
                runnable = zygoteServer.fillUsapPool(new int[]{this.mSocket.getFileDescriptor().getInt$()});
                if (runnable != null) {
                    zygoteServer.setForkChild();
                    return runnable;
                }
                Log.i(TAG, "Finished refilling USAP Pool after state change.");
            }
            this.mSocketOutStream.writeInt(0);
            return null;
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Error writing to command socket", iOException);
        }
    }

    protected boolean canPreloadApp() {
        return false;
    }

    @UnsupportedAppUsage
    void closeSocket() {
        try {
            this.mSocket.close();
        }
        catch (IOException iOException) {
            Log.e(TAG, "Exception while closing command socket in parent", iOException);
        }
    }

    FileDescriptor getFileDescriptor() {
        return this.mSocket.getFileDescriptor();
    }

    protected DataOutputStream getSocketOutputStream() {
        return this.mSocketOutStream;
    }

    protected void handlePreloadApp(ApplicationInfo applicationInfo) {
        throw new RuntimeException("Zygote does not support app preloading");
    }

    protected void handlePreloadPackage(String string2, String string3, String string4, String string5) {
        throw new RuntimeException("Zygote does not support package preloading");
    }

    boolean isClosedByPeer() {
        return this.isEof;
    }

    protected boolean isPreloadComplete() {
        return ZygoteInit.isPreloadComplete();
    }

    protected void preload() {
        ZygoteInit.lazyPreload();
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    Runnable processOneCommand(ZygoteServer object) {
        ZygoteArguments zygoteArguments;
        block29 : {
            Object object2;
            void var1_8;
            String string2;
            block28 : {
                FileDescriptor[] arrfileDescriptor;
                int n;
                int n2;
                int[] arrn3;
                int[] arrn;
                int[][] arrn2;
                block31 : {
                    block30 : {
                        block27 : {
                            try {
                                object2 = Zygote.readArgumentList(this.mSocketReader);
                                arrfileDescriptor = this.mSocket.getAncillaryFileDescriptors();
                                if (object2 == null) {
                                    this.isEof = true;
                                    return null;
                                }
                                zygoteArguments = new ZygoteArguments((String[])object2);
                                if (zygoteArguments.mAbiListQuery) {
                                    this.handleAbiListQuery();
                                    return null;
                                }
                                if (zygoteArguments.mPidQuery) {
                                    this.handlePidQuery();
                                    return null;
                                }
                                if (zygoteArguments.mUsapPoolStatusSpecified) {
                                    return this.handleUsapPoolStatusChange((ZygoteServer)object, zygoteArguments.mUsapPoolEnabled);
                                }
                                if (zygoteArguments.mPreloadDefault) {
                                    this.handlePreload();
                                    return null;
                                }
                                if (zygoteArguments.mPreloadPackage != null) {
                                    this.handlePreloadPackage(zygoteArguments.mPreloadPackage, zygoteArguments.mPreloadPackageLibs, zygoteArguments.mPreloadPackageLibFileName, zygoteArguments.mPreloadPackageCacheKey);
                                    return null;
                                }
                                if (!this.canPreloadApp() || zygoteArguments.mPreloadApp == null) break block27;
                                object2 = Base64.getDecoder().decode(zygoteArguments.mPreloadApp);
                            }
                            catch (IOException iOException) {
                                throw new IllegalStateException("IOException on command socket", iOException);
                            }
                            object = Parcel.obtain();
                            ((Parcel)object).unmarshall((byte[])object2, 0, ((String[])object2).length);
                            ((Parcel)object).setDataPosition(0);
                            object2 = ApplicationInfo.CREATOR.createFromParcel((Parcel)object);
                            ((Parcel)object).recycle();
                            if (object2 == null) throw new IllegalArgumentException("Failed to deserialize --preload-app");
                            this.handlePreloadApp((ApplicationInfo)object2);
                            return null;
                        }
                        if (zygoteArguments.mApiBlacklistExemptions != null) {
                            return this.handleApiBlacklistExemptions((ZygoteServer)object, zygoteArguments.mApiBlacklistExemptions);
                        }
                        if (zygoteArguments.mHiddenApiAccessLogSampleRate != -1) return this.handleHiddenApiAccessLogSampleRate((ZygoteServer)object, zygoteArguments.mHiddenApiAccessLogSampleRate, zygoteArguments.mHiddenApiAccessStatslogSampleRate);
                        if (zygoteArguments.mHiddenApiAccessStatslogSampleRate != -1) {
                            return this.handleHiddenApiAccessLogSampleRate((ZygoteServer)object, zygoteArguments.mHiddenApiAccessLogSampleRate, zygoteArguments.mHiddenApiAccessStatslogSampleRate);
                        }
                        if (zygoteArguments.mPermittedCapabilities != 0L || zygoteArguments.mEffectiveCapabilities != 0L) break block29;
                        Zygote.applyUidSecurityPolicy(zygoteArguments, this.peer);
                        Zygote.applyInvokeWithSecurityPolicy(zygoteArguments, this.peer);
                        Zygote.applyDebuggerSystemProperty(zygoteArguments);
                        Zygote.applyInvokeWithSystemProperty(zygoteArguments);
                        arrn2 = null;
                        if (zygoteArguments.mRLimits != null) {
                            arrn2 = (int[][])zygoteArguments.mRLimits.toArray((T[])Zygote.INT_ARRAY_2D);
                        }
                        if (zygoteArguments.mInvokeWith == null) break block30;
                        try {
                            object2 = Os.pipe2((int)OsConstants.O_CLOEXEC);
                            string2 = object2[1];
                            object2 = object2[0];
                            Os.fcntlInt((FileDescriptor)((Object)string2), (int)OsConstants.F_SETFD, (int)0);
                            n = string2.getInt$();
                            n2 = object2.getInt$();
                        }
                        catch (ErrnoException errnoException) {
                            throw new IllegalStateException("Unable to set up pipe for invoke-with", errnoException);
                        }
                        arrn3 = new int[]{n, n2};
                        break block31;
                    }
                    object2 = null;
                    string2 = null;
                    arrn3 = null;
                }
                int[] arrn4 = arrn = new int[2];
                arrn4[0] = -1;
                arrn4[1] = -1;
                Object object3 = this.mSocket.getFileDescriptor();
                if (object3 != null) {
                    arrn[0] = object3.getInt$();
                }
                if ((object3 = ((ZygoteServer)object).getZygoteSocketFileDescriptor()) != null) {
                    arrn[1] = object3.getInt$();
                }
                int n3 = zygoteArguments.mUid;
                int n4 = zygoteArguments.mGid;
                int[] arrn5 = zygoteArguments.mGids;
                n2 = zygoteArguments.mRuntimeFlags;
                n = zygoteArguments.mMountExternal;
                String string3 = zygoteArguments.mSeInfo;
                String string4 = zygoteArguments.mNiceName;
                boolean bl = zygoteArguments.mStartChildZygote;
                String string5 = zygoteArguments.mInstructionSet;
                object3 = zygoteArguments.mAppDataDir;
                int n5 = zygoteArguments.mTargetSdkVersion;
                if ((n = Zygote.forkAndSpecialize(n3, n4, arrn5, n2, arrn2, n, string3, string4, arrn, arrn3, bl, string5, (String)object3, n5)) == 0) {
                    try {
                        ((ZygoteServer)object).setForkChild();
                        ((ZygoteServer)object).closeServerSocket();
                        IoUtils.closeQuietly((FileDescriptor)object2);
                    }
                    catch (Throwable throwable) {}
                    try {
                        bl = zygoteArguments.mStartChildZygote;
                    }
                    catch (Throwable throwable) {
                        object2 = null;
                        break block28;
                    }
                    try {
                        object = this.handleChildProc(zygoteArguments, arrfileDescriptor, (FileDescriptor)((Object)string2), bl);
                    }
                    catch (Throwable throwable) {
                        object2 = null;
                        break block28;
                    }
                    IoUtils.closeQuietly((FileDescriptor)((Object)string2));
                    IoUtils.closeQuietly(null);
                    return object;
                } else {
                    IoUtils.closeQuietly((FileDescriptor)((Object)string2));
                    string2 = null;
                    try {
                        this.handleParentProc(n, arrfileDescriptor, (FileDescriptor)object2);
                    }
                    catch (Throwable throwable) {
                        break block28;
                    }
                    IoUtils.closeQuietly(null);
                    IoUtils.closeQuietly((FileDescriptor)object2);
                    return null;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
            }
            IoUtils.closeQuietly((FileDescriptor)((Object)string2));
            IoUtils.closeQuietly((FileDescriptor)object2);
            throw var1_8;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Client may not specify capabilities: permitted=0x");
        ((StringBuilder)object).append(Long.toHexString(zygoteArguments.mPermittedCapabilities));
        ((StringBuilder)object).append(", effective=0x");
        ((StringBuilder)object).append(Long.toHexString(zygoteArguments.mEffectiveCapabilities));
        throw new ZygoteSecurityException(((StringBuilder)object).toString());
    }

    private static class HiddenApiUsageLogger
    implements VMRuntime.HiddenApiUsageLogger {
        private static HiddenApiUsageLogger sInstance = new HiddenApiUsageLogger();
        private int mHiddenApiAccessLogSampleRate = 0;
        private int mHiddenApiAccessStatslogSampleRate = 0;
        private final MetricsLogger mMetricsLogger = new MetricsLogger();

        private HiddenApiUsageLogger() {
        }

        public static HiddenApiUsageLogger getInstance() {
            return sInstance;
        }

        private void logUsage(String object, String string2, int n, boolean bl) {
            int n2 = 0;
            n = n != 0 ? (n != 1 ? (n != 2 ? (n != 3 ? n2 : 3) : 2) : 1) : 0;
            object = new LogMaker(1391).setPackageName((String)object).addTaggedData(1394, string2).addTaggedData(1392, n);
            if (bl) {
                ((LogMaker)object).addTaggedData(1393, 1);
            }
            this.mMetricsLogger.write((LogMaker)object);
        }

        private void newLogUsage(String string2, int n, boolean bl) {
            int n2 = 0;
            n = n != 0 ? (n != 1 ? (n != 2 ? (n != 3 ? n2 : 3) : 2) : 1) : 0;
            StatsLog.write(178, Process.myUid(), string2, n, bl);
        }

        public static void setHiddenApiAccessLogSampleRates(int n, int n2) {
            if (n != -1) {
                HiddenApiUsageLogger.sInstance.mHiddenApiAccessLogSampleRate = n;
            }
            if (n2 != -1) {
                HiddenApiUsageLogger.sInstance.mHiddenApiAccessStatslogSampleRate = n2;
            }
        }

        public void hiddenApiUsed(int n, String string2, String string3, int n2, boolean bl) {
            if (n < this.mHiddenApiAccessLogSampleRate) {
                this.logUsage(string2, string3, n2, bl);
            }
            if (n < this.mHiddenApiAccessStatslogSampleRate) {
                this.newLogUsage(string3, n2, bl);
            }
        }
    }

}

