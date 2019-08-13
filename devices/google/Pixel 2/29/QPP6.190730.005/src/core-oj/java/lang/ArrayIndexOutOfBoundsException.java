/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class ArrayIndexOutOfBoundsException
extends IndexOutOfBoundsException {
    private static final long serialVersionUID = -5116101128118950844L;

    public ArrayIndexOutOfBoundsException() {
    }

    public ArrayIndexOutOfBoundsException(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Array index out of range: ");
        stringBuilder.append(n);
        super(stringBuilder.toString());
    }

    public ArrayIndexOutOfBoundsException(String string) {
        super(string);
    }
}

