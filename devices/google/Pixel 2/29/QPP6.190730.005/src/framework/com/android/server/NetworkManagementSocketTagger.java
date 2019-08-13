/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.SocketTagger
 */
package com.android.server;

import android.os.StrictMode;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Slog;
import dalvik.system.SocketTagger;
import java.io.FileDescriptor;
import java.net.SocketException;

public final class NetworkManagementSocketTagger
extends SocketTagger {
    private static final boolean LOGD = false;
    public static final String PROP_QTAGUID_ENABLED = "net.qtaguid_enabled";
    private static final String TAG = "NetworkManagementSocketTagger";
    private static ThreadLocal<SocketTags> threadSocketTags = new ThreadLocal<SocketTags>(){

        @Override
        protected SocketTags initialValue() {
            return new SocketTags();
        }
    };

    public static int getThreadSocketStatsTag() {
        return NetworkManagementSocketTagger.threadSocketTags.get().statsTag;
    }

    public static int getThreadSocketStatsUid() {
        return NetworkManagementSocketTagger.threadSocketTags.get().statsUid;
    }

    public static void install() {
        SocketTagger.set((SocketTagger)new NetworkManagementSocketTagger());
    }

    public static int kernelToTag(String string2) {
        int n = string2.length();
        if (n > 10) {
            return Long.decode(string2.substring(0, n - 8)).intValue();
        }
        return 0;
    }

    private static native int native_deleteTagData(int var0, int var1);

    private static native int native_setCounterSet(int var0, int var1);

    private static native int native_tagSocketFd(FileDescriptor var0, int var1, int var2);

    private static native int native_untagSocketFd(FileDescriptor var0);

    public static void resetKernelUidStats(int n) {
        int n2;
        if (SystemProperties.getBoolean("net.qtaguid_enabled", false) && (n2 = NetworkManagementSocketTagger.native_deleteTagData(0, n)) < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("problem clearing counters for uid ");
            stringBuilder.append(n);
            stringBuilder.append(" : errno ");
            stringBuilder.append(n2);
            Slog.w("NetworkManagementSocketTagger", stringBuilder.toString());
        }
    }

    public static void setKernelCounterSet(int n, int n2) {
        int n3;
        if (SystemProperties.getBoolean("net.qtaguid_enabled", false) && (n3 = NetworkManagementSocketTagger.native_setCounterSet(n2, n)) < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setKernelCountSet(");
            stringBuilder.append(n);
            stringBuilder.append(", ");
            stringBuilder.append(n2);
            stringBuilder.append(") failed with errno ");
            stringBuilder.append(n3);
            Log.w("NetworkManagementSocketTagger", stringBuilder.toString());
        }
    }

    public static int setThreadSocketStatsTag(int n) {
        int n2 = NetworkManagementSocketTagger.threadSocketTags.get().statsTag;
        NetworkManagementSocketTagger.threadSocketTags.get().statsTag = n;
        return n2;
    }

    public static int setThreadSocketStatsUid(int n) {
        int n2 = NetworkManagementSocketTagger.threadSocketTags.get().statsUid;
        NetworkManagementSocketTagger.threadSocketTags.get().statsUid = n;
        return n2;
    }

    private void tagSocketFd(FileDescriptor fileDescriptor, int n, int n2) {
        int n3;
        if (n == -1 && n2 == -1) {
            return;
        }
        if (SystemProperties.getBoolean("net.qtaguid_enabled", false) && (n3 = NetworkManagementSocketTagger.native_tagSocketFd(fileDescriptor, n, n2)) < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("tagSocketFd(");
            stringBuilder.append(fileDescriptor.getInt$());
            stringBuilder.append(", ");
            stringBuilder.append(n);
            stringBuilder.append(", ");
            stringBuilder.append(n2);
            stringBuilder.append(") failed with errno");
            stringBuilder.append(n3);
            Log.i("NetworkManagementSocketTagger", stringBuilder.toString());
        }
    }

    private void unTagSocketFd(FileDescriptor fileDescriptor) {
        int n;
        Object object = threadSocketTags.get();
        if (((SocketTags)object).statsTag == -1 && ((SocketTags)object).statsUid == -1) {
            return;
        }
        if (SystemProperties.getBoolean("net.qtaguid_enabled", false) && (n = NetworkManagementSocketTagger.native_untagSocketFd(fileDescriptor)) < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("untagSocket(");
            ((StringBuilder)object).append(fileDescriptor.getInt$());
            ((StringBuilder)object).append(") failed with errno ");
            ((StringBuilder)object).append(n);
            Log.w("NetworkManagementSocketTagger", ((StringBuilder)object).toString());
        }
    }

    public void tag(FileDescriptor fileDescriptor) throws SocketException {
        SocketTags socketTags = threadSocketTags.get();
        if (socketTags.statsTag == -1 && StrictMode.vmUntaggedSocketEnabled()) {
            StrictMode.onUntaggedSocket();
        }
        this.tagSocketFd(fileDescriptor, socketTags.statsTag, socketTags.statsUid);
    }

    public void untag(FileDescriptor fileDescriptor) throws SocketException {
        this.unTagSocketFd(fileDescriptor);
    }

    public static class SocketTags {
        public int statsTag = -1;
        public int statsUid = -1;
    }

}

