/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class StringIndexOutOfBoundsException
extends IndexOutOfBoundsException {
    private static final long serialVersionUID = -6762910422159637258L;

    public StringIndexOutOfBoundsException() {
    }

    public StringIndexOutOfBoundsException(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("String index out of range: ");
        stringBuilder.append(n);
        super(stringBuilder.toString());
    }

    StringIndexOutOfBoundsException(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("length=");
        stringBuilder.append(n);
        stringBuilder.append("; index=");
        stringBuilder.append(n2);
        super(stringBuilder.toString());
    }

    StringIndexOutOfBoundsException(int n, int n2, int n3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("length=");
        stringBuilder.append(n);
        stringBuilder.append("; regionStart=");
        stringBuilder.append(n2);
        stringBuilder.append("; regionLength=");
        stringBuilder.append(n3);
        super(stringBuilder.toString());
    }

    public StringIndexOutOfBoundsException(String string) {
        super(string);
    }

    StringIndexOutOfBoundsException(String string, int n) {
        this(string.length(), n);
    }

    StringIndexOutOfBoundsException(String string, int n, int n2) {
        this(string.length(), n, n2);
    }
}

