/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructPollfd
 *  com.android.internal.os.-$
 *  com.android.internal.os.-$$Lambda
 *  com.android.internal.os.-$$Lambda$ZygoteServer
 *  com.android.internal.os.-$$Lambda$ZygoteServer$NJVbduCrCzDq0RHpPga7lyCk4eE
 *  dalvik.system.ZygoteHooks
 */
package com.android.internal.os;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.os.SystemClock;
import android.os.Trace;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructPollfd;
import android.util.Log;
import android.util.Slog;
import com.android.internal.os.-$;
import com.android.internal.os.Zygote;
import com.android.internal.os.ZygoteConnection;
import com.android.internal.os._$$Lambda$ZygoteServer$NJVbduCrCzDq0RHpPga7lyCk4eE;
import dalvik.system.ZygoteHooks;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class ZygoteServer {
    public static final String TAG = "ZygoteServer";
    private static final String USAP_POOL_SIZE_MAX_DEFAULT = "10";
    private static final int USAP_POOL_SIZE_MAX_LIMIT = 100;
    private static final String USAP_POOL_SIZE_MIN_DEFAULT = "1";
    private static final int USAP_POOL_SIZE_MIN_LIMIT = 1;
    private boolean mCloseSocketFd;
    private boolean mIsFirstPropertyCheck = true;
    private boolean mIsForkChild;
    private long mLastPropCheckTimestamp = 0L;
    private boolean mUsapPoolEnabled = false;
    private FileDescriptor mUsapPoolEventFD;
    private int mUsapPoolRefillThreshold = 0;
    private int mUsapPoolSizeMax = 0;
    private int mUsapPoolSizeMin = 0;
    private LocalServerSocket mUsapPoolSocket;
    private final boolean mUsapPoolSupported;
    private LocalServerSocket mZygoteSocket;

    ZygoteServer() {
        this.mUsapPoolEventFD = null;
        this.mZygoteSocket = null;
        this.mUsapPoolSocket = null;
        this.mUsapPoolSupported = false;
    }

    ZygoteServer(boolean bl) {
        this.mUsapPoolEventFD = Zygote.getUsapPoolEventFD();
        if (bl) {
            this.mZygoteSocket = Zygote.createManagedSocketFromInitSocket("zygote");
            this.mUsapPoolSocket = Zygote.createManagedSocketFromInitSocket("usap_pool_primary");
        } else {
            this.mZygoteSocket = Zygote.createManagedSocketFromInitSocket("zygote_secondary");
            this.mUsapPoolSocket = Zygote.createManagedSocketFromInitSocket("usap_pool_secondary");
        }
        this.fetchUsapPoolPolicyProps();
        this.mUsapPoolSupported = true;
    }

    private ZygoteConnection acceptCommandPeer(String object) {
        try {
            object = this.createNewConnection(this.mZygoteSocket.accept(), (String)object);
            return object;
        }
        catch (IOException iOException) {
            throw new RuntimeException("IOException during accept()", iOException);
        }
    }

    private void fetchUsapPoolPolicyProps() {
        if (this.mUsapPoolSupported) {
            String string2 = Zygote.getConfigurationProperty("usap_pool_size_max", USAP_POOL_SIZE_MAX_DEFAULT);
            if (!string2.isEmpty()) {
                this.mUsapPoolSizeMax = Integer.min(Integer.parseInt(string2), 100);
            }
            if (!(string2 = Zygote.getConfigurationProperty("usap_pool_size_min", USAP_POOL_SIZE_MIN_DEFAULT)).isEmpty()) {
                this.mUsapPoolSizeMin = Integer.max(Integer.parseInt(string2), 1);
            }
            if (!(string2 = Zygote.getConfigurationProperty("usap_refill_threshold", Integer.toString(this.mUsapPoolSizeMax / 2))).isEmpty()) {
                this.mUsapPoolRefillThreshold = Integer.min(Integer.parseInt(string2), this.mUsapPoolSizeMax);
            }
            if (this.mUsapPoolSizeMin >= this.mUsapPoolSizeMax) {
                Log.w(TAG, "The max size of the USAP pool must be greater than the minimum size.  Restoring default values.");
                this.mUsapPoolSizeMax = Integer.parseInt(USAP_POOL_SIZE_MAX_DEFAULT);
                this.mUsapPoolSizeMin = Integer.parseInt(USAP_POOL_SIZE_MIN_DEFAULT);
                this.mUsapPoolRefillThreshold = this.mUsapPoolSizeMax / 2;
            }
        }
    }

    private void fetchUsapPoolPolicyPropsWithMinInterval() {
        long l = SystemClock.elapsedRealtime();
        if (this.mIsFirstPropertyCheck || l - this.mLastPropCheckTimestamp >= 60000L) {
            this.mIsFirstPropertyCheck = false;
            this.mLastPropCheckTimestamp = l;
            this.fetchUsapPoolPolicyProps();
        }
    }

    static /* synthetic */ int lambda$runSelectLoop$0(FileDescriptor fileDescriptor) {
        return fileDescriptor.getInt$();
    }

    void closeServerSocket() {
        block5 : {
            if (this.mZygoteSocket == null) break block5;
            FileDescriptor fileDescriptor = this.mZygoteSocket.getFileDescriptor();
            this.mZygoteSocket.close();
            if (fileDescriptor == null) break block5;
            try {
                if (this.mCloseSocketFd) {
                    Os.close((FileDescriptor)fileDescriptor);
                }
            }
            catch (ErrnoException errnoException) {
                Log.e(TAG, "Zygote:  error closing descriptor", errnoException);
            }
            catch (IOException iOException) {
                Log.e(TAG, "Zygote:  error closing sockets", iOException);
            }
        }
        this.mZygoteSocket = null;
    }

    protected ZygoteConnection createNewConnection(LocalSocket localSocket, String string2) throws IOException {
        return new ZygoteConnection(localSocket, string2);
    }

    Runnable fillUsapPool(int[] object) {
        int n;
        Trace.traceBegin(64L, "Zygote:FillUsapPool");
        this.fetchUsapPoolPolicyPropsWithMinInterval();
        int n2 = this.mUsapPoolSizeMax - n;
        if (n < this.mUsapPoolSizeMin || n2 >= this.mUsapPoolRefillThreshold) {
            ZygoteHooks.preFork();
            Zygote.resetNicePriority();
            for (n = Zygote.getUsapPoolCount(); n < this.mUsapPoolSizeMax; ++n) {
                Runnable runnable = Zygote.forkUsap(this.mUsapPoolSocket, (int[])object);
                if (runnable == null) continue;
                return runnable;
            }
            ZygoteHooks.postForkCommon();
            object = new StringBuilder();
            ((StringBuilder)object).append("Filled the USAP pool. New USAPs: ");
            ((StringBuilder)object).append(n2);
            Log.i("zygote", ((StringBuilder)object).toString());
        }
        Trace.traceEnd(64L);
        return null;
    }

    FileDescriptor getZygoteSocketFileDescriptor() {
        return this.mZygoteSocket.getFileDescriptor();
    }

    public boolean isUsapPoolEnabled() {
        return this.mUsapPoolEnabled;
    }

    void registerServerSocketAtAbstractName(String string2) {
        if (this.mZygoteSocket == null) {
            try {
                LocalServerSocket localServerSocket;
                this.mZygoteSocket = localServerSocket = new LocalServerSocket(string2);
                this.mCloseSocketFd = false;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error binding to abstract socket '");
                stringBuilder.append(string2);
                stringBuilder.append("'");
                throw new RuntimeException(stringBuilder.toString(), iOException);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Runnable runSelectLoop(String string2) {
        int n;
        Object object;
        ArrayList<FileDescriptor> arrayList = new ArrayList<FileDescriptor>();
        ArrayList<Object> arrayList2 = new ArrayList<Object>();
        arrayList.add(this.mZygoteSocket.getFileDescriptor());
        arrayList2.add(null);
        do {
            Object object2;
            int n2;
            Object object3;
            Object object4;
            this.fetchUsapPoolPolicyPropsWithMinInterval();
            if (this.mUsapPoolEnabled) {
                object3 = Zygote.getUsapPipeFDs();
                object = new StructPollfd[arrayList.size() + 1 + ((int[])object3).length];
            } else {
                object = new StructPollfd[arrayList.size()];
                object3 = null;
            }
            int n3 = 0;
            for (byte[] arrby : arrayList) {
                object[n3] = new StructPollfd();
                object[n3].fd = arrby;
                object[n3].events = (short)OsConstants.POLLIN;
                ++n3;
            }
            if (this.mUsapPoolEnabled) {
                object[n3] = new StructPollfd();
                object[n3].fd = this.mUsapPoolEventFD;
                object[n3].events = (short)OsConstants.POLLIN;
                int n4 = ((int[])object3).length;
                n = n3 + 1;
                n2 = 0;
                do {
                    object4 = n;
                    if (n2 < n4) {
                        object4 = object3[n2];
                        object2 = new FileDescriptor();
                        object2.setInt$(object4);
                        object[n] = new StructPollfd();
                        object[n].fd = object2;
                        object[n].events = (short)OsConstants.POLLIN;
                        ++n;
                        ++n2;
                        continue;
                    }
                    break;
                } while (true);
            } else {
                object4 = n3;
            }
            try {
                Os.poll((StructPollfd[])object, (int)-1);
                n = 0;
            }
            catch (ErrnoException errnoException) {
                throw new RuntimeException("poll failed", errnoException);
            }
            while (--object4 >= 0) {
                block30 : {
                    block31 : {
                        byte[] arrby;
                        if ((object[object4].revents & OsConstants.POLLIN) == 0) continue;
                        if (object4 == 0) {
                            object3 = this.acceptCommandPeer(string2);
                            arrayList2.add(object3);
                            arrayList.add(((ZygoteConnection)object3).getFileDescriptor());
                            continue;
                        }
                        if (object4 < n3) {
                            Throwable throwable2;
                            block29 : {
                                block28 : {
                                    try {
                                        object2 = (ZygoteConnection)arrayList2.get((int)object4);
                                        object3 = ((ZygoteConnection)object2).processOneCommand(this);
                                        boolean bl = this.mIsForkChild;
                                        if (bl) {
                                            if (object3 != null) {
                                                this.mIsForkChild = false;
                                                return object3;
                                            }
                                            object3 = new IllegalStateException("command == null");
                                            throw object3;
                                        }
                                        if (object3 == null) {
                                            if (((ZygoteConnection)object2).isClosedByPeer()) {
                                                ((ZygoteConnection)object2).closeSocket();
                                                arrayList2.remove((int)object4);
                                                arrayList.remove((int)object4);
                                            }
                                            break block28;
                                        }
                                        object3 = new IllegalStateException("command != null");
                                        throw object3;
                                    }
                                    catch (Throwable throwable2) {
                                        break block29;
                                    }
                                    catch (Exception exception) {
                                        if (this.mIsForkChild) {
                                            Log.e(TAG, "Caught post-fork exception in child process.", exception);
                                            throw exception;
                                        }
                                        Slog.e(TAG, "Exception executing zygote command: ", exception);
                                        ((ZygoteConnection)arrayList2.remove((int)object4)).closeSocket();
                                        arrayList.remove((int)object4);
                                    }
                                }
                                this.mIsForkChild = false;
                                continue;
                            }
                            this.mIsForkChild = false;
                            throw throwable2;
                        }
                        arrby = new byte[8];
                        n2 = Os.read((FileDescriptor)object[object4].fd, (byte[])arrby, (int)0, (int)arrby.length);
                        if (n2 != 8) break block30;
                        object2 = new ByteArrayInputStream(arrby);
                        object3 = new DataInputStream((InputStream)object2);
                        long l = ((DataInputStream)object3).readLong();
                        if (object4 <= n3) break block31;
                        Zygote.removeUsapTableEntry((int)l);
                    }
                    n = 1;
                    continue;
                }
                try {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Incomplete read from USAP management FD of size ");
                    ((StringBuilder)object3).append(n2);
                    Log.e(TAG, ((StringBuilder)object3).toString());
                }
                catch (Exception exception) {
                    if (object4 == n3) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Failed to read from USAP pool event FD: ");
                        ((StringBuilder)object2).append(exception.getMessage());
                        Log.e(TAG, ((StringBuilder)object2).toString());
                        continue;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Failed to read from USAP reporting pipe: ");
                    ((StringBuilder)object2).append(exception.getMessage());
                    Log.e(TAG, ((StringBuilder)object2).toString());
                }
            }
        } while (n == 0 || (object = this.fillUsapPool(arrayList.subList(1, arrayList.size()).stream().mapToInt(_$$Lambda$ZygoteServer$NJVbduCrCzDq0RHpPga7lyCk4eE.INSTANCE).toArray())) == null);
        return object;
    }

    void setForkChild() {
        this.mIsForkChild = true;
    }

    Runnable setUsapPoolStatus(boolean bl, LocalSocket localSocket) {
        if (!this.mUsapPoolSupported) {
            Log.w(TAG, "Attempting to enable a USAP pool for a Zygote that doesn't support it.");
            return null;
        }
        if (this.mUsapPoolEnabled == bl) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("USAP Pool status change: ");
        String string2 = bl ? "ENABLED" : "DISABLED";
        stringBuilder.append(string2);
        Log.i(TAG, stringBuilder.toString());
        this.mUsapPoolEnabled = bl;
        if (bl) {
            return this.fillUsapPool(new int[]{localSocket.getFileDescriptor().getInt$()});
        }
        Zygote.emptyUsapPool();
        return null;
    }
}

