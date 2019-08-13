/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import javax.net.ssl.SNIServerName;

public abstract class SNIMatcher {
    private final int type;

    protected SNIMatcher(int n) {
        if (n >= 0) {
            if (n <= 255) {
                this.type = n;
                return;
            }
            throw new IllegalArgumentException("Server name type cannot be greater than 255");
        }
        throw new IllegalArgumentException("Server name type cannot be less than zero");
    }

    public final int getType() {
        return this.type;
    }

    public abstract boolean matches(SNIServerName var1);
}

