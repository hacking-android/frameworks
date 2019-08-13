/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import libcore.util.Objects;

public final class StructUcred {
    public final int gid;
    public final int pid;
    public final int uid;

    public StructUcred(int n, int n2, int n3) {
        this.pid = n;
        this.uid = n2;
        this.gid = n3;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

