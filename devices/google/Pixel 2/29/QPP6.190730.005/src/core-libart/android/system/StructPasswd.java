/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import libcore.util.Objects;

public final class StructPasswd {
    public final String pw_dir;
    public final int pw_gid;
    public final String pw_name;
    public final String pw_shell;
    public final int pw_uid;

    public StructPasswd(String string, int n, int n2, String string2, String string3) {
        this.pw_name = string;
        this.pw_uid = n;
        this.pw_gid = n2;
        this.pw_dir = string2;
        this.pw_shell = string3;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

