/*
 * Decompiled with CFR 0.145.
 */
package android.net;

public abstract class NetworkSpecifier {
    public void assertValidFromUid(int n) {
    }

    public NetworkSpecifier redact() {
        return this;
    }

    public abstract boolean satisfiedBy(NetworkSpecifier var1);
}

