/*
 * Decompiled with CFR 0.145.
 */
package junit.framework;

public class AssertionFailedError
extends AssertionError {
    private static final long serialVersionUID = 1L;

    public AssertionFailedError() {
    }

    public AssertionFailedError(String string) {
        super((Object)AssertionFailedError.defaultString(string));
    }

    private static String defaultString(String string) {
        block0 : {
            if (string != null) break block0;
            string = "";
        }
        return string;
    }
}

