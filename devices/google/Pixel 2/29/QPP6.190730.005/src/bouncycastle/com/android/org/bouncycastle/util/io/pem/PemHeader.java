/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.io.pem;

public class PemHeader {
    private String name;
    private String value;

    public PemHeader(String string, String string2) {
        this.name = string;
        this.value = string2;
    }

    private int getHashCode(String string) {
        if (string == null) {
            return 1;
        }
        return string.hashCode();
    }

    private boolean isEqual(String string, String string2) {
        if (string == string2) {
            return true;
        }
        if (string != null && string2 != null) {
            return string.equals(string2);
        }
        return false;
    }

    public boolean equals(Object object) {
        boolean bl;
        block5 : {
            block4 : {
                bl = object instanceof PemHeader;
                boolean bl2 = false;
                if (!bl) {
                    return false;
                }
                if ((object = (PemHeader)object) == this) break block4;
                bl = bl2;
                if (!this.isEqual(this.name, ((PemHeader)object).name)) break block5;
                bl = bl2;
                if (!this.isEqual(this.value, ((PemHeader)object).value)) break block5;
            }
            bl = true;
        }
        return bl;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.getHashCode(this.name) + this.getHashCode(this.value) * 31;
    }
}

