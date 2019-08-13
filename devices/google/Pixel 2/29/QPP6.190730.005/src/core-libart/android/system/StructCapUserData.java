/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import libcore.util.Objects;

public final class StructCapUserData {
    public final int effective;
    public final int inheritable;
    public final int permitted;

    public StructCapUserData(int n, int n2, int n3) {
        this.effective = n;
        this.permitted = n2;
        this.inheritable = n3;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

