/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.OsConstants
 */
package android.net;

import android.net.DnsPacket;
import android.net.Network;
import android.net.NetworkUtils;
import android.net.ParseException;
import android.net._$$Lambda$DnsResolver$05nTktlCCI7FQsULCMbgIrjmrGc;
import android.net._$$Lambda$DnsResolver$DW9jYL2ZOH6BjebIVPhZIrrhoD8;
import android.net._$$Lambda$DnsResolver$GTAgQiExADAzbCx0WiV_97W72_g;
import android.net._$$Lambda$DnsResolver$h2SsAzA5_rVr_mzxppK8PJLQe98;
import android.net._$$Lambda$DnsResolver$hIO7FFv0AXN6Nj0Dzka_LD8S870;
import android.net._$$Lambda$DnsResolver$kjq9c3feWPGKUPV3AzJBFi1GUvw;
import android.net._$$Lambda$DnsResolver$kxKi6qjPYeR_SIipxW4tYpxyM50;
import android.net._$$Lambda$DnsResolver$t5xp_fS_zTQ564hG_PIaWJdBP8c;
import android.net._$$Lambda$DnsResolver$uxb9gSgrd6Qyj9SLhCAtOvpxa3I;
import android.net._$$Lambda$DnsResolver$vvKhya16sREGcN1Gxnqgw_LBoV4;
import android.net._$$Lambda$DnsResolver$wc3_cnx2aezlAHvMEbQVFaTPAcE;
import android.net.util.DnsUtils;
import android.os.CancellationSignal;
import android.os.Looper;
import android.os.MessageQueue;
import android.system.ErrnoException;
import android.system.OsConstants;
import android.util.Log;
import java.io.FileDescriptor;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

public final class DnsResolver {
    public static final int CLASS_IN = 1;
    public static final int ERROR_PARSE = 0;
    public static final int ERROR_SYSTEM = 1;
    private static final int FD_EVENTS = 5;
    public static final int FLAG_EMPTY = 0;
    public static final int FLAG_NO_CACHE_LOOKUP = 4;
    public static final int FLAG_NO_CACHE_STORE = 2;
    public static final int FLAG_NO_RETRY = 1;
    private static final int MAXPACKET = 8192;
    private static final int NETID_UNSET = 0;
    private static final int SLEEP_TIME_MS = 2;
    private static final String TAG = "DnsResolver";
    public static final int TYPE_A = 1;
    public static final int TYPE_AAAA = 28;
    private static final DnsResolver sInstance = new DnsResolver();

    private DnsResolver() {
    }

    private void addCancellationSignal(CancellationSignal cancellationSignal, FileDescriptor fileDescriptor, Object object) {
        cancellationSignal.setOnCancelListener(new _$$Lambda$DnsResolver$05nTktlCCI7FQsULCMbgIrjmrGc(this, object, fileDescriptor));
    }

    private void cancelQuery(FileDescriptor fileDescriptor) {
        if (!fileDescriptor.valid()) {
            return;
        }
        Looper.getMainLooper().getQueue().removeOnFileDescriptorEventListener(fileDescriptor);
        NetworkUtils.resNetworkCancel(fileDescriptor);
    }

    public static DnsResolver getInstance() {
        return sInstance;
    }

    static /* synthetic */ void lambda$query$2(Callback callback, ErrnoException errnoException) {
        callback.onError(new DnsException(1, errnoException));
    }

    static /* synthetic */ void lambda$query$3(Callback callback) {
        callback.onError(new DnsException(1, new ErrnoException("resNetworkQuery", OsConstants.ENONET)));
    }

    static /* synthetic */ void lambda$query$4(Callback callback, ErrnoException errnoException) {
        callback.onError(new DnsException(1, errnoException));
    }

    static /* synthetic */ void lambda$query$5(Callback callback, ErrnoException errnoException) {
        callback.onError(new DnsException(1, errnoException));
    }

    static /* synthetic */ void lambda$query$7(Callback callback, ErrnoException errnoException) {
        callback.onError(new DnsException(1, errnoException));
    }

    static /* synthetic */ void lambda$rawQuery$0(Callback callback, ErrnoException errnoException) {
        callback.onError(new DnsException(1, errnoException));
    }

    static /* synthetic */ void lambda$rawQuery$1(Callback callback, ErrnoException errnoException) {
        callback.onError(new DnsException(1, errnoException));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    static /* synthetic */ void lambda$registerFDListener$8(Object var0, CancellationSignal var1_1, FileDescriptor var2_3, Callback var3_5) {
        block8 : {
            var4_6 = null;
            var5_7 = null;
            // MONITORENTER : var0
            if (var1_1 == null) ** GOTO lbl10
            if (var1_1.isCanceled()) {
                // MONITOREXIT : var0
                return;
            }
lbl10: // 4 sources:
            var1_1 = NetworkUtils.resNetworkResult(var2_3);
            var2_3 = var5_7;
            break block8;
            catch (ErrnoException var2_4) {
                var1_1 = new StringBuilder();
                var1_1.append("resNetworkResult:");
                var1_1.append(var2_4.toString());
                Log.e("DnsResolver", var1_1.toString());
                var1_1 = var4_6;
            }
        }
        // MONITOREXIT : var0
        if (var2_3 != null) {
            var3_5.onError(new DnsException(1, (Throwable)var2_3));
            return;
        }
        var3_5.onAnswer(var1_1.answerbuf, var1_1.rcode);
    }

    static /* synthetic */ int lambda$registerFDListener$9(MessageQueue messageQueue, Executor executor, Object object, CancellationSignal cancellationSignal, Callback callback, FileDescriptor fileDescriptor, int n) {
        messageQueue.removeOnFileDescriptorEventListener(fileDescriptor);
        executor.execute(new _$$Lambda$DnsResolver$hIO7FFv0AXN6Nj0Dzka_LD8S870(object, cancellationSignal, fileDescriptor, callback));
        return 0;
    }

    private void registerFDListener(Executor executor, FileDescriptor fileDescriptor, Callback<? super byte[]> callback, CancellationSignal cancellationSignal, Object object) {
        MessageQueue messageQueue = Looper.getMainLooper().getQueue();
        messageQueue.addOnFileDescriptorEventListener(fileDescriptor, 5, new _$$Lambda$DnsResolver$kxKi6qjPYeR_SIipxW4tYpxyM50(messageQueue, executor, object, cancellationSignal, callback));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$addCancellationSignal$10$DnsResolver(Object object, FileDescriptor fileDescriptor) {
        synchronized (object) {
            this.cancelQuery(fileDescriptor);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$query$6$DnsResolver(Object object, boolean bl, FileDescriptor fileDescriptor, boolean bl2, FileDescriptor fileDescriptor2) {
        synchronized (object) {
            Throwable throwable2;
            block5 : {
                if (bl) {
                    try {
                        this.cancelQuery(fileDescriptor);
                    }
                    catch (Throwable throwable2) {
                        break block5;
                    }
                }
                if (bl2) {
                    this.cancelQuery(fileDescriptor2);
                }
                return;
            }
            throw throwable2;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void query(Network var1_1, String var2_5, int var3_6, int var4_7, Executor var5_8, CancellationSignal var6_9, Callback<? super List<InetAddress>> var7_10) {
        block8 : {
            if (var6_9 != null && var6_9.isCanceled()) {
                return;
            }
            var8_11 = new Object();
            if (var1_1 != null) ** GOTO lbl7
            var1_1 = NetworkUtils.getDnsNetwork();
lbl7: // 2 sources:
            var9_12 = var1_1.getNetIdForResolv();
            try {
                var2_5 = NetworkUtils.resNetworkQuery(var9_12, (String)var2_5, 1, var3_6, var4_7);
                var1_1 = new InetAddressAnswerAccumulator((Network)var1_1, 1, var7_10);
            }
            catch (ErrnoException var1_2) {}
            this.registerFDListener(var5_8, (FileDescriptor)var2_5, (Callback<? super byte[]>)var1_1, var6_9, var8_11);
            if (var6_9 == null) {
                // MONITOREXIT : var8_11
                return;
            }
            this.addCancellationSignal(var6_9, (FileDescriptor)var2_5, var8_11);
            // MONITOREXIT : var8_11
            return;
            break block8;
            catch (ErrnoException var1_3) {
                // empty catch block
            }
        }
        var5_8.execute(new _$$Lambda$DnsResolver$wc3_cnx2aezlAHvMEbQVFaTPAcE(var7_10, (ErrnoException)var1_4));
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void query(Network object, String object2, int n, Executor object3, CancellationSignal cancellationSignal, Callback<? super List<InetAddress>> inetAddressAnswerAccumulator) {
        void var1_5;
        Object network;
        if (cancellationSignal != null && cancellationSignal.isCanceled()) {
            return;
        }
        Object object4 = new Object();
        if (object != null) {
            network = object;
        } else {
            network = NetworkUtils.getDnsNetwork();
        }
        boolean bl = DnsUtils.haveIpv6((Network)network);
        boolean bl2 = DnsUtils.haveIpv4((Network)network);
        if (!bl && !bl2) {
            object3.execute(new _$$Lambda$DnsResolver$kjq9c3feWPGKUPV3AzJBFi1GUvw(inetAddressAnswerAccumulator));
            return;
        }
        int n2 = 0;
        if (bl) {
            try {
                object = NetworkUtils.resNetworkQuery(((Network)network).getNetIdForResolv(), (String)object2, 1, 28, n);
                n2 = 0 + 1;
            }
            catch (ErrnoException errnoException) {
                object3.execute(new _$$Lambda$DnsResolver$uxb9gSgrd6Qyj9SLhCAtOvpxa3I(inetAddressAnswerAccumulator, errnoException));
                return;
            }
        } else {
            object = null;
        }
        try {
            Thread.sleep(2L);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
        if (bl2) {
            try {
                object2 = NetworkUtils.resNetworkQuery(((Network)network).getNetIdForResolv(), (String)object2, 1, 1, n);
                n = n2 + 1;
            }
            catch (ErrnoException errnoException) {
                if (bl) {
                    NetworkUtils.resNetworkCancel((FileDescriptor)object);
                }
                object3.execute(new _$$Lambda$DnsResolver$t5xp_fS_zTQ564hG_PIaWJdBP8c(inetAddressAnswerAccumulator, errnoException));
                return;
            }
        } else {
            object2 = null;
            n = n2;
        }
        inetAddressAnswerAccumulator = new InetAddressAnswerAccumulator((Network)network, n, inetAddressAnswerAccumulator);
        // MONITORENTER : object4
        if (bl) {
            try {
                this.registerFDListener((Executor)object3, (FileDescriptor)object, inetAddressAnswerAccumulator, cancellationSignal, object4);
            }
            catch (Throwable throwable) {
                throw var1_5;
            }
        }
        if (bl2) {
            try {
                this.registerFDListener((Executor)object3, (FileDescriptor)object2, inetAddressAnswerAccumulator, cancellationSignal, object4);
            }
            catch (Throwable throwable) {
                throw var1_5;
            }
        }
        if (cancellationSignal == null) {
            // MONITOREXIT : object4
            return;
        }
        object3 = new _$$Lambda$DnsResolver$DW9jYL2ZOH6BjebIVPhZIrrhoD8(this, object4, bl2, (FileDescriptor)object2, bl, (FileDescriptor)object);
        cancellationSignal.setOnCancelListener((CancellationSignal.OnCancelListener)object3);
        // MONITOREXIT : object4
        return;
        catch (ErrnoException errnoException) {
            object3.execute(new _$$Lambda$DnsResolver$vvKhya16sREGcN1Gxnqgw_LBoV4(inetAddressAnswerAccumulator, errnoException));
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void rawQuery(Network object, String string2, int n, int n2, int n3, Executor executor, CancellationSignal cancellationSignal, Callback<? super byte[]> callback) {
        void var8_12;
        void var1_5;
        void var6_10;
        block12 : {
            int n4;
            void var7_11;
            Object object2;
            block11 : {
                if (var7_11 != null && var7_11.isCanceled()) {
                    return;
                }
                object2 = new Object();
                if (object != null) {
                    try {
                        n4 = ((Network)object).getNetIdForResolv();
                        break block11;
                    }
                    catch (ErrnoException errnoException) {
                        break block12;
                    }
                }
                n4 = 0;
            }
            try {
                void var2_6;
                void var5_9;
                void var4_8;
                void var3_7;
                object = NetworkUtils.resNetworkQuery(n4, (String)var2_6, (int)var3_7, (int)var4_8, (int)var5_9);
            }
            catch (ErrnoException errnoException) {
                // empty catch block
            }
            synchronized (object2) {
                this.registerFDListener((Executor)var6_10, (FileDescriptor)object, (Callback<? super byte[]>)var8_12, (CancellationSignal)var7_11, object2);
                if (var7_11 == null) {
                    return;
                }
                try {
                    this.addCancellationSignal((CancellationSignal)var7_11, (FileDescriptor)object, object2);
                    return;
                }
                catch (Throwable throwable) {}
                throw throwable;
            }
        }
        var6_10.execute(new _$$Lambda$DnsResolver$GTAgQiExADAzbCx0WiV_97W72_g((Callback)var8_12, (ErrnoException)var1_5));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void rawQuery(Network object, byte[] arrby, int n, Executor executor, CancellationSignal cancellationSignal, Callback<? super byte[]> callback) {
        ErrnoException errnoException2;
        block8 : {
            Object object2;
            int n2;
            block7 : {
                if (cancellationSignal != null && cancellationSignal.isCanceled()) {
                    return;
                }
                object2 = new Object();
                if (object != null) {
                    try {
                        n2 = ((Network)object).getNetIdForResolv();
                        break block7;
                    }
                    catch (ErrnoException errnoException2) {
                        break block8;
                    }
                }
                n2 = 0;
            }
            object = NetworkUtils.resNetworkSend(n2, arrby, arrby.length, n);
            synchronized (object2) {
                this.registerFDListener(executor, (FileDescriptor)object, callback, cancellationSignal, object2);
                if (cancellationSignal == null) {
                    return;
                }
                this.addCancellationSignal(cancellationSignal, (FileDescriptor)object, object2);
                return;
            }
        }
        executor.execute(new _$$Lambda$DnsResolver$h2SsAzA5_rVr_mzxppK8PJLQe98(callback, errnoException2));
    }

    public static interface Callback<T> {
        public void onAnswer(T var1, int var2);

        public void onError(DnsException var1);
    }

    private static class DnsAddressAnswer
    extends DnsPacket {
        private static final boolean DBG = false;
        private static final String TAG = "DnsResolver.DnsAddressAnswer";
        private final int mQueryType;

        DnsAddressAnswer(byte[] arrby) throws ParseException {
            super(arrby);
            if ((this.mHeader.flags & 32768) != 0) {
                if (this.mHeader.getRecordCount(0) != 0) {
                    this.mQueryType = ((DnsPacket.DnsRecord)this.mRecords[0].get((int)0)).nsType;
                    return;
                }
                throw new ParseException("No question found");
            }
            throw new ParseException("Not an answer packet");
        }

        public List<InetAddress> getAddresses() {
            ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>();
            if (this.mHeader.getRecordCount(1) == 0) {
                return arrayList;
            }
            for (DnsPacket.DnsRecord dnsRecord : this.mRecords[1]) {
                int n = dnsRecord.nsType;
                if (n != this.mQueryType || n != 1 && n != 28) continue;
                try {
                    arrayList.add(InetAddress.getByAddress(dnsRecord.getRR()));
                }
                catch (UnknownHostException unknownHostException) {}
            }
            return arrayList;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface DnsError {
    }

    public static class DnsException
    extends Exception {
        public final int code;

        DnsException(int n, Throwable throwable) {
            super(throwable);
            this.code = n;
        }
    }

    public static final class DnsResponse {
        public final byte[] answerbuf;
        public final int rcode;

        public DnsResponse(byte[] arrby, int n) {
            this.answerbuf = arrby;
            this.rcode = n;
        }
    }

    private class InetAddressAnswerAccumulator
    implements Callback<byte[]> {
        private final List<InetAddress> mAllAnswers;
        private DnsException mDnsException;
        private final Network mNetwork;
        private int mRcode;
        private int mReceivedAnswerCount = 0;
        private final int mTargetAnswerCount;
        private final Callback<? super List<InetAddress>> mUserCallback;

        InetAddressAnswerAccumulator(Network network, int n, Callback<? super List<InetAddress>> callback) {
            this.mNetwork = network;
            this.mTargetAnswerCount = n;
            this.mAllAnswers = new ArrayList<InetAddress>();
            this.mUserCallback = callback;
        }

        private void maybeReportAnswer() {
            int n;
            this.mReceivedAnswerCount = n = this.mReceivedAnswerCount + 1;
            if (n != this.mTargetAnswerCount) {
                return;
            }
            if (this.mAllAnswers.isEmpty() && this.maybeReportError()) {
                return;
            }
            this.mUserCallback.onAnswer(DnsUtils.rfc6724Sort(this.mNetwork, this.mAllAnswers), this.mRcode);
        }

        private boolean maybeReportError() {
            int n = this.mRcode;
            if (n != 0) {
                this.mUserCallback.onAnswer(this.mAllAnswers, n);
                return true;
            }
            DnsException dnsException = this.mDnsException;
            if (dnsException != null) {
                this.mUserCallback.onError(dnsException);
                return true;
            }
            return false;
        }

        @Override
        public void onAnswer(byte[] arrby, int n) {
            if (this.mReceivedAnswerCount == 0 || n == 0) {
                this.mRcode = n;
            }
            try {
                List<InetAddress> list = this.mAllAnswers;
                DnsAddressAnswer dnsAddressAnswer = new DnsAddressAnswer(arrby);
                list.addAll(dnsAddressAnswer.getAddresses());
            }
            catch (ParseException parseException) {
                this.mDnsException = new DnsException(0, parseException);
            }
            this.maybeReportAnswer();
        }

        @Override
        public void onError(DnsException dnsException) {
            this.mDnsException = dnsException;
            this.maybeReportAnswer();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface QueryClass {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface QueryFlag {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface QueryType {
    }

}

