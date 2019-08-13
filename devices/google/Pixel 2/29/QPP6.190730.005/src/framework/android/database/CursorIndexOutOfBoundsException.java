/*
 * Decompiled with CFR 0.145.
 */
package android.database;

public class CursorIndexOutOfBoundsException
extends IndexOutOfBoundsException {
    public CursorIndexOutOfBoundsException(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index ");
        stringBuilder.append(n);
        stringBuilder.append(" requested, with a size of ");
        stringBuilder.append(n2);
        super(stringBuilder.toString());
    }

    public CursorIndexOutOfBoundsException(String string2) {
        super(string2);
    }
}

