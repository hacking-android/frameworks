/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.lang.UCharacter;

public class CaseInsensitiveString {
    private String folded = null;
    private int hash = 0;
    private String string;

    public CaseInsensitiveString(String string) {
        this.string = string;
    }

    private static String foldCase(String string) {
        return UCharacter.foldCase(string, true);
    }

    private void getFolded() {
        if (this.folded == null) {
            this.folded = CaseInsensitiveString.foldCase(this.string);
        }
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof CaseInsensitiveString) {
            this.getFolded();
            object = (CaseInsensitiveString)object;
            CaseInsensitiveString.super.getFolded();
            return this.folded.equals(((CaseInsensitiveString)object).folded);
        }
        return false;
    }

    public String getString() {
        return this.string;
    }

    public int hashCode() {
        this.getFolded();
        if (this.hash == 0) {
            this.hash = this.folded.hashCode();
        }
        return this.hash;
    }

    public String toString() {
        return this.string;
    }
}

