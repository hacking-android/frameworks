/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

public class PeerHandle {
    public int peerId;

    public PeerHandle(int n) {
        this.peerId = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof PeerHandle)) {
            return false;
        }
        if (this.peerId != ((PeerHandle)object).peerId) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return this.peerId;
    }
}

