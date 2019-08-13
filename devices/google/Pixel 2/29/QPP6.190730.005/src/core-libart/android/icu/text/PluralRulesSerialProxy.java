/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.PluralRules;
import java.io.ObjectStreamException;
import java.io.Serializable;

class PluralRulesSerialProxy
implements Serializable {
    private static final long serialVersionUID = 42L;
    private final String data;

    PluralRulesSerialProxy(String string) {
        this.data = string;
    }

    private Object readResolve() throws ObjectStreamException {
        return PluralRules.createRules(this.data);
    }
}

