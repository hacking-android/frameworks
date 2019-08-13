/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import java.util.Locale;

public final class Domain {
    public final String hostname;
    public final boolean subdomainsIncluded;

    public Domain(String string2, boolean bl) {
        if (string2 != null) {
            this.hostname = string2.toLowerCase(Locale.US);
            this.subdomainsIncluded = bl;
            return;
        }
        throw new NullPointerException("Hostname must not be null");
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Domain)) {
            return false;
        }
        object = (Domain)object;
        if (((Domain)object).subdomainsIncluded != this.subdomainsIncluded || !((Domain)object).hostname.equals(this.hostname)) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        int n = this.hostname.hashCode();
        int n2 = this.subdomainsIncluded ? 1231 : 1237;
        return n ^ n2;
    }
}

