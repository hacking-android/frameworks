/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import libcore.util.Objects;

public final class StructCapUserHeader {
    public final int pid;
    public int version;

    public StructCapUserHeader(int n, int n2) {
        this.version = n;
        this.pid = n2;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

