/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import javax.crypto.BadPaddingException;

public class AEADBadTagException
extends BadPaddingException {
    private static final long serialVersionUID = -488059093241685509L;

    public AEADBadTagException() {
    }

    public AEADBadTagException(String string) {
        super(string);
    }
}

