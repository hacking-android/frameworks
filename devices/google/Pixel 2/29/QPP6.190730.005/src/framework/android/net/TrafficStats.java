/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.SocketTagger
 */
package android.net;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.net.INetworkStatsService;
import android.net.INetworkStatsSession;
import android.net.NetworkStats;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.server.NetworkManagementSocketTagger;
import dalvik.system.SocketTagger;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class TrafficStats {
    @Deprecated
    public static final long GB_IN_BYTES = 0x40000000L;
    @Deprecated
    public static final long KB_IN_BYTES = 1024L;
    private static final String LOOPBACK_IFACE = "lo";
    @Deprecated
    public static final long MB_IN_BYTES = 0x100000L;
    @Deprecated
    public static final long PB_IN_BYTES = 0x4000000000000L;
    @SystemApi
    public static final int TAG_NETWORK_STACK_IMPERSONATION_RANGE_END = -113;
    @SystemApi
    public static final int TAG_NETWORK_STACK_IMPERSONATION_RANGE_START = -128;
    @SystemApi
    public static final int TAG_NETWORK_STACK_RANGE_END = -257;
    @SystemApi
    public static final int TAG_NETWORK_STACK_RANGE_START = -768;
    public static final int TAG_SYSTEM_APP = -251;
    public static final int TAG_SYSTEM_BACKUP = -253;
    public static final int TAG_SYSTEM_DOWNLOAD = -255;
    @SystemApi
    public static final int TAG_SYSTEM_IMPERSONATION_RANGE_END = -241;
    @SystemApi
    public static final int TAG_SYSTEM_IMPERSONATION_RANGE_START = -256;
    public static final int TAG_SYSTEM_MEDIA = -254;
    public static final int TAG_SYSTEM_PROBE = -190;
    public static final int TAG_SYSTEM_RESTORE = -252;
    @Deprecated
    public static final long TB_IN_BYTES = 0x10000000000L;
    private static final int TYPE_RX_BYTES = 0;
    private static final int TYPE_RX_PACKETS = 1;
    private static final int TYPE_TCP_RX_PACKETS = 4;
    private static final int TYPE_TCP_TX_PACKETS = 5;
    private static final int TYPE_TX_BYTES = 2;
    private static final int TYPE_TX_PACKETS = 3;
    public static final int UID_REMOVED = -4;
    public static final int UID_TETHERING = -5;
    public static final int UNSUPPORTED = -1;
    private static NetworkStats sActiveProfilingStart;
    private static Object sProfilingLock;
    private static INetworkStatsService sStatsService;

    static {
        sProfilingLock = new Object();
    }

    private static long addIfSupported(long l) {
        block0 : {
            if (l != -1L) break block0;
            l = 0L;
        }
        return l;
    }

    public static void clearThreadStatsTag() {
        NetworkManagementSocketTagger.setThreadSocketStatsTag(-1);
    }

    @SuppressLint(value={"Doclava125"})
    public static void clearThreadStatsUid() {
        NetworkManagementSocketTagger.setThreadSocketStatsUid(-1);
    }

    public static void closeQuietly(INetworkStatsSession iNetworkStatsSession) {
        if (iNetworkStatsSession != null) {
            try {
                iNetworkStatsSession.close();
            }
            catch (Exception exception) {
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
        }
    }

    public static int getAndSetThreadStatsTag(int n) {
        return NetworkManagementSocketTagger.setThreadSocketStatsTag(n);
    }

    private static NetworkStats getDataLayerSnapshotForUid(Context object) {
        int n = Process.myUid();
        try {
            object = TrafficStats.getStatsService().getDataLayerSnapshotForUid(n);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getLoopbackRxBytes() {
        try {
            long l = TrafficStats.getStatsService().getIfaceStats(LOOPBACK_IFACE, 0);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getLoopbackRxPackets() {
        try {
            long l = TrafficStats.getStatsService().getIfaceStats(LOOPBACK_IFACE, 1);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getLoopbackTxBytes() {
        try {
            long l = TrafficStats.getStatsService().getIfaceStats(LOOPBACK_IFACE, 2);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getLoopbackTxPackets() {
        try {
            long l = TrafficStats.getStatsService().getIfaceStats(LOOPBACK_IFACE, 3);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=130143562L)
    private static String[] getMobileIfaces() {
        try {
            String[] arrstring = TrafficStats.getStatsService().getMobileIfaces();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getMobileRxBytes() {
        long l = 0L;
        String[] arrstring = TrafficStats.getMobileIfaces();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            l += TrafficStats.addIfSupported(TrafficStats.getRxBytes(arrstring[i]));
        }
        return l;
    }

    public static long getMobileRxPackets() {
        long l = 0L;
        String[] arrstring = TrafficStats.getMobileIfaces();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            l += TrafficStats.addIfSupported(TrafficStats.getRxPackets(arrstring[i]));
        }
        return l;
    }

    @UnsupportedAppUsage
    public static long getMobileTcpRxPackets() {
        long l = 0L;
        for (String string2 : TrafficStats.getMobileIfaces()) {
            long l2;
            try {
                l2 = TrafficStats.getStatsService().getIfaceStats(string2, 4);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            l += TrafficStats.addIfSupported(l2);
        }
        return l;
    }

    @UnsupportedAppUsage
    public static long getMobileTcpTxPackets() {
        long l = 0L;
        for (String string2 : TrafficStats.getMobileIfaces()) {
            long l2;
            try {
                l2 = TrafficStats.getStatsService().getIfaceStats(string2, 5);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            l += TrafficStats.addIfSupported(l2);
        }
        return l;
    }

    public static long getMobileTxBytes() {
        long l = 0L;
        String[] arrstring = TrafficStats.getMobileIfaces();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            l += TrafficStats.addIfSupported(TrafficStats.getTxBytes(arrstring[i]));
        }
        return l;
    }

    public static long getMobileTxPackets() {
        long l = 0L;
        String[] arrstring = TrafficStats.getMobileIfaces();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            l += TrafficStats.addIfSupported(TrafficStats.getTxPackets(arrstring[i]));
        }
        return l;
    }

    @UnsupportedAppUsage
    public static long getRxBytes(String string2) {
        try {
            long l = TrafficStats.getStatsService().getIfaceStats(string2, 0);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getRxPackets(String string2) {
        try {
            long l = TrafficStats.getStatsService().getIfaceStats(string2, 1);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=130143562L)
    private static INetworkStatsService getStatsService() {
        synchronized (TrafficStats.class) {
            if (sStatsService == null) {
                sStatsService = INetworkStatsService.Stub.asInterface(ServiceManager.getService("netstats"));
            }
            INetworkStatsService iNetworkStatsService = sStatsService;
            return iNetworkStatsService;
        }
    }

    public static int getThreadStatsTag() {
        return NetworkManagementSocketTagger.getThreadSocketStatsTag();
    }

    public static int getThreadStatsUid() {
        return NetworkManagementSocketTagger.getThreadSocketStatsUid();
    }

    public static long getTotalRxBytes() {
        try {
            long l = TrafficStats.getStatsService().getTotalStats(0);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getTotalRxPackets() {
        try {
            long l = TrafficStats.getStatsService().getTotalStats(1);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getTotalTxBytes() {
        try {
            long l = TrafficStats.getStatsService().getTotalStats(2);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getTotalTxPackets() {
        try {
            long l = TrafficStats.getStatsService().getTotalStats(3);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public static long getTxBytes(String string2) {
        try {
            long l = TrafficStats.getStatsService().getIfaceStats(string2, 2);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getTxPackets(String string2) {
        try {
            long l = TrafficStats.getStatsService().getIfaceStats(string2, 3);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getUidRxBytes(int n) {
        int n2 = Process.myUid();
        if (n2 != 1000 && n2 != n) {
            return -1L;
        }
        try {
            long l = TrafficStats.getStatsService().getUidStats(n, 0);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getUidRxPackets(int n) {
        int n2 = Process.myUid();
        if (n2 != 1000 && n2 != n) {
            return -1L;
        }
        try {
            long l = TrafficStats.getStatsService().getUidStats(n, 1);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public static long getUidTcpRxBytes(int n) {
        return -1L;
    }

    @Deprecated
    public static long getUidTcpRxSegments(int n) {
        return -1L;
    }

    @Deprecated
    public static long getUidTcpTxBytes(int n) {
        return -1L;
    }

    @Deprecated
    public static long getUidTcpTxSegments(int n) {
        return -1L;
    }

    public static long getUidTxBytes(int n) {
        int n2 = Process.myUid();
        if (n2 != 1000 && n2 != n) {
            return -1L;
        }
        try {
            long l = TrafficStats.getStatsService().getUidStats(n, 2);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static long getUidTxPackets(int n) {
        int n2 = Process.myUid();
        if (n2 != 1000 && n2 != n) {
            return -1L;
        }
        try {
            long l = TrafficStats.getStatsService().getUidStats(n, 3);
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public static long getUidUdpRxBytes(int n) {
        return -1L;
    }

    @Deprecated
    public static long getUidUdpRxPackets(int n) {
        return -1L;
    }

    @Deprecated
    public static long getUidUdpTxBytes(int n) {
        return -1L;
    }

    @Deprecated
    public static long getUidUdpTxPackets(int n) {
        return -1L;
    }

    public static void incrementOperationCount(int n) {
        TrafficStats.incrementOperationCount(TrafficStats.getThreadStatsTag(), n);
    }

    public static void incrementOperationCount(int n, int n2) {
        int n3 = Process.myUid();
        try {
            TrafficStats.getStatsService().incrementOperationCount(n3, n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static void setThreadStatsTag(int n) {
        NetworkManagementSocketTagger.setThreadSocketStatsTag(n);
    }

    @SystemApi
    public static void setThreadStatsTagApp() {
        TrafficStats.setThreadStatsTag(-251);
    }

    @SystemApi
    public static void setThreadStatsTagBackup() {
        TrafficStats.setThreadStatsTag(-253);
    }

    @SystemApi
    public static void setThreadStatsTagRestore() {
        TrafficStats.setThreadStatsTag(-252);
    }

    @SuppressLint(value={"Doclava125"})
    public static void setThreadStatsUid(int n) {
        NetworkManagementSocketTagger.setThreadSocketStatsUid(n);
    }

    @Deprecated
    public static void setThreadStatsUidSelf() {
        TrafficStats.setThreadStatsUid(Process.myUid());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void startDataProfiling(Context object) {
        Object object2 = sProfilingLock;
        synchronized (object2) {
            if (sActiveProfilingStart == null) {
                sActiveProfilingStart = TrafficStats.getDataLayerSnapshotForUid((Context)object);
                return;
            }
            object = new IllegalStateException("already profiling data");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static NetworkStats stopDataProfiling(Context object) {
        Object object2 = sProfilingLock;
        synchronized (object2) {
            if (sActiveProfilingStart != null) {
                object = NetworkStats.subtract(TrafficStats.getDataLayerSnapshotForUid((Context)object), sActiveProfilingStart, null, null);
                sActiveProfilingStart = null;
                return object;
            }
            object = new IllegalStateException("not profiling data");
            throw object;
        }
    }

    public static void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        SocketTagger.get().tag(datagramSocket);
    }

    public static void tagFileDescriptor(FileDescriptor fileDescriptor) throws IOException {
        SocketTagger.get().tag(fileDescriptor);
    }

    public static void tagSocket(Socket socket) throws SocketException {
        SocketTagger.get().tag(socket);
    }

    public static void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        SocketTagger.get().untag(datagramSocket);
    }

    public static void untagFileDescriptor(FileDescriptor fileDescriptor) throws IOException {
        SocketTagger.get().untag(fileDescriptor);
    }

    public static void untagSocket(Socket socket) throws SocketException {
        SocketTagger.get().untag(socket);
    }
}

