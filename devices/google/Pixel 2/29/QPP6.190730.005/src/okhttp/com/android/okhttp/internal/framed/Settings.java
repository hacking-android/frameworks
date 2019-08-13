/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.framed;

import java.util.Arrays;

public final class Settings {
    static final int CLIENT_CERTIFICATE_VECTOR_SIZE = 8;
    static final int COUNT = 10;
    static final int CURRENT_CWND = 5;
    static final int DEFAULT_INITIAL_WINDOW_SIZE = 65536;
    static final int DOWNLOAD_BANDWIDTH = 2;
    static final int DOWNLOAD_RETRANS_RATE = 6;
    static final int ENABLE_PUSH = 2;
    static final int FLAG_CLEAR_PREVIOUSLY_PERSISTED_SETTINGS = 1;
    static final int FLOW_CONTROL_OPTIONS = 10;
    static final int FLOW_CONTROL_OPTIONS_DISABLED = 1;
    static final int HEADER_TABLE_SIZE = 1;
    static final int INITIAL_WINDOW_SIZE = 7;
    static final int MAX_CONCURRENT_STREAMS = 4;
    static final int MAX_FRAME_SIZE = 5;
    static final int MAX_HEADER_LIST_SIZE = 6;
    static final int PERSISTED = 2;
    static final int PERSIST_VALUE = 1;
    static final int ROUND_TRIP_TIME = 3;
    static final int UPLOAD_BANDWIDTH = 1;
    private int persistValue;
    private int persisted;
    private int set;
    private final int[] values = new int[10];

    void clear() {
        this.persisted = 0;
        this.persistValue = 0;
        this.set = 0;
        Arrays.fill(this.values, 0);
    }

    int flags(int n) {
        int n2 = 0;
        if (this.isPersisted(n)) {
            n2 = 0 | 2;
        }
        int n3 = n2;
        if (this.persistValue(n)) {
            n3 = n2 | 1;
        }
        return n3;
    }

    int get(int n) {
        return this.values[n];
    }

    int getClientCertificateVectorSize(int n) {
        block0 : {
            if ((this.set & 256) == 0) break block0;
            n = this.values[8];
        }
        return n;
    }

    int getCurrentCwnd(int n) {
        block0 : {
            if ((this.set & 32) == 0) break block0;
            n = this.values[5];
        }
        return n;
    }

    int getDownloadBandwidth(int n) {
        block0 : {
            if ((this.set & 4) == 0) break block0;
            n = this.values[2];
        }
        return n;
    }

    int getDownloadRetransRate(int n) {
        block0 : {
            if ((this.set & 64) == 0) break block0;
            n = this.values[6];
        }
        return n;
    }

    boolean getEnablePush(boolean bl) {
        int n = this.set;
        boolean bl2 = false;
        n = (n & 4) != 0 ? this.values[2] : (bl ? 1 : 0);
        bl = bl2;
        if (n == 1) {
            bl = true;
        }
        return bl;
    }

    int getHeaderTableSize() {
        int n = (this.set & 2) != 0 ? this.values[1] : -1;
        return n;
    }

    int getInitialWindowSize(int n) {
        block0 : {
            if ((this.set & 128) == 0) break block0;
            n = this.values[7];
        }
        return n;
    }

    int getMaxConcurrentStreams(int n) {
        block0 : {
            if ((this.set & 16) == 0) break block0;
            n = this.values[4];
        }
        return n;
    }

    int getMaxFrameSize(int n) {
        block0 : {
            if ((this.set & 32) == 0) break block0;
            n = this.values[5];
        }
        return n;
    }

    int getMaxHeaderListSize(int n) {
        block0 : {
            if ((this.set & 64) == 0) break block0;
            n = this.values[6];
        }
        return n;
    }

    int getRoundTripTime(int n) {
        block0 : {
            if ((this.set & 8) == 0) break block0;
            n = this.values[3];
        }
        return n;
    }

    int getUploadBandwidth(int n) {
        block0 : {
            if ((this.set & 2) == 0) break block0;
            n = this.values[1];
        }
        return n;
    }

    boolean isFlowControlDisabled() {
        int n = this.set;
        boolean bl = false;
        if (((n = (n & 1024) != 0 ? this.values[10] : 0) & 1) != 0) {
            bl = true;
        }
        return bl;
    }

    boolean isPersisted(int n) {
        boolean bl = true;
        if ((this.persisted & 1 << n) == 0) {
            bl = false;
        }
        return bl;
    }

    boolean isSet(int n) {
        boolean bl = true;
        if ((this.set & 1 << n) == 0) {
            bl = false;
        }
        return bl;
    }

    void merge(Settings settings) {
        for (int i = 0; i < 10; ++i) {
            if (!settings.isSet(i)) continue;
            this.set(i, settings.flags(i), settings.get(i));
        }
    }

    boolean persistValue(int n) {
        boolean bl = true;
        if ((this.persistValue & 1 << n) == 0) {
            bl = false;
        }
        return bl;
    }

    Settings set(int n, int n2, int n3) {
        if (n >= this.values.length) {
            return this;
        }
        int n4 = 1 << n;
        this.set |= n4;
        this.persistValue = (n2 & 1) != 0 ? (this.persistValue |= n4) : (this.persistValue &= n4);
        this.persisted = (n2 & 2) != 0 ? (this.persisted |= n4) : (this.persisted &= n4);
        this.values[n] = n3;
        return this;
    }

    int size() {
        return Integer.bitCount(this.set);
    }
}

