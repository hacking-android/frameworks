/*
 * Decompiled with CFR 0.145.
 */
package android.net;

public class Credentials {
    private final int gid;
    private final int pid;
    private final int uid;

    public Credentials(int n, int n2, int n3) {
        this.pid = n;
        this.uid = n2;
        this.gid = n3;
    }

    public int getGid() {
        return this.gid;
    }

    public int getPid() {
        return this.pid;
    }

    public int getUid() {
        return this.uid;
    }
}

