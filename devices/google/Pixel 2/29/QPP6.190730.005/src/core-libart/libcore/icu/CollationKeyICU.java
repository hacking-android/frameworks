/*
 * Decompiled with CFR 0.145.
 */
package libcore.icu;

import java.text.CollationKey;

public final class CollationKeyICU
extends CollationKey {
    private final android.icu.text.CollationKey key;

    public CollationKeyICU(String string, android.icu.text.CollationKey collationKey) {
        super(string);
        this.key = collationKey;
    }

    @Override
    public int compareTo(CollationKey comparable) {
        comparable = comparable instanceof CollationKeyICU ? ((CollationKeyICU)comparable).key : new android.icu.text.CollationKey(((CollationKey)comparable).getSourceString(), ((CollationKey)comparable).toByteArray());
        return this.key.compareTo((android.icu.text.CollationKey)comparable);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof CollationKey)) {
            return false;
        }
        if (this.compareTo((CollationKey)object) != 0) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public byte[] toByteArray() {
        return this.key.toByteArray();
    }
}

