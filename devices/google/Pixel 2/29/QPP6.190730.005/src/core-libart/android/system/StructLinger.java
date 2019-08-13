/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import libcore.util.Objects;

public final class StructLinger {
    public final int l_linger;
    public final int l_onoff;

    public StructLinger(int n, int n2) {
        this.l_onoff = n;
        this.l_linger = n2;
    }

    public boolean isOn() {
        boolean bl = this.l_onoff != 0;
        return bl;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

