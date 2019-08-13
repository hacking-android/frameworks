/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import libcore.util.Objects;

public final class StructUtsname {
    public final String machine;
    public final String nodename;
    public final String release;
    public final String sysname;
    public final String version;

    public StructUtsname(String string, String string2, String string3, String string4, String string5) {
        this.sysname = string;
        this.nodename = string2;
        this.release = string3;
        this.version = string4;
        this.machine = string5;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

