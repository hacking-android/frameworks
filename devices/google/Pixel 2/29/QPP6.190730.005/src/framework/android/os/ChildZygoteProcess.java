/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.net.LocalSocketAddress;
import android.os.ZygoteProcess;

public class ChildZygoteProcess
extends ZygoteProcess {
    private final int mPid;

    ChildZygoteProcess(LocalSocketAddress localSocketAddress, int n) {
        super(localSocketAddress, null);
        this.mPid = n;
    }

    public int getPid() {
        return this.mPid;
    }
}

