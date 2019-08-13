/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class Error
extends Throwable {
    static final long serialVersionUID = 4980196508277280342L;

    public Error() {
    }

    public Error(String string) {
        super(string);
    }

    public Error(String string, Throwable throwable) {
        super(string, throwable);
    }

    protected Error(String string, Throwable throwable, boolean bl, boolean bl2) {
        super(string, throwable, bl, bl2);
    }

    public Error(Throwable throwable) {
        super(throwable);
    }
}

