/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class ClassCircularityError
extends LinkageError {
    private static final long serialVersionUID = 1054362542914539689L;

    public ClassCircularityError() {
    }

    public ClassCircularityError(String string) {
        super(string);
    }
}

